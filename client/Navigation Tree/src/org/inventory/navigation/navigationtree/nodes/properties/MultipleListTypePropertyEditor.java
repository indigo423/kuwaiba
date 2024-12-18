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
package org.inventory.navigation.navigationtree.nodes.properties;

import java.awt.Component;
import java.beans.PropertyEditorSupport;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import org.inventory.communications.CommunicationsStub;
import org.inventory.communications.core.LocalObjectListItem;
import org.inventory.core.services.api.notifications.NotificationUtil;
import org.inventory.core.services.i18n.I18N;
import org.inventory.navigation.navigationtree.nodes.UpdateObjectCallback;

/**
 * Provides a custom property editor for list-type values where you can choose more than one item
 * @author Charles Edward Bedon Cortazar {@literal <charles.bedon@kuwaiba.org>}
 */
public class MultipleListTypePropertyEditor extends PropertyEditorSupport {
    /**
     * A scroll panel containing the list of list type items
     */
    private JScrollPane pnlListTypes;
    /**
     * The list of list types
     */
    private JList<LocalObjectListItem> lstListTypes;
    /**
     * A flag to enable saving to the server based on if the user changed the initial selection
     */
    private boolean save = false;
    /**
     * A reference to the actual property
     */
    private MultipleListTypeProperty property;
    /**
     * The piece of logic to execute upon an update.
     */
    private UpdateObjectCallback updateCallback;
    
    public MultipleListTypePropertyEditor(List<LocalObjectListItem> list, List<LocalObjectListItem> initialValues, 
            MultipleListTypeProperty property, UpdateObjectCallback updateCallback) {
        this.property = property;
        this. pnlListTypes = new JScrollPane();
        this.lstListTypes = new JList<>(list.toArray(new LocalObjectListItem[0]));
        this.updateCallback = updateCallback;
        lstListTypes.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        
        List<Integer> selectedIndices = new ArrayList<>();
        
        if (initialValues != null) {
            initialValues.stream().forEach((initialValue) -> {
                int index = list.indexOf(initialValue);
                if (index != -1)
                    selectedIndices.add(index);
            });
        }
        
        lstListTypes.setSelectedIndices(selectedIndices.stream().mapToInt( i -> i ).toArray());
        
        lstListTypes.addListSelectionListener((e) -> {
            save = true;
        });
        
        this.pnlListTypes.setViewportView(lstListTypes);
    }

    @Override
    public String getAsText() {
        return "Click the button for details...";
    }
    
    @Override
    public void setValue(Object value) {
        if (save) {
            try {
                this.updateCallback.executeChange(property.getNode().getObject().getClassName(), 
                        property.getNode().getObject().getId(), property.getName(),
                        lstListTypes.getSelectedValuesList().stream().map(n -> String.valueOf(n.getId())
                                        ).collect(Collectors.joining(";")));
                property.getNode().refresh();
            } catch (IllegalArgumentException ex) {
                NotificationUtil.getInstance().showSimplePopup(I18N.gm("error"), 
                    NotificationUtil.ERROR_MESSAGE, CommunicationsStub.getInstance().getError());
            }
            
            save = false;
        }
    }
    
    @Override
    public Component getCustomEditor() {
        return pnlListTypes;
    }

    @Override
    public boolean supportsCustomEditor(){
        return true;
    }
}