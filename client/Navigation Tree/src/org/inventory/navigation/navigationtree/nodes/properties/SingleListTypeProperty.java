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
package org.inventory.navigation.navigationtree.nodes.properties;

import java.beans.PropertyEditor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import org.inventory.communications.CommunicationsStub;
import org.inventory.communications.core.LocalObjectListItem;
import org.inventory.core.services.api.notifications.NotificationUtil;
import org.inventory.navigation.navigationtree.nodes.ObjectNode;
import org.inventory.navigation.navigationtree.nodes.UpdateObjectCallback;
import org.openide.nodes.PropertySupport;

/**
 * A dedicated property class for list type attributes
 * @author Charles Edward Bedon Cortazar {@literal <charles.bedon@kuwaiba.org>}
 */
public class SingleListTypeProperty extends PropertySupport.ReadWrite<LocalObjectListItem> {
    private PropertyEditor propertyEditor;
    private LocalObjectListItem value;
    private ObjectNode node;
    private UpdateObjectCallback updateObjectCallback;

    public SingleListTypeProperty(String name, String displayName, String toolTextTip, 
            List<LocalObjectListItem> list, ObjectNode node, LocalObjectListItem value, UpdateObjectCallback updateObjectCallback) {
        super(name, LocalObjectListItem.class, displayName, toolTextTip);
        this.propertyEditor = new SingleListTypePropertyEditor(list, this);
        this.value = value == null ? new LocalObjectListItem() : value;
        this.node = node;
        this.updateObjectCallback = updateObjectCallback;
    }

    @Override
    public LocalObjectListItem getValue() throws IllegalAccessException, InvocationTargetException {        
       return value;
    }

    @Override
    public void setValue(LocalObjectListItem value) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        try {
            this.updateObjectCallback.executeChange(node.getObject().getClassName(), node.getObject().getId(), getName(), value);
            this.value = value;
        } catch (IllegalArgumentException ex) {
            NotificationUtil.getInstance().showSimplePopup("Error", 
                    NotificationUtil.ERROR_MESSAGE, CommunicationsStub.getInstance().getError());
        }
    }

    @Override
    public PropertyEditor getPropertyEditor(){
        return propertyEditor;
    }
}