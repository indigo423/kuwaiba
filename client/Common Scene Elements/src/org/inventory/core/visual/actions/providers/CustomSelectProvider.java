/*
 *  Copyright 2010-2020 Neotropic SAS <contact@neotropic.co>.
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
package org.inventory.core.visual.actions.providers;

import java.awt.Point;
import java.util.Collections;
import org.inventory.core.visual.scene.AbstractScene;
import org.netbeans.api.visual.action.SelectProvider;
import org.netbeans.api.visual.widget.Widget;

/**
 * This is a custom provider that implements a simple widget select action AND updates the scene lookup
 * This is useful is you need to know that a widget position changed to check if the scene should saved, for example
 * @author Charles Edward Bedon Cortazar {@literal <charles.bedon@kuwaiba.org>}
 */
public class CustomSelectProvider implements SelectProvider {

    private AbstractScene scene;

    public CustomSelectProvider(AbstractScene scene) {
        this.scene = scene;
    }

    @Override
    public boolean isAimingAllowed (Widget widget, Point localLocation, boolean invertSelection) {
        return false;
    }

    @Override
    public boolean isSelectionAllowed (Widget widget, Point localLocation, boolean invertSelection) {
        return scene.findObject (widget) != null;
    }

    @Override
    public void select (Widget widget, Point localLocation, boolean invertSelection) {
        Object object = scene.findObject (widget);
        scene.setFocusedObject (object);
        
        scene.userSelectionSuggested (Collections.emptySet (), invertSelection);
        
        if (object != null) {
            if (! invertSelection  &&  scene.getSelectedObjects ().contains (object))
                return;
            
            scene.userSelectionSuggested (Collections.singleton (object), invertSelection);
        }
        
        //Updates the lookup so that other modules are aware of this selection
        ((AbstractScene.SceneLookup)scene.getLookup()).updateLookup(widget.getLookup());
    }    
}
