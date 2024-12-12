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
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

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
    
    private GoogleChartColumn columnLine0;
    private GoogleChartColumn columnLine1;
    /**
     * Object to save column list
     */
    private List<GoogleChartColumn> listCols;
    /**
     * Object to save rows list
     */
    private List<GoogleChartRow> listRows;
    
    /**
     * Object to save column list
     */
    private List<GoogleChartColumn> listColsLine;
    /**
     * Object to save rows list
     */
    private List<GoogleChartRow> listRowsLine;

    /**
     * Constructor
     */
    public MainView() {
        setSizeFull();
        setSpacing(false);
        //drawGanttChart();
        add(drawPieChart());
        // Add random table
        add(generateTable());
        
        
    }
    
     /**
     * Generate table with random values
     */
    private Grid<Object> generateTable() {

        Grid<Object> grid = new Grid<>();

        // Genera las columnas aleatorias
        grid.setItems(generateRandomData().toArray(new Object[0][]));

        // Columna de texto con diferentes colores
        grid.addComponentColumn(row -> {
            Span span = new Span((String) ((Object[]) row)[0]);
            span.getStyle().set("color", (String) ((Object[]) row)[1]);
            return span;
        }).setHeader("IPv4").setFlexGrow(1);

        grid.addColumn(row -> (Integer) ((Object[]) row)[2]).setHeader("In Use").setFlexGrow(1);

        grid.addColumn(row -> (Integer) ((Object[]) row)[3]).setHeader("Reserved").setFlexGrow(1);

        grid.addColumn(row -> (Integer) ((Object[]) row)[4]).setHeader("Free").setFlexGrow(1);

        
        return grid;
    }
    /**
     * Add numbers random to table
     */
    private List<Object[]> generateRandomData() {
        List<Object[]> data = new ArrayList<>();
        Random random = new Random();

        String[] colors = new String[]{"red", "green", "orange"};

        for (int i = 0; i < 10; i++) {
            int colorIndex = random.nextInt(colors.length);
            String color = colors[colorIndex];

            String text = "120.10.40.0/ " + (i+1) + "0";

            int number1 = random.nextInt(100);
            int number2 = random.nextInt(100);
            int number3 = random.nextInt(100);

            data.add(new Object[]{text, color, number1, number2, number3});
        }

        return data;
    }

     /**
     * Draw the graphic pieChart and get the other graphics (column, bar and line chart)
     */
    private HorizontalLayout drawPieChart() {
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
        column0 = new GoogleChartColumn("Space", GoogleChartColumnType.STRING);
        column1 = new GoogleChartColumn("Space Management ", GoogleChartColumnType.NUMBER);
       
        listCols = new ArrayList<>();
        listCols.add(column0);
        listCols.add(column1);
        // End Columns <--
        
         // --> Init Rows            
        // First item
        GoogleChartColumnObject item1Column1 = new GoogleChartColumnObject(column0, "inUse", "In Use");
        GoogleChartColumnObject item1Column2 = new GoogleChartColumnObject(column1, 30);
        // Second item
        GoogleChartColumnObject item2Column1 = new GoogleChartColumnObject(column0, "reserved", "Reserved");
        GoogleChartColumnObject item2Column2 = new GoogleChartColumnObject(column1, 28);
        // Third item
        GoogleChartColumnObject item3Column1 = new GoogleChartColumnObject(column0, "free", "Free");
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
        
        GoogleChart drawBar = drawBarChart();
        GoogleChart drawColumn =  drawColumnChart();
        GoogleChart drawLine = lineChart();
        VerticalLayout lytPieChart = new VerticalLayout(pieChart, drawBar);
        lytPieChart.getStyle().set("height", "100%");
        lytPieChart.getStyle().set("widht", "100%");
        VerticalLayout vertiChart = new VerticalLayout(drawColumn,drawLine);
        vertiChart.getStyle().set("height", "100%");
        vertiChart.getStyle().set("widht", "100%");
        HorizontalLayout output = new HorizontalLayout(lytPieChart, vertiChart);
        output.setSizeFull();
        output.setHeightFull();
        output.getStyle().set("height", "100%");
        output.getStyle().set("widht", "100%");
        return output;
    }
    /**
     * Draw the BarChart graphic
     */
    private GoogleChart drawBarChart() {
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
        GoogleChartColumnObject item1Column1 = new GoogleChartColumnObject(column1, "In Use");
        GoogleChartColumnObject item1Column2 = new GoogleChartColumnObject(column2, 50);
        HashMap<String, Object> item1Column3Map = new HashMap<>();
        item1Column3Map.put("color", "#1ABC9C");
        GoogleChartColumnObject item1Column3 = new GoogleChartColumnObject(column3, item1Column3Map);
        
        // Second item
        GoogleChartColumnObject item2Column1 = new GoogleChartColumnObject(column1, "Reserved");
        GoogleChartColumnObject item2Column2 = new GoogleChartColumnObject(column2, 63);
        HashMap<String, Object> item2Column3Map = new HashMap<>();
        item2Column3Map.put("fill-color", "#C5A5CF");
        GoogleChartColumnObject item2Column3 = new GoogleChartColumnObject(column3, item2Column3Map);
        
        // Third item
        GoogleChartColumnObject item3Column1 = new GoogleChartColumnObject(column1, "Free");
        GoogleChartColumnObject item3Column2 = new GoogleChartColumnObject(column2, 30);
        HashMap<String, Object> item3Column3Map = new HashMap<>();
        item3Column3Map.put("stroke-color", "#871B47");
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
        options.put("title", "Bar Chart");
        
        GoogleChartOptions option = new GoogleChartOptions(options); 
        // End customization <--
        
        // Bar Chart
        GoogleChart barChart = new GoogleChart(GoogleChartType.BARCHART, listCols, listRows, option, true);
        // Layout
        VerticalLayout lytBarChart = new VerticalLayout(new H4("Bar Chart"), barChart);
        lytBarChart.setSpacing(false);
        //return lytBarChart;
        return barChart;
    }
    /**
     * Draw the Column chart graphic
     */
    private GoogleChart drawColumnChart() {
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
        column1 = new GoogleChartColumn("Space", GoogleChartColumnType.STRING);
        column2 = new GoogleChartColumn("Space Management", GoogleChartColumnType.NUMBER);
        
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
        GoogleChartColumnObject item1Column1 = new GoogleChartColumnObject(column1, "In Use");
        GoogleChartColumnObject item1Column2 = new GoogleChartColumnObject(column2, 60);
        
        HashMap<String, Object> item1Column3Map = new HashMap<>();
        item1Column3Map.put("color", "#39F16E");
        GoogleChartColumnObject item1Column3 = new GoogleChartColumnObject(column3, item1Column3Map);
        // Second item
        GoogleChartColumnObject item2Column1 = new GoogleChartColumnObject(column1, "Free");
        GoogleChartColumnObject item2Column2 = new GoogleChartColumnObject(column2, 20);
        HashMap<String, Object> item2Column3Map = new HashMap<>();
        item2Column3Map.put("fill-color", "#E74C3C");
        GoogleChartColumnObject item2Column3 = new GoogleChartColumnObject(column3, item2Column3Map);
        
        // Third item
        GoogleChartColumnObject item3Column1 = new GoogleChartColumnObject(column1, "Reserved");
        GoogleChartColumnObject item3Column2 = new GoogleChartColumnObject(column2, 10);
         HashMap<String, Object> item3Column3Map = new HashMap<>();
        item3Column3Map.put("fill-color", "#7F8C8D");
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
        options.put("title", "Column Chart");
        GoogleChartOptions option = new GoogleChartOptions(options); 
        // End customization <--
       
        // Column Chart
        GoogleChart columnChart = new GoogleChart(GoogleChartType.COLUMNCHART, listCols, listRows, option, true);
        // Layout
        VerticalLayout lytColumnChart = new VerticalLayout(new H4("Column Chart"), columnChart);
        lytColumnChart.setSpacing(false);
        return columnChart;
    }
    /**
     * Draw the BarChart graphic
     */
    private GoogleChart lineChart() {
        column1 = new GoogleChartColumn("Space", GoogleChartColumnType.STRING);
        column2 = new GoogleChartColumn("Space", GoogleChartColumnType.NUMBER);
        column3 = new GoogleChartColumn("IP number", GoogleChartColumnType.NUMBER);
        
        listCols = new ArrayList<>();
        listCols.add(column1);
        listCols.add(column2);
        listCols.add(column3);
        
        // --> Init Rows            
        // First item
        GoogleChartColumnObject item1Column1 = new GoogleChartColumnObject(column1, "In Use");
        GoogleChartColumnObject item1Column2 = new GoogleChartColumnObject(column2, 60);
        GoogleChartColumnObject item1Column3 = new GoogleChartColumnObject(column3, 120);
        
        GoogleChartColumnObject item2Column1 = new GoogleChartColumnObject(column1, "Free");
        GoogleChartColumnObject item2Column2 = new GoogleChartColumnObject(column2, 20);
        GoogleChartColumnObject item2Column3 = new GoogleChartColumnObject(column3, 50);
        
        GoogleChartColumnObject item3Column1 = new GoogleChartColumnObject(column1, "Reserved");
        GoogleChartColumnObject item3Column2 = new GoogleChartColumnObject(column2, 10);
        GoogleChartColumnObject item3Column3 = new GoogleChartColumnObject(column3, 70);

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
        
        HashMap<String, Object> options = new HashMap<>();
        options.put("title", "Line Chart");
        GoogleChartOptions option = new GoogleChartOptions(options);
        GoogleChart columnChart = new GoogleChart(GoogleChartType.LINECHART, listCols, listRows, option, true);

        return columnChart;
    }
}