/*
 *  Copyright 2010-2024 Neotropic SAS <contact@neotropic.co>.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       https://apache.org/licenses/LICENSE-2.0.txt
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.neotropic.kuwaiba.modules.commercial.processman.forms.reference.vaadin.miniapps;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Label;
import java.util.Properties;
import org.neotropic.kuwaiba.core.apis.integration.miniapps.AbstractMiniApplication;

/**
 * Simple embedded mini application.
 * @author Johny Andres Ortega Ruiz {@literal <johny.ortega@kuwaiba.org>}
 */
public class MiniAppEmbeddedTest extends AbstractMiniApplication<Component, Component> {

    public MiniAppEmbeddedTest(Properties inputParameters) {
        super(inputParameters);
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public Component launchDetached() {
        return null;
    }

    @Override
    public Component launchEmbedded() {
        return new Label("Embedded Mini Application Test");
    }

    @Override
    public int getType() {
        return AbstractMiniApplication.TYPE_WEB;
    }
    
}
