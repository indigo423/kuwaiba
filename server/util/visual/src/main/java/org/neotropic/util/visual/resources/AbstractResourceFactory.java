/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.neotropic.util.visual.resources;

/**
 * Abstract class that defines the main methods to obtain the URL of different resources such as images, icons etc.
 * @author Orlando Paz  {@literal <orlando.paz@kuwaiba.org>} 
 */
public abstract class AbstractResourceFactory {
    
    /**
     * Builds and caches an icon of a given class. 
     * @param className the class name of the icon will be built for
     * @return The URL of the icon
     */
    public abstract String getClassIcon(String className);
    
    /**
     * Builds and caches the small icon of the given class.
     * @param className The class name of the small icon will be built for.
     * @return The URL of the small icon
     */
    public abstract String getClassSmallIcon(String className);
}
