/*
 * Copyright 2010-2022 Neotropic SAS <contact@neotropic.co>.
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
package com.neotropic.flow.component.mxgraph;

import com.vaadin.flow.component.ComponentEvent;

/**
 *
 * @author Mauricio Ruiz Beltrán {@literal <mauricio.ruiz@kuwaiba.org>}
 */
public class MxGraphCellOnClickOverlayButton extends ComponentEvent<MxGraphCell> {
    
    private String overlayButtonId;
    private MxGraphCell resultCell;

    public String getOverlayButtonId() {
        return overlayButtonId;
    }

    public void setOverlayButtonId(String overlayButtonId) {
        this.overlayButtonId = overlayButtonId;
    }

    public MxGraphCell getResultCell() {
        return resultCell;
    }

    public void setResultCell(MxGraphCell resultCell) {
        this.resultCell = resultCell;
    }
    
    public MxGraphCellOnClickOverlayButton(MxGraphCell source, boolean fromClient, String overlayButtonId, MxGraphCell resultCell) {
        super(source, fromClient);
        this.overlayButtonId = overlayButtonId;
        this.resultCell = resultCell;
    }
}