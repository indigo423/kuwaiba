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
import com.neotropic.synchronization.notification.Broadcaster;
import com.neotropic.synchronization.services.imp.PersonService;
import com.neotropic.synchronization.visual.layouts.AppNavLayout;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.shared.Registration;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Hardy Ryan Chingal Martinez {@literal <ryan.chingal@kuwaiba.org>}
 * created on 01/04/2022-10:21
 */
@Route(value = "userList", layout = AppNavLayout.class)
public class UserList extends VerticalLayout implements BeforeEnterObserver, HasDynamicTitle {

    private Grid<Person> grdPeople;
    private Div message;
    private ExecutorService executor;
    private Registration broadcasterRegistration;

    @Autowired
    private Broadcaster broadcaster;

    @Autowired
    private PersonService personService;

    public UserList() {
        setSizeFull();
        message = new Div();
        grdPeople = new Grid<>();
        personService = new PersonService();
        executor = Executors.newCachedThreadPool();

        grdPeople.setSizeFull();
        grdPeople.addColumn(Person::getLastName).setHeader("Lastname");
        grdPeople.addColumn(Person::getFirstName).setHeader("Firstname");
        grdPeople.addColumn(Person::getEmail).setHeader("E-Mail");
    }

    private void initResources(){
        add(message);
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        UI ui = attachEvent.getUI();
        broadcasterRegistration = broadcaster.register(newMessage -> {
            ui.access(() -> {
                message.setText(newMessage);
            });
        });

        broadcasterRegistration = broadcaster.registerUser(usersFound -> {
            ui.access(() -> {
                message.setVisible(false);
                grdPeople.setItems(usersFound);
                add(grdPeople);
            });
        });
    }
    @Override
    protected void onDetach(DetachEvent detachEvent) {
        broadcasterRegistration.remove();
        broadcasterRegistration = null;
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        initResources();
    }

    @Override
    public String getPageTitle() {
        return "Lista de Usuarios";
    }
}

