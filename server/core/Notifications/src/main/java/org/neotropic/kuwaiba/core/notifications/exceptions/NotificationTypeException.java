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
package org.neotropic.kuwaiba.core.notifications.exceptions;

/**
 * Exception thrown when the type of notification to be used is not known.
 * @author Lina Sofia Cardona Martinez {@literal <lina.cardona@kuwaiba.org>}
 */
public class NotificationTypeException extends NotificationException {
    public NotificationTypeException(String msg) {
        super(msg);
    }
}
