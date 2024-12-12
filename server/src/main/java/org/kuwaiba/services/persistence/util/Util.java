/*
 *  Copyright 2010-2019 Neotropic SAS <contact@neotropic.co>
 *
 *  Licensed under the EPL License, Version 1.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.eclipse.org/legal/epl-v10.html
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.kuwaiba.services.persistence.util;

import com.neotropic.kuwaiba.sync.model.SyncDataSourceConfiguration;
import com.neotropic.kuwaiba.sync.model.SynchronizationGroup;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.kuwaiba.apis.persistence.PersistenceService;
import org.kuwaiba.apis.persistence.application.GroupProfile;
import org.kuwaiba.apis.persistence.application.GroupProfileLight;
import org.kuwaiba.apis.persistence.application.Pool;
import org.kuwaiba.apis.persistence.application.Privilege;
import org.kuwaiba.apis.persistence.application.Task;
import org.kuwaiba.apis.persistence.application.UserProfile;
import org.kuwaiba.apis.persistence.application.UserProfileLight;
import org.kuwaiba.apis.persistence.application.process.ProcessInstance;
import org.kuwaiba.apis.persistence.business.BusinessObjectLight;
import org.kuwaiba.apis.persistence.exceptions.ApplicationObjectNotFoundException;
import org.kuwaiba.apis.persistence.exceptions.InvalidArgumentException;
import org.kuwaiba.apis.persistence.exceptions.MetadataObjectNotFoundException;
import org.kuwaiba.apis.persistence.exceptions.UnsupportedPropertyException;
import org.kuwaiba.apis.persistence.metadata.AttributeMetadata;
import org.kuwaiba.apis.persistence.metadata.ClassMetadata;
import org.kuwaiba.apis.persistence.metadata.ClassMetadataLight;
import org.kuwaiba.apis.persistence.metadata.GenericObjectList;
import org.kuwaiba.services.persistence.cache.CacheManager;
import org.kuwaiba.services.persistence.impl.neo4j.RelTypes;
import org.kuwaiba.apis.persistence.util.StringPair;
import org.kuwaiba.interfaces.ws.toserialize.application.RemotePool;
import org.kuwaiba.interfaces.ws.toserialize.application.TaskNotificationDescriptor;
import org.kuwaiba.interfaces.ws.toserialize.application.TaskScheduleDescriptor;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Path;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.ResourceIterator;
import org.neo4j.graphdb.Result;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.traversal.TraversalDescription;
import org.neo4j.kernel.impl.traversal.MonoDirectionalTraversalDescription;


/**
 * Utility class containing misc methods to perform common tasks
 * @author Charles Edward Bedon Cortazar {@literal <charles.bedon@kuwaiba.org>}
 */
public class Util {
    /**
     * Converts a String value to an object value based on a give mapping. This method
     * does not convert binary or relationship-like attributes
     * @param value Value as String
     * @param type The alleged type of the provided value
     * @return the converted value
     * @throws InvalidArgumentException If the type can't be converted
     */
    public static Object getRealValue(String value, String type) throws InvalidArgumentException {
        if (value == null)
            return null;
        try {
            
            switch (type) {
                case "String":
                    return value;
                case "Float":
                    return Float.valueOf(value);
                case "Long":
                    return Long.valueOf(value);
                case "Integer":
                    return Integer.valueOf(value);
                case "Boolean":
                    return Boolean.valueOf(value);
                case "Date":
                case "Timestamp":
                    return Long.valueOf(value);
                default:
                    throw new InvalidArgumentException(String.format("Type %s not found", type));
            }
            
        }catch (NumberFormatException | InvalidArgumentException e){
            throw new InvalidArgumentException(String.format("Can not convert value %s to a type %s", value, type));
        }
    }

    /**
     * Gets the list type items nodes instances of the provided class and ids
     * @param listTypeIds The ids of the list type items nodes to find
     * @param listTypeClassNode Node the list items are supposed to be instance of
     * @return The corresponding list type item nodes. The ones not found will be ignored
     */
    public static List<Node> getListTypeItemNodes(Node listTypeClassNode, List<String> listTypeIds) {
        Iterable<Relationship> listTypeItems = listTypeClassNode.getRelationships(RelTypes.INSTANCE_OF, Direction.INCOMING);
        
        List<Node> res = new ArrayList<>();
        
        for (Relationship listTypeRelationship : listTypeItems) {
            if (listTypeIds.contains((String)listTypeRelationship.getStartNode().getProperty(Constants.PROPERTY_UUID)) )
                res.add(listTypeRelationship.getStartNode());
        }
        
        return res;
    }

    /**
     * Releases all relationships related to an object given its direction and a relationship's property value
     * @param instance Object from/to the relationships are connected
     * @param relationshipType Relationship type
     * @param relationshipDirection Relationship Direction
     * @param propertyName Relationship's property to be used as filter
     * @param propertyValue Relationship's property value to be used as filter
     */
    public static void releaseRelationships(Node instance, RelTypes relationshipType,
            Direction relationshipDirection, String propertyName, String propertyValue) {
        Iterable<Relationship> relatedItems = instance.getRelationships(relationshipType, relationshipDirection);
        for (Relationship relatedItemRelationship : relatedItems){
            if (relatedItemRelationship.getProperty(propertyName).equals(propertyValue))
                relatedItemRelationship.delete();
        }
    }
    
    public static void deleteTemplateObject(Node instance) {
        for (Relationship rel : instance.getRelationships(Direction.INCOMING, RelTypes.CHILD_OF))
            deleteTemplateObject(rel.getStartNode());

        for (Relationship rel : instance.getRelationships())
            rel.delete();

        instance.delete();
    }

    /**
     * Read and returns the bytes of a given file
     * @param fileName file to be opened
     * @return bytes on that file
     * @throws java.io.FileNotFoundException If the file could not be found
     */
    public static byte[] readBytesFromFile(String fileName) throws FileNotFoundException, IOException{
        byte[] bytes = null;
        File f = new File(fileName);
        try (InputStream is = new FileInputStream(f)) {
            long length = f.length();
            
            if (length < Integer.MAX_VALUE) { //checks if the file is too big
                bytes = new byte[(int)length];
                // Read in the bytes
                int offset = 0;
                int numRead = 0;
                while (offset < bytes.length
                        && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
                    offset += numRead;
                }
                
                // Ensure all the bytes have been read in
                if (offset < bytes.length) {
                    throw new IOException("Could not completely read file " + f.getName());
                }
            }
        }
        return bytes;
    }


    /**
     * Saves a file, receiving the file name and the contents as parameters. If the directory structure doesn't exist, it's created
     * @param directory path to the directory
     * @param fileName the file name
     * @param content the file content
     * @throws FileNotFoundException 
     * @throws IOException
     */
    public static void saveFile(String directory, String fileName, byte[] content) throws FileNotFoundException, IOException {
        java.nio.file.Path directoryPath = FileSystems.getDefault().getPath(directory);
        if (!Files.exists(directoryPath) || !Files.isWritable(directoryPath))
            throw new FileNotFoundException(String.format("Path %s does not exist or is not writeable", directoryPath.toAbsolutePath()));

        try (FileOutputStream fos = new FileOutputStream(directory + "/" + fileName)) { //NOI18N
            fos.write(content);
        }
    }

    /**
     * Gets an object's class name given the node representing it
     * @param objectNode The node to e evaluated
     * @return The object's class name.
     * @throws MetadataObjectNotFoundException if no class node is associated to this node (this should not happen)
     * @throws UnsupportedPropertyException if the class node is malformed
     */
    public static String getObjectClassName(Node objectNode) throws MetadataObjectNotFoundException, UnsupportedPropertyException {
        Iterator<Relationship> iterator = objectNode.getRelationships(RelTypes.INSTANCE_OF).iterator();
        if (!iterator.hasNext())
            throw new MetadataObjectNotFoundException(String.format("The object with id %s does not have a class associated to it", objectNode.getId()));
        
        Node classNode = iterator.next().getEndNode();
        if (!classNode.hasProperty(Constants.PROPERTY_NAME))
            throw new UnsupportedPropertyException(Constants.PROPERTY_NAME);
        return (String)classNode.getProperty(Constants.PROPERTY_NAME);
    }

    /**
     * Creates a ClassMetadata with default values
     * @param classDefinition
     * @return
     * @throws MetadataObjectNotFoundException If the class does not have a name
     */
    public static ClassMetadata setDefaultsForClassMetadatas(ClassMetadata classDefinition) throws MetadataObjectNotFoundException{
        if(classDefinition.getName() == null){
            throw new MetadataObjectNotFoundException("Can not create a class metadata entry without a name");
        }
        if(classDefinition.getDisplayName() == null){
            classDefinition.setDisplayName("");
        }
        if(classDefinition.getDescription() == null){
            classDefinition.setDescription("");
        }
        if(classDefinition.getIcon() == null){
            classDefinition.setIcon(new byte[0]);
        }
        if(classDefinition.getSmallIcon() == null){
            classDefinition.setSmallIcon(new byte[0]);
        }
        
        classDefinition.getColor();
        return classDefinition;
    }

    /**
     * Converts a class metadata node into a ClassMetadataLight object
     * @param classNode the class Node
     * @return a class metadata light object
     */
    public static ClassMetadataLight createClassMetadataLightFromNode(Node classNode)
    {
        ClassMetadataLight myClass = new ClassMetadataLight(classNode.getId(),(String)classNode.getProperty(Constants.PROPERTY_NAME),(String)classNode.getProperty(Constants.PROPERTY_DISPLAY_NAME));
        
        myClass.setAbstract((Boolean)classNode.getProperty(Constants.PROPERTY_ABSTRACT));
        myClass.setSmallIcon((byte[])classNode.getProperty(Constants.PROPERTY_SMALL_ICON));
        myClass.setColor((Integer)classNode.getProperty(Constants.PROPERTY_COLOR));
        myClass.setCustom((Boolean)classNode.getProperty(Constants.PROPERTY_CUSTOM));
        myClass.setInDesign((Boolean)classNode.getProperty(Constants.PROPERTY_IN_DESIGN));
        myClass.setViewable((Boolean)isSubclassOf(Constants.CLASS_VIEWABLEOBJECT, classNode));
        myClass.setListType((Boolean)isSubclassOf(Constants.CLASS_GENERICOBJECTLIST, classNode));
        myClass.setId(classNode.getId());
        
        //Parent
        if (classNode.getSingleRelationship(RelTypes.EXTENDS, Direction.OUTGOING) != null){
            myClass.setParentClassName(classNode.getSingleRelationship(
                    RelTypes.EXTENDS, Direction.OUTGOING).getEndNode().getProperty(Constants.PROPERTY_NAME).toString());
        }
        else
            myClass.setParentClassName(null);
        return myClass;
    }

    /**
     * Converts a class metadata node into a ClassMetadata object
     * @param classNode a class metadata node
     * @return a class metadata object
     */
    public static ClassMetadata createClassMetadataFromNode(Node classNode)
    {
        ClassMetadata myClass = new ClassMetadata();
        List<AttributeMetadata> listAttributes = new ArrayList<>();

        myClass.setName((String)classNode.getProperty(Constants.PROPERTY_NAME));
        Iterable<Label> labels = classNode.getLabels();
        for (Label label : labels) {
            if(label.name().contains("org."))
                myClass.setCategory(label.name());
        }
        myClass.setAbstract((Boolean)classNode.getProperty(Constants.PROPERTY_ABSTRACT));
        myClass.setColor((Integer)classNode.getProperty(Constants.PROPERTY_COLOR));
        myClass.setCountable((Boolean)classNode.getProperty(Constants.PROPERTY_COUNTABLE));
        myClass.setInDesign((Boolean)classNode.getProperty(Constants.PROPERTY_IN_DESIGN));
        myClass.setCustom((Boolean)classNode.getProperty(Constants.PROPERTY_CUSTOM));
        myClass.setDescription((String)classNode.getProperty(Constants.PROPERTY_DESCRIPTION));
        myClass.setDisplayName((String)classNode.getProperty(Constants.PROPERTY_DISPLAY_NAME));
        myClass.setIcon((byte[])classNode.getProperty(Constants.PROPERTY_ICON));
        myClass.setSmallIcon((byte[])classNode.getProperty(Constants.PROPERTY_SMALL_ICON));
        myClass.setId(classNode.getId());
        myClass.setListType(isSubclassOf(Constants.CLASS_GENERICOBJECTLIST, classNode));
        //Is Viewable if is subclass of
        myClass.setViewable((Boolean)isSubclassOf(Constants.CLASS_VIEWABLEOBJECT, classNode));
        //Parent
        if (classNode.getSingleRelationship(RelTypes.EXTENDS, Direction.OUTGOING) != null){
            myClass.setParentClassName(
                    classNode.getSingleRelationship(
                        RelTypes.EXTENDS, Direction.OUTGOING).getEndNode().getProperty(
                            Constants.PROPERTY_NAME).toString());
        }
        else
            myClass.setParentClassName(null);
        
        for (Relationship rel : classNode.getRelationships(RelTypes.HAS_ATTRIBUTE))
            listAttributes.add(createAttributeMetadataFromNode(rel.getEndNode()));
        
        myClass.setAttributes(listAttributes);
                
        return myClass;
    }

    /**
     * Converts a attribute metadata node into a AttrributeMetadata object
     * @param attributeNode
     * @return the attribute as an instance of AttributeMetada class
     */
    public static AttributeMetadata createAttributeMetadataFromNode(Node attributeNode)
    {
        AttributeMetadata attribute =  new AttributeMetadata();

        attribute.setName((String)attributeNode.getProperty(Constants.PROPERTY_NAME));
        attribute.setDescription((String)attributeNode.getProperty(Constants.PROPERTY_DESCRIPTION));
        attribute.setDisplayName((String)attributeNode.getProperty(Constants.PROPERTY_DISPLAY_NAME));
        attribute.setReadOnly((Boolean)attributeNode.getProperty(Constants.PROPERTY_READ_ONLY));
        attribute.setType((String)attributeNode.getProperty(Constants.PROPERTY_TYPE));
        attribute.setVisible((Boolean)attributeNode.getProperty(Constants.PROPERTY_VISIBLE));
        attribute.setAdministrative((Boolean)attributeNode.getProperty(Constants.PROPERTY_ADMINISTRATIVE));
        attribute.setNoCopy(attributeNode.hasProperty(Constants.PROPERTY_NO_COPY) ? (Boolean)attributeNode.getProperty(Constants.PROPERTY_NO_COPY) : false);
        attribute.setMandatory(attributeNode.hasProperty(Constants.PROPERTY_MANDATORY) ? (Boolean)attributeNode.getProperty(Constants.PROPERTY_MANDATORY) : false );
        attribute.setUnique((Boolean)attributeNode.getProperty(Constants.PROPERTY_UNIQUE));
        attribute.setId(attributeNode.getId());
        attribute.setOrder(attributeNode.hasProperty(Constants.PROPERTY_ORDER) ? (int)attributeNode.getProperty(Constants.PROPERTY_ORDER) : 1000);
        attribute.setMultiple(attributeNode.hasProperty(Constants.PROPERTY_MULTIPLE) ? (boolean)attributeNode.getProperty(Constants.PROPERTY_MULTIPLE) : false);

        return attribute;
    }
    
    public static RemotePool createRemotePoolFromNode(Node instance) throws InvalidArgumentException {
        String instanceUuid = instance.hasProperty(Constants.PROPERTY_UUID) ? (String) instance.getProperty(Constants.PROPERTY_UUID) : null;
        if (instanceUuid == null)
            throw new InvalidArgumentException(String.format("The pool with id %s does not have uuid", instance.getId()));
                                
        return new RemotePool(instanceUuid, 
                (String)instance.getProperty(Constants.PROPERTY_NAME), 
                (String)instance.getProperty(Constants.PROPERTY_DESCRIPTION),
                (String)instance.getProperty(Constants.PROPERTY_CLASS_NAME), 
                (Integer)instance.getProperty(Constants.PROPERTY_TYPE));
    }
    
    public static BusinessObjectLight createRemoteObjectLightFromPoolNode (Node instance) {
        
        return new BusinessObjectLight(
            String.format("Pool of %s", instance.getProperty(Constants.PROPERTY_CLASS_NAME)), 
            instance.hasProperty(Constants.PROPERTY_UUID) ? (String) instance.getProperty(Constants.PROPERTY_UUID) : null, 
            (String)instance.getProperty(Constants.PROPERTY_NAME));
    }
    
    /**
     * @param poolNode
     * @return 
     * @throws InvalidArgumentException
     */
    public static Pool createPoolFromNode(Node poolNode) throws InvalidArgumentException {
        if (!poolNode.hasProperty(Constants.PROPERTY_UUID))
            throw new InvalidArgumentException(String.format("The pool with id %s does not have uuid", poolNode.getId()));
            
        return new Pool((String) poolNode.getProperty(Constants.PROPERTY_UUID), 
                        poolNode.hasProperty(Constants.PROPERTY_NAME) ? (String)poolNode.getProperty(Constants.PROPERTY_NAME) : null, 
                        poolNode.hasProperty(Constants.PROPERTY_DESCRIPTION) ? (String)poolNode.getProperty(Constants.PROPERTY_DESCRIPTION) : null,
                        (String)poolNode.getProperty(Constants.PROPERTY_CLASS_NAME), 
                        poolNode.hasProperty(Constants.PROPERTY_TYPE) ? (int)poolNode.getProperty(Constants.PROPERTY_TYPE) : -1);
    }
    
    public static Task createTaskFromNode(Node taskNode) {
        Iterable<String> allProperties = taskNode.getPropertyKeys();
            
        List<StringPair> parameters = new ArrayList<>();

        for (String property : allProperties) {
            if (property.startsWith("PARAM_"))
                parameters.add(new StringPair(property.replace("PARAM_", ""), (String)taskNode.getProperty(property)));
        }

        
        TaskScheduleDescriptor schedule = new TaskScheduleDescriptor(taskNode.hasProperty(Constants.PROPERTY_START_TIME) ? (long)taskNode.getProperty(Constants.PROPERTY_START_TIME) : 0,
                                                taskNode.hasProperty(Constants.PROPERTY_EVERY_X_MINUTES) ? (int)taskNode.getProperty(Constants.PROPERTY_EVERY_X_MINUTES) : 0, 
                                                taskNode.hasProperty(Constants.PROPERTY_EXECUTION_TYPE) ? (int)taskNode.getProperty(Constants.PROPERTY_EXECUTION_TYPE) : 0);

        TaskNotificationDescriptor notificationType = new TaskNotificationDescriptor(taskNode.hasProperty(Constants.PROPERTY_EMAIL) ? (String)taskNode.getProperty(Constants.PROPERTY_EMAIL) : "", 
                                                                            taskNode.hasProperty(Constants.PROPERTY_NOTIFICATION_TYPE) ? (int)taskNode.getProperty(Constants.PROPERTY_NOTIFICATION_TYPE) : 0);
        
        List<UserProfileLight> subscribedUsers = new ArrayList<>();
        
        for (Relationship rel : taskNode.getRelationships(Direction.INCOMING, RelTypes.SUBSCRIBED_TO))
            subscribedUsers.add(createUserProfileLightFromNode(rel.getStartNode()));
        
        return new Task(taskNode.getId(),
                                (String)taskNode.getProperty(Constants.PROPERTY_NAME), 
                                (String)taskNode.getProperty(Constants.PROPERTY_DESCRIPTION), 
                                (boolean)taskNode.getProperty(Constants.PROPERTY_ENABLED),
                                taskNode.hasProperty(Constants.PROPERTY_COMMIT_ON_EXECUTE) ? (boolean)taskNode.getProperty(Constants.PROPERTY_COMMIT_ON_EXECUTE) : false,
                                taskNode.hasProperty(Constants.PROPERTY_SCRIPT) ? (String)taskNode.getProperty(Constants.PROPERTY_SCRIPT) : null, 
                                parameters, schedule, notificationType, subscribedUsers);
        
    }
            
    public static ProcessInstance createProcessInstanceFromNode(Node processInstanceNode) {
        ProcessInstance processInstance = new ProcessInstance(processInstanceNode.getId(), 
            (String) processInstanceNode.getProperty(Constants.PROPERTY_NAME), 
            (String) processInstanceNode.getProperty(Constants.PROPERTY_DESCRIPTION), 
            (Long) processInstanceNode.getProperty(Constants.PROPERTY_CURRENT_ACTIVITY_ID), 
            (Long) processInstanceNode.getProperty(Constants.PROPERTY_PROCESS_DEFINITION_ID));

        if (processInstanceNode.hasProperty(Constants.PROPERTY_ARTIFACTS_CONTENT))
            processInstance.setArtifactsContent((byte[]) processInstanceNode.getProperty(Constants.PROPERTY_ARTIFACTS_CONTENT));
        
        return processInstance;
    }
    
    

    /**
     * Creates a UserProfileLight object (a user object without privileges) from a node
     * @param userNode The source user node
     * @return The UserProfileLight object built with the information of the source node
     */

    public static UserProfileLight createUserProfileLightFromNode(Node userNode){
       
       return   new UserProfileLight(userNode.getId(),
                (String)userNode.getProperty(UserProfile.PROPERTY_NAME),
                (String)userNode.getProperty(UserProfile.PROPERTY_FIRST_NAME),
                (String)userNode.getProperty(UserProfile.PROPERTY_LAST_NAME),
                (boolean)userNode.getProperty(UserProfile.PROPERTY_ENABLED),
                (long)userNode.getProperty(UserProfile.PROPERTY_CREATION_DATE),
                userNode.hasProperty(UserProfile.PROPERTY_TYPE) ?  //To keep backward compatibility
                        (int)userNode.getProperty(UserProfile.PROPERTY_TYPE) : UserProfile.USER_TYPE_GUI,
                userNode.hasProperty(UserProfile.PROPERTY_EMAIL) ? //To keep backward compatibility
                    (String) userNode.getProperty(UserProfile.PROPERTY_EMAIL) : null);
    }
    
    /**
     * Converts a node representing a user into a UserProfile object. The privileges inherited from the 
     * group <b>will not</b> be taken into account. See also <code>createUserProfileFromNode</code>.
     * @param userNode The source user node
     * @return UserProfile The UserProfile object built with the information of the source node
     */

    public static UserProfile createUserProfileWithoutGroupPrivilegesFromNode(Node userNode){
       List<Privilege> privileges = new ArrayList<>();

       for(Relationship relationship: userNode.getRelationships(RelTypes.HAS_PRIVILEGE, Direction.OUTGOING))
           privileges.add(createPrivilegeFromNode(relationship.getEndNode()));
       
       return   new UserProfile(userNode.getId(),
                (String)userNode.getProperty(UserProfile.PROPERTY_NAME),
                (String)userNode.getProperty(UserProfile.PROPERTY_FIRST_NAME),
                (String)userNode.getProperty(UserProfile.PROPERTY_LAST_NAME),
                (boolean)userNode.getProperty(UserProfile.PROPERTY_ENABLED),
                (long)userNode.getProperty(UserProfile.PROPERTY_CREATION_DATE),
                userNode.hasProperty(UserProfile.PROPERTY_TYPE) ?  //To keep backward compatibility
                        (int)userNode.getProperty(UserProfile.PROPERTY_TYPE) : UserProfile.USER_TYPE_GUI, 
                userNode.hasProperty(UserProfile.PROPERTY_EMAIL) ? //To keep backward compatibility
                    (String) userNode.getProperty(UserProfile.PROPERTY_EMAIL) : null,
                privileges);
    }
    
    /**
     * Converts a node representing a user into a UserProfile object. The privileges inherited from the 
     * group <b>will</b> be computed. Note that the user privileges override the group privileges with the same feature token. 
     * See also <code>createUserProfileWithoutGroupPrivilegesFromNode</code>.
     * @param userNode The source user node
     * @return UserProfile The UserProfile object built with the information of the source node
     */
    public static UserProfile createUserProfileWithGroupPrivilegesFromNode(Node userNode){
       List<Privilege> privileges = new ArrayList<>();
       
       for (Relationship relationship : userNode.getRelationships(RelTypes.BELONGS_TO_GROUP, Direction.OUTGOING)) {
           //group Privileges         
           Node groupNode = relationship.getEndNode();
           
           for(Relationship rel: groupNode.getRelationships(RelTypes.HAS_PRIVILEGE, Direction.OUTGOING))
                privileges.add(createPrivilegeFromNode(rel.getEndNode()));
       }

       for(Relationship relationship: userNode.getRelationships(RelTypes.HAS_PRIVILEGE, Direction.OUTGOING)) {
            Privilege userPrivilege = createPrivilegeFromNode(relationship.getEndNode());
            //If the privilege already exists, override it
            privileges.remove(userPrivilege); //Note that two privileges with the same feature token and different access level are equals.
            privileges.add(userPrivilege);
       }
       
       return   new UserProfile(userNode.getId(),
                (String)userNode.getProperty(UserProfile.PROPERTY_NAME),
                (String)userNode.getProperty(UserProfile.PROPERTY_FIRST_NAME),
                (String)userNode.getProperty(UserProfile.PROPERTY_LAST_NAME),
                (boolean)userNode.getProperty(UserProfile.PROPERTY_ENABLED),
                (long)userNode.getProperty(UserProfile.PROPERTY_CREATION_DATE),
                userNode.hasProperty(UserProfile.PROPERTY_TYPE) ?  //To keep backward compatibility
                        (int)userNode.getProperty(UserProfile.PROPERTY_TYPE) : UserProfile.USER_TYPE_GUI, 
                userNode.hasProperty(UserProfile.PROPERTY_EMAIL) ? //To keep backward compatibility
                    (String) userNode.getProperty(UserProfile.PROPERTY_EMAIL) : null, 
                privileges);
    }
    
    /**
     * Releases all the relationships associated to a user, and deletes the node corresponding to such user. The user "admin" can not be deleted. 
     * Historical entry associated to the user to be deleted are NOT deleted, so they can be edited later if necessary.
     * @param userNode The user node.
     * @throws InvalidArgumentException If you try to delete the default administrator
     */
    public static void deleteUserNode(Node userNode) throws InvalidArgumentException {
        if (userNode.hasProperty(UserProfile.PROPERTY_TYPE) && (int)userNode.getProperty(UserProfile.PROPERTY_TYPE) == UserProfile.USER_TYPE_SYSTEM)
            throw new InvalidArgumentException("System users can not be deleted or modified");
        
        String userName = (String)userNode.getProperty(Constants.PROPERTY_NAME);
        if (UserProfile.DEFAULT_ADMIN.equals(userName))
            throw new InvalidArgumentException("The default administrator can not be deleted");

        //Delete the privilege nodes
        for (Relationship hasPrivilegeRelationship : userNode.getRelationships(Direction.OUTGOING, RelTypes.HAS_PRIVILEGE)) { 
            Node privilegeNode = hasPrivilegeRelationship.getEndNode();
            hasPrivilegeRelationship.delete();
            privilegeNode.delete();
        }

        //Delete the rest of relationships. Audit trail entries are kept.
        for (Relationship relationship : userNode.getRelationships()) 
            relationship.delete();
                
        userNode.delete();
        CacheManager.getInstance().removeUser(userName);
    }

    /**
     * Converts a node representing a group into a GroupProfile object
     * @param groupNode The source node
     * @return A GroupProfile object built from the source node information
     */
    public static GroupProfile createGroupProfileFromNode(Node groupNode){
        
        List<UserProfile> users = new ArrayList<>();
        Iterable<Relationship> usersRelationships = groupNode.getRelationships(RelTypes.BELONGS_TO_GROUP, Direction.INCOMING);
        //Users
        for (Relationship relationship : usersRelationships) {
            Node userNode = relationship.getStartNode();
            //user Privileges
            List<Privilege> userPrivileges = new ArrayList<>();
            for(Relationship rel: userNode.getRelationships(RelTypes.HAS_PRIVILEGE, Direction.OUTGOING))
                userPrivileges.add(createPrivilegeFromNode(rel.getEndNode()));

            users.add(new UserProfile(userNode.getId(),
                        (String)userNode.getProperty(UserProfile.PROPERTY_NAME),
                        (String)userNode.getProperty(UserProfile.PROPERTY_FIRST_NAME),
                        (String)userNode.getProperty(UserProfile.PROPERTY_LAST_NAME),
                        (boolean)userNode.getProperty(UserProfile.PROPERTY_ENABLED),
                        (long)userNode.getProperty(UserProfile.PROPERTY_CREATION_DATE),
                        userNode.hasProperty(UserProfile.PROPERTY_TYPE) ? //To keep backward compatibility
                            (int)userNode.getProperty(UserProfile.PROPERTY_TYPE) :
                            UserProfile.USER_TYPE_GUI, 
                        userNode.hasProperty(UserProfile.PROPERTY_EMAIL) ? //To keep backward compatibility
                            (String) userNode.getProperty(UserProfile.PROPERTY_EMAIL) : null, 
                        userPrivileges));
        }
        
        List<Privilege> privileges = new ArrayList<>();
        for(Relationship relationship: groupNode.getRelationships(RelTypes.HAS_PRIVILEGE, Direction.OUTGOING))
           privileges.add(createPrivilegeFromNode(relationship.getEndNode()));
        
        
        GroupProfile group =  new GroupProfile(groupNode.getId(),
                (String)groupNode.getProperty(Constants.PROPERTY_NAME),
                groupNode.hasProperty(Constants.PROPERTY_DESCRIPTION) ? 
                        (String)groupNode.getProperty(Constants.PROPERTY_DESCRIPTION) : "",
                (Long)groupNode.getProperty(Constants.PROPERTY_CREATION_DATE),
                users, privileges);
        return group;
    }
    
    /**
     * Converts a node representing a group into a GroupProfileLight object
     * @param groupNode The source node
     * @return A GroupProfileLight object built from the source node information
     */
    public static GroupProfileLight createGroupProfileLightFromNode(Node groupNode){        
        return  new GroupProfileLight(groupNode.getId(),
                (String)groupNode.getProperty(Constants.PROPERTY_NAME),
                groupNode.hasProperty(Constants.PROPERTY_DESCRIPTION) ? 
                        (String)groupNode.getProperty(Constants.PROPERTY_DESCRIPTION) : "",
                (Long)groupNode.getProperty(Constants.PROPERTY_CREATION_DATE));
    }
    
    
    public static Privilege createPrivilegeFromNode(Node privilegeNode){
        return new Privilege((String)privilegeNode.getProperty(Privilege.PROPERTY_FEATURE_TOKEN), 
                (int)privilegeNode.getProperty(Privilege.PROPERTY_ACCESS_LEVEL));
    }
    
    /**
     * Creates a generic object list (a list type) from a node
     * @param listTypeNode the list type node
     * @return a list type
     */
    public static GenericObjectList createGenericObjectListFromNode(Node listTypeNode) throws InvalidArgumentException {
        String listTypeNodeUuid = listTypeNode.hasProperty(Constants.PROPERTY_UUID) ? (String) listTypeNode.getProperty(Constants.PROPERTY_UUID) : null;
        
        if (listTypeNodeUuid == null)
            throw new InvalidArgumentException(String.format("The list type item with id %s does not have uuid", listTypeNode.getId()));
            
        
        GenericObjectList listType = new GenericObjectList(listTypeNodeUuid, 
                (String)listTypeNode.getProperty(Constants.PROPERTY_NAME));
        return listType;
    }
    
    
    /**
     * Converts a node representing a Node into a SynchronizationGroup object
     * @param syncGroupNode The source node
     * @return A SynchronizationGroup object built from the source node information
     * @throws InvalidArgumentException if some element of the list of 
     * syncDataSourceConfiguration has more paramNames than paramValues
     * @throws org.kuwaiba.apis.persistence.exceptions.MetadataObjectNotFoundException
     * @throws org.kuwaiba.apis.persistence.exceptions.UnsupportedPropertyException
     */
    public static SynchronizationGroup createSyncGroupFromNode(Node syncGroupNode)  
            throws InvalidArgumentException, MetadataObjectNotFoundException, UnsupportedPropertyException {    
        
        if (!syncGroupNode.hasProperty(Constants.PROPERTY_NAME))
            throw new InvalidArgumentException(String.format("The sync group with id %s is malformed. Check its properties", syncGroupNode.getId()));

        List<SyncDataSourceConfiguration> syncDataSourceConfiguration = new ArrayList<>();

        for(Relationship rel : syncGroupNode.getRelationships(Direction.INCOMING, RelTypes.BELONGS_TO_GROUP))
            syncDataSourceConfiguration.add(createSyncDataSourceConfigFromNode(rel.getStartNode()));

        return  new SynchronizationGroup(syncGroupNode.getId(),
                (String)syncGroupNode.getProperty(Constants.PROPERTY_NAME),
                syncDataSourceConfiguration);
    }

    /**
     * Converts a node to a SyncDataSourceConfiguration object
     * @param syncDataSourceConfigNode The source node
     * @return A SyncDataSourceConfiguration object built from the source node information
     * @throws InvalidArgumentException if the size of the list of paramNames and paramValues are not the same 
     * @throws UnsupportedPropertyException if any property of the sync data source node is malformed or if there is an error with the relationship between the syncNode an it InventoryObjectNode
     */
    public static SyncDataSourceConfiguration createSyncDataSourceConfigFromNode(Node syncDataSourceConfigNode) throws UnsupportedPropertyException, InvalidArgumentException{   
        
        if (!syncDataSourceConfigNode.hasProperty(Constants.PROPERTY_NAME))
            throw new UnsupportedPropertyException(String.format("The sync configuration with id %s is malformed. Check its properties", syncDataSourceConfigNode.getId()));
        
        if(!syncDataSourceConfigNode.hasRelationship(RelTypes.HAS_CONFIGURATION))
            throw new UnsupportedPropertyException(String.format("The sync configuration with id %s is malformed. It is not related with a inventory object", syncDataSourceConfigNode.getId()));
        
        Node inventoryObjectNode = syncDataSourceConfigNode.getSingleRelationship(RelTypes.HAS_CONFIGURATION, Direction.INCOMING).getStartNode();

        HashMap<String, String> parameters = new HashMap<>();
        String configName = "";
      
        for (String property : syncDataSourceConfigNode.getPropertyKeys()) {
            if (property.equals(Constants.PROPERTY_NAME))
                configName = (String)syncDataSourceConfigNode.getProperty(property);
            if(property.equals("deviceId") && !((String)syncDataSourceConfigNode.getProperty(property)).equals(inventoryObjectNode.getProperty(Constants.PROPERTY_UUID)))
                throw new UnsupportedPropertyException(String.format("The sync configuration with id %s is malformed. its not related with correct inventory object", inventoryObjectNode.getId()));   
            else
                parameters.put(property, (String)syncDataSourceConfigNode.getProperty(property));
        }
            
        return  new SyncDataSourceConfiguration(syncDataSourceConfigNode.getId(), configName, parameters);
    }
    
    /**
     * Traverses the graph up into the class hierarchy trying to find out if a given class
     * is the subclass of another
     * @param allegedParentClass The alleged parent class name
     * @param currentNode
     * @return
     */
    public static boolean isSubclassOf(String allegedParentClass, Node currentNode){
        Iterable<Relationship> parent = currentNode.getRelationships(RelTypes.EXTENDS, Direction.OUTGOING);
        if (!parent.iterator().hasNext())
            return false;

        Node parentNode = parent.iterator().next().getEndNode();

        if (parentNode.getProperty(Constants.PROPERTY_NAME).equals(allegedParentClass))
            return true;

        return isSubclassOf(allegedParentClass, parentNode);
    }

   /**
     * Traverses the graph up into the class hierarchy trying to find out if a given class
     * is the possible child of another
     * @param allegedParentClass The alleged parent class name
     * @param currentNode
     * @return
     */
    public static boolean isPossibleChild(String allegedParentClass, Node currentNode){
        Iterable<Relationship> parents = currentNode.getRelationships(RelTypes.POSSIBLE_CHILD, Direction.INCOMING);
        for(Relationship parent : parents){
            Node parentNode = parent.getStartNode();

            if (parentNode.getProperty(Constants.PROPERTY_NAME).equals(allegedParentClass))
                return true;
        }
        return false;
    }
    
    /**
     * Retrieves the subclasses of a given class metadata node within the class hierarchy
     * @param classMetadata The parent class metadata
     * @return The root node of the list of class metadata nodes
     */

    public static Iterable<Node> getAllSubclasses(final Node classMetadata){
        TraversalDescription td = new MonoDirectionalTraversalDescription(); //TODO revisar esto!
        td = td.depthFirst();
        td = td.relationships(RelTypes.EXTENDS, Direction.INCOMING);
        org.neo4j.graphdb.traversal.Traverser traverse = td.traverse(classMetadata);
        return traverse.nodes();
    }

    /**
     * Retrieves a String with the property value of the attribute if exists as
     * attribute of the node, if the property is a date it is formating into
     * yyyy-MM-DD, if does not exists it return an empty string.
     * @param objectNode The object node
     * @param attribute The name of the attribute. This works only for primitive types
     * @return The string representation of the value of the given attribute
     */
    public static String getAttributeFromNode(Node objectNode, String attribute){
        if(objectNode.hasProperty(attribute)) { //It's a primitive type
            Object property = objectNode.getProperty(attribute);
            if(attribute.equals(Constants.PROPERTY_CREATION_DATE)){
                Date creationDate = new Date((Long)property);
                SimpleDateFormat formatoDeFecha = new SimpleDateFormat(Constants.DATE_FORMAT);//NOI18N
                    return formatoDeFecha.format(creationDate);
            }
            else
                return property.toString();
        }
        else {//It's a list type
            for (Relationship listTypeRelationship : objectNode.getRelationships(RelTypes.RELATED_TO, Direction.OUTGOING)) {
                if (listTypeRelationship.hasProperty(Constants.PROPERTY_NAME) && listTypeRelationship.getProperty(Constants.PROPERTY_NAME).equals(attribute))
                    return (String)listTypeRelationship.getEndNode().getProperty(Constants.PROPERTY_NAME);
            }
        }
        return ""; //The attribute does not exist or has been set to null
    }

    /**
     * Gets the type(String, Integer, Float, Boolean, etc) of an attribute
     * @param classNode
     * @param attributeName
     * @return A string with the type of the attribute
     */
    public static String getTypeOfAttribute(Node classNode, String attributeName) {
        //get attribute type
        Iterable<Relationship> attributeRels = classNode.getRelationships(RelTypes.HAS_ATTRIBUTE, Direction.OUTGOING);
        for (Relationship attrRel:  attributeRels) {
            Node endNode = attrRel.getEndNode();
            if(attributeName.equals((String)endNode.getProperty(Constants.PROPERTY_NAME)))
                return (String)endNode.getProperty(Constants.PROPERTY_TYPE);
        }
        return "";
    }
    
    /**
     * Gets the class name of a given object given its respective node
     * @param instance the node to be tested
     * @return The object class name. Null if none
     */
    public static String getClassName(Node instance){
        Iterable<Relationship> aClass = instance.getRelationships(RelTypes.INSTANCE_OF, Direction.OUTGOING);
        if (!aClass.iterator().hasNext())
            return null;
        return (String)aClass.iterator().next().getEndNode().getProperty(Constants.PROPERTY_NAME);
    }
       
    public static void createAttribute(Node classNode, AttributeMetadata attributeDefinition, boolean recursive) throws InvalidArgumentException {
        if (attributeDefinition.getName() == null || attributeDefinition.getName().isEmpty())
            throw new InvalidArgumentException("Attribute name can not be null or an empty string");
        
        if (!attributeDefinition.getName().matches("^[a-zA-Z0-9_-]*$"))
            throw new InvalidArgumentException(String.format("Attribute %s contains invalid characters", attributeDefinition.getName()));

        final TraversalDescription UPDATE_TRAVERSAL = classNode.getGraphDatabase().traversalDescription().
                    breadthFirst().
                    relationships(RelTypes.EXTENDS, Direction.INCOMING);

        for(Path p : UPDATE_TRAVERSAL.traverse(classNode)) {
            String currentClassName = (String) p.endNode().getProperty(Constants.PROPERTY_NAME);
            
            boolean hasAttribute = false;
            
            for(Relationship rel : p.endNode().getRelationships(RelTypes.HAS_ATTRIBUTE)){                
                if (rel.getEndNode().getProperty(Constants.PROPERTY_NAME).equals(attributeDefinition.getName())) {
                    if (recursive)
                        throw new InvalidArgumentException(String.format("Class %s already has an attribute named %s", 
                            currentClassName, attributeDefinition.getName()));
                    
                    hasAttribute = true;
                }
            }
            if (hasAttribute)
                continue;
            
            Label label = Label.label(Constants.LABEL_ATTRIBUTE);
            Node attrNode = classNode.getGraphDatabase().createNode(label);
            attrNode.setProperty(Constants.PROPERTY_NAME, attributeDefinition.getName()); //This should not be null. That should be checked in the caller
            attrNode.setProperty(Constants.PROPERTY_MANDATORY, attributeDefinition.isMandatory()== null ? false : attributeDefinition.isMandatory());
            attrNode.setProperty(Constants.PROPERTY_DESCRIPTION, attributeDefinition.getDescription() ==  null ? "" : attributeDefinition.getDescription());
            attrNode.setProperty(Constants.PROPERTY_DISPLAY_NAME, attributeDefinition.getDisplayName() == null ? "" : attributeDefinition.getDisplayName());
            attrNode.setProperty(Constants.PROPERTY_TYPE, attributeDefinition.getType() == null ? "String" : attributeDefinition.getType()); //NOI18N
            attrNode.setProperty(Constants.PROPERTY_READ_ONLY, attributeDefinition.isReadOnly() == null ? false : attributeDefinition.isReadOnly());
            attrNode.setProperty(Constants.PROPERTY_VISIBLE, attributeDefinition.isVisible() == null ? true : attributeDefinition.isVisible());
            attrNode.setProperty(Constants.PROPERTY_ADMINISTRATIVE, attributeDefinition.isAdministrative() == null ? false : attributeDefinition.isAdministrative());
            attrNode.setProperty(Constants.PROPERTY_CREATION_DATE, Calendar.getInstance().getTimeInMillis());
            attrNode.setProperty(Constants.PROPERTY_NO_COPY, attributeDefinition.isNoCopy() == null ? false : attributeDefinition.isNoCopy());
            attrNode.setProperty(Constants.PROPERTY_UNIQUE, attributeDefinition.isUnique() == null ? false : attributeDefinition.isUnique());
            attrNode.setProperty(Constants.PROPERTY_ORDER, attributeDefinition.getOrder() == null ? 1000 : attributeDefinition.getOrder());
            attrNode.setProperty(Constants.PROPERTY_MULTIPLE, attributeDefinition.isMultiple() == null ? false : attributeDefinition.isMultiple());
            
            p.endNode().createRelationshipTo(attrNode, RelTypes.HAS_ATTRIBUTE);
        }
    }
    
    /**
     * Transactions are not handled here
     * @param classNode
     * @param attributeName
     * @param newAttributeType 
     * @throws org.kuwaiba.apis.persistence.exceptions.InvalidArgumentException 
     */
    public static void changeAttributeTypeIfPrimitive (Node classNode, String attributeName, String newAttributeType) throws InvalidArgumentException {
        final TraversalDescription UPDATE_TRAVERSAL = classNode.getGraphDatabase().traversalDescription().
                    breadthFirst().
                    relationships(RelTypes.EXTENDS, Direction.INCOMING);

        for(Path p : UPDATE_TRAVERSAL.traverse(classNode)){
            for(Relationship rel : p.endNode().getRelationships(RelTypes.HAS_ATTRIBUTE)){
                if (rel.getEndNode().getProperty(Constants.PROPERTY_NAME).equals(attributeName)){
                    rel.getEndNode().setProperty(Constants.PROPERTY_TYPE, newAttributeType);
                    break;
                }
            }
            
            for(Relationship rel : p.endNode().getRelationships(RelTypes.INSTANCE_OF, Direction.INCOMING)){
                if(rel.getStartNode().hasProperty(attributeName)){
                    Object currentValue = rel.getStartNode().getProperty(attributeName);                   
                    Object newValue = Util.convertIfPossible(currentValue, newAttributeType);
                    if (newValue != null)
                        rel.getStartNode().setProperty(attributeName, newValue);
                    else
                        rel.getStartNode().removeProperty(attributeName);
                }
            }
        }//end for
    }
    
    public static void changeAttributeTypeIfListType (Node classNode, String attributeName, String newAttributeType) throws InvalidArgumentException {
        final TraversalDescription UPDATE_TRAVERSAL = classNode.getGraphDatabase().traversalDescription().
                    breadthFirst().
                    relationships(RelTypes.EXTENDS, Direction.INCOMING);

        for(Path p : UPDATE_TRAVERSAL.traverse(classNode)){
            for(Relationship rel : p.endNode().getRelationships(RelTypes.HAS_ATTRIBUTE)){
                if (rel.getEndNode().getProperty(Constants.PROPERTY_NAME).equals(attributeName)){
                    rel.getEndNode().setProperty(Constants.PROPERTY_TYPE, newAttributeType);
                    break;
                }
            }
            
            for(Relationship rel : p.endNode().getRelationships(RelTypes.INSTANCE_OF, Direction.INCOMING)){
                for(Relationship listTypeRel : rel.getStartNode().getRelationships(Direction.OUTGOING, RelTypes.RELATED_TO, RelTypes.RELATED_TO_SPECIAL)){
                    if (listTypeRel.getProperty(Constants.PROPERTY_NAME).equals(attributeName))
                        listTypeRel.delete();
                }
            }
        }//end for
    }
    
    public static void changeAttributeProperty (Node classNode, String attributeName, String propertyName, Object propertyValue) {        
        CacheManager cm = CacheManager.getInstance();
        
        final TraversalDescription UPDATE_TRAVERSAL = classNode.getGraphDatabase().traversalDescription().
                    breadthFirst().
                    relationships(RelTypes.EXTENDS, Direction.INCOMING);
        
        for(Path p : UPDATE_TRAVERSAL.traverse(classNode)){
            for(Relationship rel : p.endNode().getRelationships(RelTypes.HAS_ATTRIBUTE)) {
                if (rel.getEndNode().getProperty(Constants.PROPERTY_NAME).equals(attributeName)) {
                    rel.getEndNode().setProperty(propertyName, propertyValue);
                    if (propertyName.equals(Constants.PROPERTY_UNIQUE)) {
                        if ((boolean)propertyValue)
                            cm.putUniqueAttributeValuesIndex((String)rel.getStartNode().getProperty(Constants.PROPERTY_NAME), attributeName, new ArrayList<>());
                        else
                            cm.removeUniqueAttribute((String)rel.getStartNode().getProperty(Constants.PROPERTY_NAME), attributeName);
                    }
                    break;
                }
            }
        }//end for
    }
    
    /**
     * 
     * @param classNode
     * @param oldAttributeName
     * @param newAttributeName 
     */
    public static void changeAttributeName(Node classNode, String oldAttributeName, String newAttributeName) {
        final TraversalDescription UPDATE_TRAVERSAL = classNode.getGraphDatabase().traversalDescription().
                    breadthFirst().
                    relationships(RelTypes.EXTENDS, Direction.INCOMING);

        for(Path p : UPDATE_TRAVERSAL.traverse(classNode)){
            for(Relationship rel : p.endNode().getRelationships(RelTypes.HAS_ATTRIBUTE)) {
                if (rel.getEndNode().getProperty(Constants.PROPERTY_NAME).equals(oldAttributeName)){
                    rel.getEndNode().setProperty(Constants.PROPERTY_NAME, newAttributeName);
                    break;
                }
            }
            
            for(Relationship rel : p.endNode().getRelationships(RelTypes.INSTANCE_OF, Direction.INCOMING)){
                if(rel.getStartNode().hasProperty(oldAttributeName)){
                    Object currentValue = rel.getStartNode().getProperty(oldAttributeName);
                    rel.getStartNode().removeProperty(oldAttributeName);
                    rel.getStartNode().setProperty(newAttributeName, currentValue);
                }
            }           
        }//end for
    }
    
    public static void deleteAttributeIfPrimitive(Node classNode, String attributeName){
        final TraversalDescription TRAVERSAL = classNode.getGraphDatabase().traversalDescription().
                    breadthFirst().relationships(RelTypes.EXTENDS, Direction.INCOMING);
        
        for(Path p : TRAVERSAL.traverse(classNode)){
            for(Relationship rel : p.endNode().getRelationships(RelTypes.HAS_ATTRIBUTE)) {
                if (rel.getEndNode().getProperty(Constants.PROPERTY_NAME).equals(attributeName)){
                    rel.getEndNode().delete();
                    rel.delete();
                    break;
                }
            }
            
            for(Relationship rel : p.endNode().getRelationships(RelTypes.INSTANCE_OF, Direction.INCOMING)){
                if(rel.getStartNode().hasProperty(attributeName))
                    rel.getStartNode().removeProperty(attributeName);
            }           
        }//end for
    }
    
    public static void deleteAttributeIfListType(Node classNode, String attributeName){
        final TraversalDescription TRAVERSAL = classNode.getGraphDatabase().traversalDescription().
                    breadthFirst().relationships(RelTypes.EXTENDS, Direction.INCOMING);
        
        for(Path p : TRAVERSAL.traverse(classNode)){
            for(Relationship rel : p.endNode().getRelationships(RelTypes.HAS_ATTRIBUTE)) {
                if (rel.getEndNode().getProperty(Constants.PROPERTY_NAME).equals(attributeName)){
                    rel.getEndNode().delete();
                    rel.delete();
                    break;
                }
            }
            
            for(Relationship rel : p.endNode().getRelationships(RelTypes.INSTANCE_OF, Direction.INCOMING)){
                for (Relationship relatedElement : rel.getStartNode().getRelationships(Direction.OUTGOING, RelTypes.RELATED_TO, RelTypes.RELATED_TO_SPECIAL)){
                    if(relatedElement.getProperty(Constants.PROPERTY_NAME).equals(attributeName))
                        relatedElement.delete();
                }
            }
        }//end for
    }
    
    /**
     * Creates a new log entry upon an action performed by an user. Transactions are not managed here
     * @param object The object that was affected by the action. Provide the db's root node if it's a general activity log entry (that is, it's not related to any specific object)
     * @param logRoot
     * @param userName User that performed the action
     * @param type
     * @param timestamp
     * @param notes
     * @param oldValue
     * @param newValue
     * @param affectedProperty
     * @return 
     * @throws ApplicationObjectNotFoundException If the user or the root of all log entries can't be found
     */
    public static Node createActivityLogEntry(Node object, Node logRoot, String userName, 
            int type, long timestamp, String affectedProperty, String oldValue, String newValue, String notes) 
            throws ApplicationObjectNotFoundException
    {
        Node userNode = logRoot.getGraphDatabase().findNode(Label.label(Constants.LABEL_USER), Constants.PROPERTY_NAME, userName);
        
        if (userNode == null)
            throw new ApplicationObjectNotFoundException(String.format("User %s can not be found", userName));
        
        Node newEntry = logRoot.getGraphDatabase().createNode();
        
        newEntry.setProperty(Constants.PROPERTY_TYPE, type);
        newEntry.setProperty(Constants.PROPERTY_CREATION_DATE, timestamp);
        if (affectedProperty != null)
            newEntry.setProperty(Constants.PROPERTY_AFFECTED_PROPERTY, affectedProperty);
        if (oldValue != null)
            newEntry.setProperty(Constants.PROPERTY_OLD_VALUE, oldValue);
        if (newValue != null)
            newEntry.setProperty(Constants.PROPERTY_NEW_VALUE, newValue);
        if (notes != null)
            newEntry.setProperty(Constants.PROPERTY_NOTES, notes);
        
        newEntry.createRelationshipTo(logRoot, RelTypes.CHILD_OF_SPECIAL);
        newEntry.createRelationshipTo(userNode, RelTypes.PERFORMED_BY);
        if (object != null)
            object.createRelationshipTo(newEntry, RelTypes.HAS_HISTORY_ENTRY);
        return newEntry;
    }
    
    /**
     * Tries to convert an attribute value to a new attribute type. It only works with primitive types String, Integer, Float, Boolean, Long, Date and Timestamp
     * @param oldValue The old value
     * @param convertTo The type we want to convert the old value to
     * @return The converted value
     * @throws InvalidArgumentException If it's not possible to perform the conversion
     */
    public static Object convertIfPossible(Object oldValue, String convertTo) throws InvalidArgumentException {
        if (oldValue == null)
            return null;
        
        String easierToHandleOldValue = oldValue.toString();
        if (convertTo.equals("String"))
            return easierToHandleOldValue;
        try {
            if (convertTo.equals("Integer"))
                return Integer.valueOf(easierToHandleOldValue);
            if (convertTo.equals("Float"))
                return Float.valueOf(easierToHandleOldValue);
            if (convertTo.equals("Boolean"))
                return Boolean.valueOf(easierToHandleOldValue);
            if (convertTo.equals("Long") || convertTo.equals("Date") || convertTo.equals("Timestamp"))
                return Integer.valueOf(easierToHandleOldValue);
        }catch (NumberFormatException ex){} //Does nothing
        
        return null;
    }
    
    /**
     * Outputs as a string a list of inventory objects (usually a list of parents in the containment hierarchy)
     * @param objectList The list of objects
     * @param startFromTheLast The output string should start from the first or the last object?
     * @param howManyToShow How many elements should be displayed? used -1 to show all
     * @return A string with the names of the objects concatenated with a "/" as separator
     */
    public static String formatObjectList(List<BusinessObjectLight> objectList, boolean startFromTheLast, int howManyToShow) {
        if (startFromTheLast)
            Collections.reverse(objectList);
        
        String outputString = "";
        int i;
        
        for (i = 0;  i <  ((howManyToShow == -1 || howManyToShow >= objectList.size()) ? objectList.size() - 1 : howManyToShow - 1); i++) {
            if (!objectList.get(i).getName().equals(Constants.NODE_DUMMYROOT)) 
                outputString += objectList.get(i) + " / "; //NOI18N
            
        }
        
        if (!objectList.get(i).getName().equals(Constants.NODE_DUMMYROOT))
            outputString += objectList.get(i);
        
        return outputString;
    }
    
    /**
     * Finds a node tagged with a label and with a particular id
     * @param label The label used to tag the node
     * @param id The id of the node to find
     * @return The node or null if no node with with that label and id could be found
     */
    public static Node findNodeByLabelAndId(Label label, long id) {
        GraphDatabaseService graphDb = (GraphDatabaseService) PersistenceService.getInstance().getConnectionManager().getConnectionHandler();
        
        try (Transaction tx = graphDb.beginTx()) {
            
            String cypherQuery = "MATCH (node:" + label.name() + ") " +
                                 "WHERE id(node) = " + id + " " +
                                 "RETURN node";
            
            Result result = graphDb.execute(cypherQuery);
            ResourceIterator<Node> node = result.columnAs("node");
            
            tx.success();
            return node.hasNext() ? node.next() : null;
        }
    }
}
