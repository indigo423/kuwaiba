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
package org.neotropic.kuwaiba.core.notifications.impl;

import java.util.List;
import org.neotropic.kuwaiba.core.notifications.NotificationType;
import org.neotropic.kuwaiba.core.notifications.connection.strategies.ConnectionStrategyFactory;
import org.neotropic.kuwaiba.core.notifications.connection.strategies.ConnectionStrategyInterface;
import org.neotropic.kuwaiba.core.notifications.exceptions.NotificationConnectionException;
import org.neotropic.kuwaiba.core.notifications.exceptions.NotificationMessageException;
import org.neotropic.kuwaiba.core.notifications.information.strategies.AbstractMessage;
import org.neotropic.kuwaiba.core.notifications.information.strategies.AbstractRecipient;
import org.neotropic.kuwaiba.core.notifications.information.strategies.AbstractSender;
import org.springframework.stereotype.Service;
import org.neotropic.kuwaiba.core.notifications.strategies.NotificationStrategyFactory;
import org.neotropic.kuwaiba.core.notifications.strategies.NotificationStrategyInterface;
import org.neotropic.kuwaiba.core.notifications.NotificationService;
import org.neotropic.kuwaiba.core.notifications.exceptions.NotificationParamsException;
import org.neotropic.kuwaiba.core.notifications.exceptions.NotificationTypeException;

/**
 * Performs the implementation of the email notification service.
 * @author Lina Sofia Cardona Martinez {@literal <lina.cardona@kuwaiba.org>}
 */
@Service
public class NotificationServiceImpl implements NotificationService {
    private final ConnectionStrategyFactory connectionFactory;
    
    private final NotificationStrategyFactory notificationStrategyFactory;
    
    public NotificationServiceImpl(ConnectionStrategyFactory connectionFactory, NotificationStrategyFactory notificationStrategyFactory) {
        this.connectionFactory = connectionFactory;
        this.notificationStrategyFactory = notificationStrategyFactory;
    }
    
    @Override
    public AbstractSender connect(NotificationType notificationType, AbstractSender sender) 
            throws NotificationConnectionException, NotificationParamsException, NotificationTypeException {
        ConnectionStrategyInterface connectionStrategy = connectionFactory.getConnectionStrategy(notificationType);
        connectionStrategy.connect(sender);
        return sender;
    }

    @Override
    public void sendUnicastNotification(NotificationType notificationType, AbstractMessage message, 
            AbstractSender sender, AbstractRecipient recipient) throws NotificationTypeException, NotificationMessageException, NotificationParamsException {
        NotificationStrategyInterface notificationStrategy = notificationStrategyFactory.getNotificationStrategy(notificationType);
        notificationStrategy.sendUnicastNotification(message, sender, recipient);
    }

    @Override
    public void sendMulticastNotification(NotificationType notificationType, 
            AbstractMessage message, AbstractSender sender, List<AbstractRecipient> recipients) 
            throws NotificationTypeException, NotificationMessageException, NotificationParamsException {
        NotificationStrategyInterface notificationStrategy = notificationStrategyFactory.getNotificationStrategy(notificationType);
        notificationStrategy.sendMulticastNotification(message, sender, recipients);
    }

    @Override
    public void disconnect(NotificationType notificationType, AbstractSender sender) throws NotificationTypeException {
        ConnectionStrategyInterface connectionStrategy = connectionFactory.getConnectionStrategy(notificationType);
        connectionStrategy.disconnect(sender);
    }
}
