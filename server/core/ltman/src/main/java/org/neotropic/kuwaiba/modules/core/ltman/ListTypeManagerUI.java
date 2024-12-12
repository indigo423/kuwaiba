/*
 *  Copyright 2010-2024 Neotropic SAS <contact@neotropic.co>.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       https://apache.org/licenses/LICENSE-2.0.txt
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.neotropic.kuwaiba.modules.core.ltman;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.BoxSizing;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamRegistration;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.VaadinSession;
import elemental.json.Json;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.neotropic.kuwaiba.core.apis.integration.dashboards.AbstractUI;
import org.neotropic.kuwaiba.core.apis.integration.modules.ModuleActionParameter;
import org.neotropic.kuwaiba.core.apis.integration.modules.ModuleActionParameterSet;
import org.neotropic.kuwaiba.core.apis.integration.modules.actions.ActionCompletedListener;
import org.neotropic.kuwaiba.core.apis.integration.modules.actions.ActionResponse;
import org.neotropic.kuwaiba.core.apis.persistence.application.ApplicationEntityManager;
import org.neotropic.kuwaiba.core.apis.persistence.application.FileObject;
import org.neotropic.kuwaiba.core.apis.persistence.application.FileObjectLight;
import org.neotropic.kuwaiba.core.apis.persistence.business.BusinessObject;
import org.neotropic.kuwaiba.core.apis.persistence.business.BusinessObjectLight;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.ApplicationObjectNotFoundException;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.BusinessObjectNotFoundException;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.InvalidArgumentException;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.InventoryException;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.MetadataObjectNotFoundException;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.NotAuthorizedException;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.OperationNotPermittedException;
import org.neotropic.kuwaiba.core.apis.persistence.metadata.ClassMetadataLight;
import org.neotropic.kuwaiba.core.apis.persistence.metadata.MetadataEntityManager;
import org.neotropic.kuwaiba.core.apis.persistence.util.Constants;
import org.neotropic.kuwaiba.core.i18n.TranslationService;
import org.neotropic.kuwaiba.modules.core.ltman.actions.DeleteListTypeItemVisualAction;
import org.neotropic.kuwaiba.modules.core.ltman.actions.NewListTypeItemVisualAction;
import org.neotropic.kuwaiba.modules.core.ltman.providers.ListTypeItemProvider;
import org.neotropic.kuwaiba.modules.core.ltman.providers.ListTypeItemUsesProvider;
import org.neotropic.kuwaiba.modules.core.navigation.actions.ShowMoreInformationAction;
import org.neotropic.kuwaiba.visualization.api.properties.PropertyFactory;
import org.neotropic.kuwaiba.visualization.api.properties.PropertyValueConverter;
import org.neotropic.util.visual.button.ActionButton;
import org.neotropic.util.visual.dialog.ConfirmDialog;
import org.neotropic.util.visual.icon.ActionIcon;
import org.neotropic.util.visual.notifications.AbstractNotification;
import org.neotropic.util.visual.notifications.SimpleNotification;
import org.neotropic.util.visual.properties.AbstractProperty;
import org.neotropic.util.visual.properties.PropertySheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.tatu.BeanTable;
import org.vaadin.tatu.BeanTableVariant;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.neotropic.kuwaiba.modules.core.logging.LoggerType;
import org.neotropic.kuwaiba.modules.core.logging.LoggingService;

/**
 * Main for the List type manager module. This class manages how the pages corresponding 
 * to different functionalities are presented in a single place.
 * @author Charles Edward Bedon Cortazar {@literal <charles.bedon@kuwaiba.org>}
 */
@Route(value = "ltman", layout = ListTypeManagerLayout.class)
public class ListTypeManagerUI extends VerticalLayout implements ActionCompletedListener, 
        PropertySheet.IPropertyValueChangedListener, HasDynamicTitle, AbstractUI {

    /**
     * the visual action to create a new list type item
     */
    @Autowired
    private NewListTypeItemVisualAction newListTypeItemVisualAction;

    @Autowired
    private TranslationService ts;
    /**
     * Reference to the Metadata Entity Manager.
     */
    @Autowired
    private MetadataEntityManager mem;
    /**
     * Reference to the Application Entity Manager.
     */
    @Autowired
    private ApplicationEntityManager aem;
    /**
     * Reference to the Logging Service.
     */
    @Autowired
    private LoggingService log;
    /**
     * The grid with the list Types
     */
    private final Grid<ClassMetadataLight> tblListTypes;
    /**
     * The grid with the list type items.
     */
    private BeanTable<BusinessObjectLight> gridListTypeItems;
    /**
     * The grid with the attached files
     */
    private Grid<FileObjectLight> grdFiles;
     /**
     * Label displayed when no files has been attached
     */
    private Label lblNoFiles;
    /**
     * object to save the selected list type
     */
    private ClassMetadataLight currentListType;
    /**
     * object to save the selected list type item
     */
    private BusinessObjectLight currentListTypeItem;
    /**
     * the top horizontal layout center side holds the current selected list type item
     */
    private HorizontalLayout lytCurrentSelectedTitle;
    /**
     * Second column the list of list type items
     */
    private VerticalLayout lytListTypeItems;
    /**
     * third column details of the list type item
     */
    private VerticalLayout lytTabs;
    /**
     * The window to show more information about an object.
     */
    @Autowired
    private ShowMoreInformationAction windowMoreInformation;
            
    @Autowired
    private DeleteListTypeItemVisualAction deleteListTypeItemVisualAction;
    
    PropertySheet propertysheet;

    public ListTypeManagerUI() {
        super();
        tblListTypes = new Grid<>();
        setSizeFull();       
    }
    
    @Override
    public void onDetach(DetachEvent ev) {
        this.newListTypeItemVisualAction.unregisterListener(this);
        this.deleteListTypeItemVisualAction.unregisterListener(this);
    }
    
    @Override
    public void actionCompleted(ActionCompletedListener.ActionCompletedEvent ev) {
        if (ev.getStatus() == ActionCompletedListener.ActionCompletedEvent.STATUS_SUCCESS) {
            if(ev.getActionResponse().containsKey(ActionResponse.ActionType.ADD)) {
                String newId = (String) ev.getActionResponse().get(Constants.PROPERTY_ID);
                ClassMetadataLight selectedListType = (ClassMetadataLight)ev.getActionResponse().get(NewListTypeItemVisualAction.PARAM_LIST_TYPE);

                if (tblListTypes != null && newId != null && selectedListType != null) {
                    currentListType = selectedListType;
                    tblListTypes.select(currentListType);
                    buildListTypeItemsHeader(currentListType);
                    buildListTypeItemsGrid(currentListType);
                    //Don't delete this, it would be useful as example if it is need to get all the data provider elements as list
                    //List<BusinessObjectLight> lista = tblListTypeItems.getDataProvider().fetch(new Query<>()).collect(Collectors.toList());
                    if (gridListTypeItems != null) {
                        BusinessObjectLight createdObj = gridListTypeItems.getDataProvider().fetch(new Query<>())
                            .filter(x -> x.getId().equals(newId))
                            .findAny().get();
                        gridListTypeItems.select(createdObj);
                    }
                }
            } else { // Removing lti
                currentListTypeItem = null;
                if (currentListType != null) {
                    buildListTypeItemsHeader(currentListType);
                    buildListTypeItemsGrid(currentListType);
                    lytTabs.setVisible(false);
                    propertysheet.clear();
                }
            }
            new SimpleNotification(ts.getTranslatedString("module.general.messages.success"),
                    ev.getMessage(), AbstractNotification.NotificationType.INFO, ts).open();
        } else
            new SimpleNotification(ts.getTranslatedString("module.general.messages.error"),
                    ev.getMessage(), AbstractNotification.NotificationType.ERROR, ts).open();
    }

    private void loadFiles() {
        try {
            List<FileObjectLight> files = aem.getFilesForListTypeItem(currentListTypeItem.getClassName(), currentListTypeItem.getId());
            grdFiles.setItems(files);
            grdFiles.getDataProvider().refreshAll();
            lblNoFiles.setVisible(files.isEmpty());
        } catch (BusinessObjectNotFoundException | MetadataObjectNotFoundException | InvalidArgumentException ex) {
            log.writeLogMessage(LoggerType.ERROR, ListTypeManagerUI.class, "", ex);
        }
    }

    private void updatePropertySheet() {
        try {
            BusinessObject aWholeListTypeItem = aem.getListTypeItem(currentListTypeItem.getClassName(), currentListTypeItem.getId());
            propertysheet.setItems(PropertyFactory.propertiesFromBusinessObject(aWholeListTypeItem, ts, aem, mem, log));
        } catch (InventoryException ex) {
            new SimpleNotification(ts.getTranslatedString("module.general.messages.error"),
                    ex.getLocalizedMessage(), AbstractNotification.NotificationType.ERROR, ts).open();
            log.writeLogMessage(LoggerType.ERROR, ListTypeManagerUI.class, "", ex);
            new SimpleNotification(ts.getTranslatedString("module.general.messages.error"),
                    ts.getTranslatedString("module.general.messages.unexpected-error"),
                    AbstractNotification.NotificationType.ERROR, ts).open();
        }
    }

    /**
     * Builds and configures the grid for displaying list types.
     */
    private void buildListTypeGrid() {
        try {
            // Build list type grid
            List<ClassMetadataLight> listTypes = mem.getSubClassesLight(Constants.CLASS_GENERICOBJECTLIST,
                    false, false);

            ListDataProvider<ClassMetadataLight> dataProvider = new ListDataProvider<>(listTypes);
            tblListTypes.setSelectionMode(Grid.SelectionMode.SINGLE);
            tblListTypes.setDataProvider(dataProvider);
            tblListTypes.setHeightFull();
            Grid.Column<ClassMetadataLight> columnName = tblListTypes.addColumn(ClassMetadataLight::getName)
                    .setKey(ts.getTranslatedString("module.general.labels.name"));

            tblListTypes.addSelectionListener(ev -> {
                if (ev.getFirstSelectedItem().isPresent()) {
                    propertysheet.clear();
                    lytTabs.setVisible(false);
                    currentListType = ev.getFirstSelectedItem().get();
                    buildListTypeItemsHeader(ev.getFirstSelectedItem().get());
                    buildListTypeItemsGrid(ev.getFirstSelectedItem().get());
                }
            });

            HeaderRow filterRow = tblListTypes.appendHeaderRow();

            TextField txtFilterListTypeName = createTxtFieldListTypeName(dataProvider);
            filterRow.getCell(columnName).setComponent(txtFilterListTypeName);

            tblListTypes.appendFooterRow().getCell(columnName).setComponent(
                    new Label(String.format("%s: %s: %s", ts.getTranslatedString("module.general.labels.total")
                            , listTypes.size(), ts.getTranslatedString("module.ltman.list-types"))));

            tblListTypes.addThemeVariants(GridVariant.LUMO_NO_BORDER,
                    GridVariant.LUMO_NO_ROW_BORDERS, GridVariant.LUMO_COMPACT);
        } catch (MetadataObjectNotFoundException ex) {
            log.writeLogMessage(LoggerType.ERROR, ListTypeManagerUI.class, "", ex);
            new SimpleNotification(ts.getTranslatedString("module.general.messages.error"),
                    ex.getLocalizedMessage(), AbstractNotification.NotificationType.ERROR, ts).open();
        }
    }

    /**
     * Builds the header for the list type items section.
     *
     * @param listType the selected list type whose header is being built.
     */
    private void buildListTypeItemsHeader(ClassMetadataLight listType) {
        lytCurrentSelectedTitle.removeAll();

        Button btnListType = new Button(listType.getDisplayName().isEmpty() ? listType.getName() : listType.getDisplayName());
        btnListType.getElement().setProperty("title", listType.getDisplayName().isEmpty() ? listType.getName() : listType.getDisplayName());
        btnListType.setSizeUndefined();
        btnListType.addClassName("breadcrumbs-button");
        btnListType.addClickListener(e -> buildListTypeItemsGrid(listType));

        lytCurrentSelectedTitle.add(btnListType);
    }

    /**
     * Builds and populates the grid for list type items.
     *
     * @param listType the selected list type whose items are to be displayed in the grid
     */
    private void buildListTypeItemsGrid(ClassMetadataLight listType) {
        lytListTypeItems.removeAll();

        gridListTypeItems = new BeanTable<>(BusinessObjectLight.class, false, 20);
        gridListTypeItems.addComponentColumn(null, this::buildGridComponentColumn).setWidth("100%");
        gridListTypeItems.addComponentColumn(null, this::createListTypeItemActionGrid);

        gridListTypeItems.setI18n(buildI18nForGrid());
        gridListTypeItems.setDataProvider(new ListTypeItemProvider(aem, ts).buildDataProvider(listType));
        gridListTypeItems.setHeight("auto");
        gridListTypeItems.setWidthFull();

        gridListTypeItems.addClassName("bean-table-hide-header");
        if (gridListTypeItems.getRowCount() <= 20) //when page is not necessary
            gridListTypeItems.addClassName("bean-table-hide-footer");

        gridListTypeItems.addThemeVariants(
                BeanTableVariant.NO_BORDER,
                BeanTableVariant.NO_ROW_BORDERS,
                BeanTableVariant.WRAP_CELL_CONTENT
        );

        lytListTypeItems.add(gridListTypeItems);
    }

    /**
     * Builds a horizontal layout for a grid column, displaying the name of an item.
     *
     * @param item The item to be displayed in the grid column.
     * @return A HorizontalLayout containing the item's name label with click functionality.
     */
    private HorizontalLayout buildGridComponentColumn(BusinessObjectLight item) {
        Label lblName = new Label(item.getName());
        lblName.setWidthFull();
        lblName.getElement().getStyle().set("cursor", "pointer");

        HorizontalLayout lytName = new HorizontalLayout(lblName);
        lytName.setPadding(false);
        lytName.setSpacing(false);
        lytName.setMargin(false);
        lytName.setWidthFull();

        lytName.addClickListener(e -> {
            currentListTypeItem = item;
            updatePropertySheet();
            lytTabs.setVisible(true);
            loadFiles();
        });

        return lytName;
    }
    
    /**
     * Create a new input field to filter list types in the header row.
     *
     * @param dataProvider Data provider to filter.
     * @return The new input field filter.
     */
    private TextField createTxtFieldListTypeName(ListDataProvider<ClassMetadataLight> dataProvider) {
        TextField txtListTypeName = new TextField();
        txtListTypeName.setSuffixComponent(new Icon(VaadinIcon.SEARCH));
        txtListTypeName.setPlaceholder(ts.getTranslatedString("module.general.labels.filter-placeholder"));
        txtListTypeName.setValueChangeMode(ValueChangeMode.EAGER);
        txtListTypeName.setWidthFull();
        txtListTypeName.addValueChangeListener(e -> dataProvider.addFilter(
            project -> StringUtils.containsIgnoreCase(project.getName(),
                txtListTypeName.getValue())));
        return txtListTypeName;
    }

    /**
     * Constructs a set of action buttons representing available options for a given list type item.
     * These options typically include viewing usages, more information and deleting the list type item.
     *
     * @param listTypeItem The list type item for which action buttons are constructed.
     * @return A horizontal layout containing action buttons for the list type item.
     */
    private HorizontalLayout createListTypeItemActionGrid(BusinessObjectLight listTypeItem) {
        ActionButton btnUsages = new ActionButton(new ActionIcon(VaadinIcon.SPLIT),
                ts.getTranslatedString("module.ltman.uses"));
        btnUsages.addClickListener(e -> buildUsagesDialog(listTypeItem));
        btnUsages.setHeight("25px");

        ActionButton btnDelete = new ActionButton(new ActionIcon(VaadinIcon.TRASH),
                this.deleteListTypeItemVisualAction.getModuleAction().getDisplayName());
        btnDelete.addClickListener(e ->
                this.deleteListTypeItemVisualAction.getVisualComponent(new ModuleActionParameterSet(
                        new ModuleActionParameter<>("listTypeItem", listTypeItem))).open()
        );
        btnDelete.setHeight("25px");

        ActionButton btnInfo = new ActionButton(new ActionIcon(VaadinIcon.INFO_CIRCLE),
                this.windowMoreInformation.getDisplayName());
        btnInfo.addClickListener(e ->
                this.windowMoreInformation.getVisualComponent(new ModuleActionParameterSet(
                        new ModuleActionParameter<>("object", listTypeItem))).open()
        );
        btnInfo.setHeight("25px");

        HorizontalLayout lyt = new HorizontalLayout(btnUsages, btnInfo, btnDelete);
        lyt.setSpacing(true);
        return lyt;
    }

    /**
     * Constructs and displays a dialog showing the usages of a given list type item.
     * Allows users to confirm releasing the usages.
     *
     * @param listTypeItem The list type item for which usages are displayed.
     */
    private void buildUsagesDialog(BusinessObjectLight listTypeItem) {
        ConfirmDialog wdwUses = new ConfirmDialog(ts, String.format(
                ts.getTranslatedString("module.ltman.list-typeitem-references"), listTypeItem.toString()));
        wdwUses.getBtnConfirm().setText(ts.getTranslatedString("module.ltman.release-usages"));
        wdwUses.getBtnCancel().setText(ts.getTranslatedString("module.general.messages.close"));
        wdwUses.getBtnConfirm().addClickListener(evt -> buildConfirmReleasesDialog(wdwUses, listTypeItem));
        wdwUses.setContent(buildUsagesGrid(wdwUses, listTypeItem));
        wdwUses.setWidth("80%");
        wdwUses.setHeight("90%");
        wdwUses.open();
    }

    /**
     * Constructs a grid displaying the usages of a given list type item.
     *
     * @param wdwUses      The confirm dialog associated with the usages grid.
     * @param listTypeItem The list type item for which usages are displayed.
     * @return The constructed grid displaying usages of the list type item.
     */
    private BeanTable<BusinessObjectLight> buildUsagesGrid(ConfirmDialog wdwUses, BusinessObjectLight listTypeItem) {
        BeanTable<BusinessObjectLight> gridUsages = new BeanTable<>(BusinessObjectLight.class, false, 20);
        gridUsages.addColumn(ts.getTranslatedString("module.general.labels.name"), BusinessObjectLight::getName).setWidth("50%");
        gridUsages.addColumn(ts.getTranslatedString("module.general.labels.class-name"), BusinessObjectLight::getClassName).setWidth("50%");

        gridUsages.setI18n(buildI18nForGrid());
        gridUsages.setDataProvider(new ListTypeItemUsesProvider(aem, ts).buildDataProvider(listTypeItem));
        gridUsages.setHeight("auto");
        gridUsages.setWidthFull();

        gridUsages.addClassNames("bean-table-hide-header-index", "bean-table-hide-body-index");
        if (gridUsages.getRowCount() <= 20) //when page is not necessary
            gridUsages.addClassName("bean-table-hide-footer");

        gridUsages.addThemeVariants(
                BeanTableVariant.NO_BORDER,
                BeanTableVariant.NO_ROW_BORDERS,
                BeanTableVariant.WRAP_CELL_CONTENT
        );

        if (gridUsages.getRowCount() == 0)
            wdwUses.getBtnConfirm().setVisible(false);

        return gridUsages;
    }

    /**
     * Builds the internationalization framework for the table.
     *
     * @return The BeanTable I18n.
     */
    private BeanTable.BeanTableI18n buildI18nForGrid() {
        BeanTable.BeanTableI18n i18n = new BeanTable.BeanTableI18n();
        i18n.setNoDataText(ts.getTranslatedString("module.general.labels.no-data"));
        i18n.setNextPage(ts.getTranslatedString("module.general.labels.next-page"));
        i18n.setErrorText(ts.getTranslatedString("module.general.labels.error-text"));
        i18n.setLastPage(ts.getTranslatedString("module.general.labels.last-page"));
        i18n.setFirstPage(ts.getTranslatedString("module.general.labels.first-page"));
        i18n.setMenuButton(ts.getTranslatedString("module.general.labels.menu-button"));
        i18n.setPreviousPage(ts.getTranslatedString("module.general.labels.previous-page"));
        i18n.setPageProvider((currentPage, lastPage) -> String.format(
                ts.getTranslatedString("module.general.labels.page-of"), currentPage, lastPage));
        return i18n;
    }

    /**
     * Constructs and displays a confirmation dialog for releasing a list type item.
     *
     * @param wdwUses      The confirm dialog associated with the usages of the list type item.
     * @param listTypeItem The list type item to be released.
     */
    private void buildConfirmReleasesDialog(ConfirmDialog wdwUses, BusinessObjectLight listTypeItem) {
        ConfirmDialog wdwConfirm = new ConfirmDialog(ts,
                ts.getTranslatedString("module.general.labels.confirmation"),
                ts.getTranslatedString("module.ltman.confirm-release")
        );
        wdwConfirm.getBtnConfirm().addClickListener(l -> {
            try {
                aem.releaseListTypeItem(listTypeItem.getClassName(), listTypeItem.getId());
                new SimpleNotification(ts.getTranslatedString("module.general.messages.success"),
                        ts.getTranslatedString("module.ltman.lti-released"),
                        AbstractNotification.NotificationType.INFO, ts).open();
                wdwUses.close();
                wdwConfirm.close();
            } catch (MetadataObjectNotFoundException | BusinessObjectNotFoundException |
                     OperationNotPermittedException | InvalidArgumentException | NotAuthorizedException ex) {
                log.writeLogMessage(LoggerType.ERROR, ListTypeManagerUI.class, "", ex);
                new SimpleNotification(ts.getTranslatedString("module.general.messages.error"),
                        ex.getLocalizedMessage(), AbstractNotification.NotificationType.ERROR, ts).open();
                wdwConfirm.close();
            }
        });
        wdwConfirm.open();
    }

    @Override
    public void updatePropertyChanged(AbstractProperty property) {
        try {
            if (currentListTypeItem != null) {
                
                HashMap<String, String> attributes = new HashMap<>();
                attributes.put(property.getName(), PropertyValueConverter.getAsStringToPersist(property));

                aem.updateListTypeItem(currentListTypeItem.getClassName(), currentListTypeItem.getId(), attributes);

                buildListTypeItemsHeader(currentListType);
                buildListTypeItemsGrid(currentListType);
                gridListTypeItems.select(currentListTypeItem);

                updatePropertySheet();

                new SimpleNotification(ts.getTranslatedString("module.general.messages.success"),
                        ts.getTranslatedString("module.general.messages.property-updated-successfully"),
                        AbstractNotification.NotificationType.INFO, ts).open();
            }
        } catch (MetadataObjectNotFoundException | ApplicationObjectNotFoundException
                 | InvalidArgumentException ex) {
            log.writeLogMessage(LoggerType.ERROR, ListTypeManagerUI.class, "", ex);
            propertysheet.undoLastEdit();
        }
    }

    @Override
    public String getPageTitle() {
        return ts.getTranslatedString("module.ltman.title");
    }

    @Override
    public void initContent() {
        setSizeFull();
        HorizontalLayout lytButton = new HorizontalLayout();
        lytButton.setWidth("25%");
        lytButton.setDefaultVerticalComponentAlignment(Alignment.CENTER);
        lytButton.setBoxSizing(BoxSizing.BORDER_BOX);
        
        lytCurrentSelectedTitle = new HorizontalLayout();
        lytCurrentSelectedTitle.setWidth("30%");
        lytCurrentSelectedTitle.getStyle().set("padding-left", "var(--lumo-space-m)");
        lytCurrentSelectedTitle.setBoxSizing(BoxSizing.BORDER_BOX);
        lytCurrentSelectedTitle.setDefaultVerticalComponentAlignment(Alignment.CENTER);

        HorizontalLayout lytPropertiesTitle = new HorizontalLayout();
        lytPropertiesTitle.setWidth("45%");
        lytPropertiesTitle.getStyle().set("padding-left", "var(--lumo-space-m)");
        lytPropertiesTitle.setBoxSizing(BoxSizing.BORDER_BOX);
        lytPropertiesTitle.setDefaultVerticalComponentAlignment(Alignment.END);

        HorizontalLayout lytTop = new HorizontalLayout(lytButton, lytCurrentSelectedTitle, lytPropertiesTitle);
        lytTop.setWidthFull();
        lytTop.setMinHeight("25px");
        lytButton.getStyle().set("padding-left", "var(--lumo-space-m)");
        
        HorizontalLayout lytMainContent = new HorizontalLayout();
        lytMainContent.setSizeFull();

        this.newListTypeItemVisualAction.registerActionCompletedLister(this);
        this.deleteListTypeItemVisualAction.registerActionCompletedLister(this);

        Button btnAddListTypeItem = new Button(this.newListTypeItemVisualAction.getModuleAction().getDisplayName()
                , new Icon(VaadinIcon.PLUS)
                , e -> this.newListTypeItemVisualAction.getVisualComponent(
                        new ModuleActionParameterSet(new ModuleActionParameter<>(
                                        NewListTypeItemVisualAction.PARAM_LIST_TYPE
                                        , currentListType))).open()
        );
        btnAddListTypeItem.setClassName("nav-button");
        lytButton.add(btnAddListTypeItem);

        VerticalLayout lytListTypes = new VerticalLayout(tblListTypes);
        lytListTypes.setWidth("25%");
        lytListTypes.setSpacing(false);
        buildListTypeGrid();

        lytListTypeItems = new VerticalLayout();
        lytListTypeItems.setHeightFull();
        lytListTypeItems.setWidth("30%");

        propertysheet = new PropertySheet(ts, new ArrayList<>());
        propertysheet.addPropertyValueChangedListener(this);
        propertysheet.setHeightByRows(true);

        VerticalLayout lytPropertySheet = new VerticalLayout(propertysheet);

        Tab tabPs = new Tab(ts.getTranslatedString("module.propertysheet.labels.header"));
        Div page1 = new Div();
        page1.setSizeFull();
        page1.add(lytPropertySheet);

        Tab tabFiles = new Tab(ts.getTranslatedString("module.navigation.actions.attach-file.name"));
        Div page2 = new Div();
        page2.add(createFilesTab());
        page2.setVisible(false);

        Map<Tab, Component> tabsToPages = new HashMap<>();
        tabsToPages.put(tabPs, page1);
        tabsToPages.put(tabFiles, page2);
        Tabs tabs = new Tabs(tabPs, tabFiles);
        Div pages = new Div(page1, page2);
        pages.setWidthFull();
        Set<Component> pagesShown = Stream.of(page1)
                .collect(Collectors.toSet());

        tabs.addSelectedChangeListener(event -> {
            pagesShown.forEach(page -> page.setVisible(false));
            pagesShown.clear();
            Component selectedPage = tabsToPages.get(tabs.getSelectedTab());
            selectedPage.setVisible(true);
            pagesShown.add(selectedPage);
        });

        lytPropertiesTitle.add(tabs);

        lytTabs = new VerticalLayout(pages);
        lytTabs.setWidth("45%");
        lytTabs.setSpacing(false);
        lytTabs.setPadding(false);

        lytMainContent.add(lytListTypes, lytListTypeItems, lytTabs);

        add(lytTop, lytMainContent);
    }

    private Component createFilesTab() {
        MemoryBuffer bufferIcon = new MemoryBuffer();
        Upload uploadIcon = new Upload(bufferIcon);
        uploadIcon.setWidthFull();
        uploadIcon.setMaxFiles(1);
        uploadIcon.setDropLabel(new Label(ts.getTranslatedString("module.queries.dropmessage")));
        uploadIcon.addSucceededListener(event -> {
            try {
                byte [] imageData = IOUtils.toByteArray(bufferIcon.getInputStream());
                if (currentListTypeItem != null) { 
                    aem.attachFileToListTypeItem(event.getFileName(), "", imageData, 
                            currentListTypeItem.getClassName(), currentListTypeItem.getId());

                    loadFiles();
                    uploadIcon.getElement().setPropertyJson("files", Json.createArray());
                    new SimpleNotification(ts.getTranslatedString("module.general.messages.success"),
                            ts.getTranslatedString("module.queries.file-attached"),
                            AbstractNotification.NotificationType.INFO, ts).open();
                }
            } catch (IOException | MetadataObjectNotFoundException | InvalidArgumentException |
                     BusinessObjectNotFoundException | OperationNotPermittedException ex) {
                uploadIcon.getElement().setPropertyJson("files", Json.createArray());
                new SimpleNotification(ts.getTranslatedString("module.general.messages.error"),
                        ex.getLocalizedMessage(), AbstractNotification.NotificationType.ERROR, ts).open();
            }
        });

        uploadIcon.addFileRejectedListener(event -> {
            new SimpleNotification(ts.getTranslatedString("module.general.messages.error"),
                    event.getErrorMessage(), AbstractNotification.NotificationType.ERROR, ts).open();
        });
        
        lblNoFiles = new Label(ts.getTranslatedString("module.ltman.no-files-found-for-object"));
        lblNoFiles.setVisible(false);
        grdFiles = new Grid<>();
        grdFiles.addColumn(FileObjectLight::getName);
        grdFiles.addComponentColumn(item -> {
           Button btnDeleteFile = new Button(new Icon(VaadinIcon.TRASH), evt -> {
               
               ConfirmDialog dlgConfirm = new ConfirmDialog(ts, ts.getTranslatedString("module.general.labels.confirmation"));
               dlgConfirm.setWidth("400px");
               dlgConfirm.getBtnConfirm().addClickListener(listener -> {
                   try {
                       aem.detachFileFromListTypeItem( item.getFileOjectId(), currentListTypeItem.getClassName(), currentListTypeItem.getId());
                       loadFiles();
                       new SimpleNotification(ts.getTranslatedString("module.general.messages.success"), ts.getTranslatedString("module.queries.file-deleted"),
                               AbstractNotification.NotificationType.INFO, ts).open();
                       dlgConfirm.close();
                   } catch (BusinessObjectNotFoundException | InvalidArgumentException | MetadataObjectNotFoundException ex) {
                       log.writeLogMessage(LoggerType.ERROR, ListTypeManagerUI.class, "", ex);
                   }
               });
               dlgConfirm.open();            
           });
            btnDeleteFile.setClassName("icon-button");
            Anchor download = new Anchor();
            download.setId("anchorDownload");
            download.getElement().setAttribute("download", true);
            download.setClassName("hidden");
            download.getElement().setAttribute("visibility", "hidden");
            Button btnDownloadAnchor = new Button();
            btnDownloadAnchor.getElement().setAttribute("visibility", "hidden");
            Button btnDownload = new Button(new Icon(VaadinIcon.DOWNLOAD));
            btnDownload.setClassName("icon-button");
            btnDownload.addClickListener(evt -> {
               try {
                   FileObject fo = aem.getFile(item.getFileOjectId(), currentListTypeItem.getClassName(), currentListTypeItem.getId());
                   final StreamRegistration regn = VaadinSession.getCurrent().getResourceRegistry().
                           registerResource(createStreamResource(item.getName(), fo.getFile()));  
                   download.setHref(regn.getResourceUri().getPath());
                   btnDownloadAnchor.clickInClient();
               } catch (BusinessObjectNotFoundException | InvalidArgumentException | MetadataObjectNotFoundException ex) {
                    log.writeLogMessage(LoggerType.ERROR, ListTypeManagerUI.class, "", ex);
               }
            });
            download.add(btnDownloadAnchor);
           
           return new HorizontalLayout(btnDeleteFile, btnDownload, download);                   
        });
        
        VerticalLayout lytFiles = new VerticalLayout(uploadIcon, lblNoFiles, grdFiles);
        lytFiles.setSpacing(false);
        return lytFiles;
    }
    
    private StreamResource createStreamResource(String name, byte[] ba) {
        return new StreamResource(name, () -> new ByteArrayInputStream(ba));                                
    } 
}