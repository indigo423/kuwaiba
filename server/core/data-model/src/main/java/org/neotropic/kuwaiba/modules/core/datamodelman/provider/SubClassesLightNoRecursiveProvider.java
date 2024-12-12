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
package org.neotropic.kuwaiba.modules.core.datamodelman.provider;

import com.vaadin.flow.data.provider.hierarchy.AbstractBackEndHierarchicalDataProvider;
import com.vaadin.flow.data.provider.hierarchy.HierarchicalDataProvider;
import com.vaadin.flow.data.provider.hierarchy.HierarchicalQuery;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Stream;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.MetadataObjectNotFoundException;
import org.neotropic.kuwaiba.core.apis.persistence.metadata.ClassMetadataLight;
import org.neotropic.kuwaiba.core.apis.persistence.metadata.MetadataEntityManager;
import org.neotropic.kuwaiba.core.i18n.TranslationService;
import org.neotropic.util.visual.notifications.AbstractNotification;
import org.neotropic.util.visual.notifications.SimpleNotification;

/**
 * Provider given for class metadata node type.
 * @author Mauricio Ruiz Beltrán {@literal <mauricio.ruiz@kuwaiba.org>}
 */
public class SubClassesLightNoRecursiveProvider extends ClassMetadataTreeProvider {
    
    /**
     * Reference to the Metadata Entity Manager.
     */
    private final MetadataEntityManager mem;
    /**
     * Reference to the Translation Service.
     */
    private final TranslationService ts;
    /**
     * Saves all the data rows in which the tree has been expanded.
     */
    private final List<ClassMetadataLight> allRowsData;
    
    /**
     * Creates a new instance of SubClassesLightNoRecursiveProvider.
     * @param mem Reference to the Metadata Entity Manager.
     * @param ts  Reference to the Translation Service.
     */
    public SubClassesLightNoRecursiveProvider(MetadataEntityManager mem, TranslationService ts) {
        this.mem = mem;
        this.ts = ts;
        allRowsData = new ArrayList<>();
    }

    /**
     * Builds a HierarchicalDataProvider to provide hierarchical data to the TreeGrid.
     * 
     * @param rootNode    The root node of the hierarchy.
     * @param incluedSelf Include he root node as the first level of the hierarchy visible.
     * @return A HierarchicalDataProvider that provides hierarchical data.
     */
    @Override
    public HierarchicalDataProvider buildTreeDataProvider(ClassMetadataLight rootNode, boolean incluedSelf) {
        return new AbstractBackEndHierarchicalDataProvider<ClassMetadataLight, Void>() {
            @Override
            protected Stream<ClassMetadataLight> fetchChildrenFromBackEnd(HierarchicalQuery<ClassMetadataLight, Void> query) {
                ClassMetadataLight parent = query.getParent();
                List<ClassMetadataLight> listChildren = new ArrayList<>();
                try {
                    List<ClassMetadataLight> childrenQuery = new ArrayList<>();
                   
                        if (parent != null) {
                            addToRows(parent);
                            childrenQuery = mem.getSubClassesLightNoRecursive(parent.getName(), 
                                    true, false);
                        }  else if (incluedSelf) {
                            addToRows(rootNode);
                            return Arrays.asList(rootNode).stream();
                        } else
                            childrenQuery = mem.getSubClassesLightNoRecursive(rootNode.getName(),
                                    true, false);
                    
                    for (ClassMetadataLight child : childrenQuery) {
                        if (!listChildren.contains(child)) {
                            listChildren.add(child);
                            addToRows(child);
                        }
                    }
                } catch (MetadataObjectNotFoundException ex) {
                    new SimpleNotification(ts.getTranslatedString("module.general.messages.error"),
                            ex.getMessage(), AbstractNotification.NotificationType.ERROR, ts).open();
                }
                return listChildren.stream();
            }

            @Override
            public int getChildCount(HierarchicalQuery<ClassMetadataLight, Void> query) {
                try {
                    ClassMetadataLight parent = query.getParent();                    
                    
                    if (parent != null)
                        return (int) mem.getSubClassesCount(parent.getName());
                    else if (incluedSelf)
                        return 1;
                    else
                        return (int) mem.getSubClassesCount(rootNode.getName());
                } catch (MetadataObjectNotFoundException ex) {
                    new SimpleNotification(ts.getTranslatedString("module.general.messages.error"),
                            ex.getMessage(), AbstractNotification.NotificationType.ERROR, ts).open();
                    return 0;
                }
            }

            @Override
            public boolean hasChildren(ClassMetadataLight node) {
                try {
                    return (int) mem.getSubClassesCount(node.getName()) > 0;
                } catch (MetadataObjectNotFoundException ex) {
                    new SimpleNotification(ts.getTranslatedString("module.general.messages.error"),
                            ex.getMessage(), AbstractNotification.NotificationType.ERROR, ts).open();
                }
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
    public void addToRows(ClassMetadataLight node) {
        if(!containsNode(node))
            allRowsData.add(node);
    }

    /**
     * Removes a node from the list of all rows.
     * 
     * @param node The node to be removed.
     */
    @Override
    public void removeFromRows(ClassMetadataLight node) {
        if(!containsNode(node))
            allRowsData.remove(node);
    }

    /**
     * Retrieves all data rows.
     * 
     * @return The list with the data rows.
     */
    @Override
    public List<ClassMetadataLight> getAllDataRows() {
        return allRowsData == null ? new ArrayList<>() : allRowsData;
    }

    /**
     * Checks if a given node is already contained in the list of all expanded rows.
     * 
     * @param node The node to check.
     * @return True if the node is contained; otherwise, false.
     */
    @Override
    public boolean containsNode(ClassMetadataLight node) {
        return allRowsData.stream().anyMatch(childNode -> childNode.equals(node));
    }

    /**
     * Returns a node if it exists in the data rows.
     * 
     * @param nodeId The id of the node to be found.
     * @return The node found.
     */
    @Override
    public ClassMetadataLight getNodeById(long nodeId) {
        try {
            return allRowsData.stream()
                    .filter(n -> n.getId() == nodeId)
                    .findFirst().get();
        } catch (NoSuchElementException ex) {
            new SimpleNotification(ts.getTranslatedString("module.general.messages.error"),
                    ex.getMessage(), AbstractNotification.NotificationType.ERROR, ts).open();
            return null;
        }
    }
}