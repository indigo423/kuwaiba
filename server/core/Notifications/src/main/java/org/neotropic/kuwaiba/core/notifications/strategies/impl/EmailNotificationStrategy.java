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
package org.neotropic.kuwaiba.core.notifications.strategies.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import org.neotropic.kuwaiba.core.i18n.TranslationService;
import org.neotropic.kuwaiba.core.notifications.exceptions.NotificationMessageException;
import org.neotropic.kuwaiba.core.notifications.exceptions.NotificationParamsException;
import org.neotropic.kuwaiba.core.notifications.information.strategies.AbstractMessage;
import org.neotropic.kuwaiba.core.notifications.information.strategies.AbstractRecipient;
import org.neotropic.kuwaiba.core.notifications.information.strategies.AbstractSender;
import org.neotropic.kuwaiba.core.notifications.strategies.NotificationStrategyInterface;
import org.springframework.mail.javamail.MimeMessageHelper;

/**
 * Implementation of e-mail notification system
 * @author Lina Sofia Cardona Martinez {@literal <lina.cardona@kuwaiba.org>}
 */
public class EmailNotificationStrategy implements NotificationStrategyInterface {

    /**
     * Email server configure properties
     */
    private static final String CONTENT_TYPE = "text/html" ;
    /**
     * Define attachment limits size, by default 20MB
     */
    private final int maximumAttachmentSize = 20;
    
    private final TranslationService ts;
    
    public EmailNotificationStrategy(TranslationService ts) {
        this.ts = ts;
    }
    
    @Override
    public void sendUnicastNotification(AbstractMessage message, AbstractSender sender, AbstractRecipient recipient) throws NotificationMessageException, NotificationParamsException {
        List<AbstractRecipient> recipientList = Collections.singletonList(recipient);
        sendMulticastNotification(message, sender, recipientList);
    }

    @Override
    public void sendMulticastNotification(AbstractMessage message, AbstractSender sender, List<AbstractRecipient> recipients) throws NotificationMessageException, NotificationParamsException {
        Map<String, Object> senderParams = sender.getSenderParams();
        validateSenderParameters(senderParams);
        Map<String, Object> messageParams = message.getBodyMessage();
        validateMessageParameters(messageParams);
        validateRecipientsParameters(recipients);
        Session session = (Session) senderParams.get("session");
        try {
            // Set the mail headers
            MimeMessage msg = new MimeMessage(session);
            MimeMessageHelper helper = new MimeMessageHelper(msg,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());

            msg.setSentDate(new Date());
            
            String emailReply = null;
            String login = senderParams.get("email").toString();
            String userName = null;
            if(senderParams.containsKey("replyTo"))
                emailReply = senderParams.get("replyTo").toString();
            if(senderParams.containsKey("userName"))
                userName = senderParams.get("userName").toString();
            helper.setFrom(emailReply != null ? emailReply : login, userName != null ? userName : login);
            if(emailReply != null)
                helper.setReplyTo(emailReply);
            
            if(messageParams.containsKey("subject"))
                helper.setSubject(messageParams.get("subject").toString());
            String body = "";
            if(messageParams.containsKey("body"))
                body = messageParams.get("body").toString();
            
            List<InternetAddress> internetAddresses = new ArrayList<>();
            recipients.forEach(recipient -> {
                Map<String,Object> recipientParams = recipient.getRecipient();
                try {
                    internetAddresses.add(new InternetAddress(recipientParams.get("recipient").toString()));
                } catch (AddressException ex) {
                }
            });
            
            helper.setTo(internetAddresses.toArray(InternetAddress[]::new));
            Map<String, byte[]> attachments = new HashMap<>();
            if(messageParams.containsKey("attachments"))
                attachments = (Map<String, byte[]>) messageParams.get("attachments");
            // Create the mime body and attachments
            MimeBodyPart msgBody = new MimeBodyPart();
            msgBody.setContent(body, CONTENT_TYPE);
            MimeBodyPart attFile = new MimeBodyPart();
            List<File> attachmentlist = new ArrayList<>();
            for (Map.Entry<String, byte[]> attachment : attachments.entrySet()) {// for loop used to allow exception
                File tempFile = File.createTempFile("report_", attachment.getKey());
                FileOutputStream fileOutputStream = new FileOutputStream(tempFile);
                fileOutputStream.write(attachment.getValue());
                if ((tempFile.length() / (1024 * 1024)) > maximumAttachmentSize) {
                    throw new NotificationMessageException(String.format(ts.getTranslatedString("notifications.email.attachment.size.error")
                            , tempFile.getName(), maximumAttachmentSize));
                }
                attFile.attachFile(tempFile);
                attachmentlist.add(tempFile);
            }
            Multipart partMsg = new MimeMultipart();
            partMsg.addBodyPart(msgBody);
            if(!attachments.isEmpty())
                partMsg.addBodyPart(attFile);
            msg.setContent(partMsg);
            Transport.send(msg);
            //delete temporal attachment
            attachmentlist.forEach(File::delete);
        } catch (MessagingException ex) {
            throw new NotificationMessageException(String.format(ts.getTranslatedString("notifications.email.send.error"), ex.getMessage()));
        } catch (IOException ex) {
            throw new NotificationMessageException(String.format(ts.getTranslatedString("notifications.email.attachment.error"), ex.getMessage()));
        }
    }
    
    private void validateSenderParameters(Map<String, Object> parameters) throws NotificationParamsException {
        if(!parameters.containsKey("email")) 
            throw new NotificationParamsException (String.format(ts.getTranslatedString("notifications.invalid.parameter"), "email"));
        if(!parameters.containsKey("password")) 
            throw new NotificationParamsException (String.format(ts.getTranslatedString("notifications.invalid.parameter"), "password"));
        if(!parameters.containsKey("session"))
            throw new NotificationParamsException (String.format(ts.getTranslatedString("notifications.session-invalid")));
    }
    
    private void validateRecipientsParameters(List<AbstractRecipient> recipients) throws NotificationParamsException {
        recipients.forEach(recipient -> {
            if(!recipient.getRecipient().containsKey("recipient")) {
                try {
                    throw new NotificationParamsException (String.format(ts.getTranslatedString("notifications.invalid.parameter"), "recipient"));
                } catch (NotificationParamsException ex) {
                }
            }
        });
        
    }
    
    private void validateMessageParameters(Map<String, Object> parameters) throws NotificationMessageException {
        if(parameters.isEmpty()) 
            throw new NotificationMessageException(ts.getTranslatedString("notifications.message-empty.error"));
    }
}
