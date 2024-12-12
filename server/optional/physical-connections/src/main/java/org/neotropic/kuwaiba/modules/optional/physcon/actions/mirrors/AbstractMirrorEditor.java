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
package org.neotropic.kuwaiba.modules.optional.physcon.actions.mirrors;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.server.Command;

/**
 * Component to edit mirrors release.
 * @author Johny Andres Ortega Ruiz {@literal <johny.ortega@kuwaiba.org>}
 */
public abstract class AbstractMirrorEditor extends HorizontalLayout {
    protected final PortComponent source;
    protected final PortComponent target;
    protected final VerticalLayout lytParent;
    protected final Command cmdUpdateFreePorts;
    
    public AbstractMirrorEditor(PortComponent source, PortComponent target, VerticalLayout lytParent, Command cmdUpdateFreePorts) {
        this.source = source;
        this.target = target;
        this.lytParent = lytParent;
        this.cmdUpdateFreePorts = cmdUpdateFreePorts;
    }
}
