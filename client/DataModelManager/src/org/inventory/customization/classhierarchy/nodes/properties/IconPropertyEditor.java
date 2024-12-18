/*
 *  Copyright 2010-2020 Neotropic SAS <contact@neotropic.co>
 *
 *  Licensed under the EPL License, Version 1.0 (the "License");
 *  you may not use this file except in compliance with the License
 *  You may obtain a copy of the License at
 *
 *       http://www.eclipse.org/legal/epl-v10.html
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.inventory.customization.classhierarchy.nodes.properties;

import org.inventory.core.services.api.imports.filters.ImageFileFilter;
import java.awt.Component;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyEditorSupport;
import java.io.IOException;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import org.inventory.communications.CommunicationsStub;
import org.inventory.communications.core.caching.Cache;
import org.inventory.communications.util.Constants;
import org.inventory.communications.util.Utils;
import org.inventory.core.services.api.notifications.NotificationUtil;
import org.inventory.core.services.i18n.I18N;
import org.openide.explorer.propertysheet.ExPropertyEditor;
import org.openide.explorer.propertysheet.PropertyEnv;

/**
 * Provides a custom property editor for icon and small-icon.
 * @author Adrian Martinez Molina {@literal <adrian.martinez@kuwaiba.org>}
 */
public class IconPropertyEditor extends PropertyEditorSupport
    implements ExPropertyEditor{
       
    /**
     * Reference to the communications stub singleton
     */
    private CommunicationsStub com;
    
    private byte[] icon = null;
        
    private long id;
    
    private String attribute;
    
    private int maxAllowedSize;
    
    private InnerPanel myPanel;
        
    public IconPropertyEditor(long id, String attribute) {
        this.id = id;
        this.attribute = attribute;
        this.com = CommunicationsStub.getInstance();
    }

    @Override
    public String getAsText() {
        return I18N.gm("click_to_choose_file");
    }
    
    @Override
    public Component getCustomEditor(){
       if (myPanel == null){
            myPanel = new InnerPanel();
            maxAllowedSize = attribute.equals(Constants.PROPERTY_ICON) ? 32 : 16;
       }
       return myPanel;
    }
    
    @Override
    public boolean supportsCustomEditor(){
        return true;
    }
    
    public void captureImage(JFileChooser fChooser){
        if(fChooser.showOpenDialog(fChooser)  == JFileChooser.APPROVE_OPTION){ 
            Image myIcon = Toolkit.getDefaultToolkit().createImage(fChooser.getSelectedFile().getAbsolutePath());
            if (myIcon == null)
                NotificationUtil.getInstance().showSimplePopup(I18N.gm("error"), NotificationUtil.ERROR_MESSAGE, String.format(I18N.gm("image_not_loaded"), fChooser.getSelectedFile().getAbsolutePath()));
            else{
                // The width and height take a little time in be set
                while (myIcon.getWidth(null) == -1 && myIcon.getHeight(null) == -1) {}
                
                if((myIcon.getHeight(null) > maxAllowedSize) || (myIcon.getWidth(null) > maxAllowedSize)) //Images have limits depending of if you need to set "icon" or "smallIcon"
                    NotificationUtil.getInstance().showSimplePopup(I18N.gm("error"), NotificationUtil.ERROR_MESSAGE, String.format(I18N.gm("image_exceeds_limits")));
                else{
                    try {
                        icon = Utils.getByteArrayFromFile(fChooser.getSelectedFile());
                        myPanel.updateIcon();
                        //This should be here but in the setValue method, however I haven't discovered yet why clicking on "cancel" still calls setValue
                        if (attribute.equals(Constants.PROPERTY_ICON)){
                            if(!com.setClassMetadataProperties(id, null, null, null, null, icon, -1, null, null, null, null))
                                NotificationUtil.getInstance().showSimplePopup(I18N.gm("error"), NotificationUtil.ERROR_MESSAGE, com.getError());
                            else
                                Cache.getInstace().resetAll();
                        }else{
                            if(!com.setClassMetadataProperties(id, null, null, null, icon, null, -1, null, null, null, null))
                                NotificationUtil.getInstance().showSimplePopup(I18N.gm("error"), NotificationUtil.ERROR_MESSAGE, com.getError());
                            else
                                Cache.getInstace().resetAll();
                        }
                    } catch (IOException ex) {
                        icon = null;
                        NotificationUtil.getInstance().showSimplePopup(I18N.gm("error"), NotificationUtil.ERROR_MESSAGE, String.format(I18N.gm("file_could_not_be_converted"), ex.getMessage()));
                    }
                }
            }
        }
    }
           
    @Override
    public void attachEnv(PropertyEnv pe) {
       //Here comes the code to access the property using us
    }  
    
    private class InnerPanel extends JPanel{
        private JFileChooser fChooser;
        private JLabel lblText;
        private JButton btnImageChooser;
        
        public InnerPanel() {
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setBorder(new EmptyBorder(10, 10, 10, 10) );
            fChooser = new JFileChooser();
            fChooser.setAcceptAllFileFilterUsed(false);
            fChooser.setFileFilter(new ImageFileFilter());
            fChooser.setMultiSelectionEnabled(false);

            lblText = new JLabel(I18N.gm("select_image_with_proper_size"));
            
            btnImageChooser = new JButton(I18N.gm("browse"));

            btnImageChooser.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    captureImage(fChooser);
                }
            });
            
            lblText.setAlignmentX(CENTER_ALIGNMENT);
            add(lblText);
            btnImageChooser.setAlignmentX(CENTER_ALIGNMENT);
            add(btnImageChooser);
        }
        
        public void updateIcon() {
            btnImageChooser.setIcon(new ImageIcon(Utils.getIconFromByteArray(icon, 
                    Utils.DEFAULT_ICON_COLOR, Utils.DEFAULT_ICON_WIDTH, Utils.DEFAULT_ICON_HEIGHT)));
        }
    }
}