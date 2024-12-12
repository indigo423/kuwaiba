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
package org.neotropic.kuwaiba.modules.core.navigation.navtree;

import org.neotropic.kuwaiba.modules.core.navigation.navtree.provider.InventoryObjectNodeTreeProvider;
import com.vaadin.flow.component.treegrid.TreeGrid;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.neotropic.kuwaiba.modules.core.navigation.navtree.nodes.InventoryObjectNode;
import org.neotropic.kuwaiba.visualization.api.resources.ResourceFactory;
import org.neotropic.util.visual.general.FormattedObjectDisplayNameSpan;
import org.neotropic.util.visual.grids.IconNameCellGrid;
import org.neotropic.util.visual.icons.ClassNameIconGenerator;

/**
 * This class extends TreeGrid and is used to display hierarchical data in the form of a tree.
 * It is designed to work with InventoryObjectNode type objects.
 * It provides functionality to create and manage a hierarchical data tree.
 * 
 * @author Mauricio Ruiz Beltrán {@literal <mauricio.ruiz@kuwaiba.org>}
 * @param <T> The type of InventoryObjectNode object to display in the TreeGrid.
 */
public class InventoryObjectNodeTreeGrid<T extends InventoryObjectNode> extends TreeGrid<InventoryObjectNode> {
    
    /**
     * Factory to build resources from data source.
     */
    private ResourceFactory rs;
    /**
     * Provider given for inventory object node type navigation tree.
     */
    private InventoryObjectNodeTreeProvider provider;
    
    /**
     * Creates a new instance of InventoryObjectNodeTreeGrid.
     */
    public InventoryObjectNodeTreeGrid() { }
    
    /**
     * Creates and displays the initial data in the TreeGrid from an InventoryObjectNode.
     * 
     * @param rs Reference to the Resource Factory.
     * @param provider Provider given for the root node.
     * @param rootNode The root node that will be used to build the data tree.
     * @param incluedSelf Include the root node as the first level of the hierarchy visible.
     */
    public void createDataProvider(ResourceFactory rs, InventoryObjectNodeTreeProvider provider, 
            InventoryObjectNode rootNode, boolean incluedSelf) {
        this.provider = provider;
        this.rs = rs;
        
        setDataProvider(provider.buildTreeDataProvider(rootNode, incluedSelf));
        buildComponentHierarchyColumn();
    }
    
    /**
     * Builds the column that allows to visualize the name of the cell and its assigned visual resource.
     */
    private void buildComponentHierarchyColumn() {
        addComponentHierarchyColumn(item -> {
            FormattedObjectDisplayNameSpan spnItemName = new FormattedObjectDisplayNameSpan(
                    item.getObject(),
                    false,
                    false,
                    true,
                    false
            );
            IconNameCellGrid node = new IconNameCellGrid(
                    spnItemName, 
                    item.getObject().getClassName(),
                    new ClassNameIconGenerator(rs)
            );
            return node;
        });
    }
    
    /**
     * Updates the view of a node.
     * 
     * @param node The node to be updated.
     */
    public void updateChild(InventoryObjectNode node) {
        if (node != null && containsNode(node)) {
            getDataProvider().refreshItem(node);
            getElement().executeJs("this.clearCache()");
        }
    }
    
    /**
     * Updates the view of a parent to which a child has been removed.
     * 
     * @param parentNode The parent node.
     * @param node The child node to be removed.
     */
    public void removeChild(InventoryObjectNode parentNode, InventoryObjectNode node) {
        if (parentNode != null && node != null) {
            if(containsNode(parentNode)) {
                this.provider.removeFromRows(node);
                getDataProvider().refreshItem(parentNode, true);
            } else 
                refreshAll();
            getElement().executeJs("this.clearCache()");
        }
    }
    
    /**
     * Updates the view of a parent to which a new child has been added.
     * 
     * @param parentNode The parent node to which the child was added.
     */
    public void addChild(InventoryObjectNode parentNode) {
        if (parentNode != null && containsNode(parentNode)) {
            getDataProvider().refreshItem(parentNode, true);
            
            if (!isExpanded(parentNode)) {
                List<InventoryObjectNode> listParents = new ArrayList<>();
                listParents.add(parentNode);
                
                Collection<InventoryObjectNode> collection = new ArrayList<>(listParents);
                this.expandRecursively(collection, 0);
            }
            
            getElement().executeJs("this.clearCache()");
        }
    }
    
    /**
     * Updates the view of a new parent to which a new child has been added.
     * 
     * @param newParentNode The new parent node to which the child was copied.
     */
    public void copyChild(InventoryObjectNode newParentNode) {
        addChild(newParentNode);
    } 
    
    /**
     * Updates the view of the parent and the new parent the child has been moved to.
     * 
     * @param parentNode The original parent node.
     * @param newParentNode The new parent node to which the child was moved.
     * @param node The child node that was moved.
     */
    public void moveChild(InventoryObjectNode parentNode, InventoryObjectNode newParentNode, InventoryObjectNode node) {
        if (parentNode != null && containsNode(parentNode)) {
            if (containsNode(newParentNode)) {
                getDataProvider().refreshItem(parentNode, true);
                getDataProvider().refreshItem(newParentNode, true);
                
                List<InventoryObjectNode> listParents = new ArrayList<>();
                listParents.add(parentNode);
                listParents.add(newParentNode);
                
                Collection<InventoryObjectNode> collection = new ArrayList<>(listParents);
                this.collapseRecursively(collection, 0);
                this.expandRecursively(collection, 0);
                
                getElement().executeJs("this.clearCache()");
            } else
                removeChild(parentNode, node);
        }
    }
    
    /**
     * Checks if a given node is already contained in the list of all expanded rows.
     * 
     * @param node The node to check.
     * @return True if the node is contained; otherwise, false.
     */
    public boolean containsNode(InventoryObjectNode node) {
        return this.provider.containsNode(node);
    }
    
    /**
     * Updates the TreeGrid view by refreshing all the elements.
     */
    public void refreshAll() {
        getDataProvider().refreshAll();
    }
    
    /**
     * Returns a node if it exists in the data rows.
     * 
     * @param nodeId The id of the node to be found.
     * @return The node found.
     */
    public InventoryObjectNode getNodeById(String nodeId) {
        return this.provider.getNodeById(nodeId);
    }
}