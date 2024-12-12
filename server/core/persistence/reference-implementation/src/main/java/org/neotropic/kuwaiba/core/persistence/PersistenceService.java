/*
 *  Copyright 2010-2024 Neotropic SAS <contact@neotropic.co>.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       https://apache.org/licenses/LICENSE-2.0.txt
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.neotropic.kuwaiba.core.persistence;

import java.util.Properties;
import org.neotropic.kuwaiba.core.apis.persistence.ConnectionManager;
import org.neotropic.kuwaiba.core.apis.persistence.application.ApplicationEntityManager;
import org.neotropic.kuwaiba.core.apis.persistence.business.BusinessEntityManager;
import org.neotropic.kuwaiba.core.apis.persistence.metadata.MetadataEntityManager;
import org.neotropic.kuwaiba.core.apis.persistence.util.Constants;
import org.neotropic.kuwaiba.core.i18n.TranslationService;
import org.neotropic.kuwaiba.modules.core.logging.LoggerType;
import org.neotropic.kuwaiba.modules.core.logging.LoggingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * This service manages the life cycle of the connection to the database and the rest of 
 * services that connect to the database.
 * @author Charles Edward Bedon Cortazar {@literal <charles.bedon@kuwaiba.org>}
 */
@Service
public class PersistenceService {
    /**
     * General purpose configuration variables.
     */
    private Properties generalProperties;
    /**
     * The properties (call them configuration variables if you will) used in the 
     * {@link ConnectionManager}.
     */
    private Properties connectionProperties;
    /**
     * The properties (call them configuration variables if you will) used in the 
     * {@link MetadataEntityManager}.
     */
    private Properties metadataProperties;
    /**
     * The properties (call them configuration variables if you will) used in the 
     * {@link ApplicationEntityManager}.
     */
    private Properties applicationProperties;
    /**
     * The properties (call them configuration variables if you will) used in the 
     * {@link BusinessEntityManager}.
     */
    private Properties businessProperties;
    /**
     * The current state of the service (basically, it tells what is the status of the connection to the data base)
     */
    private EXECUTION_STATE state = EXECUTION_STATE.STOPPED;
    /**
     * The connection manager to be initialized.
     */
    @Autowired
    private ConnectionManager connectionManager;
    /**
     * The metadata entity manager to be initialized.
     */
    @Autowired
    private MetadataEntityManager mem;
    /**
     * The application entity manager to be initialized.
     */
    @Autowired
    private ApplicationEntityManager aem;
    /**
     * The business entity manager to be initialized.
     */
    @Autowired
    private BusinessEntityManager bem;
    /**
     * Reference to the translation service.
     */
    @Autowired
    private TranslationService ts;
    /**
     * Reference to the Logging service.
     */
    @Autowired
    private LoggingService log;
    
    
    public Properties getMetadataProperties() {
        return metadataProperties;
    }

    public void setMetadataProperties(Properties metadataProperties) {
        this.metadataProperties = metadataProperties;
    }

    public Properties getApplicationProperties() {
        return applicationProperties;
    }

    public void setApplicationProperties(Properties applicationProperties) {
        this.applicationProperties = applicationProperties;
    }

    public Properties getBusinessProperties() {
        return businessProperties;
    }

    public void setBusinessProperties(Properties businessProperties) {
        this.businessProperties = businessProperties;
    }

    public Properties getGeneralProperties() {
        return generalProperties;
    }

    public void setGeneralProperties(Properties generalProperties) {
        this.generalProperties = generalProperties;
    }

    public Properties getConnectionProperties() {
        return connectionProperties;
    }

    public void setConnectionProperties(Properties connectionProperties) {
        this.connectionProperties = connectionProperties;
    }

    public ConnectionManager getConnectionManager() {
        return connectionManager;
    }

    public MetadataEntityManager getMem() {
        return mem;
    }

    public ApplicationEntityManager getAem() {
        return aem;
    }

    public BusinessEntityManager getBem() {
        return bem;
    }
     
    public void start()throws IllegalStateException {
        if (state == EXECUTION_STATE.RUNNING)
            throw new IllegalStateException(ts.getTranslatedString("module.persistence.messages.already-running"));
        
        if (connectionProperties == null || metadataProperties == null || applicationProperties == null || 
                businessProperties == null || generalProperties == null)
            throw new IllegalStateException("Not all configuration properties have been set");
        
        if (System.getSecurityManager() == null && (boolean)generalProperties.get("enableSecurityManager")) {
            System.setSecurityManager(new SecurityManager());
            log.writeLogMessage(LoggerType.INFO, PersistenceService.class, "Security Manager enabled");
        }
        
        try {
            log.writeLogMessage(LoggerType.INFO, PersistenceService.class, 
                    String.format("Starting Persistence Service version %s", Constants.PERSISTENCE_SERVICE_VERSION));
         
            connectionManager.setConfiguration(connectionProperties); //NOI18N
            log.writeLogMessage(LoggerType.INFO, PersistenceService.class, "Establishing connection to the database...");
            connectionManager.openConnection();
            log.writeLogMessage(LoggerType.INFO, PersistenceService.class,  "Connection established");
            log.writeLogMessage(LoggerType.INFO, PersistenceService.class,  
                    String.format("Using database %s", connectionManager.getConnectionDetails()));
            
            mem.setConfiguration(metadataProperties);
            mem.initCache();
            log.writeLogMessage(LoggerType.INFO, PersistenceService.class,  "Metadata Entity Manager initialized successfully");
            
            
            aem.setConfiguration(applicationProperties);
            aem.initCache();
            log.writeLogMessage(LoggerType.INFO, PersistenceService.class, "Application Entity Manager initialized successfully");
            
            bem.setConfiguration(businessProperties);
            bem.initCache();
            log.writeLogMessage(LoggerType.INFO, PersistenceService.class, "Business Entity Manager initialized successfully");
            
            log.writeLogMessage(LoggerType.INFO, PersistenceService.class, "Persistence Service is up and running");
            
            state = EXECUTION_STATE.RUNNING;
        } catch(Exception ex) {
            if (connectionManager != null)
                connectionManager.closeConnection();
            log.writeLogMessage(LoggerType.ERROR, PersistenceService.class, 
                    "Persistence Service could not be started", ex);
            state = EXECUTION_STATE.STOPPED;
        }
    }
    public void stop() throws IllegalStateException {
        if (state == EXECUTION_STATE.STOPPED)
            throw new IllegalStateException("Persistence Service can not be stopped because it is not running");
        
        log.writeLogMessage(LoggerType.INFO, PersistenceService.class, "Closing connection...");
        connectionManager.closeConnection();
        log.writeLogMessage(LoggerType.INFO, PersistenceService.class, "Connection closed");
        state = EXECUTION_STATE.STOPPED;
    }
    public void restart(){
        stop();
        start();
    }
        
    public EXECUTION_STATE getState() {
        return state;
    }
    
    public enum EXECUTION_STATE {
        STOPPED,
        RUNNING,
        PAUSED
    }
}
