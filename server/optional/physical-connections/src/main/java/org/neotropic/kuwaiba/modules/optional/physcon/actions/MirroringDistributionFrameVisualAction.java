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
package org.neotropic.kuwaiba.modules.optional.physcon.actions;

import org.neotropic.kuwaiba.core.apis.persistence.util.Constants;
import org.springframework.stereotype.Component;

/**
 * Action to do mirroring in distribution frames.
 * @author Johny Andres Ortega Ruiz {@literal <johny.ortega@kuwaiba.org>}
 */
@Component
public class MirroringDistributionFrameVisualAction extends ManagePortMirroringVisualAction {
    
    @Override
    public String appliesTo() {
        return Constants.CLASS_GENERICDISTRIBUTIONFRAME;
    }
}
