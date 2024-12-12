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
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.listbox.MultiSelectListBox;
import org.neotropic.kuwaiba.core.apis.persistence.application.ApplicationEntityManager;
import org.neotropic.kuwaiba.core.apis.persistence.application.UserProfile;
import org.neotropic.kuwaiba.core.i18n.TranslationService;
import org.neotropic.util.visual.properties.AbstractProperty;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Support for user properties
 * @author Juan Sebastian Betancourt {@literal <juan.betancourt@kuwaiba.org>}
 */
public class UserProperty extends AbstractProperty<List<UserProfile>> {
    /**
     * Reference to the application entity manager.
     */
    private ApplicationEntityManager aem;
    /**
     * List of selected users
     */
    private List<UserProfile> selectedUsers = new ArrayList<>();

    List<Object> allItems;
    public static String NOT_USERS_SELECTED_LABEL;
    public static String USER_SELECTED_LABEL;
    public static String USERS_SELECTED_LABEL;

    public UserProperty(String name, String displayName, String description, List value, TranslationService ts, ApplicationEntityManager aem) {
        super(name, displayName, description, value, ts);
        this.aem = aem;
        NOT_USERS_SELECTED_LABEL = ts.getTranslatedString("module.scheduleJob.ui.users.info.no-user");
        USERS_SELECTED_LABEL = ts.getTranslatedString("module.scheduleJob.ui.users.info.users");
        USER_SELECTED_LABEL = ts.getTranslatedString("module.scheduleJob.ui.users.info.user");
    }

    @Override
    public AbstractField getAdvancedEditor() {
        List<UserProfile> users = aem.getUsers();
        MultiSelectListBox<UserProfile> mlsUsers = new MultiSelectListBox<>();
        mlsUsers.setItems(users);
        mlsUsers.select(getValue());

        mlsUsers.addValueChangeListener(listener -> {
            Set<UserProfile> selectedItems = mlsUsers.getSelectedItems();
            selectedUsers.clear();
            selectedUsers.addAll(selectedItems); // selectedItems es un Set, pero addAll puede aceptar un Set
        });

        Button accept = getAccept();
        accept.addClickListener(event -> setValue(selectedUsers));

        return mlsUsers;
    }

    @Override
    public boolean supportsAdvancedEditor() { return true; }

    @Override
    public AbstractField getInplaceEditor() { return null; }

    @Override
    public String getAsString() {
        Collection<?> value = getValue();

        if (value == null || value.isEmpty())
            return NOT_USERS_SELECTED_LABEL;

        List<?> tempList;

        if (value instanceof List)
            tempList = (List<?>) value;
        else if (value instanceof Set)
            tempList = new ArrayList<>((Set<?>) value);
        else
            throw new IllegalArgumentException("Unsupported collection type: " + value.getClass().getName());

        String sel = tempList.size() == 1 ? USER_SELECTED_LABEL : USERS_SELECTED_LABEL;
        return tempList.size() + " " + sel;
    }

    @Override
    public boolean supportsInplaceEditor() { return false; }

    @Override
    public List getDefaultValue() { return new ArrayList<>(); }
}
