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

package org.neotropic.kuwaiba.modules.commercial.sync.components;

import lombok.Data;
import org.neotropic.kuwaiba.modules.commercial.sync.model.SyncResult;

import java.util.UUID;

/**
 * Instances of this class are intended to inform about the results of a synchronization process.
 * In principle a simple list of strings would suffice, however this class could be extended in
 * the future to provide mechanisms to retry a sync action
 *
 * @author Charles Edward Bedon Cortazar {@literal <charles.bedon@kuwaiba.org>}
 */
@Data
public class SyncResultItem {
    /**
     * An unexpected error was found while execute the sync action
     */
    public static int TYPE_ERROR = 0;
    /**
     * The sync action was executed successfully
     */
    public static int TYPE_SUCCESS = 1;
    /**
     * The sync action was executed with warnings
     */
    public static int TYPE_WARNING = 2;
    /**
     * The sync action was executed and an information message was generated
     */
    public static int TYPE_INFORMATION = 3;
    /**
     * The type of result. Gives feedback of the status of the executed action. See TYPE_* for possible values
     */
    private int type;
    /**
     * The description of the action that was performed
     */
    private String actionDescription;
    /**
     * The textual description of the result of that action
     */
    private String result;
    /**
     * Data source configuration id
     */
    private long dataSourceId;

    /**
     * Item ID
     */
    private String itemId;

    public SyncResultItem(SyncResult syncResult) {
        this.itemId = UUID.randomUUID().toString();
        this.dataSourceId = syncResult.getDataSourceId();
        this.type = syncResult.getType();
        this.actionDescription = syncResult.getActionDescription();
        this.result = syncResult.getResult();
    }

    public String getTypeAsString() {
        switch (type) {
            case 0: // TYPE_ERROR
                return "Error";
            case 1: // TYPE_SUCCESS
                return "Success";
            case 2: // TYPE_WARNING
                return "Warning";
            case 3: // TYPE_INFORMATION
                return "Information";
            default:
                return "Error";
        }
    }
}