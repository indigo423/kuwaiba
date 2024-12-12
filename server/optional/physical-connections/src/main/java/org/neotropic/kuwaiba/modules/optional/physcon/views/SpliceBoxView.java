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

package org.neotropic.kuwaiba.modules.optional.physcon.views;

import com.neotropic.flow.component.mxgraph.MxConstants;
import com.neotropic.flow.component.mxgraph.MxGraph;
import com.neotropic.flow.component.mxgraph.MxGraphEdge;
import com.neotropic.flow.component.mxgraph.MxGraphNode;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;
import org.neotropic.kuwaiba.core.apis.integration.modules.views.AbstractImageExporter;
import org.neotropic.kuwaiba.core.apis.integration.modules.views.AbstractView;
import org.neotropic.kuwaiba.core.apis.integration.modules.views.AbstractViewEdge;
import org.neotropic.kuwaiba.core.apis.integration.modules.views.AbstractViewNode;
import org.neotropic.kuwaiba.core.apis.integration.modules.views.ViewEventListener;
import org.neotropic.kuwaiba.core.apis.persistence.application.ApplicationEntityManager;
import org.neotropic.kuwaiba.core.apis.persistence.business.BusinessEntityManager;
import org.neotropic.kuwaiba.core.apis.persistence.business.BusinessObject;
import org.neotropic.kuwaiba.core.apis.persistence.business.BusinessObjectLight;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.InvalidArgumentException;
import org.neotropic.kuwaiba.core.apis.persistence.metadata.MetadataEntityManager;
import org.neotropic.kuwaiba.core.apis.persistence.util.Constants;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.InventoryException;
import org.neotropic.kuwaiba.core.apis.persistence.metadata.ClassMetadata;
import org.neotropic.kuwaiba.core.i18n.TranslationService;
import org.neotropic.kuwaiba.modules.core.logging.LoggerType;
import org.neotropic.kuwaiba.modules.core.logging.LoggingService;

/**
 * View for graphic visualization of splice box equipment
 * @author Orlando Paz  {@literal <orlando.paz@kuwaiba.org>} 
 */
public class SpliceBoxView extends AbstractView<VerticalLayout> {
    /**
     * Reference to the main canvas of the view
     */
    private MxGraph mxGraph;
    /**
     * Reference to the translation service.
     */
    private TranslationService ts;
    /**
     * Reference to the Application Entity Manager
     */
    private ApplicationEntityManager aem;
    /**
     * Reference to the Business Entity Manager
     */
    private BusinessEntityManager bem;
    /**
     * Reference to the Metadata Entity Manager
     */
    private MetadataEntityManager mem;
    private BusinessObjectLight businessObject;
    /**
     * Reference to the Logging Service.
     */
    private LoggingService log;
    
    public SpliceBoxView(BusinessObjectLight businessObject, BusinessEntityManager bem, ApplicationEntityManager aem, 
            MetadataEntityManager mem, TranslationService ts, LoggingService log) {
        this.businessObject = businessObject;
        this.bem = bem;  
        this.aem = aem;
        this.mem = mem;
        this.ts = ts;
        this.log = log;
    }

    @Override
    public byte[] getAsXml() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public byte[] getAsImage(AbstractImageExporter exporter) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public VerticalLayout getAsUiElement() throws InvalidArgumentException {
        if (businessObject != null) {
            int widthPort = 60, heightPort = 50, startY = 30, widthExternalPort = 30, heightExternalPort = 30;
            VerticalLayout lytGraph = new VerticalLayout();
            lytGraph.setSizeFull();
            mxGraph = new MxGraph();
            mxGraph.setWidth("100%");
            mxGraph.setHeight("100%");
            mxGraph.setOverflow("scroll");
            mxGraph.setGrid("");
            mxGraph.setIsCellEditable(false);
            mxGraph.setIsCellResizable(false);
            mxGraph.setIsCellMovable(false);
            lytGraph.add(mxGraph);
            lytGraph.setHorizontalComponentAlignment(FlexComponent.Alignment.CENTER, mxGraph);
            MxGraphNode mainBox = new MxGraphNode();
            mainBox.setUuid("main");
            mainBox.setLabel(businessObject.getName());
            mainBox.setGeometry(250, startY, 100, 100);
            mxGraph.addNode(mainBox);
            try {
                LinkedHashMap<BusinessObjectLight, BusinessObjectLight> mapPorts = new LinkedHashMap<>();
                List<BusinessObjectLight> lstOpticalPorts = bem.getChildrenOfClassLight(businessObject.getId(), businessObject.getClassName(), "OpticalPort", -1);
                lstOpticalPorts = lstOpticalPorts.stream().sorted((object1, object2) -> object1.getName().compareTo(object2.getName())).collect(Collectors.toList());
                for (BusinessObjectLight port : lstOpticalPorts) {
                    if (port.getName().toLowerCase().startsWith("in")) {
                        List<BusinessObjectLight> lstMirrors = bem.getSpecialAttribute(port.getClassName(), port.getId(), "mirror");
                        if (lstMirrors != null && lstMirrors.size() > 0) {
                            mapPorts.put(port, lstMirrors.get(0));
                        } else {
                            mapPorts.put(port, null);
                        }
                    }
                }
                if (mapPorts.isEmpty()) {
                    return new VerticalLayout(new Label(ts.getTranslatedString("module.visualization.splice-box-view-no-input-ports")));
                }
                int i = 1;
                MxGraphNode groupPort;
                MxGraphNode nodeIn;
                MxGraphNode nodeOut;
                MxGraphNode startIn;
                MxGraphNode endOut;
                MxGraphEdge edgeIn;
                MxGraphEdge edgeOut;
                for (BusinessObjectLight inPort : mapPorts.keySet()) {

                    groupPort = new MxGraphNode();
                    groupPort.setUuid("gp" + i);
                    groupPort.setLabel("");
                    groupPort.setGeometry(0, heightPort * (i - 1), widthPort * 2, heightPort);
                    groupPort.setCellParent("main");
                    mxGraph.addNode(groupPort);

                    nodeIn = new MxGraphNode();
                    nodeIn.setUuid("in" + i);
                    nodeIn.setLabel(inPort.getName());
                    nodeIn.setGeometry(0, 0, widthPort, heightPort);
                    nodeIn.setCellParent("gp" + i);
                    nodeIn.setVerticalLabelPosition(MxConstants.ALIGN_MIDDLE);
                    mxGraph.addNode(nodeIn);
                    List<BusinessObjectLight> inLinks = bem.getSpecialAttribute(inPort.getClassName(), inPort.getId(), "endpointA");
                    if (inLinks == null || inLinks.isEmpty()) 
                        inLinks = bem.getSpecialAttribute(inPort.getClassName(), inPort.getId(), "endpointB");
                    
                    if (inLinks != null && inLinks.size() > 0) {
                        BusinessObject theWholeLink = bem.getObject(inLinks.get(0).getClassName(), inLinks.get(0).getId());
                        String hexColor = null;
                        if (theWholeLink.getAttributes().containsKey(Constants.PROPERTY_COLOR)) {
                            hexColor = theWholeLink.getAttributes().get(Constants.PROPERTY_COLOR) instanceof String ? 
                                    (String) theWholeLink.getAttributes().get(Constants.PROPERTY_COLOR) : null;
                            if (hexColor != null) {
                                ClassMetadata classInLink = mem.getClass(inLinks.get(0).getClassName());
                                String colorType = classInLink.getType(Constants.PROPERTY_COLOR);
                                if(mem.isSubclassOf(Constants.CLASS_GENERICOBJECTLIST, colorType)) {
                                    BusinessObject colorObject = aem.getListTypeItem(colorType, hexColor);
                                    hexColor = colorObject.getAttributes().containsKey("value") && colorObject.getAttributes().get("value") instanceof String ? (String) colorObject.getAttributes().get("value") : null; //NOI18N
                                }
                            }
                        }
                        if (hexColor == null)
                            hexColor = "steelblue";  //default color
                        nodeIn.setFillColor(hexColor);
                        startIn = new MxGraphNode();
                        startIn.setUuid("s" + i);
                        startIn.setLabel("");
                        startIn.setGeometry(10, startY + ((heightPort) * (i - 1)) + ((heightPort - heightExternalPort)/2), 
                                widthExternalPort, heightExternalPort);
                        startIn.setFillColor("white");
                        startIn.setVerticalLabelPosition(MxConstants.ALIGN_MIDDLE);
                        edgeIn = new MxGraphEdge();
                        edgeIn.setSource("s" + i);
                        edgeIn.setTarget("in" + i);
                        edgeIn.setStrokeWidth(1);
                        edgeIn.setLabel(inLinks.get(0).getName());
                        mxGraph.addNode(startIn);
                        mxGraph.addEdge(edgeIn);
                    } else 
                        nodeIn.setFillColor("gray");
                    

                    BusinessObjectLight outPort = mapPorts.get(inPort);
                    nodeOut = new MxGraphNode();
                    nodeOut.setUuid("out" + i);
                    nodeOut.setGeometry(widthPort, 0, widthPort, heightPort);
                    nodeOut.setCellParent("gp" + i);
                    nodeOut.setVerticalLabelPosition(MxConstants.ALIGN_MIDDLE);
                    mxGraph.addNode(nodeOut);
                    if (outPort != null) {
                        nodeOut.setLabel(outPort.getName());
                        List<BusinessObjectLight> outLinks = bem.getSpecialAttribute(outPort.getClassName(), outPort.getId(), "endpointA");
                        if (outLinks == null || outLinks.isEmpty())
                            outLinks = bem.getSpecialAttribute(outPort.getClassName(), outPort.getId(), "endpointB");
                        
                        if (outLinks != null && outLinks.size() > 0) {
                            BusinessObject theWholeLink = bem.getObject(outLinks.get(0).getClassName(), outLinks.get(0).getId());
                            String hexColor = null;
                            if (theWholeLink.getAttributes().containsKey(Constants.PROPERTY_COLOR)) {
                                hexColor = theWholeLink.getAttributes().get(Constants.PROPERTY_COLOR) instanceof String ? (String) theWholeLink.getAttributes().get(Constants.PROPERTY_COLOR) : null;
                                if (hexColor != null) {
                                    ClassMetadata classInLink = mem.getClass(outLinks.get(0).getClassName());
                                    String colorType = classInLink.getType(Constants.PROPERTY_COLOR);
                                    if(mem.isSubclassOf(Constants.CLASS_GENERICOBJECTLIST, colorType)) {
                                        BusinessObject colorObject = aem.getListTypeItem(colorType, hexColor);
                                        hexColor = colorObject.getAttributes().containsKey("value") && colorObject.getAttributes().get("value") instanceof String ? (String) colorObject.getAttributes().get("value") : null; //NOI18N
                                    }
                                }
                            }
                            if (hexColor == null)
                                hexColor = "steelblue";  //default color
                            nodeOut.setFillColor(hexColor);
                            endOut = new MxGraphNode();
                            endOut.setUuid("e" + i);
                            endOut.setLabel("");
                            endOut.setGeometry(530, startY + (heightPort) * (i - 1) + ((heightPort - heightExternalPort)/2), widthExternalPort, heightExternalPort   );
                            endOut.setFillColor("white");
                            endOut.setVerticalLabelPosition(MxConstants.ALIGN_MIDDLE);
                            edgeOut = new MxGraphEdge();
                            edgeOut.setSource("out" + i);
                            edgeOut.setTarget("e" + i);
                            edgeOut.setStrokeWidth(1);
                            edgeOut.setLabel(outLinks.get(0).getName());
                            mxGraph.addNode(endOut);
                            mxGraph.addEdge(edgeOut);
                        } else 
                            nodeOut.setFillColor("gray"); //doenst have a end point
                        
                    } else  // doesnt have a mirror port                   
                        nodeOut.setFillColor("black");
                    i++;
                }

            } catch (InventoryException ex) {
                log.writeLogMessage(LoggerType.ERROR, SpliceBoxView.class, "", ex);
            }
            return lytGraph;
        }
        return new VerticalLayout(new Label(ts.getTranslatedString("module.visualization.view.no-business-object-associated")));
    }

    @Override
    public void buildFromSavedView(byte[] view) { }

    @Override
    public AbstractViewNode addNode(BusinessObjectLight businessObject, Properties properties) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public AbstractViewEdge addEdge(BusinessObjectLight businessObject, BusinessObjectLight sourceBusinessObject, BusinessObjectLight targetBusinessObject, Properties properties) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeNode(BusinessObjectLight businessObject) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeEdge(BusinessObjectLight businessObject) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void nodeClickListener(ViewEventListener listener) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void edgeClickListener(ViewEventListener listener) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void clean() { }
}
