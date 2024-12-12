/*
 *  Copyright 2010-2019 Neotropic SAS <contact@neotropic.co>.
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
package com.neotropic.kuwaiba.sync.connectors.snmp.reference;

import com.neotropic.kuwaiba.sync.model.SyncFinding;
import com.neotropic.kuwaiba.sync.model.SyncResult;
import com.neotropic.kuwaiba.sync.model.SyncUtil;
import com.neotropic.kuwaiba.sync.model.TableData;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.kuwaiba.apis.persistence.PersistenceService;
import org.kuwaiba.apis.persistence.application.ApplicationEntityManager;
import org.kuwaiba.apis.persistence.application.Pool;
import org.kuwaiba.apis.persistence.business.BusinessEntityManager;
import org.kuwaiba.apis.persistence.business.BusinessObject;
import org.kuwaiba.apis.persistence.business.BusinessObjectLight;
import org.kuwaiba.apis.persistence.exceptions.ApplicationObjectNotFoundException;
import org.kuwaiba.apis.persistence.exceptions.ArraySizeMismatchException;
import org.kuwaiba.apis.persistence.exceptions.InvalidArgumentException;
import org.kuwaiba.apis.persistence.exceptions.MetadataObjectNotFoundException;
import org.kuwaiba.apis.persistence.exceptions.BusinessObjectNotFoundException;
import org.kuwaiba.apis.persistence.exceptions.NotAuthorizedException;
import org.kuwaiba.apis.persistence.exceptions.OperationNotPermittedException;
import org.kuwaiba.apis.persistence.metadata.AttributeMetadata;
import org.kuwaiba.apis.persistence.metadata.ClassMetadata;
import org.kuwaiba.apis.persistence.metadata.ClassMetadataLight;
import org.kuwaiba.apis.persistence.metadata.MetadataEntityManager;
import org.kuwaiba.beans.WebserviceBean;
import org.kuwaiba.exceptions.ServerSideException;
import org.kuwaiba.services.persistence.util.Constants;
import org.kuwaiba.services.persistence.util.Util;
import org.kuwaiba.util.i18n.I18N;
import org.openide.util.Exceptions;

/**
 * Loads data from a SNMP file to replace/update an existing element in the
 * inventory
 * @author Adrian Martinez Molina {@literal <adrian.martinez@kuwaiba.org>}
 */
public class EntPhysicalSynchronizer {
    /**
     * Device Data Source Configuration id
     */
    private final long dsConfigId;
    /**
     * To keep a trace of the list types evaluated, to not create them twice
     */
    private final List<String> listTypeEvaluated;
    /**
     * An aux variable, used to store the branch of the old object structure
     * while the objects are checked, before the creations of the branch
     */
    private HashMap<String, String> syncedInstances = new HashMap<>();
    /**
     * A list of the all the paths to sync, each path contains a list of instance(ids)
     * e g
     * [
     *  [0,1001,1002],
     *  [0,1001,1003],
     *  [0,1001,1004],
     *  ::
     * ] 
     * every path goes from the root/chassis until a last leaf(child with no children) of that path
     * there are as much paths as leafs
     */
    private List<List<String>> snmpBranches = new ArrayList<>();
    private ArrayList<ArrayList<BusinessObjectLight>> kuwaibaPathsLigth = new ArrayList<>();
    private List<List<BusinessObject>> kuwaibaPaths = new ArrayList<>();
    
    
    List<BusinessObjectLight> kuwaibaServices;
    
    
    private List<BusinessObjectLight> kuwaibaPorts = new ArrayList<>();
    /**
     * the result finding list
     */
    private List<SyncResult> results = new ArrayList<>();
    /**
     * The entity table loaded into the memory [mibName, [list of values]]
     * e.g
     * [
     * entPhysicalName => [1, WS-C2960C-8TC-L - Fixed Module 0, WS-C2960C-8TC-L - Power Supply 0, WS-C2960C-8TC-L - Sensor 0, FastEthernet0/1, FastEthernet0/2, ...]
     * instance => [1001, 1002, 1003, 1004, ....]
     * entPhysicalContainedIn => [0, 1001, 1001, 1001, 1002, 1002..]
     * ]
     */
    private final HashMap<String, List<String>> entityData;
    /**
     * The if-mib table loaded into the memory
     * e.g [

     */
    private final HashMap<String, List<String>> ifXTable;
    /**
     * Default initial ParentId in the SNMP table data
     */
    private String INITAL_ID;
    /**
     * reference to the bem
     */
    private BusinessEntityManager bem;
    /**
     * Reference to de aem
     */
    private ApplicationEntityManager aem;
    /**
     * Reference to de mem
     */
    private MetadataEntityManager mem;
    
    private BusinessObjectLight objSync;
    
    private final HashMap<String, List<BusinessObject>> newCreatedPortsToCreate;
    
    public EntPhysicalSynchronizer(long dsConfigId, BusinessObjectLight obj, List<TableData> data) {
        try {
            PersistenceService persistenceService = PersistenceService.getInstance();
            objSync = obj;
            bem = persistenceService.getBusinessEntityManager();
            aem = persistenceService.getApplicationEntityManager();
            mem = persistenceService.getMetadataEntityManager();
        } catch (IllegalStateException ex) {
            Logger.getLogger(WebserviceBean.class.getName()).log(Level.SEVERE,
                    ex.getClass().getSimpleName() + ": {0}", ex.getMessage()); //NOI18N
            bem = null;
            aem = null;
            mem = null;
        }
        this.dsConfigId = dsConfigId;
        entityData = (HashMap<String, List<String>>)data.get(0).getValue();
        ifXTable = (HashMap<String, List<String>>)data.get(1).getValue();
        listTypeEvaluated = new ArrayList<>();
        newCreatedPortsToCreate = new HashMap<>();      
        kuwaibaServices = new ArrayList<>();
    }

    public List<SyncResult> sync() throws MetadataObjectNotFoundException
            , BusinessObjectNotFoundException, InvalidArgumentException
            , OperationNotPermittedException, ApplicationObjectNotFoundException
            , ArraySizeMismatchException, NotAuthorizedException, ServerSideException 
    {
        readSnmpData();
        synchronizeHierarchy();
        
        readCurrentStructure(objSync
                , bem.getObjectChildren(objSync.getClassName()
                        , objSync.getId()
                        , -1)
                , new ArrayList<>());
        parseToBusinessObject();
        synchronize();
        syncIfMibData();
        return results;
    }

    //<editor-fold desc="Methods to read the data and load it into memory" defaultstate="collapsed">
    /**
     * Reads the snmp data loaded into memory
     * @throws InvalidArgumentException if the table info load is corrupted and
     * has no chassis
     */
    public void readSnmpData() throws InvalidArgumentException {
        //The initial id is the id of the chassis in most of cases is 0, except in the model SG500 
        INITAL_ID = entityData.get("entPhysicalContainedIn").get(entityData.get("entPhysicalClass").indexOf("3"));
        if (entityData.get("entPhysicalContainedIn").contains(INITAL_ID))
            createSnmpPathsMap(INITAL_ID, "");
        else 
            results.add(new SyncResult(dsConfigId, SyncFinding.EVENT_ERROR
                    , I18N.gm("no_inital_id_was_found")
                    , I18N.gm("check_initial_id_in_snmp_data")));
    }

    /**
     * Translates the snmp map (entityData) into a map with all possible paths 
     * to sync.  It reads the instance (the id) of each object and creates the 
     * children path searching that id in entPhysicalContainedIn this is the 
     * contency map, it shows who is the father of whom.
     * 
     * e.g. 
     * instance => 1001
     * entPhysicalContainedIn => 0
     * 
     * instance: 0 (the chassis/initial_id) is the parent of the instance: 1001 
     * 
     * Please refer to syncSourcePaths declaration to check the data result format
     * @param parentId the start/chassis/root of the device that is being sync
     * most of time is 0
     */
    private void createSnmpPathsMap(String parentId, String path){
        path += parentId + ", ";
        boolean hasChildren = false;
        for (int i = 0; i < entityData.get("entPhysicalContainedIn").size(); i++) {
            if (entityData.get("entPhysicalContainedIn").get(i).equals(parentId)) {
                 if (isClassUsed(entityData.get("entPhysicalClass").get(i),
                        entityData.get("entPhysicalDescr").get(i))) {
                    createSnmpPathsMap(entityData.get("instance").get(i), path);
                    hasChildren = true;
                }
            }
        }
        
        if(!hasChildren){
            snmpBranches .add(Arrays.asList(path.split(", ")));
            path = "";
        }
    }

    /**
     * Creates a kuwaiba's class hierarchy from the SNMP file
     * @param deviceModel the device model
     * @param className_ the given class name
     * @param name name of the element
     * @param descr description of the element
     * @return equivalent kuwaiba's class
     */
    public String parseClass(String deviceModel, String className_, String name, String descr) {
        if (className_.isEmpty())
            return null;
        
        int classId = Integer.valueOf(className_);
        if (classId == 3 && (!name.isEmpty() && !descr.isEmpty())) //chassis
            return objSync.getClassName();
        else if (deviceModel != null && classId == 10 && deviceModel.contains("2960")) 
            return "ElectricalPort";
        else if (classId == 10){ //port
            if (name.toLowerCase().contains("usb") || descr.toLowerCase().contains("usb")) //NOI18N
                return "USBPort";//NOI18N
            else if (name.toLowerCase().contains("fastethernet") || name.toLowerCase().contains("mgmteth") || 
                    name.toLowerCase().contains("cpu") || name.toLowerCase().contains("control") ||
                    (descr.toLowerCase().contains("ethernet") && !descr.toLowerCase().contains("gigabit")) ||
                    descr.toLowerCase().contains("fast") || descr.toLowerCase().contains("management")) //NOI18N
                return "ElectricalPort";//NOI18N
            else if(!name.isEmpty() && !descr.isEmpty())
                return "OpticalPort";//NOI18N
            
        } else if (classId == 5) { //container
            if (!descr.contains("Disk")) //NOI18N
                return "Slot";//NOI18N
            
        } else if (classId == 6 && name.toLowerCase().contains("power") && !name.toLowerCase().contains("module") || classId == 6 && descr.toLowerCase().contains("power") || classId == 6 && descr.toLowerCase().contains("psu")) 
            return "PowerPort";//NOI18N
        else if (classId == 6 && name.contains("Module")) //NOI18N
            return "HybridBoard"; //NOI18N
        else if (classId == 9) { //module
            //In Routers ASR9001 the Transceivers, some times have an empty desc
                if ((name.matches("[A-Za-z]+([0-9]+\\/)+[0-9]+") 
                        || name.toLowerCase().contains("transceiver") 
                        || descr.toLowerCase().contains("transceiver") 
                        || ((descr.toLowerCase().contains("sfp") && !name.toLowerCase().contains("card") && !name.matches("[A-Za-z]+([0-9]+\\/)+[0-9]+-[A-Za-z]+"))
                            || descr.toLowerCase().contains("xfp") 
                            || descr.toLowerCase().contains("cpak") || descr.toLowerCase().equals("ge t")
                            )) 
                        && !name.toLowerCase().contains("spa") && !descr.toLowerCase().contains("spa"))
                return "Transceiver"; //NOI18N
//0/RP0/CPU0-GigabitEthernet0/0/0/1
            return name.matches("[A-Za-z]+([0-9]+\\/)+[0-9]+-[A-Za-z]+") ? null : "IPBoard"; 
//ASR540 extra transceivers, we keep only the main transceiver with the 0/0/ 
        }
        else if(classId == 1 && descr.contains("switch processor"))
            return "SwitchProcessor"; //NOI18N
        
        return null;
    }

    /**
     * Returns if the class is used or not
     *
     * @param line the line of the SNMP to extract the className and the name of
     * the element
     * @return false if is a sensor, true in the most of the cases for now
     */
    private boolean isClassUsed(String classId_, String descr) {
        int classId = Integer.valueOf(classId_);
        //chassis(3) port(10) powerSupply(6) module(9) container(5) 
        switch(classId){
            case 3:
            case 10:
            case 6:
            case 9:
                return true;
            case 1:
                return descr.trim().toLowerCase().contains("switch processor");//NOI18N
            case 5:
                return !descr.trim().toLowerCase().contains("disk");//NOI18N
        }
        return false;
    }
    //</editor-fold>
   
    //recorremos la rama snmp y la mandamosa  mapear
    private List<BusinessObject> parseSnmpBranch(List<String> snmpBranch){
        List<BusinessObject> syncBranch = new ArrayList<>();
        //we translate each branch from snmp id instances into kuwaiba objects
        for(String snmpInstance : snmpBranch){
            if(!snmpInstance.equals(INITAL_ID)){
                BusinessObject syncObj = businessObjectFrom(snmpInstance);
                if(syncObj != null){
                    syncObj.getAttributes().put("sync", "to_sync");
                    syncBranch.add(syncObj);
                }
            }
        }
        return syncBranch;
    }
    
    /**
     * Creates an object with snmp instance data
     */
    private BusinessObject businessObjectFrom(String snpmInstance){
        int i = entityData.get("instance").indexOf(snpmInstance);
        String name = entityData.get("entPhysicalName").get(i); //NOI18N
        String kuwaibaClass = parseClass(
                    entityData.get("entPhysicalModelName").get(i),//NOI18N
                    entityData.get("entPhysicalClass").get(i), //NOI18N
                    name, entityData.get("entPhysicalDescr").get(i)); //NOI18N
        if(kuwaibaClass != null){ //TODO improve this, parse class and isclassUsed should avoid extra info like transceiver in 540    
            HashMap<String, String> attributes = snmpAttributes(kuwaibaClass, i);
            //with the class (from the id) we create a kuwaiba object
            return new BusinessObject(kuwaibaClass, 
                    snpmInstance, 
                    attributes.get(Constants.PROPERTY_NAME), 
                    attributes);
        }
        return null;
    }

    /**
     * Search in every branch by the port (it should be unique)
     * @return a branch with a port as leaf
     */
    private List<BusinessObject> findBranchByPort(BusinessObject syncChassis, BusinessObject syncEndPort){
        for (List<BusinessObject> kuwaibaBranch : kuwaibaPaths) {

            if(!kuwaibaBranch.isEmpty()){ 
                List<BusinessObjectLight> kuwBranch = new ArrayList<>(kuwaibaBranch);
                BusinessObjectLight chassis = kuwBranch.get(0);
                BusinessObjectLight kuwEndPort = kuwBranch.get(kuwBranch.size() -1);

                if(syncChassis.getClassName().equals(chassis.getClassName())
                        && syncEndPort.getClassName().contains("Port") 
                        && kuwEndPort.getClassName().contains("Port")
                        && syncEndPort.getClassName().equals(kuwEndPort.getClassName())
                        && syncEndPort.getName().equals(kuwEndPort.getName()))

                    return kuwaibaBranch;
            }//end if empty
        }
            
        return new ArrayList<>();
    }
    
    /**
     * Parse from business object light to business object
     */
    private void parseToBusinessObject(){
        try {
            for (List<BusinessObjectLight> pathLigth : kuwaibaPathsLigth) {
                List<BusinessObject> path = new ArrayList<>();
                for (BusinessObjectLight objLigth : pathLigth) {
                    BusinessObject obj = bem.getObject(objLigth.getClassName(), objLigth.getId());
                    path.add(obj);
                }
                kuwaibaPaths.add(path);
            }
        } catch (MetadataObjectNotFoundException | BusinessObjectNotFoundException | InvalidArgumentException ex) {
            Exceptions.printStackTrace(ex);
        }
    }
    
    /**
     * Updates containment hierarchy
     */
    private void synchronizeHierarchy(){
        HashMap<String, List<String>> syncHierarchy = new HashMap<>();

        for (List<String> snmpBranch : snmpBranches ) {
            List<BusinessObject> syncBranch = parseSnmpBranch(snmpBranch);

            for(int i = 0; syncBranch.size() > i; i ++){
                BusinessObject parent = syncBranch.remove(i);
                if(!syncHierarchy.containsKey(parent.getClassName()))
                    syncHierarchy.put(parent.getClassName(), new ArrayList());

                for (BusinessObject obj : syncBranch) {
                    if(!syncHierarchy.get(parent.getClassName()).contains(obj.getClassName()))
                        syncHierarchy.get(parent.getClassName()).add(obj.getClassName());
                }
            }
        }
        
        try {
            for (String parentClass : syncHierarchy.keySet()) {
            
                List<ClassMetadataLight> currentPossibleChildren = mem.getPossibleChildren(parentClass);
                List<String> syncPossibleChildren = syncHierarchy.get(parentClass);
                
                if (syncPossibleChildren != null) {
                    List<String> possibleChildrenToAdd = new ArrayList<>();
                    for (String syncPossibleChild : syncPossibleChildren){
                        boolean isPossibleChild = false;
                        for(ClassMetadataLight currentPossibleChild : currentPossibleChildren){
                            if(syncPossibleChild.equals(currentPossibleChild.getName())){
                                isPossibleChild = true;
                                break;
                            }
                        }
        
                        if(!isPossibleChild)
                            possibleChildrenToAdd.add(syncPossibleChild);
                    }
                    if(!possibleChildrenToAdd.isEmpty()){
                        mem.addPossibleChildren(parentClass, possibleChildrenToAdd.toArray(new String[possibleChildrenToAdd.size()]));
                        results.add(new SyncResult(dsConfigId
                                , SyncResult.TYPE_SUCCESS
                                , String.format("%s possible child of %s"
                                        , possibleChildrenToAdd
                                        , parentClass)
                                , "Contaiment Hierarchy updated"));
                    }
                }
            } //end for
        }catch (MetadataObjectNotFoundException | InvalidArgumentException ex) {
            results.add(new SyncResult(dsConfigId
                    , SyncResult.TYPE_ERROR
                    , ex.getLocalizedMessage()
                    , "Contaiment Hieararchy not updated"));
        }
        
    }
    
    /**
     * Starts the synchronization
     */
    private void synchronize(){
        boolean upateChassis = true;
        for (List<String> snmpBranch : snmpBranches ) {
            List<BusinessObject> syncBranch = parseSnmpBranch(snmpBranch);
            if(!syncBranch.isEmpty()){
                BusinessObject syncChassis = syncBranch.get(0);
                if(upateChassis 
                        && syncChassis.getClassName()
                                .equals(objSync.getClassName()))
                {
                    updateAttributes(syncChassis);
                    upateChassis = false;
                }
                //First (EMPTY ROUTER) if the router is empty only the chassis
                if(kuwaibaPaths.size() == 1 && kuwaibaPaths.get(0).size() == 1){
                    kuwaibaPaths.add(new ArrayList<>(syncBranch));
                    syncBranch.remove(0);
                    processBranch(syncBranch); 
                }
                else{
                    syncBranch.remove(0);
                    BusinessObject syncEndPort = null;
                    //Second, we search by port in sync(snmp branch)!, form now on everything is based on the synch branch having port
                    //we search that port in the current kuwaiba's branches and we found something 
                    //we search in the sync branch, THE PORT IS NOT ALWAYS THE BRANCH LEAF!!!!! e.g ASR90001
                    for (BusinessObject syncObj : syncBranch) {
                        if(syncObj.getClassName().contains("Port")){
                            syncEndPort = syncObj;
                            break;
                        }
                    }
                    boolean synced = false;
                    if(syncEndPort != null){ //There is a port to sync
//We search if there is a Kuwaiba's branch with that port
                        List<BusinessObject> kuwaibaBranchWithPort = findBranchByPort(syncChassis, syncEndPort);
                        
                        if(!kuwaibaBranchWithPort.isEmpty()){
                            syncBranchByPort(syncEndPort, syncBranch, kuwaibaBranchWithPort);
                            synced = true;
                        }
                    }
                    if(!synced)
                        syncBranch(syncChassis, syncBranch);

                    //end if search by port    
                    //else logic when sync has no port?
                }//end else 
            }
        }
        usyncBranches();
    }
    
    /**
     * Sync a found branch with a matched port
     * @param syncEndPort found port
     * @param syncBranch the whole sync branch
     * @param kuBranch  the current kuwaiba branch
     */
    private void syncBranchByPort(BusinessObject syncEndPort
            , List<BusinessObject> syncBranch
            , List<BusinessObject> kuBranch)
    {
        List<BusinessObject> branchToSync = new ArrayList<>();
        BusinessObjectLight kuwEndPort = null;
        
        List<BusinessObject> kuwaibaBranch = new ArrayList<>(kuBranch);
        if(!kuwaibaBranch.isEmpty())
            kuwaibaBranch.remove(0);
        
        for(int i = 0; i < syncBranch.size(); i++){
            BusinessObject sync = syncBranch.get(i);
            boolean noMatch = true;

            for (BusinessObjectLight kuwObj : kuwaibaBranch) {
                if(kuwObj.getClassName().contains("Port")){
                    kuwEndPort = kuwObj;
                    break;
                }
            }    
            if(kuwEndPort != null)
                syncEndPort.setId(kuwEndPort.getId());

            for(int j = i; j < kuwaibaBranch.size(); j++){
                BusinessObject kuw = kuwaibaBranch.get(j);

                if(sync.getClassName().equals(kuw.getClassName()) 
                    && kuw.getName().equals(sync.getName()))
                {
                    sync.setId(kuw.getId()); //don't forget to keep the id
                    if(kuw.getAttributes().containsKey("sync")
                            && kuw.getAttributes().get("sync").contains("synced-"))
                    {
                        sync.getAttributes().put("sync", "to_sync_found");
                        syncedInstances.put(sync.getAttributes().get("syncSnmpInstance"), kuw.getId());
                    }
                    noMatch = false; //si al almenos una coincidencia paila
                    branchToSync.add(sync);
                    break; //se econtró, siguiente en el branch sync
                }
                else{
                    sync.getAttributes().put("sync", "not_sync");
                    branchToSync.add(kuw); 
                }
            }//end for kuwaiba
// if we achive the end of the kuwaiba's branch and there is no more coincidences, we add everything let over from sync to be created(synced)
            if(noMatch && !kuwaibaBranch.contains(sync)){
                sync.getAttributes().put("sync", "to_sync_not_found");
                branchToSync.add(sync);
            }
        }
        processBranch(branchToSync);
    }

    /**
     * We didn't found a branch created in kuwaiba with a searched port, so we search in EVERY branch of kuwaiba 
     * @param syncChassis
     * @param syncBranch 
     */
    private void syncBranch(BusinessObject syncChassis, List<BusinessObject> syncBranch)
    {
        int found = 0;
        for (List<BusinessObject> kuwaibaBranch : kuwaibaPaths) {
            if(!kuwaibaBranch.isEmpty()){ 
                List<BusinessObject> kuwBranch = new ArrayList<>(kuwaibaBranch);
                //buscamos el puerto  ¿pa'qué? aún no sé
                if(syncChassis.getClassName().equals(kuwBranch.remove(0).getClassName())){
                    for(int i = found; i < syncBranch.size(); i++){
                        BusinessObject sync = syncBranch.get(i);
                        
                        for(int j = 0; j < kuwBranch.size(); j++){
                            BusinessObject kuw = kuwBranch.get(j);
                            if(sync.getClassName().equals(kuw.getClassName()) 
                                    && kuw.getName().equals(sync.getName()))
                            {
                                sync.setId(kuw.getId()); //OJO!! ! guardamos el id
                                if(kuw.getAttributes().containsKey("sync")
                                        && kuw.getAttributes().get("sync").contains("synced-"))
                                {
                                    sync.getAttributes().put("sync", "to_sync_found");
                                    syncedInstances.put(sync.getAttributes().get("syncSnmpInstance"), kuw.getId());
                                }
                            //If is a port without coincidence but the left over of the branch had a coincidence 
                            //we keep it because aybe in other branch could exists.
                                found++;
                                break;
                            } 
                        }//end for one branch
                    }
                }
            }//end if empty branch
        }//end for branchs
        
        
        processBranch(syncBranch);
        
        syncBranch.add(0, syncChassis);
        kuwaibaPaths.add(syncBranch);
        
    }
    
    /**
     * we check the branch to marked some elements for deletion using the 
     * attribute 
     */
    private void usyncBranches(){
        for (List<BusinessObject> kuwaibaBranch : kuwaibaPaths) {
            
            List<BusinessObject> branchToSync = new ArrayList<>();
            
            if(!kuwaibaBranch.isEmpty()){ 
                List<BusinessObject> kuwBranch = new ArrayList<>(kuwaibaBranch);
                BusinessObject chassis = kuwBranch.remove(0);
                
                for (List<String> snmpBranch : snmpBranches ) {
                    //Parse from snmp to kuwaiba
                    List<BusinessObject> syncBranch = parseSnmpBranch(snmpBranch);
                    if(!syncBranch.isEmpty() 
                            && syncBranch.remove(0).getClassName()
                                    .equals(chassis.getClassName()))
                    {
                        for(int i = 0; i < syncBranch.size(); i++){
                            BusinessObject sync = syncBranch.get(i);
                            for(int j = i; j < kuwBranch.size(); j++){
                                BusinessObject kuw = kuwBranch.get(j);
                                if(sync.getClassName().equals(kuw.getClassName()) 
                                        && kuw.getName().equals(sync.getName()))
                                    break;
                                else{
                                    sync.getAttributes().put("sync", "not_sync");
                                    if(!branchToSync.contains(kuw))
                                        branchToSync.add(kuw);
                                }
                            }//end for one branch
                        }
                    }
                }//end for sync branch
            }
            deleteBranch(branchToSync);
        }
    }
    
    /**
     * We process the branch and create the object in the branch
     * using the attribute sync to know if it should be created, updated, or do nothing
     * @param syncBranch the branch to sync
     */
    private void processBranch (List<BusinessObject> syncBranch){
        
        BusinessObjectLight parent;
        
        for (int i = 0; i < syncBranch.size(); i++) {
            BusinessObject sync = syncBranch.get(i);
            boolean createObj = true;
            try{
                if(sync.getAttributes().containsKey("sync")
                    && sync.getAttributes().get("sync").contains("to_sync"))
                {
                    ListIterator<BusinessObject> it = syncBranch.listIterator(i);
                    parent = it.hasPrevious() ? it.previous() : null;

                    if(syncedInstances.containsKey(sync.getAttributes().get("syncSnmpInstance"))
                            && sync.getAttributes().get("sync").contains("to_sync_found"))
                    {
                        updateAttributes(sync);
                    } else{ 
                        if((sync.getClassName().contains("Port") 
                                && sync.getAttributes().get("sync").contains("to_sync_found")) 
                                || sync.getAttributes().get("sync").contains("to_sync_found"))
                            createObj = false;

                        if(createObj){//create object
                            sync.getAttributes().put("sync", "synced-" + new Date().getTime());   
                            String newObjId = bem.createObject(sync.getClassName()
                                    , parent == null ? objSync.getClassName() : parent.getClassName()
                                    , parent == null ? objSync.getId() : parent.getId()
                                    , sync.getAttributes()
                                    , null);

                            results.add(new SyncResult(dsConfigId
                                    , SyncResult.TYPE_SUCCESS
                                    , String.format("%s [%s] created in -> %s [%s]"
                                            , sync.getName()
                                            , sync.getClassName() 
                                            , parent == null ? objSync.getName() : parent.getName()
                                            , parent == null ? objSync.getClassName() : parent.getClassName())
                                    , "Object created"));

                            //we keep record of sync objects to not create them again, a map of snmp instance  = obj id
                            syncedInstances.put(sync.getAttributes().get("syncSnmpInstance"), newObjId);
                            sync.setId(newObjId);
                        }
                        //if the child is a port already created we need to move it under the new created parent
                        //aquíe fijate si toca mober esta maricada no solo pal puerto
                        //y el padre no es kuwiaba pa borrar
                        else if(sync.getClassName().contains("Port") 
                                && sync.getAttributes().get("sync").contains("to_sync_found"))
                        {
                            HashMap<String, String[]> objToMove = new HashMap<>();
                            objToMove.put(sync.getClassName(), new String[]{sync.getId()});
                            bem.moveObjects(parent == null ? objSync.getClassName() : parent.getClassName()
                                    , parent == null ? objSync.getId() : parent.getId()
                                    , objToMove);
                            //update attribute sync
                            results.add(new SyncResult(dsConfigId
                                    , SyncResult.TYPE_SUCCESS
                                    , String.format("%s [%s]"
                                            , sync.getName()
                                            , sync.getClassName())
                                    , "Object moved"));
                        }
                    }
                }
            } catch (MetadataObjectNotFoundException
                    | BusinessObjectNotFoundException 
                    | InvalidArgumentException 
                    | OperationNotPermittedException 
                    | ApplicationObjectNotFoundException ex)
            {
                results.add(new SyncResult(dsConfigId, SyncResult.TYPE_ERROR
                        , ex.getLocalizedMessage()
                        , String.format("%s [%s] not created",
                                sync.getName(),
                                sync.getClassName())));
            }
        }
        deleteBranch(syncBranch);
    }
    
    /**
     * Deletes the objects marked for deletion 
     */
    private void deleteBranch(List<BusinessObject> branch){
        List<BusinessObject> branchToDelete = new ArrayList<>(branch);
        Collections.reverse(branchToDelete); //we reverse because if we delete the father first, all its children will be deleted at same time.
        branchToDelete.forEach(kuwObj -> {
            try {
                if(kuwObj.getAttributes().get("sync") == null 
                        || kuwObj.getAttributes().get("sync").contains("not_sync"))
                {
                    bem.deleteObject(kuwObj.getClassName(), kuwObj.getId(), false);
                    results.add(new SyncResult(dsConfigId, SyncResult.TYPE_SUCCESS, 
                            String.format("%s [%s]"
                                    , kuwObj.getName()
                                    , kuwObj.getClassName())
                            , "Deleted"));
                }
            } catch (InvalidArgumentException 
                    | BusinessObjectNotFoundException 
                    | MetadataObjectNotFoundException 
                    | OperationNotPermittedException ex) 
            {
                results.add(new SyncResult(dsConfigId
                        , SyncResult.TYPE_ERROR
                        , ex.getLocalizedMessage()
                        , String.format("%s [%s] not deleted"
                                , kuwObj.getName()
                                , kuwObj.getClassName())));
            }
        });
    }
   
    /**
     * Updates/crates the attributes, also add a sync attribute, so kuwaiba 
     * will not update again the attributes again if no more than 5 minutes have passed 
     * @param sync object
     */
    private void updateAttributes(BusinessObject sync)    {
        HashMap<String, String> newAttributes = new HashMap<>();

        String kuwaibaId = syncedInstances.get(sync.getAttributes().get("syncSnmpInstance"));
        HashMap<String, String> attributes = new HashMap<>();

        if(kuwaibaId != null){
            for(List<BusinessObject> kuwibaBranch : kuwaibaPaths){
                for (BusinessObject obj : kuwibaBranch){
                    if(obj.getId().equals(kuwaibaId)){
                        attributes = obj.getAttributes();
                        break;
                    }
                }
            }
        }
        //syncronizamos si han pasado mas de 5 minutos
        long minutes = 0; 
        if(attributes.containsKey("sync") 
                && attributes.get("sync").contains("synced-"))
        {
            String[] syncAttr = attributes.get("sync").split("-");

            if(syncAttr.length == 2){
                Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis(Long.valueOf(syncAttr[1]));

                long diff = new Date().getTime() - cal.getTimeInMillis();
                minutes = TimeUnit.MILLISECONDS.toMinutes(diff);    
            }
        }
            
        if(minutes > 3){
            
            for(String syncAttrName : sync.getAttributes().keySet()) {
                String syncValue = sync.getAttributes().get(syncAttrName);
                if (!attributes.containsKey(syncAttrName))
                    newAttributes.put(syncAttrName, syncValue);
                else{
                    String value = attributes.get(syncAttrName);
                    if (value != null && syncValue != null 
                            && !value.equals(syncValue)) 
                        newAttributes.put(syncAttrName, value);
                }
            }
            
            try{
                newAttributes.put("sync", String.format("synced-%s", new Date().getTime())); 

                if(!newAttributes.isEmpty()){
                    bem.updateObject(sync.getClassName(), sync.getId(), newAttributes);

                    results.add(new SyncResult(dsConfigId
                            , SyncResult.TYPE_SUCCESS
                            , String.format("%s: %s"
                                   , sync.toString()
                                   , newAttributes.toString())
                            , "Attributes updated"));
                }
            } catch (MetadataObjectNotFoundException 
                    | BusinessObjectNotFoundException 
                    | InvalidArgumentException 
                    | OperationNotPermittedException ex) 
            {
                results.add(new SyncResult(dsConfigId
                        , SyncResult.TYPE_ERROR
                        , "Attributes not updated"
                        , ex.getLocalizedMessage()));
            }
        }
    }
    
    /**
     * Read all the transceivers in a router, (used to double check interface 
     * creation in some routers like 540)
     * @param children router first level children
     * @return list of all transceivers in device
     */
    private List<BusinessObject> readTransceiver (List<BusinessObjectLight> children)
    {
        List<BusinessObject> interfaces = new ArrayList<>();
        try {    
            for (BusinessObjectLight child : children) {
                if (child.getClassName().equals(Constants.CLASS_TRANSCEIVER))
                    interfaces.add(bem.getObject(child.getClassName(), child.getId()));
                
                interfaces.addAll(readTransceiver(bem.getObjectChildren(child.getClassName(), child.getId(), -1)));
            }
        } catch (MetadataObjectNotFoundException 
                | BusinessObjectNotFoundException 
                | InvalidArgumentException ex)
        {
            results.add(new SyncResult(dsConfigId, SyncResult.TYPE_ERROR
                        , ex.getMessage()
                        , "Structure not read"));
        }
        return interfaces;
    }
    
    /**
     * All the interfaces in a device 
     * @param children first level of the device
     * @return all the existing interfaces in device
     */
    private List<BusinessObject> readInterfaces (List<BusinessObjectLight> children)
    {
        List<BusinessObject> interfaces = new ArrayList<>();
        try {    
            for (BusinessObjectLight child : children) {
                if ((child.getClassName().equals(Constants.CLASS_MPLSTUNNEL)
                        || mem.isSubclassOf("GenericCommunicationsPort", child.getClassName())
                        || mem.isSubclassOf("GenericLogicalPort", child.getClassName())
                        || mem.isSubclassOf("Pseudowire", child.getClassName())
                        || child.getClassName().equals(Constants.CLASS_VIRTUALPORT)
                        || child.getClassName().equals("ServiceInstance")
                        || child.getClassName().contains("Channel"))
                        && !child.getClassName().contains("PowerPort"))
                {
                    interfaces.add(bem.getObject(child.getClassName(), child.getId()));
                }
                interfaces.addAll(readInterfaces(bem.getObjectSpecialChildren(child.getClassName(), child.getId())));
                interfaces.addAll(readInterfaces(bem.getObjectChildren(child.getClassName(), child.getId(), -1)));
            }
        } catch (MetadataObjectNotFoundException 
                | BusinessObjectNotFoundException 
                | InvalidArgumentException ex)
        {
            results.add(new SyncResult(dsConfigId, SyncResult.TYPE_ERROR
                        , ex.getMessage()
                        , "Structure not read"));
        }
        return interfaces;
    }
    
    /** 
     * Reads the current device/object structure from kuwaiba and make list of 
     * all its branches, and creates a branch from device/chassis until every 
     * leaf(device child with no children), every branch is a list of kuwaiba's 
     * objects there will be as much branches as leafs
     */
    private boolean readCurrentStructure(BusinessObjectLight parent, 
            List<BusinessObjectLight> children
            , ArrayList<BusinessObjectLight> path)
    {
        path.add(parent);
        
        if(parent.getClassName().contains("Ports"))
            kuwaibaPorts.add(parent);
        
        boolean removeIdFromBranch = false;
        if(!children.isEmpty()){
            for(BusinessObjectLight child : children)   
                try {
                    removeIdFromBranch = readCurrentStructure(child
                            , bem.getObjectChildren(child.getClassName()
                                    , child.getId()
                                    , -1)
                            , path);
                } catch (MetadataObjectNotFoundException | BusinessObjectNotFoundException | InvalidArgumentException ex) {
                    Exceptions.printStackTrace(ex);
                }
            if(removeIdFromBranch)
                path.remove(parent);
        }
        else{
            kuwaibaPathsLigth.add(new ArrayList<>(path));
            path.remove(parent);
            return true;
        }
            
        return false;
    }
    
    /**
     * Create a hash map for the attributes of the given index of the data read it from SNMP
     * @param index the index of the instance entry from snmp data
     * @return a HashMap of attributes key:attribute name, value: attribute value 
     * @throws MetadataObjectNotFoundException if the attributes we are trying to sync doesn't exists in the class-metadata
     * @throws InvalidArgumentException
     * @throws OperationNotPermittedException 
     */
    private HashMap<String, String> snmpAttributes(String syncClass, int index){
        HashMap<String, String> attributes = new HashMap<>();
        try {
            ClassMetadata mappedClass = mem.getClass(syncClass);

            String syncName = entityData.get("entPhysicalName").get(index);//NOI18N
            
//We standarized the port names
            if(!objSync.getClassName().equals(syncClass) && 
                    SyncUtil.isSynchronizable(syncName) && 
                    syncClass.toLowerCase().contains("port") && 
                    !syncName.contains("Power") && 
                    !syncClass.contains("Power"))
            //TODO improve this    
                syncName = SyncUtil.normalizePortName(syncName);

            attributes.put("syncSnmpName", entityData.get("entPhysicalName").get(index));
            attributes.put("syncSnmpInstance", Integer.toString(index));//NOI18N
            attributes.put("sync", "none");//NOI18N
            attributes.put("name", syncName);//NOI18N
            
            String syncDescription = entityData.get("entPhysicalDescr").get(index).trim();
            if(!syncDescription.isEmpty() 
                    && mappedClass.getAttribute("description") != null)
            {
                attributes.put("description", syncDescription);
            }
            if (!entityData.get("entPhysicalMfgName").get(index).isEmpty() 
                    && mappedClass.getAttribute("vendor") != null){
                
                String syncVendor = findingListTypeId(index
                        , syncName, syncClass
                        , "EquipmentVendor");//NOI18N
                if (syncVendor != null) 
                    attributes.put("vendor", syncVendor); //NOI18N
                
            }
            if (!entityData.get("entPhysicalSerialNum").get(index).isEmpty()){
                if(mappedClass.getAttribute("serialNumber") == null)
                    System.out.println("crear atributo serial en class");

                attributes.put("serialNumber", entityData.get("entPhysicalSerialNum").get(index).trim());//NOI18N
            }
            if (!entityData.get("entPhysicalModelName").get(index).isEmpty()
                    && syncClass != null)
            {
                AttributeMetadata modelAttribute = mappedClass.getAttribute("model");
                if(modelAttribute != null){
                    String syncModel = findingListTypeId(index
                            , syncName, syncClass
                            , modelAttribute.getType());//NOI18N
                    if(syncModel != null)
                        attributes.put("model", syncModel);//NOI18N
                }
                else{
                    results.add(new SyncResult(dsConfigId, SyncResult.TYPE_INFORMATION
                        , String.format("Create attribute model in %s [%s]"
                                , syncName, syncClass)
                        , "Attribute required"));
                }
            }
        } catch (MetadataObjectNotFoundException ex) {
            results.add(new SyncResult(dsConfigId, SyncResult.TYPE_ERROR
                        , ex.getMessage()
                        , "Attributes not updated"));
        }
        
        return attributes;
    }
        
    //<editor-fold desc="List Types" defaultstate="collapsed">
    private String findingListTypeId(int i, String syncName, String syncClass
            , String listType)
    {
        String SNMPoid, listTypeId = null; 

        if(listType.equals("EquipmentVendor")) //NOI18N
            SNMPoid = "entPhysicalMfgName"; //NOI18N
        else{
            SNMPoid = "entPhysicalModelName"; //NOI18N
        }
        if (!entityData.get(SNMPoid).get(i).isEmpty()) {
            try{
                String snmpListType = entityData.get(SNMPoid).get(i);
                String id_ = matchListTypeNames(snmpListType, listType);
                if (id_ != null)
                    listTypeId = id_;
                                
                else {//The list type doesn't exist, we create a finding
                    if (!listTypeEvaluated.contains(snmpListType)) {
                        
                        String createListTypeItem = aem.createListTypeItem(listType
                                , snmpListType.trim()
                                , snmpListType.trim());
                        
                        results.add(new SyncResult(dsConfigId, SyncResult.TYPE_SUCCESS
                                , String.format("for: %s [%s], model updated to: %s"
                                        , syncName, syncClass
                                        , snmpListType)
                                , String.format("Model added as list type under %s"
                                        , snmpListType)));
                        listTypeEvaluated.add(snmpListType);
                        return createListTypeItem;
                    }
                }
            } catch (MetadataObjectNotFoundException | InvalidArgumentException | OperationNotPermittedException ex) {
                results.add(new SyncResult(dsConfigId, SyncResult.TYPE_ERROR
                        , String.format("%s", ex.getMessage())
                        , String.format("%s [%s] model not update", syncName
                                , syncClass)));
            }    
        }
        return listTypeId;
    }

    /**
     * Compare the names from the SNMP in order to find one that match with a
     * created list types in Kuwaiba
     * @param listTypeNameToLoad the list type name
     * @return the kuwaiba's list type item id
     * @throws MetadataObjectNotFoundException if the list type doesn't exists
     * @throws InvalidArgumentException if the class name provided is not a list type
     */
    private String matchListTypeNames(String listTypeNameToLoad, String listTypeClassName) 
            throws MetadataObjectNotFoundException, InvalidArgumentException 
    {
        List<BusinessObjectLight> listTypeItems = aem.getListTypeItems(listTypeClassName);
        List<String> onlyNameListtypes = new ArrayList<>();
        listTypeItems.forEach(createdLitType -> 
            onlyNameListtypes.add(createdLitType.getName())
        );
        
        if(onlyNameListtypes.contains(listTypeNameToLoad))
            return listTypeItems.get(onlyNameListtypes.indexOf(listTypeNameToLoad)).getId();
        
        for (BusinessObjectLight createdLitType : listTypeItems) {
            int matches = 0;
            int maxLength = listTypeNameToLoad.length() > createdLitType.getName().length() ? listTypeNameToLoad.length() : createdLitType.getName().length();
            listTypeNameToLoad = listTypeNameToLoad.toLowerCase().trim();
            String nameCreatedInKuwaiba = createdLitType.getName().toLowerCase().trim();
            if (listTypeNameToLoad.equals(nameCreatedInKuwaiba)) {
                return createdLitType.getId();
            }
            for (int i = 1; i < maxLength; i++) {
                String a, b;
                if (listTypeNameToLoad.length() < i)
                    break;
                else 
                    a = listTypeNameToLoad.substring(i - 1, i);
                if (nameCreatedInKuwaiba.length() < i)
                    break;
                else 
                    b = nameCreatedInKuwaiba.substring(i - 1, i);
                if (a.equals(b)) 
                    matches++;
            }
            if (matches == listTypeNameToLoad.length()) 
                return createdLitType.getId();
        }
        return null;
    }
    //</editor-fold>

    /**
     * Sync for interfaces based on if mib snmp
     */
    private void syncIfMibData(){
        results.add(new SyncResult(dsConfigId, SyncResult.TYPE_INFORMATION
                , "-- intefaces --"
                , "--"));
        
        List<String> services = ifXTable.get("ifAlias"); //NOI18N
        List<String> portNames = ifXTable.get("ifName"); //NOI18N
        List<String> portSpeeds = ifXTable.get("ifHighSpeed"); //NOI18N
        
        List<BusinessObject> interfaces = readInterfaces(new ArrayList<>(Arrays.asList(objSync)));
        List<BusinessObject> currentTransceivers = readTransceiver(new ArrayList<>(Arrays.asList(objSync)));
                
        for(String ifName : portNames){//ifName is the individual port name
            String ifAlias = services.get(portNames.indexOf(ifName)); //service name
            String portSpeed = portSpeeds.get(portNames.indexOf(ifName));
            
            HashMap<String, String> attributes = new HashMap<>();
            attributes.put(Constants.PROPERTY_NAME, SyncUtil.normalizePortName(ifName));
            attributes.put("syncSnmpIfName", ifName);
            attributes.put("syncSnmpHighSpeed", portSpeed);  //NOI18N
            attributes.put("sync", "synced-" + new Date().getTime()); 
            
            if(!ifAlias.isEmpty())
                attributes.put("syncSnmpIfAlias", ifAlias);
            
            String createdId; 
            try{
                //We must create the Mngmnt Port, virtualPorts, tunnels and Loopbacks
                if(SyncUtil.isSynchronizable(ifName)){
                    String logicalInterfaceClass = null;
                    //First we search the interfaces in the current structure
                    //We must add the s when we look for po ports because posx/x/x ports has no s in the if mib
                    BusinessObjectLight currentInterface = searchInterfaceBySnmpName(interfaces, ifName);
                    BusinessObjectLight currentLogicalInterface = null;
                  
                    //Is a virtual port
                    if (ifName.contains(".") || ifName.toLowerCase().contains(".si.")){
                        currentInterface = searchInterfaceBySnmpName(interfaces
                                , ifName.split("\\.")[0]);
                        
                        currentLogicalInterface = searchInterfaceBySnmpName(interfaces
                                , ifName);

                        if (currentInterface != null && currentLogicalInterface == null) {

                            List<BusinessObjectLight> virtualPortsChildren = bem.getObjectChildren(currentInterface.getClassName(), currentInterface.getId(), -1);

                            for (BusinessObjectLight virtualChild : virtualPortsChildren) {
                                if (virtualChild.getName().equals(SyncUtil.normalizePortName(ifName.split("\\.")[1]))) {
                                    currentLogicalInterface = virtualChild;
                                    break;
                                }
                            }
                        }
                        if(currentInterface != null && currentLogicalInterface == null){
                            if (ifName.toLowerCase().contains(".si")) {
                                attributes.put(Constants.PROPERTY_NAME,
                                         ifName.split("\\.")[2]);
                                logicalInterfaceClass = Constants.CLASS_SERVICE_INSTANCE;
                            } else if (ifName.toLowerCase().contains(".")) {
                                attributes.put(Constants.PROPERTY_NAME,
                                         ifName.split("\\.")[1]);
                                logicalInterfaceClass = Constants.CLASS_VIRTUALPORT;
                            }

                            createdId = createInterface(currentInterface.getId(),
                                     currentInterface.getClassName(),
                                     logicalInterfaceClass,
                                     attributes);

                            BusinessObject newObj = new BusinessObject(logicalInterfaceClass,
                                     createdId,
                                     attributes.get(Constants.PROPERTY_NAME));
                            newObj.setAttributes(attributes);
                            interfaces.add(newObj);

                            results.add(new SyncResult(dsConfigId
                                , SyncResult.TYPE_SUCCESS
                                , String.format("%s [%s]"
                                            , attributes.get(Constants.PROPERTY_NAME)
                                            , logicalInterfaceClass)
                                , "Inventory object created"));
                            
                            relateService(ifAlias
                                    , ifName
                                    , createdId
                                    , logicalInterfaceClass);
                        }
                    }
                    
                    //we create from scratch
                    if(currentInterface == null 
                            && (ifName.toLowerCase().equals("gi0") 
                                || ifName.toLowerCase().startsWith("Po") 
                                || ifName.toLowerCase().contains("se")))
                    {
                        String interfaceClass = null;
                        if(ifName.toLowerCase().equals("gi0"))
                            interfaceClass = Constants.CLASS_ELECTRICALPORT; 
                        else if(ifName.startsWith("Po") && ifName.length() < 4) //port channel
                            interfaceClass = "PortChannel";
                        else if (ifName.toLowerCase().contains("se"))
                            interfaceClass = Constants.CLASS_SERIALPORT;
                        
                        if (interfaceClass != null){   
                            createdId = createInterface(objSync.getId()
                                    , objSync.getClassName()
                                    , interfaceClass, attributes);
                            
                            relateService(ifAlias, ifName, createdId
                                , interfaceClass);
                              
                            BusinessObject newObj = new BusinessObject(logicalInterfaceClass,
                                     createdId,
                                     attributes.get(Constants.PROPERTY_NAME));
                            newObj.setAttributes(attributes);
                            interfaces.add(newObj);
                            results.add(new SyncResult(dsConfigId
                                , SyncResult.TYPE_SUCCESS
                                , String.format("%s [%s]"
                                            , attributes.get(Constants.PROPERTY_NAME)
                                            , interfaceClass)
                                , "Inventory object created"));
                        }
                    }
//logical interfaces special children of the device
                    if(currentInterface == null  
                            && (ifName.toLowerCase().contains("tu") 
                            || ifName.toLowerCase().contains("lo"))){
                        currentLogicalInterface = searchInterfaceBySnmpName(interfaces, ifName);
                        
                        //MPLSTunnel
                        if(ifName.toLowerCase().contains("tu"))
                            logicalInterfaceClass = Constants.CLASS_MPLSTUNNEL;
                        //LoopBacks
                        else if(ifName.toLowerCase().contains("lo"))
                            logicalInterfaceClass = Constants.CLASS_VIRTUALPORT;
                        
                        if(logicalInterfaceClass != null 
                                && currentLogicalInterface == null){

                            createdId = bem.createSpecialObject(logicalInterfaceClass
                                    , objSync.getClassName()
                                    , objSync.getId(), attributes, null);
                            BusinessObject newObj = new BusinessObject(logicalInterfaceClass, createdId, ifName);
                            newObj.setAttributes(attributes);
                            interfaces.add(newObj);
                            
                            results.add(new SyncResult(dsConfigId
                                    , SyncResult.TYPE_SUCCESS
                                    , String.format("%s [%s]"
                                                , attributes.get(Constants.PROPERTY_NAME)
                                                , logicalInterfaceClass)
                                    , "Inventory object created"));
                            
                            relateService(ifAlias, ifName, createdId, logicalInterfaceClass);
                        }
                    }                   
                    //we Update attributes, for now only high speed
                    if(currentInterface != null ){                         
                        
                        HashMap<String, String> currentAttributes = bem.getObject(currentInterface.getClassName(), currentInterface.getId()).getAttributes();
                        attributes = new HashMap<>();
                        attributes.put("sync", "synced-" + new Date().getTime()); 
                        //We must update the speedPort
                        String currentHighSpeed = currentAttributes.get("highSpeed");
                        if(currentHighSpeed == null || !currentHighSpeed.equals(portSpeed)){
                            attributes.put("highSpeed", portSpeed);
                            results.add(new SyncResult(dsConfigId, SyncResult.TYPE_SUCCESS,  
                                String.format("The attribute highSpeed was updated from: %s to %s", currentHighSpeed, portSpeed),    
                                String.format("highSpeed updated in interface: %s", currentInterface.getName())));
                        }//We must check if the ifAlias must be updated
                        String currentiIfAlias = currentAttributes.get("ifAlias");
                        if(!ifAlias.isEmpty() && (currentiIfAlias == null || !currentiIfAlias.equals(ifAlias))){
                            attributes.put("ifAlias", ifAlias);
                            results.add(new SyncResult(dsConfigId, SyncResult.TYPE_SUCCESS,  
                                String.format("The attribute ifAlias was updated from: %s to %s", currentiIfAlias, ifAlias),    
                                String.format("ifAlias updated in interface: %s", currentInterface.getName())));
                        }
                        bem.updateObject(currentInterface.getClassName(), currentInterface.getId(), attributes);
                        relateService(ifAlias, ifName, currentInterface.getId(), currentInterface.getClassName());
                        //we update the name for taking only the numeric part
                        //for virtual ports
                        if(currentLogicalInterface != null && ifName.contains(".") && currentLogicalInterface.getName().contains(".")){
                            attributes.put(Constants.PROPERTY_NAME, SyncUtil.normalizePortName(ifName)); 
                            bem.updateObject(currentLogicalInterface.getClassName(), currentLogicalInterface.getId(), attributes);
                            results.add(new SyncResult(dsConfigId, SyncResult.TYPE_SUCCESS,  
                                    String.format("The attribute name was updated from: %s to %s", currentAttributes.get(Constants.PROPERTY_NAME), attributes.get(Constants.PROPERTY_NAME)),    
                                    String.format("name updated in interface: %s", currentLogicalInterface)));
                        }//a service instance
                    }   
                    
                    if(currentInterface == null && currentLogicalInterface == null){
                        BusinessObject transceiverParent = searchTransceiverBySnmpName(currentTransceivers, ifName);
                        
                        if(transceiverParent != null){
                            createdId = createInterface(transceiverParent.getId()
                                    , transceiverParent.getClassName()
                                    , Constants.CLASS_OPTICALPORT, attributes);
                            
                            relateService(ifAlias, ifName, createdId
                                , Constants.CLASS_OPTICALPORT);
                              
                            BusinessObject newObj = new BusinessObject(Constants.CLASS_OPTICALPORT,
                                     createdId,
                                     attributes.get(Constants.PROPERTY_NAME));
                            newObj.setAttributes(attributes);
                            interfaces.add(newObj);
                            results.add(new SyncResult(dsConfigId
                                , SyncResult.TYPE_SUCCESS
                                , String.format("%s [%s]"
                                            , attributes.get(Constants.PROPERTY_NAME)
                                            , Constants.CLASS_OPTICALPORT)
                                , "Inventory object created"));
                        }
                    }
                }//end for ifNames
            } catch (MetadataObjectNotFoundException 
                    | BusinessObjectNotFoundException 
                    | OperationNotPermittedException 
                    | InvalidArgumentException 
                    | ApplicationObjectNotFoundException ex) 
            {
                results.add(new SyncResult(dsConfigId, SyncResult.TYPE_ERROR, 
                    String.format("Creating interface %s", ifName),
                    ex.getLocalizedMessage()));
            }
        }//the not found things
    }
    
    private String createInterface(String parentId
            , String parentClass, String className
            , HashMap<String, String >attributes)
    {
        try{
            attributes.put("sync", "synced-" + new Date().getTime()); 
            String id = bem.createObject(className, parentClass, parentId
                    , attributes, null);
            results.add(new SyncResult(dsConfigId
                    , SyncResult.TYPE_SUCCESS
                    ,  String.format("Interface %s [%s] created"
                            , attributes.get(Constants.PROPERTY_NAME)
                            , Constants.CLASS_ELECTRICALPORT)
                    ,   "New interface created"));
            return id;
        } catch (MetadataObjectNotFoundException 
                | BusinessObjectNotFoundException 
                | OperationNotPermittedException 
                | InvalidArgumentException 
                | ApplicationObjectNotFoundException ex) 
        {
            results.add(new SyncResult(dsConfigId
                    , SyncResult.TYPE_ERROR
                    , ex.getLocalizedMessage()
                    , String.format("Interface no created %s"
                            , attributes.get("name"))));
        }
        return "";
    }
    
    private void kuwaibaServices(){
        try{
            List<Pool> serviceRoot = bem.getRootPools(Constants.CLASS_GENERICCUSTOMER, 2, false);
            for(Pool customerPool: serviceRoot){
                //TelecoOperators
                List<BusinessObjectLight> poolItems = bem.getPoolItems(customerPool.getId(), -1);
                for(BusinessObjectLight telecoOperator : poolItems){
                    List<Pool> poolsInObject = bem.getPoolsInObject(telecoOperator.getClassName(), telecoOperator.getId(), "GenericService");
                    //Service Pool
                    for(Pool servicePool : poolsInObject){
                        List<BusinessObjectLight> actualServices = bem.getPoolItems(servicePool.getId(), -1);
                        actualServices.forEach((actualService) -> {
                            kuwaibaServices.add(actualService);
                        });
                    }
                }
            }
        }catch(ApplicationObjectNotFoundException
                | BusinessObjectNotFoundException
                | InvalidArgumentException ex)
        {
            results.add(new SyncResult(dsConfigId, SyncResult.TYPE_ERROR
                    , ex.getLocalizedMessage()
                    , "interface: %s, not related with service: %s"));
        }
    }
    
    /**
     * Checks if a given service name exists in kuwaiba in order to 
     * associate the resource read it form the if-mib 
     * @param serviceName the service read it form the  if-mib
     * @param portId the port of the resource created
     * @param portClassName the class name of the port read it form the if-mib 
     * @throws ApplicationObjectNotFoundException
     * @throws BusinessObjectNotFoundException
     * @throws MetadataObjectNotFoundException
     * @throws InvalidArgumentException
     * @throws OperationNotPermittedException 
     */
    private void relateService(String serviceName, String portName
            , String portId, String portClassName) 
    {
       try{
            //Now we check the resources with the given serviceName or ifAlias
            boolean related = false;
            if(kuwaibaServices.isEmpty())
                kuwaibaServices();
                
            if(!serviceName.isEmpty()){
                for(BusinessObjectLight currentService : kuwaibaServices){
                    //The service is al ready created in kuwaiba
                    if(serviceName.equals(currentService.getName()))
                    {
                        List<BusinessObjectLight> serviceResources = 
                                bem.getSpecialAttribute(currentService.getClassName()
                                        , currentService.getId()
                                        , "uses");
                        for (BusinessObjectLight resource : serviceResources) {
                            if(resource.getId() != null 
                                    && portId != null 
                                    && resource.getId().equals(portId))
                            { //The port is already a resource of the service
                                results.add(new SyncResult(dsConfigId
                                        , SyncResult.TYPE_INFORMATION
                                        , String.format(" %s [%s] related with service %s"
                                                , portName
                                                , portClassName
                                                , currentService.getName())
                                        , "already related")); 
                                related = true;
                                break;
                            } 
                        }
                        if(!related){
                            bem.createSpecialRelationship(currentService.getClassName()
                                    , currentService.getId(), portClassName
                                    , portId, "uses", true);
                            related = true;
                            results.add(new SyncResult(dsConfigId
                                    , SyncResult.TYPE_SUCCESS
                                    , String.format(" %s [%s] related to %s"
                                           , portName
                                           , portClassName
                                           , currentService.getName())
                                    , "service related"));
                        }
                    }
                }
                if(!related)
                    results.add(new SyncResult(dsConfigId, SyncResult.TYPE_WARNING
                            , String.format("interface: %s not related with service: %s"
                                    , portName
                                    , serviceName)
                            , "service not found"));
            }
        } catch (BusinessObjectNotFoundException 
                | MetadataObjectNotFoundException 
                | OperationNotPermittedException 
                | InvalidArgumentException ex) 
        {
            results.add(new SyncResult(dsConfigId, SyncResult.TYPE_ERROR
                    , ex.getLocalizedMessage()
                    , String.format("interface: %s, not related with service: %s"
                            , portName, serviceName)));
        }
    }
        
    /**
     * Checks if a given port exists in the current structure, we search for 
     * the wrappedifName and the ifName
     * @param wrappedIfName the right name of the virtual or logical port
     * @param ifName a given name for port, virtual port or MPLS Tunnel
     * @param type 1 port, 2 virtual port, 3 MPLSTunnel, 4 bdi, 5 VLAN
     * @return the object, null doesn't exists in the current structure
     */
    private BusinessObject searchInterfaceBySnmpName(
            List<BusinessObject> interfaces, String interfaceName)
    {
        for(BusinessObject currentPort: interfaces){
            if(currentPort.getAttributes().get("syncSnmpName") != null
                    && currentPort.getAttributes().get("syncSnmpName")
                    .equals(interfaceName))
                return currentPort;
            else if(currentPort.getAttributes().get("syncSnmpIfName") != null 
                    && currentPort.getAttributes().get("syncSnmpIfName")
                    .equals(interfaceName))
                return currentPort;
            else if(currentPort.getName().equals(SyncUtil.normalizePortName(interfaceName)))
                return currentPort;
        }
        return null;
    }
    
    private BusinessObject searchTransceiverBySnmpName(
            List<BusinessObject> transceiver, String interfaceName)
    {
        for(BusinessObject currentTransceiver: transceiver){
            if(currentTransceiver.getAttributes().get("syncSnmpName") != null
                    && currentTransceiver.getAttributes().get("syncSnmpName")
                    .equals(interfaceName))
                return currentTransceiver;
            else if(currentTransceiver.getAttributes().get("syncSnmpIfName") != null 
                    && currentTransceiver.getAttributes().get("syncSnmpIfName")
                    .equals(interfaceName))
                return currentTransceiver;
            else if(currentTransceiver.getName().equalsIgnoreCase(interfaceName))
                return currentTransceiver;
            else if(currentTransceiver.getName().equals(SyncUtil.normalizePortName(interfaceName)))
                return currentTransceiver;
        }
        return null;
    }
}
