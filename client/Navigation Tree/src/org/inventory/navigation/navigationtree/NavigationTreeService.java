/*
 *  Copyright 2010-2020 Neotropic SAS <contact@neotropic.co>.
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
 *  under the License.
 */
package org.inventory.navigation.navigationtree;

import java.util.ArrayList;
import java.util.List;
import org.inventory.communications.CommunicationsStub;
import org.inventory.communications.core.LocalObjectLight;

/**
 * Provides the business logic for the related TopComponent
 * @author Charles Edward Bedon Cortazar {@literal <charles.bedon@kuwaiba.org>}
 */
public class NavigationTreeService {
    private NavigationTreeTopComponent component;
    private CommunicationsStub com = CommunicationsStub.getInstance();

    public NavigationTreeService(NavigationTreeTopComponent component){
        this.component = component;
    }
    
    public List<LocalObjectLight> getRootChildren(){
        List<LocalObjectLight> rootChildren = com.getObjectChildren("-1", -1);
        if(rootChildren != null)
            return rootChildren;
        else
            return new ArrayList<>();
    }
}