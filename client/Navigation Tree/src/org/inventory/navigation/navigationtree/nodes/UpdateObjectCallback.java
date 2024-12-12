/*
 *  Copyright 2010-2020 Neotropic SAS <contact@neotropic.co>.
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

package org.inventory.navigation.navigationtree.nodes;

/**
 * Functional interface to be used upon an object update using the property sheet.Implementors 
 * will provide the logic to be executed when a change occurs.
 * @author Charles Edward Bedon Cortazar {@literal <charles.bedon@kuwaiba.org>}
 * @param <T> The type of the property to be updated.
 */
public interface UpdateObjectCallback<T> {
    /**
     * The logic to be executed on a change event.
     * @param objectClassName The class of the object to be updated.
     * @param id The id of the object to be updated.
     * @param propertyName The name of the property to be updated
     * @param value The new value of the property to be updated
     */
    public void executeChange(String objectClassName, String id, String propertyName, T value) throws IllegalArgumentException;
}
