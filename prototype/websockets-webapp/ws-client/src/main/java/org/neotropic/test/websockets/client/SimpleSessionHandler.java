/*
 *  Copyright 2010-2021 Neotropic SAS <contact@neotropic.co>.
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

package org.neotropic.test.websockets.client;

import java.lang.reflect.Type;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.neotropic.test.websockets.client.model.GenericResult;
import org.neotropic.test.websockets.client.model.ManagedEntityStatusRequest;
import org.neotropic.test.websockets.client.model.ManagedEntityStatusResponse;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;

/**
 *
 * @author Charles Edward Bedon Cortazar {@literal <charles.bedon@kuwaiba.org>}
 */
public class SimpleSessionHandler implements StompSessionHandler {
    private String subscriptionUri;
    private String messageEndpoint;

    public SimpleSessionHandler(String subscriptionUri, String messageEndpoint) {
        this.subscriptionUri = subscriptionUri;
        this.messageEndpoint = messageEndpoint;
    }
    
    @Override
    public void afterConnected(StompSession session, StompHeaders sh) {
        session.subscribe(subscriptionUri, this);
        session.send(messageEndpoint, new ManagedEntityStatusRequest("MY_DEVICE_ID_OR_SERIAL"));
        Logger.getLogger(SimpleSessionHandler.class.toString()).log(Level.INFO, "Message sent");
    }

    @Override
    public void handleException(StompSession ss, StompCommand sc, StompHeaders sh, byte[] bytes, Throwable thrwbl) {
        thrwbl.printStackTrace();
    }

    @Override
    public void handleTransportError(StompSession ss, Throwable thrwbl) {
        thrwbl.printStackTrace();
    }

    @Override
    public Type getPayloadType(StompHeaders sh) {
        return ManagedEntityStatusResponse.class;
    }

    @Override
    public void handleFrame(StompHeaders sh, Object payload) {
        ManagedEntityStatusResponse msg = (ManagedEntityStatusResponse) payload;
        Logger.getLogger(SimpleSessionHandler.class.toString()).log(Level.INFO, "Message received");
        switch (msg.getExecutionCode()) {
            case GenericResult.ACTION_RESULT_ERROR:
                Logger.getLogger(SimpleSessionHandler.class.toString()).log(Level.SEVERE, 
                        "Operation ended with errors: " + (msg.getMessages() == null || msg.getMessages().isEmpty() ? "No details provided" : msg.getMessages().get(0)));
                break;
            default:
                Logger.getLogger(SimpleSessionHandler.class.toString()).log(Level.INFO, "Operation ended successfully");
        }
        
    }
}