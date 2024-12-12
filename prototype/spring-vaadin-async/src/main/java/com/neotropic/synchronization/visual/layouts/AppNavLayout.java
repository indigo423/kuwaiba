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
package com.neotropic.synchronization.visual.layouts;

import com.neotropic.synchronization.commons.ERunProcessState;
import com.neotropic.synchronization.visual.pages.LandingPage;
import com.neotropic.synchronization.visual.pages.UserInsert;
import com.neotropic.synchronization.visual.pages.UserList;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.TabsVariant;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.PWA;

import java.util.ArrayList;
import java.util.List;
/**
 * Default page layout menu
 * @author Hardy Ryan Chingal Martinez {@literal <ryan.chingal@kuwaiba.org>}
 */
@Push
@PWA(name = "Vaadin Application",
        shortName = "Vaadin App",
        description = "This is an example Vaadin application.",
        enableInstallPrompt = false)
public class AppNavLayout extends AppLayout implements AfterNavigationObserver {

    private final Tabs menu;
    private final MenuBar menuBar;

    public AppNavLayout() {
        Div title = new Div();
        title.setText("MyApp");
        title.getStyle()
                .set("font-size", "var(--lumo-font-size-l)")
                .set("left", "var(--lumo-space-l)")
                .set("margin", "0")
                .set("position", "absolute");
        menu = createMenuTabs();
        menuBar = createMenuBar();
        addToNavbar(title, menuBar);
    }

    private static Tabs createMenuTabs() {
        final Tabs tabs = new Tabs();
        tabs.setOrientation(Tabs.Orientation.HORIZONTAL);
        tabs.getStyle().set("margin", "auto");
        tabs.add(getAvailableTabs());
        tabs.addThemeVariants(TabsVariant.LUMO_MINIMAL);
        return tabs;
    }

    private static MenuBar createMenuBar() {
        final MenuBar menu  = new MenuBar();
        menu.getStyle().set("margin", "auto");
        menu.addItem(createTab("Synchronic Data", LandingPage.class, VaadinIcon.LAYOUT, createBadge(ERunProcessState.NONE)));
        menu.addItem(createTab("Users", UserList.class, VaadinIcon.USER));
        menu.addItem(createTab("Add User", UserInsert.class, VaadinIcon.USER_CARD));
        return menu;
    }

    private static Tab[] getAvailableTabs() {
        final List<Tab> tabs = new ArrayList<>();
        tabs.add(createTab("Synchronic Data", LandingPage.class, VaadinIcon.LAYOUT));
        tabs.add(createTab("Add User", UserInsert.class, VaadinIcon.USER_CARD));
        return tabs.toArray(new Tab[tabs.size()]);
    }

    private static Tab createTab(String title, Class<? extends Component> viewClass) {
        return createTab(new RouterLink(title, viewClass));
    }

    private static Tab createTab(String title,
                                 Class<? extends Component> viewClass, VaadinIcon icon) {
        return createTab(new Icon(icon), new RouterLink(title, viewClass));
    }

    private static Tab createTab(String title,
                                 Class<? extends Component> viewClass, VaadinIcon icon, Component component) {
        return createTab(new Icon(icon), new RouterLink(title, viewClass), component);
    }

    private static Tab createTab(String title, String href, VaadinIcon icon) {
        return createTab(new Icon(icon), new Anchor(href, title));
    }

    private static Tab createTab(Component... content) {
        final Tab tab = new Tab();
        tab.add(content);
        return tab;
    }

    /**
     * Helper method for creating a badge.
     */
    private static Div createBadge(ERunProcessState runState) {
        if(runState.equals(ERunProcessState.NONE))
            return new Div();
        else {
            Div content = new Div();
            content.add(runState.getValue());
            content.getStyle().set("margin-inline-start", "var(--lumo-space-xs)");
            return content;
        }

        //Span badge = new Span(value.getValue());
        //badge.getElement().getThemeList().add("badge small contrast");
        //badge.getStyle().set("margin-inline-start", "var(--lumo-space-xs)");
        //return badge;
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {
        // Select the matching navigation tab on page load
        String location = event.getLocation().getFirstSegment();
        menu.getChildren().forEach(component -> {
            if (component instanceof Tab) {
                Tab tab = (Tab) component;
                tab.getChildren().findFirst().ifPresent(component1 -> {
                    if (component1 instanceof RouterLink) {
                        RouterLink link = (RouterLink) component1;
                        if (link.getHref().equals(location)) {
                            menu.setSelectedTab(tab);
                        }
                    }
                });
            }
        });
    }
}
