/*
 * Copyright 2010-2020 Neotropic SAS <contact@neotropic.co>.
 *
 * Licensed under the EPL License, Version 1.0 (the "License"); you may not use
 * this file except in compliance with the License. You may obtain a copy of the
 * License at
 *
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.neotropic.inventory.modules.ipam.nodes.actions;

import com.neotropic.inventory.modules.ipam.windows.BDIsInterfaceFrame;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.JOptionPane;
import org.inventory.communications.CommunicationsStub;
import org.inventory.communications.core.LocalObjectLight;
import org.inventory.communications.core.LocalPrivilege;
import org.inventory.communications.core.LocalValidator;
import org.inventory.communications.util.Constants;
import org.inventory.core.services.api.notifications.NotificationUtil;
import org.inventory.core.services.i18n.I18N;
import org.inventory.navigation.navigationtree.nodes.actions.ActionsGroupType;
import org.inventory.navigation.navigationtree.nodes.actions.GenericObjectNodeAction;
import org.openide.util.lookup.ServiceProvider;

/**
 * Actions to relate a Service Instance to a BridgeDomain
 * @author Adrian Martinez Molina {@literal <adrian.martinez@kuwaiba.org>}
 */
@ActionsGroupType(group=ActionsGroupType.Group.RELATE_TO)
@ServiceProvider(service=GenericObjectNodeAction.class)
public class RelateIPToBDIInterfaceAction extends GenericObjectNodeAction {

    public RelateIPToBDIInterfaceAction(){
        putValue(NAME, I18N.gm("relate_to_bdi"));
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        List<LocalObjectLight> bdis = CommunicationsStub.getInstance().getObjectsOfClassLight(Constants.CLASS_BRIDGEDOMAININTERFACE);
        
        if (bdis != null) {
            if (bdis.isEmpty()) {
                JOptionPane.showMessageDialog(null, I18N.gm("no_interfaces_created_create_at_least_one"), 
                    I18N.gm("information"), JOptionPane.INFORMATION_MESSAGE);
            } else {
                BDIsInterfaceFrame frame = new BDIsInterfaceFrame(selectedObjects, bdis);
                frame.setVisible(true);
            }
        } else
            NotificationUtil.getInstance().showSimplePopup(I18N.gm("error"), NotificationUtil.ERROR_MESSAGE, CommunicationsStub.getInstance().getError());
    }
    
    @Override
    public LocalValidator[] getValidators() {
        return null;
    }
    
    @Override
    public LocalPrivilege getPrivilege() {        
        return new LocalPrivilege(LocalPrivilege.PRIVILEGE_IP_ADDRESS_MANAGER, LocalPrivilege.ACCESS_LEVEL_READ_WRITE);
    }

    @Override
    public String[] appliesTo() {
        return new String [] {Constants.CLASS_IP_ADDRESS};
    }
    
    @Override
    public int numberOfNodes() {
        return -1;
    }
}
