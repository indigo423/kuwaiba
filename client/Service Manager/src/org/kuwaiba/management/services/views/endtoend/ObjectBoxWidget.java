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
package org.kuwaiba.management.services.views.endtoend;

import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.border.Border;
import org.inventory.communications.core.LocalObjectLight;
import org.inventory.core.visual.scene.SelectableNodeWidget;
import org.netbeans.api.visual.layout.LayoutFactory;
import org.netbeans.api.visual.model.ObjectState;
import org.netbeans.api.visual.widget.LabelWidget;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;

/**
 * Represents a node in the Graphical Physical Path view
 * @author Charles Edward Bedon Cortazar {@literal <charles.bedon@kuwaiba.org>}
 */
public class ObjectBoxWidget extends SelectableNodeWidget {
    public static Color[] colorPalette = new Color[]{Color.PINK, Color.LIGHT_GRAY, Color.ORANGE,
                                                    new Color(240, 218, 0), new Color(190, 240, 137), new Color(255, 164, 133),
                                                    new Color(25, 160, 255), new Color(238, 167, 244), new Color(203, 228, 138),
                                                    new Color(255, 102, 245), new Color(217, 181, 255), new Color(255, 114, 195),
                                                    new Color(166, 243, 66)};
    private static Border emptyBorder = BorderFactory.createEmptyBorder(10, 10 ,10 , 10);
    private Color originalColor;
    private LabelWidget labelWidget;
    private Widget childrenWidget;
    
    public ObjectBoxWidget(Scene scene, LocalObjectLight object) {
        super(scene, object);
        setOpaque(true);
        setLayout(LayoutFactory.createVerticalFlowLayout());
        this.labelWidget = new LabelWidget(scene, object.toString());
        this.labelWidget.setBorder(emptyBorder);
        addChild(labelWidget);
        this.childrenWidget = new Widget(scene);
        this.childrenWidget.setLayout(LayoutFactory.createVerticalFlowLayout());
        this.childrenWidget.setBorder(emptyBorder);
        addChild(childrenWidget);
    }
    public ObjectBoxWidget(Scene scene, LocalObjectLight object, Color originalColor) {
        this (scene, object);
        this.originalColor = originalColor;
        setBackground(originalColor);
    }
    
    
    public void addBox(Widget child){
        childrenWidget.addChild(child);
    }
    
    @Override
    public void notifyStateChanged (ObjectState previousState, ObjectState state) {
        if (state.isSelected()) {
            setBackground(Color.DARK_GRAY);
            labelWidget.setForeground(Color.WHITE);
        }
        if (previousState.isSelected()) {
            setBackground(originalColor == null ? Color.WHITE : originalColor);
            labelWidget.setForeground(Color.BLACK);
        }
    }
}
