/**
 * Copyright 2010-2022 Neotropic SAS <contact@neotropic.co>.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.neotropic.flow.component.olmap.demo;

import com.neotropic.flow.component.olmap.Coordinate;
import com.neotropic.flow.component.olmap.OlMap;
import com.neotropic.flow.component.olmap.TileLayerSourceOsm;
import com.neotropic.flow.component.olmap.ViewOptions;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;

/**
 * A sample OpenLayers component.
 * @author Johny Andres Ortega Ruiz {@literal <johny.ortega@kuwaiba.org>}
 */
@Route
//@PWA(name = "Vaadin Application",
//        shortName = "Vaadin App",
//        description = "This is an example Vaadin application.")
//@CssImport("./styles/shared-styles.css")
//@CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")
public class MainView extends Div {
    
    public MainView() {
        ViewOptions viewOptions = new ViewOptions(new Coordinate(-76.599934, 2.457385), 13);
        add(new OlMap(new TileLayerSourceOsm(), viewOptions));
        setSizeFull();
    }

}
