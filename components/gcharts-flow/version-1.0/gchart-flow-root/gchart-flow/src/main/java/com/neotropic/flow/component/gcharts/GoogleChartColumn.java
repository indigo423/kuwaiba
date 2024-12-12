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
import elemental.json.JsonObject;

/**
 * Each column consists of id, label and a data type.
 * This class provides information about the attributes that make up the column of the chart.
 * @author Mauricio Ruiz {@literal <mauricio.ruiz@kuwaiba.org>}
 */
public class GoogleChartColumn {
    /**
     * Data type. A string with the data type of the values of the column. 
     * The type can be one of the following: 'string', 'number', 'boolean', 'date', 'datetime', and 'timeofday'.
     */
    private GoogleChartColumnType type;
    /**
     * The column label is typically displayed as part of the visualization, 
     * for example as a column header in a table, or as a legend label in a pie chart.
     * If no value is specified, an empty string is assigned.
     */
    private String label;
    /**
     * A string with a unique identifier for the column. 
     * If no value is specified, an empty string is assigned.
     */
    private String id;
    
    /**
     * Constructor of the chart columns. only with the data type.
     * Great for pie charts, but not where it is necessary to identify the attribute of the elements to be displayed, like columns, bars, tables.
     * @param type The data type of the column values. Use the GoogleChartColumnType class options {@link GoogleChartColumnType}.
     */
    public GoogleChartColumn(GoogleChartColumnType type) {
        this.type = type;
    }
    
    /**
     * Constructor of the chart columns. With a label and the data type.
     * @param label The label of the column. A string that identifies the attribute of the elements to be displayed. 
     * Example name, days. Feel free to use the one you prefer.
     * @param type The data type of the column values. Use the GoogleChartColumnType class options {@link GoogleChartColumnType}.
     */
    public GoogleChartColumn(String label, GoogleChartColumnType type) {
        this.label = label;
        this.type = type;
    }

    public GoogleChartColumnType getType() {
        return type;
    }

    public void setType(GoogleChartColumnType type) {
        this.type = type;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public JsonObject toJson() {
        JsonObject attributes = Json.createObject();
        attributes.put(GoogleChartConstants.Column.TYPE, type.toString());
        if(this.getLabel() != null && !this.getLabel().isEmpty())
            attributes.put(GoogleChartConstants.Column.LABEL, label);
        if(this.getId() != null && !this.getId().isEmpty())
            attributes.put(GoogleChartConstants.Column.ID, id);
        return attributes;
    }
}