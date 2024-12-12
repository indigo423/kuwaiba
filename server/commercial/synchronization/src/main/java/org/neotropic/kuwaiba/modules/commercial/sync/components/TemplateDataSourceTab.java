/*
 * Copyright 2010-2024. Neotropic SAS <contact@neotropic.co>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.neotropic.kuwaiba.modules.commercial.sync.components;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Focusable;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.grid.editor.Editor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.BindingValidationStatus;
import com.vaadin.flow.data.provider.CallbackDataProvider;
import com.vaadin.flow.data.provider.ConfigurableFilterDataProvider;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.server.Command;
import lombok.Getter;
import org.neotropic.kuwaiba.core.apis.integration.modules.ModuleActionParameter;
import org.neotropic.kuwaiba.core.apis.integration.modules.ModuleActionParameterSet;
import org.neotropic.kuwaiba.core.apis.persistence.application.ApplicationEntityManager;
import org.neotropic.kuwaiba.core.apis.persistence.business.BusinessEntityManager;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.ApplicationObjectNotFoundException;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.InvalidArgumentException;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.UnsupportedPropertyException;
import org.neotropic.kuwaiba.core.i18n.TranslationService;
import org.neotropic.kuwaiba.modules.commercial.sync.SynchronizationService;
import org.neotropic.kuwaiba.modules.commercial.sync.actions.DeleteTemplateDataSourceVisualAction;
import org.neotropic.kuwaiba.modules.commercial.sync.model.TemplateDataSource;
import org.neotropic.util.visual.button.ActionButton;
import org.neotropic.util.visual.notifications.AbstractNotification;
import org.neotropic.util.visual.notifications.SimpleNotification;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 * This class represents a tab for managing template data sources. It includes
 * features for creating, editing, and deleting data sources, along with
 * managing their properties.
 *
 * @author Hardy Ryan Chingal Martinez {@literal <ryan.chingal@neotropic.co>}
 */
public class TemplateDataSourceTab extends Tab {

    /**
     * Parameter, template data source configuration.
     */
    public static String PARAM_TEMPLATE_DATA_SOURCE = "templateDataSource"; //NOI18N
    /**
     * Parameter command close.
     */
    public static String PARAM_COMMANDCLOSE = "commandClose";
    /**
     * Reference to the template DataSource VisualAction
     */
    private final DeleteTemplateDataSourceVisualAction deleteTemplateDataSourceVisualAction;
    /**
     * Reference to the Business Entity Manager
     */
    private final BusinessEntityManager bem;
    /**
     * Reference to the Application Entity Manager
     */
    private final ApplicationEntityManager aem;
    /**
     * Reference to the Translation Service
     */
    private final TranslationService ts;
    /**
     * Reference to the Synchronization Service
     */
    private final SynchronizationService ss;
    @Getter
    private VerticalLayout tabContent;
    /**
     * Layouts
     */
    private VerticalLayout lytRightSide;
    /**
     * The grid with sync data source configurations list
     */
    private Grid<TemplateDataSource> grdTemplateDataSrc;
    private Div divBusinessObjectName;
    private Button btnSave;
    private Binder<ParameterItemDataSource> binderCommonParemers;
    private BindingValidationStatus<?> verifiedHandler;
    /**
     * Split the content
     */
    private HorizontalLayout mainLayout;
    /**
     * Selected BusinessObjectLight
     */
    private ConfigurableFilterDataProvider<TemplateDataSource, Void, HashMap<String, String>> cdpDataSource;

    /**
     * Constructs a `TemplateDataSourceTab`.
     *
     * @param name The name of the tab.
     * @param enabled `true` if the tab is enabled; otherwise, `false`.
     * @param selectedTab `true` if the tab is selected; otherwise, `false`.
     * @param deleteTemplateDataSourceVisualAction An action for deleting
     * template data sources.
     * @param bem The Business Entity Manager.
     * @param aem The Application Entity Manager.
     * @param ss The Synchronization Service.
     * @param ts The Translation Service.
     */
    public TemplateDataSourceTab(String name, boolean enabled, boolean selectedTab,
            DeleteTemplateDataSourceVisualAction deleteTemplateDataSourceVisualAction,
            BusinessEntityManager bem,
            ApplicationEntityManager aem,
            SynchronizationService ss,
            TranslationService ts) {
        super(name);
        setEnabled(enabled);
        setSelected(selectedTab);
        this.tabContent = new VerticalLayout();
        this.deleteTemplateDataSourceVisualAction = deleteTemplateDataSourceVisualAction;
        this.bem = bem;
        this.aem = aem;
        this.ss = ss;
        this.ts = ts;
        //onAttach();
        initElements();
        //by default it will be invisible, state change will be maked by parent class
        this.tabContent.setVisible(false);
        add(tabContent);
    }

    /**
     * Initializes the elements within the tab.
     */
    private void initElements() {

        tabContent.setSizeFull();
        tabContent.setMargin(false);
        tabContent.setSpacing(false);
        tabContent.setPadding(false);

        // Main Layout
        //Left side | Right side
        mainLayout = new HorizontalLayout();
        mainLayout.setSizeFull();

        createLeftSide();
        lytRightSide = new VerticalLayout();

        tabContent.add(mainLayout);
    }

    /**
     * Creates the left side content of the tab.
     */
    private void createLeftSide() {
        ActionButton newButton = new ActionButton(new Icon(VaadinIcon.PLUS),
                ts.getTranslatedString("module.sync.actions.new-sync-data-source-configuration.description"));

        divBusinessObjectName = new Div();
        HorizontalLayout lytDataSourceHeader = new HorizontalLayout();
        grdTemplateDataSrc = new Grid<>();

        newButton.setClassName("confirm-button");
        newButton.addThemeVariants(ButtonVariant.LUMO_ICON);
        newButton.addClickListener(event -> createRightSide(new TemplateDataSource()));
        lytDataSourceHeader.addAndExpand(divBusinessObjectName);
        lytDataSourceHeader.add(newButton);
        lytDataSourceHeader.setAlignItems(FlexComponent.Alignment.CENTER);

        createGrid();
        // Main left Layout
        VerticalLayout lytLeftSide = new VerticalLayout();
        lytLeftSide.setSizeFull();
        lytLeftSide.setMargin(false);
        lytLeftSide.setSpacing(false);
        lytLeftSide.setId("left-template-lyt");
        lytLeftSide.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.STRETCH);
        lytLeftSide.add(lytDataSourceHeader, grdTemplateDataSrc);
        lytLeftSide.setHorizontalComponentAlignment(FlexComponent.Alignment.END, lytDataSourceHeader);
        mainLayout.add(lytLeftSide);
    }

    /**
     * Creates the grid for data source configurations.
     */
    private void createGrid() {

        grdTemplateDataSrc.setSelectionMode(Grid.SelectionMode.SINGLE);
        grdTemplateDataSrc.setWidthFull();
        grdTemplateDataSrc.setHeight(80, Unit.VH);
        grdTemplateDataSrc.addThemeVariants(GridVariant.LUMO_WRAP_CELL_CONTENT);
        grdTemplateDataSrc.addColumn(TemplateDataSource::getName)
                .setHeader(ts.getTranslatedString("module.sync.data-source.grid.name"))
                .setAutoWidth(true).setResizable(true);
        grdTemplateDataSrc.addColumn(TemplateDataSource::getDescription)
                .setHeader(ts.getTranslatedString("module.sync.data-source.grid.description"))
                .setAutoWidth(true).setResizable(true);
        grdTemplateDataSrc.addComponentColumn(this::addButtonsToGrid)
                .setHeader(ts.getTranslatedString("module.sync.data-source.grid.options"))
                .setAutoWidth(true).setResizable(true);

        grdTemplateDataSrc.addSelectionListener(selection -> {
            Optional<TemplateDataSource> optionalTemplateDataSource = selection.getFirstSelectedItem();
            if (optionalTemplateDataSource.isPresent()) {
                editTemplateAction(optionalTemplateDataSource.get());
            }
        });
        createGrdDataProvider();
    }

    /**
     * Creates a data provider for the data source grid.
     */
    private void createGrdDataProvider() {

        CallbackDataProvider<TemplateDataSource, HashMap<String, String>> dataSourceProvider = DataProvider.fromFilteringCallbacks(
                query -> {
                    List<TemplateDataSource> elements;
                    //filters
                    HashMap<String, String> filters = query.getFilter().orElse(null);
                    try {
                        elements = ss.getTemplateDataSrc(filters, query.getOffset(), query.getLimit());
                    } catch (ApplicationObjectNotFoundException | InvalidArgumentException
                    | UnsupportedPropertyException ex) {
                        elements = new ArrayList<>();
                    }
                    return elements.stream();
                },
                (Query<TemplateDataSource, HashMap<String, String>> query) -> {
                    //filters
                    HashMap<String, String> filters = query.getFilter().orElse(null);
                    try {
                        return ss.getTemplateDataSrcCount(filters);
                    } catch (ApplicationObjectNotFoundException | InvalidArgumentException
                    | UnsupportedPropertyException ex) {
                        return 0;
                    }
                }
        );
        cdpDataSource = dataSourceProvider.withConfigurableFilter();
        grdTemplateDataSrc.setDataProvider(cdpDataSource);
    }

    /**
     * Adds buttons to the data source grid for each item.
     *
     * @param item The data source configuration item.
     * @return Menu with allowed actions for the data source item.
     */
    private Component addButtonsToGrid(TemplateDataSource item) {
        Icon btnDeleteTemplate = new Icon(VaadinIcon.TRASH);
        MenuBar menuTemplate = new MenuBar();

        Command afterExecuteDelete = this::createGrdDataProvider;
        btnDeleteTemplate.setColor("var(--lumo-error-text-color)");
        btnDeleteTemplate.getElement().setProperty("title",
                ts.getTranslatedString("module.sync.actions.delete-sync-data-source-configuration.name"));
        btnDeleteTemplate.addClickListener(event
                -> deleteTemplateAction(afterExecuteDelete, item));
        menuTemplate.addItem(btnDeleteTemplate);
        menuTemplate.addThemeVariants(MenuBarVariant.LUMO_TERTIARY_INLINE);
        return menuTemplate;
    }

    /**
     * Creates the right side of the TemplateDataSourceTab, which includes form
     * fields for editing data source details.
     *
     * @param templateDataSource The data source entity.
     */
    private void createRightSide(TemplateDataSource templateDataSource) {
        lytRightSide.removeAll();
        lytRightSide.setSizeFull();
        lytRightSide.setMargin(false);
        lytRightSide.setSpacing(false);
        lytRightSide.setVisible(true);
        createRightSideContent(templateDataSource);
        mainLayout.add(lytRightSide);
    }

    /**
     * Initiates the action to delete a template data source.
     *
     * @param afterExecuteDelete The command to execute after the delete action.
     * @param item The data source configuration item to delete.
     */
    private void deleteTemplateAction(Command afterExecuteDelete, TemplateDataSource item) {
        this.deleteTemplateDataSourceVisualAction.getVisualComponent(new ModuleActionParameterSet(
                new ModuleActionParameter(PARAM_TEMPLATE_DATA_SOURCE, item),
                new ModuleActionParameter(PARAM_COMMANDCLOSE, afterExecuteDelete)
        )).open();
        hideRightSide();
    }

    /**
     * Initiates the action to edit a template data source.
     *
     * @param item The data source configuration item to edit.
     */
    private void editTemplateAction(TemplateDataSource item) {
        if (item != null) {
            createRightSide(item);
        } else {
            hideRightSide();
        }
    }

    /**
     * Hides the right side of the TemplateDataSourceTab.
     */
    private void hideRightSide() {
        lytRightSide.removeAll();
        lytRightSide.setVisible(false);
    }

    /**
     * Creates the right-side content, including data source details and common
     * properties.
     *
     * @param templateDataSource The data source entity.
     */
    private void createRightSideContent(TemplateDataSource templateDataSource) {
        Div labelDataSource = new Div();
        HorizontalLayout lytDataSourceHeader = new HorizontalLayout();
        btnSave = new Button(new Icon(VaadinIcon.DOWNLOAD));
        TextField txtDataSourceName = new TextField(ts.getTranslatedString("module.sync.template-data-source.name.label"));
        TextArea txaDataSourceDescription = new TextArea(ts.getTranslatedString("module.sync.template-data-source.description.label"));
        Grid<ParameterItemDataSource> grdDataSourceCommons = new Grid<>();

        grdDataSourceCommons.setHeight("10em");
        //binder general properties
        if (templateDataSource.getName() != null) {
            txtDataSourceName.setValue(templateDataSource.getName());
        }
        if (templateDataSource.getDescription() != null) {
            txaDataSourceDescription.setValue(templateDataSource.getDescription());
        }

        txtDataSourceName.setRequired(true);
        txtDataSourceName.setRequiredIndicatorVisible(true);
        txtDataSourceName.setValueChangeMode(ValueChangeMode.EAGER);
        txtDataSourceName.addValueChangeListener(event -> {
            templateDataSource.setName(event.getValue());
            validateSave(templateDataSource);
        });
        txaDataSourceDescription.setValueChangeMode(ValueChangeMode.EAGER);
        txaDataSourceDescription.setWidthFull();
        txaDataSourceDescription.addValueChangeListener(event -> {
            templateDataSource.setDescription(event.getValue());
            validateSave(templateDataSource);
        });

        btnSave.addThemeVariants(ButtonVariant.LUMO_ICON);
        btnSave.setEnabled(false);
        btnSave.addClickListener(event -> saveDatasource(templateDataSource));
        labelDataSource.getElement().setProperty("innerHTML", ts.getTranslatedString("module.sync.template-data-source.label"));
        //create right layout
        lytDataSourceHeader.addAndExpand(txtDataSourceName);
        lytDataSourceHeader.add(btnSave);
        lytDataSourceHeader.setVerticalComponentAlignment(FlexComponent.Alignment.END, btnSave);

        lytRightSide.add(labelDataSource, lytDataSourceHeader, txaDataSourceDescription);

        createCommonsGrid(grdDataSourceCommons, templateDataSource);

        validateSave(templateDataSource);
    }

    /**
     * Creates the grid for common data source properties and sets up its
     * columns.
     *
     * @param grdDataSourceCommons The grid of common properties.
     * @param templateDataSource The data source entity.
     */
    private void createCommonsGrid(Grid<ParameterItemDataSource> grdDataSourceCommons, TemplateDataSource templateDataSource) {
        Editor<ParameterItemDataSource> editor = grdDataSourceCommons.getEditor();
        grdDataSourceCommons.setHeight(55, Unit.VH);
        binderCommonParemers = new Binder<>();
        TextField txtPropertyName = new TextField();
        ValidationMessage propertyNameValidationMessage = new ValidationMessage();

        editor.setBinder(binderCommonParemers);
        Grid.Column<ParameterItemDataSource> propertyNameColumn = grdDataSourceCommons.addColumn(ParameterItemDataSource::getPropertyName)
                .setHeader(ts.getTranslatedString("module.sync.new.data-source.grid.property.name"))
                .setAutoWidth(true)
                .setResizable(true);

        Grid.Column<ParameterItemDataSource> optionsColumn = grdDataSourceCommons.addColumn(
                new ComponentRenderer<>(Button::new, (button, parameter) -> {
                    button.addThemeVariants(ButtonVariant.LUMO_TERTIARY,
                            ButtonVariant.LUMO_ERROR,
                            ButtonVariant.LUMO_ICON);
                    button.addClickListener(e -> {
                        this.removeCommonProperty(parameter, templateDataSource);
                        grdDataSourceCommons.getDataProvider().refreshAll();
                        validateSave(templateDataSource);
                    });
                    button.setIcon(new Icon(VaadinIcon.TRASH));
                })).setAutoWidth(true).setResizable(true);

        //create grid header
        HeaderRow headerRow = grdDataSourceCommons.prependHeaderRow();
        HorizontalLayout lytGridOptions = new HorizontalLayout();
        Div gridHeader = new Div();
        ActionButton btnAddProperty = new ActionButton(new Icon(VaadinIcon.PLUS));

        btnAddProperty.setClassName("confirm-button");
        btnAddProperty.addThemeVariants(ButtonVariant.LUMO_ICON);
        btnAddProperty.addClickListener(item -> addElementToTemplate(grdDataSourceCommons,
                templateDataSource, editor));
        gridHeader.getElement().setProperty("innerHTML", ts.getTranslatedString("module.sync.new.data-source.grid.commons.title"));
        lytGridOptions.addAndExpand(gridHeader);
        lytGridOptions.add(btnAddProperty);
        lytGridOptions.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        headerRow.join(propertyNameColumn, optionsColumn).setComponent(lytGridOptions);

        //add binders and editor o row
        txtPropertyName.setRequired(true);
        txtPropertyName.setRequiredIndicatorVisible(true);
        txtPropertyName.setValueChangeMode(ValueChangeMode.EAGER);
        txtPropertyName.setWidthFull();
        txtPropertyName.setPlaceholder(ts.getTranslatedString("module.sync.new.data-source.grid.property.name"));
        txtPropertyName.addKeyDownListener(Key.ENTER, event
                -> enterItemAction(grdDataSourceCommons, templateDataSource, editor));
        propertyNameColumn.setEditorComponent(txtPropertyName);

        binderCommonParemers.forField(txtPropertyName)
                .asRequired(ts.getTranslatedString("error.module.sync.new.data-source.grid.property.name"))
                .withValidationStatusHandler(
                        handler -> showValidationError(handler, binderCommonParemers,
                                propertyNameValidationMessage,
                                templateDataSource
                        ))
                .bind(ParameterItemDataSource::getPropertyName, ParameterItemDataSource::setPropertyName);
        grdDataSourceCommons.addItemDoubleClickListener(e -> {
            editor.editItem(e.getItem());
            Component editorComponent = e.getColumn().getEditorComponent();
            if (editorComponent instanceof Focusable) {
                ((Focusable) editorComponent).focus();
            }
        });

        grdDataSourceCommons.setItems(templateDataSource.getParameterToItem());
        binderCommonParemers.addValueChangeListener(event
                -> validateSave(templateDataSource));

        //add to right layout
        lytRightSide.add(grdDataSourceCommons, propertyNameValidationMessage);
    }

    /**
     * Handles the action when Enter key is pressed within a text field of the
     * data source properties grid.
     *
     * @param grdDataSourceCommons The data source properties grid.
     * @param templateDataSource The data source entity.
     * @param editor The editor for data source properties.
     */
    private void enterItemAction(Grid<ParameterItemDataSource> grdDataSourceCommons,
            TemplateDataSource templateDataSource, Editor<ParameterItemDataSource> editor) {
        //Create a new item and edit it
        editor.cancel();
        addElementToTemplate(grdDataSourceCommons, templateDataSource, editor);
    }

    /**
     * Adds a new common property to the template data source.
     *
     * @param grdDataSourceCommons The grid of common properties.
     * @param templateDataSource The data source entity.
     * @param editor The editor for data source properties.
     */
    private void addElementToTemplate(Grid<ParameterItemDataSource> grdDataSourceCommons,
            TemplateDataSource templateDataSource, Editor<ParameterItemDataSource> editor) {
        ParameterItemDataSource newItemDataSrc = new ParameterItemDataSource();
        templateDataSource.addParameterItem(newItemDataSrc);
        grdDataSourceCommons.setItems(templateDataSource.getListOfParameters());
        editor.editItem(newItemDataSrc);
        validateSave(templateDataSource);
    }

    /**
     * Validates whether the data source is ready for saving and updates the
     * Save button accordingly.
     *
     * @param templateDataSource The data source entity.
     */
    private void validateSave(TemplateDataSource templateDataSource) {
        boolean valid = templateDataSource.getName() != null
                && !templateDataSource.getName().trim().isEmpty();
        valid = valid && !templateDataSource.getListOfParameters().isEmpty()
                && templateDataSource.getListOfParameters()
                        .stream().allMatch(item
                                -> item.getPropertyName() != null
                        && !item.getPropertyName().trim().isEmpty()
                        );

        if (verifiedHandler != null) 
            valid = valid && !verifiedHandler.isError();
        

        if (valid) {
            btnSave.setEnabled(true);
            btnSave.setClassName("confirm-button");
        } else {
            btnSave.setEnabled(false);
            btnSave.removeClassName("confirm-button");
        }
    }

    /**
     * Shows validation errors or clears them based on the binding status of
     * data source properties.
     *
     * @param handler The binding validation status handler.
     * @param binder The binder for data source properties.
     * @param propertyValueValidationMessage The validation message for property
     * values.
     * @param templateDataSource The data source entity.
     */
    private void showValidationError(BindingValidationStatus<?> handler,
            Binder<ParameterItemDataSource> binder,
            ValidationMessage propertyValueValidationMessage,
            TemplateDataSource templateDataSource) {
        if (binder.getBean() != null && handler.isError()) {
            propertyValueValidationMessage.setText(handler.getMessage().get());
        } else {
            propertyValueValidationMessage.setText(null);
        }

        verifiedHandler = handler;
        validateSave(templateDataSource);
    }

    /**
     * Saves the data of the data source configuration.
     *
     * @param templateDataSource The data source configuration to save.
     */
    private void saveDatasource(TemplateDataSource templateDataSource) {
        validateSave(templateDataSource);
        if (templateDataSource.getListOfParameters() != null
                && !templateDataSource.getListOfParameters().isEmpty()) {
            try {
                ss.saveTemplateDataSource(templateDataSource);
                grdTemplateDataSrc.getDataProvider().refreshAll();

                new SimpleNotification(ts.getTranslatedString("module.general.messages.information"),
                        ts.getTranslatedString("module.sync.actions.save-template-data-source.success"),
                        AbstractNotification.NotificationType.INFO, ts).open();

            } catch (ApplicationObjectNotFoundException | InvalidArgumentException ex) {
                new SimpleNotification(ts.getTranslatedString("module.general.messages.information"),
                        ex.getMessage(),
                        AbstractNotification.NotificationType.ERROR, ts).open();
            }
        } else {
            new SimpleNotification(ts.getTranslatedString("module.general.messages.information"),
                    ts.getTranslatedString("error.module.sync.new.data-source.properties"),
                    AbstractNotification.NotificationType.ERROR, ts).open();
        }
    }

    /**
     * Removes a common property from the template data source.
     *
     * @param parameter The parameter item to remove.
     * @param templateDataSource The data source entity.
     */
    private void removeCommonProperty(ParameterItemDataSource parameter, TemplateDataSource templateDataSource) {
        templateDataSource.getListOfParameters().remove(parameter);
    }

    /**
     * Refreshes the template data source grid.
     */
    public void refreshTemplateGrd() {
        grdTemplateDataSrc.getDataProvider().refreshAll();
    }
}
