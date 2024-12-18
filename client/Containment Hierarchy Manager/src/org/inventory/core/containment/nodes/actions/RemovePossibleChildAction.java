/*
 *  Copyright 2010-2020 Neotropic SAS <contact@neotropic.co>
 *
 *  Licensed under the EPL License, Version 1.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.eclipse.org/legal/epl-v10.html
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.inventory.core.containment.nodes.actions;

import java.awt.event.ActionEvent;
import org.inventory.communications.CommunicationsStub;
import org.inventory.communications.core.LocalPrivilege;
import org.inventory.core.services.api.actions.GenericInventoryAction;
import org.inventory.core.services.api.notifications.NotificationUtil;
import org.inventory.core.containment.nodes.ClassMetadataChildren;
import org.inventory.core.containment.nodes.ClassMetadataNode;
import org.inventory.core.services.i18n.I18N;
import org.openide.nodes.Node;
import org.openide.util.Utilities;

/**
 * Implements the "remove a class from container hierarchy" action
 * @author Charles Edward Bedon Cortazar {@literal <charles.bedon@kuwaiba.org>}
 */
public class RemovePossibleChildAction extends GenericInventoryAction {
    private static RemovePossibleChildAction instance;
    
    private RemovePossibleChildAction() {
        putValue(NAME, I18N.gm("remove"));
    }
    
    public static RemovePossibleChildAction getInstance() {
        return instance == null ? instance = new RemovePossibleChildAction() : instance;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        ClassMetadataNode selectedNode = Utilities.actionsGlobalContext().lookup(ClassMetadataNode.class);
        if (selectedNode != null) {
            long childId = selectedNode.getObject().getId();
            long parentId = ((ClassMetadataNode) selectedNode.getParentNode()).getObject().getId();
            
            if (CommunicationsStub.getInstance().removePossibleChildren(parentId, new long [] {childId})) {
                ((ClassMetadataChildren) selectedNode.getParentNode().getChildren()).remove(new Node[] {selectedNode});
                CommunicationsStub.getInstance().refreshCache(false, false, false, true, false);
                
                NotificationUtil.getInstance().showSimplePopup(I18N.gm("success"), NotificationUtil.INFO_MESSAGE,
                    I18N.gm("operation_complete_successfully"));
            } else {
                NotificationUtil.getInstance().showSimplePopup(I18N.gm("error"), NotificationUtil.ERROR_MESSAGE, CommunicationsStub.getInstance().getError());
            }
        }
    }
    
    @Override
    public LocalPrivilege getPrivilege() {
        return new LocalPrivilege(LocalPrivilege.PRIVILEGE_CONTAINMENT_MANAGER, LocalPrivilege.ACCESS_LEVEL_READ_WRITE);
    }
}