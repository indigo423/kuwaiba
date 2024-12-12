/*
 *  Copyright 2010-2024 Neotropic SAS <contact@neotropic.co>.
 *
 *  Licensed under the EPL License, Version 1.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.eclipse.org/legal/epl-v10.html
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.neotropic.kuwaiba.modules.core.navigation.explorers;

import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.neotropic.kuwaiba.core.apis.integration.modules.ModuleActionParameter;
import org.neotropic.kuwaiba.core.apis.integration.modules.ModuleActionParameterSet;
import org.neotropic.kuwaiba.core.apis.integration.modules.explorers.AbstractExplorer;
import org.neotropic.kuwaiba.core.apis.persistence.application.ActivityLogEntry;
import org.neotropic.kuwaiba.core.apis.persistence.application.ApplicationEntityManager;
import org.neotropic.kuwaiba.core.apis.persistence.business.BusinessObjectLight;
import org.neotropic.kuwaiba.core.i18n.TranslationService;
import org.neotropic.kuwaiba.modules.core.navigation.actions.ShowMoreInformationAction;
import org.neotropic.kuwaiba.modules.core.navigation.explorers.provider.BusinessObjectAuditTrailProvider;
import org.neotropic.util.visual.button.ActionButton;
import org.neotropic.util.visual.icon.ActionIcon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.vaadin.tatu.BeanTable;
import org.vaadin.tatu.BeanTableVariant;

import java.util.Date;
import java.util.HashMap;

/**
 * Shows audit trail associated to a given object.
 * @author Mauricio Ruiz Beltrán {@literal <mauricio.ruiz@kuwaiba.org>}
 */
@Component
public class AuditTrailExplorer extends AbstractExplorer<VerticalLayout> {
    /**
     * Reference to the translation service.
     */
    @Autowired
    private TranslationService ts;
    /**
     * Reference to the Application Entity Manager.
     */
    @Autowired
    private ApplicationEntityManager aem;
    /**
     * The window to show more information about an object.
     */
    @Autowired
    private ShowMoreInformationAction windowMoreInformation;

    @Override
    public String getName() {
        return ts.getTranslatedString("module.navigation.explorers.audit-trail.title");
    }

    @Override
    public String getDescription() {
        return ts.getTranslatedString("module.navigation.explorers.audit-trail.description");
    }
    
    @Override
    public String getHeader() {
        return ts.getTranslatedString("module.navigation.explorers.audit-trail.header");
    }

    @Override
    public String appliesTo() {
        return "InventoryObject";
    }

    /**
     * Builds the UI for the audit trail explorer.
     *
     * @param selectedObject Object selected for which the audit trail is shown.
     * @return Main layout of the UI.
     */
    @Override
    public VerticalLayout build(BusinessObjectLight selectedObject) {
        // Activity Type
        HashMap<Integer, String> types = new HashMap<>();
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

        // Audit trail table
        BeanTable<ActivityLogEntry> tblObjectAuditTrail = new BeanTable<>(ActivityLogEntry.class, false, 20);
        tblObjectAuditTrail.addColumn(ts.getTranslatedString("module.audit-trail.activity-timestamp"),
                item -> new Date(item.getTimestamp()));
        tblObjectAuditTrail.addColumn(ts.getTranslatedString("module.audit-trail.activity-type"),
                item -> ts.getTranslatedString(types.get(item.getType())));
        tblObjectAuditTrail.addColumn(ts.getTranslatedString("module.audit-trail.activity-user"),
                ActivityLogEntry::getUserName);
        tblObjectAuditTrail.addColumn(ts.getTranslatedString("module.audit-trail.activity-property"),
                ActivityLogEntry::getAffectedProperty);
        tblObjectAuditTrail.addColumn(ts.getTranslatedString("module.audit-trail.activity-oldValue"),
                ActivityLogEntry::getOldValue);
        tblObjectAuditTrail.addColumn(ts.getTranslatedString("module.audit-trail.activity-newValue"),
                ActivityLogEntry::getNewValue);
        tblObjectAuditTrail.setClassNameProvider(item -> item != null && !item.getNotes().isEmpty() ? "text" : "");
        // Build the internationalization framework for the table.
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
        tblObjectAuditTrail.setI18n(i18n);
        // Build data provider
        tblObjectAuditTrail.setDataProvider(new BusinessObjectAuditTrailProvider(aem, ts).buildDataProvider(selectedObject));
        tblObjectAuditTrail.setHeight("auto");
        tblObjectAuditTrail.setWidthFull();
        tblObjectAuditTrail.addClassNames("bean-table-hide-header-index", "bean-table-hide-body-index");
        if (tblObjectAuditTrail.getRowCount() <= 20) //when page is not necessary
            tblObjectAuditTrail.addClassName("bean-table-hide-footer");
        // Add theme variants
        tblObjectAuditTrail.addThemeVariants(
                BeanTableVariant.NO_BORDER,
                BeanTableVariant.NO_ROW_BORDERS,
                BeanTableVariant.WRAP_CELL_CONTENT
        );
        // Add the audit trail table
        VerticalLayout lytTable = new VerticalLayout(tblObjectAuditTrail);
        lytTable.setHeightFull();
        lytTable.setWidthFull();

        // Information
        Label lblInfo = new Label(ts.getTranslatedString("module.navigation.explorers.help"));
        lblInfo.setWidth("90%");
        lblInfo.setClassName("info-label");
        // Show more information about the object
        ActionButton btnInfo = new ActionButton(new ActionIcon(VaadinIcon.INFO),
                this.windowMoreInformation.getDisplayName());
        btnInfo.addClickListener(event ->
                this.windowMoreInformation.getVisualComponent(new ModuleActionParameterSet(
                        new ModuleActionParameter<>("object", selectedObject))).open());
        // Refresh data provider
        ActionButton btnRefresh = new ActionButton(new ActionIcon(VaadinIcon.REFRESH),
                ts.getTranslatedString("module.general.labels.refresh"));
        btnRefresh.addClickListener(event -> {
            tblObjectAuditTrail.setDataProvider(new BusinessObjectAuditTrailProvider(aem, ts).buildDataProvider(selectedObject));
            if (tblObjectAuditTrail.getRowCount() > 20)
                tblObjectAuditTrail.removeClassName("bean-table-hide-footer");
        });
        // Add information and the actions buttons
        HorizontalLayout lytActions = new HorizontalLayout(lblInfo, btnInfo, btnRefresh);
        lytActions.setSpacing(true);
        lytActions.setWidthFull();

        // Add content to the main layout
        VerticalLayout lytMain = new VerticalLayout(lytActions, lytTable);
        lytMain.setPadding(true);
        lytMain.setSpacing(true);
        lytMain.setHeightFull();
        lytMain.setWidthFull();

        return lytMain;
    }

    public void clearResources() {
    }
}