/*
 *  Copyright 2010-2018 Neotropic SAS <contact@neotropic.co>.
 *
 *  Licensed under the EPL License, Version 1.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.eclipse.org/legal/epl-v10.html
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.inventory.navigation.special.relationships;

import org.inventory.communications.core.LocalObjectLight;
import org.inventory.core.services.api.behaviors.Refreshable;
import org.inventory.core.templates.layouts.customshapes.nodes.CustomShapeNode;
import org.inventory.customization.listmanager.nodes.ListTypeItemNode;
import org.inventory.navigation.navigationtree.nodes.ObjectNode;
import org.inventory.navigation.special.relationships.nodes.SpecialRelatedObjectNode;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.explorer.ExplorerManager;
import org.openide.explorer.ExplorerUtils;
import org.openide.explorer.view.BeanTreeView;
import org.openide.nodes.Node;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.windows.TopComponent;
import org.openide.util.NbBundle.Messages;
import org.openide.util.Utilities;
import org.openide.windows.WindowManager;

/**
 * Shows a tree with the special relationships of an object
 * @author Charles Edward Bedon Cortazar {@literal <charles.bedon@kuwaiba.org>}
 * @author Johny Andres Ortega Ruiz {@literal <johny.ortega@kuwaiba.org>}
 */
@ConvertAsProperties(
        dtd = "-//org.inventory.navigation.special.relationships//SpecialRelationships//EN",
        autostore = false
)
@TopComponent.Description(
        preferredID = "SpecialRelationshipsTopComponent",
        iconBase="org/inventory/navigation/special/res/special_relationships_explorer.png", 
        persistenceType = TopComponent.PERSISTENCE_ALWAYS
)
@TopComponent.Registration(mode = "navigator", openAtStartup = false)
@ActionID(category = "Tools", id = "org.inventory.navigation.special.relationships.SpecialRelationshipsTopComponent")
@ActionReferences(value = { @ActionReference(path = "Menu/Tools/Navigation"),
    @ActionReference(path = "Toolbars/01_Navigation", position = 5 )})
@TopComponent.OpenActionRegistration(
        displayName = "#CTL_SpecialRelationshipsAction",
        preferredID = "SpecialRelationshipsTopComponent"
)
@Messages({
    "CTL_SpecialRelationshipsAction=Relationships Explorer",
    "CTL_SpecialRelationshipsTopComponent=Relationships",
    "HINT_SpecialRelationshipsTopComponent=Relationships"
})
public final class SpecialRelationshipsTopComponent extends TopComponent implements 
    ExplorerManager.Provider, LookupListener, Refreshable {
    
    private BeanTreeView tree;
    private ExplorerManager em;
    
    private Lookup.Result<ObjectNode> lookupResult;

    public SpecialRelationshipsTopComponent() {
        initComponents();
        initComponentsCustom();
        setName(Bundle.CTL_SpecialRelationshipsTopComponent());
    }
    
    private void initComponentsCustom() {
        em = new ExplorerManager();
        associateLookup(ExplorerUtils.createLookup(em, getActionMap()));
        tree = new BeanTreeView();
        add(tree);
        em.setRootContext(Node.EMPTY);
        em.getRootContext().setDisplayName("Select a node from a view or tree");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        toolBarMain = new javax.swing.JToolBar();
        btnSpecialRelationshipsGraphExplorer = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        toolBarMain.setRollover(true);
        toolBarMain.setAlignmentY(0.5F);
        toolBarMain.setMaximumSize(new java.awt.Dimension(392, 38));
        toolBarMain.setMinimumSize(new java.awt.Dimension(392, 38));
        toolBarMain.setPreferredSize(new java.awt.Dimension(392, 38));

        btnSpecialRelationshipsGraphExplorer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/inventory/navigation/special/res/graphical_representation.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(btnSpecialRelationshipsGraphExplorer, org.openide.util.NbBundle.getMessage(SpecialRelationshipsTopComponent.class, "SpecialRelationshipsTopComponent.btnSpecialRelationshipsGraphExplorer.text")); // NOI18N
        btnSpecialRelationshipsGraphExplorer.setToolTipText(org.openide.util.NbBundle.getMessage(SpecialRelationshipsTopComponent.class, "SpecialRelationshipsTopComponent.btnSpecialRelationshipsGraphExplorer.toolTipText")); // NOI18N
        btnSpecialRelationshipsGraphExplorer.setEnabled(false);
        btnSpecialRelationshipsGraphExplorer.setFocusable(false);
        btnSpecialRelationshipsGraphExplorer.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSpecialRelationshipsGraphExplorer.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSpecialRelationshipsGraphExplorer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSpecialRelationshipsGraphExplorerActionPerformed(evt);
            }
        });
        toolBarMain.add(btnSpecialRelationshipsGraphExplorer);

        add(toolBarMain, java.awt.BorderLayout.PAGE_START);
    }// </editor-fold>//GEN-END:initComponents

    private void btnSpecialRelationshipsGraphExplorerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSpecialRelationshipsGraphExplorerActionPerformed
        if (em.getRootContext() instanceof SpecialRelatedObjectNode) {
            
            LocalObjectLight rootObject = ((SpecialRelatedObjectNode) em.getRootContext()).getObject();
            
            SpecialRelationshipsGraphExplorerTopComponent tc = (SpecialRelationshipsGraphExplorerTopComponent) WindowManager
                .getDefault().findTopComponent("SpecialRelationshipsGraphExplorerTopComponent_" + rootObject.getId());
            
            if (tc == null) {
                tc = new SpecialRelationshipsGraphExplorerTopComponent(rootObject);
                tc.open();
            } else { 
                if (tc.isOpened())
                    tc.requestAttention(true);
                else { //Even after closed, the TCs (even the no-singletons) continue to exist in the NBP's PersistenceManager registry, 
                       //so we will reuse the instance, refreshing the vierw first
                    tc.refresh();
                    tc.open();
                }
            }
            tc.requestActive();
        }
    }//GEN-LAST:event_btnSpecialRelationshipsGraphExplorerActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSpecialRelationshipsGraphExplorer;
    private javax.swing.JToolBar toolBarMain;
    // End of variables declaration//GEN-END:variables
    @Override
    public void componentOpened() {
        ExplorerUtils.activateActions(em, true);
        lookupResult = Utilities.actionsGlobalContext().lookupResult(ObjectNode.class);
        lookupResult.addLookupListener(this);
        resultChanged(null);
    }

    @Override
    public void componentClosed() {
        ExplorerUtils.activateActions(em, false);
        em.setRootContext(Node.EMPTY);
        lookupResult.removeLookupListener(this);
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

    @Override
    public ExplorerManager getExplorerManager() {
        return em;
    }

    @Override
    public void resultChanged(LookupEvent ev) {
        
        if(lookupResult.allInstances().size() == 1){
            ObjectNode node = (ObjectNode)lookupResult.allInstances().iterator().next();
            
            if (node instanceof SpecialRelatedObjectNode) //Ignore its own nodes
                return;
            if (node instanceof ListTypeItemNode) 
                return;
            if (node instanceof CustomShapeNode) 
                return;
            
            if (!btnSpecialRelationshipsGraphExplorer.isEnabled())
                btnSpecialRelationshipsGraphExplorer.setEnabled(true);
            
            SpecialRelatedObjectNode rootNode = new SpecialRelatedObjectNode(node.getObject());
            em.setRootContext(rootNode);
        }
    }

    @Override
    public void refresh() {
        if (em.getRootContext() instanceof SpecialRelatedObjectNode)
            ((SpecialRelatedObjectNode) em.getRootContext()).refresh();
    }
}
