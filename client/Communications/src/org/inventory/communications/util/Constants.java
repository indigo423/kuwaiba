/*
 *  Copyright 2010-2020 Neotropic SAS <contact@neotropic.co>
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

package org.inventory.communications.util;

/**
 * Misc constants
 * @author Charles Edward Bedon Cortazar {@literal <charles.bedon@kuwaiba.org>}
 */
public class Constants {
    /**
     * No debugging
     */
    public static final int DEBUG_LEVEL_DISABLED = 0;
    public static final int DEBUG_LEVEL_INFO = 1;
    public static final int DEBUG_LEVEL_FINE = 2;
    public static int DEBUG_LEVEL = DEBUG_LEVEL_INFO;
    
    public static final int DEVICE_LAYOUT_RESIZE_BORDER_SIZE = 4;
    //public static int DEBUG_LEVEL = DEBUG_LEVEL_DISABLED;
     /**
     * IP address type for IPv4
     */
    public static final int IPV4_TYPE = 4;
    /**
     * IP address type for IPv6
     */
    public static final int IPV6_TYPE = 6;
    /**
     * DummyRoot constant. It's a pseudo class
     */
    public static final String DUMMYROOT = "DummyRoot";
    /**
     * Name for the class InventoryObject
     */
    public static final String CLASS_INVENTORYOBJECT = "InventoryObject";
    public static final String CLASS_CONFIGURATIONITEM = "ConfigurationItem";
    /**
     * Name for the class GenericObjectList
     */
    public static final String CLASS_GENERICOBJECTLIST = "GenericObjectList";
    /**
     * Name for the class GenericService
     */
    public static final String CLASS_GENERICSERVICE = "GenericService";
    /**
     * Name for the class Service Instance
     */
    public static final String CLASS_SERVICEINSTANCE = "ServiceInstance";
    /**
     * Name for the class VRFInstance
     */
    public static final String CLASS_VRFINSTANCE = "VRFInstance";
    /**
     * Name for the class GenericCustomer
     */
    public static final String CLASS_GENERICCUSTOMER = "GenericCustomer";
    /**
     * Root class to all ports
     */
    public static final String CLASS_GENERICPORT = "GenericPort";
    /**
     * Virtual port
     */
    public static final String CLASS_GENERICLOGICALPORT = "GenericLogicalPort";
    /**
     * Physical port
     */
    public static final String CLASS_GENERICPHYSICALPORT = "GenericPhysicalPort";
    /**
     * Root class to all physical containers (wire, wireless, etc)
     */
    public static final String CLASS_GENERICPHYSICALCONTAINER = "GenericPhysicalContainer";
    /**
     * Root class to all physical links (cables, fibers, etc)
     */
    public static final String CLASS_GENERICPHYSICALLINK = "GenericPhysicalLink";
    /**
     * Root class of all communications equipment (GenericDataLinkLayerElement, GenericNetworkElement, etc)
     */
    public static final String CLASS_GENERICCOMMUNICATIONSELEMENT = "GenericCommunicationsElement";   
    /**
     * Class GenericPhysicalNode
     */
    public static final String CLASS_GENERICPHYSICALNODE = "GenericPhysicalNode";
    /**
     * Root class of all network element
     */
    public static final String CLASS_GENERICNETWORKELEMENT = "GenericNetworkElement";
    /**
     * Root class of all generic distribution frame (DDF, ODF, etc)
     */
    public static final String CLASS_GENERICDISTRIBUTIONFRAME = "GenericDistributionFrame";
    /**
     * Root class of all generic distribution frame (DDF, ODF, etc)
     */
    public static final String CLASS_GENERICBOX = "GenericBox";
    /**
     * Root class of all application list type (CustomShape)
     */
    public static final String CLASS_GENERICAPPLICATIONLISTTYPE = "GenericApplicationListType";
    /**
     * Class CustomShape
     */
    public static final String CLASS_CUSTOMSHAPE = "CustomShape";
    /**
     * Root class of all logical connections
     */
    public static final String CLASS_GENERICLOGICALCONNECTION = "GenericLogicalConnection";
    /**
     * Class Rack
     */
    public static final String CLASS_RACK = "Rack";
    /**
     * Name for the class User
     */
    public static final String CLASS_USER = "User";
    /**
     * Name for class subnet IPv4 is this constant used in the IPAM  module
     */
    public static final String CLASS_SUBNET = "Subnet";
    /**
     * Name for class subnet IPv4 is this constant used in the IPAM  module
     */
    public static final String CLASS_SUBNET_IPV4 = "SubnetIPv4";
    /**
     * Name for class subnet IPv6 is this constant used in the IPAM  module
     */
    public static final String CLASS_SUBNET_IPV6 = "SubnetIPv6";
    /**
     * Name for class Location owner this constant is used in the IPAM  module
     */
    public static final String CLASS_LOCATIONOWNER = "LocationOwner";
    /**
     * Name for class VLAN this constant is used in the IPAM  module
     */
    public static final String CLASS_VLAN = "VLAN";
     /**
     * Class EVLAN
     */
    public static final String CLASS_EVLAN = "EVLAN";
    /**
     * Class EGVLAN
     */
    public static final String CLASS_EGVLAN = "EGVLAN";
    /**
     * Class CVLAN
     */
    public static final String CLASS_CVLAN = "CVLAN";
    /**
     * Name for class GenericContract
     */
    public static final String CLASS_GENERICCONTRACT = "GenericContract";
    
    /**
     * Name for class GenericProject
     */
    public static final String CLASS_GENERICPROJECT = "GenericProject";
    
    /**
     * Name for class GenericActivity
     */
    public static final String CLASS_GENERICACTIVITY = "GenericActivity";
    /**
     * Name for class GenericSDHTransportLink
     */
    public static final String CLASS_GENERICSDHTRANSPORTLINK = "GenericSDHTransportLink";
    /**
     * Name for class GenericSDHContainerLink
     */
    public static final String CLASS_GENERICSDHCONTAINERLINK = "GenericSDHContainerLink";
    /**
     * Name for class GenericSDHTributaryLink
     */
    public static final String CLASS_GENERICSDHTRIBUTARYLINK = "GenericSDHTributaryLink";
    
    public static final String CLASS_GENERICAPPLICATIONELEMENT = "GenericApplicationElement";
    /**
     * Not an actual class, but yet used by the service manager to identify a pool mapped to a LocalObjectLight
     */
    public static final String CLASS_POOL = "Pool";
    /**
     * Class ViewableObject
     */
    public static final String CLASS_VIEWABLEOBJECT = "ViewableObject";
    /**
     * Class BridgeDomain
     */
    public static final String CLASS_BRIDGEDOMAIN = "BridgeDomain";
    /**
     * Class BridgeDomainInterface
     */
    public static final String CLASS_BRIDGEDOMAININTERFACE = "BridgeDomainInterface";
    /**
     * Class MPLSTunnel
     */
    public static final String CLASS_MPLSTUNNEL = "MPLSTunnel";
    /**
     * Class FrameRelay
     */
    public static final String CLASS_FRAMERELAYCIRCUIT = "FrameRelayCircuit";
    /**
     * Class WireContainer
     */
    public static final String CLASS_WIRECONTAINER = "WireContainer";
    /**
     * Class MPLSLink
     */
    public static final String CLASS_MPLSLINK = "MPLSLink";
    /**
     * Class Warehouse
     */
    public static final String CLASS_WAREHOUSE = "Warehouse";
    /**
     * Class VirualWarehouse
     */
    public static final String CLASS_VIRTUALWAREHOUSE = "VirtualWarehouse";
    
    /**
     * Class VirtualPort
     */
    public static final String CLASS_VIRTUALPORT = "VirtualPort";
    /**
     * Default type for a new attribute
     */
    public static final String DEFAULT_ATTRIBUTE_TYPE = "String";
    /**
     * Integer, Float, Long, Boolean, String or Text
     */
    public static final int MAPPING_PRIMITIVE = 1;
    /**
     * Dates
     */
    public static final int MAPPING_DATE = 2;
    /**
     * Timestamp
     */
    public static final int MAPPING_TIMESTAMP = 3;
    /**
     * Many to one relationship (such as types)
     */
    public static final int MAPPING_MANYTOONE = 5;
    /**
     * Many to Many relationship (such as accountable persons for a given equipment)
     */
    public static final int MAPPING_MANYTOMANY = 6;
    /**
     * This relationship is used to relate a GenericPort with an IP address 
     */
    public static final String RELATIONSHIP_IPAMHASADDRESS = "ipamHasIpAddress";
    /**
     * This relationship is used to relate a VLAN with a Subnet
     */
    public static final String RELATIONSHIP_IPAMBELONGSTOVLAN = "ipamBelongsToVlan";
    /**
     * This relationship is used to relate a VRF with a Subnet
     */
    public static final String RELATIONSHIP_IPAMBELONGSTOVRFINSTANCE = "ipamBelongsToVrfInstance";
    /**
     * This relationship is used to connect a generic port(service instances) with an interface
     */
    public static final String RELATIONSHIP_IPAMPORTRELATEDTOINTERFACE = "ipamportrelatedtointerface";
    /**
     * A fixed label to indicate a given node doesn't have a name set
     */
    public static final String LABEL_NONAME = "<No Name>";
    
    /**
     * Possible attributes types
     */
    public static final String [] ATTRIBUTE_TYPES = new String[]{"String", "Integer", "Long", "Float", "Boolean", "Date", "Timestamp"};
    public static final String ATTRIBUTE_MODEL = "model";
    /**
     * Property name
     */    
    public static final String PROPERTY_NAME = "name";
    
    /**
     * Property state
     */
    public static final String PROPERTY_STATE = "state";
    /**
     * Property class name
     */
    public static final String PROPERTY_CLASSNAME = "className";
    /**
     * Property display name
     */
    public static final String PROPERTY_DISPLAYNAME = "displayName";
    /**
     * Property description
     */
    public static final String PROPERTY_DESCRIPTION = "description";
    /**
     * Property enabled
     */
    public static final String PROPERTY_ENABLED = "enabled"; //NOI18N
    /**
     * Property commit on execute
     */
    public static final String PROPERTY_COMMIT_ON_EXECUTE = "commitOnExecute"; //NOI18N
    /**
     * Property script
     */
    public static final String PROPERTY_SCRIPT = "script"; //NOI18N
    /**
     * Property executionTime
     */
    public static final String PROPERTY_EXECUTION_TYPE = "executionType"; //NOI18N
    /**
     * Property everyXMinutes
     */
    public static final String PROPERTY_EVERY_X_MINUTES = "everyXMinutes"; //NOI18N
    /**
     * Property start time
     */
    public static final String PROPERTY_START_TIME = "startTime"; //NOI18N
    /**
     * Property Notification Type
     */
    public static final String PROPERTY_NOTIFICATION_TYPE = "notificationType"; //NOI18N
    /**
     * 
     */
    public static final String PROPERTY_PARAMETERS = "parameters"; //NOI18N
    /**
     * Property email
     */
    public static final String PROPERTY_EMAIL = "email"; //NOI18N
    /**
     * Property abstract
     */
    public static final String PROPERTY_ABSTRACT = "abstract";
    /**
     * Property in design
     */
    public static final String PROPERTY_INDESIGN = "inDesign";
    /**
     * Property countable
     */
    public static final String PROPERTY_COUNTABLE = "countable";
    /**
     * Property custom
     */
    public static final String PROPERTY_CUSTOM = "custom";
    /**
     * Property class color
     */
    public static final String PROPERTY_COLOR = "color";
    /**
     * Property small icon
     */
    public static final String PROPERTY_SMALLICON = "smallIcon";
    /**
     * Property icon
     */
    public static final String PROPERTY_ICON = "icon";
    /**
     * Property creation date
     */
    public static final String PROPERTY_CREATIONDATE = "creationDate";
    /**
     * Property type
     */
    public static final String PROPERTY_TYPE = "type";
    /**
     * Property administrative
     */
    public static final String PROPERTY_ADMINISTRATIVE = "administrative";
    /**
     * Property no copy
     */
    public static final String PROPERTY_NOCOPY = "noCopy";
    /**
     * Property unique
     */
    public static final String PROPERTY_UNIQUE = "unique";
    /** 
     * Defines if the attribute of a class is mandatory or not
     */
    public static final String PROPERTY_MANDATORY = "mandatory"; //NOI18N
    /** 
     * Defines if an attribute is a multiple selection list type
     */
    public static final String PROPERTY_MULTIPLE = "multiple"; //NOI18N
    /**
     * Property visible
     */
    public static final String PROPERTY_VISIBLE = "visible";
    /**
     * Property order
     */
    public static final String PROPERTY_ORDER = "order";
    /**
     * Property read only
     */
    public static final String PROPERTY_READONLY = "readOnly";
    /**
     * Property parent
     */
    public static final String PROPERTY_PARENT = "parent";
    /**
     * Property id
     */
    public static final String PROPERTY_ID = "id";
    /**
     * Property rackUnits
     */
    public static final String PROPERTY_RACK_UNITS = "rackUnits";
    /**
     * Property mask in a ip address
     */
    public static final String PROPERTY_IP_MASK = "mask";
    /**
     * Property tags (as in File Object tags)
     */
    public static final String PROPERTY_TAGS = "tags";
    /**
     * Property numberingAscending
     */
    public static final String PROPERTY_RACK_UNITS_NUMBERING = "rackUnitsNumberingDescending";
    /**
     * Property startRackUnit
     */
    public static final String PROPERTY_POSITION = "position";
    /**
     * Property network IP  in a subnet
     */
    public static final String PROPERTY_NETWORKIP = "networkIp";
    /**
     * Property broadcast IP in a subnet
     */
    public static final String PROPERTY_BROADCASTIP = "broadcastIp";
    /**
     * Property maximum number of hosts in a subnet
     */
    public static final String PROPERTY_HOSTS = "hosts";
    /**
     * Property version
     */
    public static final String PROPERTY_VERSION = "version";
    /**
     * SNMP version 2c property community
     */
    public static final String PROPERTY_COMMUNITY = "community";
    /**
     * SNMP version 3 property authentication protocol
     */
    public static final String PROPERTY_AUTH_PROTOCOL = "authProtocol";
    /**
     * SNMP version 3 property authentication protocol pass phrase
     */
    public static final String PROPERTY_AUTH_PASS = "authPass";    
    /**
     * SNMP version 3 property security Level
     */
    public static final String PROPERTY_SECURITY_LEVEL = "securityLevel";
    /**
     * SNMP version 3 property context Name
     */
    public static final String PROPERTY_CONTEXT_NAME = "contextName";
    /**
     * SNMP version 3 property security name
     */
    public static final String PROPERTY_SECURITY_NAME = "securityName";
    /**
     * SNMP version 3 property privacy Protocol
     */
    public static final String PROPERTY_PRIVACY_PROTOCOL = "privacyProtocol";
    /**
     * SNMP version 3 property privacy protocol pass phrase
     */
    public static final String PROPERTY_PRIVACY_PASS = "privacyPass";
    /**
     * List type class for operational state
     */
    public static final String LIST_TYPE_OPERATIONAL_STATE = "OperationalState";
    /**
     * Physical connection classes
     */
    public static final String CLASS_IP_ADDRESS = "IPAddress";
    
    /**
     * Generic classes
     */
    public static final String CLASS_GENERICCONNECTION="GenericConnection";
    /**
     * Generic classes
     */
    public static final String CLASS_GENERICPHYSICALCONNECTION="GenericPhysicalConnection";
    //Misc versions
    /**
     * Version for the XML document to save views (see http://neotropic.co/kuwaiba/wiki/index.php?title=XML_Documents#To_Save_Object_Views for details)
     */
     public static final String VIEW_FORMAT_VERSION = "1.2";
     /**
      * Version of the current Topology View XML document
      */
     public static final String TOPOLOGYVIEW_FORMAT_VERSION = "1.2";
}
