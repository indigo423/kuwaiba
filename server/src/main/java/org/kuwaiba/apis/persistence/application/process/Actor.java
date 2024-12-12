/*
 *  Copyright 2010-2019 Neotropic SAS <contact@neotropic.co>.
 *
 *  Licensed under the EPL License, Version 1.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.eclipse.org/legal/epl-v10.html
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.kuwaiba.apis.persistence.application.process;

/**
 * An actor is someone (a person, organizational unit or external agent, like a customer) who is responsible to cary on with an activity.
 * @author Charles Edward Bedon Cortazar {@literal <charles.bedon@kuwaiba.org>}
 */
public class Actor {
    /**
     * The actor is a local user
     */
    public static int TYPE_USER = 1;
    /**
     * The actor is a local group
     */
    public static int TYPE_GROUP = 2;
    /**
     * The actor is an external agent (a customer, provider, etc)
     */
    public static int TYPE_EXTERNAL = 3;
    /**
     * The id of the actor. If it is an external agent, this value will be -1
     */
    private long id;
    /**
     * Alias used to identify the actor. Most of the times, it will be the name of the user/group
     */
    private String name;
    /**
     * Actor type. See TYPE_* for valid values
     */
    private int type;

    public Actor(long id, String name, int type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
