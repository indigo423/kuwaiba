/*
 *  Copyright 2010 Charles Edward Bedon Cortazar <charles.bedon@zoho.com>.
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

package org.kuwaiba.entity.location;

import org.kuwaiba.core.annotations.NoSerialize;
import org.kuwaiba.entity.multiple.people.Employee;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;


/**
 * A Warehouse is a place where you store spare parts and new and obsolete equipment
 * @author Charles Edward Bedon Cortazar <charles.bedon@zoho.com>
 */
@Entity
public class Warehouse extends GenericLocation{
    @ManyToMany
    @JoinColumn(name="responsibles_id")
    @NoSerialize //Just for now
    protected List<Employee> resposibles;

    public List<Employee> getResposibles() {
        return resposibles;
    }

    public void setResposibles(List<Employee> resposibles) {
        this.resposibles = resposibles;
    }
    
}