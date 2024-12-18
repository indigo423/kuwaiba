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

package org.inventory.queries;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Random;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.xml.stream.XMLStreamException;
import org.inventory.communications.CommunicationsStub;
import org.inventory.communications.core.LocalAttributeMetadata;
import org.inventory.communications.core.LocalClassMetadata;
import org.inventory.communications.core.LocalClassMetadataLight;
import org.inventory.communications.core.LocalObjectLight;
import org.inventory.communications.core.queries.LocalQuery;
import org.inventory.communications.core.queries.LocalQueryLight;
import org.inventory.communications.core.queries.LocalResultRecord;
import org.inventory.communications.core.queries.LocalTransientQuery;
import org.inventory.core.services.api.notifications.NotificationUtil;
import org.inventory.core.services.utils.JComplexDialogPanel;
import org.inventory.queries.scene.QueryEditorNodeWidget;
import org.inventory.queries.scene.QueryEditorScene;
import org.inventory.queries.scene.ClassNodeWidget;
import org.inventory.queries.scene.filters.ListTypeFilter;

/**
 * This class will replace the old QueryManagerService in next releases
 * @author Charles Edward Bedon Cortazar {@literal <charles.bedon@kuwaiba.org>}
 */
public class QueryManagerService implements ActionListener {

    private QueryManagerTopComponent qbtc;
    private CommunicationsStub com = CommunicationsStub.getInstance();
    /**
     * This one has the execution details
     */
    private LocalTransientQuery currentTransientQuery;
    /**
     * This one has the storing details
     */
    private LocalQuery localQuery;
    /**
     * Array containing the query properties set by using the "configure" button 
     * (name, description and share as public)
     */
    private Object[] queryProperties;

    public QueryManagerService(QueryManagerTopComponent qbtc) {
        this.qbtc = qbtc;
        queryProperties = new Object[3];
        resetProperties();
    }

    public LocalClassMetadataLight[] getClassList(){
        LocalClassMetadataLight[] items = com.getAllLightMeta(false);
        if (items == null){
            qbtc.getNotifier().showSimplePopup("Error", NotificationUtil.ERROR_MESSAGE, com.getError());
            return new LocalClassMetadataLight[0];
        }
        return items;
    }

    public LocalClassMetadata getClassDetails(String className){
        LocalClassMetadata res= com.getMetaForClass(className, false);
        if (res == null)
            qbtc.getNotifier().showSimplePopup("Error", NotificationUtil.ERROR_MESSAGE, com.getError());
        return res;
    }

    public LocalResultRecord[] executeQuery(LocalTransientQuery oldTransientQuery) {
        LocalResultRecord[] res = com.executeQuery(oldTransientQuery == null ? currentTransientQuery : oldTransientQuery);
        if (res == null)
            qbtc.getNotifier().showSimplePopup("Error", NotificationUtil.ERROR_MESSAGE, com.getError());
        return res;
    }
    
    public void createQuery(int page){
         LocalTransientQuery temp = qbtc.getQueryScene().getTransientQuery(qbtc.getQueryScene().getCurrentSearchedClass(),
                        qbtc.getChkAnd().isSelected() ? LocalTransientQuery.CONNECTOR_AND : LocalTransientQuery.CONNECTOR_OR,
                        Integer.valueOf(qbtc.getTxtResultLimit().getText()), page, false);
        //this keep the last TransientQuery in case that the top component it been closed
        if(temp != null)
            currentTransientQuery = temp;
        else
            currentTransientQuery.setPage(page);
    }

    public LocalQueryLight[] getQueries(boolean showAll){
        LocalQueryLight[] res = com.getQueries(showAll);
        if (res == null){
            qbtc.getNotifier().showSimplePopup("Error", NotificationUtil.ERROR_MESSAGE, com.getError());
            return null;
        }
        else return res;
    }

    public void saveQuery(){
        currentTransientQuery = qbtc.getQueryScene().getTransientQuery(qbtc.getQueryScene().getCurrentSearchedClass(),
                            qbtc.getChkAnd().isSelected() ? LocalTransientQuery.CONNECTOR_AND : LocalTransientQuery.CONNECTOR_OR,
                            Integer.valueOf(qbtc.getTxtResultLimit().getText()), 0, false);

        if (localQuery == null){ //It's a new query
            
            if (com.createQuery((String)queryProperties[0], currentTransientQuery.toXML(), (String)queryProperties[1], (Boolean)queryProperties[2]) != -1)
                qbtc.getNotifier().showSimplePopup("Success", NotificationUtil.INFO_MESSAGE, "Query created successfully");
            else
                qbtc.getNotifier().showSimplePopup("Error", NotificationUtil.ERROR_MESSAGE, com.getError());
            /*
             * Only for debugging purposes
             try{
                FileOutputStream fos = new FileOutputStream("query.xml");
                fos.write(currentTransientQuery.toXML());
                fos.flush();
                fos.close();
                JOptionPane.showMessageDialog(qbtc, "Query Saved Successfully","Success",JOptionPane.INFORMATION_MESSAGE);
            }catch(IOException e){
                e.printStackTrace();
            }*/
        }else{ //It's an old query. An update is necessary
            localQuery.setName((String)queryProperties[0]);
            localQuery.setStructure(currentTransientQuery.toXML());
            localQuery.setDescription((String)queryProperties[1]);
            localQuery.setPublic((Boolean)queryProperties[2]);

            if (com.saveQuery(localQuery))
                qbtc.getNotifier().showSimplePopup("Success", NotificationUtil.INFO_MESSAGE, "Query saved successfully");
            else
                qbtc.getNotifier().showSimplePopup("Error", NotificationUtil.ERROR_MESSAGE, com.getError());
        }
    }

    public void deleteQuery(){
        if (com.deleteQuery(localQuery.getId())){
            qbtc.getQueryScene().clear();
            qbtc.getQueryScene().setCurrentSearchedClass(null);
            localQuery = null;
            qbtc.getQueryScene().validate();
            qbtc.getCmbClassList().setSelectedItem(null);
            qbtc.getNotifier().showSimplePopup("Success", NotificationUtil.INFO_MESSAGE, "Saved query deleted successfully");
        }else
            qbtc.getNotifier().showSimplePopup("Error", NotificationUtil.ERROR_MESSAGE, com.getError());
    }

    public LocalTransientQuery getCurrentTransientQuery() {
        return currentTransientQuery;
    }

    public LocalQuery getCurrentLocalQuery(){
        return localQuery;
    }

    public Object[] getQueryProperties(){
        return queryProperties;
    }

    public void setQueryProperties(Object[] newProperties){
        queryProperties = newProperties;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JCheckBox insideCheck = (JCheckBox)e.getSource();
        LocalAttributeMetadata lam = (LocalAttributeMetadata)insideCheck.getClientProperty("attribute");
        boolean isParentMenu = false;
        switch (e.getID()){
            case QueryEditorScene.SCENE_FILTERENABLED:
                QueryEditorNodeWidget newNode;
                if (lam.getType().equals(LocalObjectLight.class)){
                    LocalClassMetadataLight myMetadata;
                    if (lam.getName().equals("parent")){ //NOI18N
                        if(qbtc.getQueryScene().getCurrentSearchedClass() != null){
                            List<LocalClassMetadataLight> los = com.getUpstreamContainmentHierarchy(qbtc.getQueryScene().getCurrentSearchedClass().getClassName(), true);
                            if (los == null){
                                qbtc.getNotifier().showSimplePopup("Error", NotificationUtil.ERROR_MESSAGE, com.getError());
                                return;
                            }

                            if (los.isEmpty()){
                                JOptionPane.showMessageDialog(null, "This class is not a possible child to another");
                                return;
                            }

                            JComboBox lstPossibleParentClasses = new JComboBox(los.toArray());
                            lstPossibleParentClasses.setName("possibleParentClasses");
                            JComplexDialogPanel pnlMyDialog = new JComplexDialogPanel(
                                    new String[] {"Select the possible parent class"}, 
                                    new JComponent[] {lstPossibleParentClasses});
                            pnlMyDialog.setLayout(new BoxLayout(pnlMyDialog, BoxLayout.PAGE_AXIS));
                            insideCheck.setEnabled(false);

                            if (JOptionPane.showConfirmDialog(
                                    null,
                                    pnlMyDialog,
                                    "Possible parent filter",
                                    JOptionPane.OK_CANCEL_OPTION,
                                    JOptionPane.QUESTION_MESSAGE) == JOptionPane.CANCEL_OPTION){
                                insideCheck.setSelected(false);
                                insideCheck.setEnabled(true);
                                return;
                            }
                            insideCheck.setEnabled(true);
                            LocalClassMetadataLight selectedValue = (LocalClassMetadataLight)((JComboBox)pnlMyDialog.getComponent("possibleParentClasses")).getSelectedItem();
                            if (selectedValue == null){
                                JOptionPane.showMessageDialog(null, "Searching for objects with a null parent is not supported yet");
                                insideCheck.setSelected(false);
                                return;
                            }
                            myMetadata = com.getMetaForClass(selectedValue.getClassName(),false);
                            isParentMenu = true;
                        }
                        else{
                            int i = 0;
                            for (String attributeName : currentTransientQuery.getAttributeNames()) {
                                if(attributeName.equals("parent"))
                                    break;
                                i++;
                            }
                            myMetadata = com.getMetaForClass(currentTransientQuery.getJoins().get(i).getClassName(),false);
                            isParentMenu = true;
                        }
                    }
                    
                    else
                        myMetadata = com.getMetaForClass((String)insideCheck.getClientProperty("className"),false);
                    
                    LocalClassMetadataLight myMetadataLight;
                    if(!isParentMenu){
                        myMetadataLight = new LocalClassMetadataLight(myMetadata.getId(), 
                        myMetadata.getClassName(), myMetadata.getDisplayName(),
                        myMetadata.getParentName(), myMetadata.isAbstract(), 
                        myMetadata.isViewable(), myMetadata.isListType(), myMetadata.isCustom(), 
                                myMetadata.isInDesign(), null, 0);
                    }
                    else 
                        myMetadataLight = myMetadata;
                    newNode = (ClassNodeWidget)qbtc.getQueryScene().findWidget(myMetadataLight);
                    if (newNode == null){
                        newNode = (QueryEditorNodeWidget) qbtc.getQueryScene().addNode(myMetadataLight);
                        if (newNode instanceof ListTypeFilter)
                            ((ListTypeFilter)newNode).build(com.getList(((ListTypeFilter)newNode).getWrappedClass().getClassName(), true, false));
                        else
                            newNode.build(null);
                        qbtc.getQueryScene().validate();
                    }
                    insideCheck.putClientProperty("related-node", myMetadataLight);
                }else{
                    String newNodeId = lam.getType().getSimpleName()+"_"+new Random().nextInt(10000);
                    newNode = (QueryEditorNodeWidget)qbtc.getQueryScene().addNode(newNodeId);
                    newNode.build(newNodeId);
                    insideCheck.putClientProperty("related-node", newNodeId);
                }

                String edgeName = "Edge_"+new Random().nextInt(1000);
                qbtc.getQueryScene().addEdge(edgeName);
                qbtc.getQueryScene().setEdgeSource(edgeName, lam);
                qbtc.getQueryScene().setEdgeTarget(edgeName, newNode.getDefaultPinId());

                newNode.setPreferredLocation(new Point(insideCheck.getParent().getLocation().x + 200, insideCheck.getParent().getLocation().y));
                break;
            case QueryEditorScene.SCENE_FILTERDISABLED:
                if (insideCheck.getClientProperty("related-node") == null)
                    return;
                qbtc.getQueryScene().removeAllRelatedNodes(insideCheck.getClientProperty("related-node"));
                insideCheck.putClientProperty("related-node",null);
        }
        qbtc.getQueryScene().validate();
    }

    /**
     * Renders a query extracted from the database
     * @param selectedQuery query to be rendered
     */
    public void renderQuery(LocalQueryLight selectedQuery) {
        localQuery = com.getQuery(selectedQuery.getId());
        if (localQuery == null){
            qbtc.getNotifier().showSimplePopup("Error", NotificationUtil.ERROR_MESSAGE, com.getError());
            return;
        }
        try {
            currentTransientQuery = new LocalTransientQuery(localQuery);
            qbtc.getQueryScene().clear();
            ClassNodeWidget rootNode = renderClassNode(currentTransientQuery);
            qbtc.getQueryScene().setCurrentSearchedClass(rootNode.getWrappedClass());
            qbtc.getQueryScene().organizeNodes(rootNode.getWrappedClass(), QueryEditorScene.X_OFFSET, QueryEditorScene.Y_OFFSET);
            qbtc.getQueryScene().validate();
        } catch (XMLStreamException ex) {
            qbtc.getNotifier().showSimplePopup("Error", NotificationUtil.ERROR_MESSAGE, "Error parsing XML file");
            return;
        }
        queryProperties[0] = localQuery.getName();
        queryProperties[1] = localQuery.getDescription();
        queryProperties[2] = localQuery.isPublic();
        
    }

    private ClassNodeWidget renderClassNode(LocalTransientQuery subQuery){
        LocalClassMetadata classMetadata = com.getMetaForClass(subQuery.getClassName(), false);
        if (classMetadata == null){
            qbtc.getNotifier().showSimplePopup("Error", NotificationUtil.ERROR_MESSAGE, com.getError());
            return null;
        }
        ClassNodeWidget currentNode = ((ClassNodeWidget)qbtc.getQueryScene().addNode(classMetadata));
        currentNode.build(null);
        currentNode.setVisibleAttributes(subQuery.getVisibleAttributeNames());

        //Marking the scene to validate is necessary for the newlycreated node to be painted
        //providing the clientArea necessary to calculate locations of new nodes
        qbtc.getQueryScene().validate();
        
        for (LocalTransientQuery join : subQuery.getJoins()){
            if (join != null){
                if (join.getAttributeNames().size() > 0 || join.getVisibleAttributeNames().size() > 0){
                    //if (!join.getAttributeNames().get(0).equals("id")) //NOI18N //In this case, show an expanded class node widget
                    renderClassNode(join);                         //Any other way around show a simplified version
                }
            }
        }
        currentNode.setFilteredAttributes(subQuery.getAttributeNames(), subQuery.getConditions());
        
        return currentNode;
    }

    public void resetLocalQuery(){
        this.localQuery = null;
        resetProperties();
    }

    private void resetProperties() {
        queryProperties[0] = "New Query "+ new Random().nextInt(10000);
        queryProperties[1] = "";
        queryProperties[2] = false; //By default the queries are private
    }
}