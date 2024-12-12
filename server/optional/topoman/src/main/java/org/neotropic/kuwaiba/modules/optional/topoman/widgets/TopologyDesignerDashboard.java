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
package org.neotropic.kuwaiba.modules.optional.topoman.widgets;

import com.neotropic.flow.component.mxgraph.MxConstants;
import com.neotropic.flow.component.mxgraph.MxGraph;
import com.neotropic.flow.component.mxgraph.MxGraphBindedKeyEvent;
import com.neotropic.flow.component.mxgraph.MxGraphCell;
import com.neotropic.flow.component.mxgraph.MxGraphEdge;
import com.neotropic.flow.component.mxgraph.MxGraphNode;
import com.vaadin.componentfactory.theme.EnhancedDialogVariant;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.ThemableLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.server.StreamRegistration;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.StreamResourceRegistry;
import com.vaadin.flow.server.VaadinSession;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
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
import org.neotropic.kuwaiba.core.apis.persistence.application.ApplicationEntityManager;
import org.neotropic.kuwaiba.core.apis.persistence.application.Session;
import org.neotropic.kuwaiba.core.apis.persistence.application.ViewObject;
import org.neotropic.kuwaiba.core.apis.persistence.application.ViewObjectLight;
import org.neotropic.kuwaiba.core.apis.persistence.business.BusinessEntityManager;
import org.neotropic.kuwaiba.core.apis.persistence.business.BusinessObject;
import org.neotropic.kuwaiba.core.apis.persistence.business.BusinessObjectLight;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.ApplicationObjectNotFoundException;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.BusinessObjectNotFoundException;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.InvalidArgumentException;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.InventoryException;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.MetadataObjectNotFoundException;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.NotAuthorizedException;
import org.neotropic.kuwaiba.core.apis.persistence.metadata.MetadataEntityManager;
import org.neotropic.kuwaiba.core.apis.persistence.util.Constants;
import org.neotropic.kuwaiba.core.i18n.TranslationService;
import org.neotropic.kuwaiba.modules.core.navigation.ObjectOptionsPanel;
import org.neotropic.kuwaiba.modules.optional.topoman.TopologyView;
import org.neotropic.kuwaiba.modules.optional.topoman.actions.DeleteTopologyViewVisualAction;
import org.neotropic.kuwaiba.modules.optional.topoman.actions.NewTopologyViewVisualAction;
import org.neotropic.kuwaiba.modules.optional.topoman.tools.AddObjectSelector;
import org.neotropic.kuwaiba.visualization.api.BusinessObjectViewEdge;
import org.neotropic.kuwaiba.visualization.api.BusinessObjectViewNode;
import org.neotropic.kuwaiba.visualization.api.properties.PropertyFactory;
import org.neotropic.kuwaiba.visualization.api.properties.PropertyValueConverter;
import org.neotropic.kuwaiba.visualization.api.resources.ResourceFactory;
import org.neotropic.kuwaiba.visualization.mxgraph.BasicStyleEditor;
import org.neotropic.util.visual.dialog.ConfirmDialog;
import org.neotropic.util.visual.icons.ClassNameIconGenerator;
import org.neotropic.util.visual.mxgraph.exporters.MxGraphJpgExporter;
import org.neotropic.util.visual.notifications.AbstractNotification;
import org.neotropic.util.visual.notifications.SimpleNotification;
import org.neotropic.util.visual.paperdialog.PaperDialogSearchObject;
import org.neotropic.util.visual.properties.AbstractProperty;
import org.neotropic.util.visual.properties.PropertySheet;
import org.neotropic.util.visual.properties.StringProperty;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Properties;
import java.util.UUID;
import org.neotropic.kuwaiba.modules.core.logging.LoggingService;

import static org.neotropic.kuwaiba.modules.optional.topoman.TopologyView.FREE_SHAPE;
import static org.neotropic.kuwaiba.modules.optional.topoman.TopologyView.ICON;

/**
 * Topology designer Main Dashboard.
 * @author Orlando Paz {@literal <Orlando.Paz@kuwaiba.org>}
 */
public class TopologyDesignerDashboard extends VerticalLayout implements PropertySheet.IPropertyValueChangedListener {
    /**
     * Reference to the action registry.
     */
    private final CoreActionsRegistry coreActionRegistry;
    /**
     * All non-general purpose actions provided by other modules than Navigation.
     */
    private final AdvancedActionsRegistry advancedActionsRegistry;
    /**
     * All the object-related views exposed by other modules.
     */
    private final ViewWidgetRegistry viewWidgetRegistry;
    /**
     * All the registered object explorers.
     */
    private final ExplorerRegistry explorerRegistry;
    /**
     * Reference to the translation service.
     */
    private final TranslationService ts;
    /**
     * Reference to the Metadata Entity Manager.
     */
    private final MetadataEntityManager mem;
    /**
     * Reference to the Application Entity Manager.
     */
    private final ApplicationEntityManager aem;
    /**
     * Reference to the Business Entity Manager.
     */
    private final BusinessEntityManager bem;
    /**
     * listener to remove topology view action
     */
    private ActionCompletedListener listenerDeleteAction;
    /**
     * listener to add new topology view Action
     */
    private ActionCompletedListener listenerNewViewAction;
        /**
     * reference of the visual action to remove a topology view
     */
    private final DeleteTopologyViewVisualAction deleteTopologyViewVisualAction;
    /**
     * reference of the visual action to add a topology view
     */
    private final NewTopologyViewVisualAction newTopologyViewVisualAction ;
    /**
     * factory to instance object icons
     */
    private final ResourceFactory resourceFactory;
    /**
     * current view in the canvas
     */
    private ViewObject currentView;
    /**
     * Instance of the main canvas view
     */
    private TopologyView topologyView;
    /**
     * list of topology views
     */
    private List<ViewObjectLight> topologyViews;
    /**
     * Reference to the grid that shows the topology views
     */
    private Grid<ViewObjectLight> tblViews;
    /**
     * Dialog that lists the whole list of the views
     */
    Dialog wdwTopologyViews;
    /**
     * reference to the current selected object in the canvas
     */
    private BusinessObjectLight selectedObject;
    /**
     * main property sheet instance
     */
    PropertySheet propSheetObjects;
    /**
     * Prop Sheet for view properties
     */
    PropertySheet propSheetTopoView;
    /**
     * button to remove views
     */
    private Button btnRemoveView;
    
    private Button btnRemoveObjectFromView;
    
    private Button btnAddCloud;
    
    private Button btnAddRectShape;
    
    private Button btnAddEllipseShape;
    
    private Button btnAddLabel;
    
    private Button btnCopyView;
         
    public static String CLASS_VIEW = "TopologyModuleView";
    
    private AddObjectSelector topomanAddObject;
    
    private BasicStyleEditor styleEditor;
    
    private Accordion accordionProperties;
    
    private boolean openningView;
    
    private Button btnAddObject;  
    
    private PaperDialogSearchObject topomanSearchObject;
    
    private ConfirmDialog dlgAddobject;
    
    private Button btnSetBGImage;
    
    private Button btnExportAsJPG;
    
    private VerticalLayout lytObjectOption;
    
    private ObjectOptionsPanel pnlOptions;
    
    private final LoggingService log;
    
    public ViewObject getCurrentView() {
        return currentView;
    }

    public void setCurrentView(ViewObject currentView) {
        this.currentView = currentView;
        resetDashboard();
    }

    public TopologyDesignerDashboard(CoreActionsRegistry coreActionRegistry, AdvancedActionsRegistry advancedActionsRegistry,
            ViewWidgetRegistry viewWidgetRegistry, ExplorerRegistry explorerRegistry, TranslationService ts, MetadataEntityManager mem, ApplicationEntityManager aem, BusinessEntityManager bem,
            ResourceFactory resourceFactory, NewTopologyViewVisualAction newTopologyViewVisualAction, DeleteTopologyViewVisualAction deleteTopologyViewVisualAction, LoggingService log) {
        super(); 
        this.ts = ts;
        this.mem = mem;
        this.aem = aem;
        this.bem = bem;
        this.log = log;
        this.resourceFactory = resourceFactory;
        this.newTopologyViewVisualAction = newTopologyViewVisualAction;
        this.deleteTopologyViewVisualAction = deleteTopologyViewVisualAction;
        this.coreActionRegistry = coreActionRegistry;
        this.advancedActionsRegistry = advancedActionsRegistry;
        this.viewWidgetRegistry = viewWidgetRegistry;
        this.explorerRegistry = explorerRegistry;
        setSizeFull();
        setPadding(false);
        setSpacing(false);
        setMargin(false);
        this.openningView = false;
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
        createContent();
    }

    @Override
    public void onDetach(DetachEvent ev) {
        this.deleteTopologyViewVisualAction.unregisterListener(listenerDeleteAction);
        this.newTopologyViewVisualAction.unregisterListener(listenerNewViewAction);
        topologyView.getMxgraphCanvas().getMxGraph().removeListeners();
    }

    public void showActionCompletedMessages(ActionCompletedListener.ActionCompletedEvent ev) {
        if (ev.getStatus() == ActionCompletedListener.ActionCompletedEvent.STATUS_SUCCESS)
            new SimpleNotification(ts.getTranslatedString("module.general.messages.success"), ev.getMessage(), 
                            AbstractNotification.NotificationType.INFO, ts).open();
        else
            new SimpleNotification(ts.getTranslatedString("module.general.messages.error"), ev.getMessage(), 
                            AbstractNotification.NotificationType.ERROR, ts).open();
    }

    private void createContent() {
        Button btnOpenView = new Button(new Icon(VaadinIcon.FOLDER_OPEN_O), ev -> openListTopologyViewDialog());
        setButtonTitle(btnOpenView, ts.getTranslatedString("module.topoman.open-topo-view"));
        btnOpenView.setClassName("icon-button");
        Button btnNewView = new Button(new Icon(VaadinIcon.FILE_ADD), ev ->
             this.newTopologyViewVisualAction.getVisualComponent(new ModuleActionParameterSet()).open());
        setButtonTitle(btnNewView, ts.getTranslatedString("module.topoman.actions.new-view.name"));
        btnNewView.setClassName("icon-button");
        btnCopyView = new Button(new Icon(VaadinIcon.COPY_O), ev -> copyCurrentView());
        setButtonTitle(btnCopyView, ts.getTranslatedString("module.topoman.copy-topo-view"));
        btnCopyView.setClassName("icon-button");
        btnRemoveView = new Button(new Icon(VaadinIcon.CLOSE_CIRCLE_O), evt -> {
            if (currentView != null)
                this.deleteTopologyViewVisualAction.getVisualComponent(new ModuleActionParameterSet(
                        new ModuleActionParameter<>("viewId", currentView.getId()))).open();
        });
        btnRemoveView.setClassName("icon-button");
        btnRemoveView.setEnabled(false);
        setButtonTitle(btnRemoveView, ts.getTranslatedString("module.topoman.remove-view"));
        
        Anchor download = new Anchor();
        download.setId("anchorDownload");
        download.getElement().setAttribute("download", true);
        download.setClassName("hidden");
        download.getElement().setAttribute("visibility", "hidden");
        Button btnDownloadAnchor = new Button();
        btnDownloadAnchor.getElement().setAttribute("visibility", "hidden");
        btnExportAsJPG = new Button(new Icon(VaadinIcon.FILE_PICTURE), evt -> {
            if (currentView != null) {
                byte [] data = this.topologyView.getAsImage(new MxGraphJpgExporter(log));
                if (data.length > 0) {
                    String name = currentView.getName().trim().toLowerCase().replaceAll("\\s+", "_");
                    final StreamRegistration regn = VaadinSession.getCurrent().getResourceRegistry().
                            registerResource(createStreamResource(name + "_" +
                                    LocalDate.now().toString() + ".jpg", data));
                    download.setHref(regn.getResourceUri().getPath());
                    btnDownloadAnchor.clickInClient();
                } else {
                    new SimpleNotification(ts.getTranslatedString("module.general.messages.warning"),
                            ts.getTranslatedString("module.topoman.message-warning.add-elements"),
                            AbstractNotification.NotificationType.WARNING, ts).open();
                }
            }
        });
        btnExportAsJPG.setClassName("icon-button");
        setButtonTitle(btnExportAsJPG, ts.getTranslatedString("module.general.label.export-as-image"));
        btnExportAsJPG.setEnabled(false);
        download.add(btnDownloadAnchor);
              
        topologyView = new TopologyView(mem, aem, bem, ts, resourceFactory, log);
        topologyView.getMxgraphCanvas().getMxGraph().addContextMenuItem("sendb", "Send to Back", "", MxGraph.TARGET_CONTEXT_MENU_ITEM_VERTEX);
        topologyView.getMxgraphCanvas().getMxGraph().addContextMenuItem("sendf", "Send to Front", "", MxGraph.TARGET_CONTEXT_MENU_ITEM_VERTEX);
        topologyView.getMxgraphCanvas().getMxGraph().addContextMenuItem("deleteobj", "Delete", "", MxGraph.TARGET_CONTEXT_MENU_ITEM_CELLS);
        topologyView.getMxgraphCanvas().getMxGraph().addContextMenuItemSelectedListener(listener -> {
            if (listener.getItem().equals("sendb")) {
               BusinessObjectLight bol = topologyView.getMxgraphCanvas().getNodes().keySet().stream().filter(item -> item.getId().equals(listener.getCellId())).findAny().get();              
               MxGraphNode node = topologyView.getMxgraphCanvas().getNodes().remove(bol);              
               LinkedHashMap<BusinessObjectLight, MxGraphNode>  newMap = new LinkedHashMap<>(topologyView.getMxgraphCanvas().getNodes().size());
               newMap.put(bol, node);
               newMap.putAll(topologyView.getMxgraphCanvas().getNodes());
               topologyView.getMxgraphCanvas().setNodes(newMap);
               node.orderCell(true);
               saveCurrentView();
            }
            if (listener.getItem().equals("sendf")) {
                BusinessObjectLight bol = topologyView.getMxgraphCanvas().getNodes().keySet().stream().filter(item -> item.getId().equals(listener.getCellId())).findAny().get();              
                MxGraphNode node = topologyView.getMxgraphCanvas().getNodes().remove(bol);  
                topologyView.getMxgraphCanvas().getNodes().put(bol, node);
                node.orderCell(false);
                saveCurrentView();
            }
            if (listener.getItem().equals("deleteobj")) {
                deleteSelectedObject();
            }
        });
        topologyView.getMxgraphCanvas().setComObjectSelected(() -> {
                      
            String objectId = topologyView.getMxgraphCanvas().getSelectedCellId();
            if (MxGraphCell.PROPERTY_VERTEX.equals(topologyView.getMxgraphCanvas().getSelectedCellType())) {
                selectedObject = ((BusinessObjectViewNode) topologyView.getAsViewMap().findNode(objectId)).getIdentifier();             
            } else {
                 selectedObject = ((BusinessObjectViewEdge) topologyView.getAsViewMap().findEdge(objectId)).getIdentifier();            
            }
            updateShapeProperties();
            setGeneralToolsEnabled(true);
            setSelectionToolsEnabled(true);
        });
        topologyView.getMxgraphCanvas().setComObjectUnselected(() -> {
            selectedObject = null;
            updateShapeProperties();
            updatePropertySheetObjects();
            setSelectionToolsEnabled(false);
        });
        topologyView.getMxgraphCanvas().getMxGraph().addGraphChangedListener(eventListener -> {
            if (!openningView)
                saveCurrentView();
        });
        topologyView.getMxgraphCanvas().getMxGraph().addGraphLoadedListener(eventListener -> {
            topologyView.getMxgraphCanvas().getMxGraph().bindKey(37, (MxGraphBindedKeyEvent t) -> {
                if (selectedObject != null && t.getKey().equals(37 + "")) {
                    MxGraphNode node = topologyView.getMxgraphCanvas().findMxGraphNode(selectedObject);
                    node.setX(node.getX() - ARROWS_KEY_DELTA);
                    saveCurrentView();
                }
            });
            topologyView.getMxgraphCanvas().getMxGraph().bindKey(39, (MxGraphBindedKeyEvent t) -> {
                if (selectedObject != null && t.getKey().equals(39 + "")) {
                    MxGraphNode node = topologyView.getMxgraphCanvas().findMxGraphNode(selectedObject);
                    node.setX(node.getX() + ARROWS_KEY_DELTA);
                    saveCurrentView();
                }
            });
            topologyView.getMxgraphCanvas().getMxGraph().bindKey(38, (MxGraphBindedKeyEvent t) -> {
                if (selectedObject != null && t.getKey().equals(38 + "")) {
                    MxGraphNode node = topologyView.getMxgraphCanvas().findMxGraphNode(selectedObject);
                    node.setY(node.getY() - ARROWS_KEY_DELTA);
                    saveCurrentView();
                }
            });
            topologyView.getMxgraphCanvas().getMxGraph().bindKey(40, (MxGraphBindedKeyEvent t) -> {
                if (selectedObject != null && t.getKey().equals(40 + "")) {
                    MxGraphNode node = topologyView.getMxgraphCanvas().findMxGraphNode(selectedObject);
                    node.setY(node.getY() + ARROWS_KEY_DELTA);
                    saveCurrentView();
                }
            });
        });
                
        btnAddObject= new Button(new Icon(VaadinIcon.INSERT), e -> openDlgAddObject());
        btnAddObject.setClassName("icon-button");
        setButtonTitle(btnAddObject, ts.getTranslatedString("module.topoman.add-objects"));
        
        topomanSearchObject = new PaperDialogSearchObject(ts, topologyView.getMxgraphCanvas());
        topomanSearchObject.addSelectObjectListener(event -> {
            BusinessObjectLight tmpObject = event.getObject();
             if (tmpObject == null)
                 return;
             MxGraphCell cell;
             cell = topologyView.getMxgraphCanvas().getNodes().get(tmpObject);
             if (cell != null)
                 cell.selectCell();
            dlgAddobject.close();
        });

        // Add a new object or connection to the canvas
        buildComponentToAddObject();
        initDlgAddObject();

        btnRemoveObjectFromView = new Button(new Icon(VaadinIcon.FILE_REMOVE), e -> deleteSelectedObject());
        btnRemoveObjectFromView.setClassName("icon-button");
        setButtonTitle(btnRemoveObjectFromView, ts.getTranslatedString("module.topoman.remove-object-from-view"));
        btnAddCloud = new Button(new Icon(VaadinIcon.CLOUD), e -> addIconNodeToView());
        btnAddCloud.setClassName("icon-button");
        setButtonTitle(btnAddCloud, ts.getTranslatedString("module.topoman.add-cloud"));
        btnAddRectShape = new Button(new Icon(VaadinIcon.THIN_SQUARE),
                e -> addShapeNodeToView(MxConstants.SHAPE_RECTANGLE));
        btnAddRectShape.setClassName("icon-button");
        setButtonTitle(btnAddRectShape, ts.getTranslatedString("module.topoman.add-rectangle"));
        btnAddEllipseShape = new Button(new Icon(VaadinIcon.CIRCLE_THIN),
                e -> addShapeNodeToView(MxConstants.SHAPE_ELLIPSE));
        btnAddEllipseShape.setClassName("icon-button");
        setButtonTitle(btnAddEllipseShape, ts.getTranslatedString("module.topoman.add-ellipse"));
        btnAddLabel = new Button(new Icon(VaadinIcon.TEXT_LABEL),
                e -> addShapeNodeToView(MxConstants.SHAPE_LABEL));
        btnAddLabel.setClassName("icon-button");
        setButtonTitle(btnAddLabel, ts.getTranslatedString("module.topoman.add-label"));
        
        btnSetBGImage = new Button(VaadinIcon.PICTURE.create(), evt -> {
            openSetBGImageDlg();
        });
        btnSetBGImage.setClassName("icon-button");
        setButtonTitle(btnSetBGImage, ts.getTranslatedString("module.topoman.upload-bgimage"));

        Button btnToogleGrid = new Button(new Icon(VaadinIcon.GRID), evt -> {
             if (topologyView.getMxgraphCanvas().getMxGraph().getGrid() != null &&
                     topologyView.getMxgraphCanvas().getMxGraph().getGrid().isEmpty())
                topologyView.getMxgraphCanvas().getMxGraph().setGrid("images/grid.gif");
             else
                topologyView.getMxgraphCanvas().getMxGraph().setGrid("");
         }); 
        btnToogleGrid.setClassName("icon-button");
        btnToogleGrid.getElement().setProperty("title", ts.getTranslatedString("module.topoman.show-hide-grid"));
              
        Button btnToogleBirdView = new Button(new Icon(VaadinIcon.FLIGHT_LANDING), evt -> {
             if (topologyView.getMxgraphCanvas().getMxGraph().hasOutline() == null ||
                     !topologyView.getMxgraphCanvas().getMxGraph().hasOutline()) {
                topologyView.getMxgraphCanvas().getMxGraph().setHasOutline(true);
                topologyView.getMxgraphCanvas().getMxGraph().setHeight("410px");
             }
             else {
                topologyView.getMxgraphCanvas().getMxGraph().setHasOutline(false);
                topologyView.getMxgraphCanvas().getMxGraph().setHeight("100%");
             }
         }); 
        btnToogleBirdView.setClassName("icon-button");
        btnToogleBirdView.getElement().setProperty("title", ts.getTranslatedString("module.topoman.show-hide-bird-view"));
                    
        HorizontalLayout lytTools = new HorizontalLayout(topomanSearchObject,btnNewView, btnOpenView, btnCopyView, 
                                    btnRemoveView, btnAddObject, btnRemoveObjectFromView, btnSetBGImage, btnToogleGrid, btnToogleBirdView, btnAddCloud,
                                    btnAddRectShape, btnAddEllipseShape, btnAddLabel, btnExportAsJPG, download);
        lytTools.setAlignItems(Alignment.CENTER);
        lytTools.setSpacing(false);
        setGeneralToolsEnabled(false);
        setSelectionToolsEnabled(false);

        configureEdgeCreation();
        initializeActions();
        initializeTblViews();   

        VerticalLayout lytDashboard = new VerticalLayout(lytTools, topologyView.getAsUiElement());
        setMarginPaddingLayout(lytDashboard);
        lytDashboard.setSpacing(false);
        lytDashboard.setWidth("75%");
               
        //properties  
         PropertySheet.IPropertyValueChangedListener listenerPropSheetObjects = new PropertySheet.IPropertyValueChangedListener() {
            @Override
            public void updatePropertyChanged(AbstractProperty property) {
                try {
                    if (selectedObject != null) {
                        HashMap<String, String> attributes = new HashMap<>();
                        attributes.put(property.getName(), PropertyValueConverter.getAsStringToPersist(property));

                        bem.updateObject(selectedObject.getClassName(), selectedObject.getId(), attributes);
                        updatePropertySheetObjects();
                        saveCurrentView();

                        //special case when the name is updated the label must be refreshed in the canvas
                        if (property.getName().equals(Constants.PROPERTY_NAME)) {
                            selectedObject.setName((String) property.getValue());
                            if (MxGraphCell.PROPERTY_VERTEX.equals(topologyView.getMxgraphCanvas().getSelectedCellType()))
                                topologyView.getMxgraphCanvas().getNodes().get(selectedObject).setLabel(selectedObject.toString());
                            else
                                topologyView.getMxgraphCanvas().getEdges().get(selectedObject).setLabel(selectedObject.toString());
                            
                            topologyView.getMxgraphCanvas().getMxGraph().refreshGraph();
                        }

                        new SimpleNotification(ts.getTranslatedString("module.general.messages.success"), ts.getTranslatedString("module.general.messages.property-updated-successfully"), 
                            AbstractNotification.NotificationType.INFO, ts).open();
                    }
                } catch (InventoryException ex) {
                    new SimpleNotification(ts.getTranslatedString("module.general.messages.error"), ex.getLocalizedMessage(), 
                            AbstractNotification.NotificationType.ERROR, ts).open();
                    propSheetObjects.undoLastEdit();
                }
            }
        };
        propSheetObjects = new PropertySheet(ts, new ArrayList<>());
        propSheetObjects.addPropertyValueChangedListener(listenerPropSheetObjects);
        
        PropertySheet.IPropertyValueChangedListener listenerPropSheetTopoView = new PropertySheet.IPropertyValueChangedListener() {
            @Override
            public void updatePropertyChanged(AbstractProperty property) {
                if (currentView != null) {
                    if (property.getName().equals(Constants.PROPERTY_NAME))
                        currentView.setName(property.getAsString());
                    if (property.getName().equals(Constants.PROPERTY_DESCRIPTION))
                        currentView.setDescription(property.getAsString());                  
                    if (saveCurrentView()) {
                        new SimpleNotification(ts.getTranslatedString("module.general.messages.success"), ts.getTranslatedString("module.general.messages.property-updated-successfully"), 
                            AbstractNotification.NotificationType.INFO, ts).open();
                        loadViews();
                    }      
                    else 
                        propSheetTopoView.undoLastEdit();
                }
            }
        };
        propSheetTopoView = new PropertySheet(ts, new ArrayList<>());
        propSheetTopoView.addPropertyValueChangedListener(listenerPropSheetTopoView);
        
        accordionProperties = new Accordion();
        accordionProperties.setWidthFull();
        accordionProperties.addOpenedChangeListener(event -> {
            if(event.getOpenedIndex().isPresent() && pnlOptions != null)
                pnlOptions.accOptions.close();
        });
          
        accordionProperties.add(ts.getTranslatedString("module.topoman.view-properties"), propSheetTopoView);
        accordionProperties.add(ts.getTranslatedString("module.topoman.object-properties"), propSheetObjects);
  
        styleEditor = new BasicStyleEditor(ts);
        styleEditor.updateControlsVisibility(null);
        
        accordionProperties.add(ts.getTranslatedString("module.topoman.shape-properties"), styleEditor);
        accordionProperties.add(ts.getTranslatedString("module.topoman.help"), new Label());
        
        lytObjectOption = new VerticalLayout();
        lytObjectOption.setPadding(false);
        lytObjectOption.setSpacing(false);
        lytObjectOption.setMargin(false);
        lytObjectOption.setWidthFull();
        
        VerticalLayout lytAccordion = new VerticalLayout(accordionProperties, lytObjectOption);
        lytAccordion.setPadding(false);
        lytAccordion.setSpacing(false);
        lytAccordion.setMargin(false);
        lytAccordion.setMinWidth("25%");
        lytAccordion.setWidth("25%");
        
        HorizontalLayout lytMain = new HorizontalLayout(lytDashboard, lytAccordion);
        lytMain.setSizeFull();
        
        setMarginPaddingLayout(lytMain);
        setSpacing(false);
        setMargin(false);
        setPadding(false);
        addAndExpand(lytMain);
        setSizeFull();
    }
    private static final int ARROWS_KEY_DELTA = 1;

    private void updateShapeProperties() {
        lytObjectOption.removeAll();
        if (selectedObject != null) {
            if (selectedObject.getClassName().equals(FREE_SHAPE)) {
                MxGraphNode node = topologyView.getMxgraphCanvas().findMxGraphNode(selectedObject);
                if (node != null) {
                    styleEditor.update(node);
                    accordionProperties.open(2);
                }
            } else try {
                if (!selectedObject.getClassName().equals(ICON)) {
                    if (selectedObject.getClassName().equals("edge")
                            || mem.isSubclassOf(Constants.CLASS_GENERICCONNECTION, selectedObject.getClassName())) {
                        MxGraphEdge edge = topologyView.getMxgraphCanvas().findMxGraphEdge(selectedObject);
                        if (edge != null) {
                            styleEditor.update(edge);
                            if (selectedObject.getClassName().equals("edge")) {
                                accordionProperties.open(2);
                            } else {
                                updatePropertySheetObjects();
                                accordionProperties.open(1);
                                showObjectOptionsPanel();
                            }
                        }
                    } else { // Inventory Node
                        MxGraphNode node = topologyView.getMxgraphCanvas().findMxGraphNode(selectedObject);
                        if (node != null) {
                            updatePropertySheetObjects();
                            accordionProperties.open(1);
                            showObjectOptionsPanel();
                        }
                    }
                }
            } catch (MetadataObjectNotFoundException ex) {
                new SimpleNotification(ts.getTranslatedString("module.general.messages.error"),
                        ex.getLocalizedMessage(),
                        AbstractNotification.NotificationType.ERROR, ts).open();
            }
        } else
            styleEditor.update(null);
    }
    
    private void showObjectOptionsPanel() {
        try {
            pnlOptions = new ObjectOptionsPanel(
                    selectedObject,
                    coreActionRegistry,
                    advancedActionsRegistry,
                    viewWidgetRegistry,
                    explorerRegistry,
                    mem, aem, bem, ts, log
            );
            pnlOptions.accOptions.close();
            pnlOptions.setShowPropertySheet(false);
            pnlOptions.accOptions.addOpenedChangeListener(event -> {
                if (event.getOpenedIndex().isPresent())
                    accordionProperties.close();
            });
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
                            wdwExplorer.setContent(((AbstractExplorer<?>) event.getSource())
                                    .build(selectedObject));
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
            lytObjectOption.add(pnlOptions.build(UI.getCurrent().getSession().getAttribute(Session.class).getUser()));
        } catch (InventoryException ex) {
            new SimpleNotification(ts.getTranslatedString("module.general.messages.error"),
                    ex.getLocalizedMessage(), AbstractNotification.NotificationType.ERROR, ts).open();
        }
    }

    private void setMarginPaddingLayout(ThemableLayout lyt) {
        lyt.setMargin(false);
        lyt.setPadding(false);
    }

    /**
     * resets the topology view instance and creates a empty one
     */
    public void resetDashboard() {
        if (currentView != null) {
            topologyView.clean();
            topologyView.buildFromSavedView(currentView.getStructure());         
        }
    }

    /**
     * Save the current view in the canvas
     */
    private boolean saveCurrentView() {
        try {
            if (currentView != null) {
                aem.updateGeneralView(currentView.getId(), currentView.getName(), currentView.getDescription(), topologyView.getAsXml(), currentView.getBackground());
                currentView.setStructure(topologyView.getAsXml());
                return true;
            }
        } catch (InvalidArgumentException | ApplicationObjectNotFoundException ex) {
            new SimpleNotification(ts.getTranslatedString("module.general.messages.error"), ts.getTranslatedString("module.general.messages.unexpected-error"), 
                            AbstractNotification.NotificationType.ERROR, ts).open();           
        }
        return false;
    }

    /**
     * Removes the selected object in the view.
     */
    private void deleteSelectedObject() {
        if (selectedObject != null) {
            if (MxGraphCell.PROPERTY_VERTEX.equals(topologyView.getMxgraphCanvas().getSelectedCellType())) 
                topologyView.removeNode(selectedObject);              
            else 
                topologyView.removeEdge(selectedObject);
            
            selectedObject = null;
            setSelectionToolsEnabled(false);
        }
    }

    private void initializeTblViews() {
        loadViews();
        tblViews = new Grid<>();
        ListDataProvider<ViewObjectLight> dataProvider = new ListDataProvider<>(topologyViews);
        tblViews.setDataProvider(dataProvider);
        tblViews.addColumn(ViewObjectLight::getName).setFlexGrow(3).setKey(ts.getTranslatedString("module.general.labels.name"));
        tblViews.addItemClickListener(listener -> {          
            openningView = true; // added to ignore change events while we are opening the view
            openTopologyView(listener.getItem());
            MxGraphNode dummyNode = new MxGraphNode(); 
            dummyNode.addCellAddedListener(eventListener ->  {
                openningView = false;
                topologyView.getMxgraphCanvas().getMxGraph().removeNode(dummyNode);
            });
            topologyView.getMxgraphCanvas().getMxGraph().addNode(dummyNode);
        });
        HeaderRow filterRow = tblViews.appendHeaderRow();

        TextField txtViewNameFilter = new TextField(ts.getTranslatedString("module.general.labels.filter"), ts.getTranslatedString("module.general.labels.filter-placeholder"));
        txtViewNameFilter.setValueChangeMode(ValueChangeMode.EAGER);
        txtViewNameFilter.setWidthFull();
        txtViewNameFilter.addValueChangeListener(event -> dataProvider.addFilter(
                project -> StringUtils.containsIgnoreCase(project.getName(),
                        txtViewNameFilter.getValue())));

        filterRow.getCell(tblViews.getColumnByKey(ts.getTranslatedString("module.general.labels.name"))).setComponent(txtViewNameFilter);

    }

    /**
     * loads the given topology view into the view
     * @param item the topology view to be loaded
     */
    private void openTopologyView(ViewObjectLight item) {
        try {
            ViewObject view = aem.getGeneralView(item.getId());
            setCurrentView(view);
            if (wdwTopologyViews != null)
                this.wdwTopologyViews.close();
            if (currentView.getBackground() != null && currentView.getBackground().length > 0) {
                StreamResource resource = new StreamResource("bgimage.jpg", () -> new ByteArrayInputStream(currentView.getBackground()));
                VaadinSession.getCurrent().getResourceRegistry().registerResource(resource);
                topologyView.getMxgraphCanvas().getMxGraph().setBackgroundImage(StreamResourceRegistry.getURI(resource).toString());
            } else 
                topologyView.getMxgraphCanvas().getMxGraph().setBackgroundImage("");
           
            selectedObject = null;
            updatePropertySheetView();
            updatePropertySheetObjects();
            updateShapeProperties();
            accordionProperties.open(0);
            setGeneralToolsEnabled(true);
            this.btnRemoveView.setEnabled(true);
        } catch (ApplicationObjectNotFoundException ex) {
            new SimpleNotification(ts.getTranslatedString("module.general.messages.error"), ex.getLocalizedMessage(), 
                            AbstractNotification.NotificationType.ERROR, ts).open();
        }
    }

    public void loadViews() {
        try {
            topologyViews = aem.getGeneralViews(CLASS_VIEW, -1);
        } catch (InvalidArgumentException | NotAuthorizedException ex) {
            new SimpleNotification(ts.getTranslatedString("module.general.messages.error"), ex.getLocalizedMessage(), 
                            AbstractNotification.NotificationType.ERROR, ts).open();
        }
    }

    /**
     * Initialize the general actions that provides the functionalty to create 
     * and remove topology views 
     */
    private void initializeActions() {
        listenerDeleteAction = (ActionCompletedListener.ActionCompletedEvent ev) -> {
            refreshTopologyViews();
            showActionCompletedMessages(ev);
            setCurrentView(null);  
            selectedObject = null;
            currentView = null;
            topologyView.clean();
            updatePropertySheetObjects();
            updatePropertySheetView();
            setGeneralToolsEnabled(false);
            setSelectionToolsEnabled(false);
        };
        this.deleteTopologyViewVisualAction.registerActionCompletedLister(listenerDeleteAction);
        
        listenerNewViewAction = (ActionCompletedListener.ActionCompletedEvent ev) -> {
            refreshTopologyViews();
            showActionCompletedMessages(ev);
            btnRemoveView.setEnabled(true);
            if (wdwTopologyViews != null)
                wdwTopologyViews.close();
            selectedObject = null;
            updateShapeProperties();
            updatePropertySheetObjects();
            ActionResponse response = ev.getActionResponse();
            try {
                ViewObject newView = aem.getGeneralView((long) response.get("viewId"));
                setCurrentView(newView);
                updatePropertySheetView();
                setGeneralToolsEnabled(true);
            } catch (ApplicationObjectNotFoundException ex) {
                new SimpleNotification(ts.getTranslatedString("module.general.messages.error"), ex.getLocalizedMessage(), 
                            AbstractNotification.NotificationType.ERROR, ts).open();
            }

        };
        this.newTopologyViewVisualAction.registerActionCompletedLister(listenerNewViewAction);        
    }

    private void refreshTopologyViews() {
        loadViews();
        tblViews.getDataProvider().refreshAll();
        tblViews.setItems(topologyViews);
        tblViews.getDataProvider().refreshAll();
    }
    /**
     * open the dialog that shows the list of available views.
     */
    private void openListTopologyViewDialog() {
        if (!topologyViews.isEmpty()) {
            if (wdwTopologyViews == null) {
                wdwTopologyViews = new Dialog();

                Button btnCancel = new Button(ts.getTranslatedString("module.general.messages.cancel"),
                        ev -> wdwTopologyViews.close());
                VerticalLayout lytContent = new VerticalLayout(tblViews, btnCancel);
                lytContent.setAlignItems(Alignment.CENTER);
                wdwTopologyViews.add(lytContent);
                wdwTopologyViews.setWidth("600px");
            }
            wdwTopologyViews.open();
        } else
            new SimpleNotification(ts.getTranslatedString("module.general.messages.information"), ts.getTranslatedString("module.topoman.labels.no-views-created"), 
                            AbstractNotification.NotificationType.INFO, ts).open();
    }
    
    private void updatePropertySheetObjects() {
        try {        
            if (selectedObject != null) {
                BusinessObject aWholeListTypeItem = bem.getObject(selectedObject.getClassName(), selectedObject.getId());
                propSheetObjects.setItems(PropertyFactory.propertiesFromBusinessObject(aWholeListTypeItem, ts, aem, mem, log));
            } else 
                propSheetObjects.clear();
        } catch (InventoryException ex) {
            new SimpleNotification(ts.getTranslatedString("module.general.messages.error"), ex.getLocalizedMessage(), 
                            AbstractNotification.NotificationType.ERROR, ts).open();
        }
    }
    
    private void updatePropertySheetView() {
        if (currentView != null) {
            ArrayList<AbstractProperty> viewProperties = new ArrayList<>();
            viewProperties.add(new StringProperty(Constants.PROPERTY_NAME,
                    Constants.PROPERTY_NAME, "", currentView.getName(), ts));
            viewProperties.add(new StringProperty(Constants.PROPERTY_DESCRIPTION,
                    Constants.PROPERTY_DESCRIPTION, "", currentView.getDescription(), ts));
            propSheetTopoView.setItems(viewProperties);
        } else
            propSheetTopoView.clear();
    }

    @Override
    public void updatePropertyChanged(AbstractProperty property) {
        try {
            if (selectedObject != null) {
                HashMap<String, String> attributes = new HashMap<>();
                attributes.put(property.getName(), PropertyValueConverter.getAsStringToPersist(property));

                bem.updateObject(selectedObject.getClassName(), selectedObject.getId(), attributes);
                saveCurrentView();

                //special case when the name is updated the label must be refreshed in the canvas
                if (property.getName().equals(Constants.PROPERTY_NAME)) {
                    if (MxGraphCell.PROPERTY_VERTEX.equals(topologyView.getMxgraphCanvas().getSelectedCellType()))
                        topologyView.getMxgraphCanvas().getNodes().get(selectedObject).setLabel((String) property.getValue());
                    else
                        topologyView.getMxgraphCanvas().getEdges().get(selectedObject).setLabel((String) property.getValue());
                    
                    topologyView.getMxgraphCanvas().getMxGraph().refreshGraph();
                }

                new SimpleNotification(ts.getTranslatedString("module.general.messages.success"), ts.getTranslatedString("module.general.messages.property-updated-successfully"), 
                            AbstractNotification.NotificationType.INFO, ts).open();
            }
        } catch (InventoryException ex) {
            new SimpleNotification(ts.getTranslatedString("module.general.messages.error"), ex.getLocalizedMessage(), 
                            AbstractNotification.NotificationType.ERROR, ts).open();
        }
    }

    /**
     * add a single node to the view
     *
     * @param node the node to be added
     */
    private void addInventoryNodeToView(BusinessObjectLight node, int x, int y) {
        if (topologyView.getAsViewMap().findNode(node) == null) {
            String uri = resourceFactory.getClassIcon(node.getClassName());
            Properties props = new Properties();
            props.put("imageUrl", uri);
            props.put("x", x);
            props.put("y", y);
            addNode(props, node);
        }
    }
    
    /**
     * add a single icon node to the view
     */
    private void addIconNodeToView() {
        BusinessObjectLight obj = new BusinessObjectLight(ICON, UUID.randomUUID().toString(), ICON);
        Properties props = new Properties();
        props.put("imageUrl", TopologyView.URL_IMG_CLOUD);
        props.put("x", 50);
        props.put("y", 100);
        props.put("w", 64);
        props.put("h", 64);
        props.put("label", ts.getTranslatedString("module.topoman.new-cloud"));
        addNode(props, obj);
    }
    
     /**
     * add a single icon node to the view
     */
    private void addShapeNodeToView(String shape) {
        BusinessObjectLight obj = new BusinessObjectLight(FREE_SHAPE, UUID.randomUUID().toString(), FREE_SHAPE);
        Properties props = new Properties();
        props.put("x", 50);
        props.put("y", 100);
        props.put("w", 80);
        props.put("h", 30);
        props.put("shape", shape);
        if (shape.equals(MxConstants.SHAPE_LABEL))
            props.put("label", "Sample Text");  
        addNode(props, obj);
    }
    
    /**
     * adds a polyline edge to the view. It does not represents a inventory object.
     */
    private void addPolylineEdgeToView(String aSideId, String bSideId) {
       if (aSideId != null && bSideId != null) {
            BusinessObjectLight newEdge = new BusinessObjectLight("edge", UUID.randomUUID().toString(), "New Edge");
            BusinessObjectLight endPointA = new BusinessObjectLight("", aSideId, "");
            BusinessObjectLight endPointB = new BusinessObjectLight("", bSideId, "");
            addEdgeToView(newEdge, endPointA, endPointB, new Properties());
       }
    }
    
    private void addEdgeToView(BusinessObjectLight edge, BusinessObjectLight aSideId, BusinessObjectLight bSideId, Properties props) {
       if (aSideId != null && bSideId != null) {
           props.put("controlPoints", new ArrayList<>());
           props.put("label", edge.toString());
           props.put("source", aSideId.getId());
           props.put("target", bSideId.getId());
           topologyView.addEdge(edge, aSideId, bSideId, props);
       }
    }

    private void addNode(Properties props, BusinessObjectLight node) {      
        topologyView.addNode(node, props);
    }

    private void copyCurrentView() {
        if (currentView != null) {            
            try {
                aem.createGeneralView(CLASS_VIEW, currentView.getName() + " Copy", currentView.getDescription(), currentView.getStructure(), null);
                refreshTopologyViews();
                new SimpleNotification("", String.format(ts.getTranslatedString("module.topoman.copy-created"), currentView.getName() + " Copy"), 
                            AbstractNotification.NotificationType.INFO, ts).open();
            } catch (InvalidArgumentException ex) {
                new SimpleNotification(ts.getTranslatedString("module.general.messages.error"), ex.getLocalizedMessage(), 
                            AbstractNotification.NotificationType.ERROR, ts).open();
            }
        }
    }

    private void configureEdgeCreation() {
        topologyView.getMxgraphCanvas().getMxGraph().addEdgeCompleteListener(evt ->
            addPolylineEdgeToView(evt.getSourceId(), evt.getTargetId()));
    }

    /**
     * Set the title/tool tip for the given button
     * @param button the button to be set
     * @param title the title to be added
     */
    public static void setButtonTitle(Button button, String title) {
        button.getElement().setProperty("title", title);     
    }

    private void setSelectionToolsEnabled(boolean b) {
        btnRemoveObjectFromView.setEnabled(b);
    }

    private void setGeneralToolsEnabled(boolean b) {
        btnRemoveView.setEnabled(b);
        btnCopyView.setEnabled(b);
        btnAddCloud.setEnabled(b);
        btnAddRectShape.setEnabled(b);
        btnAddRectShape.setEnabled(b);
        btnAddEllipseShape.setEnabled(b);
        btnAddLabel.setEnabled(b);
        btnSetBGImage.setEnabled(b);
        topomanAddObject.setEnabled(b);
        topomanSearchObject.setEnabled(b);
        btnAddObject.setEnabled(b);
        btnExportAsJPG.setEnabled(b);
    }

    private void buildComponentToAddObject() {
        topomanAddObject = new AddObjectSelector(ts, bem, topologyView.getMxgraphCanvas(), new ClassNameIconGenerator(resourceFactory));
        topomanAddObject.addNewObjectListener(event -> {
            BusinessObjectLight tmpObject = event.getObject();
            if (tmpObject == null)
                return;
            try {
                if (mem.isSubclassOf(Constants.CLASS_GENERICCONNECTION, tmpObject.getClassName())) {
                    List<BusinessObjectLight> lstEndpointA = bem.getSpecialAttribute(tmpObject.getClassName(),
                            tmpObject.getId(), "endpointA");
                    List<BusinessObjectLight> lstEndpointB = bem.getSpecialAttribute(tmpObject.getClassName(),
                            tmpObject.getId(), "endpointB");
                    if (lstEndpointA.isEmpty() && lstEndpointB.isEmpty()) {
                        List<BusinessObjectLight> listEndpointA = bem.getSpecialAttribute(tmpObject.getClassName(),
                                tmpObject.getId(), "ospLastMileCircuitEndpointA");
                        List<BusinessObjectLight> listEndpointB = bem.getSpecialAttribute(tmpObject.getClassName(),
                                tmpObject.getId(), "ospLastMileCirtuitEndpointB");
                        if (listEndpointA.isEmpty() && listEndpointB.isEmpty()) {
                            new SimpleNotification(ts.getTranslatedString("module.general.messages.warning"),
                                    ts.getTranslatedString("module.topoman.some-endpoint-not-in-view"),
                                    AbstractNotification.NotificationType.WARNING, ts).open();
                        } else
                            addEndPoints(tmpObject, listEndpointA, listEndpointB);
                    } else
                        addEndPoints(tmpObject, lstEndpointA, lstEndpointB);
                } else
                    addInventoryNodeToView(tmpObject, 100, 50);
                topologyView.syncViewMap();
                dlgAddobject.close();
            } catch (InvalidArgumentException | MetadataObjectNotFoundException | BusinessObjectNotFoundException ex) {
                new SimpleNotification(ts.getTranslatedString("module.general.messages.error"),
                        ex.getLocalizedMessage(), AbstractNotification.NotificationType.ERROR, ts).open();
            }
        });
    }

    /**
     * Adds endpoints to a topology view based on the provided connection and two lists of endpoints.
     * If the connection is a subclass of a physical container, it directly adds an edge between
     * the endpoints in the topology view. Otherwise, it attempts to find parent nodes for the endpoints
     * in the topology and adds an edge between them.
     *
     * @param tmpObject     The business object representing the connection between the endpoints.
     * @param listEndpointA A list containing the first endpoint to be connected.
     * @param listEndpointB A list containing the second endpoint to be connected.
     */
    private void addEndPoints(BusinessObjectLight tmpObject,
                              List<BusinessObjectLight> listEndpointA,
                              List<BusinessObjectLight> listEndpointB) {
        try {
            BusinessObjectLight deviceA = null;
            BusinessObjectLight deviceB = null;
            // Check if the tmpObject is a subclass of "GenericPhysicalContainer"
            if (mem.isSubclassOf(Constants.CLASS_GENERICPHYSICALCONTAINER, tmpObject.getClassName())) {
                // Validate and assign deviceA from listEndpointA if it exists and is in the topology view
                if (listEndpointA.get(0) != null  && topologyView.getAsViewMap().findNode(listEndpointA.get(0)) != null)
                    deviceA = listEndpointA.get(0);
                // Validate and assign deviceB from listEndpointB if it exists and is in the topology view
                if (listEndpointB.get(0) != null  && topologyView.getAsViewMap().findNode(listEndpointB.get(0)) != null)
                    deviceB = listEndpointB.get(0);
                // If either deviceA or deviceB is null, show a warning notification
                if (deviceA == null || deviceB == null) {
                    new SimpleNotification(ts.getTranslatedString("module.general.messages.warning"),
                            ts.getTranslatedString("module.topoman.some-endpoint-not-in-view"),
                            AbstractNotification.NotificationType.WARNING, ts).open();
                } else { // If both devices are valid, add an edge between them in the topology view
                    Properties props = new Properties();
                    props.put("controlPoints", new ArrayList<>());
                    props.put("label", tmpObject.toString());
                    topologyView.addEdge(tmpObject, deviceA, deviceB, props);
                }
            } else {
                // Find parents for deviceA and assign if a valid parent is found in the topology view
                List<BusinessObjectLight> parentsA = bem.getParents(listEndpointA.get(0).getClassName(), listEndpointA.get(0).getId());
                if (parentsA != null) {
                    for (BusinessObjectLight parent : parentsA) {
                        if (!parent.getClassName().equals(Constants.DUMMY_ROOT)
                                && (mem.isSubclassOf(Constants.CLASS_GENERICCOMMUNICATIONSELEMENT, parent.getClassName())
                                || mem.isSubclassOf(Constants.CLASS_GENERICBOX, parent.getClassName()))
                                && topologyView.getAsViewMap().findNode(parent) != null) {
                            deviceA = parent;
                            break;
                        }
                    }
                }

                // If no valid deviceA is found, show a warning notification and exit
                if (deviceA == null) {
                    new SimpleNotification(ts.getTranslatedString("module.general.messages.warning"),
                            ts.getTranslatedString("module.topoman.some-endpoint-not-in-view"),
                            AbstractNotification.NotificationType.WARNING, ts).open();
                    return;
                }

                // Find parents for deviceB and assign if a valid parent is found in the topology view
                List<BusinessObjectLight> parentsB = bem.getParents(listEndpointB.get(0).getClassName(), listEndpointB.get(0).getId());
                if (parentsB != null) {
                    for (BusinessObjectLight parent : parentsB) {
                        if (!parent.getClassName().equals(Constants.DUMMY_ROOT)
                                && (mem.isSubclassOf(Constants.CLASS_GENERICCOMMUNICATIONSELEMENT, parent.getClassName())
                                || mem.isSubclassOf(Constants.CLASS_GENERICBOX, parent.getClassName()))
                                && topologyView.getAsViewMap().findNode(parent) != null) {
                            deviceB = parent;
                            break;
                        }
                    }
                }

                // If no valid deviceB is found, show a warning notification and exit
                if (deviceB == null) {
                    new SimpleNotification(ts.getTranslatedString("module.general.messages.warning"),
                            ts.getTranslatedString("module.topoman.some-endpoint-not-in-view"),
                            AbstractNotification.NotificationType.WARNING, ts).open();
                    return;
                }

                // If both devices are valid, add an edge between them in the topology view
                Properties props = new Properties();
                props.put("sourceLabel", listEndpointA.get(0).getName());
                props.put("targetLabel", listEndpointB.get(0).getName());

                addInventoryNodeToView(deviceA, 100, 50);
                addInventoryNodeToView(deviceB, 400, 50);
                addEdgeToView(tmpObject, deviceA, deviceB, props);
            }
        } catch (InvalidArgumentException | MetadataObjectNotFoundException | BusinessObjectNotFoundException ex) {
            new SimpleNotification(ts.getTranslatedString("module.general.messages.error"),
                    ex.getLocalizedMessage(), AbstractNotification.NotificationType.ERROR, ts).open();
        }
    }

    private void initDlgAddObject() {
        dlgAddobject = new ConfirmDialog(ts, ts.getTranslatedString("module.topoman.add-objects"));
        dlgAddobject.setId("wdw-add");
        dlgAddobject.setMinWidth("50%");
        dlgAddobject.setMinHeight("50%");

        VerticalLayout lytContent = new VerticalLayout(topomanAddObject);
        dlgAddobject.add(lytContent);
    }

    private void openDlgAddObject() {
        topomanAddObject.setWidthFull();
        topomanAddObject.getTxtSearch().setValue("");
        topomanAddObject.getTxtSearch().setWidthFull();
        dlgAddobject.open();
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

        Image bgImage = new Image();
        bgImage.setWidth("64px");
        bgImage.setHeight("64px");
        byte [] bg = currentView.getBackground() == null ? new byte [0] : currentView.getBackground();
        if (bg.length > 0) {
            StreamResource resource = new StreamResource("bgImage.jpg", () -> new ByteArrayInputStream(bg));
            bgImage.setSrc(resource);
        } else {
            bgImage.setSrc("img/no_image.png");
        }
        uploadViewImg.addSucceededListener(evt -> {
            StreamResource resource = new StreamResource(evt.getFileName(), bufferIcon::getInputStream);
            bgImage.setSrc(resource);
        });
        
        Button btnClearImage = new Button(ts.getTranslatedString("module.topoman.clear-bgimage"), 
                VaadinIcon.ARROWS_CROSS.create(), evt -> {
                currentView.setBackground(null);
                saveCurrentView();
                topologyView.getMxgraphCanvas().getMxGraph().setBackgroundImage("");
        });
        uploadViewImg.addFileRejectedListener(event ->
            new SimpleNotification(ts.getTranslatedString("module.general.messages.error"), event.getErrorMessage(),
                            AbstractNotification.NotificationType.ERROR, ts).open()
        );
        HorizontalLayout lytImage = new HorizontalLayout(bgImage, btnClearImage);
        lytImage.setWidth("400px");
        lytImage.setAlignItems(Alignment.CENTER);
        VerticalLayout lytContent = new VerticalLayout(lytImage, uploadViewImg);
        dlgImage.setContent(lytContent);
        dlgImage.getBtnConfirm().addClickListener(listener -> {
            try {
                byte[] imageData = IOUtils.toByteArray(bufferIcon.getInputStream());
                if (imageData != null && imageData.length > 0) {
                    currentView.setBackground(imageData);
                    saveCurrentView();

                    StreamResource resource = new StreamResource("bgimage.jpg", () -> new ByteArrayInputStream(currentView.getBackground()));
                    VaadinSession.getCurrent().getResourceRegistry().registerResource(resource);
                    topologyView.getMxgraphCanvas().getMxGraph().setBackgroundImage(StreamResourceRegistry.getURI(resource).toString());
                }
                dlgImage.close();
            } catch (IOException ex) {
                new SimpleNotification(ts.getTranslatedString("module.general.messages.error"),
                        ex.getLocalizedMessage(), AbstractNotification.NotificationType.ERROR, ts).open();
            }
        });
        dlgImage.open();
    }
    
    private StreamResource createStreamResource(String name, byte[] ba) {
        return new StreamResource(name, () -> new ByteArrayInputStream(ba));                                
    }
}