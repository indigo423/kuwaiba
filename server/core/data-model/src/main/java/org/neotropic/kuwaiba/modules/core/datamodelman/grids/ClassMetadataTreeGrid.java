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
package org.neotropic.kuwaiba.modules.core.datamodelman.grids;

import com.vaadin.flow.component.treegrid.TreeGrid;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.neotropic.kuwaiba.core.apis.persistence.metadata.ClassMetadataLight;
import org.neotropic.kuwaiba.modules.core.datamodelman.provider.ClassMetadataTreeProvider;
import org.neotropic.kuwaiba.visualization.api.resources.ResourceFactory;
import org.neotropic.util.visual.grids.IconNameCellGrid;
import org.neotropic.util.visual.icons.ClassNameIconGenerator;

/**
 * This class extends TreeGrid and is used to display hierarchical data in the form of a tree.
 * It is designed to work with ClassMetadata type objects.
 * It provides functionality to create and manage a hierarchical data tree.
 * 
 * @author Mauricio Ruiz Beltrán {@literal <mauricio.ruiz@kuwaiba.org>}
 * @param <T> The type of ClassMetadata object to display in the TreeGrid.
 */
public class ClassMetadataTreeGrid<T> extends TreeGrid<ClassMetadataLight> {
    
    /**
     * Factory to build resources from data source.
     */
    private ResourceFactory resourceFactory;
    /**
     * Provider given for inventory object node type navigation tree.
     */
    private ClassMetadataTreeProvider provider;
    
    /**
     * Creates a new instance of ClassMetadataTreeGrid.
     */
    public ClassMetadataTreeGrid() { }
    
    /**
     * Creates and displays the initial data in the TreeGrid from an ClassMetadata.
     * 
     * @param resourceFactory Reference to the Resource Factory.
     * @param provider Provider given for the root node.
     * @param rootNode The root node that will be used to build the data tree.
     * @param incluedSelf Include the root node as the first level of the hierarchy visible.
     */
    public void createDataProvider(ResourceFactory resourceFactory, ClassMetadataTreeProvider provider,
            ClassMetadataLight rootNode, boolean incluedSelf) {
        this.provider = provider;
        this.resourceFactory = resourceFactory;
        
        setDataProvider(provider.buildTreeDataProvider(rootNode, incluedSelf));
        buildComponentHierarchyColumn();
    }
    
    /**
     * Builds the column that allows to visualize the name of the cell and its assigned visual resource.
     */
    private void buildComponentHierarchyColumn() {
        addComponentHierarchyColumn(item -> {
            return new IconNameCellGrid(
                    item.getName(), 
                    item.getName(),
                    new ClassNameIconGenerator(resourceFactory)
            );
        });
    }
    
    /**
     * Updates the view of a node.
     * 
     * @param node The node to be updated.
     */
    public void updateChild(ClassMetadataLight node) {
        if (node != null && containsNode(node)) {
            getDataProvider().refreshItem(node);
            getElement().executeJs("this.clearCache()");
        }
    }
    
    /**
     * Updates the view of a node. Taking into account the superior parent if it is contained in the tree.
     * @param parentNode The parent node.
     * @param node       The node to be updated.
     */
    public void updateChildFromParent(ClassMetadataLight parentNode, ClassMetadataLight node) {
        updateChild(node);
        if (parentNode != null && containsNode(parentNode)) {
            this.collapse(parentNode);
            this.expand(parentNode);
            getDataProvider().refreshItem(parentNode, true);
            getElement().executeJs("this.clearCache()");
        }
    }
    
    /**
     * Updates the view of a parent to which a child has been removed.
     * 
     * @param parentNode The parent node.
     * @param node       The child node to be removed.
     */
    public void removeChild(ClassMetadataLight parentNode, ClassMetadataLight node) {
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
     * @param parentNode 
     */
    public void addChild(ClassMetadataLight parentNode) {
        if (parentNode != null && containsNode(parentNode)) {
            getDataProvider().refreshItem(parentNode, true);
            if (isExpanded(parentNode)) {
                getElement().executeJs("this.clearCache()");
                
                this.collapse(parentNode);
                this.expand(parentNode);
            } else {
                List<ClassMetadataLight> listParents = new ArrayList<>();
                listParents.add(parentNode);
                
                Collection<ClassMetadataLight> collection = new ArrayList<>(listParents);
                this.expandRecursively(collection, 0);
                
                getElement().executeJs("this.clearCache()");
            }
        }
    }
    
    /**
     * Checks if a given node is already contained in the list of all expanded rows.
     * 
     * @param node The node to check.
     * @return True if the node is contained; otherwise, false.
     */
    public boolean containsNode(ClassMetadataLight node) {
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
    public ClassMetadataLight getNodeById(long nodeId) {
        return this.provider.getNodeById(nodeId);
    }
}