/*
 * Copyright 2010-2024 Neotropic SAS <contact@neotropic.co>.
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
package org.neotropic.util.visual.selectors;

import com.vaadin.flow.component.ComponentEvent;
import org.neotropic.kuwaiba.core.apis.persistence.business.BusinessObjectLight;

import java.util.List;

/**
 * The network resource event, useful to detect any change in the selection of a network resource.
 *
 * @author Mauricio Ruiz Beltrán {@literal <mauricio.ruiz@kuwaiba.org>}
 */
public class NetworkResourceChangeEvent extends ComponentEvent<NetworkResourceSelector> {

    private final BusinessObjectLight selectedObject;
    private final List<BusinessObjectLight> selectedObjects;

    public NetworkResourceChangeEvent(NetworkResourceSelector source, BusinessObjectLight selectedObject,
                                      List<BusinessObjectLight> selectedObjects, boolean fromClient) {
        super(source, fromClient);
        this.selectedObject = selectedObject;
        this.selectedObjects = selectedObjects;
    }

    public BusinessObjectLight getSelectedObject() {
        return selectedObject;
    }

    public List<BusinessObjectLight> getSelectedObjects() {
        return selectedObjects;
    }
}