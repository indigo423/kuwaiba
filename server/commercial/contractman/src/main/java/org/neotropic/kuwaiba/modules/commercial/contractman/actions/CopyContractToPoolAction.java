/*
 * Copyright 2010-2024 Neotropic SAS <contact@neotropic.co>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.neotropic.kuwaiba.modules.commercial.contractman.actions;

import com.vaadin.flow.component.UI;
import javax.annotation.PostConstruct;
import org.neotropic.kuwaiba.core.apis.integration.modules.ModuleActionException;
import org.neotropic.kuwaiba.core.apis.integration.modules.actions.AbstractAction;
import org.neotropic.kuwaiba.core.apis.integration.modules.actions.ActionResponse;
import org.neotropic.kuwaiba.core.apis.persistence.application.InventoryObjectPool;
import org.neotropic.kuwaiba.core.apis.persistence.application.Privilege;
import org.neotropic.kuwaiba.core.apis.persistence.application.Session;
import org.neotropic.kuwaiba.core.apis.persistence.business.BusinessObjectLight;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.ApplicationObjectNotFoundException;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.BusinessObjectNotFoundException;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.InvalidArgumentException;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.MetadataObjectNotFoundException;
import org.neotropic.kuwaiba.core.i18n.TranslationService;
import org.neotropic.kuwaiba.modules.commercial.contractman.ContractManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Creates copy of a contract.
 * @author Mauricio Ruiz Beltrán {@literal <mauricio.ruiz@kuwaiba.org>}
 */
@Component
public class CopyContractToPoolAction extends AbstractAction {
    /**
     * Reference to the translation service.
     */
    @Autowired
    private TranslationService ts;
    /**
     * Reference to the contract service.
     */
    @Autowired
    private ContractManagerService cms;
    /**
     * Parameter pool.
     */
    public static String PARAM_POOL= "pool";
    /**
     * Parameter contract.
     */
    public static String PARAM_CONTRACT = "contract";
    
    @PostConstruct
    public void init() {
        this.id = "contractman.actions.copy";
        this.displayName = ts.getTranslatedString("module.contractman.actions.copy-contract-to-pool.name");
        this.description = ts.getTranslatedString("module.contractman.actions.copy-contract-to-pool.description");
        this.order = 1000;
        
        setCallback((parameters) -> {
            try {
                Session session = UI.getCurrent().getSession().getAttribute(Session.class);
                BusinessObjectLight contract = (BusinessObjectLight) parameters.get(PARAM_CONTRACT);
                InventoryObjectPool pool = (InventoryObjectPool) parameters.get(PARAM_POOL);
                
                cms.copyContractToPool(pool.getId(), contract.getClassName(), contract.getId(), session.getUser().getUserName());
            } catch (MetadataObjectNotFoundException | InvalidArgumentException | ApplicationObjectNotFoundException | BusinessObjectNotFoundException ex) {
                throw new ModuleActionException(ex.getMessage());
            }
            return new ActionResponse();
        });
    }

    @Override
    public boolean requiresConfirmation() {
        return false;
    }

    @Override
    public int getRequiredAccessLevel() {
        return Privilege.ACCESS_LEVEL_READ_WRITE;
    }
}