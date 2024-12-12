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
package org.neotropic.kuwaiba.modules.commercial.softman.actions;

import com.vaadin.flow.component.UI;
import org.neotropic.kuwaiba.core.apis.integration.modules.ModuleActionException;
import org.neotropic.kuwaiba.core.apis.integration.modules.actions.AbstractAction;
import org.neotropic.kuwaiba.core.apis.integration.modules.actions.ActionResponse;
import org.neotropic.kuwaiba.core.apis.persistence.application.InventoryObjectPool;
import org.neotropic.kuwaiba.core.apis.persistence.application.Privilege;
import org.neotropic.kuwaiba.core.apis.persistence.application.Session;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.InventoryException;
import org.neotropic.kuwaiba.core.i18n.TranslationService;
import org.neotropic.kuwaiba.modules.commercial.softman.SoftwareManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Deletes a license pool.
 * @author Mauricio Ruiz Beltrán {@literal <mauricio.ruiz@kuwaiba.org>}
 */
@Component
public class DeleteLicensePoolAction extends AbstractAction {
    /**
     * Reference to the Translation Service.
     */
    @Autowired
    private TranslationService ts;
    /**
     * Reference to the Software Manager Service.
    */
    @Autowired
    private SoftwareManagerService sms;
    
    @PostConstruct
    protected void init() {
        this.id = "softman.delete-license-pool";
        this.displayName = ts.getTranslatedString("module.softman.actions.delete-pool.name");
        this.description = ts.getTranslatedString("module.softman.actions.delete-pool.description");
        this.order = 1000;
        
        this.setCallback((parameters) -> {
            Session session = UI.getCurrent().getSession().getAttribute(Session.class);
            InventoryObjectPool pool = (InventoryObjectPool) parameters.get("pool");
            try {
                sms.deleteLicensePool(pool.getId(), pool.getClassName(), session.getUser().getUserName());
            } catch (InventoryException ex) {
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