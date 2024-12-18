/*
 *  Copyright 2010-2020 Neotropic SAS <contact@neotropic.co>.
 *
 *   Licensed under the EPL License, Version 1.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *        http://www.eclipse.org/legal/epl-v10.html
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */

package org.inventory.views.objectview.scene;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import org.inventory.communications.CommunicationsStub;
import org.inventory.communications.core.LocalClassMetadata;
import org.inventory.communications.core.LocalObjectLight;
import org.inventory.communications.core.views.LocalObjectView;
import org.inventory.communications.util.Constants;
import org.inventory.core.services.api.notifications.NotificationUtil;
import org.inventory.core.services.i18n.I18N;
import org.inventory.core.visual.scene.AbstractScene;
import org.inventory.core.visual.actions.CustomMoveControlPointAction;
import org.inventory.core.visual.menu.ObjectWidgetMenu;
import org.inventory.core.visual.scene.ObjectConnectionWidget;
import org.inventory.core.visual.scene.ObjectNodeWidget;
import org.inventory.core.visual.actions.CustomAddRemoveControlPointAction;
import org.inventory.core.visual.actions.CustomMoveAction;
import org.inventory.core.visual.configuration.ObjectViewConfigurationObject;
import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.action.ConnectProvider;
import org.netbeans.api.visual.action.PopupMenuProvider;
import org.netbeans.api.visual.anchor.AnchorFactory;
import org.netbeans.api.visual.anchor.PointShape;
import org.netbeans.api.visual.router.RouterFactory;
import org.netbeans.api.visual.widget.ConnectionWidget;
import org.netbeans.api.visual.widget.LayerWidget;
import org.netbeans.api.visual.widget.Widget;
import org.openide.util.Exceptions;

/**
 * This is the main scene for an object's view
 * @author Charles Edward Bedon Cortazar {@literal <charles.bedon@kuwaiba.org>}
 */
public final class ChildrenViewScene extends AbstractScene<LocalObjectLight, LocalObjectLight> {
    public static final int X_OFFSET = 100;
    
    /**
     * The common connection provider
     */
    private final PhysicalConnectionProvider myConnectionProvider;
    /**
     * Default control point move action (shared by all connection widgets)
     */
    private final CustomMoveControlPointAction moveControlPointAction =
            new CustomMoveControlPointAction(this);
    /**
     * Default add/remove control point action (shared by all connection widgets)
     */
    private final CustomAddRemoveControlPointAction addRemoveControlPointAction =
            new CustomAddRemoveControlPointAction(this);
    /**
     * Default move widget action (shared by all connection widgets)
     */
    private final CustomMoveAction moveAction = new CustomMoveAction(this);
    /**
     * Popup provider for all nodes and connections
     */
    private final PopupMenuProvider defaultPopupMenuProvider;
    
    public ChildrenViewScene (ObjectViewConfigurationObject configObject) {
        interactionLayer = new LayerWidget(this);
        backgroundLayer = new LayerWidget(this);
        nodeLayer = new LayerWidget(this);
        edgeLayer = new LayerWidget(this);
        labelsLayer = new LayerWidget(this);
        myConnectionProvider = new PhysicalConnectionProvider(this);
        this.configObject = configObject;
        
        addChild(backgroundLayer);
        addChild(edgeLayer);
        addChild(nodeLayer);
        addChild(labelsLayer);
	addChild(interactionLayer);
        
        getActions().addAction(ActionFactory.createZoomAction());
        getInputBindings ().setZoomActionModifiers(0); //No keystroke combinations
        getActions().addAction(ActionFactory.createPanAction());
        getInputBindings ().setPanActionButton(MouseEvent.BUTTON1); //Pan using the left click

        defaultPopupMenuProvider = new ObjectWidgetMenu();
        
        setActiveTool(ACTION_SELECT);
        initSelectionListener();
    }
    
    /**
     * This methods are called if addNode/addEdge instead of "addChild"
     * @param node
     * @return
     */
    @Override
    protected Widget attachNodeWidget(LocalObjectLight node) {
        LocalClassMetadata classMetadata = CommunicationsStub.getInstance().getMetaForClass(node.getClassName(), false);
        ObjectNodeWidget widget;
        if (classMetadata == null) //Should not happen, but this check should always be done
            widget = new ObjectNodeWidget(this, node);
        else
            widget = new ObjectNodeWidget(this, node, classMetadata.getIcon());
        
        //The order the actions are added to a widget matters, if Select goes
        //after Move, you will need a double click to select the widget
        //Also, it is necessary to associate a PopupMenuAction AFTER the selection, otherwise
        //the actions will fail, as they mostly depend on detecting what node is selected, 
        //therefore the widget has to be selected before launching the menu
        widget.getActions(ACTION_SELECT).addAction(createSelectAction());
        widget.getActions(ACTION_SELECT).addAction(moveAction);
        widget.getActions(ACTION_SELECT).addAction(ActionFactory.createPopupMenuAction(defaultPopupMenuProvider));
        
        widget.getActions(ACTION_CONNECT).addAction(ActionFactory.createConnectAction(interactionLayer, myConnectionProvider));
        widget.getActions(ACTION_CONNECT).addAction(createSelectAction());
        widget.getActions(ACTION_CONNECT).addAction(ActionFactory.createPopupMenuAction(defaultPopupMenuProvider));
        
        nodeLayer.addChild(widget);
        return widget;
    }

    @Override
    protected Widget attachEdgeWidget(LocalObjectLight edge) {
        LocalClassMetadata classMetadata = CommunicationsStub.getInstance().getMetaForClass(edge.getClassName(), false);
        ObjectConnectionWidget widget = new ObjectConnectionWidget(this, edge, ObjectConnectionWidget.LINE);
        widget.getActions().addAction(createSelectAction());
        widget.getActions().addAction(ActionFactory.createPopupMenuAction(defaultPopupMenuProvider));
        widget.getActions().addAction(addRemoveControlPointAction);
        widget.getActions().addAction(moveControlPointAction);
        widget.setControlPointShape(PointShape.SQUARE_FILLED_BIG);
        widget.setEndPointShape(PointShape.SQUARE_FILLED_BIG);
        widget.setRouter(RouterFactory.createFreeRouter());
        widget.setToolTipText(edge.toString());
        widget.setLineColor(classMetadata.getColor());
        edgeLayer.addChild(widget);
        return widget;
    }

    public LayerWidget getNodesLayer(){
        return nodeLayer;
    }

    public LayerWidget getEdgesLayer(){
        return edgeLayer;
    }
   
    @Override
    public ConnectProvider getConnectProvider(){
        return this.myConnectionProvider;
    }

    @Override
    public void clear(){
        backgroundLayer.removeChildren();
        super.clear();
    }

    @Override
    public byte[] getAsXML() {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            XMLOutputFactory xmlof = XMLOutputFactory.newInstance();
            XMLEventWriter xmlew = xmlof.createXMLEventWriter(baos);
            XMLEventFactory xmlef = XMLEventFactory.newInstance();
            
            QName qnameView = new QName("view"); //NOI18N
            xmlew.add(xmlef.createStartElement(qnameView, null, null));
            xmlew.add(xmlef.createAttribute(new QName("version"), Constants.VIEW_FORMAT_VERSION)); //NOI18N
            
            QName qnameClass = new QName("class"); //NOI18N
            xmlew.add(xmlef.createStartElement(qnameClass, null, null));
            xmlew.add(xmlef.createCharacters("ObjectView")); //NOI18N
            xmlew.add(xmlef.createEndElement(qnameClass, null));
            
            QName qnameNodes = new QName("nodes"); //NOI18N
            xmlew.add(xmlef.createStartElement(qnameNodes, null, null));
            
            for (Widget nodeWidget : nodeLayer.getChildren()) {
                QName qnameNode = new QName("node"); //NOI18N
                xmlew.add(xmlef.createStartElement(qnameNode, null, null));
                xmlew.add(xmlef.createAttribute(new QName("x"), Integer.toString(nodeWidget.getPreferredLocation().x))); //NOI18N
                xmlew.add(xmlef.createAttribute(new QName("y"), Integer.toString(nodeWidget.getPreferredLocation().y))); //NOI18N
                LocalObjectLight lolNode = (LocalObjectLight) findObject(nodeWidget);
                xmlew.add(xmlef.createAttribute(new QName("class"), lolNode.getClassName())); //NOI18N
                xmlew.add(xmlef.createCharacters(lolNode.getId()));
                xmlew.add(xmlef.createEndElement(qnameNode, null));
            }
            xmlew.add(xmlef.createEndElement(qnameNodes, null));
            
            QName qnameEdges = new QName("edges"); //NOI18N
            xmlew.add(xmlef.createStartElement(qnameEdges, null, null));
            
            for (Widget edgeWidget : edgeLayer.getChildren()) {
                ObjectConnectionWidget acwEdge = (ObjectConnectionWidget) edgeWidget;
                if (acwEdge.getSourceAnchor() == null || acwEdge.getTargetAnchor() == null) //This connection is malformed because one of the endpoints does not exist
                    continue;                                                               //probably, it was moved to another parent
                
                QName qnameEdge = new QName("edge"); //NOI18N
                xmlew.add(xmlef.createStartElement(qnameEdge, null, null));
                
                LocalObjectLight lolEdge = (LocalObjectLight) findObject(acwEdge);
                xmlew.add(xmlef.createAttribute(new QName("id"), lolEdge.getId())); //NOI18N
                xmlew.add(xmlef.createAttribute(new QName("class"), lolEdge.getClassName())); //NOI18N
                LocalObjectLight aSideObject = (LocalObjectLight) findObject(acwEdge.getSourceAnchor().getRelatedWidget());
                xmlew.add(xmlef.createAttribute(new QName("asideid"), aSideObject.getId())); //NOI18N
                xmlew.add(xmlef.createAttribute(new QName("asideclass"), aSideObject.getClassName())); //NOI18N
                LocalObjectLight bSideObject = (LocalObjectLight) findObject(acwEdge.getTargetAnchor().getRelatedWidget());
                xmlew.add(xmlef.createAttribute(new QName("bsideId"), bSideObject.getId())); //NOI18N
                xmlew.add(xmlef.createAttribute(new QName("bsideclass"), bSideObject.getClassName())); //NOI18N
                
                for (Point point : acwEdge.getControlPoints()) {
                    QName qnameControlpoint = new QName("controlpoint"); //NOI18N
                    xmlew.add(xmlef.createStartElement(qnameControlpoint, null, null));
                    xmlew.add(xmlef.createAttribute(new QName("x"), Integer.toString(point.x))); //NOI18N
                    xmlew.add(xmlef.createAttribute(new QName("y"), Integer.toString(point.y))); //NOI18N
                    xmlew.add(xmlef.createEndElement(qnameControlpoint, null));
                }
                xmlew.add(xmlef.createEndElement(qnameEdge, null));
            }
            xmlew.add(xmlef.createEndElement(qnameEdges, null));
            
            xmlew.add(xmlef.createEndElement(qnameView, null));
            xmlew.close();
            return baos.toByteArray();
        } catch (XMLStreamException ex) {
            Exceptions.printStackTrace(ex);
        }
        return null;
    }

    @Override
    public void render(byte[] structure) throws IllegalArgumentException {
        CommunicationsStub com = CommunicationsStub.getInstance();
        
        LocalObjectLight object = (LocalObjectLight) configObject.getProperty("currentObject"); //NOI18N
        LocalObjectView currentView = (LocalObjectView) configObject.getProperty("currentView"); //NOI18N
        
       //<editor-fold defaultstate="collapsed" desc="uncomment this for debugging purposes, write the XML view into a file">
//        try {
//            FileOutputStream fos = new FileOutputStream(System.getProperty("user.home") + "/oview_" + currentView.getId() + ".xml");
//            fos.write(currentView.getStructure());
//            fos.close();
//        } catch(Exception e) {}
        //</editor-fold>
        
        List<LocalObjectLight> myNodes = com.getObjectChildren(object.getId(), com.getMetaForClass(object.getClassName(),false).getId());
        if (myNodes == null)
            throw new IllegalArgumentException();
        
        List<LocalObjectLight> myConnections = com.getSpecialChildrenOfClassLight(object.getClassName(), object.getId(), Constants.CLASS_GENERICCONNECTION);
        if (myConnections == null)
            throw new IllegalArgumentException();
        
        renderDefaultView(object, myNodes, myConnections);
        
        if (structure == null) //There is no a saved view
            return;
        
        try {
            XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            QName qZoom = new QName("zoom"); //NOI18N
            QName qCenter = new QName("center"); //NOI18N
            QName qNode = new QName("node"); //NOI18N
            QName qEdge = new QName("edge"); //NOI18N
            QName qLabel = new QName("label"); //NOI18N
            QName qControlPoint = new QName("controlpoint"); //NOI18N

            ByteArrayInputStream bais = new ByteArrayInputStream(structure);
            XMLStreamReader reader = inputFactory.createXMLStreamReader(bais);

            while (reader.hasNext()) {
                int event = reader.next();
                if (event == XMLStreamConstants.START_ELEMENT) {
                    if (reader.getName().equals(qNode)) {
                        int xCoordinate = Double.valueOf(reader.getAttributeValue(null,"x")).intValue(); //NOI18N
                        int yCoordinate = Double.valueOf(reader.getAttributeValue(null,"y")).intValue(); //NOI18N
                        String objectClass = reader.getAttributeValue(null, "class"); //NOI18N
                        String objectId = reader.getElementText();
                        
                        Widget widget = findWidget(new LocalObjectLight(objectId, "" /* Not relevant for comparison purposes */, objectClass));
                        
                        if (widget != null) {
                            widget.setPreferredLocation(new Point(xCoordinate, yCoordinate));
                            widget.setBackground(com.getMetaForClass(objectClass, false).getColor());
                            validate();
                        } else //The node is no longer inside the current object, since it already exists on the database, probably it was moved somewhere else
                            currentView.setDirty(true);
                    } else {
                        if (reader.getName().equals(qEdge)) {
                            
                            String aSideId = reader.getAttributeValue(null, "asideid"); //NOI18N
                            String aSideClass = reader.getAttributeValue(null, "asideclass"); //NOI18N
                            String bSideId = reader.getAttributeValue(null, "bsideid"); //NOI18N
                            String bSideClass = reader.getAttributeValue(null, "bsideclass"); //NOI18N
                            String objectId = reader.getAttributeValue(null, "id"); //NOI18N
                            String className = reader.getAttributeValue(null,"class"); //NOI18N

                            ObjectConnectionWidget edgeWidget = (ObjectConnectionWidget)findWidget(new LocalObjectLight(objectId, "", className));
                            
                            if (edgeWidget != null) {
                                List<Point> localControlPoints = new ArrayList<>();
                                while(true) {
                                    reader.nextTag();
                                    if (reader.getName().equals(qControlPoint)) {
                                        if (reader.getEventType() == XMLStreamConstants.START_ELEMENT)
                                            localControlPoints.add(new Point(Integer.valueOf(reader.getAttributeValue(null,"x")), Integer.valueOf(reader.getAttributeValue(null,"y"))));
                                    } else {
                                        edgeWidget.setControlPoints(localControlPoints,false);
                                        break;
                                    }
                                }
                                validate();
                            } else
                                    currentView.setDirty(true);
                        } else {
                            if (reader.getName().equals(qLabel)) {
                                //Unavailable for now
                            } else {
                                if (reader.getName().equals(qZoom))
                                    currentView.setZoom(Integer.valueOf(reader.getText()));
                                else {
                                    if (reader.getName().equals(qCenter)) {
                                        double x = Double.valueOf(reader.getAttributeValue(null, "x")); //NOI18N
                                        double y = Double.valueOf(reader.getAttributeValue(null, "y")); //NOI18N
                                        currentView.setCenter(new double[]{ x, y });
                                    } else {
                                        //Place more tags
                                    }
                                }
                            }
                        }
                    }
                }
            }
            reader.close();
            setBackgroundImage(currentView.getBackground());

            if (currentView.isDirty()) {
                fireChangeEvent(new ActionEvent(this, ChildrenViewScene.SCENE_CHANGEANDSAVE, "Removing old objects"));
                NotificationUtil.getInstance().showSimplePopup(I18N.gm("information"), NotificationUtil.WARNING_MESSAGE, "Some changes have been detected since the last time the view was saved. The view was updated accordingly");
                currentView.setDirty(false);
            }
        } catch (XMLStreamException ex) {
            if (Constants.DEBUG_LEVEL == Constants.DEBUG_LEVEL_INFO)
                Exceptions.printStackTrace(ex);
        }
        
        //revalidate() -> validate() was the only combination that allows the correct rendering of the connections in the scene.
        revalidate();
        validate();
    }
    
    private void renderDefaultView(LocalObjectLight currentObject, List<LocalObjectLight> children, List<LocalObjectLight> connections) {
        
        int lastX = 0;
            
        for (LocalObjectLight child : children) { // Add the nodes
            Widget widget = addNode(child);
            widget.setPreferredLocation(new Point(lastX, 0));
            widget.setBackground(CommunicationsStub.getInstance().getMetaForClass(child.getClassName(), false).getColor());
                
            lastX += ChildrenViewScene.X_OFFSET;
            validate();
        }
            
        //TODO: This algorithm to find the endpoints for a connection could be improved in many ways
        for (LocalObjectLight container : connections) {            
            List<LocalObjectLight> aSide = CommunicationsStub.getInstance()
                .getSpecialAttribute(container.getClassName(), container.getId(), "endpointA"); //NOI18N
            if (aSide == null) {
                NotificationUtil.getInstance().showSimplePopup(I18N.gm("error"), NotificationUtil.ERROR_MESSAGE, CommunicationsStub.getInstance().getError());
                continue;
            }
            
            if (aSide.isEmpty()) {
                NotificationUtil.getInstance().showSimplePopup(I18N.gm("information"), NotificationUtil.INFO_MESSAGE, String.format("Connection %s has a loose endpoint and won't be displayed", container));
                continue;
            }
            
            List<LocalObjectLight> bSide = CommunicationsStub.getInstance()
                .getSpecialAttribute(container.getClassName(), container.getId(), "endpointB"); //NOI18N
            
            if (bSide == null) {
                NotificationUtil.getInstance().showSimplePopup(I18N.gm("error"), NotificationUtil.ERROR_MESSAGE, CommunicationsStub.getInstance().getError());
                continue;
            }
            
            if (bSide.isEmpty()) {
                NotificationUtil.getInstance().showSimplePopup(I18N.gm("information"), NotificationUtil.INFO_MESSAGE, String.format("Connection %s has a loose endpoint and won't be displayed", container));
                continue;
            }

            //The nodes in the view correspond to equipment or infrastructure, not the actual ports
            //so we have to find the equipment being dislayed so we can find them in the scene            
            List<LocalObjectLight> parentsASide = CommunicationsStub.getInstance()
                .getParents(aSide.get(0).getClassName(), aSide.get(0).getId());
            if (parentsASide == null)
                continue;
                
            List<LocalObjectLight> parentsBSide = CommunicationsStub.getInstance()
                .getParents(bSide.get(0).getClassName(), bSide.get(0).getId());
            
            if (parentsBSide == null)
                continue;

            int currentObjectIndexASide = parentsASide.indexOf(currentObject);
            ObjectNodeWidget aSideWidget = currentObjectIndexASide == 0 ? (ObjectNodeWidget) findWidget(aSide.get(0)) : (ObjectNodeWidget) findWidget(parentsASide.get(currentObjectIndexASide - 1));
                
            int currentObjectIndexBSide = parentsBSide.indexOf(currentObject);
            ObjectNodeWidget bSideWidget = currentObjectIndexBSide == 0 ? (ObjectNodeWidget) findWidget(bSide.get(0)) : (ObjectNodeWidget) findWidget(parentsBSide.get(currentObjectIndexBSide - 1));

            ConnectionWidget newEdge = (ConnectionWidget) addEdge(container);
            newEdge.setSourceAnchor(AnchorFactory.createCenterAnchor(aSideWidget));
            newEdge.setTargetAnchor(AnchorFactory.createCenterAnchor(bSideWidget));
            validate();
        }
    }
    
    @Override
    public boolean supportsConnections() {
        return true;
    }

    @Override
    public boolean supportsBackgrounds() {
        return true;
    }

    @Override
    public void render(LocalObjectLight root) {} //Not used in this view
    
    public void enableHighContrastMode(boolean enable) {
        for (Widget aNode : nodeLayer.getChildren()) 
            ((ObjectNodeWidget)aNode).setHighContrast(enable);
        
        for (Widget aConnection : edgeLayer.getChildren()) 
            ((ObjectConnectionWidget)aConnection).setHighContrast(enable);
        
        validate();
    }
}
