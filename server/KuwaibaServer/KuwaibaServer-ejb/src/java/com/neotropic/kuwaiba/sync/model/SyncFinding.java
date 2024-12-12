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
 */

package com.neotropic.kuwaiba.sync.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * This class represents a single result from comparing the info from a sync data 
 * source and the corresponding information in the inventory database
 * @author Charles Edward Bedon Cortazar <charles.bedon@kuwaiba.org>
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class SyncFinding {
    /**
     * An unexpected error was found while analyzing a particular situation
     */
    public static int EVENT_ERROR = 0;
    /**
     * A new element was detected in the sync data source
     */
    public static int EVENT_NEW = 1;
    /**
     * An element that was before in the sync data source is not any more
     */
    public static int EVENT_DELETE = 2;
    /**
     * The attributes of a given element/set of elements were updated
     */
    public static int EVENT_UPDATE = 3;
    
    /**
     * The type of difference found. See EVENT_XXX fields for possible values
     */
    private int type;
    /**
     * Textual description of the difference
     */
    private String description;
    /**
     * Relevant information that can be used to . Although its format depends on every 
     * particular implementation, a JSON/YML format is suggested
     */
    private String extraInformation;

    public SyncFinding() { }

    public SyncFinding(int type, String description, String extraInformation) {
        this.type = type;
        this.description = description;
        this.extraInformation = extraInformation;
    }
    
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExtraInformation() {
        return extraInformation;
    }

    public void setExtraInformation(String extraInformation) {
        this.extraInformation = extraInformation;
    }
}
