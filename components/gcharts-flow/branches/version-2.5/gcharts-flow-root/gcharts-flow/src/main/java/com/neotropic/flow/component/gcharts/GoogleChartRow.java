/*
 * Copyright 2010-2021 Neotropic SAS <contact@neotropic.co>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.neotropic.flow.component.gcharts;

import elemental.json.Json;
import elemental.json.JsonArray;
import java.util.List;

/**
 * Each row object has one required property, which is an array of cells in that row.
 * @author Mauricio Ruiz {@literal <mauricio.ruiz@kuwaiba.org>}
 */
public class GoogleChartRow {
    private List<GoogleChartColumnObject> columnObjects;

    /**
     * Cells in the row array should be in the same order as their column descriptions in GoogleChartColumn class {@link GoogleChartColumn}.
     * @param columnObjects. The column object. See GoogleChartColumnObject class {@link GoogleChartColumnObject}.
     */
    public GoogleChartRow(List<GoogleChartColumnObject> columnObjects) {
        this.columnObjects = columnObjects;
    }
    
    public List<GoogleChartColumnObject> getColumnObjects() {
        return columnObjects;
    }

    public void setColumnObjects(List<GoogleChartColumnObject> columnObjects) {
        this.columnObjects = columnObjects;
    }
    
    /**
     * This method converts objects list in Json format.
     * @return Objects list in Json format.
     */
    public JsonArray toJson() {
         if (columnObjects != null) {
             JsonArray result = Json.createArray();
             for(int i = 0; i < columnObjects.size(); i++) 
                 result.set(i, columnObjects.get(i).toJson());
             return result;
         }
        return null;
    }
}