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
package org.neotropic.kuwaiba.modules.optional.pools.providers;

import com.vaadin.flow.data.provider.AbstractBackEndDataProvider;
import com.vaadin.flow.data.provider.Query;
import lombok.Getter;
import org.neotropic.kuwaiba.core.apis.persistence.application.InventoryObjectPool;
import org.neotropic.kuwaiba.core.apis.persistence.business.BusinessEntityManager;
import org.neotropic.kuwaiba.core.apis.persistence.business.BusinessObjectLight;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.ApplicationObjectNotFoundException;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.InvalidArgumentException;
import org.neotropic.kuwaiba.core.i18n.TranslationService;
import org.neotropic.util.visual.notifications.AbstractNotification;
import org.neotropic.util.visual.notifications.SimpleNotification;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * Data provider for business objects (pool items) that handles loading and accessing data.
 * Extends the {@link PoolItemProvider} class.
 *
 * @author Mauricio Ruiz Beltrán {@literal <mauricio.ruiz@kuwaiba.org>}
 */
public class PoolItemProvider {
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
    @Getter
    private final List<BusinessObjectLight> cachedData = new ArrayList<>();

    /**
     * Constructs a new instance of {@code PoolItemProvider}.
     *
     * @param bem Reference to the Business Entity Manager.
     * @param ts  Reference to the Translation Service.
     */
    public PoolItemProvider(BusinessEntityManager bem, TranslationService ts) {
        this.bem = bem;
        this.ts = ts;
    }

    /**
     * Builds a data provider for business objects from a given pool.
     *
     * @param parentPool   The parent pool from which data will be retrieved.
     * @param rootObject   Is there a root object?
     * @param includedSelf Boolean indicating whether the root object should be included in the retrieved data.
     * @return Built data provider for pool items.
     */
    public AbstractBackEndDataProvider<BusinessObjectLight, Void> buildDataProvider(
            InventoryObjectPool parentPool, BusinessObjectLight rootObject, boolean includedSelf) {
        return new AbstractBackEndDataProvider<BusinessObjectLight, Void>() {
            @Override
            protected Stream<BusinessObjectLight> fetchFromBackEnd(Query<BusinessObjectLight, Void> query) {
                if (cachedData.isEmpty()) {
                    if (rootObject != null && includedSelf)
                        cachedData.add(rootObject);
                    else
                        cachedData.addAll(Objects.requireNonNull(getPoolItems(parentPool)));
                }
                return cachedData.stream().skip(query.getOffset()).limit(query.getLimit());
            }

            @Override
            protected int sizeInBackEnd(Query<BusinessObjectLight, Void> query) {
                if (cachedData.isEmpty()) {
                    if (rootObject != null && includedSelf)
                        cachedData.add(rootObject);
                    else
                        cachedData.addAll(Objects.requireNonNull(getPoolItems(parentPool)));
                }
                return cachedData.size();
            }
        };
    }

    /**
     * Retrieves the items of a given pool.
     *
     * @param parentPool The parent pool from which the items will be obtained.
     * @return The business objects that are items of a given pool.
     */
    public List<BusinessObjectLight> getPoolItems(InventoryObjectPool parentPool) {
        try {
            return bem.getPoolItems(parentPool.getId(), -1);
        } catch (InvalidArgumentException | ApplicationObjectNotFoundException ex) {
            new SimpleNotification(ts.getTranslatedString("module.general.messages.error"),
                    ex.getMessage(), AbstractNotification.NotificationType.ERROR, ts).open();
            return null;
        }
    }
}