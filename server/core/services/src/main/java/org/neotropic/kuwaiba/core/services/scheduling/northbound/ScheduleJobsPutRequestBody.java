/*
 * Copyright 2010-2024 Neotropic SAS <contact@neotropic.co>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.neotropic.kuwaiba.core.services.scheduling.northbound;

import lombok.Data;

/**
 * Wrapper of Body to PUT  Request in {@link ScheduleController}.
 * Contains the necessary elements for update a scheduleJob and scheduleJob pool.
 * @author Juan Sebastian Betancourt {@literal <juan.betancourt@kuwaiba.org>}
 */
@Data
public class ScheduleJobsPutRequestBody {
    private String propertyToUpdate;
    private String value;
    private String userName;
}
