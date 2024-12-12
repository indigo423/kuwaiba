/*
 * Copyright 2010-2024. Neotropic SAS <contact@neotropic.co>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.neotropic.kuwaiba.modules.commercial.sync.components;

/**
 * @author Hardy Ryan Chingal Martinez {@literal <ryan.chingal@neotropic.co>}
 */
public enum EJobState {
    NEW("sync.step.state.new"),
    IN_PROGRESS("sync.step.state.in_progess"),
    STOP("sync.step.state.stop"),
    FINISH("sync.step.state.finish");

    private final String jobState;

    EJobState(String jobState) {
        this.jobState = jobState;
    }

    public String getValue() {
        return jobState;
    }
}

