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
package org.neotropic.kuwaiba.modules.optional.topoman.tools;

import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.shared.Registration;
import lombok.Getter;
import lombok.Setter;
import org.neotropic.kuwaiba.core.apis.persistence.business.BusinessEntityManager;
import org.neotropic.kuwaiba.core.apis.persistence.business.BusinessObjectLight;
import org.neotropic.kuwaiba.core.apis.persistence.util.Constants;
import org.neotropic.kuwaiba.core.i18n.TranslationService;
import org.neotropic.util.visual.button.ActionButton;
import org.neotropic.util.visual.general.FormattedObjectDisplayNameSpan;
import org.neotropic.util.visual.grids.IconNameCellGrid;
import org.neotropic.util.visual.icon.ActionIcon;
import org.neotropic.util.visual.icons.IconGenerator;
import org.neotropic.util.visual.mxgraph.MxGraphCanvas;
import org.vaadin.tatu.BeanTable;
import org.vaadin.tatu.BeanTableVariant;

/**
 * The {@code TopologyAddObject} class provides a UI for searching and adding topological objects.
 * <p>
 * It includes a search text field and tables displaying search results, allowing objects to be added
 * to a graphical view.
 * </p>
 *
 * @author Mauricio Ruiz Beltrán {@literal <mauricio.ruiz@kuwaiba.org>}
 */
public class AddObjectSelector extends Div {
    @Setter
    @Getter
    TextField txtSearch;

    /**
     * Constructs a {@code TopologyAddObject} with the specified parameters.
     *
     * @param ts            The translation service
     * @param bem           The business entity manager
     * @param mxGraphCanvas The mxGraph canvas
     * @param iconGenerator The icon generator
     */
    public AddObjectSelector(TranslationService ts,
                             BusinessEntityManager bem,
                             MxGraphCanvas mxGraphCanvas,
                             IconGenerator iconGenerator) {
        setWidthFull();
        setHeight("auto");

        HorizontalLayout lytContent = new HorizontalLayout();
        lytContent.setPadding(false);
        lytContent.setMargin(false);
        lytContent.setSpacing(true);

        txtSearch = new TextField();
        txtSearch.setWidthFull();
        txtSearch.setValueChangeMode(ValueChangeMode.EAGER);
        txtSearch.setClearButtonVisible(true);
        txtSearch.setPlaceholder(ts.getTranslatedString("module.topoman.search-objects"));
        ActionIcon iconSearch = new ActionIcon(VaadinIcon.SEARCH);
        txtSearch.setPrefixComponent(iconSearch);

        txtSearch.addValueChangeListener((AbstractField.ComponentValueChangeEvent<TextField, String> event) -> {
            lytContent.removeAll();
            if (event.isFromClient()) {
                lytContent.add(createGrid(bem, ts, event.getValue(), iconGenerator, mxGraphCanvas, false));
                lytContent.add(createGrid(bem, ts, event.getValue(), iconGenerator, mxGraphCanvas, true));
                
                add(lytContent);
                txtSearch.focus();
            }
        });

        add(txtSearch, lytContent);
    }

    /**
     * Creates a grid for displaying business objects.
     *
     * @param bem           The business entity manager
     * @param ts            The translation service
     * @param value         The search value
     * @param iconGenerator The icon generator
     * @param mxGraphCanvas The mxGraph canvas
     * @param isConnection  Whether the grid is for connections
     * @return The created grid
     */
    private BeanTable<BusinessObjectLight> createGrid(BusinessEntityManager bem,
                                                      TranslationService ts,
                                                      String value,
                                                      IconGenerator iconGenerator,
                                                      MxGraphCanvas mxGraphCanvas,
                                                      boolean isConnection) {
        BeanTable<BusinessObjectLight> grid = new BeanTable<>(BusinessObjectLight.class, false, 10);
        addComponentColumnToGrid(ts, grid, iconGenerator, mxGraphCanvas, isConnection);
        grid.setI18n(buildI18nForGrid(ts));
        grid.setDataProvider(isConnection ? loadDataProvider(bem, value, true) : loadDataProvider(bem, value, false));

        if (grid.getRowCount() <= 10) //when page is not necessary
            grid.addClassName("bean-table-hide-footer");

        grid.addThemeVariants(BeanTableVariant.NO_BORDER, BeanTableVariant.NO_ROW_BORDERS, BeanTableVariant.WRAP_CELL_CONTENT);
        grid.setHeight("auto");
        grid.setWidth("50%");
        return grid;
    }

    /**
     * Adds a component column to the grid.
     *
     * @param ts            The translation service
     * @param grid          The grid
     * @param iconGenerator The icon generator
     * @param mxGraphCanvas The mxGraph canvas
     * @param isConnection  Whether the column is for connections
     */
    private void addComponentColumnToGrid(TranslationService ts,
                                          BeanTable<BusinessObjectLight> grid,
                                          IconGenerator iconGenerator,
                                          MxGraphCanvas mxGraphCanvas,
                                          boolean isConnection) {
        grid.addComponentColumn(isConnection ? ts.getTranslatedString("module.topoman.labels.connections")
                        : ts.getTranslatedString("module.topoman.labels.nodes"),
                object -> {
                    ActionButton btnAdd = new ActionButton(new Icon(VaadinIcon.PLUS_CIRCLE_O),
                            ts.getTranslatedString("module.topoman.labels.add-to-view"));
                    btnAdd.addClickListener(clickEvent -> {
                        txtSearch.setValue(object.getName());
                        fireEvent(new NewObjectEvent(AddObjectSelector.this, false, object));
                    });

                    FormattedObjectDisplayNameSpan spnItemName = new FormattedObjectDisplayNameSpan(
                            object,
                            false,
                            false,
                            true,
                            false
                    );

                    IconNameCellGrid cell = new IconNameCellGrid(
                            spnItemName,
                            object.getClassName(),
                            iconGenerator
                    );
                    cell.setWidth("99%");

                    if (mxGraphCanvas.getNodes().containsKey(object) || mxGraphCanvas.getEdges().containsKey(object)) {
                        cell.getElement().getStyle().set("color", "#E74C3C");
                        btnAdd.setVisible(false);
                    }

                    HorizontalLayout hly = new HorizontalLayout(cell, btnAdd);
                    hly.setMargin(false);
                    hly.setPadding(false);

                    return hly;
                });
    }

    /**
     * Loads the data provider for the grid.
     *
     * @param bem          The business entity manager
     * @param value        The search value
     * @param isConnection Whether the data provider is for connections
     * @return The data provider
     */
    private DataProvider<BusinessObjectLight, Void> loadDataProvider(BusinessEntityManager bem,
                                                                     String value,
                                                                     boolean isConnection) {
        return DataProvider.fromCallbacks(
                query -> bem.getSuggestedObjectsWithFilter(value,
                        query.getOffset(), query.getLimit(),
                        isConnection ? Constants.CLASS_GENERICCONNECTION
                                : Constants.CLASS_VIEWABLEOBJECT, Constants.CLASS_GENERICLOGICALELEMENT
                ).stream(),
                query -> (int) bem.getSuggestedObjectsWithFilter(value,
                        query.getOffset(), query.getLimit(),
                        isConnection ? Constants.CLASS_GENERICCONNECTION
                                : Constants.CLASS_VIEWABLEOBJECT, Constants.CLASS_GENERICLOGICALELEMENT
                ).stream().count()
        );
    }

    /**
     * Builds the internationalization for the grid.
     *
     * @param ts The translation service
     * @return The grid internationalization
     */
    private BeanTable.BeanTableI18n buildI18nForGrid(TranslationService ts) {
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
     * Adds a listener for new object events.
     *
     * @param listener The listener
     * @return The registration for the listener
     */
    public Registration addNewObjectListener(ComponentEventListener<NewObjectEvent> listener) {
        return addListener(NewObjectEvent.class, listener);
    }

    /**
     * Event fired when a new object is added.
     */
    @Getter
    public static class NewObjectEvent extends ComponentEvent<AddObjectSelector> {
        private final BusinessObjectLight object;

        /**
         * Constructs a new {@code NewObjectEvent}.
         *
         * @param source     The source of the event
         * @param fromClient Whether the event originated from the client
         * @param object     The business object
         */
        public NewObjectEvent(AddObjectSelector source, boolean fromClient, BusinessObjectLight object) {
            super(source, fromClient);
            this.object = object;
        }
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled); //To change body of generated methods, choose Tools | Templates.
        txtSearch.setEnabled(enabled);
    }
}