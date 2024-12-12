/*
 *  Copyright 2010-2018 Neotropic SAS <contact@neotropic.co>
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
package com.neotropic.api.forms;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

/**
 *
 * @author Johny Andres Ortega Ruiz <johny.ortega@kuwaiba.org>
 */
public class ElementGridLayout extends AbstractElementContainer {
    private int rows;
    private int columns;
    
    public ElementGridLayout() {
    }
    
    public void setRows(int rows) {
        this.rows = rows;
    }
    
    public int getRows() {
        return rows;                
    }
    
    public void setColumns(int columns) {
        this.columns = columns;
    }
    
    public int getColumns() {
        return columns;
    }

    @Override
    public void initFromXML(XMLStreamReader reader) throws XMLStreamException {
        super.initFromXML(reader);
                
        String attrValue = reader.getAttributeValue(null, Constants.Attribute.ROWS);
        if (attrValue == null)
            throw new XMLStreamException(String.format("Missing attribute %s in tag %s", Constants.Attribute.ROWS, Constants.Tag.GRID_LAYOUT));
        
        rows = Integer.valueOf(attrValue);
        
        attrValue = reader.getAttributeValue(null, Constants.Attribute.COLUMNS);
        if (attrValue == null)
            throw new XMLStreamException(String.format("Missing attribute %s in tag %s", Constants.Attribute.COLUMNS, Constants.Tag.GRID_LAYOUT));
        
        columns = Integer.valueOf(attrValue);
    }
    
    @Override
    public void propertyChange() {
        if (hasProperty(Constants.EventAttribute.ONPROPERTYCHANGE, Constants.Property.REPAINT)) {
            
            boolean oldValue = repaint();
            boolean newValue = (boolean) getNewValue(Constants.EventAttribute.ONPROPERTYCHANGE, Constants.Property.REPAINT);

            setRepaint(newValue);

            firePropertyChangeEvent();
            
            fireElementEvent(new EventDescriptor(
                Constants.EventAttribute.ONPROPERTYCHANGE, 
                Constants.Property.REPAINT, newValue, oldValue));
        }
        super.propertyChange();        
    }
    
    @Override
    public String getTagName() {
        return Constants.Tag.GRID_LAYOUT;
    }
        
}
