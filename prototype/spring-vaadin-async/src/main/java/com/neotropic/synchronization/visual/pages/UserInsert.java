/*
 *  Copyright 2022 Neotropic SAS. <contact@neotropic.co>.
 *  Licensed under the EPL License, Version 1.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *   http://www.eclipse.org/legal/epl-v10.html
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.neotropic.synchronization.visual.pages;

import com.neotropic.synchronization.data.entitites.Person;
import com.neotropic.synchronization.visual.layouts.AppNavLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.router.Route;

/**
 * Insert new users to database
 * @author Hardy Ryan Chingal Martinez {@literal <ryan.chingal@kuwaiba.org>}
 * created on 31/03/2022-16:06
 */
@Route(value = "userManagement", layout = AppNavLayout.class)
public class UserInsert extends VerticalLayout implements BeforeEnterObserver, HasDynamicTitle {

    private TextField firstName;
    private TextField lastName;
    private EmailField email;
    private Button btnSave;
    private Person person;
    private Binder<Person> personBinder;

    private void initComponents() {
        FormLayout lytPersonForm = new FormLayout();
        firstName = new TextField("First name");
        lastName = new TextField("Last name");
        email = new EmailField("Email");
        btnSave = new Button("Load content", new Icon(VaadinIcon.HARDDRIVE));

        btnSave.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);
        lytPersonForm.add(firstName, lastName);
        lytPersonForm.add( email, 2);
        lytPersonForm.setResponsiveSteps(
                // Use one column by default
                new FormLayout.ResponsiveStep("0", 1),
                // Use two columns, if the layout's width exceeds 320px
                new FormLayout.ResponsiveStep("320px", 2),
                // Use three columns, if the layout's width exceeds 500px
                new FormLayout.ResponsiveStep("500px", 3)
        );
        bindElements();

        add(lytPersonForm, btnSave);
        setAlignSelf(Alignment.END, btnSave);
    }

    private void bindElements() {
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        initComponents();
    }

    @Override
    public String getPageTitle() {
        return "user Management";
    }
}
