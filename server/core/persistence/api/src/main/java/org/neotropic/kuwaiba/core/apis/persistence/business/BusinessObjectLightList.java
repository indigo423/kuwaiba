/*
 *  Copyright 2010-2024 Neotropic SAS <contact@neotropic.co>
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License
 *  You may obtain a copy of the License at
 *
 *       https://apache.org/licenses/LICENSE-2.0.txt
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.neotropic.kuwaiba.core.apis.persistence.business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * This class represents a list of RemoteBusinessObjectLight instances. It's basically a wrapper for ArrayList&lt;RemoteBusinessObjectLight&gt; 
 * It's used only to improve code readability. This class extended from ArrayList of RemoteBusinessObjectLight at the beginning, but JAX-WS just refused to treat it as a List
 * @author Charles Edward Bedon Cortazar {@literal <charles.bedon@kuwaiba.org>}
 */
public class BusinessObjectLightList implements  Serializable {
    private List<BusinessObjectLight> list;

    public BusinessObjectLightList() {
        list = new ArrayList<>();
    }
    
    public boolean add(BusinessObjectLight element) {
        return list.add(element);
    }
    
    public boolean addAll(Collection<? extends BusinessObjectLight> elements) {
        return list.addAll(elements);
    }
    
    public boolean remove(BusinessObjectLight element) {
        return list.remove(element);
    }

    public List<BusinessObjectLight> getList() {
        return list;
    }

    public void setList(List<BusinessObjectLight> list) {
        this.list = list;
    }
}