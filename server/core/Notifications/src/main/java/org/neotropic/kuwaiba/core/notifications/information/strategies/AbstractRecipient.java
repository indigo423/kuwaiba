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
package org.neotropic.kuwaiba.core.notifications.information.strategies;

import java.util.Map;

/**
 * Generic interface for the different types of notification receivers
 * @author Lina Sofia Cardona Martinez {@literal <lina.cardona@kuwaiba.org>}
 */
public interface AbstractRecipient {
    
    /**
     * Gets the attributes of the specific receiver
     * @return a map with the receiver attributes
     */
    public Map<String, Object> getRecipient();
}
