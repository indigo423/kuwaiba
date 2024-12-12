/**
 * Obtains the contacts associated with the objects housed in the room
 * Neotropic SAS - version 1.0
 * Parameters: None
 * Applies to: Room
 */

import org.neotropic.kuwaiba.modules.optional.reports.html.*;
import org.neotropic.kuwaiba.core.apis.persistence.business.BusinessObjectLight;
import org.neotropic.kuwaiba.core.persistence.reference.neo4j.util.Util;
import org.neo4j.graphdb.Node;
import java.text.DateFormat;
import java.util.Date;
import java.util.ArrayList;
import org.neotropic.kuwaiba.modules.optional.reports.javascript.DataTable;
import org.neotropic.kuwaiba.modules.optional.reports.javascript.DataTable.DataType;
import org.neotropic.kuwaiba.modules.optional.reports.plugins.gcharts.GChartsFactory;
import org.neotropic.kuwaiba.modules.optional.reports.plugins.gcharts.GChartsFactory.ChartType;

def report = new HTMLReport(String.format("contacts associated with the objects housed in the room %s", instanceNode.getProperty("name")), "Neotropic SAS", "1.5");
report.setEmbeddedStyleSheet(HTMLReport.getDefaultStyleSheet());

def resultTable = new HTMLTable(null, "reportTable", null);
resultTable.getRows().add(new HTMLRow(
    [
    new HTMLColumnHeader(null, null, "ELement Name"),
    new HTMLColumnHeader(null, null, "Contact Name"),
    new HTMLColumnHeader(null, null, "Contact Rol"),
    new HTMLColumnHeader(null, null, "Contact Availability"),
    new HTMLColumnHeader(null, null, "Contact cellphone"),
    new HTMLColumnHeader(null, null, "Contact email"),
    ] as HTMLColumnHeader[]));


def cypherQuery = new StringBuilder()
    .append("MATCH (room:inventoryObjects{_uuid:\$id})-[:INSTANCE_OF]->(parent:classes{name:'Room'})\n")
    .append("MATCH (room)<-[:CHILD_OF*]-(child)\n")
    .append("MATCH (child)<-[:RELATED_TO_SPECIAL]-(contact:contacts)\n")
    .append("RETURN child as element, COLLECT(contact) AS contacts\n")
    .toString();

HashMap<String, Object> parameters = new HashMap<>();
parameters.put("id", objectId);

def result = connectionHandler.execute(cypherQuery, parameters); 


if(!result.hasNext()) {
    def ctosEmptyMessage = new HTMLMessage("color:red; font-weight: normal; font-size: 2em; text-align: center;", null, String.format("There are no contacts assigned to Juan %s%%", instanceNode.getProperty("name")))
    def ctosEmpty = new HTMLDiv("No Contacts", ctosEmptyMessage)
    report.getComponents().add(ctosEmpty)
    return report;
}

while (result.hasNext()) {
    Map<String, Object> row = result.next();
    
    // Get the 'element' node (child)
    Node element = (Node) row.get("element");
    String elementName = element.getProperty("name").toString();
    
    // Get the contact arrangement
    List<Node> contacts = (List<Node>) row.get("contacts");

    // Add the main row with the item and process the contacts
    if (!contacts.isEmpty()) {
        for (Node contact : contacts) {
            // Extract properties and validate them
            String contactName = contact.hasProperty("name") ? contact.getProperty("name").toString() : "<No data>";
            String contactRol = contact.hasProperty("role") ? contact.getProperty("role").toString() : "<No data>";
            String availability = contact.hasProperty("availability") ? contact.getProperty("availability").toString() : "<No data>";
            String cellphone = contact.hasProperty("cellphone") ? contact.getProperty("cellphone").toString() : "<No data>";
            String email1 = contact.hasProperty("email1") ? contact.getProperty("email1").toString() : "<No data>";
            
            // Create the row for the contact
            resultTable.getRows().add(new HTMLRow(
                [
                    new HTMLColumn(elementName),
                    new HTMLColumn(contactName),
                    new HTMLColumn(contactRol),
                    new HTMLColumn(availability),
                    new HTMLColumn(cellphone),
                    new HTMLColumn(email1)
                ] as HTMLColumn[]
            ));

            // Restart elementName for the following rows
            elementName = ""; // Leave empty after the first row of the item
        }
    } else {
        // If there are no contacts, add a row for the item with empty data
        resultTable.getRows().add(new HTMLRow(
            [
                new HTMLColumn(elementName),
                new HTMLColumn("<No data>"),
                new HTMLColumn("<No data>"),
                new HTMLColumn("<No data>"),
                new HTMLColumn("<No data>"),
                new HTMLColumn("<No data>")
            ] as HTMLColumn[]
        ));
    }
}

def currentObj = bem.getObject(objectClassName, objectId);
def location = Util.formatObjectList(bem.getParents(objectClassName, objectId), true, 4);

def informationTable = new HTMLTable("width: 80%;", "infoTable", null);
informationTable.getRows().add(new HTMLRow([new HTMLColumn(null, "generalInfoLabel", "<b>Element Name</b>"), new HTMLColumn(null, "generalInfoValue", currentObj.getName())] as HTMLColumn []));
informationTable.getRows().add(new HTMLRow([new HTMLColumn(null, "generalInfoLabel", "<b>Element Type</b>"), new HTMLColumn(null, "generalInfoValue", currentObj.getClassName())] as HTMLColumn []));
informationTable.getRows().add(new HTMLRow([new HTMLColumn(null, "generalInfoLabel", "<b>Location</b>"), new HTMLColumn(null, "generalInfoValue", location)] as HTMLColumn []));
informationTable.getRows().add(new HTMLRow([new HTMLColumn(null, "generalInfoLabel", "<b>Date</b>"), new HTMLColumn(null, "generalInfoValue", DateFormat.getDateInstance(DateFormat.MEDIUM).format(new Date()))] as HTMLColumn []));

def headerTable = new HTMLTable("width: 100%;", "headerTable", null);
headerTable.getRows().add(new HTMLRow([new HTMLColumn(informationTable)] as HTMLColumn []));
report.getComponents().add(new HTMLHx(1, String.format("Contacts associated with the objects housed in the room %s", instanceNode.getProperty("name"))));
report.getComponents().add(new HTMLHx(2, "Room information"));
report.getComponents().add(headerTable);
report.getComponents().add(new HTMLDiv("width:100%; height:5px;", "", "", ""));
report.getComponents().add(resultTable);
report.getComponents().add(new HTMLDiv("width:100%; height:10px;", "", "", ""));
report.getComponents().add(new HTMLDiv("", "footer", "", "This report is powered by <a href=\"http://www.kuwaiba.org\">Kuwaiba Open Network Inventory</a>"));
report.getComponents().add(new HTMLDiv("", "footer", "", new HTMLImage("width:82px;height:62px;", null, "http://neotropic.co/img/logo_blue.png")));

//Return the report
report;