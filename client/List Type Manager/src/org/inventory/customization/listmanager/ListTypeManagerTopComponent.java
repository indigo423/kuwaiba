/**
 * Copyright 2010-2020 Neotropic SAS <contact@neotropic.co>.
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
package org.inventory.customization.listmanager;

import javax.swing.ActionMap;
import org.inventory.core.services.api.behaviors.Refreshable;
import org.inventory.core.services.api.notifications.NotificationUtil;
import org.inventory.customization.listmanager.nodes.ListTypeChildren;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.explorer.ExplorerManager;
import org.openide.explorer.ExplorerUtils;
import org.openide.explorer.view.BeanTreeView;
import org.openide.nodes.AbstractNode;
import org.openide.windows.TopComponent;
import org.openide.util.NbBundle.Messages;
import org.openide.windows.Mode;
import org.openide.windows.WindowManager;

/**
 * List Type Manager Top component.
 * @author Charles Edward Bedon Cortazar {@literal <charles.bedon@kuwaiba.org>}
 */
@ConvertAsProperties(
        dtd = "-//org.inventory.customization.listmanager//ListTypeManager//EN",
        autostore = false
)
@TopComponent.Description(
        preferredID = "ListTypeManagerTopComponent",
        iconBase = "org/inventory/customization/listmanager/res/icon.png",
        persistenceType = TopComponent.PERSISTENCE_NEVER
)
@TopComponent.Registration(mode = "explorer", openAtStartup = false)
@ActionID(category = "Window", id = "org.inventory.customization.listmanager.ListTypeManagerTopComponent")
@ActionReferences(value = {@ActionReference(path = "Menu/Tools/Administration"),
    @ActionReference(path = "Toolbars/04_Customization", position = 3)})
@TopComponent.OpenActionRegistration(
        displayName = "#CTL_ListTypeManagerAction",
        preferredID = "ListTypeManagerTopComponent"
)
@Messages({
    "CTL_ListTypeManagerAction=List Type Manager",
    "CTL_ListTypeManagerTopComponent=List Type Manager",
    "HINT_ListTypeManagerTopComponent=Manage the list-type kind of attributes"
})
public final class ListTypeManagerTopComponent extends TopComponent implements ExplorerManager.Provider, Refreshable {
    static final String ROOT_ICON_PATH = "org/inventory/customization/listmanager/res/root.png";
    
    private final ExplorerManager em = new ExplorerManager();
    private BeanTreeView bt;
    private ListManagerService lms;
    
    public ListTypeManagerTopComponent() {
        initComponents();
        initCustomComponents();
        setName(Bundle.CTL_ListTypeManagerTopComponent());
        setToolTipText(Bundle.HINT_ListTypeManagerTopComponent());
        associateLookup(ExplorerUtils.createLookup(em, new ActionMap()));
    }
    
    public void initCustomComponents(){
        bt = new BeanTreeView();
        lms = new ListManagerService(this);
        add(bt);
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
        AbstractNode root = new AbstractNode(new ListTypeChildren());        
        root.setIconBaseWithExtension(ROOT_ICON_PATH);
        em.setRootContext(root);
        em.getRootContext().setDisplayName("List Types");
    }

    @Override
    public void componentClosed() {
        lms.refreshLists();
        em.getRootContext().getChildren().remove(em.getRootContext().getChildren().getNodes());
        //Workaround, because when you close a TC whose mode is "explorer" and open it again,
        //it docks as "explorer". This forces the TC to be always docked "explorer"
        Mode myMode = WindowManager.getDefault().findMode("explorer"); //NOI18N
        myMode.dockInto(this);
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

    public NotificationUtil getNotifier(){
        return NotificationUtil.getInstance();
    }

    @Override
    public void refresh() {
        componentClosed();
        componentOpened();
    }
}
