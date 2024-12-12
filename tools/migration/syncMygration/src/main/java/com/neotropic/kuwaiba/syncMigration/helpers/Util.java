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

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.ResourceIterator;
import org.neo4j.graphdb.Result;

/**
 * Utility class containing misc methods to perform common tasks
 * @author Hardy Ryan Chingal Martinez {@literal <ryan.chingal@kuwaiba.org>}
 * created on 28/09/2022-14:27
 */
public class Util {
    /**
     * Finds a node tagged with a label and with a particular id
     * @param graphDb The graphdb handler.
     * @param label The label used to tag the node
     * @param id The id of the node to find
     * @return The node or null if no node with with that label and id could be found
     */
    public static Node findNodeByLabelAndId(GraphDatabaseService graphDb, Label label, long id) {
        String cypherQuery = "MATCH (node:" + label.name() + ") " +
                "WHERE id(node) = " + id + " " +
                "RETURN node";

        Result result = graphDb.execute(cypherQuery);
        ResourceIterator<Node> node = result.columnAs("node");

        return node.hasNext() ? node.next() : null;
    }

    /**
     *
     * Finds a node tagged with a label and with a particular uuid
     * @param graphDb The graphdb handler.
     * @param label The label used to tag the node
     * @param id The id of the node to find
     * @return The node or null if no node with with that label and id could be found
     */
    public static Node findNodeByLabelAndUuid(GraphDatabaseService graphDb, Label label, String id) {
        String cypherQuery = "MATCH (node:" + label.name() + ") " +
                "WHERE node._uuid = '" + id + "' " +
                "RETURN node";

        Result result = graphDb.execute(cypherQuery);
        ResourceIterator<Node> node = result.columnAs("node");

        return node.hasNext() ? node.next() : null;
    }
}
