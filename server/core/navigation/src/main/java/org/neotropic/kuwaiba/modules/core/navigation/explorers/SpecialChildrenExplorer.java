/*
 *  Copyright 2010-2024 Neotropic SAS <contact@neotropic.co>.
 *
 *  Licensed under the EPL License, Version 1.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.eclipse.org/legal/epl-v10.html
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.neotropic.kuwaiba.modules.core.navigation.explorers;

import com.vaadin.componentfactory.theme.EnhancedDialogVariant;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.ShortcutEventListener;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.treegrid.TreeGrid;
import com.vaadin.flow.data.provider.hierarchy.AbstractBackEndHierarchicalDataProvider;
import com.vaadin.flow.data.provider.hierarchy.HierarchicalDataProvider;
import com.vaadin.flow.data.provider.hierarchy.HierarchicalQuery;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.server.Command;
import org.neotropic.kuwaiba.core.apis.integration.modules.ModuleActionParameter;
import org.neotropic.kuwaiba.core.apis.integration.modules.ModuleActionParameterSet;
import org.neotropic.kuwaiba.core.apis.integration.modules.actions.AbstractVisualInventoryAction;
import org.neotropic.kuwaiba.core.apis.integration.modules.actions.AdvancedActionsRegistry;
import org.neotropic.kuwaiba.core.apis.integration.modules.actions.CoreActionsRegistry;
import org.neotropic.kuwaiba.core.apis.integration.modules.explorers.AbstractExplorer;
import org.neotropic.kuwaiba.core.apis.integration.modules.explorers.ExplorerRegistry;
import org.neotropic.kuwaiba.core.apis.integration.modules.views.AbstractObjectRelatedViewWidget;
import org.neotropic.kuwaiba.core.apis.integration.modules.views.ViewWidgetRegistry;
import org.neotropic.kuwaiba.core.apis.persistence.application.ActivityLogEntry;
import org.neotropic.kuwaiba.core.apis.persistence.application.ApplicationEntityManager;
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
import org.neotropic.kuwaiba.modules.core.navigation.ObjectOptionsPanel;
import org.neotropic.kuwaiba.modules.core.navigation.actions.ShowMoreInformationAction;
import org.neotropic.kuwaiba.modules.core.navigation.explorers.nodes.SpecialChildrenNode;
import org.neotropic.kuwaiba.visualization.api.properties.PropertyValueConverter;
import org.neotropic.kuwaiba.visualization.api.resources.ResourceFactory;
import org.neotropic.util.visual.dialog.ConfirmDialog;
import org.neotropic.util.visual.general.FormattedObjectDisplayNameSpan;
import org.neotropic.util.visual.grids.IconNameCellGrid;
import org.neotropic.util.visual.icons.ClassNameIconGenerator;
import org.neotropic.util.visual.notifications.AbstractNotification;
import org.neotropic.util.visual.notifications.SimpleNotification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import org.neotropic.kuwaiba.modules.core.logging.LoggingService;

/**
 * An explorer that allows the user to see the special children of an inventory object.
 * @author Mauricio Ruiz Beltrán {@literal <mauricio.ruiz@kuwaiba.org>}
 */
@CssImport("./styles/explorer.css")
@Component
public class SpecialChildrenExplorer extends AbstractExplorer<VerticalLayout> {
    /**
     * Reference to the action registry.
     */
    @Autowired
    private CoreActionsRegistry coreActionRegistry;
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
     * Factory to build resources from data source.
     */
    @Autowired
    private ResourceFactory resourceFactory;
    /**
     * The window to show more information about an object.
     */
    @Autowired
    private ShowMoreInformationAction windowMoreInformation;
    /**
     * Reference to the Logging Service.
     */
    @Autowired
    private LoggingService log;
    /**
     * Navigation tree of special children
     */
    private TreeGrid<SpecialChildrenNode> navTree;
    /**
     * Object to save the current object
     */
    private BusinessObjectLight currentObject;
    /**
     * The right-side panel displaying the property sheet of the selected object plus some other options.
     */
    private VerticalLayout lytDetailsPanel;
    /**
     * An icon generator for create icons
     */
    private ClassNameIconGenerator iconGenerator;
    /**
     * Object to show more information about business object
     */
    private Button btnInfo;
    /**
     * The evaluation of the shortcut
     */
    private boolean shortcutActive;
    /**
     * The shortcut listener
     */
    private ShortcutEventListener listener;
    /**
     * Dialog to update the object
     */
    private ConfirmDialog dlgRename;
    
    @Override
    public String getName() {
        return ts.getTranslatedString("module.navigation.explorers.special-children.title");
    }

    @Override
    public String getDescription() {
        return ts.getTranslatedString("module.navigation.explorers.special-children.description");
    }
    
    @Override
    public String getHeader() {
        return ts.getTranslatedString("module.navigation.explorers.special-children.header");
    }

    @Override
    public String appliesTo() {
        return "InventoryObject";
    }
    
    @Override
    public VerticalLayout build(BusinessObjectLight selectedObject) {
        // Main layout
        VerticalLayout lytMain = new VerticalLayout();
        try {
            iconGenerator = new ClassNameIconGenerator(resourceFactory);
            currentObject = selectedObject;
            // List Special Children
            List<BusinessObjectLight> specialChildren = bem.getObjectSpecialChildren(selectedObject.getClassName(), selectedObject.getId());
            if (specialChildren.isEmpty()) {
                // Information
                Label lblHeader = new Label(ts.getTranslatedString("module.general.messages.information"));
                lblHeader.setClassName("dialog-title");
                Label lblInfo = new Label (String.format(ts.getTranslatedString("module.navigation.explorers.special-children.no-special-children"), selectedObject.getName()));
                lytMain.add(lblHeader, lblInfo);
                lytMain.setSizeFull();
                return lytMain;
            } else {
                shortcutActive = true;
                initShortcuts();
                // Add info to layout
                Label lblInfo = new Label(ts.getTranslatedString("module.navigation.explorers.help"));
                lblInfo.setClassName("info-label");
                HorizontalLayout lytInfo = new HorizontalLayout(lblInfo);
                lytInfo.setMargin(false);
                lytInfo.setPadding(false);
                // Navigation Tree
                loadNavigationTree(selectedObject);
                VerticalLayout lytNavTree = new VerticalLayout(navTree);
                lytNavTree.setSizeFull();
                lytNavTree.setHeightFull();
                lytNavTree.setWidth("50%");
                lytNavTree.setSpacing(false);
                lytNavTree.setMargin(false);
                lytNavTree.setPadding(false);
                // Details Panel
                lytDetailsPanel = new VerticalLayout();
                lytDetailsPanel.setId("lyt-details-panel");
                lytDetailsPanel.setSpacing(false);
                lytDetailsPanel.setMargin(false);
                lytDetailsPanel.setHeightFull();
                lytDetailsPanel.setWidth("60%");
                lytDetailsPanel.setClassName("button-more-info-position");
                buildDetailsPanel(currentObject);
                // Content
                HorizontalLayout lytContent = new HorizontalLayout(lytNavTree, lytDetailsPanel);
                lytContent.setWidthFull();
                lytContent.setHeightFull();
                // Add content to layout
                lytMain.add(lytInfo, lytContent);
                lytMain.setHeightFull();
                lytMain.setWidthFull();
                lytMain.setHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
                return lytMain;
            }
        } catch ( InventoryException ex) {
            // Information
            Label lblHeader = new Label(ts.getTranslatedString("module.general.messages.information"));
            Label lblInfo = new Label(String.format(ts.getTranslatedString("module.general.messages.component-cant-be-loaded"), ex.getLocalizedMessage()));
            lytMain.add(lblHeader, lblInfo);
            lytMain.setSizeFull();
            return lytMain;
        }        
    }
    
    private void loadNavigationTree(BusinessObjectLight selectedObject) {
        navTree = new TreeGrid<>();
        navTree.setId("navTree");
        navTree.setHeight("600px");
        navTree.setDataProvider(getDataProvider(selectedObject.getClassName(), selectedObject.getId()));
        navTree.setSelectionMode(Grid.SelectionMode.SINGLE);

        navTree.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_NO_ROW_BORDERS
                , GridVariant.LUMO_COMPACT, GridVariant.LUMO_WRAP_CELL_CONTENT);

        navTree.addComponentHierarchyColumn(item -> {
            FormattedObjectDisplayNameSpan itemName = new FormattedObjectDisplayNameSpan(
                    item.getBusinessObject(), false, false, true, false);

            return new IconNameCellGrid(itemName, item.getBusinessObject().getClassName(), iconGenerator);
        });
        
        navTree.addItemClickListener(event -> {
            currentObject = event.getItem().getBusinessObject();
            btnInfo.setEnabled(true);
            buildDetailsPanel(currentObject);
            lytDetailsPanel.setVisible(true);
        });
    }
    
    private HierarchicalDataProvider getDataProvider(String parentClassName, String parentId) {
        return new AbstractBackEndHierarchicalDataProvider() {
            @Override
            protected Stream fetchChildrenFromBackEnd(HierarchicalQuery query) {
                SpecialChildrenNode parent = (SpecialChildrenNode) query.getParent();
                try {
                    List<SpecialChildrenNode> inventoryNodes = new ArrayList<>();
                    List<BusinessObjectLight> children;
                    if (parent != null)
                        children = bem.getObjectSpecialChildren(parent.getBusinessObject().getClassName(), parent.getBusinessObject().getId());
                    else
                        children = bem.getObjectSpecialChildren(parentClassName, parentId);
                    
                    children.forEach(object -> inventoryNodes.add(new SpecialChildrenNode(object)));
                    return inventoryNodes.stream();
                } catch (MetadataObjectNotFoundException | BusinessObjectNotFoundException | InvalidArgumentException ex) {
                    new SimpleNotification(ts.getTranslatedString("module.general.messages.error"), ex.getLocalizedMessage(),
                            AbstractNotification.NotificationType.ERROR, ts).open();
                    return new ArrayList().stream();
                }
            }

            @Override
            public int getChildCount(HierarchicalQuery query) {
                SpecialChildrenNode parent = (SpecialChildrenNode) query.getParent();
                try {
                    if (parent != null)
                        return (int) bem.countSpecialChildren(parent.getBusinessObject().getClassName(), parent.getBusinessObject().getId());
                    else
                        return (int) bem.countSpecialChildren(parentClassName, parentId);
                } catch (MetadataObjectNotFoundException | BusinessObjectNotFoundException | InvalidArgumentException ex) {
                    new SimpleNotification(ts.getTranslatedString("module.general.messages.error"), ex.getLocalizedMessage(),
                            AbstractNotification.NotificationType.ERROR, ts).open();
                    return 0;
                }
            }

            @Override
            public boolean hasChildren(Object object) {
                if (object instanceof SpecialChildrenNode) {
                    SpecialChildrenNode node = (SpecialChildrenNode) object;
                    try {
                        return ((int) bem.countSpecialChildren(node.getBusinessObject().getClassName(),
                                node.getBusinessObject().getId())) > 0;
                    } catch (MetadataObjectNotFoundException | BusinessObjectNotFoundException |
                             InvalidArgumentException e) {
                        return false;
                    }
                } else
                    return false;
            }
        };
    }
    
    /**
     * Creates the right-most layout with the options for the selected object
     * @param selectedObject the selected object in the nav tree
     */
    private void buildDetailsPanel(BusinessObjectLight selectedObject) {
        try {
            lytDetailsPanel.removeAll();
            if (!selectedObject.getClassName().equals(Constants.DUMMY_ROOT)) {
                ObjectOptionsPanel pnlOptions = new ObjectOptionsPanel(selectedObject,
                        coreActionRegistry, advancedActionsRegistry, viewWidgetRegistry, explorerRegistry, mem, aem, bem, ts, log);
                pnlOptions.setShowCoreActions(true);
                pnlOptions.setShowCustomActions(true);
                pnlOptions.setShowViews(true);
                pnlOptions.setShowExplorers(true);
                pnlOptions.setSelectionListener((event) -> {
                    try {
                        switch (event.getActionCommand()) {
                            case ObjectOptionsPanel.EVENT_ACTION_SELECTION:
                                ModuleActionParameterSet parameters = new ModuleActionParameterSet(new ModuleActionParameter<>("businessObject", selectedObject));
                                Dialog wdwObjectAction = (Dialog) ((AbstractVisualInventoryAction) event.getSource()).getVisualComponent(parameters);
                                wdwObjectAction.open();
                                break;
                            case ObjectOptionsPanel.EVENT_EXPLORER_SELECTION:
                                ConfirmDialog wdwExplorer = new ConfirmDialog(ts);
                                wdwExplorer.getBtnConfirm().addClickListener(ev -> wdwExplorer.close());
                                wdwExplorer.getBtnCancel().setVisible(false);
                                wdwExplorer.setHeader(String.format(ts.getTranslatedString(
                                                ((AbstractExplorer<?>) event.getSource()).getHeader()),
                                        selectedObject.toString()));
                                wdwExplorer.setContent(((AbstractExplorer<?>) event.getSource()).build(selectedObject));
                                wdwExplorer.setHeight("90%");
                                wdwExplorer.setMinWidth("70%");
                                wdwExplorer.open();
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
                                        event.getSource()).getTitle(), selectedObject.getName())));
                                wdwView.setContent(((AbstractObjectRelatedViewWidget<?>) event.getSource()).build(selectedObject));
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
                        if(property.getName().equals(Constants.PROPERTY_NAME))
                            refreshGrids();
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
                // Header
                Label lblTitle = new Label(selectedObject.toString());
                lblTitle.setClassName("dialog-title");
                // Action show more information
                btnInfo = new Button(this.windowMoreInformation.getDisplayName());
                btnInfo.setWidthFull();
                btnInfo.addClickListener(event ->
                    this.windowMoreInformation.getVisualComponent(new ModuleActionParameterSet(
                            new ModuleActionParameter<>("object", currentObject))).open());
                // Add content to layout
                lytDetailsPanel.add(lblTitle, btnInfo, pnlOptions.build(UI.getCurrent().getSession().getAttribute(Session.class).getUser()));
            }
        } catch (InventoryException ex) {
            new SimpleNotification(ts.getTranslatedString("module.general.messages.error"), 
                    ex.getLocalizedMessage(), AbstractNotification.NotificationType.ERROR, ts).open();
        }
    }
    
    /**
     * Refresh the grids if an attributes is updated
     */
    private void refreshGrids() {
        if (navTree != null)
            navTree.getDataProvider().refreshAll();
    }

    /**
     * Initializes the shortcuts handler, Handles rename F2 shortcut.
     */
    private void initShortcuts() {
        if (listener == null) {
            listener = event -> {
                if (shortcutActive && event.matches(Key.F2)) {
                    if (navTree != null && navTree.getSelectedItems() != null && !navTree.getSelectedItems().isEmpty()) {
                        Optional<SpecialChildrenNode> selected = navTree.getSelectedItems().stream().findFirst();
                        selected.ifPresent(s -> shortcutNameEditor(s.getBusinessObject()));
                    }
                }
            };
            UI.getCurrent().addShortcutListener(listener, Key.F2);
        }
    }

    /**
     * Creates a dialog to edit the node name.
     *
     * @param obj The selected node.
     */
    private void shortcutNameEditor(BusinessObjectLight obj) {
        if (obj != null) {
            TextField txtEditValue = new TextField(ts.getTranslatedString("module.general.labels.new-name"));
            txtEditValue.setWidthFull();
            txtEditValue.setValue(obj.getName());

            Command cmdRenameObject = () -> {
                try {
                    HashMap<String, String> attributes = new HashMap<>();
                    attributes.put(Constants.PROPERTY_NAME, txtEditValue.getValue());
                    bem.updateObject(obj.getClassName(), obj.getId(), attributes);
                    refreshGrids();
                    txtEditValue.clear();
                    buildDetailsPanel(obj);
                    dlgRename = null;
                } catch (MetadataObjectNotFoundException | BusinessObjectNotFoundException
                         | OperationNotPermittedException | InvalidArgumentException ex) {
                    new SimpleNotification(ts.getTranslatedString("module.general.messages.error"),
                            ex.getLocalizedMessage(), AbstractNotification.NotificationType.ERROR, ts).open();
                }
            };

            if (dlgRename == null) {
                dlgRename = new ConfirmDialog(ts
                        , txtEditValue
                        , cmdRenameObject);
                dlgRename.setHeader(ts.getTranslatedString("module.general.labels.rename"));
                dlgRename.setThemeVariants(EnhancedDialogVariant.SIZE_SMALL);
                dlgRename.setWidth(Constants.DEFAULT_SMALL_DIALOG_WIDTH);
                dlgRename.getBtnConfirm().setEnabled(false);

                txtEditValue.setValueChangeMode(ValueChangeMode.EAGER);
                txtEditValue.addValueChangeListener(e -> {
                    if (dlgRename != null)
                        dlgRename.getBtnConfirm().setEnabled(!e.getValue().equals(obj.getName()));
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

    @Override
    public void clearResources() {
        shortcutActive = false;
        navTree = null;
    }
}