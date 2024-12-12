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

import java.util.List;
import org.neotropic.kuwaiba.core.notifications.exceptions.NotificationMessageException;
import org.neotropic.kuwaiba.core.notifications.exceptions.NotificationParamsException;
import org.neotropic.kuwaiba.core.notifications.information.strategies.AbstractMessage;
import org.neotropic.kuwaiba.core.notifications.information.strategies.AbstractRecipient;
import org.neotropic.kuwaiba.core.notifications.information.strategies.AbstractSender;

/**
 * Contains the methods of sending notifications to one or more recipients.
 * @author Lina Sofia Cardona Martinez {@literal <lina.cardona@kuwaiba.org>}
 */
public interface NotificationStrategyInterface {
    
    /**
     * Send a notification to a single recipient
     * @param message Information message to be sent
     * @param sender Information of the sender of the notification message
     * @param recipient contains the information about the receiver.
     * @throws NotificationMessageException
     * @throws org.neotropic.kuwaiba.core.notifications.exceptions.NotificationParamsException
     */
    public void sendUnicastNotification(AbstractMessage message, AbstractSender sender, AbstractRecipient recipient) throws NotificationMessageException, NotificationParamsException;
    
    /**
     * Send a notification to multiple recipients
     * @param message Information message to be sent
     * @param sender Information of the sender of the notification message
     * @param recipients contains the information about the receiver.
     * @throws NotificationMessageException
     * @throws org.neotropic.kuwaiba.core.notifications.exceptions.NotificationParamsException
     */
    public void sendMulticastNotification(AbstractMessage message, AbstractSender sender, List<AbstractRecipient> recipients) throws NotificationMessageException, NotificationParamsException;
}
