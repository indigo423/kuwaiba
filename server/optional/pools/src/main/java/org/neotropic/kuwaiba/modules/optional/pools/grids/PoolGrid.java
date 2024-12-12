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
package org.neotropic.kuwaiba.modules.optional.pools.grids;

import org.neotropic.kuwaiba.core.apis.persistence.application.InventoryObjectPool;
import org.neotropic.kuwaiba.modules.optional.pools.providers.PoolProvider;
import org.vaadin.tatu.BeanTable;
import org.vaadin.tatu.BeanTableVariant;

/**
 * Represents a grid of inventory object pools for displaying data in a UI.
 * Extends the {@link BeanTable} class.
 *
 * @author Mauricio Ruiz Beltrán {@literal <mauricio.ruiz@kuwaiba.org>}
 */
public class PoolGrid extends BeanTable<InventoryObjectPool> {
    /**
     * Data provider used to load data into the grid.
     */
    private PoolProvider provider;

    /**
     * Constructs a new instance of {@code BusinessObjectChildrenGrid}.
     *
     * @param pageLength Page length for the grid.
     */
    public PoolGrid(int pageLength) {
        super(InventoryObjectPool.class, false, pageLength);
        addThemeVariants();
    }

    /**
     * Builds the data provider for the grid using the provided provider.
     *
     * @param provider     Data provider to construct the data provider for the grid.
     * @param rootPool     The root pool from which data will be retrieved.
     * @param includedSelf Boolean indicating whether the root pool should be included in the retrieved data.
     */
    public void buildDataProvider(PoolProvider provider,
                                  InventoryObjectPool rootPool,
                                  boolean includedSelf) {
        this.provider = provider;
        setDataProvider(this.provider.buildDataProvider(rootPool, includedSelf));
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

    /**
     * Checks if the grid contains a given pool.
     *
     * @param pool The pool to check for existence in the grid.
     * @return True if the grid contains the pool, false otherwise.
     */
    public boolean containsPool(InventoryObjectPool pool) {
        return this.provider.getCachedData().contains(pool);
    }
}