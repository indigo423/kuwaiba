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
package org.inventory.core.authentication;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.Properties;
import javax.swing.JButton;
import org.inventory.communications.CommunicationsStub;
import org.inventory.core.services.GenericCacheInitializer;
import org.inventory.core.services.i18n.I18N;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.LifecycleManager;
import org.openide.modules.ModuleInstall;
import org.openide.util.Lookup;
import org.openide.windows.WindowManager;

/**
 * This installer shows the login window
 * TODO: Inject the user profile into the global lookup
 * @author Charles Edward Bedon Cortazar {@literal <charles.bedon@kuwaiba.org>}
 */
public class Installer extends ModuleInstall {
    private AuthenticationPanel pnlAuthentication;
    private DialogDescriptor dd;
    private JButton btnConnectionSettings;
    private ConnectionSettingsPanel connSettings;
    
    private String oldHost;
    private String oldWSDLPath;
    private int oldPort;
    private boolean oldProtocol; //true if HTTPS is enable

    @Override
    public void restored() {
      Properties defaultSettings = readProperties();
      pnlAuthentication = new AuthenticationPanel(defaultSettings);
      connSettings = new ConnectionSettingsPanel(defaultSettings);
      btnConnectionSettings = new JButton(I18N.gm("connection_settings"));
      
      dd = new DialogDescriptor(pnlAuthentication, I18N.gm("login_window"), true, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                //Surprisingly, pressing the "OK" button doesn't fire a property change, but
                //only an action event
                if (e.getSource().equals(DialogDescriptor.OK_OPTION)){
                    //This is done instead of the older approach to prompt for the user credentials
                    //again in case of error (versions 0.3 beta and earlier). With the past approach, a new
                    //Dialog was created every time, creating a new window over the past ones
                    //With this approach, errors are painted in the same dialog, but we have to tell the
                    //descriptor to not close if something goes wrong
                    if (connect())
                        dd.setClosingOptions(new Object[]{DialogDescriptor.OK_OPTION, DialogDescriptor.CANCEL_OPTION, DialogDescriptor.CLOSED_OPTION});
                    else
                        dd.setClosingOptions(new Object[]{DialogDescriptor.CANCEL_OPTION, DialogDescriptor.CLOSED_OPTION});
                }else {
                    if (e.getSource().equals(btnConnectionSettings)) {
                        oldHost = connSettings.getServerAddress();
                        oldWSDLPath = connSettings.getWSDLPath();
                        oldPort = connSettings.getServerPort();
                        oldProtocol = connSettings.isSecureConnection();

                        DialogDescriptor myDialog = new DialogDescriptor(connSettings, I18N.gm("connection_settings"));
                        myDialog.setModal(true);
                        myDialog.addPropertyChangeListener(new PropertyChangeListener() {
                            @Override
                            public void propertyChange(PropertyChangeEvent evt) {
                                if(evt.getNewValue() == DialogDescriptor.CANCEL_OPTION ||
                                        evt.getNewValue() == DialogDescriptor.CLOSED_OPTION){
                                    connSettings.setServerAddress(oldHost);
                                    connSettings.setWSDLPath(oldWSDLPath);
                                    connSettings.setProtocol(oldProtocol);
                                    connSettings.setServerPort(oldPort);
                                }
                            }
                        });
                        DialogDisplayer.getDefault().notifyLater(myDialog);
                    }
                }
            }
        });
      dd.setAdditionalOptions(new JButton[]{btnConnectionSettings});
      dd.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if(evt.getNewValue() == DialogDescriptor.CANCEL_OPTION ||
                        evt.getNewValue() == DialogDescriptor.CLOSED_OPTION)
                    LifecycleManager.getDefault().exit();
            }
        });
        dd.setClosingOptions(new Object[] { DialogDescriptor.CANCEL_OPTION });
        DialogDisplayer.getDefault().notifyLater(dd);
    }

    public boolean connect(){
        try {
            CommunicationsStub.setServerURL(
                    new URL(connSettings.isSecureConnection()? "https" : "http", connSettings.getServerAddress(), connSettings.getServerPort(), //NOI18N
                    connSettings.getWSDLPath()));

        } catch (MalformedURLException ex) {
            showExceptions(I18N.gm("malformed_url") + ex.getMessage());
            return false;
        }
        try {
            if (!CommunicationsStub.getInstance().createSession(pnlAuthentication.getTxtUser().getText(), 
                    new String(pnlAuthentication.getTxtPassword().getPassword()), connSettings.getHostVerification())){
               showExceptions(CommunicationsStub.getInstance().getError());
               return false;
            }else{
                writeProperties(pnlAuthentication.getTxtUser().getText(), connSettings.getServerAddress(), 
                        connSettings.getServerPort(), connSettings.getWSDLPath(), connSettings.isSecureConnection());
                //The title can't be set directly since it's overwritten right after the startup. We have to wait till the window is open
                WindowManager.getDefault().getMainWindow().addWindowListener(new WindowListener() {

                    @Override
                    public void windowOpened(WindowEvent e) {
                            WindowManager.getDefault().getMainWindow().setTitle(String.format("Kuwaiba Open Network Inventory - [%s - %s] - %s",
                                CommunicationsStub.getInstance().getSession().getUsername(), 
                                CommunicationsStub.getServerURL().getHost(),
                                connSettings.isSecureConnection() ? I18N.gm("secure_connection") : I18N.gm("insecure_connection")));
                            
                            for (GenericCacheInitializer cacheInitializer : Lookup.getDefault().lookupAll(GenericCacheInitializer.class)) {
                                cacheInitializer.initCache();
                            }
                    }

                    @Override
                    public void windowClosing(WindowEvent e) {  }

                    @Override
                    public void windowClosed(WindowEvent e) { }

                    @Override
                    public void windowIconified(WindowEvent e) {  }

                    @Override
                    public void windowDeiconified(WindowEvent e) {  }

                    @Override
                    public void windowActivated(WindowEvent e) { }

                    @Override
                    public void windowDeactivated(WindowEvent e) { }
                });
            }
        }catch(Exception exp){
            CommunicationsStub.resetInstance();
            showExceptions(exp.getMessage());
            return false;
        }
        return true;
    }

    private void showExceptions(String errorText){
        if (errorText == null)
            errorText = I18N.gm("unknown_error");
        //If the message is too long we better display it on a JOPtionPane
        if (errorText.length() > 50)
            pnlAuthentication.getLblError().setText(I18N.gm("error_further_details"));
        else
            pnlAuthentication.getLblError().setText(errorText);

        pnlAuthentication.setDetailedError(errorText);
        pnlAuthentication.getLblError().setVisible(true);
    }

    @Override
    public boolean closing() {
        CommunicationsStub.getInstance().closeSession();
        return true;
    }

    private void writeProperties(String userName, String serverAddress, int serverPort, String wsdlPath, boolean protocol) {
        FileOutputStream output = null;
        try{
            output = new FileOutputStream(System.getProperty("user.dir") + "/.properties"); //NOI18N
            Properties loginProperties = new Properties();
            loginProperties.put("user", userName); //NOI18N
            loginProperties.put("address", serverAddress); //NOI18N
            loginProperties.put("port", String.valueOf(serverPort)); //NOI18N
            loginProperties.put("protocol", String.valueOf(protocol)); //NOI18N
            loginProperties.put("path", wsdlPath); //NOI18N
            loginProperties.store(output, "Last login: " + Calendar.getInstance().getTimeInMillis());
            output.close();
        }catch(IOException e){
            if (output != null)
                try{ output.close();} catch(IOException ex){} //Do nothing if it fails
        }
    }
    
    private Properties readProperties(){
        FileInputStream input = null;
        try{
            input = new FileInputStream(System.getProperty("user.dir") + "/.properties"); //NOI18N
            Properties loginProperties = new Properties();
            loginProperties.load(input);
            input.close();
            return loginProperties;
        }catch (IOException e) {
            if (input != null)
                try{ input.close();} catch(IOException ex){} //Do nothing if it fails
            return null;
        }
    }

}
