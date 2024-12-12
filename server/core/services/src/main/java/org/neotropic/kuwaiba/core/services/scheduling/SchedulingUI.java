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
package org.neotropic.kuwaiba.core.services.scheduling;

import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.BoxSizing;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.TemplateRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.Command;
import org.apache.commons.lang3.StringUtils;
import org.neotropic.kuwaiba.core.apis.integration.dashboards.AbstractUI;
import org.neotropic.kuwaiba.core.apis.integration.modules.ModuleActionParameter;
import org.neotropic.kuwaiba.core.apis.integration.modules.ModuleActionParameterSet;
import org.neotropic.kuwaiba.core.apis.integration.modules.actions.ActionCompletedListener;
import org.neotropic.kuwaiba.core.apis.persistence.ChangeDescriptor;
import org.neotropic.kuwaiba.core.apis.persistence.application.ActivityLogEntry;
import org.neotropic.kuwaiba.core.apis.persistence.application.ApplicationEntityManager;
import org.neotropic.kuwaiba.core.apis.persistence.application.InventoryObjectPool;
import org.neotropic.kuwaiba.core.apis.persistence.application.Session;
import org.neotropic.kuwaiba.core.apis.persistence.application.Task;
import org.neotropic.kuwaiba.core.apis.persistence.application.UserProfile;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.ApplicationObjectNotFoundException;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.ExecutionException;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.InvalidArgumentException;
import org.neotropic.kuwaiba.core.apis.persistence.util.Constants;
import org.neotropic.kuwaiba.core.i18n.TranslationService;
import org.neotropic.kuwaiba.core.services.scheduling.actions.DeleteScheduleJobVisualAction;
import org.neotropic.kuwaiba.core.services.scheduling.actions.NewScheduleJobVisualAction;
import org.neotropic.kuwaiba.core.services.scheduling.actions.ScheduleJobVisualAction;
import org.neotropic.kuwaiba.core.services.scheduling.components.ScheduleJobPoolConfigurationDialog;
import org.neotropic.kuwaiba.core.services.scheduling.schemas.ExecuteJob;
import org.neotropic.kuwaiba.core.services.scheduling.schemas.JobExecutionListener;
import org.neotropic.kuwaiba.core.services.scheduling.schemas.ScheduleJobs;
import org.neotropic.util.visual.button.ActionButton;
import org.neotropic.util.visual.icon.ActionIcon;
import org.neotropic.util.visual.notifications.AbstractNotification;
import org.neotropic.util.visual.notifications.SimpleNotification;
import org.neotropic.util.visual.properties.AbstractProperty;
import org.neotropic.util.visual.properties.PropertySheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author Juan Sebastian Betancourt {@literal <juan.betancourt@kuwaiba.org>}
 */
@Route(value = "scheduling", layout = SchedulingLayout.class)
public class SchedulingUI extends VerticalLayout implements ActionCompletedListener,
        PropertySheet.IPropertyValueChangedListener, HasDynamicTitle, AbstractUI, JobExecutionListener {
    /**
     * Reference to the Translation Service
     */
    @Autowired
    private TranslationService ts;
    /**
     * Reference to the Scheduling Service
     */
    @Autowired
    private SchedulingService shs;
    /**
     * Reference to the Application Entity Manager.
     */
    @Autowired
    private ApplicationEntityManager aem;
    /**
     * The visual action for pool management
     */
    @Autowired
    private ScheduleJobPoolConfigurationDialog scheduleJobPoolConfigurationDialog;
    /**
     * The visual action for add jobs
     */
    @Autowired
    private NewScheduleJobVisualAction createJobAction;
    /**
     * The visual action for delete jobs
     */
    @Autowired
    private DeleteScheduleJobVisualAction deleteJobAction;
    /**
     * The visual action for schedule jobs
     */
    @Autowired
    private ScheduleJobVisualAction scheduleJobAction;
    /**
     * flag to validate the status of the module
     */
    @Value("${spring.scheduler.enabled}")
    boolean enableModule;
    /**
     * Button Object to create a new Schedule Job
     */
    private ActionButton btnAddScheduleJob;
    /**
     * Object to schedule the selected Job
     */
    private ActionButton btnScheduleJob;
    /**
     * Object to save the schedule job pool list
     */
    private List<InventoryObjectPool> listScheduleJobsPools;
    /**
     * Object to save the scheduled jobs list
     */
    private List<ExecuteJob> runningJobs;
    /**
     * Object to save the selected schedule job pool
     */
    private InventoryObjectPool currentScheduleJobPool;
    /**
     * Object to add a new Pool to filter
     */
    private InventoryObjectPool allScheduleJobs;
    /**
     * Property sheet
     */
    private PropertySheet propertySheetJob;
    /**
     * Contains the job info
     */
    private VerticalLayout lytPropertySheet;
    /**
     * Combo box Object to select schedule job pools
     */
    private ComboBox<InventoryObjectPool> cmbFilterSchedulePoolName;
    /**
     * schedule job grid layout
     */
    private VerticalLayout lytScheduleJobs;
    /**
     * The grid with the schedule jobs
     */
    private Grid<ExecuteJob> grbScheduleJobs;
    /**
     * Object to save the selected schedule job
     */
    private ExecuteJob currentExecuteJob;
    /**
     * property sheet for scheduled jobs
     */
    private Grid<ExecuteJob> grbRunningJobs;
    /**
     * Main Left layout
     */
    private VerticalLayout lytLeft;
    /**
     * Main Center layout
     */
    private VerticalLayout lytCenter;
    /**
     * Layout for scheduled jobs
     */
    private VerticalLayout lytRunningJob;
    /**
     * Layout for Search Field
     */
    private HorizontalLayout lytSearchField;
    /**
     * Layout for action buttons;
     */
    private HorizontalLayout lytActionRightButtons;
    /**
     * Layout for header lyt;
     */
    private HorizontalLayout lytHeaderRight;
    /**
     * Main Right layout
     */
    private VerticalLayout lytRight;
    /**
     * main grid data provider
     */
    private ListDataProvider<ExecuteJob> dataProvider;
    /**
     * job selected name
     */
    private Label lblJobName;
    /**
     * empty running label
     */
    private Label lblNoRunningJobs;

    @Override
    public void initContent() {
        if (!enableModule) {
            HorizontalLayout lytDisabled = new HorizontalLayout();
            lytDisabled.setSpacing(false);
            lytDisabled.setPadding(true);
            lytDisabled.getStyle().set("padding-top", "16px");
            lytDisabled.setId("left-lyt");
            lytDisabled.setHeightFull();

            Label lblDisabled = new Label(ts.getTranslatedString("module.scheduleJob.disabled-scheduling-module"));
            lytDisabled.add(lblDisabled);
            lytDisabled.setJustifyContentMode(JustifyContentMode.AROUND);
            add(lytDisabled);
        } else {
            shs.addJobUpdateListener(this);
            runningJobs = shs.getRunningJobs(0, Integer.MAX_VALUE);

            setSizeFull();
            setMargin(false);
            setSpacing(false);
            setPadding(false);

            this.createJobAction.registerActionCompletedLister(this);
            this.deleteJobAction.registerActionCompletedLister(this);
            this.scheduleJobAction.registerActionCompletedLister(this);
            this.scheduleJobPoolConfigurationDialog.registerActionCompletedLister(this);

            // Main Layout
            HorizontalLayout lytMainContent = new HorizontalLayout();
            lytMainContent.setClassName("main-lyt");
            lytMainContent.setSizeFull();

            //btn to open the dialog to add schedule job
            btnAddScheduleJob = new ActionButton(new ActionIcon(VaadinIcon.PLUS_SQUARE_O),
                    this.createJobAction.getModuleAction().getDisplayName());
            btnAddScheduleJob.addClickListener(event -> {
                this.createJobAction.getVisualComponent(new ModuleActionParameterSet(
                        new ModuleActionParameter<>("pool", currentScheduleJobPool))
                ).open();
            });
            btnAddScheduleJob.setHeight("32px");

            // Main left Layout
            lytLeft = new VerticalLayout();
            lytLeft.setClassName("left-side");
            lytLeft.setMargin(false);
            lytLeft.setSpacing(false);
            lytLeft.setPadding(false);
            lytLeft.setId("left-lyt");
            lytLeft.setHeightFull();
            lytLeft.setWidth("25%");

            //btn to open the dialog to manage schedule job pools
            ActionButton btnManagePools = new ActionButton(new ActionIcon(VaadinIcon.COG),
                    ts.getTranslatedString("module.scheduleJob.ui.label.manage-pool"));
            btnManagePools.addClickListener(event -> launchScheduleJobPoolDialog());
            btnManagePools.setHeight("32px");

            // combo box layout
            createComboSchedulePools();
            HorizontalLayout lytCmb = new HorizontalLayout();
            lytCmb.setClassName("left-action-combobox");
            lytCmb.setSpacing(false);
            lytCmb.setMargin(false);
            lytCmb.setPadding(false);
            lytCmb.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
            lytCmb.add(cmbFilterSchedulePoolName, btnManagePools);
            lytCmb.setVerticalComponentAlignment(FlexComponent.Alignment.END, btnManagePools);

            // schedule jobs grid
            buildScheduleJobGrid();
            lytScheduleJobs = new VerticalLayout();
            lytScheduleJobs.setClassName("bottom-grid");
            lytScheduleJobs.setHeightFull();
            lytScheduleJobs.setSpacing(false);
            lytScheduleJobs.setMargin(false);
            lytScheduleJobs.setPadding(false);
            lytScheduleJobs.add(lytSearchField, grbScheduleJobs);

            // Add element to main left Layout
            lytLeft.add(lytCmb, lytScheduleJobs);

            // Main Center Layout
            lytCenter = new VerticalLayout();
            lytCenter.setClassName("center");
            lytCenter.setWidth("37%");
            lytCenter.setMargin(false);
            lytCenter.setHeightFull();
            lytCenter.setBoxSizing(BoxSizing.BORDER_BOX);
            buildCenterLytContent();

            // Main Right Layout
            lytRight = new VerticalLayout();
            lytRight.setClassName("right-side");
            lytRight.setWidth("37%");
            lytRight.setMargin(false);
            buildRightLytContent();

            lytMainContent.add(lytLeft, lytCenter, lytRight);
            add(lytMainContent);
        }
    }

    /**
     * Create a comboBox to manage scheduleJob Pools
     */
    private void createComboSchedulePools() {
        // Filter name
        cmbFilterSchedulePoolName = new ComboBox<>(ts.getTranslatedString("module.scheduleJob.ui.label.schedule-job-pool"));
        cmbFilterSchedulePoolName.setPlaceholder(ts.getTranslatedString("module.scheduleJob.ui.label.choose-schedule-jobs-pool"));
        cmbFilterSchedulePoolName.setWidthFull();
        cmbFilterSchedulePoolName.setAllowCustomValue(false);
        cmbFilterSchedulePoolName.setClearButtonVisible(true);
        cmbFilterSchedulePoolName.setValue(allScheduleJobs);
        buildComboBoxFilter();

        cmbFilterSchedulePoolName.addValueChangeListener(event -> {
            if (event.getValue() != null) {
                lytScheduleJobs.remove(lytSearchField, grbScheduleJobs);
                lytSearchField.removeAll();
                grbScheduleJobs.removeAllColumns();
                if (event.getValue().equals(allScheduleJobs))
                    currentScheduleJobPool = null;
                else
                    currentScheduleJobPool = event.getValue();

                buildScheduleJobGrid();

                lytScheduleJobs.add(lytSearchField, grbScheduleJobs);
                lytScheduleJobs.setVisible(true);

            } else {
                currentScheduleJobPool = null;
                grbScheduleJobs.removeAllColumns();
                lytSearchField.removeAll();
                lytScheduleJobs.remove(lytSearchField, grbScheduleJobs);
                lytScheduleJobs.setVisible(false);
            }
            showRightLayout(false);
        });
    }

    /**
     * Get schedule job pools and create schedule job data provider to combo box
     */
    private void buildComboBoxFilter() {
        // Get the lists of schedule job pools
        listScheduleJobsPools = shs.getScheduleJobsPools(0, Integer.MAX_VALUE);
        allScheduleJobs = new InventoryObjectPool("", ts.getTranslatedString("module.scheduleJob.ui.label.all-schedule-jobs"), "", "", 0);
        listScheduleJobsPools.add(allScheduleJobs);
        cmbFilterSchedulePoolName.setItems(listScheduleJobsPools);
        cmbFilterSchedulePoolName.setValue(allScheduleJobs);
        btnAddScheduleJob.setVisible(listScheduleJobsPools.size() != 1);
    }

    /**
     * Create the grid of schedule jobs in db
     */
    private void buildScheduleJobGrid() {
        List<ExecuteJob> jobs;
        grbScheduleJobs = new Grid<>();
        grbScheduleJobs.addThemeVariants(GridVariant.LUMO_NO_BORDER,
                GridVariant.LUMO_NO_ROW_BORDERS, GridVariant.LUMO_COMPACT);
        grbScheduleJobs.setSelectionMode(Grid.SelectionMode.SINGLE);
        grbScheduleJobs.setHeightFull();
        grbScheduleJobs.setSizeFull();

        grbScheduleJobs.addItemClickListener(event -> {
            currentExecuteJob = event.getItem();
            runningJobs.forEach(job -> {
                if (event.getItem().getJobId().equals(job.getJobId()))
                    currentExecuteJob = job;
            });
            if (event.getItem() != null) {
                updatePropertySheetJob();
                lblJobName.setText(event.getItem().getName());
                showFields(true);
            } else
                lblJobName.removeAll();
            showFields(event.getItem() != null);
        });

        grbScheduleJobs.addColumn(
                        TemplateRenderer.<ExecuteJob>of("<div>[[item.name]]</div>")
                                .withProperty("name", ScheduleJobs::getName))
                .setKey(ts.getTranslatedString("module.general.labels.name"));

        grbScheduleJobs.addComponentColumn(this::createJobButtonsGrid).setTextAlign(ColumnTextAlign.END)
                .setFlexGrow(0).setWidth("40px");

        if (currentScheduleJobPool != null)
            jobs = shs.getScheduleJobsInPool(currentScheduleJobPool.getId(), 0, Integer.MAX_VALUE);
        else
            jobs = shs.getScheduleJobs(0, Integer.MAX_VALUE);

        if (runningJobs != null) {
            List<ExecuteJob> runningJobsCopy = new ArrayList<>(runningJobs);
            runningJobsCopy.forEach(runningJob -> {
                jobs.forEach(job -> {
                    if (job.getJobId().equals(runningJob.getJobId())) {
                        job.setState(runningJob.getState());
                    }
                });
            });
        }

        dataProvider = new ListDataProvider<>(jobs);

        grbScheduleJobs.setDataProvider(dataProvider);

        lytSearchField = new HorizontalLayout();
        lytSearchField.setClassName("left-action-buttons");
        lytSearchField.setSpacing(false);
        lytSearchField.setPadding(false);
        lytSearchField.setMargin(false);
        lytSearchField.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        // Validate schedule job pool list
        btnAddScheduleJob.setVisible(listScheduleJobsPools.size() != 1);
        // Validate schedule jobs list size
        if (jobs.isEmpty())
            labelInfoEmptyPool();
        else
            createSearchField(dataProvider);
    }

    private HorizontalLayout createJobButtonsGrid(ExecuteJob current) {
        HorizontalLayout lytButtons;

        ActionButton btnWarningScheduleJob = new ActionButton(new ActionIcon(VaadinIcon.WARNING),
                this.scheduleJobAction.getModuleAction().getDisplayName());
        btnWarningScheduleJob.setHeight("19px");

        Command refreshScheduledJobGrid = () -> {
            refreshScheduledJobGrid();
            refreshScheduleJobGrid();
        };

        btnWarningScheduleJob.addClickListener(event -> {
            this.scheduleJobAction.getVisualComponent(new ModuleActionParameterSet(
                    new ModuleActionParameter<>("scheduleJob", current),
                    new ModuleActionParameter<>("commandSchedule", refreshScheduledJobGrid)
            )).open();
        });

        Command deleteJob = () -> {
            showFields(false);
            refreshScheduledJobGrid();
            refreshScheduleJobGrid();
        };

        ActionButton btnDeleteJob = new ActionButton(new ActionIcon(VaadinIcon.TRASH),
                this.deleteJobAction.getModuleAction().getDisplayName());
        btnDeleteJob.setHeight("19px");
        btnDeleteJob.addClickListener(event -> {
            this.deleteJobAction.getVisualComponent(new ModuleActionParameterSet(
                    new ModuleActionParameter<>("scheduleJob", current),
                    new ModuleActionParameter<>("commandDelete", deleteJob)
            )).open();
        });

        if (current.getState() != ScheduleJobs.STATE_NOT_SCHEDULED)
            lytButtons = new HorizontalLayout(btnDeleteJob);
        else
            lytButtons = new HorizontalLayout(btnWarningScheduleJob, btnDeleteJob);

        return lytButtons;
    }
    /**
     * Create a  search field to schedule jobs in the header row
     * @param dataProvider data provider to filter
     */
    private void createSearchField(ListDataProvider<ExecuteJob> dataProvider) {
        TextField txtVariableName = new TextField();
        txtVariableName.setPlaceholder(ts.getTranslatedString("module.general.labels.filter-placeholder"));
        txtVariableName.setValueChangeMode(ValueChangeMode.EAGER);
        txtVariableName.setWidthFull();
        txtVariableName.setSuffixComponent(new ActionIcon(VaadinIcon.SEARCH,
                ts.getTranslatedString("module.scheduleJob.ui.label.filter-schedule-jobs")));
        // object name filter
        txtVariableName.addValueChangeListener(event -> dataProvider.addFilter(
                variable -> StringUtils.containsIgnoreCase(variable.getName(),
                        txtVariableName.getValue())));

        lytSearchField.add(txtVariableName, btnAddScheduleJob);
    }
    /**
     * Create a label with message for empty schedule job pools
     */
    private void labelInfoEmptyPool() {
        Label lblInfo = new Label();

        if (listScheduleJobsPools.size() == 1) {
            listScheduleJobsPools.forEach(pool ->{
                if(pool.getName().equals(ts.getTranslatedString("module.scheduleJob.ui.label.all-schedule-jobs")))
                    lblInfo.setText(ts.getTranslatedString("module.scheduleJob.ui.no-scheduled-jobs-pool"));
            });
        } else
            lblInfo.setText(ts.getTranslatedString("module.scheduleJob.ui.empty-jobs"));

        lblInfo.setWidthFull();
        lytSearchField.add(lblInfo, btnAddScheduleJob);
    }
    /**
     * Launch the schedule job pool dialog
     */
    private void launchScheduleJobPoolDialog() {
        Command commandAddSchedulePool = () -> {
            buildComboBoxFilter();
            refreshScheduleJobGrid();
        };
        Command commandDeleteSchedulePool = () -> {
            buildComboBoxFilter();
            refreshScheduleJobGrid();
        };

        this.scheduleJobPoolConfigurationDialog.getVisualComponent(new ModuleActionParameterSet(
                new ModuleActionParameter("commandAddScheduleJobPool", commandAddSchedulePool),
                new ModuleActionParameter("commandDeleteScheduleJobPool", commandDeleteSchedulePool)
        )).open();
    }
    /**
     * Build the content of center layout
     */
    private void buildCenterLytContent() {
        // add title label
        Label lblRunningJobs = new Label(ts.getTranslatedString("module.scheduleJob.ui.label.running-schedule-job"));
        lblRunningJobs.setClassName("dialog-title");
        lytRunningJob = new VerticalLayout();
        buildScheduledJobGrid();
        if (runningJobs.isEmpty())
            lytRunningJob.add(lblNoRunningJobs);
        else
            lytRunningJob.add(lblNoRunningJobs, grbRunningJobs);
        lytCenter.add(lblRunningJobs, lytRunningJob);
    }
    /**
     * Build the grid that show the scheduled jobs
     */
    private void buildScheduledJobGrid() {
        lblNoRunningJobs = new Label();
        lblNoRunningJobs.setVisible(false);
        runningJobs = shs.getRunningJobs(0, Integer.MAX_VALUE);
        grbRunningJobs = new Grid<>();
        grbRunningJobs.setSelectionMode(Grid.SelectionMode.SINGLE);

        grbRunningJobs.addItemClickListener(listener -> {
            currentExecuteJob = listener.getItem();
            if (listener.getItem() != null) {
                lblJobName.setText(listener.getItem().getName());
                updatePropertySheetJob();
                showFields(true);
            } else
                lblJobName.removeAll();
            showFields(listener.getItem() != null);

        });

        grbRunningJobs.addColumn(ExecuteJob::getName).setAutoWidth(true)
                .setKey(ts.getTranslatedString("module.general.labels.name"));
        grbRunningJobs.addComponentColumn(this::createJobStateGrid).setTextAlign(ColumnTextAlign.END).setFlexGrow(0).setWidth("135px");

        ListDataProvider<ExecuteJob> jobListDataProvider = new ListDataProvider<>(runningJobs);
        grbRunningJobs.setDataProvider(jobListDataProvider);

        if (runningJobs.isEmpty()) {
            lblNoRunningJobs = new Label();
            lblNoRunningJobs.setVisible(true);
            lblNoRunningJobs.setText(ts.getTranslatedString("module.scheduleJob.ui.no-scheduled-jobs"));
            lblNoRunningJobs.setWidthFull();
        }
    }
    /**
     * Create the stiles of grid that show the scheduled job
     * @param job job to read the state
     * @return lyt that contains the styles for grid
     */
    private HorizontalLayout createJobStateGrid(ExecuteJob job) {
        HorizontalLayout lyt;
        Div htmlState = new Div();
        htmlState.addClassName("space");
        Span html;

        switch (job.getState()) {
            case ScheduleJobs.STATE_SCHEDULED:
                html = new Span(ts.getTranslatedString("module.scheduleJob.ui.label.state-scheduled"));
                htmlState.addClassName("scheduled");
                htmlState.add(html);
                break;
            case ScheduleJobs.STATE_RUNNING:
                html = new Span(ts.getTranslatedString("module.scheduleJob.ui.label.state-running"));
                htmlState.addClassName("running");
                htmlState.add(html);
                break;
            case ScheduleJobs.STATE_EXECUTED:
                html = new Span(ts.getTranslatedString("module.scheduleJob.ui.label.state-executed"));
                htmlState.addClassName("success");
                htmlState.add(html);
                break;
            case ScheduleJobs.STATE_KILLED:
                html = new Span(ts.getTranslatedString("module.scheduleJob.ui.label.state-fail"));
                htmlState.addClassName("error");
                htmlState.add(html);
                break;
            default:
                return null;
        }

        htmlState.setHeight("15px");

        lyt = new HorizontalLayout(htmlState);
        lyt.setJustifyContentMode(JustifyContentMode.AROUND);
        return lyt;
    }
    /**
     * Build the content of right lyt
     */
    private void buildRightLytContent() {
        buildPropertySheetJob();

        HorizontalLayout lytJobName = new HorizontalLayout();

        lblJobName = new Label();
        lblJobName.setClassName("dialog-title");
        Label lblJob = new Label(
                ts.getTranslatedString("module.scheduleJob.ui.label.schedule-job"));
        lblJob.setClassName("dialog-title");

        lytJobName.add(new Html("<span>&nbsp;</span>"), lblJob, lblJobName);
        lytJobName.setWidth("50%");
        lytJobName.addClassName("job-name");

        lytHeaderRight = new HorizontalLayout();
        lytHeaderRight.setWidthFull();
        lytHeaderRight.setJustifyContentMode(JustifyContentMode.START);
        lytHeaderRight.setVisible(false);

        lytHeaderRight.add(lytJobName);

        lytPropertySheet = new VerticalLayout();
        lytPropertySheet.setClassName("propertySheet");
        lytPropertySheet.setWidthFull();
        lytPropertySheet.setMargin(false);
        lytPropertySheet.setPadding(false);
        lytPropertySheet.setSpacing(false);
        lytPropertySheet.add(propertySheetJob);
        lytPropertySheet.setVisible(false);

        lytRight.add(lytHeaderRight, lytPropertySheet);
    }

    /**
     * Build the propertySheet to show the schedule job properties
     */
    private void buildPropertySheetJob() {
        propertySheetJob = new PropertySheet(ts, new ArrayList<>());
        propertySheetJob.addPropertyValueChangedListener(this);
        propertySheetJob.setHeightFull();
    }

    /**
     * Update the propertySheet that show the schedule job properties
     */
    private void updatePropertySheetJob() {
        if (currentExecuteJob != null) {
            try {
                propertySheetJob.setItems(shs.getAbstractPropertiesFromJob(currentExecuteJob));
            } catch (ApplicationObjectNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Shows/Hides the labels, buttons in the right layout
     */
    private void showFields(boolean show) {
        lytPropertySheet.setVisible(show);
        lytHeaderRight.setVisible(show);
    }

    private void showRightLayout(boolean show) {
        lytPropertySheet.setVisible(show);
        lytHeaderRight.setVisible(show);
    }

    /**
     * Refresh the grid of scheduled jobs after jobs actions
     */
    private void refreshScheduledJobGrid() {
        lytRunningJob.remove(lblNoRunningJobs, grbRunningJobs);
        grbRunningJobs.removeAllColumns();
        buildScheduledJobGrid();
        if (runningJobs.isEmpty())
            lytRunningJob.add(lblNoRunningJobs);
        else
            lytRunningJob.add(lblNoRunningJobs, grbRunningJobs);
    }

    /**
     * Refresh the grid of schedule jobs pools after pool actions
     */
    private void refreshScheduleJobGrid() {
        lytScheduleJobs.remove(lytSearchField, grbScheduleJobs);
        grbScheduleJobs.removeAllColumns();
        lytSearchField.removeAll();
        buildScheduleJobGrid();
        lytScheduleJobs.add(lytSearchField, grbScheduleJobs);
    }

    @Override
    public String getPageTitle() {
        return ts.getTranslatedString("module.scheduleJob.ui.title");
    }

    @Override
    public void actionCompleted(ActionCompletedListener.ActionCompletedEvent ev) {
        if (ev.getStatus() == ActionCompletedListener.ActionCompletedEvent.STATUS_SUCCESS) {
            new SimpleNotification(ts.getTranslatedString("module.general.messages.success"), ev.getMessage(),
                    AbstractNotification.NotificationType.INFO, ts).open();
            refreshScheduledJobGrid();
            refreshScheduleJobGrid();
        } else
            new SimpleNotification(ts.getTranslatedString("module.general.messages.error"), ev.getMessage(),
                    AbstractNotification.NotificationType.ERROR, ts).open();
    }

    @Override
    public void updatePropertyChanged(AbstractProperty<? extends Object> property) {
        if (currentExecuteJob != null) {
            try {
                if (property.getName().equals(Constants.LABEL_USER)) {
                    ChangeDescriptor changeDescriptor = shs.updateUsersForJob(currentExecuteJob, (List<UserProfile>) new ArrayList<>((Set<?>) property.getValue()));
                    new SimpleNotification(ts.getTranslatedString("module.general.messages.success"),
                            ts.getTranslatedString("module.general.messages.property-updated-successfully"),
                            AbstractNotification.NotificationType.INFO, ts).open();

                    // activity log
                    aem.createGeneralActivityLogEntry(UI.getCurrent().getSession().getAttribute(Session.class).getUser().getUserName(),
                            ActivityLogEntry.ACTIVITY_TYPE_UPDATE_APPLICATION_OBJECT, changeDescriptor);
                }
                else if (property.getName().equals(Constants.LABEL_TASKS)) {
                    ChangeDescriptor changeDescriptor = shs.updateTaskForJob(currentExecuteJob, (Task) property.getValue());
                    new SimpleNotification(ts.getTranslatedString("module.general.messages.success"),
                            ts.getTranslatedString("module.general.messages.property-updated-successfully"),
                            AbstractNotification.NotificationType.INFO, ts).open();

                    // activity log
                    aem.createGeneralActivityLogEntry(UI.getCurrent().getSession().getAttribute(Session.class).getUser().getUserName(),
                            ActivityLogEntry.ACTIVITY_TYPE_UPDATE_APPLICATION_OBJECT, changeDescriptor);
                } else {
                    ChangeDescriptor changeDescriptor = shs.updateScheduleJob(currentExecuteJob.getJobId(),
                            property.getName(), String.valueOf(property.getValue()));

                    switch (property.getName()) {
                        case Constants.PROPERTY_NAME:
                            currentExecuteJob.setName(String.valueOf(property.getValue()));
                            refreshScheduledJobGrid();
                            refreshScheduleJobGrid();
                            break;
                        case Constants.PROPERTY_DESCRIPTION:
                            currentExecuteJob.setDescription(String.valueOf(property.getValue()));
                            refreshScheduledJobGrid();
                            refreshScheduleJobGrid();
                            break;
                        case Constants.PROPERTY_ENABLED:
                            currentExecuteJob.setEnabled(Boolean.parseBoolean(String.valueOf(property.getValue())));
                            refreshScheduledJobGrid();
                            refreshScheduleJobGrid();
                            break;
                        case Constants.PROPERTY_LOG_RESULTS:
                            currentExecuteJob.setLogResults(Boolean.parseBoolean(String.valueOf(property.getValue())));
                            refreshScheduledJobGrid();
                            refreshScheduleJobGrid();
                            break;
                        case Constants.PROPERTY_CRON:
                            currentExecuteJob.setCronExpression(String.valueOf(property.getValue()));
                            refreshScheduledJobGrid();
                            refreshScheduleJobGrid();
                        default:
                            break;
                    }
                    updatePropertySheetJob();
                    new SimpleNotification(ts.getTranslatedString("module.general.messages.success"),
                            ts.getTranslatedString("module.general.messages.property-updated-successfully"),
                            AbstractNotification.NotificationType.INFO, ts).open();

                    // activity log
                    aem.createGeneralActivityLogEntry(UI.getCurrent().getSession().getAttribute(Session.class).getUser().getUserName(),
                            ActivityLogEntry.ACTIVITY_TYPE_UPDATE_APPLICATION_OBJECT, changeDescriptor);
                }
            } catch (ApplicationObjectNotFoundException | InvalidArgumentException | ExecutionException ex) {
                new SimpleNotification(ts.getTranslatedString("module.general.messages.error"), ex.getLocalizedMessage(),
                        AbstractNotification.NotificationType.ERROR, ts).open();
                propertySheetJob.undoLastEdit();
            }
        }
    }

    @Override
    public void onDetach(DetachEvent ev) {
        this.createJobAction.unregisterListener(this);
        this.deleteJobAction.unregisterListener(this);
        this.scheduleJobAction.unregisterListener(this);
        this.scheduleJobPoolConfigurationDialog.unregisterListener(this);
        shs.removeJobUpdateListener(this);
    }

    @Override
    public void onJobExecuted(ExecuteJob job) {
        if (getUI().isPresent()) {
            UI ui = getUI().get();
            ui.access(this::refreshScheduledJobGrid);
        }
    }
}