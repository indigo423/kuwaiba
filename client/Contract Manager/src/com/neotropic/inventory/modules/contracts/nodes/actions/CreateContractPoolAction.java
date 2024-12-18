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
package com.neotropic.inventory.modules.contracts.nodes.actions;

import com.neotropic.inventory.modules.contracts.nodes.ContractManagerRootNode;
import java.awt.event.ActionEvent;
import java.util.Iterator;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import org.inventory.communications.CommunicationsStub;
import org.inventory.communications.core.LocalClassMetadataLight;
import org.inventory.communications.core.LocalPool;
import org.inventory.communications.core.LocalPrivilege;
import org.inventory.communications.util.Constants;
import org.inventory.core.services.api.actions.GenericInventoryAction;
import org.inventory.core.services.api.notifications.NotificationUtil;
import org.inventory.core.services.i18n.I18N;
import org.inventory.core.services.utils.JComplexDialogPanel;
import org.openide.util.Utilities;

/**
 * This action allows to create a contract pool
 * @author Charles Edward Bedon Cortazar {@literal <charles.bedon@kuwaiba.org>}
 */
public class CreateContractPoolAction extends GenericInventoryAction {
    private CommunicationsStub com = CommunicationsStub.getInstance();
    public CreateContractPoolAction() {
        putValue(NAME, I18N.gm("new_contract_pool"));
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        Iterator<? extends ContractManagerRootNode> selectedNodes = 
                Utilities.actionsGlobalContext().lookupResult(ContractManagerRootNode.class).allInstances().iterator();
            
        if (!selectedNodes.hasNext())
            return;
        
        ContractManagerRootNode selectedNode = selectedNodes.next();
        
        List<LocalClassMetadataLight> possibleContractClasses = com.getLightSubclasses(Constants.CLASS_GENERICCONTRACT, false, false);
        if (possibleContractClasses == null)
            NotificationUtil.getInstance().showSimplePopup(I18N.gm("error"), NotificationUtil.ERROR_MESSAGE, com.getError());
        else {       
            JTextField txtPoolName = new JTextField();
            txtPoolName.setName("txtPoolName");
            txtPoolName.setColumns(10);
            
            JComboBox cmbPoolType = new JComboBox(possibleContractClasses.toArray(new LocalClassMetadataLight[0]));
            cmbPoolType.setName("cmbPoolType");
            
            JTextField txtPoolDescription = new JTextField();
            txtPoolDescription.setName("txtPoolDescription");
            txtPoolDescription.setColumns(10);
            
            JComplexDialogPanel pnlPoolProperties = new JComplexDialogPanel(new String[] {I18N.gm("pool_name"), I18N.gm("pool_type"), I18N.gm("pool_description") }, 
                    new JComponent[] { txtPoolName, cmbPoolType, txtPoolDescription });
            
            if (JOptionPane.showConfirmDialog(null, pnlPoolProperties, I18N.gm("new_contract_pool"), JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
                
                LocalClassMetadataLight poolType = (LocalClassMetadataLight)((JComboBox)pnlPoolProperties.getComponent("cmbPoolType")).getSelectedItem();
                
                LocalPool newPool = com.createRootPool(((JTextField)pnlPoolProperties.getComponent("txtPoolName")).getText(), 
                                        ((JTextField)pnlPoolProperties.getComponent("txtPoolDescription")).getText(), 
                                        poolType == null ? Constants.CLASS_GENERICCONTRACT : poolType.getClassName(), 
                                        LocalPool.POOL_TYPE_MODULE_ROOT);
                
                if (newPool == null)
                    NotificationUtil.getInstance().showSimplePopup(I18N.gm("error"), NotificationUtil.ERROR_MESSAGE, com.getError());
                else {       
                    ((ContractManagerRootNode.ContractManagerRootChildren)selectedNode.getChildren()).addNotify();
                    NotificationUtil.getInstance().showSimplePopup(I18N.gm("information"), NotificationUtil.INFO_MESSAGE, I18N.gm("contract_pool_created"));
                }
            }
        }
    }    

    @Override
    public LocalPrivilege getPrivilege() {
        return new LocalPrivilege(LocalPrivilege.PRIVILEGE_CONTRACT_MANAGER, LocalPrivilege.ACCESS_LEVEL_READ_WRITE);
    }
}
