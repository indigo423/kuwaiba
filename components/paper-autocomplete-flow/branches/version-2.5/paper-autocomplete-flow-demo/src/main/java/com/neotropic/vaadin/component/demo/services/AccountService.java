/*
 *  Copyright 2010-2020 Neotropic SAS <contact@neotropic.co>.
 *  
 *  Licensed under the Apache License, Version 2.0 (the "License"); 
 *  you may not use this file except in compliance with the License. 
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software 
 *  distributed under the License is distributed on an "AS IS" BASIS, 
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 *  See the License for the specific language governing permissions and 
 *  limitations under the License.
 */
package com.neotropic.vaadin.component.demo.services;

import com.neotropic.vaadin.component.demo.model.Account;
import elemental.json.JsonArray;
import elemental.json.JsonObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Johny Andres Ortega Ruiz {@literal <johny.ortega@kuwaiba.org>}
 */
@Service
public class AccountService {
    @Autowired
    private UtilService utilService;
    
    public List<Account> getAccounts() {
        List<Account> accounts = new ArrayList();
        try {
            JsonObject jsonObject = utilService.getResourceAsJsonObject(
                "json/accounts.json");
            JsonArray jsonAccounts = jsonObject.getArray("accounts");
            for (int i = 0; i < jsonAccounts.length(); i++) {
                JsonObject jsonAccount = jsonAccounts.getObject(i);
                accounts.add(new Account(
                    jsonAccount.getString("companyName"),
                    jsonAccount.getString("accountNumber"),
                    jsonAccount.getString("id"),
                    jsonAccount.getString("email")
                ));
            }
            return accounts;
        } catch (IOException ex) {
            Logger.getLogger(AccountService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return accounts;
    }
}
