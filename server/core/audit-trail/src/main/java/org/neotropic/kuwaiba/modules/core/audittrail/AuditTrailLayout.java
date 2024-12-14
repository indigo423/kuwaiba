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
package org.neotropic.kuwaiba.modules.core.audittrail;

import com.vaadin.flow.component.dependency.CssImport;
import org.neotropic.util.visual.general.ModuleLayout;

/**
 * The definition of the Audit Trail layout.
 * @author Mauricio Ruiz {@literal <mauricio.ruiz@kuwaiba.org>}
 */
@CssImport(value="./styles/audit-trail.css" , themeFor="vaadin-grid")
public class AuditTrailLayout extends ModuleLayout {
    @Override
    protected String getModuleId() {
       return AuditTrailModule.MODULE_ID;
    }
}