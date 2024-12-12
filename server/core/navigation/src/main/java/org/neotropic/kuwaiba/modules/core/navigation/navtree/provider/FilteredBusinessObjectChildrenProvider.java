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
package org.neotropic.kuwaiba.modules.core.navigation.navtree.provider;

import com.vaadin.flow.data.provider.AbstractBackEndDataProvider;
import com.vaadin.flow.data.provider.Query;
import org.neotropic.kuwaiba.core.apis.persistence.application.FilterDefinition;
import org.neotropic.kuwaiba.core.apis.persistence.business.BusinessObjectLight;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.InvalidArgumentException;
import org.neotropic.kuwaiba.core.i18n.TranslationService;
import org.neotropic.util.visual.notifications.AbstractNotification;
import org.neotropic.util.visual.notifications.SimpleNotification;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * Data provider for filtered business objects that handles loading and accessing data.
 * Extends the {@link BusinessObjectProvider} class.
 *
 * @author Mauricio Ruiz Beltrán {@literal <mauricio.ruiz@kuwaiba.org>}
 */
public class FilteredBusinessObjectChildrenProvider extends BusinessObjectProvider {
    /**
     * Reference to the Translation Service.
     */
    private final TranslationService ts;
    /**
     * Reference to the filter definition.
     */
    private final FilterDefinition filterDefinition;
    /**
     * List that caches data retrieved.
     */
    private final List<BusinessObjectLight> cachedData = new ArrayList<>();

    /**
     * Constructs a new instance of {@code FilteredBusinessObjectChildrenProvider}.
     *
     * @param ts               Reference to the Translation Service.
     * @param filterDefinition Reference to the filter definition.
     */
    public FilteredBusinessObjectChildrenProvider(TranslationService ts, FilterDefinition filterDefinition) {
        this.ts = ts;
        this.filterDefinition = filterDefinition;
    }

    /**
     * Builds a data provider for business objects from a given object.
     *
     * @param rootObject   The root object from which data will be retrieved.
     * @param includedSelf Boolean indicating whether the root object should be included in the retrieved data.
     * @return Built data provider for business objects.
     */
    @Override
    public AbstractBackEndDataProvider<BusinessObjectLight, Void> buildDataProvider(
            BusinessObjectLight rootObject, boolean includedSelf) {
        return new AbstractBackEndDataProvider<BusinessObjectLight, Void>() {

            @Override
            protected Stream<BusinessObjectLight> fetchFromBackEnd(Query<BusinessObjectLight, Void> query) {
                if (cachedData.isEmpty()) {
                    if (includedSelf)
                        cachedData.add(rootObject);
                    else
                        cachedData.addAll(Objects.requireNonNull(getFilterData(rootObject)));
                }
                return cachedData.stream().skip(query.getOffset()).limit(query.getLimit());
            }

            @Override
            protected int sizeInBackEnd(Query<BusinessObjectLight, Void> query) {
                if (cachedData.isEmpty()) {
                    if (includedSelf)
                        cachedData.add(rootObject);
                    else
                        cachedData.addAll(Objects.requireNonNull(getFilterData(rootObject)));
                }
                return cachedData.size();
            }
        };
    }

    /**
     * Retrieves the filtered business objects within a given object.
     *
     * @param rootObject The root object from which the filtered children are obtained.
     * @return The business objects within a given object.
     */
    private List<BusinessObjectLight> getFilterData(BusinessObjectLight rootObject) {
        try {
            return filterDefinition.getFilter().run(rootObject.getId(), rootObject.getClassName(),
                    null, -1, -1);
        } catch (InvalidArgumentException ex) {
            new SimpleNotification(ts.getTranslatedString("module.general.messages.error"),
                    ex.getMessage(), AbstractNotification.NotificationType.ERROR, ts).open();
            return null;
        }
    }

    /**
     * Gets list that caches data retrieved from the Business Entity Manager.
     *
     * @return List that caches data retrieved.
     */
    @Override
    public List<BusinessObjectLight> getCachedData() {
        return cachedData;
    }

    /**
     * Retrieves a business object by its id from the cache.
     *
     * @param objectId Id of the business object to retrieve.
     * @return The business object with the specified id, or null if not found.
     */
    @Override
    public BusinessObjectLight getObjectById(String objectId) {
        try {
            return cachedData.stream()
                    .filter(o -> o.getId().equals(objectId))
                    .findFirst()
                    .orElse(null);
        } catch (NoSuchElementException ex) {
            new SimpleNotification(ts.getTranslatedString("module.general.messages.error"),
                    ex.getMessage(), AbstractNotification.NotificationType.ERROR, ts).open();
            return null;
        }
    }
}