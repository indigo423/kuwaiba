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
package com.neotropic.kuwaiba.modules.commercial.processman.actions;

import com.neotropic.kuwaiba.modules.commercial.processman.elementUi.ButtonElementUi;
import com.neotropic.kuwaiba.modules.commercial.processman.elementUi.CheckBoxElementUi;
import com.neotropic.kuwaiba.modules.commercial.processman.elementUi.ComboBoxElementUi;
import com.neotropic.kuwaiba.modules.commercial.processman.elementUi.ElementUi;
import com.neotropic.kuwaiba.modules.commercial.processman.elementUi.GridElementUi;
import com.neotropic.kuwaiba.modules.commercial.processman.elementUi.HorizontalLayoutElementUi;
import com.neotropic.kuwaiba.modules.commercial.processman.elementUi.LabelElementUi;
import com.neotropic.kuwaiba.modules.commercial.processman.elementUi.TextFieldElementUi;
import com.neotropic.kuwaiba.modules.commercial.processman.elementUi.VerticalLayoutElementUi;
import javax.annotation.PostConstruct;
import org.neotropic.kuwaiba.core.apis.integration.modules.ModuleActionException;
import org.neotropic.kuwaiba.core.apis.integration.modules.actions.AbstractAction;
import org.neotropic.kuwaiba.core.apis.integration.modules.actions.ActionResponse;
import org.neotropic.kuwaiba.core.apis.persistence.application.Privilege;
import org.neotropic.kuwaiba.core.i18n.TranslationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Delete the form elements.
 * @author Mauricio Ruiz Beltrán {@literal <mauricio.ruiz@kuwaiba.org>}
 */
@Component
public class DeleteElementAction extends AbstractAction {
    /**
     * Reference to the translation service.
     */
    @Autowired
    private TranslationService ts;
    
    @PostConstruct
    protected void init() {
        this.id  = "processeditor-delete-element";
        this.displayName = ts.getTranslatedString("module.processeditor.editor-form-type-artifact-actions.delete-element-name");
        this.description = ts.getTranslatedString("module.processeditor.editor-form-type-artifact-actions.delete-element-descriptionn");
        
        setCallback(parameters -> {
            try {
                ElementUi elementUi = (ElementUi) parameters.get("element");

                if (elementUi instanceof LabelElementUi) {
                    LabelElementUi labelElementUi = (LabelElementUi) elementUi;
                    labelElementUi.getElement().removeFromParent();
                } else if (elementUi instanceof TextFieldElementUi) {
                    TextFieldElementUi textFieldElementUi = (TextFieldElementUi) elementUi;
                    textFieldElementUi.getElement().removeFromParent();
                } else if (elementUi instanceof ButtonElementUi) {
                    ButtonElementUi buttonElementUi = (ButtonElementUi) elementUi;
                    buttonElementUi.getElement().removeFromParent();
                } else if (elementUi instanceof CheckBoxElementUi) {
                    CheckBoxElementUi checkBoxElementUi = (CheckBoxElementUi) elementUi;
                    checkBoxElementUi.getElement().removeFromParent();
                } else if (elementUi instanceof ComboBoxElementUi) {
                    ComboBoxElementUi comboBoxElementUi = (ComboBoxElementUi) elementUi;
                    comboBoxElementUi.getElement().removeFromParent();
                }  else if (elementUi instanceof GridElementUi) {
                    GridElementUi gridElementUi = (GridElementUi) elementUi;
                    gridElementUi.getElement().removeFromParent();
                }  else if (elementUi instanceof HorizontalLayoutElementUi) {
                    HorizontalLayoutElementUi horizontalLayoutElementUi = (HorizontalLayoutElementUi) elementUi;
                    horizontalLayoutElementUi.getElement().removeFromParent();
                }  else if (elementUi instanceof VerticalLayoutElementUi) {
                    VerticalLayoutElementUi verticalLayoutElementUi = (VerticalLayoutElementUi) elementUi;
                    verticalLayoutElementUi.getElement().removeFromParent();
                }             
            } catch (Exception ex) {
                throw new ModuleActionException(ex.getMessage());
            }
            return new ActionResponse();
        });
    }
    
    @Override
    public int getRequiredAccessLevel() {
        return Privilege.ACCESS_LEVEL_READ_WRITE;
    }

    @Override
    public boolean requiresConfirmation() {
        return false;
    }   
}