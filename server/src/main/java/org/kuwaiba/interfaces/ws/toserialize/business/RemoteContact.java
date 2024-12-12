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
package org.kuwaiba.interfaces.ws.toserialize.business;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import org.kuwaiba.apis.persistence.business.Contact;
import org.kuwaiba.apis.persistence.util.StringPair;

/**
 * Wrapper of Contact
 * @author Charles Edward Bedon Cortazar {@literal <charles.bedon@kuwaiba.org>}
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class RemoteContact extends RemoteObject {
    /**
     * The company the current contact belongs to
     */
    private RemoteObjectLight customer;

    /**
     * Default constructor. Only used by the WS layer provider
     */
    public RemoteContact() {  }

    /**
     * Default functional constructor
     * @param contact The object to be serialized
     */
    public RemoteContact(Contact contact){
        super(contact);
        this.customer = new RemoteObjectLight(contact.getCustomer());
    }

    public RemoteObjectLight getCustomer() {
        return customer;
    }

    public void setCustomer(RemoteObjectLight customer) {
        this.customer = customer;
    }
    
    public String getAttributeValue(String attributeName) {
        for (StringPair attributePair : getAttributes()) {
            if (attributePair.getKey().equals(attributeName))
                return attributePair.getValue();
        }
        
        return null;
    }
}
