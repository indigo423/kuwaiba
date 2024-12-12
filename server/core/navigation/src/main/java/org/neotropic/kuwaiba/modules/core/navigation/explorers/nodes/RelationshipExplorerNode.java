/*
 *  Copyright 2010-2024 Neotropic SAS <contact@neotropic.co>.
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
package org.neotropic.kuwaiba.modules.core.navigation.explorers.nodes;

import lombok.Data;
import org.neotropic.kuwaiba.core.apis.persistence.business.BusinessObjectLight;

import java.util.List;

/**
 * A node that represents the business object and his relationships
 * @author Mauricio Ruiz Beltrán {@literal <mauricio.ruiz@kuwaiba.org>}
 */
@Data
public class RelationshipExplorerNode {

    private String uniqueId;

    private List<BusinessObjectLight> targetObjects;

    public enum RelationshipExplorerNodeType {
        RELATIONSHIP, BUSINESS_OBJECT
    } 
    
    private BusinessObjectLight businessObject;
    
    private final RelationshipExplorerNodeType type;
    
    private String relationship;

    private BusinessObjectLight relationshipSource;

    private BusinessObjectLight relationshipTarget;
    
    public RelationshipExplorerNode(BusinessObjectLight businessObject) {
        type = RelationshipExplorerNodeType.BUSINESS_OBJECT;
        this.businessObject = businessObject;
    }

    public RelationshipExplorerNode(String relationship, BusinessObjectLight relationshipSource) {
        type = RelationshipExplorerNodeType.RELATIONSHIP;
        this.relationship = relationship;
        this.relationshipSource = relationshipSource;
    }

    public RelationshipExplorerNode(String relationship, BusinessObjectLight relationshipSource, BusinessObjectLight relationshipTarget) {
        type = RelationshipExplorerNodeType.RELATIONSHIP;
        this.relationship = relationship;
        this.relationshipSource = relationshipSource;
        this.relationshipTarget = relationshipTarget;
    }

    @Override
    public String toString() {
        if (type.equals(RelationshipExplorerNodeType.BUSINESS_OBJECT))
            return getBusinessObject().toString();
        else if (type.equals(RelationshipExplorerNodeType.RELATIONSHIP))
            return getRelationship();
        else
         return super.toString();
    }
}