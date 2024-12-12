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
package org.neotropic.kuwaiba.modules.optional.physcon.widgets;

import com.neotropic.flow.component.mxgraph.MxGraphCell;
import com.neotropic.flow.component.mxgraph.MxGraphEdge;
import com.neotropic.flow.component.mxgraph.MxGraphNode;
import com.vaadin.componentfactory.EnhancedDialog;
import com.vaadin.componentfactory.theme.EnhancedDialogVariant;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.server.StreamRegistration;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.StreamResourceRegistry;
import com.vaadin.flow.server.VaadinSession;
import org.apache.commons.io.IOUtils;
import org.neotropic.kuwaiba.core.apis.integration.modules.ModuleActionParameter;
import org.neotropic.kuwaiba.core.apis.integration.modules.ModuleActionParameterSet;
import org.neotropic.kuwaiba.core.apis.integration.modules.actions.AbstractVisualInventoryAction;
import org.neotropic.kuwaiba.core.apis.integration.modules.actions.AdvancedActionsRegistry;
import org.neotropic.kuwaiba.core.apis.integration.modules.actions.CoreActionsRegistry;
import org.neotropic.kuwaiba.core.apis.integration.modules.explorers.AbstractExplorer;
import org.neotropic.kuwaiba.core.apis.integration.modules.explorers.ExplorerRegistry;
import org.neotropic.kuwaiba.core.apis.integration.modules.views.AbstractObjectRelatedViewWidget;
import org.neotropic.kuwaiba.core.apis.integration.modules.views.AbstractViewNode;
import org.neotropic.kuwaiba.core.apis.integration.modules.views.ViewWidgetRegistry;
import org.neotropic.kuwaiba.core.apis.persistence.application.ActivityLogEntry;
import org.neotropic.kuwaiba.core.apis.persistence.application.ApplicationEntityManager;
import org.neotropic.kuwaiba.core.apis.persistence.application.Session;
import org.neotropic.kuwaiba.core.apis.persistence.application.ViewObject;
import org.neotropic.kuwaiba.core.apis.persistence.application.ViewObjectLight;
import org.neotropic.kuwaiba.core.apis.persistence.business.BusinessEntityManager;
import org.neotropic.kuwaiba.core.apis.persistence.business.BusinessObjectLight;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.InvalidArgumentException;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.InventoryException;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.MetadataObjectNotFoundException;
import org.neotropic.kuwaiba.core.apis.persistence.metadata.ClassMetadata;
import org.neotropic.kuwaiba.core.apis.persistence.metadata.MetadataEntityManager;
import org.neotropic.kuwaiba.core.apis.persistence.util.Constants;
import org.neotropic.kuwaiba.core.i18n.TranslationService;
import org.neotropic.kuwaiba.modules.core.navigation.ObjectOptionsPanel;
import org.neotropic.kuwaiba.modules.optional.physcon.PhysicalConnectionsService;
import org.neotropic.kuwaiba.modules.optional.physcon.actions.NewPhysicalConnectionWizard;
import org.neotropic.kuwaiba.modules.optional.physcon.views.ObjectView;
import org.neotropic.kuwaiba.visualization.api.BusinessObjectViewEdge;
import org.neotropic.kuwaiba.visualization.api.BusinessObjectViewNode;
import org.neotropic.kuwaiba.visualization.api.properties.PropertyValueConverter;
import org.neotropic.kuwaiba.visualization.api.resources.ResourceFactory;
import org.neotropic.util.visual.dialog.ConfirmDialog;
import org.neotropic.util.visual.general.BoldLabel;
import org.neotropic.util.visual.general.FormattedObjectDisplayNameSpan;
import org.neotropic.util.visual.mxgraph.exporters.MxGraphJpgExporter;
import org.neotropic.util.visual.notifications.AbstractNotification;
import org.neotropic.util.visual.notifications.SimpleNotification;
import org.neotropic.util.visual.paperdialog.PaperDialogSearchObject;
import org.neotropic.util.visual.properties.PropertySheet;
import org.neotropic.util.visual.views.util.UtilHtml;
import org.neotropic.util.visual.wizard.Wizard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.neotropic.kuwaiba.modules.core.logging.LoggingService;

/**
 * Toolkit for an {@link ObjectView}.
 * @author Orlando Paz {@literal <orlando.paz@kuwaiba.org>}
 */
@Component
public class ObjectViewWidget extends AbstractObjectRelatedViewWidget<VerticalLayout> {
    /**
     * Utility class that help to load resources like icons and images.
     */
    @Autowired
    private ResourceFactory resourceFactory;
    /**
     * Reference to the translation service.
     */
    @Autowired
    private TranslationService ts;
    /**
     * Reference to the connection service.
     */
    @Autowired
    private PhysicalConnectionsService physicalConnectionsService;
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
     * Reference to the Business Entity Manager.
     */
    @Autowired
    private BusinessEntityManager bem;
    /**
     * Reference to the action registry.
     */
    @Autowired
    private CoreActionsRegistry coreActionRegistry;
    /**
     * All non-general purpose actions provided by other modules than Navigation.
     */
    @Autowired
    private AdvancedActionsRegistry advancedActionsRegistry;
    /**
     * All the object-related views exposed by other modules.
     */
    @Autowired
    private ViewWidgetRegistry viewWidgetRegistry;
    /**
     * All the registered object explorers.
     */
    @Autowired
    private ExplorerRegistry explorerRegistry;
    /**
     * Reference to the Logging Service.
     */
    @Autowired
    private LoggingService log;
    /**
     * reference to the current selected object in the canvas.
     */
    private BusinessObjectLight selectedObject;
    /**
     * property sheet instance for canvas objects.
     */
    private PropertySheet propSheetObjects;
    /**
     * main property sheet instance for mpls properties.
     */
    private PropertySheet propSheetView;
    
    ObjectView objectView;

    AbstractViewNode nodeSideA;
    AbstractViewNode nodeSideB;
    
    private ViewObject currentView;
    
    private BusinessObjectLight businessObject;
    
    private byte [] bgImage;
    
    boolean showingLabels;
    
    boolean labelsColorToggled;

    private VerticalLayout lytObjectOption;

    private ObjectOptionsPanel pnlOptions;

    @Override
    public String getName() {
        return ts.getTranslatedString("module.visualization.object-view-name");
    }

    @Override
    public String getDescription() {
        return ts.getTranslatedString("module.visualization.object-view-description");
    }

    @Override
    public String getVersion() {
        return "1.1";
    }
    
    @Override
    public String getVendor() {
        return "Neotropic SAS <contact@neotropic.co>"; //NOI18N
    }
    
    @Override
    public String appliesTo() {
        return "ViewableObject";
    }
    
    @Override
    public String getTitle() {
        return ts.getTranslatedString("module.navigation.widgets.object-dashboard.object-view");
    }
    
    @Override
    public VerticalLayout build(BusinessObjectLight businessObject) throws InventoryException {
        
        this.businessObject = businessObject;
        List<ViewObjectLight> objectViews = aem.getObjectRelatedViews(businessObject.getId(), businessObject.getClassName(), -1);
        if (!objectViews.isEmpty()) {
             currentView = aem.getObjectRelatedView(businessObject.getId(),
                    businessObject.getClassName(), objectViews.get(0).getId());
        } else 
            currentView = new ViewObject(-1, "", "", "");

        objectView = new ObjectView(businessObject, mem, aem, bem, ts, resourceFactory);
        objectView.getMxGraphCanvas().setComObjectSelected(() -> {
            String objectId = objectView.getMxGraphCanvas().getSelectedCellId();
            if (MxGraphCell.PROPERTY_VERTEX.equals(objectView.getMxGraphCanvas().getSelectedCellType()))
                selectedObject = ((BusinessObjectViewNode) objectView.getAsViewMap().findNode(objectId)).getIdentifier();
            else
                selectedObject = ((BusinessObjectViewEdge) objectView.getAsViewMap().findEdge(objectId)).getIdentifier();

            lytObjectOption.removeAll();
            showObjectOptionsPanel();
        });

        Button btnSaveView = new Button(new Icon(VaadinIcon.DOWNLOAD), ev -> {
            try {
                saveView();
            } catch (InventoryException ex) {
                new SimpleNotification(ts.getTranslatedString("module.general.messages.error"), 
                        ex.getLocalizedMessage(), AbstractNotification.NotificationType.ERROR, ts).open();
            }
        });
        btnSaveView.setClassName("icon-button");
        setButtonTitle(btnSaveView, ts.getTranslatedString("module.general.messages.save"));
        
        objectView.getMxGraphCanvas().getMxGraph().addEdgeCompleteListener(evt -> {
            nodeSideA = objectView.getAsViewMap().getNodes().stream().filter(item -> ((BusinessObjectLight) item.getIdentifier()).getId().equals(evt.getSourceId())).findAny().get();
            nodeSideB = objectView.getAsViewMap().getNodes().stream().filter(item -> ((BusinessObjectLight) item.getIdentifier()).getId().equals(evt.getTargetId())).findAny().get();
            openWizardNewConnection();
        });
        Button btnConnect = new Button(new Icon(VaadinIcon.CONNECT), (selectedItem) -> {
            nodeSideA = null;
            nodeSideB = null;
            openSelectObjectToConnectDlg();
        });
        btnConnect.setClassName("icon-button");
        setButtonTitle(btnConnect, ts.getTranslatedString("module.visualization.object-view-connect"));
        
        Button btnSetBGImage = new Button(VaadinIcon.PICTURE.create(), evt -> {
            openSetBGImageDlg();
        });
        btnSetBGImage.setClassName("icon-button");
        setButtonTitle(btnSetBGImage, ts.getTranslatedString("module.topoman.upload-bgimage"));
        
        PaperDialogSearchObject dlgsearchObject = new PaperDialogSearchObject(ts, objectView.getMxGraphCanvas());
        dlgsearchObject.addSelectObjectListener(evt -> {
            BusinessObjectLight tmpObject = evt.getObject();
             if (tmpObject == null)
                 return;
             MxGraphCell cell;
            
             cell = objectView.getMxGraphCanvas().getNodes().get(tmpObject);
                               
             if (cell != null)
                 cell.selectCell();
        });
        showingLabels = true;
        Button btnShowHideLabels = new Button(VaadinIcon.TEXT_LABEL.create(), evt -> {          
            for (Map.Entry<BusinessObjectLight, MxGraphNode> entry : objectView.getMxGraphCanvas().getNodes().entrySet()) {
                if (showingLabels)
                    entry.getValue().setLabel("");
                else
                    entry.getValue().setLabel(FormattedObjectDisplayNameSpan.getFormattedDisplayName(entry.getKey(), true));
            }
            toggleShowingLabels();
        });
        btnShowHideLabels.setClassName("icon-button");
        setButtonTitle(btnShowHideLabels, ts.getTranslatedString("module.visualization.object-show-hide-labels"));

        labelsColorToggled = false;
        Button btnToggleLabelsColor = new Button(VaadinIcon.ADJUST.create(), evt -> {          
            for (Map.Entry<BusinessObjectLight, MxGraphNode> entry : objectView.getMxGraphCanvas().getNodes().entrySet()) {
                if (labelsColorToggled)
                    entry.getValue().setLabelBackgroundColor("white");
                else 
                    entry.getValue().setLabelBackgroundColor("#15ed32");
            }
            for (Map.Entry<BusinessObjectLight, MxGraphEdge> entry : objectView.getMxGraphCanvas().getEdges().entrySet()) {
                if (labelsColorToggled)
                    entry.getValue().setLabelBackgroundColor("white");
                else 
                    entry.getValue().setLabelBackgroundColor("#15ed32");
            }
            toggleLabelsColor();
        });
        btnToggleLabelsColor.setClassName("icon-button");
        setButtonTitle(btnToggleLabelsColor, ts.getTranslatedString("module.visualization.object-toggle-labels-color"));
        
        Anchor download = new Anchor();
        download.setId("anchorDownload");
        download.getElement().setAttribute("download", true);
        download.setClassName("hidden");
        download.getElement().setAttribute("visibility", "hidden");
        Button btnDownloadAnchor = new Button();
        btnDownloadAnchor.getElement().setAttribute("visibility", "hidden");
        Button btnExportAsJPG = new Button(new Icon(VaadinIcon.FILE_PICTURE), evt -> {
            if (objectView != null) {
                byte [] data = objectView.getAsImage(new MxGraphJpgExporter(log));
                final StreamRegistration regn = VaadinSession.getCurrent().getResourceRegistry().
                               registerResource(createStreamResource("objectView_" + LocalDate.now().toString() +".jpg", data));
                download.setHref(regn.getResourceUri().getPath());
                btnDownloadAnchor.clickInClient();
            }
        });
        btnExportAsJPG.setClassName("icon-button");
        setButtonTitle(btnExportAsJPG, ts.getTranslatedString("module.general.label.export-as-image"));
        download.add(btnDownloadAnchor);
        
        VerticalLayout lytGraph = new VerticalLayout();
        lytGraph.setPadding(false);
        lytGraph.setWidth("58vw");
        lytGraph.setHeight("80vh");
        lytGraph.add(objectView.getAsUiElement());
        lytGraph.setId("lyt-graph");
        Button btnRefresh = new Button(VaadinIcon.REFRESH.create(), evt -> {
            try {
                objectView.clean();
                lytGraph.removeAll();
                lytGraph.add(objectView.getAsUiElement());
            } catch (InvalidArgumentException ex) {
                new SimpleNotification(ts.getTranslatedString("module.general.messages.error"), ex.getLocalizedMessage(),
                        AbstractNotification.NotificationType.ERROR, ts).open();
            }
        });
        btnRefresh.setClassName("icon-button");
        setButtonTitle(btnRefresh, ts.getTranslatedString("module.general.labels.refresh"));

        HorizontalLayout lytTools = new HorizontalLayout(dlgsearchObject, btnSaveView, btnRefresh,
                btnConnect, btnSetBGImage, btnShowHideLabels, btnToggleLabelsColor,
                btnExportAsJPG, download);
        lytTools.setSpacing(false);

        VerticalLayout lytDashboard = new VerticalLayout(lytTools, lytGraph);
        lytDashboard.setWidth("80%");

        lytObjectOption = new VerticalLayout();
        lytObjectOption.setPadding(false);
        lytObjectOption.setSpacing(false);
        lytObjectOption.setMargin(false);
        lytObjectOption.setWidthFull();
        lytObjectOption.setHeightFull();

        HorizontalLayout lytContent = new HorizontalLayout(lytDashboard, lytObjectOption);
        lytContent.setSizeFull();
        lytContent.setPadding(false);
        lytContent.setSpacing(false);
        lytContent.setId("lyt-content");

        VerticalLayout lytMain = new VerticalLayout(lytContent);
        lytMain.setSizeFull();
        lytMain.setSpacing(false);
        lytMain.setPadding(false);
        lytMain.setId("lyt-main");

        return lytMain;
    }

    private void showObjectOptionsPanel() {
        try {
            if (selectedObject != null) {
                pnlOptions = new ObjectOptionsPanel(
                        selectedObject,
                        coreActionRegistry,
                        advancedActionsRegistry,
                        viewWidgetRegistry,
                        explorerRegistry,
                        mem, aem, bem, ts, log
                );
                pnlOptions.setSelectionListener(event -> {
                    try {
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
                    Object lastValue = pnlOptions.lastValue(property.getName());
                    attributes.put(property.getName(), PropertyValueConverter.getAsStringToPersist(property));
                    try {
                        bem.updateObject(selectedObject.getClassName(), selectedObject.getId(), attributes);
                        if (property.getName().equals(Constants.PROPERTY_NAME)) {
                            selectedObject.setName((String) property.getValue());
                            if (MxGraphCell.PROPERTY_VERTEX.equals(objectView.getMxGraphCanvas().getSelectedCellType()))
                                objectView.getMxGraphCanvas().getNodes().get(selectedObject).setLabel(selectedObject.toString());
                            else
                                objectView.getMxGraphCanvas().getEdges().get(selectedObject).setLabel(selectedObject.toString());
                            objectView.getMxGraphCanvas().getMxGraph().refreshGraph();
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
                lytObjectOption.add(pnlOptions.build(UI.getCurrent().getSession().getAttribute(Session.class).getUser()));
            }
        } catch (InventoryException ex) {
            new SimpleNotification(ts.getTranslatedString("module.general.messages.error"),
                    ex.getLocalizedMessage(), AbstractNotification.NotificationType.ERROR, ts).open();
        }
    }

    /**
     * Set the title/tool tip for the given button
     * @param button the button to be set
     * @param title the title to be added
     */
    public void setButtonTitle(Button button, String title) {
        button.getElement().setProperty("title", title);     
    }

    private void openSelectObjectToConnectDlg() {
        EnhancedDialog dlgSelectRootObjects = new EnhancedDialog();
        ComboBox<AbstractViewNode> cmbASideRoot = new ComboBox<>(ts.getTranslatedString("module.visualization.object-view-a-side"), objectView.getAsViewMap().getNodes());
        cmbASideRoot.setAllowCustomValue(false);
        cmbASideRoot.setLabel(ts.getTranslatedString("module.visualization.object-view-select-a-side"));
        cmbASideRoot.setWidthFull();
        ComboBox<AbstractViewNode> cmbBSideRoot = new ComboBox<>(ts.getTranslatedString("module.visualization.object-view-b-side"), objectView.getAsViewMap().getNodes());
        cmbBSideRoot.setAllowCustomValue(false);
        cmbBSideRoot.setLabel(ts.getTranslatedString("module.visualization.object-view-select-b-side"));
        cmbBSideRoot.setWidthFull();
        Button btnNext = new Button(ts.getTranslatedString("module.general.messages.next"));
        Button btnCancel = new Button(ts.getTranslatedString("module.general.messages.cancel"), evt -> {
            dlgSelectRootObjects.close();
        });
        dlgSelectRootObjects.setWidth("50%");
        dlgSelectRootObjects.setModal(true);
        
        btnNext.addClickListener((event) -> {
            if (cmbASideRoot.getValue() == null || cmbBSideRoot.getValue() == null) {
                new SimpleNotification(ts.getTranslatedString("module.general.messages.error"),
                        ts.getTranslatedString("module.visualization.object-view-select-both-sides"),
                        AbstractNotification.NotificationType.ERROR, ts).open();
                return;
            }
            
            if (cmbASideRoot.getValue().equals(cmbBSideRoot.getValue())) {
                new SimpleNotification(ts.getTranslatedString("module.general.messages.error"),
                        ts.getTranslatedString("module.visualization.object-view-selected-nodes-different"),
                        AbstractNotification.NotificationType.ERROR, ts).open();
                return;
            }
            nodeSideA = cmbASideRoot.getValue();
            nodeSideB = cmbBSideRoot.getValue();
            
            dlgSelectRootObjects.close();
            
            openWizardNewConnection();
        });
        
        VerticalLayout lytContent = new VerticalLayout(cmbASideRoot, cmbBSideRoot);
        lytContent.setSpacing(true);
        lytContent.setSizeFull();
        
        dlgSelectRootObjects.setContent(lytContent);
        dlgSelectRootObjects.setFooter(new HorizontalLayout(btnCancel, btnNext));
        dlgSelectRootObjects.setHeader(new BoldLabel(ts.getTranslatedString("module.visualization.object-view-new-connection")));    
        dlgSelectRootObjects.open();
    }

    private void openWizardNewConnection() {
        NewPhysicalConnectionWizard wizard = new NewPhysicalConnectionWizard((BusinessObjectLight) nodeSideA.getIdentifier(),
                (BusinessObjectLight) nodeSideB.getIdentifier(), bem, aem, mem, physicalConnectionsService, resourceFactory, ts, log);
        wizard.setWidthFull();
        wizard.setHeightFull();
        wizard.setId("wizard");
        
        ConfirmDialog dlgWizard = new ConfirmDialog(ts, ts.getTranslatedString("module.visualization.object-view-new-connection"));
        dlgWizard.setModal(true);
        dlgWizard.setDraggable(true);
        dlgWizard.setWidth("80%");
        dlgWizard.setContent(wizard.getLytMainContent());
        dlgWizard.setFooter(wizard.getLytButtons());
        wizard.addEventListener((wizardEvent) -> {
            switch (wizardEvent.getType()) {
                case Wizard.WizardEvent.TYPE_FINAL_STEP:
                    BusinessObjectLight newConnection = (BusinessObjectLight) wizardEvent.getInformation().get("connection");
                    BusinessObjectLight aSide = (BusinessObjectLight) wizardEvent.getInformation().get("rootASide");
                    BusinessObjectLight bSide = (BusinessObjectLight) wizardEvent.getInformation().get("rootBSide");

                    BusinessObjectViewEdge newEdge = new BusinessObjectViewEdge(newConnection);
                    objectView.getAsViewMap().addEdge(newEdge);

                    MxGraphEdge edge = new MxGraphEdge();
                    edge.setUuid(newConnection.getId());
                    edge.setLabel(newConnection.toString());
                    edge.setSource(aSide.getId());
                    edge.setTarget(bSide.getId());

                    try {
                        ClassMetadata theClass = mem.getClass(newConnection.getClassName());
                        edge.setStrokeColor(UtilHtml.toHexString(new Color(theClass.getColor())));
                    } catch (MetadataObjectNotFoundException ex) {
                        //In case of error, use a default black line
                    }
                    objectView.getMxGraphCanvas().addEdge(newConnection, aSide, bSide, edge);
                    new SimpleNotification(ts.getTranslatedString("module.general.messages.success"),
                            String.format(ts.getTranslatedString("module.visualization.object-view-connection-created"),
                                    newConnection.getName()), AbstractNotification.NotificationType.INFO, ts).open();
                case Wizard.WizardEvent.TYPE_CANCEL:
                    dlgWizard.close();
            }
        });
        dlgWizard.open();
    }

    public void saveView() throws InventoryException {
        byte[] viewStructure = objectView.getAsXml();
        
        if (currentView == null || currentView.getId() == -1) {

            long viewId = aem.createObjectRelatedView(businessObject.getId(), businessObject.getClassName(),
                    null, null, "ObjectView", viewStructure, bgImage); //NOI18N
            if (viewId != -1) { //Success
                currentView = aem.getObjectRelatedView(businessObject.getId(),
                    businessObject.getClassName(), viewId);
                new SimpleNotification(ts.getTranslatedString("module.general.messages.success"),
                        ts.getTranslatedString("module.visualization.object-view-view-saved"),
                    AbstractNotification.NotificationType.INFO, ts).open();
            }
            else
                new SimpleNotification(ts.getTranslatedString("module.general.messages.error"),
                        ts.getTranslatedString("module.general.messages.unexpected-error"),
                    AbstractNotification.NotificationType.ERROR, ts).open();

        } else {
            currentView.setStructure(viewStructure);
            currentView.setBackground(bgImage);
            aem.updateObjectRelatedView(businessObject.getId(), businessObject.getClassName(),
                    currentView.getId(), null, null, currentView.getStructure(), bgImage);
            new SimpleNotification(ts.getTranslatedString("module.general.messages.success"),
                    ts.getTranslatedString("module.visualization.object-view-view-saved"),
                    AbstractNotification.NotificationType.INFO, ts).open();
        }
    }
    
    private void openSetBGImageDlg() {
        MemoryBuffer bufferIcon = new MemoryBuffer();
        Upload uploadViewImg = new Upload(bufferIcon);
        uploadViewImg.setWidth("400px");
        ConfirmDialog dlg = new ConfirmDialog(ts, ts.getTranslatedString("module.topoman.upload-bgimage"));
        dlg.setContent(uploadViewImg);
       
        uploadViewImg.setMaxFiles(1);
        uploadViewImg.setDropLabel(new Label(ts.getTranslatedString("module.datamodelman.dropmessage")));
        
        ConfirmDialog dlgImage = new ConfirmDialog(ts, ts.getTranslatedString("module.topoman.upload-bgimage"));

        Image theImage = new Image();
        theImage.setWidth("64px");
        theImage.setHeight("64px");
        byte [] bg = currentView.getBackground() == null ? new byte [0] : currentView.getBackground();
        if (bg.length > 0) {
            StreamResource resource = new StreamResource("bgImage.jpg", () -> new ByteArrayInputStream(bg));
            theImage.setSrc(resource);
        } else {
            theImage.setSrc("img/no_image.png");
        }
        uploadViewImg.addSucceededListener(evt -> {
            StreamResource resource = new StreamResource(evt.getFileName(), () -> bufferIcon.getInputStream());
            theImage.setSrc(resource);         
        });
        
        Button btnClearImage = new Button(ts.getTranslatedString("module.topoman.clear-bgimage"), 
                VaadinIcon.ARROWS_CROSS.create(), evt -> {
            try {
                bgImage = null;
                currentView.setBackground(null);
                theImage.setSrc("img/no_image.png");
                objectView.getMxGraphCanvas().getMxGraph().setBackgroundImage("");
                saveView();
            } catch (InventoryException ex) {
                new SimpleNotification(ts.getTranslatedString("module.general.messages.error"), ex.getLocalizedMessage(),
                        AbstractNotification.NotificationType.ERROR, ts).open();
            }
            objectView.getMxGraphCanvas().getMxGraph().setBackgroundImage("");
        });
        uploadViewImg.addFileRejectedListener(event -> {
            new SimpleNotification(ts.getTranslatedString("module.general.messages.error"), event.getErrorMessage(),
                            AbstractNotification.NotificationType.ERROR, ts).open();
        });
        Label lblCurrentImg = new Label("Current Background Image");
        HorizontalLayout lytImage = new HorizontalLayout(theImage, btnClearImage);
        lytImage.setWidth("400px");
        lytImage.setAlignItems(FlexComponent.Alignment.CENTER);
        VerticalLayout lytContent = new VerticalLayout(lytImage, uploadViewImg);
        dlgImage.setContent(lytContent);
        dlgImage.getBtnConfirm().addClickListener(listener -> {
            try {
                byte[] imageData = IOUtils.toByteArray(bufferIcon.getInputStream());
                if (imageData != null && imageData.length > 0) {
                    this.bgImage = imageData;
                    saveView();
                    StreamResource resource = new StreamResource("bgimage.jpg", () -> new ByteArrayInputStream(bgImage));
                    VaadinSession.getCurrent().getResourceRegistry().registerResource(resource);
                    objectView.getMxGraphCanvas().getMxGraph().setBackgroundImage(StreamResourceRegistry.getURI(resource).toString());
                }
                dlgImage.close();
            } catch (IOException ex) {
                new SimpleNotification(ts.getTranslatedString("module.general.messages.error"), ex.getLocalizedMessage(),
                        AbstractNotification.NotificationType.ERROR, ts).open();
            } catch (InventoryException ex) {
                new SimpleNotification(ts.getTranslatedString("module.general.messages.error"), ex.getLocalizedMessage(),
                        AbstractNotification.NotificationType.ERROR, ts).open();
            }

        });
        dlgImage.open();
    }

    private void toggleShowingLabels() {           
        showingLabels = !showingLabels;
    }
    
    private void toggleLabelsColor() {           
        labelsColorToggled = !labelsColorToggled;
    }
    
    private StreamResource createStreamResource(String name, byte[] ba) {
        return new StreamResource(name, () -> new ByteArrayInputStream(ba));                                
    }
}