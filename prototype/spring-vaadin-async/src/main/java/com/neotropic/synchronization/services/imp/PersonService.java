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

/*
 */
package com.neotropic.synchronization.services.imp;

import com.neotropic.synchronization.data.entitites.Person;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Dumb person entity
 * @author Hardy Ryan Chingal Martinez {@literal <ryan.chingal@kuwaiba.org>}
 */
@Service
public class PersonService {

    private static String[] firstName = { "James", "John", "Robert", "Michael", "William", "David", "Richard",
            "Charles", "Joseph", "Christopher", "Mary", "Patricia", "Linda", "Barbara", "Elizabeth", "Jennifer",
            "Maria", "Susan", "Margaret", "Dorothy", "Lisa" };

    private static String[] lastName = { "Smith", "Johnson", "Williams", "Jones", "Brown", "Davis", "Miller", "Wilson",
            "Moore", "Taylor", "Andreson", "Thomas", "Jackson", "White" };

    private final Set<Person> persons = new HashSet<>();

    public List<Person> getEmployees() {
        for (int i = 0; i < 1000; i++) {
            persons.add(generatePerson(i));
        }
        return new ArrayList<>(persons);
    }

    public int getEmployeesCount() {
        return persons.size();
    }

    private Person generatePerson(int i) {
        final Person person = new Person();

        person.setId(i);
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

        return person;
    }
}
