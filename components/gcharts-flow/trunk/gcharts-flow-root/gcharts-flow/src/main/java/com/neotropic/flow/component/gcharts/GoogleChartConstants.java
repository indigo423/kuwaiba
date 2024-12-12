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
 * Constants for chart.
 * @author Mauricio Ruiz {@literal <mauricio.ruiz@kuwaiba.org>}
 */
public class GoogleChartConstants {    
    public static class Property {
        /**
         * Defines chart type property name
         */
        public static final String TYPE = "type";
        /**
         * Defines columns property name
         */
        public static final String COLUMNS = "cols";
        /**
         * Defines rows property name
         */
        public static final String ROWS = "rows";
        /**
         * Defines options property name
         */
        public static final String OPTIONS = "options";
        /**
         * Defines data property name
         */
        public static final String DATA = "data";
    }
    
    public static class Column {
        /**
         * Defines label property name
         */
        public static final String LABEL = "label";
        /**
         * Defines type property name
         */
        public static final String TYPE = "type";
        /**
         * Defines id property name
         */
        public static final String ID = "id";
    }
    
    public static class Options {
        /**
         * Defines title property name
         */
        public static final String TITLE = "title";
    }
    
    public static class Data {
        /**
         * Defines role property name
         */
        public static final String ROLE = "role";
        /**
         * Defines role property name
         */
        public static final String STYLE = "style";
    }
}