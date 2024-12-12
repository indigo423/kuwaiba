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

package com.neotropic.synchronization.services.suppliers;

import com.neotropic.synchronization.data.entitites.Person;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
/**
 * @author Hardy Ryan Chingal Martinez {@literal <ryan.chingal@kuwaiba.org>}
 * created on 05/04/2022-16:53
 */
@NoArgsConstructor
public class UserGenerator implements Supplier<List<Person>> {
    private static String[] firstName = { "James", "John", "Robert", "Michael", "William", "David", "Richard",
            "Charles", "Joseph", "Christopher", "Mary", "Patricia", "Linda", "Barbara", "Elizabeth", "Jennifer",
            "Maria", "Susan", "Margaret", "Dorothy", "Lisa" };

    private static String[] lastName = { "Smith", "Johnson", "Williams", "Jones", "Brown", "Davis", "Miller", "Wilson",
            "Moore", "Taylor", "Andreson", "Thomas", "Jackson", "White" };

    private final Set<Person> persons = new HashSet<>();

    @Override
    public List<Person> get() {
        for (int count = 0; count < 1000; count++) {
            final Person person = new Person();

            person.setId(count);
            final int firstNameIndex = (int) Math.round(Math.random() * 10000) % firstName.length;
            person.setFirstName(firstName[firstNameIndex]);
            final int lastNameIndex = (int) Math.round(Math.random() * 10000) % lastName.length;
            person.setLastName(lastName[lastNameIndex]);

            person.setEmail(
                    person.getFirstName().toLowerCase() + "." + person.getLastName().toLowerCase() + "@example.com");

            try {
                Thread.sleep(5);
            } catch (final InterruptedException e) {
                e.printStackTrace();
            }

            persons.add(person);
        }
        return new ArrayList<>(persons);
    }
}
