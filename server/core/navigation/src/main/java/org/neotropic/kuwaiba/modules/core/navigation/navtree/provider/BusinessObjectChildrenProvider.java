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
import org.neotropic.kuwaiba.core.apis.persistence.business.BusinessEntityManager;
import org.neotropic.kuwaiba.core.apis.persistence.business.BusinessObjectLight;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.BusinessObjectNotFoundException;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.InvalidArgumentException;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.MetadataObjectNotFoundException;
import org.neotropic.kuwaiba.core.i18n.TranslationService;
import org.neotropic.util.visual.notifications.AbstractNotification;
import org.neotropic.util.visual.notifications.SimpleNotification;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * Data provider for business objects that handles loading and accessing data.
 * Extends the {@link BusinessObjectProvider} class.
 *
 * @author Mauricio Ruiz Beltrán {@literal <mauricio.ruiz@kuwaiba.org>}
 */
public class BusinessObjectChildrenProvider extends BusinessObjectProvider {
    /**
     * Reference to the Business Entity Manager.
     */
    private final BusinessEntityManager bem;
    /**
     * Reference to the Translation Service.
     */
    private final TranslationService ts;
    /**
     * List that caches data retrieved.
     */
    private final List<BusinessObjectLight> cachedData = new ArrayList<>();

    /**
     * Constructs a new instance of {@code BusinessObjectChildrenProvider}.
     *
     * @param bem Reference to the Business Entity Manager.
     * @param ts  Reference to the Translation Service.
     */
    public BusinessObjectChildrenProvider(BusinessEntityManager bem, TranslationService ts) {
        this.bem = bem;
        this.ts = ts;
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
                        cachedData.addAll(Objects.requireNonNull(getObjectChildren(rootObject)));
                }
                return cachedData.stream().skip(query.getOffset()).limit(query.getLimit());
            }

            @Override
            protected int sizeInBackEnd(Query<BusinessObjectLight, Void> query) {
                if (cachedData.isEmpty()) {
                    if (includedSelf)
                        cachedData.add(rootObject);
                    else
                        cachedData.addAll(Objects.requireNonNull(getObjectChildren(rootObject)));
                }
                return cachedData.size();
            }
        };
    }

    /**
     * Retrieves the children of a given business object.
     *
     * @param rootObject The root object from which the children will be obtained.
     * @return The business objects that are children of a given object.
     */
    private List<BusinessObjectLight> getObjectChildren(BusinessObjectLight rootObject) {
        try {
            return bem.getObjectChildren(rootObject.getClassName(), rootObject.getId(), -1);
        } catch (MetadataObjectNotFoundException | BusinessObjectNotFoundException | InvalidArgumentException ex) {
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