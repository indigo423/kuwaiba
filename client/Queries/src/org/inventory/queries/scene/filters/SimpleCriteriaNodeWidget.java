/*
 *  Copyright 2010-2020 Neotropic SAS <contact@neotropic.co>.
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

package org.inventory.queries.scene.filters;

import javax.swing.JComboBox;
import org.inventory.communications.core.queries.LocalTransientQuery;
import org.inventory.core.visual.decorators.ColorSchemeFactory;
import org.inventory.queries.scene.QueryEditorNodeWidget;
import org.inventory.queries.scene.QueryEditorScene;

/**
 * This class represents a simple searching criteria, this is, related to a simple data type.
 * Subclasses should provide proper filters depending on the data type (numeric, dates, booleans, etc)
 * @author Charles Edward Bedon Cortazar {@literal <charles.bedon@kuwaiba.org>}
 */
public abstract class SimpleCriteriaNodeWidget extends QueryEditorNodeWidget {

    protected JComboBox condition;

    public SimpleCriteriaNodeWidget(QueryEditorScene scene) {
        super(scene,ColorSchemeFactory.getBlueScheme());
    }

    public LocalTransientQuery.Criteria getCondition() {
        return (LocalTransientQuery.Criteria)condition.getSelectedItem();
    }

    public void setCondition(LocalTransientQuery.Criteria condition){
        this.condition.setSelectedItem(condition);
    }

    public abstract Object getValue();
}