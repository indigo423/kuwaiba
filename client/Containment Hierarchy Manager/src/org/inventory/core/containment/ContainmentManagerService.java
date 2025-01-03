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
package org.inventory.core.containment;

import java.util.ArrayList;
import java.util.List;
import org.inventory.communications.CommunicationsStub;
import org.inventory.communications.core.LocalClassMetadata;
import org.inventory.communications.core.LocalClassMetadataLight;
import org.inventory.core.services.api.notifications.NotificationUtil;
import org.inventory.core.services.i18n.I18N;

/**
 * Contains the business logic for the related TopComponent
 * @author Charles Edward Bedon Cortazar {@literal <charles.bedon@kuwaiba.org>}
 */
public class ContainmentManagerService {

    private ContainmentManagerTopComponent hctc;

    private final List<LocalClassMetadataLight> treeModel;
    private final List<LocalClassMetadataLight> listModel;
    private final CommunicationsStub com = CommunicationsStub.getInstance();
    

    public ContainmentManagerService(ContainmentManagerTopComponent hctc){
        this.hctc = hctc;
        listModel = new ArrayList<>();
        treeModel = new ArrayList<>();
    }

    public final void updateModels(){
        listModel.clear();
        treeModel.clear();
        LocalClassMetadataLight[] allMeta;
        allMeta = com.getAllLightMeta(false);
        if (allMeta==null)
           hctc.getNotifier().showSimplePopup(I18N.gm("error"), NotificationUtil.ERROR_MESSAGE, com.getError());
        else{
            //Build the lstClasses model, made out of all metadata
            //and the bTreeView model, made out of allMeta minus the abstract classes
            //(RootObject, ConfigurationItem, GenericXXX, etc)
            //LocalClassMetadata rootClass = new LocalClassMetadata();
            //Add the root first
            treeModel.add(new LocalClassMetadata());

            for (LocalClassMetadataLight item : allMeta){
                listModel.add(item);

                if (!item.isAbstract())
                    treeModel.add(item);
            }
        }
    }
    
    public List<LocalClassMetadataLight> getListModel(){
        return listModel;
    }

    public List<LocalClassMetadataLight> getTreeModel(){
        return treeModel;
    }
}