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
package org.neotropic.kuwaiba.core.configuration.proxies;

import javax.annotation.PostConstruct;
import org.neotropic.kuwaiba.core.apis.integration.modules.AbstractModule;
import org.neotropic.kuwaiba.core.apis.integration.modules.ModuleRegistry;
import org.neotropic.kuwaiba.core.apis.integration.modules.actions.AdvancedActionsRegistry;
import org.neotropic.kuwaiba.core.configuration.proxies.actions.AssociateObjectToProxyVisualAction;
import org.neotropic.kuwaiba.core.i18n.TranslationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * The definition of the Proxy Manager module.
 * @author Mauricio Ruiz Beltrán {@literal <mauricio.ruiz@kuwaiba.org>}
 */
@Component
public class ProxyManagerModule extends AbstractModule {
    /**
     * Module id.
     */
    public static final String MODULE_ID = "configuration/proxies";
    /**
     * Translation service
     */
    @Autowired
    private TranslationService ts;
    /**
     * Reference to the module registry.
     */
    @Autowired
    private ModuleRegistry moduleRegistry;
    /**
     * Reference to the action registry.
     */
    @Autowired
    private AdvancedActionsRegistry actionRegistry;
    /**
     * Reference to the action that associate to business object to a proxy
     */
    @Autowired
    private AssociateObjectToProxyVisualAction associateObjectToProxyVisualAction;
    
    @PostConstruct
    public void init() {
        // Register all actions
        this.actionRegistry.registerAction(MODULE_ID, associateObjectToProxyVisualAction);
        // Now the module itself
        this.moduleRegistry.registerModule(this);
    }

    @Override
    public String getName() {
        return ts.getTranslatedString("module.configman.proxies.name");
    }

    @Override
    public String getDescription() {
        return ts.getTranslatedString("module.configman.proxies.description");
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
    public String getId() {
        return MODULE_ID;
    }

    @Override
    public AbstractModule.ModuleType getModuleType() {
        return AbstractModule.ModuleType.TYPE_OPEN_SOURCE;
    }
    
    @Override
    public int getCategory() {
        return CATEGORY_SETTINGS;
    }
}