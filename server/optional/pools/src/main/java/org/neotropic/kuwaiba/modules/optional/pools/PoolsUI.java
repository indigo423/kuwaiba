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
package org.neotropic.kuwaiba.modules.optional.pools;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.BoxSizing;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.Command;
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
import org.neotropic.kuwaiba.core.apis.persistence.metadata.MetadataEntityManager;
import org.neotropic.kuwaiba.core.apis.persistence.util.Constants;
import org.neotropic.kuwaiba.core.i18n.TranslationService;
import org.neotropic.kuwaiba.modules.core.navigation.ObjectOptionsPanel;
import org.neotropic.kuwaiba.modules.core.navigation.actions.CopyBusinessObjectVisualAction;
import org.neotropic.kuwaiba.modules.core.navigation.actions.CopySpecialBusinessObjectVisualAction;
import org.neotropic.kuwaiba.modules.core.navigation.actions.DefaultDeleteBusinessObjectVisualAction;
import org.neotropic.kuwaiba.modules.core.navigation.actions.MoveBusinessObjectVisualAction;
import org.neotropic.kuwaiba.modules.core.navigation.actions.MoveSpecialBusinessObjectActionVisual;
import org.neotropic.kuwaiba.modules.core.navigation.actions.NewBusinessObjectFromTemplateVisualAction;
import org.neotropic.kuwaiba.modules.core.navigation.actions.NewBusinessObjectVisualAction;
import org.neotropic.kuwaiba.modules.core.navigation.actions.NewMultipleBusinessObjectsVisualAction;
import org.neotropic.kuwaiba.modules.core.navigation.navtree.grids.BusinessObjectChildrenGrid;
import org.neotropic.kuwaiba.modules.core.navigation.navtree.provider.BusinessObjectChildrenProvider;
import org.neotropic.kuwaiba.modules.optional.pools.actions.CopyBusinessObjectToPoolVisualAction;
import org.neotropic.kuwaiba.modules.optional.pools.actions.DeletePoolVisualAction;
import org.neotropic.kuwaiba.modules.optional.pools.actions.MoveBusinessObjectToPoolVisualAction;
import org.neotropic.kuwaiba.modules.optional.pools.actions.NewPoolItemVisualAction;
import org.neotropic.kuwaiba.modules.optional.pools.actions.NewPoolVisualAction;
import org.neotropic.kuwaiba.modules.optional.pools.grids.PoolGrid;
import org.neotropic.kuwaiba.modules.optional.pools.grids.PoolItemGrid;
import org.neotropic.kuwaiba.modules.optional.pools.providers.PoolItemProvider;
import org.neotropic.kuwaiba.modules.optional.pools.providers.PoolProvider;
import org.neotropic.kuwaiba.visualization.api.properties.PropertyFactory;
import org.neotropic.kuwaiba.visualization.api.properties.PropertyValueConverter;
import org.neotropic.kuwaiba.visualization.api.resources.ResourceFactory;
import org.neotropic.util.visual.button.ActionButton;
import org.neotropic.util.visual.dialog.ConfirmDialog;
import org.neotropic.util.visual.general.FormattedObjectDisplayNameSpan;
import org.neotropic.util.visual.grids.IconNameCellGrid;
import org.neotropic.util.visual.icon.ActionIcon;
import org.neotropic.util.visual.icons.ClassNameIconGenerator;
import org.neotropic.util.visual.notifications.AbstractNotification;
import org.neotropic.util.visual.notifications.SimpleNotification;
import org.neotropic.util.visual.properties.AbstractProperty;
import org.neotropic.util.visual.properties.PropertySheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.tatu.BeanTable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;
import org.neotropic.kuwaiba.modules.core.logging.LoggingService;

/**
 * Main for Pools. This class manages how the pages corresponding to different functionalities are presented in a single place.
 *
 * @author Mauricio Ruiz Beltrán {@literal <mauricio.ruiz@kuwaiba.org>}
 */
@Route(value = "pools", layout = PoolsLayout.class)
public class PoolsUI extends VerticalLayout implements ActionCompletedListener,
        HasDynamicTitle, AbstractUI, PropertySheet.IPropertyValueChangedListener {
    /**
     * Reference to the Business Entity Manager.
     */
    @Autowired
    private BusinessEntityManager bem;
    /**
     * Reference to the Metadata Entity Manager.
     */
    @Autowired
    private MetadataEntityManager mem;
    /**
     * Reference to the Application Entity Manager.
     */
    @Autowired
    private ApplicationEntityManager aem;
    /**
     * Factory to build resources from data source.
     */
    @Autowired
    private ResourceFactory resourceFactory;
    /**
     * Reference to the translation service.
     */
    @Autowired
    private TranslationService ts;
    /**
     * Reference to the action registry.
     */
    @Autowired
    private CoreActionsRegistry actionRegistry;
    /**
     * Reference to the action registry.
     */
    @Autowired
    private AdvancedActionsRegistry advancedActionsRegistry;
    /**
     * All the object-related views exposed by other modules.
     */
    @Autowired
    private ViewWidgetRegistry viewWidgetRegistry;
    /**
     * All the registered explorers.
     */
    @Autowired
    private ExplorerRegistry explorerRegistry;
    //--> Visual actions
    /**
     * The visual action to create a new pool item
     */
    @Autowired
    private NewPoolItemVisualAction newPoolItemVisualAction;
    /**
     * The visual action to delete a class
     */
    @Autowired
    private DeletePoolVisualAction deletePoolVisualAction;
    /**
     * The visual action to create a new pool
     */
    @Autowired
    private NewPoolVisualAction newPoolVisualAction;
    /**
     * Reference to the action that creates a new Business Object.
     */
    @Autowired
    private NewBusinessObjectVisualAction actNewObj;
    /**
     * Reference to the action that creates a new Business Object from a template.
     */
    @Autowired
    private NewBusinessObjectFromTemplateVisualAction actNewObjFromTemplate;
    /**
     * Reference to the action that creates a multiple new Business Object from a pattern.
     */
    @Autowired
    private NewMultipleBusinessObjectsVisualAction actNewMultipleObj;
    /**
     * Reference to the action that deletes a Business Object.
     */
    @Autowired
    private DefaultDeleteBusinessObjectVisualAction actDeleteObj;
    /**
     * Reference to the action that copies a business object to a pool.
     */
    @Autowired
    private CopyBusinessObjectToPoolVisualAction actCopyToPool;
    /**
     * Reference to the action that moves a business object to a pool.
     */
    @Autowired
    private MoveBusinessObjectToPoolVisualAction actMoveToPool;
    /**
     * Reference to the action that copies a business object to another business object.
     */
    @Autowired
    private CopyBusinessObjectVisualAction actCopyObj;
    /**
     * Reference to the action that copies a special business object to another business object.
     */
    @Autowired
    private CopySpecialBusinessObjectVisualAction actCopySpecialObj;
    /**
     * Reference to the action that moves a business object to another business object.
     */
    @Autowired
    private MoveBusinessObjectVisualAction actMoveObj;
    /**
     * Reference to the action that moves a special business object to another business object.
     */
    @Autowired
    private MoveSpecialBusinessObjectActionVisual actMoveSpecialObj;
    /**
     * Reference to the Logging Service.
     */
    @Autowired
    private LoggingService log;
    // <-- Visual actions

    /**
     * Contains the breadcrumbs.
     */
    private HorizontalLayout lytBreadCrumbs;
    /**
     * Main content.
     */
    private HorizontalLayout lytMainSides;
    /**
     * Left side content.
     */
    private VerticalLayout lytLeftSide;
    private HorizontalLayout lytPoolOptions;
    private VerticalLayout lytGridLeft;
    private PoolGrid gridLeft;
    private VerticalLayout lytPoolDetailsPanel;
    private ComboBox<InventoryObjectPool> cmbFilterPool;
    private List<InventoryObjectPool> allPools;
    private InventoryObjectPool currentPool;
    private static final int POOL_TYPE_GENERAL_PURPOSE = 1;
    /**
     * Center side content.
     */
    private VerticalLayout lytCenterSide;
    private VerticalLayout lytGridCenter;
    private PoolItemGrid gridCenter;
    private BusinessObjectLight currentParentObject;
    private VerticalLayout lytObjectDetailsPanel;
    /**
     * Right side content.
     */
    private VerticalLayout lytRightSide;
    private VerticalLayout lytGridRight;
    /**
     * Parameters.
     */
    public static String PARAM_BUSINESS_OBJECT = "businessObject"; //NOI18N
    public static String PARAM_POOL = "pool"; //NOI18N
    /**
     * An icon generator for create icons.
     */
    private ClassNameIconGenerator iconGenerator;

    @Override
    public String getPageTitle() {
        return ts.getTranslatedString("module.pools.title");
    }

    @Override
    public void initContent() {
        iconGenerator = new ClassNameIconGenerator(resourceFactory);
        setPadding(true);
        setSpacing(true);
        setSizeFull();

        setupLayouts();
        buildPoolsHeader();

        this.lytLeftSide.add(lytPoolOptions, lytGridLeft, lytPoolDetailsPanel);
        this.lytCenterSide.add(lytGridCenter, lytObjectDetailsPanel);
        this.lytRightSide.add(lytGridRight);
        this.lytMainSides.add(lytLeftSide, lytCenterSide, lytRightSide);
        add(lytBreadCrumbs, lytMainSides);
    }

    /**
     * Sets up the layout components used in the UI.
     * <p>
     * lytBreadCrumbs
     * <p>
     * lytMainSides:
     * _______________________________________________________________________________________________
     * lytLeftSide:                    | lytCenterSide:                    | lytRightSide:
     * [lytPoolOptions                 | [lytGridCenter                    | [lytGridRight]
     * lytGridLeft                    |  lytObjectDetailsPanel]           |
     * lytPoolDetailsPanel]           |                                   |
     * _______________________________________________________________________________________________
     */
    private void setupLayouts() {
        if (lytBreadCrumbs == null) {
            this.lytBreadCrumbs = new HorizontalLayout();
            this.lytBreadCrumbs.setSpacing(false);
            this.lytBreadCrumbs.setMargin(false);
            this.lytBreadCrumbs.setPadding(false);
            this.lytBreadCrumbs.setMaxWidth("100%");
            this.lytBreadCrumbs.setId("lyt-bread-crumbs");
        }
        // --> Main sides
        if (lytMainSides == null) {
            this.lytMainSides = new HorizontalLayout();
            this.lytMainSides.setSpacing(true);
            this.lytMainSides.setMargin(false);
            this.lytMainSides.setPadding(false);
            this.lytMainSides.setSizeFull();
            this.lytMainSides.setId("lyt-main-sides");
        }
        if (lytLeftSide == null) {// --> Left side
            this.lytLeftSide = new VerticalLayout();
            this.lytLeftSide.setSpacing(true);
            this.lytLeftSide.setMargin(false);
            this.lytLeftSide.setPadding(false);
            this.lytLeftSide.setWidth("30%");
            this.lytLeftSide.setHeightFull();
            this.lytLeftSide.setId("lyt-left-side");
        }
        if (lytPoolOptions == null) {
            this.lytPoolOptions = new HorizontalLayout();
            this.lytPoolOptions.setSpacing(true);
            this.lytPoolOptions.setMargin(false);
            this.lytPoolOptions.setPadding(false);
            this.lytPoolOptions.setWidthFull();
            this.lytPoolOptions.setClassName("left-action-combobox");
            this.lytPoolOptions.setId("lyt-pool-options");
        }
        if (lytGridLeft == null) {
            this.lytGridLeft = new VerticalLayout();
            this.lytGridLeft.setSpacing(false);
            this.lytGridLeft.setMargin(false);
            this.lytGridLeft.setPadding(false);
            this.lytGridLeft.setWidthFull();
            this.lytGridLeft.setHeight("auto");
            this.lytGridLeft.setId("lyt-grid-left");
        }
        if (lytPoolDetailsPanel == null) {
            lytPoolDetailsPanel = new VerticalLayout();
            this.lytPoolDetailsPanel.setSpacing(false);
            this.lytPoolDetailsPanel.setMargin(false);
            this.lytPoolDetailsPanel.setPadding(false);
            this.lytPoolDetailsPanel.setWidthFull();
            this.lytPoolDetailsPanel.setId("lyt-pool-details-panel");
        }// <-- Left side
        if (lytCenterSide == null) {// --> Center side
            this.lytCenterSide = new VerticalLayout();
            this.lytCenterSide.setSpacing(true);
            this.lytCenterSide.setMargin(false);
            this.lytCenterSide.setPadding(false);
            this.lytCenterSide.setWidth("30%");
            this.lytCenterSide.setHeightFull();
            this.lytCenterSide.setId("lyt-center-side");
        }
        if (lytGridCenter == null) {
            this.lytGridCenter = new VerticalLayout();
            this.lytGridCenter.setSpacing(false);
            this.lytGridCenter.setMargin(false);
            this.lytGridCenter.setPadding(false);
            this.lytGridCenter.setWidthFull();
            this.lytGridCenter.setHeight("auto");
            this.lytGridCenter.setId("lyt-grid-center");
        }
        if (lytObjectDetailsPanel == null) {
            lytObjectDetailsPanel = new VerticalLayout();
            this.lytObjectDetailsPanel.setSpacing(false);
            this.lytObjectDetailsPanel.setMargin(false);
            this.lytObjectDetailsPanel.setPadding(false);
            this.lytObjectDetailsPanel.setWidthFull();
            this.lytObjectDetailsPanel.setId("lyt-object-details-panel");
        }// <-- Center side
        if (lytRightSide == null) {// --> Right side
            this.lytRightSide = new VerticalLayout();
            this.lytRightSide.setSpacing(true);
            this.lytRightSide.setMargin(false);
            this.lytRightSide.setPadding(false);
            this.lytRightSide.setWidth("30%");
            this.lytRightSide.setHeightFull();
            this.lytRightSide.setId("lyt-right-side");
        }
        if (lytGridRight == null) {
            this.lytGridRight = new VerticalLayout();
            this.lytGridRight.setSpacing(false);
            this.lytGridRight.setMargin(false);
            this.lytGridRight.setPadding(false);
            this.lytGridRight.setWidthFull();
            this.lytGridRight.setHeight("auto");
            this.lytGridRight.setId("lyt-grid-right");
        }// <-- Right side
        // <-- Main sides
    }

    // <editor-fold desc="BreadCrumbs" defaultstate="collapsed">

    /**
     * Constructs and displays breadcrumbs for navigating the parent hierarchy.
     * Clears the current content of {@code lytBreadCrumbs} and then adds buttons to represent the navigation path.
     *
     * @param pool The inventory pool object for which breadcrumbs are being generated.
     *             Can be {@code null} if no pool is provided.
     */
    private void buildParentBreadCrumbs(InventoryObjectPool pool) {
        // Clear any previous content of the breadcrumbs
        clearLayouts(lytBreadCrumbs);

        // Create a container for the breadcrumb buttons
        Div divBreadcrumbs = new Div();
        divBreadcrumbs.setWidthFull();
        divBreadcrumbs.setClassName("parents-breadcrumbs");

        // Add a button to go back to the "all pools" view
        Button btnAllPools = new Button(ts.getTranslatedString("module.pools.label.all-pools"));
        btnAllPools.getElement().setProperty("title", ts.getTranslatedString("module.pools.label.all-pools"));
        btnAllPools.setSizeUndefined();
        btnAllPools.addClassName("breadcrumbs-button");
        btnAllPools.addClickListener(e -> cmbFilterPool.setValue(allPools.get(0))); // Set the pool filter value to the first pool in the list
        divBreadcrumbs.add(btnAllPools);

        // If a specific pool is provided, add a button for that pool to the breadcrumbs
        if (pool != null) {
            Button btnPool = new Button(pool.getName());
            btnPool.getElement().setProperty("title", pool.getClassName());
            btnPool.setSizeUndefined();
            btnPool.addClassName("breadcrumbs-button");
            btnPool.addClickListener(e -> cmbFilterPool.setValue(pool)); // Set the pool filter value to the provided pool
            divBreadcrumbs.add(btnPool);
        }

        // Add the breadcrumbs container to the main layout
        lytBreadCrumbs.add(divBreadcrumbs);
    }

    /**
     * Constructs and displays breadcrumbs for navigating the parent hierarchy of a selected business object.
     * Clears the current content of {@code lytBreadCrumbs} and then adds buttons to represent the navigation path.
     *
     * @param selectedObject        The selected business object for which breadcrumbs are being generated.
     *                              Can be {@code null} if no object is selected.
     * @param selectedObjectParents The list of parent business objects of the selected object.
     *                              Can be {@code null} or empty if no parents are provided.
     */
    private void buildParentBreadCrumbs(BusinessObjectLight selectedObject, List<BusinessObjectLight> selectedObjectParents) {
        // Clear any previous content of the breadcrumbs
        clearLayouts(lytBreadCrumbs);

        // Create a container for the breadcrumb buttons
        Div divBreadcrumbs = new Div();
        divBreadcrumbs.setWidthFull();
        divBreadcrumbs.setClassName("parents-breadcrumbs");

        // Add a button to go back to the "all pools" view
        Button btnAllPools = new Button(ts.getTranslatedString("module.pools.label.all-pools"));
        btnAllPools.getElement().setProperty("title", ts.getTranslatedString("module.pools.label.all-pools"));
        btnAllPools.setSizeUndefined();
        btnAllPools.addClassName("breadcrumbs-button");
        btnAllPools.addClickListener(e -> cmbFilterPool.setValue(allPools.get(0)));
        divBreadcrumbs.add(btnAllPools);

        // If selectedObjectParents list is provided and not empty, add buttons for each parent to the breadcrumbs
        if (selectedObjectParents != null && !selectedObjectParents.isEmpty()) {
            Collections.reverse(selectedObjectParents); // Reverse the list to display parents in correct order
            selectedObjectParents.forEach(parent -> addButtonBreadCrumbs(parent, divBreadcrumbs));
        }

        // If a selected object is provided, add a button for it to the breadcrumbs
        if (selectedObject != null) //The selected object is added
            addButtonBreadCrumbs(selectedObject, divBreadcrumbs);

        // Add the breadcrumbs container to the main layout
        lytBreadCrumbs.add(divBreadcrumbs);
    }

    /**
     * Adds a button representing a business object to the breadcrumbs container.
     * The button's label is either the object's name or "/" if it represents a root object.
     *
     * @param object         The business object for which the button is being added.
     * @param divBreadcrumbs The container to which the button is being added.
     */
    private void addButtonBreadCrumbs(BusinessObjectLight object, Div divBreadcrumbs) {
        // Create a button for the business object
        Button btn = new Button(object.getClassName().equals(Constants.DUMMY_ROOT) ? "/" : object.getName());
        // Set the button's tooltip to display either the object's class name or "/"
        btn.getElement().setProperty("title", object.getClassName().equals(Constants.DUMMY_ROOT) ? "/" : object.getClassName());
        btn.addClickListener(e -> { // Define button click behavior based on object type
            if (object.getClassName().contains("Pool of")) { // Handle the case of selecting a pool (NOI18N)
                try {
                    InventoryObjectPool pool = bem.getPool(object.getId());
                    cmbFilterPool.clear();
                    cmbFilterPool.setValue(pool);
                } catch (ApplicationObjectNotFoundException | InvalidArgumentException ex) {
                    new SimpleNotification(ts.getTranslatedString("module.general.messages.error"),
                            ex.getLocalizedMessage(), AbstractNotification.NotificationType.ERROR, ts).open();
                }
            } else if (!object.getClassName().equals(Constants.DUMMY_ROOT))
                changesAfterSelectingObject(currentPool, object);
        });
        btn.setSizeUndefined();
        btn.addClassName("breadcrumbs-button");
        // Add the button to the breadcrumbs container
        divBreadcrumbs.add(btn);
    }

    /**
     * Gets the parents of a given business object.
     *
     * @param selectedObject The selected object.
     * @return The parents list.
     */
    private List<BusinessObjectLight> getParents(BusinessObjectLight selectedObject) {
        try {
            return bem.getParents(selectedObject.getClassName(), selectedObject.getId());
        } catch (BusinessObjectNotFoundException | MetadataObjectNotFoundException | InvalidArgumentException e) {
            new SimpleNotification(ts.getTranslatedString("module.general.messages.error"),
                    e.getLocalizedMessage(), AbstractNotification.NotificationType.ERROR, ts).open();
            return null;
        }
    }
    // </editor-fold>

    // <editor-fold desc="Left section UI" defaultstate="collapsed">

    /**
     * Builds the header section for managing pools.
     * Clears the current content of {@code lytPoolOptions} and then adds components for pool management.
     * This includes a pool filter, a button for creating a new pool, and a label if no pools are available.
     */
    private void buildPoolsHeader() {
        // Clear any previous content of the pool header
        clearLayouts(lytPoolOptions);
        // Retrieve the root pools
        allPools = getRootPools();
        // If root pools are available, create a filter for selecting pools
        if (allPools != null && !allPools.isEmpty()) {
            // Add a placeholder option for selecting all pools
            InventoryObjectPool aPool = new InventoryObjectPool("-1",
                    ts.getTranslatedString("module.pools.label.all-pools"),
                    "", "", POOL_TYPE_GENERAL_PURPOSE);
            allPools.add(0, aPool); // Add the "all pools" option at the beginning of the list
            lytPoolOptions.add(createPoolFilter(allPools)); // Create and add the pool filter
        } else
            lytPoolOptions.add(createLabelNoPools()); // If no pools are available, display a label indicating so

        ActionButton btnNewPool = new ActionButton(new ActionIcon(VaadinIcon.PLUS_SQUARE_O), this.newPoolVisualAction.getModuleAction().getDisplayName());
        btnNewPool.addClickListener(event -> this.newPoolVisualAction.getVisualComponent(new ModuleActionParameterSet()).open());
        lytPoolOptions.setVerticalComponentAlignment(FlexComponent.Alignment.END, btnNewPool);
        btnNewPool.setHeight("32px");

        lytPoolOptions.add(btnNewPool);
    }

    /**
     * Retrieves the root pools of the inventory.
     *
     * @return A list of InventoryObjectPool, or {@code null} if an error occurs.
     */
    private List<InventoryObjectPool> getRootPools() {
        try {
            return bem.getRootPools(null, POOL_TYPE_GENERAL_PURPOSE, false);
        } catch (InvalidArgumentException ex) {
            new SimpleNotification(ts.getTranslatedString("module.general.messages.error"),
                    ex.getLocalizedMessage(), AbstractNotification.NotificationType.ERROR, ts).open();
            return null;
        }
    }

    /**
     * Creates a ComboBox for filtering pools.
     *
     * @param pools A list of InventoryObjectPool objects representing the pools to be displayed in the ComboBox.
     * @return The ComboBox for selecting pools.
     */
    private ComboBox<InventoryObjectPool> createPoolFilter(List<InventoryObjectPool> pools) {
        cmbFilterPool = new ComboBox<>(ts.getTranslatedString("module.pools.name"));
        cmbFilterPool.setPlaceholder(ts.getTranslatedString("module.pools.label.select-pool"));
        cmbFilterPool.setWidthFull();
        cmbFilterPool.setItems(pools);
        cmbFilterPool.setClearButtonVisible(true);
        cmbFilterPool.setAllowCustomValue(false);
        cmbFilterPool.setItemLabelGenerator(InventoryObjectPool::getName);

        cmbFilterPool.addValueChangeListener(event -> {
            if (event.getValue() != null) // Perform actions after selecting a pool
                changesAfterSelectingPool(event.getValue().getId().equals("-1") ? null : event.getValue());
            else // Clear layouts if no pool is selected
                clearLayouts(lytBreadCrumbs, lytGridLeft, lytPoolDetailsPanel, lytGridCenter, lytObjectDetailsPanel, lytGridRight);
        });

        // Set the initial value of the ComboBox to the first pool in the list
        cmbFilterPool.setValue(pools.get(0));

        return cmbFilterPool;
    }

    /**
     * Creates a Label indicating that no pools are available.
     *
     * @return The Label indicating no pools are available.
     */
    private Label createLabelNoPools() {
        Label lblPoolInfo = new Label(ts.getTranslatedString("module.pools.label.no-pools"));
        lblPoolInfo.setClassName("dialog-title");
        lblPoolInfo.setWidthFull();

        return lblPoolInfo;
    }

    /**
     * Builds the grid for displaying pools.
     * Clears the current content of {@code lytGridLeft} and then constructs and populates the grid with pool data.
     *
     * @param pool The selected inventory object pool. If {@code null}, a grid with root pools will be built.
     */
    private void buildPoolsGrid(InventoryObjectPool pool) {
        // Clear any previous content of the grid layout
        clearLayouts(lytGridLeft);

        // Create a new PoolGrid instance with a specified page size
        gridLeft = new PoolGrid(25);
        gridLeft.addComponentColumn(null, aPool -> { // Add a column for displaying pool names and icons
            Icon icon = new Icon(pool == null ? VaadinIcon.FOLDER : VaadinIcon.FOLDER_OPEN);
            icon.setSize("16px");

            Label lblTitle = new Label(aPool.getName());
            lblTitle.setClassName("wrap-item-label");
            lblTitle.setId("lbl-pool-name");
            lblTitle.setWidthFull();
            lblTitle.getElement().getStyle().set("cursor", "pointer");

            HorizontalLayout lytPool = new HorizontalLayout(icon, lblTitle);
            lytPool.setBoxSizing(BoxSizing.BORDER_BOX);
            lytPool.setSpacing(true);
            lytPool.setMargin(false);
            lytPool.setPadding(false);
            lytPool.setWidthFull();
            lytPool.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);

            lytPool.addClickListener(c -> {
                cmbFilterPool.clear();
                cmbFilterPool.setValue(aPool);
            });
            return lytPool;
        });
        gridLeft.addComponentColumn(null, this::createPoolActions) // Add a column for pool actions
                .setWidth("0px").setAlignment(BeanTable.ColumnAlignment.RIGHT);
        gridLeft.setI18n(buildI18nForGrid(null)); // Set the internationalization for the grid
        // Build the data provider for the grid based on the provided pool
        gridLeft.buildDataProvider(new PoolProvider(bem, ts), pool, pool != null);
        gridLeft.setHeight("auto");
        gridLeft.setWidthFull();
        gridLeft.setId("grid-left");

        if (gridLeft.getRowCount() <= 25) //when page is not necessary
            gridLeft.addClassName("bean-table-hide-footer");

        gridLeft.addClassName("bean-table-hide-header");

        // Add the grid to the left layout container
        lytGridLeft.add(gridLeft);
    }

    /**
     * Creates actions (buttons) for managing a specific pool.
     *
     * @param pool The InventoryObjectPool for which actions are being created.
     * @return A horizontal layout containing action buttons for managing the pool.
     */
    private Component createPoolActions(InventoryObjectPool pool) {
        Command addPoolItem = () -> {
        };
        // Create a button for adding a new pool item
        ActionButton btnNewPoolItem = new ActionButton(new ActionIcon(VaadinIcon.PLUS_SQUARE_O)
                , this.newPoolItemVisualAction.getModuleAction().getDisplayName());
        btnNewPoolItem.addClickListener(event -> {
            this.newPoolItemVisualAction.getVisualComponent(new ModuleActionParameterSet(
                    new ModuleActionParameter<>("pool", pool),
                    new ModuleActionParameter<>("poolItem", addPoolItem))
            ).open();
        });

        // Create a button for deleting the pool
        ActionButton btnDeletePool = new ActionButton(new ActionIcon(VaadinIcon.TRASH)
                , this.deletePoolVisualAction.getModuleAction().getDisplayName());
        btnDeletePool.addClickListener(event -> {
            this.deletePoolVisualAction.getVisualComponent(new ModuleActionParameterSet(
                    new ModuleActionParameter<>("pool", pool))).open();
        });

        HorizontalLayout lytActions = new HorizontalLayout(btnNewPoolItem, btnDeletePool);
        lytActions.setHeight("22px");
        lytActions.setJustifyContentMode(JustifyContentMode.END);
        lytActions.setDefaultVerticalComponentAlignment(Alignment.CENTER);
        lytActions.setPadding(false);
        lytActions.setMargin(false);
        lytActions.setSpacing(false);

        return lytActions;
    }

    ;

    /**
     * Builds the panel displaying details of the selected inventory object pool.
     * Clears the current content of {@code lytPoolDetailsPanel} and then populates it with property sheet data.
     *
     * @param selectedPool The pool for which details are being displayed.
     */
    private void buildPoolDetailsPanel(InventoryObjectPool selectedPool) {
        // Clear any previous content of the pool details panel
        clearLayouts(lytPoolDetailsPanel);

        try {
            if (selectedPool != null) {
                PropertySheet propertySheetPool = new PropertySheet(ts, new ArrayList<>());
                propertySheetPool.addPropertyValueChangedListener(this);
                InventoryObjectPool aWholeCustomerPool = bem.getPool(selectedPool.getId());
                propertySheetPool.setItems(PropertyFactory.propertiesFromPoolWithoutClassName(aWholeCustomerPool, ts));

                // Add the property sheet to the pool details panel layout
                lytPoolDetailsPanel.add(propertySheetPool);
            }
        } catch (ApplicationObjectNotFoundException | InvalidArgumentException ex) {
            new SimpleNotification(ts.getTranslatedString("module.general.messages.error"),
                    ex.getLocalizedMessage(), AbstractNotification.NotificationType.ERROR, ts).open();
        }
    }

    /**
     * Performs necessary updates after selecting a new inventory object pool.
     * Updates the parent breadcrumbs, pool grid, pool details panel, and center grid.
     * Clears the layout for object details and the right grid.
     *
     * @param pool The selected pool.
     */
    private void changesAfterSelectingPool(InventoryObjectPool pool) {
        buildParentBreadCrumbs(pool);
        buildPoolsGrid(pool);
        buildPoolDetailsPanel(pool);
        buildCenterGrid(pool, null, false);
        currentPool = pool;
        clearLayouts(lytObjectDetailsPanel, lytGridRight);
    }
    // </editor-fold>

    // <editor-fold desc="Center section UI" defaultstate="collapsed">
    /**
     * Builds the grid for displaying pool items related to a specific inventory object pool.
     * Clears the current content of {@code lytGridCenter} and then constructs and populates the grid with pool item data.
     *
     * @param pool          The pool for which pool items are being displayed.
     * @param rootObject    The root object, if any.
     * @param includedSelf  Flag indicating whether to include the root object.
     */
    private void buildCenterGrid(InventoryObjectPool pool, BusinessObjectLight rootObject, boolean includedSelf) {
        // Clear any previous content of the center grid layout
        clearLayouts(lytGridCenter);

        if (pool != null) { // Only build the grid if a pool is selected
            gridCenter = new PoolItemGrid(25);
            buildObjectName(gridCenter);
            gridCenter.setI18n(buildI18nForGrid(null));
            gridCenter.buildDataProvider(new PoolItemProvider(bem, ts), pool, rootObject, includedSelf);
            gridCenter.setHeight("auto");
            gridCenter.setWidthFull();
            gridCenter.setId("grid-center");

            if (gridCenter.getRowCount() <= 25) //when page is not necessary
                gridCenter.addClassName("bean-table-hide-footer");

            gridCenter.addClassName("bean-table-hide-header");
            // Add the grid to the center grid layout
            lytGridCenter.add(gridCenter);
        }
    }

    /**
     * Builds the panel displaying details of the selected business object.
     * Clears the current content of {@code lytObjectDetailsPanel} and then populates it with object options panel data.
     *
     * @param grid           The grid instance associated with the selected object.
     * @param selectedObject The selected object for which details are being displayed.
     */
    private void buildObjectDetailsPanel(PoolItemGrid grid, BusinessObjectLight selectedObject) {
        try {
            // Clear any previous content of the object details panel
            clearLayouts(lytObjectDetailsPanel);

            // Create an ObjectOptionsPanel instance for the selected object
            ObjectOptionsPanel pnlOptions = new ObjectOptionsPanel(selectedObject,
                    actionRegistry, advancedActionsRegistry, viewWidgetRegistry, explorerRegistry, mem, aem, bem, ts, log);
            pnlOptions.setShowViews(false);
            pnlOptions.setShowExplorers(true);
            // Define selection listener for handling actions and explorer selections
            pnlOptions.setSelectionListener((event) -> {
                switch (event.getActionCommand()) {
                    case ObjectOptionsPanel.EVENT_ACTION_SELECTION:
                        ModuleActionParameterSet parameters = new ModuleActionParameterSet(
                                new ModuleActionParameter<>("businessObject", selectedObject));
                        Dialog wdwObjectAction = (Dialog) ((AbstractVisualInventoryAction) event.getSource())
                                .getVisualComponent(parameters);
                        wdwObjectAction.open();
                        break;
                    case ObjectOptionsPanel.EVENT_EXPLORER_SELECTION:
                        ConfirmDialog wdwExplorer = new ConfirmDialog(ts);
                        wdwExplorer.getBtnConfirm().addClickListener(ev -> wdwExplorer.close());
                        wdwExplorer.getBtnCancel().setVisible(false);
                        wdwExplorer.setHeader(String.format(ts.getTranslatedString(
                                        ((AbstractExplorer<?>) event.getSource()).getHeader()),
                                selectedObject.toString()));
                        wdwExplorer.setContent(((AbstractExplorer<?>) event.getSource())
                                .build((BusinessObjectLight) selectedObject));
                        wdwExplorer.setHeight("90%");
                        wdwExplorer.setMinWidth("70%");
                        wdwExplorer.open();
                        break;
                }
            });
            // Define property listener for handling property changes
            pnlOptions.setPropertyListener((property) -> {
                Session session = UI.getCurrent().getSession().getAttribute(Session.class);
                Object lastValue = pnlOptions.lastValue(property.getName());
                HashMap<String, String> attributes = new HashMap<>();
                attributes.put(property.getName(), PropertyValueConverter.getAsStringToPersist(property));
                try {
                    // Update the object's attributes in the inventory
                    bem.updateObject(selectedObject.getClassName(), selectedObject.getId(), attributes);
                    if (property.getName().equals(Constants.PROPERTY_NAME)) {
                        selectedObject.setName(property.getAsString());
                        grid.getDataProvider().refreshItem(selectedObject);
                    }
                    // Log the activity for updating the object's properties
                    aem.createObjectActivityLogEntry(session.getUser().getUserName(), selectedObject.getClassName(),
                            selectedObject.getId(), ActivityLogEntry.ACTIVITY_TYPE_UPDATE_INVENTORY_OBJECT,
                            property.getName(), lastValue == null ? "" : lastValue.toString(), property.getAsString(), "");
                } catch (InventoryException ex) {
                    new SimpleNotification(ts.getTranslatedString("module.general.messages.error"),
                            ex.getLocalizedMessage(), AbstractNotification.NotificationType.ERROR, ts).open();
                    pnlOptions.UndoLastEdit();
                }
                new SimpleNotification(ts.getTranslatedString("module.general.messages.success"),
                        ts.getTranslatedString("module.general.messages.property-updated-successfully"),
                        AbstractNotification.NotificationType.INFO, ts).open();
            });

            lytObjectDetailsPanel.add(pnlOptions.build(UI.getCurrent().getSession().getAttribute(Session.class).getUser()));
        } catch (InventoryException ex) {
            new SimpleNotification(ts.getTranslatedString("module.general.messages.error"),
                    ex.getLocalizedMessage(), AbstractNotification.NotificationType.ERROR, ts).open();
        }
    }

    /**
     * Performs necessary updates after selecting a new business object.
     * Updates the parent breadcrumbs, center grid, object details panel, and right grid.
     * Sets the current parent object to the selected object.
     *
     * @param pool           The pool associated with the selected object.
     * @param selectedObject The object that has been selected.
     */
    private void changesAfterSelectingObject(InventoryObjectPool pool, BusinessObjectLight selectedObject) {
        buildParentBreadCrumbs(selectedObject, getParents(selectedObject));
        buildCenterGrid(pool, selectedObject, true);
        buildObjectDetailsPanel(gridCenter, selectedObject);
        buildRightGrid(selectedObject);
        currentParentObject = selectedObject;
    }
    // </editor-fold>

    // <editor-fold desc="Right section UI" defaultstate="collapsed">

    /**
     * Builds the grid for displaying children of a specific business object.
     * Clears the current content of {@code lytGridRight} and then constructs and populates the grid with children data.
     *
     * @param rootObject The root object for which children are being displayed.
     */
    private void buildRightGrid(BusinessObjectLight rootObject) {
        // Clear any previous content of the right grid layout
        clearLayouts(lytGridRight);

        // Create a new BusinessObjectChildrenGrid instance with a specified page size
        BusinessObjectChildrenGrid gridRight = new BusinessObjectChildrenGrid(25);
        buildObjectName(gridRight);
        gridRight.setI18n(buildI18nForGrid(rootObject));
        gridRight.buildDataProvider(new BusinessObjectChildrenProvider(bem, ts), rootObject, false);
        gridRight.setHeight("auto");
        gridRight.setWidthFull();
        gridRight.setId("grid-right");

        if (gridRight.getRowCount() <= 25) //when page is not necessary
            gridRight.addClassName("bean-table-hide-footer");

        gridRight.addClassName("bean-table-hide-header");

        // Add the grid to the right grid layout
        lytGridRight.add(gridRight);
    }
    // </editor-fold>

    /**
     * Builds the column for displaying object names in the given BeanTable.
     *
     * @param grid The BeanTable instance for which the object name column is being built.
     */
    private void buildObjectName(BeanTable<?> grid) {
        // Add a component column to the grid for displaying object names
        grid.addComponentColumn(null, object -> {
            if (object instanceof BusinessObjectLight) {
                BusinessObjectLight businessObject = (BusinessObjectLight) object;

                FormattedObjectDisplayNameSpan spnItemName = new FormattedObjectDisplayNameSpan(
                        businessObject,
                        false,
                        false,
                        true,
                        false
                );

                IconNameCellGrid iconNameCellGrid = new IconNameCellGrid(spnItemName, businessObject.getClassName(), iconGenerator);
                iconNameCellGrid.setWidthFull();

                HorizontalLayout lytItem = new HorizontalLayout(iconNameCellGrid);
                lytItem.setBoxSizing(BoxSizing.BORDER_BOX);
                lytItem.setSpacing(true);
                lytItem.setMargin(false);
                lytItem.setPadding(false);
                lytItem.setWidthFull();
                lytItem.setHeight("25px");
                lytItem.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
                lytItem.addClickListener(c -> changesAfterSelectingObject(currentPool, businessObject));
                return lytItem;
            }
            return null;
        });
    }

    /**
     * Builds the internationalization framework for the table.
     *
     * @param parentObject The parent object if exists.
     * @return The BeanTable I18n.
     */
    private BeanTable.BeanTableI18n buildI18nForGrid(BusinessObjectLight parentObject) {
        BeanTable.BeanTableI18n i18n = new BeanTable.BeanTableI18n();
        i18n.setNoDataText(parentObject == null ? ts.getTranslatedString("module.general.labels.no-data") :
                String.format(ts.getTranslatedString("module.navigation.ui.message-info.no-children")
                        , parentObject.getName()));
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

    /**
     * Removes the content from the provided components.
     *
     * @param components The provided components.
     */
    private void clearLayouts(HasComponents... components) {
        Stream.of(components).forEach(HasComponents::removeAll);
    }

    /**
     * Handles the completion of an action.
     * This method is invoked when an action is completed, and it updates UI components accordingly
     * based on the status and response of the completed action.
     *
     * @param ev The ActionCompletedEvent representing the completed action event.
     */
    @Override
    public void actionCompleted(ActionCompletedEvent ev) {
        // Check if the action completed successfully
        if (ev.getStatus() == ActionCompletedListener.ActionCompletedEvent.STATUS_SUCCESS) {
            // Check if the action response contains information about affected pools
            if (ev.getActionResponse() != null && ev.getActionResponse().containsKey(PARAM_POOL)
                    && ev.getActionResponse().get(PARAM_POOL) != null) {
                InventoryObjectPool affectedPool = (InventoryObjectPool) ev.getActionResponse().get(PARAM_POOL);
                if (affectedPool != null) {
                    if (gridLeft.containsPool(affectedPool)) { // Check if the affected pool is displayed in the left grid
                        if (ev.getActionResponse().containsKey(ActionResponse.ActionType.ADD)
                                && affectedPool.equals(currentPool)) { // If the action response indicates adding objects to the current pool
                            buildCenterGrid(currentPool, null, false);
                        } else if (ev.getActionResponse().containsKey(ActionResponse.ActionType.REMOVE)) { // If the action response indicates removing pool
                            if (affectedPool.equals(currentPool)) // If the affected pool is the current pool, rebuild the pools header
                                buildPoolsHeader();
                            else { // If the affected pool is not the current pool, remove it from the pool list
                                allPools.remove(affectedPool);
                                cmbFilterPool.setItems(allPools);
                                buildPoolsGrid(null);
                            }
                        }
                    }
                }
            } else if (ev.getActionResponse() != null && ev.getActionResponse().containsKey(PARAM_BUSINESS_OBJECT)
                    && ev.getActionResponse().get(PARAM_BUSINESS_OBJECT) != null) { // Check if the action response contains information about affected business objects
                BusinessObjectLight affectedObject = (BusinessObjectLight) ev.getActionResponse().get(PARAM_BUSINESS_OBJECT);
                // The affected object is evaluated
                if (affectedObject != null && currentParentObject != null) {
                    // If the affected object is part of the current parent object and displayed in the center grid
                    if (gridCenter.containsObject(affectedObject) && affectedObject.equals(currentParentObject)) {
                        if (ev.getActionResponse().containsKey(ActionResponse.ActionType.ADD)) { // If the action response indicates adding objects
                            buildRightGrid(currentParentObject);
                        } else if (ev.getActionResponse().containsKey(ActionResponse.ActionType.REMOVE)
                                || ev.getActionResponse().containsKey(ActionResponse.ActionType.MOVE)) { // If the action response indicates removing or moving objects
                            clearLayouts(lytBreadCrumbs, lytGridCenter, lytObjectDetailsPanel, lytGridRight);
                            buildParentBreadCrumbs(currentPool);
                            buildCenterGrid(currentPool, null, false);
                        }
                    }
                }
            } else if (currentPool == null) { // If no current pool is selected, rebuild the pools header
                buildPoolsHeader();
            }
            new SimpleNotification(ts.getTranslatedString("module.general.messages.success"),
                    ev.getMessage(), AbstractNotification.NotificationType.INFO, ts).open();
        } else {
            new SimpleNotification(ts.getTranslatedString("module.general.messages.error"),
                    ev.getMessage(), AbstractNotification.NotificationType.ERROR, ts).open();
        }
    }

    @Override
    public void onAttach(AttachEvent ev) {
        this.newPoolVisualAction.registerActionCompletedLister(this);
        this.deletePoolVisualAction.registerActionCompletedLister(this);
        this.newPoolItemVisualAction.registerActionCompletedLister(this);
        this.actNewMultipleObj.registerActionCompletedLister(this);
        this.actNewObjFromTemplate.registerActionCompletedLister(this);
        this.actNewObj.registerActionCompletedLister(this);
        this.actDeleteObj.registerActionCompletedLister(this);
        this.actCopyToPool.registerActionCompletedLister(this);
        this.actMoveToPool.registerActionCompletedLister(this);
        this.actCopyObj.registerActionCompletedLister(this);
        this.actCopySpecialObj.registerActionCompletedLister(this);
        this.actMoveObj.registerActionCompletedLister(this);
        this.actMoveSpecialObj.registerActionCompletedLister(this);
    }

    @Override
    public void onDetach(DetachEvent ev) {
        this.newPoolVisualAction.unregisterListener(this);
        this.deletePoolVisualAction.unregisterListener(this);
        this.newPoolItemVisualAction.unregisterListener(this);
        this.actNewMultipleObj.unregisterListener(this);
        this.actNewObjFromTemplate.unregisterListener(this);
        this.actNewObj.unregisterListener(this);
        this.actDeleteObj.unregisterListener(this);
        this.actCopyToPool.unregisterListener(this);
        this.actMoveToPool.unregisterListener(this);
        this.actCopyObj.unregisterListener(this);
        this.actCopySpecialObj.unregisterListener(this);
        this.actMoveObj.unregisterListener(this);
        this.actMoveSpecialObj.unregisterListener(this);
    }

    /**
     * Updates the property of the current pool based on the provided property information.
     * This method is invoked when a property of the current pool is modified, and it updates the corresponding
     * property value in the backend, refreshes the UI to reflect the changes, and displays a success notification.
     *
     * @param property The AbstractProperty representing the property that has been modified.
     */
    @Override
    public void updatePropertyChanged(AbstractProperty<?> property) {
        // Check if a current pool is selected
        if (currentPool != null) {
            if (property.getName().equals(Constants.PROPERTY_NAME)) {
                aem.setPoolProperties(currentPool.getId(), String.valueOf(property.getValue()), currentPool.getDescription());
                currentPool.setName(String.valueOf(property.getValue()));
                gridLeft.getDataProvider().refreshItem(currentPool);
            } else if (property.getDescription().equals(Constants.PROPERTY_DESCRIPTION)) {
                aem.setPoolProperties(currentPool.getId(), currentPool.getName(), String.valueOf(property.getValue()));
                currentPool.setDescription(String.valueOf(property.getValue()));
            }
            new SimpleNotification(ts.getTranslatedString("module.general.messages.success"),
                    ts.getTranslatedString("module.general.messages.property-updated-successfully"),
                    AbstractNotification.NotificationType.INFO, ts).open();
        }
    }
}