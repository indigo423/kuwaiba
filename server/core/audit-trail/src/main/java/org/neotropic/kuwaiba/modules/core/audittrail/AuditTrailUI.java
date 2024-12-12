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
package org.neotropic.kuwaiba.modules.core.audittrail;

import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.router.Route;
import org.neotropic.kuwaiba.core.apis.integration.dashboards.AbstractUI;
import org.neotropic.kuwaiba.core.apis.integration.modules.actions.ActionCompletedListener;
import org.neotropic.kuwaiba.core.apis.persistence.application.ActivityLogEntry;
import org.neotropic.kuwaiba.core.apis.persistence.application.ApplicationEntityManager;
import org.neotropic.kuwaiba.core.apis.persistence.application.UserProfile;
import org.neotropic.kuwaiba.core.apis.persistence.application.UserProfileLight;
import org.neotropic.kuwaiba.core.i18n.TranslationService;
import org.neotropic.kuwaiba.modules.core.audittrail.tools.AuditTrailType;
import org.neotropic.util.visual.button.ActionButton;
import org.neotropic.util.visual.icon.ActionIcon;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.tatu.BeanTable;
import org.vaadin.tatu.BeanTableVariant;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Main for the Audit Trail module. This class manages how the pages corresponding
 * to different functionalities are present in a single place.
 *
 * @author Mauricio Ruiz Beltrán {@literal <mauricio.ruiz@kuwaiba.org>}
 */
@Route(value = "audit-trail", layout = AuditTrailLayout.class)
public class AuditTrailUI extends VerticalLayout implements ActionCompletedListener, 
        HasDynamicTitle, AbstractUI {
    /**
     * Reference to the Translation Service
     */
    @Autowired
    private TranslationService ts;
    /**
     * Reference to the Application Entity Manager.
     */
    @Autowired
    private ApplicationEntityManager aem;
    /**
     * The grid with the list audit trail
     */
    private BeanTable<ActivityLogEntry> gridAuditTrail;
    /**
     * Save activity type to display later in readable format
     */
    private HashMap<Integer, String> types;
    private List<AuditTrailType> listType;
    /**
     * Save filter values
     */
    private HashMap<String, Object> filters;
    private ComboBox<AuditTrailType> cmbType;
    private ComboBox<UserProfile> cmbUser;
    /**
     * Main layout
     */
    private VerticalLayout lytMain;
    /**
     * Layout for header.
     */
    private HorizontalLayout lytHeader;
    /**
     * Layout for grid data.
     */
    private VerticalLayout lytGrid;
    
    @Override
    public void actionCompleted(ActionCompletedEvent ev) { }

    @Override
    public String getPageTitle() {
        return ts.getTranslatedString("module.audit-trail.title");
    }
    
    public AuditTrailUI() {
        super();
        setSizeFull();
    }
    
    @Override
    public void onDetach(DetachEvent ev) { }

    /**
     * Initializes the content of the UI.
     */
    @Override
    public void initContent() {
        setSizeFull();
        setMargin(false);
        setSpacing(false);
        setPadding(false);

        filters = new HashMap<>();
        setupLayouts();
        buildHeader();
        getActivityType();
        loadActivityType();
        getFilters();
        buildAuditTrailGrid();

        lytMain.add(lytHeader, lytGrid);
        add(lytMain);
    }

    /**
     * Sets up the layout components used in the UI.
     * <p>
     * lytMain:
     * ____________________________________________
     * lytHeader
     * <p>
     * lytGrid
     * ____________________________________________
     */
    private void setupLayouts() {
        if (lytMain == null) {
            lytMain = new VerticalLayout();
            lytMain.setPadding(true);
            lytMain.setSpacing(true);
            lytMain.setHeightFull();
            lytMain.setWidthFull();
        }
        if (lytHeader == null) {
            lytHeader = new HorizontalLayout();
            lytHeader.setPadding(false);
            lytHeader.setSpacing(true);
            lytHeader.setWidthFull();
        }
        if (lytGrid == null) {
            lytGrid = new VerticalLayout();
            lytGrid.setPadding(false);
            lytGrid.setSpacing(false);
            lytGrid.setHeightFull();
            lytGrid.setWidthFull();
        }
    }

    /**
     * Builds the header section of the UI.
     */
    private void buildHeader() {
        Label header = new Label(ts.getTranslatedString("module.audit-trail.header"));
        header.setClassName("audit-trail-main-header");

        ActionButton btnRefresh = new ActionButton(new ActionIcon(VaadinIcon.REFRESH),
                ts.getTranslatedString("module.audit-trail.actions.refresh"));
        btnRefresh.addClickListener(event -> {
            cleanFilters();
            updateDataProvider();
        });
        btnRefresh.setHeight("32px");

        lytHeader.add(header, btnRefresh);
    }

    /**
     * Gets activity types and stores them in the types map.
     */
    private void getActivityType() {
        types = new HashMap<>();
        types.put(ActivityLogEntry.ACTIVITY_TYPE_CREATE_APPLICATION_OBJECT, "module.audit-trail.activity-type.create-application-object");
        types.put(ActivityLogEntry.ACTIVITY_TYPE_DELETE_APPLICATION_OBJECT, "module.audit-trail.activity-type.delete-application-object");
        types.put(ActivityLogEntry.ACTIVITY_TYPE_UPDATE_APPLICATION_OBJECT, "module.audit-trail.activity-type.update-aplication-object");
        types.put(ActivityLogEntry.ACTIVITY_TYPE_CREATE_INVENTORY_OBJECT, "module.audit-trail.activity-type.create-inventory-object");
        types.put(ActivityLogEntry.ACTIVITY_TYPE_DELETE_INVENTORY_OBJECT, "module.audit-trail.activity-type.delete-inventory-object");
        types.put(ActivityLogEntry.ACTIVITY_TYPE_UPDATE_INVENTORY_OBJECT, "module.audit-trail.activity-type.update-inventory-object");
        types.put(ActivityLogEntry.ACTIVITY_TYPE_CREATE_METADATA_OBJECT, "module.audit-trail.activity-type.create-metadata-object");
        types.put(ActivityLogEntry.ACTIVITY_TYPE_DELETE_METADATA_OBJECT, "module.audit-trail.activity-type.delete-metadata-object");
        types.put(ActivityLogEntry.ACTIVITY_TYPE_UPDATE_METADATA_OBJECT, "module.audit-trail.activity-type.update-metadata-object");
        types.put(ActivityLogEntry.ACTIVITY_TYPE_CHANGE_PARENT, "module.audit-trail.activity-type.move-object");
        types.put(ActivityLogEntry.ACTIVITY_TYPE_MASSIVE_DELETE_APPLICATION_OBJECT, "module.audit-trail.activity-type.massive-delete");
        types.put(ActivityLogEntry.ACTIVITY_TYPE_UPDATE_VIEW, "module.audit-trail.activity-type.view-update");
        types.put(ActivityLogEntry.ACTIVITY_TYPE_OPEN_SESSION, "module.audit-trail.activity-type.session-created");
        types.put(ActivityLogEntry.ACTIVITY_TYPE_CLOSE_SESSION, "module.audit-trail.activity-type.session-closed");
        types.put(ActivityLogEntry.ACTIVITY_TYPE_CREATE_USER, "module.audit-trail.activity-type.new-user");
        types.put(ActivityLogEntry.ACTIVITY_TYPE_MASSIVE_UPDATE_APPLICATION_OBJECT, "module.audit-trail.activity-type.massive-application-object-update");
        types.put(ActivityLogEntry.ACTIVITY_TYPE_CREATE_RELATIONSHIP_INVENTORY_OBJECT, "module.audit-trail.activity-type.create-inventory-object-relationship");
        types.put(ActivityLogEntry.ACTIVITY_TYPE_RELEASE_RELATIONSHIP_INVENTORY_OBJECT, "module.audit-trail.activity-type.release-inventory-object-relationship");
        types.put(ActivityLogEntry.ACTIVITY_TYPE_EXTERNAL_APP_EVENT, "module.audit-trail.activity-type.external-app-event");
    }

    /**
     * Loads activity types and stores them in the listType.
     */
    private void loadActivityType() {
        listType = new ArrayList<>();
        listType.add(new AuditTrailType(ActivityLogEntry.ACTIVITY_TYPE_CREATE_APPLICATION_OBJECT, "module.audit-trail.activity-type.create-application-object"));
        listType.add(new AuditTrailType(ActivityLogEntry.ACTIVITY_TYPE_DELETE_APPLICATION_OBJECT, "module.audit-trail.activity-type.delete-application-object"));
        listType.add(new AuditTrailType(ActivityLogEntry.ACTIVITY_TYPE_UPDATE_APPLICATION_OBJECT, "module.audit-trail.activity-type.update-aplication-object"));
        listType.add(new AuditTrailType(ActivityLogEntry.ACTIVITY_TYPE_CREATE_INVENTORY_OBJECT, "module.audit-trail.activity-type.create-inventory-object"));
        listType.add(new AuditTrailType(ActivityLogEntry.ACTIVITY_TYPE_DELETE_INVENTORY_OBJECT, "module.audit-trail.activity-type.delete-inventory-object"));
        listType.add(new AuditTrailType(ActivityLogEntry.ACTIVITY_TYPE_UPDATE_INVENTORY_OBJECT, "module.audit-trail.activity-type.update-inventory-object"));
        listType.add(new AuditTrailType(ActivityLogEntry.ACTIVITY_TYPE_CREATE_METADATA_OBJECT, "module.audit-trail.activity-type.create-metadata-object"));
        listType.add(new AuditTrailType(ActivityLogEntry.ACTIVITY_TYPE_DELETE_METADATA_OBJECT, "module.audit-trail.activity-type.delete-metadata-object"));
        listType.add(new AuditTrailType(ActivityLogEntry.ACTIVITY_TYPE_UPDATE_METADATA_OBJECT, "module.audit-trail.activity-type.update-metadata-object"));
        listType.add(new AuditTrailType(ActivityLogEntry.ACTIVITY_TYPE_CHANGE_PARENT, "module.audit-trail.activity-type.move-object"));
        listType.add(new AuditTrailType(ActivityLogEntry.ACTIVITY_TYPE_MASSIVE_DELETE_APPLICATION_OBJECT, "module.audit-trail.activity-type.massive-delete"));
        listType.add(new AuditTrailType(ActivityLogEntry.ACTIVITY_TYPE_UPDATE_VIEW, "module.audit-trail.activity-type.view-update"));
        listType.add(new AuditTrailType(ActivityLogEntry.ACTIVITY_TYPE_OPEN_SESSION, "module.audit-trail.activity-type.session-created"));
        listType.add(new AuditTrailType(ActivityLogEntry.ACTIVITY_TYPE_CLOSE_SESSION, "module.audit-trail.activity-type.session-closed"));
        listType.add(new AuditTrailType(ActivityLogEntry.ACTIVITY_TYPE_CREATE_USER, "module.audit-trail.activity-type.new-user"));
        listType.add(new AuditTrailType(ActivityLogEntry.ACTIVITY_TYPE_MASSIVE_UPDATE_APPLICATION_OBJECT, "module.audit-trail.activity-type.massive-application-object-update"));
        listType.add(new AuditTrailType(ActivityLogEntry.ACTIVITY_TYPE_CREATE_RELATIONSHIP_INVENTORY_OBJECT, "module.audit-trail.activity-type.create-inventory-object-relationship"));
        listType.add(new AuditTrailType(ActivityLogEntry.ACTIVITY_TYPE_RELEASE_RELATIONSHIP_INVENTORY_OBJECT, "module.audit-trail.activity-type.release-inventory-object-relationship"));
        listType.add(new AuditTrailType(ActivityLogEntry.ACTIVITY_TYPE_EXTERNAL_APP_EVENT, "module.audit-trail.activity-type.external-app-event"));
    }

    /**
     * Initializes and configures filter components.
     */
    private void getFilters() {
        //type filter
        cmbType = new ComboBox<>();
        cmbType.setSizeFull();
        cmbType.setItems(listType);
        cmbType.setItemLabelGenerator(type -> ts.getTranslatedString(type.getDisplayName()));
        cmbType.setAllowCustomValue(false);
        cmbType.setClearButtonVisible(true);
        cmbType.setPlaceholder(ts.getTranslatedString("module.audit-trail.activity-type"));
        cmbType.addValueChangeListener(event -> {
            if (event.getValue() != null)
                filters.put("type", event.getValue().getType());
            else
                filters.remove("type");

            updateDataProvider();
        });

        //user filter
        List<UserProfile> listUsers = aem.getUsers();
        cmbUser = new ComboBox<>();
        cmbUser.setSizeFull();
        cmbUser.setItems(listUsers);
        cmbUser.setItemLabelGenerator(UserProfileLight::getUserName);
        cmbUser.setAllowCustomValue(false);
        cmbUser.setClearButtonVisible(true);
        cmbUser.setPlaceholder(ts.getTranslatedString("module.audit-trail.activity-user"));
        cmbUser.addValueChangeListener(event -> {
            if (event.getValue() != null)
                filters.put("user", event.getValue().getUserName());
            else
                filters.remove("user");

            updateDataProvider();
        });
    }

    /**
     * Builds the audit trail grid for displaying activity log entries.
     */
    private void buildAuditTrailGrid() {
        gridAuditTrail = new BeanTable<>(ActivityLogEntry.class, false, 22);
        gridAuditTrail.addColumn(ts.getTranslatedString("module.audit-trail.activity-timestamp"),
                item -> new Date(item.getTimestamp())).setWidth("14%");
        gridAuditTrail.addColumn(ts.getTranslatedString("module.audit-trail.activity-type"),
                item -> ts.getTranslatedString(types.get(item.getType()))).setHeader(cmbType).setWidth("14%");
        gridAuditTrail.addColumn(ts.getTranslatedString("module.audit-trail.activity-user"),
                ActivityLogEntry::getUserName).setHeader(cmbUser).setWidth("14%");
        gridAuditTrail.addColumn(ts.getTranslatedString("module.audit-trail.activity-property"),
                ActivityLogEntry::getAffectedProperty).setWidth("14%");
        gridAuditTrail.addColumn(ts.getTranslatedString("module.audit-trail.activity-oldValue"),
                ActivityLogEntry::getOldValue).setWidth("14%");
        gridAuditTrail.addColumn(ts.getTranslatedString("module.audit-trail.activity-newValue"),
                ActivityLogEntry::getNewValue).setWidth("14%");
        gridAuditTrail.addColumn(ts.getTranslatedString("module.audit-trail.activity-notes"),
                ActivityLogEntry::getNotes).setWidth("16%");

        gridAuditTrail.setI18n(buildI18nForGrid());
        gridAuditTrail.setDataProvider(loadDataProvider());
        gridAuditTrail.setHeight("auto");
        gridAuditTrail.setWidthFull();

        gridAuditTrail.setClassNameProvider(item -> item.getNotes() != null && !item.getNotes().isEmpty() ? "text" : "");
        gridAuditTrail.addClassNames("bean-table-hide-header-index", "bean-table-hide-body-index");
        if (gridAuditTrail.getRowCount() <= 22) //when page is not necessary
            gridAuditTrail.addClassName("bean-table-hide-footer");

        gridAuditTrail.addThemeVariants(
                BeanTableVariant.NO_BORDER,
                BeanTableVariant.NO_ROW_BORDERS,
                BeanTableVariant.WRAP_CELL_CONTENT
        );

        lytGrid.add(gridAuditTrail);
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
     * Loads the data provider for the audit trail grid.
     *
     * @return DataProvider for the audit trail grid.
     */
    private DataProvider<ActivityLogEntry, Void> loadDataProvider() {
        return DataProvider.fromFilteringCallbacks(
            query -> aem.getGeneralActivityAuditTrail(query.getOffset(), query.getLimit(), filters).stream(),
            query -> (int) aem.getGeneralActivityAuditTrailCount(query.getOffset(), query.getLimit(), filters)
        );
    }

    /**
     * Updates the data provider for the audit trail grid.
     */
    private void updateDataProvider() {
        gridAuditTrail.setDataProvider(loadDataProvider());
        if (gridAuditTrail.getRowCount() <= 22) //when page is not necessary
            gridAuditTrail.addClassName("bean-table-hide-footer");
        else
            gridAuditTrail.removeClassName("bean-table-hide-footer");
    }

    /**
     * Clears filters applied to the audit trail grid.
     */
    private void cleanFilters() {
        if (cmbType.getValue() != null) {
            cmbType.clear();
            filters.remove("type");
        }
        if (cmbUser.getValue() != null) {
            cmbUser.clear();
            filters.remove("user");
        }
    }
}