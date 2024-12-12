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
 * Implements logic for swimlane nodes.
 * @author Orlando Paz  {@literal <orlando.paz@kuwaiba.org>} 
 */
public class SwimlaneNode extends ContainerNode {

    public SwimlaneNode(BPMNDiagram graph) {
        super(graph, BPMNNode.NODE_TYPE_SWIMLANE);
        init();
    }
    
    private void init() {
        setShape(MxConstants.SHAPE_SWIMLANE);
        setIsFoldable(Boolean.TRUE);
        setFillColor("#f0f3f7");
        setLabelBackgroundColor(MxConstants.NONE);
        setRawStyle("swimlane;html=1;startSize=40;" + MxConstants.STYLE_HORIZONTAL + "=0;" + MxConstants.STYLE_VERTICAL_ALIGN + "='middle';");    
        setWidth(1000);
        setHeight(200);
        setVerticalLabelPosition(MxConstants.ALIGN_MIDDLE);
        setLabelPosition(MxConstants.ALIGN_CENTER);
        setVerticalAlign(MxConstants.ALIGN_MIDDLE);
        
        addClickOverlayButtonListener((evt) -> {
            switch (evt.getButtonId()) {
                case "addSwimlane": {
                     SwimlaneNode swimlaneNode = new SwimlaneNode(graph);
                     graph.addNode(swimlaneNode);
                }
            }
        });
        addCellAddedListener(evtAdded -> {
//            addOverlayButton("addSwimlane", "AddSwimlane", "MXGRAPH/images/swimlane.png", MxConstants.ALIGN_RIGHT, MxConstants.ALIGN_BOTTOM, 15, -8, 22 , 22);           
        }); 
    }    
}