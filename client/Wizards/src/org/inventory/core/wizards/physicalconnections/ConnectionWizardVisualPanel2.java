/*
 *  Copyright 2010-2014 Neotropic SAS <contact@neotropic.co>.
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
package org.inventory.core.wizards.physicalconnections;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

public final class ConnectionWizardVisualPanel2 extends JPanel {

    /** Creates new form ConnectionWizardVisualPanel2 */
    public ConnectionWizardVisualPanel2() {
        initComponents();
        spnNumberOfChildren.setModel(new SpinnerNumberModel(0, 0, 1000, 1));
    }

    @Override
    public String getName() {
        return "Edit connection settings";
    }

    public JComboBox getCmbConnectionType() {
        return cmbConnectionType;
    }
    
    public JComboBox getCmbChildrenType() {
        return cmbChildrenType;
    }

    public JTextField getTxtName() {
        return txtName;
    }

    public JSpinner getSpnNumberOfChildren() {
        return spnNumberOfChildren;
    }

    public void hideLinksRelatedInfo() {
        this.spnNumberOfChildren.setVisible(false);
        this.cmbChildrenType.setVisible(false);
        this.lblChildrenType.setVisible(false);
        this.lblNumberOfchildren.setVisible(false);
    }
    
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblName = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        lblConnectionType = new javax.swing.JLabel();
        cmbConnectionType = new javax.swing.JComboBox();
        lblChildrenType = new javax.swing.JLabel();
        lblNumberOfchildren = new javax.swing.JLabel();
        cmbChildrenType = new javax.swing.JComboBox();
        spnNumberOfChildren = new javax.swing.JSpinner();

        org.openide.awt.Mnemonics.setLocalizedText(lblName, org.openide.util.NbBundle.getMessage(ConnectionWizardVisualPanel2.class, "ConnectionWizardVisualPanel2.lblName.text")); // NOI18N

        txtName.setText(org.openide.util.NbBundle.getMessage(ConnectionWizardVisualPanel2.class, "ConnectionWizardVisualPanel2.txtName.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(lblConnectionType, org.openide.util.NbBundle.getMessage(ConnectionWizardVisualPanel2.class, "ConnectionWizardVisualPanel2.lblConnectionType.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(lblChildrenType, org.openide.util.NbBundle.getMessage(ConnectionWizardVisualPanel2.class, "ConnectionWizardVisualPanel2.lblChildrenType.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(lblNumberOfchildren, org.openide.util.NbBundle.getMessage(ConnectionWizardVisualPanel2.class, "ConnectionWizardVisualPanel2.lblNumberOfchildren.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblName)
                    .addComponent(lblConnectionType)
                    .addComponent(lblChildrenType)
                    .addComponent(lblNumberOfchildren))
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmbChildrenType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbConnectionType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(spnNumberOfChildren, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(166, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblName)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblConnectionType)
                    .addComponent(cmbConnectionType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblChildrenType)
                    .addComponent(cmbChildrenType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNumberOfchildren)
                    .addComponent(spnNumberOfChildren, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(122, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox cmbChildrenType;
    private javax.swing.JComboBox cmbConnectionType;
    private javax.swing.JLabel lblChildrenType;
    private javax.swing.JLabel lblConnectionType;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblNumberOfchildren;
    private javax.swing.JSpinner spnNumberOfChildren;
    private javax.swing.JTextField txtName;
    // End of variables declaration//GEN-END:variables
}