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

package org.neotropic.kuwaiba.modules.optional.topoman;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.router.Route;
import org.neotropic.kuwaiba.core.apis.integration.dashboards.AbstractUI;
import org.neotropic.kuwaiba.core.apis.integration.modules.actions.AdvancedActionsRegistry;
import org.neotropic.kuwaiba.core.apis.integration.modules.actions.CoreActionsRegistry;
import org.neotropic.kuwaiba.core.apis.integration.modules.explorers.ExplorerRegistry;
import org.neotropic.kuwaiba.core.apis.integration.modules.views.ViewWidgetRegistry;
import org.neotropic.kuwaiba.core.apis.persistence.application.ApplicationEntityManager;
import org.neotropic.kuwaiba.core.apis.persistence.business.BusinessEntityManager;
import org.neotropic.kuwaiba.core.apis.persistence.metadata.MetadataEntityManager;
import org.neotropic.kuwaiba.core.i18n.TranslationService;
import org.neotropic.kuwaiba.modules.core.logging.LoggingService;
import org.neotropic.kuwaiba.modules.optional.topoman.actions.DeleteTopologyViewVisualAction;
import org.neotropic.kuwaiba.modules.optional.topoman.actions.NewTopologyViewVisualAction;
import org.neotropic.kuwaiba.modules.optional.topoman.widgets.TopologyDesignerDashboard;
import org.neotropic.kuwaiba.visualization.api.resources.ResourceFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Main for the Topology Manager module. This class manages how the pages corresponding 
 * to different functionalities are presented in a single place.
 * @author Orlando Paz {@literal <Orlando.Paz@kuwaiba.org>}
 */
@Route(value = "topoman", layout = TopologyDesignerLayout.class)
public class TopologyDesignerUI extends VerticalLayout implements HasDynamicTitle, AbstractUI {
    /**
     * Reference to the action registry.
     */
    @Autowired
    private CoreActionsRegistry coreActionRegistry;
    /**
     * All non-general purpose actions provided by other modules than Navigation.
     */
    @Autowired
    private AdvancedActionsRegistry advancedActionsRegistry;
    /**
     * All the object-related views exposed by other modules.
     */
    @Autowired
    private ViewWidgetRegistry viewWidgetRegistry;
    /**
     * All the registered object explorers.
     */
    @Autowired
    private ExplorerRegistry explorerRegistry;
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
     * Reference to the Application Entity Manager.
     */
    @Autowired
    private ApplicationEntityManager aem;
    /**
     * Reference to the Business Entity Manager.
     */
    @Autowired
    private BusinessEntityManager bem;
    
    @Autowired
    private ResourceFactory resourceFactory;
    
    @Autowired
    private NewTopologyViewVisualAction newTopologyViewVisualAction;
    
    @Autowired
    private DeleteTopologyViewVisualAction deleteTopologyViewVisualAction;
    /**
     * Reference to the Logging Service.
     */
    @Autowired
    private LoggingService log;
 
    public TopologyDesignerUI() {
        super();
        setSizeFull();
    }
    
    @Override
    public String getPageTitle() {
        return ts.getTranslatedString("module.topoman.title");
    }

    @Override
    public void initContent() {
        setSizeFull();      
        TopologyDesignerDashboard dashboard = new TopologyDesignerDashboard(
                coreActionRegistry, 
                advancedActionsRegistry,
                viewWidgetRegistry,
                explorerRegistry,
                ts, mem, aem, bem,
                resourceFactory, 
                newTopologyViewVisualAction,
                deleteTopologyViewVisualAction,
                log
        );
        dashboard.setSizeFull();
          
        setMargin(false);
        setSpacing(false);
        add(dashboard);
    }
}