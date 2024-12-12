/**
 * Copyright 2010-2022 Neotropic SAS <contact@neotropic.co>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.neotropic.flow.component.olmap.style;

import com.neotropic.flow.component.olmap.OlMapType;
import elemental.json.Json;
import elemental.json.JsonObject;

/**
 * Wrap stroke style for vector features.
 *
 * @author Johny Andres Ortega Ruiz {@literal <johny.ortega@kuwaiba.org>}
 */
public class Stroke implements OlMapType<JsonObject> {

    private String color;
    private Double width;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    @Override
    public JsonObject toJsonValue() {
        JsonObject stroke = Json.createObject();
        if (getColor() != null) {
            stroke.put("color", getColor());
        }
        if (getWidth() != null) {
            stroke.put("width", getWidth());
        }
        return stroke;
    }

}
