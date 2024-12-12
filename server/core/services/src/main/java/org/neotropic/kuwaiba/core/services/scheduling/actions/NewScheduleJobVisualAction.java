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
package org.neotropic.kuwaiba.core.services.scheduling.actions;

import com.neotropic.flow.component.paper.toggle.PaperToggleButton;
import com.vaadin.componentfactory.theme.EnhancedDialogVariant;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.listbox.MultiSelectListBox;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.timepicker.TimePicker;
import org.neotropic.kuwaiba.core.apis.integration.modules.ModuleActionException;
import org.neotropic.kuwaiba.core.apis.integration.modules.ModuleActionParameter;
import org.neotropic.kuwaiba.core.apis.integration.modules.ModuleActionParameterSet;
import org.neotropic.kuwaiba.core.apis.integration.modules.actions.AbstractAction;
import org.neotropic.kuwaiba.core.apis.integration.modules.actions.AbstractVisualAction;
import org.neotropic.kuwaiba.core.apis.integration.modules.actions.ActionCompletedListener;
import org.neotropic.kuwaiba.core.apis.integration.modules.actions.ActionResponse;
import org.neotropic.kuwaiba.core.apis.persistence.application.ApplicationEntityManager;
import org.neotropic.kuwaiba.core.apis.persistence.application.InventoryObjectPool;
import org.neotropic.kuwaiba.core.apis.persistence.application.Task;
import org.neotropic.kuwaiba.core.apis.persistence.application.UserProfile;
import org.neotropic.kuwaiba.core.apis.persistence.util.Constants;
import org.neotropic.kuwaiba.core.i18n.TranslationService;
import org.neotropic.kuwaiba.core.services.scheduling.SchedulingModule;
import org.neotropic.kuwaiba.core.services.scheduling.SchedulingService;
import org.neotropic.kuwaiba.core.services.scheduling.schemas.CronDefinition;
import org.neotropic.util.visual.button.ActionButton;
import org.neotropic.util.visual.dialog.ConfirmDialog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Visual wrapper of create a new schedule job action.
 * @author Juan Sebastian Betancourt {@literal <juan.betancourt@kuwaiba.org>}
 */
@Component
public class NewScheduleJobVisualAction extends AbstractVisualAction<Dialog> {
    /**
     * Reference to the translation service.
     */
    @Autowired
    private TranslationService ts;
    /**
     * Reference to the underlying action.
     */
    @Autowired
    private NewScheduleJobAction addJobAction;
    /**
     * Reference to the application entity manager.
     */
    @Autowired
    private ApplicationEntityManager aem;
    /**
     * Reference to the scheduling service.
     */
    @Autowired
    private SchedulingService shs;
    /**
     * flag to validate the status of the module
     */
    @Value("${scheduler.format.time}")
    boolean formatTime;
    /**
     * Dialog to select users
     */
    private Dialog usersDialog;
    /**
     * List of selected users
     */
    private List<Long> selectedUsers = new ArrayList<>();
    /**
     * combo to picker frequency execution
     */
    private ComboBox<CronDefinition.CronOptions> executePicker;
    /**
     * combo to select every options
     */
    private ComboBox<CronDefinition.CronOptions> frequencyPicker;
    /**
     * combo to select weekly options
     */
    private ComboBox<CronDefinition.CronOptions> weeklyOptions;
    /**
     * combo to picker every options
     */
    private ComboBox<Integer> IntervalPicker;
    /**
     * combo to picker daily options
     */
    private TimePicker hourPicker;
    /**
     * combo to picker days
     */
    private ComboBox<Integer> dayPicker;
    /**
     * Enable Toggle button
     */
    private PaperToggleButton btnEnable;
    /**
     * LogResults Toggle Button
     */
    private PaperToggleButton btnLogResult;
    /**
     * cron options layout
     */
    private HorizontalLayout lytCronOptions; 
    
    public NewScheduleJobVisualAction() {
        super(SchedulingModule.MODULE_ID);
    }

    @Override
    public Dialog getVisualComponent(ModuleActionParameterSet parameters) {
        InventoryObjectPool selectedPool = null;
                       
        if (parameters.containsKey("pool"))
            selectedPool = (InventoryObjectPool) parameters.get("pool");

        //Combo box to select schedule job pool
        List<InventoryObjectPool> scheduleJobPools = shs.getScheduleJobsPools(0, Integer.MAX_VALUE);
        ComboBox<InventoryObjectPool> cmbPool = new ComboBox<>(
                ts.getTranslatedString("module.scheduleJob.ui.label.schedule-job-pool"), scheduleJobPools);
        cmbPool.setAllowCustomValue(false);
        cmbPool.setRequiredIndicatorVisible(true);
        cmbPool.setSizeFull();

        if (selectedPool != null) {
            cmbPool.setValue(selectedPool);
            cmbPool.setAllowCustomValue(false);
        }

        // Text fields name and description
        HorizontalLayout lytNameDesc = new HorizontalLayout();

        TextField txtName = new TextField(ts.getTranslatedString("module.scheduleJob.ui.actions.label.name"));
        txtName.setRequiredIndicatorVisible(true);
        txtName.setSizeFull();

        TextField txtDescription = new TextField(ts.getTranslatedString("module.scheduleJob.ui.actions.label.description"));
        txtDescription.setSizeFull();

        lytNameDesc.add(txtName, txtDescription);

        //tasks - users
        HorizontalLayout lytTaskUsers = new HorizontalLayout();
        //Combo box to select task
        HorizontalLayout lytTasks = new HorizontalLayout();
        lytTasks.setWidth("80%");
        List<Task> tasks = aem.getTasks();
        ComboBox<Task> cmbTasks = new ComboBox<>(
                ts.getTranslatedString("module.scheduleJob.ui.actions.label.task"), tasks);
        cmbTasks.setItemLabelGenerator(Task::getName);
        cmbTasks.setRequiredIndicatorVisible(true);
        cmbTasks.setItemLabelGenerator(Task::getName);
        cmbTasks.setWidthFull();
        lytTasks.add(cmbTasks);

        //select users
        buildUsersDialog();
        HorizontalLayout lytUser = new HorizontalLayout();
        lytUser.setWidth("20%");
        ActionButton btnAddUsers = new ActionButton(new Icon(VaadinIcon.USERS), ts.getTranslatedString("module.scheduleJob.ui.label.users"));
        btnAddUsers.addClickListener(e -> usersDialog.open());
        btnAddUsers.setHeight("32px");
        lytUser.add(btnAddUsers);
        lytUser.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        lytUser.setAlignItems(FlexComponent.Alignment.END);
        lytTaskUsers.add(lytTasks, lytUser);

        //select cron

        HorizontalLayout lytMainCron = new HorizontalLayout();
        lytCronOptions = new HorizontalLayout();
        lytCronOptions.setVisible(false);
        buildCronOptions();
        lytMainCron.add(executePicker, lytCronOptions);

        //paper toggles

        btnEnable = new PaperToggleButton(ts.getTranslatedString("module.general.labels.enable"));
        btnEnable.setWidth("50%");
        btnEnable.setChecked(true);

        btnLogResult = new PaperToggleButton(ts.getTranslatedString("module.scheduleJob.ui.label.log-results"));
        btnLogResult.setWidth("50%");
        btnLogResult.setChecked(true);

        HorizontalLayout lytTogglesButtons = new HorizontalLayout();
        lytTogglesButtons.add(btnEnable, btnLogResult);
        lytTogglesButtons.setPadding(true);

        // Window to create a new schedule job
        ConfirmDialog wdwNewJob = new ConfirmDialog(ts, this.addJobAction.getDisplayName());
        wdwNewJob.setThemeVariants(EnhancedDialogVariant.SIZE_SMALL);

        // To show errors or warnings related to the input parameters.
        Label lblMessages = new Label();

        wdwNewJob.getBtnConfirm().addClickListener(event -> {
            try {
                if (cmbPool.getValue() == null)
                    lblMessages.setText(ts.getTranslatedString("module.general.messages.must-fill-all-fields"));
                else {
                    ActionResponse actionResponse = addJobAction.getCallback().execute(new ModuleActionParameterSet(
                            new ModuleActionParameter<>(Constants.PROPERTY_ID, cmbPool.getValue().getId()),
                            new ModuleActionParameter<>(Constants.PROPERTY_NAME, txtName.getValue()),
                            new ModuleActionParameter<>(Constants.PROPERTY_DESCRIPTION, txtDescription.getValue()),
                            new ModuleActionParameter<>(Constants.PROPERTY_CRON, buildCronExpression()),
                            new ModuleActionParameter<>(Constants.LABEL_TASKS, cmbTasks.getValue().getId()),
                            new ModuleActionParameter<>(Constants.LABEL_USER, selectedUsers.isEmpty() ? null : selectedUsers),
                            new ModuleActionParameter<>(Constants.PROPERTY_ENABLED, btnEnable.getChecked()),
                            new ModuleActionParameter<>(Constants.PROPERTY_LOG_RESULTS, btnLogResult.getChecked())
                    ));
                    if (actionResponse.containsKey("exception"))
                        throw new ModuleActionException(((Exception)actionResponse.get("exception")).getLocalizedMessage());

                    fireActionCompletedEvent(new ActionCompletedListener.ActionCompletedEvent(ActionCompletedListener.ActionCompletedEvent.STATUS_SUCCESS,
                            ts.getTranslatedString("module.scheduleJob.ui.actions.new-job-created-success"), NewScheduleJobAction.class));
                    wdwNewJob.close();
                }
            } catch (ModuleActionException ex) {
                fireActionCompletedEvent(new ActionCompletedListener.ActionCompletedEvent(ActionCompletedListener.ActionCompletedEvent.STATUS_ERROR,
                        ex.getMessage(), NewScheduleJobAction.class));
            }
        });

        wdwNewJob.getBtnConfirm().setEnabled(false);

        txtName.addValueChangeListener(event -> wdwNewJob.getBtnConfirm()
                .setEnabled(!txtName.isEmpty() && !cmbPool.isEmpty() && !cmbTasks.isEmpty() && !executePicker.isEmpty()));

        cmbPool.addValueChangeListener(event -> wdwNewJob.getBtnConfirm()
                .setEnabled(!txtName.isEmpty() && !cmbTasks.isEmpty() && !executePicker.isEmpty()));

        cmbTasks.addValueChangeListener(event -> wdwNewJob.getBtnConfirm()
                .setEnabled(!txtName.isEmpty() && !cmbPool.isEmpty() && !cmbTasks.isEmpty() && !executePicker.isEmpty()));

        executePicker.addValueChangeListener(event -> wdwNewJob.getBtnConfirm()
                .setEnabled(!txtName.isEmpty() && !cmbPool.isEmpty() && !cmbTasks.isEmpty()
                        && !frequencyPicker.isEmpty() && !IntervalPicker.isEmpty() && !hourPicker.isEmpty()
                        && !weeklyOptions.isEmpty() && !dayPicker.isEmpty()));

        frequencyPicker.addValueChangeListener(event -> wdwNewJob.getBtnConfirm()
                .setEnabled(!txtName.isEmpty() && !cmbPool.isEmpty() && !cmbTasks.isEmpty()
                        && !executePicker.isEmpty() && !IntervalPicker.isEmpty() && !hourPicker.isEmpty()
                        && !weeklyOptions.isEmpty() && !dayPicker.isEmpty()));

        IntervalPicker.addValueChangeListener(event -> wdwNewJob.getBtnConfirm()
                .setEnabled(!txtName.isEmpty() && !cmbPool.isEmpty() && !cmbTasks.isEmpty()
                        && !frequencyPicker.isEmpty() && !executePicker.isEmpty() && !hourPicker.isEmpty()
                        && !weeklyOptions.isEmpty() && !dayPicker.isEmpty()));

        hourPicker.addValueChangeListener(event -> wdwNewJob.getBtnConfirm()
                .setEnabled(!txtName.isEmpty() && !cmbPool.isEmpty() && !cmbTasks.isEmpty()
                        && !frequencyPicker.isEmpty() && !IntervalPicker.isEmpty() && !executePicker.isEmpty()
                        && !weeklyOptions.isEmpty() && !dayPicker.isEmpty()));

        weeklyOptions.addValueChangeListener(event -> wdwNewJob.getBtnConfirm()
                .setEnabled(!txtName.isEmpty() && !cmbPool.isEmpty() && !cmbTasks.isEmpty()
                        && !frequencyPicker.isEmpty() && !IntervalPicker.isEmpty() && !hourPicker.isEmpty()
                        && !executePicker.isEmpty() && !dayPicker.isEmpty()));

        dayPicker.addValueChangeListener(event -> wdwNewJob.getBtnConfirm()
                .setEnabled(!txtName.isEmpty() && !cmbPool.isEmpty() && !cmbTasks.isEmpty()
                        && !frequencyPicker.isEmpty() && !IntervalPicker.isEmpty() && !hourPicker.isEmpty()
                        && !weeklyOptions.isEmpty() && !executePicker.isEmpty()));

        wdwNewJob.setContent(cmbPool, lytNameDesc, lytTaskUsers, lytMainCron, lytTogglesButtons);

        return wdwNewJob;
    }

    /**
     * Build the content of dialog to select users
     */
    private void buildUsersDialog() {
        usersDialog = new Dialog();
//        usersDialog.getElement().setAttribute("aria-label", ts.getTranslatedString("module.scheduleJob.ui.label.select-users"));
        AtomicReference<List<UserProfile>> listAtomicReference = new AtomicReference<>(new ArrayList<>());

        VerticalLayout lytDialog = new VerticalLayout();

        Html label = new Html("<h4>"+ ts.getTranslatedString("module.scheduleJob.ui.label.select-users-dialog") +"</h4>");

        VerticalLayout lytUserList = new VerticalLayout();
        List<UserProfile> users = aem.getUsers();
        MultiSelectListBox<UserProfile> mlsUsers = new MultiSelectListBox<>();
        mlsUsers.setItems(users);
        lytUserList.add(mlsUsers);

        mlsUsers.addValueChangeListener(listener -> {
            listAtomicReference.set(null);
            listAtomicReference.set(new ArrayList<>(mlsUsers.getSelectedItems()));
            selectedUsers.clear();
        });

        Button btnSave = new Button("Accept", event -> {
            List<UserProfile> userProfiles = listAtomicReference.get();
            userProfiles.forEach(user -> selectedUsers.add(user.getId()));
            usersDialog.close();
        });

        btnSave.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        Button btnCancel = new Button("Cancel", event -> usersDialog.close());
        btnCancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        HorizontalLayout footerDialog = new HorizontalLayout();
        footerDialog.setJustifyContentMode(FlexComponent.JustifyContentMode.AROUND);
        footerDialog.add(btnCancel, btnSave);

        lytDialog.add(label, lytUserList, footerDialog);

        usersDialog.add(lytDialog);
    }

    private void buildCronOptions() {
        executePicker = new ComboBox<>(ts.getTranslatedString("module.scheduleJob.ui.label.execute"));
        executePicker.setItems(buildExecuteOptions());
        executePicker.setItemLabelGenerator(CronDefinition.CronOptions::getOption);
        executePicker.setAllowCustomValue(false);
        executePicker.setRequiredIndicatorVisible(true);
        executePicker.addValueChangeListener(event -> updateLayoutCron());

        frequencyPicker = new ComboBox<>(ts.getTranslatedString("module.scheduleJob.ui.label.frequency"));
        frequencyPicker.setItems(buildFrequencyOptions());
        frequencyPicker.setItemLabelGenerator(CronDefinition.CronOptions::getOption);
        frequencyPicker.setAllowCustomValue(false);
        frequencyPicker.setRequiredIndicatorVisible(true);
        frequencyPicker.addValueChangeListener(event -> updateEveryPicker());

        IntervalPicker = new ComboBox<>(ts.getTranslatedString("module.scheduleJob.ui.label.interval"));
        IntervalPicker.setWidth(5, Unit.EM);
        IntervalPicker.setAllowCustomValue(false);
        IntervalPicker.setRequiredIndicatorVisible(true);

        weeklyOptions = new ComboBox<>(ts.getTranslatedString("module.scheduleJob.ui.label.day-week"));
        weeklyOptions.setItems(buildWeekOption());
        weeklyOptions.setItemLabelGenerator(CronDefinition.CronOptions::getOption);
        weeklyOptions.setAllowCustomValue(false);
        weeklyOptions.setRequiredIndicatorVisible(true);

        hourPicker = new TimePicker();
        hourPicker.setLabel(ts.getTranslatedString("module.scheduleJob.ui.label.at"));
        hourPicker.setStep(Duration.ofHours(1));
        hourPicker.setLocale(formatTime ? Locale.GERMANY : Locale.US);
        hourPicker.setRequiredIndicatorVisible(true);

        dayPicker = new ComboBox<>(ts.getTranslatedString("module.scheduleJob.ui.label.day"));
        dayPicker.setWidth(5, Unit.EM);
        dayPicker.setAllowCustomValue(false);
        dayPicker.setRequiredIndicatorVisible(true);
        dayPicker.setItems(IntStream.range(1, 31 + 1)
                .boxed()
                .collect(Collectors.toList()));

    }

    private void updateLayoutCron() {
        if (executePicker.getValue().getOption() == null)
            return;

        resetCronOptions();

        int value = executePicker.getValue().getIntValue();
        switch (value) {
            case CronDefinition.EXECUTE_TYPE_EVERY:
                lytCronOptions.add(frequencyPicker, IntervalPicker);
                weeklyOptions.setValue(new CronDefinition.CronOptions("",0));
                dayPicker.setValue(0);
                hourPicker.setValue(LocalTime.of(0, 0));
                break;
            case CronDefinition.EXECUTE_TYPE_DAILY:
                lytCronOptions.add(hourPicker);
                weeklyOptions.setValue(new CronDefinition.CronOptions("",0));
                dayPicker.setValue(0);
                frequencyPicker.setValue(new CronDefinition.CronOptions("",0));
                IntervalPicker.setValue(0);
                break;
            case CronDefinition.EXECUTE_TYPE_WEEKLY:
                lytCronOptions.add(weeklyOptions, hourPicker);
                dayPicker.setValue(0);
                frequencyPicker.setValue(new CronDefinition.CronOptions("",0));
                IntervalPicker.setValue(0);
                break;
            case CronDefinition.EXECUTE_TYPE_MONTHLY:
                lytCronOptions.add(dayPicker, hourPicker);
                weeklyOptions.setValue(new CronDefinition.CronOptions("",0));
                frequencyPicker.setValue(new CronDefinition.CronOptions("",0));
                IntervalPicker.setValue(0);
                break;
            default:
                lytCronOptions.removeAll();
        }
        lytCronOptions.setVisible(true);
    }

    private void resetCronOptions() {
        lytCronOptions.removeAll();
        frequencyPicker.setValue(null);
        IntervalPicker.setValue(null);
        hourPicker.setValue(null);
        weeklyOptions.setValue(null);
        dayPicker.setValue(null);
    }

    private void updateEveryPicker() {
        if (frequencyPicker.getValue() == null) {
            IntervalPicker.setValue(null);
            IntervalPicker.setEnabled(false);
            return;
        }
        int value = frequencyPicker.getValue().getIntValue();
        if (value == 3)
            IntervalPicker.setItems(IntStream.range(1, 23 + 1)
                    .boxed()
                    .collect(Collectors.toList()));
        else
            IntervalPicker.setItems(IntStream.range(1, 59 + 1)
                    .boxed()
                    .collect(Collectors.toList()));

        IntervalPicker.setEnabled(true);
    }

    private Collection<CronDefinition.CronOptions> buildWeekOption() {
        Collection<CronDefinition.CronOptions> options = new ArrayList<>();
        options.add(new CronDefinition.CronOptions(ts.getTranslatedString("module.scheduleJob.ui.label.week.option.sun"),1));
        options.add(new CronDefinition.CronOptions(ts.getTranslatedString("module.scheduleJob.ui.label.week.option.mon"),2));
        options.add(new CronDefinition.CronOptions(ts.getTranslatedString("module.scheduleJob.ui.label.week.option.tue"),3));
        options.add(new CronDefinition.CronOptions(ts.getTranslatedString("module.scheduleJob.ui.label.week.option.wed"),4));
        options.add(new CronDefinition.CronOptions(ts.getTranslatedString("module.scheduleJob.ui.label.week.option.thu"),5));
        options.add(new CronDefinition.CronOptions(ts.getTranslatedString("module.scheduleJob.ui.label.week.option.fri"),6));
        options.add(new CronDefinition.CronOptions(ts.getTranslatedString("module.scheduleJob.ui.label.week.option.sat"),7));

        return options;
    }

    private Collection<CronDefinition.CronOptions> buildExecuteOptions() {
        Collection<CronDefinition.CronOptions> options = new ArrayList<>();
        options.add(new CronDefinition.CronOptions(ts.getTranslatedString("module.scheduleJob.ui.label.execute.option.every"), 1));
        options.add(new CronDefinition.CronOptions(ts.getTranslatedString("module.scheduleJob.ui.label.execute.option.daily"),2));
        options.add(new CronDefinition.CronOptions(ts.getTranslatedString("module.scheduleJob.ui.label.execute.option.weekly"),3));
        options.add(new CronDefinition.CronOptions(ts.getTranslatedString("module.scheduleJob.ui.label.execute.option.monthly"), 4));

        return options;
    }

    private Collection<CronDefinition.CronOptions> buildFrequencyOptions() {
        Collection<CronDefinition.CronOptions> options = new ArrayList<>();
        options.add(new CronDefinition.CronOptions(ts.getTranslatedString("module.scheduleJob.ui.label.frequency.option.seg"),1));
        options.add(new CronDefinition.CronOptions(ts.getTranslatedString("module.scheduleJob.ui.label.frequency.option.min"),2));
        options.add(new CronDefinition.CronOptions(ts.getTranslatedString("module.scheduleJob.ui.label.frequency.option.hr"),3));

        return options;
    }

    private String buildCronExpression() {
        return CronDefinition.getCronExpression(executePicker.getValue().getIntValue(),
                frequencyPicker.getValue().getIntValue(),
                IntervalPicker.getValue(),
                dayPicker.getValue(),
                weeklyOptions.getValue().getIntValue(),
                hourPicker.getValue().getHour(), ts);
    }

    @Override
    public AbstractAction getModuleAction() {
        return addJobAction;
    }
}
