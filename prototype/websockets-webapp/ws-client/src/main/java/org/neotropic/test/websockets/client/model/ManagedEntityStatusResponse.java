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

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Charles Edward Bedon Cortazar {@literal <charles.bedon@kuwaiba.org>}
 */
public class ManagedEntityStatusResponse extends GenericResult<String> {

    public ManagedEntityStatusResponse() {
    }

    public ManagedEntityStatusResponse(int executionCode, String entityStatus) {
        this.executionCode = executionCode;
        this.payload = entityStatus;
        this.messages = new ArrayList<>();
    }

    public ManagedEntityStatusResponse(int executionCode, String entityStatus, List<String> messages) {
        this.executionCode = executionCode;
        this.payload = entityStatus;
        this.messages = messages;
    }
}