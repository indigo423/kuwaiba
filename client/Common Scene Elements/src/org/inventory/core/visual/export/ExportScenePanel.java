/*
 *  Copyright 2010-2020 Neotropic SAS <contact@neotropic.co>.
 * 
 *   Licensed under the EPL License, Version 1.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *        http://www.eclipse.org/legal/epl-v10.html
 * 
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 * 
 */

package org.inventory.core.visual.export;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.inventory.core.services.api.export.filters.TextExportFilter;
import org.inventory.core.services.i18n.I18N;
import org.inventory.core.visual.export.filters.SceneExportFilter;
import org.inventory.core.visual.scene.AbstractScene;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;

/**
 * Dialog to export a scene to a file
 * @author Charles Edward Bedon Cortazar {@literal <charles.bedon@kuwaiba.org>}
 */
public class ExportScenePanel extends JPanel implements ActionListener {

    private SceneExportFilter[] filters;
    private AbstractScene exportable;
    private String defaultFileName;

    public ExportScenePanel(SceneExportFilter[] filters, AbstractScene exportable, String defaultFileName) {
        this.filters = filters;
        this.exportable = exportable;
        this.defaultFileName = defaultFileName;
        initComponents();
        initCustomComponents();
    }

    private void initCustomComponents() {
        for (SceneExportFilter filter : filters)
            cmbExportTo.addItem(filter);
        cmbExportTo.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent e) {
                updateExtension(e.getSource());
            }
        });
        
        txtOutputFile.setText(System.getProperty("user.home") + File.separator + 
                defaultFileName + ((SceneExportFilter)cmbExportTo.getSelectedItem()).getExtension());
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnExportToSettings = new javax.swing.JButton();
        txtOutputFile = new javax.swing.JTextField();
        lblOutputFile = new javax.swing.JLabel();
        btnOutputFileSet = new javax.swing.JButton();
        lblExportTo = new javax.swing.JLabel();
        cmbExportTo = new javax.swing.JComboBox();

        btnExportToSettings.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/inventory/core/visual/res/configure.png"))); // NOI18N
        btnExportToSettings.setToolTipText(I18N.gm("filter_settings")); // NOI18N
        btnExportToSettings.setPreferredSize(new java.awt.Dimension(24, 24));
        btnExportToSettings.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportToSettingsActionPerformed(evt);
            }
        });

        txtOutputFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtOutputFileActionPerformed(evt);
            }
        });

        lblOutputFile.setText(I18N.gm("export_to")); // NOI18N

        btnOutputFileSet.setText("...");
        btnOutputFileSet.setToolTipText(I18N.gm("select_output_file")); // NOI18N
        btnOutputFileSet.setPreferredSize(new java.awt.Dimension(24, 24));
        btnOutputFileSet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOutputFileSetActionPerformed(evt);
            }
        });

        lblExportTo.setText(I18N.gm("format")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblOutputFile)
                    .addComponent(lblExportTo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cmbExportTo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtOutputFile, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnExportToSettings, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnOutputFileSet, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(32, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnExportToSettings, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtOutputFile, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lblOutputFile)
                                .addComponent(btnOutputFileSet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmbExportTo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblExportTo))))
                .addGap(20, 20, 20))
        );

        btnExportToSettings.getAccessibleContext().setAccessibleDescription(I18N.gm("filter_settings")); // NOI18N
        lblOutputFile.getAccessibleContext().setAccessibleName(I18N.gm("export_to")); // NOI18N
        btnOutputFileSet.getAccessibleContext().setAccessibleDescription(I18N.gm("select_output_file")); // NOI18N
        lblExportTo.getAccessibleContext().setAccessibleName(I18N.gm("format")); // NOI18N
    }// </editor-fold>//GEN-END:initComponents

    private void btnExportToSettingsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportToSettingsActionPerformed
        SceneExportFilter selectedFilter = (SceneExportFilter)cmbExportTo.getSelectedItem();
        if (selectedFilter.getExportSettingsPanel() != null){
            DialogDescriptor dd = new DialogDescriptor(selectedFilter.getExportSettingsPanel(), 
                    I18N.gm("export_settings"), true, null);
            DialogDisplayer.getDefault().createDialog(dd).setVisible(true);
        }else JOptionPane.showMessageDialog(this, I18N.gm("no_advanced_settings_required"), I18N.gm("exporting"), JOptionPane.INFORMATION_MESSAGE);
}//GEN-LAST:event_btnExportToSettingsActionPerformed

    private void btnOutputFileSetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOutputFileSetActionPerformed
        JFileChooser fChooser = new JFileChooser();
        fChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fChooser.setDialogTitle(I18N.gm("select_directory"));
        if (fChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION)
            txtOutputFile.setText(fChooser.getSelectedFile().getAbsolutePath()+
                    File.separator + "scene" + Calendar.getInstance().get(Calendar.DAY_OF_YEAR)+ //NOI18N
                    "-"+Calendar.getInstance().get(Calendar.MINUTE)+ //NOI18N
                    (((SceneExportFilter)cmbExportTo.getSelectedItem()).getExtension())); //NOI18N
}//GEN-LAST:event_btnOutputFileSetActionPerformed

    private void txtOutputFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtOutputFileActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtOutputFileActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnExportToSettings;
    private javax.swing.JButton btnOutputFileSet;
    private javax.swing.JComboBox cmbExportTo;
    private javax.swing.JLabel lblExportTo;
    private javax.swing.JLabel lblOutputFile;
    private javax.swing.JTextField txtOutputFile;
    // End of variables declaration//GEN-END:variables

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == DialogDescriptor.OK_OPTION){
            if (txtOutputFile.getText().trim().equals("")){//NOI18N
                JOptionPane.showMessageDialog(this, I18N.gm("invalid_file_name"), I18N.gm("error"),JOptionPane.ERROR_MESSAGE);
                return;
            }

            SceneExportFilter selectedFilter = (SceneExportFilter)cmbExportTo.getSelectedItem();
           
            try{
                selectedFilter.export(exportable, txtOutputFile.getText());
            }catch(IOException ex){
                JOptionPane.showMessageDialog(this, String.format(I18N.gm("error_exporting_file"), ex.getMessage()), I18N.gm("error"), JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void updateExtension(Object source) {
        if (!txtOutputFile.getText().trim().isEmpty())
            txtOutputFile.setText(
                    txtOutputFile.getText().substring(0, txtOutputFile.getText().lastIndexOf('.'))+
                    ((TextExportFilter)((JComboBox)source).getSelectedItem()).getExtension());
    }
    
    
}