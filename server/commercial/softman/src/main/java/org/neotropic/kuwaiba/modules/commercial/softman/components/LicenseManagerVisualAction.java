/*
 * Copyright 2010-2024. Neotropic SAS <contact@neotropic.co>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.neotropic.kuwaiba.modules.commercial.softman.components;

import com.vaadin.flow.component.dialog.Dialog;
import org.neotropic.kuwaiba.core.apis.integration.modules.ModuleActionParameterSet;
import org.neotropic.kuwaiba.core.apis.integration.modules.actions.AbstractAction;
import org.neotropic.kuwaiba.core.apis.integration.modules.actions.AbstractVisualAction;
import org.neotropic.kuwaiba.core.apis.persistence.application.ApplicationEntityManager;
import org.neotropic.kuwaiba.core.apis.persistence.business.BusinessEntityManager;
import org.neotropic.kuwaiba.core.apis.persistence.metadata.MetadataEntityManager;
import org.neotropic.kuwaiba.core.i18n.TranslationService;
import org.neotropic.kuwaiba.modules.commercial.softman.SoftwareManagerModule;
import org.neotropic.kuwaiba.modules.commercial.softman.SoftwareManagerService;
import org.neotropic.kuwaiba.modules.commercial.softman.actions.DeleteLicensePoolVisualAction;
import org.neotropic.kuwaiba.modules.commercial.softman.actions.DeleteLicenseVisualAction;
import org.neotropic.kuwaiba.modules.commercial.softman.actions.NewLicensePoolVisualAction;
import org.neotropic.kuwaiba.modules.commercial.softman.actions.NewLicenseVisualAction;
import org.neotropic.kuwaiba.modules.commercial.softman.actions.ReleaseLicenseVisualAction;
import org.neotropic.kuwaiba.modules.core.logging.LoggingService;
import org.neotropic.kuwaiba.visualization.api.resources.ResourceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Dialog to manage licenses.
 * @author Mauricio Ruiz Beltrán {@literal <mauricio.ruiz@kuwaiba.org>}
 */
@Component
public class LicenseManagerVisualAction extends AbstractVisualAction<Dialog> {
    /**
     * Reference to the Translation Service.
     */
    @Autowired
    private TranslationService ts;
    /**
     * Reference to the Metadata Entity Manager
     */
    @Autowired
    private MetadataEntityManager mem;
    /**
     * Reference to the Application Entity Manager
     */
    @Autowired
    private ApplicationEntityManager aem;
    /**
     * Reference to the Business Entity Manager
     */
    @Autowired
    private BusinessEntityManager bem;
    /**
     * Reference to the Software Manager Service
    */
    @Autowired
    private SoftwareManagerService sms;
    /**
     * An action to add license pools
     */
    @Autowired
    private NewLicensePoolVisualAction newLicensePoolVisualAction;
    /**
     * An action to delete license pools
     */
    @Autowired
    private DeleteLicensePoolVisualAction deleteLicensePoolVisualAction;
    /**
     * An action to add licenses
     */
    @Autowired
    private NewLicenseVisualAction newLicenseVisualAction;
    /**
     * An action to delete licenses
     */
    @Autowired
    private DeleteLicenseVisualAction deleteLicenseVisualAction;
    /**
     * An action to release licenses
     */
    @Autowired
    private ReleaseLicenseVisualAction releaseLicenseVisualAction;
    /**
     * Factory to build resources from data source
     */
    @Autowired
    private ResourceFactory resourceFactory;
    /**
     * Reference to the Logging Service
     */
    @Autowired
    private LoggingService log;

    public LicenseManagerVisualAction() {
        super(SoftwareManagerModule.MODULE_ID);
    }

    @Override
    public Dialog getVisualComponent(ModuleActionParameterSet parameters) {
        LicenseManagerDialog lmd =  new LicenseManagerDialog(ts, mem, aem, bem, sms,
                                        newLicensePoolVisualAction, 
                                        deleteLicensePoolVisualAction,
                                        newLicenseVisualAction,
                                        deleteLicenseVisualAction,
                                        releaseLicenseVisualAction,
                                        resourceFactory, log);
        
        //Create window to manage the licenses
        return lmd;
    }

    // It does not use because the action won't be registry
    @Override
    public AbstractAction getModuleAction() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}