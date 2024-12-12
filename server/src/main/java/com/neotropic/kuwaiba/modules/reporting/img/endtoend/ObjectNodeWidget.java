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
package com.neotropic.kuwaiba.modules.reporting.img.endtoend;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import org.kuwaiba.interfaces.ws.toserialize.business.RemoteObjectLight;
import org.netbeans.api.visual.laf.LookFeel;
import org.netbeans.api.visual.layout.LayoutFactory;
import org.netbeans.api.visual.model.ObjectState;
import org.netbeans.api.visual.widget.ImageWidget;
import org.netbeans.api.visual.widget.LabelWidget;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;
import org.openide.util.Lookup;
import org.openide.util.lookup.Lookups;

/**
 *
 * @author Charles Edward Bedon Cortazar {@literal charles.bedon@kuwaiba.org}
 */
public class ObjectNodeWidget extends Widget {
    /**
     * Default widget size
     */
    public static final Dimension DEFAULT_DIMENSION = new Dimension(24, 24);
    /**
     * The label
     */
    private LabelWidget labelWidget;
    /**
     * An icon or a colored square
     */
    private Widget nodeWidget;
    /**
     * Widgets in high-contrast mode are opaque, with a dark background and light-colored letter
     */
    private boolean highContrast;
    
    private Lookup lookup;
    
    /**
     * Default constructor
     * @param scene Scene this widget belongs to
     * @param businessObject object represented by this widget
     */
    public ObjectNodeWidget(Scene scene, RemoteObjectLight businessObject) {
        this(scene, businessObject, Color.ORANGE);
    }
    
    public ObjectNodeWidget(Scene scene, RemoteObjectLight businessObject, Color iconColor) {
        super(scene);
        LookFeel lookFeel = scene.getLookFeel();
        
        this.nodeWidget = new Widget(scene);
        this.nodeWidget.setPreferredSize(DEFAULT_DIMENSION);
        this.nodeWidget.setBackground(iconColor);
        this.nodeWidget.setOpaque(true);
        
        this.labelWidget = new LabelWidget(scene, businessObject.toString());
        this.labelWidget.setBorder(getScene().getLookFeel().getBorder(getState()));
        this.labelWidget.setOpaque(true);
        
        //Centers the text, and makes the widgets to stack one onto another
        setLayout(LayoutFactory.createVerticalFlowLayout(LayoutFactory.SerialAlignment.CENTER, 1 - lookFeel.getMargin()));
        addChild(nodeWidget);
        addChild(labelWidget);
        
        highContrast = false;
        
        setState (ObjectState.createNormal());
        
        lookup = Lookups.fixed(businessObject);
    }
    
    public ObjectNodeWidget(Scene scene, RemoteObjectLight businessObject, Image icon) {
        super(scene);
        LookFeel lookFeel = scene.getLookFeel();
        
        this.nodeWidget = new ImageWidget(scene, icon);
        
        this.labelWidget = new LabelWidget(scene);
        this.labelWidget.setLabel(businessObject.toString());
        
        //Centers the text, and makes the widgets to stack one onto another
        setLayout(LayoutFactory.createVerticalFlowLayout(LayoutFactory.SerialAlignment.CENTER, 1 - lookFeel.getMargin()));
        addChild(nodeWidget);
        addChild(labelWidget);
        
        setState (ObjectState.createNormal());
        
        lookup = Lookups.fixed(businessObject);
    }
    
    public LabelWidget getLabelWidget() {
        return labelWidget;
    }
    
    public Widget getNodeWidget() {
        return nodeWidget;
    }

    public boolean isHighContrast() {
        return highContrast;
    }

    public void setHighContrast(boolean highContrast) {
        this.highContrast = highContrast;
        notifyStateChanged(getState(), getState());
    }
    
    /**
     * Implements the widget-state specific look of the widget.
     * @param previousState the previous state
     * @param state the new state
     */
    @Override
    public void notifyStateChanged (ObjectState previousState, ObjectState state) {
        if (!highContrast) {
            labelWidget.setForeground (getScene().getLookFeel().getForeground (state));
            labelWidget.setBackground(getScene().getLookFeel().getBackground(state));
            labelWidget.setBorder(getScene().getLookFeel().getBorder (state));
        } else {
            labelWidget.setForeground (HighContrastLookAndFeel.getInstance().getForeground (state));
            labelWidget.setBackground(HighContrastLookAndFeel.getInstance().getBackground(state));
            labelWidget.setBorder(HighContrastLookAndFeel.getInstance().getBorder (state));
        }
    }  

    @Override
    public String toString() {
        return "ObjectNodeWidget{" + labelWidget.getLabel() + '}';
    }
    
    @Override
    public Lookup getLookup() {
        return lookup;
    }
}
