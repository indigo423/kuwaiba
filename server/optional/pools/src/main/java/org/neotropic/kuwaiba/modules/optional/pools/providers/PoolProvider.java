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
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.InvalidArgumentException;
import org.neotropic.kuwaiba.core.i18n.TranslationService;
import org.neotropic.util.visual.notifications.AbstractNotification;
import org.neotropic.util.visual.notifications.SimpleNotification;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * Data provider for inventory object pools that handles loading and accessing data.
 * Extends the {@link PoolProvider} class.
 *
 * @author Mauricio Ruiz Beltrán {@literal <mauricio.ruiz@kuwaiba.org>}
 */
public class PoolProvider {
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
    private final List<InventoryObjectPool> cachedData = new ArrayList<>();
    /**
     * Type of pool general purpose. These pools are not linked to any particular model.
     */
    public static final int POOL_TYPE_GENERAL_PURPOSE = 1;

    /**
     * Constructs a new instance of {@code PoolProvider}.
     *
     * @param bem Reference to the Business Entity Manager.
     * @param ts  Reference to the Translation Service.
     */
    public PoolProvider(BusinessEntityManager bem, TranslationService ts) {
        this.bem = bem;
        this.ts = ts;
    }

    /**
     * Builds a data provider for inventory object pools.
     *
     * @param rootPool     The root pool from which data will be retrieved. If exists.
     * @param includedSelf Boolean indicating whether the root pool should be included in the retrieved data.
     * @return Built data provider for inventory object pools.
     */
    public AbstractBackEndDataProvider<InventoryObjectPool, Void> buildDataProvider(
            InventoryObjectPool rootPool, boolean includedSelf) {
        return new AbstractBackEndDataProvider<InventoryObjectPool, Void>() {
            @Override
            protected Stream<InventoryObjectPool> fetchFromBackEnd(Query<InventoryObjectPool, Void> query) {
                if (cachedData.isEmpty()) {
                    if (rootPool != null && includedSelf)
                        cachedData.add(rootPool);
                    else
                        cachedData.addAll(Objects.requireNonNull(getRootPools()));
                }
                return cachedData.stream().skip(query.getOffset()).limit(query.getLimit());
            }

            @Override
            protected int sizeInBackEnd(Query<InventoryObjectPool, Void> query) {
                if (cachedData.isEmpty()) {
                    if (rootPool != null && includedSelf)
                        cachedData.add(rootPool);
                    else
                        cachedData.addAll(Objects.requireNonNull(getRootPools()));
                }
                return cachedData.size();
            }
        };
    }

    /**
     * Retrieves the pools that don't have any parent and are normally intended to be managed by the Pool Manager.
     *
     * @return The inventory object pools.
     */
    public List<InventoryObjectPool> getRootPools() {
        try {
            return bem.getRootPools(null, POOL_TYPE_GENERAL_PURPOSE, false);
        } catch (InvalidArgumentException ex) {
            new SimpleNotification(ts.getTranslatedString("module.general.messages.error"),
                    ex.getMessage(), AbstractNotification.NotificationType.ERROR, ts).open();
            return null;
        }
    }
}