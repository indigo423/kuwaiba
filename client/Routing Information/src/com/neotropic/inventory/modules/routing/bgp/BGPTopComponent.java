/*
 *  Copyright 2010-2020 Neotropic SAS <contact@neotropic.co>.
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
package com.neotropic.inventory.modules.routing.bgp;

import com.neotropic.inventory.modules.routing.bgp.scene.BGPModuleScene;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.inventory.communications.util.Utils;
import org.inventory.core.services.api.behaviors.Refreshable;
import org.inventory.core.services.api.notifications.NotificationUtil;
import org.inventory.core.services.i18n.I18N;
import org.inventory.core.visual.export.ExportScenePanel;
import org.inventory.core.visual.export.filters.ImageFilter;
import org.inventory.core.visual.export.filters.SceneExportFilter;
import org.inventory.core.visual.scene.AbstractScene;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.explorer.ExplorerManager;
import org.openide.util.ImageUtilities;
import org.openide.util.Lookup;
import org.openide.windows.TopComponent;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(
        dtd = "-//com.neotropic.inventory.modules.routing//BGP//EN",
        autostore = false
)
@TopComponent.Description(
        preferredID = "BGPTopComponent",
        iconBase = "com/neotropic/inventory/modules/routing/res/icon.png",
        persistenceType = TopComponent.PERSISTENCE_NEVER
)
@TopComponent.Registration(mode = "editor", openAtStartup = false)
@ActionID(category = "Window", id = "com.neotropic.inventory.modules.routing.bgp.BGPTopComponent")
@ActionReferences(value = {@ActionReference(path = "Menu/Tools/Advanced/Routing")})
@TopComponent.OpenActionRegistration(
        displayName = "Border Gateway Protocol",
        preferredID = "BGPTopComponent"
)

public final class BGPTopComponent extends TopComponent implements Refreshable{

    private static final String ICON_PATH = "com/neotropic/inventory/modules/routing/res/icon.png";
    private BGPModuleScene scene;
    private BGPModuleService service;
    private BGPConfigurationObject configObject;
    
    public BGPTopComponent() {
        initComponents();
        initCustomComponents();
        setName("Border Gateway Protocol");
        setToolTipText("Border Gateway Protocol");
        setIcon(ImageUtilities.loadImage(ICON_PATH, true));
    }
    
    public void initCustomComponents() {
        //em = new ExplorerManager();
        scene = new BGPModuleScene();
        service = new BGPModuleService(scene);
        associateLookup(scene.getLookup());
        
        configObject = Lookup.getDefault().lookup(BGPConfigurationObject.class);
        configObject.setProperty("saved", true);
        
        scene.setActiveTool(BGPModuleScene.ACTION_SELECT);
        pnlScrollMain.setViewportView(scene.createView());
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        barTools = new javax.swing.JToolBar();
        btnSave = new javax.swing.JButton();
        btnExport = new javax.swing.JButton();
        btnRefresh = new javax.swing.JButton();
        btnShowConnectionLabels = new javax.swing.JToggleButton();
        btnAddBackground = new javax.swing.JButton();
        btnRemoveBackground = new javax.swing.JButton();
        pnlScrollMain = new javax.swing.JScrollPane();

        setLayout(new java.awt.BorderLayout());

        barTools.setRollover(true);
        barTools.setPreferredSize(new java.awt.Dimension(520, 40));

        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/neotropic/inventory/modules/routing/res/save.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(btnSave, org.openide.util.NbBundle.getMessage(BGPTopComponent.class, "BGPTopComponent.btnSave.text")); // NOI18N
        btnSave.setToolTipText(org.openide.util.NbBundle.getMessage(BGPTopComponent.class, "BGPTopComponent.btnSave.toolTipText")); // NOI18N
        btnSave.setFocusable(false);
        btnSave.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSave.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });
        barTools.add(btnSave);

        btnExport.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/neotropic/inventory/modules/routing/res/export.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(btnExport, org.openide.util.NbBundle.getMessage(BGPTopComponent.class, "BGPTopComponent.btnExport.text")); // NOI18N
        btnExport.setToolTipText(org.openide.util.NbBundle.getMessage(BGPTopComponent.class, "BGPTopComponent.btnExport.toolTipText")); // NOI18N
        btnExport.setFocusable(false);
        btnExport.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnExport.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnExport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportActionPerformed(evt);
            }
        });
        barTools.add(btnExport);

        btnRefresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/neotropic/inventory/modules/routing/res/refresh.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(btnRefresh, org.openide.util.NbBundle.getMessage(BGPTopComponent.class, "BGPTopComponent.btnRefresh.text")); // NOI18N
        btnRefresh.setToolTipText(org.openide.util.NbBundle.getMessage(BGPTopComponent.class, "BGPTopComponent.btnRefresh.toolTipText")); // NOI18N
        btnRefresh.setFocusable(false);
        btnRefresh.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnRefresh.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });
        barTools.add(btnRefresh);

        btnShowConnectionLabels.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/neotropic/inventory/modules/routing/res/hide_conn_labels.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(btnShowConnectionLabels, org.openide.util.NbBundle.getMessage(BGPTopComponent.class, "BGPTopComponent.btnShowConnectionLabels.text")); // NOI18N
        btnShowConnectionLabels.setToolTipText(org.openide.util.NbBundle.getMessage(BGPTopComponent.class, "BGPTopComponent.btnShowConnectionLabels.toolTipText")); // NOI18N
        btnShowConnectionLabels.setFocusable(false);
        btnShowConnectionLabels.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnShowConnectionLabels.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnShowConnectionLabels.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnShowConnectionLabelsActionPerformed(evt);
            }
        });
        barTools.add(btnShowConnectionLabels);

        btnAddBackground.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/neotropic/inventory/modules/routing/res/add-background.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(btnAddBackground, org.openide.util.NbBundle.getMessage(BGPTopComponent.class, "BGPTopComponent.btnAddBackground.text")); // NOI18N
        btnAddBackground.setToolTipText(org.openide.util.NbBundle.getMessage(BGPTopComponent.class, "BGPTopComponent.btnAddBackground.toolTipText")); // NOI18N
        btnAddBackground.setFocusable(false);
        btnAddBackground.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnAddBackground.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnAddBackground.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddBackgroundActionPerformed(evt);
            }
        });
        barTools.add(btnAddBackground);

        btnRemoveBackground.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/neotropic/inventory/modules/routing/res/remove-background.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(btnRemoveBackground, org.openide.util.NbBundle.getMessage(BGPTopComponent.class, "BGPTopComponent.btnRemoveBackground.text")); // NOI18N
        btnRemoveBackground.setToolTipText(org.openide.util.NbBundle.getMessage(BGPTopComponent.class, "BGPTopComponent.btnRemoveBackground.toolTipText")); // NOI18N
        btnRemoveBackground.setFocusable(false);
        btnRemoveBackground.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnRemoveBackground.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnRemoveBackground.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveBackgroundActionPerformed(evt);
            }
        });
        barTools.add(btnRemoveBackground);

        add(barTools, java.awt.BorderLayout.PAGE_START);
        add(pnlScrollMain, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        if (scene.getNodes().isEmpty())
            JOptionPane.showMessageDialog(null, "The view is empty, it won't be saved", "New View", JOptionPane.INFORMATION_MESSAGE);
        else {
            
            if (service.saveCurrentView()) {
                NotificationUtil.getInstance().showSimplePopup("Save view", NotificationUtil.INFO_MESSAGE, "View saved successfully");
                setHtmlDisplayName(getDisplayName());
            }
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnExportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportActionPerformed
        ExportScenePanel exportPanel = new ExportScenePanel(new SceneExportFilter[]{ ImageFilter.getInstance() },
            scene, getDisplayName());
        DialogDescriptor dd = new DialogDescriptor(exportPanel, "Export Options",true, exportPanel);
        DialogDisplayer.getDefault().createDialog(dd).setVisible(true);
    }//GEN-LAST:event_btnExportActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        if (!(boolean)configObject.getProperty("saved")) {
            switch (JOptionPane.showConfirmDialog(this, "This topology has not been saved, do you want to save it?",
                I18N.gm("confirmation"), JOptionPane.YES_NO_CANCEL_OPTION)){
                case JOptionPane.YES_OPTION:
                    btnSaveActionPerformed(new ActionEvent(this, 0, "close"));
            }
        }
    }//GEN-LAST:event_btnRefreshActionPerformed

    private void btnShowConnectionLabelsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnShowConnectionLabelsActionPerformed
        scene.toggleConnectionLabels(!btnShowConnectionLabels.isSelected());
    }//GEN-LAST:event_btnShowConnectionLabelsActionPerformed

    private void btnAddBackgroundActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddBackgroundActionPerformed
        JFileChooser fChooser = Utils.getGlobalFileChooser();
        fChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fChooser.setFileFilter(new FileNameExtensionFilter("Image files", "gif","jpg", "png"));
        if (fChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            try {
                Image myBackgroundImage = ImageIO.read(new File(fChooser.getSelectedFile().getAbsolutePath()));
                scene.setBackgroundImage(myBackgroundImage);
                scene.fireChangeEvent(new ActionEvent(this, AbstractScene.SCENE_CHANGE, "Add Background"));
            } catch (IOException ex) {
                NotificationUtil.getInstance().showSimplePopup(I18N.gm("error"), NotificationUtil.ERROR_MESSAGE, ex.getMessage());
            }
        }
    }//GEN-LAST:event_btnAddBackgroundActionPerformed

    private void btnRemoveBackgroundActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveBackgroundActionPerformed
        scene.setBackgroundImage(null);
        scene.fireChangeEvent(new ActionEvent(this, AbstractScene.SCENE_CHANGE, "Remove Background"));
    }//GEN-LAST:event_btnRemoveBackgroundActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToolBar barTools;
    private javax.swing.JButton btnAddBackground;
    private javax.swing.JButton btnExport;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnRemoveBackground;
    private javax.swing.JButton btnSave;
    private javax.swing.JToggleButton btnShowConnectionLabels;
    private javax.swing.JScrollPane pnlScrollMain;
    // End of variables declaration//GEN-END:variables
    @Override
    public void componentOpened() {
        scene.clear();
        service.loadView();
    }

    @Override
    public void componentClosed() {
        // TODO add custom code on component closing
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
    public void refresh() {
        scene.clear();
        service.reloadBGPView();
    }
}
