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
import com.neotropic.synchronization.services.JobService;
import com.neotropic.synchronization.services.imp.PersonService;
import com.neotropic.synchronization.visual.layouts.AppNavLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Demo for load asynchronous elements inside a table
 * @author Hardy Ryan Chingal Martinez {@literal <ryan.chingal@kuwaiba.org>}
 */
@Route(value = "initialPage", layout = AppNavLayout.class)
public class LandingPage extends VerticalLayout implements BeforeEnterObserver , HasDynamicTitle {

    private Button btnLoad;
    private Grid<Person> grdPeople;
    private Paragraph loadingText;
    private ExecutorService executor;

    @Autowired
    private Broadcaster broadcaster;

    @Autowired
    private PersonService personService;

    @Autowired
    private JobService jobService;

    public LandingPage() {
        setSizeFull();
        btnLoad = new Button("Load content", new Icon(VaadinIcon.AUTOMATION));
        grdPeople = new Grid<>();
        loadingText = new Paragraph("loading...");
        //personService = new PersonService();
        executor = Executors.newCachedThreadPool();

        grdPeople.setSizeFull();
        grdPeople.addColumn(Person::getLastName).setHeader("Lastname");
        grdPeople.addColumn(Person::getFirstName).setHeader("Firstname");
        grdPeople.addColumn(Person::getEmail).setHeader("E-Mail");
    }

    private void initResources(){
        add(btnLoad);
        btnLoad.addClickListener(event ->{
            add(loadingText, grdPeople);
            broadcaster.broadcast("cargando datos");

            jobService.createJob("loadUsers", personService, broadcaster );
        });
    }


    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        initResources();
    }

    @Override
    public String getPageTitle() {
        return "Pagina de inicio";
    }
}
