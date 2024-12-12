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
package org.neotropic.kuwaiba.modules.core.ltman.providers;

import com.vaadin.flow.data.provider.AbstractBackEndDataProvider;
import com.vaadin.flow.data.provider.Query;
import lombok.Getter;
import org.neotropic.kuwaiba.core.apis.persistence.application.ApplicationEntityManager;
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
 * Data provider for uses from a given list type item.
 *
 * @author Mauricio Ruiz Beltrán {@literal <mauricio.ruiz@kuwaiba.org>}
 */
public class ListTypeItemUsesProvider {
    /**
     * Reference to the Application Entity Manager.
     */
    private final ApplicationEntityManager aem;
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
     * Constructs a new instance of {@code ListTypeItemUsesProvider}.
     *
     * @param aem Reference to the Application Entity Manager.
     * @param ts  Reference to the Translation Service.
     */
    public ListTypeItemUsesProvider(ApplicationEntityManager aem, TranslationService ts) {
        this.aem = aem;
        this.ts = ts;
    }

    /**
     * Builds a data provider for list type item uses.
     *
     * @param listTypeItem The list type item from which data will be retrieved.
     * @return Built data provider for list type item uses.
     */
    public AbstractBackEndDataProvider<BusinessObjectLight, Void> buildDataProvider(BusinessObjectLight listTypeItem) {
        return new AbstractBackEndDataProvider<BusinessObjectLight, Void>() {

            @Override
            protected Stream<BusinessObjectLight> fetchFromBackEnd(Query<BusinessObjectLight, Void> query) {
                if (cachedData.isEmpty())
                    cachedData.addAll(Objects.requireNonNull(getListTypeItemUses(listTypeItem)));

                return cachedData.stream().skip(query.getOffset()).limit(query.getLimit());
            }

            @Override
            protected int sizeInBackEnd(Query<BusinessObjectLight, Void> query) {
                if (cachedData.isEmpty())
                    cachedData.addAll(Objects.requireNonNull(getListTypeItemUses(listTypeItem)));

                return cachedData.size();
            }
        };
    }

    /**
     * Gets an uses list.
     *
     * @param listTypeItem The list type item from which data will be retrieved.
     * @return The list of uses.
     */
    private List<BusinessObjectLight> getListTypeItemUses(BusinessObjectLight listTypeItem) {
        try {
            return aem.getListTypeItemUses(listTypeItem.getClassName(), listTypeItem.getId(), -1);
        } catch (ApplicationObjectNotFoundException | InvalidArgumentException ex) {
            new SimpleNotification(ts.getTranslatedString("module.general.messages.error"),
                    ex.getMessage(), AbstractNotification.NotificationType.ERROR, ts).open();
            return null;
        }
    }
}