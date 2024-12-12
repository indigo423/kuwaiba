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

package org.neotropic.kuwaiba.visualization.api.resources;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import javax.imageio.ImageIO;
import org.springframework.stereotype.Service;

/**
 * A Service class that builds and/or caches default icons.
 * @author Julian David Camacho Erazo {@literal <julian.camacho@kuwaiba.org>}
 */
@Service
public class IconDefaultService {
    /**
     * Default large icons cache
     */
    private final HashMap<ClassIconKey, byte[]> defaultIcons;
    /**
     * Default small icons cache
     */
    private final HashMap<ClassSmallIconKey, byte[]> smallDefaultIcons;
    /**
     * Default relationship icons cache
     */
    private final HashMap<RelationshipIconKey, byte[]> defaultRelationships;
    /**
     * Default large class icons cache
     */
    private final HashMap<String, byte[]> classDefaultIcons;
    /**
     * Default small class icons cache
     */
    private final HashMap<String, byte[]> smallClassDefaultIcons;
    /**
     * Default relationship color cache
     */
    private final HashMap<Integer, byte[]> defaultColorRelationships;
    /**
     * Default icon width (used in views)
     */
    public static final int DEFAULT_ICON_WIDTH = 24;
    /**
     * Default icon height (used in views)
     */
    public static final int DEFAULT_ICON_HEIGHT = 24;
    /**
     * Default icon width (used in navigation trees)
     */
    public static final int DEFAULT_SMALL_ICON_WIDTH = 10;
    /**
     * Default icon height (used in navigation trees)
     */
    public static final int DEFAULT_SMALL_ICON_HEIGHT = 10;
    
    
    public IconDefaultService(){
        defaultIcons = new HashMap();
        smallDefaultIcons = new HashMap();
        defaultRelationships = new HashMap();
        classDefaultIcons = new HashMap();
        smallClassDefaultIcons = new HashMap();
        defaultColorRelationships = new HashMap();
    }
    
    /**
     * Builds an icon as a byte array
     * @param className  The className of the icon
     * @param color The color of the icon
     * @return The default icon as a byte array
     */
    public byte[] getClassIconDefault(String className, int color) {
        byte[] icon = null;
        ClassIconKey classIcon = null;
        
        if(classDefaultIcons.containsKey(className)){
            icon = smallClassDefaultIcons.get(className); 
            
            classIcon = new ClassIconKey(className, color,icon);
            ClassIconKey oldKey = classIcon.getOldKey(defaultIcons);          
            
            if(oldKey != null){
                classDefaultIcons.remove(className);
                defaultIcons.remove(oldKey);
                
                icon = getIcon(new Color(color), DEFAULT_ICON_WIDTH, DEFAULT_ICON_HEIGHT);
                classIcon.setIcon(icon);
                
                classDefaultIcons.put(className, icon);
                defaultIcons.put(classIcon,icon);
            }
            
            return classDefaultIcons.get(className);
        }
        
        icon = getIcon(new Color(color), DEFAULT_ICON_WIDTH, DEFAULT_ICON_HEIGHT);
        classIcon = new ClassIconKey(className, color,icon);
        classDefaultIcons.put(className, icon);
        defaultIcons.put(classIcon, icon);
      
        return icon;
    }
    
    /**
     * Builds an icon as a byte array
     * @return The default icon as a byte array
     */
    public byte[] getClassIconDefault() {
        
        if(classDefaultIcons.containsKey("default")){
            return classDefaultIcons.get("default");
        }
        
        byte [] icon = getIcon(Color.BLACK, DEFAULT_ICON_WIDTH, DEFAULT_ICON_HEIGHT);
        ClassIconKey classIcon = new ClassIconKey("default", Color.BLACK.getRGB(),icon);
        classDefaultIcons.put("default", icon);
        defaultIcons.put(classIcon, icon);
        
        return icon;
    }
    
     /**
     * Builds a small icon as a byte array
     * @param className The className of the small icon
     * @param color The color of the small Icon
     * @return The small default icon as a byte array
     */
    public byte[] getClassSmallIconDefault(String className, int color) {
        byte[] icon = null;
        ClassSmallIconKey classSmallIcon = null;
        
        if(smallClassDefaultIcons.containsKey(className)){
            icon = smallClassDefaultIcons.get(className); 
            
            classSmallIcon = new ClassSmallIconKey(className, color,icon);
            ClassSmallIconKey oldKey = classSmallIcon.getOldKey(smallDefaultIcons);          
            
            if(oldKey != null){
                smallClassDefaultIcons.remove(className);
                smallDefaultIcons.remove(oldKey);
                
                icon = getIcon(new Color(color), DEFAULT_SMALL_ICON_WIDTH, DEFAULT_SMALL_ICON_HEIGHT);
                classSmallIcon.setIcon(icon);
                
                smallClassDefaultIcons.put(className, icon);
                smallDefaultIcons.put(classSmallIcon,icon);
            }
            
            return smallClassDefaultIcons.get(className);
        }
        
        icon = getIcon(new Color(color), DEFAULT_SMALL_ICON_WIDTH, DEFAULT_SMALL_ICON_HEIGHT);
        classSmallIcon = new ClassSmallIconKey(className, color,icon);
        smallClassDefaultIcons.put(className, icon);
        smallDefaultIcons.put(classSmallIcon, icon);
      
        return icon;
    }
    
    /**
     * Builds a small icon as a byte array
     * @return The small default icon as a byte array
     */
    public byte[] getClassSmallIconDefault() { 
        
        if(smallClassDefaultIcons.containsKey("default")){
            return smallClassDefaultIcons.get("default");
        }
        
        byte [] icon = getIcon(Color.DARK_GRAY, DEFAULT_SMALL_ICON_WIDTH, DEFAULT_SMALL_ICON_HEIGHT);
        ClassSmallIconKey classSmallIcon = new ClassSmallIconKey("default", Color.DARK_GRAY.getRGB(),icon);
        smallClassDefaultIcons.put("default", icon);
        smallDefaultIcons.put(classSmallIcon, icon);
        
        return icon;
    }
   
    /**
      * Builds an icon resource
      * @param color The color of the icon
      * @param width The width of the icon
      * @param height The height of the icon
      * @return The icon as a byte array
      */
    public byte[] getRelationshipIconDefault(int color, int width, int height) {
        byte[] icon = null;
        RelationshipIconKey relationshipIconKey = null;
        
        if(defaultColorRelationships.containsKey(color)){
            icon = defaultColorRelationships.get(color); 
            
            relationshipIconKey = new RelationshipIconKey(color, width, height, icon);
            RelationshipIconKey oldKey = relationshipIconKey.getOldKey(defaultRelationships);          
            
            if(oldKey != null){
                defaultColorRelationships.remove(color);
                defaultRelationships.remove(oldKey);
                
                icon = getIcon(new Color(color), width, height);
                relationshipIconKey.setIcon(icon);
                
                defaultColorRelationships.put(color, icon);
                defaultRelationships.put(relationshipIconKey,icon);
            }
            
            return defaultColorRelationships.get(color);
        }
        
        icon = getIcon(new Color(color), width, height);
        relationshipIconKey = new RelationshipIconKey(color, width, height, icon);
        defaultColorRelationships.put(color, icon);
        defaultRelationships.put(relationshipIconKey,icon);
      
        return icon;
    }
   
    /**
     * Creates (or retrieves a cached version) of a squared colored icon
     * @param color The color of the icon
     * @param width The width of the icon
     * @param height The height of the icon
     * @return The icon as a byte array
     */
    private byte[] getIcon(Color color, int width, int height) {
        try {
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D graphics = image.createGraphics();
            graphics.setColor(color);
            graphics.fillRect(0, 0, width, height);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "png", baos);
            return baos.toByteArray();
        } catch (IOException ex) {
            return null;
        }
    }  
    
    
    public class ClassIconKey{
        private String className;
        private int color;
        private byte[] icon;
        
        public ClassIconKey(String className, int color, byte[] icon){
            this.className = className;
            this.color = color;
            this.icon = icon;
        }
        
        public ClassIconKey getOldKey(HashMap<ClassIconKey, byte[]> defaultIcons) {
            ClassIconKey[] response = {null};
            defaultIcons.forEach((key, value) -> {
                if(this.equals(key))
                    response[0] = key;
            });
           return response[0];
        }
        
        @Override
        public boolean equals(Object obj) {
            if(obj == null || !(obj instanceof ClassIconKey))
                return false;
            
            ClassIconKey objKey = (ClassIconKey) obj;
            return this.className.equals(objKey.className) && this.color != objKey.color;
        }
        
        public byte[] getIcon(){
            return this.icon;
        }
        
        public void setIcon(byte[] icon){
            this.icon = icon;
        } 
    }
    
    public class ClassSmallIconKey{
        private String className;
        private int color;
        private byte[] icon;
        
        public ClassSmallIconKey(String className, int color, byte[] icon){
            this.className = className;
            this.color = color;
            this.icon = icon;
        }
        
        public ClassSmallIconKey getOldKey(HashMap<ClassSmallIconKey, byte[]> smallDefaultIcons) {
            ClassSmallIconKey[] response = {null};
            smallDefaultIcons.forEach((key, value) -> {
                if(this.equals(key))
                    response[0] = key;
            });
           return response[0];
        }
        
        @Override
        public boolean equals(Object obj) {
            if(obj == null || !(obj instanceof ClassSmallIconKey))
                return false;
            
            ClassSmallIconKey objKey = (ClassSmallIconKey) obj;
            return this.className.equals(objKey.className) && this.color != objKey.color;
        }
        
        public byte[] getIcon(){
            return this.icon;
        }
        
        public void setIcon(byte[] icon){
            this.icon = icon;
        }
    }
    
    public class RelationshipIconKey{
        private int color;
        private int width;
        private int height;
        private byte[] icon;
        
        public RelationshipIconKey(int color, int width, int height, byte[] icon){
            this.color = color;
            this.width = width;
            this.height = height;
            this.icon = icon;
        }
        
        public RelationshipIconKey getOldKey(HashMap<RelationshipIconKey, byte[]> defaultRelationships) {
            RelationshipIconKey[] response = {null};
            defaultRelationships.forEach((key, value) -> {
                if(this.equals(key))
                    response[0] = key;
            });
           return response[0];
        }
        
        @Override
        public boolean equals(Object obj) {
            if(obj == null || !(obj instanceof RelationshipIconKey))
                return false;
            
            RelationshipIconKey objKey = (RelationshipIconKey) obj;
            return (this.color == objKey.color) && 
                    (this.width != objKey.width || this.height != objKey.height);
        }
        
        public byte[] getIcon(){
            return this.icon;
        }
        
        public void setIcon(byte[] icon){
            this.icon = icon;
        }
    }

}
