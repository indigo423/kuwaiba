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

import com.vaadin.flow.data.provider.hierarchy.AbstractBackEndHierarchicalDataProvider;
import com.vaadin.flow.data.provider.hierarchy.HierarchicalDataProvider;
import com.vaadin.flow.data.provider.hierarchy.HierarchicalQuery;
import org.neotropic.kuwaiba.core.apis.persistence.application.FilterDefinition;
import org.neotropic.kuwaiba.core.apis.persistence.business.BusinessObjectLight;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.InvalidArgumentException;
import org.neotropic.kuwaiba.core.i18n.TranslationService;
import org.neotropic.kuwaiba.modules.core.navigation.navtree.nodes.InventoryObjectNode;
import org.neotropic.util.visual.notifications.AbstractNotification;
import org.neotropic.util.visual.notifications.SimpleNotification;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

/**
 * Custom provider for filter definition that extends of InventoryObjectNodeTreeProvider.
 * @author Mauricio Ruiz Beltrán {@literal <mauricio.ruiz@kuwaiba.org>}
 */
public class FilteredObjectChildrenProvider extends InventoryObjectNodeTreeProvider {
    /**
     * Reference to the Translation Service.
     */
    private final TranslationService ts;
    /**
     * Saves all the data rows in which the tree has been expanded.
     */
    private final List<InventoryObjectNode> allRowsData;
    /**
     * Reference to the filter definition.
     */
    private final FilterDefinition filterDefinition;
    
    /**
     * Creates a new instance of FilteredObjectChildren.
     * @param filterDefinition Filter definition for the root node.
     * @param ts Reference to the Translation Service.
     */
    public FilteredObjectChildrenProvider(FilterDefinition filterDefinition, TranslationService ts) {
        this.ts = ts;
        this.filterDefinition = filterDefinition;
        allRowsData = new ArrayList<>();
    }
    
    /**
     * Builds a HierarchicalDataProvider to provide hierarchical data to the TreeGrid.
     * 
     * @param rootNode The root node of the hierarchy.
     * @param includedSelf Include he root node as the first level of the hierarchy visible.
     * @return A HierarchicalDataProvider that provides hierarchical data.
     */
    @Override
    public HierarchicalDataProvider buildTreeDataProvider(InventoryObjectNode rootNode, boolean includedSelf) {
        return new AbstractBackEndHierarchicalDataProvider<InventoryObjectNode, Void>() {
            @Override
            protected Stream<InventoryObjectNode> fetchChildrenFromBackEnd(HierarchicalQuery<InventoryObjectNode, Void> query) {
                InventoryObjectNode parent = query.getParent();
                List<InventoryObjectNode> listChildren = new ArrayList<>();
                try {
                    List<BusinessObjectLight> childrenQuery = new ArrayList<>();
                    childrenQuery = getFilterData(filterDefinition, rootNode, parent);

                    for (BusinessObjectLight child : childrenQuery) {
                        listChildren.add(new InventoryObjectNode(child));
                        addToRows(new InventoryObjectNode(child));
                    }
                } catch (InvalidArgumentException ex) {
                    new SimpleNotification(ts.getTranslatedString("module.general.messages.error"),
                            ex.getMessage(), AbstractNotification.NotificationType.ERROR, ts).open();
                }
                return listChildren.stream().skip(query.getOffset()).limit(query.getLimit());
            }

            @Override

            public int getChildCount(HierarchicalQuery<InventoryObjectNode, Void> query) {
                try {
                    InventoryObjectNode parent = query.getParent();

                    if (filterDefinition != null)
                        return getFilterData(filterDefinition, rootNode, parent).size();
                } catch (InvalidArgumentException ex) {
                    new SimpleNotification(ts.getTranslatedString("module.general.messages.error"),
                            ex.getMessage(), AbstractNotification.NotificationType.ERROR, ts).open();
                }
                return 0;
            }

            @Override
            public boolean hasChildren(InventoryObjectNode node) {
                return false;
            }
        };
    }

    /**
     * Adds a node to the list of all expanded rows.
     * 
     * @param node The node to add to the list.
     */
    @Override
    public void addToRows(InventoryObjectNode node) {
        if(!containsNode(node))
            allRowsData.add(node);
    }
    
    /**
     * Removes a node from the list of all rows.
     * 
     * @param node The node to be removed.
     */
    @Override
    public void removeFromRows(InventoryObjectNode node) {
        if(!containsNode(node))
            allRowsData.remove(node);
    }

    /**
     * Retrieves all data rows.
     * 
     * @return The list with the data rows.
     */
    @Override
    public List<InventoryObjectNode> getAllDataRows() {
        return allRowsData == null ? new ArrayList<>() : allRowsData;
    }

    /**
     * Checks if a given node is already contained in the list of all expanded rows.
     * 
     * @param node The node to check.
     * @return True if the node is contained; otherwise, false.
     */
    @Override
    public boolean containsNode(InventoryObjectNode node) {
        return allRowsData.stream().anyMatch(childNode -> childNode.equals(node));
    }

    /**
     * Returns a node if it exists in the data rows.
     * 
     * @param nodeId The id of the node to be found.
     * @return The node found.
     */
    @Override
    public InventoryObjectNode getNodeById(String nodeId) {
        try {
            return allRowsData.stream()
                    .filter(n -> n.getId().equals(nodeId))
                    .findFirst().get();
        } catch (NoSuchElementException ex) {
            new SimpleNotification(ts.getTranslatedString("module.general.messages.error"),
                    ex.getMessage(), AbstractNotification.NotificationType.ERROR, ts).open();
            return null;
        }
    }
    
    /**
     * Retrieves the filtered business objects within a given node. 
     * 
     * @param filterDefinition The filter definition to be applied. 
     * @param rootNode The root node from which the filtered children are obtained.
     * @param parentNode The parent node from which the filtered children are obtained;
     * if null, they are obtained from the root node.
     * @return The business objects within a given node.
     * 
     * @throws InvalidArgumentException If the class name is null
     */
    private List<BusinessObjectLight> getFilterData(FilterDefinition filterDefinition,
            InventoryObjectNode rootNode, InventoryObjectNode parentNode) throws InvalidArgumentException {
        List<BusinessObjectLight> filteredChildren;
        if (parentNode != null) {
            addToRows(parentNode);
            filteredChildren = filterDefinition.getFilter().run(parentNode.getObject().getId(),
                    parentNode.getObject().getClassName(), null, -1, -1);
        } else
            filteredChildren = filterDefinition.getFilter().run(rootNode.getId(),
                    rootNode.getClassName(), null, -1, -1);
        return filteredChildren;
    }
}