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
package org.inventory.core.history;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import org.inventory.communications.CommunicationsStub;
import org.inventory.communications.core.LocalApplicationLogEntry;
import org.inventory.core.services.api.notifications.NotificationUtil;
import org.inventory.core.services.i18n.I18N;
import org.netbeans.swing.etable.ETable;

/**
 * Audit Trail module TC service
 * @author Charles Edward Bedon Cortazar {@literal <charles.bedon@kuwaiba.org>}
 */
public class AuditTrailService {
    private AuditTrailTopComponent component;
    private CommunicationsStub com;
    private int currentPage;
    
    //Actions
    public static final int SHOW_ALL = 1;
    public static final int SHOW_NEXT_PAGE = 2;
    public static final int SHOW_PREVIOUS_PAGE = 3;
    public static final int SHOW_FIRST_PAGE = 4;
    
    public static final int RESULTS_LIMIT = 50;

    public AuditTrailService(AuditTrailTopComponent component) {
        this.component = component;
        currentPage = 1;
        com = CommunicationsStub.getInstance();
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
   
    public void updateTable(int action){
        LocalApplicationLogEntry[] records = new LocalApplicationLogEntry[0];
        switch(action){
            case SHOW_ALL:
                records = com.getGeneralActivityAuditTrail(0, 0);
                break;
            case SHOW_NEXT_PAGE:
                records = com.getGeneralActivityAuditTrail(++currentPage, RESULTS_LIMIT);
                break;
            case SHOW_PREVIOUS_PAGE:
                records = com.getGeneralActivityAuditTrail(--currentPage, RESULTS_LIMIT);
                break;
            case SHOW_FIRST_PAGE:
                records = com.getGeneralActivityAuditTrail(1, RESULTS_LIMIT);
                break;
        }
        
        if (records == null)
            NotificationUtil.getInstance().showSimplePopup(I18N.gm("error"), NotificationUtil.ERROR_MESSAGE, com.getError());
        else{
            AuditTrailTableModel tableModel = new AuditTrailTableModel(records);
            ETable table = component.getTable();
            
            if (table.getModel() instanceof DefaultTableModel)
                table.setModel(tableModel);
            else
                table.setModel(tableModel);
            
            TableColumnModel columnModel = table.getColumnModel();
            
            for (int i = 0; i < tableModel.getColumnCount(); i += 1) {
                if (I18N.gm("old_value").equals(tableModel.getColumnName(i)) || I18N.gm("new_value").equals(tableModel.getColumnName(i)))                
                    columnModel.getColumn(i).setPreferredWidth(81);
            }
        }
    }
}
