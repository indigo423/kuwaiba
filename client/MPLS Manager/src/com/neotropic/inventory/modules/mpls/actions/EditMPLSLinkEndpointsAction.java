/*
 *  Copyright 2010-2020 Neotropic SAS <contact@neotropic.co>.
 * 
 *   Licensed under the EPL License, Version 1.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *        http://www.eclipse.org/legal/epl-v10.html
 * 
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 * 
 */
package com.neotropic.inventory.modules.mpls.actions;

import com.neotropic.inventory.modules.mpls.windows.EditMPLSLinkEnpointsFrame;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import static javax.swing.Action.NAME;
import org.inventory.communications.CommunicationsStub;
import org.inventory.communications.core.LocalObjectLight;
import org.inventory.communications.core.LocalPrivilege;
import org.inventory.communications.core.LocalValidator;
import org.inventory.communications.util.Constants;
import org.inventory.core.services.api.notifications.NotificationUtil;
import org.inventory.core.services.i18n.I18N;
import org.inventory.navigation.navigationtree.nodes.actions.GenericObjectNodeAction;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author adrian
 */
@ServiceProvider(service = GenericObjectNodeAction.class)
public class EditMPLSLinkEndpointsAction extends GenericObjectNodeAction {

    private CommunicationsStub com = CommunicationsStub.getInstance(); 
    
    public EditMPLSLinkEndpointsAction() {
        putValue(NAME, "Edit MPLS Link Endpoints...");
    }
    
    @Override
    public LocalValidator[] getValidators() {
        return null;
    }

    @Override
    public String[] appliesTo() {
        return new String[] { Constants.CLASS_MPLSLINK };
    }

    @Override
    public int numberOfNodes() {
        return 1;
    }

    @Override
    public LocalPrivilege getPrivilege() {
        return new LocalPrivilege(LocalPrivilege.PRIVILEGE_MPLS_MODULE, LocalPrivilege.ACCESS_LEVEL_READ_WRITE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        LocalObjectLight selectedObject = selectedObjects.get(0);
        HashMap<String, LocalObjectLight[]> specialAttributes = CommunicationsStub.getInstance().getSpecialAttributes(selectedObjects.get(0).getClassName(), selectedObjects.get(0).getId());
        
        if (specialAttributes == null) {
            NotificationUtil.getInstance().showSimplePopup(I18N.gm("error"), NotificationUtil.ERROR_MESSAGE, CommunicationsStub.getInstance().getError());
            return;
        }
        
        LocalObjectLight aSideRoot = null;
        LocalObjectLight bSideRoot = null;
            
        if (specialAttributes.containsKey("mplsEndpointA")) {//NOI18N
            aSideRoot =  CommunicationsStub.getInstance().getFirstParentOfClass(specialAttributes.get("mplsEndpointA")[0].getClassName(), specialAttributes.get("mplsEndpointA")[0].getId(), Constants.CLASS_GENERICCOMMUNICATIONSELEMENT); //NOI18N  specialAttributes.get("endpointA")[0]; //NOI18N
            if (aSideRoot == null) {
                NotificationUtil.getInstance().showSimplePopup(I18N.gm("error"), NotificationUtil.ERROR_MESSAGE, CommunicationsStub.getInstance().getError());
                return;
            }
        }
        if (specialAttributes.containsKey("mplsEndpointB")) { //NOI18N
            bSideRoot = CommunicationsStub.getInstance().getFirstParentOfClass(specialAttributes.get("mplsEndpointB")[0].getClassName(), specialAttributes.get("mplsEndpointB")[0].getId(),  Constants.CLASS_GENERICCOMMUNICATIONSELEMENT); //NOI18N  specialAttributes.get("endpointA")[0]; //NOI18N
            if (bSideRoot == null) {
                NotificationUtil.getInstance().showSimplePopup(I18N.gm("error"), NotificationUtil.ERROR_MESSAGE, CommunicationsStub.getInstance().getError());
                return;
            }
        }
        
        EditMPLSLinkEnpointsFrame frame = new EditMPLSLinkEnpointsFrame(selectedObject, aSideRoot != null ? 
                aSideRoot : new LocalObjectLight("-1", Constants.DUMMYROOT, Constants.DUMMYROOT), bSideRoot != null ? bSideRoot : new LocalObjectLight("-1", Constants.DUMMYROOT, Constants.DUMMYROOT));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    
}
