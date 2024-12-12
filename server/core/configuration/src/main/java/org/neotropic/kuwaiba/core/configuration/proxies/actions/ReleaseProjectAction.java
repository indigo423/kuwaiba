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
package org.neotropic.kuwaiba.core.configuration.proxies.actions;

import com.vaadin.flow.component.UI;
import javax.annotation.PostConstruct;
import org.neotropic.kuwaiba.core.apis.integration.modules.ModuleActionException;
import org.neotropic.kuwaiba.core.apis.integration.modules.actions.AbstractAction;
import org.neotropic.kuwaiba.core.apis.integration.modules.actions.ActionResponse;
import org.neotropic.kuwaiba.core.apis.persistence.application.Privilege;
import org.neotropic.kuwaiba.core.apis.persistence.application.Session;
import org.neotropic.kuwaiba.core.apis.persistence.business.BusinessObjectLight;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.ApplicationObjectNotFoundException;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.BusinessObjectNotFoundException;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.InvalidArgumentException;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.MetadataObjectNotFoundException;
import org.neotropic.kuwaiba.core.configuration.proxies.ProxyManagerService;
import org.neotropic.kuwaiba.core.i18n.TranslationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Release the projects associated to a proxy.
 * @author Mauricio Ruiz {@literal <mauricio.ruiz@kuwaiba.org>}
 */
@Component
public class ReleaseProjectAction extends AbstractAction {
    /**
     * Reference to the translation service.
     */
    @Autowired
    private TranslationService ts;
    /**
     * Reference to the Business Entity Manager
     */
    @Autowired
    private ProxyManagerService pms;
    
    @PostConstruct
    protected void init() {
        this.id = "configman.release-project";
        this.displayName = ts.getTranslatedString("module.configman.proxies.actions.release-project.name");
        this.description = ts.getTranslatedString("module.configman.proxies.actions.release-project.description");
        
        setCallback((parameters) -> {
            Session session = UI.getCurrent().getSession().getAttribute(Session.class);
            BusinessObjectLight proxy = (BusinessObjectLight) parameters.get("proxy");
            BusinessObjectLight project = (BusinessObjectLight) parameters.get("project");
            try {
                pms.releaseProjectFromProxy(project.getClassName(), project.getId(),
                         proxy.getClassName(), proxy.getId(),
                         session.getUser().getUserName());
            } catch (BusinessObjectNotFoundException | ApplicationObjectNotFoundException
                    | MetadataObjectNotFoundException | InvalidArgumentException ex) {
                throw new ModuleActionException(ex.getMessage());
            }
            return new ActionResponse();
        });
    }

    @Override
    public int getRequiredAccessLevel() {
        return Privilege.ACCESS_LEVEL_READ_WRITE;
    }

    @Override
    public boolean requiresConfirmation() {
        return false;
    }
}