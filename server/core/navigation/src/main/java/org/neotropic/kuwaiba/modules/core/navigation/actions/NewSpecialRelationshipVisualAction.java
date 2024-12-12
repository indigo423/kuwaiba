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
package org.neotropic.kuwaiba.modules.core.navigation.actions;

import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.textfield.TextField;
import org.neotropic.kuwaiba.core.apis.integration.modules.ModuleActionException;
import org.neotropic.kuwaiba.core.apis.integration.modules.ModuleActionParameter;
import org.neotropic.kuwaiba.core.apis.integration.modules.ModuleActionParameterSet;
import org.neotropic.kuwaiba.core.apis.integration.modules.actions.AbstractAction;
import org.neotropic.kuwaiba.core.apis.integration.modules.actions.AbstractVisualInventoryAction;
import org.neotropic.kuwaiba.core.apis.integration.modules.actions.ActionCompletedListener;
import org.neotropic.kuwaiba.core.apis.integration.modules.actions.ActionResponse;
import org.neotropic.kuwaiba.core.apis.persistence.application.ApplicationEntityManager;
import org.neotropic.kuwaiba.core.apis.persistence.business.BusinessEntityManager;
import org.neotropic.kuwaiba.core.apis.persistence.business.BusinessObjectLight;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.InventoryException;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.MetadataObjectNotFoundException;
import org.neotropic.kuwaiba.core.apis.persistence.metadata.MetadataEntityManager;
import org.neotropic.kuwaiba.core.apis.persistence.util.Constants;
import org.neotropic.kuwaiba.core.i18n.TranslationService;
import org.neotropic.kuwaiba.modules.core.navigation.NavigationModule;
import org.neotropic.util.visual.dialog.ConfirmDialog;
import org.neotropic.util.visual.notifications.AbstractNotification;
import org.neotropic.util.visual.notifications.SimpleNotification;
import org.neotropic.util.visual.selectors.NetworkResourceSelector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * UI of new special relationship action.
 *
 * @author Mauricio Ruiz Beltrán {@literal <mauricio.ruiz@kuwaiba.org>}
 */
@Component
public class NewSpecialRelationshipVisualAction extends AbstractVisualInventoryAction {

    /**
     * Reference to the translation service.
     */
    @Autowired
    private TranslationService ts;
    /**
     * Reference to the Business Entity Manager.
     */
    @Autowired
    private BusinessEntityManager bem;
    /**
     * Reference to the Application Entity Manager.
     */
    @Autowired
    private ApplicationEntityManager aem;
    /**
     * Reference to the Metadata Entity Manager.
     */
    @Autowired
    private MetadataEntityManager mem;
    /**
     * Reference to the underlying action.
     */
    @Autowired
    private NewSpecialRelationshipAction newSpecialRelationshipAction;

    public NewSpecialRelationshipVisualAction() {
        super(NavigationModule.MODULE_ID);
    }

    @Override
    public Dialog getVisualComponent(ModuleActionParameterSet parameters) {
        String paramObject = "object";
        BusinessObjectLight object = (BusinessObjectLight) parameters.get(paramObject);

        ConfirmDialog wdwRelate = new ConfirmDialog(ts, this.getModuleAction().getDisplayName());

        TextField txtName = new TextField(ts.getTranslatedString("module.general.labels.name"));
        txtName.setRequiredIndicatorVisible(true);
        txtName.setSizeFull();
        wdwRelate.add(txtName);

        AtomicReference<BusinessObjectLight> selectedObject = new AtomicReference<>();
        AtomicReference<List<BusinessObjectLight>> selectedObjects = new AtomicReference<>(new ArrayList<>());
        try {
            //Builds the selector that allows to search for a network resource, selecting a parent and browsing its children. Applies to inventory objects.
            NetworkResourceSelector networkResourceSelector = new NetworkResourceSelector(
                    selectedObject.get(), selectedObjects.get(),
                    ts.getTranslatedString("module.navigation.actions.new-special-relationship.placeholder"),
                    aem, bem, mem, ts);
            networkResourceSelector.setId("network-resource-selector");

            networkResourceSelector.addSelectedObjectChangeListener(event -> {
                selectedObject.set(event.getSelectedObject());
                selectedObjects.set(event.getSelectedObjects());
            });

            wdwRelate.add(networkResourceSelector);
        } catch (InventoryException ex) {
            new SimpleNotification(ts.getTranslatedString("module.general.messages.error"),
                    ex.getMessage(), AbstractNotification.NotificationType.ERROR, ts).open();
        }

        wdwRelate.getBtnConfirm().addClickListener(event -> {
            try {
                if (txtName.getValue() == null || txtName.getValue().trim().isEmpty())
                    notificationEmptyFields(ts, ts.getTranslatedString("module.general.labels.name"));
                else if (selectedObject.get() == null)
                    notificationEmptyFields(ts, ts.getTranslatedString("module.navigation.actions.new-special-relationship.label-target-object"));
                else if (mem.isSubclassOf(Constants.CLASS_INVENTORYOBJECT,
                        selectedObject.get().getClassName())) {
                    ModuleActionParameterSet actionParameters = new ModuleActionParameterSet(
                            new ModuleActionParameter<>(NewSpecialRelationshipAction.PARAM_OBJECT_CLASS, object.getClassName()),
                            new ModuleActionParameter<>(NewSpecialRelationshipAction.PARAM_OBJECT_ID, object.getId()),
                            new ModuleActionParameter<>(NewSpecialRelationshipAction.PARAM_OTHER_OBJECT_CLASS, selectedObject.get().getClassName()),
                            new ModuleActionParameter<>(NewSpecialRelationshipAction.PARAM_OTHER_OBJECT_ID, selectedObject.get().getId()),
                            new ModuleActionParameter<>(NewSpecialRelationshipAction.PARAM_RELATIONSHIP_NAME, txtName.getValue())
                    );

                    ActionResponse actionResponse = newSpecialRelationshipAction.getCallback().execute(actionParameters);
                    actionResponse.put(ActionResponse.ActionType.RELATE, "");
                    actionResponse.put(paramObject, object);
                    actionResponse.put(NewSpecialRelationshipAction.PARAM_RELATIONSHIP_NAME, txtName.getValue());

                    fireActionCompletedEvent(new ActionCompletedListener.ActionCompletedEvent(
                            ActionCompletedListener.ActionCompletedEvent.STATUS_SUCCESS,
                            String.format(ts.getTranslatedString("module.navigation.actions.new-special-relationship.success"),
                                    txtName.getValue(), object, selectedObject),
                            NewSpecialRelationshipAction.class,
                            actionResponse));

                    wdwRelate.close();
                }
            } catch (MetadataObjectNotFoundException | ModuleActionException ex) {
                new SimpleNotification(ts.getTranslatedString("module.general.messages.error"),
                        ex.getMessage(), AbstractNotification.NotificationType.ERROR, ts).open();
            }
        });

        wdwRelate.setId("wdw-relate");
        wdwRelate.setMinHeight("20%");
        return wdwRelate;
    }

    private void notificationEmptyFields(TranslationService ts, String field) {
        new SimpleNotification(ts.getTranslatedString("module.general.messages.warning"),
                String.format(ts.getTranslatedString("module.general.messages.attribute-not-empty"), field),
                AbstractNotification.NotificationType.WARNING, ts).open();
    }

    @Override
    public AbstractAction getModuleAction() {
        return newSpecialRelationshipAction;
    }

    @Override
    public int getRequiredSelectedObjects() {
        return 1;
    }
}