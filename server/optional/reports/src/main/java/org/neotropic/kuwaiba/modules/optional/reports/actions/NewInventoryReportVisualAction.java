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
package org.neotropic.kuwaiba.modules.optional.reports.actions;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.textfield.TextField;
import java.util.Arrays;
import org.neotropic.kuwaiba.core.apis.integration.modules.actions.AbstractAction;
import org.neotropic.kuwaiba.core.apis.integration.modules.actions.AbstractVisualAction;
import org.neotropic.kuwaiba.core.apis.integration.modules.actions.ActionCompletedListener;
import org.neotropic.kuwaiba.core.apis.integration.modules.ModuleActionException;
import org.neotropic.kuwaiba.core.apis.integration.modules.ModuleActionParameter;
import org.neotropic.kuwaiba.core.apis.integration.modules.ModuleActionParameterSet;
import org.neotropic.kuwaiba.core.apis.persistence.application.ApplicationEntityManager;
import org.neotropic.kuwaiba.core.apis.persistence.application.reporting.ReportMetadataLight;
import org.neotropic.kuwaiba.core.apis.persistence.business.BusinessEntityManager;
import org.neotropic.kuwaiba.core.apis.persistence.metadata.MetadataEntityManager;
import org.neotropic.kuwaiba.core.i18n.TranslationService;
import org.neotropic.kuwaiba.modules.optional.reports.ReportsModule;
import org.neotropic.util.visual.dialog.ConfirmDialog;
import org.neotropic.util.visual.notifications.AbstractNotification;
import org.neotropic.util.visual.notifications.SimpleNotification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Visual wrapper of create a new inventory report action.
 * 
 * @author Orlando Paz {@literal <orlando.paz@kuwaiba.org>}
 * @author Mauricio Ruiz Beltrán {@literal <mauricio.ruiz@kuwaiba.org>}
 */
@Component
public class NewInventoryReportVisualAction extends AbstractVisualAction<Dialog> {
    /**
     * Reference to the translation service.
     */
    @Autowired
    private TranslationService ts;
    /**
     * Reference to the underlying action.
     */
    @Autowired
    private NewInventoryReportAction newInventoryReportAction;
    /**
     * Reference to the metadata entity manager.
     */
    @Autowired
    protected MetadataEntityManager mem;
    /**
     * Reference to the application entity manager.
     */
    @Autowired
    protected ApplicationEntityManager aem;
    /**
     * Reference to the business entity manager.
     */
    @Autowired
    protected BusinessEntityManager bem;

    public NewInventoryReportVisualAction() {
        super(ReportsModule.MODULE_ID);
    }
    
    @Override
    public Dialog getVisualComponent(ModuleActionParameterSet parameters) {
        TextField txtName = new TextField(ts.getTranslatedString("module.general.labels.name"));
        txtName.setRequiredIndicatorVisible(true);
        txtName.setSizeFull();
        
        TextField txtDescription = new TextField(ts.getTranslatedString("module.general.labels.description"));
        txtDescription.setSizeFull();
        
        ComboBox<Integer> cmbType = new ComboBox<>(ts.getTranslatedString("module.general.labels.type"));
        cmbType.setItems(Arrays.asList(ReportMetadataLight.TYPE_HTML,
                                       ReportMetadataLight.TYPE_CSV,                                     
                                       ReportMetadataLight.TYPE_PDF,
                                       ReportMetadataLight.TYPE_XLSX,
                                       ReportMetadataLight.TYPE_OTHER));
        cmbType.setValue(ReportMetadataLight.TYPE_HTML);
        cmbType.setItemLabelGenerator(ReportMetadataLight::getTypeAsString);
        cmbType.setWidthFull();
        
        // To show errors or warnings related to the input parameters.
        ConfirmDialog wdwNewReport = new ConfirmDialog(ts, newInventoryReportAction.getDisplayName());
        wdwNewReport.getBtnConfirm().addClickListener(event -> {
            try {
                if (txtName.getValue().trim().isEmpty()) 
                    notificationEmptyFields(ts.getTranslatedString("module.general.labels.name"));
                else if (cmbType.getValue() == null)
                    notificationEmptyFields(ts.getTranslatedString("module.general.labels.type"));
                else {
                    newInventoryReportAction.getCallback().execute(new ModuleActionParameterSet(
                            new ModuleActionParameter<>("reportName", txtName.getValue()),
                            new ModuleActionParameter<>("type", cmbType.getValue()),
                            new ModuleActionParameter<>("description", txtDescription.getValue())));
                    
                    fireActionCompletedEvent(new ActionCompletedListener.ActionCompletedEvent(
                            ActionCompletedListener.ActionCompletedEvent.STATUS_SUCCESS,
                            ts.getTranslatedString("module.reporting.actions.report-created"),
                            NewClassReportAction.class));
                    wdwNewReport.close();
                }
            } catch (ModuleActionException ex) {
                fireActionCompletedEvent(new ActionCompletedListener.ActionCompletedEvent(
                        ActionCompletedListener.ActionCompletedEvent.STATUS_ERROR,
                        ex.getMessage(), NewClassReportAction.class));
            }
        });
        
        wdwNewReport.setContent(txtName, txtDescription, cmbType);
        return wdwNewReport;        
    }
    
    private void notificationEmptyFields(String field) {
        new SimpleNotification(ts.getTranslatedString("module.general.messages.warning"),
                String.format(ts.getTranslatedString("module.general.messages.attribute-not-empty"), field),
                AbstractNotification.NotificationType.WARNING, ts).open();
    }
    
    @Override
    public AbstractAction getModuleAction() {
        return newInventoryReportAction;
    }
}