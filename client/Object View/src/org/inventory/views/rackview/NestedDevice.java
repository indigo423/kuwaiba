/*
 *  Copyright 2010-2020 Neotropic SAS <contact@neotropic.co>.
 * 
 *   Licensed under the EPL License, Version 1.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *        http://www.eclipse.org/legal/epl-v10.html
 * 
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */
package org.inventory.views.rackview;

import org.inventory.views.rackview.widgets.NestedDeviceWidget;

/**
 * A interface to represents a nested device in a containment hierarchy
 * @author Johny Andres Ortega Ruiz {@literal <johny.ortega@kuwaiba.org>}
 */
public interface NestedDevice {
    /**
     * Gets the device parent
     */
    public NestedDeviceWidget getParent();
    /**
     * Sets the parent of the current device
     */
    public void setParent(NestedDeviceWidget parent);
}
