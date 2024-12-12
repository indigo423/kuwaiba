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

/**
 * Used to define column data type.
 * @author Mauricio Ruiz {@literal <mauricio.ruiz@kuwaiba.org>}
 */
public enum GoogleChartColumnType {
    STRING("string"),
    NUMBER("number"),
    BOOLEAN("boolean"),
    DATE("date"),
    DATETIME("datetime"),
    TIMEOFDAY("timeofday"),
    HASHMAP("hashmap");

    private final String value;

    private GoogleChartColumnType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}