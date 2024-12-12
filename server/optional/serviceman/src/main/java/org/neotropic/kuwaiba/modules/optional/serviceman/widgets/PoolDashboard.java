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

package org.neotropic.kuwaiba.modules.optional.serviceman.widgets;

import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.CssImport;
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
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.neotropic.kuwaiba.core.apis.integration.modules.ModuleActionParameter;
import org.neotropic.kuwaiba.core.apis.integration.modules.ModuleActionParameterSet;
import org.neotropic.kuwaiba.core.apis.integration.modules.actions.AbstractAction;
import org.neotropic.kuwaiba.core.apis.integration.modules.actions.AbstractVisualAction;
import org.neotropic.kuwaiba.core.apis.integration.modules.actions.ActionCompletedListener;
import org.neotropic.kuwaiba.core.apis.persistence.application.InventoryObjectPool;
import org.neotropic.kuwaiba.core.apis.persistence.application.Session;
import org.neotropic.kuwaiba.core.apis.persistence.business.BusinessObjectLight;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.ApplicationObjectNotFoundException;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.BusinessObjectNotFoundException;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.InvalidArgumentException;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.MetadataObjectNotFoundException;
import org.neotropic.kuwaiba.core.apis.persistence.util.Constants;
import org.neotropic.kuwaiba.core.i18n.TranslationService;
import org.neotropic.kuwaiba.modules.optional.serviceman.ServiceManagerModule;
import org.neotropic.kuwaiba.modules.optional.serviceman.ServiceManagerService;
import org.neotropic.kuwaiba.modules.optional.serviceman.ServiceManagerUI;
import org.neotropic.kuwaiba.modules.optional.serviceman.actions.DeleteCustomerPoolVisualAction;
import org.neotropic.kuwaiba.modules.optional.serviceman.actions.DeleteServicePoolVisualAction;
import org.neotropic.kuwaiba.modules.optional.serviceman.actions.NewCustomerPoolVisualAction;
import org.neotropic.kuwaiba.modules.optional.serviceman.actions.NewServicePoolVisualAction;
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

/**
 * A general purpose pool dashboard that allows to set the name and description of pools.
 * @author Charles Edward Bedon Cortazar {@literal <charles.bedon@kuwaiba.org>}
 * @author Mauricio Ruiz Beltrán {@literal <mauricio.ruiz@kuwaiba.org>}
 */
@CssImport(value = "./css/poolConfigurationDialog.css")
@Component
public class PoolDashboard extends AbstractVisualAction<Dialog> implements ActionCompletedListener,
        PropertySheet.IPropertyValueChangedListener {
    
    /**
     * Reference to the Service Manager Service
     */
    @Autowired
    private ServiceManagerService sms;
    /**
     * Reference to the Translation Service
     */            
    @Autowired
    private TranslationService ts;
    /**
     * Reference to the action that creates customer pools
     */
    @Autowired
    private NewCustomerPoolVisualAction newCustomerPoolVisualAction;
    /**
     * Reference to the action that creates service pools
     */
    @Autowired
    private NewServicePoolVisualAction newServicePoolVisualAction;
    /**
     * Reference to the action that deletes customer pools
     */
    @Autowired
    private DeleteCustomerPoolVisualAction deleteCustomerPoolVisualAction;
    /**
     * Reference to the action that deletes service pools
     */
    @Autowired
    private DeleteServicePoolVisualAction deleteServicePoolVisualAction;
    /**
     * Layouts
     */
    private SplitLayout splitLayout;
    private VerticalLayout lytLeftSide;
    private VerticalLayout lytPoolsGrid;
    private VerticalLayout lytRightSide;
    private VerticalLayout lytPropertySheet;
    private HorizontalLayout lytRightControls;
    private HorizontalLayout lytRightActionButtons;
    /**
     * Commands
     */
    private Command commandAddCustomerPoolUI;
    private Command commandAddCustomerPoolDashboard;
    private Command commandDeleteCustomerPoolUI;
    private Command commandDeleteCustomerPoolDashboard;
    private Command commandAddServicePoolUI;
    private Command commandAddServicePoolDashboard;
    private Command commandDeleteServicePoolUI;
    private Command commandDeleteServicePoolDashboard;
    /**
     * Saves the selected customer if it exists
     */
    private BusinessObjectLight customer;
    /**
     * Property sheet
     */
    private PropertySheet propertySheetPool;
    /**
     * Shows the selected pool name
     */
    private Label lblPoolName;
    /**
     * Saves the selected pool
     */
    private InventoryObjectPool currentPool;
    private Boolean isServicePool;
    /**
     * Grid to shows the pools
     */
    private Grid<InventoryObjectPool> gridPools;

    public PoolDashboard() {
        super(ServiceManagerModule.MODULE_ID);
    }

    private void initResources() {
        this.newCustomerPoolVisualAction.registerActionCompletedLister(this);
        this.newServicePoolVisualAction.registerActionCompletedLister(this);
        this.deleteCustomerPoolVisualAction.registerActionCompletedLister(this);
        this.deleteServicePoolVisualAction.registerActionCompletedLister(this);
    }
    
    public void freeResources(DetachEvent ev) {
        this.newCustomerPoolVisualAction.unregisterListener(this);
        this.newServicePoolVisualAction.unregisterListener(this);
        this.deleteCustomerPoolVisualAction.unregisterListener(this);
        this.deleteServicePoolVisualAction.unregisterListener(this);
    }
    
    @Override
    public void actionCompleted(ActionCompletedListener.ActionCompletedEvent ev) {
        if (ev.getStatus() == ActionCompletedListener.ActionCompletedEvent.STATUS_SUCCESS) {
            new SimpleNotification(ts.getTranslatedString("module.general.messages.success"),
                    ev.getMessage(), AbstractNotification.NotificationType.INFO, ts).open();
        } else {
            new SimpleNotification(ts.getTranslatedString("module.general.messages.error"),
                    ev.getMessage(), AbstractNotification.NotificationType.ERROR, ts).open();
        }
    }
    
    @Override
    public Dialog getVisualComponent(ModuleActionParameterSet parameters) {
        ConfirmDialog wdwPool = new ConfirmDialog(ts);
        wdwPool.getBtnConfirm().setVisible(false);
        wdwPool.getBtnCancel().setText(ts.getTranslatedString("module.general.messages.close"));
        wdwPool.setContentSizeFull();
        wdwPool.addDetachListener(event -> freeResources(event));

        initResources();
        setupLayouts();
        setCommands();
        
        if (parameters.containsKey(ServiceManagerUI.PARAMETER_CUSTOMER_POOLS)) {
            commandAddCustomerPoolUI = (Command) parameters.get("commandAddCustomerPoolUI");
            commandDeleteCustomerPoolUI = (Command) parameters.get("commandDeleteCustomerPoolUI");
            
            wdwPool.setHeader(ts.getTranslatedString("module.serviceman.actions.manage-customer-pool.name"));
            buildPoolsGrid(ServiceManagerUI.PARAMETER_CUSTOMER_POOLS, null);
        } else if (parameters.containsKey(ServiceManagerUI.PARAMETER_SERVICE_POOLS)) {
            commandAddServicePoolUI = (Command) parameters.get("commandAddServicePoolUI");
            commandDeleteServicePoolUI = (Command) parameters.get("commandDeleteServicePoolUI");
            
            wdwPool.setHeader(ts.getTranslatedString("module.serviceman.actions.manage-service-pool.name"));
            customer = (BusinessObjectLight) parameters.get(ServiceManagerUI.PARAMETER_CUSTOMER);
            buildPoolsGrid(ServiceManagerUI.PARAMETER_SERVICE_POOLS, customer);
        }
        
        lytLeftSide.add(lytPoolsGrid);
        splitLayout.addToPrimary(lytLeftSide);
        
        buildRightHeader();
        buildPropertySheet();
        splitLayout.addToSecondary(lytRightSide);
        
        wdwPool.setContent(splitLayout);
        return wdwPool;
    }
    
    /**
     * Creates the layouts for each of the sections.
     */
    private void setupLayouts() {
        // Main layout
        if (splitLayout == null) {
            splitLayout = new SplitLayout();
            splitLayout.setClassName("main-split");
            splitLayout.setSplitterPosition(36);
            splitLayout.setId("splitLayout");
        } else 
            splitLayout.removeAll();
        
        // --> Init left section
        if (lytLeftSide == null) {
            lytLeftSide = new VerticalLayout();
            lytLeftSide.setClassName("left-side-dialog");
            lytLeftSide.setSpacing(false);
            lytLeftSide.setPadding(false);
            lytLeftSide.setMargin(false);
            lytLeftSide.setHeightFull();
            lytLeftSide.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.STRETCH);
            lytLeftSide.setId("lytLeftSide");
        } else
            lytLeftSide.removeAll();
        
        if (lytPoolsGrid == null) {
            lytPoolsGrid = new VerticalLayout();
            lytPoolsGrid.setClassName("bottom-grid");
            lytPoolsGrid.setId("lytPoolsGrid");
            lytPoolsGrid.setSpacing(false);
            lytPoolsGrid.setPadding(false);
            lytPoolsGrid.setMargin(false);
            lytPoolsGrid.setHeightFull();
        } else
            lytPoolsGrid.removeAll();
        // <-- End left section
        
        // --> Init right section
        if (lytRightSide == null) {
            lytRightSide = new VerticalLayout();
            lytRightSide.setClassName("right-side-dialog");
            lytRightSide.setId("lytRightSide");
            lytRightSide.setMargin(false);
            lytRightSide.setPadding(false);
            lytRightSide.setSpacing(false);
            lytRightSide.setHeightFull();
        } else
            lytRightSide.removeAll();

        if (lytRightControls == null) {
            lytRightControls = new HorizontalLayout();
            lytRightControls.setClassName("script-control");
            lytRightControls.setPadding(false);
            lytRightControls.setMargin(false);
            lytRightControls.setSpacing(false);
            lytRightControls.setWidthFull();
            lytRightControls.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
            lytRightControls.setId("lytRightControls");
            lytRightSide.add(lytRightControls);
        } else
            lytRightControls.removeAll();
        
        if (lytRightActionButtons == null) {
            lytRightActionButtons = new HorizontalLayout();
            lytRightActionButtons.setClassName("button-container");
            lytRightActionButtons.setWidth("10%");
            lytRightActionButtons.setPadding(false);
            lytRightActionButtons.setMargin(false);
            lytRightActionButtons.setSpacing(false);
            lytRightActionButtons.setId("lytRightActionButtons");
            lytRightActionButtons.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
            lytRightActionButtons.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        } else 
            lytRightActionButtons.removeAll();
        
        if (lytPropertySheet == null) {
            lytPropertySheet = new VerticalLayout();
            lytPropertySheet.setClassName("propertySheet");
            lytPropertySheet.setId("lytPropertySheet");
            lytPropertySheet.setSpacing(false);
            lytPropertySheet.setPadding(false);
            lytPropertySheet.setMargin(false);
            lytPropertySheet.setHeightFull();
            lytRightSide.add(lytPropertySheet);
        } else
            lytPropertySheet.removeAll();
        // <-- End right section
    }
    
    /**
     * Sets commands that allow updating the dashboard and UI.
     */
    private void setCommands() {
        commandAddCustomerPoolDashboard = () -> {
            buildPoolsGrid(ServiceManagerUI.PARAMETER_CUSTOMER_POOLS, null);
            commandAddCustomerPoolUI.execute();
        };
        
        commandDeleteCustomerPoolDashboard = () -> {
            buildPoolsGrid(ServiceManagerUI.PARAMETER_CUSTOMER_POOLS, null);
            clearPoolProperties();
            commandDeleteCustomerPoolUI.execute();
        };
        
        commandAddServicePoolDashboard = () -> {
            buildPoolsGrid(ServiceManagerUI.PARAMETER_SERVICE_POOLS, customer);
            commandAddServicePoolUI.execute();
        };
        
        commandDeleteServicePoolDashboard = () -> {
            buildPoolsGrid(ServiceManagerUI.PARAMETER_SERVICE_POOLS, customer);
            clearPoolProperties();
            commandDeleteServicePoolUI.execute();
        };
    }
    
    //<editor-fold defaultstate="collapsed" desc="Left section">
    /**
     * Builds the grid that shows the customer or service pool.
     * If an object exists, the service pool associated with that object is returned. 
     * Otherwise the customer pool is returned.
     * @param parameterPool The parameter pool, it can be "customerPools" or "servicePools".
     * @param object        The selected object if exists.
     */
    private void buildPoolsGrid(String parameterPool, BusinessObjectLight object) {
        try {
            //create data provider
            List<InventoryObjectPool> listPools;
            if (object == null)
                listPools = sms.getCustomerPools();
            else
                listPools = sms.getServicePoolsInCostumer(
                        object.getClassName(),
                        object.getId(),
                        Constants.CLASS_GENERICSERVICE);
            
            ListDataProvider<InventoryObjectPool> dataProviderPools = new ListDataProvider<>(listPools);
            // Create grid
            gridPools = new Grid<>();
            gridPools.addThemeVariants(GridVariant.LUMO_NO_BORDER,
                    GridVariant.LUMO_NO_ROW_BORDERS, GridVariant.LUMO_COMPACT);
            gridPools.setSelectionMode(Grid.SelectionMode.SINGLE);
            gridPools.setDataProvider(dataProviderPools);
            gridPools.setId("gridPools");
            gridPools.setHeightFull();
                        
            Grid.Column<InventoryObjectPool> nameColumn = gridPools.addColumn(TemplateRenderer.<InventoryObjectPool>of(
                    "<div>[[item.name]] &nbsp; <font class=\"text-secondary\">[[item.className]]</font></div>")
                    .withProperty("name", InventoryObjectPool::getName)
                    .withProperty("className", InventoryObjectPool::getClassName));
            
            gridPools.addItemClickListener(e -> {
                currentPool = e.getItem();
                // builds header and the controls
                lblPoolName.setText(e.getItem().getName());
                lytRightControls.add(lblPoolName);
                createRightControlButtons(e.getItem(), object != null);
                lytRightSide.add(lytRightControls);
                // builds property sheet
                buildPropertySheet();
                updatePropertySheet(e.getItem(), object != null);
                lytRightSide.add(lytPropertySheet);
            });
            
            lytPoolsGrid.removeAll();
            lytPoolsGrid.add(gridPools);
            createHeaderGrid(parameterPool, gridPools, nameColumn, dataProviderPools);
        } catch (InvalidArgumentException | BusinessObjectNotFoundException |
                MetadataObjectNotFoundException | ApplicationObjectNotFoundException ex) {
            new SimpleNotification(ts.getTranslatedString("module.general.messages.error"),
                    ex.getMessage(), AbstractNotification.NotificationType.ERROR, ts).open();
        }
    }
    
    /**
     * Creates a header for the grid, which contains a text field to filter and the button to add a new pool.
     * @param parameterPool     The parameter pool, it can be "customerPools" or "servicePools".
     * @param grid              The current grid.
     * @param column            Column to apply the filter to.
     * @param dataProviderPools Data provider to filter.
     */
    private void createHeaderGrid(String parameterPool, Grid grid, Grid.Column column,
            ListDataProvider<InventoryObjectPool> dataProviderPools) {
        TextField txtSearchPoolByName = new TextField();
        txtSearchPoolByName.setPlaceholder(ts.getTranslatedString("module.general.labels.filter-placeholder"));
        txtSearchPoolByName.setValueChangeMode(ValueChangeMode.EAGER);
        txtSearchPoolByName.setWidthFull();
        txtSearchPoolByName.setSuffixComponent(new ActionIcon(VaadinIcon.SEARCH,
                ts.getTranslatedString("module.contractman.pool.label.filter")));
        txtSearchPoolByName.addValueChangeListener(event -> dataProviderPools.addFilter(
                pool -> StringUtils.containsIgnoreCase(pool.getName(),
                        txtSearchPoolByName.getValue())));

        ActionButton btnAddPool = null;
        if (parameterPool.equals(ServiceManagerUI.PARAMETER_CUSTOMER_POOLS)) {
            btnAddPool = new ActionButton(new ActionIcon(VaadinIcon.PLUS_SQUARE_O),
                    this.newCustomerPoolVisualAction.getModuleAction().getDisplayName());
            btnAddPool.addClickListener((event) -> {
                this.newCustomerPoolVisualAction.getVisualComponent(new ModuleActionParameterSet(
                        new ModuleActionParameter<>("commandAddCustomerPoolDashboard",
                                commandAddCustomerPoolDashboard))).open();
            });
        } else if (parameterPool.equals(ServiceManagerUI.PARAMETER_SERVICE_POOLS)) {
            btnAddPool = new ActionButton(new ActionIcon(VaadinIcon.PLUS_SQUARE_O),
                    this.newServicePoolVisualAction.getModuleAction().getDisplayName());
            btnAddPool.addClickListener((event) -> {
                this.newServicePoolVisualAction.getVisualComponent(new ModuleActionParameterSet(
                        new ModuleActionParameter<>("commandAddServicePoolDashboard",
                                commandAddServicePoolDashboard),
                        new ModuleActionParameter<>("businessObject", customer))).open();
            });
        }

        if (btnAddPool != null) {
            btnAddPool.setHeight("32px");

            HorizontalLayout lytActionButtons = new HorizontalLayout();
            lytActionButtons.setClassName("left-action-buttons");
            lytActionButtons.setSpacing(false);
            lytActionButtons.setPadding(false);
            lytActionButtons.setMargin(false);
            lytActionButtons.setWidthFull();
            lytActionButtons.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
            lytActionButtons.add(txtSearchPoolByName, btnAddPool);

            HeaderRow filterRow = grid.appendHeaderRow();
            filterRow.getCell(column).setComponent(lytActionButtons);
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Right section">
    /**
     * Builds the right section header.
     */
    private void buildRightHeader() {
        lblPoolName = new Label();
        lblPoolName.setClassName("dialog-title");
        lblPoolName.setWidth("90%");
        lytRightControls.add(lblPoolName);
    }
    
    /**
     * Creates the button that allows you to delete a pool.
     * @param pool          The selected pool.
     * @param isServicePool The selected pool is a service pool?
     */
    private void createRightControlButtons(InventoryObjectPool pool, boolean isServicePool) {
        ActionButton btnDeletePool;
        if (isServicePool) {
            btnDeletePool = new ActionButton(new ActionIcon(VaadinIcon.TRASH),
                this.deleteServicePoolVisualAction.getModuleAction().getDisplayName());
            
            btnDeletePool.addClickListener(event -> {
                this.deleteServicePoolVisualAction.getVisualComponent(new ModuleActionParameterSet(
                    new ModuleActionParameter<>(ServiceManagerUI.PARAMETER_SERVICE_POOL, pool),
                    new ModuleActionParameter<>("commandDeleteServicePoolDashboard",
                            commandDeleteServicePoolDashboard))).open();
            });
        } else {
            btnDeletePool = new ActionButton(new ActionIcon(VaadinIcon.TRASH),
                this.deleteCustomerPoolVisualAction.getModuleAction().getDisplayName());
            
            btnDeletePool.addClickListener(event -> {
                this.deleteCustomerPoolVisualAction.getVisualComponent(new ModuleActionParameterSet(
                        new ModuleActionParameter<>(ServiceManagerUI.PARAMETER_CUSTOMER_POOL, pool),
                        new ModuleActionParameter<>("commandDeleteCustomerPoolDashboard",
                                commandDeleteCustomerPoolDashboard))).open();
            });
        }
        
        lytRightActionButtons.removeAll();
        lytRightActionButtons.add(btnDeletePool);
        lytRightControls.add(lytRightActionButtons);
    }
    
    /**
     * Builds the property sheet to manage the pool properties.
     */
    private void buildPropertySheet() {
        propertySheetPool = new PropertySheet(ts, new ArrayList<>());
        propertySheetPool.addPropertyValueChangedListener(this);
        propertySheetPool.setHeightFull();
        
        lytPropertySheet.removeAll();
        lytPropertySheet.add(propertySheetPool);
    }
    
    /**
     * Gets the pool properties that is to be updated.
     * @param pool          The selected pool.
     * @param isServicePool The selected pool is a service pool?
     */
    private void updatePropertySheet(InventoryObjectPool pool, boolean isServicePool) {
        try {
            if (pool != null) {
                InventoryObjectPool aWholePool;
                if (!isServicePool) {
                    aWholePool = sms.getCustomerPool(pool.getId(), pool.getClassName());
                    this.isServicePool = false;
                }else {
                    aWholePool = sms.getServicePool(pool.getId(), pool.getClassName());
                    this.isServicePool = true;
                }
                    
                propertySheetPool.setItems(PropertyFactory.propertiesFromPoolWithoutClassName(aWholePool, ts));
            }
        } catch (ApplicationObjectNotFoundException | InvalidArgumentException  ex) {
            new SimpleNotification(ts.getTranslatedString("module.general.messages.error"),
                    ex.getMessage(), AbstractNotification.NotificationType.ERROR, ts).open();
        }
    }
    
    @Override
    public void updatePropertyChanged(AbstractProperty<? extends Object> property) {
        if (currentPool != null) {
            try {
                Session session = UI.getCurrent().getSession().getAttribute(Session.class);
                if (property.getName().equals(Constants.PROPERTY_NAME)) {
                    if (isServicePool) {
                        sms.updateServicePool(
                                currentPool.getId(),
                                currentPool.getClassName(),
                                String.valueOf(property.getValue()),
                                currentPool.getDescription(),
                                session.getUser().getUserName()
                        );
                        
                        commandAddServicePoolUI.execute();
                    } else {
                        sms.updateCustomerPool(
                                currentPool.getId(),
                                currentPool.getClassName(),
                                String.valueOf(property.getValue()),
                                currentPool.getDescription(),
                                session.getUser().getUserName()
                        );
                        
                        commandAddCustomerPoolUI.execute();
                    }
                    
                    currentPool.setName(String.valueOf(property.getValue()));
                    gridPools.getDataProvider().refreshItem(currentPool);
                    lblPoolName.setText(currentPool.getName());
                } else if (property.getDescription().equals(Constants.PROPERTY_DESCRIPTION)) {
                    if (isServicePool) {
                        sms.updateServicePool(
                                currentPool.getId(),
                                currentPool.getClassName(),
                                currentPool.getName(),
                                String.valueOf(property.getValue()),
                                session.getUser().getUserName()
                        );
                    } else {
                        sms.updateCustomerPool(
                                currentPool.getId(),
                                currentPool.getClassName(),
                                currentPool.getName(),
                                String.valueOf(property.getValue()),
                                session.getUser().getUserName()
                        );
                        
                        commandAddCustomerPoolUI.execute();
                    }
                    currentPool.setDescription(String.valueOf(property.getValue()));
                }
                updatePropertySheet(currentPool, isServicePool);
                new SimpleNotification(ts.getTranslatedString("module.general.messages.success"),
                        ts.getTranslatedString("module.general.messages.property-updated-successfully"),
                        AbstractNotification.NotificationType.INFO, ts).open();
            } catch (ApplicationObjectNotFoundException ex) {
                new SimpleNotification(ts.getTranslatedString("module.general.messages.error"), 
                        ex.getMessage(), AbstractNotification.NotificationType.ERROR, ts).open();
                propertySheetPool.undoLastEdit();
            }
        }
    }
    
    /**
     * After deleting a pool it is necessary to clean some elements.
     */
    private void clearPoolProperties() {
        lblPoolName.setText("");
        lytRightActionButtons.removeAll();
        propertySheetPool.clear();
    }
    //</editor-fold>
    
    @Override
    public AbstractAction getModuleAction() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}