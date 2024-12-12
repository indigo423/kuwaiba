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
package org.neotropic.kuwaiba.modules.optional.serviceman.explorers;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.TemplateRenderer;
import com.vaadin.flow.server.Command;
import org.neotropic.kuwaiba.core.apis.integration.modules.ModuleActionParameter;
import org.neotropic.kuwaiba.core.apis.integration.modules.ModuleActionParameterSet;
import org.neotropic.kuwaiba.core.apis.integration.modules.explorers.AbstractExplorer;
import org.neotropic.kuwaiba.core.apis.persistence.business.BusinessObjectLight;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.BusinessObjectNotFoundException;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.InvalidArgumentException;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.MetadataObjectNotFoundException;
import org.neotropic.kuwaiba.core.apis.persistence.util.Constants;
import org.neotropic.kuwaiba.core.i18n.TranslationService;
import org.neotropic.kuwaiba.modules.optional.serviceman.ServiceManagerService;
import org.neotropic.kuwaiba.modules.optional.serviceman.ServiceManagerUI;
import org.neotropic.kuwaiba.modules.optional.serviceman.actions.ReleaseObjectFromServiceVisualAction;
import org.neotropic.util.visual.button.ActionButton;
import org.neotropic.util.visual.notifications.AbstractNotification;
import org.neotropic.util.visual.notifications.SimpleNotification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * An explorer that allows the user to see the network resources of a service and be able to release them.
 * @author Mauricio Ruiz Beltrán {@literal <mauricio.ruiz@kuwaiba.org>}
 */
@CssImport("./styles/explorer.css")
@Component
public class NetworkResourcesExplorer extends AbstractExplorer<VerticalLayout> {

    /**
     * Reference to the Service Manager Service.
     */
    @Autowired
    private ServiceManagerService sms;
    /**
     * Reference to the translation service.
     */
    @Autowired
    private TranslationService ts;
    /**
     * Reference to the action that releases network resources from services.
     */
    @Autowired
    private ReleaseObjectFromServiceVisualAction releaseObjectFromServiceVisualAction;
    /**
     * Main layouts.
     */
    private VerticalLayout lytMain;
    /**
     * Shows the list of network resources related to the service.
     */
    private Grid<BusinessObjectLight> gridNetworkResources;
    /**
     * Saves the selected service.
     */
    private BusinessObjectLight selectedService;
    /**
     * Saves the selected network resource.
     */
    private BusinessObjectLight selectedNetworkResource;
    /**
     * Command.
     */
    private Command commandRelease;
    
    @Override
    public String getName() {
        return ts.getTranslatedString("module.serviceman.explorers.network-resources.title");
    }

    @Override
    public String getDescription() {
        return ts.getTranslatedString("module.serviceman.explorers.network-resources.description");
    }

    @Override
    public String getHeader() {
        return ts.getTranslatedString("module.serviceman.explorers.network-resources.header");
    }

    @Override
    public String appliesTo() {
        return Constants.CLASS_GENERICSERVICE;
    }

    @Override
    public VerticalLayout build(BusinessObjectLight selectedObject) {
        selectedService = selectedObject;
        setCommand();
        
        lytMain = new VerticalLayout();
        lytMain.setHeightFull();
        lytMain.setWidthFull();
        addContent();
        return lytMain;
    }
    
    /**
     * Sets a command that allows to notify that a network resource has been released and refresh to the content.
     */
    private void setCommand() {
        commandRelease = () -> {
            new SimpleNotification(ts.getTranslatedString("module.general.messages.success"),
                    String.format(
                            ts.getTranslatedString("module.serviceman.actions.release-object-from-service.ui.relationship-success"),
                             selectedNetworkResource.getName()), AbstractNotification.NotificationType.INFO, ts).open();
            addContent();
        };
    }
    
    /**
     * Adds the content to the main layout, depending on whether related network resources exist or not.
     */
    private void addContent() {
        lytMain.removeAll();
        List<BusinessObjectLight> objects = getDataProvider(selectedService);
        if (!objects.isEmpty()) {
            buildGridNetworkResources();
            gridNetworkResources.setItems(objects);
            lytMain.add(gridNetworkResources);
        } else {
            Label lblInfo = new Label(ts.getTranslatedString("module.serviceman.explorers.network-resources.no-network-resources"));
            lblInfo.setClassName("dialog-title");
            lytMain.add(lblInfo);
        }
    }
       
    /**
     * Gets the network resources related to the selected service.
     * @param selectedService The selected service.
     * @return The network resources list related to service.
     */
    private List<BusinessObjectLight> getDataProvider(BusinessObjectLight selectedService) {
        try {
            return sms.getObjectsRelatedToService(
                    selectedService.getClassName(), selectedService.getId());
        } catch (BusinessObjectNotFoundException | MetadataObjectNotFoundException | InvalidArgumentException ex) {
            new SimpleNotification(ts.getTranslatedString("module.general.messages.error"),
                    ex.getMessage(), AbstractNotification.NotificationType.ERROR, ts).open();
            return new ArrayList<>();
        }
    }
    
    /**
     * Builds the network resources grid.
     */
    private void buildGridNetworkResources() {
        gridNetworkResources = new Grid<>();
        gridNetworkResources.setSelectionMode(Grid.SelectionMode.NONE);
        gridNetworkResources.setHeight("680px");
        gridNetworkResources.setId("gridObjects");
        gridNetworkResources.addThemeVariants(GridVariant.LUMO_NO_ROW_BORDERS
                , GridVariant.LUMO_COMPACT, GridVariant.LUMO_WRAP_CELL_CONTENT);
        
        gridNetworkResources.addColumn(TemplateRenderer.<BusinessObjectLight>of(
                "<div>[[item.name]] &nbsp; <font class=\"text-secondary\">[[item.className]]</font></div>")
                .withProperty("name", BusinessObjectLight::getName)
                .withProperty("className", BusinessObjectLight::getClassName))
                .setHeader(ts.getTranslatedString("module.serviceman.explorers.network-resources.title"));
        gridNetworkResources.addComponentColumn(this::releaseResource).setTextAlign(ColumnTextAlign.END).setFlexGrow(0);
    }
    
    /**
     * Builds a component that allows to delete a client, this is shown next to the name for each of the services in the grid.
     * @param selectedObject The selected service.
     * @return Visual component that allows you to delete a service.
     */
    private com.vaadin.flow.component.Component releaseResource(BusinessObjectLight selectedObject) {
        ActionButton btnDeleteService = new ActionButton(new Icon(VaadinIcon.UNLINK),
                this.releaseObjectFromServiceVisualAction.getModuleAction().getDisplayName());
        btnDeleteService.addClickListener(event -> {
            selectedNetworkResource = selectedObject;
            releaseObjectFromServiceVisualAction.getVisualComponent(new ModuleActionParameterSet(
                    new ModuleActionParameter<>(ServiceManagerUI.PARAMETER_SERVICE, selectedService),
                    new ModuleActionParameter<>(ServiceManagerUI.PARAMETER_BUSINESS_OBJECT, selectedObject),
                    new ModuleActionParameter<>("commandRelease", commandRelease))).open();
        });

        HorizontalLayout lytActions = new HorizontalLayout(btnDeleteService);
        lytActions.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        lytActions.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        lytActions.setHeight("22px");
        return lytActions;
    }

    public void clearResources() {
    }
}