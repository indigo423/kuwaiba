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
package org.neotropic.kuwaiba.core.services.scheduling.components;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.TemplateRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.server.Command;
import org.apache.commons.lang3.StringUtils;
import org.neotropic.kuwaiba.core.apis.integration.modules.ModuleActionParameter;
import org.neotropic.kuwaiba.core.apis.integration.modules.ModuleActionParameterSet;
import org.neotropic.kuwaiba.core.apis.integration.modules.actions.AbstractAction;
import org.neotropic.kuwaiba.core.apis.integration.modules.actions.AbstractVisualAction;
import org.neotropic.kuwaiba.core.apis.integration.modules.actions.ActionCompletedListener;
import org.neotropic.kuwaiba.core.apis.persistence.application.InventoryObjectPool;
import org.neotropic.kuwaiba.core.apis.persistence.application.Session;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.ApplicationObjectNotFoundException;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.InvalidArgumentException;
import org.neotropic.kuwaiba.core.apis.persistence.util.Constants;
import org.neotropic.kuwaiba.core.i18n.TranslationService;
import org.neotropic.kuwaiba.core.services.scheduling.SchedulingModule;
import org.neotropic.kuwaiba.core.services.scheduling.SchedulingService;
import org.neotropic.kuwaiba.core.services.scheduling.actions.DeleteScheduleJobPoolVisualAction;
import org.neotropic.kuwaiba.core.services.scheduling.actions.NewScheduleJobPoolVisualAction;
import org.neotropic.kuwaiba.core.services.scheduling.schemas.ExecuteJob;
import org.neotropic.kuwaiba.core.services.scheduling.schemas.ScheduleJobs;
import org.neotropic.kuwaiba.visualization.api.properties.PropertyFactory;
import org.neotropic.util.visual.button.ActionButton;
import org.neotropic.util.visual.dialog.ConfirmDialog;
import org.neotropic.util.visual.icon.ActionIcon;
import org.neotropic.util.visual.notifications.AbstractNotification;
import org.neotropic.util.visual.notifications.SimpleNotification;
import org.neotropic.util.visual.properties.AbstractProperty;
import org.neotropic.util.visual.properties.PropertySheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Visual wrapper to manage schedule job pools actions.
 * @author Juan Sebastian Betancourt {@literal <juan.betancourt@kuwaiba.org>}
 */
@Component
public class ScheduleJobPoolConfigurationDialog extends AbstractVisualAction<Dialog> implements ActionCompletedListener,
        PropertySheet.IPropertyValueChangedListener  {
    /**
     * The grid with the schedule jobs pools
     */
    private Grid<InventoryObjectPool> gridPools;
    /**
     * The grid with the schedule jobs in the pool dialog
     */
    private Grid<ExecuteJob> grbScheduleJobs;
    /**
     * Configuration Variable data provider variables in the pool
     */
    private ListDataProvider<ExecuteJob> dataProviderScheduleInPool;
    /**
     * Schedule Job list
     */
    private List<ExecuteJob> executeJobs;
    /**
     * list of pool objects
     */
    private List<InventoryObjectPool> listPools;
    /**
     * Object to save the selected schedule job pool
     */
    private InventoryObjectPool currentScheduleJobPool;
    /**
     * Split the content
     */
    private SplitLayout splitLayout;
    /**
     * Contains the filters grid in the bottom left side
     */
    private VerticalLayout lytPoolsGrid;
    /**
     * Layout for actions of pool definition
     */
    private HorizontalLayout lytRightActionButtons;
    /**
     * Contains the script editor in the right side, bottom
     */
    private VerticalLayout lytScriptEditor;
    /**
     *
     * Contains the script editor header
     */
    private VerticalLayout lytScriptHeader;
    /**
     * Contains all scheduleJobs  inside pool
     */
    private VerticalLayout lytScheduleJobsInPool;
    /**
     * Property sheet
     */
    private PropertySheet propertySheetPool;
    /**
     * action over main layout after add new pool
     */
    private Command addScheduleJobPool;
    /**
     * action over main layout after delete pool
     */
    private Command deleteScheduleJobPool;
    /**
     * action after add new pool
     */
    private Command commandAddPool;
    /**
     * action after delete pool
     */
    private Command commandDeletePool;
    /**
     * Reference to the Translation Service
     */
    @Autowired
    private TranslationService ts;
    /**
     * Reference to the Scheduling Service.
     */
    @Autowired
    private SchedulingService schs;
    /**
     * The visual action to create a new schedule job pool
     */
    @Autowired
    private NewScheduleJobPoolVisualAction newPoolVisualAction;
    /**
     * The visual action to delete a schedule job pool
     */
    @Autowired
    private DeleteScheduleJobPoolVisualAction deletePoolVisualAction;

    public ScheduleJobPoolConfigurationDialog() {
        super(SchedulingModule.MODULE_ID);
    }

    @Override
    public Dialog getVisualComponent(ModuleActionParameterSet parameters) {
        this.newPoolVisualAction.registerActionCompletedLister(this);
        this.deletePoolVisualAction.registerActionCompletedLister(this);

        ConfirmDialog dialog = new ConfirmDialog(ts, ts.getTranslatedString("module.scheduleJob.ui.label.manage-pool"));
        dialog.getBtnConfirm().setVisible(false);
        dialog.setContentSizeFull();
        dialog.addDetachListener(event -> freeResources());
        //load components from parent layout
        addScheduleJobPool = (Command) parameters.get("commandAddScheduleJobPool");
        deleteScheduleJobPool = (Command) parameters.get("commandDeleteScheduleJobPool");
        //create commands current layout
        commandAddPool = this::refreshPoolList;
        commandDeletePool = () -> {
            refreshPoolList();
            showFields(false);
        };

        //create content
        splitLayout = new SplitLayout();
        splitLayout.setClassName("main-split");
        splitLayout.setSplitterPosition(36);
        //--left side
        // Main left Layout
        VerticalLayout lytLeftSide = new VerticalLayout();
        lytLeftSide.setClassName("left-side-dialog");
        lytLeftSide.setSpacing(false);
        lytLeftSide.setPadding(false);
        lytLeftSide.setMargin(false);
        lytLeftSide.setHeightFull();
        lytLeftSide.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.STRETCH);
        //top grid
        VerticalLayout lytTopGrid = new VerticalLayout();
        lytTopGrid.setClassName("top-grid-dialog");
        lytTopGrid.setSpacing(true);
        lytTopGrid.setPadding(false);
        lytTopGrid.setMargin(false);
        //bottom grid
        lytPoolsGrid = new VerticalLayout();
        lytPoolsGrid.setClassName("bottom-grid");
        lytPoolsGrid.setSpacing(false);
        lytPoolsGrid.setPadding(false);
        lytPoolsGrid.setMargin(false);
        lytPoolsGrid.setHeightFull();
        buildLeftMainGrid();
        lytPoolsGrid.add(gridPools);
        lytLeftSide.add(lytPoolsGrid);
        //end left side
        splitLayout.addToPrimary(lytLeftSide);

        //--Right side
        VerticalLayout lytRightMain = new VerticalLayout();
        lytRightMain.setClassName("right-side-dialog");
        lytRightMain.setMargin(false);
        lytRightMain.setPadding(false);
        lytRightMain.setSpacing(true);

        // Layout for action buttons
        lytRightActionButtons = new HorizontalLayout();
        lytRightActionButtons.setClassName("button-container");
        lytRightActionButtons.setPadding(false);
        lytRightActionButtons.setMargin(false);
        lytRightActionButtons.setSpacing(false);
        lytRightActionButtons.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        lytRightActionButtons.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);

        HorizontalLayout lytFilterName = new HorizontalLayout();
        lytFilterName.setMargin(false);
        lytFilterName.setPadding(false);
        lytFilterName.setWidth("65%");
        lytFilterName.add(new Label(ts.getTranslatedString("module.scheduleJob.ui.actions.label.schedule-pool-name")));

        createRightControlButtons();
        HorizontalLayout lytScriptControls = new HorizontalLayout();
        lytScriptControls.setClassName("script-control");
        lytScriptControls.setPadding(false);
        lytScriptControls.setMargin(false);
        lytScriptControls.setSpacing(false);
        lytScriptControls.setWidthFull();
        lytScriptControls.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        lytScriptControls.add(lytFilterName, lytRightActionButtons);

        lytScriptHeader = new VerticalLayout();
        lytScriptHeader.setClassName("header-script-control");
        lytScriptHeader.setPadding(false);
        lytScriptHeader.setMargin(false);
        lytScriptHeader.setSpacing(false);
        lytScriptHeader.setSpacing(false);
        lytScriptHeader.add(lytScriptControls);
        lytScriptHeader.setVisible(false);

        //propertySheet
        propertySheetPool = new PropertySheet(ts, new ArrayList<>());
        propertySheetPool.addPropertyValueChangedListener(this);
        propertySheetPool.setHeightFull();

        lytScriptEditor = new VerticalLayout();
        lytScriptEditor.setClassName("propertySheet");
        lytScriptEditor.setWidthFull();
        lytScriptEditor.setMargin(false);
        lytScriptEditor.setSpacing(false);
        lytScriptEditor.setPadding(false);
        lytScriptEditor.add(propertySheetPool);
        lytScriptEditor.setVisible(false);

        //right grid
        createScheduleJobsInPool();
        lytScheduleJobsInPool = new VerticalLayout();
        lytScheduleJobsInPool.setClassName("grig-pool-container");
        lytScheduleJobsInPool.setHeightFull();
        lytScheduleJobsInPool.setMargin(false);
        lytScheduleJobsInPool.setSpacing(false);
        lytScheduleJobsInPool.setPadding(false);
        lytScheduleJobsInPool.add(grbScheduleJobs);
        lytScheduleJobsInPool.setVisible(false);

        lytRightMain.add(lytScriptHeader, lytScriptEditor, lytScheduleJobsInPool);
        //end right side
        splitLayout.addToSecondary(lytRightMain);
        dialog.setContent(splitLayout);
    
        return dialog;
    }

    /**
     * Shows/Hides the labels and buttons in the header of the filter editor
     */
    private void showFields(boolean show) {
        lytScriptHeader.setVisible(show);
        lytScriptEditor.setVisible(show);
        lytScheduleJobsInPool.setVisible(show);
    }
    /**
     * create main left grid
     */
    private void buildLeftMainGrid() {
        listPools = schs.getScheduleJobsPools(0, Integer.MAX_VALUE);
        ListDataProvider<InventoryObjectPool> dataProvider = new ListDataProvider<>(listPools);
        //create grid
        gridPools = new Grid<>();
        gridPools.addThemeVariants(GridVariant.LUMO_NO_BORDER,
                GridVariant.LUMO_NO_ROW_BORDERS, GridVariant.LUMO_COMPACT);
        gridPools.setSelectionMode(Grid.SelectionMode.SINGLE);
        gridPools.setDataProvider(dataProvider);
        gridPools.setHeight("100%");

        gridPools.addItemClickListener(listener -> {
            currentScheduleJobPool = listener.getItem();
            updatePropertySheet(currentScheduleJobPool);
            createScheduleJobInPoolDataProvider(currentScheduleJobPool);
            showFields(true);
        });

        Grid.Column<InventoryObjectPool> nameColum = gridPools.addColumn(InventoryObjectPool::getName);

        lytPoolsGrid.removeAll();
        lytPoolsGrid.add(gridPools);
        createHeaderGrid(nameColum, dataProvider);
    }

    /**
     * update property sheet
     */
    private void updatePropertySheet(InventoryObjectPool pool) {
        if (pool != null)
            propertySheetPool.setItems(PropertyFactory.propertiesFromPoolWithoutClassName(pool, ts));
    }

    /**
     * create a schedule job in data provider to perform search
     * @param pool current pool
     */
    private void createScheduleJobInPoolDataProvider(InventoryObjectPool pool) {
        if (pool != null) {
            executeJobs = schs.getScheduleJobsInPool(pool.getId(), 0, Integer.MAX_VALUE);
            dataProviderScheduleInPool = new ListDataProvider<>(executeJobs);
            grbScheduleJobs.setDataProvider(dataProviderScheduleInPool);
            grbScheduleJobs.getDataProvider().refreshAll();
        }
    }

    /**
     * create a search field and the button to add new schedule job pool in header row of schedule pool grid
     * @param column column to add fields
     * @param dataProvider data provider to perform searchs
     */
    private void createHeaderGrid(Grid.Column column, ListDataProvider<InventoryObjectPool> dataProvider) {
        TextField txtSearchPoolByName = new TextField();
        txtSearchPoolByName.setPlaceholder(ts.getTranslatedString("module.general.labels.filter-placeholder"));
        txtSearchPoolByName.setValueChangeMode(ValueChangeMode.EAGER);
        txtSearchPoolByName.setWidthFull();
        txtSearchPoolByName.setSuffixComponent(new ActionIcon(VaadinIcon.SEARCH,
                ts.getTranslatedString("module.scheduleJob.ui.label.filter-schedule-jobs-pool")));
        txtSearchPoolByName.addValueChangeListener(event -> dataProvider.addFilter(
                    pool -> StringUtils.containsIgnoreCase(pool.getName(),
                            txtSearchPoolByName.getValue())));
        // action button layout
        ActionButton btnAddPool = new ActionButton(new ActionIcon(VaadinIcon.PLUS_SQUARE_O),
                this.newPoolVisualAction.getModuleAction().getDisplayName());
        btnAddPool.addClickListener(event -> {
           this.newPoolVisualAction.getVisualComponent(new ModuleActionParameterSet(
                   new ModuleActionParameter<>("commandClose", addScheduleJobPool),
                   new ModuleActionParameter<>("commandAdd", commandAddPool)
           )).open();
        });
        btnAddPool.setHeight("32px");

        HorizontalLayout lytActionButtons = new HorizontalLayout();
        lytActionButtons.setClassName("left-action-buttons");
        lytActionButtons.setSpacing(false);
        lytActionButtons.setPadding(false);
        lytActionButtons.setMargin(false);
        lytActionButtons.setWidthFull();
        lytActionButtons.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        lytActionButtons.add(txtSearchPoolByName, btnAddPool);

        HeaderRow filterRow = gridPools.appendHeaderRow();
        filterRow.getCell(column).setComponent(lytActionButtons);
    }

    /**
     * create btn that launch the delete schedule job pool action
     */
    private void createRightControlButtons() {
        ActionButton btnDeletePool = new ActionButton(new ActionIcon(VaadinIcon.TRASH),
                this.deletePoolVisualAction.getModuleAction().getDisplayName());
        btnDeletePool.addClickListener(event -> {
            this.deletePoolVisualAction.getVisualComponent(new ModuleActionParameterSet(
                    new ModuleActionParameter<>("scheduleJobPool", currentScheduleJobPool),
                    new ModuleActionParameter<>("commandClose", deleteScheduleJobPool),
                    new ModuleActionParameter<>("commandDelete", commandDeletePool)
            )).open();
        });

        lytRightActionButtons.add(btnDeletePool);
    }

    /**
     * add label to schedule jobs in pool
     */
    private void createScheduleJobsInPool() {
        grbScheduleJobs = new Grid<>();
        grbScheduleJobs.addThemeVariants(GridVariant.LUMO_NO_BORDER,
                GridVariant.LUMO_NO_ROW_BORDERS, GridVariant.LUMO_COMPACT);
        grbScheduleJobs.setSelectionMode(Grid.SelectionMode.NONE);
        grbScheduleJobs.setHeightFull();
        Grid.Column<ExecuteJob> nameColumn = grbScheduleJobs.addColumn(
                        TemplateRenderer.<ExecuteJob>of("<div>[[item.name]]</div>")
                                .withProperty("name", ScheduleJobs::getName));

        Label lblScheduleJobInPool = new Label(ts.getTranslatedString("module.scheduleJob.ui.label.schedule-job-pool"));
        HeaderRow filterRow = grbScheduleJobs.appendHeaderRow();
        filterRow.getCell(nameColumn).setComponent(lblScheduleJobInPool);

    }

    /**
     * unregister the visual actions
     */
    public void freeResources() {
        this.newPoolVisualAction.unregisterListener(this);
        this.deletePoolVisualAction.unregisterListener(this);
    }

    @Override
    public AbstractAction getModuleAction() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void actionCompleted(ActionCompletedEvent ev) {
        if (ev.getStatus() == ActionCompletedListener.ActionCompletedEvent.STATUS_SUCCESS) {
            new SimpleNotification(ts.getTranslatedString("module.general.messages.success"), ev.getMessage(),
                    AbstractNotification.NotificationType.INFO, ts).open();
        } else {
            new SimpleNotification(ts.getTranslatedString("module.general.messages.error"), ev.getMessage(),
                    AbstractNotification.NotificationType.ERROR, ts).open();
        }
    }

    @Override
    public void updatePropertyChanged(AbstractProperty<? extends Object> property) {
        if (currentScheduleJobPool != null) {
            try {
                schs.updateScheduleJobsPools(currentScheduleJobPool.getId()
                        , property.getName()
                        , String.valueOf(property.getValue())
                        , UI.getCurrent().getSession().getAttribute(Session.class).getUser().getUserName()
                );
                if (property.getName().equals(Constants.PROPERTY_NAME))
                    currentScheduleJobPool.setName(String.valueOf(property.getValue()));
                else if (property.getName().equals(Constants.PROPERTY_DESCRIPTION))
                    currentScheduleJobPool.setDescription(String.valueOf(property.getValue()));

                updatePropertySheet(currentScheduleJobPool);
                refreshPoolList();
                addScheduleJobPool.execute();
                new SimpleNotification(ts.getTranslatedString("module.general.messages.success"),
                        ts.getTranslatedString("module.general.messages.property-updated-successfully"),
                        AbstractNotification.NotificationType.INFO, ts).open();
            } catch (InvalidArgumentException | ApplicationObjectNotFoundException ex) {
                new SimpleNotification(ts.getTranslatedString("module.general.messages.error"), ex.getLocalizedMessage(),
                        AbstractNotification.NotificationType.ERROR, ts).open();
                propertySheetPool.undoLastEdit();
            }
        }
    }
    /**
     * main grid refresh items
     */
    private void refreshPoolList() {
        //List of pool for filter
        listPools = schs.getScheduleJobsPools(0, Integer.MAX_VALUE);
        gridPools.setItems(listPools);
    }

}
