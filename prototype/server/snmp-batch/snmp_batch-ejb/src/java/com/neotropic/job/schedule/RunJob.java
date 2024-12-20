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
package com.neotropic.job.schedule;

import com.neotropic.job.runnable.RunnableJob;
import java.util.Properties;
import javax.ejb.Stateless;

/**
 * 
 * @author Johny Andres Ortega Ruiz <johny.ortega@kuwaiba.org>
 */
@Stateless
public class RunJob implements RunJobRemote {
    
    @Override
    public void runSnmpLeafJob(Properties properties) {
        System.out.println(">>> RunJob::runSnmpLeafJob");
        new RunnableJob("snmpLeaf", properties).run();
    }

    @Override
    public void runSnmpTableJob(Properties properties) {
        System.out.println(">>> RunJob::runSnmpTableJob");
        new RunnableJob("snmpTable", properties).run();
    }
}
