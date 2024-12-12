/*
 *  Copyright 2010-2019 Neotropic SAS <contact@neotropic.co>.
 *  
 *  Licensed under the Apache License, Version 2.0 (the "License"); 
 *  you may not use this file except in compliance with the License. 
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software 
 *  distributed under the License is distributed on an "AS IS" BASIS, 
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 *  See the License for the specific language governing permissions and 
 *  limitations under the License.
 */
package com.neotropic.flow.component.mxgraph;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.DomEvent;

/**
 * 
 * @author Orlando Paz Duarte {@literal <orlando.paz@kuwaiba.org>}
 */
@DomEvent("right-click-cell")
public class MxGraphRightClickCellEvent extends ComponentEvent<MxGraphCell> {
    
    public MxGraphRightClickCellEvent(MxGraphCell source, boolean fromClient) {
        super(source, fromClient);
    }
    
}
