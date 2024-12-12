/*
 *  Copyright 2011 Charles Edward Bedon Cortazar <charles.bedon@zoho.com>.
 * 
 *   Licensed under the EPL License, Version 1.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *        http://www.eclipse.org/legal/epl-v10.html
 * 
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 * 
 */

package org.inventory.core.services.factories;

import org.inventory.core.services.api.LocalObjectListItem;
import org.openide.util.Lookup;

/**
 * A factory used to create dummy objects, like null ObjectListItems
 * @author Charles Edward Bedon Cortazar <charles.bedon@zoho.com>
 */
public abstract class ObjectFactory {
    public static LocalObjectListItem createNullItem(){
        LocalObjectListItem nullItem = Lookup.getDefault().lookup(LocalObjectListItem.class);
        nullItem.setDisplayName("None");
        return nullItem;
    }
}