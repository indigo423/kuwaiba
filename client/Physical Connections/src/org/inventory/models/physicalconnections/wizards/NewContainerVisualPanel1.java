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
package org.inventory.models.physicalconnections.wizards;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import org.inventory.communications.CommunicationsStub;
import org.inventory.communications.core.LocalClassMetadataLight;
import org.inventory.communications.core.LocalObjectLight;
import org.inventory.communications.util.Constants;
import org.inventory.core.services.api.notifications.NotificationUtil;
import org.inventory.core.services.i18n.I18N;

/**
 * The panel of the first step of the New Container wizard
 * @author Charles Edward Bedon Cortazar {@literal <charles.bedon@kuwaiba.org>}
 */
public class NewContainerVisualPanel1 extends javax.swing.JPanel {

    /**
     * Creates new form NewContainerVisualPanel1
     */
    public NewContainerVisualPanel1() {
        initComponents();
        List<LocalClassMetadataLight> containerClasses = CommunicationsStub.getInstance().
                getLightSubclasses(Constants.CLASS_GENERICPHYSICALCONTAINER, false, false);
        
        if (containerClasses == null) {
            cmbContainerClass.setModel(new DefaultComboBoxModel());
            NotificationUtil.getInstance().showSimplePopup(I18N.gm("error"), NotificationUtil.ERROR_MESSAGE, CommunicationsStub.getInstance().getError());
        } else {
            cmbContainerClass.setModel(new DefaultComboBoxModel(containerClasses.toArray()));
            if (!containerClasses.isEmpty()) {
                List<LocalObjectLight> containerTemplates = CommunicationsStub.getInstance().getTemplatesForClass(((LocalClassMetadataLight)cmbContainerClass.getItemAt(0)).getClassName(), false);
                cmbContainerTemplate.setModel(new DefaultComboBoxModel(containerTemplates.toArray(new LocalObjectLight[0])));
                chkNoTemplate.setSelected(containerTemplates.isEmpty());
                chkNoTemplate.setEnabled(!containerTemplates.isEmpty());
                cmbContainerTemplate.setEnabled(!containerTemplates.isEmpty());
            } else {
                cmbContainerTemplate.setModel(new DefaultComboBoxModel());
                chkNoTemplate.setSelected(true);
                chkNoTemplate.setEnabled(false);
                cmbContainerTemplate.setEnabled(false);
            }
        }
        
        cmbContainerClass.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    LocalClassMetadataLight selectedClass = (LocalClassMetadataLight)cmbContainerClass.getSelectedItem();
                    List<LocalObjectLight> containerTemplates = CommunicationsStub.getInstance().getTemplatesForClass(selectedClass.getClassName(), false);
                    ((DefaultComboBoxModel)cmbContainerTemplate.getModel()).removeAllElements();
                    if (containerTemplates != null) {
                        cmbContainerTemplate.setModel(new DefaultComboBoxModel(containerTemplates.toArray(new LocalObjectLight[0])));
                        chkNoTemplate.setSelected(containerTemplates.isEmpty());
                        chkNoTemplate.setEnabled(!containerTemplates.isEmpty());
                        cmbContainerTemplate.setEnabled(!containerTemplates.isEmpty());
                    }
                }
            }
        });    
    }
    
    public String getContainerName() {
        return txtContainerName.getText();
    }
    
    public LocalClassMetadataLight getContainerClass() {
        return (LocalClassMetadataLight)cmbContainerClass.getSelectedItem();
    }
    
    public LocalObjectLight getContainerTemplate() {
        return (LocalObjectLight)cmbContainerTemplate.getSelectedItem();
    }
    
    @Override
    public String getName() {
        return "Container information";
    }
    
    public boolean dontUseTemplate() {
        return chkNoTemplate.isSelected();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblContainerClass = new javax.swing.JLabel();
        cmbContainerClass = new javax.swing.JComboBox();
        lblCOntainerTemplate = new javax.swing.JLabel();
        cmbContainerTemplate = new javax.swing.JComboBox();
        lblContainerName = new javax.swing.JLabel();
        txtContainerName = new javax.swing.JTextField();
        chkNoTemplate = new javax.swing.JCheckBox();

        org.openide.awt.Mnemonics.setLocalizedText(lblContainerClass, org.openide.util.NbBundle.getMessage(NewContainerVisualPanel1.class, "NewContainerVisualPanel1.lblContainerClass.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(lblCOntainerTemplate, org.openide.util.NbBundle.getMessage(NewContainerVisualPanel1.class, "NewContainerVisualPanel1.lblCOntainerTemplate.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(lblContainerName, org.openide.util.NbBundle.getMessage(NewContainerVisualPanel1.class, "NewContainerVisualPanel1.lblContainerName.text")); // NOI18N

        txtContainerName.setText(org.openide.util.NbBundle.getMessage(NewContainerVisualPanel1.class, "NewContainerVisualPanel1.txtContainerName.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(chkNoTemplate, org.openide.util.NbBundle.getMessage(NewContainerVisualPanel1.class, "NewContainerVisualPanel1.chkNoTemplate.text")); // NOI18N
        chkNoTemplate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkNoTemplateActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblContainerName)
                        .addGap(44, 44, 44)
                        .addComponent(txtContainerName, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblContainerClass)
                            .addComponent(lblCOntainerTemplate))
                        .addGap(23, 23, 23)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(chkNoTemplate)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(cmbContainerClass, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(cmbContainerTemplate, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addContainerGap(66, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(54, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblContainerName)
                    .addComponent(txtContainerName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblContainerClass)
                    .addComponent(cmbContainerClass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCOntainerTemplate)
                    .addComponent(cmbContainerTemplate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(chkNoTemplate)
                .addGap(76, 76, 76))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void chkNoTemplateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkNoTemplateActionPerformed
        cmbContainerTemplate.setEnabled(!chkNoTemplate.isSelected());
    }//GEN-LAST:event_chkNoTemplateActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox chkNoTemplate;
    private javax.swing.JComboBox cmbContainerClass;
    private javax.swing.JComboBox cmbContainerTemplate;
    private javax.swing.JLabel lblCOntainerTemplate;
    private javax.swing.JLabel lblContainerClass;
    private javax.swing.JLabel lblContainerName;
    private javax.swing.JTextField txtContainerName;
    // End of variables declaration//GEN-END:variables

}
