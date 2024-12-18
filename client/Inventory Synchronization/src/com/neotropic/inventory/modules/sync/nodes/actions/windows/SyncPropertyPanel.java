/*
 *  Copyright 2010-2020 Neotropic SAS <contact@neotropic.co>
 *
 *  Licensed under the EPL License, Version 1.0 (the "License");
 *  you may not use this file except in compliance with the License
 *  You may obtain a copy of the License at
 *
 *       http://www.eclipse.org/legal/epl-v10.html
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.inventory.navigation.navigationtree.nodes.properties;

import java.awt.BorderLayout;
import org.openide.explorer.propertysheet.PropertySheetView;
import org.openide.nodes.Node;
import org.openide.windows.Mode;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;

/**
 *
 * @author Adrian Martinez Molina {@literal <charles.bedon@kuwaiba.org>}
 */
public class SyncPropertyPanel extends TopComponent {

    private PropertySheetView editor;
    private Node node;

    public SyncPropertyPanel(Node node) {
        editor = new PropertySheetView();
        this.node = node;
        this.setDisplayName(node.getDisplayName());
        setLayout(new BorderLayout());
        add(editor);
        //This requires that CoreUI to be enable in the project
        Mode myMode = WindowManager.getDefault().findMode("properties");
        myMode.dockInto(this);
    }

    @Override
    public int getPersistenceType() {
        return TopComponent.PERSISTENCE_NEVER;
    }

    @Override
    public void componentOpened() {
        //This is important. If setNodes is called in the constructor, it won't work!
        editor.setNodes(new Node[]{ node });
    }
}
