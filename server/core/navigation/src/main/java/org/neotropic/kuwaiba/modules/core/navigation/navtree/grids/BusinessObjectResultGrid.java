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
package org.neotropic.kuwaiba.modules.core.navigation.navtree.grids;

import com.vaadin.flow.data.provider.CallbackDataProvider;
import com.vaadin.flow.data.provider.ConfigurableFilterDataProvider;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.Query;
import org.neotropic.kuwaiba.core.apis.persistence.business.BusinessEntityManager;
import org.neotropic.kuwaiba.core.apis.persistence.business.BusinessObjectLight;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.InvalidArgumentException;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.MetadataObjectNotFoundException;
import org.neotropic.kuwaiba.core.apis.persistence.util.Constants;
import org.neotropic.kuwaiba.core.i18n.TranslationService;
import org.neotropic.util.visual.grids.BusinessObjectLightGridFilter;
import org.neotropic.util.visual.notifications.AbstractNotification;
import org.neotropic.util.visual.notifications.SimpleNotification;
import org.vaadin.tatu.BeanTable;
import org.vaadin.tatu.BeanTableVariant;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Represents a business object results table that allows you to filter and display data in a UI.
 * Extends the {@link BeanTable} class.
 *
 * <p>
 * use:
 * BusinessObjectResultGrid grid = new BusinessObjectResultGrid(...); //new instance
 * grid.buildDataProvider(...); //builds the data
 * </p>
 *
 * @author Mauricio Ruiz Beltrán {@literal <mauricio.ruiz@kuwaiba.org>}
 */
public class BusinessObjectResultGrid extends BeanTable<BusinessObjectLight> {
    /**
     * Reference to the business entity manager.
     */
    private BusinessEntityManager bem;
    /**
     * Reference to the translation service.
     */
    private TranslationService ts;
    /**
     * To use the filter and the provider in the grid.
     */
    private ConfigurableFilterDataProvider<BusinessObjectLight, Void, BusinessObjectLightGridFilter> configurableFilterDataProvider;
    /**
     * Filter for the grid only use name to filter.
     */
    private BusinessObjectLightGridFilter gridFilter;
    /**
     * The class name of the objects in the grid.
     */
    private String className;
    /**
     * The initial object list.
     */
    private List<BusinessObjectLight> initialResults;
    /**
     * The current searched text.
     */
    private String searchedText;
    /**
     * Saves the last loaded objects.
     */
    private List<BusinessObjectLight> currentResults;

    /**
     * Constructs a new instance of {@code BusinessObjectResultGrid}.
     *
     * @param pageLength The grid page length.
     */
    public BusinessObjectResultGrid(int pageLength) {
        super(BusinessObjectLight.class, false, pageLength);
        addThemeVariants();
    }

    /**
     * Builds the data provider for the grid.
     *
     * @param bem            Reference to the business entity manager.
     * @param ts             Reference to the translation service.
     * @param className      The class name of the objects.
     * @param searchedText   The text to search for.
     * @param initialResults The initial list of objects.
     */
    public void buildDataProvider(BusinessEntityManager bem, TranslationService ts, String className,
                                  String searchedText, List<BusinessObjectLight> initialResults) {
        this.bem = bem;
        this.ts = ts;
        this.className = className;
        this.searchedText = searchedText;
        this.gridFilter = new BusinessObjectLightGridFilter();
        this.initialResults = initialResults;
        this.currentResults = null;

        // Creates a data provider with filtering callbacks
        CallbackDataProvider<BusinessObjectLight, BusinessObjectLightGridFilter> provider =
                DataProvider.fromFilteringCallbacks(this::filterData, this::getTotalSize);

        configurableFilterDataProvider = provider.withConfigurableFilter(); // Configures the data provider to be configurable with filters
        setDataProvider(configurableFilterDataProvider); // Sets the configured data provider
    }

    /**
     * Filters the data based on the query.
     *
     * @param query The query containing filter parameters.
     * @return A stream of filtered objects.
     */
    private Stream<BusinessObjectLight> filterData(Query<BusinessObjectLight, BusinessObjectLightGridFilter> query) {
        BusinessObjectLightGridFilter filter = query.getFilter().orElse(null);
        HashMap<String, String> valuesToFilter = new HashMap<>();
        try {
            if (filter != null && filter.getBusinessObjectName() != null && currentResults != null) {
                // If there is a filter and current results are available, filter the current results
                return currentResults.stream()
                        .filter(obj -> obj.getName().toLowerCase().contains(filter.getBusinessObjectName().toLowerCase()))
                        .collect(Collectors.toList()).stream();
            } else {
                // If no filter or current results are not available, fetch data from the database
                for (BusinessObjectLight object : initialResults) {
                    if (((BusinessObjectLight) object).getName().toLowerCase().contains(searchedText.toLowerCase())
                            && !className.toLowerCase().contains(searchedText.toLowerCase())) {
                        valuesToFilter.put(Constants.PROPERTY_NAME, searchedText);
                        break;
                    }
                }

                currentResults = bem.getObjectsOfClassLight(this.className,
                        valuesToFilter, query.getOffset(), query.getLimit());
                return currentResults.stream();
            }
        } catch (InvalidArgumentException | MetadataObjectNotFoundException e) {
            new SimpleNotification(ts.getTranslatedString("module.general.messages.error"),
                    e.getLocalizedMessage(), AbstractNotification.NotificationType.ERROR, ts).open();
        }
        return Stream.empty();
    }

    /**
     * Calculates the total size of the data.
     *
     * @param query The query containing filter parameters.
     * @return The total size of the data.
     */
    private int getTotalSize(Query<BusinessObjectLight, BusinessObjectLightGridFilter> query) {
        BusinessObjectLightGridFilter filter = query.getFilter().orElse(null);
        HashMap<String, String> valuesToFilter = new HashMap<>();
        try {
            if (filter != null && filter.getBusinessObjectName() != null && currentResults != null) {
                // If there is a filter and current results are available, count the filtered results
                return (int) currentResults.stream()
                        .filter(obj -> obj.getName().toLowerCase().contains(
                                filter.getBusinessObjectName().toLowerCase())).count();
            } else {
                // If no filter or current results are not available, fetch data from the database
                for (BusinessObjectLight object : initialResults) {
                    if (((BusinessObjectLight) object).getName().toLowerCase().contains(searchedText.toLowerCase())
                            && !className.toLowerCase().contains(searchedText.toLowerCase())) {
                        valuesToFilter.put(Constants.PROPERTY_NAME, searchedText);
                        break;
                    }
                }
                return bem.getObjectsOfClassLight(this.className, valuesToFilter,
                        query.getOffset(), query.getLimit()).size();
            }
        } catch (InvalidArgumentException | MetadataObjectNotFoundException e) {
            new SimpleNotification(ts.getTranslatedString("module.general.messages.error"),
                    e.getLocalizedMessage(), AbstractNotification.NotificationType.ERROR, ts).open();
        }
        return 0;
    }

    /**
     * Sets the text filter for the grid.
     *
     * @param textFilter Text filter to apply.
     */
    public void setFilter(String textFilter) {
        if (textFilter != null && !textFilter.trim().isEmpty()) { // If the text filter is valid, set it in the grid filter object
            gridFilter.setBusinessObjectName(textFilter);
        } else { // If the text filter is null or empty, set it to null in the grid filter object
            gridFilter.setBusinessObjectName(null);
        }
        configurableFilterDataProvider.setFilter(gridFilter); // Apply the updated filter to the configurable data provider
    }

    /**
     * Adds theme variants to the grid.
     */
    private void addThemeVariants() {
        addThemeVariants(
                BeanTableVariant.NO_BORDER,
                BeanTableVariant.NO_ROW_BORDERS,
                BeanTableVariant.WRAP_CELL_CONTENT
        );
    }
}