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
 * Implements logic for task/activity nodes.
 * @author Orlando Paz  {@literal <orlando.paz@kuwaiba.org>} 
 */
public class TaskNode extends BPMNNode {

    public TaskNode(BPMNDiagram graph) {
        super(graph, BPMNNode.NODE_TYPE_TASK);
        init();
    }

    private void init() {
        setIsResizable(false);
        setUsePortToConnect(false);
        setWidth(100);
        setHeight(50);
        setLabelBackgroundColor(MxConstants.NONE);
        setRawStyle(MxConstants.STYLE_ROUNDED + "=1;" + 
                MxConstants.STYLE_STROKEWIDTH + "=2;"+ 
                MxConstants.STYLE_WHITE_SPACE + "=wrap;html=1;");
        setFillColor(MxConstants.NONE);
        setVerticalLabelPosition(MxConstants.ALIGN_CENTER);
    }
}