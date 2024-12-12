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
package org.neotropic.kuwaiba.modules.optional.taskman.providers;

import com.vaadin.flow.data.provider.AbstractBackEndDataProvider;
import com.vaadin.flow.data.provider.Query;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.neotropic.kuwaiba.core.apis.persistence.application.ResultMessage;
import org.neotropic.kuwaiba.core.apis.persistence.application.TaskResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * Data provider for messages from a given task result.
 *
 * @author Mauricio Ruiz Beltrán {@literal <mauricio.ruiz@kuwaiba.org>}
 */
@Getter
@NoArgsConstructor
public class ResultMessageProvider {
    /**
     * List that caches data retrieved.
     */
    private final List<ResultMessage> cachedData = new ArrayList<>();

    /**
     * Builds a data provider for task result messages.
     *
     * @param taskResult The task result from which data will be retrieved.
     * @return Built data provider for task result messages.
     */
    public AbstractBackEndDataProvider<ResultMessage, Void> buildDataProvider(TaskResult taskResult) {
        return new AbstractBackEndDataProvider<ResultMessage, Void>() {

            @Override
            protected Stream<ResultMessage> fetchFromBackEnd(Query<ResultMessage, Void> query) {
                if (cachedData.isEmpty())
                    cachedData.addAll(Objects.requireNonNull(getResultMessages(taskResult)));

                return cachedData.stream().skip(query.getOffset()).limit(query.getLimit());
            }

            @Override
            protected int sizeInBackEnd(Query<ResultMessage, Void> query) {
                if (cachedData.isEmpty())
                    cachedData.addAll(Objects.requireNonNull(getResultMessages(taskResult)));

                return cachedData.size();
            }
        };
    }

    /**
     * Gets a task result messages list.
     *
     * @param taskResult The task result from which data will be retrieved.
     * @return The list of task result messages.
     */
    private List<ResultMessage> getResultMessages(TaskResult taskResult) {
        return taskResult.getMessages();
    }
}