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

package org.neotropic.kuwaiba.web;

import com.neotropic.kuwaiba.modules.commercial.sdh.SdhModule;
import com.vaadin.flow.component.page.Push;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.ApplicationObjectNotFoundException;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.ExecutionException;
import org.neotropic.kuwaiba.core.i18n.TranslationService;
import org.neotropic.kuwaiba.core.persistence.PersistenceService;
import org.neotropic.kuwaiba.core.persistence.reference.extras.processman.ProcessManagerService;
import org.neotropic.kuwaiba.core.services.scheduling.SchedulingService;
import org.neotropic.kuwaiba.northbound.ws.KuwaibaSoapWebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.xml.ws.Endpoint;
import java.util.Properties;
import org.neotropic.kuwaiba.modules.core.logging.LoggerType;
import org.neotropic.kuwaiba.modules.core.logging.LoggingService;

/**
 * Application entry point.
 * @author Charles Edward Bedon Cortazar {@literal <charles.bedon@kuwaiba.org>}
 */
@Push
@SpringBootApplication
public class Application extends SpringBootServletInitializer {
    
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    
    @Component
    public static class Bootstrap {
        // Web service properties
        @Value("${ws.port}")
        private int wsPort;
        // General properties
        @Value("${general.enable-security-manager}")
        private boolean enableSecurityManager;
        @Value("${general.locale}")
        private String locale;
        
        // Connection properties
        @Value("${db.path}")
        private String dbPath;
        @Value("${db.host}")
        private String dbHost;
        @Value("${db.port}")
        private int dbPort;
        
        // Application properties
        @Value("${aem.enforce-business-rules}")
        private String enforceBusinessRules;
        @Value("${aem.process-engine-path}")
        private String processEnginePath;
        @Value("${aem.processes-path}")
        private String processesPath;
        @Value("${aem.max-routes}")
        private String maxRoutes;
        @Value("${aem.backgrounds-path}")
        private String backgroundsPath;
        
        // Business properties
        @Value("${bem.attachments-path}")
        private String attachmentsPath;
        @Value("${bem.max-attachment-size}")
        private String maxAttachmentSize;
        
        @Autowired
        private PersistenceService persistenceService;
        @Autowired
        private TranslationService ts;
        @Autowired
        private KuwaibaSoapWebService ws;
        @Autowired
        private SdhModule modSdh;
        @Autowired
        private ProcessManagerService processManagerService;
        @Autowired
        private SchedulingService shs;
        @Autowired
        private LoggingService log;
        
        @PostConstruct
        void init() {
            
            try {
                ts.setCurrentlanguage(locale);
            } catch (IllegalArgumentException ex) {
                log.writeLogMessage(LoggerType.INFO, Application.class, ex.getMessage());
            }
            
            Properties generalProperties = new Properties();
            generalProperties.put("enableSecurityManager", enableSecurityManager);
            persistenceService.setGeneralProperties(generalProperties);
            
            Properties connectionProperties = new Properties();
            connectionProperties.put("dbPath", dbPath);
            connectionProperties.put("dbHost", dbHost);
            connectionProperties.put("dbPort", dbPort);
            persistenceService.setConnectionProperties(connectionProperties);

            persistenceService.setMetadataProperties(new Properties());
            
            Properties applicationProperties = new Properties();
            applicationProperties.put("enforceBusinessRules", enforceBusinessRules);
            applicationProperties.put("processEnginePath", processEnginePath);
            applicationProperties.put("processesPath", processesPath);
            applicationProperties.put("maxRoutes", maxRoutes);
            applicationProperties.put("backgroundsPath", backgroundsPath);
            persistenceService.setApplicationProperties(applicationProperties);
            
            Properties businessProperties = new Properties();
            businessProperties.put("attachmentsPath", attachmentsPath);
            businessProperties.put("maxAttachmentSize", maxAttachmentSize);
            persistenceService.setBusinessProperties(businessProperties);
            
            try {
                persistenceService.start();
            } catch (IllegalStateException ex) {
                log.writeLogMessage(LoggerType.ERROR, Application.class, 
                        String.format(ts.getTranslatedString("module.persistence.messages.cant-start-persistence-service"), 
                            ex.getLocalizedMessage()));
            }
            log.writeLogMessage(LoggerType.INFO, SdhModule.class, 
                    String.format(ts.getTranslatedString("module.general.messages.initializing"), modSdh.getName()));
            modSdh.configureModule(persistenceService.getMem(), persistenceService.getAem(), persistenceService.getBem());
            processManagerService.init(persistenceService, ts, log);

            if (persistenceService.getState().equals(PersistenceService.EXECUTION_STATE.RUNNING)) {
                // After Java 8, SAAJMetaFactoryImpl is no longer the default implementation of SAAJMetaFactory, so for later versions
                // the mapping has to be done manually.
                System.setProperty("javax.xml.soap.MetaFactory","com.sun.xml.messaging.saaj.soap.SAAJMetaFactoryImpl");
                
                Endpoint.publish(String.format("http://0.0.0.0:%s/kuwaiba/KuwaibaService", wsPort), ws);
                log.writeLogMessage(LoggerType.INFO, PersistenceService.class,  
                        String.format(ts.getTranslatedString("module.webservice.messages.initialized"), wsPort));
            } else
                log.writeLogMessage(LoggerType.ERROR, PersistenceService.class, 
                        ts.getTranslatedString("module.webservice.messages.cant-start-web-service"));

            try {
                shs.scheduleJobs();
            } catch (ExecutionException | ApplicationObjectNotFoundException ex) {
                log.writeLogMessage(LoggerType.ERROR, Application.class, ex.getMessage());
            }
        }
        
        @PreDestroy
        void shutdown() {
            try {
                persistenceService.stop();
            } catch (IllegalStateException ex) {
                log.writeLogMessage(LoggerType.ERROR, Application.class, ex.getLocalizedMessage());
            }
        }
    }
}
