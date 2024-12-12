/*
 *  Copyright 2010-2024 Neotropic SAS <contact@neotropic.co>.
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

package org.neotropic.util.visual.properties;

import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import org.neotropic.kuwaiba.core.apis.persistence.util.Constants;
import org.neotropic.kuwaiba.core.i18n.TranslationService;

/**
 * Support for local-date-time type properties
 * @author Orlando Paz {@literal <orlando.paz@kuwaiba.org>}
 */
public class LocalDateTimeProperty extends AbstractProperty<LocalDateTime> {
    /**
     * Default formatter.
     */
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("EEE dd MMM yyyy HH:mm", Locale.ENGLISH);

    public LocalDateTimeProperty(String name, String displayName, String description, LocalDateTime value, TranslationService ts) {
        super(name, displayName, description, value, ts);
        setType(Constants.DATA_TYPE_TIME_STAMP);
    }  
    
    public LocalDateTimeProperty(String name, String displayName, String description, long value, TranslationService ts) {
        super(name, displayName, description, Instant.ofEpochMilli(value).atZone(ZoneId.systemDefault()).toLocalDateTime(), ts);
         setType(Constants.DATA_TYPE_TIME_STAMP);
    }

    public LocalDateTimeProperty(String name, String displayName, String description, long value, TranslationService ts, boolean readOnly, boolean mandatory, boolean unique) {
        super(name, displayName, description,  Instant.ofEpochMilli(value).atZone(ZoneId.systemDefault()).toLocalDateTime(), ts, readOnly, mandatory, unique);
        setType(Constants.DATA_TYPE_TIME_STAMP);
    }
    
    

    @Override
    public AbstractField getAdvancedEditor() {
        throw new UnsupportedOperationException("This property type does not support an advanced editor."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean supportsAdvancedEditor() {
        return false;
    }

    @Override
    public AbstractField getInplaceEditor() {       
        DateTimePicker datePicker = new DateTimePicker();
        datePicker.setDatePlaceholder("Date");
        datePicker.setTimePlaceholder("Time");
        return datePicker;
    }

    @Override
    public String getAsString() {
        return getValue() == null ? AbstractProperty.NULL_LABEL : getValue().format(DATE_FORMATTER);
    }
    
    @Override
    public boolean supportsInplaceEditor() {
        return true;
    }

    @Override
    public LocalDateTime getDefaultValue() {
        return LocalDateTime.now();
    }

}
