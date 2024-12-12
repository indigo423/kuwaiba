/*
 * Copyright 2024 Neotropic SAS.
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
package org.neotropic.kuwaiba.modules.core.templateman.visual;

import org.neotropic.kuwaiba.core.apis.persistence.application.TemplateObjectLight;
import org.neotropic.kuwaiba.visualization.api.resources.ResourceFactory;

/**
 * Represents a port in template manager module TreeGrid editor for template
 * @author Lina Sofia Cardona Martinez {@literal <lina.cardona@kuwaiba.org>}
 */
public class PortTemplateNode extends TemplateNode {
    
    public PortTemplateNode(TemplateObjectLight template, ResourceFactory resourceFactory, String portName) {
        super(template, resourceFactory);
        setNode(portName);
    }
    
}
