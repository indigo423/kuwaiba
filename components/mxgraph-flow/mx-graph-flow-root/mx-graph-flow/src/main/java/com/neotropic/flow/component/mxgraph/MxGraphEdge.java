/*
 * Copyright 2019 Johny Ortega.
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

package com.neotropic.flow.component.mxgraph;

import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.shared.Registration;


/**
 * The cell is an edge
 * @author Orlando Paz Duarte {@literal <orlando.paz@kuwaiba.org>}
 */
public class MxGraphEdge extends MxGraphCell {

    public MxGraphEdge() {
        super();
        setIsEdge(true);
        setIsVertex(false);
    }
    
    public Registration addPointsChangedListener(ComponentEventListener<MxGraphEdgePointsChanged> pointsChangedEvent) {
        return addListener(MxGraphEdgePointsChanged.class, pointsChangedEvent);
    }
}
