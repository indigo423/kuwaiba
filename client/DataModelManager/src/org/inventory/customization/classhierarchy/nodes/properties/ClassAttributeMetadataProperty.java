/*
 *  Copyright 2010-2020 Neotropic SAS <contact@neotropic.co>
 *
 *  Licensed under the EPL License, Version 1.0 (the "License");
 *  you may not use this file except in compliance with the License
 *  You may obtain a copy of the License at
 *
 *       http://www.eclipse.org/legal/epl-v10.html
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.inventory.customization.classhierarchy.nodes.properties;

import java.beans.PropertyEditor;
import java.lang.reflect.InvocationTargetException;
import org.inventory.communications.core.LocalAttributeMetadata;
import org.inventory.core.services.i18n.I18N;
import org.inventory.customization.classhierarchy.nodes.ClassMetadataNode;
import org.openide.nodes.PropertySupport;

/**
 * Provides a property editor
 * @author Adrian Martinez Molina {@literal <charles.bedon@kuwaiba.org>}
 */
public class ClassAttributeMetadataProperty extends PropertySupport.ReadWrite {

    private ClassMetadataNode classNode;
    private LocalAttributeMetadata attributeMetadata;
    
    public ClassAttributeMetadataProperty(LocalAttributeMetadata attributeMetadata, ClassMetadataNode classNode) {
        super(attributeMetadata.getName(), String.class, 
                (attributeMetadata.isMandatory() ? "\u002A" : "") + attributeMetadata.getName(), //this mark the mandatory attributes with a *
                I18N.gm("click_to_edit"));
        this.attributeMetadata = attributeMetadata;
        this.classNode = classNode;
    }
    
    @Override
    public Object getValue() throws IllegalAccessException, InvocationTargetException {
        return "["+I18N.gm("click_to_edit")+"]";
    }
    
    @Override
    public void setValue(Object t) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        //We don't do anything here because the properties of this attribute will be set in the custom editor
    }
    
    @Override
    public PropertyEditor getPropertyEditor(){
        return new AttributeEditorSupport(this);
    }

    public LocalAttributeMetadata getAttributeMetadata() {
        return attributeMetadata;
    }

    public void setAttributeMetadata(LocalAttributeMetadata attributeMetadata) {
        this.attributeMetadata = attributeMetadata;
    }

    public ClassMetadataNode getClassNode() {
        return classNode;
    }
}