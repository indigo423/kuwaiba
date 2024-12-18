/*
 *  Copyright 2010-2020 Neotropic SAS <contact@neotropic.co>.
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

package org.kuwaiba.management.services.nodes.actions;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.util.Iterator;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import org.inventory.communications.CommunicationsStub;
import org.inventory.communications.core.LocalPool;
import org.inventory.communications.core.LocalPrivilege;
import org.inventory.communications.util.Constants;
import org.inventory.core.services.api.actions.GenericInventoryAction;
import org.inventory.core.services.api.notifications.NotificationUtil;
import org.inventory.core.services.utils.JComplexDialogPanel;
import org.kuwaiba.management.services.nodes.ServiceManagerRootNode;
import org.openide.util.Utilities;

/**
 * Creates a new customer pool
 * @author adrian martinez molina {@literal <adrian.martinez@kuwaiba.org>}
 */
public class CreateCustomerPoolAction extends GenericInventoryAction {
    /**
     * Reference to the communications stub singleton
     */
    private CommunicationsStub com;
  
    public CreateCustomerPoolAction() {
        putValue(NAME, java.util.ResourceBundle.getBundle("org/kuwaiba/management/services/Bundle").getString("LBL_CREATE_CUSTOMERS_POOL"));
        com = CommunicationsStub.getInstance();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        Iterator<? extends ServiceManagerRootNode> selectedNodes = 
                Utilities.actionsGlobalContext().lookupResult(ServiceManagerRootNode.class).allInstances().iterator();
            
        if (!selectedNodes.hasNext())
            return;
        
        ServiceManagerRootNode selectedNode = selectedNodes.next();
        
        JTextField txtName = new JTextField(), txtDescription =  new JTextField();
        txtName.setName("txtName"); //NOI18N
        txtName.setPreferredSize(new Dimension(120, 18));
        txtDescription.setName("txtDescription"); //NOI18N
        
        JComplexDialogPanel pnlMyDialog = new JComplexDialogPanel(
                new String[]{java.util.ResourceBundle.getBundle("org/kuwaiba/management/services/Bundle").getString("LBL_NAME"), 
                    java.util.ResourceBundle.getBundle("org/kuwaiba/management/services/Bundle").getString("LBL_DESCRIPTION")},
                new JComponent []{txtName, txtDescription});
        
        if (JOptionPane.showConfirmDialog(null,
                pnlMyDialog,
                java.util.ResourceBundle.getBundle("org/kuwaiba/management/services/Bundle").getString("LBL_CREATE_CUSTOMERS_POOL"),
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE) == JOptionPane.OK_OPTION){
        
                    LocalPool newPool = com.createRootPool(((JTextField)pnlMyDialog.getComponent("txtName")).getText(), 
                            ((JTextField)pnlMyDialog.getComponent("txtDescription")).getText(), Constants.CLASS_GENERICCUSTOMER, LocalPool.POOL_TYPE_MODULE_ROOT);
                    
                    if (newPool ==  null)
                        NotificationUtil.getInstance().showSimplePopup("Error", NotificationUtil.ERROR_MESSAGE, com.getError());

                    else {
                        
                        ((ServiceManagerRootNode.ServiceManagerRootChildren)selectedNode.getChildren()).addNotify();
                        NotificationUtil.getInstance().showSimplePopup("Success", NotificationUtil.INFO_MESSAGE, java.util.ResourceBundle.getBundle("org/kuwaiba/management/services/Bundle").getString("LBL_CREATED"));
                    }
        }
    }

    @Override
    public LocalPrivilege getPrivilege() {
        return new LocalPrivilege(LocalPrivilege.PRIVILEGE_SERVICE_MANAGER, LocalPrivilege.ACCESS_LEVEL_READ_WRITE);
    }
}
