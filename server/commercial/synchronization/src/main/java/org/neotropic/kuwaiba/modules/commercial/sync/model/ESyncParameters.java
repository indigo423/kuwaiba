/*
 * Copyright 2010-2024. Neotropic SAS <contact@neotropic.co>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.neotropic.kuwaiba.modules.commercial.sync.model;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Hardy Ryan Chingal Martinez <ryan.chingal@neotropic.co>
 */
public enum ESyncParameters {
    SSH_PORT ("port"),
    SSH_HOST ("host"),
    SSH_USER ("user"),
    SSH_PASSWORD ("password"),
    SNMP_ADDRESS ("ipAddress"),
    SNMP_PORT ("port"),
    ;
    private final String value;
    // Reverse-lookup map for getting a day from an abbreviation
    private static final Map<String, ESyncParameters> lookup = new HashMap<>();

    ESyncParameters(String value) {
        this.value = value;
    }
    /**
     * create map with enum values
     */
    static {
        for (ESyncParameters enumSelected : ESyncParameters.values()) {
            lookup.put(enumSelected.getValue(), enumSelected);
        }
    }

    /**
     * return value for Enum Tag
     * @return value; String
     */
    public String getValue() {
        return value;
    }

    /**
     * Return enum based on register values
     * @param abbreviation;String; values of any Tag
     * @return enum Tag;ESyncParameters
     */
    public static ESyncParameters get(String abbreviation) {
        return lookup.get(abbreviation);
    }

}
