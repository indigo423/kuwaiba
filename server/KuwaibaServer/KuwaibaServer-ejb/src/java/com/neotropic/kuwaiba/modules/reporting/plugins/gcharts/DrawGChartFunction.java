/**
 *  Copyright 2010-2017 Neotropic SAS <contact@neotropic.co>.
 * 
 *   Licensed under the EPL License, Version 1.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *        http://www.eclipse.org/legal/epl-v10.html
 * 
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */
package com.neotropic.kuwaiba.modules.reporting.plugins.gcharts;

import com.neotropic.kuwaiba.modules.reporting.html.HTMLDiv;
import com.neotropic.kuwaiba.modules.reporting.javascript.DataTable;
import com.neotropic.kuwaiba.modules.reporting.javascript.DataTable.DataType;
import com.neotropic.kuwaiba.modules.reporting.javascript.JavaScriptFunction;
import com.neotropic.kuwaiba.modules.reporting.plugins.gcharts.GChartsFactory.ChartType;

/**
 * JavaScript function to draw a specified type of chart
 * @author Johny Andres Ortega Ruiz <johny.ortega@kuwaiba.org>
 */
public class DrawGChartFunction extends JavaScriptFunction {
    /**
     * The entry data to the chart
     */
    private final DataTable dataTable;
    /**
     * The chart type see <code>ChartType</code>
     */
    private final ChartType chartType;
    /**
     * Title to the chart
     */
    private final String chartTitle;
    /**
     * The html div tag which is used to render the chart
     */
    private final HTMLDiv htmlDiv;
    
    /**
     * @param functionName Unique name to identify the draw chart operation
     * @param chartTitle The chart title
     * @param dataTable The data set to show in the chart
     * @param chartType The type of chart to draw see <code>ChartType</code>
     * @param htmlDiv The div tag used to render the chart
     */
    public DrawGChartFunction(String functionName, String chartTitle, DataTable dataTable, ChartType chartType, HTMLDiv htmlDiv) {
        super(functionName);
        this.chartTitle = chartTitle;
        this.chartType = chartType;
        this.htmlDiv = htmlDiv;
        this.dataTable = dataTable;
    }
    
    @Override
    public String getCodeBlock() throws Exception {
        String codeBlock = "";
        
        DataType [] dataTypes = dataTable.getTypes();
        String [] dataLabels = dataTable.getLabels();
        
        if (dataTypes.length == dataLabels.length) {
            codeBlock += "\nvar data = new google.visualization.DataTable();"; //NOI18N
            
            int length = dataLabels.length;
            for (int i = 0; i < length; i += 1)
                codeBlock += "\ndata.addColumn('" + dataTypes[i].getValue() + "', '" + dataLabels[i] + "');"; //NOI18N
            
            for (String[] row : dataTable.getRows()) {
                if (row.length == length) {
                    codeBlock += "\ndata.addRow(["; //NOI18N
                    
                    for (int i = 0; i < length - 1; i += 1)
                        codeBlock += DataTable.cellValue(dataTypes[i], row[i]) + ", ";
                    
                    codeBlock += DataTable.cellValue(dataTypes[length - 1], row[length - 1]);
                    
                    codeBlock += "]);"; //NOI18N
                } else {
                    throw new Exception(String.format(
                        "In the function %s The length of row %s and labels array do not match", 
                        Integer.toString(dataTable.getRows().indexOf(row)),
                        getFunctionName()));
                }
            }
            
            codeBlock += "\nvar options = {"; //NOI18N
            codeBlock += "'title':'" + chartTitle + "'"; //NOI18N
            codeBlock += "};";
            
            codeBlock += "\nvar chart = new google.visualization." + chartType.getValue() + "(document.getElementById('" + htmlDiv.getId() + "'));"; //NOI18N
            codeBlock += "\nchart.draw(data, options);"; //NOI18N
            
        } else {
            throw new Exception(String.format(
                "In the function %s The length of types array and labels array do not match", 
                getFunctionName()));
        }
        return codeBlock;
    }
}
