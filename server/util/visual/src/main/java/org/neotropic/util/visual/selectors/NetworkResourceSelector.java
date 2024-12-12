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
package org.neotropic.util.visual.selectors;

import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.shared.Registration;
import org.neotropic.kuwaiba.core.apis.persistence.application.ApplicationEntityManager;
import org.neotropic.kuwaiba.core.apis.persistence.application.FilterDefinition;
import org.neotropic.kuwaiba.core.apis.persistence.business.BusinessEntityManager;
import org.neotropic.kuwaiba.core.apis.persistence.business.BusinessObjectLight;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.InventoryException;
import org.neotropic.kuwaiba.core.apis.persistence.metadata.MetadataEntityManager;
import org.neotropic.kuwaiba.core.apis.persistence.util.Constants;
import org.neotropic.kuwaiba.core.i18n.TranslationService;
import org.neotropic.util.visual.notifications.AbstractNotification;
import org.neotropic.util.visual.notifications.SimpleNotification;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Network resource selector.
 *
 * @author Mauricio Ruiz Beltrán {@literal <mauricio.ruiz@kuwaiba.org>}
 */
public class NetworkResourceSelector extends FlexLayout {
    private final String placeholder;
    private final ApplicationEntityManager aem;
    private final BusinessEntityManager bem;
    private final MetadataEntityManager mem;
    private final TranslationService ts;

    private BusinessObjectLight selectedObject;
    private List<ComboBox<BusinessObjectLight>> cmbObjectSelectors = new ArrayList<>();
    private List<HorizontalLayout> rows = new ArrayList<>();
    private final HasValue.ValueChangeListener<AbstractField.ComponentValueChangeEvent<
            ComboBox<BusinessObjectLight>, BusinessObjectLight>> valueChangeListener;

    /**
     * Creates a new instance of the network resource selector.
     *
     * @param selectedObject  The selected object. If exists.
     * @param selectedObjects The selected objects. If they exist.
     * @param placeholder     The custom placeholder.
     * @param aem             Reference to the Application Entity Manager.
     * @param bem             Reference to the Business Entity Manager.
     * @param mem             Reference to the Metadata Entity Manager.
     * @param ts              Reference to the Translation Service.
     * @throws InventoryException If an unexpected error occurs.
     */
    public NetworkResourceSelector(BusinessObjectLight selectedObject, List<BusinessObjectLight> selectedObjects,
                                   String placeholder, ApplicationEntityManager aem, BusinessEntityManager bem,
                                   MetadataEntityManager mem, TranslationService ts) throws InventoryException {
        Objects.requireNonNull(placeholder);
        Objects.requireNonNull(aem);
        Objects.requireNonNull(bem);
        Objects.requireNonNull(mem);
        Objects.requireNonNull(ts);

        this.placeholder = placeholder;
        this.aem = aem;
        this.bem = bem;
        this.mem = mem;
        this.ts = ts;

        if (selectedObject != null) {
            if (selectedObjects == null || selectedObjects.isEmpty())
                selectedObjects = Arrays.asList(selectedObject);
        }
        this.selectedObject = selectedObject;

        // Called every time a change is made to the selector
        valueChangeListener = valueChangeEvent -> {
            try {
                ComboBox<BusinessObjectLight> cmbSource = valueChangeEvent.getSource();
                BusinessObjectLight value = valueChangeEvent.getValue();

                int index = cmbObjectSelectors.indexOf(cmbSource);
                List<ComboBox<BusinessObjectLight>> removeCmbs = new ArrayList<>();
                List<HorizontalLayout> removeRows = new ArrayList<>();

                for (int i = index + 1; i < cmbObjectSelectors.size(); i++) {
                    removeCmbs.add(cmbObjectSelectors.get(i));
                    HorizontalLayout row = rows.get(i);
                    removeRows.add(row);
                    remove(row);
                }

                cmbObjectSelectors.removeAll(removeCmbs);
                rows.removeAll(removeRows);

                if (value != null) {
                    this.selectedObject = value;
                    add(addRow(value, null));
                    getStyle().set("margin-bottom", "25px");
                } else {
                    if (index - 1 >= 0)
                        this.selectedObject = cmbObjectSelectors.get(index - 1).getValue();
                    else
                        this.selectedObject = null;
                }

                fireEvent(new NetworkResourceChangeEvent(this, this.selectedObject,
                        getSelectedObjects(), valueChangeEvent.isFromClient()));
            } catch (InventoryException ex) {
                new SimpleNotification(
                        ts.getTranslatedString("module.general.messages.error"),
                        ex.getLocalizedMessage(),
                        AbstractNotification.NotificationType.ERROR,
                        ts
                ).open();
            }
        };

        // Load previously selected objects
        AtomicInteger countSelectedObjects = new AtomicInteger(0);
        if (selectedObjects != null && !selectedObjects.isEmpty()) {
            for (BusinessObjectLight object : selectedObjects) {
                if (countSelectedObjects.get() == 0)
                    add(addRow(null, object));
                else
                    add(addRow(selectedObjects.get(countSelectedObjects.get() - 1), object));

                countSelectedObjects.getAndIncrement();
            }
            getStyle().set("margin-bottom", "25px");
        }

        add(addRow());
        setSizeFull();
        setFlexDirection(FlexDirection.COLUMN);
    }

    /**
     * Adds a new row to the selector.
     *
     * @return The new selector row.
     * @throws InventoryException If an unexpected error occurs.
     */
    public HorizontalLayout addRow() throws InventoryException {
        return addRow(null, null);
    }

    /**
     * Adds a new row to the selector, if the parent exists the option to filter the objects will be enabled.
     *
     * @param parent         The parent of business object. If exists.
     * @param businessObject The business object. If exists.
     * @return The new selector row.
     * @throws InventoryException If an unexpected error occurs.
     */
    private HorizontalLayout addRow(BusinessObjectLight parent, BusinessObjectLight businessObject) throws InventoryException {
        HorizontalLayout lytRow = new HorizontalLayout();
        lytRow.setWidthFull();
        lytRow.setDefaultVerticalComponentAlignment(Alignment.CENTER);
        lytRow.setMargin(false);
        lytRow.setPadding(false);
        lytRow.setId("lytRow");

        ComboBox<BusinessObjectLight> cmbObjectSelector = getObjectSelector(businessObject);
        cmbObjectSelector.setId("cmbObjectSelector");
        cmbObjectSelector.setWidthFull();

        cmbObjectSelectors.add(cmbObjectSelector);
        lytRow.add(cmbObjectSelector);

        if (parent != null) {
            cmbObjectSelector.setWidth("70%");

            HashMap<String, Object> attrToFilter = new HashMap<>();
            attrToFilter.put(Constants.PROPERTY_ENABLED, true);

            ComboBox<FilterDefinition> cmbFilters = new ComboBox<>();
            cmbFilters.setClearButtonVisible(true);
            cmbFilters.setItemLabelGenerator(FilterDefinition::getName);
            cmbFilters.setItems(aem.getFilterDefinitionsForClass(parent.getClassName(),
                    true, true, attrToFilter,
                    -1, -1));
            cmbFilters.setPlaceholder(ts.getTranslatedString(
                    "module.serviceman.actions.relate-service-to-network-resource.filter"));
            cmbFilters.setRenderer(new ComponentRenderer<>(item -> {
                HorizontalLayout lytItem = new HorizontalLayout(VaadinIcon.FILTER.create(),
                        new Label(item.getName()));
                lytItem.setDefaultVerticalComponentAlignment(Alignment.CENTER);
                return lytItem;
            }));
            cmbFilters.setId("cmbFilters");
            cmbFilters.setWidthFull();

            cmbFilters.addValueChangeListener(valueChangeEvent -> {
                FilterDefinition value = valueChangeEvent.getValue();
                if (value != null)
                    cmbObjectSelector.setDataProvider(SelectorsUtils.getFilterDataProvider(parent, value, ts));
                else
                    cmbObjectSelector.setDataProvider(getObjectDataProvider(cmbObjectSelector));
            });

            HorizontalLayout lytFilters = new HorizontalLayout(VaadinIcon.FILTER.create(), cmbFilters);
            lytFilters.setDefaultVerticalComponentAlignment(Alignment.CENTER);
            lytFilters.setId("lytFilters");
            lytFilters.setWidth("30%");
            lytFilters.setMargin(false);
            lytFilters.setPadding(false);

            lytRow.add(lytFilters);
        }

        rows.add(lytRow);
        return lytRow;
    }

    /**
     * Gets the selected objects in the different rows of the selector.
     *
     * @return The business object list.
     */
    private List<BusinessObjectLight> getSelectedObjects() {
        List<BusinessObjectLight> selectedObjects = new ArrayList<>();
        cmbObjectSelectors.forEach(cmb -> {
            if (cmb.getValue() != null)
                selectedObjects.add(cmb.getValue());
        });
        return selectedObjects;
    }

    /**
     * Gets the combo box that allows you to select a network resource.
     *
     * @param selectedObject The business object. If exists.
     * @return The combo box that allows you to select a network resource.
     */
    private ComboBox<BusinessObjectLight> getObjectSelector(BusinessObjectLight selectedObject) {
        ComboBox<BusinessObjectLight> cmbObjectSelector = new ComboBox<>();
        cmbObjectSelector.setWidthFull();
        cmbObjectSelector.setItemLabelGenerator(BusinessObjectLight::getName);
        cmbObjectSelector.setRenderer(new ObjectRenderer(mem, ts));
        cmbObjectSelector.setClearButtonVisible(true);
        cmbObjectSelector.setDataProvider(getObjectDataProvider(cmbObjectSelector));
        cmbObjectSelector.setValue(selectedObject);
        cmbObjectSelector.addValueChangeListener(valueChangeListener);
        if (placeholder != null)
            cmbObjectSelector.setPlaceholder(placeholder);
        return cmbObjectSelector;
    }

    /**
     * Provides data to populate and filter business objects.
     *
     * @param cmbObjectSelector The current combo box enabled.
     * @return The data provider.
     */
    private DataProvider<BusinessObjectLight, String> getObjectDataProvider(ComboBox<BusinessObjectLight> cmbObjectSelector) {
        return DataProvider.fromFilteringCallbacks(
                query -> {
                    int index = cmbObjectSelectors.indexOf(cmbObjectSelector);
                    BusinessObjectLight object = null;
                    if (index - 1 >= 0)
                        object = cmbObjectSelectors.get(index - 1).getValue();

                    if (object == null) {
                        String filter = query.getFilter().orElse(null);
                        if (filter != null && !filter.isEmpty()) {
                            return bem.getSuggestedObjectsWithFilter(
                                    filter,
                                    query.getOffset(),
                                    query.getLimit(),
                                    Constants.CLASS_INVENTORYOBJECT
                            ).stream();
                        } else
                            return null;
                    } else {
                        return bem.getSuggestedChildrenWithFilter(
                                object.getClassName(),
                                object.getId(),
                                query.getFilter().orElse(null),
                                true,
                                query.getOffset(),
                                query.getLimit(),
                                Constants.CLASS_INVENTORYOBJECT
                        ).stream();
                    }
                },
                query -> {
                    int index = cmbObjectSelectors.indexOf(cmbObjectSelector);
                    BusinessObjectLight object = null;
                    if (index - 1 >= 0)
                        object = cmbObjectSelectors.get(index - 1).getValue();

                    if (object == null) {
                        String filter = query.getFilter().orElse(null);
                        if (filter != null && !filter.isEmpty()) {
                            return (int) bem.getSuggestedObjectsWithFilter(
                                    filter,
                                    query.getOffset(),
                                    query.getLimit(),
                                    Constants.CLASS_INVENTORYOBJECT
                            ).stream().count();
                        } else
                            return 0;
                    } else {
                        return (int) bem.getSuggestedChildrenWithFilter(
                                object.getClassName(),
                                object.getId(),
                                query.getFilter().orElse(null),
                                true,
                                query.getOffset(),
                                query.getLimit(),
                                Constants.CLASS_INVENTORYOBJECT
                        ).stream().count();
                    }
                }
        );
    }

    public Registration addSelectedObjectChangeListener(ComponentEventListener<NetworkResourceChangeEvent> listener) {
        return addListener(NetworkResourceChangeEvent.class, listener);
    }
}