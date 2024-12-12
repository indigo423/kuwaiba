/*
 * Copyright 2010-2024 Neotropic SAS <contact@neotropic.co>.
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
package org.neotropic.kuwaiba.core.services.scheduling.properties;

import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.Tag;
import org.neotropic.kuwaiba.core.i18n.TranslationService;
import org.neotropic.kuwaiba.core.services.scheduling.selectors.CronSelector;
import org.neotropic.util.visual.properties.AbstractProperty;

/**
 * Support for Cron expression properties
 * @author Juan Sebastian Betancourt {@literal <juan.betancourt@kuwaiba.org>}
 */
public class CronProperty extends AbstractProperty<String> {
    /**
     * Reference to the translation service.
     */
    private TranslationService ts;

    public CronProperty(String name, String displayName, String description, String value, TranslationService ts) {
        super(name, displayName, description, value, ts);
        this.ts = ts;
    }

    @Override
    public String getDefaultValue() { return null; }

    @Override
    public AbstractField getAdvancedEditor() {
        return new CronField(new CronSelector(ts, getAccept()));
    }

    @Override
    public boolean supportsAdvancedEditor() { return true; }

    @Override
    public boolean supportsInplaceEditor() { return false; }

    @Override
    public AbstractField getInplaceEditor() { return null; }

    @Override
    public String getAsString() {
        return getValue() == null ? AbstractProperty.NULL_LABEL : getValue();
    }

    @Tag("cron-field")
    private class CronField extends AbstractField implements HasComponents, HasSize {

        public CronField(CronSelector selector) {
            super(null);
            setWidthFull();
            setSizeFull();
            selector.addResultCron(event -> setValue(event.getCronExpression()));
            add(selector);
        }

        @Override
        protected void setPresentationValue(Object o) {
        }
    }
}
