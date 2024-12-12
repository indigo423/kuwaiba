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
package org.neotropic.kuwaiba.modules.core.navigation;

import com.neotropic.flow.component.paper.dialog.PaperDialog;
import com.vaadin.componentfactory.theme.EnhancedDialogVariant;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.ShortcutEventListener;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
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
import org.neotropic.kuwaiba.core.apis.integration.modules.views.AbstractObjectRelatedViewWidget;
import org.neotropic.kuwaiba.core.apis.integration.modules.views.ViewWidgetRegistry;
import org.neotropic.kuwaiba.core.apis.persistence.application.ActivityLogEntry;
import org.neotropic.kuwaiba.core.apis.persistence.application.ApplicationEntityManager;
import org.neotropic.kuwaiba.core.apis.persistence.application.FilterDefinition;
import org.neotropic.kuwaiba.core.apis.persistence.application.Session;
import org.neotropic.kuwaiba.core.apis.persistence.business.BusinessEntityManager;
import org.neotropic.kuwaiba.core.apis.persistence.business.BusinessObjectLight;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.BusinessObjectNotFoundException;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.InvalidArgumentException;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.InventoryException;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.MetadataObjectNotFoundException;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.OperationNotPermittedException;
import org.neotropic.kuwaiba.core.apis.persistence.metadata.MetadataEntityManager;
import org.neotropic.kuwaiba.core.apis.persistence.util.Constants;
import org.neotropic.kuwaiba.core.i18n.TranslationService;
import org.neotropic.kuwaiba.modules.core.navigation.actions.CopyBusinessObjectVisualAction;
import org.neotropic.kuwaiba.modules.core.navigation.actions.CopySpecialBusinessObjectVisualAction;
import org.neotropic.kuwaiba.modules.core.navigation.actions.DefaultDeleteBusinessObjectVisualAction;
import org.neotropic.kuwaiba.modules.core.navigation.actions.ManageAttachmentsVisualAction;
import org.neotropic.kuwaiba.modules.core.navigation.actions.ManageSpecialRelationshipsVisualAction;
import org.neotropic.kuwaiba.modules.core.navigation.actions.MoveBusinessObjectVisualAction;
import org.neotropic.kuwaiba.modules.core.navigation.actions.MoveSpecialBusinessObjectActionVisual;
import org.neotropic.kuwaiba.modules.core.navigation.actions.NewBusinessObjectFromTemplateVisualAction;
import org.neotropic.kuwaiba.modules.core.navigation.actions.NewBusinessObjectVisualAction;
import org.neotropic.kuwaiba.modules.core.navigation.actions.NewMultipleBusinessObjectsVisualAction;
import org.neotropic.kuwaiba.modules.core.navigation.actions.NewMultipleSpecialBusinessObjectsVisualAction;
import org.neotropic.kuwaiba.modules.core.navigation.actions.NewSpecialBusinessObjectVisualAction;
import org.neotropic.kuwaiba.modules.core.navigation.actions.ReleaseFromVisualAction;
import org.neotropic.kuwaiba.modules.core.navigation.actions.ShowMoreInformationAction;
import org.neotropic.kuwaiba.modules.core.navigation.explorers.DialogNavigationSearch;
import org.neotropic.kuwaiba.modules.core.navigation.navtree.grids.BusinessObjectChildrenGrid;
import org.neotropic.kuwaiba.modules.core.navigation.navtree.grids.BusinessObjectResultGrid;
import org.neotropic.kuwaiba.modules.core.navigation.navtree.provider.BusinessObjectChildrenProvider;
import org.neotropic.kuwaiba.modules.core.navigation.navtree.provider.FilteredBusinessObjectChildrenProvider;
import org.neotropic.kuwaiba.visualization.api.properties.PropertyValueConverter;
import org.neotropic.kuwaiba.visualization.api.resources.ResourceFactory;
import org.neotropic.util.visual.button.ActionButton;
import org.neotropic.util.visual.dialog.ConfirmDialog;
import org.neotropic.util.visual.general.FormattedObjectDisplayNameSpan;
import org.neotropic.util.visual.grids.IconNameCellGrid;
import org.neotropic.util.visual.icons.ClassNameIconGenerator;
import org.neotropic.util.visual.notifications.AbstractNotification;
import org.neotropic.util.visual.notifications.SimpleNotification;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.tatu.BeanTable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.neotropic.kuwaiba.modules.core.logging.LoggingService;

/**
 * Main entry point to the navigation module.
 * @author Mauricio Ruiz Beltrán {@literal <mauricio.ruiz@kuwaiba.org>}
 */
@Route(value = "navman", layout = NavigationLayout.class)
public class NavigationUI extends VerticalLayout implements
        ActionCompletedListener, HasDynamicTitle, AbstractUI {
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
     * All the object-related views exposed by other modules.
     */
    @Autowired
    private ViewWidgetRegistry viewWidgetRegistry;
    /**
     * All the registered explorers.
     */
    @Autowired
    private ExplorerRegistry explorerRegistry;
    /**
     * The window to show more information about an object.
     */
    @Autowired
    private ShowMoreInformationAction windowMoreInformation;
    /**
     * Reference to the action that creates a new Business Object.
     */
    @Autowired
    private NewBusinessObjectVisualAction actNewObj;
    /**
     * Reference to the action that creates a new Special Business Object.
     */
    @Autowired
    private NewSpecialBusinessObjectVisualAction actNewSpecialObj;
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
     * Reference to the action that creates a multiple new Business Object from a pattern.
     */
    @Autowired
    private NewMultipleSpecialBusinessObjectsVisualAction actNewMultipleSpecialObj;
    /**
     * Reference to the action that deletes a Business Object.
     */
    @Autowired
    private DefaultDeleteBusinessObjectVisualAction actDeleteObj;
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
     * Reference to the action that manage Attachments for an object.
     */
    @Autowired
    private ManageAttachmentsVisualAction actManAttachmentsObj;
    /**
     * Reference to the action that release a business object from other business object.
     */
    @Autowired
    private ReleaseFromVisualAction actReleaseFrom;
    /**
     * Reference to the Logging Service.
     */
    @Autowired
    private LoggingService log;
    /**
     * Contains the search dialog.
     */
    private HorizontalLayout lytSearch;
    /**
     * Contains the root object actions.
     */
    private HorizontalLayout lytRoot;
    /**
     * Will show the breadcrumbs.
     */
    private Div divBreadcrumbs;
    /**
     * Contains the breadcrumbs.
     */
    private HorizontalLayout lytBreadCrumbs;
    /**
     * Main content
     */
    private HorizontalLayout lytMainSides;
    // --> Left section
    /**
     * Left side content
     */
    private VerticalLayout lytLeftSide;
    /**
     * An icon generator for create icons
     */
    private ClassNameIconGenerator iconGenerator;
    /**
     * The component to show the results of a search
     */
    private DialogNavigationSearch searchDialog;
    /**
     * Contains the grid that will be displayed in the left section.
     */
    private VerticalLayout lytGridLeft;
    /**
     * The grid that will be displayed in the left section.
     */
    private BusinessObjectChildrenGrid gridLeft;
    /**
     * Contains the object options panel.
     */
    private VerticalLayout lytDetailsPanel;
    /**
     * Button to go to object dashboard.
     */
    private ActionButton btnGoToDashboard;
    /**
     * Button to show more information about a given business object.
     */
    private ActionButton btnInfo;
    /**
     * Contains the extra actions for the selected business object.
     */
    private HorizontalLayout lytExtraActions;
    /**
     * Saves the currently selected business object.
     */
    private BusinessObjectLight currentParentObject;
    private BusinessObjectLight firstParentOfCurrentObject;
    // <-- Left section
    // --> Center side
    /**
     * Contains business object filters.
     */
    private HorizontalLayout lytFilters;
    /**
     * Scroller for the grid center.
     */
    private Scroller scrollerGridCenter;
    /**
     * Contains the grid that will be displayed in the center section.
     */
    private VerticalLayout lytGridCenter;
    /**
     * The grid that will be displayed in the center section.
     */
    private BusinessObjectChildrenGrid gridCenter;
    /**
     * Center side content.
     */
    private VerticalLayout lytCenterSide;
    /**
     * Saves the currently selected filter.
     */
    private FilterDefinition currentFilterDefinition;
    // <-- Center side
    /**
     * Parameters
     */
    public static String PARAM_BUSINESS_OBJECT = "businessObject"; //NOI18N
    public static String PARAM_PARENT = "parent"; //NOI18N
    /**
     * Contains the action menu for the business objects.
     */
    private VerticalLayout lytMenuActionsRoot;
    private VerticalLayout lytMenuActionsSearch;
    private VerticalLayout lytMenuActionsLeft;
    private VerticalLayout lytMenuActionsCenter;
    /**
     * The evaluation of the shortcut.
     */
    private boolean shortcutActive;
    /**
     * Dialog to update the business object name.
     */
    private ConfirmDialog dlgRename;

    @Override
    public String getPageTitle() {
        return ts.getTranslatedString("module.navigation.title");
    }

    @Override
    public void initContent() {
        iconGenerator = new ClassNameIconGenerator(resourceFactory);
        setPadding(true);
        setSpacing(true);
        setSizeFull();

        setupLayouts();
        buildSearchDialog();
        buildExploreFromRoot();
        // left section
        buildLeftExtraActions();
        initShortcuts();
        // add content to left side
        this.lytLeftSide.add(lytGridLeft, lytExtraActions, lytDetailsPanel, lytMenuActionsSearch, lytMenuActionsLeft);

        // center section
        buildGridCenterScroller();
        this.lytCenterSide.add(lytFilters, scrollerGridCenter, lytMenuActionsCenter);

        // add content to sides
        this.lytMainSides.add(lytLeftSide, lytCenterSide);

        // add content to main layout
        add(lytSearch, lytRoot, lytBreadCrumbs, lytMainSides);
    }

    /**
     * setUp the layouts
     *                                            lytSearch
     *                                 lytRoot: [lytMenuActionsRoot]
     * lytBreadCrumbs
     * <p>
     * lytMainSides:
     * _______________________________________________________________________________________________
     * lytLeftSide:                        | lytCenterSide:                          | lytRightSide:
     * [lytGridLeft: [lytMenuActionsLeft]  | [lytFilters                             | @todo
     * lytExtraActions                     | lytGridCenter: [lytMenuActionsCenter]   |
     * lytDetailsPanel]                    | ]                                       |
     * _______________________________________________________________________________________________
     */
    private void setupLayouts() {
        if (lytSearch == null) {
            this.lytSearch = new HorizontalLayout();
            this.lytSearch.setSpacing(false);
            this.lytSearch.setMargin(false);
            this.lytSearch.setPadding(false);
            this.lytSearch.setWidthFull();
            this.lytSearch.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
            this.lytSearch.setId("lyt-search");
        }
        if (lytRoot == null) {// --> Root
            this.lytRoot = new HorizontalLayout();
            this.lytRoot.setMargin(false);
            this.lytRoot.setPadding(false);
            this.lytRoot.setSpacing(true);
            this.lytRoot.setWidthFull();
            this.lytRoot.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
            this.lytRoot.setId("lyt-root");
        }
        if (lytMenuActionsRoot == null) {
            this.lytMenuActionsRoot = new VerticalLayout();
            this.lytMenuActionsRoot.setPadding(false);
            this.lytMenuActionsRoot.setWidth("0px");
            this.lytMenuActionsRoot.setId("lyt-menu-actions-root");
        }// <-- Root
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
        if (lytMenuActionsSearch == null) { // When the options are for objects resulting from the search
            this.lytMenuActionsSearch = new VerticalLayout();
            this.lytMenuActionsSearch.setSpacing(false);
            this.lytMenuActionsSearch.setMargin(false);
            this.lytMenuActionsSearch.setPadding(false);
            this.lytMenuActionsSearch.setWidthFull();
            this.lytMenuActionsSearch.setId("lyt-menu-actions-search");
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
        if (lytMenuActionsLeft == null) {
            this.lytMenuActionsLeft = new VerticalLayout();
            this.lytMenuActionsLeft.setSpacing(false);
            this.lytMenuActionsLeft.setMargin(false);
            this.lytMenuActionsLeft.setPadding(false);
            this.lytMenuActionsLeft.setWidthFull();
            this.lytMenuActionsLeft.setId("lyt-menu-actions-left");
        }
        if (lytExtraActions == null) {
            this.lytExtraActions = new HorizontalLayout();
            this.lytExtraActions.setSpacing(true);
            this.lytExtraActions.setMargin(false);
            this.lytExtraActions.setPadding(false);
            this.lytExtraActions.setMaxHeight("60px");
            this.lytExtraActions.setWidthFull();
            this.lytExtraActions.setId("lyt-extra-actions");
        }
        if (lytDetailsPanel == null) {
            lytDetailsPanel = new VerticalLayout();
            this.lytDetailsPanel.setSpacing(false);
            this.lytDetailsPanel.setMargin(false);
            this.lytDetailsPanel.setPadding(false);
            this.lytDetailsPanel.setWidthFull();
            this.lytDetailsPanel.setId("lyt-details-panel");
        }// <-- Left side
        if (lytCenterSide == null) {// --> Center side
            this.lytCenterSide = new VerticalLayout();
            this.lytCenterSide.setSpacing(true);
            this.lytCenterSide.setMargin(false);
            this.lytCenterSide.setPadding(false);
            this.lytCenterSide.setWidth("40%");
            this.lytCenterSide.setHeightFull();
            this.lytCenterSide.setMaxHeight("100%");
            this.lytCenterSide.setId("lyt-center-side");
        }
        if (lytFilters == null) {
            this.lytFilters = new HorizontalLayout();
            this.lytFilters.setSpacing(false);
            this.lytFilters.setMargin(false);
            this.lytFilters.setPadding(false);
            this.lytFilters.setWidthFull();
            this.lytFilters.setId("lyt-filters");
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
        if (lytMenuActionsCenter == null) {
            this.lytMenuActionsCenter = new VerticalLayout();
            this.lytMenuActionsCenter.setSpacing(false);
            this.lytMenuActionsCenter.setMargin(false);
            this.lytMenuActionsCenter.setPadding(false);
            this.lytMenuActionsCenter.setWidthFull();
            this.lytMenuActionsCenter.setId("lyt-menu-actions-center");
        }// <-- Center side
        // <-- Main sides
    }

    /**
     * Builds the search dialog that allows to find objects by name, class and id.
     * If an object is selected from the results list, its options panel is built, otherwise the search text is processed.
     */
    private void buildSearchDialog() {
        searchDialog = new DialogNavigationSearch(ts, bem, iconGenerator, e -> {
            if (e != null) {
                if (e instanceof String) // No suggestion was chosen
                    processSearch((String) e);
                else if (e instanceof BusinessObjectLight) { // A single element was selected
                    clearLayouts(lytDetailsPanel);
                    buildGridLeft((BusinessObjectLight) e, true);
                    changesAfterSelectingObject((BusinessObjectLight) e);
                }
            }
            searchDialog.close();
        });

        lytSearch.add(searchDialog);
    }

    /**
     * After a search the searched text is process to create a result of
     * business objects grouped by class name in grids.
     *
     * @param searchedText The searched text.
     */
    private void processSearch(String searchedText) {
        try {
            HashMap<String, List<BusinessObjectLight>> searchResults = new HashMap<>();
            if (!searchedText.isEmpty()) {
                searchResults = bem.getSuggestedObjectsWithFilterGroupedByClassName(
                        null,
                        searchedText,
                        0,
                        DialogNavigationSearch.MAX_CLASSES_SEARCH_LIMIT,
                        0,
                        DialogNavigationSearch.MAX_OBJECTS_SEARCH_LIMIT
                );
            } else {
                new SimpleNotification(ts.getTranslatedString("module.general.messages.warning"),
                        ts.getTranslatedString("module.navigation.ui.message-warning.search-criteria-empty"),
                        AbstractNotification.NotificationType.WARNING, ts).open();
            }

            if (!searchResults.isEmpty()) {
                clearLayouts(lytGridLeft, lytDetailsPanel, lytBreadCrumbs, lytFilters, lytGridCenter);
                disableComponents(btnGoToDashboard, btnInfo);
                lytGridLeft.add(buildAccordionOfGrids(searchResults, searchedText));
            }
        } catch (InvalidArgumentException ex) {
            new SimpleNotification(ts.getTranslatedString("module.general.messages.error"),
                    ex.getLocalizedMessage(), AbstractNotification.NotificationType.ERROR, ts).open();
        }
    }

    /**
     * Creates/updates the localization path, that shows the whole list
     * of the parents  of the selected object.
     *
     * @param selectedObject        The selected object
     * @param selectedObjectParents The selected object parents.
     */
    private void buildParentBreadCrumbs(BusinessObjectLight selectedObject, List<BusinessObjectLight> selectedObjectParents) {
        clearLayouts(lytBreadCrumbs);

        divBreadcrumbs = new Div();
        divBreadcrumbs.setWidthFull();
        divBreadcrumbs.setClassName("parents-breadcrumbs");

        if (selectedObjectParents != null && !selectedObjectParents.isEmpty()) {
            Collections.reverse(selectedObjectParents);
            firstParentOfCurrentObject = selectedObjectParents.get(selectedObjectParents.size() - 1);
            selectedObjectParents.forEach(this::addButtonBreadCrumbs);
        }

        if (selectedObject != null) //The selected object is added
            addButtonBreadCrumbs(selectedObject);

        lytBreadCrumbs.add(divBreadcrumbs);
    }

    /**
     * Builds interactive breadcrumbs.
     *
     * @param object Object associated with the breadcrumb.
     */
    private void addButtonBreadCrumbs(BusinessObjectLight object) {
        Button btn = new Button(object.getClassName().equals(Constants.DUMMY_ROOT) ? "/" : object.getName());
        btn.getElement().setProperty("title", object.getClassName().equals(Constants.DUMMY_ROOT) ? "/" : object.getClassName());
        btn.addClickListener(e -> {
            if (!object.getClassName().equals(Constants.DUMMY_ROOT))
                changesAfterSelectingObject(object);
            else {
                currentParentObject = object;
                buildGridLeft(object, false);
                clearLayouts(lytDetailsPanel, lytBreadCrumbs, lytFilters, lytGridCenter);
                disableComponents(btnGoToDashboard, btnInfo);
            }
        });
        btn.setSizeUndefined();
        btn.addClassName("breadcrumbs-button");
        divBreadcrumbs.add(btn);
    }

    /**
     * Builds a button and options menu to interact with the root start navigation from the root
     * by clicking on the button to show the root's children objects
     */
    private void buildExploreFromRoot() {
        BusinessObjectLight rootObject = new BusinessObjectLight(Constants.DUMMY_ROOT, "-1", Constants.DUMMY_ROOT);

        ActionButton btnExploreFromDummyRoot = new ActionButton(
                ts.getTranslatedString("module.navigation.actions.explore-from-root"),
                ts.getTranslatedString("module.navigation.actions.explore-from-root")
        );
        btnExploreFromDummyRoot.setHeight("30px");
        btnExploreFromDummyRoot.setWidth("auto");

        btnExploreFromDummyRoot.addClickListener(e -> changesAfterSelectingRoot(rootObject));

        ActionButton btnActions = createObjectActions(rootObject, lytMenuActionsRoot, true, false);
        btnActions.setHeight("30px");
        btnActions.setWidth("auto");

        this.lytRoot.add(btnExploreFromDummyRoot, btnActions, lytMenuActionsRoot);
    }

    // --> Left section

    /**
     * Groups the search results, if it includes more than two different classes
     * it creates an accordion with a grid for every set of classes, otherwise it will show a single grid
     *
     * @param searchResults Objects list based on the search string.
     * @param searchedText  The searched text.
     * @return An accordion with a grid for every set of classes.
     */
    private Component buildAccordionOfGrids(HashMap<String, List<BusinessObjectLight>> searchResults, String searchedText) {
        Accordion accordion = new Accordion();
        accordion.setWidth("100%");

        clearLayouts(lytMenuActionsSearch);
        for (Map.Entry<String, List<BusinessObjectLight>> entry : searchResults.entrySet()) {
            String className = entry.getKey();
            if (!className.equals(Constants.DUMMY_ROOT)) {
                BusinessObjectResultGrid resultGrid = new BusinessObjectResultGrid(20);
                resultGrid.addComponentColumn(null, object -> {
                    FormattedObjectDisplayNameSpan spnItemName = new FormattedObjectDisplayNameSpan(
                            object,
                            false,
                            false,
                            true,
                            false
                    );

                    IconNameCellGrid cell = new IconNameCellGrid(
                            spnItemName,
                            object.getClassName(),
                            iconGenerator
                    );
                    cell.addClickListener(c -> changesAfterSelectingObject(object));
                    cell.setWidth("99%");
                    return cell;
                });
                resultGrid.addComponentColumn(null, object -> createObjectActions(object, lytMenuActionsSearch, false, false))
                        .setWidth("0px")
                        .setAlignment(BeanTable.ColumnAlignment.RIGHT);
                resultGrid.setI18n(buildI18nForGrid(null));
                resultGrid.buildDataProvider(bem, ts, className, searchedText, entry.getValue());
                resultGrid.setWidthFull();

                VerticalLayout lytGrid = new VerticalLayout();
                lytGrid.setSpacing(false);
                lytGrid.setPadding(false);
                lytGrid.setMargin(false);
                lytGrid.setWidthFull();
                lytGrid.setHeight("auto");
                if (resultGrid.getRowCount() > 1) {
                    TextField textField = new TextField();
                    textField.setPlaceholder(ts.getTranslatedString("module.navigation.actions.filter-results"));
                    textField.setValueChangeMode(ValueChangeMode.EAGER);
                    textField.addValueChangeListener(c -> resultGrid.setFilter(c.getValue()));
                    textField.setSizeFull();
                    lytGrid.add(textField);
                }
                lytGrid.add(resultGrid);

                if (resultGrid.getRowCount() <= 20) //when page is not necessary
                    resultGrid.addClassName("bean-table-hide-footer");

                resultGrid.addClassName("bean-table-hide-header");

                if (searchResults.size() == 1) //case for one element selected from search
                    return lytGrid;

                accordion.add(className, lytGrid);
            }
        }
        return accordion;
    }

    /**
     * Builds the grid from a parent object, it can contain the children of the root,
     * or the currently selected object. This grid uses pagination.
     * @param parentObject The parent object.
     * @param includedSelf Is the parent object included in the results?
     */
    private void buildGridLeft(BusinessObjectLight parentObject, boolean includedSelf) {
        clearLayouts(lytGridLeft, lytMenuActionsLeft);

        gridLeft = new BusinessObjectChildrenGrid(10);
        addComponentColumnToGrid(gridLeft, lytMenuActionsLeft, false);
        gridLeft.setI18n(buildI18nForGrid(parentObject));
        gridLeft.buildDataProvider(
                new BusinessObjectChildrenProvider(bem, ts),
                parentObject,
                includedSelf
        );
        gridLeft.setHeight("auto");
        gridLeft.setWidthFull();
        gridLeft.setId("grid-left");

        if (gridLeft.getRowCount() <= 10) //when page is not necessary
            gridLeft.addClassName("bean-table-hide-footer");

        gridLeft.addClassName("bean-table-hide-header");

        lytGridLeft.add(gridLeft);
    }

    /**
     * Extra actions that allow to open the object dashboard and show more information.
     */
    private void buildLeftExtraActions() {
        btnGoToDashboard = new ActionButton(
                ts.getTranslatedString("module.navigation.widgets.object-dashboard.open-to-dashboard"),
                ts.getTranslatedString("module.navigation.widgets.object-dashboard.open-to-dashboard")
        );
        btnGoToDashboard.setVisible(false);
        btnGoToDashboard.setWidth("50%");
        btnGoToDashboard.setHeight("30px");

        btnGoToDashboard.addClickListener(e -> {
            if (currentParentObject != null && !currentParentObject.getClassName().equals(Constants.DUMMY_ROOT)) {
                getUI().ifPresent(ui -> {
                    ui.getSession().setAttribute(BusinessObjectLight.class, currentParentObject);
                    ui.getPage().open(RouteConfiguration.forRegistry(VaadinService.getCurrent()
                            .getRouter().getRegistry()).getUrl(ObjectDashboard.class), "_blank");
                });
            }
        });

        btnInfo = new ActionButton(
                ts.getTranslatedString("module.navigation.actions.show-more-information-button-name"),
                ts.getTranslatedString("module.navigation.actions.show-more-information-button-name")
        );
        btnInfo.setVisible(false);
        btnInfo.setWidthFull();
        btnInfo.setHeight("30px");

        btnInfo.addClickListener(e -> {
            if (currentParentObject != null) {
                this.windowMoreInformation.getVisualComponent(new ModuleActionParameterSet(
                        new ModuleActionParameter<>("object", currentParentObject))).open();
            }
        });

        lytExtraActions.add(/*btnGoToDashboard,*/ btnInfo);
    }

    /**
     * After selecting an object, an option panel is built that will allow you to manage the current object.
     *
     * @param selectedObject The selected object.
     */
    private void buildDetailsPanel(BusinessObjectLight selectedObject) {
        try {
            if (!selectedObject.getClassName().equals(Constants.DUMMY_ROOT)) {
                ObjectOptionsPanel pnlOptions = new ObjectOptionsPanel(selectedObject, actionRegistry,
                        advancedActionsRegistry, viewWidgetRegistry, explorerRegistry, mem, aem, bem, ts, log);
                pnlOptions.setShowViews(true);
                pnlOptions.setShowExplorers(true);
                pnlOptions.setSelectionListener(e -> {
                    try {
                        switch (e.getActionCommand()) {
                            case ObjectOptionsPanel.EVENT_ACTION_SELECTION:
                                ModuleActionParameterSet parameters = new ModuleActionParameterSet(
                                        new ModuleActionParameter<>("businessObject", selectedObject));
                                Dialog wdwObjectAction = (Dialog) ((AbstractVisualInventoryAction) e.getSource())
                                        .getVisualComponent(parameters);
                                wdwObjectAction.open();
                                break;
                            case ObjectOptionsPanel.EVENT_EXPLORER_SELECTION:
                                ConfirmDialog wdwExplorer = new ConfirmDialog(ts);
                                wdwExplorer.getBtnConfirm().addClickListener(ev -> {
                                    wdwExplorer.close();
                                    shortcutActive = true;
                                    ((AbstractExplorer<?>) e.getSource()).clearResources();
                                });
                                wdwExplorer.addDialogCloseActionListener(ev -> {
                                    wdwExplorer.close();
                                    shortcutActive = true;
                                    ((AbstractExplorer<?>) e.getSource()).clearResources();
                                });
                                wdwExplorer.getBtnCancel().setVisible(false);
                                wdwExplorer.setHeader(String.format(ts.getTranslatedString(
                                                ((AbstractExplorer<?>) e.getSource()).getHeader()),
                                        selectedObject.toString()));
                                wdwExplorer.setContent(((AbstractExplorer<?>) e.getSource()).build(selectedObject));
                                wdwExplorer.setHeight("90%");
                                wdwExplorer.setMinWidth("70%");
                                wdwExplorer.open();
                                shortcutActive = false;
                                break;
                            case ObjectOptionsPanel.EVENT_VIEW_SELECTION:
                                ConfirmDialog wdwView = new ConfirmDialog(ts);
                                wdwView.setModal(false);
                                wdwView.addThemeVariants(EnhancedDialogVariant.SIZE_LARGE);
                                wdwView.setWidth("90%");
                                wdwView.setHeight("90%");
                                wdwView.setContentSizeFull();
                                wdwView.getBtnConfirm().addClickListener(ev -> wdwView.close());
                                wdwView.setHeader(ts.getTranslatedString(String.format(((AbstractObjectRelatedViewWidget<?>)
                                        e.getSource()).getTitle(), selectedObject.getName())));
                                wdwView.setContent(((AbstractObjectRelatedViewWidget<?>) e.getSource()).build(selectedObject));
                                wdwView.getBtnCancel().setVisible(false);
                                wdwView.open();
                                break;
                        }
                    } catch (InventoryException ex) {
                        new SimpleNotification(ts.getTranslatedString("module.general.messages.error"),
                                ex.getLocalizedMessage(), AbstractNotification.NotificationType.ERROR, ts).open();
                    }
                });

                pnlOptions.setPropertyListener((property) -> {
                    HashMap<String, String> attributes = new HashMap<>();
                    Session session = UI.getCurrent().getSession().getAttribute(Session.class);
                    Object lastValue =  pnlOptions.lastValue(property.getName());
                    attributes.put(property.getName(), PropertyValueConverter.getAsStringToPersist(property));
                    try {
                        bem.updateObject(selectedObject.getClassName(), selectedObject.getId(), attributes);
                        if(property.getName().equals(Constants.PROPERTY_NAME) && gridLeft.containsObject(selectedObject)) {
                            selectedObject.setName(property.getAsString());
                            gridLeft.getDataProvider().refreshItem(selectedObject);
                            buildParentBreadCrumbs(selectedObject, getParents(selectedObject));
                        }
                        new SimpleNotification(ts.getTranslatedString("module.general.messages.success"),
                                ts.getTranslatedString("module.general.messages.property-updated-successfully"),
                                AbstractNotification.NotificationType.INFO, ts).open();
                        // activity log
                        aem.createObjectActivityLogEntry(session.getUser().getUserName(), selectedObject.getClassName(),
                                selectedObject.getId(), ActivityLogEntry.ACTIVITY_TYPE_UPDATE_INVENTORY_OBJECT,
                                property.getName(), lastValue == null ? "" : lastValue.toString(), property.getAsString(), "");
                    } catch (InventoryException ex) {
                        new SimpleNotification(ts.getTranslatedString("module.general.messages.error"),
                                ex.getLocalizedMessage(), AbstractNotification.NotificationType.ERROR, ts).open();
                        pnlOptions.UndoLastEdit();
                    }
                });

                clearLayouts(lytDetailsPanel);
                // Add content to layout
                lytDetailsPanel.add(pnlOptions.build(UI.getCurrent().getSession().getAttribute(Session.class).getUser()));
            }
        } catch (InventoryException ex) {
            new SimpleNotification(ts.getTranslatedString("module.general.messages.error"),
                    ex.getLocalizedMessage(), AbstractNotification.NotificationType.ERROR, ts).open();
        }
    }
    // <-- Left section

    // --> Center section
    /**
     * Builds the filters definition for a selected parent object.
     *
     * @param parentObject The parent object.
     */
    private void buildFilters(BusinessObjectLight parentObject) {
        clearLayouts(lytFilters);

        ComboBox<FilterDefinition> cmbFilters = new ComboBox<>();
        cmbFilters.setWidthFull();

        List<FilterDefinition> filters = getDataForFilters(parentObject);
        cmbFilters.setItems(filters);
        cmbFilters.setValue(filters.isEmpty() ? null : filters.get(0));

        cmbFilters.setAllowCustomValue(false);
        cmbFilters.setPlaceholder(ts.getTranslatedString("module.configman.filter.placeholder-select-a-filter"));
        cmbFilters.setRenderer(new ComponentRenderer<>(item -> {
            Label lblNodeName = new Label(item.getName());
            Icon icn = new Icon(VaadinIcon.FILTER);
            icn.setSize("12px");
            HorizontalLayout lytNode = new HorizontalLayout(icn, lblNodeName);
            lytNode.setMargin(false);
            lytNode.setPadding(false);
            lytNode.setDefaultVerticalComponentAlignment(Alignment.END);
            lytNode.setVerticalComponentAlignment(Alignment.CENTER, icn);
            lytNode.getElement().setProperty("title", item.getDescription());
            return lytNode;
        }));

        cmbFilters.addValueChangeListener(e -> {
            if (e.getValue().getScript() == null || e.getValue().getId() == -1
                    || (e.getValue().getScript() != null && e.getValue().getScript().isEmpty()))
                buildGridCenter(parentObject, null);
            else if (e.getValue().getFilter() != null)
                buildGridCenter(parentObject, e.getValue());

        });
        lytFilters.add(cmbFilters);
    }

    /**
     * Gets the filters definition for a selected parent object.
     *
     * @param parentObject The parent object.
     */
    private List<FilterDefinition> getDataForFilters(BusinessObjectLight parentObject) {
        List<FilterDefinition> filters = new ArrayList<>();
        FilterDefinition noFilter = new FilterDefinition(-1, ts.getTranslatedString(
                "module.configman.filter.no-filter"), true);
        filters.add(noFilter);

        try {
            HashMap<String, Object> filterAttributes = new HashMap<>();
            filterAttributes.put(Constants.PROPERTY_ENABLED, true);
            List<FilterDefinition> filterDefinitionsForClass = aem.getFilterDefinitionsForClass(
                    parentObject.getClassName(), true, true,
                    filterAttributes, -1, -1);
            if (filterDefinitionsForClass != null)
                filterDefinitionsForClass.stream().filter(f -> (f.getFilter() != null))
                        .forEachOrdered(filters::add);
        } catch (InvalidArgumentException ex) {
            new SimpleNotification(ts.getTranslatedString("module.general.messages.error"),
                    ex.getLocalizedMessage(), AbstractNotification.NotificationType.ERROR, ts).open();
        }

        return filters;
    }

    /**
     * Builds scroller for the grid that shows the children of a business object.
     */
    private void buildGridCenterScroller() {
        scrollerGridCenter = new Scroller();
        scrollerGridCenter.setWidthFull();
        scrollerGridCenter.setMaxHeight("100%");
        scrollerGridCenter.setId("scroller-grid-center");
        scrollerGridCenter.setScrollDirection(Scroller.ScrollDirection.VERTICAL);
        scrollerGridCenter.setContent(lytGridCenter);
    }

    /**
     * Builds the grid from a parent object, it can contain the filter definition results,
     * or the children of the currently selected object. This grid uses pagination.
     *
     * @param parentObject     The parent object.
     * @param filterDefinition The filter definition.
     */
    private void buildGridCenter(BusinessObjectLight parentObject, FilterDefinition filterDefinition) {
        clearLayouts(lytGridCenter, lytMenuActionsCenter);

        currentFilterDefinition = filterDefinition;

        gridCenter = new BusinessObjectChildrenGrid(20);
        addComponentColumnToGrid(gridCenter, lytMenuActionsCenter, true);
        gridCenter.setI18n(buildI18nForGrid(parentObject));
        gridCenter.buildDataProvider(
                filterDefinition == null ? new BusinessObjectChildrenProvider(bem, ts)
                        : new FilteredBusinessObjectChildrenProvider(ts, filterDefinition),
                parentObject,
                false
        );
        gridCenter.setHeight("auto");
        gridCenter.setWidthFull();
        gridCenter.setId("grid-center");

        if (gridCenter.getRowCount() <= 20) //when page is not necessary
            gridCenter.addClassName("bean-table-hide-footer");

        gridCenter.addClassName("bean-table-hide-header");

        lytGridCenter.add(gridCenter);
    }
    // <-- Center section

    /**
     * Creates the menu action for a given businessObjectLight.
     *
     * @param businessObject The object to apply the actions.
     * @param layoutMenu     Action menu layout.
     * @return The menu bar with actions.
     */
    private ActionButton createObjectActions(BusinessObjectLight businessObject, VerticalLayout layoutMenu,
                                             boolean isRoot, boolean isChildrenLayout) {
        PaperDialog paperDialog = new PaperDialog();
        paperDialog.getStyle().set("border-top-left-radius", "5px");
        paperDialog.getStyle().set("border-top-right-radius", "5px");
        paperDialog.getStyle().set("margin-top", isRoot ? "30px" : "0px");
        paperDialog.setWidth("200px");
        paperDialog.setId("menu-" + businessObject.getId());
        paperDialog.setNoOverlap(true);
        paperDialog.setMargin(false);

        FlexLayout lytMenuContent = new FlexLayout();
        lytMenuContent.setSizeFull();
        lytMenuContent.setFlexDirection(FlexLayout.FlexDirection.COLUMN);
        lytMenuContent.getStyle().set("margin", "0px"); //NOI18N
        lytMenuContent.getStyle().set("padding", "0px"); //NOI18N

        actionRegistry.getActionsForModule(NavigationModule.MODULE_ID).forEach(anAction ->
        {
            if (anAction != null) {
                HorizontalLayout lytMenuElement = new HorizontalLayout();
                lytMenuElement.setClassName("sub-menu-element");

                if (businessObject.getClassName().equals(Constants.DUMMY_ROOT)) {
                    if (!(anAction instanceof DefaultDeleteBusinessObjectVisualAction) &&
                            !(anAction instanceof CopyBusinessObjectVisualAction) &&
                            !(anAction instanceof CopySpecialBusinessObjectVisualAction) &&
                            !(anAction instanceof MoveBusinessObjectVisualAction) &&
                            !(anAction instanceof MoveSpecialBusinessObjectActionVisual) &&
                            !(anAction instanceof ManageSpecialRelationshipsVisualAction) &&
                            !(anAction instanceof ManageAttachmentsVisualAction)) {
                        lytMenuElement.add(new Label(anAction.getModuleAction().getDisplayName()));
                        lytMenuContent.add(lytMenuElement);
                        lytMenuElement.addClickListener(e -> {
                            ((Dialog) anAction.getVisualComponent(new ModuleActionParameterSet(
                                    new ModuleActionParameter<>(NewBusinessObjectVisualAction.PARAM_BUSINESS_OBJECT
                                            , businessObject)))).open();
                            paperDialog.close();
                        });
                    }
                } else {
                    lytMenuElement.add(new Label(anAction.getModuleAction().getDisplayName()));
                    lytMenuContent.add(lytMenuElement);
                    lytMenuElement.addClickListener(e -> {
                        ((Dialog) anAction.getVisualComponent(new ModuleActionParameterSet(
                                new ModuleActionParameter<>(NewBusinessObjectVisualAction.PARAM_BUSINESS_OBJECT
                                        , businessObject)))).open();
                        paperDialog.close();
                    });
                }
            }
        });
        paperDialog.add(lytMenuContent);
        layoutMenu.add(paperDialog);

        ActionButton btnMenu = isRoot ? new ActionButton(ts.getTranslatedString("module.navigation.actions.root-menu"),
                ts.getTranslatedString("module.navigation.actions.root-menu"))
                : new ActionButton(new Icon(VaadinIcon.ELLIPSIS_DOTS_H));

        if (isChildrenLayout)
            btnMenu.getStyle().set("margin-right", "10px");

        btnMenu.addClickListener(e -> paperDialog.open("menu-" + businessObject.getId(), btnMenu, false));
        return btnMenu;
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
     * Disables the provided components.
     *
     * @param components The provided components.
     */
    private void disableComponents(Component... components) {
        Stream.of(components).forEach(component -> component.setVisible(false));
    }

    /**
     * After selecting the root object, it updates: the current parent, the left grid, details panel,
     * breadcrumbs, filters and the center grid.
     *
     * @param selectedObject The selected object.
     */
    private void changesAfterSelectingRoot(BusinessObjectLight selectedObject) {
        currentParentObject = selectedObject;
        buildGridLeft(selectedObject, false);
        clearLayouts(lytDetailsPanel, lytBreadCrumbs, lytFilters, lytGridCenter);
        disableComponents(btnGoToDashboard, btnInfo);
    }

    /**
     * After selecting an object, it updates: the current parent, the left grid, details panel,
     * breadcrumbs, filters and the center grid.
     *
     * @param selectedObject The selected object.
     */
    private void changesAfterSelectingObject(BusinessObjectLight selectedObject) {
        currentParentObject = selectedObject;
        buildGridLeft(selectedObject, true);
        buildDetailsPanel(selectedObject);
        btnGoToDashboard.setVisible(true);
        btnInfo.setVisible(true);
        buildParentBreadCrumbs(selectedObject, getParents(selectedObject));
        buildFilters(selectedObject);
        buildGridCenter(selectedObject, null);
    }

    /**
     * Gets the parents of a given business object.
     *
     * @param selectedObject The selected object.
     * @return The parents list.
     */
    private List<BusinessObjectLight> getParents(BusinessObjectLight selectedObject) {
        List<BusinessObjectLight> parents = new ArrayList<>();
        try {
            List<BusinessObjectLight> allParents = bem.getParents(selectedObject.getClassName(), selectedObject.getId());
            allParents.forEach(parent -> {
                try {
                    if (parent.getClassName().equals(Constants.DUMMY_ROOT) ||
                            mem.isSubclassOf(Constants.CLASS_INVENTORYOBJECT, parent.getClassName()))
                        parents.add(parent);
                } catch (MetadataObjectNotFoundException ignored) { }
            });
        } catch (BusinessObjectNotFoundException | MetadataObjectNotFoundException | InvalidArgumentException e) {
            new SimpleNotification(ts.getTranslatedString("module.general.messages.error"),
                    e.getLocalizedMessage(), AbstractNotification.NotificationType.ERROR, ts).open();
        }
        return parents;
    }

    /**
     * Builds the main columns of a grid. The first column contains details of the object,
     * the second contains the actions available for the object.
     *
     * @param grid       The current grid.
     * @param layoutMenu The current layout for menu actions.
     */
    private void addComponentColumnToGrid(BusinessObjectChildrenGrid grid, VerticalLayout layoutMenu, boolean isChildrenLayout) {
        grid.addComponentColumn(null, object -> {
            FormattedObjectDisplayNameSpan spnItemName = new FormattedObjectDisplayNameSpan(
                    object,
                    false,
                    false,
                    true,
                    false
            );

            IconNameCellGrid cell = new IconNameCellGrid(
                    spnItemName,
                    object.getClassName(),
                    iconGenerator
            );
            cell.addClickListener(c -> changesAfterSelectingObject(object));
            cell.setWidth("99%");
            return cell;
        });

        grid.addComponentColumn(null, object -> createObjectActions(object, layoutMenu, false, isChildrenLayout))
                .setWidth("0px")
                .setAlignment(BeanTable.ColumnAlignment.RIGHT);
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

    // --> Actions
    @Override
    public void onAttach(AttachEvent ev) {
        // To prevent registering the events twice on page reloads.
        this.actNewObjFromTemplate.unregisterListener(this);
        this.actNewMultipleObj.unregisterListener(this);
        this.actNewMultipleSpecialObj.unregisterListener(this);
        this.actNewObj.unregisterListener(this);
        this.actNewSpecialObj.unregisterListener(this);
        this.actDeleteObj.unregisterListener(this);
        this.actCopyObj.unregisterListener(this);
        this.actMoveObj.unregisterListener(this);
        this.actManAttachmentsObj.unregisterListener(this);
        this.actCopySpecialObj.unregisterListener(this);
        this.actMoveSpecialObj.unregisterListener(this);
        this.actReleaseFrom.unregisterListener(this);
        //Register action listeners.
        this.actNewObjFromTemplate.registerActionCompletedLister(this);
        this.actNewMultipleObj.registerActionCompletedLister(this);
        this.actNewMultipleSpecialObj.registerActionCompletedLister(this);
        this.actNewObj.registerActionCompletedLister(this);
        this.actNewSpecialObj.registerActionCompletedLister(this);
        this.actDeleteObj.registerActionCompletedLister(this);
        this.actCopyObj.registerActionCompletedLister(this);
        this.actMoveObj.registerActionCompletedLister(this);
        this.actManAttachmentsObj.registerActionCompletedLister(this);
        this.actCopySpecialObj.registerActionCompletedLister(this);
        this.actMoveSpecialObj.registerActionCompletedLister(this);
        this.actReleaseFrom.registerActionCompletedLister(this);
    }

    @Override
    public void onDetach(DetachEvent ev) {
        this.actNewObjFromTemplate.unregisterListener(this);
        this.actNewMultipleObj.unregisterListener(this);
        this.actNewMultipleSpecialObj.unregisterListener(this);
        this.actNewObj.unregisterListener(this);
        this.actNewSpecialObj.unregisterListener(this);
        this.actDeleteObj.unregisterListener(this);
        this.actCopyObj.unregisterListener(this);
        this.actMoveObj.unregisterListener(this);
        this.actManAttachmentsObj.unregisterListener(this);
        this.actReleaseFrom.unregisterListener(this);
    }

    @Override
    public void actionCompleted(ActionCompletedEvent ev) {
        if (ev.getStatus() == ActionCompletedEvent.STATUS_SUCCESS) {
            if (ev.getActionResponse() != null && ev.getActionResponse().containsKey(PARAM_BUSINESS_OBJECT)
                    && ev.getActionResponse().get(PARAM_BUSINESS_OBJECT) != null) {
                BusinessObjectLight affectedObject = (BusinessObjectLight) ev.getActionResponse().get(PARAM_BUSINESS_OBJECT);

                // The affected node is evaluated
                if (affectedObject != null && currentParentObject != null) {
                    if (ev.getActionResponse().containsKey(ActionResponse.ActionType.ADD)) {
                        //It's root? When is root the grid in the left panel is updated, otherwise the grid in the center panel is updated
                        if (affectedObject.getClassName().equals(Constants.DUMMY_ROOT) && affectedObject.equals(currentParentObject))
                            buildGridLeft(affectedObject, false);
                        else if (gridLeft.containsObject(affectedObject) && affectedObject.equals(currentParentObject))
                            buildGridCenter(affectedObject, currentFilterDefinition);
                    } else if (ev.getActionResponse().containsKey(ActionResponse.ActionType.REMOVE)) {
                        // The root is not validated here because it should not have the possibility of being removed
                        if (gridLeft.containsObject(affectedObject) && affectedObject.equals(currentParentObject)) {
                            if (firstParentOfCurrentObject != null) { // Check if the deleted object has a parent
                                // If the parent is the root
                                if (firstParentOfCurrentObject.getClassName().equals(Constants.DUMMY_ROOT))
                                    changesAfterSelectingRoot(firstParentOfCurrentObject);
                                else // otherwise
                                    changesAfterSelectingObject(firstParentOfCurrentObject);
                            } else { // Headless object
                                // The panels are cleared
                                clearLayouts(lytGridLeft, lytMenuActionsLeft, lytDetailsPanel,
                                        lytBreadCrumbs, lytFilters, lytGridCenter);
                                disableComponents(btnGoToDashboard, btnInfo);
                            }
                        } else if (gridCenter.containsObject(affectedObject) &&
                                ev.getActionResponse().containsKey(PARAM_PARENT) &&
                                ev.getActionResponse().get(PARAM_PARENT) != null &&
                                currentParentObject.equals((BusinessObjectLight) ev.getActionResponse().get(PARAM_PARENT))) {
                            // If the removed object belongs to the central grid, the center panel is updated
                            buildGridCenter(currentParentObject, currentFilterDefinition);
                        }
                    } else if (ev.getActionResponse().containsKey(ActionResponse.ActionType.COPY) &&
                            ev.getActionResponse().containsKey(Constants.PROPERTY_PARENT_ID) &&
                            ev.getActionResponse().get(Constants.PROPERTY_PARENT_ID) != null &&
                            currentParentObject.getId().equals((String) ev.getActionResponse().get(Constants.PROPERTY_PARENT_ID))) {
                        // If the new object is a child of the current parent then the central grid is updated
                        buildGridCenter(currentParentObject, currentFilterDefinition);
                    } else if (ev.getActionResponse().containsKey(ActionResponse.ActionType.MOVE)) {
                        // If a child was moved, the central grid is updated
                        buildGridCenter(currentParentObject, currentFilterDefinition);
                    }
                }
            }
            new SimpleNotification(ts.getTranslatedString("module.general.messages.success"),
                    ev.getMessage(), AbstractNotification.NotificationType.INFO, ts).open();
        } else
            new SimpleNotification(ts.getTranslatedString("module.general.messages.error"),
                    ev.getMessage(), AbstractNotification.NotificationType.ERROR, ts).open();
    }
    // <-- Actions

    /**
     * Initializes the shortcuts handler, Handles rename F2 shortcut.
     */
    private void initShortcuts() {
        shortcutActive = true;
        ShortcutEventListener listener = event -> {
            if (shortcutActive && event.matches(Key.F2)) {
                if (gridLeft != null && currentParentObject != null && gridLeft.containsObject(currentParentObject))
                    shortcutNameEditor(currentParentObject);
            }
        };
        UI.getCurrent().addShortcutListener(listener, Key.F2);
    }

    /**
     * Creates a dialog to edit the business object name.
     *
     * @param selectedObject The selected object.
     */
    private void shortcutNameEditor(BusinessObjectLight selectedObject) {
        if (selectedObject != null) {
            TextField txtEditValue = new TextField(ts.getTranslatedString("module.general.labels.new-name"));
            txtEditValue.setWidthFull();
            txtEditValue.setValue(selectedObject.getName());

            Command cmdRenameObject = () -> {
                try {
                    HashMap<String, String> attributes = new HashMap<>();
                    attributes.put(Constants.PROPERTY_NAME, txtEditValue.getValue());
                    bem.updateObject(selectedObject.getClassName(), selectedObject.getId(), attributes);
                    selectedObject.setName(txtEditValue.getValue());
                    gridLeft.getDataProvider().refreshItem(selectedObject);
                    buildParentBreadCrumbs(selectedObject, getParents(selectedObject));
                    txtEditValue.clear();
                    buildDetailsPanel(selectedObject);
                    dlgRename = null;
                } catch (MetadataObjectNotFoundException | BusinessObjectNotFoundException
                         | OperationNotPermittedException | InvalidArgumentException ex) {
                    new SimpleNotification(ts.getTranslatedString("module.general.messages.error"),
                            ex.getLocalizedMessage(), AbstractNotification.NotificationType.ERROR, ts).open();
                }
            };

            if (dlgRename == null) {
                dlgRename = new ConfirmDialog(ts, txtEditValue, cmdRenameObject);
                dlgRename.setHeader(ts.getTranslatedString("module.general.labels.rename"));
                dlgRename.setThemeVariants(EnhancedDialogVariant.SIZE_SMALL);
                dlgRename.setWidth(Constants.DEFAULT_SMALL_DIALOG_WIDTH);
                dlgRename.getBtnConfirm().setEnabled(false);

                txtEditValue.setValueChangeMode(ValueChangeMode.EAGER);
                txtEditValue.addValueChangeListener(e -> {
                    if (dlgRename != null)
                        dlgRename.getBtnConfirm().setEnabled(!e.getValue().equals(selectedObject.getName()));
                });

                dlgRename.getBtnCancel().addClickListener(e -> {
                    if (dlgRename != null) {
                        dlgRename.close();
                        dlgRename = null;
                    }
                    txtEditValue.clear();
                });

                dlgRename.addDialogCloseActionListener(e -> {
                    if (dlgRename != null) {
                        dlgRename.close();
                        dlgRename = null;
                    }
                    txtEditValue.clear();
                });

                dlgRename.open();
                txtEditValue.focus();
            }
        }
    }
}