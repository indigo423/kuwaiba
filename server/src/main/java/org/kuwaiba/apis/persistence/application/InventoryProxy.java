/*
 *  Copyright 2010-2019 Neotropic SAS <contact@neotropic.co>.
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

package org.kuwaiba.apis.persistence.application;

import org.kuwaiba.apis.persistence.business.BusinessObject;

/**
 * Inventory proxies are used to integrate third party-applications with Kuwaiba. Sometimes these applications must refer to 
 * assets managed by Kuwaiba from another perspective (financial, for example). In these applications, multiple Kuwaiba inventory assets might be represented by
 * a single entity (e.g. a router with slots, boards and ports might just be something like "standard network device"). Proxies are used to map multiple inventory 
 * elements into a single entity. It's a sort of "impedance matching" between systems that refer to the same real world object from different perspectives.
 * @author Charles Edward Bedon Cortazar {@literal <charles.bedon@kuwaiba.org>}
 */
public class InventoryProxy extends BusinessObject {

    public InventoryProxy(BusinessObject proxyObject) {
        super(proxyObject.getClassName(), proxyObject.getId(), proxyObject.getName(), proxyObject.getAttributes());
    }
}
