/*
 * Copyright 2010-2024 Neotropic SAS <contact@neotropic.co>.
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
package org.neotropic.kuwaiba.modules.core.navigation.navtree.provider;

import com.vaadin.flow.data.provider.hierarchy.HierarchicalDataProvider;
import java.util.List;
import org.neotropic.kuwaiba.modules.core.navigation.navtree.nodes.InventoryObjectNode;

/**
 * Provider given for inventory object node type navigation tree.
 * @author Mauricio Ruiz Beltrán {@literal <mauricio.ruiz@kuwaiba.org>}
 */
public abstract class InventoryObjectNodeTreeProvider {

    /**
     * Builds a HierarchicalDataProvider to provide hierarchical data to the TreeGrid.
     * 
     * @param rootNode The root node of the hierarchy.
     * @param incluedSelf Include he root node as the first level of the hierarchy visible.
     * @return A HierarchicalDataProvider that provides hierarchical data.
     */
    public abstract HierarchicalDataProvider buildTreeDataProvider(InventoryObjectNode rootNode, boolean incluedSelf);
    
    /**
     * Adds a node to the list of all expanded rows.
     * 
     * @param node The node to add to the list.
     */
    public abstract void addToRows(InventoryObjectNode node);
    
    /**
     * Removes a node from the list of all rows.
     * 
     * @param node The node to be removed.
     */
    public abstract void removeFromRows(InventoryObjectNode node);
    
    /**
     * Retrieves all data rows.
     * 
     * @return The list with the data rows.
     */
    public abstract List<InventoryObjectNode> getAllDataRows();
    
    /**
     * Checks if a given node is already contained in the list of all expanded rows.
     * 
     * @param node The node to check.
     * @return True if the node is contained; otherwise, false.
     */
    public abstract boolean containsNode(InventoryObjectNode node);
    
    /**
     * Returns a node if it exists in the data rows.
     * 
     * @param nodeId The id of the node to be found.
     * @return The node found.
     */
    public abstract InventoryObjectNode getNodeById(String nodeId);
}