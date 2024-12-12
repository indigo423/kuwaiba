/**
 *  Copyright 2010-2017 Neotropic SAS <contact@neotropic.co>.
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
package com.neotropic.batchartifacts.listeners;

import java.util.Date;
import javax.batch.api.listener.JobListener;
import javax.batch.runtime.context.JobContext;
import javax.inject.Inject;

/**
 * A batch artifact used to listener the end of the snmpLeafJob
 * @author Johny Andres Ortega Ruiz <johny.ortega@kuwaiba.org>
 */
public class SnmpLeafJobListener implements JobListener {
    
    @Inject
    private JobContext jobContext;

    @Override
    public void beforeJob() throws Exception {
        System.out.println(">>> snmpLeafJob start " + new Date());
    }

    @Override
    public void afterJob() throws Exception {
        System.out.println(">>> snmpLeafJob end " + new Date() + " exit status: " + jobContext.getExitStatus() +  "; " + jobContext.getBatchStatus());
    }
    
}
