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
package org.neotropic.kuwaiba.core.notifications.connection.strategies.impl;

import java.util.Map;
import java.util.Properties;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import org.neotropic.kuwaiba.core.i18n.TranslationService;
import org.neotropic.kuwaiba.core.notifications.connection.strategies.ConnectionStrategyInterface;
import org.neotropic.kuwaiba.core.notifications.exceptions.NotificationConnectionException;
import org.neotropic.kuwaiba.core.notifications.exceptions.NotificationException;
import org.neotropic.kuwaiba.core.notifications.exceptions.NotificationParamsException;
import org.neotropic.kuwaiba.core.notifications.information.strategies.AbstractSender;

/**
 * Implement the via connection strategy for e-mail notifications.
 * @author Lina Sofia Cardona Martinez {@literal <lina.cardona@kuwaiba.org>}
 */
public class EmailConnectionStrategy implements ConnectionStrategyInterface {
    private final TranslationService ts;
    
    public EmailConnectionStrategy(TranslationService ts) {
        this.ts = ts;
    }
    
    @Override
    public void connect(AbstractSender sender) throws NotificationConnectionException, NotificationParamsException {
        try {
            Map<String, Object> senderParams = sender.getSenderParams();
            verifySenderParams(senderParams);
            String login = senderParams.get("email").toString();
            String password = senderParams.get("password").toString();
            Properties props = new Properties();
            props.put("mail.smtp.auth", true);
            props.put("mail.smtp.starttls.enable", true);
            props.put("mail.smtp.host", senderParams.get("serverName").toString());
            props.put("mail.smtp.port", (Integer) senderParams.get("serverPort"));

            // Create a default session
            Session session = Session.getInstance(props, new javax.mail.Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(login, password);
                }
            });
            sender.setConnectionInformation(session);
        } catch(NotificationException ex) {
            throw new NotificationConnectionException(ts.getTranslatedString("notifications.connection.error"));
        }
    }

    @Override
    public void disconnect(AbstractSender sender) {
    }
    
    /**
     * Verify that the transmitter parameters necessary to establish the connection exist.
     * @param senderParams parameters of sender
     * @throws NotificationParamsException If the parameters of the transmitter are not correct
     */
    private void verifySenderParams(Map<String, Object> senderParams) throws NotificationParamsException  {
        if(!senderParams.containsKey("serverName")) 
            throw new NotificationParamsException (String.format(ts.getTranslatedString("notifications.invalid.parameter"), "serverName"));
        if(!senderParams.containsKey("serverPort")) 
            throw new NotificationParamsException (String.format(ts.getTranslatedString("notifications.invalid.parameter"), "serverPort"));
        if(!senderParams.containsKey("email")) 
            throw new NotificationParamsException (String.format(ts.getTranslatedString("notifications.invalid.parameter"), "email"));
        if(!senderParams.containsKey("password")) 
            throw new NotificationParamsException (String.format(ts.getTranslatedString("notifications.invalid.parameter"), "password"));
    }
}
