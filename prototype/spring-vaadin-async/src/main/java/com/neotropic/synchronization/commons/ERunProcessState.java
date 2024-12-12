/*
 *  Copyright 2022 Neotropic SAS. <contact@neotropic.co>.
 *  Licensed under the EPL License, Version 1.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *   http://www.eclipse.org/legal/epl-v10.html
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.neotropic.synchronization.commons;

import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;

/**
 * Enum run process state
 * @author Hardy Ryan Chingal Martinez {@literal <ryan.chingal@kuwaiba.org>}
 * created on 01/04/2022-09:22
 */
public enum ERunProcessState {
    NONE (new Icon(VaadinIcon.COG_O)),
    IN_PROGRESS(new Icon(VaadinIcon.START_COG)),
    STOP (new Icon(VaadinIcon.STOP_COG)),
    FINISH (new Icon(VaadinIcon.COG));

    private final Icon runProcessState;

    ERunProcessState(Icon runProcessState){
        this.runProcessState = runProcessState;
    }

    /**
     * Return description for enum value
     *
     * @return the state
     */
    public Icon getValue() {
        return runProcessState;
    }
}
