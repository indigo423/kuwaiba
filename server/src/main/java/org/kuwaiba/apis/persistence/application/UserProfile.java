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

package org.kuwaiba.apis.persistence.application;

import java.util.List;

/**
 * Represents the privileges a user profile
 * @author Charles Edward Bedon Cortazar {@literal <charles.bedon@kuwaiba.org>}
 */
public class UserProfile extends UserProfileLight {
   
    /**
     * User's privileges. See class Privileges for the complete list of supported privileges
     */
    private List<Privilege> privileges;
 
    public UserProfile(long id, String userName, String firstName, String lastName, 
            boolean enabled, long creationDate, int type, String email, List<Privilege> privileges) {
        super (id, userName, firstName, lastName, enabled, creationDate, type, email);
        
        this.privileges = privileges;
    }

    public List<Privilege> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(List<Privilege> privileges) {
        this.privileges = privileges;
    }
}