/*
 *  Copyright 2010-2017 Neotropic SAS <contact@neotropic.co>.
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
package org.kuwaiba.ws.toserialize.application;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import org.kuwaiba.apis.persistence.application.FavoritesFolder;

/**
 * Wrapper for entity class favoritesFolder
 * @author Johny Andres Ortega Ruiz <johny.ortega@kuwaiba.org>
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class RemoteFavoritesFolder {
    
    private long id;
    private String name;
    
    public RemoteFavoritesFolder() {
    }
    
    public RemoteFavoritesFolder(long id, String name) {
        this.id = id;
        this.name = name;
    }
    
    public RemoteFavoritesFolder(FavoritesFolder bookmark) {
        this.id = bookmark.getId();
        this.name = bookmark.getName();
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
}
