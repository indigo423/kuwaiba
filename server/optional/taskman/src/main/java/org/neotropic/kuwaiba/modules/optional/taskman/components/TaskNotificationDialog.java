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
package org.neotropic.kuwaiba.modules.optional.taskman.components;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import org.neotropic.kuwaiba.core.apis.integration.modules.ModuleActionParameterSet;
import org.neotropic.kuwaiba.core.apis.integration.modules.actions.AbstractAction;
import org.neotropic.kuwaiba.core.apis.integration.modules.actions.AbstractVisualAction;
import org.neotropic.kuwaiba.core.apis.persistence.application.ApplicationEntityManager;
import org.neotropic.kuwaiba.core.apis.persistence.application.ResultMessage;
import org.neotropic.kuwaiba.core.apis.persistence.application.Task;
import org.neotropic.kuwaiba.core.apis.persistence.application.TaskResult;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.ApplicationObjectNotFoundException;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.InvalidArgumentException;
import org.neotropic.kuwaiba.core.i18n.TranslationService;
import org.neotropic.kuwaiba.modules.optional.taskman.TaskManagerModule;
import org.neotropic.kuwaiba.modules.optional.taskman.providers.ResultMessageProvider;
import org.neotropic.util.visual.dialog.ConfirmDialog;
import org.neotropic.util.visual.notifications.AbstractNotification;
import org.neotropic.util.visual.notifications.SimpleNotification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.vaadin.tatu.BeanTable;
import org.vaadin.tatu.BeanTableVariant;

/**
 * Visual wrapper of show a task notification.
 *
 * @author Mauricio Ruiz Beltrán {@literal <mauricio.ruiz@kuwaiba.org>}
 */
@CssImport(value = "./css/poolConfigurationDialog.css")
@Component
public class TaskNotificationDialog extends AbstractVisualAction<Dialog> {
    /**
     * Reference to the Translation Service
     */            
    @Autowired
    private TranslationService ts;
    /**
     * Reference to the Application Entity Manager.
     */
    @Autowired
    private ApplicationEntityManager aem;
    
    public TaskNotificationDialog() {
        super(TaskManagerModule.MODULE_ID);
    }

    /**
     * Creates and returns the visual component (Dialog) to display task results or error message.
     *
     * @param parameters ModuleActionParameterSet containing the task information.
     * @return Dialog The dialog containing the task result or error message.
     */
    @Override
    public Dialog getVisualComponent(ModuleActionParameterSet parameters) {
        if (parameters.containsKey("task")) {
            Task currentTask = (Task) parameters.get("task");
            
            ConfirmDialog wdwNotification = new ConfirmDialog(ts,
                    String.format(ts.getTranslatedString("module.taskman.task.actions.execute-task-result"),
                            currentTask.getName()));
            wdwNotification.getBtnCancel().setText(ts.getTranslatedString("module.general.messages.close"));
            wdwNotification.getBtnConfirm().setVisible(false);
            wdwNotification.setWidth("80%");
            wdwNotification.setHeight("90%");
            wdwNotification.setContent(buildResultsGrid(executeTask(currentTask)));
            return wdwNotification;
        } else {
            ConfirmDialog errorDialog = new ConfirmDialog(ts, "",
                    ts.getTranslatedString("module.taskman.task.actions.delete-task-error"));
            errorDialog.getBtnConfirm().addClickListener(e -> errorDialog.close());
            return errorDialog;
        }
    }

    /**
     * Executes the given task and returns the result.
     *
     * @param task The task to be executed.
     * @return TaskResult The result of the task execution.
     */
    private TaskResult executeTask(Task task) {
        try {
            return aem.executeTask(task.getId());
        } catch (ApplicationObjectNotFoundException | InvalidArgumentException ex) {
            new SimpleNotification(ts.getTranslatedString("module.general.messages.error"),
                    ex.getLocalizedMessage(), AbstractNotification.NotificationType.ERROR, ts).open();
            return null;
        }
    }

    /**
     * Builds a grid to display the results of the task execution.
     *
     * @param taskResult The result of the task execution.
     * @return BeanTable<ResultMessage> A table displaying the result messages.
     */
    private BeanTable<ResultMessage> buildResultsGrid(TaskResult taskResult) {
        BeanTable<ResultMessage> gridResult = new BeanTable<>(ResultMessage.class, false, 20);
        gridResult.addComponentColumn(ts.getTranslatedString("module.taskman.task.actions.execute-task-header"),
                TaskNotificationDialog::getResultMessage);

        gridResult.setI18n(buildI18nForGrid());
        gridResult.setDataProvider(new ResultMessageProvider().buildDataProvider(taskResult));
        gridResult.setHeight("auto");
        gridResult.setWidthFull();

        gridResult.setClassNameProvider(result -> result.getMessage() != null && !result.getMessage().isEmpty() ? "text-result" : "");
        gridResult.addClassNames("bean-table-hide-header-index", "bean-table-hide-body-index");
        if (gridResult.getRowCount() <= 20) //when page is not necessary
            gridResult.addClassName("bean-table-hide-footer");

        gridResult.addThemeVariants(
                BeanTableVariant.NO_BORDER,
                BeanTableVariant.NO_ROW_BORDERS,
                BeanTableVariant.WRAP_CELL_CONTENT
        );

        return gridResult;
    }

    /**
     * Creates a Div containing the formatted result message.
     *
     * @param result The result message to be displayed.
     * @return Div A div containing the formatted result message.
     */
    private static Div getResultMessage(ResultMessage result) {
        Div htmlStatus = new Div();
        Span html;

        HorizontalLayout lytResult = new HorizontalLayout();
        lytResult.setPadding(true);
        lytResult.setSpacing(false);

        switch (result.getMessageType()) {
            case ResultMessage.STATUS_SUCCESS:
                html = new Span(result.getMessage());   
                htmlStatus.addClassNames("success", "task-result");
                htmlStatus.add(html);
                break;
            case ResultMessage.STATUS_WARNING:
                html = new Span(result.getMessage());  
                htmlStatus.addClassNames("warning", "task-result");
                htmlStatus.add(html);
                break;
            case ResultMessage.STATUS_ERROR:
                html = new Span(result.getMessage());  
                htmlStatus.addClassNames("error", "task-result");
                htmlStatus.add(html);
                break;
            default:
                return null;
        }
        return htmlStatus;
    }

    /**
     * Builds the internationalization framework for the table.
     *
     * @return The BeanTable I18n.
     */
    private BeanTable.BeanTableI18n buildI18nForGrid() {
        BeanTable.BeanTableI18n i18n = new BeanTable.BeanTableI18n();
        i18n.setNoDataText(ts.getTranslatedString("module.general.labels.no-data"));
        i18n.setNextPage(ts.getTranslatedString("module.general.labels.next-page"));
        i18n.setErrorText(ts.getTranslatedString("module.general.labels.error-text"));
        i18n.setLastPage(ts.getTranslatedString("module.general.labels.last-page"));
        i18n.setFirstPage(ts.getTranslatedString("module.general.labels.first-page"));
        i18n.setMenuButton(ts.getTranslatedString("module.general.labels.menu-button"));
        i18n.setPreviousPage(ts.getTranslatedString("module.general.labels.previous-page"));
        i18n.setPageProvider((currentPage, lastPage) -> String.format(
                ts.getTranslatedString("module.general.labels.page-of"), currentPage, lastPage));
        return i18n;
    }
   
    @Override
    public AbstractAction getModuleAction() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}