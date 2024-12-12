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

package org.neotropic.test.websockets.client.model;

import java.util.List;

/**
 *
 * @author Charles Edward Bedon Cortazar {@literal <charles.bedon@kuwaiba.org>}
 */
public abstract class GenericResult<T> {
    /**
     * The action could be completed successfully.
     */
    public static final int ACTION_RESULT_OK = 1;
    /**
     * The action ended with errors.
     */
    public static final int ACTION_RESULT_ERROR = 2;
    /**
     * The action was performed, but there are warning messages to be reviewed.
     */
    public static final int ACTION_RESULT_WARNING = 3;
    /**
     * The result of the action.
     */
    protected int executionCode;
    /**
     * The list of messages associated to the action (exceptions, warnings, etc).
     */
    protected List<String> messages;
    /**
     * The actual result of the execution of the call.
     */
    protected T payload;

    public int getExecutionCode() {
        return executionCode;
    }

    public void setExecutionCode(int executionCode) {
        this.executionCode = executionCode;
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    public T getPayload() {
        return payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }
}