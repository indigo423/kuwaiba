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
package org.neotropic.kuwaiba.modules.commercial.sync.notification;

import com.vaadin.flow.shared.Registration;
import org.neotropic.kuwaiba.modules.commercial.sync.model.SyncResult;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

/**
 * @author Hardy Ryan Chingal Martinez {@literal <ryan.chingal@neotropic.co>}
 */
@Component
public class Broadcaster {

    /**
     * Thread for notification executions
     */
    private static final Executor executor = Executors.newSingleThreadExecutor();
    /**
     * Used to refresh pages
     */
    private static final List<Consumer<List<SyncResult>>> syncResultListener = new ArrayList<>();
    /**
     * Keeps the map dependency for page refresh
     */
    private static Map<String, List<String>> updatePagesMap;
    private static final List<Consumer<String>> listeners = new ArrayList<>();


    public synchronized Registration register(Consumer<String> listener) {
        listeners.add(listener);
        return () -> {
            synchronized (Broadcaster.class) {
                listeners.remove(listener);
            }
        };
    }

    public synchronized void broadcast(String message) {
        for (Consumer<String> listener : listeners) {
            executor.execute(() -> {
                LocalDateTime lt = LocalDateTime.now();
                System.out.println("new push at: " + lt);
                listener.accept(message);
            });
        }
    }

    public synchronized void broadcast(List<SyncResult> syncResults) {
        for (Consumer<List<SyncResult>> listener : syncResultListener) {
            executor.execute(() -> {
                listener.accept(syncResults);
            });
        }
    }

    public void notifySyncResults(List<SyncResult> syncResults) {
        for (Consumer<List<SyncResult>> listener : syncResultListener) {
            listener.accept(syncResults);
        }
    }
}
