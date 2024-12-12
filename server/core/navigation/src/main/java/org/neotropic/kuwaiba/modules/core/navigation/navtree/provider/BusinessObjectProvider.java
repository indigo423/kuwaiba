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
package org.neotropic.kuwaiba.modules.core.navigation.navtree.provider;

import com.vaadin.flow.data.provider.AbstractBackEndDataProvider;
import org.neotropic.kuwaiba.core.apis.persistence.business.BusinessObjectLight;

import java.util.List;

/**
 * Abstract business object provider that defines methods for constructing data providers
 * and accessing cached or id based data.
 *
 * <p>
 * Concrete implementations of this class must provide specific functionality to build
 * Data providers and access data in cache or by id.
 * </p>
 *
 * @author Mauricio Ruiz Beltrán {@literal <mauricio.ruiz@kuwaiba.org>}
 */
public abstract class BusinessObjectProvider {

    /**
     * Builds a data provider for business objects.
     *
     * @param rootObject   The root object from which data will be retrieved.
     * @param includedSelf Boolean indicating whether the root object should be included in the retrieved data.
     * @return Built data provider for business objects.
     */
    public abstract AbstractBackEndDataProvider<BusinessObjectLight, Void> buildDataProvider(
            BusinessObjectLight rootObject, boolean includedSelf);

    /**
     * Recovers cached data.
     *
     * @return List of cached business objects.
     */
    public abstract List<BusinessObjectLight> getCachedData();

    /**
     * Retrieves a business object by its id.
     *
     * @param objectId Id of the business object to retrieve.
     * @return The business object with the specified id, or null if not found.
     */
    public abstract BusinessObjectLight getObjectById(String objectId);
}