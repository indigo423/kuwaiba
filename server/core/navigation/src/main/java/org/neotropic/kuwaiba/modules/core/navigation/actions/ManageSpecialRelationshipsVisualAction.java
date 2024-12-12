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
package org.neotropic.kuwaiba.modules.core.navigation.actions;

import com.vaadin.componentfactory.theme.EnhancedDialogVariant;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.BoxSizing;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouteConfiguration;
import com.vaadin.flow.server.VaadinService;
import org.neotropic.kuwaiba.core.apis.integration.modules.ModuleActionParameter;
import org.neotropic.kuwaiba.core.apis.integration.modules.ModuleActionParameterSet;
import org.neotropic.kuwaiba.core.apis.integration.modules.actions.AbstractAction;
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
import org.neotropic.kuwaiba.core.apis.persistence.application.Session;
import org.neotropic.kuwaiba.core.apis.persistence.business.BusinessEntityManager;
import org.neotropic.kuwaiba.core.apis.persistence.business.BusinessObjectLight;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.BusinessObjectNotFoundException;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.InvalidArgumentException;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.InventoryException;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.MetadataObjectNotFoundException;
import org.neotropic.kuwaiba.core.apis.persistence.metadata.MetadataEntityManager;
import org.neotropic.kuwaiba.core.apis.persistence.util.Constants;
import org.neotropic.kuwaiba.core.i18n.TranslationService;
import org.neotropic.kuwaiba.modules.core.navigation.NavigationModule;
import org.neotropic.kuwaiba.modules.core.navigation.ObjectDashboard;
import org.neotropic.kuwaiba.modules.core.navigation.ObjectOptionsPanel;
import org.neotropic.kuwaiba.visualization.api.properties.PropertyValueConverter;
import org.neotropic.kuwaiba.visualization.api.resources.ResourceFactory;
import org.neotropic.util.visual.button.ActionButton;
import org.neotropic.util.visual.dialog.ConfirmDialog;
import org.neotropic.util.visual.general.FormattedObjectDisplayNameSpan;
import org.neotropic.util.visual.icon.ActionIcon;
import org.neotropic.util.visual.icons.ClassNameIconGenerator;
import org.neotropic.util.visual.notifications.AbstractNotification;
import org.neotropic.util.visual.notifications.SimpleNotification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import org.neotropic.kuwaiba.modules.core.logging.LoggingService;

/**
 * UI of manage special relationship.
 *
 * @author Mauricio Ruiz Beltrán {@literal <mauricio.ruiz@kuwaiba.org>}
 */
@Component
public class ManageSpecialRelationshipsVisualAction extends AbstractVisualInventoryAction {
    /**
     * Reference to the Translation Service.
     */
    @Autowired
    private TranslationService ts;
    /**
     * Reference to the Business Entity Manager.
     */
    @Autowired
    private BusinessEntityManager bem;
    /**
     * Reference to the Management Special Relationships Action.
     */
    @Autowired
    private ManageSpecialRelationshipsAction manageSpecialRelationshipsAction;
    /**
     * Reference to the Release Special Relationship Visual Action.
     */
    @Autowired
    private ReleaseSpecialRelationshipVisualAction releaseSpecialRelationshipVisualAction;
    /**
     * Reference to the Release Multiple Special Relationship Visual Action.
     */
    @Autowired
    private ReleaseMultipleSpecialRelationshipVisualAction releaseMultipleSpecialRelationshipVisualAction;
    /**
     * Reference to the New Special Relationship Visual Action.
     */
    @Autowired
    private NewSpecialRelationshipVisualAction newSpecialRelationshipVisualAction;
    /**
     * Factory to build resources from data source.
     */
    @Autowired
    private ResourceFactory resourceFactory;
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
     * The window to show more information about an object
     */
    @Autowired
    private ShowMoreInformationAction windowMoreInformation;
    /**
     * Reference to the Logging Service.
     */
    @Autowired
    private LoggingService log;

    public ManageSpecialRelationshipsVisualAction() {
        super(NavigationModule.MODULE_ID);
    }

    @Override
    public Dialog getVisualComponent(ModuleActionParameterSet parameters) {
        String paramBusinessObject = "businessObject"; //NOI18N
        String paramObject = "object"; //NOI18N
        String paramOtherObject = "otherObject"; //NOI18N
        String paramRelationshipName = "relationshipName"; //NOI18N

        BusinessObjectLight selectedObject = (BusinessObjectLight) parameters.get(paramBusinessObject);
        if (selectedObject != null) {
            AtomicReference<String> currentRelationshipName = new AtomicReference<>();//Saves the current relationship
            AtomicReference<BusinessObjectLight> businessObjectDetails = new AtomicReference<>();//Saves the current selected object, to show details
            List<String> relationshipNames = getRelationshipNames(bem, ts, selectedObject);
            // Top content layout
            Label lblNoRelationships = new Label(!relationshipNames.isEmpty() ? "" :
                    ts.getTranslatedString("module.navigation.actions.management-special-relationships.label-no-relationships"));
            lblNoRelationships.setClassName("info-label");
            lblNoRelationships.setWidthFull();

            ActionButton btnRelate = new ActionButton(
                    this.newSpecialRelationshipVisualAction.getModuleAction().getDisplayName(),
                    new ActionIcon(VaadinIcon.PLUS_SQUARE_O),
                    this.newSpecialRelationshipVisualAction.getModuleAction().getDisplayName()
            );
            btnRelate.setWidth("18%");
            btnRelate.setHeight("30px");
            btnRelate.setId("btn-relate");
            btnRelate.getElement().getStyle().set("margin-right", "20px");

            btnRelate.addClickListener(event ->
                    this.newSpecialRelationshipVisualAction.getVisualComponent(new ModuleActionParameterSet(
                            new ModuleActionParameter<>(paramObject, selectedObject)
                    )).open()
            );

            HorizontalLayout lytTop = new HorizontalLayout(lblNoRelationships, btnRelate);
            lytTop.setPadding(false);
            lytTop.setMargin(false);

            // Details panel layout
            VerticalLayout lytDetailsPanel = new VerticalLayout();
            lytDetailsPanel.setId("lyt-details-panel");
            lytDetailsPanel.setSpacing(false);
            lytDetailsPanel.setHeightFull();
            lytDetailsPanel.setWidthFull();

            // It will show the relationships, if they exist, of the selected object
            Grid<String> tblRelationshipNames = new Grid<>();
            tblRelationshipNames.setId("tbl-relationship-names");
            tblRelationshipNames.setItems(relationshipNames);
            tblRelationshipNames.setHeight("600px");
            tblRelationshipNames.setWidthFull();
            tblRelationshipNames.addComponentColumn(relationshipName -> {
                ActionButton btnRelease = new ActionButton(new ActionIcon(VaadinIcon.UNLINK),
                        this.releaseMultipleSpecialRelationshipVisualAction.getModuleAction().getDisplayName());
                btnRelease.setWidth("10px");

                btnRelease.addClickListener(event -> {
                    List<BusinessObjectLight> targetObjects = getTargetObjects(bem, ts, relationshipName, selectedObject);

                    this.releaseMultipleSpecialRelationshipVisualAction.getVisualComponent(new ModuleActionParameterSet(
                            new ModuleActionParameter<>(ReleaseMultipleSpecialRelationshipVisualAction.PARAM_OBJECT, selectedObject),
                            new ModuleActionParameter<>(ReleaseMultipleSpecialRelationshipVisualAction.PARAM_RELATIONSHIP_NAME, relationshipName),
                            new ModuleActionParameter<>(ReleaseMultipleSpecialRelationshipVisualAction.PARAM_OTHER_OBJECTS, targetObjects)
                    )).open();
                });

                Label lblIcon = new Label(relationshipName);
                Image objIcon = new Image();
                objIcon.setSrc(resourceFactory.getRelationshipIcon(Color.decode("#5bb327"), 10, 10));

                HorizontalLayout lytCell = new HorizontalLayout(objIcon, lblIcon);
                lytCell.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
                lytCell.getElement().getStyle().set("overflow-x", "auto");
                lytCell.setBoxSizing(BoxSizing.BORDER_BOX);
                lytCell.setWidthFull();
                lytCell.setSpacing(true);
                lytCell.setMargin(false);
                lytCell.setPadding(false);
                lytCell.setId("lyt-cell-relationship");

                HorizontalLayout lytRelationship = new HorizontalLayout(lytCell, btnRelease);
                lytRelationship.setSpacing(true);
                lytRelationship.setMargin(false);
                lytRelationship.setPadding(false);
                return lytRelationship;
            });
            tblRelationshipNames.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_NO_ROW_BORDERS
                    , GridVariant.LUMO_COMPACT, GridVariant.LUMO_WRAP_CELL_CONTENT);

            // It will show objects that have the selected relationship
            Grid<BusinessObjectLight> tblTargetObjects = new Grid<>();
            tblTargetObjects.setSelectionMode(Grid.SelectionMode.SINGLE);
            tblTargetObjects.setId("tbl-target-objects");
            tblTargetObjects.setHeight("600px");
            tblTargetObjects.setWidthFull();
            tblTargetObjects.addComponentColumn(anObject -> {
                ActionButton btnRelease = new ActionButton(new ActionIcon(VaadinIcon.UNLINK),
                        ts.getTranslatedString("module.navigation.actions.release-special-relationship.title"));
                btnRelease.setWidth("10px");

                btnRelease.addClickListener(event -> {
                    this.releaseSpecialRelationshipVisualAction.getVisualComponent(new ModuleActionParameterSet(
                            new ModuleActionParameter<>(ReleaseSpecialRelationshipVisualAction.PARAM_OBJECT, selectedObject),
                            new ModuleActionParameter<>(ReleaseSpecialRelationshipVisualAction.PARAM_OTHER_OBJECT, anObject),
                            new ModuleActionParameter<>(ReleaseSpecialRelationshipVisualAction.PARAM_RELATIONSHIP_NAME,
                                    currentRelationshipName.get()))
                    ).open();
                });

                Image objIcon = new Image(new ClassNameIconGenerator(resourceFactory).apply(anObject.getClassName()), "-");

                FormattedObjectDisplayNameSpan itemName = new FormattedObjectDisplayNameSpan(
                        anObject, false, false,
                        true, false);

                HorizontalLayout lytCell = new HorizontalLayout(objIcon, itemName);
                lytCell.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
                lytCell.getElement().getStyle().set("overflow-x", "auto");
                lytCell.setBoxSizing(BoxSizing.BORDER_BOX);
                lytCell.setWidthFull();
                lytCell.setSpacing(true);
                lytCell.setMargin(false);
                lytCell.setPadding(false);
                lytCell.setId("lyt-cell-objects");

                HorizontalLayout lytObjects = new HorizontalLayout(lytCell, btnRelease);
                lytObjects.setSpacing(true);
                lytObjects.setMargin(false);
                lytObjects.setPadding(false);
                return lytObjects;
            });
            tblTargetObjects.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_NO_ROW_BORDERS
                    , GridVariant.LUMO_COMPACT, GridVariant.LUMO_WRAP_CELL_CONTENT);


            //After selecting a relationship, the related objects will be displayed.
            tblRelationshipNames.addItemClickListener(event -> {
                if (event.getItem() != null) {
                    currentRelationshipName.set(event.getItem());
                    tblTargetObjects.setItems(getTargetObjects(bem, ts, currentRelationshipName.get(), selectedObject));
                }
            });

            //After selecting a target object
            tblTargetObjects.addItemClickListener(event -> {
                lytDetailsPanel.removeAll(); // First clean
                if (event.getItem() != null && !event.getItem().getClassName().equals(Constants.DUMMY_ROOT)) {
                    businessObjectDetails.set(event.getItem());

                    Label lblTitle = new Label(businessObjectDetails.get().toString());
                    lblTitle.setClassName("dialog-title");
                    lytDetailsPanel.add(lblTitle);// Add content to layout

                    // Action go to Dashboard
                    Button btnGoToDashboard = new Button(
                            ts.getTranslatedString("module.navigation.widgets.object-dashboard.open-to-dashboard"));
                    btnGoToDashboard.setWidth("50%");
                    btnGoToDashboard.addClickListener(ev -> btnGoToDashboard.getUI().ifPresent(ui -> {
                        ui.getSession().setAttribute(BusinessObjectLight.class, businessObjectDetails.get());
                        ui.getPage().open(RouteConfiguration.forRegistry(
                                        VaadinService.getCurrent().getRouter().getRegistry())
                                .getUrl(ObjectDashboard.class), "_blank");
                    }));

                    // Show more information
                    Button btnInfo = new Button(ts.getTranslatedString("module.navigation.actions.show-more-information-button-name"));
                    btnInfo.setWidth("50%");
                    btnInfo.addClickListener(e -> this.windowMoreInformation.getVisualComponent(new ModuleActionParameterSet(
                            new ModuleActionParameter<>(paramObject, businessObjectDetails.get()))).open());

                    HorizontalLayout lytExtraActions = new HorizontalLayout(btnGoToDashboard, btnInfo);
                    lytExtraActions.setSpacing(false);
                    lytExtraActions.setId("lyt-extra-actions");
                    lytExtraActions.setWidthFull();
                    lytDetailsPanel.add(lytExtraActions);// Add content to layout

                    try {
                        ObjectOptionsPanel pnlOptions = new ObjectOptionsPanel(businessObjectDetails.get(),
                                actionRegistry, advancedActionsRegistry, viewWidgetRegistry,
                                explorerRegistry, mem, aem, bem, ts, log);
                        pnlOptions.setShowViews(true);
                        pnlOptions.setShowExplorers(true);
                        pnlOptions.setSelectionListener(e -> {
                            try {
                                switch (e.getActionCommand()) {
                                    case ObjectOptionsPanel.EVENT_ACTION_SELECTION:
                                        ModuleActionParameterSet actionParameters = new ModuleActionParameterSet(
                                                new ModuleActionParameter<>(paramBusinessObject, businessObjectDetails.get()));
                                        Dialog wdwObjectAction = (Dialog) ((AbstractVisualInventoryAction) e.getSource())
                                                .getVisualComponent(actionParameters);
                                        wdwObjectAction.open();
                                        break;
                                    case ObjectOptionsPanel.EVENT_EXPLORER_SELECTION:
                                        ConfirmDialog wdwExplorer = new ConfirmDialog(ts);
                                        wdwExplorer.getBtnConfirm().addClickListener(ev -> wdwExplorer.close());
                                        wdwExplorer.getBtnCancel().setVisible(false);
                                        wdwExplorer.setHeader(String.format(ts.getTranslatedString(
                                                        ((AbstractExplorer<?>) e.getSource()).getHeader()),
                                                businessObjectDetails.get().toString()));
                                        wdwExplorer.setContent(((AbstractExplorer<?>) e.getSource()).build(businessObjectDetails.get()));
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
                                                e.getSource()).getTitle(), businessObjectDetails.get().getName())));
                                        wdwView.setContent(((AbstractObjectRelatedViewWidget<?>) e.getSource()).build(businessObjectDetails.get()));
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
                            Object lastValue = pnlOptions.lastValue(property.getName());
                            attributes.put(property.getName(), PropertyValueConverter.getAsStringToPersist(property));
                            try {
                                bem.updateObject(businessObjectDetails.get().getClassName(), businessObjectDetails.get().getId(), attributes);
                                if (property.getName().equals(Constants.PROPERTY_NAME)) {
                                    businessObjectDetails.get().setName(PropertyValueConverter.getAsStringToPersist(property));
                                    tblTargetObjects.getDataProvider().refreshItem(businessObjectDetails.get());
                                }
                                new SimpleNotification(ts.getTranslatedString("module.general.messages.success"),
                                        ts.getTranslatedString("module.general.messages.property-updated-successfully"),
                                        AbstractNotification.NotificationType.INFO, ts).open();
                                // activity log
                                aem.createObjectActivityLogEntry(session.getUser().getUserName(), businessObjectDetails.get().getClassName(),
                                        businessObjectDetails.get().getId(), ActivityLogEntry.ACTIVITY_TYPE_UPDATE_INVENTORY_OBJECT,
                                        property.getName(), lastValue == null ? "" : lastValue.toString(), property.getAsString(), "");
                            } catch (InventoryException ex) {
                                new SimpleNotification(ts.getTranslatedString("module.general.messages.error"),
                                        ex.getLocalizedMessage(), AbstractNotification.NotificationType.ERROR, ts).open();
                                pnlOptions.UndoLastEdit();
                            }
                        });

                        // Add content to layout
                        lytDetailsPanel.add(pnlOptions.build(UI.getCurrent().getSession().getAttribute(Session.class).getUser()));
                    } catch (InventoryException ex) {
                        new SimpleNotification(ts.getTranslatedString("module.general.messages.error"),
                                ex.getLocalizedMessage(), AbstractNotification.NotificationType.ERROR, ts).open();
                    }
                }
            });

            // --> This listener allows to update the UI after relating an object.
            ActionCompletedListener listenerNewSpecialRelationship = (ActionCompletedListener.ActionCompletedEvent ev) -> {
                if (ev.getStatus() == ActionCompletedListener.ActionCompletedEvent.STATUS_SUCCESS) {
                    if (ev.getActionResponse() != null && ev.getActionResponse().containsKey(paramObject)
                            && ev.getActionResponse().get(paramObject) != null
                            && ev.getActionResponse().containsKey(ActionResponse.ActionType.RELATE)
                            && ev.getActionResponse().containsKey(paramRelationshipName)
                            && ev.getActionResponse().get(paramRelationshipName) != null) {
                        BusinessObjectLight sourceObject = (BusinessObjectLight) ev.getActionResponse().get(paramObject);

                        if (selectedObject.equals(sourceObject)) {
                            tblRelationshipNames.setItems(getRelationshipNames(bem, ts, sourceObject));
                            if (currentRelationshipName.get() != null
                                    && currentRelationshipName.get().equals(ev.getActionResponse().get(paramRelationshipName)))
                                tblTargetObjects.setItems(getTargetObjects(bem, ts, currentRelationshipName.get(), sourceObject));

                            new SimpleNotification(ts.getTranslatedString("module.general.messages.success"),
                                    ev.getMessage(), AbstractNotification.NotificationType.INFO, ts).open();
                        }
                    }
                } else
                    new SimpleNotification(ts.getTranslatedString("module.general.messages.error"),
                            ev.getMessage(), AbstractNotification.NotificationType.ERROR, ts).open();
            };
            this.newSpecialRelationshipVisualAction.unregisterListener(listenerNewSpecialRelationship);
            this.newSpecialRelationshipVisualAction.registerActionCompletedLister(listenerNewSpecialRelationship);

            // --> This listener allows to update the UI after releasing an object.
            ActionCompletedListener listenerReleaseSpecialRelationship = (ActionCompletedListener.ActionCompletedEvent ev) -> {
                if (ev.getStatus() == ActionCompletedListener.ActionCompletedEvent.STATUS_SUCCESS) {
                    if (ev.getActionResponse() != null
                            && ev.getActionResponse().containsKey(paramObject)
                            && ev.getActionResponse().get(paramObject) != null
                            && ev.getActionResponse().containsKey(ActionResponse.ActionType.RELEASE)) {

                        BusinessObjectLight sourceObject = (BusinessObjectLight) ev.getActionResponse().get(paramObject);
                        if (sourceObject.equals(selectedObject)) {
                            if (ev.getActionResponse().containsKey(paramRelationshipName)
                                    && ev.getActionResponse().get(paramRelationshipName) != null) {
                                List<BusinessObjectLight> objects = getTargetObjects(bem, ts,
                                        (String) ev.getActionResponse().get(paramRelationshipName), sourceObject);

                                if (ev.getActionResponse().containsKey(paramOtherObject)
                                        && ev.getActionResponse().get(paramOtherObject) != null
                                        && businessObjectDetails.get() != null
                                        && ev.getActionResponse().get(paramOtherObject).equals(businessObjectDetails.get()))
                                    lytDetailsPanel.removeAll();

                                if (objects.isEmpty())
                                    tblRelationshipNames.setItems(getRelationshipNames(bem, ts, sourceObject));

                                tblTargetObjects.setItems(objects);
                            } else {
                                lytDetailsPanel.removeAll();
                                tblTargetObjects.setItems(new ArrayList<>());
                                tblRelationshipNames.setItems(getRelationshipNames(bem, ts, sourceObject));
                            }
                            new SimpleNotification(ts.getTranslatedString("module.general.messages.success"),
                                    ev.getMessage(), AbstractNotification.NotificationType.INFO, ts).open();
                        }
                    }
                } else
                    new SimpleNotification(ts.getTranslatedString("module.general.messages.error"),
                            ev.getMessage(), AbstractNotification.NotificationType.ERROR, ts).open();
            };
            this.releaseSpecialRelationshipVisualAction.unregisterListener(listenerReleaseSpecialRelationship);
            this.releaseSpecialRelationshipVisualAction.registerActionCompletedLister(listenerReleaseSpecialRelationship);

            // --> This listener allows to update the UI after releasing multiples objects.
            ActionCompletedListener listenerMultipleReleaseSpecialRelationship = (ActionCompletedListener.ActionCompletedEvent ev) -> {
                if (ev.getStatus() == ActionCompletedListener.ActionCompletedEvent.STATUS_SUCCESS) {
                    if (ev.getActionResponse() != null
                            && ev.getActionResponse().containsKey(paramObject)
                            && ev.getActionResponse().get(paramObject) != null
                            && ev.getActionResponse().containsKey(ActionResponse.ActionType.RELEASE)) {

                        BusinessObjectLight sourceObject = (BusinessObjectLight) ev.getActionResponse().get(paramObject);
                        if (sourceObject.equals(selectedObject)) {
                            if (ev.getActionResponse().containsKey(paramRelationshipName)
                                    && ev.getActionResponse().get(paramRelationshipName) != null) {
                                List<BusinessObjectLight> objects = getTargetObjects(bem, ts,
                                        (String) ev.getActionResponse().get(paramRelationshipName), sourceObject);

                                if (ev.getActionResponse().containsKey(paramOtherObject)
                                        && ev.getActionResponse().get(paramOtherObject) != null
                                        && businessObjectDetails.get() != null
                                        && ev.getActionResponse().get(paramOtherObject).equals(businessObjectDetails.get()))
                                    lytDetailsPanel.removeAll();

                                if (objects.isEmpty())
                                    tblRelationshipNames.setItems(getRelationshipNames(bem, ts, sourceObject));

                                tblTargetObjects.setItems(objects);
                            } else {
                                lytDetailsPanel.removeAll();
                                tblTargetObjects.setItems(new ArrayList<>());
                                tblRelationshipNames.setItems(getRelationshipNames(bem, ts, sourceObject));
                            }
                            new SimpleNotification(ts.getTranslatedString("module.general.messages.success"),
                                    ev.getMessage(), AbstractNotification.NotificationType.INFO, ts).open();
                        }
                    }
                } else
                    new SimpleNotification(ts.getTranslatedString("module.general.messages.error"),
                            ev.getMessage(), AbstractNotification.NotificationType.ERROR, ts).open();
            };
            this.releaseMultipleSpecialRelationshipVisualAction.unregisterListener(listenerMultipleReleaseSpecialRelationship);
            this.releaseMultipleSpecialRelationshipVisualAction.registerActionCompletedLister(listenerMultipleReleaseSpecialRelationship);

            // Left Content
            Label lblHeader = new Label(ts.getTranslatedString("module.navigation.actions.management-special-relationships.header-relationshipNames"));
            lblHeader.setWidthFull();

            VerticalLayout lytLeftSite = new VerticalLayout(lblHeader, tblRelationshipNames);
            lytLeftSite.setId("lyt-left-site");
            lytLeftSite.setHeightFull();
            lytLeftSite.setWidth("33%");

            // Center Content
            Label lblHeaderObjects = new Label(ts.getTranslatedString("module.navigation.actions.management-special-relationships.header-objects"));
            lblHeaderObjects.setWidthFull();

            Label lblInfo = new Label(ts.getTranslatedString("module.navigation.actions.management-special-relationships.label-select-relationship"));
            lblInfo.setClassName("info-label");
            lblInfo.setWidthFull();

            VerticalLayout lytCenterSite = new VerticalLayout(lblHeaderObjects, tblTargetObjects, lblInfo);
            lytCenterSite.setId("lyt-center-site");
            lytCenterSite.setHeightFull();
            lytCenterSite.setWidth("33%");

            // Right Content
            Label lblInfoDetails = new Label(ts.getTranslatedString("module.navigation.actions.management-special-relationships.header-details"));
            lblInfoDetails.setClassName("info-label");
            lblInfoDetails.setWidthFull();

            VerticalLayout lytRightSite = new VerticalLayout(lblInfoDetails, lytDetailsPanel);
            lytRightSite.setId("lyt-right-site");
            lytRightSite.setHeightFull();
            lytRightSite.setWidth("33%");

            // Main Layout
            HorizontalLayout lytMain = new HorizontalLayout(lytLeftSite, lytCenterSite, lytRightSite);
            lytMain.setHeightFull();
            lytMain.setWidthFull();
            lytMain.setId("lyt-main");

            // Special Relationships Window
            ConfirmDialog wdwSpecialRelationships = new ConfirmDialog(ts,
                    String.format(ts.getTranslatedString("module.navigation.actions.management-special-relationships.from")
                            , selectedObject));
            wdwSpecialRelationships.setId("wdw-special-relationships");
            wdwSpecialRelationships.getBtnCancel().setText(ts.getTranslatedString("module.general.messages.close"));
            wdwSpecialRelationships.getBtnConfirm().setVisible(false);
            wdwSpecialRelationships.setWidth("99%");
            wdwSpecialRelationships.setHeight("89%");
            // Free the resources when closing the window
            wdwSpecialRelationships.getBtnCancel().addClickListener(event -> {
                this.newSpecialRelationshipVisualAction.unregisterListener(listenerNewSpecialRelationship);
                this.releaseSpecialRelationshipVisualAction.unregisterListener(listenerReleaseSpecialRelationship);
                this.releaseMultipleSpecialRelationshipVisualAction.unregisterListener(listenerMultipleReleaseSpecialRelationship);
                wdwSpecialRelationships.close();
            });
            wdwSpecialRelationships.addDetachListener(event -> {
                this.newSpecialRelationshipVisualAction.unregisterListener(listenerNewSpecialRelationship);
                this.releaseSpecialRelationshipVisualAction.unregisterListener(listenerReleaseSpecialRelationship);
                this.releaseMultipleSpecialRelationshipVisualAction.unregisterListener(listenerMultipleReleaseSpecialRelationship);
            });
            // Add content to the window
            wdwSpecialRelationships.add(lytTop, lytMain);
            return wdwSpecialRelationships;
        }
        return null;
    }

    private List<String> getRelationshipNames(BusinessEntityManager bem,
                                              TranslationService ts,
                                              BusinessObjectLight businessObject) {
        try {
            HashMap<String, List<BusinessObjectLight>> allRelationships = bem.getSpecialAttributes(
                    businessObject.getClassName(), businessObject.getId());
            return new ArrayList<>(allRelationships.keySet());
        } catch (MetadataObjectNotFoundException | BusinessObjectNotFoundException | InvalidArgumentException ex) {
            new SimpleNotification(ts.getTranslatedString("module.general.messages.error"),
                    ex.getLocalizedMessage(), AbstractNotification.NotificationType.ERROR, ts).open();
            return new ArrayList<>();
        }
    }

    private List<BusinessObjectLight> getTargetObjects(BusinessEntityManager bem,
                                                       TranslationService ts,
                                                       String relationshipName,
                                                       BusinessObjectLight object) {
        try {
            return bem.getSpecialAttribute(object.getClassName(), object.getId(), relationshipName);
        } catch (BusinessObjectNotFoundException | MetadataObjectNotFoundException | InvalidArgumentException ex) {
            new SimpleNotification(ts.getTranslatedString("module.general.messages.error"),
                    ex.getLocalizedMessage(), AbstractNotification.NotificationType.ERROR, ts).open();
            return new ArrayList<>();
        }
    }

    @Override
    public AbstractAction getModuleAction() {
        return manageSpecialRelationshipsAction;
    }

    @Override
    public int getRequiredSelectedObjects() {
        return 1;
    }
}