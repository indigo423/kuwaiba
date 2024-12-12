/*
 *  Copyright 2010-2023 Neotropic SAS <contact@neotropic.co>.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       https://apache.org/licenses/LICENSE-2.0.txt
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.neotropic.tools.neo4j.db.copy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;
import org.neo4j.driver.Transaction;
import org.neo4j.driver.types.Node;
import org.neo4j.driver.types.Relationship;

/**
 * Copy the nodes and relationships from Neo4j 4.4, 4.3, 4.2, 4.1, 4.0, 3.5
 * databases (source db) to Neo4j 4.4, 4.3, 4.2, 4.1, 4.0, 3.5 databases (target
 * db). The application connect to the source db using the Neo4j Java Driver 4.4
 * and only read the nodes and relationships before connect to the target db
 * using the Neo4j Java Driver 4.4 DELETE ALL NODES AND RELATIONSHIPS IN TARGET
 * DB and create new nodes and relationships copy the nodes and relationships of
 * source db.
 *
 * @author Johny Andres Ortega Ruiz {@literal <johny.ortega@kuwaiba.org>}
 */
public class DatabaseCopy {

    public static void main(String[] args) {
        System.out.println(args.length);
        if (args.length < 2) {
            if (args.length == 0) {
                System.err.println("Missing arguments <sourceUri> and <targetUri>");
            } else if (args.length == 1) {
                System.err.println("Missing argument <targetUri>");
            }
            System.out.println("Use");
            System.out.println("java -jar database-copy-4.4.jar <sourceUri> <targetUri>");
            System.out.println("Example");
            System.out.println("java -jar database-copy-4.4.jar bolt://localhost:6677 bolt://localhost:7687");
            return;
        }
        String sourceUri = args[0];
        String targetUri = args[1];
        
        System.out.println(String.format("Source URI %s", sourceUri));
        System.out.println(String.format("Target URI %s", targetUri));
        System.out.println("Start Copy");
        
        List<Node> nodes = new ArrayList();
        List<Relationship> relationships = new ArrayList();
        HashMap<Long, Long> newNodes = new HashMap();

        Driver sourceDriver = GraphDatabase.driver(sourceUri);
        try (Session session = sourceDriver.session()) {
            session.readTransaction(tx -> {
                tx.run("MATCH (n) RETURN n").stream().forEach(record -> {
                    Node node = record.get(0).asNode();
                    nodes.add(node);
                });
                return nodes;
            }
            );
            session.readTransaction(tx -> {
                tx.run("MATCH ()-[r]-() RETURN DISTINCT r").stream().forEach(record -> {
                    Relationship relationship = record.get(0).asRelationship();
                    relationships.add(relationship);
                });
                return relationships;
            });
        }
        sourceDriver.close();

        Driver targetDriver = GraphDatabase.driver(targetUri);
        try (Transaction tx = targetDriver.session().beginTransaction()) {
            System.out.println("Deleting all nodes and relationships");
            tx.run("MATCH (n) DETACH DELETE n");
            // Creating nodes
            System.out.println("Start Copy nodes");
            AtomicInteger i = new AtomicInteger(0);
            nodes.forEach(node -> {
                StringBuilder labels = new StringBuilder();
                node.labels().forEach(label -> labels.append(String.format(":%s", label)));
                
                HashMap<String, Object> parameters = new HashMap();
                parameters.put("props", node.asMap());
                Result result = tx.run(String.format("CREATE (n%s $props) RETURN n", labels), parameters);
                newNodes.put(node.id(), result.single().get(0).asNode().id());
                
                System.out.println(String.format("Create %s/%s nodes", i.incrementAndGet(), nodes.size()));
            });
            System.out.println("End Copy nodes");
            // Creating edges
            System.out.println("Start Copy relationships");

            i.set(0);
            relationships.forEach(relationship -> {
                HashMap<String, Object> parameters = new HashMap();
                parameters.put("startNodeId", newNodes.get(relationship.startNodeId()));
                parameters.put("endNodeId", newNodes.get(relationship.endNodeId()));
                parameters.put("props", relationship.asMap());
                StringBuilder type = new StringBuilder();
                if (relationship.type() != null && !relationship.type().isEmpty()) {
                    type.append(String.format(":%s", relationship.type()));
                }
                StringBuilder queryBuilder = new StringBuilder();
                queryBuilder.append("MATCH (n), (m)\n");
                queryBuilder.append("WHERE id(n) = $startNodeId\n");
                queryBuilder.append("AND id(m) = $endNodeId\n");
                queryBuilder.append(String.format("CREATE (n)-[r%s $props]->(m)", type.toString()));

                tx.run(queryBuilder.toString(), parameters);
                System.out.println(String.format("Create %s/%s relationships", i.incrementAndGet(), relationships.size()));
            });
            System.out.println("End Copy relationships");
            
            System.out.println("Start Commit transaction");
            tx.commit();
            System.out.println("End Commit transaction");
        }
        targetDriver.close();
        System.out.println("End Copy");
        System.out.println(String.format("Copied %s nodes", nodes.size()));
        System.out.println(String.format("Copied %s relationships", relationships.size()));
    }
}