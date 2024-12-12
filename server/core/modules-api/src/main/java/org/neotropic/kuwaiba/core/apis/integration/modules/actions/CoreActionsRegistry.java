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

package org.neotropic.kuwaiba.core.apis.integration.modules.actions;
        
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.MetadataObjectNotFoundException;
import org.neotropic.kuwaiba.core.apis.persistence.metadata.MetadataEntityManager;
import org.neotropic.kuwaiba.core.i18n.TranslationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * All general purpose, inventory object-dependent actions (e.g. new object or delete object) in all modules 
 * (but most likely in the Navigation module) considered as core actions and  
 * must be registered here at module startup. Relevant menus (Such as <b>Basic Actions</b> in the Navigation module) 
 * will be dynamically built using the actions in this registry as well as the classes they are applicable to.
 * There are two types of core actions: <b>Delete Actions</b>, which are the actions intended to remove objects. 
 * from the system. Most objects will use the default implementation, however, some might require a special 
 * procedure -such as for physical or logical connections-). <b>Regular Actions</b> are basically the rest of them, 
 * actions such as move/copy objects or launch reports.
 * @author Charles Edward Bedon Cortazar {@literal <charles.bedon@kuwaiba.org>}
 */
@Service
public class CoreActionsRegistry {
    /**
     * The list of registered core actions.
     */
    private List<AbstractVisualInventoryAction> regularActions;
    /**
     * The list of registered delete actions.
     */
    private List<AbstractVisualInventoryAction> deleteActions;
    /**
     * All registered misc actions grouped by instances of what class are they applicable to.
     */
    private HashMap<String, List<AbstractVisualInventoryAction>> regularActionsByApplicableClass;
    /**
     * All registered delete actions grouped by instances of what class are they applicable to.
     */
    private HashMap<String, List<AbstractVisualInventoryAction>> deleteActionsByApplicableClass;
    /**
     * All registered actions grouped by the module they are provided by.
     */
    private HashMap<String, List<AbstractVisualInventoryAction>> actionsByModule;
    /**
     * Default delete action. Most likely provided by the navigation module.
     */
    private AbstractVisualInventoryAction defaultDeleteAction;
    /**
     * Reference to the MetadataEntityManager to access the data model cache.
     */
    @Autowired
    private MetadataEntityManager mem;
    @Autowired
    private TranslationService ts;
    
    public CoreActionsRegistry() {
        this.regularActions = new ArrayList<>();
        this.deleteActions = new ArrayList<>();
        this.regularActionsByApplicableClass = new HashMap<>();
        this.deleteActionsByApplicableClass = new HashMap<>();
        this.actionsByModule = new HashMap<>();
    }
    
    public void setDefaultDeleteAction(AbstractVisualInventoryAction defaultDeleteAction) {
        this.defaultDeleteAction = defaultDeleteAction;
    }
    
    /**
     * Checks what misc actions are associated to a given inventory class. For example, 
     * NewCustomer and ShowReports are part of the returned list if <code>filter</code> is
     * GenericCustomer. Note that the difference between this method and {@link #getMiscActionsApplicableToRecursive(java.lang.String) } is 
     * that this method will return the actions whose appliesTo matches exactly with the provided filter, while the latter 
     * might match even subclasses of the appliesTo return value.
     * @param filter The class to be evaluated.
     * @return The actions that can be executed from an instance of the given class or superclass.
     */
    public List<AbstractVisualInventoryAction> getRegularActionsApplicableTo(String filter) {
        return this.regularActionsByApplicableClass.containsKey(filter) ? this.regularActionsByApplicableClass.get(filter) : new ArrayList<>();
    }
    
    /**
     * Checks what misc actions are associated to a given inventory class. For example, 
     * NewCustomer and ShowReports are part of the returned list if <code>filter</code> is
     * CorporateCustomer.
     * @param filter The class to be evaluated.
     * @return The actions that can be executed from an instance of the given class or superclass.
     */
    public List<AbstractVisualInventoryAction> getActionsApplicableToRecursive(String filter) {
        return this.regularActions.stream().filter((anAction) -> {
            try {
                return anAction.appliesTo() == null ? false : mem.isSubclassOf(filter, anAction.appliesTo());
            } catch (MetadataObjectNotFoundException ex) { // No existing (or cached) classes will be ignored
                return false;
            }
        }).collect(Collectors.toList());
    }
    
    /**
     * Checks what delete actions (there might be more than one, if several delete action implementations are provided) 
     * are associated to a given inventory class. See {@link #getMiscActionsApplicableTo(java.lang.String) } for more 
     * details on its behavior.
     * @param filter The class to be evaluated.
     * @return The delete actions that can be executed from an instance of the given class or superclass.
     */
    public List<AbstractVisualInventoryAction> getDeleteActionsApplicableTo(String filter) {
        return this.deleteActionsByApplicableClass.containsKey(filter) ? this.deleteActionsByApplicableClass.get(filter) : new ArrayList<>();
    }
    
    /**
     * Checks recursively in the class hierarchy what delete actions are associated to a given inventory class. 
     * See {@link #getActionsApplicableToRecursive(java.lang.String) } for more details on its behavior.
     * @param filter The class to be evaluated.
     * @return The delete actions that can be executed from an instance of the given class or superclass.
     */
    public List<AbstractVisualInventoryAction> getDeleteActionsApplicableToRecursive(String filter) {
        return this.deleteActions.stream().filter((anAction) -> {
            try {
                return anAction.appliesTo() == null ? false : mem.isSubclassOf(filter, anAction.appliesTo());
            } catch (MetadataObjectNotFoundException ex) { // No existing (or cached) classes will be ignored
                return false;
            }
        }).collect(Collectors.toList());
    }    
    
    /**
     * Adds an action to the registry.This method also feeds the action map cache structure, which is a hash map which keys are 
     * all the possible super classes the actions are applicable to and the keys are the corresponding actions.
     * @param moduleId The id of the module this action is provided by. The id is returned by AbstractModule.getId().
     * @param action The action to be added. Duplicated action ids are allowed, as long as the duplicate can be used 
     * to overwrite default behaviors, for example, if an object (say a connection) has a specific delete routine  that should 
     * be executed instead of the general purpose delete action, both actions should have the same id, and the renderer should 
     * override the default action with the specific one.
     * @throws IllegalArgumentException If a delete action (a subclass of {@link AbstractDeleteAction}) is provided as argument.
     */
    public void registerRegularAction(String moduleId, AbstractVisualInventoryAction action) throws IllegalArgumentException {
        if (action instanceof AbstractDeleteAction) 
            throw new IllegalArgumentException(String.format(ts.getTranslatedString("module.general.messages.regular-action-registry-not-delete-action"), 
                    action.getClass().getName()));
        
        this.regularActions.add(action);
        
        if (!this.actionsByModule.containsKey(moduleId))
            this.actionsByModule.put(moduleId, new ArrayList<>());
        this.actionsByModule.get(moduleId).add(action);

        String applicableTo = action.appliesTo() == null ? "" : action.appliesTo(); // Actions not applicable to any particular class are classified under an empty string key
        if (!this.regularActionsByApplicableClass.containsKey(applicableTo))
            this.regularActionsByApplicableClass.put(applicableTo, new ArrayList<>());
        this.regularActionsByApplicableClass.get(applicableTo).add(action);
    }

    /**
     * Registers a custom delete action.
     * @param moduleId The id of the module the action belongs to.
     * @param action The action itself.
     */
    public void registerDeleteAction(String moduleId, AbstractVisualInventoryAction action) {
        this.deleteActions.add(action);
        
        if (!this.actionsByModule.containsKey(moduleId))
            this.actionsByModule.put(moduleId, new ArrayList<>());
        this.actionsByModule.get(moduleId).add(action);

        String applicableTo = action.appliesTo() == null ? "" : action.appliesTo(); // Actions not applicable to any particular class are classified under an empty string key
        if (!this.deleteActionsByApplicableClass.containsKey(applicableTo))
            this.deleteActionsByApplicableClass.put(applicableTo, new ArrayList<>());
        this.deleteActionsByApplicableClass.get(applicableTo).add(action);
    }
    
    /**
     * Returns all registered regular actions.
     * @return All registered regular actions.
     */
    public List<AbstractVisualInventoryAction> getRegularActions() {
        return this.regularActions;
    }

    /**
     * Returns all registered delete actions.
     * @return All registered delete actions.
     */
    public List<AbstractVisualInventoryAction> getDeleteActions() {
        return this.deleteActions;
    }
    
    /**
     * Gets the appropriate delete action for a given class, if explicitly registered, 
     * and the default implementation otherwise.
     * @param className The name of the class to evaluate.
     * @return The delete action applicable to the given class. If there isn't one 
     * explicitly set, it will return the default implementation.
     */
    public AbstractVisualInventoryAction getDeleteActionForClass(String className) {
        return deleteActions.stream().filter(aDeleteAction -> {
                try {
                    return aDeleteAction.appliesTo() == null  ? false : mem.isSubclassOf(aDeleteAction.appliesTo(), className);
                } catch (MetadataObjectNotFoundException mone) {
                    return false;
                }
            }).findFirst().orElse(defaultDeleteAction);
    }
    
    /**
     * Returns all actions registered by a particular module.
     * @param moduleId The id of the module. Usually the strings that comes from calling AbstractModule.getId().
     * @return The list of actions, even if none registered for the given module (in that case, an empty array will be returned).
     */
    public List<AbstractVisualInventoryAction> getActionsForModule(String moduleId) {
        return this.actionsByModule.containsKey(moduleId) ? this.actionsByModule.get(moduleId) : new ArrayList<>();
    }
}
