/*
 *  Copyright 2010-2020 Neotropic SAS <contact@neotropic.co>
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
package org.kuwaiba.management.services;

import org.kuwaiba.management.services.nodes.ServiceManagerRootNode;

/**
 * Service Manager Service
 * @author Charles Edward Bedon Cortazar {@literal <charles.bedon@kuwaiba.org>}
 */
public class ServiceManagerService {
    private ServiceManagerTopComponent smtc;

    public ServiceManagerService(ServiceManagerTopComponent smtc) {
        this.smtc = smtc;
    }
    
    public void setTreeRoot(){
        smtc.getExplorerManager().setRootContext(new ServiceManagerRootNode(
                        new ServiceManagerRootNode.ServiceManagerRootChildren()));
    }
    
}
