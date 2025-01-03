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

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.server.Command;

/**
 *
 * @author Mauricio Ruiz Beltrán {@literal <mauricio.ruiz@kuwaiba.org>}
 */
public class ButtonElementUi extends Button implements ElementUi {

    private String elementUiId;
    private String elementUiWidth;
    private String elementUiHeight;
    private String elementUiMinWidth;
    private String elementUiCaption;
    private String elementUiOnClick;
    private String elementUiStyleName;
    private String elementUiOnLoad;
    private String elementUiOnPropertyChange;
    
    public ButtonElementUi() { }
        
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
    public String getElementUiHeight() {
        return elementUiHeight;
    }

    @Override
    public void setElementUiHeight(String elementUiHeigh) {
        this.elementUiHeight = elementUiHeigh;
    }   
    
    @Override
    public String getElementUiMinWidth() {
        return elementUiMinWidth;
    }

    @Override
    public void setElementUiMinWidth(String elementUiMinWidth) {
        this.elementUiMinWidth = elementUiMinWidth;
    }

    public String getElementUiCaption() {
        return elementUiCaption;
    }

    public void setElementUiCaption(String elementUiCaption) {
        this.elementUiCaption = elementUiCaption;
    }

    public String getElementUiOnClick() {
        return elementUiOnClick;
    }

    public void setElementUiOnClick(String elementUiOnClick) {
        this.elementUiOnClick = elementUiOnClick;
    }

    public String getElementUiStyleName() {
        return elementUiStyleName;
    }

    public void setElementUiStyleName(String elementUiStyleName) {
        this.elementUiStyleName = elementUiStyleName;
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
    
    @Override
    public void addEventListener(Command command) {
        getElement().addEventListener("click", event -> {
            command.execute(); 
        });
    }
}