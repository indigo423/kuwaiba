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

import com.neotropic.flow.component.mxgraph.MxGraph;
import com.neotropic.flow.component.mxgraph.MxGraphNode;
import com.neotropic.flow.component.mxgraph.bpmn.SymbolNode.SymbolType;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Custom Mxgraph class to represent a BPMN Diagram
 * @author Orlando Paz  {@literal <orlando.paz@kuwaiba.org>} 
 */
public class BPMNDiagram extends MxGraph {

    boolean showSymbolContextMenu;
    
    public BPMNDiagram() {
        showSymbolContextMenu = true;
        configDiagram();
    }

    public boolean isShowSymbolContextMenu() {
        return showSymbolContextMenu;
    }

    public void setShowSymbolContextMenu(boolean showSymbolContextMenu) {
        this.showSymbolContextMenu = showSymbolContextMenu;
    }
         
    private void configDiagram() {
         setDropEnabled(true);
         setBPMNModeEnabled(true);
         addEdgeCompleteListener(edgeCompleteEvent -> {
             BPMNConnection newEdge = new BPMNConnection(this); 
             BPMNNode source = (BPMNNode) getNodes().stream().filter(item -> item.getUuid().equals(edgeCompleteEvent.getSourceId())).findFirst().get();
             BPMNNode target = (BPMNNode) getNodes().stream().filter(item -> item.getUuid().equals(edgeCompleteEvent.getTargetId())).findFirst().get();
             if (source.getGenericType().equals(BPMNNode.NODE_TYPE_LABEL) || target.getGenericType().equals(BPMNNode.NODE_TYPE_LABEL))
                 newEdge = new BPMNConnection(this, BPMNConnection.LinkType.LABEL); 
             newEdge.setSource(edgeCompleteEvent.getSourceId());
             newEdge.setTarget(edgeCompleteEvent.getTargetId());
             addEdge(newEdge);
         });
         
        if (showSymbolContextMenu) {
            for (SymbolType st : SymbolNode.SymbolType.values()) {
                 addContextMenuItem(st.name(), st.name(), st.getImgUrl(), MxGraph.TARGET_CONTEXT_MENU_ITEM_VERTEX, st.getNodeType());
            }
        }

        addContextMenuItemSelectedListener(listener -> {
            if (listener.getItem() != null && listener.getCellId() != null) {
                MxGraphNode node =  getNodes().stream().filter(item -> item.getUuid().equals(listener.getCellId())).findFirst().get();
                node.setImage(SymbolNode.SymbolType.valueOf(listener.getItem()).getImgUrl());
            }
        });
    }
    
    public List<BPMNNode> getBPMNNodes() {
        return getNodes().stream().filter(item -> item instanceof BPMNNode)
                .map(item -> (BPMNNode)item).collect(Collectors.toList());
    }
    
    public List<SwimlaneNode> getSwimlanes() {
        return getNodes().stream().filter(item -> item instanceof SwimlaneNode)
                .map(item -> (SwimlaneNode)item).collect(Collectors.toList());
    }
    
    public List<SymbolNode> getSymbolNodes() {
        return getNodes().stream().filter(item -> item instanceof SymbolNode)
                .map(item -> (SymbolNode)item).collect(Collectors.toList());
    }
    
    public List<SubProcessNode> getSubProcess() {
        return getNodes().stream().filter(item -> item instanceof SubProcessNode)
                .map(item -> (SubProcessNode)item).collect(Collectors.toList());
    }
    
    public List<BPMNConnection> getBPMNConnection() {
        return getEdges().stream().filter(item -> item instanceof BPMNConnection)
                .map(item -> (BPMNConnection)item).collect(Collectors.toList());
    }
}