/*
 *  Copyright 2010-2019 Neotropic SAS <contact@neotropic.co>.
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
package com.neotropic.kuwaiba.syncMigration;

import com.neotropic.kuwaiba.syncMigration.core.DbManagement;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import java.io.File;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class SyncMigrationApplication implements CommandLineRunner {

	private static DbManagement dbManagement;
	// Main driver method
	public static void main(String[] args) {
		SpringApplication.run(SyncMigrationApplication.class, args);
	}

	// run() method for springBootApplication to execute
	@Override
	public void run(String args[]) throws Exception	{
		if (args.length == 2) {
			try {

				String DB_FULL_PATH = (args[0]);
				String DB_NAME = (args[1]);

				dbManagement = new DbManagement(DB_FULL_PATH + File.separator + DB_NAME);
				dbManagement.createDefaultTemplates();
				dbManagement.updateNodes();
				dbManagement.shutDown();
			} catch (Exception e) {

				System.out.println("ERROR :" + e);

			}
		} else {
			System.out.println("Argument needed are 3 but recived " + args.length);
			System.out.println("1. Argument: database name ");
			System.out.println("2. Argument: path to create database ");
			System.out.println("Example :");

			System.out.println("java -jar syncMigration /home/myuser/data mydbKuwaiba");
			System.exit(1);
		}
	}
}
