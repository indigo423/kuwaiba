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

package org.kuwaiba.apis.persistence.application;

import com.neotropic.kuwaiba.modules.GenericCommercialModule;
import com.neotropic.kuwaiba.sync.model.SyncDataSourceConfiguration;
import com.neotropic.kuwaiba.sync.model.SynchronizationGroup;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import org.kuwaiba.apis.persistence.application.process.ActivityDefinition;
import org.kuwaiba.apis.persistence.application.process.Artifact;
import org.kuwaiba.apis.persistence.application.process.ArtifactDefinition;
import org.kuwaiba.apis.persistence.application.process.ProcessDefinition;
import org.kuwaiba.apis.persistence.application.process.ProcessInstance;
import org.kuwaiba.apis.persistence.business.BusinessObjectLight;
import org.kuwaiba.apis.persistence.business.BusinessObjectList;
import org.kuwaiba.apis.persistence.exceptions.ApplicationObjectNotFoundException;
import org.kuwaiba.apis.persistence.exceptions.ArraySizeMismatchException;
import org.kuwaiba.apis.persistence.exceptions.BusinessRuleException;
import org.kuwaiba.apis.persistence.exceptions.InvalidArgumentException;
import org.kuwaiba.apis.persistence.exceptions.MetadataObjectNotFoundException;
import org.kuwaiba.apis.persistence.exceptions.NotAuthorizedException;
import org.kuwaiba.apis.persistence.exceptions.BusinessObjectNotFoundException;
import org.kuwaiba.apis.persistence.exceptions.NoCommercialModuleFoundException;
import org.kuwaiba.apis.persistence.exceptions.OperationNotPermittedException;
import org.kuwaiba.apis.persistence.exceptions.UnsupportedPropertyException;
import org.kuwaiba.apis.persistence.metadata.ClassMetadataLight;
import org.kuwaiba.util.ChangeDescriptor;
import org.kuwaiba.apis.persistence.util.StringPair;
import org.kuwaiba.interfaces.ws.toserialize.application.TaskNotificationDescriptor;
import org.kuwaiba.interfaces.ws.toserialize.application.TaskScheduleDescriptor;
import org.kuwaiba.interfaces.ws.toserialize.application.UserInfoLight;

/**
 * This is the entity in charge of manipulating application objects such as users, views, etc
 * @author Charles Edward Bedon Cortazar {@literal <charles.bedon@kuwaiba.org>}
 */
public interface ApplicationEntityManager {
    /**
     * Type of pool general purpose. 
     * These pools are not linked to any particular model
     */
    public static final int POOL_TYPE_GENERAL_PURPOSE = 1;
    /**
     * Type of pool module root. 
     * These pools are used in models and are the root of such model
     */
    public static final int POOL_TYPE_MODULE_ROOT = 2;
    /**
     * Type of pool module component. 
     * These pools are used in models and are in the lower levels of the pool 
     * containment hierarchy.
     */
    public static int POOL_TYPE_MODULE_COMPONENT = 3;
    /**
     * String that identifies the class used for pools
     */
    public static final String CLASS_POOL = "Pool";
    
   
    /**
     * Gets current sessions
     * @return A dictionary whose keys are the user names and the keys are the session related objects
     */
    public HashMap<String, Session> getSessions();
    
    /**
     * Creates a user
     * @param userName New user's name. Mandatory.
     * @param password New user's password
     * @param firstName New user's first name
     * @param lastName New user's last name
     * @param enabled Shall the new user be enabled by default
     * @param type User type. See UserProfileLight.USER_TYPE_* for possible values
     * @param privileges New user's privileges. Use null for none
     * @param defaultGroupId Default group this user will be associated to
     * @return The id of the newly created user
     * @throws InvalidArgumentException Thrown if the username is null or empty or the username already exists
     */
    public long createUser(String userName, String password, String firstName,
            String lastName, boolean enabled, int type, List<Privilege> privileges, long defaultGroupId)
            throws InvalidArgumentException;
    
    /**
     * Set the properties of a given user using the id to search for it
     * @param oid User id
     * @param userName New user's name. User null to leave it unchanged.
     * @param password New user's password. Use null to leave it unchanged
     * @param firstName New user's first name. Use null to leave it unchanged
     * @param lastName New user's last name. Use null to leave it unchanged
     * @param enabled 0 for false, 1 for true, -1 to leave it unchanged
     * @param type User type. See UserProfile.USER_TYPE* for possible values. Use -1 to leave it unchanged
     * @throws InvalidArgumentException Thrown if the username is null or empty or the username already exists
     * @throws ApplicationObjectNotFoundException If the user could not be found
     */
    public void setUserProperties(long oid, String userName, String password, String firstName,
            String lastName, int enabled, int type)
            throws InvalidArgumentException, ApplicationObjectNotFoundException;
    /**
     * Updates the attributes of a user, using its username as key to find it
     * @param formerUsername Current user name
     * @param newUserName New name. Null if unchanged
     * @param password Password. Null if unchanged
     * @param firstName User's first name. Null if unchanged
     * @param lastName User's last name. Null if unchanged
     * @param enabled 0 for false, 1 for true, -1 to leave it unchanged
     * @param type User type. See UserProfile.USER_TYPE* for possible values. -1 to leave it unchanged
     * @throws InvalidArgumentException If the format of any of the parameters provided is erroneous
     * @throws ApplicationObjectNotFoundException If the user could not be found
     */
    public void setUserProperties(String formerUsername, String newUserName, String password, String firstName,
            String lastName, int enabled, int type)
            throws InvalidArgumentException, ApplicationObjectNotFoundException;
    
    /**
     * Adds a user to a group
     * @param userId The id of the user to be added to the group
     * @param groupId Id of the group which the user will be added to
     * @throws InvalidArgumentException If the user is already related to that group.
     * @throws ApplicationObjectNotFoundException If the user or group can not be found
     */
    public void addUserToGroup(long userId, long groupId) throws InvalidArgumentException, ApplicationObjectNotFoundException;
    /**
     * Removes a user from a group
     * @param userId The id of the user to be added to the group
     * @param groupId Id of the group which the user will be added to
     * @throws InvalidArgumentException If the user is not related to that group
     * @throws ApplicationObjectNotFoundException If the user or the group could not be found
     */
    public void removeUserFromGroup(long userId, long groupId) throws InvalidArgumentException, ApplicationObjectNotFoundException;
    /**
     * Sets a privilege to a user. If the feature token provided already has been assigned to the user, the access level will be changed, otherwise, a privilege will be created
     * @param userId The user Id
     * @param featureToken The feature token. See class Privilege for details. Note that this token must match to the one expected by the client application. That's the only way the correct features will be enabled.
     * @param accessLevel The feature token. See class Privilege.ACCESS_LEVEL* for details. 
     * @throws InvalidArgumentException If the access level is invalid or if the featureToken has a wrong format
     * @throws ApplicationObjectNotFoundException If the user could not be found.
     */
    public void setPrivilegeToUser(long userId, String featureToken, int accessLevel) throws InvalidArgumentException, ApplicationObjectNotFoundException;
    /**
     * Sets a privilege to a group. If the feature token provided already has been assigned to the group, the access level will be changed, otherwise, a privilege will be created
     * @param groupId The group Id
     * @param featureToken The feature token. See class Privilege for details. Note that this token must match to the one expected by the client application. That's the only way the correct features will be enabled.
     * @param accessLevel The feature token. See class Privilege.ACCESS_LEVEL* for details. 
     * @throws InvalidArgumentException If the access level is invalid or if the featureToken has a wrong format
     * @throws ApplicationObjectNotFoundException If the group could not be found
     */
    public void setPrivilegeToGroup(long groupId, String featureToken, int accessLevel) throws InvalidArgumentException, ApplicationObjectNotFoundException;
    
    /**
     * Removes a privilege from a user
     * @param userId Id of the user
     * @param featureToken The feature token. See class Privilege for details. 
     * @throws InvalidArgumentException If the feature token is not related to the user
     * @throws ApplicationObjectNotFoundException If the user could not be found
     */
    public void removePrivilegeFromUser(long userId, String featureToken) throws InvalidArgumentException, ApplicationObjectNotFoundException;
    /**
     * Removes a privilege from a user
     * @param groupId Id of the group
     * @param featureToken The feature token. See class Privilege for details. 
     * @throws InvalidArgumentException If the feature token is not related to the group
     * @throws ApplicationObjectNotFoundException If the group could not be found
     */
    public void removePrivilegeFromGroup(long groupId, String featureToken) throws InvalidArgumentException, ApplicationObjectNotFoundException;
    /**
     * Creates a group
     * @param groupName The group name
     * @param description The group description
     * @param users users who belong the group
     * @return The new group id.
     * @throws InvalidArgumentException if there's already a group with that name
     * @throws ApplicationObjectNotFoundException If any of the provided users could not be found
     */
     public long createGroup(String groupName, String description, List<Long> users)
            throws InvalidArgumentException, ApplicationObjectNotFoundException;

    /**
     * Retrieves the list of all users
     * @return The list of users
     */
    public List<UserProfile> getUsers() ;

    /**
     * Retrieves the list of all groups
     * @return An array of GroupProfile
     */
    public List<GroupProfile> getGroups();

    /**
     * Set the attributes of a group
     * @param oid The oid of the group.
     * @param groupName The name of the group. Use null to leave the old value.
     * @param description The description of the group. Use null to leave the old value.
     * @throws InvalidArgumentException If any of the privileges ids is invalid.
     * @throws ApplicationObjectNotFoundException If the group could not be found.
     */
    public void setGroupProperties(long oid, String groupName, String description)
            throws InvalidArgumentException, ApplicationObjectNotFoundException;

   /**
     * Removes a list of users
     * @param oids The ids of the users to be deleted.
     * @throws ApplicationObjectNotFoundException If any of the users could not be found.
     * @throws InvalidArgumentException If any of the users is the default administrator, which can't be deleted
     */
    public void deleteUsers(long[] oids)
            throws ApplicationObjectNotFoundException, InvalidArgumentException;

    /**
     * Removes a list of groups
     * @param oids The oid of the groups to delete.
     * @throws ApplicationObjectNotFoundException If any of the groups could not be found.
     * @throws InvalidArgumentException If the group you are trying to delete contains the default administrator
     */
    public void deleteGroups(long[] oids) throws ApplicationObjectNotFoundException, InvalidArgumentException;
    
   /**
     * Creates a list type item
     * @param className List type
     * @param name new item's name
     * @param displayName new item's display name
     * @return new item's id
     * @throws MetadataObjectNotFoundException if className is not an existing class
     * @throws InvalidArgumentException if the class provided is not a list type
     * @throws OperationNotPermittedException If the class is abstract or marked as in design.
     */
    public String createListTypeItem(String className, String name, String displayName)
            throws MetadataObjectNotFoundException, InvalidArgumentException, OperationNotPermittedException;

    /**
     * Retrieves all the items related to a given list type
     * @param className list type
     * @return A list of RemoteBusinessObjectLight instances representing the items
     * @throws MetadataObjectNotFoundException if className is not an existing class
     * @throws InvalidArgumentException if the class provided is not a list type
     */
    public List<BusinessObjectLight> getListTypeItems(String className)
            throws MetadataObjectNotFoundException, InvalidArgumentException;
    
    /**
     * Retrieves list type item given its id
     * @param listTypeClassName The class name of list type item
     * @param listTypeItemId The id of list type item
     * @return A RemoteBusinessObjectLight instance representing the item
     * @throws MetadataObjectNotFoundException If the list type class Name is not an existing class
     * @throws InvalidArgumentException if the list type class name provided is not a list type
     * @throws ApplicationObjectNotFoundException If the the item id can not be found
     */
    public BusinessObjectLight getListTypeItem(String listTypeClassName, String listTypeItemId) throws 
        MetadataObjectNotFoundException, InvalidArgumentException, ApplicationObjectNotFoundException;
    
    /**
     * Retrieves a list type item given its name
     * @param listTypeClassName The class name of list type item
     * @param listTypeItemName The name of list type item
     * @return A RemoteBusinessObjectLight instance representing the item
     * @throws MetadataObjectNotFoundException If the list type class Name is not an existing class
     * @throws InvalidArgumentException if the list type class name provided is not a list type
     * @throws ApplicationObjectNotFoundException If the the item id can not be found
     */
    public BusinessObjectLight getListTypeItemWithName(String listTypeClassName, String listTypeItemName) throws 
        MetadataObjectNotFoundException, InvalidArgumentException, ApplicationObjectNotFoundException;
    
    /**
     * Deletes a list type item
     * @param className List type item class
     * @param oid list type item oid
     * @param realeaseRelationships Should the relationships be released
     * @throws MetadataObjectNotFoundException if the class name is not valid
     * @throws BusinessObjectNotFoundException if the list type item can't be found
     * @throws OperationNotPermittedException if the object has relationships
     * @throws org.kuwaiba.apis.persistence.exceptions.InvalidArgumentException If the class provided is not a list type
     * @throws org.kuwaiba.apis.persistence.exceptions.NotAuthorizedException If the user can't delete a list type item
     */
    public void deleteListTypeItem(String className, String oid, boolean realeaseRelationships)
            throws MetadataObjectNotFoundException, BusinessObjectNotFoundException, OperationNotPermittedException, InvalidArgumentException, NotAuthorizedException;

    /**
     * Get the possible list types
     * @return A list of ClassMetadataLight instances representing the possible list types
     * @throws ApplicationObjectNotFoundException if the GenericObjectList class does not exist
     */
    public List<ClassMetadataLight> getInstanceableListTypes()
            throws ApplicationObjectNotFoundException;
    
    /**
     * Creates a view for a given list type item. If there's already a view of the provided view type, it will be overwritten
     * @param listTypeItemId list type item id
     * @param listTypeItemClassName list type item class name
     * @param name view name
     * @param description view description
     * @param viewClassName view class name
     * @param structure XML document with the view structure
     * @param background background image
     * @return The id of the new view.
     * @throws MetadataObjectNotFoundException if the list type item class can not be found
     * @throws InvalidArgumentException if the view type is not supported
     */
    public long createListTypeItemRelatedView(String listTypeItemId, String listTypeItemClassName, String viewClassName, String name, String description, byte [] structure, byte [] background) 
        throws MetadataObjectNotFoundException, InvalidArgumentException;
    
    /**
     * Gets a view related to a list type item, such as the default, rack or equipment views
     * @param listTypeItemId list type item id
     * @param listTypeItemClass list type item class
     * @param viewId view id
     * @return The associated view (there should be only one of each type). Null if there's none yet
     * @throws ApplicationObjectNotFoundException if the list type item or the view can not be found
     * @throws MetadataObjectNotFoundException if the corresponding class metadata can not be found
     * @throws InvalidArgumentException if the provided view type is not supported
     */    
    public ViewObject getListTypeItemRelatedView(String listTypeItemId, String listTypeItemClass, long viewId) 
        throws MetadataObjectNotFoundException, InvalidArgumentException, ApplicationObjectNotFoundException;
    
    /**
     * Updates a view for a given list type item. If there's already a view of the provided view type, it will be overwritten
     * @param listTypeItemId list type item id
     * @param listTypeItemClass list type item class
     * @param viewId viewId
     * @param name view name
     * @param description view description
     * @param structure XML document with the view structure
     * @param background Background image. If null, the previous will be removed, if 0-sized array, it will remain unchanged
     * @return The summary of the changes
     * @throws BusinessObjectNotFoundException if the list type item can not be found
     * @throws MetadataObjectNotFoundException if the list type item class can not be found
     * @throws InvalidArgumentException if the view type is not supported
     * @throws ApplicationObjectNotFoundException If the view can not be found
     */
    public ChangeDescriptor updateListTypeItemRelatedView(String listTypeItemId, String listTypeItemClass, long viewId, 
        String name, String description, byte[] structure, byte[] background) 
        throws MetadataObjectNotFoundException, InvalidArgumentException, BusinessObjectNotFoundException, ApplicationObjectNotFoundException;
    
    /**
     * Gets the views related to a list type item, such as the default, rack or equipment views
     * @param listTypeItemId list type item id
     * @param listTypeItemClass list type class name
     * @param limit max number of results
     * @return The associated views
     * @throws MetadataObjectNotFoundException if the corresponding class metadata can not be found
     * @throws InvalidArgumentException if the provided view type is not supported
     */
    public List<ViewObjectLight> getListTypeItemRelatedViews(String listTypeItemId, String listTypeItemClass, int limit) 
        throws MetadataObjectNotFoundException, InvalidArgumentException;
    
    /**
     * Deletes a list type item related view
     * @param listTypeItemId list type item id
     * @param listTypeItemClass list type class name
     * @param viewId related view id
     * @throws MetadataObjectNotFoundException if the list type item class can not be found
     * @throws InvalidArgumentException if the list type item can no be found using the id
     * @throws ApplicationObjectNotFoundException if the view can not be found
     */
    public void deleteListTypeItemRelatedView(String listTypeItemId, String listTypeItemClass, long viewId) 
        throws MetadataObjectNotFoundException, InvalidArgumentException, ApplicationObjectNotFoundException;
    
    /**
     * Retrieves the objects that make reference to a given list type item
     * @param listTypeItemClass The list type class
     * @param listTypeItemId The list type item id
     * @param limit The limit of results. Use -1 to retrieve all.
     * @return The list of business objects related to the list type item
     * @throws ApplicationObjectNotFoundException 
     * @throws InvalidArgumentException 
     */
    public List<BusinessObjectLight> getListTypeItemUses(String listTypeItemClass, String listTypeItemId, int limit) 
        throws ApplicationObjectNotFoundException, InvalidArgumentException;
    
    /**
     * Gets the list of template elements with a device layout
     * @return the list of template elements with a device layout
     * @throws InvalidArgumentException If any template does not have uuid
     */
    public List<BusinessObjectLight> getDeviceLayouts() throws InvalidArgumentException;
    
    /**
     * Gets the device layout structure as an XML file
     * @param oid object id
     * @param className object class
     * @return The structure of the device layout
     * @throws org.kuwaiba.apis.persistence.exceptions.ApplicationObjectNotFoundException In case that any of the devices contained within the main one
     * has a malformed <b>model</b> attribute.
     * @throws org.kuwaiba.apis.persistence.exceptions.InvalidArgumentException If the structure is somehow malformed.
     */
    public byte[] getDeviceLayoutStructure(String oid, String className) throws ApplicationObjectNotFoundException, InvalidArgumentException;
        
    /**
     * Get a view related to an object, such as the default rack or object views
     * @param oid object's id
     * @param objectClass object's class
     * @param viewId view id
     * @return The associated view (there should be only one of each type). Null if there's none yet
     * @throws ApplicationObjectNotFoundException if the object or the view can not be found
     * @throws MetadataObjectNotFoundException if the corresponding class metadata can not be found
     * @throws InvalidArgumentException if the provided view type is not supported
     * @throws BusinessObjectNotFoundException If the object can not be found
     */
    public ViewObject getObjectRelatedView(String oid, String objectClass, long viewId)
            throws ApplicationObjectNotFoundException,  BusinessObjectNotFoundException, MetadataObjectNotFoundException, InvalidArgumentException;

    /**
     * Get a view related to an object, such as the default, rack or equipment views
     * @param oid object's id
     * @param objectClass object's class
     * @param limit max number of results
     * @return The associated views
     * @throws BusinessObjectNotFoundException if the object can not be found
     * @throws MetadataObjectNotFoundException if the corresponding class metadata can not be found
     * @throws InvalidArgumentException if the provided view type is not supported
     */
    public List<ViewObjectLight> getObjectRelatedViews(String oid, String objectClass, int limit)
            throws BusinessObjectNotFoundException, MetadataObjectNotFoundException, InvalidArgumentException;
  
    /**
     * Allows to retrieve a list of views of a certain type, specifying their class
     * @param viewClassName The class name
     * @param limit The limit of results. -1 for all
     * @return The list of views
     * @throws InvalidArgumentException If the view class does not exist
     * @throws NotAuthorizedException If the user is not allowed to query for general views
     */
    public List<ViewObjectLight> getGeneralViews(String viewClassName, int limit) throws InvalidArgumentException, NotAuthorizedException;;

    /**
     * Returns a view of those that are not related to a particular object (i.e.: GIS views)
     * @param viewId view id
     * @return An object representing the view
     * @throws ApplicationObjectNotFoundException If the requested view is not found
     */
    public ViewObject getGeneralView(long viewId) throws ApplicationObjectNotFoundException;

    /**
     * Creates a view for a given object. If there's already a view of the provided view type, it will be overwritten
     * @param oid object's oid
     * @param objectClass object class
     * @param name view name
     * @param description view description
     * @param viewClassName view class name (See class ViewObject for details about the supported types)
     * @param structure XML document with the view structure (see http://sourceforge.net/apps/mediawiki/kuwaiba/index.php?title=XML_Documents#To_Save_Object_Views for details about the supported format)
     * @param background background image
     * @return The id of the new view.
     * @throws BusinessObjectNotFoundException if the object can not be found
     * @throws MetadataObjectNotFoundException if the object class can not be found
     * @throws InvalidArgumentException if the view type is not supported
     */
    public long createObjectRelatedView(String oid, String objectClass, String name, String description, 
            String viewClassName, byte[] structure, byte[] background)
            throws BusinessObjectNotFoundException, MetadataObjectNotFoundException, InvalidArgumentException;

    /**
     * Creates a view not related to a particular object
     * @param viewClass View class
     * @param name view name
     * @param description view description
     * @param structure XML document specifying the view structure (nodes, edges, control points)
     * @param background Background image
     * @return The id of the newly created view
     * @throws InvalidArgumentException if the view type is invalid
     */
    public long createGeneralView(String viewClass, String name, String description, byte[] structure, byte[] background)
            throws InvalidArgumentException;

    /**
     * Updates a view for a given object. If there's already a view of the provided view type, it will be overwritten
     * @param oid object's oid
     * @param objectClass object class
     * @param viewId viewId
     * @param name view name
     * @param description view description
     * @param structure XML document with the view structure (see http://neotropic.co/kuwaiba/wiki/index.php?title=XML_Documents#To_Save_Object_Views for details about the supported format)
     * @param background Background image. If null, the previous will be removed, if 0-sized array, it will remain unchanged
     * @return The summary of the changes
     * @throws BusinessObjectNotFoundException if the object can not be found
     * @throws MetadataObjectNotFoundException if the object class can not be found
     * @throws InvalidArgumentException if the view type is not supported
     * @throws ApplicationObjectNotFoundException If the view can not be found
     */
    public ChangeDescriptor updateObjectRelatedView(String oid, String objectClass, long viewId, String name, 
            String description, byte[] structure, byte[] background)
            throws BusinessObjectNotFoundException, MetadataObjectNotFoundException, InvalidArgumentException, ApplicationObjectNotFoundException;

    /**
     * Saves a view not related to a particular object. The view type can not be changed
     * @param oid View id
     * @param name view name. Null to leave unchanged
     * @param description view description. Null to leave unchanged
     * @param structure XML document specifying the view structure (nodes, edges, control points). Null to leave unchanged
     * @param background Background image. If null, the previous will be removed, if 0-sized array, it will remain unchanged
     * @return The summary of the changes.
     * @throws InvalidArgumentException if the view type is invalid or if the background could not be saved.
     * @throws ApplicationObjectNotFoundException if the view couldn't be found
     */
    public ChangeDescriptor updateGeneralView(long oid, String name, String description, byte[] structure, byte[] background)
            throws InvalidArgumentException, ApplicationObjectNotFoundException;


    /**
     * Deletes a list of general views
     * @param ids The ids of the views to be deleted.
     * @throws ApplicationObjectNotFoundException if the view can't be found
     */
    public void deleteGeneralViews(long[] ids) throws ApplicationObjectNotFoundException;

    /**
     * Creates a Query
     * @param queryName The name of the query.
     * @param ownerOid The if of the user that will own the query. Use -1 to make it public.
     * @param queryStructure The structure of the query as an XML document.
     * @param description The description of the query.
     * @return The id of the newly created query.
     * @throws ApplicationObjectNotFoundException If the owner could not be found.
     */
    public long createQuery(String queryName, long ownerOid, byte[] queryStructure,
            String description) throws ApplicationObjectNotFoundException;

    /**
     * Updates an existing query
     * @param queryOid The id of the query
     * @param queryName The name of the query. Leave null to keep the old value.
     * @param ownerOid The id of the user that owns this query. Use -1 to keep the old value.
     * @param queryStructure The structure of the query as an XML document. Leave null to keep the old value.
     * @param description The description of the query. Leave null to keep the old value.
     * @return The summary of the changes.
     * @throws ApplicationObjectNotFoundException If the query can not be found.
     */
    public ChangeDescriptor saveQuery(long queryOid, String queryName, long ownerOid, byte[] queryStructure, String description)
            throws ApplicationObjectNotFoundException;

    /**
     * Deletes a Query
     * @param queryOid The id of the query.
     * @throws ApplicationObjectNotFoundException If the query could not be found
     */
    public void deleteQuery(long queryOid) throws ApplicationObjectNotFoundException;

    /**
     * Gets all queries
     * @param showPublic Include public queries or show only the private ones.
     * @return The list of queries.
     */
    public List<CompactQuery> getQueries(boolean showPublic);

    /**
     * Gets a single query
     * @param queryOid The id of the query.
     * @return The query as an object.
     * @throws ApplicationObjectNotFoundException If the query could not be found.
     */
    public CompactQuery getQuery(long queryOid) 
            throws ApplicationObjectNotFoundException;

    /**
     * Used to perform complex queries. Please note
     * that the first record is reserved for the column headers, so and empty result set
     * will have at least one record.
     * @param query The code-friendly representation of the query made using the graphical query builder
     * @return a set of objects matching the specified criteria as ResultRecord array
     * @throws MetadataObjectNotFoundException If the class to be search is cannot be found
     * @throws InvalidArgumentException If any instance does not have a uuid
     */
    public List<ResultRecord> executeQuery(ExtendedQuery query) throws MetadataObjectNotFoundException, InvalidArgumentException;

    /**
     * Get the data model class hierarchy as an XML document
     * @param showAll
     * @return The class hierarchy as an XML document
     * @throws MetadataObjectNotFoundException If one of the core classes could not be found
     */
    public byte[] getClassHierachy(boolean showAll) 
            throws MetadataObjectNotFoundException;
    
    //Pools
    /**
     * Creates a pool without a parent. They're used as general purpose place to put inventory objects, or as root for particular models
     * @param name Pool name
     * @param description Pool description
     * @param instancesOfClass What kind of objects can this pool contain? 
     * @param type Type of pool. For possible values see ApplicationManager.POOL_TYPE_XXX
     * @return The id of the new pool
     * @throws MetadataObjectNotFoundException If instancesOfClass is not a valid subclass of InventoryObject
     */
    public String createRootPool(String name, String description, String instancesOfClass, int type)
            throws MetadataObjectNotFoundException;
    
    /**
     * Creates a pool that will have as parent an inventory object. This special containment structure can be used to 
     * provide support for new models
     * @param parentClassname Class name of the parent object
     * @param parentId Id of the parent object
     * @param name Pool name
     * @param description Pool description
     * @param instancesOfClass What kind of objects can this pool contain? 
     * @param type Type of pool. For possible values see ApplicationManager.POOL_TYPE_XXX
     * @return The id of the new pool
     * @throws MetadataObjectNotFoundException If instancesOfClass is not a valid subclass of InventoryObject
     * @throws BusinessObjectNotFoundException If the parent object can not be found
     */
    public String createPoolInObject(String parentClassname, String parentId, String name, String description, String instancesOfClass, int type)
            throws MetadataObjectNotFoundException, BusinessObjectNotFoundException;

    /**
     * Creates a pool that will have as parent another pool. This special containment structure can be used to 
     * provide support for new models
     * @param parentId Id of the parent pool
     * @param name Pool name
     * @param description Pool description
     * @param instancesOfClass What kind of objects can this pool contain? 
     * @param type Type of pool. Not used so far, but it will be in the future. It will probably be used to help organize the existing pools
     * @return The id of the new pool
     * @throws MetadataObjectNotFoundException If instancesOfClass is not a valid subclass of InventoryObject
     * @throws ApplicationObjectNotFoundException If the parent object can not be found
     */
    public String createPoolInPool(String parentId, String name, String description, String instancesOfClass, int type)
            throws MetadataObjectNotFoundException, ApplicationObjectNotFoundException;    
    
    /**
     * Deletes a set of pools. Note that this method will delete and commit the changes until it finds an error, so if deleting any of the pools fails, don't try to delete those that were already processed
     * @param ids the list of ids from the objects to be deleted
     * @throws ApplicationObjectNotFoundException If any of the pools to be deleted couldn't be found
     * @throws OperationNotPermittedException If any of the objects in the pool can not be deleted because it's not a business related instance (it's more a security restriction)
     */
    public void deletePools(String[] ids) throws ApplicationObjectNotFoundException, OperationNotPermittedException;
   
    /**
     * Updates a pool. The class name field is read only to preserve the integrity of the pool. Same happens to the field type
     * @param poolId Pool Id
     * @param name Pool name. If null, this field will remain unchanged
     * @param description Pool description. If null, this field will remain unchanged
     * @return The summary of the changes.
     */
    public ChangeDescriptor setPoolProperties(String poolId, String name, String description);
    
    /**
     * Gets the name Of a special parent by scale up the SPECIAL_OF_CHILD hierarchy
     * Use case: Use in reports to get the costumer of a service
     * @param className className
     * @param id node id
     * @param targetLevel number of levels to scale up
     * @return the name of the parent
     */
    public String getNameOfSpecialParentByScaleUp(String className, long id, int targetLevel);
    
    /**
     * Gets a business object audit trail
     * @param objectClass Object class
     * @param objectId Object id
     * @param limit Max number of results to be shown
     * @return The list of activity entries
     * @throws BusinessObjectNotFoundException If the object can not be found
     * @throws MetadataObjectNotFoundException If the provided class couldn't be found
     * @throws InvalidArgumentException If the class provided is not subclass of  InventoryObject
     */
    public List<ActivityLogEntry> getBusinessObjectAuditTrail(String objectClass, String objectId, int limit)
            throws BusinessObjectNotFoundException, MetadataObjectNotFoundException, InvalidArgumentException;
    
    /**
     * Retrieves the list of general activity log entries
     * @param page current page
     * @param limit limit of results per page. 0 to retrieve them all
     * @return The list of activity log entries
     */
    public List<ActivityLogEntry> getGeneralActivityAuditTrail(int page, int limit);
    
    /**
     * Validates if a user is allowed to call a given web service method
     * @param methodName The method to check if the user is allowed to call it.
     * @param ipAddress The IP address the method is being invoked from
     * @param sessionId The session token
     * @throws NotAuthorizedException If the user is not allowed to invoke the method
     */
    public void validateWebServiceCall(String methodName, String ipAddress, String sessionId) throws NotAuthorizedException;
    
    /**
     * Creates a session
     * @param user User name
     * @param password Password
     * @param sessionType The type of session to be created. This type depends on what kind of client is trying to access (a desktop client, a web client, a web service user, etc. See Session.TYPE_XXX for possible session types
     * @param IPAddress IP address the session is created from
     * @return A session object with information about the session itself plus information about the user
     * @throws ApplicationObjectNotFoundException If the user does not exist
     * @throws NotAuthorizedException If the password is incorrect or if the user is not enabled.
     */
    public Session createSession(String user, String password, int sessionType, String IPAddress) throws ApplicationObjectNotFoundException, NotAuthorizedException;
    
    /**
     * Retrieves a user from the session ring given a session id
     * @param sessionId The session token
     * @return The user associated to a given session
     * @throws ApplicationObjectNotFoundException If the user could not be found
     */
    public UserProfile getUserInSession(String sessionId) throws ApplicationObjectNotFoundException;
    /**
     * Gets all the users in a group
     * @param groupId The id of the group
     * @return The list of users in that group
     * @throws ApplicationObjectNotFoundException If the group could not be found. 
     */
    public List<UserProfile> getUsersInGroup(long groupId) throws ApplicationObjectNotFoundException;
    /**
     * Retrieves the list of groups a user belongs to
     * @param userId The id of the user
     * @return The list of groups for this user
     * @throws ApplicationObjectNotFoundException If the user could not be found 
     */
    public List<GroupProfileLight> getGroupsForUser(long userId) throws ApplicationObjectNotFoundException;
    
    /**
     * Closes a session
     * @param sessionId The session id
     * @param remoteAddress remote IP Address
     * @throws NotAuthorizedException If the session ID is Invalid or the IP does not match with the one registered for this session
     */
    public void closeSession(String sessionId, String remoteAddress) throws NotAuthorizedException;
 
    /**
     * Sets the configuration variables of this manager
     * @param properties A Properties object with the configuration variables
     */
    public void setConfiguration(Properties properties);
    
    /**
     * Gets the configuration variables of this manager
     * @return A Properties object with the configuration variables
     */
    public Properties getConfiguration();
    
    /**
     * Creates a general activity log entry, that is, an entry that is not associated to a particular object
     * @param userName User that performed the action
     * @param type Type of action. See class ActivityLogEntry for possible values
     * @param notes Optional additional notes related to the action. The Id of the element, if it was created/deleted. Null if no notes should be added 
     * @throws ApplicationObjectNotFoundException If the log root node could not be found
     */
    public void createGeneralActivityLogEntry(String userName, int type,
            String notes) throws ApplicationObjectNotFoundException;
    /**
     * Creates a general activity log entry, that is, an entry that is not associated to a particular object
     * @param userName User that performed the action
     * @param type Type of action. See class ActivityLogEntry for possible values
     * @param changeDescriptor The descriptor with all the changes performed by the method
     * @throws ApplicationObjectNotFoundException If the log root node could not be found
     */
    public void createGeneralActivityLogEntry(String userName, int type, ChangeDescriptor changeDescriptor) throws ApplicationObjectNotFoundException;
    
    /**
     * Creates an object activity log entry, that is, an entry that is directly related to an object, such as the modification of the value of an attribute
     * @param userName User that performs the operation
     * @param className The class of the object being modified
     * @param oid The oid of the object being modified
     * @param type The type of action. See ActivityLogEntry class for possible values
     * @param affectedProperties Properties that were affected. Normally, they're separated by spaces, but it's not required
     * @param oldValues Old values. Normally, they're separated by spaces, but it's not required
     * @param newValues New values. Normally, they're separated by spaces, but it's not required
     * @param notes Additional notes associated with the change
     * @throws ApplicationObjectNotFoundException If the object activity log could no be found
     * @throws BusinessObjectNotFoundException  If the modified object itself could not be found
     */
    public void createObjectActivityLogEntry(String userName, String className, String oid, int type, 
        String affectedProperties, String oldValues, String newValues, String notes) throws ApplicationObjectNotFoundException, BusinessObjectNotFoundException;
    
    /**
     * Creates an object activity log entry, that is, an entry that is directly related to an object, such as the modification of the value of an attribute
     * @param userName User that performs the operation
     * @param className The class of the object being modified
     * @param oid The oid of the object being modifies
     * @param type The type of action. See ActivityLogEntry class for possible values
     * @param changeDescriptor The summary of the changes that were done
     * @throws ApplicationObjectNotFoundException If the object activity log could no be found
     * @throws BusinessObjectNotFoundException If the modified object itself could not be found
     */
    public void createObjectActivityLogEntry(String userName, String className, String oid,  
            int type, ChangeDescriptor changeDescriptor) throws ApplicationObjectNotFoundException, BusinessObjectNotFoundException;
    
    /**
     * Allows to execute custom database queries. This method should not be used as it's only a temporary solution
     * @param dbCode A string with the query
     * @param needReturn
     * @return A table with results, that could also be interpreted as a multidimensional array with numerous paths
     * @throws NotAuthorizedException If the user is not allowed to run arbitrary code on the database
     * @deprecated Don't use it, instead, create a method in the corresponding entity manager instead of running code directly on the database
     */
    public HashMap<String, BusinessObjectList> executeCustomDbCode(String dbCode, boolean needReturn) throws NotAuthorizedException;
    /**
     * This method is the evolution of the deprecated {@link #executeCustomDbCode(java.lang.String, boolean) }. It allows the user to execute 
     * scripts that usually perform queries to the data base using the native query language and then pre-process the result before returning 
     * anything. 
     * @param queryName The (unique) name of the query.
     * @param parameters The parameters as entries in a Properties object. The consumer of the script must be aware of the format of the parameters. They could 
     * be provided as simple strings, or in their former types.
     * @return A list of results as {@link Properties } instances instances. As a way of speaking, every Properties instances is like a row in a table of results. 
     * @throws ApplicationObjectNotFoundException If the scripted query with the name provided could not be found.
     * @throws InvalidArgumentException If any of the parameters is invalid or if an unexpected error occurred during the execution of the script.
     */
    public List<Properties> executeCustomScriptedQuery(String queryName, Properties parameters) throws ApplicationObjectNotFoundException, InvalidArgumentException;
    
    /**
     * Registers a commercial module. Replaces an existing one if the name of provided one is already registered
     * @param module The module to be registered
     * @throws NotAuthorizedException If the user is not authorized to register commercial modules
     */
    public void registerCommercialModule(GenericCommercialModule module) throws NotAuthorizedException;
    /**
     * Gets a particular commercial module based on its name
     * @param moduleName The module name
     * @return The module. Null if the name could not be found
     * @throws NotAuthorizedException If the user is not authorized to access a particular commercial module
     * @throws NoCommercialModuleFoundException the commercial module can not be found
     */
    public GenericCommercialModule getCommercialModule(String moduleName) throws NotAuthorizedException, NoCommercialModuleFoundException;
    /**
     * Gets a commercial module based on its name
     * @return The module instance
     * @throws NotAuthorizedException If the user is not authorized to access a particular commercial module
     */
    public Collection<GenericCommercialModule> getCommercialModules() throws NotAuthorizedException;    
    /**
     * Creates and schedule a task. A task is an application entity that allows to run jobs that will be executed depending on certain schedule
     * @param name Task name
     * @param description Task description
     * @param enabled Is the task enabled?
     * @param commitOnExecute Should this task commit the changes made (if any) after executing it?
     * @param script The script to be executed
     * @param parameters The parameters for the script
     * @param schedule When the task should be executed
     * @param notificationType How the result of the task should be notified to the associated users 
     * @return The id of the newly created task
     */
    public long createTask(String name, String description, boolean enabled, boolean commitOnExecute, String script, List<StringPair> parameters, TaskScheduleDescriptor schedule, TaskNotificationDescriptor notificationType);
    /**
     * Updates any of these properties from a task: name, description, enabled and script
     * @param taskId Task id
     * @param propertyName Property name. Possible values: "name", "description", "enabled" and "script"
     * @param propertyValue The value of the property. For the property "enabled", the allowed values are "true" and "false"
     * @return The summary of the changes
     * @throws ApplicationObjectNotFoundException If the task could not be found
     * @throws InvalidArgumentException If the property name has an invalid value
     */
    public ChangeDescriptor updateTaskProperties(long taskId, String propertyName, String propertyValue) throws ApplicationObjectNotFoundException, InvalidArgumentException;
    /**
     * Updates the parameters of a task. If any of the values is null, that parameter will be deleted, if the parameter does not exist, it will be created
     * @param taskId Task id
     * @param parameters The parameters to be modified as pairs paramName/paramValue
     * @return The summary of the changes
     * @throws ApplicationObjectNotFoundException If the task could not be found
     */
    public ChangeDescriptor updateTaskParameters(long taskId, List<StringPair> parameters) throws ApplicationObjectNotFoundException;
    /**
     * Updates a task schedule
     * @param taskId Task id
     * @param schedule New schedule
     * @return The summary of the changes
     * @throws ApplicationObjectNotFoundException If the task could not be found
     */
    public ChangeDescriptor updateTaskSchedule(long taskId, TaskScheduleDescriptor schedule) throws ApplicationObjectNotFoundException;
    /**
     * Updates a task notification type
     * @param taskId Task id
     * @param notificationType New notification type
     * @return The summary of the changes
     * @throws ApplicationObjectNotFoundException If the task could not be found
     */
    public ChangeDescriptor updateTaskNotificationType(long taskId, TaskNotificationDescriptor notificationType) throws ApplicationObjectNotFoundException;
    /**
     * Deletes a task and unsubscribes all users from it
     * @param taskId Task id
     * @throws ApplicationObjectNotFoundException If the task could not be found
     */
    public void deleteTask(long taskId) throws ApplicationObjectNotFoundException;
    /**
     * Subscribes a user to a task, so it will be notified of the result of its execution
     * @param taskId Id of the task
     * @param userId Id of the user
     * @return The summary of the changes
     * @throws ApplicationObjectNotFoundException If the task or the user could not be found
     * @throws InvalidArgumentException If the user is already subscribed to the task
     */
    public ChangeDescriptor subscribeUserToTask(long userId, long taskId) throws ApplicationObjectNotFoundException, InvalidArgumentException;
    /**
     * Unsubscribes a user from a task, so it will no longer be notified about the result of its execution
     * @param taskId Id of the task
     * @param userId Id of the user
     * @return The summary of the changes
     * @throws ApplicationObjectNotFoundException If the task or the user could not be found
     */
    public ChangeDescriptor unsubscribeUserFromTask(long userId, long taskId) throws ApplicationObjectNotFoundException;
    /**
     * Retrieves the information about a particular task
     * @param taskId Id of the task
     * @return A remote task object representing the task
     * @throws ApplicationObjectNotFoundException If the task could not be found
     */
    public Task getTask(long taskId) throws ApplicationObjectNotFoundException;
    /**
     * Gets the subscribers of a particular task
     * @param taskId task id
     * @return The list of users subscribers to the task identified with the id taskId
     * @throws ApplicationObjectNotFoundException Id the task could not be found
     */
    public List<UserInfoLight> getSubscribersForTask(long taskId) throws ApplicationObjectNotFoundException;
    /**
     * Gets all registered tasks
     * @return A list with the task objects
     */
    public List<Task> getTasks();
    /**
     * Gets the tasks associated to a particular user
     * @param userId Id if the user
     * @return A list with the task objects
     * @throws ApplicationObjectNotFoundException If the user can not be found
     */
    public List<Task> getTasksForUser(long userId) throws ApplicationObjectNotFoundException;
    /**
     * Executes a task on demand
     * @param taskId Id of the task
     * @return An object representing the task result
     * @throws ApplicationObjectNotFoundException If the task could not be found
     * @throws InvalidArgumentException  If the task doesn't have a script
     */
    public TaskResult executeTask(long taskId) throws ApplicationObjectNotFoundException, InvalidArgumentException;
    /**
     * Creates a template.
     * @param templateClass The class you want to create a template for.
     * @param templateName The name of the template. It can not be null.
     * @return The id of the newly created template.
     * @throws org.kuwaiba.apis.persistence.exceptions.MetadataObjectNotFoundException If the provided class does not exist
     * @throws org.kuwaiba.apis.persistence.exceptions.OperationNotPermittedException If the template class is abstract.
     */
    public String createTemplate(String templateClass, String templateName) throws MetadataObjectNotFoundException, OperationNotPermittedException;
    /**
     * Creates an object inside a template.
     * @param templateElementClass Class of the object you want to create.
     * @param templateElementParentClassName Class of the parent to the obejct you want to create.
     * @param templateElementParentId Id of the parent to the obejct you want to create.
     * @param templateElementName Name of the element.
     * @return The id of the new object.
     * @throws org.kuwaiba.apis.persistence.exceptions.MetadataObjectNotFoundException If the object (or its parent) class could not be found
     * @throws org.kuwaiba.apis.persistence.exceptions.ApplicationObjectNotFoundException If the parent object could not be found
     * @throws org.kuwaiba.apis.persistence.exceptions.OperationNotPermittedException If the class provided to create the new element from is abstract.
     */
    public String createTemplateElement(String templateElementClass, String templateElementParentClassName, 
        String templateElementParentId, String templateElementName) 
        throws MetadataObjectNotFoundException, ApplicationObjectNotFoundException, OperationNotPermittedException;
    
    /**
     * Create an special object inside an template
     * @param tsElementClass Template special element class
     * @param tsElementParentClassName Template special element parent class name
     * @param tsElementParentId Template special element parent Id
     * @param tsElementName Template special element name
     * @return The id of the new object
     * @throws OperationNotPermittedException If the element class are not a possible special child of the element parent class
     *                                        Or if the element class given are abstract
     * @throws MetadataObjectNotFoundException If the element class or element parent class can not be found
     * @throws ApplicationObjectNotFoundException If the element parent can no be found
     */
    public String createTemplateSpecialElement(String tsElementClass, String tsElementParentClassName, String tsElementParentId, String tsElementName) 
        throws OperationNotPermittedException, MetadataObjectNotFoundException, ApplicationObjectNotFoundException;
    
    /**
     * Creates multiple template elements using a given name pattern
     * @param templateElementClassName The class name of the new set of template elements
     * @param templateElementParentClassName The parent class name of the new set of template elements
     * @param templateElementParentId The parent id of the new set of template elements
     * @param numberOfTemplateElements The number of template elements
     * @param templateElementNamePattern Name pattern of the new set of template elements
     * @return An array of ids for the new template elements
     * @throws MetadataObjectNotFoundException If the parent class name or the template element class name cannot be found
     * @throws OperationNotPermittedException If the given template element class cannot be a child of the given parent
     * @throws ApplicationObjectNotFoundException If the parent class name cannot be found
     * @throws InvalidArgumentException If the given pattern to generate the name has less possibilities that the number of template elements to be created
     */
    public String[] createBulkTemplateElement(String templateElementClassName, String templateElementParentClassName, 
            String templateElementParentId, int numberOfTemplateElements, String templateElementNamePattern)
        throws MetadataObjectNotFoundException, OperationNotPermittedException, ApplicationObjectNotFoundException, InvalidArgumentException;
    
    /**
     * Creates multiple special template elements using a given name pattern
     * @param stElementClass The class name of the new set of special template elements
     * @param stElementParentClassName The parent class name of the new set of special template elements
     * @param stElementParentId The parent id of the new set of special template elements
     * @param numberOfTemplateElements The number of template elements
     * @param stElementNamePattern Name pattern of the new set of special template elements
     * @return An array if ids for the new special template elements
     * @throws OperationNotPermittedException If the parent class name or the special template element class name cannot be found
     * @throws MetadataObjectNotFoundException If the given special template element class cannot be a child of the given parent
     * @throws ApplicationObjectNotFoundException If the parent class name cannot be found
     * @throws InvalidArgumentException If the given pattern to generate the name has less possibilities that the number of special template elements to be created
     */
    public String[] createBulkSpecialTemplateElement(String stElementClass, String stElementParentClassName, 
            String stElementParentId, int numberOfTemplateElements, String stElementNamePattern) 
        throws OperationNotPermittedException, MetadataObjectNotFoundException, ApplicationObjectNotFoundException, InvalidArgumentException;
    /**
     * Updates the value of an attribute of a template element.
     * @param templateElementClass Class of the element you want to update.
     * @param templateElementId Id of the element you want to update.
     * @param attributeNames Names of the attributes that you want to be updated as an array of strings.
     * @param attributeValues The values of the attributes you want to upfate. For list types, it's the id of the related type
     * @return The summary of the changes
     * @throws org.kuwaiba.apis.persistence.exceptions.MetadataObjectNotFoundException If any of the classes provided as arguments do not exist
     * @throws org.kuwaiba.apis.persistence.exceptions.ApplicationObjectNotFoundException If the template element could not be found
     * @throws org.kuwaiba.apis.persistence.exceptions.InvalidArgumentException If the arrays attributeNames and attributeValues have different sizes
     */
    public ChangeDescriptor updateTemplateElement(String templateElementClass, String templateElementId, 
            String[] attributeNames, String[] attributeValues) throws MetadataObjectNotFoundException, ApplicationObjectNotFoundException, InvalidArgumentException;
    /**
     * Deletes an element within a template or a template itself.
     * @param templateElementClass The template element class.
     * @param templateElementId The template element id.
     * @return The summary of the changes
     * @throws org.kuwaiba.apis.persistence.exceptions.MetadataObjectNotFoundException If the element's class could not be found.
     * @throws org.kuwaiba.apis.persistence.exceptions.ApplicationObjectNotFoundException If the element could not be found.
     */
    public ChangeDescriptor deleteTemplateElement(String templateElementClass, String templateElementId) throws MetadataObjectNotFoundException, ApplicationObjectNotFoundException;
    /**
     * Gets the templates available for a given class
     * @param className Class whose templates we need
     * @return A list of templates (actually, the top element) as a list of RemoteOObjects
     * @throws org.kuwaiba.apis.persistence.exceptions.MetadataObjectNotFoundException If the class provided could not be found.
     */
    public List<TemplateObjectLight> getTemplatesForClass(String className) throws MetadataObjectNotFoundException;
    /**
     * Retrieves the children of a given template element.
     * @param templateElementClass Template element class.
     * @param templateElementId Template element id.
     * @return The template element's children as a list of RemoteBusinessObjectLight instances.
     */
    public List<TemplateObjectLight> getTemplateElementChildren(String templateElementClass, String templateElementId);
    /**
     * Retrieves the children of a given template special element.
     * @param tsElementClass Template special element class.
     * @param tsElementId Template special element id.
     * @return The template element's children as a list of RemoteBusinessObjectLight instances.
     */
    public List<TemplateObjectLight> getTemplateSpecialElementChildren(String tsElementClass, String tsElementId);
    /**
     * Retrives all the information of a given template element.
     * @param templateElementClass Template element class.
     * @param templateElementId Template element id.
     * @return The template element information
     * @throws MetadataObjectNotFoundException If the template class does not exist
     * @throws ApplicationObjectNotFoundException If the template element could not be found.
     * @throws InvalidArgumentException If an attribute value can't be mapped into value.
     */
    public TemplateObject getTemplateElement(String templateElementClass, String templateElementId)
        throws MetadataObjectNotFoundException, ApplicationObjectNotFoundException, InvalidArgumentException;
    
    /**
     * Copy template elements within templates. Should not be used to copy entire templates.
     * @param sourceObjectsClassNames Array with the class names of the elements to be copied.
     * @param sourceObjectsIds  Array with the ids of the elements to be copied.
     * @param newParentClassName Class of the parent of the copied objects.
     * @param newParentId Id of the parent of the copied objects.
     * @return An array with the ids of the newly created elements in the same order they were provided.
     * @throws MetadataObjectNotFoundException If any of the classes could not be found.
     * @throws ApplicationObjectNotFoundException If any of the source template elements could not be found.
     * @throws ArraySizeMismatchException If the arrays provided as arguments have different sizes.
     */
    public String[] copyTemplateElements(String[] sourceObjectsClassNames, String[] sourceObjectsIds, 
            String newParentClassName, String newParentId) throws MetadataObjectNotFoundException, ApplicationObjectNotFoundException, ArraySizeMismatchException;
    
    /**
     * Copy template special elements within templates,
     * @param sourceObjectsClassNames Array with the class names of the special elements to be copied.
     * @param sourceObjectsIds Array with the ids of the special elements to be copied.
     * @param newParentClassName Class of the parent of the copied objects.
     * @param newParentId Id of the parent of the copied objects.
     * @return An array with the ids of the newly created special elements in the same order they were provided.
     * @throws ArraySizeMismatchException If the arrays provided as arguments have different sizes.
     * @throws MetadataObjectNotFoundException If any of the classes could not be found.
     * @throws ApplicationObjectNotFoundException If any of the source template elements could not be found.
     */
    public String[] copyTemplateSpecialElement(String[] sourceObjectsClassNames, String[] sourceObjectsIds, String newParentClassName, String newParentId) 
        throws ArraySizeMismatchException, ApplicationObjectNotFoundException, MetadataObjectNotFoundException;
    // Favorites
    /**
     * Adds an object to the favorites folder
     * @param objectClass Object class
     * @param objectId Object id
     * @param favoritesFolderId favorites folder id
     * @param userId User Id
     * @throws ApplicationObjectNotFoundException If the favorites folder can not be found
     * @throws MetadataObjectNotFoundException If the object can not be found
     * @throws BusinessObjectNotFoundException If the object can not be found
     * @throws OperationNotPermittedException If the object have a relationship with the favorite folder
     * @throws InvalidArgumentException If a node does not have uuid
     */
    public void addObjectTofavoritesFolder(String objectClass, String objectId, long favoritesFolderId, long userId) 
        throws ApplicationObjectNotFoundException, MetadataObjectNotFoundException, BusinessObjectNotFoundException, OperationNotPermittedException, InvalidArgumentException;
    
    /**
     * Removes an object associated to a favorites folder
     * @param objectClass Object class
     * @param objectId Object id
     * @param favoritesFolderId favorites folder id
     * @param userId User Id
     * @throws ApplicationObjectNotFoundException If the favorites folder can not be found
     * @throws MetadataObjectNotFoundException If the object can not be found
     * @throws BusinessObjectNotFoundException If the object can not be found
     * @throws InvalidArgumentException If a node does not have uuid
     */
    public void removeObjectFromfavoritesFolder(String objectClass, String objectId, long favoritesFolderId, long userId) 
        throws ApplicationObjectNotFoundException, MetadataObjectNotFoundException, BusinessObjectNotFoundException, InvalidArgumentException;
        
    /**
     * Create a relationship between an user and a new favorites folder
     * @param name favorites folder name
     * @param userId User id
     * @return The new favorites folder Id
     * @throws ApplicationObjectNotFoundException If the user can not be found 
     * @throws InvalidArgumentException If the name is null or empty
     */
    public long createFavoritesFolderForUser(String name, long userId) 
        throws ApplicationObjectNotFoundException, InvalidArgumentException;
    
    /**
     * Delete a Bookmark Folder of an User
     * @param favoritesFolderId favorites folder id
     * @param userId User Id
     * @throws ApplicationObjectNotFoundException If any favorites folder in the array can not be found
     */
    public void deleteFavoritesFolders (long[] favoritesFolderId, long userId)
        throws ApplicationObjectNotFoundException;
    
    /**
     * Get the favorites folders create by an user.
     * @param userId user id
     * @return List of Bookmarks folders for an User
     * @throws ApplicationObjectNotFoundException If the user can not be found
     */
    public List<FavoritesFolder> getFavoritesFoldersForUser(long userId) 
        throws ApplicationObjectNotFoundException;
    
    /**
     * Get the object assigned to the bookmark
     * @param favoritesFolderId favorites folder id
     * @param limit Max number of results
     * @param userId User Id
     * @throws ApplicationObjectNotFoundException If the favorites folder can not be found
     * @return List of objects related to bookmark
     * @throws InvalidArgumentException If any of the object does not have uuid
     */
    public List<BusinessObjectLight> getObjectsInFavoritesFolder(long favoritesFolderId, long userId, int limit) 
        throws ApplicationObjectNotFoundException, InvalidArgumentException;
    /**
     * Get the bookmarks where an object is associated
     * @param userId User id
     * @param objectClass Object class
     * @param objectId Object id
     * @return list of favorites folders where an object are an item
     * @throws MetadataObjectNotFoundException If the object can not be found
     * @throws BusinessObjectNotFoundException If the object can not be found
     * @throws ApplicationObjectNotFoundException If the object is associated to a bookmark folder but 
     *                                            The favorites folder is not associated to the current user
     * @throws InvalidArgumentException If nodes do not have uuid
     */
    public List<FavoritesFolder> getFavoritesFoldersForObject(long userId, String objectClass, String objectId) 
        throws MetadataObjectNotFoundException, BusinessObjectNotFoundException, ApplicationObjectNotFoundException, InvalidArgumentException;
    
    /**
     * Gets a favorites folder
     * @param favoritesFolderId favorites folder id
     * @param userId User id
     * @throws ApplicationObjectNotFoundException If the favorites folder can not be found
     * @return The favorite folder with id
     */
    public FavoritesFolder getFavoritesFolder(long favoritesFolderId, long userId) 
        throws ApplicationObjectNotFoundException;
    
    /**
     * Updates a favorites folder
     * @param favoritesFolderId favorites folder id
     * @param userId User Id
     * @param favoritesFolderName favorites folder name
     * @throws ApplicationObjectNotFoundException If the favorites folder can not be found
     * @throws InvalidArgumentException If the name of the favorites folder is null or empty
     */
    public void updateFavoritesFolder(long favoritesFolderId, long userId, String favoritesFolderName) 
        throws ApplicationObjectNotFoundException, InvalidArgumentException;
    
    /**
     * Creates a business rule given a set of constraints
     * @param ruleName Rule name
     * @param ruleDescription Rule description
     * @param ruleType Rule type. See BusinesRule.TYPE* for possible values.
     * @param ruleScope The scope of the rule. See BusinesRule.SCOPE* for possible values.
     * @param appliesTo The class this rule applies to. Can not be null.
     * @param ruleVersion The version of the rule. Useful to migrate it if necessary in further versions of the platform
     * @param constraints An array with the definition of the logic to be matched with the rule. Can not be empty or null
     * @return The id of the newly created business rule
     * @throws InvalidArgumentException If any of the parameters is null (strings) or leer than 1 or if the constraints array is null or empty
     */
    public long createBusinessRule(String ruleName, String ruleDescription, int ruleType, 
            int ruleScope, String appliesTo, String ruleVersion, List<String> constraints) throws InvalidArgumentException;
    
    /**
     * Deletes a business rule
     * @param businessRuleId Rule id
     * @throws ApplicationObjectNotFoundException If the given rule does not exist
     */
    public void deleteBusinessRule(long businessRuleId) throws ApplicationObjectNotFoundException;
    
    /**
     * Retrieves the business rules of a particular type.
     * @param type Rule type. See BusinesRule.TYPE* for possible values. Use -1 to retrieve all
     * @return The list of business rules with the matching type.
     */
    public List<BusinessRule> getBusinessRules(int type);
    /**
     * check if a relationship can be established between two objects with the attribute values defined in the rule
     * @param sourceObjectClassName The class of the element that's the subject of the rule
     * @param sourceObjectId The id of the subject of the rule
     * @param targetObjectClassName The class of the element that's the object of the rule
     * @param targetObjectId The id of the object of the rule
     * @throws BusinessRuleException If the rule matches, and the involved objects don't comply with the conditions stated by the rule
     * @throws InvalidArgumentException If the rule is malformed or can not be read.
     */
    public void checkRelationshipByAttributeValueBusinessRules(String sourceObjectClassName, String sourceObjectId ,
            String targetObjectClassName, String targetObjectId) throws BusinessRuleException, InvalidArgumentException;
    
    /**
     * Fetches a synchronization group. From the conceptual point of view, a sync group is a set of Synchronization Data Sources.
     * @param syncGroupId The id of the sync group
     * @return The sync group
     * @throws org.kuwaiba.apis.persistence.exceptions.ApplicationObjectNotFoundException If the sync group could not be found
     * @throws org.kuwaiba.apis.persistence.exceptions.InvalidArgumentException If the sync data group information is somehow malformed in the database
     * @throws org.kuwaiba.apis.persistence.exceptions.MetadataObjectNotFoundException If can not find the class name of the device related with the data source configuration
     * @throws org.kuwaiba.apis.persistence.exceptions.UnsupportedPropertyException
     */
    public SynchronizationGroup getSyncGroup(long syncGroupId) throws ApplicationObjectNotFoundException, InvalidArgumentException, MetadataObjectNotFoundException, UnsupportedPropertyException;
    /**
     * Gets the list of available sync groups 
     * @return The list of available sync groups
     * @throws InvalidArgumentException If any of the sync groups is malformed in the database
     * @throws org.kuwaiba.apis.persistence.exceptions.MetadataObjectNotFoundException
     * @throws org.kuwaiba.apis.persistence.exceptions.UnsupportedPropertyException
     */
    public List<SynchronizationGroup> getSyncGroups() throws InvalidArgumentException, MetadataObjectNotFoundException, UnsupportedPropertyException;
    /**
     * Gets a data source configuration of the object (there is only one data source configuration per object)
     * @param objectId the object id (a GenericCommunicationElement) or the SyncDataSourceConfig id
     * @return a SyncDataSourceConfiguration
     * @throws InvalidArgumentException  If any of the configurations is malformed in the database
     * @throws ApplicationObjectNotFoundException If the sync data source configuration  could not be found
     * @throws UnsupportedPropertyException if any property of the sync data source node is malformed or if there is an error with the relationship between the syncNode an it InventoryObjectNode
     */
    public  SyncDataSourceConfiguration getSyncDataSourceConfiguration(String objectId) throws InvalidArgumentException, ApplicationObjectNotFoundException, UnsupportedPropertyException;   
    /**
     * Gets a synchronization data source configuration by its id
     * @param objectId SyncDataSourceConfigurationById the synDatasourceConfig id
     * @return a SyncDatasourceConfiguration
     * @throws InvalidArgumentException  If any of the configurations is malformed in the database
     * @throws ApplicationObjectNotFoundException if the syncDatasource could not be found
     * @throws UnsupportedPropertyException if any property of the sync data source node is malformed or if there is an error with the relationship between the syncNode an it InventoryObjectNode
     */
    public SyncDataSourceConfiguration getSyncDataSourceConfigurationById(long objectId) throws InvalidArgumentException, ApplicationObjectNotFoundException, UnsupportedPropertyException;
    /**
     * Gets the data source configurations associated to a sync group. A data source configuration is a set of parameters to access a sync data source
     * @param syncGroupId The sync group the requested configurations belong to
     * @return A list of data source configurations
     * @throws ApplicationObjectNotFoundException If the sync group could not be found
     * @throws InvalidArgumentException If any of the configurations is malformed in the database
     * @throws org.kuwaiba.apis.persistence.exceptions.MetadataObjectNotFoundException
     * @throws org.kuwaiba.apis.persistence.exceptions.UnsupportedPropertyException
     */
    public List<SyncDataSourceConfiguration> getSyncDataSourceConfigurations(long syncGroupId) throws ApplicationObjectNotFoundException, InvalidArgumentException, MetadataObjectNotFoundException, UnsupportedPropertyException;
    /**
     * Creates a synchronization group
     * @param name The name of the new group
     * @return The id of the newly created group
     * @throws InvalidArgumentException If any of the parameters is invalid
     * @throws ApplicationObjectNotFoundException If the sync provider could not be found
     */
    public long createSyncGroup(String name) throws InvalidArgumentException, ApplicationObjectNotFoundException;
    /**
     * Updates the data source configurations associated to a given sync group
     * @param syncGroupId The Id of the sync group to be updated
     * @param syncGroupProperties The list of synchronization group properties
     * @throws ApplicationObjectNotFoundException If the sync group could not be found
     * @throws InvalidArgumentException If any of the provided data source configurations is invalid
     */
    public void updateSyncGroup(long syncGroupId, List<StringPair> syncGroupProperties)throws ApplicationObjectNotFoundException, InvalidArgumentException;
    
    /**
     * Deletes a sync group
     * @param syncGroupId The id of the sync group
     * @throws ApplicationObjectNotFoundException If the sync group can no be found
     */
    public void deleteSynchronizationGroup(long syncGroupId) throws ApplicationObjectNotFoundException;
    /**
     * Creates a data source configuration and associates it to a sync group
     * @param objectId  the id of the object(GenericCommunicationsElement) the data source configuration will belong to
     * @param syncGroupId The id of the sync group the data source configuration will be related to
     * @param name The name of the configuration
     * @param parameters The list of parameters that will be part of the new configuration. A sync data source configuration is a set of parameters that allow the synchronization provider to access a sync data source
     * @return The id of the newly created data source
     * @throws ApplicationObjectNotFoundException If the object has no sync data source configuration group could not be found
     * @throws InvalidArgumentException  If any of the parameters is not valid
     */
    public long createSyncDataSourceConfig(String objectId, long syncGroupId, String name, List<StringPair> parameters)throws ApplicationObjectNotFoundException, InvalidArgumentException, OperationNotPermittedException;
    /**
     * Updates a synchronization data source
     * @param syncDataSourceConfigId The id of an synchronization data source
     * @param parameters the list of parameters to update
     * @throws ApplicationObjectNotFoundException If the sync data source cannot be found
     */
    public void updateSyncDataSourceConfig(long syncDataSourceConfigId, List<StringPair> parameters) throws ApplicationObjectNotFoundException;

    /**
     * Deletes a synchronization data source
     * @param syncDataSourceConfigId The id of an synchronization data source
     * @throws ApplicationObjectNotFoundException If the sync data source cannot be found
     */
    public void deleteSynchronizationDataSourceConfig(long syncDataSourceConfigId) throws ApplicationObjectNotFoundException ;
    
    /**
     * Copy a set of sync group
     * @param syncGroupIds The array of sync groups ids to copy
     * @return A list of new sync groups
     * @throws ApplicationObjectNotFoundException If some of the sync group cannot be found or If the provider of the sync group cannot be found
     * @throws InvalidArgumentException If the sync group is malformed
     */
    //public List<SynchronizationGroup> copySyncGroup(long[] syncGroupIds) throws ApplicationObjectNotFoundException, InvalidArgumentException;
    /**
     * Creates "copy" a relation between a set of sync data source configurations and a given sync group
     * @param syncGroupId The Sync Group Id target
     * @param syncDataSourceConfigurationIds Set of sync data source configuration ids
     * @throws ApplicationObjectNotFoundException If the sync group cannot be found, or some sync data source configuration cannot be found
     * @throws InvalidArgumentException If the sync group cannot be found, or some sync data source configuration cannot be found
     */
    public void copySyncDataSourceConfiguration(long syncGroupId, long[] syncDataSourceConfigurationIds) throws ApplicationObjectNotFoundException, InvalidArgumentException;
    /**
     * Release a set of sync data source configuration from a given sync group
     * @param syncGroupId The Sync Group Id target
     * @param syncDataSourceConfigurationIds Set of sync data source configuration ids
     * @throws ApplicationObjectNotFoundException If the sync group cannot be found, or some sync data source configuration cannot be found
     * @throws InvalidArgumentException If the sync group cannot be found, or some sync data source configuration cannot be found
     */
    public void releaseSyncDataSourceConfigFromSyncGroup(long syncGroupId, long[] syncDataSourceConfigurationIds) throws ApplicationObjectNotFoundException, InvalidArgumentException;
    /**
     * Moves a sync data source configuration from a sync group to another sync group
     * @param syncGroupId The Sync Group Id target
     * @param syncDataSourceConfigurationIds Set of sync data source configuration ids
     * @throws ApplicationObjectNotFoundException If the sync group cannot be found, or some sync data source configuration cannot be found
     * @throws InvalidArgumentException If the sync group is malformed, or some sync data source configuration is malformed
     */
    public void moveSyncDataSourceConfiguration(long oldSyncGroupId, long newSyncGroupId, long[] syncDataSourceConfigurationIds) throws ApplicationObjectNotFoundException, InvalidArgumentException;
    /**
    * Gets the artifact associated to an activity (for example, a form that was already filled in by a user in a previous, already committed activity)
    * @param processInstanceId The id of the process instance. This process may have been ended already.
    * @param activityId The id of the activity the artifact belongs to
    * @return The artifact corresponding to the given activity
    * @throws ApplicationObjectNotFoundException If the process instance or activity couldn't be found.
    */
    public Artifact getArtifactForActivity(long processInstanceId, long activityId) throws ApplicationObjectNotFoundException;
    /**
    * Given an activity definition, returns the artifact definition associated to it
    * @param processDefinitionId The id of the process the activity is related to
    * @param activityDefinitionId The id of the activity
    * @return An object containing the artifact definition
    * @throws ApplicationObjectNotFoundException If the process or the activity could not be found
    */
    public ArtifactDefinition getArtifactDefinitionForActivity(long processDefinitionId, long activityDefinitionId) throws ApplicationObjectNotFoundException;
    /**
    * Saves the artifact generated once an activity has been completed (for example, the user filled in a form). 
    * @param processInstanceId The process instance the activity belongs to
    * @param activityDefinitionId The activity id
    * @param artifact The artifact to be saved
    * @throws ApplicationObjectNotFoundException If the process could not be found or if the activity definition could not be found
    * @throws InvalidArgumentException If the activity had been already executed,  of there's a mismatch in the artifact versions or if the user is not an authorized actor to carry on with the activity
    */
    public void commitActivity(long processInstanceId, long activityDefinitionId, Artifact artifact) 
            throws ApplicationObjectNotFoundException, InvalidArgumentException;
    /**
     * Updates the artifact generated once an activity has been completed (for example, the user filled in a form). 
     * @param processInstanceId The process instance the activity belongs to 
     * @param activityDefinitionId The activity id
     * @param artifact The artifact to be saved
     * @throws ApplicationObjectNotFoundException If the process could not be found or if the activity definition could not be found
     * @throws InvalidArgumentException If the activity had been already executed,  of there's a mismatch in the artifact versions or if the user is not an authorized actor to carry on with the activity
    */    
    public void updateActivity(long processInstanceId, long activityDefinitionId, Artifact artifact) 
        throws ApplicationObjectNotFoundException, InvalidArgumentException;
    /**
     * Gets Process Instance Activities Path
     * @param processInstanceId Process Instance Id to get path
     * @return The activity definition
     * 
     */    
    public List<ActivityDefinition> getProcessInstanceActivitiesPath(long processInstanceId);
    /**
    * Requests for the next activity to be executed in a process instance.
    * @param processInstanceId The running process to get the next activity from
    * @return The activity definition
    * @throws ApplicationObjectNotFoundException If the process instance could not be found
    * @throws InvalidArgumentException If the process already ended
    */
    public ActivityDefinition getNextActivityForProcessInstance(long processInstanceId) throws ApplicationObjectNotFoundException, InvalidArgumentException;
    /**
    * Retrieves a process definition
    * @param processDefinitionId The id of the process
    * @return The process definition. It contains an XML document to be parsed by the consumer
    * @throws ApplicationObjectNotFoundException If the process could not be found or if it's malformed
    */
    public ProcessDefinition getProcessDefinition(long processDefinitionId) throws ApplicationObjectNotFoundException;
    /**
     * Retrieves a process definition
     * @param processDefinitionId The id of the process definition
     * @param activityDefinitionId The id of the Activity definition
     * @return The activity definition
     */
    public ActivityDefinition getActivityDefinition(long processDefinitionId, long activityDefinitionId);
    /**
    * Deletes a process definition
    * @param processDefinitionId The process definition to be deleted
    * @throws ApplicationObjectNotFoundException If the process definition could not be found
     * @throws org.kuwaiba.apis.persistence.exceptions.InvalidArgumentException if there are process instances related to the process definition
    */
    public void deleteProcessDefinition(long processDefinitionId) throws ApplicationObjectNotFoundException, InvalidArgumentException;

    /**
    * Updates a process definition, either its standard properties or its structure
    * @param processDefinitionId The process definition id
    * @param properties A key value dictionary with the standard properties to be updated. These properties are: name, description, version and enabled (use 'true' or 'false' for the latter)
    * @param structure A byte array withe XML process definition body
    * @throws org.kuwaiba.apis.persistence.exceptions.ApplicationObjectNotFoundException If the process definition could not be found
    * @throws org.kuwaiba.apis.persistence.exceptions.InvalidArgumentException If the structure is invalid or If one of the properties is malformed or has an unexpected name
    */
    public void updateProcessDefinition(long processDefinitionId, List<StringPair> properties, byte[] structure) throws ApplicationObjectNotFoundException, InvalidArgumentException;
    /**
     * Creates a process definition. A process definition is the metadata that defines the steps and constraints 
     * of a given project
     * @param name The name of the new process definition
     * @param description The description of the new process definition
     * @param version The version of the new process definition. This is a three numbers, dot separated string (e.g. 2.4.1)
     * @param enabled If the project is enabled to create instances from it
     * @param structure The structure of the process definition. It's an XML document that represents a BPMN process definition
     * @return The id of the newly created process definition
     * @throws InvalidArgumentException If the process structure defines a malformed process or if the version is invalid
     */    
    public long createProcessDefinition(String name, String description, String version, boolean enabled, byte[] structure) 
            throws InvalidArgumentException;
    /**
     * Gets a process instances of a process definition
     * @param processDefinitionId The process definition id
     * @return The process instances
     * @throws ApplicationObjectNotFoundException If the process definition could not be found
     */
    public List<ProcessInstance> getProcessInstances(long processDefinitionId) throws ApplicationObjectNotFoundException;
    /**
     * Gets a process definition instances
     * @return The process instances
     */
    public List<ProcessDefinition> getProcessDefinitions();
    /**
     * Gets a process instance
     * @param processInstanceId Process Instance Id
     * @return a Process Instance for the given id
     * @throws ApplicationObjectNotFoundException If the process instance could not be found
     */
    public ProcessInstance getProcessInstance(long processInstanceId) throws ApplicationObjectNotFoundException ;
    /**
     * Updates the process definitions
     * @throws org.kuwaiba.apis.persistence.exceptions.InvalidArgumentException
     */
    public void reloadProcessDefinitions() throws InvalidArgumentException;
    /**
    * Creates an instance of a process, that is, starts one
    * @param processDefinitionId The id of the process to be started
    * @param processInstanceName The name of the new process
    * @param processInstanceDescription The description of the new process
    * @return The id of the newly created process instance
    * @throws ApplicationObjectNotFoundException If the process definition could not be found
    * @throws InvalidArgumentException If the process definition is disabled
    */
    public long createProcessInstance(long processDefinitionId, String processInstanceName, String processInstanceDescription) 
            throws ApplicationObjectNotFoundException, InvalidArgumentException;
    /**
     * Deletes a process instance
     * @param processInstanceId Process Instance Id
     * @throws OperationNotPermittedException If the process can no be deleted
     */
    public void deleteProcessInstance(long processInstanceId) throws OperationNotPermittedException;
    
    //Config Variables Management
    /**
     * Creates a configuration variable inside a pool. A configuration variable is a place where a value will be stored so it can retrieved by whomever need it. 
     * These variables are typically used to store values that help other modules to work, such as URLs, user names, dimensions, etc
     * @param configVariablesPoolId The id of the pool where the config variable will be put
     * @param name The name of the pool. This value can not be null or empty. Duplicate variable names are not allowed
     * @param description The description of the what the variable does
     * @param type The type of the variable. Use 1 for number, 2 for strings, 3 for booleans, 4 for unidimensional arrays and 5 for matrixes. 
     * @param masked If the value should be masked when rendered (for security reasons, for example)
     * @param valueDefinition In most cases (primitive types like numbers, strings or booleans) will be the actual value of the variable as a string (for example "5" or "admin" or "true"). For arrays and matrixes use the following notation: <br> 
     * Arrays: (value1,value2,value3,valueN), matrixes: [(row1col1, row1col2,... row1colN), (row2col1, row2col2,... row2colN), (rowNcol1, rowNcol2,... rowNcolN)]. The values will be interpreted as strings 
     * @return The id of the newly created variable
     * @throws ApplicationObjectNotFoundException If the parent pool could not be found
     * @throws org.kuwaiba.apis.persistence.exceptions.InvalidArgumentException If the name is empty, the type is invalid, the value definition is empty
     */
    public long createConfigurationVariable(String configVariablesPoolId, String name, String description, int type, boolean masked, String valueDefinition) throws ApplicationObjectNotFoundException, InvalidArgumentException;
    /**
     * Updates the value of a configuration variable. See #{@link #createConfigurationVariable(long, java.lang.String, java.lang.String, int, boolean, java.lang.String)  } for value definition syntax
     * @param name The current name of the variable that will be modified
     * @param propertyToUpdate The name of the property to be updated. Possible values are: "name", "description", "type", "masked" and "value"
     * @param newValue The new value as string
     * @throws InvalidArgumentException If the property to be updated can not be recognized
     * @throws ApplicationObjectNotFoundException If the config variable can not be found
     */
    public void updateConfigurationVariable(String name, String propertyToUpdate, String newValue) throws InvalidArgumentException, ApplicationObjectNotFoundException;
    /**
     * Deletes a config variable
     * @param name The name of the variable to be deleted
     * @throws ApplicationObjectNotFoundException If the config variable could not be found
     */
    public void deleteConfigurationVariable(String name) throws ApplicationObjectNotFoundException;
    /**
     * Retrieves a configuration variable
     * @param name The name of the variable to be retrieved
     * @return The variable
     * @throws org.kuwaiba.apis.persistence.exceptions.ApplicationObjectNotFoundException If the variable could not be found
     */
    public ConfigurationVariable getConfigurationVariable(String name) throws ApplicationObjectNotFoundException;
    /**
     * Retrieves only the value of a configuration variable. Masked values are returned as null. Unexisting variables will be returned as null.
     * @param name The name of the variable. Masked values are returned as null. Unexisting variables will be returned as null.
     * @return The value of the variable as a java object/data type. The numbers are returned as floats. The arrays and matrixes are returned as <code>ArrayList{@literal <String>}</code> and <code>ArrayList<ArrayList{@literal <String>}</code> instances respectively
     * @throws InvalidArgumentException If the value of the variable could not be successfully translated into a java type variable
     * @throws org.kuwaiba.apis.persistence.exceptions.ApplicationObjectNotFoundException If no configuration variable with that name could be found.
     */
    public Object getConfigurationVariableValue(String name) throws InvalidArgumentException, ApplicationObjectNotFoundException;
    /**
     * Gets the configuration variables in a configuration variable pool
     * @param poolId The id pool to retrieve the variables from
     * @return The list of config variables in the given pool
     * @throws ApplicationObjectNotFoundException If the pool could not be found
     */
    public List<ConfigurationVariable> getConfigurationVariablesInPool(String poolId) throws ApplicationObjectNotFoundException;
    /**
     * Gets the configuration variables with a given prefix
     * @param prefix The prefix of the variables name
     * @return The list of config variables with the given prefix
     */    
    public List<ConfigurationVariable> getConfigurationVariablesWithPrefix(String prefix);
    /**
     * Retrieves the list of pools of config variables
     * @return The available pools of configuration variables
     */
    public List<Pool> getConfigurationVariablesPools();
    /**
     * Creates a pool of configuration variables
     * @param name The name of the pool. Empty or null values are not allowed
     * @param description The description of the pool
     * @return The id of the newly created pool
     * @throws org.kuwaiba.apis.persistence.exceptions.InvalidArgumentException If the name provided is null or empty
     */
    public String createConfigurationVariablesPool(String name, String description) throws InvalidArgumentException;
    /**
     * Updates an attribute of a given config variables pool
     * @param poolId The id of the pool to update
     * @param propertyToUpdate The property to update. The valid values are "name" and "description"
     * @param value The value of the property to be updated
     * @throws InvalidArgumentException If the property provided is not valid
     * @throws ApplicationObjectNotFoundException If the pool could not be found
     */
    public void updateConfigurationVariablesPool(String poolId, String propertyToUpdate, String value) throws InvalidArgumentException, ApplicationObjectNotFoundException;
    /**
     * Deletes a configuration variables pool. Deleting a pool also deletes the config variables contained within
     * @param poolId The id of the pool to be deleted
     * @throws ApplicationObjectNotFoundException If the pool could not be found
     */
    public void deleteConfigurationVariablesPool(String poolId) throws ApplicationObjectNotFoundException;
    //<editor-fold desc="Validators" defaultstate="collapsed">
    /**
     * Creates a validator definition. 
     * @param name The name of the validator. It's recommended to use camel case notation (for example thisIsAName). This field is mandatory
     * @param description The optional description of the validator
     * @param classToBeApplied The class or super class of the classes whose instances will be checked against this validator
     * @param script The groovy script containing the logic of the validator , that is, the 
     * @param enabled If this validador should be applied or not
     * @return The id of the newly created validator definition
     * @throws InvalidArgumentException If the name is null or empty
     * @throws MetadataObjectNotFoundException If the classToBeApplied argument could not be found
     */
    public long createValidatorDefinition(String name, String description, String classToBeApplied, String script, boolean enabled) 
            throws InvalidArgumentException, MetadataObjectNotFoundException;
    /**
     * Updates the properties of a validator. The null values will be ignored
     * @param validatorDefinitionId The id of teh validator definition to be updated
     * @param name The new name, if any, null otherwise
     * @param description The new description, if any, null otherwise
     * @param classToBeApplied The new class to be associated to this validator, if any, null otherwise
     * @param script The new script, if any, null otherwise
     * @param enabled If the validator should be enabled or not, if any, null otherwise
     * @throws ApplicationObjectNotFoundException If the validator definition could not be found
     * @throws MetadataObjectNotFoundException If the classToBeApplied parameter is not valid
     * @throws InvalidArgumentException If the name is not null, but it is empty
     */
    public void updateValidatorDefinition(long validatorDefinitionId, String name, String description, String classToBeApplied, String script, Boolean enabled) 
            throws ApplicationObjectNotFoundException, MetadataObjectNotFoundException, InvalidArgumentException;
    /**
     * Retrieves all the validator definitions in the system
     * @param className The class to retrieve the validator definitions from.
     * @return The list of validator definitions
     */
    public List<ValidatorDefinition> getValidatorDefinitionsForClass(String className);
    /**
     * Runs the existing validations for the class associated to the given object. Validators set to enabled = false will be ignored
     * @param objectClass The class of the object
     * @param objectId The id of the object
     * @return The list of validators associated to the object and its class
     * @throws org.kuwaiba.apis.persistence.exceptions.BusinessObjectNotFoundException If the object can not be found
     */
    public List<Validator> runValidationsForObject(String objectClass, long objectId) throws BusinessObjectNotFoundException;
    /**
     * Deletes a validator definition
     * @param validatorDefinitionId the id of the validator to be deleted
     * @throws ApplicationObjectNotFoundException If the validator definition could not be found
     */
    public void deleteValidatorDefinition(long validatorDefinitionId) throws ApplicationObjectNotFoundException;
    //</editor-fold>
    //<editor-fold desc="Outside Plant" defaultstate="collapsed">
    /**
    * Creates an Outside Plant View
    * @param name The name of the new view
    * @param description The description of the new view
    * @param structure The XML document with the contents of the view. The format of the XML document is consistent with the other views
    * @throws InvalidArgumentException If the name is empty. 
    * @return The id of the newly created view
    */
    public long createOSPView(String name, String description, byte[] structure) throws InvalidArgumentException;
    
    /**
    * Retrieves the specific information about an existing OSP view
    * @param viewId The id of the view
    * @return An object containing the view details and structure
    * @throws ApplicationObjectNotFoundException If the view could not be found
    */
    public ViewObject getOSPView(long viewId) throws ApplicationObjectNotFoundException;
    /**
    * Retrieves the existing OSP views
    * @return The list of existing OSP views
    * @throws InvalidArgumentException If one of the views is malformed
    */
    public List<ViewObjectLight> getOSPViews() throws InvalidArgumentException;

    /**
    * Updates an existing OSP view
    * @param viewId The id of the view
    * @param name The new name of the view. Null if to remain unchanged
    * @param description The new description of the view. Null if to remain unchanged
    * @param structure   The new content of the view. Null if to remain unchanged
    * @throws ApplicationObjectNotFoundException If the view could not be found
    * @throws InvalidArgumentException If the new name (if applicable) is empty
    */
    public void updateOSPView(long viewId, String name, String description, byte[] structure) throws ApplicationObjectNotFoundException, InvalidArgumentException;

    /**
    * Deletes an existing OSP view
    * @param viewId The id of the view to be deleted
    * @throws ApplicationObjectNotFoundException If the view could not be found
    */
    public void deleteOSPView(long viewId) throws ApplicationObjectNotFoundException;
    //</editor-fold>
}