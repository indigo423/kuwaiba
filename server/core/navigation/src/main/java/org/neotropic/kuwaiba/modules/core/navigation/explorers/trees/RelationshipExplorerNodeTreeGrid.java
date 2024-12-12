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
package org.neotropic.kuwaiba.modules.core.navigation.explorers.trees;

import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.treegrid.TreeGrid;
import org.neotropic.kuwaiba.modules.core.navigation.explorers.grids.IconRelationshipCellGrid;
import org.neotropic.kuwaiba.modules.core.navigation.explorers.nodes.RelationshipExplorerNode;
import org.neotropic.kuwaiba.modules.core.navigation.explorers.provider.RelationshipExplorerNodeTreeProvider;
import org.neotropic.kuwaiba.visualization.api.resources.ResourceFactory;
import org.neotropic.util.visual.general.FormattedObjectDisplayNameSpan;
import org.neotropic.util.visual.grids.IconNameCellGrid;
import org.neotropic.util.visual.icons.ClassNameIconGenerator;

import java.util.Collection;
import java.util.List;

/**
 * This class extends TreeGrid and is used to display hierarchical data in the form of a tree.
 * It is designed to work with RelationshipExplorerNode type objects.
 * It provides functionality to create and manage a hierarchical data tree.
 *
 * @param <T> The type of RelationshipExplorerNode object to display in the TreeGrid.
 * @author Mauricio Ruiz Beltrán {@literal <mauricio.ruiz@kuwaiba.org>}
 */
public class RelationshipExplorerNodeTreeGrid<T extends RelationshipExplorerNode> extends TreeGrid<RelationshipExplorerNode> {

    /**
     * Factory to build resources from data source.
     */
    private ResourceFactory rs;
    /**
     * Provider given for relationship explorer node type navigation tree.
     */
    private RelationshipExplorerNodeTreeProvider provider;

    /**
     * Creates a new instance of RelationshipExplorerNodeTreeGrid.
     */
    public RelationshipExplorerNodeTreeGrid() {
    }

    /**
     * Creates and displays the initial data in the TreeGrid from an RelationshipExplorerNode.
     *
     * @param rs       Reference to the Resource Factory.
     * @param provider Provider given for the root node.
     * @param rootNode The root node that will be used to build the data tree.
     */
    public void createDataProvider(ResourceFactory rs,
                                   RelationshipExplorerNodeTreeProvider provider,
                                   RelationshipExplorerNode rootNode) {
        this.provider = provider;
        this.rs = rs;

        setDataProvider(provider.buildTreeDataProvider(rootNode));
        buildComponentHierarchyColumn();
        buildThemeVariants();
    }

    private void buildComponentHierarchyColumn() {
        addComponentHierarchyColumn(item -> {
            if (null == item.getType())
                return new Label(item.toString());
            else {
                switch (item.getType()) {
                    case BUSINESS_OBJECT:
                        FormattedObjectDisplayNameSpan itemName = new FormattedObjectDisplayNameSpan(
                                item.getBusinessObject(), false, false, true, false);

                        return new IconNameCellGrid(itemName, item.getBusinessObject().getClassName(), new ClassNameIconGenerator(rs));
                    case RELATIONSHIP:
                        return new IconRelationshipCellGrid(item.getRelationship(), rs);
                    default:
                        return new Label(item.toString());
                }
            }
        });
    }

    private void buildThemeVariants() {
        addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_NO_ROW_BORDERS
                , GridVariant.LUMO_COMPACT, GridVariant.LUMO_WRAP_CELL_CONTENT);
    }

    /**
     * Updates the view of a node.
     *
     * @param node The node to be updated.
     */
    public void updateChild(RelationshipExplorerNode node) {
        if (node != null && containsNode(node)) {
            getDataProvider().refreshItem(node);
            getElement().executeJs("this.clearCache()");
        }
    }

    /**
     * Checks if a given node is already contained in the list of all expanded rows.
     *
     * @param node The node to check.
     * @return True if the node is contained; otherwise, false.
     */
    public boolean containsNode(RelationshipExplorerNode node) {
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
    public RelationshipExplorerNode getNodeById(String nodeId) {
        return this.provider.getNodeById(nodeId);
    }

    /**
     * Returns a node list if they exist in the data rows.
     *
     * @param nodeId The id of the node to be found.
     * @return The node list found.
     */
    public List<RelationshipExplorerNode> getNodesById(String nodeId) {
        return this.provider.getNodesById(nodeId);
    }

    /**
     * This method collapses specific relationships within a collection of relationship explorer nodes,
     * removing those relationships that are connected to the collapsed nodes.
     *
     * @param collapsedNodes A collection of RelationshipExplorerNode items that have been collapsed.
     */
    public void collapseNodes(Collection<RelationshipExplorerNode> collapsedNodes) {
        this.provider.collapseNodes(collapsedNodes);
    }
}