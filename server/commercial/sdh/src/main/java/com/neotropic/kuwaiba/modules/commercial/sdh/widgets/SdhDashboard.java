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

package com.neotropic.kuwaiba.modules.commercial.sdh.widgets;

import com.neotropic.flow.component.mxgraph.MxGraphBindedKeyEvent;
import com.neotropic.flow.component.mxgraph.MxGraphCell;
import com.neotropic.flow.component.mxgraph.MxGraphNode;
import com.neotropic.kuwaiba.modules.commercial.sdh.SdhService;
import com.neotropic.kuwaiba.modules.commercial.sdh.SdhView;
import com.neotropic.kuwaiba.modules.commercial.sdh.actions.DeleteSdhContainerLinkVisualAction;
import com.neotropic.kuwaiba.modules.commercial.sdh.actions.DeleteSdhTransportLinkVisualAction;
import com.neotropic.kuwaiba.modules.commercial.sdh.actions.DeleteSdhTributaryLinkVisualAction;
import com.neotropic.kuwaiba.modules.commercial.sdh.actions.DeleteSdhViewVisualAction;
import com.neotropic.kuwaiba.modules.commercial.sdh.actions.NewSdhViewVisualAction;
import com.neotropic.kuwaiba.modules.commercial.sdh.tools.SdhTools;
import com.neotropic.kuwaiba.modules.commercial.sdh.wizard.NewSDHContainerLinkWizard;
import com.neotropic.kuwaiba.modules.commercial.sdh.wizard.NewSDHTransportLinkWizard;
import com.neotropic.kuwaiba.modules.commercial.sdh.wizard.NewSDHTributaryLinkWizard;
import com.vaadin.componentfactory.EnhancedDialog;
import com.vaadin.componentfactory.theme.EnhancedDialogVariant;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.ThemableLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.server.StreamResourceRegistry;
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
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.OperationNotPermittedException;
import org.neotropic.kuwaiba.core.apis.persistence.metadata.MetadataEntityManager;
import org.neotropic.kuwaiba.core.apis.persistence.util.Constants;
import org.neotropic.kuwaiba.core.i18n.TranslationService;
import org.neotropic.kuwaiba.modules.core.navigation.ObjectOptionsPanel;
import org.neotropic.kuwaiba.visualization.api.BusinessObjectViewEdge;
import org.neotropic.kuwaiba.visualization.api.BusinessObjectViewNode;
import org.neotropic.kuwaiba.visualization.api.properties.PropertyFactory;
import org.neotropic.kuwaiba.visualization.api.properties.PropertyValueConverter;
import org.neotropic.kuwaiba.visualization.api.resources.ResourceFactory;
import org.neotropic.util.visual.dialog.ConfirmDialog;
import org.neotropic.util.visual.general.BoldLabel;
import org.neotropic.util.visual.notifications.AbstractNotification;
import org.neotropic.util.visual.notifications.SimpleNotification;
import org.neotropic.util.visual.properties.AbstractProperty;
import org.neotropic.util.visual.properties.PropertySheet;
import org.neotropic.util.visual.properties.StringProperty;
import org.neotropic.util.visual.wizard.Wizard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import static com.neotropic.kuwaiba.modules.commercial.sdh.SdhModule.RELATIONSHIP_SDHTLENDPOINTA;
import static com.neotropic.kuwaiba.modules.commercial.sdh.SdhModule.RELATIONSHIP_SDHTLENDPOINTB;
import org.neotropic.kuwaiba.modules.core.logging.LoggingService;

/**
 * SDH Main Dashboard.
 * @author Orlando Paz {@literal <Orlando.Paz@kuwaiba.org>}
 */
public class SdhDashboard extends VerticalLayout implements ActionCompletedListener {
    
    /**
     * The parameter of the business object.
     */
    public static String PARAM_BUSINESS_OBJECT = "businessObject"; //NOI18N
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
     * listener to remove Sdh view action
     */
    private ActionCompletedListener listenerDeleteAction;
    /**
     * listener to add new view Action
     */
    private ActionCompletedListener listenerNewViewAction;
    /**
     * reference of the visual action to remove a Sdh view
     */
    private final DeleteSdhViewVisualAction deleteSdhViewVisualAction;
    /**
     * reference of the visual action to remove a Sdh transport link
     */
    private final DeleteSdhTransportLinkVisualAction deleteSdhTransportLinkVisualAction;
    /**
     * reference of the visual action to remove a Sdh tributary link
     */
    private final DeleteSdhTributaryLinkVisualAction deleteSdhTributaryLinkVisualAction;
    /**
     * reference of the visual action to remove a Sdh container link
     */
    private final DeleteSdhContainerLinkVisualAction deleteSdhContainerLinkVisualAction;
    /**
     * reference of the visual action to add a Sdh view
     */
    private final NewSdhViewVisualAction newSdhViewVisualAction;
    /**
     * factory to instance object icons
     */
    private final ResourceFactory resourceFactory;
    /**
     * service to persistence actions
     */
    private final SdhService sdhService;
     /**
     * source Equipment in create new connection dialog
     */   
    private BusinessObjectLight selectedSourceEquipment;
    /**
     * target Equipment in create new connection dialog
     */
    private BusinessObjectLight selectedTargetEquipment;
    /**
     * current view in the canvas
     */
    private ViewObject currentView;
    /**
     * canvas toolbar
     */
    private SdhTools sdhTools;
    /**
     * Instance of the main canvas view
     */
    private SdhView sdhView;
    /**
     * list of sdh views
     */
    private List<ViewObjectLight> sdhViews;
    /**
     * Reference to the grid that shows the sdh views 
     */
    private Grid<ViewObjectLight> tblViews;
    /**
     * Dialog that lists the whole list of the views
     */
    private ConfirmDialog wdwSdhViews;
    /**
     * reference to the current selected object in the canvas
     */
    private BusinessObjectLight selectedObject;
    /**
     *  property sheet instance for canvas objects
     */
    private PropertySheet propSheetObjects;
     /**
     * main property sheet instance for sdh properties
     */
    private PropertySheet propSheetSdh;
    
    private boolean blankView;
    
    public final String CONNECTION_TRANSPORTLINK;
    
    public final String CONNECTION_CONTAINERLINK;
    
    public final String CONNECTION_TRIBUTARYLINK;
    
    private VerticalLayout lytObjectOption;
    
    private ObjectOptionsPanel pnlOptions;
    
    private Accordion accordionProperties;
    /**
     * Reference to the Kuwaiba Logging Service
     */
    private final LoggingService log;

    public ViewObject getCurrentView() {
        return currentView;
    }

    public void setCurrentView(ViewObject currentView) {
        this.currentView = currentView;
        resetDashboard();
    }

    public SdhTools getSdhTools() {
        return sdhTools;
    }

    public void setSdhTools(SdhTools sdhTools) {
        this.sdhTools = sdhTools;
    }  
    
    public SdhDashboard(CoreActionsRegistry coreActionRegistry, AdvancedActionsRegistry advancedActionsRegistry,
            ViewWidgetRegistry viewWidgetRegistry, ExplorerRegistry explorerRegistry,
            TranslationService ts, MetadataEntityManager mem, ApplicationEntityManager aem, BusinessEntityManager bem,
            ResourceFactory resourceFactory, SdhService sdhService, DeleteSdhViewVisualAction deleteSDHViewVisualAction,
            NewSdhViewVisualAction newSDHViewVisualAction,
            DeleteSdhTransportLinkVisualAction deleteSdhTransportLinkVisualAction,
            DeleteSdhTributaryLinkVisualAction deleteSdhTributaryLinkVisualAction,
            DeleteSdhContainerLinkVisualAction deleteSdhContainerLinkVisualAction,
            LoggingService log) {
        super();
        this.ts = ts;
        this.mem = mem;
        this.aem = aem;
        this.bem = bem;
        this.resourceFactory = resourceFactory;
        this.sdhService = sdhService;
        this.newSdhViewVisualAction = newSDHViewVisualAction;
        this.deleteSdhViewVisualAction = deleteSDHViewVisualAction;
        this.deleteSdhTransportLinkVisualAction = deleteSdhTransportLinkVisualAction;
        this.deleteSdhTributaryLinkVisualAction = deleteSdhTributaryLinkVisualAction;
        this.deleteSdhContainerLinkVisualAction = deleteSdhContainerLinkVisualAction;
        this.coreActionRegistry = coreActionRegistry;
        this.advancedActionsRegistry = advancedActionsRegistry;
        this.viewWidgetRegistry = viewWidgetRegistry;
        this.explorerRegistry = explorerRegistry;
        this.log = log;
        setSizeFull();
        setPadding(false);
        setMargin(false);
        this.blankView = false;
        CONNECTION_TRANSPORTLINK = ts.getTranslatedString("module.sdh.transport-link-label");
        CONNECTION_CONTAINERLINK = ts.getTranslatedString("module.sdh.container-link-label");
        CONNECTION_TRIBUTARYLINK = ts.getTranslatedString("module.sdh.tributary-link-label");
    }        

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent); 
        createContent();
        
        this.deleteSdhTransportLinkVisualAction.unregisterListener(this);
        this.deleteSdhTransportLinkVisualAction.registerActionCompletedLister(this);
        this.deleteSdhTributaryLinkVisualAction.unregisterListener(this);
        this.deleteSdhTributaryLinkVisualAction.registerActionCompletedLister(this);
        this.deleteSdhContainerLinkVisualAction.unregisterListener(this);
        this.deleteSdhContainerLinkVisualAction.registerActionCompletedLister(this);
    }
   
    @Override
    public void onDetach(DetachEvent ev) {
        this.deleteSdhViewVisualAction.unregisterListener(listenerDeleteAction);
        this.newSdhViewVisualAction.unregisterListener(listenerNewViewAction);
        this.deleteSdhTransportLinkVisualAction.unregisterListener(this);
        this.deleteSdhTributaryLinkVisualAction.unregisterListener(this);
        this.deleteSdhContainerLinkVisualAction.unregisterListener(this);
        sdhView.getMxgraphCanvas().getMxGraph().removeListeners();
    }
    
    public void showActionCompledMessages(ActionCompletedListener.ActionCompletedEvent ev) {
        if (ev.getStatus() == ActionCompletedListener.ActionCompletedEvent.STATUS_SUCCESS) 
            new SimpleNotification(ts.getTranslatedString("module.general.messages.success"), ev.getMessage(), 
                            AbstractNotification.NotificationType.INFO, ts).open();
        else
            new SimpleNotification(ts.getTranslatedString("module.general.messages.error"), ev.getMessage(), 
                            AbstractNotification.NotificationType.ERROR, ts).open();
    }
       
    private void createContent() {

        sdhView = new SdhView(mem, aem, bem, ts, resourceFactory, log);
        sdhView.getMxgraphCanvas().setComObjectSelected(() -> {
            String objectId = sdhView.getMxgraphCanvas().getSelectedCellId();
            if (MxGraphCell.PROPERTY_VERTEX.equals(sdhView.getMxgraphCanvas().getSelectedCellType()))
                selectedObject = ((BusinessObjectViewNode) sdhView.getAsViewMap().findNode(objectId)).getIdentifier();
            else
                selectedObject = ((BusinessObjectViewEdge) sdhView.getAsViewMap().findEdge(objectId)).getIdentifier();
            
            lytObjectOption.removeAll();
            showObjectOptionsPanel();
            updatePropertySheetObjects();
            sdhTools.setGeneralToolsEnabled(true);
            sdhTools.setSelectionToolsEnabled(true);
            accordionProperties.close();
            accordionProperties.open(1);
        });
        sdhView.getMxgraphCanvas().setComObjectUnselected(() -> {
            lytObjectOption.removeAll();
            selectedObject = null;
            updatePropertySheetObjects();
            sdhTools.setSelectionToolsEnabled(false);
        });
        sdhView.getMxgraphCanvas().setComObjectDeleted(this::openConfirmDialogDeleteObject);
        sdhView.getMxgraphCanvas().getMxGraph().addGraphChangedListener(eventListener -> {
            if (!blankView)
                saveCurrentView();
        });
        sdhView.getMxgraphCanvas().getMxGraph().addGraphLoadedListener(eventListener -> {
            sdhView.getMxgraphCanvas().getMxGraph().bindKey(37, (MxGraphBindedKeyEvent t) -> {
                if (selectedObject != null && t.getKey().equals(37 + "")) {
                    MxGraphNode node = sdhView.getMxgraphCanvas().findMxGraphNode(selectedObject);
                    node.setX(node.getX() - ARROWS_KEY_DELTA);
                    saveCurrentView();
                }
            });
            sdhView.getMxgraphCanvas().getMxGraph().bindKey(39, (MxGraphBindedKeyEvent t) -> {
                if (selectedObject != null && t.getKey().equals(39 + "")) {
                    MxGraphNode node = sdhView.getMxgraphCanvas().findMxGraphNode(selectedObject);
                    node.setX(node.getX() + ARROWS_KEY_DELTA);
                    saveCurrentView();
                }
            });
            sdhView.getMxgraphCanvas().getMxGraph().bindKey(38, (MxGraphBindedKeyEvent t) -> {
                if (selectedObject != null && t.getKey().equals(38 + "")) {
                    MxGraphNode node = sdhView.getMxgraphCanvas().findMxGraphNode(selectedObject);
                    node.setY(node.getY() - ARROWS_KEY_DELTA);
                    saveCurrentView();
                }
            });
            sdhView.getMxgraphCanvas().getMxGraph().bindKey(40, (MxGraphBindedKeyEvent t) -> {
                if (selectedObject != null && t.getKey().equals(40 + "")) {
                    MxGraphNode node = sdhView.getMxgraphCanvas().findMxGraphNode(selectedObject);
                    node.setY(node.getY() + ARROWS_KEY_DELTA);
                    saveCurrentView();
                }
            });
        });

        sdhTools = new SdhTools(sdhView, bem, ts, log);

        sdhTools.getBtnOpenView().addClickListener(ev -> openListSDHViewDialog());
        sdhTools.getBtnNewView().addClickListener(ev -> this.newSdhViewVisualAction.getVisualComponent(new ModuleActionParameterSet()).open());
        sdhTools.getBtnRemoveView().addClickListener(evt -> {
            if (currentView != null)
                this.deleteSdhViewVisualAction.getVisualComponent(new ModuleActionParameterSet(
                        new ModuleActionParameter<>("viewId", currentView.getId()))).open();
        });

        sdhTools.addNewObjectListener(event -> {
            BusinessObjectLight tmpObject = event.getObject();
             if (tmpObject == null)
                return;
            try {

                if (mem.isSubclassOf("GenericSDHTransportLink", tmpObject.getClassName())) {
                    HashMap<String, List<BusinessObjectLight>> devices = bem.getSpecialAttributes(tmpObject.getClassName(), tmpObject.getId(),
                            RELATIONSHIP_SDHTLENDPOINTA, RELATIONSHIP_SDHTLENDPOINTB);
                    BusinessObject communicationsEquipmentA = null, communicationsEquipmentB = null;
                    if (devices.containsKey(RELATIONSHIP_SDHTLENDPOINTA) && !devices.get(RELATIONSHIP_SDHTLENDPOINTA).isEmpty()) {
                        BusinessObjectLight sideA = devices.get(RELATIONSHIP_SDHTLENDPOINTA).get(0);
                        communicationsEquipmentA = bem.getFirstParentOfClass(sideA.getClassName(), sideA.getId(),
                                Constants.CLASS_GENERICCOMMUNICATIONSELEMENT);
                        if (communicationsEquipmentA == null)
                            throw new BusinessObjectNotFoundException(
                                    String.format(ts.getTranslatedString("module.sdh.error.port-invalid"),
                                            sideA.getClassName(), sideA.getId()));
                    }
                    if (devices.containsKey(RELATIONSHIP_SDHTLENDPOINTB) && !devices.get(RELATIONSHIP_SDHTLENDPOINTB).isEmpty()) {
                        BusinessObjectLight sideB = devices.get(RELATIONSHIP_SDHTLENDPOINTB).get(0);

                        communicationsEquipmentB = bem.getFirstParentOfClass(sideB.getClassName(), sideB.getId(),
                                Constants.CLASS_GENERICCOMMUNICATIONSELEMENT);
                        if (communicationsEquipmentB == null)
                            throw new BusinessObjectNotFoundException(
                                    String.format(ts.getTranslatedString("module.sdh.error.port-invalid"),
                                            sideB.getClassName(), sideB.getId()));
                    }
                    if (communicationsEquipmentA != null && communicationsEquipmentB != null) {
                        addNodeToView(communicationsEquipmentA, 100);
                        addNodeToView(communicationsEquipmentB, 400);
                        addEdgeToView(tmpObject, communicationsEquipmentA, communicationsEquipmentB);
                    }
                } else
                    addNodeToView(tmpObject, 100);
            } catch (MetadataObjectNotFoundException | BusinessObjectNotFoundException | 
                    InvalidArgumentException | ApplicationObjectNotFoundException ex) {
                new SimpleNotification(ts.getTranslatedString("module.general.messages.error"),
                        ex.getLocalizedMessage(), AbstractNotification.NotificationType.ERROR, ts).open();
            }
        });
        sdhTools.addSelectObjectListener(event -> {
            BusinessObjectLight tmpObject = event.getObject();
             if (tmpObject == null)
                return;
            MxGraphCell cell;
             if(tmpObject.getClassName().equals(Constants.CLASS_MPLSLINK))
                cell = sdhView.getMxgraphCanvas().getEdges().get(tmpObject);
             else
                cell = sdhView.getMxgraphCanvas().getNodes().get(tmpObject);
             if (cell != null)
                cell.selectCell();

        });

        sdhTools.addNewConnectionListener(event -> {
            selectedSourceEquipment = null;
            selectedTargetEquipment = null;
            openDlgSelectConnectionType();
        });
        sdhTools.addSaveViewListener(event -> saveCurrentView());
        sdhTools.addDeleteObjectListener(event -> deleteSelectedObject(false));
        sdhTools.addDeleteObjectPermanentlyObjectListener(event -> openConfirmDialogDeleteObject());
        sdhTools.setGeneralToolsEnabled(false);

        initializeActions();
        initializeTblViews();

        VerticalLayout lytDashboard = new VerticalLayout(sdhTools, sdhView.getAsUiElement());
        lytDashboard.setWidth("70%");
        //prop sheet section
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

                        //special case when the name is updated the label must be refreshed in the sdhView.getMxgraphCanvas()
                        if (property.getName().equals(Constants.PROPERTY_NAME)) {
                            selectedObject.setName((String) property.getValue());
                            if (MxGraphCell.PROPERTY_VERTEX.equals(sdhView.getMxgraphCanvas().getSelectedCellType()))
                                sdhView.getMxgraphCanvas().getNodes().get(selectedObject).setLabel(selectedObject.toString());
                            else
                                sdhView.getMxgraphCanvas().getEdges().get(selectedObject).setLabel(selectedObject.toString());
                            sdhView.getMxgraphCanvas().getMxGraph().refreshGraph();
                        }

                        new SimpleNotification(ts.getTranslatedString("module.general.messages.success"),
                                ts.getTranslatedString("module.general.messages.property-updated-successfully"),
                                AbstractNotification.NotificationType.INFO, ts).open();
                    }
                } catch (InventoryException ex) {
                    new SimpleNotification(ts.getTranslatedString("module.general.messages.error"),
                            ex.getLocalizedMessage(), AbstractNotification.NotificationType.ERROR, ts).open();
                }
            }
        };
        propSheetObjects = new PropertySheet(ts, new ArrayList<>());
        propSheetObjects.addPropertyValueChangedListener(listenerPropSheetObjects);

        PropertySheet.IPropertyValueChangedListener listenerPropSheetSDH = new PropertySheet.IPropertyValueChangedListener() {
            @Override
            public void updatePropertyChanged(AbstractProperty property) {
                if (currentView != null) {

                    if (property.getName().equals(Constants.PROPERTY_NAME))
                        currentView.setName(property.getAsString());
                    if (property.getName().equals(Constants.PROPERTY_DESCRIPTION))
                        currentView.setDescription(property.getAsString());

                    saveCurrentView();
                    loadViews();
                    new SimpleNotification(ts.getTranslatedString("module.general.messages.success"), ts.getTranslatedString("module.general.messages.property-updated-successfully"),
                            AbstractNotification.NotificationType.INFO, ts).open();
                }
            }
        };
        propSheetSdh = new PropertySheet(ts, new ArrayList<>());
        propSheetSdh.addPropertyValueChangedListener(listenerPropSheetSDH);

        Label lblHintControlPoints = new Label(ts.getTranslatedString("module.mpls.hint-create-delete-control-point"));
        lblHintControlPoints.setClassName("hintMplsView");
        VerticalLayout lytFooterView = new VerticalLayout(lblHintControlPoints);
        setMarginPaddingLayout(lytFooterView);
        lytFooterView.setSpacing(false);
        
        accordionProperties = new Accordion();
        accordionProperties.add(ts.getTranslatedString("module.mpls.view-properties"), propSheetSdh);
        accordionProperties.add(ts.getTranslatedString("module.mpls.object-properties"), propSheetObjects);
        accordionProperties.add(ts.getTranslatedString("module.mpls.help"), lytFooterView);
        accordionProperties.add(ts.getTranslatedString("module.mpls.context"), new Label());
        accordionProperties.addOpenedChangeListener(event -> {
            if(event.getOpenedIndex().isPresent() && pnlOptions != null)
                pnlOptions.accOptions.close();
        });
        accordionProperties.setWidthFull();
        accordionProperties.close();
        
        lytObjectOption = new VerticalLayout();
        lytObjectOption.setPadding(false);
        lytObjectOption.setSpacing(false);
        lytObjectOption.setMargin(false);
        lytObjectOption.setWidthFull();

        VerticalLayout lytSheet = new VerticalLayout(accordionProperties, lytObjectOption);
        lytSheet.setSpacing(false);
        setMarginPaddingLayout(lytSheet);
        lytSheet.setWidth("30%");
        lytSheet.addClassName("overflow-y-scroll");

        HorizontalLayout lytMain = new HorizontalLayout(lytDashboard, lytSheet);
        lytMain.setSizeFull();
        setMarginPaddingLayout(lytMain);

        addAndExpand(lytMain);
        setSizeFull();
        configureEdgeCreation();
    }
    private static final int ARROWS_KEY_DELTA = 1;

    private void setMarginPaddingLayout(ThemableLayout lytViewInfo) {
        lytViewInfo.setMargin(false);
        lytViewInfo.setPadding(false);
    }
    
    private void configureEdgeCreation() {
        sdhView.getMxgraphCanvas().getMxGraph().addEdgeCompleteListener(evt -> {
            selectedSourceEquipment = ((BusinessObjectViewNode) sdhView.getAsViewMap().findNode(evt.getSourceId())).getIdentifier();
            selectedTargetEquipment = ((BusinessObjectViewNode) sdhView.getAsViewMap().findNode(evt.getTargetId())).getIdentifier();          
            openDlgSelectConnectionType();
        });
    }
    
    /**
     * resets the sdh view instance and creates an empty one
     */
    public void resetDashboard() {
        sdhView.clean();
        if (currentView != null)
            sdhView.buildFromSavedView(currentView.getStructure());
    }
      
    /**
     * Save the current view in the canvas.
     */
    private void saveCurrentView() {
        try {
            if (currentView != null) {
                aem.updateGeneralView(currentView.getId(), currentView.getName(), currentView.getDescription(), sdhView.getAsXml(), null);
                currentView.setStructure(sdhView.getAsXml());
            }
        } catch (InvalidArgumentException | ApplicationObjectNotFoundException ex) {
            new SimpleNotification(ts.getTranslatedString("module.general.messages.error"),
                    ts.getTranslatedString("module.general.messages.unexpected-error"),
                    AbstractNotification.NotificationType.ERROR, ts).open();
        }
          
    }
    
    /**
     * Creates a confirm dialog to ask for the remove object action
     */
    private void openConfirmDialogDeleteObject() {
        ConfirmDialog dlgConfirmDelete = new ConfirmDialog(ts, ts.getTranslatedString("module.general.labels.confirmation"),
                ts.getTranslatedString("module.sdh.delete-permanently-message"));
        dlgConfirmDelete.open();
        dlgConfirmDelete.getBtnConfirm().addClickListener(evt -> {
            deleteSelectedObject(true);
//            saveCurrentView();
            dlgConfirmDelete.close();           
        });
    }

    /**
     * removes the selected object in the view
     * @param deletePermanently Boolean that if true specifies that the object
     * is permanently deleted or false if it was only deleted from the view. 
     */
    private void deleteSelectedObject(boolean deletePermanently) {            
        if( selectedObject != null) {
            try {
                if (MxGraphCell.PROPERTY_VERTEX.equals(sdhView.getMxgraphCanvas().getSelectedCellType())) {
                    if (deletePermanently)
                        bem.deleteObject(selectedObject.getClassName(), selectedObject.getId(), false);
                    sdhView.removeNode(selectedObject);                                 
                    new SimpleNotification(ts.getTranslatedString("module.general.messages.success"), ts.getTranslatedString("module.sdh.object-deleted"), 
                            AbstractNotification.NotificationType.INFO, ts).open();
                } else {
                    if (deletePermanently) 
                        sdhService.deleteSDHTransportLink(selectedObject.getClassName(), selectedObject.getId(), true);       
                    
                    sdhView.removeEdge(selectedObject);
                    new SimpleNotification(ts.getTranslatedString("module.general.messages.success"), ts.getTranslatedString("module.sdh.sdh-link-deleted"), 
                            AbstractNotification.NotificationType.INFO, ts).open();
                }
                if (deletePermanently)
                        saveCurrentView();     
                selectedObject = null;              
                updatePropertySheetObjects();
            } catch (BusinessObjectNotFoundException | InvalidArgumentException | MetadataObjectNotFoundException
                    | OperationNotPermittedException ex) {
                new SimpleNotification(ts.getTranslatedString("module.general.messages.error"), ex.getMessage(),
                        AbstractNotification.NotificationType.ERROR, ts).open();
            } catch (InventoryException ex) {
                new SimpleNotification(ts.getTranslatedString("module.general.messages.error"), ex.getMessage(),
                        AbstractNotification.NotificationType.ERROR, ts).open();
            }
        }
    }
 
    private void initializeTblViews() {        
        loadViews();
        tblViews = new Grid<>();
        ListDataProvider<ViewObjectLight> dataProvider = new ListDataProvider<>(sdhViews);
        tblViews.setDataProvider(dataProvider);
        tblViews.addColumn(ViewObjectLight::getName).setFlexGrow(3).setKey(ts.getTranslatedString("module.general.labels.name"));
        tblViews.addItemClickListener(listener -> {
            blankView = true; // added to ignore change events while we are opening the view
            openSDHView(listener.getItem());
            sdhTools.setView(listener.getItem());
            MxGraphNode dummyNode = new MxGraphNode(); 
            dummyNode.addCellAddedListener(eventListener ->  {
                blankView = false;
                sdhView.getMxgraphCanvas().getMxGraph().removeNode(dummyNode);
            });
            sdhView.getMxgraphCanvas().getMxGraph().addNode(dummyNode);
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
     * Loads the given sdh view into the view.
     * @param item the sdh view to be loaded
     */
    private void openSDHView(ViewObjectLight item) {
        try {
            ViewObject view = aem.getGeneralView(item.getId());
            setCurrentView(view);
            if (wdwSdhViews != null)
                this.wdwSdhViews.close();
            this.sdhTools.setGeneralToolsEnabled(true);
            selectedObject = null;
            accordionProperties.close();
            accordionProperties.open(0);
            updatePropertySheetObjects();
            updatePropertySheetView();
        } catch (ApplicationObjectNotFoundException ex) {
            new SimpleNotification(ts.getTranslatedString("module.general.messages.error"), ex.getMessage(),
                    AbstractNotification.NotificationType.ERROR, ts).open();
        }
    }
    /**
     * Loads all the sdh Views.
     */
    public void loadViews() {
        try {
            sdhViews = aem.getGeneralViews(SdhService.CLASS_VIEW,-1);             
        } catch (InvalidArgumentException | NotAuthorizedException ex) {
            new SimpleNotification(ts.getTranslatedString("module.general.messages.error"), ex.getMessage(),
                    AbstractNotification.NotificationType.ERROR, ts).open();
        }
    }

    /**
     * Initialize the general actions that provides the functionalty to create 
     * and remove sdh views 
     */
    private void initializeActions() {
        listenerDeleteAction = (ActionCompletedListener.ActionCompletedEvent ev) -> {
            loadViews();
            tblViews.getDataProvider().refreshAll();
            tblViews.setItems(sdhViews);
            tblViews.getDataProvider().refreshAll();
            showActionCompledMessages(ev);
            sdhTools.setGeneralToolsEnabled(false);
            sdhTools.setSelectionToolsEnabled(false);
            setCurrentView(null);
            selectedObject = null;
            updatePropertySheetObjects();
            updatePropertySheetView();
        };
        this.deleteSdhViewVisualAction.registerActionCompletedLister(listenerDeleteAction);
        
        listenerNewViewAction = (ActionCompletedListener.ActionCompletedEvent ev) -> {
            loadViews();
            tblViews.getDataProvider().refreshAll();
            tblViews.setItems(sdhViews);
            tblViews.getDataProvider().refreshAll();
            showActionCompledMessages(ev);
            sdhTools.setGeneralToolsEnabled(true);
            if (wdwSdhViews != null)
                wdwSdhViews.close();
            selectedObject = null;
            updatePropertySheetObjects();
            
            ActionResponse response = ev.getActionResponse();
            try {
                ViewObject newView = aem.getGeneralView((long) response.get("viewId"));
                setCurrentView(newView);
                updatePropertySheetView();
                          
            } catch (ApplicationObjectNotFoundException ex) {
                new SimpleNotification(ts.getTranslatedString("module.general.messages.error"), ex.getMessage(),
                        AbstractNotification.NotificationType.ERROR, ts).open();
            }

        };
        this.newSdhViewVisualAction.registerActionCompletedLister(listenerNewViewAction);        
    }

    /**
     * open the dialog that shows the list of available SDH views.
     */
    private void openListSDHViewDialog() {
        if (!sdhViews.isEmpty()) {
            wdwSdhViews = new ConfirmDialog(ts, "");
            wdwSdhViews.getBtnConfirm().setVisible(false);
            wdwSdhViews.add(tblViews);
            wdwSdhViews.setWidth("600px");
            wdwSdhViews.open();
        } else
            new SimpleNotification(ts.getTranslatedString("module.general.messages.information"),
                    ts.getTranslatedString("module.sdh.no-views-created"),
                    AbstractNotification.NotificationType.INFO, ts).open();
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
                                        ((AbstractExplorer) event.getSource()).getHeader()),
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
                lytObjectOption.add(pnlOptions.build(UI.getCurrent().getSession().getAttribute(Session.class).getUser()));
            }
        } catch (InventoryException ex) {
            new SimpleNotification(ts.getTranslatedString("module.general.messages.error"),
                    ex.getLocalizedMessage(), AbstractNotification.NotificationType.ERROR, ts).open();
        }
    }
  
    private void updatePropertySheetObjects() {
        try {        
            if (selectedObject != null) {
                BusinessObject aWholeListTypeItem = bem.getObject(selectedObject.getClassName(), selectedObject.getId());
                propSheetObjects.setItems(PropertyFactory.propertiesFromBusinessObject(aWholeListTypeItem, ts, aem, mem, log));
            } else 
                propSheetObjects.clear();
        } catch (InventoryException ex) {
            new SimpleNotification(ts.getTranslatedString("module.general.messages.error"),
                    ex.getLocalizedMessage(), AbstractNotification.NotificationType.INFO, ts).open();
        }
    }
    
    private void updatePropertySheetView() {
        if (currentView != null) {
            ArrayList<AbstractProperty> viewProperties = new ArrayList<>();
            viewProperties.add(new StringProperty(Constants.PROPERTY_NAME,
                    Constants.PROPERTY_NAME, "", currentView.getName(), ts));
            viewProperties.add(new StringProperty(Constants.PROPERTY_DESCRIPTION,
                    Constants.PROPERTY_DESCRIPTION, "", currentView.getDescription(), ts));
            propSheetSdh.setItems(viewProperties);
        } else
            propSheetSdh.clear();
    }
    /**
     * add a single node to the sdh view
     * @param node the node to be added
     */
    private void addNodeToView(BusinessObjectLight node, int x) {
        if (sdhView.getAsViewMap().findNode(node) == null) {
            String uri = resourceFactory.getClassIcon(node.getClassName());
            Properties props = new Properties();
            props.put("imageUrl", uri);
            props.put("x", x);
            props.put("y", 50);
            sdhView.addNode(node, props);                         
        } else 
            new SimpleNotification(ts.getTranslatedString("module.general.messages.success"), ts.getTranslatedString("module.sdh.object-already-included"), 
                            AbstractNotification.NotificationType.INFO, ts).open();                         
    }
    
    /**
     * adds and edge with his nodes o the sdh view
     */
    private void addEdgeToView(BusinessObjectLight edge, BusinessObjectLight source, BusinessObjectLight target) {
        if (sdhView.getAsViewMap().findEdge(edge) == null) {
            Properties props = new Properties();
            props.put("controlPoints", new ArrayList<>());
            props.put("sourceLabel", source == null ? "" : source.getName());
            props.put("targetLabel", target == null ? "" : target.getName());
            sdhView.addEdge(edge, source, target, props);                            
        } else 
            new SimpleNotification(ts.getTranslatedString("module.general.messages.success"), ts.getTranslatedString("module.sdh.edge-already-included"), 
                            AbstractNotification.NotificationType.INFO, ts).open();                   
        
    }
 
    void openDlgSelectConnectionType() {
        ConfirmDialog dlgConnection = new ConfirmDialog(ts);
        ComboBox<Integer> cbxConnType = new ComboBox<>();
        cbxConnType.setWidth("300px");
        cbxConnType.setItems(Arrays.asList(1, 2, 3));
        cbxConnType.setItemLabelGenerator(item -> {
            switch (item) {
                    case 1:
                        return CONNECTION_TRANSPORTLINK;
                    case 2:
                        return CONNECTION_CONTAINERLINK;
                    default:
                        return CONNECTION_TRIBUTARYLINK;
                }
        });
        dlgConnection.getBtnConfirm().addClickListener(listener -> {
            if (cbxConnType.getValue() != null) {
                Wizard wizard;
                String message, wizardHeader;
                switch (cbxConnType.getValue()) {
                    case 1:
                        wizard = new NewSDHTransportLinkWizard(selectedSourceEquipment,
                        selectedTargetEquipment, bem, mem, resourceFactory, sdhService, ts);
                        message = ts.getTranslatedString("module.sdh.transport-link-created");
                        wizardHeader = String.format("%s %s", ts.getTranslatedString("module.general.labels.create"), CONNECTION_TRANSPORTLINK);
                        break;
                    case 2:
                        wizard = new NewSDHContainerLinkWizard(selectedSourceEquipment,
                        selectedTargetEquipment, mem, resourceFactory, sdhService, ts, log);
                        message = ts.getTranslatedString("module.sdh.container-link-created");
                        wizardHeader = String.format("%s %s", ts.getTranslatedString("module.general.labels.create"), CONNECTION_CONTAINERLINK);                 
                        break;
                    case 3:
                        wizard = new NewSDHTributaryLinkWizard(selectedSourceEquipment,
                        selectedTargetEquipment, mem, bem, resourceFactory, sdhService, ts);
                        message = ts.getTranslatedString("module.sdh.tributary-link-created");
                        wizardHeader = String.format("%s %s", ts.getTranslatedString("module.general.labels.create"), CONNECTION_TRIBUTARYLINK);                                        
                        break;
                    default: return;
                }
                
                EnhancedDialog dlgWizard = new EnhancedDialog();

                wizard.setSizeFull();
                wizard.addEventListener((wizardEvent) -> {
                    switch (wizardEvent.getType()) {
                        case Wizard.WizardEvent.TYPE_FINAL_STEP:
                            BusinessObjectLight newConnection = (BusinessObjectLight) wizardEvent.getInformation().get("connection");
                            if (cbxConnType.getValue() == 1) {
                                BusinessObjectLight aSide = (BusinessObjectLight) wizardEvent.getInformation().get("equipmentA");
                                BusinessObjectLight bSide = (BusinessObjectLight) wizardEvent.getInformation().get("equipmentB");
                                addEdgeToView(newConnection, aSide, bSide);
                            }
                            new SimpleNotification(ts.getTranslatedString("module.general.messages.success"), String.format(message, newConnection.getName()),
                                    AbstractNotification.NotificationType.INFO, ts).open();
                        case Wizard.WizardEvent.TYPE_CANCEL:
                            dlgWizard.close();
                    }
                });

                dlgWizard.setModal(true);
                dlgWizard.setWidth("70%");
                dlgWizard.setHeader(new BoldLabel(wizardHeader));
                dlgWizard.setContent(wizard.getLytMainContent());
                dlgWizard.setFooter(wizard.getLytButtons());
                dlgConnection.close();
                dlgWizard.open();
            }
        });
        dlgConnection.setContent(cbxConnType);
        dlgConnection.setHeader(ts.getTranslatedString("module.sdh.select-connection-type"));
        dlgConnection.open();
    }       

    @Override
    public void actionCompleted(ActionCompletedListener.ActionCompletedEvent ev) {
        if (ev.getStatus() == ActionCompletedListener.ActionCompletedEvent.STATUS_SUCCESS) {
            if (ev.getActionResponse() != null 
                    && ev.getActionResponse().containsKey(PARAM_BUSINESS_OBJECT)
                    && ev.getActionResponse().get(PARAM_BUSINESS_OBJECT) != null
                    && ev.getActionResponse().containsKey(ActionResponse.ActionType.REMOVE)) {
                BusinessObjectLight affectedNode = (BusinessObjectLight) ev.getActionResponse().get(PARAM_BUSINESS_OBJECT);
                if (sdhView != null && sdhView.findMxGraphEdge(affectedNode) != null) {
                    sdhView.removeEdge(affectedNode);
                    saveCurrentView();
                }
            }
            
            new SimpleNotification(ts.getTranslatedString("module.general.messages.success"),
                    ev.getMessage(), AbstractNotification.NotificationType.INFO, ts).open();
        } else
            new SimpleNotification(ts.getTranslatedString("module.general.messages.error"),
                    ev.getMessage(), AbstractNotification.NotificationType.ERROR, ts).open();
    }
}