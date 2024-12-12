/*
 * Copyright 2010-2024 Neotropic SAS <contact@neotropic.co>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.neotropic.kuwaiba.modules.commercial.processman.elementUi;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.server.Command;

/**
 *
 * @author Mauricio Ruiz Beltrán {@literal <mauricio.ruiz@kuwaiba.org>}
 */
public class ComboBoxElementUi extends ComboBox implements ElementUi {

    private String elementUiId;
    private String elementUiWidth;
    private String elementUiHeight;
    private String elementUiMinWidth;
    private String elementUiValue;
    private String elementUiItems;
    private String elementUiShared;
    private String elementUiOnLazyLoad;
    private String elementUiPropertyChangeListener;
    private String elementUiOnLoad;
    private String elementUiOnPropertyChange;
    private String elementUiDataType;
    
    public ComboBoxElementUi() { }
    
    @Override
    public String getElementUiId() {
        return elementUiId;
    }

    @Override
    public void setElementUiId(String elementUiId) {
        this.elementUiId = elementUiId;
    }

    @Override
    public String getElementUiWidth() {
        return elementUiWidth;
    }

    @Override
    public void setElementUiWidth(String elementUiWidth) {
        this.elementUiWidth = elementUiWidth;
    }
    
    @Override
    public String getElementUiMinWidth() {
        return elementUiMinWidth;
    }

    @Override
    public void setElementUiMinWidth(String elementUiMinWidth) {
        this.elementUiMinWidth = elementUiMinWidth;
    }

    @Override
    public String getElementUiHeight() {
        return elementUiHeight;
    }

    @Override
    public void setElementUiHeight(String elementUiHeigh) {
        this.elementUiHeight = elementUiHeigh;
    } 

    public String getElementUiValue() {
        return elementUiValue;
    }

    public void setElementUiValue(String elementUiValue) {
        this.elementUiValue = elementUiValue;
    }

    public String getElementUiItems() {
        return elementUiItems;
    }

    public void setElementUiItems(String elementUiItems) {
        this.elementUiItems = elementUiItems;
    }
    
    public String getElementUiShared() {
        return elementUiShared;
    }

    public void setElementUiShared(String elementUiShared) {
        this.elementUiShared = elementUiShared;
    }

    public String getElementUiOnLazyLoad() {
        return elementUiOnLazyLoad;
    }

    public void setElementUiOnLazyLoad(String elementUiOnLazyLoad) {
        this.elementUiOnLazyLoad = elementUiOnLazyLoad;
    }

    public String getElementUiPropertyChangeListener() {
        return elementUiPropertyChangeListener;
    }

    public void setElementUiPropertyChangeListener(String elementUiPropertyChangeListener) {
        this.elementUiPropertyChangeListener = elementUiPropertyChangeListener;
    }

    public String getElementUiOnLoad() {
        return elementUiOnLoad;
    }

    public void setElementUiOnLoad(String elementUiOnLoad) {
        this.elementUiOnLoad = elementUiOnLoad;
    }

    public String getElementUiOnPropertyChange() {
        return elementUiOnPropertyChange;
    }

    public void setElementUiOnPropertyChange(String elementUiOnPropertyChange) {
        this.elementUiOnPropertyChange = elementUiOnPropertyChange;
    }

    public String getElementUiDataType() {
        return elementUiDataType;
    }

    public void setElementUiDataType(String elementUiDataType) {
        this.elementUiDataType = elementUiDataType;
    }
    
    @Override
    public void addEventListener(Command command) {
        getElement().addEventListener("click", event -> {
            command.execute(); 
        });
    }
}