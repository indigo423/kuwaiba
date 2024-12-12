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
import elemental.json.JsonObject;
import java.util.HashMap;
import java.util.Map;

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
     * The label Map column allows the sending of labels composed of a string and an object.
     */
    private HashMap<String, Object> labelMap;
    
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
    
    public GoogleChartColumn(HashMap<String, Object> labelMap, GoogleChartColumnType type) {
        this.labelMap = labelMap;
        this.type = type;
    }
    
    /**
     * Constructor of the chart columns. With an id, label and the data type.
     * @param id String ID of the column. Must be unique in the table.
     * Use basic alphanumeric characters, so the host page does not require fancy escapes to access the column.
     * @param label The label of the column. A string that identifies the attribute of the elements to be displayed. 
     * Example name, days. Feel free to use the one you prefer.
     * @param type The data type of the column values. Use the GoogleChartColumnType class options {@link GoogleChartColumnType}.
     */
    public GoogleChartColumn(String id, String label, GoogleChartColumnType type) {
        this.id = id;
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

    public HashMap<String, Object> getLabelMap() {
        return labelMap;
    }

    public void setLabelMap(HashMap<String, Object> labelMap) {
        this.labelMap = labelMap;
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
    
    public JsonObject toJson(boolean labelmap) {
        JsonObject attributes = Json.createObject();
        if (labelmap) {
            if (this.getLabelMap() != null && !this.getLabelMap().isEmpty()) {
                for (Map.Entry<String, Object> entry : this.getLabelMap().entrySet()) {
                    if (entry.getValue() instanceof String[]) {
                        String[] array = (String[]) entry.getValue();
                        JsonArray list = Json.createArray();
                        for (int i = 0; i < array.length; i++) {
                            list.set(i, array[i]);
                        }
                        attributes.put(entry.getKey(), list);
                    } else if (entry.getValue() instanceof String)
                        attributes.put(entry.getKey(), (String) entry.getValue());
                    else if (entry.getValue() instanceof Boolean)
                        attributes.put(entry.getKey(), (boolean) entry.getValue());
                }
            }
        }
        return attributes;
    }
}