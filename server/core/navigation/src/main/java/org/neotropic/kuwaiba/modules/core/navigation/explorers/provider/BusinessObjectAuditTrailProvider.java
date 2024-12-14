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
package org.neotropic.kuwaiba.modules.core.navigation.explorers.provider;

import com.vaadin.flow.data.provider.AbstractBackEndDataProvider;
import com.vaadin.flow.data.provider.Query;
import lombok.Getter;
import org.neotropic.kuwaiba.core.apis.persistence.application.ActivityLogEntry;
import org.neotropic.kuwaiba.core.apis.persistence.application.ApplicationEntityManager;
import org.neotropic.kuwaiba.core.apis.persistence.business.BusinessObjectLight;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.BusinessObjectNotFoundException;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.InvalidArgumentException;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.MetadataObjectNotFoundException;
import org.neotropic.kuwaiba.core.i18n.TranslationService;
import org.neotropic.util.visual.notifications.AbstractNotification;
import org.neotropic.util.visual.notifications.SimpleNotification;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * Data provider for activity log entry from a given object.
 *
 * @author Mauricio Ruiz Beltrán {@literal <mauricio.ruiz@kuwaiba.org>}
 */
public class BusinessObjectAuditTrailProvider {
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
    private final List<ActivityLogEntry> cachedData = new ArrayList<>();

    /**
     * Constructs a new instance of {@code BusinessObjectAuditTrailProvider}.
     *
     * @param aem Reference to the Application Entity Manager.
     * @param ts  Reference to the Translation Service.
     */
    public BusinessObjectAuditTrailProvider(ApplicationEntityManager aem, TranslationService ts) {
        this.aem = aem;
        this.ts = ts;
    }

    /**
     * Builds a data provider for activity log entries.
     *
     * @param businessObject The object from which data will be retrieved.
     * @return Built data provider for activity log entries.
     */
    public AbstractBackEndDataProvider<ActivityLogEntry, Void> buildDataProvider(BusinessObjectLight businessObject) {
        return new AbstractBackEndDataProvider<ActivityLogEntry, Void>() {

            @Override
            protected Stream<ActivityLogEntry> fetchFromBackEnd(Query<ActivityLogEntry, Void> query) {
                if (cachedData.isEmpty())
                    cachedData.addAll(Objects.requireNonNull(getBusinessObjectAuditTrail(businessObject)));

                return cachedData.stream().skip(query.getOffset()).limit(query.getLimit());
            }

            @Override
            protected int sizeInBackEnd(Query<ActivityLogEntry, Void> query) {
                if (cachedData.isEmpty())
                    cachedData.addAll(Objects.requireNonNull(getBusinessObjectAuditTrail(businessObject)));

                return cachedData.size();
            }
        };
    }

    /**
     * Gets a business object audit trail.
     *
     * @return The list of activity entries.
     */
    private List<ActivityLogEntry> getBusinessObjectAuditTrail(BusinessObjectLight businessObject) {
        try {
            return aem.getBusinessObjectAuditTrail(businessObject.getClassName(), businessObject.getId(), -1);
        } catch (BusinessObjectNotFoundException | MetadataObjectNotFoundException | InvalidArgumentException ex) {
            new SimpleNotification(ts.getTranslatedString("module.general.messages.error"),
                    ex.getMessage(), AbstractNotification.NotificationType.ERROR, ts).open();
            return null;
        }
    }
}