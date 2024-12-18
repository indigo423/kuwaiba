/*
 *  Copyright 2010-2016 Neotropic SAS <contact@neotropic.co>.
 *
 *  Licensed under the EPL License, Version 1.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.eclipse.org/legal/epl-v10.html
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.kuwaiba.custom.map.buttons;

import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Button;
import org.kuwaiba.connection.Connection;
import org.kuwaiba.custom.overlays.ControlPointMarker;

/**
 * 
 * @author Johny Andres Ortega Ruiz <johny.ortega@kuwaiba.org>
 */
public class MarkerButton extends Button {
    public static final String NAME = "MarkerBtn";
    
    public MarkerButton(VaadinSession session, Connection connection) {
        super("Marker");
        
        addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                session.setAttribute(MarkerButton.NAME, true);
                session.setAttribute(ConnectionButton.NAME, false);
                session.setAttribute(MeasureButton.NAME, false);
                
                for (ControlPointMarker marker : connection.getControlPoints()) {
                    marker.setDraggable(true);
                }
            }
        });
        
    }
}
