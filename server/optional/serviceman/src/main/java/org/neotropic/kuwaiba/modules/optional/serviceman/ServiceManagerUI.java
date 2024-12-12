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
package org.neotropic.kuwaiba.modules.optional.serviceman;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.TemplateRenderer;
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteConfiguration;
import com.vaadin.flow.server.Command;
import com.vaadin.flow.server.VaadinService;
import org.neotropic.kuwaiba.core.apis.integration.dashboards.AbstractUI;
import org.neotropic.kuwaiba.core.apis.integration.modules.ModuleActionParameter;
import org.neotropic.kuwaiba.core.apis.integration.modules.ModuleActionParameterSet;
import org.neotropic.kuwaiba.core.apis.integration.modules.actions.AbstractVisualInventoryAction;
import org.neotropic.kuwaiba.core.apis.integration.modules.actions.ActionCompletedListener;
import org.neotropic.kuwaiba.core.apis.integration.modules.actions.ActionResponse;
import org.neotropic.kuwaiba.core.apis.integration.modules.actions.AdvancedActionsRegistry;
import org.neotropic.kuwaiba.core.apis.integration.modules.actions.CoreActionsRegistry;
import org.neotropic.kuwaiba.core.apis.integration.modules.explorers.AbstractExplorer;
import org.neotropic.kuwaiba.core.apis.integration.modules.explorers.ExplorerRegistry;
import org.neotropic.kuwaiba.core.apis.integration.modules.views.ViewWidgetRegistry;
import org.neotropic.kuwaiba.core.apis.persistence.application.ActivityLogEntry;
import org.neotropic.kuwaiba.core.apis.persistence.application.ApplicationEntityManager;
import org.neotropic.kuwaiba.core.apis.persistence.application.InventoryObjectPool;
import org.neotropic.kuwaiba.core.apis.persistence.application.Session;
import org.neotropic.kuwaiba.core.apis.persistence.business.BusinessEntityManager;
import org.neotropic.kuwaiba.core.apis.persistence.business.BusinessObjectLight;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.ApplicationObjectNotFoundException;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.BusinessObjectNotFoundException;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.InvalidArgumentException;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.InventoryException;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.MetadataObjectNotFoundException;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.OperationNotPermittedException;
import org.neotropic.kuwaiba.core.apis.persistence.metadata.MetadataEntityManager;
import org.neotropic.kuwaiba.core.apis.persistence.util.Constants;
import org.neotropic.kuwaiba.core.i18n.TranslationService;
import org.neotropic.kuwaiba.modules.core.navigation.ObjectDashboard;
import org.neotropic.kuwaiba.modules.core.navigation.ObjectOptionsPanel;
import org.neotropic.kuwaiba.modules.core.navigation.actions.ShowMoreInformationAction;
import org.neotropic.kuwaiba.modules.optional.serviceman.actions.DeleteCustomerVisualAction;
import org.neotropic.kuwaiba.modules.optional.serviceman.actions.DeleteServiceVisualAction;
import org.neotropic.kuwaiba.modules.optional.serviceman.actions.NewCustomerVisualAction;
import org.neotropic.kuwaiba.modules.optional.serviceman.actions.NewServiceFromTemplateVisualAction;
import org.neotropic.kuwaiba.modules.optional.serviceman.actions.NewServiceVisualAction;
import org.neotropic.kuwaiba.modules.optional.serviceman.actions.RelateNetworkResourceToServiceVisualAction;
import org.neotropic.kuwaiba.modules.optional.serviceman.explorers.DialogServiceManagerSearch;
import org.neotropic.kuwaiba.modules.optional.serviceman.widgets.PoolDashboard;
import org.neotropic.kuwaiba.visualization.api.properties.PropertyValueConverter;
import org.neotropic.kuwaiba.visualization.api.resources.ResourceFactory;
import org.neotropic.util.visual.button.ActionButton;
import org.neotropic.util.visual.dialog.ConfirmDialog;
import org.neotropic.util.visual.icon.ActionIcon;
import org.neotropic.util.visual.icons.ClassNameIconGenerator;
import org.neotropic.util.visual.notifications.AbstractNotification;
import org.neotropic.util.visual.notifications.SimpleNotification;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.neotropic.kuwaiba.modules.core.logging.LoggingService;

/**
 * Main for the service manager module. This class manages how the pages
 * corresponding to different functionalities are presented in a single place.
 * 
 * @author Charles Edward Bedon Cortazar {@literal <charles.bedon@kuwaiba.org>}
 * @author Mauricio Ruiz Beltrán {@literal <mauricio.ruiz@kuwaiba.org>}
 */
@Route(value = "serviceman", layout = ServiceManagerLayout.class)
public class ServiceManagerUI extends VerticalLayout implements ActionCompletedListener, HasDynamicTitle, AbstractUI {

    /**
     * Reference to the Service Manager Service
     */
    @Autowired
    private ServiceManagerService sms;
    /**
     * Reference to the Business Entity Manager
     */
    @Autowired
    private BusinessEntityManager bem;
    /**
     * Reference to the Metadata Entity Manager
     */
    @Autowired
    private MetadataEntityManager mem;
    /**
     * Reference to the Application Entity Manager
     */
    @Autowired
    private ApplicationEntityManager aem;
    /**
     * Reference to the translation service
     */
    @Autowired
    private TranslationService ts;
    /**
     * Factory to build resources from data source
     */
    @Autowired
    private ResourceFactory resourceFactory;
    /**
     * Reference to the action registry
     */
    @Autowired
    private CoreActionsRegistry actionRegistry;
    /**
     * Reference to the action registry
     */
    @Autowired
    private AdvancedActionsRegistry advancedActionsRegistry;
    /**
     * All the object-related views exposed by other modules
     */
    @Autowired
    private ViewWidgetRegistry viewWidgetRegistry;
    /**
     * All the registered explorers
     */
    @Autowired
    private ExplorerRegistry explorerRegistry;
    /**
     * Reference to the action that creates customers
     */
    @Autowired
    private NewCustomerVisualAction newCustomerVisualAction;
    /**
     * Reference to the action that creates services
     */
    @Autowired
    private NewServiceVisualAction newServiceVisualAction;
    /**
     * Reference to the action that creates services from template
     */
    @Autowired
    private NewServiceFromTemplateVisualAction newServiceFromTemplateVisualAction;
    /**
     * Reference to the action that deletes customers
     */
    @Autowired
    private DeleteCustomerVisualAction deleteCustomerVisualAction;
    /**
     * Reference to the action that deletes services
     */
    @Autowired
    private DeleteServiceVisualAction deleteServiceVisualAction;
    /**
     * Reference to the action that relates a service to a network resource.
     */
    @Autowired
    private RelateNetworkResourceToServiceVisualAction relateNetworkResourceToServiceVisualAction;
    /**
     * The window to show more information about an object
     */
    @Autowired
    private ShowMoreInformationAction windowMoreInformation;
    /**
     * The visual action for pool management
     */
    @Autowired
    private PoolDashboard poolDashboard;
    /**
     * Reference to the Logging Service.
     */
    @Autowired
    private LoggingService log;
    /**
     * Parameter for customer pools
     */
    public static final String PARAMETER_CUSTOMER_POOLS = "customerPools";
    /**
     * Parameter for customer pool
     */
    public static final String PARAMETER_CUSTOMER_POOL = "customerPool";
    /**
     * Parameter for customer
     */
    public static final String PARAMETER_CUSTOMER = "customer";
    /**
     * Parameter for service pool
     */
    public static final String PARAMETER_SERVICE_POOLS = "servicePools";
    /**
     * Parameter for service pool
     */
    public static final String PARAMETER_SERVICE_POOL = "servicePool";
    /**
     * Parameter for service
     */
    public static final String PARAMETER_SERVICE = "service";
    /**
     * Parameter business object
     */
    public static String PARAMETER_BUSINESS_OBJECT = "businessObject";
    /**
     * Number of object per class
     */
    private static final int RESULTS_OBJECTS_PER_CLASS = 8;
    /**
     * The component to show the results of a search
     */
    private DialogServiceManagerSearch customerSearchDialog;
    private DialogServiceManagerSearch servicesSearchDialog;
    /**
     * An icon generator for create icons
     */
    private ClassNameIconGenerator iconGenerator;
    /**
     * Main layout 
     */
    private HorizontalLayout lytContent;
    /**
     * Layouts shown in the left section
     */
    private VerticalLayout lytLeft;
    private HorizontalLayout lytCustomerPools;
    private HorizontalLayout lytCustomerOptions;
    /**
     * Layouts shown in the center section
     */
    private VerticalLayout lytCenter;
    private HorizontalLayout lytServicePools;
    private HorizontalLayout lytServiceOptions;
    /**
     * Layouts shown in the right section
     */
    private VerticalLayout lytRight;
    private VerticalLayout lytDetailsPanel;
    /**
     * Grids to show the customers and services
     */
    private Grid<BusinessObjectLight> gridCustomers;
    private Grid<BusinessObjectLight> gridServices;
    /**
     * Saves the customer and service list
     */
    private List<BusinessObjectLight> listCustomers;
    private List<BusinessObjectLight> listServices;
    /**
     * Buttons that allow you to manage the pools and their objects
     */
    private ActionButton btnManageCustomerPools;
    private ActionButton btnManageServicePools;
    private ActionButton btnAddCustomer;
    private ActionButton btnAddService;
    private ActionButton btnAddServiceFromTemplate;
    /**
     * Combo box to customer and service pools
     */
    private ComboBox<InventoryObjectPool> cmbCustomerPools;
    private ComboBox<InventoryObjectPool> cmbServicePools;
    /**
     * Saves the selected pool
     */
    private InventoryObjectPool selectedCustomerPool;
    private InventoryObjectPool selectedServicePool;
    /**
     * Saves the selected customer
     */
    private BusinessObjectLight selectedCustomer;
    
    @Override
    public String getPageTitle() {
        return ts.getTranslatedString("module.serviceman.title");
    }
    
    @Override
    public void onAttach(AttachEvent ev) {
        this.newCustomerVisualAction.registerActionCompletedLister(this);
        this.newServiceVisualAction.registerActionCompletedLister(this);
        this.newServiceFromTemplateVisualAction.registerActionCompletedLister(this);
        this.deleteCustomerVisualAction.registerActionCompletedLister(this);
        this.deleteServiceVisualAction.registerActionCompletedLister(this);
        this.relateNetworkResourceToServiceVisualAction.registerActionCompletedLister(this);
    }
    
    @Override
    public void onDetach(DetachEvent ev) {
        this.newCustomerVisualAction.unregisterListener(this);
        this.newServiceVisualAction.unregisterListener(this);
        this.newServiceFromTemplateVisualAction.unregisterListener(this);
        this.deleteCustomerVisualAction.unregisterListener(this);
        this.deleteServiceVisualAction.unregisterListener(this);
        this.relateNetworkResourceToServiceVisualAction.unregisterListener(this);
    }
    
    @Override
    public void actionCompleted(ActionCompletedListener.ActionCompletedEvent ev) {
        if (ev.getStatus() == ActionCompletedListener.ActionCompletedEvent.STATUS_SUCCESS) {
            if (ev.getActionResponse() != null) {
                if (ev.getActionResponse().containsKey(ActionResponse.ActionType.ADD)) {
                    if (ev.getActionResponse().containsKey(PARAMETER_CUSTOMER_POOL)
                            && ev.getActionResponse().get(PARAMETER_CUSTOMER_POOL) != null
                            && gridCustomers != null) {
                        refreshCustomersDataProvider((InventoryObjectPool) ev.getActionResponse().get(PARAMETER_CUSTOMER_POOL));
                    } else if (ev.getActionResponse().containsKey(PARAMETER_SERVICE_POOL)
                            && ev.getActionResponse().get(PARAMETER_SERVICE_POOL) != null
                            && gridServices != null) {
                        refreshServiceDataProvider((InventoryObjectPool) ev.getActionResponse().get(PARAMETER_SERVICE_POOL));
                    }
                } else if (ev.getActionResponse().containsKey(ActionResponse.ActionType.REMOVE)) {
                    if (ev.getActionResponse().containsKey(PARAMETER_CUSTOMER)
                            && ev.getActionResponse().get(PARAMETER_CUSTOMER) != null
                            && gridCustomers != null) {
                        if (selectedCustomerPool != null)
                            refreshCustomersDataProvider(selectedCustomerPool);
                        else {
                            BusinessObjectLight customer = (BusinessObjectLight) ev.getActionResponse().get(PARAMETER_CUSTOMER);
                            if (listCustomers != null && listCustomers.contains(customer)) {
                                listCustomers.remove(customer);
                                gridCustomers.setItems(listCustomers);
                            } else
                                gridCustomers.getDataProvider().refreshAll();
                        }
                        clearCustomerResources();
                    } else if (ev.getActionResponse().containsKey(PARAMETER_SERVICE)
                            && ev.getActionResponse().get(PARAMETER_SERVICE) != null
                            && gridServices != null) {
                        if (selectedServicePool != null)
                            refreshServiceDataProvider(selectedServicePool);
                        else {
                            BusinessObjectLight service = (BusinessObjectLight) ev.getActionResponse().get(PARAMETER_SERVICE);
                            if (listServices != null && listServices.contains(service)) {
                                listServices.remove(service);
                                gridServices.setItems(listServices);
                            } else
                                gridServices.getDataProvider().refreshAll();
                        }
                    }
                    lytDetailsPanel.removeAll();
                }
            }
            new SimpleNotification(ts.getTranslatedString("module.general.messages.success"),
                    ev.getMessage(), AbstractNotification.NotificationType.INFO, ts).open();
        } else {
            new SimpleNotification(ts.getTranslatedString("module.general.messages.error"),
                    ev.getMessage(), AbstractNotification.NotificationType.ERROR, ts).open();
        }
    }

    @Override
    public void initContent() {
        setPadding(false);
        setMargin(false);
        setSizeFull();
        
        setupLayouts();
        controlButtons();
        buildCustomerPoolsFilter();
        iconGenerator = new ClassNameIconGenerator(resourceFactory);
        buildGridCustomers();
        buildCustomerSearchDialog();
        buildServicePoolsFilter();
        buildGridServices();
        buildServiceSearchDialog();
        
        lytContent.add(lytLeft, lytCenter, lytRight);
        add(lytContent);
    }
    
    /**
     * Creates the layouts for each of the sections.
     */
    private void setupLayouts() {
        // Main layout
        if (lytContent == null) {
            lytContent = new HorizontalLayout();
            lytContent.setId("lytContent");
            lytContent.setWidthFull();
            lytContent.setHeightFull();
        }
        
        // --> Init left section
        if (lytLeft == null) {
            lytLeft = new VerticalLayout();
            lytLeft.setSpacing(false);
            lytLeft.setMargin(false);
            lytLeft.setId("lytLeft");
            lytLeft.setWidth("33%");
            lytLeft.setHeightFull();
        }
        if (lytCustomerPools == null) {
            lytCustomerPools = new HorizontalLayout();
            lytCustomerPools.setSpacing(true);
            lytCustomerPools.setMargin(false);
            lytCustomerPools.setPadding(false);
            lytCustomerPools.setId("lytCustomerPools");
            lytCustomerPools.setWidthFull();
        }
        if (lytCustomerOptions == null) {
            lytCustomerOptions = new HorizontalLayout();
            lytCustomerOptions.setSpacing(true);
            lytCustomerOptions.setMargin(false);
            lytCustomerOptions.setPadding(false);
            lytCustomerOptions.setId("lytCustomerOptions");
            lytCustomerOptions.setWidthFull();
        }
        // <-- End left section
        
        // --> Init center section
        if (lytCenter == null) {
            lytCenter = new VerticalLayout();
            lytCenter.setSpacing(false);
            lytCenter.setMargin(false);
            lytCenter.setId("lytLeft");
            lytCenter.setWidth("33%");
            lytCenter.setHeightFull();
        }
        if (lytServicePools == null) {
            lytServicePools = new HorizontalLayout();
            lytServicePools.setSpacing(true);
            lytServicePools.setMargin(false);
            lytServicePools.setPadding(false);
            lytServicePools.setId("lytServicePools");
            lytServicePools.setWidthFull();
        }
        if (lytServiceOptions == null) {
            lytServiceOptions = new HorizontalLayout();
            lytServiceOptions.setSpacing(true);
            lytServiceOptions.setMargin(false);
            lytServiceOptions.setPadding(false);
            lytServiceOptions.setId("lytServiceOptions");
            lytServiceOptions.setWidthFull();
        }
        // <-- End center section
        
        // --> Init right section
        if (lytRight == null) {
            lytRight = new VerticalLayout();
            lytRight.setSpacing(false);
            lytRight.setMargin(false);
            lytRight.setId("lytRight");
            lytRight.setWidth("33%");
            lytRight.setHeightFull();
        }
        if (lytDetailsPanel == null) {
            lytDetailsPanel = new VerticalLayout();
            lytDetailsPanel.setSpacing(false);
            lytDetailsPanel.setMargin(false);
            lytDetailsPanel.setPadding(false);
            lytDetailsPanel.setId("lytDetailsPanel");
            lytDetailsPanel.setWidthFull();
            lytDetailsPanel.setHeightFull();
            lytRight.add(lytDetailsPanel);
        }
        // <-- End right section
    }
    
    /**
     * Creates the necessary buttons to manage customers and services.
     */
    private void controlButtons() {
        btnManageCustomerPools = new ActionButton(new ActionIcon(VaadinIcon.COG),
                ts.getTranslatedString("module.serviceman.actions.manage-customer-pool.name"));
        
        btnManageCustomerPools.getStyle().set("margin-top", "30px");
        btnManageCustomerPools.setId("btnManageCustomerPools");
        btnManageCustomerPools.setHeight("32px");
        btnManageCustomerPools.setWidth("10%");
        btnManageCustomerPools.addClickListener(event -> launchPoolDialog(PARAMETER_CUSTOMER_POOLS, null));

        btnManageServicePools = new ActionButton(new ActionIcon(VaadinIcon.COG),
                ts.getTranslatedString("module.serviceman.actions.manage-service-pool.name"));
        btnManageServicePools.getStyle().set("margin-top", "30px");
        btnManageServicePools.setId("btnManageServicePools");
        btnManageServicePools.setHeight("32px");
        btnManageServicePools.setWidth("10%");
        btnManageServicePools.addClickListener(event -> {
            if (selectedCustomer == null) {
                new SimpleNotification(ts.getTranslatedString("module.general.messages.warning"),
                        ts.getTranslatedString("module.serviceman.actions.manage-service-pool.warning-unselected-customer"),
                        AbstractNotification.NotificationType.WARNING, ts).open();
            } else {
                launchPoolDialog(PARAMETER_SERVICE_POOLS, selectedCustomer);
            }
        });

        btnAddCustomer = new ActionButton(new ActionIcon(VaadinIcon.PLUS_SQUARE_O),
                this.newCustomerVisualAction.getModuleAction().getDisplayName());
        btnAddCustomer.addClickListener(event -> {
            if (selectedCustomerPool == null) {
                new SimpleNotification(ts.getTranslatedString("module.general.messages.warning"),
                    ts.getTranslatedString("module.serviceman.actions.new-customer.warning-unselected-customer-pool"),
                        AbstractNotification.NotificationType.WARNING, ts).open();
            } else
                this.newCustomerVisualAction.getVisualComponent(new ModuleActionParameterSet(
                        new ModuleActionParameter<>(PARAMETER_CUSTOMER_POOL,
                                selectedCustomerPool))).open();
        });
        btnAddCustomer.setId("btnAddCustomer");
        btnAddCustomer.setHeight("32px");
        btnAddCustomer.setWidth("10%");
        
        btnAddService = new ActionButton(new ActionIcon(VaadinIcon.PLUS_SQUARE_O),
                this.newServiceVisualAction.getModuleAction().getDisplayName());
        btnAddService.addClickListener(event -> {
            if (selectedServicePool == null) {
                new SimpleNotification(ts.getTranslatedString("module.general.messages.warning"),
                        ts.getTranslatedString("module.serviceman.actions.new-service.warning-unselected-service-pool"),
                        AbstractNotification.NotificationType.WARNING, ts).open();
            } else
                this.newServiceVisualAction.getVisualComponent(new ModuleActionParameterSet(
                        new ModuleActionParameter<>(PARAMETER_SERVICE_POOL, selectedServicePool))).open();
        });
        btnAddService.setId("btnAddService");
        btnAddService.setHeight("32px");
        btnAddService.setWidth("10%");

        btnAddServiceFromTemplate = new ActionButton(new ActionIcon(VaadinIcon.ASTERISK),
                this.newServiceFromTemplateVisualAction.getModuleAction().getDisplayName());
        btnAddServiceFromTemplate.addClickListener(event -> {
            if (selectedServicePool == null) {
                new SimpleNotification(ts.getTranslatedString("module.general.messages.warning"),
                        ts.getTranslatedString("module.serviceman.actions.new-service.warning-unselected-service-pool"),
                        AbstractNotification.NotificationType.WARNING, ts).open();
            } else
                this.newServiceFromTemplateVisualAction.getVisualComponent(new ModuleActionParameterSet(
                        new ModuleActionParameter<>(PARAMETER_SERVICE_POOL, selectedServicePool))).open();
        });
        btnAddServiceFromTemplate.setId("btnAddServiceFromTemplate");
        btnAddServiceFromTemplate.setHeight("32px");
        btnAddServiceFromTemplate.setWidth("10%");
    }
    
    /**
     * After a search the searched text is process to create a result of
     * business objects grouped by class name in grids.
     * @param searchedText The searched text.
     * @param classToApply Class to which the filter will be applied, it can be "GenericCustomer" or "GenericService".
     */
    private List<BusinessObjectLight> processSearch(String searchedText, String classToApply) {
        if (searchedText != null && !searchedText.trim().isEmpty()) {
            if (classToApply.equals(Constants.CLASS_GENERICSERVICE)
                    || classToApply.equals(Constants.CLASS_GENERICCUSTOMER)) {
                List<BusinessObjectLight> suggestedObjectsResults = bem.getSuggestedObjectsWithFilter(
                        searchedText, 0,
                        RESULTS_OBJECTS_PER_CLASS,
                        classToApply
                );
                return suggestedObjectsResults;
            }
        }
        return new ArrayList<>();
    }
    
    /**
     * Launches the dialog that allows you to manage the  customer and service pool.
     * @param parameterPool The parameter pool, it can be "customerPools" or "servicePools".
     * @param object        The selected object if exists.
     */
    private void launchPoolDialog(String parameterPool, BusinessObjectLight object) {
        Command commandAddCustomerPoolUI = () -> cmbCustomerPools.setItems(getCustomerPoolsData());
        
        Command commandDeleteCustomerPoolUI = () -> cmbCustomerPools.setItems(getCustomerPoolsData());
        
        Command commandAddServicePoolUI = () -> {
            if (selectedCustomer != null)
                cmbServicePools.setItems(getServicePoolsData(selectedCustomer));
        };
        
        Command commandDeleteServicePoolUI = () -> {
            if (selectedCustomer != null)
                cmbServicePools.setItems(getServicePoolsData(selectedCustomer));
        };
        
        ModuleActionParameterSet parameters = new ModuleActionParameterSet();
        if (object == null) {
            parameters.put("commandAddCustomerPoolUI", commandAddCustomerPoolUI);
            parameters.put("commandDeleteCustomerPoolUI", commandDeleteCustomerPoolUI);
        } else {
            parameters.put(ServiceManagerUI.PARAMETER_CUSTOMER, object);
            parameters.put("commandAddServicePoolUI", commandAddServicePoolUI);
            parameters.put("commandDeleteServicePoolUI", commandDeleteServicePoolUI);
        }
        parameters.put(parameterPool, "");
        this.poolDashboard.getVisualComponent(parameters).open();
    }
    
    /**
     * Cleans up customer resources when the selected customer is deleted.
     */
    private void clearCustomerResources() {
        if (cmbServicePools != null)
            cmbServicePools.setItems(new ArrayList<>());
        if (gridServices != null)
            gridServices.setItems(new ArrayList<>());
    }
    
    /**
     * Cleans up elements when a new search is performed.
     * @param isService Searching a service?
     */
    private void clearElements(boolean isService) {
        cmbCustomerPools.setValue(null);
        selectedCustomerPool = null;
        cmbServicePools.setValue(null);
        selectedServicePool = null;
        if (isService) {
            gridCustomers.setItems(new ArrayList<>());
            customerSearchDialog.clearSearch();
            selectedCustomer = null;
        } else {
            gridServices.setItems(new ArrayList<>());
            servicesSearchDialog.clearSearch();
        }
        lytDetailsPanel.removeAll();
    }
    
    //<editor-fold defaultstate="collapsed" desc="Left section">
    /**
     * Gets the customer pools data.
     * @return The customer pools list.
     */
    private List<InventoryObjectPool> getCustomerPoolsData() {
        try {
            return sms.getCustomerPools();
        } catch (InvalidArgumentException ex) {
            new SimpleNotification(ts.getTranslatedString("module.general.messages.error"),
                    ex.getMessage(), AbstractNotification.NotificationType.ERROR, ts).open();
            return new ArrayList<>();
        }
    }
    
    /**
     * Creates the customer pool filter, a combo box is displayed.
     */
    private void buildCustomerPoolsFilter() {
        cmbCustomerPools = new ComboBox<>(
                ts.getTranslatedString("module.serviceman.customer-pool.filter"));
        cmbCustomerPools.setItems(getCustomerPoolsData());
        cmbCustomerPools.setId("cmbCustomerPools");
        cmbCustomerPools.setWidth("90%");
        
        cmbCustomerPools.addValueChangeListener(e -> {
            if (e.getValue() != null) {
                refreshCustomersDataProvider(e.getValue());
                selectedCustomerPool = e.getValue();
                servicesSearchDialog.clearSearch();
                customerSearchDialog.clearSearch();
                cmbServicePools.setItems(new ArrayList<>());
                gridServices.setItems(new ArrayList<>());
                selectedCustomer = null;
                lytDetailsPanel.removeAll();
            } else
                selectedCustomerPool = null;
        });
        
        lytCustomerPools.add(cmbCustomerPools, btnManageCustomerPools);
        lytLeft.add(lytCustomerPools);
    }
    
    /**
     * Updates customer data provider.
     * @param pool The selected customer pool.
     */
    private void refreshCustomersDataProvider(InventoryObjectPool pool) {
        try {
            List<BusinessObjectLight> customers = sms.getCustomersInPool(pool.getId(), null, 0, 50);
            gridCustomers.setItems(customers);
        } catch (ApplicationObjectNotFoundException | InvalidArgumentException ex) {
            new SimpleNotification(ts.getTranslatedString("module.general.messages.error"),
                    ex.getMessage(), AbstractNotification.NotificationType.ERROR, ts).open();
        }
    }
    
    /**
     * Builds the grid that shows the customers of a previously selected customer pool.
     */
    private void buildGridCustomers() {
        gridCustomers = new Grid<>();
        gridCustomers.setWidthFull();
        gridCustomers.setHeightFull();
        gridCustomers.setId("gridCustomers");
        gridCustomers.addThemeVariants(GridVariant.LUMO_NO_ROW_BORDERS
                , GridVariant.LUMO_COMPACT, GridVariant.LUMO_WRAP_CELL_CONTENT);
        gridCustomers.setSelectionMode(Grid.SelectionMode.SINGLE);
        
        gridCustomers.addColumn(TemplateRenderer.<BusinessObjectLight>of(
                "<div>[[item.name]] &nbsp; <font class=\"text-secondary\">[[item.className]]</font></div>")
                .withProperty("name", BusinessObjectLight::getName)
                .withProperty("className", BusinessObjectLight::getClassName))
                .setHeader(ts.getTranslatedString("module.serviceman.dashboard.ui.customers"));
        gridCustomers.addComponentColumn(this::deleteCustomer).setTextAlign(ColumnTextAlign.END).setFlexGrow(0);
        
        gridCustomers.addItemClickListener(e -> {
            cmbServicePools.setItems(getServicePoolsData(e.getItem()));
            buildDetailsPanel(e.getItem(), true, false);
            selectedCustomer = e.getItem();
        });
    }
    
    /**
     * Build a dynamic dialog that displays search results as the user types in the search box, applies to customers.
     */
    private void buildCustomerSearchDialog() {
        customerSearchDialog = new DialogServiceManagerSearch(ts, bem, mem, 
                iconGenerator, Constants.CLASS_GENERICCUSTOMER,
                ts.getTranslatedString("module.serviceman.customer.filter"), e -> {
                    listCustomers = new ArrayList<>();
                    if (e instanceof String) { // No suggestion was chosen
                        List<BusinessObjectLight> listCustomer = 
                                processSearch((String) e, Constants.CLASS_GENERICCUSTOMER);
                        if (!listCustomer.isEmpty())                            
                            listCustomers = listCustomer;
                    } else { // A single element was selected
                        if (e instanceof BusinessObjectLight)
                            listCustomers.add((BusinessObjectLight) e);
                    }

                    if (cmbServicePools != null)
                        cmbServicePools.setItems(new ArrayList<>());
                    selectedCustomer = null;
                    gridCustomers.setItems(listCustomers);
                    clearElements(false);
                    customerSearchDialog.close();
                });
        customerSearchDialog.setId("customerSearchDialog");
        customerSearchDialog.setWidth("90%");
        
        lytCustomerOptions.add(customerSearchDialog, btnAddCustomer);
        lytLeft.add(lytCustomerOptions, gridCustomers);
    }
    
    /**
     * Builds a component that allows to delete a client, this is shown next to the name for each of the customers in the grid.
     * @param object The selected customer.
     * @return Visual component that allows you to delete a customer.
     */
    private Component deleteCustomer(BusinessObjectLight object) {
        ActionButton btnDeleteCustomer = new ActionButton(new Icon(VaadinIcon.TRASH),
                this.deleteCustomerVisualAction.getModuleAction().getDisplayName());
        btnDeleteCustomer.addClickListener(event -> deleteCustomerVisualAction.getVisualComponent(
                new ModuleActionParameterSet(new ModuleActionParameter<>(PARAMETER_BUSINESS_OBJECT, object))
        ).open());

        HorizontalLayout lytActions = new HorizontalLayout(btnDeleteCustomer);
        lytActions.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        lytActions.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        lytActions.setHeight("22px");
        return lytActions;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Center section">
    /**
     * Gets the data from the service pool the a customer.
     * @param customer The selected customer.
     * @return The customer pools list.
     */
    private List<InventoryObjectPool> getServicePoolsData(BusinessObjectLight customer) {
        try {
            return sms.getServicePoolsInCostumer(customer.getClassName(), customer.getId(),
                    Constants.CLASS_GENERICSERVICE);
        } catch (InvalidArgumentException | BusinessObjectNotFoundException |
                MetadataObjectNotFoundException | ApplicationObjectNotFoundException ex) {
            new SimpleNotification(ts.getTranslatedString("module.general.messages.error"),
                    ex.getMessage(), AbstractNotification.NotificationType.ERROR, ts).open();
            return new ArrayList<>();
        }
    }
    
    /**
     * Creates the service pool filter, a combo box is displayed.
     */
    private void buildServicePoolsFilter() {
        cmbServicePools = new ComboBox<>(ts.getTranslatedString("module.serviceman.service-pool.filter"));
        cmbServicePools.setId("cmbServicePools");
        cmbServicePools.setWidth("90%");
        
        cmbServicePools.addValueChangeListener(e -> {
            if (e.getValue() != null) {
                refreshServiceDataProvider(e.getValue());
                selectedServicePool = e.getValue();
                servicesSearchDialog.clearSearch();
            } else 
                selectedServicePool = null;
        });
        
        lytServicePools.add(cmbServicePools, btnManageServicePools);
        lytCenter.add(lytServicePools);
    }
    
    /**
     * Updates service data provider.
     * @param pool The selected service pool.
     */
    private void refreshServiceDataProvider(InventoryObjectPool pool) {
        try {
            List<BusinessObjectLight> services = sms.getServicesInPool(pool.getId(), null, 0, 50);
            gridServices.setItems(services);
        } catch (ApplicationObjectNotFoundException | InvalidArgumentException ex) {
            new SimpleNotification(ts.getTranslatedString("module.general.messages.error"),
                    ex.getMessage(), AbstractNotification.NotificationType.ERROR, ts).open();
        }
    }
    
    /**
     * Builds the grid that shows the services of a previously selected service pool.
     */
    private void buildGridServices() {
        gridServices = new Grid<>();
        gridServices.setWidthFull();
        gridServices.setHeightFull();
        gridServices.setId("gridServices");
        gridServices.addThemeVariants(GridVariant.LUMO_NO_ROW_BORDERS
                , GridVariant.LUMO_COMPACT, GridVariant.LUMO_WRAP_CELL_CONTENT);
        gridServices.setSelectionMode(Grid.SelectionMode.SINGLE);
        
        gridServices.addColumn(TemplateRenderer.<BusinessObjectLight>of(
                "<div>[[item.name]] &nbsp; <font class=\"text-secondary\">[[item.className]]</font></div>")
                .withProperty("name", BusinessObjectLight::getName)
                .withProperty("className", BusinessObjectLight::getClassName))
                .setHeader(ts.getTranslatedString("module.serviceman.dashboard.ui.services"));
        gridServices.addComponentColumn(this::deleteService).setTextAlign(ColumnTextAlign.END).setFlexGrow(0);
        gridServices.addItemClickListener(e -> buildDetailsPanel(e.getItem(), false, true));
    }
    
    /**
     * Builds a component that allows to delete a client, this is shown next to the name for each of the services in the grid.
     * @param object The selected service.
     * @return Visual component that allows you to delete a service.
     */
    private Component deleteService(BusinessObjectLight object) {
        ActionButton btnDeleteService = new ActionButton(new Icon(VaadinIcon.TRASH),
                this.deleteServiceVisualAction.getModuleAction().getDisplayName());
        btnDeleteService.addClickListener(event -> deleteServiceVisualAction.getVisualComponent(
                new ModuleActionParameterSet(new ModuleActionParameter<>(PARAMETER_BUSINESS_OBJECT, object))
        ).open());

        HorizontalLayout lytActions = new HorizontalLayout(btnDeleteService);
        lytActions.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        lytActions.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        lytActions.setHeight("22px");
        return lytActions;
    }
    
    /**
     * Build a dynamic dialog that displays search results as the user types in the search box, applies to services.
     */
    private void buildServiceSearchDialog() {
        servicesSearchDialog = new DialogServiceManagerSearch(ts, bem, mem, 
                iconGenerator, Constants.CLASS_GENERICSERVICE,
                ts.getTranslatedString("module.serviceman.service.filter"), e -> {
                    listServices = new ArrayList<>();
                    if (e instanceof String) { // No suggestion was chosen
                        List<BusinessObjectLight> listService = 
                                processSearch((String) e, Constants.CLASS_GENERICSERVICE);
                        if (!listService.isEmpty()) {
                            listServices.clear();
                            listServices = listService;
                        }
                    } else { // A single element was selected
                        if (e instanceof BusinessObjectLight) {
                            listServices.clear();
                            listServices.add((BusinessObjectLight) e);
                        }
                    }
                    gridServices.setItems(listServices);
                    clearElements(true);
                    servicesSearchDialog.close();
                });
        servicesSearchDialog.setId("servicesSearchDialog");
        servicesSearchDialog.setWidth("80%");

        lytServiceOptions.add(servicesSearchDialog, btnAddService, btnAddServiceFromTemplate);
        lytCenter.add(lytServiceOptions, gridServices);
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Right section">
    /**
     * Creates the right-most layout with the options for the selected object.
     * @param selectedObject The selected object in the customers or services grid.
     * @param isCustomer     The selected object is a customer?
     * @param isService      The selected object is a service?
     */
    private void buildDetailsPanel(BusinessObjectLight selectedObject, boolean isCustomer, boolean isService) {
        if (selectedObject != null && (isCustomer || isService)) {
            try {
                lytDetailsPanel.removeAll();

                ObjectOptionsPanel pnlOptions = new ObjectOptionsPanel(selectedObject,
                        actionRegistry, advancedActionsRegistry,
                        viewWidgetRegistry, explorerRegistry,
                        mem, aem, bem, ts, log);
                pnlOptions.setShowViews(false);
                pnlOptions.setShowExplorers(true);
                pnlOptions.setSelectionListener((event) -> {
                    switch (event.getActionCommand()) {
                        case ObjectOptionsPanel.EVENT_ACTION_SELECTION:
                            ModuleActionParameterSet parameters = new ModuleActionParameterSet(
                                    new ModuleActionParameter<>(PARAMETER_BUSINESS_OBJECT, selectedObject));
                            Dialog wdwObjectAction = (Dialog) ((AbstractVisualInventoryAction) event.getSource())
                                    .getVisualComponent(parameters);
                            wdwObjectAction.open();
                            break;
                        case ObjectOptionsPanel.EVENT_EXPLORER_SELECTION:
                            ConfirmDialog wdwExplorer = new ConfirmDialog(ts);
                            wdwExplorer.getBtnConfirm().addClickListener(ev -> wdwExplorer.close());
                            wdwExplorer.getBtnCancel().setVisible(false);
                            wdwExplorer.setHeader(String.format(ts.getTranslatedString(
                                    ((AbstractExplorer<?>) event.getSource()).getHeader()), selectedObject.getName()));
                            wdwExplorer.setContent(((AbstractExplorer<?>) event.getSource()).build(selectedObject));
                            wdwExplorer.setHeight("90%");
                            wdwExplorer.setMinWidth("70%");
                            wdwExplorer.open();
                            break;
                    }
                });

                pnlOptions.setPropertyListener(property -> {
                    try {
                        Session session = UI.getCurrent().getSession().getAttribute(Session.class);
                        Object lastValue = pnlOptions.lastValue(property.getName());
                        HashMap<String, String> attributes = new HashMap<>();
                        attributes.put(property.getName(), PropertyValueConverter.getAsStringToPersist(property));

                        if (isCustomer) {
                            try {
                                sms.updateCustomer(selectedObject.getClassName(), selectedObject.getId(),
                                        attributes, session.getUser().getUserName());
                                if (property.getName().equals(Constants.PROPERTY_NAME) && gridCustomers != null) {
                                    selectedObject.setName(PropertyValueConverter.getAsStringToPersist(property));
                                    gridCustomers.getDataProvider().refreshItem(selectedObject);
                                }
                            } catch (MetadataObjectNotFoundException | BusinessObjectNotFoundException |
                                    OperationNotPermittedException | InvalidArgumentException |
                                    ApplicationObjectNotFoundException ex) {
                                new SimpleNotification(ts.getTranslatedString("module.general.messages.error"),
                                        ex.getMessage(), AbstractNotification.NotificationType.ERROR, ts).open();
                            }
                        } else if (isService) {
                            try {
                                sms.updateService(selectedObject.getClassName(), selectedObject.getId(),
                                        attributes, session.getUser().getUserName());
                            } catch (MetadataObjectNotFoundException | OperationNotPermittedException |
                                    InvalidArgumentException ex) {
                                new SimpleNotification(ts.getTranslatedString("module.general.messages.error"),
                                        ex.getMessage(), AbstractNotification.NotificationType.ERROR, ts).open();
                            }
                            if (property.getName().equals(Constants.PROPERTY_NAME) && gridServices != null) {
                                selectedObject.setName(PropertyValueConverter.getAsStringToPersist(property));
                                gridServices.getDataProvider().refreshItem(selectedObject);
                            }
                        }
                        aem.createObjectActivityLogEntry(session.getUser().getUserName(),
                                selectedObject.getClassName(), selectedObject.getId(),
                                ActivityLogEntry.ACTIVITY_TYPE_UPDATE_INVENTORY_OBJECT,
                                property.getName(), lastValue == null ? "" : lastValue.toString(),
                                property.getAsString(), "");
                    } catch (ApplicationObjectNotFoundException | BusinessObjectNotFoundException ex) {
                        new SimpleNotification(ts.getTranslatedString("module.general.messages.error"),
                                ex.getMessage(), AbstractNotification.NotificationType.ERROR, ts).open();
                    }
                });

                Label lblTitle = new Label(selectedObject.toString());
                lblTitle.setClassName("dialog-title");

                Button btnGoToDashboard = new Button(
                        ts.getTranslatedString("module.navigation.widgets.object-dashboard.open-to-dashboard"));
                btnGoToDashboard.setWidth("50%");
                
                Button btnInfo = new Button(
                        ts.getTranslatedString("module.navigation.actions.show-more-information-button-name"));
                btnInfo.setWidthFull();

                // Action go to Dashboard
                btnGoToDashboard.addClickListener(ev
                        -> getUI().ifPresent(ui -> {
                            ui.getSession().setAttribute(BusinessObjectLight.class, selectedObject);
                            ui.getPage().open(RouteConfiguration.forRegistry(
                                    VaadinService.getCurrent().getRouter().getRegistry())
                                    .getUrl(ObjectDashboard.class), "_blank");
                        })
                );

                btnInfo.addClickListener(e -> this.windowMoreInformation.getVisualComponent(new ModuleActionParameterSet(
                        new ModuleActionParameter<>("object", selectedObject))).open());

                HorizontalLayout lytExtraActions = new HorizontalLayout(/*btnGoToDashboard,*/ btnInfo);
                lytExtraActions.setSpacing(false);
                lytExtraActions.setId("lytExtraActions");
                lytExtraActions.setWidthFull();

                // Add content to layout
                lytDetailsPanel.add(lblTitle, lytExtraActions,
                        pnlOptions.build(UI.getCurrent().getSession().getAttribute(Session.class).getUser()));
            } catch (InventoryException ex) {
                new SimpleNotification(ts.getTranslatedString("module.general.messages.error"),
                        ex.getMessage(), AbstractNotification.NotificationType.ERROR, ts).open();
            }
        }
    }
    //</editor-fold>
}