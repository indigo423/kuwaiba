/*
 *  Copyright 2010-2020, Neotropic SAS <contact@neotropic.co>
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
import org.inventory.communications.CommunicationsStub;
import org.inventory.communications.util.Constants;
import org.inventory.core.services.api.notifications.NotificationUtil;
import org.inventory.navigation.navigationtree.nodes.ObjectNode;
import org.inventory.navigation.navigationtree.nodes.UpdateObjectCallback;
import org.openide.nodes.PropertySupport;

/**
 * Provides a valid representation of LocalObjects attributes as Properties,
 * as LocalObject is just a proxy and can't be a bean itself
 * @author Charles Edward Bedon Cortazar {@literal <charles.bedon@kuwaiba.org>}
 */
public class NativeTypeProperty extends PropertySupport.ReadWrite {
    private ObjectNode node;
    private Object value;
    private UpdateObjectCallback updateObjectCallback;
    
    public NativeTypeProperty(String name, Class valueType, String displayName,
            String toolTextTip, ObjectNode node, Object value, UpdateObjectCallback updateObjectCallback) {
        super(name, valueType, displayName, toolTextTip);
        this.node = node;
        this.value = value;
        this.updateObjectCallback = updateObjectCallback;
    }
    
    @Override
    public Object getValue() throws IllegalAccessException, InvocationTargetException {        
       if (value == null && getValueType() == Boolean.class)
           return false;
       return value;
    }

    @Override
    public void setValue(Object t) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (t == null || t.equals(value) || t.equals("<null value>")) //Don't update if no changes were performed
            return;
        try {
            this.updateObjectCallback.executeChange(node.getObject().getClassName(), node.getObject().getId(), getName(), t);
            value = t;
            if (getName().equals(Constants.PROPERTY_NAME))
                node.getObject().setName((String)t);
        } catch (IllegalArgumentException ex) {
            NotificationUtil.getInstance().showSimplePopup("Error", 
                    NotificationUtil.ERROR_MESSAGE, CommunicationsStub.getInstance().getError());
        }
    }

    @Override
    public PropertyEditor getPropertyEditor(){        
        return super.getPropertyEditor();
    }
}