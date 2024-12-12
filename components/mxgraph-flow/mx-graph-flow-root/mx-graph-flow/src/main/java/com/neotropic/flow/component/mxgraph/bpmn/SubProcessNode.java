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

/**
 * Implements logic for sub Process type nodes.
 * @author Orlando Paz  {@literal <orlando.paz@kuwaiba.org>} 
 */
public class SubProcessNode extends ContainerNode {

    public SubProcessNode(BPMNDiagram graph) {
        super(graph, BPMNNode.NODE_TYPE_SUBPROCESS);
        init();
    }
    
    private void init() {
        setIsFoldable(Boolean.TRUE);
        setUsePortToConnect(false);
        setWidth(190);
        setHeight(120);
        setRawStyle(MxConstants.STYLE_ROUNDED + "=1;" + MxConstants.STYLE_STROKEWIDTH + "=2;" 
                 + MxConstants.STYLE_DASHED + "=1;" + MxConstants.STYLE_DASH_PATTERN + "=1 1;" );
        setFillColor(MxConstants.NONE);
        SymbolNode startNode = new SymbolNode(graph, SymbolNode.SymbolType.Event);
        startNode.setCellParent(getUuid());
        startNode.setX(10);
        startNode.setY(35);
        addCellAddedListener(eventListener -> {
            graph.addNode(startNode);
        });
    }
}