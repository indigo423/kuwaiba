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
import org.neotropic.util.visual.resources.AbstractResourceFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * A factory class that builds and/or caches resources (mostly icons and backgrounds).
 * @author Johny Andres Ortega Ruiz {@literal <johny.ortega@kuwaiba.org>}
 * @author Orlando Paz {@literal <Orlando.paz@kuwaiba.org>}
 * @author Julian David Camacho Erazo {@literal <julian.camacho@kuwaiba.org>}
 */
@Service
public class ResourceFactory extends AbstractResourceFactory {
    /**
     * Context path of Kuwaiba
     */
    @Value("${server.servlet.context-path}")
    private String contextPath;
    /**
     * Endpoint for large icons
     */
    private final String iconSourceEndpoint = "/v2.1.1/core/mem/icons/large/%s";
    /**
     * Endpoint for small icons
     */
    private final String smallIconSourceEndpoint = "/v2.1.1/core/mem/icons/small/%s";
    /**
     * Endpoint for relationship icons
     */
    private final String relationshipSourceEndpoint = "/v2.1.1/core/mem/icons/relationships/%s/%s/%s";
    
    public ResourceFactory() {}
    
    /**
    * Returns the URI for the large icon of a class with the given name.
    * 
    * @param className the name of the class
    * @return the URI of the large icon
    */
    public String getClassIconUri(String className){
        return contextPath + String.format(iconSourceEndpoint, className) ;
    }

    /**
    * Returns the URI for the small icon of a class with the given name.
    * 
    * @param className the name of the class
    * @return the URI of the small icon
    */
    public String getClassSmallIconUri(String className){
        return contextPath + String.format(smallIconSourceEndpoint, className) ;
    }

    /**
    * Returns the URI for a relationship icon with the given color, width, and height.
    * 
    * @param color the color of the icon (as an RGB value)
    * @param width the width of the icon
    * @param height the height of the icon
    * @return the URI of the relationship icon
    */
    public String getRelationshipIconUri(int color, int width, int height){
        return contextPath + String.format(relationshipSourceEndpoint, color, width, height);
    }

    /**
    * Returns the URI for the large icon of a class with the given name.
    * 
    * @param className the name of the class
    * @return the URI of the large icon
    */
    @Override
    public String getClassIcon(String className) {
        return getClassIconUri(className);
    }

    /**
    * Returns the URI for the small icon of a class with the given name.
    * 
    * @param className the name of the class
    * @return the URI of the small icon
    */
    @Override
    public String getClassSmallIcon(String className) {
        return getClassSmallIconUri(className);
    }

    /**
    * Returns the URI for a relationship icon with the given color, width, and height.
    * 
    * @param color the color of the icon (as a Color object)
    * @param width the width of the icon
    * @param height the height of the icon
    * @return the URI of the relationship icon
    */
    public String getRelationshipIcon(Color color, int width, int height) {
        return getRelationshipIconUri(color.getRGB(), width, height);
    }
}