/*
 *  Copyright 2010-2024 Neotropic SAS <contact@neotropic.co>.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       https://apache.org/licenses/LICENSE-2.0.txt
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.neotropic.kuwaiba.modules.core.navigation.actions;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.neotropic.kuwaiba.core.apis.integration.modules.ModuleActionParameterSet;
import org.neotropic.kuwaiba.core.apis.integration.modules.actions.AbstractAction;
import org.neotropic.kuwaiba.core.apis.integration.modules.actions.AbstractVisualAction;
import org.neotropic.kuwaiba.core.apis.persistence.business.BusinessEntityManager;
import org.neotropic.kuwaiba.core.apis.persistence.business.BusinessObjectLight;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.BusinessObjectNotFoundException;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.InvalidArgumentException;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.MetadataObjectNotFoundException;
import org.neotropic.kuwaiba.core.apis.persistence.util.Constants;
import org.neotropic.kuwaiba.core.i18n.TranslationService;
import org.neotropic.kuwaiba.modules.core.navigation.NavigationModule;
import org.neotropic.util.visual.button.ActionButton;
import org.neotropic.util.visual.dialog.ConfirmDialog;
import org.neotropic.util.visual.icon.ActionIcon;
import org.neotropic.util.visual.notifications.AbstractNotification;
import org.neotropic.util.visual.notifications.SimpleNotification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Window to show more information about an object.
 * @author Mauricio Ruiz Beltrán {@literal <mauricio.ruiz@kuwaiba.org>}
 */
@CssImport("./styles/dialog.css")
@CssImport(value = "./css/poolConfigurationDialog.css")
@Component
public class ShowMoreInformationAction extends AbstractVisualAction<Dialog> {
    /**
     * Reference to the translation service.
     */
    @Autowired
    private TranslationService ts;
    /**
     * Reference to Business Entity Manager
     */
    @Autowired
    private BusinessEntityManager bem;

    public ShowMoreInformationAction() {
        super(NavigationModule.MODULE_ID);
    }
    
    @Override
    public Dialog getVisualComponent(ModuleActionParameterSet parameters) {
        BusinessObjectLight selectedObject;
        if (parameters.containsKey("object")) {
            selectedObject = (BusinessObjectLight) parameters.get("object");
            ConfirmDialog wdwInfo = new ConfirmDialog(ts,
                     String.format(ts.getTranslatedString("module.navigation.actions.show-more-information"),
                             selectedObject.getName()));
            wdwInfo.getBtnConfirm().setVisible(false);
            // Validation
            List<BusinessObjectLight> parents;
            String msg = "";
            try {
                parents = bem.getParents(selectedObject.getClassName(), selectedObject.getId());
                if (parents != null && !parents.isEmpty()) {
                    for (BusinessObjectLight parent : parents) {
                        if (!parent.getName().equals(Constants.DUMMY_ROOT))
                            msg += ": " + parent;
                        else
                            msg += ": " + ts.getTranslatedString("module.general.labels.root");
                    }
                } else
                    msg += ": " + ts.getTranslatedString("module.navigation.actions.show-more-information-label-no-parents");
            } catch (BusinessObjectNotFoundException | MetadataObjectNotFoundException | InvalidArgumentException ex) {
                new SimpleNotification(ts.getTranslatedString("module.general.messages.error"), ex.getLocalizedMessage(),
                        AbstractNotification.NotificationType.ERROR, ts).open();
            }
            // Close action
            ActionButton btnClose = new ActionButton(ts.getTranslatedString("module.general.messages.close"));
            btnClose.addClickListener(event -> wdwInfo.close());
            btnClose.setWidthFull();
            btnClose.setThemeName("primary");
            btnClose.setClassName("primary-button");
            // Labels
            Label lblId = new Label(String.format("%s:", ts.getTranslatedString("module.navigation.actions.show-more-information-label-id")));
            Label labelId = new Label(selectedObject.getId());
            Label lblClass = new Label(String.format("%s:", ts.getTranslatedString("module.navigation.actions.show-more-information-label-class-name")));
            Label labelClass = new Label(selectedObject.getClassName());
            Label lblPath = new Label(String.format("%s", ts.getTranslatedString("module.navigation.actions.show-more-information-label-parents")));
            Label labelPath = new Label(msg);
            lblId.setClassName("label");
            lblClass.setClassName("label");
            lblPath.setClassName("label");
            labelId.setClassName("label-object");
            labelClass.setClassName("label-object");
            labelPath.setClassName("label-path");
            // Copy to clipboard action
            ActionButton btnCopyToClipboard = new ActionButton(new ActionIcon(VaadinIcon.COPY),
                    ts.getTranslatedString("module.general.labels.copy-to-clipboard"));
            btnCopyToClipboard.getElement().getStyle().set("margin-left", "10px");
            btnCopyToClipboard.addClickListener(event -> {
                copyToClipboard(event, selectedObject.getId());
                new SimpleNotification(ts.getTranslatedString("module.general.messages.information"),
                        ts.getTranslatedString("module.navigation.actions.show-more-information-notification-object-id-copied"),
                        AbstractNotification.NotificationType.INFO, ts).open();
            });
            // Content layout
            HorizontalLayout lytId = new HorizontalLayout(lblId, labelId, btnCopyToClipboard);
            lytId.setSpacing(false);
            
            HorizontalLayout lytClass = new HorizontalLayout(lblClass, labelClass);
            lytClass.setSpacing(false);
            
            HorizontalLayout lytPath = new HorizontalLayout(lblPath, labelPath);
            lytPath.setSpacing(false);
            
            VerticalLayout lytContent = new VerticalLayout(lytId, lytClass, lytPath);
            lytContent.setId("lytcontent");
            lytContent.setPadding(false);
            lytContent.setSpacing(true);
            lytContent.setMargin(false);
            
            Scroller scroller = new Scroller(lytContent);
            scroller.setSizeUndefined();
                        
            // Add content to dialog
            wdwInfo.setContent(scroller);
            wdwInfo.setFooter(btnClose);
            wdwInfo.setMinWidth("40%");
            // Return window
            return wdwInfo;
        } else {
            ConfirmDialog errorDialog = new ConfirmDialog(ts, "",
                    ts.getTranslatedString("module.general.messages.object-not-found")
            );
            errorDialog.getBtnConfirm().addClickListener(e -> errorDialog.close());
            return errorDialog;
        }
    }

    @Override
    public AbstractAction getModuleAction() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public String getDisplayName() {
        return ts.getTranslatedString("module.navigation.actions.show-more-information-button-name");
    }

    private void copyToClipboard(ClickEvent<Button> event, String textValue) {
        StringBuilder javascript = new StringBuilder();
        // JavaScript code in a String
        javascript.append("    const el = document.createElement('textarea');\n");
        javascript.append("    el.value = $0;\n");
        javascript.append("    el.setAttribute('readonly', '');\n");
        javascript.append("    el.style.position = 'absolute';\n");
        javascript.append("    el.style.left = '-9999px';\n");
        javascript.append("    document.body.appendChild(el);\n");
        javascript.append("    el.select();\n");
        javascript.append("    document.execCommand('copy');\n");
        javascript.append("    document.body.removeChild(el);");
        // call function from script file
        event.getSource().getElement().executeJs(javascript.toString(), textValue);
    }
}