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
package org.neotropic.kuwaiba.core.notifications.information.strategies.impl;

import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.neotropic.kuwaiba.core.notifications.information.strategies.AbstractMessage;

/**
 * Implements the type of message to send notifications by e-mail.
 * @author Lina Sofia Cardona Martinez {@literal <lina.cardona@kuwaiba.org>}
 */
@Data
@Getter
@Setter
@AllArgsConstructor
public class EmailMessage implements AbstractMessage {
    /**
     * message title
     */
    private String subject;
    /**
     * message body text
     */
    private String body;
    /**
     * attachments included in the message
     */
    private Map<String, byte[]> attachments;

    @Override
    public Map<String, Object> getBodyMessage() {
        Map<String, Object> attributes = new HashMap<>();
        if(subject != null && !subject.isEmpty())
            attributes.put("subject", subject);
        if(body != null && !body.isEmpty())
            attributes.put("body", body);
        if(attachments != null && !attachments.isEmpty())
            attributes.put("attachments", attachments);
        return attributes;
    }
}