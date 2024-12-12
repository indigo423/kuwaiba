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
package org.neotropic.kuwaiba.core.notifications.strategies;

import org.neotropic.kuwaiba.core.i18n.TranslationService;
import org.neotropic.kuwaiba.core.notifications.NotificationType;
import org.neotropic.kuwaiba.core.notifications.exceptions.NotificationTypeException;
import org.neotropic.kuwaiba.core.notifications.strategies.impl.EmailNotificationStrategy;
import org.springframework.stereotype.Component;

/**
 * 
 * @author Lina Sofia Cardona Martinez {@literal <lina.cardona@kuwaiba.org>}
 */
@Component
public class NotificationStrategyFactory {
    private final TranslationService ts;
    
    public NotificationStrategyFactory(TranslationService ts) {
        this.ts = ts;
    }
    
    /**
     * Sets the type of notification strategy to use according to the type of notification.
     * @param type notification type
     * @return the notification strategy to be used
     * @throws NotificationTypeException Exception thrown if notification type does not exist
     */
    public NotificationStrategyInterface getNotificationStrategy(NotificationType type) throws NotificationTypeException {
        switch (type) {
            case EMAIL:
                return new EmailNotificationStrategy(ts);
            default:
                throw new NotificationTypeException(String.format(ts.getTranslatedString("notifications.notification-type-invalid"), type));
        }
    }
}
