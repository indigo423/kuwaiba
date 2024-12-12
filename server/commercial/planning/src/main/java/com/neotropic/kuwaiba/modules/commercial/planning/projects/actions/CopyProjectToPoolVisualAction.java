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
package com.neotropic.kuwaiba.modules.commercial.planning.projects.actions;

import com.neotropic.kuwaiba.modules.commercial.planning.projects.ProjectsModule;
import com.neotropic.kuwaiba.modules.commercial.planning.projects.ProjectsService;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.server.Command;
import java.util.ArrayList;
import java.util.List;
import org.neotropic.kuwaiba.core.apis.integration.modules.ModuleActionException;
import org.neotropic.kuwaiba.core.apis.integration.modules.ModuleActionParameter;
import org.neotropic.kuwaiba.core.apis.integration.modules.ModuleActionParameterSet;
import org.neotropic.kuwaiba.core.apis.integration.modules.actions.AbstractAction;
import org.neotropic.kuwaiba.core.apis.integration.modules.actions.AbstractVisualAction;
import org.neotropic.kuwaiba.core.apis.integration.modules.actions.ActionCompletedListener;
import org.neotropic.kuwaiba.core.apis.persistence.application.InventoryObjectPool;
import org.neotropic.kuwaiba.core.apis.persistence.business.BusinessObjectLight;
import org.neotropic.kuwaiba.core.apis.persistence.exceptions.InvalidArgumentException;
import org.neotropic.kuwaiba.core.i18n.TranslationService;
import org.neotropic.util.visual.button.ActionButton;
import org.neotropic.util.visual.dialog.ConfirmDialog;
import org.neotropic.util.visual.icon.ActionIcon;
import org.neotropic.util.visual.notifications.AbstractNotification;
import org.neotropic.util.visual.notifications.SimpleNotification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Visual wrapper of copy project to pool action.
 * @author Mauricio Ruiz Beltrán {@literal <mauricio.ruiz@kuwaiba.org>}
 */
@Component
public class CopyProjectToPoolVisualAction extends AbstractVisualAction<Dialog> {
    /**
     * Reference to the translation service.
     */
    @Autowired
    private TranslationService ts;
    /**
     * Reference to the Projects Service
     */
    @Autowired
    private ProjectsService ps;
    /**
     * Reference to the underlying action.
     */
    @Autowired
    private CopyProjectToPoolAction copyProjectToPoolAction;
    /**
     * The visual action to create a new projects pool
     */
    @Autowired
    private NewProjectsPoolVisualAction newProjectsPoolVisualAction;
     /**
     * Parameter pool
     */
    public static String PARAM_POOL = "pool";
    /**
     * Parameter project
     */
    public static String PARAM_PROJECT = "project";
    /**
     * Parameter command
     */
    public static String PARAM_COMMAND = "command";
    /**
     * Parameter Command
     */
    private static final String PARAM_COMMAND_ADD = "commandAdd";
    private static final String PARAM_COMMAND_CLOSE = "commandClose";
    /**
     * Command to add pool from main UI
     */
    private Command addPoolUI;
    /**
     *  Button to add a new pool.
     */
    private ActionButton btnAddPool;
    /**
     * ComboBox for select a target pool.
     */
    private ComboBox<InventoryObjectPool> cmbPool;
    /**
     * Dialog to copy an inventory object.
     */
    private ConfirmDialog wdwCopy;
    /**
     * Label to show information when there are no available pools.
     */
    private Label lblInfo;
    /**
     * Current project
     */
    BusinessObjectLight project;
    
    public CopyProjectToPoolVisualAction() {
        super(ProjectsModule.MODULE_ID);
    }

    @Override
    public Dialog getVisualComponent(ModuleActionParameterSet parameters) {
        try {
            if (parameters.containsKey(PARAM_PROJECT)) {
                project = (BusinessObjectLight) parameters.get(PARAM_PROJECT);
                wdwCopy = new ConfirmDialog(ts, this.getModuleAction().getDisplayName());

                lblInfo = new Label(ts.getTranslatedString("module.projects.pool.lable.no-pools-available"));
                lblInfo.setClassName("projects-lbl-no-pools");
                lblInfo.setWidthFull();

                addPoolUI = (Command) parameters.get("commandAddProjectPool");
                Command addPool = () -> refreshPool();
                btnAddPool = new ActionButton(new ActionIcon(VaadinIcon.PLUS_SQUARE_O), this.newProjectsPoolVisualAction.getModuleAction().getDisplayName());
                btnAddPool.addClickListener(event -> {
                    this.newProjectsPoolVisualAction.getVisualComponent(new ModuleActionParameterSet(
                            new ModuleActionParameter(PARAM_COMMAND_ADD, addPool),
                            new ModuleActionParameter(PARAM_COMMAND_CLOSE, addPoolUI)
                    )).open();
                });
                btnAddPool.setHeight("32px");
                
                List<InventoryObjectPool> pools = ps.getProjectPools();
                List<InventoryObjectPool> availablePools = new ArrayList<>();
                if (!pools.isEmpty()) {
                    pools.stream().filter(pool -> (pool.getClassName().equals(project.getClassName())))
                            .forEachOrdered(pool -> {
                                availablePools.add(pool);
                            });
                }
                
                cmbPool = new ComboBox<>(ts.getTranslatedString("module.projects.pool.header"), availablePools);
                cmbPool.setAllowCustomValue(false);
                cmbPool.setRequiredIndicatorVisible(true);
                cmbPool.setWidthFull();
                
                if (!availablePools.isEmpty()) {
                    HorizontalLayout lytPools = new HorizontalLayout(cmbPool, btnAddPool);
                    lytPools.setAlignSelf(FlexComponent.Alignment.END, btnAddPool);
                    lytPools.setSizeFull();
                    lytPools.setSpacing(true);

                    wdwCopy.setContent(lytPools);
                    wdwCopy.setWidth("60%");
                } else {
                    HorizontalLayout lytInfo = new HorizontalLayout(lblInfo, btnAddPool);
                    lytInfo.setAlignSelf(FlexComponent.Alignment.END, btnAddPool);
                    lytInfo.setSizeFull();
                    lytInfo.setSpacing(true);

                    wdwCopy.setContent(lytInfo);
                    wdwCopy.setWidth("30%");
                }
                
                wdwCopy.getBtnConfirm().addClickListener(event -> {
                    try {
                        copyProjectToPoolAction.getCallback().execute(new ModuleActionParameterSet(
                                new ModuleActionParameter<>(PARAM_PROJECT, project),     
                                new ModuleActionParameter<>(PARAM_POOL, cmbPool.getValue())));
                        
                        fireActionCompletedEvent(new ActionCompletedListener.ActionCompletedEvent(ActionCompletedListener.ActionCompletedEvent.STATUS_SUCCESS,
                                ts.getTranslatedString("module.projects.actions.copy-object-to-pool.success"), CopyProjectToPoolAction.class));
                        wdwCopy.close();
                    } catch (ModuleActionException ex) {
                        fireActionCompletedEvent(new ActionCompletedListener.ActionCompletedEvent(ActionCompletedListener.ActionCompletedEvent.STATUS_ERROR,
                            ex.getMessage(), CopyProjectToPoolAction.class));
                    }
                });
                wdwCopy.getBtnConfirm().setEnabled(false);
                cmbPool.addValueChangeListener(event -> wdwCopy.getBtnConfirm().setEnabled(cmbPool.getValue() != null));
                
                return wdwCopy;
            } else {
                ConfirmDialog errorDialog = new ConfirmDialog(ts,
                        this.getModuleAction().getDisplayName(),
                        String.format(ts.getTranslatedString("module.general.messages.parameter-not-found"), PARAM_PROJECT)
                );
                errorDialog.getBtnConfirm().addClickListener(e -> errorDialog.close());
                return errorDialog;
            }
        } catch (InvalidArgumentException ex) {
            new SimpleNotification(ts.getTranslatedString("module.general.messages.error"), ex.getLocalizedMessage(),
                    AbstractNotification.NotificationType.ERROR, ts).open();
        }
        return null;
    }

    private void refreshPool() {
        try {
            List<InventoryObjectPool> pools = ps.getProjectPools();
            List<InventoryObjectPool> availablePools = new ArrayList<>();
            if (!pools.isEmpty()) {
                pools.stream().filter(pool -> (pool.getClassName().equals(project.getClassName())))
                        .forEachOrdered(pool -> {
                            availablePools.add(pool);
                        });
            }
            cmbPool.setItems(availablePools);
            cmbPool.clear();
        } catch (InvalidArgumentException ex) {
            new SimpleNotification(ts.getTranslatedString("module.general.messages.error"), ex.getLocalizedMessage(),
                    AbstractNotification.NotificationType.ERROR, ts).open();
        }
    }
    
    @Override
    public AbstractAction getModuleAction() {
        return copyProjectToPoolAction;
    }   
}