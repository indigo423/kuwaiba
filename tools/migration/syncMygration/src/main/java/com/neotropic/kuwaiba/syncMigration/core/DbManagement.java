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
package com.neotropic.kuwaiba.syncMigration.core;

import com.neotropic.kuwaiba.syncMigration.DataSourceProperties;
import com.neotropic.kuwaiba.syncMigration.entities.RelTypes;
import com.neotropic.kuwaiba.syncMigration.entities.SyncDataSourceCommonParameters;
import com.neotropic.kuwaiba.syncMigration.entities.SyncDataSourceConfiguration;
import com.neotropic.kuwaiba.syncMigration.entities.TemplateDataSource;
import com.neotropic.kuwaiba.syncMigration.exceptions.ApplicationObjectNotFoundException;
import com.neotropic.kuwaiba.syncMigration.exceptions.InvalidArgumentException;
import com.neotropic.kuwaiba.syncMigration.helpers.Constants;
import com.neotropic.kuwaiba.syncMigration.helpers.Util;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Result;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Hardy Ryan Chingal Martinez {@literal <ryan.chingal@kuwaiba.org>}
 * created on 28/09/2022-08:43
 */
public class DbManagement {
    private GraphDatabaseService graphDb;
    private final boolean connectionStatus;
    private TemplateDataSource sshTemplate;
    private TemplateDataSource snmpTemplate;
    private TemplateDataSource httpTemplate;
    private TemplateDataSource otherTemplate;

    private static final Label syncDsCommonPropertiesLabel = Label.label(Constants.LABEL_SYNCDSCOMMON.getValue());
    private static final Label syncDatasourceConfigLabel = Label.label(Constants.LABEL_SYNCDSCONFIG.getValue());
    private static final Label templateDataSourceLabel = Label.label(Constants.LABEL_TEMPLATE_DATASOURCE.getValue());
    private static final Label inventoryObjectLabel = Label.label(Constants.LABEL_INVENTORY_OBJECTS.getValue());

    /**
     * Default constructor
     *
     * @param databaseDirectory database path
     */
    public DbManagement(String databaseDirectory) {
        this.connectionStatus = openConnection(databaseDirectory);
    }

    /**
     *
     * @param dbPathString  database path
     * @return true if connection with database are enable and open
     */
    private boolean openConnection(String dbPathString) {
        try {

            //set database path
            File dbFile = new File(dbPathString);
            graphDb = new GraphDatabaseFactory().newEmbeddedDatabase(dbFile);

            if (dbFile.exists() && dbFile.canWrite() && graphDb != null) {
                System.out.println("Data base Connected");
                registerShutdownHook(graphDb);

                return true;
            } else if (!dbFile.exists() || !dbFile.canWrite() && graphDb == null) {
                System.out.println("Path " + dbFile.getAbsolutePath() + " does not exist or is not writeable");
                return false;
            }

        } catch (Exception e) {
            System.out.println("Error opening conection: " + e.getMessage());
        }
        return false;
    }

    /**
     * Registers a shutdown hook for the Neo4j instance so that it shuts down
     * nicely when the VM exits (even if you "Ctrl-C" the running application).
     *
     * @param graphDb neo4j service with basic operation
     */
    private static void registerShutdownHook(final GraphDatabaseService graphDb) {

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                graphDb.shutdown();
            }
        });
    }

    /**
     * Create if not exist a default synchronization templates of data sources
     */
    public void createDefaultTemplates(){
        String sshTemplateNodeSql = "MATCH(data:templateSyncDataSource) WHERE data.name contains 'SSH' RETURN data";
        String snmpTemplateNodeSql = "MATCH(data:templateSyncDataSource) WHERE data.name contains 'SNMP' RETURN data";
        String httpTemplateNodeSql = "MATCH(data:templateSyncDataSource) WHERE data.name contains 'HTTP' RETURN data";
        String otherTemplateNodeSql = "MATCH(data:templateSyncDataSource) WHERE data.name contains 'OTHER' RETURN data";
        if (connectionStatus) {
            try (Transaction tx = graphDb.beginTx()) {
                Result sshTemplatesResult = graphDb.execute(sshTemplateNodeSql);
                if (!sshTemplatesResult.hasNext()) {
                    // SSH templateSyncDataSource {name: "SSH", description: "SSH", IP: "", USER:"", PASSWORD:""
                    // , PORT:"" }
                    Label label = Label.label(Constants.LABEL_TEMPLATE_DATASOURCE.getValue());
                    Node sshTemplateNode = graphDb.createNode(label);
                    sshTemplateNode.setProperty(Constants.PROPERTY_NAME.getValue(),
                            Constants.LABEL_SSH.getValue().toUpperCase());
                    sshTemplateNode.setProperty(Constants.PROPERTY_DESCRIPTION.getValue(),
                            Constants.LABEL_SSH.getValue().toUpperCase());
                    sshTemplateNode.setProperty(Constants.PROPERTY_PASSWORD.getValue().toUpperCase(), "");
                    sshTemplateNode.setProperty(Constants.PROPERTY_PORT.getValue().toUpperCase(), "");
                    sshTemplateNode.setProperty(Constants.PROPERTY_IP.getValue().toUpperCase(), "");
                    sshTemplateNode.setProperty(Constants.PROPERTY_USER.getValue().toUpperCase(), "");
                    sshTemplate = this.createTemplateDataSourceFromNode(sshTemplateNode);
                    System.out.println("SSH tempate in queue to save"); //NOI18N
                } else {
                    Map<String, Object> pointer = sshTemplatesResult.next();
                    Node sshTemplateNode = (Node) pointer.get("data");
                    sshTemplate = this.createTemplateDataSourceFromNode(sshTemplateNode);
                    System.out.println("SSH tempate found "+sshTemplate.getId()); //NOI18N
                }

                Result snmpTemplatesResult = graphDb.execute(snmpTemplateNodeSql);
                if (!snmpTemplatesResult.hasNext()) {
                    // SNMP templateSyncDataSource {name: "SNMP", description: "SNMP", COMMUNITY: "", CONTACT:"", LOCATION:"" }
                    Label label = Label.label(Constants.LABEL_TEMPLATE_DATASOURCE.getValue());
                    Node snmpTemplateNode = graphDb.createNode(label);
                    snmpTemplateNode.setProperty(Constants.PROPERTY_NAME.getValue(),
                            Constants.LABEL_SNMP.getValue().toUpperCase());
                    snmpTemplateNode.setProperty(Constants.PROPERTY_DESCRIPTION.getValue(),
                            Constants.LABEL_SNMP.getValue().toUpperCase());
                    snmpTemplateNode.setProperty(Constants.PROPERTY_COMMUNITY.getValue().toUpperCase(), "");
                    snmpTemplateNode.setProperty(Constants.PROPERTY_CONTACT.getValue().toUpperCase(), "");
                    snmpTemplateNode.setProperty(Constants.PROPERTY_LOCATION.getValue().toUpperCase(), "");
                    snmpTemplate = this.createTemplateDataSourceFromNode(snmpTemplateNode);
                    System.out.println("SNMP tempate in queue to save"); //NOI18N
                } else {
                    Map<String, Object> pointer = snmpTemplatesResult.next();
                    Node snmpTemplateNode = (Node) pointer.get("data");
                    snmpTemplate = this.createTemplateDataSourceFromNode(snmpTemplateNode);
                    System.out.println("SNMP tempate found "+snmpTemplateNode.getId()); //NOI18N
                }

                Result httpTemplatesResult = graphDb.execute(httpTemplateNodeSql);
                if (!httpTemplatesResult.hasNext()) {
                    // HTTP templateSyncDataSource {name: "HTTP", description: "HTTP", IP: "", USER:"", PASSWORD:"" }
                    Label label = Label.label(Constants.LABEL_TEMPLATE_DATASOURCE.getValue());
                    Node httpTemplateNode = graphDb.createNode(label);
                    httpTemplateNode.setProperty(Constants.PROPERTY_NAME.getValue(),
                            Constants.LABEL_HTTP.getValue().toUpperCase());
                    httpTemplateNode.setProperty(Constants.PROPERTY_DESCRIPTION.getValue(),
                            Constants.LABEL_HTTP.getValue().toUpperCase());
                    httpTemplateNode.setProperty(Constants.PROPERTY_HTTP.getValue().toUpperCase(), "");
                    httpTemplateNode.setProperty(Constants.PROPERTY_IP.getValue().toUpperCase(), "");
                    httpTemplateNode.setProperty(Constants.PROPERTY_USER.getValue().toUpperCase(), "");
                    httpTemplateNode.setProperty(Constants.PROPERTY_PASSWORD.getValue().toUpperCase(), "");
                    httpTemplate = this.createTemplateDataSourceFromNode(httpTemplateNode);
                    System.out.println("HTTP tempate in queue to save"); //NOI18N
                } else {
                    Map<String, Object> pointer = httpTemplatesResult.next();
                    Node httpTemplateNode = (Node) pointer.get("data");
                    httpTemplate = this.createTemplateDataSourceFromNode(httpTemplateNode);
                    System.out.println("HTTP tempate found "+httpTemplate.getId()); //NOI18N
                }

                Result otherTemplatesResult = graphDb.execute(otherTemplateNodeSql);
                if (!otherTemplatesResult.hasNext()) {
                    // OTHER templateSyncDataSource {name: "OTHER", description: "OTHER" }
                    Label label = Label.label(Constants.LABEL_TEMPLATE_DATASOURCE.getValue());
                    Node otherTemplateNode = graphDb.createNode(label);
                    otherTemplateNode.setProperty(Constants.PROPERTY_NAME.getValue(),
                            Constants.LABEL_OTHER.getValue().toUpperCase());
                    otherTemplateNode.setProperty(Constants.PROPERTY_DESCRIPTION.getValue(),
                            Constants.LABEL_OTHER.getValue().toUpperCase());
                    otherTemplate = this.createTemplateDataSourceFromNode(otherTemplateNode);
                    System.out.println("OTHER tempate in queue to save"); //NOI18N
                } else {
                    Map<String, Object> pointer = httpTemplatesResult.next();
                    Node httpTemplateNode = (Node) pointer.get("data");
                    httpTemplate = this.createTemplateDataSourceFromNode(httpTemplateNode);
                    System.out.println("OTHER tempate found "+httpTemplate.getId()); //NOI18N
                }
                tx.success();
                System.out.println("Templates loaded successfully"); //NOI18N
            } catch (Exception ex) {
                System.out.println("Error create default template: " + ex.getMessage()); //NOI18N
            }
        }

    }
    public void updateNodes(){
        // utility script
        String propertiesListSql = "MATCH(confs:syncDatasourceConfiguration )\n"
                .concat("	 WHERE NOT (confs)-[:EXTENDS_FROM_TEMPLATE]->(:templateDataSource)\n")
                .concat("	 WITH keys(confs) as dataKeys, confs as dataProperties \n")
                .concat("RETURN dataKeys, dataProperties");

        List<DataSourceProperties> dataSources = new ArrayList<>();
        Result propertiesResult = graphDb.execute(propertiesListSql);
        while (propertiesResult.hasNext()){
            Map<String, Object> pointer = propertiesResult.next();
            List<String> keys = (List<String>) pointer.get("dataKeys");
            Node properties = (Node)pointer.get("dataProperties");
            pointer.get("dataProperties");
            dataSources.add(new DataSourceProperties(keys, properties));
        }


        for (DataSourceProperties sourceProperties: dataSources) {

            if (sourceProperties.getKeys().stream().anyMatch(e -> e.toUpperCase().contains(Constants.LABEL_SSH.getValue().toUpperCase()))){
                if (connectionStatus) {
                    if(sshTemplate == null)
                        System.out.println("Error ssh template not found."); //NOI18N
                    else{
                        try (Transaction tx = graphDb.beginTx()) {
                            //# syncDatasourceConfiguration <id> <name>
                            //# syncDatasourceCommonProperties <id> <dataSourceType> <properties_of_template_data_source>
                            SyncDataSourceConfiguration syncDatasourceConfiguration = new SyncDataSourceConfiguration();
                            SyncDataSourceCommonParameters commonParameters = new SyncDataSourceCommonParameters(Constants.LABEL_SSH.getValue());
                            sourceProperties.getProperties().getPropertyKeys().forEach(item -> {
                                if (item.toUpperCase().contains(Constants.PROPERTY_IP.getValue().toUpperCase())) {
                                    commonParameters.addParameter(Constants.PROPERTY_IP.getValue().toUpperCase(),
                                            (String) sourceProperties.getProperties().getProperty(item));
                                } else if (item.toUpperCase().contains(Constants.PROPERTY_USER.getValue().toUpperCase())) {
                                    commonParameters.addParameter(Constants.PROPERTY_USER.getValue().toUpperCase(),
                                            (String) sourceProperties.getProperties().getProperty(item));
                                } else if (item.toUpperCase().contains(Constants.PROPERTY_PASSWORD.getValue().toUpperCase())) {
                                    commonParameters.addParameter(Constants.PROPERTY_PASSWORD.getValue().toUpperCase(),
                                            (String) sourceProperties.getProperties().getProperty(item));
                                } else if (item.toUpperCase().contains(Constants.PROPERTY_PORT.getValue().toUpperCase())) {
                                    commonParameters.addParameter(Constants.PROPERTY_PORT.getValue().toUpperCase(),
                                            (String) sourceProperties.getProperties().getProperty(item));
                                } else if (item.toUpperCase().contains(Constants.PROPERTY_DEVICE_ID.getValue().toUpperCase())) {
                                    syncDatasourceConfiguration.setBusinessObjectLightId(
                                            (String) sourceProperties.getProperties().getProperty(item));
                                } else {
                                    syncDatasourceConfiguration.addParameter(item.toUpperCase(),
                                            (String) sourceProperties.getProperties().getProperty(item));
                                }
                            });
                            syncDatasourceConfiguration.setCommonParameters(commonParameters);
                            syncDatasourceConfiguration.setTemplateDataSource(sshTemplate);
                            try {
                                saveDataSource(syncDatasourceConfiguration, getSyncGroupNode(sourceProperties.getProperties()));
                            } catch (ApplicationObjectNotFoundException ex) {
                                System.out.println("Error object not found: " + ex.getMessage()); //NOI18N
                            } catch (InvalidArgumentException ex) {
                                System.out.println("Error invalid argument: " + ex.getMessage()); //NOI18N
                            }
                            //delete old data source
                            this.deleteSynchronizationDataSourceConfig(sourceProperties.getProperties().getId());
                            tx.success();
                        } catch (Exception ex) {
                            System.out.println("Error create SSH data source: " + ex.getMessage()); //NOI18N
                        }
                    }
                }
            } else if (sourceProperties.getKeys().stream().anyMatch(e -> e.toUpperCase().contains(Constants.LABEL_SNMP.getValue().toUpperCase())
                    || e.toUpperCase().contains(Constants.PROPERTY_COMMUNITY.getValue().toUpperCase()) )) {
                if (connectionStatus) {
                    if(snmpTemplate == null)
                        System.out.println("Error snmp template not found."); //NOI18N
                    else{
                        try (Transaction tx = graphDb.beginTx()) {
                            //# syncDatasourceConfiguration <id> <name>
                            //# syncDatasourceCommonProperties <id> <dataSourceType> <properties_of_template_data_source>
                            SyncDataSourceConfiguration syncDatasourceConfiguration = new SyncDataSourceConfiguration();
                            SyncDataSourceCommonParameters commonParameters = new SyncDataSourceCommonParameters(Constants.LABEL_SNMP.getValue());
                            sourceProperties.getProperties().getPropertyKeys().forEach(item -> {
                                if (item.toUpperCase().contains(Constants.PROPERTY_COMMUNITY.getValue().toUpperCase())) {
                                    commonParameters.addParameter(Constants.PROPERTY_COMMUNITY.getValue().toUpperCase(),
                                            (String) sourceProperties.getProperties().getProperty(item));
                                } else if (item.toUpperCase().contains(Constants.PROPERTY_CONTACT.getValue().toUpperCase())) {
                                    commonParameters.addParameter(Constants.PROPERTY_CONTACT.getValue().toUpperCase(),
                                            (String) sourceProperties.getProperties().getProperty(item));
                                } else if (item.toUpperCase().contains(Constants.PROPERTY_LOCATION.getValue().toUpperCase())) {
                                    commonParameters.addParameter(Constants.PROPERTY_LOCATION.getValue().toUpperCase(),
                                            (String) sourceProperties.getProperties().getProperty(item));
                                } else if (item.toUpperCase().contains(Constants.PROPERTY_DEVICE_ID.getValue().toUpperCase())) {
                                    syncDatasourceConfiguration.setBusinessObjectLightId(
                                            (String) sourceProperties.getProperties().getProperty(item));
                                } else {
                                    syncDatasourceConfiguration.addParameter(item.toUpperCase(),
                                            (String) sourceProperties.getProperties().getProperty(item));
                                }
                            });
                            syncDatasourceConfiguration.setCommonParameters(commonParameters);
                            syncDatasourceConfiguration.setTemplateDataSource(snmpTemplate);
                            try {
                                saveDataSource(syncDatasourceConfiguration, getSyncGroupNode(sourceProperties.getProperties()));
                            } catch (ApplicationObjectNotFoundException ex) {
                                System.out.println("Error object not found: " + ex.getMessage()); //NOI18N
                            } catch (InvalidArgumentException ex) {
                                System.out.println("Error invalid argument: " + ex.getMessage()); //NOI18N
                            }
                            //delete old data source
                            this.deleteSynchronizationDataSourceConfig(sourceProperties.getProperties().getId());
                            tx.success();
                        } catch (Exception ex) {
                            System.out.println("Error create SNMP data source: " + ex.getMessage()); //NOI18N
                        }
                    }
                }
            } else if (sourceProperties.getKeys().stream().anyMatch(e -> e.toUpperCase().contains(Constants.LABEL_HTTP.getValue().toUpperCase()))) {
                if (connectionStatus) {
                    if(httpTemplate == null)
                        System.out.println("Error http template not found."); //NOI18N
                    else{
                        try (Transaction tx = graphDb.beginTx()) {
                            //# syncDatasourceConfiguration <id> <name>
                            //# syncDatasourceCommonProperties <id> <dataSourceType> <properties_of_template_data_source>
                            SyncDataSourceConfiguration syncDatasourceConfiguration = new SyncDataSourceConfiguration();
                            SyncDataSourceCommonParameters commonParameters = new SyncDataSourceCommonParameters(Constants.LABEL_HTTP.getValue());
                            sourceProperties.getProperties().getPropertyKeys().forEach(item -> {
                                if (item.toUpperCase().contains(Constants.PROPERTY_IP.getValue().toUpperCase())) {
                                    commonParameters.addParameter(Constants.PROPERTY_IP.getValue().toUpperCase(),
                                            (String) sourceProperties.getProperties().getProperty(item));
                                } else if (item.toUpperCase().contains(Constants.PROPERTY_USER.getValue().toUpperCase())) {
                                    commonParameters.addParameter(Constants.PROPERTY_USER.getValue().toUpperCase(),
                                            (String) sourceProperties.getProperties().getProperty(item));
                                } else if (item.toUpperCase().contains(Constants.PROPERTY_PASSWORD.getValue().toUpperCase())) {
                                    commonParameters.addParameter(Constants.PROPERTY_PASSWORD.getValue().toUpperCase(),
                                            (String) sourceProperties.getProperties().getProperty(item));
                                } else if (item.toUpperCase().contains(Constants.PROPERTY_DEVICE_ID.getValue().toUpperCase())) {
                                    syncDatasourceConfiguration.setBusinessObjectLightId(
                                            (String) sourceProperties.getProperties().getProperty(item));
                                } else {
                                    syncDatasourceConfiguration.addParameter(item.toUpperCase(),
                                            (String) sourceProperties.getProperties().getProperty(item));
                                }
                            });
                            syncDatasourceConfiguration.setCommonParameters(commonParameters);
                            syncDatasourceConfiguration.setTemplateDataSource(httpTemplate);
                            try {
                                saveDataSource(syncDatasourceConfiguration, getSyncGroupNode(sourceProperties.getProperties()));
                            } catch (ApplicationObjectNotFoundException ex) {
                                System.out.println("Error object not found: " + ex.getMessage()); //NOI18N
                            } catch (InvalidArgumentException ex) {
                                System.out.println("Error invalid argument: " + ex.getMessage()); //NOI18N
                            }
                            //delete old data source
                            this.deleteSynchronizationDataSourceConfig(sourceProperties.getProperties().getId());
                            tx.success();
                        } catch (Exception ex) {
                            System.out.println("Error create HTTP data source: " + ex.getMessage()); //NOI18N
                        }
                    }
                }
            } else {
                if (connectionStatus) {
                    if(otherTemplate == null)
                        System.out.println("Error http template not found."); //NOI18N
                    else{
                        try (Transaction tx = graphDb.beginTx()) {
                            //# syncDatasourceConfiguration <id> <name>
                            //# syncDatasourceCommonProperties <id> <dataSourceType> <properties_of_template_data_source>
                            SyncDataSourceConfiguration syncDatasourceConfiguration = new SyncDataSourceConfiguration();
                            sourceProperties.getProperties().getPropertyKeys().forEach(item -> {
                                if (item.toUpperCase().contains(Constants.PROPERTY_DEVICE_ID.getValue().toUpperCase())) {
                                    syncDatasourceConfiguration.setBusinessObjectLightId(
                                            (String) sourceProperties.getProperties().getProperty(item));
                                } else {
                                    syncDatasourceConfiguration.addParameter(item.toUpperCase(),
                                            (String) sourceProperties.getProperties().getProperty(item));
                                }
                            });
                            syncDatasourceConfiguration.setTemplateDataSource(httpTemplate);
                            try {
                                saveDataSource(syncDatasourceConfiguration, getSyncGroupNode(sourceProperties.getProperties()));
                            } catch (ApplicationObjectNotFoundException ex) {
                                System.out.println("Error object not found: " + ex.getMessage()); //NOI18N
                            } catch (InvalidArgumentException ex) {
                                System.out.println("Error invalid argument: " + ex.getMessage()); //NOI18N
                            }
                            //delete old data source
                            this.deleteSynchronizationDataSourceConfig(sourceProperties.getProperties().getId());
                            tx.success();
                        } catch (Exception ex) {
                            System.out.println("Error create HTTP data source: " + ex.getMessage()); //NOI18N
                        }
                    }
                }
            }
        }
    }

    /**
     * @param syncDataSourceConfiguration data source to be saved
     * @param syncGroupNodes list of sync group related to current data source
     * @throws ApplicationObjectNotFoundException If the sync group cannot be
     *                                            found, or some sync data source configuration cannot be found
     * @throws InvalidArgumentException           If the sync group is malformed, or some
     *                                            sync data source configuration is malformed
     */
    public void saveDataSource(SyncDataSourceConfiguration syncDataSourceConfiguration, List<Node> syncGroupNodes)
            throws ApplicationObjectNotFoundException, InvalidArgumentException {

        if(syncDataSourceConfiguration.getBusinessObjectLightId() == null)
            throw new InvalidArgumentException(
                    String.format("The sync data source with id %s not related to any business object",
                            syncDataSourceConfiguration.getId()));

        try (Transaction tx = this.graphDb.beginTx()) {

            Node businessObjectsNode = Util.findNodeByLabelAndUuid(this.graphDb, inventoryObjectLabel
                    , syncDataSourceConfiguration.getBusinessObjectLightId());
            if(businessObjectsNode == null)
                throw new ApplicationObjectNotFoundException(
                        String.format("The sync data source with id %s not related to any business object",
                                syncDataSourceConfiguration.getId()));

            Node dataSourceCommonNode = null;
            if(syncDataSourceConfiguration.getCommonParameters() != null && syncDataSourceConfiguration.getCommonParameters().getId() <= 0){
                dataSourceCommonNode = this.graphDb.createNode(syncDsCommonPropertiesLabel);
                syncDataSourceConfiguration.getCommonParameters().setId(dataSourceCommonNode.getId());
                entityToNodeCommon(syncDataSourceConfiguration.getCommonParameters(), dataSourceCommonNode);
            } else if (syncDataSourceConfiguration.getCommonParameters() != null) {
                dataSourceCommonNode = Util.findNodeByLabelAndId(this.graphDb, syncDsCommonPropertiesLabel,
                        syncDataSourceConfiguration.getCommonParameters().getId());
                if (dataSourceCommonNode == null)
                    throw new ApplicationObjectNotFoundException(
                            String.format("The sync data source with id %s could not be find",
                                    syncDataSourceConfiguration.getCommonParameters().getId()));
                else
                    entityToNodeCommon(syncDataSourceConfiguration.getCommonParameters(), dataSourceCommonNode);
            }
            Node dataSrcConfigurationNode;
            if(syncDataSourceConfiguration.getId() <= 0){
                dataSrcConfigurationNode = this.graphDb.createNode(syncDatasourceConfigLabel);
                syncDataSourceConfiguration.setId(dataSrcConfigurationNode.getId());
                entityToNodeSpecific(syncDataSourceConfiguration, dataSrcConfigurationNode);
            } else {
                dataSrcConfigurationNode = Util.findNodeByLabelAndId(this.graphDb, syncDatasourceConfigLabel,
                        syncDataSourceConfiguration.getId());
                if (dataSrcConfigurationNode == null)
                    throw new ApplicationObjectNotFoundException(
                            String.format("The sync data source with id %s could not be find", syncDataSourceConfiguration.getId()));
                else
                    entityToNodeSpecific(syncDataSourceConfiguration, dataSrcConfigurationNode);
            }

            //create relation between sync datasource and common properties
            for(Relationship relationship : dataSrcConfigurationNode.getRelationships(RelTypes.HAS_SYNC_COMMON_PROPERTIES,
                    Direction.OUTGOING)){
                relationship.delete();
            }
            if(dataSourceCommonNode != null)
                dataSrcConfigurationNode.createRelationshipTo(dataSourceCommonNode, RelTypes.HAS_SYNC_COMMON_PROPERTIES);

            //create relation between sync datasource and inventory object
            for(Relationship relationship : dataSrcConfigurationNode.getRelationships(RelTypes.HAS_SYNC_CONFIGURATION,
                    Direction.INCOMING)){
                relationship.delete();
            }
            businessObjectsNode.createRelationshipTo(dataSrcConfigurationNode, RelTypes.HAS_SYNC_CONFIGURATION);

            //create relation between sync datasource and template
            Node templateDataSrcNode = Util.findNodeByLabelAndId(this.graphDb, templateDataSourceLabel,
                    syncDataSourceConfiguration.getTemplateDataSource().getId());
            if(templateDataSrcNode == null)
                throw new ApplicationObjectNotFoundException(
                        String.format("The sync data source with id %s not related to any template object",
                                syncDataSourceConfiguration.getId()));
            Relationship extendsFromTemplateRel = dataSrcConfigurationNode.getSingleRelationship( RelTypes.EXTENDS_FROM_TEMPLATE,
                    Direction.INCOMING);
            if(extendsFromTemplateRel != null)
                extendsFromTemplateRel.delete();
            dataSrcConfigurationNode.createRelationshipTo(templateDataSrcNode, RelTypes.EXTENDS_FROM_TEMPLATE);

            //create relation between sync datasource and sync group
            for(Node syncGroupNode : syncGroupNodes){
                dataSrcConfigurationNode.createRelationshipTo(syncGroupNode, RelTypes.BELONGS_TO_SYNC_GROUP);
            }
            tx.success();
        }
    }

    private List<Node> getSyncGroupNode(Node oldSyncDataSourceNode){
        List<Node> syncGroups = new ArrayList<>();
        //create relation between sync datasource and inventory object
        for(Relationship relationship : oldSyncDataSourceNode.getRelationships(RelTypes.BELONGS_TO_GROUP,
                Direction.OUTGOING)){
            syncGroups.add(relationship.getEndNode());
        }
        return syncGroups;
    }

    private void entityToNodeSpecific(SyncDataSourceConfiguration entity, Node node) {
        if (entity.getName() != null && !entity.getName().trim().isEmpty())
            node.setProperty(Constants.PROPERTY_NAME.getValue(), entity.getName());

        if (entity.getDescription() != null && !entity.getDescription().trim().isEmpty())
            node.setProperty(Constants.PROPERTY_DESCRIPTION.getValue(), entity.getDescription());

        //remove old parameters
        if (entity.getParameters() != null)
            entity.getParameters().forEach((key, value) -> node.removeProperty(key));

        //add new parameters
        entity.getListOfParameters().forEach(item -> {
            if (item.getPropertyName() != null && !item.getPropertyName().trim().isEmpty()
                    && item.getPropertyValue() != null && !item.getPropertyValue().trim().isEmpty()) {
                node.setProperty(item.getPropertyName(), item.getPropertyValue());
            }
        });
    }

    private void entityToNodeCommon(SyncDataSourceCommonParameters entity, Node node) {
        node.setProperty(Constants.PROPERTY_DATASOURCE_TYPE.getValue(), entity.getDataSourcetype());
        //remove old parameters
        if (entity.getParameters() != null)
            entity.getParameters().forEach((key, value) -> node.removeProperty(key));

        //add new parameters
        entity.getListOfParameters().forEach(item -> {
            if (item.getPropertyName() != null && !item.getPropertyName().trim().isEmpty()
                    && item.getPropertyValue() != null && !item.getPropertyValue().trim().isEmpty()) {
                node.setProperty(item.getPropertyName(), item.getPropertyValue());
            }

        });
    }

    /**
     *
     * @param node template data node
     * @return Template DataSource entity
     */
   private TemplateDataSource createTemplateDataSourceFromNode(Node node){
        HashMap<String, String> parameters = new HashMap<>();
        String name = null ;
        String description = null;

        for (String property : node.getPropertyKeys()) {
            if (property.equalsIgnoreCase(Constants.PROPERTY_NAME.getValue()))
                name = (String)node.getProperty(property);
            else if (property.equalsIgnoreCase(Constants.PROPERTY_DESCRIPTION.getValue()))
                description = (String)node.getProperty(property);
            else
                parameters.put(property, (String)node.getProperty(property));
        }

        return new TemplateDataSource(node.getId(), name, description, parameters);
    }
    /**
     * Deletes a synchronization data source
     *
     * @param syncDataSourceConfigId The id of and synchronization data source
     * @throws ApplicationObjectNotFoundException If the sync data source cannot
     * be found
     */
    private void deleteSynchronizationDataSourceConfig(long syncDataSourceConfigId) throws ApplicationObjectNotFoundException {
        try (Transaction tx = this.graphDb.beginTx()) {
            Node syncDataSourceConfigNode = Util.findNodeByLabelAndId(this.graphDb, syncDatasourceConfigLabel, syncDataSourceConfigId);
            if (syncDataSourceConfigNode == null)
                throw new ApplicationObjectNotFoundException(String.format("Can not find the Synchronization Data Source Configuration with id %s", syncDataSourceConfigId));

            String cypherQuery = "MATCH (node:".concat(syncDatasourceConfigLabel.name()).concat(") \n")
                    .concat("WHERE id(node) = ").concat(String.valueOf(syncDataSourceConfigNode.getId())).concat("\n")
                    .concat("DETACH DELETE node");
            graphDb.execute(cypherQuery);
            tx.success();
        }
    }
    /**
     * shutdown database
     */
    public void shutDown() {
        System.out.println();
        System.out.println("\nShutting down database ...");
        graphDb.shutdown();
        System.out.println("Database Shut down ");
    }
}
