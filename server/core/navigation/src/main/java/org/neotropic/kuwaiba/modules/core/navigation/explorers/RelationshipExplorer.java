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
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.server.Command;
import org.neotropic.kuwaiba.core.apis.integration.modules.ModuleActionParameter;
import org.neotropic.kuwaiba.core.apis.integration.modules.ModuleActionParameterSet;
import org.neotropic.kuwaiba.core.apis.integration.modules.actions.AbstractVisualInventoryAction;
import org.neotropic.kuwaiba.core.apis.integration.modules.actions.AdvancedActionsRegistry;
import org.neotropic.kuwaiba.core.apis.integration.modules.actions.CoreActionsRegistry;
import org.neotropic.kuwaiba.core.apis.integration.modules.explorers.AbstractExplorer;
import org.neotropic.kuwaiba.core.apis.integration.modules.explorers.ExplorerRegistry;
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
import org.neotropic.kuwaiba.modules.core.navigation.explorers.nodes.RelationshipExplorerNode;
import org.neotropic.kuwaiba.modules.core.navigation.explorers.provider.RelationshipExplorerNodeProvider;
import org.neotropic.kuwaiba.modules.core.navigation.explorers.trees.RelationshipExplorerNodeTreeGrid;
import org.neotropic.kuwaiba.visualization.api.properties.PropertyValueConverter;
import org.neotropic.kuwaiba.visualization.api.resources.ResourceFactory;
import org.neotropic.util.visual.dialog.ConfirmDialog;
import org.neotropic.util.visual.notifications.AbstractNotification;
import org.neotropic.util.visual.notifications.SimpleNotification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.neotropic.kuwaiba.modules.core.logging.LoggingService;

/**
 * An explorer that allows the user to see the relationships of an inventory object.
 * @author Mauricio Ruiz Beltrán {@literal <mauricio.ruiz@kuwaiba.org>}
 */
@CssImport("./styles/explorer.css")
@Component
public class RelationshipExplorer extends AbstractExplorer<VerticalLayout> {
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
     * Navigation tree of relationships
     */
    private RelationshipExplorerNodeTreeGrid<RelationshipExplorerNode> navTree;
    /**
     * Object to save the current object
     */
    private BusinessObjectLight currentObject;
    /**
     * The right-side panel displaying the property sheet of the selected object plus some other options.
     */
    private VerticalLayout lytDetailsPanel;
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
        return ts.getTranslatedString("module.navigation.explorers.relationship.title");
    }

    @Override
    public String getDescription() {
        return ts.getTranslatedString("module.navigation.explorers.relationship.description");
    }
    
    @Override
    public String getHeader() {
        return ts.getTranslatedString("module.navigation.explorers.relationship.header");
    }

    @Override
    public String appliesTo() {
        return "InventoryObject";
    }
    
    @Override
    public VerticalLayout build(BusinessObjectLight selectedObject) {
        shortcutActive = true;
        initShortcuts();
        currentObject = selectedObject;
        // Main Layout
        VerticalLayout lytMain = new VerticalLayout();
        // Add info to layout
        Label lblInfo = new Label(ts.getTranslatedString("module.navigation.explorers.help"));
        lblInfo.setClassName("info-label");
        HorizontalLayout lytInfo = new HorizontalLayout(lblInfo);
        lytInfo.setMargin(false);
        lytInfo.setPadding(false);
        // Navigation Tree
        loadNavigationTree(selectedObject);
        VerticalLayout lytNavTree = new VerticalLayout(navTree);
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
        return lytMain;
    }

    private void loadNavigationTree(BusinessObjectLight selectedObject) {
        navTree = new RelationshipExplorerNodeTreeGrid<>();
        navTree.setId("navTree");
        navTree.setHeight("600px");

        navTree.createDataProvider(resourceFactory,
                new RelationshipExplorerNodeProvider(bem, ts),
                new RelationshipExplorerNode(selectedObject));

        navTree.setSelectionMode(Grid.SelectionMode.SINGLE);
        navTree.setAllRowsVisible(true);
        navTree.expand(new RelationshipExplorerNode(selectedObject));

        navTree.addItemClickListener(event -> {
            if (event.getItem().getType().equals(RelationshipExplorerNode.RelationshipExplorerNodeType.BUSINESS_OBJECT)) {
                btnInfo.setEnabled(true);
                currentObject = event.getItem().getBusinessObject();
                buildDetailsPanel(currentObject);
                lytDetailsPanel.setVisible(true);
            } else {
                btnInfo.setEnabled(false);
                lytDetailsPanel.setVisible(false);
            }
        });

        navTree.addCollapseListener(event -> navTree.collapseNodes(event.getItems()));
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
                pnlOptions.setShowViews(false);
                pnlOptions.setShowExplorers(true);
                pnlOptions.setSelectionListener((event) -> {
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
                            refreshGrids(selectedObject.getId(), property.getAsString());
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
                // Action show more information
                btnInfo = new Button(this.windowMoreInformation.getDisplayName());
                btnInfo.setWidthFull();
                btnInfo.addClickListener(event ->
                        this.windowMoreInformation.getVisualComponent(new ModuleActionParameterSet(
                                new ModuleActionParameter<>("object", currentObject))).open());
                // Add content to layout
                lytDetailsPanel.add(btnInfo, pnlOptions.build(UI.getCurrent().getSession().getAttribute(Session.class).getUser()));
            }
        } catch (InventoryException ex) {
            new SimpleNotification(ts.getTranslatedString("module.general.messages.error"), 
                    ex.getLocalizedMessage(), AbstractNotification.NotificationType.ERROR, ts).open();
        }
    }
    
    /**
     * Refresh the grids if an attributes is updated
     */
    private void refreshGrids(String id, String newName) {
        if (navTree != null) {
            try {
                List<RelationshipExplorerNode> nodes = navTree.getNodesById(id);
                nodes.forEach(node -> {
                    if (node != null) {
                        node.getBusinessObject().setName(newName);
                        navTree.updateChild(node);
                    }
                });
            } catch (NoSuchElementException ex) {
                new SimpleNotification(ts.getTranslatedString("module.general.messages.error"),
                        ex.getLocalizedMessage(), AbstractNotification.NotificationType.ERROR, ts).open();
            }
        }
    }

    /**
     * Initializes the shortcuts handler, Handles rename F2 shortcut.
     */
    private void initShortcuts() {
        if (listener == null) {
            listener = event -> {
                if (shortcutActive && event.matches(Key.F2)) {
                    if (navTree != null && navTree.getSelectedItems() != null && !navTree.getSelectedItems().isEmpty()) {
                        Optional<RelationshipExplorerNode> selected = navTree.getSelectedItems().stream().findFirst();
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
                    refreshGrids(obj.getId(), obj.getName());
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