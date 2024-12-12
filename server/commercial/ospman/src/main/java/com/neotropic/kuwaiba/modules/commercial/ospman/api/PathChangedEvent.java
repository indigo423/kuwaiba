/*
 *  Copyright 2010-2024 Neotropic SAS <contact@neotropic.co>.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       https://apache.org/licenses/LICENSE-2.0.txt
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.neotropic.kuwaiba.modules.commercial.ospman.api;

import java.util.List;
import java.util.function.Consumer;

/**
 * Path Changed Event to add/fire in the Outside Plant View.
 * @author Johny Andres Ortega Ruiz {@literal <johny.ortega@kuwaiba.org>}
 */
public class PathChangedEvent {
    public interface PathChangedEventListener extends Consumer<PathChangedEvent> {
    }
    private final PathChangedEventListener listener;
    private final List<GeoCoordinate> controlPoints;
    
    public PathChangedEvent(List<GeoCoordinate> controlPoints, PathChangedEventListener listener) {
        this.controlPoints = controlPoints;
        this.listener = listener;
    } 
    
    public List<GeoCoordinate> getControlPoints() {
        return controlPoints;
    }
    
    public PathChangedEventListener getListener() {
        return listener;
    }
}
