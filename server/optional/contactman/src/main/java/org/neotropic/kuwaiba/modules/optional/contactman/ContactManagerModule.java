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
package org.neotropic.kuwaiba.modules.optional.contactman;

import javax.annotation.PostConstruct;
import org.neotropic.kuwaiba.core.apis.integration.modules.AbstractModule;
import org.neotropic.kuwaiba.core.apis.integration.modules.ModuleRegistry;
import org.neotropic.kuwaiba.core.apis.integration.modules.actions.AdvancedActionsRegistry;
import org.neotropic.kuwaiba.core.apis.integration.modules.actions.CoreActionsRegistry;
import org.neotropic.kuwaiba.core.i18n.TranslationService;
import org.neotropic.kuwaiba.modules.optional.contactman.actions.DeleteContactVisualAction;
import org.neotropic.kuwaiba.modules.optional.contactman.actions.RelateObjectToContactVisualAction;
import org.neotropic.kuwaiba.modules.optional.contactman.widgets.NewContactForCustomerAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * The definition of the Contact Manager module.
 * 
 * @author Mauricio Ruiz Beltrán {@literal <mauricio.ruiz@kuwaiba.org>}
 */
@Component
public class ContactManagerModule extends AbstractModule {
    /**
     * Module id.
     */
    public static final String MODULE_ID = "contactman";
    /**
     * Reference to translation service
     */
    @Autowired
    private TranslationService ts;
    /**
     * Reference to the core action registry.
     */
    @Autowired
    private CoreActionsRegistry coreActionsRegistry;
    /**
     * Reference to the module registry.
     */
    @Autowired
    private ModuleRegistry moduleRegistry;
    /**
     * Reference to the module-specific action pool.
     */
    @Autowired
    private AdvancedActionsRegistry advancedActionsRegistry;
    /**
     * Create a new contact for a selected object
     */
    @Autowired
    private NewContactForCustomerAction newContactForCustomerAction;
    /**
     * Relate inventory objects to contacts
     */
    @Autowired
    private RelateObjectToContactVisualAction relateObjectToContactVisualAction;
    /**
     * The visual action to delete a contact preselect
     */
    @Autowired
    private DeleteContactVisualAction deleteContactVisualAction;
   
    @PostConstruct
    public void init() {
        // Register all actions provided by this module
        this.advancedActionsRegistry.registerAction(getId(), relateObjectToContactVisualAction);
        this.advancedActionsRegistry.registerAction(getId(), newContactForCustomerAction);
        this.coreActionsRegistry.registerDeleteAction(getId(), deleteContactVisualAction);

        // Now register the module itself
        this.moduleRegistry.registerModule(this);
    }

    @Override
    public String getId() {
        return MODULE_ID;
    }

    @Override
    public String getName() {
        return ts.getTranslatedString("module.contactman.name");
    }

    @Override
    public int getCategory() {
        return CATEGORY_OTHER;
    }

    @Override
    public String getDescription() {
        return ts.getTranslatedString("module.contactman.description");
    }

    @Override
    public String getVersion() {
        return "2.1.1";
    }

    @Override
    public String getVendor() {
        return "Neotropic SAS <contact@neotropic.co>";
    }

    @Override
    public ModuleType getModuleType() {
        return AbstractModule.ModuleType.TYPE_OPEN_SOURCE;
    }   
}