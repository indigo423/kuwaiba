/*
 *  Copyright 2010-2021 Neotropic SAS <contact@neotropic.co>.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       https://apache.org/licenses/LICENSE-2.0.txt
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.neotropic.flow.component.gcharts;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.shared.Registration;
import elemental.json.Json;
import elemental.json.JsonArray;
import elemental.json.JsonObject;
import elemental.json.JsonString;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

/**
 * Main class of the google chart web component.
 * This class provides different methods for creating Google charts.
 * Add details such as the type of chart you want, see those supported by GoogleChartTypes.
 * You can also include a title to the chart, use GoogleChartOptions class
 * Feel free to choose the method according to your needs.
 * 
 * @author Mauricio Ruiz {@literal <mauricio.ruiz@kuwaiba.org>}
 */
@Tag("google-chart")
@JsModule("@google-web-components/google-chart/google-chart.js")
@NpmPackage(value = "@google-web-components/google-chart", version = "^4.0.2")
public class GoogleChart extends Component implements HasStyle, HasSize, FlexComponent<Component> {    

    /**
     * Use this method to create a simple column chart.
     * @param chartColumns Columns list. See the GoogleChartColumn class {@link GoogleChartColumn#GoogleChartColumn}.
     * @param chartRows Rows list. See the GoogleChartRow class {@link GoogleChartRow#GoogleChartRow}.
     */
    public GoogleChart(List<GoogleChartColumn> chartColumns, List<GoogleChartRow> chartRows) {        
        getElement().setPropertyJson(GoogleChartConstants.Property.COLUMNS, getColumnsToJson(chartColumns));
        
        boolean hasDateType = false;
        for (GoogleChartColumn cols: chartColumns) {
             if (cols.getType().equals(GoogleChartColumnType.DATE) || cols.getType().equals(GoogleChartColumnType.DATETIME)) {
                hasDateType = true;
                break;
             }                
        }
           
        if (hasDateType == true) 
            getElement().executeJs("var cols = $0; var rows = $1;"
                        +  "for (i = 0; i < rows.length; i++) {\n" +
                                "for(j = 0; j < rows[i].length; j++) {\n" +
                                    "if(cols[j]['type'] === 'date' || cols[j]['type'] === 'datetime') {\n" +
                                        "rows[i][j] = new Date(rows[i][j]);\n" +
                                    "}\n" +
                                "}\n" +
                            "}"
                        + "this.rows = rows",
                        getColumnsToJson(chartColumns), getRowsToJson(chartRows));
        else
            getElement().setPropertyJson(GoogleChartConstants.Property.ROWS, getRowsToJson(chartRows));
        setSizeFull();
    }
  
    /**
     * Use this method to define and create a specific type chart.
     * @param type Define chart type. Use the GoogleChartType class options {@link GoogleChartType}.
     * @param chartColumns Columns list. See the GoogleChartColumn class {@link GoogleChartColumn#GoogleChartColumn}.
     * @param chartRows Rows list. See the GoogleChartRow class {@link GoogleChartRow#GoogleChartRow}.
     */
    public GoogleChart(GoogleChartType type, List<GoogleChartColumn> chartColumns, List<GoogleChartRow> chartRows) {
        getElement().setProperty(GoogleChartConstants.Property.TYPE, type.toString());
        getElement().setPropertyJson(GoogleChartConstants.Property.COLUMNS, getColumnsToJson(chartColumns));
        
        boolean hasDateType = false;
        for (GoogleChartColumn cols: chartColumns) {
             if (cols.getType().equals(GoogleChartColumnType.DATE) || cols.getType().equals(GoogleChartColumnType.DATETIME)) {
                hasDateType = true;
                break;
             }                
        }
           
        if (hasDateType == true) 
            getElement().executeJs("var cols = $0; var rows = $1;"
                        +  "for (i = 0; i < rows.length; i++) {\n" +
                                "for(j = 0; j < rows[i].length; j++) {\n" +
                                    "if(cols[j]['type'] === 'date' || cols[j]['type'] === 'datetime') {\n" +
                                        "rows[i][j] = new Date(rows[i][j]);\n" +
                                    "}\n" +
                                "}\n" +
                            "}"
                        + "this.rows = rows",
                        getColumnsToJson(chartColumns), getRowsToJson(chartRows));
        else
            getElement().setPropertyJson(GoogleChartConstants.Property.ROWS, getRowsToJson(chartRows));
        setSizeFull();    
    }
    
    /**
     * Use this method to define and create a specific type chart. Also add a customization.
     * @param type Define chart type. Use the GoogleChartType class options {@link GoogleChartType}.
     * @param chartColumns Columns list. See the GoogleChartColumn class {@link GoogleChartColumn#GoogleChartColumn}.
     * @param chartRows Rows list. See the GoogleChartRow class {@link GoogleChartRow#GoogleChartRow}.
     * @param options Customization tool for the chart. See the GoogleChartOptions class {@link GoogleChartOptions}.
     */
    public GoogleChart(GoogleChartType type, List<GoogleChartColumn> chartColumns
            , List<GoogleChartRow> chartRows, GoogleChartOptions options) 
    {
        getElement().setProperty(GoogleChartConstants.Property.TYPE, type.toString());
        getElement().setPropertyJson(GoogleChartConstants.Property.COLUMNS, getColumnsToJson(chartColumns));
        
        boolean hasDateType = false;
        for (GoogleChartColumn cols: chartColumns) {
             if (cols.getType().equals(GoogleChartColumnType.DATE) || cols.getType().equals(GoogleChartColumnType.DATETIME)) {
                hasDateType = true;
                break;
             }                
        }
           
        if (hasDateType == true) 
            getElement().executeJs("var cols = $0; var rows = $1;"
                        +  "for (i = 0; i < rows.length; i++) {\n" +
                                "for(j = 0; j < rows[i].length; j++) {\n" +
                                    "if(cols[j]['type'] === 'date' || cols[j]['type'] === 'datetime') {\n" +
                                        "rows[i][j] = new Date(rows[i][j]);\n" +
                                    "}\n" +
                                "}\n" +
                            "}"
                        + "this.rows = rows",
                        getColumnsToJson(chartColumns), getRowsToJson(chartRows));
        else
            getElement().setPropertyJson(GoogleChartConstants.Property.ROWS, getRowsToJson(chartRows));
        
        getElement().setPropertyJson(GoogleChartConstants.Property.OPTIONS, options.toJson());
        setSizeFull();
    }
    
    /**
     * Use this method to define and create a specific type chart. Also add a customization.
     * @param type Define chart type. Use the GoogleChartType class options {@link GoogleChartType}.
     * @param chartColumns Columns list. See the GoogleChartColumn class {@link GoogleChartColumn#GoogleChartColumn}.
     * @param chartRows Rows list. See the GoogleChartRow class {@link GoogleChartRow#GoogleChartRow}.
     * @param options Customization tool for the chart. See the GoogleChartOptions class {@link GoogleChartOptions}. Use null to default values.
     * @param data validates if the format to be returned is data type.
     */
    public GoogleChart(GoogleChartType type, List<GoogleChartColumn> chartColumns, List<GoogleChartRow> chartRows, GoogleChartOptions options, boolean data) {    
        getElement().setProperty(GoogleChartConstants.Property.TYPE, type.toString());
        if (data)
            getElement().setPropertyJson(GoogleChartConstants.Property.DATA, getColumnsAndRowsToJson(chartColumns, chartRows));
        else {
            getElement().setPropertyJson(GoogleChartConstants.Property.COLUMNS, getColumnsToJson(chartColumns));

            boolean hasDateType = false;
            for (GoogleChartColumn cols : chartColumns) {
                if (cols.getType().equals(GoogleChartColumnType.DATE) || cols.getType().equals(GoogleChartColumnType.DATETIME)) {
                    hasDateType = true;
                    break;
                }
            }

            if (hasDateType == true)
                getElement().executeJs("var cols = $0; var rows = $1;"
                        + "for (i = 0; i < rows.length; i++) {\n"
                        + "for(j = 0; j < rows[i].length; j++) {\n"
                        + "if(cols[j]['type'] === 'date' || cols[j]['type'] === 'datetime') {\n"
                        + "rows[i][j] = new Date(rows[i][j]);\n"
                        + "}\n"
                        + "}\n"
                        + "}"
                        + "this.rows = rows",
                        getColumnsToJson(chartColumns), getRowsToJson(chartRows));
            else
                getElement().setPropertyJson(GoogleChartConstants.Property.ROWS, getRowsToJson(chartRows));
        }
        
        if (options != null)
            getElement().setPropertyJson(GoogleChartConstants.Property.OPTIONS, options.toJson());
        
        setSizeFull();
    }
    
    /**
     * This method converts columns values in Json format, required by component.
     * @param chartColumns Columns list.
     * @return Columns values in Json format.
     */
    private JsonArray getColumnsToJson(List<GoogleChartColumn> chartColumns) {
        if (chartColumns != null) {
            JsonArray result = Json.createArray();
            for (int i = 0; i < chartColumns.size(); i++) 
               result.set(i, chartColumns.get(i).toJson());
            return result; 
        }
        return null;
    }
    
    /**
     * This method converts rows values in Json format, required by component.
     * @param chartRows Rows list.
     * @return Rows values in Json format.
     */
    private JsonArray getRowsToJson(List<GoogleChartRow> chartRows) {
        if (chartRows != null) {
            JsonArray result = Json.createArray();
            for (int i = 0; i < chartRows.size(); i++) 
               result.set(i, chartRows.get(i).toJson());
            return result; 
        } 
        return null;
    }
    
    /**
     * This method converts columns and rows values in Json format, required by component.
     * @param chartColumns Columns list.
     * @param chartRows Rows list.
     * @return Columns and rows values in Json format
     */
    private JsonArray getColumnsAndRowsToJson(List<GoogleChartColumn> chartColumns, List<GoogleChartRow> chartRows) {
        if (chartColumns != null && chartRows != null) {
            JsonArray result = Json.createArray();
            
            JsonArray colArray = Json.createArray();
            AtomicInteger colCounter = new AtomicInteger(0);
            chartColumns.forEach(col -> {
                if (col.getLabelMap() != null) {
                    JsonObject role = col.toJson(true);
                    colArray.set(colCounter.get(), role);
                } else if (col.getLabel() != null) {
                    JsonString label = Json.create(col.getLabel());
                    colArray.set(colCounter.get(), label);
                }
                colCounter.getAndIncrement();
            });
            
            AtomicInteger resultCounter = new AtomicInteger(0);
            result.set(resultCounter.get(), colArray);
            resultCounter.getAndIncrement();
            
            chartRows.forEach(row -> {
                result.set(resultCounter.get(), row.toJson());
                resultCounter.getAndIncrement();
            });
            return result;
        } 
        return null;
    }
    
    /**
     * This method listens if the user clicks on an element in the chart.
     * @param listener for click event
     * @return chart select detail
     */
    public Registration addChartClickListener(ComponentEventListener<GoogleChartEvent.ChartClickEvent> listener) {
        return addListener(GoogleChartEvent.ChartClickEvent.class, listener);
    }
    
    /**
     * This method captures the selection of chart elements.
     * Returns the value of the zero position column of each row.
     * @param consumer; selection event
     */
    public void getSelectedConsumer(Consumer<String> consumer) {
        this.getElement().executeJs("var value=[];\n"
                + "var selection = this.chartWrapper.getChart().getSelection();\n"
                + "for (var i=0; i < selection.length; i++) {\n"
                + "var row = this.chartWrapper.getChart().getSelection()[i].row;\n"
                + "value.push(this.chartWrapper.getDataTable().getValue(row, 0));\n"
                + "}\n"
                + "return value;")
                .then(value -> {
                    List<String> listElements = new ArrayList();
                    JsonArray arrayElements = (JsonArray) value;
                    
                    for (int i = 0; i < arrayElements.length(); i++)
                        listElements.add(arrayElements.getString(i));

                    String element = listElements.get(listElements.size() -1);
                    consumer.accept(element);
                });
    }
}