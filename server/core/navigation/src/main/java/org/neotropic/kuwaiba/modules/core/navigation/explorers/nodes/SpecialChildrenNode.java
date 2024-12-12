/*
 * Copyright 2010-2024 Neotropic SAS <contact@neotropic.co>.
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
package org.neotropic.kuwaiba.modules.core.navigation.explorers.nodes;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.neotropic.kuwaiba.core.apis.persistence.business.BusinessObjectLight;

import java.util.UUID;

/**
 * A node that represents a special children.
 *
 * @author Mauricio Ruiz Beltrán {@literal <mauricio.ruiz@kuwaiba.org>}
 */
@Setter
@Getter
@Data
public class SpecialChildrenNode {

    private BusinessObjectLight businessObject;
    private String uniqueId;

    public SpecialChildrenNode(BusinessObjectLight businessObject) {
        this.businessObject = businessObject;
        this.uniqueId = UUID.randomUUID().toString();
    }
}