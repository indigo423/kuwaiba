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

import java.util.List;
import java.util.stream.Collectors;

/**
 * Abstract class of nodes that can contain other nodes inside them.
 * @author Orlando Paz  {@literal <orlando.paz@kuwaiba.org>} 
 */
public abstract class ContainerNode extends BPMNNode {

    public ContainerNode(BPMNDiagram graph, String nodeType) {
        super(graph, nodeType);
    }
    
    List<BPMNNode> getBPMNNodes() {
        return graph.getBPMNNodes().stream().filter(item -> item.getCellParent().equals(getUuid())).collect(Collectors.toList());
    }
    
    List<SwimlaneNode> getSwimlanes() {
        return graph.getNodes().stream().filter(item -> item.getCellParent().equals(getUuid()) && item instanceof SwimlaneNode)
                .map(item -> (SwimlaneNode)item).collect(Collectors.toList());
    }
    
    List<SymbolNode> getSymbols() {
        return graph.getNodes().stream().filter(item -> item.getCellParent().equals(getUuid()) &&  item instanceof SymbolNode)
                .map(item -> (SymbolNode)item).collect(Collectors.toList());
    }
    
    List<SymbolNode> getTasks() {
        return graph.getNodes().stream().filter(item -> item.getCellParent().equals(getUuid()) &&  item instanceof TaskNode)
                .map(item -> (SymbolNode)item).collect(Collectors.toList());
    }
    
    List<SubProcessNode> getSubprocess() {
        return graph.getNodes().stream().filter(item -> item.getCellParent().equals(getUuid()) &&  item instanceof SubProcessNode)
                .map(item -> (SubProcessNode)item).collect(Collectors.toList());
    }
    
    List<LabelNode> getLabels() {
        return graph.getNodes().stream().filter(item -> item.getCellParent().equals(getUuid()) &&  item instanceof LabelNode)
                .map(item -> (LabelNode)item).collect(Collectors.toList());
    }
}