/*
 *  Copyright 2010-2017 Neotropic SAS <contact@neotropic.co>.
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
package com.neotropic.kuwaiba.sync.connectors.snmp.reference;

import com.neotropic.kuwaiba.sync.model.TableData;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Johny Andres Ortega Ruiz <johny.ortega@kuwaiba.org>
 */
public class SnmpTableData extends TableData {
    public static HashMap<String, String> entPhysicalEntryOids;
    
    
    public SnmpTableData(String name, HashMap<String, List<String>> value) {
        super(name, value);
    }
    
    public static class EntPhysicalEntry {
        private static EntPhysicalEntry instance;
        private final HashMap<String, String> oids;
        
        private EntPhysicalEntry() {
            oids = new HashMap();
            oids.put("entPhysicalDescr", "1.3.6.1.2.1.47.1.1.1.1.2");
            oids.put("entPhysicalClass", "1.3.6.1.2.1.47.1.1.1.1.5");
            oids.put("entPhysicalContainedIn", "1.3.6.1.2.1.47.1.1.1.1.4");
            oids.put("entPhysicalName", "1.3.6.1.2.1.47.1.1.1.1.7");
            oids.put("entPhysicalHardwareRev", "1.3.6.1.2.1.47.1.1.1.1.8");
            oids.put("entPhysicalFirmwareRev", "1.3.6.1.2.1.47.1.1.1.1.9");
            oids.put("entPhysicalSerialNum", "1.3.6.1.2.1.47.1.1.1.1.11");
            oids.put("entPhysicalMfgName", "1.3.6.1.2.1.47.1.1.1.1.12");
            oids.put("entPhysicalModelName", "1.3.6.1.2.1.47.1.1.1.1.13");
        }
        
        public static EntPhysicalEntry getInstance() {
            return instance == null ? instance = new EntPhysicalEntry() : instance;
        }
        
        public HashMap<String, String> getOids() {
            return oids;            
        }
    }
}

