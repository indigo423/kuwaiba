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
package org.neotropic.kuwaiba.modules.core.navigation.explorers.provider;

import com.vaadin.flow.data.provider.hierarchy.AbstractBackEndHierarchicalDataProvider;
import com.vaadin.flow.data.provider.hierarchy.HierarchicalDataProvider;
import com.vaadin.flow.data.provider.hierarchy.HierarchicalQuery;
import org.neotropic.kuwaiba.core.apis.persistence.business.BusinessEntityManager;
import org.neotropic.kuwaiba.core.apis.persistence.business.BusinessObjectLight;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.BusinessObjectNotFoundException;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.InvalidArgumentException;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.MetadataObjectNotFoundException;
import org.neotropic.kuwaiba.core.apis.persistence.util.Constants;
import org.neotropic.kuwaiba.core.i18n.TranslationService;
import org.neotropic.kuwaiba.modules.core.navigation.explorers.nodes.RelationshipExplorerNode;
import org.neotropic.util.visual.notifications.AbstractNotification;
import org.neotropic.util.visual.notifications.SimpleNotification;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Custom provider for relationship explorer.
 *
 * @author Mauricio Ruiz Beltrán {@literal <mauricio.ruiz@kuwaiba.org>}
 */
public class RelationshipExplorerNodeProvider extends RelationshipExplorerNodeTreeProvider {

    /**
     * Reference to the Business Entity Manager.
     */
    private final BusinessEntityManager bem;
    /**
     * Reference to the Translation Service.
     */
    private final TranslationService ts;
    /**
     * Saves all the data rows in which the tree has been expanded.
     */
    private final List<RelationshipExplorerNode> allRowsData;
    /**
     * Saves all relationships in which the tree has been expanded.
     */
    private final List<RelationshipExplorerNode> allRelationships;

    public RelationshipExplorerNodeProvider(BusinessEntityManager bem, TranslationService ts) {
        this.bem = bem;
        this.ts = ts;
        allRowsData = new ArrayList<>();
        allRelationships = new ArrayList<>();
    }

	@Override
	public HierarchicalDataProvider buildTreeDataProvider(RelationshipExplorerNode rootNode) {
		return new AbstractBackEndHierarchicalDataProvider<RelationshipExplorerNode, Void>() {
			@Override
			protected Stream<RelationshipExplorerNode> fetchChildrenFromBackEnd(HierarchicalQuery<RelationshipExplorerNode, Void> query) {
				RelationshipExplorerNode parent = query.getParent();
				try {
					List<RelationshipExplorerNode> relationshipExplorerNode = new ArrayList<>();
					if (parent != null) {
						if (parent.getType() == RelationshipExplorerNode.RelationshipExplorerNodeType.RELATIONSHIP) {
							addRelationship(parent);
							addToRows(parent);

							if (parent.getRelationship().equals("parent")) {
								List<BusinessObjectLight> objectParents = bem.getMultipleParents(parent.getRelationshipSource().getId());
								objectParents.forEach(objectParent -> {
									if (!objectParent.getClassName().equals(Constants.DUMMY_ROOT)) {
										RelationshipExplorerNode node = new RelationshipExplorerNode(objectParent);
										node.setUniqueId(UUID.randomUUID().toString());
                                        node.setRelationship("parent");
                                        node.setRelationshipSource(parent.getRelationshipSource());
                                        node.setRelationshipTarget(objectParent);

										addToRows(node);
										relationshipExplorerNode.add(node);
									}
								});
							} else {
								List<BusinessObjectLight> objects
										= bem.getSpecialAttribute(parent.getRelationshipSource().getClassName(),
										parent.getRelationshipSource().getId(), parent.getRelationship());

								objects.forEach(object -> {
									RelationshipExplorerNode node = new RelationshipExplorerNode(object);
									node.setUniqueId(UUID.randomUUID().toString());
                                    node.setRelationship(parent.getRelationship());
                                    node.setRelationshipSource(parent.getRelationshipSource());
                                    node.setRelationshipTarget(object);

									addToRows(node);
									if (!containsRelationship(node))
										relationshipExplorerNode.add(node);
								});
							}
						} else if (parent.getType() == RelationshipExplorerNode.RelationshipExplorerNodeType.BUSINESS_OBJECT) {
							HashMap<String, List<BusinessObjectLight>> relationships =
									bem.getSpecialAttributes(parent.getBusinessObject().getClassName(), parent.getBusinessObject().getId());

							List<BusinessObjectLight> objectParents = bem.getMultipleParents(parent.getBusinessObject().getId());
							Set<String> addedParents = new HashSet<>();// To avoid duplicates

							objectParents.forEach(objectParent -> {
								if (objectParent != null && !objectParent.getClassName().equals(Constants.DUMMY_ROOT)) {
									String parentKey = "parent-" + parent.getBusinessObject().getId();
									if (!addedParents.contains(parentKey)) {// Check if this node has already been added
										RelationshipExplorerNode node = new RelationshipExplorerNode("parent", parent.getBusinessObject());
										node.setUniqueId(UUID.randomUUID().toString());
                                        node.setRelationshipTarget(objectParent);
										node.setTargetObjects(objectParents);
										relationshipExplorerNode.add(node);
										addedParents.add(parentKey);// Add to list of processed nodes
									}
								}
							});

							List<String> relationshipNames = new ArrayList<>();
							List<BusinessObjectLight> targetObjects = new ArrayList<>();
							relationships.forEach((relationshipName, relationshipList) -> {
								for (BusinessObjectLight relationshipObject : relationshipList) {
									RelationshipExplorerNode relationship = new RelationshipExplorerNode(relationshipName
											, parent.getBusinessObject(), relationshipObject);
									relationship.setUniqueId(UUID.randomUUID().toString());

									if (!containsRelationship(relationship)) {
										addRelationship(relationship);
										addToRows(relationship);
										if (!relationshipNames.contains(relationshipName)) {
											relationshipNames.add(relationshipName);
											relationshipExplorerNode.add(relationship);
										}
									}

									for (BusinessObjectLight targetObject : relationshipList) {
										if (!containsRelationship(new RelationshipExplorerNode(targetObject)))
											targetObjects.add(targetObject);
									}
									relationship.setTargetObjects(targetObjects);
								}
							});
						}
						return relationshipExplorerNode.stream().skip(query.getOffset()).limit(query.getLimit());
					} else {
						rootNode.setUniqueId(UUID.randomUUID().toString());
                        rootNode.setRelationshipSource(rootNode.getBusinessObject());
						addToRows(rootNode);
						relationshipExplorerNode.add(rootNode);
					}
					return relationshipExplorerNode.stream();
				} catch (BusinessObjectNotFoundException | MetadataObjectNotFoundException |
						 InvalidArgumentException ex) {
					new SimpleNotification(ts.getTranslatedString("module.general.messages.error")
							, ex.getLocalizedMessage()
							, AbstractNotification.NotificationType.ERROR, ts).open();
					return new ArrayList().stream();
				}

			}

			@Override
			public int getChildCount(HierarchicalQuery<RelationshipExplorerNode, Void> query) {
				try {
					RelationshipExplorerNode parent = query.getParent();
					List<RelationshipExplorerNode> relationshipExplorerNode = new ArrayList<>();
					if (parent != null) {
						if (parent.getType() == RelationshipExplorerNode.RelationshipExplorerNodeType.RELATIONSHIP) {

                            if (parent.getRelationship().equals("parent")) {
								List<BusinessObjectLight> objectParents = bem.getMultipleParents(parent.getRelationshipSource().getId());
								objectParents.forEach(objectParent -> {
									if (!objectParent.getClassName().equals(Constants.DUMMY_ROOT))
                                        relationshipExplorerNode.add(new RelationshipExplorerNode(objectParent));
								});
							} else {
								List<BusinessObjectLight> objects
										= bem.getSpecialAttribute(parent.getRelationshipSource().getClassName(),
										parent.getRelationshipSource().getId(), parent.getRelationship());

								objects.forEach(object -> {
									if (!containsRelationship(new RelationshipExplorerNode(object)))
                                        relationshipExplorerNode.add(new RelationshipExplorerNode(object));
								});
							}
						} else if (parent.getType() == RelationshipExplorerNode.RelationshipExplorerNodeType.BUSINESS_OBJECT) {
							List<BusinessObjectLight> objectParents = bem.getMultipleParents(parent.getBusinessObject().getId());
							Set<String> addedParents = new HashSet<>();// To avoid duplicates
							objectParents.forEach(objectParent -> {
								if (objectParent != null && !objectParent.getClassName().equals(Constants.DUMMY_ROOT)) {
									String parentKey = "parent-" + parent.getBusinessObject().getId();
									if (!addedParents.contains(parentKey)) {// Check if this node has already been added
										relationshipExplorerNode.add(new RelationshipExplorerNode("parent"
												, parent.getBusinessObject()));
										addedParents.add(parentKey);// Add to list of processed nodes
									}
								}
							});

							HashMap<String, List<BusinessObjectLight>> relationships
									= bem.getSpecialAttributes(parent.getBusinessObject().getClassName()
									, parent.getBusinessObject().getId());

							List<String> relationshipNames = new ArrayList<>();
							relationships.forEach((relationshipName, relationshipList) -> {
								for (BusinessObjectLight relationshipObject : relationshipList) {
									RelationshipExplorerNode relationship = new RelationshipExplorerNode(relationshipName
											, parent.getBusinessObject(), relationshipObject);
									if (!containsRelationship(relationship)) {
										if (!relationshipNames.contains(relationshipName)) {
											relationshipNames.add(relationshipName);
											relationshipExplorerNode.add(relationship);
										}
									}
								}
							});
						}
						return (int) relationshipExplorerNode.stream().skip(query.getOffset()).limit(query.getLimit()).count();
					} else
						relationshipExplorerNode.add(rootNode);

					return relationshipExplorerNode.size();
				} catch (MetadataObjectNotFoundException | BusinessObjectNotFoundException |
						 InvalidArgumentException ex) {
					new SimpleNotification(ts.getTranslatedString("module.general.messages.error"),
							ex.getLocalizedMessage(), AbstractNotification.NotificationType.ERROR, ts).open();
					return 0;
				}
			}

			@Override
			public boolean hasChildren(RelationshipExplorerNode node) {
				try {
					if (node.getType() == RelationshipExplorerNode.RelationshipExplorerNodeType.RELATIONSHIP) {
						if (node.getRelationship().equals("parent")) {
							List<BusinessObjectLight> objectParents = bem.getMultipleParents(node.getRelationshipSource().getId());
							for (BusinessObjectLight objectParent : objectParents) {
								if (!objectParent.getClassName().equals(Constants.DUMMY_ROOT))
									return true;
							}
						} else {
							List<BusinessObjectLight> relationships
									= bem.getSpecialAttribute(node.getRelationshipSource().getClassName(),
									node.getRelationshipSource().getId(), node.getRelationship());

							return !relationships.isEmpty();
						}
					} else if (node.getType() == RelationshipExplorerNode.RelationshipExplorerNodeType.BUSINESS_OBJECT) {
						HashMap<String, List<BusinessObjectLight>> relationships
								= bem.getSpecialAttributes(node.getBusinessObject().getClassName()
								, node.getBusinessObject().getId());
						List<BusinessObjectLight> objectParents = bem.getMultipleParents(node.getBusinessObject().getId());
						for (BusinessObjectLight objectParent : objectParents) {
							if (objectParent != null && !objectParent.getClassName().equals(Constants.DUMMY_ROOT))
								return true;
						}
						return !relationships.isEmpty();
					}
				} catch (MetadataObjectNotFoundException | BusinessObjectNotFoundException |
						 InvalidArgumentException ex) {
					new SimpleNotification(ts.getTranslatedString("module.general.messages.error"),
							ex.getLocalizedMessage(), AbstractNotification.NotificationType.ERROR, ts).open();
					return false;
				}
				return false;
			}
		};
	}

    /**
     * Adds a node to the list of all expanded relationships.
     *
     * @param node The node to add to the relationships.
     */
    private void addRelationship(RelationshipExplorerNode node) {
        if (node.getRelationship() != null && node.getRelationshipSource() != null && node.getRelationshipTarget() != null) {
            if (!containsRelationship(node))
                allRelationships.add(new RelationshipExplorerNode(node.getRelationship(), node.getRelationshipSource(), node.getRelationshipTarget()));
        }
    }

    /**
     * Checks if a given node is already contained in the list of all expanded relationships.
     *
     * @param node The node to check.
     * @return True if the node is contained; otherwise, false.
     */
    private boolean containsRelationship(RelationshipExplorerNode node) {
        if (node.getRelationship() != null && node.getRelationshipSource() != null && node.getRelationshipTarget() != null) {
            for (RelationshipExplorerNode relationship : allRelationships) {
                if (relationship.getRelationship().equals(node.getRelationship())
                        && relationship.getRelationshipSource().equals(node.getRelationshipTarget())
                        && relationship.getRelationshipTarget().equals(node.getRelationshipSource()))
                    return true;
            }
        } else if (node.getBusinessObject() != null) {
            for (RelationshipExplorerNode relationship : allRelationships) {
                if (relationship.getRelationshipSource().equals(node.getBusinessObject()))
                    return true;
            }
        }
        return false;
    }

    /**
     * Adds a node to the list of all expanded rows.
     *
     * @param node The node to add to the list.
     */
    @Override
    public void addToRows(RelationshipExplorerNode node) {
        if (!containsNode(node))
            allRowsData.add(node);
    }

    /**
     * Removes a node from the list of all rows.
     *
     * @param node The node to be removed.
     */
    @Override
    public void removeFromRows(RelationshipExplorerNode node) {
        if (!containsNode(node))
            allRowsData.remove(node);
    }

    /**
     * Retrieves all data rows.
     *
     * @return The list with the data rows.
     */
    @Override
    public List<RelationshipExplorerNode> getAllDataRows() {
        return allRowsData == null ? new ArrayList<>() : allRowsData;
    }

    /**
     * Checks if a given node is already contained in the list of all expanded rows.
     *
     * @param node The node to check.
     * @return True if the node is contained; otherwise, false.
     */
    @Override
    public boolean containsNode(RelationshipExplorerNode node) {
        return allRowsData.stream().anyMatch(childNode -> childNode.equals(node));
    }

    /**
     * Returns a node if it exists in the data rows.
     *
     * @param nodeId The id of the node to be found.
     * @return The node found.
     */
    @Override
    public RelationshipExplorerNode getNodeById(String nodeId) {
        try {
            return allRowsData.stream()
                    .filter(n -> {
                        return n.getBusinessObject() != null && n.getBusinessObject().getId().equals(nodeId);
                    })
                    .findFirst().get();
        } catch (NoSuchElementException ex) {
            new SimpleNotification(ts.getTranslatedString("module.general.messages.error"),
                    ex.getMessage(), AbstractNotification.NotificationType.ERROR, ts).open();
            return null;
        }
    }

    /**
     * Returns a list of nodes if they exist in the data rows.
     *
     * @param nodeId The id of the node to be found.
     * @return The node list found.
     */
    @Override
    public List<RelationshipExplorerNode> getNodesById(String nodeId) {
        return allRowsData.stream()
                .filter(n -> n.getBusinessObject() != null && n.getBusinessObject().getId().equals(nodeId))
                .collect(Collectors.toList());
    }

	/**
	 * This method collapses specific relationships within a collection of relationship explorer nodes,
	 * removing those relationships that are connected to the collapsed nodes.
	 *
	 * @param collapsedNodes A collection of RelationshipExplorerNode items that have been collapsed.
	 */
	@Override
	public void collapseNodes(Collection<RelationshipExplorerNode> collapsedNodes) {
		// List that will store the relationships to be removed
		List<RelationshipExplorerNode> toRemove = new ArrayList<>();

		// Iterate over each collapsed node
		collapsedNodes.forEach(node -> {
			// List to track pending resources that need to be processed
			List<BusinessObjectLight> pendingResources = new ArrayList<>();

			// If the collapsed node is a relationship, add its target objects
			if (node.getType().equals(RelationshipExplorerNode.RelationshipExplorerNodeType.RELATIONSHIP)) {
				if(node.getTargetObjects() != null && !node.getTargetObjects().isEmpty())
                    pendingResources.addAll(node.getTargetObjects());
			}  // If the collapsed node is a business object, add the source or target as appropriate
			else if (node.getType().equals(RelationshipExplorerNode.RelationshipExplorerNodeType.BUSINESS_OBJECT)) {
				if (node.getRelationshipTarget() != null)
					pendingResources.add(node.getRelationshipTarget());
				else if (node.getRelationshipSource() != null)
					pendingResources.add(node.getRelationshipSource());
			}

			// Set to help avoid revisiting resources
			Set<BusinessObjectLight> visitedResources = new HashSet<>();

			// While there are pending resources to process
			while (!pendingResources.isEmpty()) {
				// Get the first pending resource
				BusinessObjectLight currentResource = pendingResources.remove(0);

				// Skip if the resource has already been visited
				if (visitedResources.contains(currentResource))
					continue;

				// Mark the resource as visited
				visitedResources.add(currentResource);

				// If there are relationships in the allRelationships list
				if (!allRelationships.isEmpty()) {
					// Iterate over all relationships
					for (RelationshipExplorerNode relationship : allRelationships) {
						// Check if the current resource is the source of a relationship
						if (currentResource.equals(relationship.getRelationshipSource())
								&& relationship.getRelationshipTarget() != null) {
							// Add the relationship to the removal list
							toRemove.add(relationship);
							// Add the target of this relationship to the list of pending resources
							pendingResources.add(relationship.getRelationshipTarget());
						}

					}
				}
			}
		});
		// Remove all relationships that were marked in the toRemove list
		allRelationships.removeAll(toRemove);
	}
}