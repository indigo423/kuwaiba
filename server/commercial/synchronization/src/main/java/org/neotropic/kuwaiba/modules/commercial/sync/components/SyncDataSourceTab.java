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

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Focusable;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.accordion.AccordionPanel;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.details.DetailsVariant;
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
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.TemplateRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.server.Command;
import lombok.Getter;
import org.neotropic.kuwaiba.core.apis.integration.modules.ModuleActionParameter;
import org.neotropic.kuwaiba.core.apis.integration.modules.ModuleActionParameterSet;
import org.neotropic.kuwaiba.core.apis.persistence.application.ApplicationEntityManager;
import org.neotropic.kuwaiba.core.apis.persistence.business.BusinessEntityManager;
import org.neotropic.kuwaiba.core.apis.persistence.business.BusinessObjectLight;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.ApplicationObjectNotFoundException;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.InvalidArgumentException;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.MetadataObjectNotFoundException;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.UnsupportedPropertyException;
import org.neotropic.kuwaiba.core.apis.persistence.util.Constants;
import org.neotropic.kuwaiba.core.i18n.TranslationService;
import org.neotropic.kuwaiba.modules.commercial.sync.SynchronizationService;
import org.neotropic.kuwaiba.modules.commercial.sync.actions.AssociateSyncDataSourceToGroupVisualAction;
import org.neotropic.kuwaiba.modules.commercial.sync.actions.DeleteSyncDataSourceConfigurationVisualAction;
import org.neotropic.kuwaiba.modules.commercial.sync.actions.ReleaseSyncDataSourceConfigurationVisualAction;
import org.neotropic.kuwaiba.modules.commercial.sync.actions.RunSingleSynchronizationVisualAction;
import org.neotropic.kuwaiba.modules.commercial.sync.model.SyncDataSourceCommonParameters;
import org.neotropic.kuwaiba.modules.commercial.sync.model.SyncDataSourceConfiguration;
import org.neotropic.kuwaiba.modules.commercial.sync.model.TemplateDataSource;
import org.neotropic.kuwaiba.visualization.api.resources.ResourceFactory;
import org.neotropic.util.visual.button.ActionButton;
import org.neotropic.util.visual.icons.ClassNameIconGenerator;
import org.neotropic.util.visual.notifications.AbstractNotification;
import org.neotropic.util.visual.notifications.SimpleNotification;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 * This class represents a tab for managing data sources. It includes
 * features for creating, editing, and deleting data sources, along with
 * managing their properties.
 * 
 * @author Hardy Ryan Chingal Martinez {@literal <ryan.chingal@neotropic.co>}
 */
public class SyncDataSourceTab extends Tab {

    /**
     * display Number of classes for page
     */
    private static final int RESULTS_CLASSES_PER_PAGE = 5;
    /**
     * New business object visual action parameter business object.
     */
    public static String PARAM_BUSINESS_OBJECT = "businessObject"; //NOI18N
    /**
     * Parameter, data source configuration.
     */
    public static String PARAM_SYNC_DATA_SOURCE = "syncDataSourceConfiguration"; //NOI18N
    /**
     * Parameter, groups that will be related to datasource.
     */
    public static String PARAM_RELATED_GROUPS = "groups"; //NOI18N
    /**
     * Parameter command close.
     */
    public static String PARAM_COMMANDCLOSE = "commandClose";
    @Getter
    private final VerticalLayout tabContent;
    /**
     * The visual action for add a new Sync Data Source Configuration to sync
     * group
     */
    private final AssociateSyncDataSourceToGroupVisualAction associateSyncDataSourceToGroupVisualAction;
    /**
     * The visual action for delete Sync Data Source Configuration
     */
    private final DeleteSyncDataSourceConfigurationVisualAction deleteSyncDataSourceConfigurationVisualAction;
    /**
     * The visual action for release Sync Data Source Configuration
     */
    private final ReleaseSyncDataSourceConfigurationVisualAction releaseSyncDataSourceConfigurationVisualAction;
    /**
     * The visual action for run Synchronization
     */
    private final RunSingleSynchronizationVisualAction runSingleSynchronizationVisualAction;
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
    /**
     * Factory to build resources from data source.
     */
    private final ResourceFactory resourceFactory;
    /**
     * Search inventory object
     */
    public SearchInventaryObject searchInventaryObject;
    /**
     * Layouts
     */
    private VerticalLayout lytRightSide;
    /**
     * The grid with sync data source configurations list
     */
    private Grid<SyncDataSourceConfiguration> gridSourceConfigurations;
    private Div divBusinessObjectName;
    private Button btnSave;
    private Binder<ParameterItemDataSource> binderCommonParemers;
    private Binder<ParameterItemDataSource> binderSpecificParemers;
    private BindingValidationStatus<?> verifiedHandler;
    /**
     * The combo box with common parameters list
     */
    private ComboBox<TemplateDataSource> cmbTemplateDataSource;
    /**
     * Split the content
     */
    private HorizontalLayout mainLayout;
    /**
     * An icon generator for create icons
     */
    private ClassNameIconGenerator iconGenerator;
    /**
     * Selected BusinessObjectLight
     */
    private BusinessObjectLight selectedObject;
    /**
     * Selected BusinessObjectLight
     */
    private ConfigurableFilterDataProvider<SyncDataSourceConfiguration, Void, HashMap<String, String>> cdpDataSource;
    private Grid<ParameterItemDataSource> grdDataSourceCommons;
    private Grid<ParameterItemDataSource> grdDataSourceSpecific;

    /**
     * Creates a new SyncDataSourceTab.
     *
     * @param name The name of the tab.
     * @param enabled Whether the tab is enabled.
     * @param selectedTab Whether the tab is selected.
     * @param associateSyncDataSourceToGroupVisualAction Action for associating
     * a data source to a group.
     * @param deleteSyncDataSourceConfigurationVisualAction Action for deleting
     * a data source configuration.
     * @param releaseSyncDataSourceConfigurationVisualAction Action for
     * releasing a data source configuration.
     * @param runSingleSynchronizationVisualAction Action for running a single
     * synchronization.
     * @param resourceFactory The resource factory.
     * @param bem The BusinessEntityManager.
     * @param aem The ApplicationEntityManager.
     * @param ss The SynchronizationService.
     * @param ts The TranslationService.
     */
    public SyncDataSourceTab(String name, boolean enabled, boolean selectedTab,
            AssociateSyncDataSourceToGroupVisualAction associateSyncDataSourceToGroupVisualAction,
            DeleteSyncDataSourceConfigurationVisualAction deleteSyncDataSourceConfigurationVisualAction,
            ReleaseSyncDataSourceConfigurationVisualAction releaseSyncDataSourceConfigurationVisualAction,
            RunSingleSynchronizationVisualAction runSingleSynchronizationVisualAction,
            ResourceFactory resourceFactory,
            BusinessEntityManager bem,
            ApplicationEntityManager aem,
            SynchronizationService ss,
            TranslationService ts) {
        super(name);
        setEnabled(enabled);
        setSelected(selectedTab);
        this.tabContent = new VerticalLayout();
        this.resourceFactory = resourceFactory;
        this.associateSyncDataSourceToGroupVisualAction = associateSyncDataSourceToGroupVisualAction;
        this.deleteSyncDataSourceConfigurationVisualAction = deleteSyncDataSourceConfigurationVisualAction;
        this.releaseSyncDataSourceConfigurationVisualAction = releaseSyncDataSourceConfigurationVisualAction;
        this.runSingleSynchronizationVisualAction = runSingleSynchronizationVisualAction;
        this.bem = bem;
        this.aem = aem;
        this.ss = ss;
        this.ts = ts;
        //onAttach();
        initElements();
        //by default, it will be invisible, state change will be maked by parent class
        this.tabContent.setVisible(false);
        add(tabContent);
    }

    /**
     * Initializes the elements of the tab.
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
     * Creates the left side of the tab.
     */
    private void createLeftSide() {
        HorizontalLayout lytDataSourceHeader = new HorizontalLayout();
        divBusinessObjectName = new Div();
        gridSourceConfigurations = new Grid<>();
        // Main left Layout
        VerticalLayout lytLeftSide = new VerticalLayout();

        ActionButton newDataSourceBtn = new ActionButton(new Icon(VaadinIcon.PLUS),
                ts.getTranslatedString("module.sync.actions.new-sync-data-source-configuration.description"));
        ActionButton newButton = new ActionButton(new Icon(VaadinIcon.PLUS),
                ts.getTranslatedString("module.sync.actions.new-sync-data-source-configuration.description"));

        newButton.setClassName("confirm-button");
        newButton.addThemeVariants(ButtonVariant.LUMO_ICON);
        newButton.addClickListener(this::launchNewDataSource);

        lytDataSourceHeader.addAndExpand(divBusinessObjectName);
        lytDataSourceHeader.add(newButton);
        lytDataSourceHeader.setAlignItems(FlexComponent.Alignment.CENTER);
        lytDataSourceHeader.setVisible(false);

        newDataSourceBtn.setClassName("confirm-button");
        newDataSourceBtn.addThemeVariants(ButtonVariant.LUMO_ICON);
        newDataSourceBtn.addClickListener(event -> {
            // update layout
            lytLeftSide.removeAll();
            createSearchComponent(lytDataSourceHeader);
            lytLeftSide.add(searchInventaryObject, lytDataSourceHeader, gridSourceConfigurations);
            lytLeftSide.setHorizontalComponentAlignment(FlexComponent.Alignment.END, lytDataSourceHeader);
            lytLeftSide.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.STRETCH);
        });

        createGrid();

        //initial layout elements
        lytLeftSide.setSizeFull();
        lytLeftSide.setMargin(false);
        lytLeftSide.setSpacing(false);
        lytLeftSide.setId("left-lyt");
        lytLeftSide.add(newDataSourceBtn);
        lytLeftSide.add(gridSourceConfigurations);
        lytLeftSide.setHorizontalComponentAlignment(FlexComponent.Alignment.END, newDataSourceBtn);
        lytLeftSide.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.STRETCH);

        mainLayout.add(lytLeftSide);
    }

    /**
     * Create search inventory objects
     */
    private void createSearchComponent(HorizontalLayout lytDataSourceHeader) {
        iconGenerator = new ClassNameIconGenerator(resourceFactory);
        searchInventaryObject = new SearchInventaryObject(ts, bem, iconGenerator, event -> {
            if (event instanceof String) {// No suggestion was chosen
                processSearch((String) event, 0, RESULTS_CLASSES_PER_PAGE);
            } else { // A single element was selected
                selectedObject = (BusinessObjectLight) event;
                divBusinessObjectName.getElement().setProperty("innerHTML",
                        String.format(ts.getTranslatedString("module.sync.data-source.element.title.html"),
                                selectedObject.getName()));
                createGrdDataProvider();
            }
            searchInventaryObject.close();
            lytDataSourceHeader.setVisible(true);
        });
    }

    /**
     * Processes a search for inventory objects.
     *
     * @param searchedText The searched text.
     * @param skip The skip for pagination.
     * @param limit The limit for pagination.
     */
    private void processSearch(String searchedText, int skip, int limit) {
        try {
            List<BusinessObjectLight> searchBObjestResult;
            HashMap<String, String> filters = null;
            //if the search has changed we must execute the query again
            if (!searchedText.isEmpty()) {
                filters = new HashMap<>();
                filters.put(Constants.PROPERTY_NAME, searchedText);
            }
            searchBObjestResult = bem.getObjectsOfClassLight(Constants.CLASS_GENERICNETWORKELEMENT, filters, skip, limit);
            if (searchBObjestResult == null || searchBObjestResult.isEmpty()) {
                new SimpleNotification(ts.getTranslatedString("module.general.messages.information"),
                        ts.getTranslatedString("module.general.messages.no-search-results"),
                        AbstractNotification.NotificationType.ERROR, ts).open();

            } else {
                createGrdDataProvider();
            }
        } catch (InvalidArgumentException | MetadataObjectNotFoundException ex) {
            new SimpleNotification(ts.getTranslatedString("module.general.messages.error"), ex.getLocalizedMessage(),
                    AbstractNotification.NotificationType.ERROR, ts).open();
        }
    }

    /**
     * Launches the action to create a new data source.
     *
     * @param event The click event.
     */
    private void launchNewDataSource(ClickEvent<Button> event) {
        createRightSide(new SyncDataSourceConfiguration());
    }

    /**
     * Creates the data source grid.
     */
    private void createGrid() {
        gridSourceConfigurations.setSelectionMode(Grid.SelectionMode.SINGLE);
        gridSourceConfigurations.setWidthFull();
        gridSourceConfigurations.setHeight(80, Unit.VH);
        gridSourceConfigurations.addThemeVariants(GridVariant.LUMO_WRAP_CELL_CONTENT);
        gridSourceConfigurations.addColumn(item -> {
            if (item.getCommonParameters() != null && item.getCommonParameters().getDataSourcetype() != null) {
                return item.getCommonParameters().getDataSourcetype();
            } else {
                return "OTHER";
            }
        })
                .setHeader(ts.getTranslatedString("module.sync.data-source.grid.type"))
                .setResizable(true);
        gridSourceConfigurations.addColumn(SyncDataSourceConfiguration::getName)
                .setHeader(ts.getTranslatedString("module.sync.data-source.grid.name"))
                .setResizable(true);
        gridSourceConfigurations.addColumn(SyncDataSourceConfiguration::getDescription)
                .setHeader(ts.getTranslatedString("module.sync.data-source.grid.description"))
                .setResizable(true);
        gridSourceConfigurations.addComponentColumn(this::addButtonsToGrid)
                .setHeader(ts.getTranslatedString("module.sync.data-source.grid.options"))
                .setResizable(true);

        gridSourceConfigurations.addSelectionListener(selection -> {
            Optional<SyncDataSourceConfiguration> syncDataSourceConf = selection.getFirstSelectedItem();
            if (syncDataSourceConf.isPresent()) {
                editDataSourceAction(syncDataSourceConf.get());
            }
        });
    }

    /**
     * Creates the data provider for the data source grid.
     */
    private void createGrdDataProvider() {
        lytRightSide.removeAll();
        lytRightSide.setVisible(false);
        CallbackDataProvider<SyncDataSourceConfiguration, HashMap<String, String>> dataSourceProvider = DataProvider.fromFilteringCallbacks(
                query -> {
                    List<SyncDataSourceConfiguration> elements;
                    //filters
                    HashMap<String, String> filters = query.getFilter().orElse(null);
                    try {
                        elements = ss.getSyncDataSrcByBussinesObject(selectedObject.getId(), filters, query.getOffset(), query.getLimit());
                    } catch (ApplicationObjectNotFoundException | InvalidArgumentException
                    | UnsupportedPropertyException ex) {
                        elements = new ArrayList<>();
                    }
                    return elements.stream();
                },
                query -> {
                    //filters
                    HashMap<String, String> filters = query.getFilter().orElse(null);
                    try {
                        return ss.getSyncDataSrcByBussinesObjectCount(selectedObject.getId(), filters);
                    } catch (ApplicationObjectNotFoundException | InvalidArgumentException
                    | UnsupportedPropertyException ex) {
                        return 0;
                    }
                }
        );
        cdpDataSource = dataSourceProvider.withConfigurableFilter();
        gridSourceConfigurations.setDataProvider(cdpDataSource);
    }

    /**
     * Adds buttons to the data source grid.
     *
     * @param item A data source configuration item.
     * @return A menu with allowed actions over the data source.
     */
    private Component addButtonsToGrid(SyncDataSourceConfiguration item) {

        Icon btnRun = new Icon(VaadinIcon.PLAY_CIRCLE);
        Icon btnLink = new Icon(VaadinIcon.LINK);
        Icon btnUnlink = new Icon(VaadinIcon.UNLINK);
        Icon btnDelete = new Icon(VaadinIcon.TRASH);
        MenuBar menuDataSource = new MenuBar();

        btnRun.getElement().setProperty("title",
                ts.getTranslatedString("module.sync.data-source.button.run.description"));
        btnRun.addClickListener(event -> {
            this.runSingleSynchronizationVisualAction.getVisualComponent(new ModuleActionParameterSet(
                    new ModuleActionParameter(PARAM_SYNC_DATA_SOURCE, item)
            )).open();
        });

        btnLink.getElement().setProperty("title",
                ts.getTranslatedString("module.sync.data-source.button.link-group"));
        btnLink.addClickListener(event -> {
            this.associateSyncDataSourceToGroupVisualAction.getVisualComponent(new ModuleActionParameterSet(
                    new ModuleActionParameter(PARAM_SYNC_DATA_SOURCE, item)
            )).open();
        });

        btnUnlink.getElement().setProperty("title",
                ts.getTranslatedString("module.sync.data-source.button.unlink-group"));
        btnUnlink.addClickListener(event -> {
            this.releaseSyncDataSourceConfigurationVisualAction.getVisualComponent(new ModuleActionParameterSet(
                    new ModuleActionParameter(PARAM_SYNC_DATA_SOURCE, item)
            )).open();
        });

        Command afterExecuteDelete = this::createGrdDataProvider;
        btnDelete.setColor("var(--lumo-error-text-color)");
        btnDelete.getElement().setProperty("title",
                ts.getTranslatedString("module.sync.actions.delete-sync-data-source-configuration.name"));
        btnDelete.addClickListener(event
                -> deleteDataSourceAction(afterExecuteDelete, item));

        menuDataSource.addItem(btnRun);
        menuDataSource.addItem(btnLink);
        menuDataSource.addItem(btnUnlink);
        menuDataSource.addItem(btnDelete);
        menuDataSource.addThemeVariants(MenuBarVariant.LUMO_TERTIARY_INLINE);
        return menuDataSource;
    }

    /**
     * Initiates the action to delete a data source.
     *
     * @param afterExecuteDelete The command to execute after the delete action.
     * @param item The data source item to delete.
     */
    private void deleteDataSourceAction(Command afterExecuteDelete, SyncDataSourceConfiguration item) {
        this.deleteSyncDataSourceConfigurationVisualAction.getVisualComponent(new ModuleActionParameterSet(
                new ModuleActionParameter(PARAM_SYNC_DATA_SOURCE, item),
                new ModuleActionParameter(PARAM_COMMANDCLOSE, afterExecuteDelete)
        )).open();
        hideRightSide();
    }

    /**
     * Initiates the action to edit a data source.
     *
     * @param item The data source item to edit.
     */
    private void editDataSourceAction(SyncDataSourceConfiguration item) {
        if (item != null) {
            createRightSide(item);
        } else {
            hideRightSide();
        }
    }

    /**
     * Hides the right side of the SyncDataSourceTab.
     */
    private void hideRightSide() {
        lytRightSide.removeAll();
        lytRightSide.setVisible(false);
    }

    /**
     * Creates the right side of the tab for a data source configuration.
     *
     * @param syncDataSourceConfiguration The data source configuration.
     */
    private void createRightSide(SyncDataSourceConfiguration syncDataSourceConfiguration) {
        lytRightSide.removeAll();
        if (selectedObject != null) {
            lytRightSide.setSizeFull();
            lytRightSide.setMargin(false);
            lytRightSide.setSpacing(false);
            lytRightSide.setVisible(true);
            syncDataSourceConfiguration.setBusinessObjectLight(selectedObject);
            createRightSideContent(syncDataSourceConfiguration);
            mainLayout.add(lytRightSide);
        }
    }

    /**
     * Creates the content for the right side of the tab for a data source
     * configuration.
     *
     * @param syncDataSourceConfiguration The data source configuration.
     */
    private void createRightSideContent(SyncDataSourceConfiguration syncDataSourceConfiguration) {
        Div labelDataSource = new Div();
        HorizontalLayout lytDataSourceHeader = new HorizontalLayout();
        Accordion acoProperties = new Accordion();
        btnSave = new Button(new Icon(VaadinIcon.DOWNLOAD));
        TextField txtDataSourceType = new TextField(ts.getTranslatedString("module.sync.new.data-source.type.label"));
        TextField txtDataSourceName = new TextField(ts.getTranslatedString("module.sync.new.data-source.name.label"));
        TextArea txaDataSourceDescription = new TextArea(ts.getTranslatedString("module.sync.data-source.grid.description"));
        cmbTemplateDataSource = new ComboBox<>(ts.getTranslatedString("module.sync.template-data-source.title"));
        grdDataSourceCommons = new Grid<>();
        grdDataSourceSpecific = new Grid<>();

        createCbmCommonPropertiesProvider();
        //binder general properties
        if (syncDataSourceConfiguration.getTemplateDataSource() != null) {
            cmbTemplateDataSource.setValue(syncDataSourceConfiguration.getTemplateDataSource());
        }
        if (syncDataSourceConfiguration.getName() != null) {
            txtDataSourceName.setValue(syncDataSourceConfiguration.getName());
        }
        if (syncDataSourceConfiguration.getCommonParameters() != null
                && syncDataSourceConfiguration.getCommonParameters().getDataSourcetype() != null) {
            txtDataSourceType.setValue(syncDataSourceConfiguration.getCommonParameters().getDataSourcetype());
        }
        if (syncDataSourceConfiguration.getDescription() != null) {
            txaDataSourceDescription.setValue(syncDataSourceConfiguration.getDescription());
        }

        cmbTemplateDataSource.addValueChangeListener(event -> {
            if (event.getValue() != null) {
                syncDataSourceConfiguration.setTemplateDataSource(event.getValue());
                SyncDataSourceCommonParameters commonParameters = new SyncDataSourceCommonParameters(event.getValue().getName());
                commonParameters.setParameters(event.getValue().getParameters());
                syncDataSourceConfiguration.setCommonParameters(commonParameters);
                txtDataSourceType.setValue(event.getValue().getName());
                txtDataSourceName.setValue(String.format("%s-%s", event.getValue().getName(), selectedObject.getName()));
                grdDataSourceCommons.setItems(syncDataSourceConfiguration.getCommonParameters().getParameterToItem());
            } else {
                txtDataSourceName.clear();
                txtDataSourceType.clear();
                grdDataSourceCommons.setItems(new ArrayList<>());
            }
            validateSave(syncDataSourceConfiguration);
        });

        acoProperties.setSizeFull();
        grdDataSourceCommons.setHeight("10em");
        grdDataSourceSpecific.setHeight("10em");
        cmbTemplateDataSource.setWidthFull();
        cmbTemplateDataSource.setRequired(true);
        cmbTemplateDataSource.setRequiredIndicatorVisible(true);
        cmbTemplateDataSource.setItemLabelGenerator(TemplateDataSource::getName);
        cmbTemplateDataSource.setRenderer(getRenderer());
        txtDataSourceType.setReadOnly(true);
        txtDataSourceName.setRequired(true);
        txtDataSourceName.setRequiredIndicatorVisible(true);
        txtDataSourceName.setValueChangeMode(ValueChangeMode.EAGER);
        txtDataSourceName.addValueChangeListener(event -> {
            syncDataSourceConfiguration.setName(event.getValue());
            validateSave(syncDataSourceConfiguration);
        });
        txaDataSourceDescription.setValueChangeMode(ValueChangeMode.EAGER);
        txaDataSourceDescription.setWidthFull();
        txaDataSourceDescription.addValueChangeListener(event -> {
            syncDataSourceConfiguration.setDescription(event.getValue());
            validateSave(syncDataSourceConfiguration);
        });
        btnSave.addThemeVariants(ButtonVariant.LUMO_ICON);
        btnSave.setEnabled(false);
        btnSave.addClickListener(event -> saveDatasource(syncDataSourceConfiguration));
        labelDataSource.getElement().setProperty("innerHTML", ts.getTranslatedString("module.sync.new.data-source.label"));
        //create right layout
        lytDataSourceHeader.addAndExpand(txtDataSourceType);
        lytDataSourceHeader.addAndExpand(txtDataSourceName);
        lytDataSourceHeader.add(btnSave);
        lytDataSourceHeader.setVerticalComponentAlignment(FlexComponent.Alignment.END, btnSave);

        lytRightSide.add(labelDataSource, cmbTemplateDataSource,
                lytDataSourceHeader, txaDataSourceDescription, acoProperties);

        AccordionPanel dataSourceCommonsPanel = acoProperties.add(ts.getTranslatedString("module.sync.new.data-source.grid.commons.title"),
                createCommonsGrid(grdDataSourceCommons, syncDataSourceConfiguration));
        AccordionPanel dataSourceSpecificPanel = acoProperties.add(ts.getTranslatedString("module.sync.new.data-source.grid.specific.title"),
                createSpecificGrid(grdDataSourceSpecific, syncDataSourceConfiguration));
        dataSourceCommonsPanel.addThemeVariants(DetailsVariant.REVERSE, DetailsVariant.SMALL);
        dataSourceSpecificPanel.addThemeVariants(DetailsVariant.REVERSE, DetailsVariant.SMALL);

        validateSave(syncDataSourceConfiguration);
    }

    /**
     * Creates a ComboBox with common properties provider.
     */
    private void createCbmCommonPropertiesProvider() {
        DataProvider<TemplateDataSource, String> commonPropertiesDataProvider = DataProvider.fromFilteringCallbacks(
                query -> {
                    List<TemplateDataSource> elements;
                    //filters
                    Optional<String> objectName = query.getFilter();
                    HashMap<String, String> filters = null;
                    try {
                        if (objectName.isPresent() && !objectName.get().trim().isEmpty()) {
                            filters = new HashMap<>();
                            filters.put(Constants.PROPERTY_NAME, objectName.get());
                        }
                        elements = ss.getTemplateDataSrc(filters, query.getOffset(), query.getLimit());
                    } catch (ApplicationObjectNotFoundException | InvalidArgumentException
                    | UnsupportedPropertyException ex) {
                        elements = new ArrayList<>();
                    }
                    return elements.stream();
                },
                query -> {
                    //filters
                    Optional<String> objectName = query.getFilter();
                    HashMap<String, String> filters = null;
                    try {
                        if (objectName.isPresent() && !objectName.get().trim().isEmpty()) {
                            filters = new HashMap<>();
                            filters.put(Constants.PROPERTY_NAME, objectName.get());
                        }
                        return ss.getTemplateDataSrcCount(filters);

                    } catch (ApplicationObjectNotFoundException | InvalidArgumentException
                    | UnsupportedPropertyException ex) {
                        return 0;
                    }
                }
        );
        cmbTemplateDataSource.setDataProvider(commonPropertiesDataProvider);
    }

    /**
     * Creates the grid for common properties of a data source.
     *
     * @param grdDataSourceCommons The grid for common properties.
     * @param syncDataSourceConfiguration The data source configuration.
     * @return The main body containing the grid.
     */
    private VerticalLayout createCommonsGrid(Grid<ParameterItemDataSource> grdDataSourceCommons,
            SyncDataSourceConfiguration syncDataSourceConfiguration) {
        VerticalLayout mainBody = new VerticalLayout();
        Editor<ParameterItemDataSource> editor = grdDataSourceCommons.getEditor();
        grdDataSourceCommons.setHeight(55, Unit.VH);
        binderCommonParemers = new Binder<>();
        TextField txtPropertyName = new TextField();
        TextField txtPropertyValue = new TextField();
        ValidationMessage propertyNameValidationMessage = new ValidationMessage();
        ValidationMessage propertyValueValidationMessage = new ValidationMessage();

        mainBody.setSizeFull();
        editor.setBinder(binderCommonParemers);
        Grid.Column<ParameterItemDataSource> propertyNameColumn = grdDataSourceCommons.addColumn(ParameterItemDataSource::getPropertyName)
                .setHeader(ts.getTranslatedString("module.sync.new.data-source.grid.property.name"))
                .setAutoWidth(true)
                .setResizable(true);

        Grid.Column<ParameterItemDataSource> propertyValueColumn = grdDataSourceCommons.addColumn(ParameterItemDataSource::getPropertyValue)
                .setHeader(ts.getTranslatedString("module.sync.new.data-source.grid.property.value"))
                .setAutoWidth(true)
                .setResizable(true);

        Grid.Column<ParameterItemDataSource> optionsColumn = grdDataSourceCommons.addColumn(
                new ComponentRenderer<>(Button::new, (button, parameter) -> {
                    button.addThemeVariants(ButtonVariant.LUMO_TERTIARY, ButtonVariant.LUMO_ERROR,
                            ButtonVariant.LUMO_ICON);
                    button.addClickListener(e -> {
                        this.removeCommonProperty(parameter, syncDataSourceConfiguration);
                        grdDataSourceCommons.getDataProvider().refreshAll();
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
        btnAddProperty.addClickListener(item
                -> addCommonProperty(grdDataSourceCommons, syncDataSourceConfiguration, editor));

        gridHeader.getElement().setProperty("innerHTML",
                ts.getTranslatedString("module.sync.new.data-source.grid.commons.description.html"));
        lytGridOptions.addAndExpand(gridHeader);
        lytGridOptions.add(btnAddProperty);
        lytGridOptions.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        headerRow.join(propertyNameColumn, propertyValueColumn, optionsColumn).setComponent(lytGridOptions);

        //add binders and editor to name
        txtPropertyName.setRequired(true);
        txtPropertyName.setRequiredIndicatorVisible(true);
        txtPropertyName.setValueChangeMode(ValueChangeMode.EAGER);
        txtPropertyName.setWidthFull();
        txtPropertyName.setPlaceholder(ts.getTranslatedString("module.sync.new.data-source.grid.property.name"));
        txtPropertyName.addKeyDownListener(Key.ENTER, event
                -> enterCommonPropertyAction(grdDataSourceCommons,
                        syncDataSourceConfiguration, editor));
        propertyNameColumn.setEditorComponent(txtPropertyName);
        binderCommonParemers.forField(txtPropertyName)
                .asRequired(ts.getTranslatedString("error.module.sync.new.data-source.grid.property.name"))
                .withValidationStatusHandler(
                        handler -> showValidationError(handler, binderCommonParemers,
                                propertyNameValidationMessage,
                                syncDataSourceConfiguration))
                .bind(ParameterItemDataSource::getPropertyName, ParameterItemDataSource::setPropertyName);

        //add binders and editor to value
        txtPropertyValue.setRequired(true);
        txtPropertyValue.setRequiredIndicatorVisible(true);
        txtPropertyValue.setValueChangeMode(ValueChangeMode.EAGER);
        txtPropertyValue.setWidthFull();
        txtPropertyValue.setPlaceholder(ts.getTranslatedString("module.sync.new.data-source.grid.property.value"));
        txtPropertyValue.addKeyDownListener(Key.ENTER, event
                -> enterCommonPropertyAction(grdDataSourceCommons,
                        syncDataSourceConfiguration, editor));
        propertyValueColumn.setEditorComponent(txtPropertyValue);
        binderCommonParemers.forField(txtPropertyValue)
                .asRequired(ts.getTranslatedString("error.module.sync.new.data-source.grid.property.value"))
                //.withValidator(item -> item != null && !item.trim().isEmpty(), ts.getTranslatedString("error.module.sync.new.data-source.grid.property.value"))
                .withValidationStatusHandler(
                        handler -> showValidationError(handler, binderCommonParemers,
                                propertyValueValidationMessage,
                                syncDataSourceConfiguration))
                .bind(ParameterItemDataSource::getPropertyValue, ParameterItemDataSource::setPropertyValue);

        grdDataSourceCommons.addItemDoubleClickListener(e -> {
            if (e.getItem() != null) {
                grdDataSourceCommons.getDataProvider().refreshAll();
                editor.editItem(e.getItem());
                Component editorComponent = e.getColumn().getEditorComponent();
                if (editorComponent instanceof Focusable) {
                    ((Focusable) editorComponent).focus();
                }
            }
        });

        grdDataSourceCommons.setItems(syncDataSourceConfiguration.getCommonParameters().getParameterToItem());
        binderCommonParemers.addValueChangeListener(event
                -> validateSave(syncDataSourceConfiguration)
        );
        //add to right layout
        mainBody.add(grdDataSourceCommons, propertyNameValidationMessage, propertyValueValidationMessage);
        return mainBody;
    }

    /**
     * Adds a common property to the grid and initializes its editing.
     *
     * @param grdDataSourceCommons The grid for common properties.
     * @param syncDataSourceConfiguration The data source configuration.
     * @param editor The data source editor.
     */
    private void addCommonProperty(Grid<ParameterItemDataSource> grdDataSourceCommons,
            SyncDataSourceConfiguration syncDataSourceConfiguration, Editor<ParameterItemDataSource> editor) {

        ParameterItemDataSource newItemDataSrc = new ParameterItemDataSource();
        syncDataSourceConfiguration.getCommonParameters().addParameterItem(newItemDataSrc);
        grdDataSourceCommons.setItems(syncDataSourceConfiguration.getCommonParameters().getListOfParameters());
        editor.editItem(newItemDataSrc);
        validateSave(syncDataSourceConfiguration);
    }

    /**
     * Realiza una acción común al ingresar una propiedad en la cuadrícula de
     * parámetros.
     *
     * @param grdDataSourceCommons Cuadrícula de propiedades comunes.
     * @param syncDataSourceConfiguration Configuración de la fuente de datos
     * sincronizada.
     * @param editor Editor para los parámetros de la fuente de datos.
     */
    private void enterCommonPropertyAction(Grid<ParameterItemDataSource> grdDataSourceCommons,
            SyncDataSourceConfiguration syncDataSourceConfiguration,
            Editor<ParameterItemDataSource> editor) {
        // Cancel editing of the current item
        if (editor.getItem().getPropertyValue() != null
                && !editor.getItem().getPropertyValue().trim().isEmpty()
                && editor.getItem().getPropertyName() != null
                && !editor.getItem().getPropertyName().trim().isEmpty()) {
            editor.cancel();
            grdDataSourceCommons.deselectAll();
            validateSave(syncDataSourceConfiguration);
            grdDataSourceCommons.getDataProvider().refreshAll();
        }
    }

    /**
     * Creates and returns a vertical layout that represents a grid for specific
     * properties in the data source configuration.
     *
     * @param grdDataSourceSpecific The grid of specific properties.
     * @param syncDataSourceConfiguration The data source entity.
     * @return The vertical layout containing the specific property grid.
     */
    private VerticalLayout createSpecificGrid(Grid<ParameterItemDataSource> grdDataSourceSpecific,
            SyncDataSourceConfiguration syncDataSourceConfiguration) {
        VerticalLayout mainBody = new VerticalLayout();
        Editor<ParameterItemDataSource> editor = grdDataSourceSpecific.getEditor();
        grdDataSourceSpecific.setHeight(56, Unit.VH);
        binderSpecificParemers = new Binder<>();
        TextField txtPropertyName = new TextField();
        TextField txtPropertyValue = new TextField();
        ValidationMessage propertyNameValidationMessage = new ValidationMessage();
        ValidationMessage propertyValueValidationMessage = new ValidationMessage();

        mainBody.setSizeFull();
        editor.setBinder(binderSpecificParemers);
        Grid.Column<ParameterItemDataSource> propertyNameColumn = grdDataSourceSpecific.addColumn(ParameterItemDataSource::getPropertyName)
                .setHeader(ts.getTranslatedString("module.sync.new.data-source.grid.property.name"))
                .setAutoWidth(true)
                .setResizable(true);

        Grid.Column<ParameterItemDataSource> propertyValueColumn = grdDataSourceSpecific.addColumn(ParameterItemDataSource::getPropertyValue)
                .setHeader(ts.getTranslatedString("module.sync.new.data-source.grid.property.value"))
                .setAutoWidth(true)
                .setResizable(true);

        Grid.Column<ParameterItemDataSource> optionsColumn = grdDataSourceSpecific.addColumn(
                new ComponentRenderer<>(Button::new, (button, parameter) -> {
                    button.addThemeVariants(ButtonVariant.LUMO_TERTIARY, ButtonVariant.LUMO_ERROR,
                            ButtonVariant.LUMO_ICON);
                    button.addClickListener(e -> {
                        this.removeSpecificProperty(parameter, syncDataSourceConfiguration);
                        grdDataSourceSpecific.getDataProvider().refreshAll();
                    });
                    button.setIcon(new Icon(VaadinIcon.TRASH));
                })).setAutoWidth(true).setResizable(true);

        //create grid header
        HeaderRow headerRow = grdDataSourceSpecific.prependHeaderRow();
        HorizontalLayout lytGridOptions = new HorizontalLayout();
        Div gridHeader = new Div();
        ActionButton btnAddProperty = new ActionButton(new Icon(VaadinIcon.PLUS));

        //add property
        btnAddProperty.setClassName("confirm-button");
        btnAddProperty.addThemeVariants(ButtonVariant.LUMO_ICON);
        btnAddProperty.addClickListener(item
                -> addSpecificProperty(grdDataSourceSpecific, syncDataSourceConfiguration, editor));

        gridHeader.getElement().setProperty("innerHTML",
                 ts.getTranslatedString("module.sync.new.data-source.grid.specific.description.html"));
        lytGridOptions.addAndExpand(gridHeader);
        lytGridOptions.add(btnAddProperty);
        lytGridOptions.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        headerRow.join(propertyNameColumn, propertyValueColumn, optionsColumn).setComponent(lytGridOptions);

        //add binders and editor to name
        txtPropertyName.setRequired(true);
        txtPropertyName.setRequiredIndicatorVisible(true);
        txtPropertyName.setValueChangeMode(ValueChangeMode.EAGER);
        txtPropertyName.setWidthFull();
        txtPropertyName.setPlaceholder(ts.getTranslatedString("module.sync.new.data-source.grid.property.name"));
        txtPropertyName.addKeyDownListener(Key.ENTER, event
                -> enterSpecificPropertyAction(grdDataSourceSpecific,
                        syncDataSourceConfiguration, editor));
        propertyNameColumn.setEditorComponent(txtPropertyName);
        binderSpecificParemers.forField(txtPropertyName)
                .asRequired(ts.getTranslatedString("error.module.sync.new.data-source.grid.property.name"))
                .withValidationStatusHandler(
                        handler -> showValidationError(handler, binderSpecificParemers,
                                propertyNameValidationMessage,
                                syncDataSourceConfiguration
                        ))
                .bind(ParameterItemDataSource::getPropertyName, ParameterItemDataSource::setPropertyName);

        //add binders and editor to value
        txtPropertyValue.setRequired(true);
        txtPropertyValue.setRequiredIndicatorVisible(true);
        txtPropertyValue.setValueChangeMode(ValueChangeMode.EAGER);
        txtPropertyValue.setWidthFull();
        txtPropertyValue.setPlaceholder(ts.getTranslatedString("module.sync.new.data-source.grid.property.value"));
        txtPropertyValue.addKeyDownListener(Key.ENTER, event
                -> enterSpecificPropertyAction(grdDataSourceSpecific,
                        syncDataSourceConfiguration, editor));
        propertyValueColumn.setEditorComponent(txtPropertyValue);
        binderSpecificParemers.forField(txtPropertyValue)
                .asRequired(ts.getTranslatedString("error.module.sync.new.data-source.grid.property.value"))
                .withValidationStatusHandler(
                        handler -> showValidationError(handler, binderSpecificParemers,
                                propertyValueValidationMessage,
                                syncDataSourceConfiguration
                        ))
                .bind(ParameterItemDataSource::getPropertyValue, ParameterItemDataSource::setPropertyValue);

        grdDataSourceSpecific.addItemDoubleClickListener(e -> {
            if (e.getItem() != null) {
                grdDataSourceSpecific.getDataProvider().refreshAll();
                editor.editItem(e.getItem());
                Component editorComponent = e.getColumn().getEditorComponent();
                if (editorComponent instanceof Focusable) {
                    ((Focusable) editorComponent).focus();
                }
            }
        });

        grdDataSourceSpecific.setItems(syncDataSourceConfiguration.getParameterToItem());
        binderSpecificParemers.addValueChangeListener(event
                -> validateSave(syncDataSourceConfiguration));
        //add to right layout
        mainBody.add(grdDataSourceSpecific, propertyNameValidationMessage, propertyValueValidationMessage);
        return mainBody;
    }

    /**
     * Adds a new specific property to the grid of specific properties.
     *
     * @param grdDataSourceSpecific The grid of specific properties.
     * @param syncDataSourceConfiguration The data source entity.
     * @param editor The editor for ParameterItemDataSource.
     */
    private void addSpecificProperty(Grid<ParameterItemDataSource> grdDataSourceSpecific,
            SyncDataSourceConfiguration syncDataSourceConfiguration,
            Editor<ParameterItemDataSource> editor) {
        ParameterItemDataSource newItemDataSrc = new ParameterItemDataSource();
        syncDataSourceConfiguration.addParameterItem(newItemDataSrc);
        grdDataSourceSpecific.setItems(syncDataSourceConfiguration.getListOfParameters());
        editor.editItem(newItemDataSrc);
        validateSave(syncDataSourceConfiguration);
    }

    /**
     * This method handles the specific property action in the data source
     * configuration. It cancels editing of the current item if necessary and
     * performs various actions to refresh the grid.
     *
     * @param grdDataSourceSpecific The grid of specific properties.
     * @param syncDataSourceConfiguration The data source entity.
     * @param editor The editor for ParameterItemDataSource.
     */
    private void enterSpecificPropertyAction(Grid<ParameterItemDataSource> grdDataSourceSpecific,
             SyncDataSourceConfiguration syncDataSourceConfiguration,
             Editor<ParameterItemDataSource> editor) {
        // Cancel editing of the current item
        if (editor.getItem().getPropertyValue() != null
                && !editor.getItem().getPropertyValue().trim().isEmpty()
                && editor.getItem().getPropertyName() != null
                && !editor.getItem().getPropertyName().trim().isEmpty()) {
            editor.cancel();
            grdDataSourceSpecific.deselectAll();
            validateSave(syncDataSourceConfiguration);
            grdDataSourceSpecific.getDataProvider().refreshAll();
        }
    }

    /**
     * Validates the data source configuration for saving and updates the state
     * of the "Save" button accordingly.
     *
     * @param syncDataSourceConfiguration The data source entity to validate.
     */
    private void validateSave(SyncDataSourceConfiguration syncDataSourceConfiguration) {
        boolean valid = syncDataSourceConfiguration.getName() != null
                && !syncDataSourceConfiguration.getName().trim().isEmpty();
        // verify specific properties
        if (!syncDataSourceConfiguration.getListOfParameters().isEmpty()) {
            valid = valid && syncDataSourceConfiguration.getListOfParameters()
                    .stream().allMatch(item
                            -> item.getPropertyName() != null
                    && !item.getPropertyName().trim().isEmpty()
                    && item.getPropertyValue() != null
                    && !item.getPropertyValue().trim().isEmpty());
        }
        // verify common properties
        valid = valid && syncDataSourceConfiguration.getCommonParameters() != null;
        valid = valid
                && syncDataSourceConfiguration.getCommonParameters().getDataSourcetype() != null;
        valid = valid
                && !syncDataSourceConfiguration.getCommonParameters().getListOfParameters().isEmpty()
                && syncDataSourceConfiguration.getCommonParameters().getListOfParameters()
                        .stream().allMatch(item -> item.getPropertyName() != null
                        && !item.getPropertyName().trim().isEmpty()
                        && item.getPropertyValue() != null
                        && !item.getPropertyValue().trim().isEmpty());
        if (verifiedHandler != null) {
            valid = valid && !verifiedHandler.isError();
        }

        if (valid) {
            btnSave.setEnabled(true);
            btnSave.setClassName("confirm-button");
        } else {
            btnSave.setEnabled(false);
            btnSave.removeClassName("confirm-button");
        }
    }

    /**
     * Convenience method for displaying validation errors related to
     * properties. It updates the validation message and the state of the "Save"
     * button.
     *
     * @param handler The binding validation status handler.
     * @param binder The binder for ParameterItemDataSource.
     * @param propertyValueValidationMessage The validation message element.
     * @param syncDataSourceConfiguration The data source entity being
     * validated.
     */
    private void showValidationError(BindingValidationStatus<?> handler,
            Binder<ParameterItemDataSource> binder,
            ValidationMessage propertyValueValidationMessage,
            SyncDataSourceConfiguration syncDataSourceConfiguration) {
        if (binder.getBean() != null && handler.isError()) {
            propertyValueValidationMessage.setText(handler.getMessage().get());
        } else {
            propertyValueValidationMessage.setText(null);
        }

        verifiedHandler = handler;
        validateSave(syncDataSourceConfiguration);
    }

    /**
     * Saves the data of the data source configuration after validation.
     *
     * @param syncDataSourceConfiguration The data source configuration to be
     * saved.
     */
    private void saveDatasource(SyncDataSourceConfiguration syncDataSourceConfiguration) {
        validateSave(syncDataSourceConfiguration);
        if (syncDataSourceConfiguration.getCommonParameters() != null
                && syncDataSourceConfiguration.getCommonParameters().getDataSourcetype() != null) {
            try {
                ss.saveDataSource(syncDataSourceConfiguration);
                gridSourceConfigurations.getDataProvider().refreshAll();
                if (syncDataSourceConfiguration.getId() > 0) {
                    new SimpleNotification(ts.getTranslatedString("module.general.messages.information"),
                            ts.getTranslatedString("successfully.module.sync.new.data-source.edit"),
                            AbstractNotification.NotificationType.INFO, ts).open();
                } else {
                    new SimpleNotification(ts.getTranslatedString("module.general.messages.information"),
                            ts.getTranslatedString("successfully.module.sync.new.data-source.save"),
                            AbstractNotification.NotificationType.INFO, ts).open();
                }

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
        grdDataSourceSpecific.getDataProvider().refreshAll();
        grdDataSourceCommons.getDataProvider().refreshAll();
    }

    /**
     * Removes a specific property from the data source configuration.
     *
     * @param parameter The ParameterItemDataSource to be removed.
     * @param syncDataSourceConfiguration The data source entity.
     */
    private void removeSpecificProperty(ParameterItemDataSource parameter, SyncDataSourceConfiguration syncDataSourceConfiguration) {
        syncDataSourceConfiguration.getListOfParameters().remove(parameter);
    }

    /**
     * Removes a common property from the data source configuration.
     *
     * @param parameter The ParameterItemDataSource to be removed.
     * @param syncDataSourceConfiguration The data source entity.
     */
    private void removeCommonProperty(ParameterItemDataSource parameter, SyncDataSourceConfiguration syncDataSourceConfiguration) {
        syncDataSourceConfiguration.getCommonParameters().getListOfParameters().remove(parameter);
    }

    /**
     * Returns a TemplateRenderer for rendering a TemplateDataSource in a
     * specific format.
     *
     * @return The TemplateRenderer for TemplateDataSource.
     */
    private TemplateRenderer<TemplateDataSource> getRenderer() {
        StringBuilder tpl = new StringBuilder();
        tpl.append("<div style=\"display: flex;\">");
        tpl.append("    [[item.name]] - ");
        tpl.append("    <div style=\"font-size: var(--lumo-font-size-s); color: var(--lumo-secondary-text-color);\">[[[item.description]]]</div>");
        tpl.append("  </div>");
        tpl.append("</div>");

        return TemplateRenderer.<TemplateDataSource>of(tpl.toString())
                .withProperty("name", TemplateDataSource::getName)
                .withProperty("description", TemplateDataSource::getDescription);
    }
}
