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
package org.neotropic.flow.component;

import com.neotropic.flow.component.gcharts.GoogleChart;
import com.neotropic.flow.component.gcharts.GoogleChartColumn;
import com.neotropic.flow.component.gcharts.GoogleChartColumnObject;
import com.neotropic.flow.component.gcharts.GoogleChartColumnType;
import com.neotropic.flow.component.gcharts.GoogleChartOptions;
import com.neotropic.flow.component.gcharts.GoogleChartRow;
import com.neotropic.flow.component.gcharts.GoogleChartType;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

/**
 * Main View for google chart web component demo
 * @author Mauricio Ruiz {@literal <mauricio.ruiz@kuwaiba.org>}
 */
@Route
public class MainView extends VerticalLayout {
    
    public MainView() {
        drawPieChart();
        drawDonutChart();
        drawColumnChart();
        drawBarChart();
        drawBarChartWithTimeOfDay();
        drawTimelineChart();
        drawBubbleChart();
        drawAreaChart();
        drawLineChart();
        drawGeoChart();
        drawHistogramChart();
        drawTableChart();
    }
    
    private void drawPieChart() {
        /**
         * The example consists of: 
         * --two columns that containing the label and the column type 
         * --three rows of items that containing their name and value
         * --a title.
         *
         * Format required by the component:
         *  cols='[{"label":"Name", "type":"string"},{"label":"Number", "type":"number"}]' 
         *  rows='[["Monitors", 25],["Printers", 28],["keyboards", 31]]' 
         *  options='{\"title\": \"This is the chart title\"}'
         */
        
        // --> Init Columns
        GoogleChartColumn column1 = new GoogleChartColumn("Name", GoogleChartColumnType.STRING);
        GoogleChartColumn column2 = new GoogleChartColumn("Number", GoogleChartColumnType.NUMBER);
       
        List<GoogleChartColumn> listCols = new ArrayList<>();
        listCols.add(column1);
        listCols.add(column2);
        // End Columns <--
        
         // --> Init Rows            
        // First item
        GoogleChartColumnObject item1Column1 = new GoogleChartColumnObject(column1, "monitors", "Monitors");
        GoogleChartColumnObject item1Column2 = new GoogleChartColumnObject(column2, 25);
        // Second item
        GoogleChartColumnObject item2Column1 = new GoogleChartColumnObject(column1, "printers", "Printers");
        GoogleChartColumnObject item2Column2 = new GoogleChartColumnObject(column2, 28);
        // Third item
        GoogleChartColumnObject item3Column1 = new GoogleChartColumnObject(column1, "keyboards", "Keyboards");
        GoogleChartColumnObject item3Column2 = new GoogleChartColumnObject(column2, 31);
        
        List<GoogleChartColumnObject> listObject1 = new ArrayList<>();
        listObject1.add(0, item1Column1);
        listObject1.add(1, item1Column2);
        
        List<GoogleChartColumnObject> listObject2 = new ArrayList<>();
        listObject2.add(0, item2Column1);
        listObject2.add(1, item2Column2);
        
        List<GoogleChartColumnObject> listObject3 = new ArrayList<>();
        listObject3.add(0, item3Column1);
        listObject3.add(1, item3Column2);

        List<GoogleChartRow> listRows = new ArrayList();
        listRows.add(new GoogleChartRow(listObject1));
        listRows.add(new GoogleChartRow(listObject2));
        listRows.add(new GoogleChartRow(listObject3));
        // End Rows <--
        
        // Ttile
        String title = "This is the chart title";
        GoogleChartOptions option = new GoogleChartOptions(title); 
        // Pie Chart
        GoogleChart pieChart = new GoogleChart(GoogleChartType.PIECHART, listCols, listRows, option); 
        // Layout
        VerticalLayout lytPieChart = new VerticalLayout(new H4("Pie Chart"), pieChart);
        lytPieChart.setSpacing(false);
        add(lytPieChart);
    }
    
     private void drawDonutChart() {
        /**
         * The example consists of: 
         * --two columns that containing the label and the column type 
         * --three rows of items that containing their name and value
         * --a title.
         *
         * Format required by the component:
         *  cols='[{"label":"Name", "type":"string"},{"label":"Number", "type":"number"}]' 
         *  rows='[["Monitors", 25],["Printers", 28],["keyboards", 31]]' 
         *  options='{\"title\": \"This is the chart title\"}'
         */
        
        // --> Init Columns
        GoogleChartColumn column1 = new GoogleChartColumn("Name", GoogleChartColumnType.STRING);
        GoogleChartColumn column2 = new GoogleChartColumn("Number", GoogleChartColumnType.NUMBER);
       
        List<GoogleChartColumn> listCols = new ArrayList<>();
        listCols.add(column1);
        listCols.add(column2);
        // End Columns <--
        
         // --> Init Rows            
        // First item
        GoogleChartColumnObject item1Column1 = new GoogleChartColumnObject(column1, "monitors", "Monitors");
        GoogleChartColumnObject item1Column2 = new GoogleChartColumnObject(column2, 25);
        // Second item
        GoogleChartColumnObject item2Column1 = new GoogleChartColumnObject(column1, "printers", "Printers");
        GoogleChartColumnObject item2Column2 = new GoogleChartColumnObject(column2, 28);
        // Third item
        GoogleChartColumnObject item3Column1 = new GoogleChartColumnObject(column1, "keyboards", "Keyboards");
        GoogleChartColumnObject item3Column2 = new GoogleChartColumnObject(column2, 31);
        
        List<GoogleChartColumnObject> listObject1 = new ArrayList<>();
        listObject1.add(0, item1Column1);
        listObject1.add(1, item1Column2);
        
        List<GoogleChartColumnObject> listObject2 = new ArrayList<>();
        listObject2.add(0, item2Column1);
        listObject2.add(1, item2Column2);
        
        List<GoogleChartColumnObject> listObject3 = new ArrayList<>();
        listObject3.add(0, item3Column1);
        listObject3.add(1, item3Column2);

        List<GoogleChartRow> listRows = new ArrayList();
        listRows.add(new GoogleChartRow(listObject1));
        listRows.add(new GoogleChartRow(listObject2));
        listRows.add(new GoogleChartRow(listObject3));
        // End Rows <--
        
        // Ttile
        String title = "This is the chart title";
        HashMap<String, Object> options = new HashMap<>();
        options.put("title", title);
        options.put("pieHole", "0.3");
        List<String> colors = new ArrayList<>();
        colors.add("#e0440e");
        colors.add("#e6693e");
        colors.add("#ec8f6e");
        
        options.put("colors", new String[]{"rgba(140, 166, 45, 0.3)", "red", "pink"});
        GoogleChartOptions option = new GoogleChartOptions(options); 
        // Pie Chart
        GoogleChart donutChart = new GoogleChart(GoogleChartType.PIECHART, listCols, listRows, option); 
        donutChart.setHeight("400px");
        donutChart.setWidth("400px");        
        // Layout
        VerticalLayout lytPieChart = new VerticalLayout(new H4("Donut Chart"), donutChart);
        lytPieChart.setSpacing(false);
        
        add(lytPieChart);
    }
        
    private void drawColumnChart() {
        /**
         * The example consists of: 
         * --two columns that containing the label and the column type 
         * --three rows of items that containing their value
         * 
         * Format required by the component:
         *  cols='[{"label":"Name", "type":"string"},{"label":"Height", "type":"number"}]' 
         *  rows='[["User1", 1.75],["User2", 1.80],["User3", 1.92]]' 
         */
        
        // --> Init Columns
        GoogleChartColumn column1 = new GoogleChartColumn("Name", GoogleChartColumnType.STRING);
        GoogleChartColumn column2 = new GoogleChartColumn("Height", GoogleChartColumnType.NUMBER);
       
        List<GoogleChartColumn> listCols = new ArrayList<>();
        listCols.add(column1);
        listCols.add(column2);
        // End Columns <--
        
         // --> Init Rows            
        // First item
        GoogleChartColumnObject item1Column1 = new GoogleChartColumnObject(column1, "User1");
        GoogleChartColumnObject item1Column2 = new GoogleChartColumnObject(column2, 1.75);
        // Second item
        GoogleChartColumnObject item2Column1 = new GoogleChartColumnObject(column1, "User2");
        GoogleChartColumnObject item2Column2 = new GoogleChartColumnObject(column2, 1.80);
        // Third item
        GoogleChartColumnObject item3Column1 = new GoogleChartColumnObject(column1, "User3");
        GoogleChartColumnObject item3Column2 = new GoogleChartColumnObject(column2, 1.92);
        
        List<GoogleChartColumnObject> listObject1 = new ArrayList<>();
        listObject1.add(0, item1Column1);
        listObject1.add(1, item1Column2);
        
        List<GoogleChartColumnObject> listObject2 = new ArrayList<>();
        listObject2.add(0, item2Column1);
        listObject2.add(1, item2Column2);
        
        List<GoogleChartColumnObject> listObject3 = new ArrayList<>();
        listObject3.add(0, item3Column1);
        listObject3.add(1, item3Column2);

        List<GoogleChartRow> listRows = new ArrayList();
        listRows.add(new GoogleChartRow(listObject1));
        listRows.add(new GoogleChartRow(listObject2));
        listRows.add(new GoogleChartRow(listObject3));
        // End Rows <--
        
        // Column Chart
        GoogleChart columnChart = new GoogleChart(listCols, listRows);
        // Layout
        VerticalLayout lytColumnChart = new VerticalLayout(new H4("Column Chart"), columnChart);
        lytColumnChart.setSpacing(false);
        add(lytColumnChart);
    }
    
    private void drawBarChart() {
        /**
         * The example consists of: 
         * --two columns that containing the label and the column type 
         * --three rows of items that containing their value
         *
         * Format required by the component:
         *  cols='[{"label":"City", "type":"string"},{"label":"Average Altitude", "type":"number"}]' 
         *  rows='[["Cali", 1018],["Bogota", 2643],["Medellin", 1495]]' 
         */
        
        // --> Init Columns
        GoogleChartColumn column1 = new GoogleChartColumn("City", GoogleChartColumnType.STRING);
        GoogleChartColumn column2 = new GoogleChartColumn("Average Altitude", GoogleChartColumnType.NUMBER);
       
        List<GoogleChartColumn> listCols = new ArrayList<>();
        listCols.add(column1);
        listCols.add(column2);
        // End Columns <--
        
         // --> Init Rows            
        // First item
        GoogleChartColumnObject item1Column1 = new GoogleChartColumnObject(column1, "Cali");
        GoogleChartColumnObject item1Column2 = new GoogleChartColumnObject(column2, 1018);
        // Second item
        GoogleChartColumnObject item2Column1 = new GoogleChartColumnObject(column1, "Bogota");
        GoogleChartColumnObject item2Column2 = new GoogleChartColumnObject(column2, 2643);
        // Third item
        GoogleChartColumnObject item3Column1 = new GoogleChartColumnObject(column1, "Medellin");
        GoogleChartColumnObject item3Column2 = new GoogleChartColumnObject(column2, 1495);
        
        List<GoogleChartColumnObject> listObject1 = new ArrayList<>();
        listObject1.add(0, item1Column1);
        listObject1.add(1, item1Column2);
        
        List<GoogleChartColumnObject> listObject2 = new ArrayList<>();
        listObject2.add(0, item2Column1);
        listObject2.add(1, item2Column2);
        
        List<GoogleChartColumnObject> listObject3 = new ArrayList<>();
        listObject3.add(0, item3Column1);
        listObject3.add(1, item3Column2);

        List<GoogleChartRow> listRows = new ArrayList();
        listRows.add(new GoogleChartRow(listObject1));
        listRows.add(new GoogleChartRow(listObject2));
        listRows.add(new GoogleChartRow(listObject3));
        // End Rows <--
        
        // Bar Chart
        GoogleChart barChart = new GoogleChart(GoogleChartType.BARCHART, listCols, listRows);
        // Layout
        VerticalLayout lytBarChart = new VerticalLayout(new H4("Bar Chart"), barChart);
        lytBarChart.setSpacing(false);
        add(lytBarChart);
    }
    
    private void drawBarChartWithTimeOfDay() {
        /**
         * The example consists of: 
         * --two columns that containing the label and the column type 
         * --three rows of items that containing their value
         *
         * Format required by the component:
         *  cols='[
         *    {"label":"Time of Day", "type":"timeofday"},
         *    {"label":"Emails Received", "type":"number"}
         *  ]' 
         *  rows='[
         *    [[10, 15, 30], 17],
         *    [[12, 30, 50], 23],
         *    [[2, 10, 20], 42]
         *  ]' 
         */
        
         // --> Init Columns
        GoogleChartColumn column1 = new GoogleChartColumn("Time of Day", GoogleChartColumnType.TIMEOFDAY);
        GoogleChartColumn column2 = new GoogleChartColumn("Emails Received", GoogleChartColumnType.NUMBER);
        
        List<GoogleChartColumn> listCols = new ArrayList<>();
        listCols.add(column1);
        listCols.add(column2);
        // End Columns <--
                      
        // --> Init Rows
        LocalTime timeItem1 = LocalTime.of(10, 15, 30);
        LocalTime timeItem2 = LocalTime.of(12, 30, 50);
        LocalTime timeItem3 = LocalTime.of(2, 10, 20);
          
        // First item
        GoogleChartColumnObject item1Column1 = new GoogleChartColumnObject(column1, timeItem1);
        GoogleChartColumnObject item1Column2 = new GoogleChartColumnObject(column2, 17);
        // Second item
        GoogleChartColumnObject item2Column1 = new GoogleChartColumnObject(column1, timeItem2);
        GoogleChartColumnObject item2Column2 = new GoogleChartColumnObject(column2, 23);
        // Third item
        GoogleChartColumnObject item3Column1 = new GoogleChartColumnObject(column1, timeItem3);
        GoogleChartColumnObject item3Column2 = new GoogleChartColumnObject(column2, 42);
        
        List<GoogleChartColumnObject> listObject1 = new ArrayList<>();
        listObject1.add(0, item1Column1);
        listObject1.add(1, item1Column2);
        
        List<GoogleChartColumnObject> listObject2 = new ArrayList<>();
        listObject2.add(0, item2Column1);
        listObject2.add(1, item2Column2);
        
        List<GoogleChartColumnObject> listObject3 = new ArrayList<>();
        listObject3.add(0, item3Column1);
        listObject3.add(1, item3Column2);

        List<GoogleChartRow> listRows = new ArrayList();
        listRows.add(new GoogleChartRow(listObject1));
        listRows.add(new GoogleChartRow(listObject2));
        listRows.add(new GoogleChartRow(listObject3));
        // End Rows <--
        
        // Bar Chart
        GoogleChart barChart = new GoogleChart(GoogleChartType.BARCHART, listCols, listRows);
        // Layout
        VerticalLayout lytBarChart = new VerticalLayout(new H4("Bar Chart With Time Of Day"), barChart);
        lytBarChart.setSpacing(false);
        add(lytBarChart);
    }
    
    private void drawTimelineChart() {
        /**
         * The example consists of: 
         * --three columns that containing the label and the column type 
         * --three rows of items that containing their value
         * 
         * Format required by the component:
         * cols='[
         *   {"label":"Name", "type":"string"},
         *   {"label":"Start Date", "type":"date"},
         *   {"label":"End Date", "type":"date"}
         * ]'
         * rows='[
         *   ['User1', new Date(2001, 8, 5), new Date(2005, 6, 3)],
         *   ['User2', new Date(2010, 9, 4), new Date(2015, 2, 25)],
         *   ['User3', new Date(1801, 2, 4), new Date(1809, 2, 4)]        
         * ]'
         */
        
        // --> Init Columns
        GoogleChartColumn column1 = new GoogleChartColumn("Name", GoogleChartColumnType.STRING);
        GoogleChartColumn column2 = new GoogleChartColumn("Start Date", GoogleChartColumnType.DATETIME);
        GoogleChartColumn column3 = new GoogleChartColumn("End Date", GoogleChartColumnType.DATETIME);
        
        List<GoogleChartColumn> listCols = new ArrayList<>();
        listCols.add(column1);
        listCols.add(column2);
        listCols.add(column3);
        // End Columns <--
                      
        // --> Init Rows
        Calendar startDateItem1 = new GregorianCalendar();
        startDateItem1.set(2001, 8, 5);
        Calendar endDateItem1 = new  GregorianCalendar();
        endDateItem1.set(2005, 6, 3);
        Calendar startDateItem2 = new GregorianCalendar();
        startDateItem2.set(2010, 9, 4);
        Calendar endDateItem2 = new GregorianCalendar();
        endDateItem2.set(2015, 2, 25);
        Calendar startDateItem3 = new GregorianCalendar();
        startDateItem3.set(2020, 10, 22);
        Calendar endDateItem3 = new GregorianCalendar();
        endDateItem3.set(2025, 12, 31);
        
        // First item
        GoogleChartColumnObject item1Column1 = new GoogleChartColumnObject(column1, "User1");
        GoogleChartColumnObject item1Column2 = new GoogleChartColumnObject(column2, startDateItem1.getTime());
        GoogleChartColumnObject item1Column3 = new GoogleChartColumnObject(column3, endDateItem1.getTime());
        // Second item
        GoogleChartColumnObject item2Column1 = new GoogleChartColumnObject(column1, "User2");
        GoogleChartColumnObject item2Column2 = new GoogleChartColumnObject(column2, startDateItem2.getTime());
        GoogleChartColumnObject item2Column3 = new GoogleChartColumnObject(column3, endDateItem2.getTime());
        // Third item
        GoogleChartColumnObject item3Column1 = new GoogleChartColumnObject(column1, "User3");
        GoogleChartColumnObject item3Column2 = new GoogleChartColumnObject(column2, startDateItem3.getTime());
        GoogleChartColumnObject item3Column3 = new GoogleChartColumnObject(column3, endDateItem3.getTime());
        
        List<GoogleChartColumnObject> listObject1 = new ArrayList<>();
        listObject1.add(0, item1Column1);
        listObject1.add(1, item1Column2);
        listObject1.add(2, item1Column3);
        
        List<GoogleChartColumnObject> listObject2 = new ArrayList<>();
        listObject2.add(0, item2Column1);
        listObject2.add(1, item2Column2);
        listObject2.add(2, item2Column3);
        
        List<GoogleChartColumnObject> listObject3 = new ArrayList<>();
        listObject3.add(0, item3Column1);
        listObject3.add(1, item3Column2);
        listObject3.add(2, item3Column3);

        List<GoogleChartRow> listRows = new ArrayList();
        listRows.add(new GoogleChartRow(listObject1));
        listRows.add(new GoogleChartRow(listObject2));
        listRows.add(new GoogleChartRow(listObject3));
        // End Rows <--
        
        // Timeline Chart
        GoogleChart timelineChart = new GoogleChart(GoogleChartType.TIMELINECHART, listCols, listRows);
        // Layout
        VerticalLayout lytTimelineChart = new VerticalLayout(new H4("Timeline Chart"), timelineChart);
        lytTimelineChart.setSpacing(false);
        add(lytTimelineChart);
    }
    
    private void drawBubbleChart() {
        /**
         * The example consists of: 
         * --four columns that containing the label and the column type 
         * --three rows of items that containing their value
         * 
         * Format required by the component:
         * cols='[
         *   {"label":"ID", "type":"string"},
         *   {"label":"X", "type":"number"},
         *   {"label":"Y", "type":"number"},
         *   {"label":"Temperature", "type":"number"}
         * ]'
         * rows='[
         *   ['', 80, 167, 30],
         *   ['', 79, 136, 40],
         *   ['', 81, 170, 55]        
         * ]'
         */
        
        // --> Init Columns
        GoogleChartColumn column = new GoogleChartColumn("ID", GoogleChartColumnType.STRING);
        GoogleChartColumn column1 = new GoogleChartColumn("X", GoogleChartColumnType.NUMBER);
        GoogleChartColumn column2 = new GoogleChartColumn("Y", GoogleChartColumnType.NUMBER);
        GoogleChartColumn column3 = new GoogleChartColumn("Temperature", GoogleChartColumnType.NUMBER);
        
        List<GoogleChartColumn> listCols = new ArrayList<>();
        listCols.add(column);
        listCols.add(column1);
        listCols.add(column2);
        listCols.add(column3);
        // End Columns <--
                      
        // --> Init Rows    
        // First item
        GoogleChartColumnObject item1Column = new GoogleChartColumnObject(column, "");
        GoogleChartColumnObject item1Column1 = new GoogleChartColumnObject(column1, 80);
        GoogleChartColumnObject item1Column2 = new GoogleChartColumnObject(column2, 167);
        GoogleChartColumnObject item1Column3 = new GoogleChartColumnObject(column3, 30);
        // Second item
        GoogleChartColumnObject item2Column = new GoogleChartColumnObject(column, "");
        GoogleChartColumnObject item2Column1 = new GoogleChartColumnObject(column1, 79);
        GoogleChartColumnObject item2Column2 = new GoogleChartColumnObject(column2, 136);
        GoogleChartColumnObject item2Column3 = new GoogleChartColumnObject(column3, 40);
        // Third item
        GoogleChartColumnObject item3Column = new GoogleChartColumnObject(column, "");
        GoogleChartColumnObject item3Column1 = new GoogleChartColumnObject(column1, 81);
        GoogleChartColumnObject item3Column2 = new GoogleChartColumnObject(column2, 170);
        GoogleChartColumnObject item3Column3 = new GoogleChartColumnObject(column3, 55);
        
        List<GoogleChartColumnObject> listObject1 = new ArrayList<>();
        listObject1.add(0, item1Column);
        listObject1.add(1, item1Column1);
        listObject1.add(2, item1Column2);
        listObject1.add(3, item1Column3);
        
        List<GoogleChartColumnObject> listObject2 = new ArrayList<>();
        listObject2.add(0, item2Column);
        listObject2.add(1, item2Column1);
        listObject2.add(2, item2Column2);
        listObject2.add(3, item2Column3);
        
        List<GoogleChartColumnObject> listObject3 = new ArrayList<>();
        listObject3.add(0, item3Column);
        listObject3.add(1, item3Column1);
        listObject3.add(2, item3Column2);
        listObject3.add(3, item3Column3);

        List<GoogleChartRow> listRows = new ArrayList();
        listRows.add(new GoogleChartRow(listObject1));
        listRows.add(new GoogleChartRow(listObject2));
        listRows.add(new GoogleChartRow(listObject3));
        // End Rows <--
        
        // Bubble Chart
        GoogleChart bubbleChart = new GoogleChart(GoogleChartType.BUBBLECHART, listCols, listRows);
        // Layout
        VerticalLayout lytBubbleChart = new VerticalLayout(new H4("Bubble Chart"), bubbleChart);
        lytBubbleChart.setSpacing(false);
        add(lytBubbleChart);
    }
    
    private void drawAreaChart() {
        /**
         * The example consists of: 
         * --three columns that containing the label and the column type 
         * --three rows of items that containing their value
         * 
         * Format required by the component:
         * cols='[
         *   {"label":"Date", "type":"date"},
         *   {"label":"Sales", "type":"number"},
         *   {"label":"Expenses", "type":"number"}
         * ]'
         * rows='[
         *   [new Date(2001, 1, 31), 1000, 400],
         *   [new Date(2005, 9, 18), 660, 1220],
         *   [new Date(2010, 4, 2), 1170, 460]        
         * ]'
         */
        
        // --> Init Columns
        GoogleChartColumn column1 = new GoogleChartColumn("Date", GoogleChartColumnType.DATE);
        GoogleChartColumn column2 = new GoogleChartColumn("Sales", GoogleChartColumnType.NUMBER);
        GoogleChartColumn column3 = new GoogleChartColumn("Expenses", GoogleChartColumnType.NUMBER);
        
        List<GoogleChartColumn> listCols = new ArrayList<>();
        listCols.add(column1);
        listCols.add(column2);
        listCols.add(column3);
        // End Columns <--
                      
        // --> Init Rows
        Calendar dateItem1 = new GregorianCalendar();
        dateItem1.set(2001, 1, 31);
        Calendar dateItem2 = new  GregorianCalendar();
        dateItem2.set(2005, 9, 18);
        Calendar dateItem3 = new GregorianCalendar();
        dateItem3.set(2010, 4, 2);
        
        // First item
        GoogleChartColumnObject item1Column1 = new GoogleChartColumnObject(column1, dateItem1.getTime());
        GoogleChartColumnObject item1Column2 = new GoogleChartColumnObject(column2, 1000);
        GoogleChartColumnObject item1Column3 = new GoogleChartColumnObject(column3, 400);
        // Second item
        GoogleChartColumnObject item2Column1 = new GoogleChartColumnObject(column1, dateItem2.getTime());
        GoogleChartColumnObject item2Column2 = new GoogleChartColumnObject(column2, 660);
        GoogleChartColumnObject item2Column3 = new GoogleChartColumnObject(column3, 1120);
        // Third item
        GoogleChartColumnObject item3Column1 = new GoogleChartColumnObject(column1, dateItem3.getTime());
        GoogleChartColumnObject item3Column2 = new GoogleChartColumnObject(column2, 1170);
        GoogleChartColumnObject item3Column3 = new GoogleChartColumnObject(column3, 460);
        
        List<GoogleChartColumnObject> listObject1 = new ArrayList<>();
        listObject1.add(0, item1Column1);
        listObject1.add(1, item1Column2);
        listObject1.add(2, item1Column3);
        
        List<GoogleChartColumnObject> listObject2 = new ArrayList<>();
        listObject2.add(0, item2Column1);
        listObject2.add(1, item2Column2);
        listObject2.add(2, item2Column3);
        
        List<GoogleChartColumnObject> listObject3 = new ArrayList<>();
        listObject3.add(0, item3Column1);
        listObject3.add(1, item3Column2);
        listObject3.add(2, item3Column3);

        List<GoogleChartRow> listRows = new ArrayList();
        listRows.add(new GoogleChartRow(listObject1));
        listRows.add(new GoogleChartRow(listObject2));
        listRows.add(new GoogleChartRow(listObject3));
        // End Rows <--
        
        // Area Chart
        GoogleChart areaChart = new GoogleChart(GoogleChartType.AREACHART, listCols, listRows);
        // Layout
        VerticalLayout lytAreaChart = new VerticalLayout(new H4("Area Chart"), areaChart);
        lytAreaChart.setSpacing(false);
        add(lytAreaChart);
    }
    
    private void drawLineChart() {
        /**
         * The example consists of: 
         * --three columns that containing the label and the column type 
         * --three rows of items that containing their value
         * 
         * Format required by the component:
         * cols='[
         *   {"label":"Date", "type":"date"},
         *   {"label":"Sales", "type":"number"},
         *   {"label":"Expenses", "type":"number"}
         * ]'
         * rows='[
         *   [new Date(2001, 1, 31), 1000, 400],
         *   [new Date(2005, 9, 18), 660, 1220],
         *   [new Date(2010, 4, 2), 1170, 460]        
         * ]'
         */
        
        // --> Init Columns
        GoogleChartColumn column1 = new GoogleChartColumn("Date", GoogleChartColumnType.DATE);
        GoogleChartColumn column2 = new GoogleChartColumn("Sales", GoogleChartColumnType.NUMBER);
        GoogleChartColumn column3 = new GoogleChartColumn("Expenses", GoogleChartColumnType.NUMBER);
        
        List<GoogleChartColumn> listCols = new ArrayList<>();
        listCols.add(column1);
        listCols.add(column2);
        listCols.add(column3);
        // End Columns <--
                      
        // --> Init Rows
        Calendar dateItem1 = new GregorianCalendar();
        dateItem1.set(2001, 1, 31);
        Calendar dateItem2 = new  GregorianCalendar();
        dateItem2.set(2005, 9, 18);
        Calendar dateItem3 = new GregorianCalendar();
        dateItem3.set(2010, 4, 2);
        
        // First item
        GoogleChartColumnObject item1Column1 = new GoogleChartColumnObject(column1, dateItem1.getTime());
        GoogleChartColumnObject item1Column2 = new GoogleChartColumnObject(column2, 1000);
        GoogleChartColumnObject item1Column3 = new GoogleChartColumnObject(column3, 400);
        // Second item
        GoogleChartColumnObject item2Column1 = new GoogleChartColumnObject(column1, dateItem2.getTime());
        GoogleChartColumnObject item2Column2 = new GoogleChartColumnObject(column2, 660);
        GoogleChartColumnObject item2Column3 = new GoogleChartColumnObject(column3, 1120);
        // Third item
        GoogleChartColumnObject item3Column1 = new GoogleChartColumnObject(column1, dateItem3.getTime());
        GoogleChartColumnObject item3Column2 = new GoogleChartColumnObject(column2, 1170);
        GoogleChartColumnObject item3Column3 = new GoogleChartColumnObject(column3, 460);
        
        List<GoogleChartColumnObject> listObject1 = new ArrayList<>();
        listObject1.add(0, item1Column1);
        listObject1.add(1, item1Column2);
        listObject1.add(2, item1Column3);
        
        List<GoogleChartColumnObject> listObject2 = new ArrayList<>();
        listObject2.add(0, item2Column1);
        listObject2.add(1, item2Column2);
        listObject2.add(2, item2Column3);
        
        List<GoogleChartColumnObject> listObject3 = new ArrayList<>();
        listObject3.add(0, item3Column1);
        listObject3.add(1, item3Column2);
        listObject3.add(2, item3Column3);

        List<GoogleChartRow> listRows = new ArrayList();
        listRows.add(new GoogleChartRow(listObject1));
        listRows.add(new GoogleChartRow(listObject2));
        listRows.add(new GoogleChartRow(listObject3));
        // End Rows <--
        
        // Line ChartdrawGeoChart
        GoogleChart lineChart = new GoogleChart(GoogleChartType.LINECHART, listCols, listRows);
        // Layout
        VerticalLayout lytLineChart = new VerticalLayout(new H4("Line Chart"), lineChart);
        lytLineChart.setSpacing(false);
        add(lytLineChart);
    }
    
    private void drawGeoChart() {
        /**
         * The example consists of: 
         * --two columns that containing the label and the column type 
         * --three rows of items that containing their name and value
         *
         * Format required by the component:
         *  cols='[{"label":"Country", "type":"string"},{"label":"Popularity", "type":"number"}]' 
         *  rows='[["Colombia", 250],["RU", 700],["Canada", 500]]' 
         */
        
        // --> Init Columns
        GoogleChartColumn column1 = new GoogleChartColumn("Country", GoogleChartColumnType.STRING);
        GoogleChartColumn column2 = new GoogleChartColumn("Popularity", GoogleChartColumnType.NUMBER);
       
        List<GoogleChartColumn> listCols = new ArrayList<>();
        listCols.add(column1);
        listCols.add(column2);
        // End Columns <--
        
         // --> Init Rows            
        // First item
        GoogleChartColumnObject item1Column1 = new GoogleChartColumnObject(column1, "Colombia");
        GoogleChartColumnObject item1Column2 = new GoogleChartColumnObject(column2, 250);
        // Second item
        GoogleChartColumnObject item2Column1 = new GoogleChartColumnObject(column1, "RU");
        GoogleChartColumnObject item2Column2 = new GoogleChartColumnObject(column2, 700);
        // Third item
        GoogleChartColumnObject item3Column1 = new GoogleChartColumnObject(column1, "Canada");
        GoogleChartColumnObject item3Column2 = new GoogleChartColumnObject(column2, 500);
        
        List<GoogleChartColumnObject> listObject1 = new ArrayList<>();
        listObject1.add(0, item1Column1);
        listObject1.add(1, item1Column2);
        
        List<GoogleChartColumnObject> listObject2 = new ArrayList<>();
        listObject2.add(0, item2Column1);
        listObject2.add(1, item2Column2);
        
        List<GoogleChartColumnObject> listObject3 = new ArrayList<>();
        listObject3.add(0, item3Column1);
        listObject3.add(1, item3Column2);

        List<GoogleChartRow> listRows = new ArrayList();
        listRows.add(new GoogleChartRow(listObject1));
        listRows.add(new GoogleChartRow(listObject2));
        listRows.add(new GoogleChartRow(listObject3));
        // End Rows <--
        
        
        // Ttile
        String title = "This is the chart title";
        GoogleChartOptions option = new GoogleChartOptions(title); 
        // Geo Chart
        GoogleChart geoChart = new GoogleChart(GoogleChartType.GEOCHART, listCols, listRows, option); 
        // Layout
        VerticalLayout lytGeoChart = new VerticalLayout(new H4("Geo Chart"), geoChart);
        lytGeoChart.setSpacing(false);
        add(lytGeoChart);
    }

    private void drawHistogramChart() {
        /**
         * The example consists of: 
         * --two columns that containing the label and the column type 
         * --three rows of items that containing their name and value
         *
         * Format required by the component:
         *  cols='[{"label":"Dinosaur", "type":"string"},{"label":"Length", "type":"number"}]' 
         *  rows='[
         *    ["Acrocanthosaurus (top-spined lizard)", 12.2],
         *    ["Albertosaurus (Alberta lizard)", 9.1],
         *    ["Allosaurus (other lizard)", 12.2]
         *  ]' 
         *  options='{\"title\": \"Lengths of dinosaurs, in meters\"}'
         */
        
        // --> Init Columns
        GoogleChartColumn column1 = new GoogleChartColumn("Dinosaur", GoogleChartColumnType.STRING);
        GoogleChartColumn column2 = new GoogleChartColumn("Length", GoogleChartColumnType.NUMBER);
       
        List<GoogleChartColumn> listCols = new ArrayList<>();
        listCols.add(column1);
        listCols.add(column2);
        // End Columns <--
        
         // --> Init Rows            
        // First item
        GoogleChartColumnObject item1Column1 = new GoogleChartColumnObject(column1, "Acrocanthosaurus (top-spined lizard)");
        GoogleChartColumnObject item1Column2 = new GoogleChartColumnObject(column2, 12.2);
        // Second item
        GoogleChartColumnObject item2Column1 = new GoogleChartColumnObject(column1, "Albertosaurus (Alberta lizard)");
        GoogleChartColumnObject item2Column2 = new GoogleChartColumnObject(column2, 9.1);
        // Third item
        GoogleChartColumnObject item3Column1 = new GoogleChartColumnObject(column1, "Allosaurus (other lizard)");
        GoogleChartColumnObject item3Column2 = new GoogleChartColumnObject(column2, 12.2);
        
        List<GoogleChartColumnObject> listObject1 = new ArrayList<>();
        listObject1.add(0, item1Column1);
        listObject1.add(1, item1Column2);
        
        List<GoogleChartColumnObject> listObject2 = new ArrayList<>();
        listObject2.add(0, item2Column1);
        listObject2.add(1, item2Column2);
        
        List<GoogleChartColumnObject> listObject3 = new ArrayList<>();
        listObject3.add(0, item3Column1);
        listObject3.add(1, item3Column2);

        List<GoogleChartRow> listRows = new ArrayList();
        listRows.add(new GoogleChartRow(listObject1));
        listRows.add(new GoogleChartRow(listObject2));
        listRows.add(new GoogleChartRow(listObject3));
        // End Rows <--
             
        // Geo Chart
        GoogleChart histogramChart = new GoogleChart(GoogleChartType.HISTOGRAMCHART, 
                listCols, listRows, new GoogleChartOptions("Lengths of dinosaurs, in meters")); 
        // Layout
        VerticalLayout lytHistogramChart = new VerticalLayout(new H4("Histogram Chart"), histogramChart);
        lytHistogramChart.setSpacing(false);
        add(lytHistogramChart);
    }
    
    private void drawTableChart() {
        /**
         * Exercise for the demo.
         * The example consists of: 
         * --two columns that containing the label and the column type 
         * --three rows of items that containing their value
         * --a title.
         *
         * Format required by the component:
         *  cols='[{"label":"Name", "type":"string"},{"label":"Is Client", "type":"boolean"}]' 
         *  rows='[["User A", false],["User B", true],["User C", true]]'
         */
                  
       // --> Init Columns
        GoogleChartColumn column1 = new GoogleChartColumn("Name", GoogleChartColumnType.STRING);
        GoogleChartColumn column2 = new GoogleChartColumn("Is Client", GoogleChartColumnType.BOOLEAN);
       
        List<GoogleChartColumn> listCols = new ArrayList<>();
        listCols.add(column1);
        listCols.add(column2);
        // End Columns <--
        
         // --> Init Rows            
        // First item
        GoogleChartColumnObject item1Column1 = new GoogleChartColumnObject(column1, "User A");
        GoogleChartColumnObject item1Column2 = new GoogleChartColumnObject(column2, false);
        // Second item
        GoogleChartColumnObject item2Column1 = new GoogleChartColumnObject(column1, "User B");
        GoogleChartColumnObject item2Column2 = new GoogleChartColumnObject(column2, true);
        // Third item
        GoogleChartColumnObject item3Column1 = new GoogleChartColumnObject(column1, "User C");
        GoogleChartColumnObject item3Column2 = new GoogleChartColumnObject(column2, true);
        
        List<GoogleChartColumnObject> listObject1 = new ArrayList<>();
        listObject1.add(0, item1Column1);
        listObject1.add(1, item1Column2);
        
        List<GoogleChartColumnObject> listObject2 = new ArrayList<>();
        listObject2.add(0, item2Column1);
        listObject2.add(1, item2Column2);
        
        List<GoogleChartColumnObject> listObject3 = new ArrayList<>();
        listObject3.add(0, item3Column1);
        listObject3.add(1, item3Column2);

        List<GoogleChartRow> listRows = new ArrayList();
        listRows.add(new GoogleChartRow(listObject1));
        listRows.add(new GoogleChartRow(listObject2));
        listRows.add(new GoogleChartRow(listObject3));
        // End Rows <--
        
        // Table Chart
        GoogleChart tableChart = new GoogleChart(GoogleChartType.TABLECHART, listCols, listRows);
        // Layout
        VerticalLayout lytTableChart = new VerticalLayout(new H4("Table Chart"), tableChart);
        lytTableChart.setSpacing(false);
        add(lytTableChart); 
    }
}