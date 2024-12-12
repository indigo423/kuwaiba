/*
 *  Copyright 2010-2019 Neotropic SAS <contact@neotropic.co>.
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

package com.neotropic.kuwaiba.sync.connectors.ssh.bdi.entities;

import com.neotropic.kuwaiba.sync.model.AbstractDataEntity;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity class that represents a Cisco's bridge domain
 * @author Charles Edward Bedon Cortazar {@literal <charles.bedon@kuwaiba.org>}
 */
public class BridgeDomain extends AbstractDataEntity {
    /**
     * List of network interfaces associated to the bridge domain
     */
    private List<NetworkInterface> networkInterfaces;

    public BridgeDomain(String name) {
        this.name = name;
        this.networkInterfaces = new ArrayList<>();
    }

    public List<NetworkInterface> getNetworkInterfaces() {
        return networkInterfaces;
    }

    public void setNetworkInterfaces(List<NetworkInterface> networkInterfaces) {
        this.networkInterfaces = networkInterfaces;
    }

    @Override
    public DATA_TYPE getType() {
        return DATA_TYPE.JAVA_OBJECT;
    }
}
