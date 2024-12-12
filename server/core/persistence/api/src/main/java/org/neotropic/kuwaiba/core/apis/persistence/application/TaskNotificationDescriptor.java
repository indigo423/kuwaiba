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

package org.neotropic.kuwaiba.core.apis.persistence.application;

/**
 * Describes how the results of a task should be notified to the users associated to it.
 * @author Charles Edward Bedon Cortazar {@literal <charles.bedon@kuwaiba.org>}
 */
public class TaskNotificationDescriptor {
    /**
     * Execute once, on system start-up.
     */
    public static final int TYPE_CLIENT = 1;
    /**
     * Execute once, on user's log in
     */
    public static final int TYPE_EMAIL = 2;
    /**
     * Email where to send the notification. Use with type TYPE_EMAIL
     */
    private String email;
    /**
     * Type of notification. See static members of this class for possible values
     */
    private int notificationType;

    //No-arg constructor required
    public TaskNotificationDescriptor() {   }

    public TaskNotificationDescriptor(String email, int notificationType) {
        this.email = email;
        this.notificationType = notificationType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(int notificationType) {
        this.notificationType = notificationType;
    }
}
