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
package org.neotropic.kuwaiba.modules.core.datamodelman;

import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import elemental.json.Json;
import java.awt.Color;
import org.apache.commons.io.IOUtils;
import org.neotropic.kuwaiba.core.apis.integration.dashboards.AbstractUI;
import org.neotropic.kuwaiba.core.apis.integration.modules.ModuleActionParameter;
import org.neotropic.kuwaiba.core.apis.integration.modules.ModuleActionParameterSet;
import org.neotropic.kuwaiba.core.apis.integration.modules.actions.ActionCompletedListener;
import org.neotropic.kuwaiba.core.apis.integration.modules.actions.ActionResponse;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.ApplicationObjectNotFoundException;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.BusinessObjectNotFoundException;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.InvalidArgumentException;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.MetadataObjectNotFoundException;
import org.neotropic.kuwaiba.core.apis.persistence.metadata.AttributeMetadata;
import org.neotropic.kuwaiba.core.apis.persistence.metadata.ClassMetadata;
import org.neotropic.kuwaiba.core.apis.persistence.metadata.ClassMetadataLight;
import org.neotropic.kuwaiba.core.apis.persistence.metadata.MetadataEntityManager;
import org.neotropic.kuwaiba.core.apis.persistence.util.Constants;
import org.neotropic.kuwaiba.core.i18n.TranslationService;
import org.neotropic.kuwaiba.modules.core.datamodelman.actions.DeleteAttributeVisualAction;
import org.neotropic.kuwaiba.modules.core.datamodelman.actions.DeleteClassVisualAction;
import org.neotropic.kuwaiba.modules.core.datamodelman.actions.NewAttributeVisualAction;
import org.neotropic.kuwaiba.modules.core.datamodelman.actions.NewClassVisualAction;
import org.neotropic.kuwaiba.modules.core.datamodelman.grids.ClassMetadataTreeGrid;
import org.neotropic.kuwaiba.modules.core.datamodelman.provider.SubClassesLightNoRecursiveProvider;
import org.neotropic.kuwaiba.visualization.api.properties.PropertyFactory;
import org.neotropic.kuwaiba.visualization.api.resources.ResourceFactory;
import org.neotropic.util.visual.button.ActionButton;
import org.neotropic.util.visual.general.BoldLabel;
import org.neotropic.util.visual.icon.ActionIcon;
import org.neotropic.util.visual.notifications.AbstractNotification;
import org.neotropic.util.visual.notifications.SimpleNotification;
import org.neotropic.util.visual.properties.AbstractProperty;
import org.neotropic.util.visual.properties.ColorProperty;
import org.neotropic.util.visual.properties.PropertySheet;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import org.neotropic.kuwaiba.modules.core.logging.LoggingService;

/**
 * Main for the Data Model manager module. This class manages how the pages
 * corresponding to different functionalities are presented in a single place.
 *
 * @author Orlando Paz {@literal <Orlando.Paz@kuwaiba.org>}
 */
@Route(value = "dmman", layout = DataModelManagerLayout.class)
public class DataModelManagerUI extends VerticalLayout implements ActionCompletedListener, HasDynamicTitle, AbstractUI {

    /**
     * Reference to the translation service.
     */
    @Autowired
    private TranslationService ts;
    /**
     * Reference to the Metadata Entity Manager.
     */
    @Autowired
    private MetadataEntityManager mem;
    /**
     * factory to build resources from data source
     */
    @Autowired
    private ResourceFactory resourceFactory;
    /**
     * the visual action to create a new class
     */
    @Autowired
    private NewClassVisualAction newClassVisualAction;
    /**
     * the visual action to delete a class
     */
    @Autowired
    private DeleteClassVisualAction deleteClassVisualAction;
    /**
     * the visual action to create a new attribute
     */
    @Autowired
    private NewAttributeVisualAction newAttributeVisualAction;
    /**
     * the visual action to delete attribute
     */
    @Autowired
    private DeleteAttributeVisualAction deleteAttributeVisualAction;
    /**
     * Reference to the Logging Service.
     */
    @Autowired
    private LoggingService log;
    /**
     * sheet for general Attributes
     */
    private PropertySheet propsheetGeneralAttributes;
    /**
     * sheet for class Attributes properties
     */
    private PropertySheet propsheetClassAttributes;
    /**
     * current selected class
     */
    private ClassMetadataLight selectedClass;
    private ClassMetadataLight selectedClassFilter;
    private ClassMetadataLight selectedListTypeFilter;
    /**
     * grid to list class attributes
     */
    private Grid<AttributeMetadata> tblClassAttributes;
    /**
     * current selected class attribute
     */
    private AttributeMetadata selectedAttribute;
    /**
     * icon class image
     */
    private Image iconImage;
    /**
     * upload control to small class icon
     */
    private Image smallIconImage;
    /**
     * layout to show class attributes property sheet
     */
    private VerticalLayout lytPropSheetClassAttributes;
    /**
     * contains class icons
     */
    private VerticalLayout lytIcons;
    /**
     * Label class name
     */
    private BoldLabel lblClassName;
    private HorizontalLayout lytBreadCumb;
    /**
     * Classes grid
     */
    private ClassMetadataTreeGrid<ClassMetadataLight> gridClasses;
    /**
     * List types grid
     */
    private ClassMetadataTreeGrid<ClassMetadataLight> gridListTypes;
    /**
     * Filters for class grids and list type
     */
    private ComboBox<ClassMetadataLight> cmbClassFilter;
    private ComboBox<ClassMetadataLight> cmbListTypeFilter;
    /**
     * Main layouts
     */
    private VerticalLayout lytTabs;
    private HorizontalLayout lytClassActions;
    private VerticalLayout lytSecondary;
    private VerticalLayout lytAttributes;
    private VerticalLayout lytClasses;
    private VerticalLayout lytListTypes;
    /**
     * Buttons for class actions
     */
    private ActionButton btnAddClass;
    private ActionButton btnDeleteClass;
    /**
     * Buttons for attribute actions
     */
    private ActionButton btnDeleteAttribute;
    /**
     * Main tabs
     */
    private Tabs tabsRoot;
    private Tab tabClasses;
    private Tab tabListTypes;
    private Div pagClasses;
    /**
     * Split layout
     */
    private SplitLayout splitLayout;
    /**
     * Root class for List Types
     */
    private ClassMetadata classGenericObjectList;
    /**
     * Root class for Classes
     */
    private ClassMetadata classInventoryObject;
    /**
     * Saves the expanded classes
     */
    private Set<ClassMetadataLight> expandedClasses;

    public DataModelManagerUI() {
        super();
        setSizeFull();
    }

    @Override
    public void onDetach(DetachEvent ev) {
        this.newClassVisualAction.unregisterListener(this);
        this.deleteClassVisualAction.unregisterListener(this);
        this.newAttributeVisualAction.unregisterListener(this);
        this.deleteAttributeVisualAction.unregisterListener(this);
    }

    @Override
    public void actionCompleted(ActionCompletedListener.ActionCompletedEvent ev) {
        if (ev.getStatus() == ActionCompletedListener.ActionCompletedEvent.STATUS_SUCCESS) {

            if (tabsRoot.getSelectedTab().equals(tabClasses)) {//If it's a class
                if (gridClasses != null) {
                    if (ev.getActionResponse() != null
                            && ev.getActionResponse().containsKey(NewAttributeVisualAction.PARAM_CLASS)
                            && ev.getActionResponse().get(NewAttributeVisualAction.PARAM_CLASS) != null) {

                        if (ev.getActionResponse().containsKey(NewAttributeVisualAction.PARAM_ATTRIBUTE)
                                && ev.getActionResponse().get(NewAttributeVisualAction.PARAM_ATTRIBUTE) != null) {

                            updateAttributeElements();
                            new SimpleNotification(ts.getTranslatedString("module.general.messages.success"), 
                                    ev.getMessage(), AbstractNotification.NotificationType.INFO, ts).open();
                        } else {
                            try {
                                ClassMetadataLight affectedNode = (ClassMetadataLight) ev.getActionResponse()
                                                .get(NewClassVisualAction.PARAM_CLASS);
                                
                                if (gridClasses.containsNode(affectedNode)) {
                                    if (ev.getActionResponse().containsKey(ActionResponse.ActionType.ADD)) {
                                        refreshClassFilter(affectedNode, selectedClassFilter,
                                                getClasses(), false);
                                    } else if (ev.getActionResponse().containsKey(ActionResponse.ActionType.REMOVE)) {
                                        if (affectedNode.equals(selectedClassFilter))
                                            selectedClassFilter = null;
                                        afterRemovingClass(affectedNode, selectedClass);
                                        refreshClassFilter(affectedNode, selectedClassFilter,
                                                getClasses(), true);
                                    }
                                } else 
                                    gridClasses.refreshAll();
                                
                                new SimpleNotification(ts.getTranslatedString("module.general.messages.success"),
                                        ev.getMessage(), AbstractNotification.NotificationType.INFO, ts).open();
                            } catch (NoSuchElementException ex) {
                                new SimpleNotification(ts.getTranslatedString("module.general.messages.error"),
                                        ex.getMessage(), AbstractNotification.NotificationType.ERROR, ts).open();
                            }
                        }
                    }
                }
            } else if (tabsRoot.getSelectedTab().equals(tabListTypes)) {//If it's a list type
                if (gridListTypes != null) {
                    if (ev.getActionResponse() != null
                            && ev.getActionResponse().containsKey(NewAttributeVisualAction.PARAM_CLASS)
                            && ev.getActionResponse().get(NewAttributeVisualAction.PARAM_CLASS) != null) {

                        if (ev.getActionResponse().containsKey(NewAttributeVisualAction.PARAM_ATTRIBUTE)
                                && ev.getActionResponse().get(NewAttributeVisualAction.PARAM_ATTRIBUTE) != null) {

                            updateAttributeElements();
                            new SimpleNotification(ts.getTranslatedString("module.general.messages.success"),
                                    ev.getMessage(), AbstractNotification.NotificationType.INFO, ts).open();
                        } else {
                            try {
                                ClassMetadataLight affectedNode = (ClassMetadataLight) ev.getActionResponse()
                                                .get(NewClassVisualAction.PARAM_CLASS);
                                
                                if (gridListTypes.containsNode(affectedNode)) {
                                    if (ev.getActionResponse().containsKey(ActionResponse.ActionType.ADD)) {
                                        refreshListTypeFilter(affectedNode, selectedListTypeFilter,
                                                getListTypesClasses(), false);
                                    } else if (ev.getActionResponse().containsKey(ActionResponse.ActionType.REMOVE)) {
                                        if (affectedNode.equals(selectedListTypeFilter))
                                            selectedListTypeFilter = null;
                                        afterRemovingClass(affectedNode, selectedClass);
                                        refreshListTypeFilter(affectedNode, selectedListTypeFilter,
                                                getListTypesClasses(), true);
                                    }
                                } else 
                                    gridListTypes.refreshAll();
                                
                                new SimpleNotification(ts.getTranslatedString("module.general.messages.success"),
                                        ev.getMessage(), AbstractNotification.NotificationType.INFO, ts).open();
                            } catch (NoSuchElementException ex) {
                                new SimpleNotification(ts.getTranslatedString("module.general.messages.error"),
                                        ex.getMessage(), AbstractNotification.NotificationType.ERROR, ts).open();
                            }
                        }
                    }
                }
            }
        } else 
            new SimpleNotification(ts.getTranslatedString("module.general.messages.error"),
                    ev.getMessage(), AbstractNotification.NotificationType.ERROR, ts).open();
    }

    private void afterRemovingClass(ClassMetadataLight affectedNode,
                                    ClassMetadataLight selectedClass) {
        if (affectedNode != null) {
            if (affectedNode.equals(selectedClass)) {
                this.selectedClass = null;
                cleanClassElements();
            }
        }
    }

    private void cleanClassElements() {
        if (btnAddClass != null)
            btnAddClass.setEnabled(false);

        //clear general attributes section
        if (propsheetGeneralAttributes != null)
            propsheetGeneralAttributes.clear();

        if (lytIcons != null)
            lytIcons.setVisible(false);

        if (btnDeleteClass != null)
            btnDeleteClass.setEnabled(false);

        selectedAttribute = null;

        updateGridClassAttributes(null);

        if (propsheetClassAttributes != null)
            propsheetClassAttributes.clear();

        if (lblClassName != null)
            lblClassName.setText("");

        if (lytBreadCumb != null)
            lytBreadCumb.removeAll();

        if (btnDeleteAttribute != null)
            btnDeleteAttribute.setEnabled(false);
    }

    @Override
    public void initContent() {
        try {
            setSizeFull();
            lblClassName = new BoldLabel();
            lytBreadCumb = new HorizontalLayout();

            expandedClasses = new HashSet<>();
            
            // in case we are updating the page
            this.newClassVisualAction.unregisterListener(this);
            this.deleteClassVisualAction.unregisterListener(this);
            this.newAttributeVisualAction.unregisterListener(this);
            this.deleteAttributeVisualAction.unregisterListener(this);
            
            // register action completed
            this.newClassVisualAction.registerActionCompletedLister(this);
            this.deleteClassVisualAction.registerActionCompletedLister(this);
            this.newAttributeVisualAction.registerActionCompletedLister(this);
            this.deleteAttributeVisualAction.registerActionCompletedLister(this);
            
            // root classes
            classInventoryObject = mem.getClass(Constants.CLASS_INVENTORYOBJECT);
            classGenericObjectList = mem.getClass(Constants.CLASS_GENERICOBJECTLIST);
            
            splitLayout = new SplitLayout();
            splitLayout.setSizeFull();
            splitLayout.setSplitterPosition(25);
            
            initializePropSheetGenericAttributes();
            initializeGridClassAttributes();
            initializePropSheetClassAttributes();
            initializeIconUploaders();
            
            setupLayouts();
            createActions();
            
            createClassTabs();
            splitLayout.addToPrimary(lytTabs);
            
            setPadding(false);
            add(splitLayout);
        } catch (MetadataObjectNotFoundException ex) {
            new SimpleNotification(ts.getTranslatedString("module.general.messages.error"), 
                    ex.getMessage(), AbstractNotification.NotificationType.ERROR, ts).open();
        }
    }

    private void setupLayouts() {
        if (lytTabs == null) {
            lytTabs = new VerticalLayout();
            lytTabs.setId("lytTabs");
            lytTabs.setHeightFull();
            lytTabs.setSpacing(true);
            lytTabs.setMargin(false);
        } 
        if (lytClassActions == null) {
            lytClassActions = new HorizontalLayout();
            lytClassActions.setId("lytClassActions");
            lytClassActions.setSpacing(false);
            lytClassActions.setWidthFull();
            lytClassActions.setJustifyContentMode(JustifyContentMode.END);
        }
        if (lytSecondary == null) {
            lytSecondary = new VerticalLayout();
            lytSecondary.setId("lytSecondary");
            lytSecondary.setSizeFull();
        }
        if (lytAttributes == null) {
            lytAttributes = new VerticalLayout();
            lytAttributes.setId("lytAttributes");
            lytAttributes.setSpacing(false);
            lytAttributes.setPadding(false);
            lytAttributes.setSizeFull();
        }
    }

    private void createActions() {
        btnAddClass = new ActionButton(new ActionIcon(VaadinIcon.PLUS_SQUARE_O),
                this.newClassVisualAction.getModuleAction().getDisplayName());
        btnAddClass.addClickListener(event -> {
            if (selectedClass != null) {
                this.newClassVisualAction.getVisualComponent(new ModuleActionParameterSet(
                        new ModuleActionParameter<>(NewClassVisualAction.PARAM_CLASS, selectedClass))).open();
            } else {
                new SimpleNotification(ts.getTranslatedString("module.general.messages.warning"),
                        ts.getTranslatedString("module.datamodelman.messages.class-unselected"),
                        AbstractNotification.NotificationType.WARNING, ts).open();
            }
        });

        btnDeleteClass = new ActionButton(new ActionIcon(VaadinIcon.TRASH),
                this.deleteClassVisualAction.getModuleAction().getDisplayName());
        btnDeleteClass.addClickListener(event -> {
            if (selectedClass != null)
                this.deleteClassVisualAction.getVisualComponent(new ModuleActionParameterSet(
                        new ModuleActionParameter<>("class", selectedClass))).open();
            else
                new SimpleNotification(ts.getTranslatedString("module.general.messages.warning"),
                        ts.getTranslatedString("module.datamodelman.messages.class-unselected"),
                        AbstractNotification.NotificationType.WARNING, ts).open();
        });
        btnDeleteClass.setEnabled(false);

        lytClassActions.add(btnAddClass, btnDeleteClass);

        // now create the tabs for class attributes
        createAttributeTabs();
    }

    private void createClassTabs() {
        tabClasses = new Tab(ts.getTranslatedString("module.datamodelman.inventory-classes"));
        tabListTypes = new Tab(ts.getTranslatedString("module.datamodelman.list-types"));
        
        pagClasses = new Div();
        tabsRoot = new Tabs(tabClasses, tabListTypes);
        tabsRoot.setFlexGrowForEnclosedTabs(1);
        
        tabsRoot.addSelectedChangeListener(event -> {
            if (event.getSelectedTab().equals(tabClasses)) {
                selectedClass = null;
                selectedListTypeFilter = null;
                expandedClasses.clear();
                createClassFilter(classInventoryObject, getClasses());
                lytListTypes.setVisible(false);
                lytClasses.setVisible(true);
            } else if (event.getSelectedTab().equals(tabListTypes)) {
                selectedClass = null;
                selectedClassFilter = null;
                expandedClasses.clear();
                createListTypeFilter(classGenericObjectList, getListTypesClasses());
                lytClasses.setVisible(false);
                lytListTypes.setVisible(true);
            }
        });
        tabsRoot.setSelectedTab(tabClasses);
        
        createClassFilter(classInventoryObject, getClasses());
        pagClasses.setHeightFull();
        pagClasses.setWidthFull();
        lytTabs.add(tabsRoot, pagClasses);
    }

    private List<ClassMetadataLight> getClasses() {
        try {
            return mem.getSubClassesLight(Constants.CLASS_INVENTORYOBJECT, true, true);
        } catch (MetadataObjectNotFoundException ex) {
            new SimpleNotification(ts.getTranslatedString("module.general.messages.error"),
                    ex.getMessage(), AbstractNotification.NotificationType.ERROR, ts).open();
            return null;
        }
    }

    private void createClassFilter(ClassMetadataLight rootClass,
                                   List<ClassMetadataLight> classes) {
        cmbClassFilter = new ComboBox<>(ts.getTranslatedString("module.general.labels.filter"));
        cmbClassFilter.setItemLabelGenerator(ClassMetadataLight::getName);
        cmbClassFilter.setClearButtonVisible(true);
        cmbClassFilter.setAllowCustomValue(false);
        cmbClassFilter.setWidthFull();

        if (classes != null)
            cmbClassFilter.setItems(classes);

        cmbClassFilter.addValueChangeListener(ev -> {
            cleanClassElements();
            if (ev.getValue() != null) {
                selectedClassFilter = ev.getValue();
                selectedClass = ev.getValue();
                buildClassesGrid(ev.getValue());
            } else {
                if (gridClasses != null)
                    gridClasses.removeAllColumns();
                selectedClassFilter = null;
                selectedClass = null;
            }
        });

        if (rootClass != null) {
            selectedClass = rootClass;
            cmbClassFilter.setValue(rootClass);
        }
    }

    private void refreshClassFilter(ClassMetadataLight affectedNode,
                                    ClassMetadataLight selectedClassFilter,
                                    List<ClassMetadataLight> classes,
                                    boolean isRemoveAction) {
        if (classes != null)
            cmbClassFilter.setItems(classes);

        if (isRemoveAction)
            expandedClasses.remove(affectedNode);
        else
            expandedClasses.add(affectedNode);

        if (selectedClassFilter != null) {
            this.selectedClassFilter = selectedClassFilter;
            cmbClassFilter.setValue(selectedClassFilter);
            for (ClassMetadataLight node : expandedClasses)
                gridClasses.expand(node);
            if (!isRemoveAction) {
                selectedClass = affectedNode;
                gridClasses.select(affectedNode);
                loadAttributes(affectedNode);
            }
        }
    }

    private List<ClassMetadataLight> getListTypesClasses() {
        try {
            return mem.getSubClassesLight(Constants.CLASS_GENERICOBJECTLIST, true, true);
        } catch (MetadataObjectNotFoundException ex) {
            new SimpleNotification(ts.getTranslatedString("module.general.messages.error"),
                    ex.getMessage(), AbstractNotification.NotificationType.ERROR, ts).open();
            return null;
        }
    }

    private void createListTypeFilter(ClassMetadataLight rootClass,
                                      List<ClassMetadataLight> classes) {
        cmbListTypeFilter = new ComboBox<>(ts.getTranslatedString("module.general.labels.filter"));
        cmbListTypeFilter.setItemLabelGenerator(ClassMetadataLight::getName);
        cmbListTypeFilter.setClearButtonVisible(true);
        cmbListTypeFilter.setAllowCustomValue(false);
        cmbListTypeFilter.setWidthFull();

        if (classes != null)
            cmbListTypeFilter.setItems(classes);

        cmbListTypeFilter.addValueChangeListener(ev -> {
            cleanClassElements();
            if (ev.getValue() != null) {
                selectedListTypeFilter = ev.getValue();
                selectedClass = ev.getValue();
                buildListTypesGrid(ev.getValue());
            } else {
                if (gridListTypes != null)
                    gridListTypes.removeAllColumns();

                selectedListTypeFilter = null;
                selectedClass = null;
            }
        });

        if (rootClass != null) {
            selectedClass = rootClass;
            cmbListTypeFilter.setValue(rootClass);
        }
    }

    private void refreshListTypeFilter(ClassMetadataLight affectedNode,
                                       ClassMetadataLight selectedListTypeFilter,
                                       List<ClassMetadataLight> classes,
                                       boolean isRemoveAction) {
        if (classes != null)
            cmbListTypeFilter.setItems(classes);

        if (isRemoveAction)
            expandedClasses.remove(affectedNode);
        else
            expandedClasses.add(affectedNode);

        if (selectedListTypeFilter != null) {
            this.selectedListTypeFilter = selectedListTypeFilter;
            cmbListTypeFilter.setValue(selectedListTypeFilter);
            for (ClassMetadataLight node : expandedClasses)
                gridListTypes.expand(node);
            if (!isRemoveAction) {
                selectedClass = affectedNode;
                gridListTypes.select(affectedNode);
                loadAttributes(affectedNode);
            }
        }
    }

    private void buildClassesGrid(ClassMetadataLight rootClass) {   
        try {
            if (rootClass != null) {
                lytClasses = new VerticalLayout();
                lytClasses.setId("lytClasses");
                lytClasses.setPadding(false);
                lytClasses.setSpacing(false);
                lytClasses.setMargin(false);
                lytClasses.setHeightFull();

                gridClasses = new ClassMetadataTreeGrid<>();
                gridClasses.createDataProvider(resourceFactory,
                        new SubClassesLightNoRecursiveProvider(mem, ts),
                        rootClass, true);
                gridClasses.setId("gridClasses");

                gridClasses.addItemClickListener(item -> {
                    selectedClass = item.getItem();
                    loadAttributes(selectedClass);
                });

                gridClasses.addThemeVariants(GridVariant.LUMO_NO_BORDER,
                        GridVariant.LUMO_NO_ROW_BORDERS, GridVariant.LUMO_COMPACT);
                gridClasses.setSelectionMode(Grid.SelectionMode.SINGLE);
                gridClasses.setHeightFull();

                gridClasses.addExpandListener(event -> expandedClasses.addAll(event.getItems()));
                gridClasses.addCollapseListener(event -> expandedClasses.removeAll(event.getItems()));

                lytClasses.add(cmbClassFilter);
                lytClasses.add(gridClasses);

                if(pagClasses != null) {
                    pagClasses.removeAll();
                    pagClasses.add(lytClasses);
                }

                gridClasses.expand(rootClass);
            }
        } catch (IllegalArgumentException ex) {
            new SimpleNotification(ts.getTranslatedString("module.general.messages.error"),
                    ex.getMessage(), AbstractNotification.NotificationType.ERROR, ts).open();
        }
    }

    private void buildListTypesGrid(ClassMetadataLight rootClass) {
        try {
            if (rootClass != null) {
                lytListTypes = new VerticalLayout();
                lytListTypes.setId("lytListTypes");
                lytListTypes.setPadding(false);
                lytListTypes.setSpacing(false);
                lytListTypes.setMargin(false);
                lytListTypes.setHeightFull();

                gridListTypes = new ClassMetadataTreeGrid<>();
                gridListTypes.createDataProvider(resourceFactory,
                        new SubClassesLightNoRecursiveProvider(mem, ts),
                        rootClass, true);
                gridListTypes.setId("gridListTypes");

                gridListTypes.addItemClickListener(item -> {
                    selectedClass = item.getItem();
                    loadAttributes(selectedClass);
                });

                gridListTypes.addThemeVariants(GridVariant.LUMO_NO_BORDER,
                        GridVariant.LUMO_NO_ROW_BORDERS, GridVariant.LUMO_COMPACT);
                gridListTypes.setSelectionMode(Grid.SelectionMode.SINGLE);
                gridListTypes.setHeightFull();

                gridListTypes.addExpandListener(event -> expandedClasses.addAll(event.getItems()));
                gridListTypes.addCollapseListener(event -> expandedClasses.removeAll(event.getItems()));

                lytListTypes.add(cmbListTypeFilter);
                lytListTypes.add(gridListTypes);

                if(pagClasses != null) {
                    pagClasses.removeAll();
                    pagClasses.add(lytListTypes);
                }

                gridListTypes.expand(rootClass);
            }
        } catch (IllegalArgumentException ex) {
            new SimpleNotification(ts.getTranslatedString("module.general.messages.error"), 
                    ex.getMessage(), AbstractNotification.NotificationType.ERROR, ts).open();
        }
    }
    
    /**
     * Evaluate in which tab and grid where the selected class is located,
     * then sends the data to the method that updates the item.
     */
    private void updateCurrentGrid(ClassMetadataLight selectedClass,
                                   ClassMetadataLight selectedClassFilter,
                                   ClassMetadataLight selectedListTypeFilter) {
        if (tabsRoot.getSelectedTab().equals(tabClasses) && gridClasses != null) {
            cmbClassFilter.setItems(getClasses());
            if (selectedClassFilter != null) {
                this.selectedClassFilter = selectedClassFilter;
                cmbClassFilter.setValue(selectedClassFilter);

                if (selectedClass != null) {
                    expandedClasses.add(selectedClass);
                    for (ClassMetadataLight node : expandedClasses)
                        gridClasses.expand(node);

                    this.selectedClass = selectedClass;
                    gridClasses.select(selectedClass);
                    loadAttributes(selectedClass);
                } else {
                    for (ClassMetadataLight node : expandedClasses)
                        gridClasses.expand(node);
                }
            }
        } else if (tabsRoot.getSelectedTab().equals(tabListTypes) && gridListTypes != null) {
            cmbListTypeFilter.setItems(getListTypesClasses());
            if (selectedListTypeFilter != null) {
                this.selectedListTypeFilter = selectedListTypeFilter;
                cmbListTypeFilter.setValue(selectedListTypeFilter);

                if (selectedClass != null) {
                    expandedClasses.add(selectedClass);
                    for (ClassMetadataLight node : expandedClasses)
                        gridListTypes.expand(node);

                    this.selectedClass = selectedClass;
                    gridListTypes.select(selectedClass);
                    loadAttributes(selectedClass);
                } else {
                    for (ClassMetadataLight node : expandedClasses)
                        gridListTypes.expand(node);
                }
            }
        }
    }

    private void createAttributeTabs() {
        Tab tabProperties = new Tab(ts.getTranslatedString("module.datamodelman.properties"));
        Tab tabAttributes = new Tab(ts.getTranslatedString("module.datamodelman.class-attributes"));

        Tabs tabsAttribute = new Tabs(tabProperties, tabAttributes);
        tabsAttribute.setFlexGrowForEnclosedTabs(1);
        tabsAttribute.setSelectedTab(tabProperties);
        tabsAttribute.addSelectedChangeListener(event -> {
            if (event.getSelectedTab().equals(tabProperties))
                createClassProperties();
            else
                createClassAttributes();
        });

        HorizontalLayout lytHeaderContent = new HorizontalLayout(lblClassName, lytBreadCumb, lytClassActions);
        lytHeaderContent.setAlignItems(Alignment.CENTER);
        lytHeaderContent.setWidthFull();

        VerticalLayout lytContent = new VerticalLayout(lytHeaderContent, tabsAttribute);
        lytContent.setSpacing(false);
        lytSecondary.add(lytContent);

        createClassProperties();
        lytSecondary.add(lytAttributes);

        splitLayout.addToSecondary(lytSecondary);
    }

    private void createClassAttributes() {
        lytAttributes.removeAll();

        ActionButton btnAddAttribute = new ActionButton(new ActionIcon(VaadinIcon.PLUS_SQUARE_O),
                this.newAttributeVisualAction.getModuleAction().getDisplayName());
        btnAddAttribute.addClickListener(event -> {
            if (selectedClass != null) {
                this.newAttributeVisualAction.getVisualComponent(new ModuleActionParameterSet(
                        new ModuleActionParameter<>("class", selectedClass))).open();
            } else {
                new SimpleNotification(ts.getTranslatedString("module.general.messages.warning"),
                        ts.getTranslatedString("module.datamodelman.messages.class-unselected"),
                        AbstractNotification.NotificationType.WARNING, ts).open();
            }
        });

        btnDeleteAttribute = new ActionButton(new ActionIcon(VaadinIcon.TRASH),
                this.deleteAttributeVisualAction.getModuleAction().getDisplayName());
        btnDeleteAttribute.addClickListener(event -> {
            if (selectedClass != null && selectedAttribute != null) {
                this.deleteAttributeVisualAction.getVisualComponent(new ModuleActionParameterSet(
                        new ModuleActionParameter<>("class", selectedClass),
                        new ModuleActionParameter<>("attribute", selectedAttribute))).open();
            } else {
                new SimpleNotification(ts.getTranslatedString("module.general.messages.warning"),
                         ts.getTranslatedString("module.datamodelman.messages.class-unselected"),
                        AbstractNotification.NotificationType.WARNING, ts).open();
            }
        });
        btnDeleteAttribute.setEnabled(false);

        HorizontalLayout lytActions = new HorizontalLayout(btnAddAttribute, btnDeleteAttribute);
        lytActions.setSpacing(false);
        
        VerticalLayout lytListClassAttributes = new VerticalLayout(lytActions, tblClassAttributes);

        lytPropSheetClassAttributes = new VerticalLayout(
                new H4(ts.getTranslatedString("module.datamodelman.attributes")), propsheetClassAttributes);
        lytPropSheetClassAttributes.setSpacing(false);
        lytPropSheetClassAttributes.setVisible(false);

        HorizontalLayout lytClassAttributes = new HorizontalLayout(lytListClassAttributes, lytPropSheetClassAttributes);
        lytClassAttributes.setSizeFull();

        lytAttributes.add(lytClassAttributes);
    }

    private void createClassProperties() {
        lytAttributes.removeAll();

        BoldLabel lblIcon = new BoldLabel(ts.getTranslatedString("module.datamodelman.icon"));
        lblIcon.setClassName("lbl-icon-dmman");

        Div divIcon = new Div(iconImage);

        ActionButton btnRemoveIcon = new ActionButton(new ActionIcon(VaadinIcon.CLOSE_CIRCLE_O,
                ts.getTranslatedString("module.datamodelman.remove-icon")));
        btnRemoveIcon.addClickListener(event -> {
            byte[] imageData = new byte[0];
            if (selectedClass != null) {
                try {
                    HashMap<String, Object> newSmallIcon = new HashMap<>();
                    newSmallIcon.put(Constants.PROPERTY_ICON, imageData);
                    mem.setClassProperties(selectedClass.getId(), newSmallIcon);
                    updateIconImages(selectedClass);

                    new SimpleNotification(ts.getTranslatedString("module.general.messages.success"),
                            ts.getTranslatedString("module.general.messages.property-updated-successfully"),
                            AbstractNotification.NotificationType.INFO, ts).open();
                } catch (ApplicationObjectNotFoundException | MetadataObjectNotFoundException
                         | InvalidArgumentException | BusinessObjectNotFoundException ex) {
                    new SimpleNotification(ts.getTranslatedString("module.general.messages.error"),
                            ex.getMessage(), AbstractNotification.NotificationType.ERROR, ts).open();
                }
            }
        });

        ActionButton btnRemoveSmallIcon = new ActionButton(new ActionIcon(VaadinIcon.CLOSE_CIRCLE_O),
                ts.getTranslatedString("module.datamodelman.remove-icon"));
        btnRemoveSmallIcon.addClickListener(event -> {
            byte[] imageData = new byte[0];
            if (selectedClass != null) {
                try {
                    HashMap<String, Object> newSmallIcon = new HashMap<>();
                    newSmallIcon.put(Constants.PROPERTY_SMALL_ICON, imageData);
                    mem.setClassProperties(selectedClass.getId(), newSmallIcon);
                    updateIconImages(selectedClass);
                    updateCurrentGrid(selectedClass, selectedClassFilter, selectedListTypeFilter);
                    new SimpleNotification(ts.getTranslatedString("module.general.messages.success"),
                            ts.getTranslatedString("module.general.messages.property-updated-successfully"),
                            AbstractNotification.NotificationType.INFO, ts).open();
                } catch (ApplicationObjectNotFoundException | MetadataObjectNotFoundException
                         | InvalidArgumentException | BusinessObjectNotFoundException ex) {
                    new SimpleNotification(ts.getTranslatedString("module.general.messages.error"),
                            ex.getMessage(), AbstractNotification.NotificationType.ERROR, ts).open();
                }
            }
        });

        HorizontalLayout lytClassIcon = new HorizontalLayout(lblIcon, divIcon, btnRemoveIcon, buildIconUploadArea());
        lytClassIcon.setSpacing(true);
        lytClassIcon.setAlignItems(Alignment.CENTER);

        // Small Icon
        BoldLabel lblSmallIcon = new BoldLabel(ts.getTranslatedString("module.datamodelman.smallicon"));
        lblSmallIcon.setClassName("lbl-icon-dmman");

        Div divSmallIcon = new Div(smallIconImage);
        divSmallIcon.setClassName("div-icon-dmman");

        HorizontalLayout lytSmallClassIcon = new HorizontalLayout(lblSmallIcon, divSmallIcon, btnRemoveSmallIcon, buildSmallIconUploadArea());
        lytSmallClassIcon.setSpacing(true);
        lytSmallClassIcon.setAlignItems(Alignment.CENTER);

        BoldLabel lblInfoFile = new BoldLabel(String.format("%s.     %s: %s bytes",
                ts.getTranslatedString("module.datamodelman.accepted-icon-file-types"),
                ts.getTranslatedString("module.datamodelman.max-size"),
                Constants.MAX_ICON_SIZE_IN_BYTES));
        lblInfoFile.setClassName("text-secondary");

        lytIcons = new VerticalLayout(new H4(ts.getTranslatedString("module.datamodelman.icons")),
                lblInfoFile, lytClassIcon, lytSmallClassIcon);
        lytIcons.setVisible(true);
        lytIcons.setSpacing(true);
        lytIcons.setPadding(false);

        VerticalLayout lytGeneralAttributes = new VerticalLayout(propsheetGeneralAttributes);
        lytGeneralAttributes.setSpacing(false);
        lytGeneralAttributes.setPadding(false);

        lytAttributes.add(lytGeneralAttributes, lytIcons);
    }

    private void loadAttributes(ClassMetadataLight selectedClass) {
        try {
            if (selectedClass != null) {
                updatePropertySheetGeneralAttributes(selectedClass);
                updateGridClassAttributes(selectedClass);
                updateIconImages(selectedClass);

                if (propsheetClassAttributes != null)
                    propsheetClassAttributes.clear();
                if (lytPropSheetClassAttributes != null)
                    lytPropSheetClassAttributes.setVisible(false);
                if (lytIcons != null)
                    lytIcons.setVisible(true);
                if (btnDeleteAttribute != null)
                    btnDeleteAttribute.setEnabled(false);
                if (btnDeleteClass != null)
                    btnDeleteClass.setEnabled(true);
                if (btnAddClass != null)
                    btnAddClass.setEnabled(true);

                List<ClassMetadataLight> parents = mem.getUpstreamClassHierarchy(selectedClass.getName(), false);
                lblClassName.setText(selectedClass.getName());

                lytBreadCumb.removeAll();
                lytBreadCumb.add(createParentBreadCrumbs(parents, 2));
            }
        } catch (MetadataObjectNotFoundException ex) {
            new SimpleNotification(ts.getTranslatedString("module.general.messages.error"), ex.getMessage(),
                    AbstractNotification.NotificationType.ERROR, ts).open();
        }
    }

    /**
     * Creates/updates the localization path, that shows the whole list of the
     * parents of the selected object in the tree
     *
     * @param selectedItemParents the selected object in the location tree
     * @param kind the kind of bread crumbs if is location or device
     */
    private Div createParentBreadCrumbs(List<ClassMetadataLight> selectedItemParents, int kind) {
        Div divPowerline = new Div();
        divPowerline.setWidthFull();
        divPowerline.setHeight("20px");
        divPowerline.setClassName("parents-breadcrumbs");

        List<ClassMetadataLight> parents = new ArrayList(selectedItemParents);
        Collections.reverse(parents);
        selectedItemParents.forEach(parent -> {
            Span span = new Span(new Label(parent.getName().equals(Constants.DUMMY_ROOT) ? "/" : parent.getName()));
            span.setSizeUndefined();
            span.setTitle(String.format("[%s]", parent.getName()));
            span.addClassNames("parent", kind == 1 ? "location-parent-color" : "device-parent-color");
            divPowerline.add(span);
        });

        return divPowerline;
    }

    private void updatePropertySheetGeneralAttributes(ClassMetadataLight selectedClass) {
        try {
            if (selectedClass != null) {
                ClassMetadata classMetadata = mem.getClass(selectedClass.getName());
                List<AbstractProperty> properties = PropertyFactory.generalPropertiesFromClass(classMetadata, ts);
                properties.sort(Comparator
                        .comparing((AbstractProperty property) -> property instanceof ColorProperty)
                        .thenComparing(AbstractProperty::getName));
                propsheetGeneralAttributes.setItems(properties);
            }
        } catch (MetadataObjectNotFoundException ex) {
            new SimpleNotification(ts.getTranslatedString("module.general.messages.error"),
                    ex.getMessage(), AbstractNotification.NotificationType.ERROR, ts).open();
        }
    }

    private void updatePropertySheetClassAttributes() {
        try {
            propsheetClassAttributes.setItems(PropertyFactory.generalPropertiesFromAttribute(selectedAttribute, mem, ts, log));
        } catch (Exception ex) {
            new SimpleNotification(ts.getTranslatedString("module.general.messages.error"),
                    ts.getTranslatedString("module.general.messages.unexpected-error"),
                    AbstractNotification.NotificationType.ERROR, ts).open();
        }
    }

    private void initializePropSheetGenericAttributes() {
        propsheetGeneralAttributes = new PropertySheet(ts, new ArrayList<>());
        propsheetGeneralAttributes.addPropertyValueChangedListener((AbstractProperty<? extends Object> property) -> {
            try {
                if (selectedClass != null) {
                    boolean updateCurrentGrid = false;
                    HashMap<String, Object> newProperties = new HashMap<>();
                    switch (property.getName()) {
                        case Constants.PROPERTY_NAME:
                            newProperties.put(Constants.PROPERTY_NAME, property.getValue());
                            selectedClass.setName(property.getValue().toString());
                            updateCurrentGrid = true;
                            break;
                        case Constants.PROPERTY_DISPLAY_NAME:
                            newProperties.put(Constants.PROPERTY_DISPLAY_NAME, property.getValue());
                            selectedClass.setDisplayName(property.getValue().toString());
                            break;
                        case Constants.PROPERTY_DESCRIPTION:
                            newProperties.put(Constants.PROPERTY_DESCRIPTION, property.getValue());
                            break;
                        case Constants.PROPERTY_ABSTRACT:
                            newProperties.put(Constants.PROPERTY_ABSTRACT, property.getValue());
                            selectedClass.setAbstract((Boolean) property.getValue());
                            break;
                        case Constants.PROPERTY_IN_DESIGN:
                            newProperties.put(Constants.PROPERTY_IN_DESIGN, property.getValue());
                            selectedClass.setInDesign((Boolean) property.getValue());
                            break;
                        case Constants.PROPERTY_COUNTABLE:
                            newProperties.put(Constants.PROPERTY_COUNTABLE, property.getValue());
                            break;
                        case Constants.PROPERTY_COLOR:
                            int color = Color.decode((String) property.getValue()).getRGB();
                            newProperties.put(Constants.PROPERTY_COLOR, color);
                            selectedClass.setColor(color);
                            updateCurrentGrid = true;
                            break;
                    }
                    mem.setClassProperties(selectedClass.getId(), newProperties);

                    updatePropertySheetGeneralAttributes(selectedClass);
                    if (updateCurrentGrid)
                        updateCurrentGrid(selectedClass, selectedClassFilter, selectedListTypeFilter);
                    
                    new SimpleNotification(ts.getTranslatedString("module.general.messages.success"),
                            ts.getTranslatedString("module.general.messages.property-updated-successfully"),
                            AbstractNotification.NotificationType.INFO, ts).open();
                }
            } catch (MetadataObjectNotFoundException | BusinessObjectNotFoundException
                    | InvalidArgumentException | ApplicationObjectNotFoundException ex) {
                new SimpleNotification(ts.getTranslatedString("module.general.messages.error"),
                        ex.getMessage(), AbstractNotification.NotificationType.ERROR, ts).open();
                propsheetGeneralAttributes.undoLastEdit();
            }
        });
    }

    private void initializeGridClassAttributes() {
        tblClassAttributes = new Grid<>();
        tblClassAttributes.addThemeVariants(GridVariant.LUMO_COMPACT);
        tblClassAttributes.addThemeVariants(GridVariant.MATERIAL_COLUMN_DIVIDERS);
        tblClassAttributes.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
        tblClassAttributes.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        tblClassAttributes.addColumn(item -> {
            String value = item.isMandatory() ? (item.getName() + " *") : item.getName();
            value += item.getDisplayName() != null && !item.getDisplayName().isEmpty()
                    ? " (" + item.getDisplayName() + ")"
                    : " (" + ts.getTranslatedString("module.datamodelman.display-name-not-set") + ")";
            return value;
        })
                .setHeader(ts.getTranslatedString("module.general.labels.attribute-name"))
                .setKey(ts.getTranslatedString("module.general.labels.name"));

        tblClassAttributes.addItemClickListener(ev -> {
            try {
                selectedAttribute = ev.getItem();
                updatePropertySheetClassAttributes();
                lytPropSheetClassAttributes.setVisible(true);
                btnDeleteAttribute.setEnabled(true);
            } catch (Exception ex) {
                new SimpleNotification(ts.getTranslatedString("module.general.messages.error"), ex.getMessage(),
                        AbstractNotification.NotificationType.ERROR, ts).open();
            }
        });
    }

    private void updateGridClassAttributes(ClassMetadataLight object) {
        try {
            if (object != null) {
                ClassMetadata classMetadata = mem.getClass(object.getName());
                List<AttributeMetadata> attributes = classMetadata.getAttributes();
                attributes.sort(Comparator.comparing(AttributeMetadata::getName));
                tblClassAttributes.setItems(attributes);
                tblClassAttributes.getDataProvider().refreshAll();
            } else {
                tblClassAttributes.setItems(new ArrayList<>());
            }
        } catch (MetadataObjectNotFoundException ex) {
            new SimpleNotification(ts.getTranslatedString("module.general.messages.error"), ex.getMessage(),
                    AbstractNotification.NotificationType.ERROR, ts).open();
        }
    }

    private void initializePropSheetClassAttributes() {
        propsheetClassAttributes = new PropertySheet(ts, new ArrayList<>());
        propsheetClassAttributes.addPropertyValueChangedListener((AbstractProperty<? extends Object> property) -> {
            try {
                if (selectedAttribute != null && selectedClass != null) {
                    HashMap<String, Object> newProperties = new HashMap<>();

                    switch (property.getName()) {
                        case Constants.PROPERTY_NAME:
                            newProperties.put(Constants.PROPERTY_NAME, property.getValue().toString());
                            break;
                        case Constants.PROPERTY_DISPLAY_NAME:
                            newProperties.put(Constants.PROPERTY_DISPLAY_NAME, property.getValue());
                            break;
                        case Constants.PROPERTY_DESCRIPTION:
                            newProperties.put(Constants.PROPERTY_DESCRIPTION, property.getValue());
                            break;
                        case Constants.PROPERTY_TYPE:
                            if (property.getValue() != null)
                                newProperties.put(Constants.PROPERTY_TYPE, property.getValue());
                            break;
                        case Constants.PROPERTY_MANDATORY:
                            newProperties.put(Constants.PROPERTY_MANDATORY, property.getValue());
                            break;
                        case Constants.PROPERTY_UNIQUE:
                            newProperties.put(Constants.PROPERTY_UNIQUE, property.getValue());
                            break;
                        case Constants.PROPERTY_MULTIPLE:
                            newProperties.put(Constants.PROPERTY_MULTIPLE, property.getValue());
                            break;
                        case Constants.PROPERTY_VISIBLE:
                            newProperties.put(Constants.PROPERTY_VISIBLE, property.getValue());
                            break;
                        case Constants.PROPERTY_ADMINISTRATIVE:
                            newProperties.put(Constants.PROPERTY_ADMINISTRATIVE, property.getValue());
                            break;
                        case Constants.PROPERTY_NO_COPY:
                            newProperties.put(Constants.PROPERTY_NO_COPY, property.getValue());
                            break;
                        case Constants.PROPERTY_ORDER:
                            newProperties.put(Constants.PROPERTY_ORDER, property.getValue());
                            break;
                    }

                    mem.setAttributeProperties(selectedClass.getId(), selectedAttribute.getId(), newProperties);
                    // Refresh Objects
                    selectedAttribute = mem.getAttribute(selectedClass.getId(), selectedAttribute.getId());
                    updateGridClassAttributes(selectedClass);
                    // Update Property Sheet
                    updatePropertySheetClassAttributes();
                    new SimpleNotification(ts.getTranslatedString("module.general.messages.success"),
                            ts.getTranslatedString("module.general.messages.property-updated-successfully"),
                            AbstractNotification.NotificationType.INFO, ts).open();
                }
            } catch (MetadataObjectNotFoundException | BusinessObjectNotFoundException
                    | InvalidArgumentException ex) {
                new SimpleNotification(ts.getTranslatedString("module.general.messages.error"),
                        ex.getMessage(), AbstractNotification.NotificationType.ERROR, ts).open();
                propsheetClassAttributes.undoLastEdit();
            }
        });
    }

    private void initializeIconUploaders() {
        iconImage = new Image();
        iconImage.setWidth(Constants.DEFAULT_ICON_SIZE);
        iconImage.setHeight(Constants.DEFAULT_ICON_SIZE);

        smallIconImage = new Image();
        smallIconImage.setWidth(Constants.DEFAULT_SMALL_ICON_SIZE);
        smallIconImage.setHeight(Constants.DEFAULT_SMALL_ICON_SIZE);
    }
    
    private Upload buildIconUploadArea() {
        Upload uploadIcon;
        MemoryBuffer bufferIcon = new MemoryBuffer();
        uploadIcon = new Upload(bufferIcon);
        uploadIcon.setAcceptedFileTypes("image/jpeg", "image/png", "image/gif");
        uploadIcon.setMaxFiles(1);
        uploadIcon.setDropLabel(new Label(ts.getTranslatedString("module.datamodelman.dropmessage")));
        uploadIcon.setMaxFileSize(Constants.MAX_ICON_SIZE_IN_BYTES);
        uploadIcon.addSucceededListener(event -> {
            try {
                byte[] imageData = IOUtils.toByteArray(bufferIcon.getInputStream());
                if (selectedClass != null) {
                    HashMap<String, Object> newIcon = new HashMap<>();
                    newIcon.put(Constants.PROPERTY_ICON, imageData);
                    mem.setClassProperties(selectedClass.getId(), newIcon);
                    
                    StreamResource resource = new StreamResource("icon", () -> bufferIcon.getInputStream());
                    iconImage.setSrc(resource);
                    uploadIcon.getElement().setPropertyJson("files", Json.createArray());
                    
                    new SimpleNotification(ts.getTranslatedString("module.general.messages.success"),
                            ts.getTranslatedString("module.general.messages.property-updated-successfully"),
                            AbstractNotification.NotificationType.INFO, ts).open();
                }
            } catch (ApplicationObjectNotFoundException | MetadataObjectNotFoundException 
                    | InvalidArgumentException | BusinessObjectNotFoundException ex) {
                new SimpleNotification(ts.getTranslatedString("module.general.messages.error"),
                        ex.getMessage(), AbstractNotification.NotificationType.ERROR, ts).open();
            } catch (IOException ex) {
                new SimpleNotification(ts.getTranslatedString("module.general.messages.error"),
                        ts.getTranslatedString("module.general.messages.unexpected-error"),
                        AbstractNotification.NotificationType.ERROR, ts).open();
            }
        });

        uploadIcon.addFileRejectedListener(listener -> {
            new SimpleNotification(ts.getTranslatedString("module.general.messages.error"),
                    listener.getErrorMessage(), AbstractNotification.NotificationType.ERROR, ts).open();
        });
        
        return uploadIcon;
    }
    
    private Upload buildSmallIconUploadArea() {
        Upload uploadSmallIcon;
        MemoryBuffer bufferSmallIcon = new MemoryBuffer();
        uploadSmallIcon = new Upload(bufferSmallIcon);
        uploadSmallIcon.setAcceptedFileTypes("image/jpeg", "image/png", "image/gif");
        uploadSmallIcon.setMaxFiles(1);
        uploadSmallIcon.setDropLabel(new Label(ts.getTranslatedString("module.datamodelman.dropmessage")));
        uploadSmallIcon.setMaxFileSize(Constants.MAX_ICON_SIZE_IN_BYTES);

        uploadSmallIcon.addSucceededListener(event -> {
            try {
                byte[] imageData = IOUtils.toByteArray(bufferSmallIcon.getInputStream());
                if (selectedClass != null) {
                    HashMap<String, Object> newSmallIcon = new HashMap<>();
                    newSmallIcon.put(Constants.PROPERTY_SMALL_ICON, imageData);
                    mem.setClassProperties(selectedClass.getId(), newSmallIcon);
                    updateCurrentGrid(selectedClass, selectedClassFilter, selectedListTypeFilter);
                    
                    StreamResource resource = new StreamResource("icon", () -> bufferSmallIcon.getInputStream());
                    smallIconImage.setSrc(resource);
                    uploadSmallIcon.getElement().setPropertyJson("files", Json.createArray());
                    
                    new SimpleNotification(ts.getTranslatedString("module.general.messages.success"),
                            ts.getTranslatedString("module.general.messages.property-updated-successfully"),
                            AbstractNotification.NotificationType.INFO, ts).open();
                }
            } catch (ApplicationObjectNotFoundException | MetadataObjectNotFoundException
                    | InvalidArgumentException | BusinessObjectNotFoundException ex) {
                new SimpleNotification(ts.getTranslatedString("module.general.messages.error"),
                        ex.getMessage(), AbstractNotification.NotificationType.ERROR, ts).open();
            } catch (IOException ex) {
                new SimpleNotification(ts.getTranslatedString("module.general.messages.error"),
                        ts.getTranslatedString("module.general.messages.unexpected-error"),
                        AbstractNotification.NotificationType.ERROR, ts).open();
            }
        });

        uploadSmallIcon.addFileRejectedListener(listener
                -> new SimpleNotification(ts.getTranslatedString("module.general.messages.error"),
                        listener.getErrorMessage(), AbstractNotification.NotificationType.ERROR, ts).open());
        
        return uploadSmallIcon;
    }

    private void updateIconImages(ClassMetadataLight selectedClass) {
        try {
            if (selectedClass != null) {
                ClassMetadata classMetadata = mem.getClass(selectedClass.getName());
                byte[] iconBytes = classMetadata.getIcon();
                if (iconBytes.length > 0) {
                    StreamResource resource = new StreamResource("icon.jpg", () -> new ByteArrayInputStream(iconBytes));
                    iconImage.setSrc(resource); // Icon 32X32
                } else {
                    iconImage.setSrc("img/no_image.png");
                }
                //small icon
                byte[] smallIconBytes = classMetadata.getSmallIcon();
                if (smallIconBytes.length > 0) {
                    StreamResource resource = new StreamResource("small_icon.jpg", () -> new ByteArrayInputStream(smallIconBytes));
                    smallIconImage.setSrc(resource); // "Small Icon 16X16");
                } else {
                    smallIconImage.setSrc("img/no_image.png");
                }
            }
        } catch (MetadataObjectNotFoundException ex) {
            new SimpleNotification(ts.getTranslatedString("module.general.messages.error"),
                    ex.getMessage(), AbstractNotification.NotificationType.ERROR, ts).open();
        }
    }

    private void updateAttributeElements() {
        updateGridClassAttributes(selectedClass);
        selectedAttribute = null;
        propsheetClassAttributes.clear();
        if (btnDeleteAttribute != null)
            btnDeleteAttribute.setEnabled(false);
    }

    @Override
    public String getPageTitle() {
        return ts.getTranslatedString("module.datamodelman.title");
    }
}