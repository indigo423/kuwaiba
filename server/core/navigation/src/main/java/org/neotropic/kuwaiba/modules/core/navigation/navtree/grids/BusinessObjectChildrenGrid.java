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

import com.vaadin.flow.component.Tag;
import org.neotropic.kuwaiba.core.apis.persistence.business.BusinessObjectLight;
import org.neotropic.kuwaiba.modules.core.navigation.navtree.provider.BusinessObjectProvider;
import org.vaadin.tatu.BeanTable;
import org.vaadin.tatu.BeanTableVariant;

/**
 * Represents a grid of business objects for displaying data in a UI.
 * Extends the {@link BeanTable} class.
 *
 * @author Mauricio Ruiz Beltrán {@literal <mauricio.ruiz@kuwaiba.org>}
 */
@Tag("table")
public class BusinessObjectChildrenGrid extends BeanTable<BusinessObjectLight> {
    /**
     * Data provider used to load data into the grid.
     */
    private BusinessObjectProvider provider;

    /**
     * Constructs a new instance of {@code BusinessObjectChildrenGrid}.
     *
     * @param pageLength Page length for the grid.
     */
    public BusinessObjectChildrenGrid(int pageLength) {
        super(BusinessObjectLight.class, false, pageLength);
        addThemeVariants();
    }

    /**
     * Builds the data provider for the grid using the provided provider.
     *
     * @param provider     Data provider to construct the data provider for the grid.
     * @param rootObject   The root object from which data will be retrieved.
     * @param includedSelf Boolean indicating whether the root object should be included in the retrieved data.
     */
    public void buildDataProvider(BusinessObjectProvider provider,
                                  BusinessObjectLight rootObject,
                                  boolean includedSelf) {
        this.provider = provider;
        setDataProvider(this.provider.buildDataProvider(rootObject, includedSelf));
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
     * Retrieves a business object by its id using the associated data provider.
     *
     * @param objectId Id of the business object to retrieve.
     * @return The business object with the specified id, or null if not found.
     */
    public BusinessObjectLight getObjectById(String objectId) {
        return this.provider.getObjectById(objectId);
    }

    /**
     * Checks if the grid contains a given business object.
     *
     * @param object The business object to check for existence in the grid.
     * @return True if the grid contains the object, false otherwise.
     */
    public boolean containsObject(BusinessObjectLight object) {
        return this.provider.getCachedData().contains(object);
    }
}