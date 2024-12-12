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
 * Implements logic for symbol type nodes.
 * @author Orlando Paz  {@literal <orlando.paz@kuwaiba.org>} 
 */
public class SymbolNode extends BPMNNode {
    SymbolType type;

    public enum SymbolType {
        //Some sybol type are commented while defining if they are going to be used 
        Event("MXGRAPH/images/event.png", BPMNNode.NODE_TYPE_EVENT),
        //Event_Intermediate("MXGRAPH/images/event_intermediate.png", BPMNNode.NODE_TYPE_EVENT),
        Event_End("MXGRAPH/images/event_end.png", BPMNNode.NODE_TYPE_EVENT),
        //	Timer("MXGRAPH/images/timer.png", BPMNNode.NODE_TYPE_EVENT),
        //	Message("MXGRAPH/images/message.png", BPMNNode.NODE_TYPE_EVENT),
        //	Message_Intermediate("MXGRAPH/images/message_intermediate.png", BPMNNode.NODE_TYPE_EVENT),
        //      Message_End("MXGRAPH/images/message_end.png", BPMNNode.NODE_TYPE_EVENT),
        //      Terminate("MXGRAPH/images/terminate.png", BPMNNode.NODE_TYPE_EVENT),
        //	Link("MXGRAPH/images/link.png", BPMNNode.NODE_TYPE_LINK),
        //	Rule("MXGRAPH/images/rule.png", BPMNNode.NODE_TYPE_EVENT),
        //	Multiple("MXGRAPH/images/multiple.png", BPMNNode.NODE_TYPE_GATEWAY),
        //	Error("MXGRAPH/images/error.png", BPMNNode.NODE_TYPE_EVENT),
        //	Cancel_End("MXGRAPH/images/cancel_end.png", BPMNNode.NODE_TYPE_EVENT),
        //	Cancel_Intermediate("MXGRAPH/images/cancel_intermediate.png", BPMNNode.NODE_TYPE_EVENT),
        Fork("MXGRAPH/images/fork.svg", BPMNNode.NODE_TYPE_GATEWAY),
        Merge("MXGRAPH/images/merge.png", BPMNNode.NODE_TYPE_GATEWAY),
        Exclusive("MXGRAPH/images/exclusive.svg", BPMNNode.NODE_TYPE_GATEWAY),;
//	Inclusive("MXGRAPH/images/inclusive.png", BPMNNode.NODE_TYPE_GATEWAY);
//    
        String imgUrl;
        String nodeType;

        private SymbolType(String imgUrl, String nodeType) {
            this.imgUrl = imgUrl;
            this.nodeType = nodeType;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public String getNodeType() {
            return nodeType;
        }

    };
    
    public SymbolNode(BPMNDiagram graph, SymbolType type) {
        super(graph, type.nodeType);
        this.type = type;
        init();
    }

    private void init() {
        setShape(MxConstants.SHAPE_IMAGE);
        setWidth(50);
        setHeight(50);
        setX(10);
        setY(10);
        setIsResizable(false);
        setImage(type.getImgUrl());
        setTag(type.getNodeType());
        setUsePortToConnect(false); 
    }
    
    public boolean isEvent() {
        return type.getNodeType().equals(NODE_TYPE_EVENT);
    }
    
    public boolean isGateway() {
        return type.getNodeType().equals(NODE_TYPE_GATEWAY);
    }

    public SymbolType getType() {
        return type;
    }
}