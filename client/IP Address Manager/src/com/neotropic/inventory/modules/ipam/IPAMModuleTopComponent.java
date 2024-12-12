/*
 * Copyright 2010-2019 Neotropic SAS <contact@neotropic.co>.
 *
 * Licensed under the EPL License, Version 1.0 (the "License"); you may not use
 * this file except in compliance with the License. You may obtain a copy of the
 * License at
 *
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.neotropic.inventory.modules.ipam;

import org.inventory.core.services.api.behaviors.Refreshable;
import org.inventory.core.services.api.notifications.NotificationUtil;
import com.neotropic.inventory.modules.ipam.nodes.IPAMRootNode;
import org.inventory.core.services.i18n.I18N;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.explorer.ExplorerManager;
import org.openide.explorer.ExplorerUtils;
import org.openide.explorer.view.BeanTreeView;
import org.openide.nodes.Node;
import org.openide.windows.TopComponent;

/**
 * Top component for the IP address manager module.
 * Adrian Martinez Molina {@literal <adrian.martinez@kuwaiba.org>}
 */
@ConvertAsProperties(
        dtd = "-//com.neotropic.inventory.modules.ipam//IPAMModuleTopComponent//EN",
        autostore = false
)
@TopComponent.Description(
        preferredID = "IPAMModuleTopComponent",
        iconBase="com/neotropic/inventory/modules/res/icon.png", 
        persistenceType = TopComponent.PERSISTENCE_ALWAYS
)
@TopComponent.Registration(mode = "explorer", openAtStartup = false)
@ActionID(category = "Window", id = "com.neotropic.inventory.modules.ipam.IPAMModuleTopComponent")
@ActionReferences(value = {@ActionReference(path = "Menu/Tools/Advanced"),
    @ActionReference(path = "Toolbars/10_Advanced", position = 3)})
@TopComponent.OpenActionRegistration(
        displayName = "#IPAM.module.displayname",
        preferredID = "IPAMModuleTopComponent"
)

public final class IPAMModuleTopComponent extends TopComponent implements ExplorerManager.Provider, Refreshable {
    
    private static final ExplorerManager em = new ExplorerManager();
    private BeanTreeView treeView;
    
    public IPAMModuleTopComponent() {
        initComponents();
        setName(I18N.gm("IPAM.module.name"));
        setToolTipText(I18N.gm("IPAM.module.tooltiptext"));
        initCustomComponents();
    }

    public void initCustomComponents(){
        associateLookup(ExplorerUtils.createLookup(em, getActionMap()));
        treeView = new BeanTreeView();
        treeView.setRootVisible(false);
        add(treeView);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setLayout(new java.awt.BorderLayout());
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    @Override
    public void componentOpened() {
        setRoot();
    }

    @Override
    public void componentClosed() {
        em.setRootContext(Node.EMPTY);
    }

    void writeProperties(java.util.Properties p) {
        // better to version settings since initial version as advocated at
        // http://wiki.apidesign.org/wiki/PropertyFiles
        p.setProperty("version", "1.0");
        // TODO store your settings
    }

    void readProperties(java.util.Properties p) {
        String version = p.getProperty("version");
        // TODO read your settings according to their version
    }

    public void setRoot(){
       em.setRootContext(new IPAMRootNode(new IPAMRootNode.IpamSubnetRootPoolChildren()));
    }
    
    @Override
    public ExplorerManager getExplorerManager() {
        return em;
    }

    @Override
    public void refresh() {}

    public NotificationUtil getNotifier(){
         return NotificationUtil.getInstance();
    }
   
}
