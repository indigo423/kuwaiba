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
import com.vaadin.flow.component.combobox.ComboBox;
import org.neotropic.kuwaiba.core.apis.persistence.application.ApplicationEntityManager;
import org.neotropic.kuwaiba.core.apis.persistence.application.Task;
import org.neotropic.kuwaiba.core.i18n.TranslationService;
import org.neotropic.util.visual.properties.AbstractProperty;

import java.util.List;

/**
 * Support for task properties
 * @author Juan Sebastian Betancourt {@literal <juan.betancourt@kuwaiba.org>}
 */
public class TaskProperty extends AbstractProperty<Task> {
    /**
     * Reference to the translation service.
     */
    private TranslationService ts;
    /**
     * Reference to the application entity manager.
     */
    private ApplicationEntityManager aem;
    /**
     * Object to store the task name.
     */
    private Task selectTask;

    @Override
    public Task getDefaultValue() {
        return null;
    }

    public TaskProperty(String name, String displayName, String description, Task value, TranslationService ts, ApplicationEntityManager aem) {
        super(name, displayName, description, value, ts);
        this.ts = ts;
        this.aem = aem;
    }

    @Override
    public AbstractField getAdvancedEditor() {
        List<Task> tasks = aem.getTasks();
        ComboBox<Task> cmbTasks = new ComboBox<>(
                ts.getTranslatedString("module.scheduleJob.ui.actions.label.task"), tasks);
        cmbTasks.setItemLabelGenerator(Task::getName);
        cmbTasks.setWidthFull();
        cmbTasks.setAllowCustomValue(false);
        cmbTasks.setRequiredIndicatorVisible(true);
        cmbTasks.setValue(getValue());

        cmbTasks.addValueChangeListener(event -> getAccept().setEnabled(cmbTasks.getValue() != null));

        getAccept().addClickListener(event -> setValue(cmbTasks.getValue()));

        return cmbTasks;
    }

    @Override
    public boolean supportsAdvancedEditor() { return true; }

    @Override
    public boolean supportsInplaceEditor() { return false; }

    @Override
    public AbstractField getInplaceEditor() { return null; }

    @Override
    public String getAsString() {
        return getValue() == null ? AbstractProperty.NULL_LABEL : getValue().getName();
    }
}
