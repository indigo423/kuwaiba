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
import javax.mail.Session;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.neotropic.kuwaiba.core.notifications.information.strategies.AbstractSender;

/**
 * Define the attributes of email notification sender
 * @author Lina Sofia Cardona Martinez {@literal <lina.cardona@kuwaiba.org>}
 */
@Getter
@Setter
@NoArgsConstructor
public class EmailSender implements AbstractSender {
    /**
    * mail server
    */
    private String serverName;
    /**
    * port of server
    */
    private int serverPort;
    /**
    * email address
    */
    private String emailAddress;
    /**
    * username of email address
    */
    private String userName;
    /**
    * email password
    */
    private String password;
    /**
    * 
    */
    private String replyTo;
    /**
    * email session.
    */
    private Session session;
    
    public EmailSender(String serverName, int serverPort, String emailAddress, String password) {
        this.serverName = serverName;
        this.serverPort = serverPort;
        this.emailAddress = emailAddress;
        this.password = password;
    } 
    
    public EmailSender(String serverName, int serverPort, String emailAddress, String password, String userName, String replyTo) {
        this.serverName = serverName;
        this.serverPort = serverPort;
        this.emailAddress = emailAddress;
        this.password = password;
        this.userName = userName;
        this.replyTo = replyTo;
    } 

    @Override
    public Map<String, Object> getSenderParams() {
        Map<String, Object> params = new HashMap<>();
        if(serverName != null && !serverName.isEmpty())
            params.put("serverName", serverName);
        if(serverPort != 0) 
            params.put("serverPort", serverPort);
        if(emailAddress != null && !emailAddress.isEmpty())
            params.put("email", emailAddress);
        if(userName != null && !userName.isEmpty())
            params.put("userName", userName);
        if(password != null && !password.isEmpty())
            params.put("password", password);
        if(replyTo != null && !replyTo.isEmpty())
            params.put("replyTo", replyTo);
        if(session != null)
            params.put("session", session);
        return params;
    }

    @Override
    public void setConnectionInformation(Object session) {
        Session newSession = (Session) session;
        this.session = newSession;
    }
}
