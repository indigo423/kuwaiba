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
package com.neotropic.flow.component.mxgraph;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.DomEvent;
import com.vaadin.flow.component.EventData;

/**
 * This class represents a custom event for resizing cells in the MxGraph component.
 * It is triggered when a cell in the graph is resized by the user.
 * 
 * @author Mauricio Ruiz Beltrán {@literal <mauricio.ruiz@kuwaiba.org>}
 */
@DomEvent("cell-resized")
public class MxGraphCellResizeEvent extends ComponentEvent<MxGraph> {
    /**
     * Identifier of the cell that was resized.
     */
    private String cellId;
    
    /**
     * Constructor for the MxGraphCellResizeEvent class.
     * 
     * @param source     The MxGraph component that originated the event.
     * @param fromClient Indicates whether the event was triggered from the client.
     * @param cellId     Identifier of the cell that was resized.
     */
    public MxGraphCellResizeEvent(MxGraph source, boolean fromClient, @EventData("event.detail.cellId") String cellId) {
        super(source, fromClient);
        this.cellId = cellId;
    }   

    /**
     * Gets the identifier of the cell that was resized.
     * 
     * @return The cell identifier.
     */
    public String getCellId() {
        return cellId;
    }

    /**
     * Sets the identifier of the cell that was resized.
     * 
     * @param cellId The identifier of the cell to set.
     */
    public void setCellId(String cellId) {
        this.cellId = cellId;
    }
}