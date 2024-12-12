/*
 *  Copyright 2010-2017 Neotropic SAS <contact@neotropic.co>.
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
package org.kuwaiba.apis.persistence;

import com.neotropic.kuwaiba.modules.GenericCommercialModule;
import com.neotropic.kuwaiba.modules.ipam.IPAMModule;
import com.neotropic.kuwaiba.modules.mpls.MPLSModule;
import com.neotropic.kuwaiba.modules.projects.ProjectsModule;
import com.neotropic.kuwaiba.modules.sdh.SDHModule;
import java.lang.reflect.InvocationTargetException;
import java.util.Calendar;
import java.util.Properties;
import org.kuwaiba.apis.persistence.application.ApplicationEntityManager;
import org.kuwaiba.apis.persistence.business.BusinessEntityManager;
import org.kuwaiba.apis.persistence.exceptions.ConnectionException;
import org.kuwaiba.apis.persistence.exceptions.NotAuthorizedException;
import org.kuwaiba.apis.persistence.metadata.MetadataEntityManager;
import org.kuwaiba.apis.persistence.integrity.DataIntegrityService;
import org.kuwaiba.apis.persistence.integrity.DataModelLoader;
import org.kuwaiba.services.persistence.util.Constants;

/**
 * This class provides a singleton object that handles the persistence service lifecycle
 * @author Charles Edward Bedon Cortazar <charles.bedon@kuwaiba.org>
 */
public class PersistenceService {
    
    private static PersistenceService instance;
    
    private EXECUTION_STATE state;
    private Properties configuration;
    private ConnectionManager connectionManager;
    private MetadataEntityManager mem;
    private BusinessEntityManager bem;
    private ApplicationEntityManager aem;
    private DataIntegrityService dataIntegrityService;
    private DataModelLoader dataModelLoader;
        
    private PersistenceService(){
        state = EXECUTION_STATE.STOPPED;
        configuration = new Properties();
    }
    
    public static PersistenceService getInstance() {
        if (instance == null)
            instance = new PersistenceService();
        return instance;
    }
    
    public void start()throws IllegalStateException {
        if (state == EXECUTION_STATE.RUNNING)
            throw new IllegalStateException("Persistence Service can not be started because is already running");
        
        if (System.getSecurityManager() == null && Boolean.valueOf(configuration.getProperty("enableSecurityManager", "false")))
            System.setSecurityManager(new SecurityManager());
        try {
            System.out.println(String.format("[KUWAIBA] [%s] Starting Persistence Service version %s", Calendar.getInstance().getTime(), Constants.PERSISTENCE_SERVICE_VERSION));
            PersistenceLayerFactory plf = new PersistenceLayerFactory();
            connectionManager = plf.createConnectionManager();
            Properties connectionConfiguration = new Properties();
            connectionConfiguration.put("dbPath", configuration.getProperty("dbPath"));
            connectionManager.setConfiguration(connectionConfiguration); //NOI18N
            System.out.println(String.format("[KUWAIBA] [%s] Establishing connection to the database...", Calendar.getInstance().getTime()));
            connectionManager.openConnection();
            System.out.println(String.format("[KUWAIBA] [%s] Connection established", Calendar.getInstance().getTime()));
            System.out.println(String.format("[KUWAIBA] [%s] %s", Calendar.getInstance().getTime(), connectionManager.getConnectionDetails()));
            aem = plf.createApplicationEntityManager(connectionManager);
            Properties applicationConfiguration = new Properties();
            applicationConfiguration.put("backgroundsPath", configuration.getProperty("backgroundsPath"));
            applicationConfiguration.put("corporateLogo", configuration.getProperty("corporateLogo"));
            aem.setConfiguration(applicationConfiguration);
            mem = plf.createMetadataEntityManager(connectionManager);
            bem = plf.createBusinessEntityManager(connectionManager, aem, mem);
            dataModelLoader = new DataModelLoader(connectionManager, mem);
            //dataIntegrityService = new DataIntegrityService(connectionManager);
            //dataIntegrityService.checkIntegrity();
            System.out.println(String.format("[KUWAIBA] [%s] Detecting advanced modules...", Calendar.getInstance().getTime()));
            //Place here some fancy OSGi stuff instead of this horrid hardcoded list
            aem.registerCommercialModule(new IPAMModule());
            aem.registerCommercialModule(new SDHModule());
            aem.registerCommercialModule(new MPLSModule());
            aem.registerCommercialModule(new ProjectsModule());
            
            for (GenericCommercialModule aModule : aem.getCommercialModules()) {
                System.out.println(String.format("[KUWAIBA]   [%s]  %s %s by %s", Calendar.getInstance().getTime(), aModule.getName(), aModule.getVersion(), aModule.getVendor()));
                aModule.configureModule(aem, mem, bem);
            }
            System.out.println(String.format("[KUWAIBA] [%s] Persistence Service is up and running", Calendar.getInstance().getTime()));
            state = EXECUTION_STATE.RUNNING;
        }catch(InstantiationException | IllegalAccessException | ConnectionException | NoSuchMethodException | IllegalArgumentException | InvocationTargetException | NotAuthorizedException e){
            if (connectionManager != null)
                connectionManager.closeConnection();
            System.out.println(String.format("[KUWAIBA] [%s] Persistence Service could not be started: %s", Calendar.getInstance().getTime(), e.getMessage()));
            state = EXECUTION_STATE.STOPPED;
        }
    }
    public void stop() throws IllegalStateException {
        if (state == EXECUTION_STATE.STOPPED)
            throw new IllegalStateException("Persistence Service can not be stopped because is not running");
        
        System.out.println(String.format("[KUWAIBA] [%s] Closing connection...", Calendar.getInstance().getTime()));
        connectionManager.closeConnection();
        System.out.println(String.format("[KUWAIBA] [%s] Connection closed", Calendar.getInstance().getTime()));
        state = EXECUTION_STATE.STOPPED;
    }
    public void restart(){
        stop();
        start();
    }
 
    public MetadataEntityManager getMetadataEntityManager() throws IllegalStateException {
        if (state != EXECUTION_STATE.RUNNING)
            throw new IllegalStateException("Can't locate an instance of the Metadata Entity Manager.  Persistence Service is not running");
        return mem;
    }
    
    public BusinessEntityManager getBusinessEntityManager() throws IllegalStateException {
        if (state != EXECUTION_STATE.RUNNING)
            throw new IllegalStateException("Can't locate an instance of the Business Entity Manager. Persistence Service is not running");
        return bem;
    }
    
    public ApplicationEntityManager getApplicationEntityManager() throws IllegalStateException {
        if (state != EXECUTION_STATE.RUNNING)
            throw new IllegalStateException("Can't locate an instance of the Application Entity Manager. Persistence Service is not running");
        return aem;
    }
    
    public ConnectionManager getConnectionManager() throws IllegalStateException {
        if (state != EXECUTION_STATE.RUNNING)
            throw new IllegalStateException("Can't locate an instance of the Connection Manager. Persistence Service is not running");
        return connectionManager;
    }
    
    public DataIntegrityService getDataIntegrityService() throws IllegalStateException{
        if (state != EXECUTION_STATE.RUNNING)
            throw new IllegalStateException("Can't locate an instance of the Data Integrity Service. Persistence Service is not running");
        return dataIntegrityService; 
    }
    
    public DataModelLoader getDataModelLoader() throws IllegalStateException{
        if (state != EXECUTION_STATE.RUNNING)
            throw new IllegalStateException("Can't locate an instance of the Data model loader. Persistence Service is not running");
        return dataModelLoader; 
    }
    
    public EXECUTION_STATE getState() {
        return state;
    }

    /**
     * This method sets the configuration properties to be used to connect to the database
     * @param properties 
     */
    public void setConfiguration(Properties properties) {
        this.configuration = properties;
    }
    
    public enum EXECUTION_STATE {
        STOPPED,
        RUNNING,
        PAUSED
    }
}