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
 * Used to define chart type.
 * This class provides the different chart types that the component supports.
 * @author Mauricio Ruiz {@literal <mauricio.ruiz@kuwaiba.org>}
 */
public enum GoogleChartType {
    /**
     * Defines the chart type as a pie. 
     * Pie chart should have:
     * a first column of type string and second column of type number.
     */
    PIECHART("pie"),
    /**
     * Defines the chart type as a column
     */
    COLUMNCHART("column"),
    /**
     * Defines the chart type as a table
     */
    TABLECHART("table"),
    /**
     * Defines the chart type as a bar
     */
    BARCHART("bar"),
    /**
     * Define the chart type as a line
     */
    LINECHART("line"),
    /**
     * Define the chart type as a bubble. 
     * Bubble chart should have at least 3 columns.
     * Requires first column of type string.
     */
    BUBBLECHART("bubble"),
    /**
     * Define the chart type as a timeline.
     * Timeline chart should have at least 3 columns
     */
    TIMELINECHART("timeline"),
    /**
     * Define the chart type as an area.
     */
    AREACHART("area"),
    /**
     * Define the chart type as a geo.
     */
    GEOCHART("geo"),
    /**
     * Define the chart type as a histogram.
     */
    HISTOGRAMCHART("histogram"),
    /**
     * Define the chart type as a Gantt diagram.
     * Gannt diagram chart should have:
     * First column of type String, task id. 
     * Second column of type String,  task name.
     * Third column of type String, task resource/classification (optional).
     * Fourth column of type date, task start date.
     * Fifth column of type date, task end date.
     * Sixth column of type number, task duration. Null initial value (value calculated with start date and end date).
     * Seventh column of type number, task percent completed.
     * Eighth column of type string, task dependence (defines as sub-task of). 
     */
    GANTTCHART("gantt");

    private final String value;

    private GoogleChartType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}