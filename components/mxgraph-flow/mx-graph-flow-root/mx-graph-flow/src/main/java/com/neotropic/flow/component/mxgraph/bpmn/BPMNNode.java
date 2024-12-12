/*
 * Copyright 2010-2022 Neotropic SAS <contact@neotropic.co>.
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

package com.neotropic.flow.component.mxgraph.bpmn;

import com.neotropic.flow.component.mxgraph.MxConstants;
import com.neotropic.flow.component.mxgraph.MxGraphCellOnClickOverlayButton;
import com.neotropic.flow.component.mxgraph.MxGraphNode;
import java.util.List;

/**
 * Abstract class that defines generic behavior of all possible nodes in a BPMN diagram
 * @author Orlando Paz {@literal <orlando.paz@kuwaiba.org>} 
 */
public abstract class BPMNNode extends MxGraphNode {

    public static String NODE_TYPE_EVENT = "event";
    public static String NODE_TYPE_GATEWAY = "gateway";
    public static String NODE_TYPE_TASK = "task";
    public static String NODE_TYPE_SWIMLANE = "swimlane";
    public static String NODE_TYPE_SUBPROCESS = "subprocess";
    public static String NODE_TYPE_LINK = "link";
    public static String NODE_TYPE_LABEL = "label";
    BPMNDiagram graph;
    public static int ICON_HEIGHT = 22;
    public static int ICON_WIDTH = 22;
    private String genericType;
    private boolean showNewSubprocessOption = false; 
    private List<BPMNNode> nodes;

    public BPMNNode(BPMNDiagram graph, String genericType) {
        super();
        this.graph = graph;
        this.genericType = genericType;
        init();
    }

    public boolean isShowNewSubprocessOption() {
        return showNewSubprocessOption;
    }

    public void setShowNewSubprocessOption(boolean showNewSubprocessOption) {
        this.showNewSubprocessOption = showNewSubprocessOption;
    }

    public String getGenericType() {
        return genericType;
    }

    public void setGenericType(String genericType) {
        this.genericType = genericType;
    }    
    
    private void init() {
        setTag(genericType);
        setShowOverlayButtonsOnSelect(false);
        nodes = graph.getBPMNNodes();
        
        addClickOverlayButtonListener((evt) -> {
            nodes.forEach(node -> node.clearCellOverlays());
            switch (evt.getButtonId()) {
                case "removeNode":
                    nodes.remove(this);
                    graph.removeNode(this);
                    break;                
                case "event_end": {
                    SymbolNode newNode = new SymbolNode(graph, SymbolNode.SymbolType.Event_End);
                    newNode.setX(getX() + 250);
                    newNode.setY(getY());
                    
                    newNode.addCellParentChangedListener(event -> newNode.setCellParent(getCellParent()));
                    
                    BPMNConnection newEdge = new BPMNConnection(graph);
                    newEdge.setSource(getUuid());
                    newEdge.setTarget(newNode.getUuid());
                    graph.addNode(newNode);
                    graph.addEdge(newEdge);
                    
                    newNode.addCellAddedListener(event -> newNode.selectCell());
                    fireEvent(new MxGraphCellOnClickOverlayButton(this, false, "event_end", newNode));
                    nodes.add(newNode);
                    break;
                }
                case "fork": {
                    SymbolNode newNode = new SymbolNode(graph, SymbolNode.SymbolType.Fork);
                    newNode.setX(getX() + 250);
                    newNode.setY(getY());
                    
                    newNode.addCellParentChangedListener(event -> newNode.setCellParent(getCellParent()));
                    
                    BPMNConnection newEdge = new BPMNConnection(graph);
                    newEdge.setSource(getUuid());
                    newEdge.setTarget(newNode.getUuid());
                    graph.addNode(newNode);
                    graph.addEdge(newEdge);
                    
                    newNode.addCellAddedListener(event -> newNode.selectCell());
                    fireEvent(new MxGraphCellOnClickOverlayButton(this, false, "fork", newNode));
                    nodes.add(newNode);
                    break;
                }
                case "exclusive": {
                    SymbolNode newNode = new SymbolNode(graph, SymbolNode.SymbolType.Exclusive);
                    newNode.setX(getX() + 250);
                    newNode.setY(getY());
                    
                    newNode.addCellParentChangedListener(event -> newNode.setCellParent(getCellParent()));
                    
                    BPMNConnection newEdge = new BPMNConnection(graph);
                    newEdge.setSource(getUuid());
                    newEdge.setTarget(newNode.getUuid());
                    graph.addNode(newNode);
                    graph.addEdge(newEdge);
                    
                    newNode.addCellAddedListener(event -> newNode.selectCell());
                    fireEvent(new MxGraphCellOnClickOverlayButton(this, false, "exclusive", newNode));
                    nodes.add(newNode);
                    break;
                }
                case "task": {
                    TaskNode newNode = new TaskNode(graph);
                    newNode.setX(getX() + 250);
                    newNode.setY(getY());
                    
                    newNode.addCellParentChangedListener(event -> newNode.setCellParent(getCellParent()));
                    
                    BPMNConnection newEdge = new BPMNConnection(graph);
                    newEdge.setSource(getUuid());
                    newEdge.setTarget(newNode.getUuid());
                    graph.addNode(newNode);
                    graph.addEdge(newEdge);
                    
                    newNode.addCellAddedListener(event -> newNode.selectCell());
                    fireEvent(new MxGraphCellOnClickOverlayButton(this, false, "task", newNode));
                    nodes.add(newNode);
                    break;
                }
                case "subprocess": {
                    SubProcessNode newNode = new SubProcessNode(graph);
                    newNode.setX(getX() + 250);
                    newNode.setY(getY() - 35);
                    
                    newNode.addCellParentChangedListener(event -> newNode.setCellParent(getCellParent()));
                    
                    BPMNConnection newEdge = new BPMNConnection(graph);
                    newEdge.setSource(getUuid());
                    newEdge.setTarget(newNode.getUuid());
                    graph.addNode(newNode);
                    graph.addEdge(newEdge);
                    
                    newNode.addCellAddedListener(event -> newNode.selectCell());
                    nodes.add(newNode);
                    break;
                }
                case "label": {
                    LabelNode newNode = new LabelNode(graph);
                    newNode.setLabel("New Label");
                    newNode.setX(getX() + 30);
                    newNode.setY(getY() - 60);
                    
                    newNode.addCellParentChangedListener(event -> newNode.setCellParent(getCellParent()));
                    
                    BPMNConnection newEdge = new BPMNConnection(graph, BPMNConnection.LinkType.LABEL);
                    newEdge.setSource(getUuid());
                    newEdge.setTarget(newNode.getUuid());
                    graph.addNode(newNode);
                    graph.addEdge(newEdge);
                    
                    newNode.addCellAddedListener(event -> newNode.selectCell());
                    nodes.add(newNode);
                    break;
                }
            }          
        });
        
        addRightClickCellListener(event -> {
           nodes.forEach(node -> node.clearCellOverlays());
        });
        
        addClickCellListener(event -> {
            nodes = graph.getBPMNNodes();
            nodes.forEach(node -> node.clearCellOverlays());
            if (isSymbol() || isSubprocess() || isTask() || isLabel())
                this.addPortToConnect();
            this.addOverlayButton();
        });
    }
    
    public boolean isSymbol() {
        return  this instanceof SymbolNode;
    }
    
    public boolean isLabel() {
        return this instanceof LabelNode;
    }
    
    public boolean isTask() {
        return this instanceof TaskNode;
    }
    
    public boolean isSubprocess() {
        return this instanceof SubProcessNode;
    }
    
    public boolean isSwimlane() {
        return this instanceof SwimlaneNode;
    } 
    
    private void addOverlayButton() {
        addOverlayButton("removeNode", "Remove", "MXGRAPH/images/delete.png", MxConstants.ALIGN_RIGHT, MxConstants.ALIGN_BOTTOM, 36, -8, 16, 16);
        if (isSymbol() || isSubprocess() || isTask()) {
            //addOverlayButton("event_intermediate", "Remove", "MXGRAPH/images/event_intermediate.png", MxConstants.ALIGN_RIGHT, MxConstants.ALIGN_TOP, 12, 10, ICON_WIDTH , ICON_HEIGHT);
            addOverlayButton("event_end", "Remove", "MXGRAPH/images/event_end.png", MxConstants.ALIGN_RIGHT, MxConstants.ALIGN_TOP, 12, 10, ICON_WIDTH, ICON_HEIGHT);
            addOverlayButton("fork", "Fork", "MXGRAPH/images/fork.svg", MxConstants.ALIGN_RIGHT, MxConstants.ALIGN_TOP, 36, 10, ICON_WIDTH, ICON_HEIGHT);
            addOverlayButton("exclusive", "Exclusive", "MXGRAPH/images/exclusive.svg", MxConstants.ALIGN_RIGHT, MxConstants.ALIGN_TOP, 60, 10, ICON_WIDTH, ICON_HEIGHT);
            addOverlayButton("task", "Task", "MXGRAPH/images/task.png", MxConstants.ALIGN_RIGHT, MxConstants.ALIGN_TOP, 84, 10, ICON_WIDTH, ICON_HEIGHT);
            if (showNewSubprocessOption)
                addOverlayButton("subprocess", "SubProcess", "MXGRAPH/images/subprocess.png", MxConstants.ALIGN_RIGHT, MxConstants.ALIGN_TOP, 84, 10, ICON_WIDTH, ICON_HEIGHT);
            addOverlayButton("label", "Label", "MXGRAPH/images/text.svg", MxConstants.ALIGN_RIGHT, MxConstants.ALIGN_TOP, 108, 10, ICON_WIDTH, ICON_HEIGHT);
        }
    }
}