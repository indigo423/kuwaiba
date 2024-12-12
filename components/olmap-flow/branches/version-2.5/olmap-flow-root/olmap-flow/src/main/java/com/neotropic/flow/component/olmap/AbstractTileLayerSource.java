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
package com.neotropic.flow.component.olmap;

import elemental.json.JsonObject;

/**
 * Base class for sources providing images divided into a tile grid.
 *
 * @author Johny Andres Ortega Ruiz {@literal <johny.ortega@kuwaiba.org>}
 */
public abstract class AbstractTileLayerSource implements OlMapType<JsonObject> {

    private final TileLayerSourceType type;

    public AbstractTileLayerSource(TileLayerSourceType type) {
        this.type = type;
    }

    public TileLayerSourceType getType() {
        return type;
    }
}
