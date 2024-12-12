/*
 *  Copyright 2010-2017, Neotropic SAS <contact@neotropic.co>
 *
 *  Licensed under the EPL License, Version 1.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.eclipse.org/legal/epl-v10.html
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.inventory.reports.nodes.properties;

import java.lang.reflect.InvocationTargetException;
import org.inventory.communications.core.LocalReport;
import org.openide.nodes.PropertySupport;

/**
 * This class allows to edit the properties corresponding to the report parameters, which are always taken as Strings.
 * @author Charles Edward Bedon Cortazar <charles.bedon@kuwaiba.org>
 */
public class ReportParameterProperty extends PropertySupport.ReadOnly<String> {
    private LocalReport report;
    
    public ReportParameterProperty(String propertyName, LocalReport report) {
        super(propertyName, String.class, propertyName, propertyName);
        this.report = report;
    }
    
    @Override
    public String getValue() throws IllegalAccessException, InvocationTargetException {
        return "";
    }
}