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
 * This is the basic metadata about a file attached to an inventory object
 * @author Charles Edward Bedon Cortazar {@literal <charles.bedon@kuwaiba.org>}
 */
public class FileObjectLight {
        /**
     * File object id
     */
    protected long fileOjectId;
    /**
    * The name of the file
    */
    protected String name;
    /**
    * Tags associated to the binary file that can be used to index it or find it in searches
    */
    protected String tags;
    /**
     * Creation date
     */
    protected long creationDate;

    public FileObjectLight(long fileOjectId, String name, String tags, long creationDate) {
        this.fileOjectId = fileOjectId;
        this.name = name;
        this.tags = tags;
        this.creationDate = creationDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getFileOjectId() {
        return fileOjectId;
    }

    public void setFileOjectId(long fileOjectId) {
        this.fileOjectId = fileOjectId;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public long getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(long creationDate) {
        this.creationDate = creationDate;
    }
}
