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

package org.kuwaiba.apis.persistence.business;

import java.util.HashMap;

/**
 * Contains all the attributes (and their values) of an inventory object. Complex data types, like 
 * list types should be interpreted using the class metadata
 * @author Charles Edward Bedon Cortazar {@literal <charles.bedon@kuwaiba.org>}
 */
public class BusinessObject extends BusinessObjectLight {

    /**
     * Map of attributes and values. Multiple selection list types are represented by comma (",") separated long values. These values
     * are the ids of the list types the object is referring to
     */
    private HashMap <String, String> attributes;

    public BusinessObject(String className, String id, String name) {
        super(className, id, name);
    }

    public BusinessObject(String className, String id, String name, HashMap<String, String> attributes) {
        super(className, id, name);
        this.attributes = attributes;
    }


    public HashMap<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(HashMap<String, String> attributes) {
        this.attributes = attributes;
    }
}
