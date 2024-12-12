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
 * This class supports customization tools.
 * @author Mauricio Ruiz {@literal <mauricio.ruiz@kuwaiba.org>}
 */
public class GoogleChartOptions {
    /**
     * The tittle for chart, if exists.
     */
    private String title;
    /**
     * Chart options
     */
    private HashMap<String, Object> options;
    
    public GoogleChartOptions() { }
    
    /**
     * Customizes the chart with a title.
     * @param title The customization value 
     */
    public GoogleChartOptions(String title) {
        this.title = title;
    }
    
     /**
     * Customizes the chart with a title and/or personalized colors.
     * @param options list of options key -> value 
     */
    public GoogleChartOptions(HashMap<String, Object> options) {
        this.options = options;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
    public JsonObject toJson() {
        JsonObject attributes = toJson(options);
        if (this.getTitle() != null && !this.getTitle().isEmpty())
            attributes.put(GoogleChartConstants.Options.TITLE, title);
        return attributes;
    }
    
    
    private JsonObject toJson(Map<String, Object> options) {
        JsonObject attributes = Json.createObject();
        if (options != null && !options.isEmpty()) {
            for (Map.Entry<String, Object> entry : options.entrySet()) {
                if (entry.getValue() instanceof String[]) {
                    String[] array = (String[])entry.getValue();
                    JsonArray list = Json.createArray();
                    for (int i = 0; i < array.length; i++)
                        list.set(i, array[i]);
                    attributes.put(entry.getKey(), list);
                }
                if (entry.getValue() instanceof Map)
                    attributes.put(entry.getKey(), toJson((Map) entry.getValue()));
                else if (entry.getValue() instanceof String)
                    attributes.put(entry.getKey(), (String) entry.getValue());
                else if (entry.getValue() instanceof Boolean)
                    attributes.put(entry.getKey(), (boolean) entry.getValue());
            }
        }
        return attributes;
    }
}