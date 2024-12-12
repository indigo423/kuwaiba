/*
 *  Copyright 2010-2016 Neotropic SAS <contact@neotropic.co>.
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
 *  under the License.
 */
package org.inventory.core.templates.nodes.actions;

/**
 * Factory for all actions to be used by nodes in this module
 * @author Charles Edward Bedon Cortazar <charles.bedon@kuwaiba.org>
 */
public class TemplateActionsFactory {
    static CreateTemplateAction createTemplateAction;
    static CreateTemplateElementAction createTemplateElementAction;
    static DeleteTemplateElementAction deleteTemplateElementAction;
    
    public static CreateTemplateAction getCreateTemplateAction() {
        if (createTemplateAction == null)
            createTemplateAction = new CreateTemplateAction();
        return createTemplateAction;
    }
    
    public static CreateTemplateElementAction getCreateTemplateElementAction() {
        if (createTemplateElementAction == null)
            createTemplateElementAction = new CreateTemplateElementAction();
        return createTemplateElementAction;
    }
    
    public static DeleteTemplateElementAction getDeleteTemplateElementAction() {
        if (deleteTemplateElementAction == null)
            deleteTemplateElementAction = new DeleteTemplateElementAction();
        return deleteTemplateElementAction;
    }
}
