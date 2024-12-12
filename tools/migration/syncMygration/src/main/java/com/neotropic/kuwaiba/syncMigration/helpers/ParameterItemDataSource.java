/*
 * Copyright 2022 Neotropic SAS.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.neotropic.kuwaiba.syncMigration.helpers;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

/**
 * @author Hardy Ryan Chingal Martinez {@literal <ryan.chingal@kuwaiba.org>}
 * created on 28/09/2022-15:17
 */
public class ParameterItemDataSource {
    /**
     * Data source name parameter
     */
    @Getter @Setter
    private String propertyName;
    /**
     * Data source value parameter
     */
    @Getter @Setter
    private String propertyValue;

    public ParameterItemDataSource(String propertyName, String propertyValue) {
        this.propertyName = propertyName;
        this.propertyValue = propertyValue;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + Objects.hashCode(this.propertyName);
        hash = 11 * hash + Objects.hashCode(this.propertyValue);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ParameterItemDataSource other = (ParameterItemDataSource) obj;
        if (!Objects.equals(this.propertyName, other.propertyName)) {
            return false;
        }
        return Objects.equals(this.propertyValue, other.propertyValue);
    }
}
