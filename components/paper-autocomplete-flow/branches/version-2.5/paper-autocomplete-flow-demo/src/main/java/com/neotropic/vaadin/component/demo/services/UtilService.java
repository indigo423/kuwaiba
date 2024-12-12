/*
 *  Copyright 2010-2020 Neotropic SAS <contact@neotropic.co>.
 *  
 *  Licensed under the Apache License, Version 2.0 (the "License"); 
 *  you may not use this file except in compliance with the License. 
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software 
 *  distributed under the License is distributed on an "AS IS" BASIS, 
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 *  See the License for the specific language governing permissions and 
 *  limitations under the License.
 */
package com.neotropic.vaadin.component.demo.services;

import elemental.json.Json;
import elemental.json.JsonObject;
import java.io.IOException;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

/**
 *
 * @author Johny Andres Ortega Ruiz {@literal <johny.ortega@kuwaiba.org>}
 */
@Service
public class UtilService {
    public JsonObject getResourceAsJsonObject(String name) throws IOException {
        return Json.parse(
            new String(
                IOUtils.toByteArray(
                    getClass().getClassLoader().getResourceAsStream(
                        name
                    )
                ), 
                "UTF-8"
            )
        );
    }
}
