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

import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import org.inventory.communications.core.LocalPrivilege;
import org.inventory.communications.core.LocalValidator;
import org.inventory.communications.util.Constants;
import org.inventory.core.services.i18n.I18N;
import org.inventory.navigation.navigationtree.nodes.actions.ActionsGroupType;
import org.inventory.navigation.navigationtree.nodes.actions.GenericObjectNodeAction;
import org.kuwaiba.management.services.views.endtoend.EndToEndViewScene;
import org.kuwaiba.management.services.views.endtoend.EndToEndViewTopComponent;
import org.openide.util.lookup.ServiceProvider;
import org.openide.windows.TopComponent;

/**
 * Opens an end-to-end view of the service, by trying to match the endpoints of the 
 * logical circuits directly associated to the selected instance
 * @author Charles Edward Bedon Cortazar {@literal <charles.bedon@kuwaiba.org>}
 */
@ActionsGroupType(group=ActionsGroupType.Group.OPEN_VIEW)
@ServiceProvider(service = GenericObjectNodeAction.class)
public class ShowEndToEndViewAction extends GenericObjectNodeAction {

    public ShowEndToEndViewAction() {
        putValue(NAME, "Show  End-to-End View");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (selectedObjects.size() != 1)
            JOptionPane.showMessageDialog(null, "Select only one service node.", I18N.gm("error"), JOptionPane.ERROR_MESSAGE);
        else{
            //TopComponent endToEndTC = ((EndToEndViewTopComponent)WindowManager.getDefault().
            //  findTopComponent("ObjectViewTopComponent_" + selectedObjects.get(0).getId()));
            
            TopComponent endToEndTC = new EndToEndViewTopComponent(selectedObjects.get(0), new EndToEndViewScene());
            endToEndTC.open();
            endToEndTC.requestActive();
        }
    }
    
    @Override
    public LocalValidator[] getValidators() {
        return null;
    }

    @Override
    public LocalPrivilege getPrivilege() {
        return new LocalPrivilege(LocalPrivilege.PRIVILEGE_SERVICE_MANAGER, LocalPrivilege.ACCESS_LEVEL_READ);
    }
    
    @Override
    public String[] appliesTo() {
        return new String[] {Constants.CLASS_GENERICSERVICE};
    }
    
    @Override
    public int numberOfNodes() {
        return 1;
    }
}
