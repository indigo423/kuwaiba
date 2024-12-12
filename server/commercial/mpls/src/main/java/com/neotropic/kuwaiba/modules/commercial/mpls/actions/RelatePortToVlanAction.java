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
package com.neotropic.kuwaiba.modules.commercial.mpls.actions;

import com.neotropic.kuwaiba.modules.commercial.mpls.MplsService;
import javax.annotation.PostConstruct;
import org.neotropic.kuwaiba.core.apis.integration.modules.ModuleActionException;
import org.neotropic.kuwaiba.core.apis.integration.modules.actions.AbstractAction;
import org.neotropic.kuwaiba.core.apis.integration.modules.actions.ActionResponse;
import org.neotropic.kuwaiba.core.apis.persistence.application.Privilege;
import org.neotropic.kuwaiba.core.apis.persistence.business.BusinessObjectLight;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.BusinessObjectNotFoundException;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.InvalidArgumentException;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.MetadataObjectNotFoundException;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.OperationNotPermittedException;
import org.neotropic.kuwaiba.core.i18n.TranslationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Relate the ports to a VLAN.
 * @author Mauricio Ruiz Beltrán {@literal <mauricio.ruiz@kuwaiba.org>}
 */
@Component
public class RelatePortToVlanAction extends AbstractAction {
    /**
     * Reference to the Translation Service.
     */
    @Autowired
    private TranslationService ts;
    /**
     * Reference to the Mpls Service.
     */
    @Autowired
    private MplsService mplsService;
    /**
     *  Parameter VLAN.
     */
    public static String PARAM_VLAN = "vlan"; //NOI18N
    /**
     * Parameter Port.
     */
    public static String PARAM_PORT = "port"; //NOI18N
    
    @PostConstruct
    protected void init() {
        this.id = "mpls.relate-to-vlan";
        this.displayName = ts.getTranslatedString("module.mpls.actions.relate-port-to-vlan.name");
        this.description = ts.getTranslatedString("module.mpls.actions.relate-port-to-vlan.description");
        
        setCallback((parameters) -> {
            BusinessObjectLight port = (BusinessObjectLight) parameters.get(PARAM_PORT);
            BusinessObjectLight vlan = (BusinessObjectLight) parameters.get(PARAM_VLAN);
            
            try {
                mplsService.relatePortToVlan(
                        port.getClassName(),
                        port.getId(),
                        vlan.getClassName(),
                        vlan.getId()
                );
            } catch (MetadataObjectNotFoundException | InvalidArgumentException 
                    | BusinessObjectNotFoundException | OperationNotPermittedException  ex) {
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