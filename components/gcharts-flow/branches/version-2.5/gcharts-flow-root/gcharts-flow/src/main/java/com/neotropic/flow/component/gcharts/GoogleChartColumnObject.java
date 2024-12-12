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
import elemental.json.JsonBoolean;
import elemental.json.JsonNumber;
import elemental.json.JsonString;
import elemental.json.JsonValue;
import java.time.LocalTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Each cell in the table is described by an object with the following properties: value, f.
 * @author Mauricio Ruiz {@literal <mauricio.ruiz@kuwaiba.org>}
 */
public class GoogleChartColumnObject {
    /**
     * The cell value.
     */
    private Object v;
    /**
     * A string version of the v value, formatted for display.
     */
    private Object f;
    /**
     * Required to validate the data types of the object
     */
    private GoogleChartColumn column;
    
    /**
     * Rows data.The data type should match the column data type.
     * Recommended for date and boolean data.
     * For date type data, it is recommended to use Calendar or GregorianCalendar.
     * @param column The column to which the object belongs
     * @param v The cell value.
     */
    public GoogleChartColumnObject(GoogleChartColumn column, Object v) {
        this.column = column;
        this.v = v;
    }
    
    /**
     * Rows data.The data type should match the column data type.
     * @param column The column to which the object belongs
     * @param v The cell value.
     * @param f The cell value formatted for display. Must match v data type.
     */
    public GoogleChartColumnObject(GoogleChartColumn column, Object v, Object f) {
        this.column = column;
        this.v = v;
        this.f = f;
    }

    public Object getV() {
        return v;
    }

    public void setV(Object value) {
        this.v = value;
    }

    public Object getF() {
        return f;
    }

    public void setF(Object f) {
        this.f = f;
    }
    
    public JsonValue toJson() {
        if (column.getType().equals(GoogleChartColumnType.NUMBER) && this.getV() instanceof Number) {
            if (this.getF() != null) {
                Double valueDouble = Double.parseDouble(String.valueOf(this.getF()));
                JsonNumber number = Json.create(valueDouble);
                return number;
            } else {
                Double valueDouble = Double.parseDouble(String.valueOf(this.getV()));
                JsonNumber number = Json.create(valueDouble);
                return number;
            }
        } else if (column.getType().equals(GoogleChartColumnType.STRING) && this.getV() instanceof String) {
            if (this.getF() != null) {
                String valueString = String.valueOf(this.getF());
                JsonString string = Json.create(valueString);
                return string;
            } else {
                String valueString = String.valueOf(this.getV());
                JsonString string = Json.create(valueString);
                return string;
            }
        } else if (column.getType().equals(GoogleChartColumnType.BOOLEAN) && this.getV() instanceof Boolean) {
            if (this.getF() != null) {
                Boolean valueBoolean = Boolean.parseBoolean(String.valueOf(this.getF()));
                JsonBoolean aBoolean = Json.create(valueBoolean);
                return aBoolean;
            } else {
                Boolean valueBoolean = Boolean.parseBoolean(String.valueOf(this.getV()));
                JsonBoolean aBoolean = Json.create(valueBoolean);
                return aBoolean;
            }
        } else if (column.getType().equals(GoogleChartColumnType.DATE) || column.getType().equals(GoogleChartColumnType.DATETIME)
                && this.getV() instanceof Date) {
            if (this.getV() == null)
                return null;
            else {
                Date time = (Date) this.getV();
                long valueLong = time.getTime();
                JsonNumber number = Json.create(valueLong);
                return number;
            }
        } else if(column.getType().equals(GoogleChartColumnType.TIMEOFDAY) && this.getV() instanceof LocalTime) {
            LocalTime valueTime = (LocalTime) this.getV();
            JsonArray time = Json.createArray();
            time.set(0, valueTime.getHour());
            time.set(1, valueTime.getMinute());
            time.set(2, valueTime.getSecond());
            return time;
        } else if(column.getType().equals(GoogleChartColumnType.HASHMAP) && this.getV() instanceof HashMap) {
            HashMap<String, Object> map = (HashMap) this.getV();
            String hashMap = "";
            for (Map.Entry<String, Object> entry: map.entrySet()) {
                if (entry.getValue() instanceof String || entry.getValue() instanceof Boolean || entry.getValue() instanceof Number)
                    hashMap += String.format("%s:%s;", String.valueOf(entry.getKey()), String.valueOf(entry.getValue()));
            }        
            JsonString string = Json.create(hashMap);
            return string;
        }
        return Json.createNull();
    }
}