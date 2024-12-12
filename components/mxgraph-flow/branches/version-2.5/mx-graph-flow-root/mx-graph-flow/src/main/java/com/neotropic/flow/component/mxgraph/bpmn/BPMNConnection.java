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
import com.neotropic.flow.component.mxgraph.MxGraph;
import com.neotropic.flow.component.mxgraph.MxGraphEdge;

/**
 *
 * @author Orlando Paz  {@literal <orlando.paz@kuwaiba.org>} 
 */
public class BPMNConnection extends MxGraphEdge {

    public enum LinkType {
        SOLID,
        LABEL
    }
    
    LinkType linkType;
    MxGraph graph;
    
    public BPMNConnection(MxGraph graph) {
        this(graph, LinkType.SOLID);
    }
    
    public BPMNConnection(MxGraph graph, LinkType linkType) {
        this.linkType = linkType;
        this.graph = graph;
        init();
    }

    private void init() {
        setShowOverlayButtonsOnSelect(true);
        setEdgeStyle(MxConstants.EDGESTYLE_SEGMENT);
        setRawStyle("html=1;" + MxConstants.STYLE_ENDARROW + "="  + MxConstants.ARROW_CLASSIC_THIN+ ";" +
                  (linkType == LinkType.LABEL ? "dashed=1;" : ""));  
        
        addClickOverlayButtonListener((evt) -> {
            switch (evt.getButtonId()) {
                case "removeEdge":
                    graph.removeEdge(this);
                    break;
            }
        });
        addCellAddedListener(eventListener -> {
           addOverlayButton("removeEdge", "Remove", "MXGRAPH/images/delete.png", MxConstants.ALIGN_RIGHT, MxConstants.ALIGN_BOTTOM, 15, -15, 16 , 16); 
        });
    }
}