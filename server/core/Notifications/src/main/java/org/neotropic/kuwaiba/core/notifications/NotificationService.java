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
package org.neotropic.kuwaiba.core.notifications;

import java.util.List;
import org.neotropic.kuwaiba.core.notifications.exceptions.NotificationConnectionException;
import org.neotropic.kuwaiba.core.notifications.exceptions.NotificationMessageException;
import org.neotropic.kuwaiba.core.notifications.exceptions.NotificationParamsException;
import org.neotropic.kuwaiba.core.notifications.exceptions.NotificationTypeException;
import org.neotropic.kuwaiba.core.notifications.information.strategies.AbstractMessage;
import org.neotropic.kuwaiba.core.notifications.information.strategies.AbstractRecipient;
import org.neotropic.kuwaiba.core.notifications.information.strategies.AbstractSender;

/**
 * Contains the necessary methods to implement in the notification service.
 * @author Lina Sofia Cardona Martinez {@literal <lina.cardona@kuwaiba.org>}
 */
public interface NotificationService {

    /**
     * Makes the connection on the channel through which notifications are to be sent.
     * @param notificationType Type of notifications to be sent 
     * @param sender Information of the sender of the notification message
     * @return sender
     * @throws NotificationTypeException Exception thrown if the notification type is not found.
     * @throws NotificationConnectionException Exception thrown if the connection cannot be made.
     * @throws NotificationParamsException Exception thrown if the sender's parameters are not sufficient to establish the connection.
     */
    public AbstractSender connect(NotificationType notificationType, AbstractSender sender) 
            throws NotificationConnectionException, NotificationParamsException, NotificationTypeException;
    
    /**
     * Send a notification to a single recipient
     * @param notificationType Type of notifications to be sent 
     * @param message Information message to be sent
     * @param sender Information of the sender of the notification message
     * @param recipient contains the information about the receiver.
     * @throws NotificationTypeException Exception thrown if the notification type is not found.
     * @throws NotificationMessageException
     * @throws org.neotropic.kuwaiba.core.notifications.exceptions.NotificationParamsException
     */
    public void sendUnicastNotification(NotificationType notificationType, AbstractMessage message, 
            AbstractSender sender, AbstractRecipient recipient) throws NotificationTypeException, NotificationMessageException, NotificationParamsException;
    
    /**
     * Send a notification to multiple recipients
     * @param notificationType Type of notifications to be sent 
     * @param message Information message to be sent
     * @param sender Information of the sender of the notification message
     * @param recipients contains the information about each receiver.
     * @throws NotificationTypeException Exception thrown if the notification type is not found.
     * @throws NotificationMessageException
     * @throws org.neotropic.kuwaiba.core.notifications.exceptions.NotificationParamsException
     */
    public void sendMulticastNotification(NotificationType notificationType, AbstractMessage message, 
            AbstractSender sender, List<AbstractRecipient> recipients) throws NotificationTypeException, NotificationMessageException, NotificationParamsException;
    
    /**
     * Closes an existing session
     * @param notificationType Type of notifications 
     * @param sender Information of the sender of the notification message
     * @throws NotificationTypeException Exception thrown if the notification type is not found.
     */
    public void disconnect(NotificationType notificationType, AbstractSender sender) throws NotificationTypeException;
}
