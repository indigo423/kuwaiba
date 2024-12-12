/**
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
package org.neotropic.kuwaiba.core.apis.persistence.application;

import java.util.List;
import lombok.Builder;
import lombok.Data;
import org.neotropic.kuwaiba.core.apis.persistence.util.StringPair;

/**
 * Class used to serialize requests to be made by the consumer of the task.
 * 
 * The result of executing a Task is a {@link TaskResult}, which has a list of
 * {@link ResultMessage} which indicates whether the result is success, error or with warnings, and a message.
 * The serialization of the TaskConsumerRequest is the message that the task script sets to the {@link ResultMessage}.
 * So that its consumer can deserialize it and implement actions to solve the requests.
 * 
 * @author Johny Andres Ortega Ruiz {@literal <johny.ortega@kuwaiba.org>}
 */
@Data
@Builder
public class TaskConsumerRequest {
    /**
     * Request id, used by the task consumer to define the actions to be performed.
     */
    private String id;
    /**
     * List of parameters necessary for the resolution of the request.
     */
    private List<StringPair> parameters;
}
