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
package com.neotropic.kuwaiba.syncMigration.helpers;

/**
 * Constants needed for whole program, majority correspond to labels in database
 * @author Hardy Ryan Chingal Martinez {@literal <ryan.chingal@kuwaiba.org>}
 * created on 28/09/2022-12:33
 */
public enum Constants {
    LABEL_INVENTORY_OBJECTS("inventoryObjects"),//NOI18N
    LABEL_SYNCDSCOMMON("syncDatasourceCommonProperties"),//NOI18N
    LABEL_SYNCDSCONFIG("syncDatasourceConfiguration"),//NOI18N
    LABEL_TEMPLATE_DATASOURCE("templateDataSource"), //NOI18N
    LABEL_OTHER("OTHER"),//NOI18N
    LABEL_SSH("SSH"),//NOI18N
    LABEL_SNMP("SNMP"),//NOI18N
    LABEL_HTTP("HTTP"),//NOI18N
    PROPERTY_DEVICE_ID("deviceId"),//NOI18N
    PROPERTY_NAME("name"),//NOI18N
    PROPERTY_DESCRIPTION("description"),//NOI18N
    PROPERTY_PASSWORD("password"),//NOI18N
    PROPERTY_PORT("port"),//NOI18N
    PROPERTY_IP("ip"),//NOI18N
    PROPERTY_USER("user"),//NOI18N
    PROPERTY_COMMUNITY("community"),//NOI18N
    PROPERTY_CONTACT("contact"),//NOI18N
    PROPERTY_LOCATION("location"),//NOI18N
    PROPERTY_HTTP("http"),//NOI18N
    PROPERTY_DATASOURCE_TYPE("dataSourceType")
    ;

    private final String value;

    Constants(String value) {
        this.value = value;
    }

    /**
     * Get constant value as string
     * @return value of enum as string
     */
    public String getValue() {
        return value;
    }
}
