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
import com.neotropic.flow.component.gcharts.GoogleChartConstants;
import com.neotropic.flow.component.gcharts.GoogleChartOptions;
import com.neotropic.flow.component.gcharts.GoogleChartRow;
import com.neotropic.flow.component.gcharts.GoogleChartType;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
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
    /**
     * Object to save values of each column
     */
    private GoogleChartColumn column0;
    private GoogleChartColumn column1;
    private GoogleChartColumn column2;
    private GoogleChartColumn column3;
    private GoogleChartColumn column4;
    private GoogleChartColumn column5;
    private GoogleChartColumn column6;
    private GoogleChartColumn column7;
    /**
     * Object to save column list
     */
    private List<GoogleChartColumn> listCols;
    /**
     * Object to save rows list
     */
    private List<GoogleChartRow> listRows;
    
    public MainView() {
        setSizeFull();
        setSpacing(false);
        drawBarChart();
        drawColumnChart();
        drawPieChart();
        drawGanttChart();
    }
    
    private void drawBarChart() {
        /**
         * The example consists of: 
         * --three columns that containing the label or labelmap and the column type 
         * --three rows of items that containing their value
         *
         * Format required by the component:
         *  cols='[{"label":"City", "type":"string"},{"label":"Average Altitude", "type":"number"}, {"labelmap":"role:style", "type": "hashmap"}]' 
         *  rows='[["Cali", 1018, "color:#e5e4e2"],["Bogota", 2643, "stroke-color:#703593"],["Medellin", 1495, "stroke-color:#703593"]]' 
         * 
         * Note: For this format with three columns it is necessary to send the value of data as true in the GoogleChart constructor.
         */
        
        // --> Init Columns
        column1 = new GoogleChartColumn("City", GoogleChartColumnType.STRING);
        column2 = new GoogleChartColumn("Average Altitude", GoogleChartColumnType.NUMBER);
        HashMap<String, Object> role = new HashMap<>();
        role.put(GoogleChartConstants.Data.ROLE, GoogleChartConstants.Data.STYLE);
        column3 = new GoogleChartColumn(role, GoogleChartColumnType.HASHMAP);
        
        listCols = new ArrayList<>();
        listCols.add(column1);
        listCols.add(column2);
        listCols.add(column3);
        // End Columns <--
        
         // --> Init Rows            
        // First item
        GoogleChartColumnObject item1Column1 = new GoogleChartColumnObject(column1, "Cali");
        GoogleChartColumnObject item1Column2 = new GoogleChartColumnObject(column2, 1018);
        HashMap<String, Object> item1Column3Map = new HashMap<>();
        item1Column3Map.put("color", "#1ABC9C");
        GoogleChartColumnObject item1Column3 = new GoogleChartColumnObject(column3, item1Column3Map);
        
        // Second item
        GoogleChartColumnObject item2Column1 = new GoogleChartColumnObject(column1, "Bogota");
        GoogleChartColumnObject item2Column2 = new GoogleChartColumnObject(column2, 2643);
        HashMap<String, Object> item2Column3Map = new HashMap<>();
        item2Column3Map.put("stroke-color", "#703593");
        item2Column3Map.put("stroke-width", 4);
        item2Column3Map.put("fill-color", "#C5A5CF");
        GoogleChartColumnObject item2Column3 = new GoogleChartColumnObject(column3, item2Column3Map);
        
        // Third item
        GoogleChartColumnObject item3Column1 = new GoogleChartColumnObject(column1, "Medellin");
        GoogleChartColumnObject item3Column2 = new GoogleChartColumnObject(column2, 1495);
        HashMap<String, Object> item3Column3Map = new HashMap<>();
        item3Column3Map.put("stroke-color", "#871B47");
        item3Column3Map.put("stroke-width", 8);
        item3Column3Map.put("fill-color", "#BC5679");
        GoogleChartColumnObject item3Column3 = new GoogleChartColumnObject(column3, item3Column3Map);
        
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

        listRows = new ArrayList();
        listRows.add(new GoogleChartRow(listObject1));
        listRows.add(new GoogleChartRow(listObject2));
        listRows.add(new GoogleChartRow(listObject3));
        // End Rows <--
        
        //  --> Init customization
        HashMap<String, Object> legendValue = new HashMap<>();
        legendValue.put("position", "none"); 
         
        HashMap<String, Object> options = new HashMap<>();
        options.put("legend", legendValue);
        
        GoogleChartOptions option = new GoogleChartOptions(options); 
        // End customization <--
        
        // Bar Chart
        GoogleChart barChart = new GoogleChart(GoogleChartType.BARCHART, listCols, listRows, option, true);
        // Layout
        VerticalLayout lytBarChart = new VerticalLayout(new H4("Bar Chart"), barChart);
        lytBarChart.setSpacing(false);
        add(lytBarChart);
    }
    
    private void drawColumnChart() {
        /**
         * The example consists of: 
         * --three columns that containing the label and the column type 
         * --three rows of items that containing their value
         * 
         * Format required by the component:
         *  cols='[{"label":"Name", "type":"string"},{"label":"Height", "type":"number"},{"labelmap":"role:style", "type": "hashmap"}]' 
         *  rows='[["User1", 1.75, "color:#e5e4e2"],["User2", 1.80, "stroke-color:#703593"],["User3", 1.92, "stroke-color:#703593"]]' 
         * 
         * Note: For this format with three columns it is necessary to send the value of data as true in the GoogleChart constructor.
         */
        
        // --> Init Columns
        column1 = new GoogleChartColumn("Name", GoogleChartColumnType.STRING);
        column2 = new GoogleChartColumn("Height", GoogleChartColumnType.NUMBER);
        
        HashMap<String, Object> role = new HashMap<>();
        role.put(GoogleChartConstants.Data.ROLE, GoogleChartConstants.Data.STYLE);
        column3 = new GoogleChartColumn(role, GoogleChartColumnType.HASHMAP);
       
        listCols = new ArrayList<>();
        listCols.add(column1);
        listCols.add(column2);
        listCols.add(column3);
        // End Columns <--
        
         // --> Init Rows            
        // First item
        GoogleChartColumnObject item1Column1 = new GoogleChartColumnObject(column1, "User1");
        GoogleChartColumnObject item1Column2 = new GoogleChartColumnObject(column2, 1.75);
        
        HashMap<String, Object> item1Column3Map = new HashMap<>();
        item1Column3Map.put("color", "#1ABC9C");
        GoogleChartColumnObject item1Column3 = new GoogleChartColumnObject(column3, item1Column3Map);
        // Second item
        GoogleChartColumnObject item2Column1 = new GoogleChartColumnObject(column1, "User2");
        GoogleChartColumnObject item2Column2 = new GoogleChartColumnObject(column2, 1.80);
        HashMap<String, Object> item2Column3Map = new HashMap<>();
        item2Column3Map.put("stroke-color", "#703593");
        item2Column3Map.put("stroke-width", 4);
        item2Column3Map.put("fill-color", "#C5A5CF");
        GoogleChartColumnObject item2Column3 = new GoogleChartColumnObject(column3, item2Column3Map);
        
        // Third item
        GoogleChartColumnObject item3Column1 = new GoogleChartColumnObject(column1, "User3");
        GoogleChartColumnObject item3Column2 = new GoogleChartColumnObject(column2, 1.92);
         HashMap<String, Object> item3Column3Map = new HashMap<>();
        item3Column3Map.put("stroke-color", "#871B47");
        item3Column3Map.put("stroke-width", 8);
        item3Column3Map.put("fill-color", "#BC5679");
        GoogleChartColumnObject item3Column3 = new GoogleChartColumnObject(column3, item3Column3Map);
        
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

        listRows = new ArrayList();
        listRows.add(new GoogleChartRow(listObject1));
        listRows.add(new GoogleChartRow(listObject2));
        listRows.add(new GoogleChartRow(listObject3));
        // End Rows <--
      
        //  --> Init customization
        HashMap<String, Object> legendValue = new HashMap<>();
        legendValue.put("position", "none"); 
         
        HashMap<String, Object> options = new HashMap<>();
        options.put("legend", legendValue);
        
        GoogleChartOptions option = new GoogleChartOptions(options); 
        // End customization <--
       
        // Column Chart
        GoogleChart columnChart = new GoogleChart(GoogleChartType.COLUMNCHART, listCols, listRows, option, true);
        // Layout
        VerticalLayout lytColumnChart = new VerticalLayout(new H4("Column Chart"), columnChart);
        lytColumnChart.setSpacing(false);
        add(lytColumnChart);
    }
    
    /*
     * A Gantt chart can have tasks with multiple dependencies. 
     * To create a Gantt chart that has no dependencies, 
     * make sure that the last value for each row in your DataTable is set to null.
     * Added click listener event, this event returns a specific value of the element. 
     * It can be the id or a different value as defined by the user previously.
     */
    private void drawGanttChart() {
        /**
         * The example consists of: 
         * --eight columns that containing the label and the column type 
         * --six rows of items that containing their name and value
         *
         * Format required by the component:
         *  cols='[
         *          {"label":"Task ID", "type":"string"},
         *          {"label":"Task Name", "type":"string"},
         *          {"label":"Resource", "type":"string"},
         *          {"label":"Start", "type":"date"},
         *          {"label":"End", "type":"date"},
         *          {"label":"Duration", "type":"number"},
         *          {"label":"Percent Complete", "type":"number"},
         *          {"label":"Dependencies", "type":"string"}
         *  ]' 
         *  rows='[
         *          ["toTrain", "Walk to train stop", "walk", new Date(2021, 8, 10), new Date(2021, 8, 15), null, 100, null],
         *          ["music", "Listen to music", "music", new Date(2021, 8, 12), new Date(2021, 8, 13), null, 100, null],
         *          ["wait", "Wait for train", "wait", new Date(2021, 8, 14), new Date(2021, 8, 15), null, 100, "toTrain"],
         *          ["train", "Train ride", "train", new Date(2021, 8, 16), new Date(2021, 8, 17), null, 75, "wait"],
         *          ["toWork", "Walk to work", "walk", new Date(2021, 8, 18), new Date(2021, 8, 19), null, 0, "train, toTrain"],
         *          ["work", "Sit down at desk", "work", new Date(2021, 8, 20), new Date(2021, 8, 21), null, 20, "toWork"]
         *  ]' 
         */
        
        ganttDataColumns();
        // --> Init Rows
        // First item
        Calendar startDateItem1 = new GregorianCalendar(2021, 8, 10);
        Calendar endDateItem1 = new  GregorianCalendar(2021, 8, 15);
        GoogleChartColumnObject item1Column0 = new GoogleChartColumnObject(column0, "toTrain");
        GoogleChartColumnObject item1Column1 = new GoogleChartColumnObject(column1, "Walk to train stop");
        GoogleChartColumnObject item1Column2 = new GoogleChartColumnObject(column2, "walk");
        GoogleChartColumnObject item1Column3 = new GoogleChartColumnObject(column3, startDateItem1.getTime());
        GoogleChartColumnObject item1Column4 = new GoogleChartColumnObject(column4, endDateItem1.getTime()); 
        GoogleChartColumnObject item1Column5 = new GoogleChartColumnObject(column5, null);
        GoogleChartColumnObject item1Column6 = new GoogleChartColumnObject(column6, 100);
        GoogleChartColumnObject item1Column7 = new GoogleChartColumnObject(column7, null);
        // Second item
        Calendar startDateItem2 = new GregorianCalendar(2021, 8, 12);
        Calendar endDateItem2 = new GregorianCalendar(2021, 8, 13);
        GoogleChartColumnObject item2Column0 = new GoogleChartColumnObject(column0, "music");
        GoogleChartColumnObject item2Column1 = new GoogleChartColumnObject(column1, "Listen to music");
        GoogleChartColumnObject item2Column2 = new GoogleChartColumnObject(column2, "music");
        GoogleChartColumnObject item2Column3 = new GoogleChartColumnObject(column3, startDateItem2.getTime());
        GoogleChartColumnObject item2Column4 = new GoogleChartColumnObject(column4, endDateItem2.getTime());
        GoogleChartColumnObject item2Column5 = new GoogleChartColumnObject(column5, null);
        GoogleChartColumnObject item2Column6 = new GoogleChartColumnObject(column6, 100);
        GoogleChartColumnObject item2Column7 = new GoogleChartColumnObject(column7, null);
        // Third item
        Calendar startDateItem3 = new GregorianCalendar(2021, 8, 14);
        Calendar endDateItem3 = new GregorianCalendar(2021,8, 15);
        GoogleChartColumnObject item3Column0 = new GoogleChartColumnObject(column0, "wait");
        GoogleChartColumnObject item3Column1 = new GoogleChartColumnObject(column1, "Wait for train");
        GoogleChartColumnObject item3Column2 = new GoogleChartColumnObject(column2, "wait");
        GoogleChartColumnObject item3Column3 = new GoogleChartColumnObject(column3, startDateItem3.getTime());
        GoogleChartColumnObject item3Column4 = new GoogleChartColumnObject(column4, endDateItem3.getTime());
        GoogleChartColumnObject item3Column5 = new GoogleChartColumnObject(column5, null);
        GoogleChartColumnObject item3Column6 = new GoogleChartColumnObject(column6, 100);
        GoogleChartColumnObject item3Column7 = new GoogleChartColumnObject(column7, "toTrain");
        // Fourth item
        Calendar startDateItem4 = new GregorianCalendar(2021, 8, 16);
        Calendar endDateItem4 = new GregorianCalendar(2021, 8, 17);
        GoogleChartColumnObject item4Column0 = new GoogleChartColumnObject(column0, "train");
        GoogleChartColumnObject item4Column1 = new GoogleChartColumnObject(column1, "Train ride");
        GoogleChartColumnObject item4Column2 = new GoogleChartColumnObject(column2, "train");
        GoogleChartColumnObject item4Column3 = new GoogleChartColumnObject(column3, startDateItem4.getTime());
        GoogleChartColumnObject item4Column4 = new GoogleChartColumnObject(column4, endDateItem4.getTime());
        GoogleChartColumnObject item4Column5 = new GoogleChartColumnObject(column5, null); 
        GoogleChartColumnObject item4Column6 = new GoogleChartColumnObject(column6, 75);
        GoogleChartColumnObject item4Column7 = new GoogleChartColumnObject(column7, "wait");
        // Fifth item
        Calendar startDateItem5 = new GregorianCalendar(2021, 8, 18);
        Calendar endDateItem5 = new GregorianCalendar(2021, 8, 19);
        GoogleChartColumnObject item5Column0 = new GoogleChartColumnObject(column0, "toWork");
        GoogleChartColumnObject item5Column1 = new GoogleChartColumnObject(column1, "Walk to work");
        GoogleChartColumnObject item5Column2 = new GoogleChartColumnObject(column2, "walk");
        GoogleChartColumnObject item5Column3 = new GoogleChartColumnObject(column3, startDateItem5.getTime());
        GoogleChartColumnObject item5Column4 = new GoogleChartColumnObject(column4, endDateItem5.getTime());
        GoogleChartColumnObject item5Column5 = new GoogleChartColumnObject(column5, null);
        GoogleChartColumnObject item5Column6 = new GoogleChartColumnObject(column6, 0);
        GoogleChartColumnObject item5Column7 = new GoogleChartColumnObject(column7, "train, toTrain");
        // Sixth item
        Calendar startDateItem6 = new GregorianCalendar(2021, 8, 20);
        Calendar endDateItem6 = new GregorianCalendar(2021, 8, 21);
        GoogleChartColumnObject item6Column0 = new GoogleChartColumnObject(column0, "work");
        GoogleChartColumnObject item6Column1 = new GoogleChartColumnObject(column1, "Sit down at desk");
        GoogleChartColumnObject item6Column2 = new GoogleChartColumnObject(column2, "work");
        GoogleChartColumnObject item6Column3 = new GoogleChartColumnObject(column3, startDateItem6.getTime());
        GoogleChartColumnObject item6Column4 = new GoogleChartColumnObject(column4, endDateItem6.getTime());
        GoogleChartColumnObject item6Column5 = new GoogleChartColumnObject(column5, null);
        GoogleChartColumnObject item6Column6 = new GoogleChartColumnObject(column6, 20);
        GoogleChartColumnObject item6Column7 = new GoogleChartColumnObject(column7, "toWork");
        
        List<GoogleChartColumnObject> listObject1 = new ArrayList<>();
        listObject1.add(item1Column0);
        listObject1.add(item1Column1);
        listObject1.add(item1Column2);
        listObject1.add(item1Column3);
        listObject1.add(item1Column4);
        listObject1.add(item1Column5);
        listObject1.add(item1Column6);
        listObject1.add(item1Column7);
        
        List<GoogleChartColumnObject> listObject2 = new ArrayList<>();
        listObject2.add(item2Column0);
        listObject2.add(item2Column1);
        listObject2.add(item2Column2);
        listObject2.add(item2Column3);
        listObject2.add(item2Column4);
        listObject2.add(item2Column5);
        listObject2.add(item2Column6);
        listObject2.add(item2Column7);
        
        List<GoogleChartColumnObject> listObject3 = new ArrayList<>();
        listObject3.add(item3Column0);
        listObject3.add(item3Column1);
        listObject3.add(item3Column2);
        listObject3.add(item3Column3);
        listObject3.add(item3Column4);
        listObject3.add(item3Column5);
        listObject3.add(item3Column6);
        listObject3.add(item3Column7);
        
        List<GoogleChartColumnObject> listObject4 = new ArrayList<>();
        listObject4.add(item4Column0);
        listObject4.add(item4Column1);
        listObject4.add(item4Column2);
        listObject4.add(item4Column3);
        listObject4.add(item4Column4);
        listObject4.add(item4Column5);
        listObject4.add(item4Column6);
        listObject4.add(item4Column7);
        
        List<GoogleChartColumnObject> listObject5 = new ArrayList<>();
        listObject5.add(item5Column0);
        listObject5.add(item5Column1);
        listObject5.add(item5Column2);
        listObject5.add(item5Column3);
        listObject5.add(item5Column4);
        listObject5.add(item5Column5);
        listObject5.add(item5Column6);
        listObject5.add(item5Column7);
        
        List<GoogleChartColumnObject> listObject6 = new ArrayList<>();
        listObject6.add(item6Column0);
        listObject6.add(item6Column1);
        listObject6.add(item6Column2);
        listObject6.add(item6Column3);
        listObject6.add(item6Column4);
        listObject6.add(item6Column5);
        listObject6.add(item6Column6);
        listObject6.add(item6Column7);
        
        listRows = new ArrayList();
        listRows.add(new GoogleChartRow(listObject1));
        listRows.add(new GoogleChartRow(listObject2));
        listRows.add(new GoogleChartRow(listObject3));
        listRows.add(new GoogleChartRow(listObject4));
        listRows.add(new GoogleChartRow(listObject5));
        listRows.add(new GoogleChartRow(listObject6));
        // End Rows <--
         
        //  --> Init customization
        HashMap<String, Object> arrowValue = new HashMap<>();
        arrowValue.put("color", "green"); 
         
        HashMap<String, Object> arrow = new HashMap<>();
        arrow.put("arrow", arrowValue);
         
        HashMap<String, Object> options = new HashMap<>();
        options.put("gantt", arrow);
        options.put("height", 200*100);
        
        GoogleChartOptions option = new GoogleChartOptions(options); 
        // End customization <--
        
        // Gantt Chart
        GoogleChart ganttChart = new GoogleChart(GoogleChartType.GANTTCHART, listCols, listRows, option); 
        ganttChart.setSizeFull();
        
        /**
        * Identify previously what information column zero contains.
        * To obtain the value of column zero of each row, where the row represents the object,
        * a lambda function is needed where the getSelectedConsumer method is called.
        */
        ganttChart.addChartClickListener(event -> {
            ganttChart.getSelectedConsumer(value -> {
                Notification.show(value); // displays the resulting value 
            });
        });
        
        // Layout
        VerticalLayout lytGanttChart = new VerticalLayout(new H4("Gantt Chart"));
        lytGanttChart.setSpacing(false);
        
        H4 header = new H4("Gantt Chart");    
        VerticalLayout lytContent = new VerticalLayout(header);
        
        add(lytContent, ganttChart);
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
        column0 = new GoogleChartColumn("Name", GoogleChartColumnType.STRING);
        column1 = new GoogleChartColumn("Number", GoogleChartColumnType.NUMBER);
       
        listCols = new ArrayList<>();
        listCols.add(column0);
        listCols.add(column1);
        // End Columns <--
        
         // --> Init Rows            
        // First item
        GoogleChartColumnObject item1Column1 = new GoogleChartColumnObject(column0, "monitors", "Monitors");
        GoogleChartColumnObject item1Column2 = new GoogleChartColumnObject(column1, 25);
        // Second item
        GoogleChartColumnObject item2Column1 = new GoogleChartColumnObject(column0, "printers", "Printers");
        GoogleChartColumnObject item2Column2 = new GoogleChartColumnObject(column1, 28);
        // Third item
        GoogleChartColumnObject item3Column1 = new GoogleChartColumnObject(column0, "keyboards", "Keyboards");
        GoogleChartColumnObject item3Column2 = new GoogleChartColumnObject(column1, 31);
        
        List<GoogleChartColumnObject> listObject1 = new ArrayList<>();
        listObject1.add(item1Column1);
        listObject1.add(item1Column2);
        
        List<GoogleChartColumnObject> listObject2 = new ArrayList<>();
        listObject2.add(item2Column1);
        listObject2.add(item2Column2);
        
        List<GoogleChartColumnObject> listObject3 = new ArrayList<>();
        listObject3.add(item3Column1);
        listObject3.add(item3Column2);

        listRows = new ArrayList();
        listRows.add(new GoogleChartRow(listObject1));
        listRows.add(new GoogleChartRow(listObject2));
        listRows.add(new GoogleChartRow(listObject3));
        // End Rows <--
        
        // Ttile
        String title = "Pie Chart";
        GoogleChartOptions option = new GoogleChartOptions(title); 
        // Pie Chart
        GoogleChart pieChart = new GoogleChart(GoogleChartType.PIECHART, listCols, listRows, option); 
        
        /*
        * Identify previously what information column zero contains.
        * To obtain the value of column zero of each row, where the row represents the object,
        * a lambda function is needed where the getSelectedConsumer method is called.
        */
        pieChart.addChartClickListener(event -> {
            pieChart.getSelectedConsumer(value -> {
                Notification.show(value); // displays the resulting value 
            });
        });
        
        // Layout
        VerticalLayout lytPieChart = new VerticalLayout(pieChart);
        lytPieChart.setSpacing(false);
        add(lytPieChart);
    }

    /**
     * Defines the gantt chart columns
     */
    private void ganttDataColumns() {
        // --> Init Columns
        column0 = new GoogleChartColumn("Task ID", GoogleChartColumnType.STRING);
        column1 = new GoogleChartColumn("Task Name", GoogleChartColumnType.STRING);
        column2 = new GoogleChartColumn("Resource", GoogleChartColumnType.STRING);
        column3 = new GoogleChartColumn("Start", GoogleChartColumnType.DATE);
        column4 = new GoogleChartColumn("End", GoogleChartColumnType.DATE);
        column5 = new GoogleChartColumn("Duration", GoogleChartColumnType.NUMBER);
        column6 = new GoogleChartColumn("Percent Complete", GoogleChartColumnType.NUMBER);
        column7 = new GoogleChartColumn("Dependencies", GoogleChartColumnType.STRING);
        
        listCols = new ArrayList<>();
        listCols.add(column0);
        listCols.add(column1);
        listCols.add(column2);
        listCols.add(column3);
        listCols.add(column4);
        listCols.add(column5);
        listCols.add(column6);
        listCols.add(column7);
        // End Columns <-- 
    }
}