/*
 *  Copyright 2010 Charles Edward Bedon Cortazar <charles.bedon@zoho.com>.
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
package org.inventory.core.authentication;

import javax.swing.JTextField;

/**
 * This panel shows the form with the connection settings (server, port and WSDL path)
 * @author Charles Edward Bedon Cortazar <charles.bedon@zoho.com>
 */
public class ConnectionSettingsPanel extends javax.swing.JPanel {

    /** Creates new form ConnectionSettingsPanel */
    public ConnectionSettingsPanel() {
        initComponents();
        
        //Let's hide 'em by now
        btnSaveConfiguration.setVisible(false);
        btnTestConnection.setVisible(false);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnTestConnection = new javax.swing.JButton();
        txtServerPort = new javax.swing.JTextField();
        btnSaveConfiguration = new javax.swing.JButton();
        lblServerPort = new javax.swing.JLabel();
        lblServerAddress = new javax.swing.JLabel();
        txtServerAddress = new javax.swing.JTextField();
        lblWSDLPath = new javax.swing.JLabel();
        txtWSDLPath = new javax.swing.JTextField();

        btnTestConnection.setText(org.openide.util.NbBundle.getMessage(ConnectionSettingsPanel.class, "ConnectionSettingsPanel.btnTestConnection.text")); // NOI18N

        txtServerPort.setText(org.openide.util.NbBundle.getMessage(ConnectionSettingsPanel.class, "ConnectionSettingsPanel.txtServerPort.text")); // NOI18N

        btnSaveConfiguration.setText(org.openide.util.NbBundle.getMessage(ConnectionSettingsPanel.class, "ConnectionSettingsPanel.btnSaveConfiguration.text")); // NOI18N

        lblServerPort.setText(org.openide.util.NbBundle.getMessage(ConnectionSettingsPanel.class, "ConnectionSettingsPanel.lblServerPort.text")); // NOI18N

        lblServerAddress.setText(org.openide.util.NbBundle.getMessage(ConnectionSettingsPanel.class, "ConnectionSettingsPanel.lblServerAddress.text")); // NOI18N

        txtServerAddress.setText(org.openide.util.NbBundle.getMessage(ConnectionSettingsPanel.class, "ConnectionSettingsPanel.txtServerAddress.text")); // NOI18N

        lblWSDLPath.setText(org.openide.util.NbBundle.getMessage(ConnectionSettingsPanel.class, "ConnectionSettingsPanel.lblWSDLPath.text")); // NOI18N

        txtWSDLPath.setText(org.openide.util.NbBundle.getMessage(ConnectionSettingsPanel.class, "ConnectionSettingsPanel.txtWSDLPath.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnTestConnection)
                        .addGap(18, 18, 18)
                        .addComponent(btnSaveConfiguration))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lblWSDLPath)
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lblServerAddress)
                                .addComponent(lblServerPort))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtWSDLPath, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtServerPort)
                                    .addComponent(txtServerAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblServerAddress)
                    .addComponent(txtServerAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblServerPort)
                    .addComponent(txtServerPort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtWSDLPath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblWSDLPath))
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSaveConfiguration)
                    .addComponent(btnTestConnection))
                .addContainerGap(21, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSaveConfiguration;
    private javax.swing.JButton btnTestConnection;
    private javax.swing.JLabel lblServerAddress;
    private javax.swing.JLabel lblServerPort;
    private javax.swing.JLabel lblWSDLPath;
    private javax.swing.JTextField txtServerAddress;
    private javax.swing.JTextField txtServerPort;
    private javax.swing.JTextField txtWSDLPath;
    // End of variables declaration//GEN-END:variables


    /**
     * Retrieves the server address
     * @return a string with an IP or a canonical name
     */
    public String getServerAddress(){
        return txtServerAddress.getText();
    }

    /**
     * Retrieves the port number. Defaults to 8080 if the text is misformatted
     * @return an integer with a valid port number
     */
    public int getServerPort(){
        try{
            int res = Integer.valueOf(txtServerPort.getText());
            if (res > 65535 || res < 1)
                return 8080;
            return res;
        }catch (NumberFormatException nfe){
            return 8080;
        }
    }

    /**
     * Retrieves the WSDL path from the root of the application server
     * @return a String with the path
     */
    public String getWSDLPath(){
        return txtWSDLPath.getText();
    }

    /* Getters */
    public JTextField getTxtServerAddress() {
        return txtServerAddress;
    }

    public JTextField getTxtServerPort() {
        return txtServerPort;
    }

    public JTextField getTxtWSDLPath() {
        return txtWSDLPath;
    }
}