/*
 *  Copyright 2022 Neotropic SAS. <contact@neotropic.co>.
 *  Licensed under the EPL License, Version 1.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *   http://www.eclipse.org/legal/epl-v10.html
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.neotropic.synchronization.notification;

import com.neotropic.synchronization.data.entitites.Person;
import com.neotropic.synchronization.visual.generic.MenuCounterConsumer;
import com.vaadin.flow.shared.Registration;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

/**
 * @author Hardy Ryan Chingal Martinez {@literal <ryan.chingal@kuwaiba.org>}
 * created on 01/04/2022-08:27
 */
@Component
public class Broadcaster {
        /**
         * Keeps the map dependency for page refresh
         */
        private static Map<String, List<String>> updatePagesMap;
        /**
         * Thread for notification executions
         */
        private static final Executor executor = Executors.newSingleThreadExecutor();
        /**
         * Used to refresh pages
         */
        private static final LinkedList<MenuCounterConsumer> MENU_COUNTERS_CONSUMER_LISTENERS = new LinkedList();

        private static final LinkedList<Consumer<List<Person>>> personListener = new LinkedList();

        private static LinkedList<Consumer<String>> listeners = new LinkedList();

        public synchronized Registration registerUser(Consumer<List<Person>> listener) {
                personListener.add(listener);
                return () -> {
                        synchronized (Broadcaster.class) {
                                personListener.remove(listener);
                        }
                };
        }

        public synchronized Registration register(Consumer<String> listener) {
                listeners.add(listener);
                return () -> {
                        synchronized (Broadcaster.class) {
                                listeners.remove(listener);
                        }
                };
        }

        public synchronized void broadcast(String message) {
                for (Consumer listener : listeners)
                        executor.execute(() -> {
                                LocalDateTime lt = LocalDateTime.now();
                                System.out.println("new push at: "+lt);
                                listener.accept(message);
                        });
        }

        public synchronized void broadcast(List<Person> people) {
                for (Consumer listener : personListener)
                        executor.execute(() -> {
                                listener.accept(people);
                        });
        }
}
