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
package com.neotropic.inventory.modules.ipam.nodes.properties;

import java.beans.PropertyEditor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import org.inventory.communications.CommunicationsStub;
import org.inventory.communications.core.LocalObjectListItem;
import org.inventory.core.services.api.notifications.NotificationUtil;
import org.inventory.navigation.navigationtree.nodes.ObjectNode;
import org.openide.nodes.PropertySupport;

/**
 * A dedicated property class for list type attributes
 * @author Charles Edward Bedon Cortazar {@literal <charles.bedon@kuwaiba.org>}
 */
public class ListTypeProperty extends PropertySupport.ReadWrite<LocalObjectListItem> {
    
    private PropertyEditor propertyEditor;
    private LocalObjectListItem value;
    private ObjectNode node;

    public ListTypeProperty(String name, String displayName, String toolTextTip, 
            List<LocalObjectListItem> list, ObjectNode node, LocalObjectListItem value) {
        super(name, LocalObjectListItem.class, displayName, toolTextTip);
        this.propertyEditor = new ItemListPropertyEditor(list);
        this.value = value == null ? new LocalObjectListItem() : value;
        this.node = node;
    }

    @Override
    public LocalObjectListItem getValue() throws IllegalAccessException, InvocationTargetException {        
       return value;
    }

    @Override
    public void setValue(LocalObjectListItem t) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        HashMap<String, Object> attributesToUpdate = new HashMap<>();
        attributesToUpdate.put(getName(), t);

        if(!CommunicationsStub.getInstance().updateObject(node.getObject().getClassName(), node.getObject().getId(), attributesToUpdate))
            NotificationUtil.getInstance().showSimplePopup("Error", 
                    NotificationUtil.ERROR_MESSAGE, CommunicationsStub.getInstance().getError());
        else
            value = t;
    }

    
    @Override
    public PropertyEditor getPropertyEditor(){
        return propertyEditor;
    }
}