
package org.inventory.communications.wsclient;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.inventory.communications.wsclient package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _RelateObjectToServiceResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "relateObjectToServiceResponse");
    private final static QName _CreateActivity_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "createActivity");
    private final static QName _RunValidationsForObject_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "runValidationsForObject");
    private final static QName _GetAllProxies_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getAllProxies");
    private final static QName _RelateIPtoPort_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "relateIPtoPort");
    private final static QName _ReleasePhysicalNodeFromWarehouseResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "releasePhysicalNodeFromWarehouseResponse");
    private final static QName _DeleteWarehouseResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "deleteWarehouseResponse");
    private final static QName _RemovePossibleChildrenForClassWithIdResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "removePossibleChildrenForClassWithIdResponse");
    private final static QName _GetObjectsRelatedToServiceResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getObjectsRelatedToServiceResponse");
    private final static QName _GetSubnetsInSubnetResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getSubnetsInSubnetResponse");
    private final static QName _ExecuteInventoryLevelReportResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "executeInventoryLevelReportResponse");
    private final static QName _GetTemplateElementChildren_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getTemplateElementChildren");
    private final static QName _GetSynchronizationProviders_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getSynchronizationProviders");
    private final static QName _RelateObjectToService_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "relateObjectToService");
    private final static QName _CreateSubnetPoolResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "createSubnetPoolResponse");
    private final static QName _DeleteServicePoolResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "deleteServicePoolResponse");
    private final static QName _AddIPAddress_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "addIPAddress");
    private final static QName _GetOSPView_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getOSPView");
    private final static QName _ReleasePhysicalNodeFromWarehouse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "releasePhysicalNodeFromWarehouse");
    private final static QName _UpdateReportParametersResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "updateReportParametersResponse");
    private final static QName _GetFirstParentOfClass_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getFirstParentOfClass");
    private final static QName _GetProjectPool_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getProjectPool");
    private final static QName _DeleteSubnetPoolsResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "deleteSubnetPoolsResponse");
    private final static QName _CreateProcessDefinitionResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "createProcessDefinitionResponse");
    private final static QName _CopyObjectsResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "copyObjectsResponse");
    private final static QName _DeleteFavoritesFolders_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "deleteFavoritesFolders");
    private final static QName _CopyObjects_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "copyObjects");
    private final static QName _RemovePrivilegeFromUserResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "removePrivilegeFromUserResponse");
    private final static QName _GetContractsInPoolResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getContractsInPoolResponse");
    private final static QName _DeleteObjects_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "deleteObjects");
    private final static QName _CreateOSPViewResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "createOSPViewResponse");
    private final static QName _CreateRootPool_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "createRootPool");
    private final static QName _CreateAttributeForClassWithIdResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "createAttributeForClassWithIdResponse");
    private final static QName _SetPrivilegeToGroupResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "setPrivilegeToGroupResponse");
    private final static QName _GetSyncDataSourceConfigurations_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getSyncDataSourceConfigurations");
    private final static QName _ReleaseMirrorMultiplePort_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "releaseMirrorMultiplePort");
    private final static QName _DeleteProxyPool_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "deleteProxyPool");
    private final static QName _UnsubscribeUserFromTaskResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "unsubscribeUserFromTaskResponse");
    private final static QName _GetPoolsInWarehouse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getPoolsInWarehouse");
    private final static QName _UnsubscribeUserFromTask_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "unsubscribeUserFromTask");
    private final static QName _CreateObjectRelatedViewResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "createObjectRelatedViewResponse");
    private final static QName _GetAllCustomers_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getAllCustomers");
    private final static QName _UpdateConfigurationVariable_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "updateConfigurationVariable");
    private final static QName _SaveQueryResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "saveQueryResponse");
    private final static QName _CreateListTypeItemRelatedView_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "createListTypeItemRelatedView");
    private final static QName _MoveObjects_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "moveObjects");
    private final static QName _CreateUserResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "createUserResponse");
    private final static QName _GetSpecialAttributeResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getSpecialAttributeResponse");
    private final static QName _DeletePoolsResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "deletePoolsResponse");
    private final static QName _GetAllCustomersResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getAllCustomersResponse");
    private final static QName _GetParentsUntilFirstOfClassResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getParentsUntilFirstOfClassResponse");
    private final static QName _GetPoolResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getPoolResponse");
    private final static QName _CreateObject_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "createObject");
    private final static QName _UpdateListTypeItemRelatedViewResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "updateListTypeItemRelatedViewResponse");
    private final static QName _CreateSubnet_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "createSubnet");
    private final static QName _ReconnectPhysicalConnection_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "reconnectPhysicalConnection");
    private final static QName _ReleaseMirrorPortResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "releaseMirrorPortResponse");
    private final static QName _GetTasksResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getTasksResponse");
    private final static QName _UpdateContractResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "updateContractResponse");
    private final static QName _UpdateValidatorDefinition_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "updateValidatorDefinition");
    private final static QName _DeleteTask_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "deleteTask");
    private final static QName _DeleteSubnets_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "deleteSubnets");
    private final static QName _ReleaseMirrorPort_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "releaseMirrorPort");
    private final static QName _CreateProjectResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "createProjectResponse");
    private final static QName _GetSynchronizationProvidersResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getSynchronizationProvidersResponse");
    private final static QName _CreateSDHTributaryLink_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "createSDHTributaryLink");
    private final static QName _DeleteObjectsResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "deleteObjectsResponse");
    private final static QName _MoveObjectsToWarehouse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "moveObjectsToWarehouse");
    private final static QName _GetSpecialChildrenOfClassLightRecursiveResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getSpecialChildrenOfClassLightRecursiveResponse");
    private final static QName _SetAttributePropertiesResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "setAttributePropertiesResponse");
    private final static QName _CreateInventoryLevelReport_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "createInventoryLevelReport");
    private final static QName _GetObjectsInFavoritesFolder_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getObjectsInFavoritesFolder");
    private final static QName _GetObjectChildrenForClassWithId_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getObjectChildrenForClassWithId");
    private final static QName _GetArtifactDefinitionForActivity_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getArtifactDefinitionForActivity");
    private final static QName _DeleteBusinessRule_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "deleteBusinessRule");
    private final static QName _CopyTemplateElements_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "copyTemplateElements");
    private final static QName _CreateOSPView_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "createOSPView");
    private final static QName _DeleteCostumerResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "deleteCostumerResponse");
    private final static QName _ReconnectPhysicalConnectionResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "reconnectPhysicalConnectionResponse");
    private final static QName _DeleteSDHTributaryLink_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "deleteSDHTributaryLink");
    private final static QName _CreateSparePart_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "createSparePart");
    private final static QName _GetObjectsWithFilterLightResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getObjectsWithFilterLightResponse");
    private final static QName _LaunchAdHocAutomatedSynchronizationTask_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "launchAdHocAutomatedSynchronizationTask");
    private final static QName _AssociatePhysicalNodeToWarehouseResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "associatePhysicalNodeToWarehouseResponse");
    private final static QName _DeleteListTypeItemRelatedView_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "deleteListTypeItemRelatedView");
    private final static QName _CommitActivity_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "commitActivity");
    private final static QName _DeleteService_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "deleteService");
    private final static QName _GetSubscribersForTask_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getSubscribersForTask");
    private final static QName _CreateClassLevelReport_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "createClassLevelReport");
    private final static QName _GetOSPViewsResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getOSPViewsResponse");
    private final static QName _GetParentsResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getParentsResponse");
    private final static QName _GetProjectResourcesResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getProjectResourcesResponse");
    private final static QName _UpdateListTypeItemResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "updateListTypeItemResponse");
    private final static QName _UpdateProjectResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "updateProjectResponse");
    private final static QName _AddPossibleSpecialChildrenWithId_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "addPossibleSpecialChildrenWithId");
    private final static QName _GetPhysicalPathResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getPhysicalPathResponse");
    private final static QName _ConnectMirrorMultiplePort_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "connectMirrorMultiplePort");
    private final static QName _CreateServiceResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "createServiceResponse");
    private final static QName _UpdateContact_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "updateContact");
    private final static QName _CreateCustomerResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "createCustomerResponse");
    private final static QName _ExecuteClassLevelReport_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "executeClassLevelReport");
    private final static QName _ReleasePortFromIPResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "releasePortFromIPResponse");
    private final static QName _GetChildrenOfClass_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getChildrenOfClass");
    private final static QName _GetContact_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getContact");
    private final static QName _GetProjectPoolResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getProjectPoolResponse");
    private final static QName _UpdateFilePropertiesResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "updateFilePropertiesResponse");
    private final static QName _CreateAttributeForClassWithId_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "createAttributeForClassWithId");
    private final static QName _DeleteSparePool_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "deleteSparePool");
    private final static QName _MoveObjectsToPoolResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "moveObjectsToPoolResponse");
    private final static QName _DeleteSynchronizationGroupResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "deleteSynchronizationGroupResponse");
    private final static QName _GetBusinessObjectAuditTrailResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getBusinessObjectAuditTrailResponse");
    private final static QName _GetFavoritesFolderResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getFavoritesFolderResponse");
    private final static QName _RelateObjectsToContract_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "relateObjectsToContract");
    private final static QName _DeleteGroups_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "deleteGroups");
    private final static QName _ExecuteSyncActionsResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "executeSyncActionsResponse");
    private final static QName _AssociateObjectsToContract_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "associateObjectsToContract");
    private final static QName _DeleteGroupsResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "deleteGroupsResponse");
    private final static QName _GetConfigurationVariableValueResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getConfigurationVariableValueResponse");
    private final static QName _GetListTypeItemsResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getListTypeItemsResponse");
    private final static QName _GetCustomerResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getCustomerResponse");
    private final static QName _GetSDHContainerLinkStructureResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getSDHContainerLinkStructureResponse");
    private final static QName _GetContactsResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getContactsResponse");
    private final static QName _GetSpecialChildrenOfClassLight_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getSpecialChildrenOfClassLight");
    private final static QName _GetQueryResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getQueryResponse");
    private final static QName _UpdateFileProperties_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "updateFileProperties");
    private final static QName _CreateProxyPoolResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "createProxyPoolResponse");
    private final static QName _GetSubnetResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getSubnetResponse");
    private final static QName _SetPrivilegeToUser_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "setPrivilegeToUser");
    private final static QName _DeleteTemplateElement_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "deleteTemplateElement");
    private final static QName _GetContractsInPool_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getContractsInPool");
    private final static QName _RemovePrivilegeFromGroup_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "removePrivilegeFromGroup");
    private final static QName _CreateProjectPool_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "createProjectPool");
    private final static QName _GetPhysicalConnectionEndpointsResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getPhysicalConnectionEndpointsResponse");
    private final static QName _CreateProjectPoolResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "createProjectPoolResponse");
    private final static QName _CreateContractPoolResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "createContractPoolResponse");
    private final static QName _CreateProcessDefinition_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "createProcessDefinition");
    private final static QName _RelateObjectToContractResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "relateObjectToContractResponse");
    private final static QName _GetChildrenOfClassLight_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getChildrenOfClassLight");
    private final static QName _GetSubnetPools_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getSubnetPools");
    private final static QName _DeleteCostumer_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "deleteCostumer");
    private final static QName _DisconnectPhysicalConnection_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "disconnectPhysicalConnection");
    private final static QName _ConnectMirrorPortResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "connectMirrorPortResponse");
    private final static QName _GetObjectResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getObjectResponse");
    private final static QName _ReleaseSubnetFromVlan_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "releaseSubnetFromVlan");
    private final static QName _GetGroupsResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getGroupsResponse");
    private final static QName _SetAttributeProperties_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "setAttributeProperties");
    private final static QName _FindSDHRoutesUsingTransportLinksResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "findSDHRoutesUsingTransportLinksResponse");
    private final static QName _ConnectMplsLink_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "connectMplsLink");
    private final static QName _GetSubnetPoolResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getSubnetPoolResponse");
    private final static QName _GetCustomerPool_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getCustomerPool");
    private final static QName _SetPoolPropertiesResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "setPoolPropertiesResponse");
    private final static QName _GetUsers_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getUsers");
    private final static QName _AddPossibleChildren_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "addPossibleChildren");
    private final static QName _ConnectPhysicalLinks_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "connectPhysicalLinks");
    private final static QName _CreateContact_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "createContact");
    private final static QName _ReleaseSyncDataSourceConfigFromSyncGroupResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "releaseSyncDataSourceConfigFromSyncGroupResponse");
    private final static QName _AddObjectsToFavoritesFolderResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "addObjectsToFavoritesFolderResponse");
    private final static QName _GetPhysicalPath_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getPhysicalPath");
    private final static QName _DeleteGeneralViewResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "deleteGeneralViewResponse");
    private final static QName _RemovePrivilegeFromUser_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "removePrivilegeFromUser");
    private final static QName _GetRootPoolsResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getRootPoolsResponse");
    private final static QName _ReleaseSubnetFromVlanResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "releaseSubnetFromVlanResponse");
    private final static QName _CreateBulkSpecialTemplateElementResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "createBulkSpecialTemplateElementResponse");
    private final static QName _RemovePrivilegeFromGroupResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "removePrivilegeFromGroupResponse");
    private final static QName _ValidateSavedE2EView_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "validateSavedE2EView");
    private final static QName _ReleaseObjectFromProxy_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "releaseObjectFromProxy");
    private final static QName _DeleteConfigurationVariableResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "deleteConfigurationVariableResponse");
    private final static QName _DeleteProjectPool_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "deleteProjectPool");
    private final static QName _GetGeneralActivityAuditTrailResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getGeneralActivityAuditTrailResponse");
    private final static QName _DisconnectMPLSLinkResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "disconnectMPLSLinkResponse");
    private final static QName _GetTemplateElement_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getTemplateElement");
    private final static QName _DeleteSDHTributaryLinkResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "deleteSDHTributaryLinkResponse");
    private final static QName _GetServicesInPool_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getServicesInPool");
    private final static QName _RemoveIP_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "removeIP");
    private final static QName _RelateObjectToProjectResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "relateObjectToProjectResponse");
    private final static QName _CreateSession_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "createSession");
    private final static QName _DeleteConfigurationVariablesPool_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "deleteConfigurationVariablesPool");
    private final static QName _DeleteContractPoolResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "deleteContractPoolResponse");
    private final static QName _UpdateContractPool_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "updateContractPool");
    private final static QName _UpdateListTypeItem_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "updateListTypeItem");
    private final static QName _GetObjectsOfClassLight_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getObjectsOfClassLight");
    private final static QName _SetGroupPropertiesResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "setGroupPropertiesResponse");
    private final static QName _GetPoolsInWarehouseResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getPoolsInWarehouseResponse");
    private final static QName _GetClassWithId_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getClassWithId");
    private final static QName _GetServicePool_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getServicePool");
    private final static QName _CreateObjectRelatedView_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "createObjectRelatedView");
    private final static QName _GetActivity_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getActivity");
    private final static QName _GetAllProjectsResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getAllProjectsResponse");
    private final static QName _SetGroupProperties_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "setGroupProperties");
    private final static QName _GetSubnet_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getSubnet");
    private final static QName _CreateProxyResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "createProxyResponse");
    private final static QName _DeleteMPLSLinkResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "deleteMPLSLinkResponse");
    private final static QName _GetUpstreamSpecialContainmentHierarchy_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getUpstreamSpecialContainmentHierarchy");
    private final static QName _GetCustomerPoolsResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getCustomerPoolsResponse");
    private final static QName _GetSubClassesLightResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getSubClassesLightResponse");
    private final static QName _GetAttributeResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getAttributeResponse");
    private final static QName _CreateFavoritesFolderForUserResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "createFavoritesFolderForUserResponse");
    private final static QName _UpdateServiceResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "updateServiceResponse");
    private final static QName _GetSubnetPool_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getSubnetPool");
    private final static QName _GetSynchronizationGroupResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getSynchronizationGroupResponse");
    private final static QName _DeleteQuery_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "deleteQuery");
    private final static QName _GetSyncDataSourceConfiguration_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getSyncDataSourceConfiguration");
    private final static QName _CopySpecialObjectsResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "copySpecialObjectsResponse");
    private final static QName _ExecuteSyncActions_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "executeSyncActions");
    private final static QName _GetBGPMapResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getBGPMapResponse");
    private final static QName _CopySyncDataSourceConfigurationResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "copySyncDataSourceConfigurationResponse");
    private final static QName _CreateBulkObjectsResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "createBulkObjectsResponse");
    private final static QName _GetPossibleChildrenNoRecursiveResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getPossibleChildrenNoRecursiveResponse");
    private final static QName _GetTasksForUser_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getTasksForUser");
    private final static QName _DeleteClassWithIdResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "deleteClassWithIdResponse");
    private final static QName _GetParent_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getParent");
    private final static QName _GetAllContractsResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getAllContractsResponse");
    private final static QName _CopyTemplateSpecialElementsResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "copyTemplateSpecialElementsResponse");
    private final static QName _GetWarehousesInPool_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getWarehousesInPool");
    private final static QName _DisconnectPhysicalConnectionResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "disconnectPhysicalConnectionResponse");
    private final static QName _DeleteOSPView_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "deleteOSPView");
    private final static QName _RelateObjectsToProject_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "relateObjectsToProject");
    private final static QName _UpdateProject_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "updateProject");
    private final static QName _CreateSpecialObject_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "createSpecialObject");
    private final static QName _GetValidatorDefinitionsForClass_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getValidatorDefinitionsForClass");
    private final static QName _DetachFileFromObjectResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "detachFileFromObjectResponse");
    private final static QName _GetObjectsInFavoritesFolderResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getObjectsInFavoritesFolderResponse");
    private final static QName _CopyTemplateSpecialElements_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "copyTemplateSpecialElements");
    private final static QName _GetProxyPoolsResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getProxyPoolsResponse");
    private final static QName _DeleteProcessDefinitionResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "deleteProcessDefinitionResponse");
    private final static QName _UpdateProxyResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "updateProxyResponse");
    private final static QName _DeleteClassWithId_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "deleteClassWithId");
    private final static QName _DeleteAttribute_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "deleteAttribute");
    private final static QName _GetAllConfigurationVariables_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getAllConfigurationVariables");
    private final static QName _GetProjectResources_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getProjectResources");
    private final static QName _ReleaseSyncDataSourceConfigFromSyncGroup_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "releaseSyncDataSourceConfigFromSyncGroup");
    private final static QName _GetAllProxiesResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getAllProxiesResponse");
    private final static QName _CreateBulkTemplateElement_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "createBulkTemplateElement");
    private final static QName _GetInventoryLevelReports_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getInventoryLevelReports");
    private final static QName _CreateConfigurationVariableResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "createConfigurationVariableResponse");
    private final static QName _GetSubClassesLight_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getSubClassesLight");
    private final static QName _GetSubnetUsedIpsResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getSubnetUsedIpsResponse");
    private final static QName _CreatePhysicalConnection_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "createPhysicalConnection");
    private final static QName _GetUpstreamContainmentHierarchyResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getUpstreamContainmentHierarchyResponse");
    private final static QName _CreateSDHTransportLink_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "createSDHTransportLink");
    private final static QName _UpdateReport_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "updateReport");
    private final static QName _CreateListTypeItemResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "createListTypeItemResponse");
    private final static QName _CreateConfigurationVariablesPool_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "createConfigurationVariablesPool");
    private final static QName _GetParentOfClassResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getParentOfClassResponse");
    private final static QName _ConnectMirrorPort_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "connectMirrorPort");
    private final static QName _CreateSynchronizationDataSourceConfig_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "createSynchronizationDataSourceConfig");
    private final static QName _RunValidationsForObjectResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "runValidationsForObjectResponse");
    private final static QName _UpdateSyncDataSourceConfiguration_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "updateSyncDataSourceConfiguration");
    private final static QName _ExecuteTask_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "executeTask");
    private final static QName _SearchForContacts_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "searchForContacts");
    private final static QName _DeleteObject_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "deleteObject");
    private final static QName _GetObjectLightResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getObjectLightResponse");
    private final static QName _GetObjectsInSparePoolResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getObjectsInSparePoolResponse");
    private final static QName _GetQuery_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getQuery");
    private final static QName _GetSubClassesLightNoRecursiveResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getSubClassesLightNoRecursiveResponse");
    private final static QName _ReleasePortFromInterfaceResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "releasePortFromInterfaceResponse");
    private final static QName _UpdateCustomerPoolResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "updateCustomerPoolResponse");
    private final static QName _DeleteProxyPoolResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "deleteProxyPoolResponse");
    private final static QName _AttachFileToObject_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "attachFileToObject");
    private final static QName _AddUserToGroupResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "addUserToGroupResponse");
    private final static QName _GetChildrenOfClassLightRecursiveResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getChildrenOfClassLightRecursiveResponse");
    private final static QName _DeleteSynchronizationDataSourceConfigResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "deleteSynchronizationDataSourceConfigResponse");
    private final static QName _GetSubnets_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getSubnets");
    private final static QName _GetObjectsWithFilterLight_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getObjectsWithFilterLight");
    private final static QName _DeleteContactResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "deleteContactResponse");
    private final static QName _GetUpstreamContainmentHierarchy_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getUpstreamContainmentHierarchy");
    private final static QName _UpdateListTypeItemRelatedView_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "updateListTypeItemRelatedView");
    private final static QName _GetChildrenOfClassLightRecursive_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getChildrenOfClassLightRecursive");
    private final static QName _CreatePoolInObject_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "createPoolInObject");
    private final static QName _GetObjectRelatedViewsResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getObjectRelatedViewsResponse");
    private final static QName _CreatePhysicalConnections_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "createPhysicalConnections");
    private final static QName _GetCustomerPoolResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getCustomerPoolResponse");
    private final static QName _RemoveIPResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "removeIPResponse");
    private final static QName _DeleteWarehouse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "deleteWarehouse");
    private final static QName _GetConfigurationVariableValue_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getConfigurationVariableValue");
    private final static QName _GetValidatorDefinitionsForClassResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getValidatorDefinitionsForClassResponse");
    private final static QName _DeleteConfigurationVariablesPoolResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "deleteConfigurationVariablesPoolResponse");
    private final static QName _GetProjectsInPoolResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getProjectsInPoolResponse");
    private final static QName _GetAffectedServices_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getAffectedServices");
    private final static QName _GetClassHierarchy_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getClassHierarchy");
    private final static QName _AddIPAddressResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "addIPAddressResponse");
    private final static QName _GetTemplateSpecialElementChildren_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getTemplateSpecialElementChildren");
    private final static QName _GetCustomer_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getCustomer");
    private final static QName _CreateFavoritesFolderForUser_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "createFavoritesFolderForUser");
    private final static QName _GetSiblings_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getSiblings");
    private final static QName _GetProjectPools_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getProjectPools");
    private final static QName _CreateProcessInstanceResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "createProcessInstanceResponse");
    private final static QName _GetProxiesInPool_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getProxiesInPool");
    private final static QName _CreateMPLSLink_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "createMPLSLink");
    private final static QName _DeletePhysicalConnection_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "deletePhysicalConnection");
    private final static QName _AddPossibleChildrenForClassWithIdResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "addPossibleChildrenForClassWithIdResponse");
    private final static QName _GetFilesForObject_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getFilesForObject");
    private final static QName _MoveSyncDataSourceConfiguration_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "moveSyncDataSourceConfiguration");
    private final static QName _GetObjectRelatedViewResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getObjectRelatedViewResponse");
    private final static QName _GetMandatoryAttributesInClassResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getMandatoryAttributesInClassResponse");
    private final static QName _UpdateConfigurationVariablesPoolResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "updateConfigurationVariablesPoolResponse");
    private final static QName _DeleteReport_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "deleteReport");
    private final static QName _GetProcessDefinition_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getProcessDefinition");
    private final static QName _CreatePoolInWarehouseResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "createPoolInWarehouseResponse");
    private final static QName _GetServiceResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getServiceResponse");
    private final static QName _DeleteClassResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "deleteClassResponse");
    private final static QName _UpdateConfigurationVariableResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "updateConfigurationVariableResponse");
    private final static QName _AddPossibleChildrenResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "addPossibleChildrenResponse");
    private final static QName _GetUsersInGroupResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getUsersInGroupResponse");
    private final static QName _CreateContractResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "createContractResponse");
    private final static QName _LaunchAutomatedSynchronizationTask_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "launchAutomatedSynchronizationTask");
    private final static QName _CopyPoolItemToPool_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "copyPoolItemToPool");
    private final static QName _GetSDHTransportLinkStructure_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getSDHTransportLinkStructure");
    private final static QName _DownloadBulkLoadLog_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "downloadBulkLoadLog");
    private final static QName _GetContactsForCustomer_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getContactsForCustomer");
    private final static QName _GetObjectsWithFilterResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getObjectsWithFilterResponse");
    private final static QName _GetGeneralViewResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getGeneralViewResponse");
    private final static QName _DeleteContact_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "deleteContact");
    private final static QName _RelateIPtoPortResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "relateIPtoPortResponse");
    private final static QName _CreateSubnetResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "createSubnetResponse");
    private final static QName _CreateSDHTributaryLinkResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "createSDHTributaryLinkResponse");
    private final static QName _RelateObjectsToProjectResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "relateObjectsToProjectResponse");
    private final static QName _SetClassProperties_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "setClassProperties");
    private final static QName _CreateUser_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "createUser");
    private final static QName _RemoteActivityDefinition_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "remoteActivityDefinition");
    private final static QName _GetMPLSLinkEndpointsResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getMPLSLinkEndpointsResponse");
    private final static QName _GetConfigurationVariablesPools_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getConfigurationVariablesPools");
    private final static QName _GetWarehouseRootPools_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getWarehouseRootPools");
    private final static QName _RemovePossibleSpecialChildrenResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "removePossibleSpecialChildrenResponse");
    private final static QName _GetObjectLight_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getObjectLight");
    private final static QName _DeleteSDHContainerLinkResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "deleteSDHContainerLinkResponse");
    private final static QName _GetSiblingsResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getSiblingsResponse");
    private final static QName _UpdateProxyPoolResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "updateProxyPoolResponse");
    private final static QName _ReleaseObjectFromContractResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "releaseObjectFromContractResponse");
    private final static QName _UpdateObjectRelatedViewResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "updateObjectRelatedViewResponse");
    private final static QName _CreateBulkTemplateElementResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "createBulkTemplateElementResponse");
    private final static QName _UpdateGeneralView_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "updateGeneralView");
    private final static QName _DeleteAttributeResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "deleteAttributeResponse");
    private final static QName _GetNextActivityForProcessInstance_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getNextActivityForProcessInstance");
    private final static QName _SetPrivilegeToUserResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "setPrivilegeToUserResponse");
    private final static QName _CloseSession_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "closeSession");
    private final static QName _CreateBusinessRule_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "createBusinessRule");
    private final static QName _DeleteTaskResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "deleteTaskResponse");
    private final static QName _DeleteServicePool_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "deleteServicePool");
    private final static QName _GetPossibleSpecialChildren_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getPossibleSpecialChildren");
    private final static QName _LaunchSupervisedSynchronizationTaskResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "launchSupervisedSynchronizationTaskResponse");
    private final static QName _GetAllClasses_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getAllClasses");
    private final static QName _CreateSparePartResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "createSparePartResponse");
    private final static QName _DeleteActivity_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "deleteActivity");
    private final static QName _GetSubscribersForTaskResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getSubscribersForTaskResponse");
    private final static QName _UpdateTaskProperties_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "updateTaskProperties");
    private final static QName _GetAttributeForClassWithId_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getAttributeForClassWithId");
    private final static QName _GetPhysicalConnectionEndpoints_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getPhysicalConnectionEndpoints");
    private final static QName _SetAttributePropertiesForClassWithId_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "setAttributePropertiesForClassWithId");
    private final static QName _GetTemplatesForClass_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getTemplatesForClass");
    private final static QName _IsSubclassOf_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "isSubclassOf");
    private final static QName _LaunchSupervisedSynchronizationTask_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "launchSupervisedSynchronizationTask");
    private final static QName _UpdateTaskParameters_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "updateTaskParameters");
    private final static QName _CreateTemplateElement_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "createTemplateElement");
    private final static QName _GetE2EViewResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getE2EViewResponse");
    private final static QName _GetArtifactDefinitionForActivityResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getArtifactDefinitionForActivityResponse");
    private final static QName _DeleteActivityResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "deleteActivityResponse");
    private final static QName _CreateTemplateResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "createTemplateResponse");
    private final static QName _GetSpecialAttributes_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getSpecialAttributes");
    private final static QName _GetPool_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getPool");
    private final static QName _AssociateObjectsToContractResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "associateObjectsToContractResponse");
    private final static QName _GetAllClassesLight_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getAllClassesLight");
    private final static QName _LaunchAdHocAutomatedSynchronizationTaskResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "launchAdHocAutomatedSynchronizationTaskResponse");
    private final static QName _CreateCustomer_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "createCustomer");
    private final static QName _GetObjectRelatedViews_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getObjectRelatedViews");
    private final static QName _GetCommonParentResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getCommonParentResponse");
    private final static QName _DeleteSynchronizationDataSourceConfig_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "deleteSynchronizationDataSourceConfig");
    private final static QName _SetPoolProperties_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "setPoolProperties");
    private final static QName _GetArtifactForActivityResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getArtifactForActivityResponse");
    private final static QName _GetSubClassesLightNoRecursive_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getSubClassesLightNoRecursive");
    private final static QName _GetProcessDefinitionResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getProcessDefinitionResponse");
    private final static QName _AddUserToGroup_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "addUserToGroup");
    private final static QName _CreateServicePool_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "createServicePool");
    private final static QName _AssociatePhysicalNodeToWarehouse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "associatePhysicalNodeToWarehouse");
    private final static QName _CreateSynchronizationGroupResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "createSynchronizationGroupResponse");
    private final static QName _ReleaseObjectFromProxyResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "releaseObjectFromProxyResponse");
    private final static QName _DeleteProxy_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "deleteProxy");
    private final static QName _UpdateReportResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "updateReportResponse");
    private final static QName _GetAttribute_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getAttribute");
    private final static QName _UpdateValidatorDefinitionResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "updateValidatorDefinitionResponse");
    private final static QName _GetDeviceLayoutStructureResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getDeviceLayoutStructureResponse");
    private final static QName _ReleaseObjectFromService_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "releaseObjectFromService");
    private final static QName _UpdateProxy_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "updateProxy");
    private final static QName _GetDeviceLayouts_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getDeviceLayouts");
    private final static QName _CreateGroup_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "createGroup");
    private final static QName _CreatePhysicalConnectionResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "createPhysicalConnectionResponse");
    private final static QName _BulkUpload_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "bulkUpload");
    private final static QName _DeleteUsersResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "deleteUsersResponse");
    private final static QName _GetObjectSpecialChildrenResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getObjectSpecialChildrenResponse");
    private final static QName _GetRootPools_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getRootPools");
    private final static QName _UpdateTaskSchedule_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "updateTaskSchedule");
    private final static QName _CreatePoolItemResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "createPoolItemResponse");
    private final static QName _GetSpecialAttributesResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getSpecialAttributesResponse");
    private final static QName _ExecuteQueryResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "executeQueryResponse");
    private final static QName _CreateMPLSLinkResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "createMPLSLinkResponse");
    private final static QName _DeleteConfigurationVariable_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "deleteConfigurationVariable");
    private final static QName _GetClass_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getClass");
    private final static QName _GetProxyPools_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getProxyPools");
    private final static QName _GetPoolItemsResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getPoolItemsResponse");
    private final static QName _GetListTypeItem_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getListTypeItem");
    private final static QName _ExecuteInventoryLevelReport_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "executeInventoryLevelReport");
    private final static QName _GetAllConfigurationVariablesResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getAllConfigurationVariablesResponse");
    private final static QName _GetClassWithIdResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getClassWithIdResponse");
    private final static QName _GetContactResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getContactResponse");
    private final static QName _GetContractPool_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getContractPool");
    private final static QName _GetContainersBetweenObjectsResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getContainersBetweenObjectsResponse");
    private final static QName _GetNextActivityForProcessInstanceResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getNextActivityForProcessInstanceResponse");
    private final static QName _CreateTemplateSpecialElementResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "createTemplateSpecialElementResponse");
    private final static QName _RemoveObjectsFromFavoritesFolder_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "removeObjectsFromFavoritesFolder");
    private final static QName _UpdateProxyPool_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "updateProxyPool");
    private final static QName _CreateBulkSpecialObjects_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "createBulkSpecialObjects");
    private final static QName _ExecuteTaskResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "executeTaskResponse");
    private final static QName _ReleaseMirrorMultiplePortResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "releaseMirrorMultiplePortResponse");
    private final static QName _UpdateContactResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "updateContactResponse");
    private final static QName _GetConfigurationVariablesInPoolResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getConfigurationVariablesInPoolResponse");
    private final static QName _RelateSubnetToVlan_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "relateSubnetToVlan");
    private final static QName _CreateAttribute_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "createAttribute");
    private final static QName _CreateSynchronizationGroup_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "createSynchronizationGroup");
    private final static QName _DeleteTemplateElementResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "deleteTemplateElementResponse");
    private final static QName _CreateClassResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "createClassResponse");
    private final static QName _KillJobResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "killJobResponse");
    private final static QName _RelatePortToInterfaceResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "relatePortToInterfaceResponse");
    private final static QName _DeleteProcessDefinition_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "deleteProcessDefinition");
    private final static QName _CreateListTypeItem_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "createListTypeItem");
    private final static QName _SetUserPropertiesResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "setUserPropertiesResponse");
    private final static QName _SubscribeUserToTask_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "subscribeUserToTask");
    private final static QName _GetListTypeItems_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getListTypeItems");
    private final static QName _UpdateServicePoolResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "updateServicePoolResponse");
    private final static QName _GetGeneralView_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getGeneralView");
    private final static QName _UpdateTemplateElement_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "updateTemplateElement");
    private final static QName _CreateContractPool_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "createContractPool");
    private final static QName _CreateCustomerPool_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "createCustomerPool");
    private final static QName _GetFirstParentOfClassResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getFirstParentOfClassResponse");
    private final static QName _MoveObjectsToWarehousePoolResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "moveObjectsToWarehousePoolResponse");
    private final static QName _GetProjectsRelatedToObject_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getProjectsRelatedToObject");
    private final static QName _GetConfigurationVariablesWithPrefixResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getConfigurationVariablesWithPrefixResponse");
    private final static QName _CreateSubnetPool_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "createSubnetPool");
    private final static QName _GetContractPools_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getContractPools");
    private final static QName _CreateRootPoolResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "createRootPoolResponse");
    private final static QName _GetGeneralViews_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getGeneralViews");
    private final static QName _DeleteListTypeItem_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "deleteListTypeItem");
    private final static QName _GetAllValidatorDefinitionsResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getAllValidatorDefinitionsResponse");
    private final static QName _SetPrivilegeToGroup_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "setPrivilegeToGroup");
    private final static QName _RemovePossibleChildrenForClassWithId_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "removePossibleChildrenForClassWithId");
    private final static QName _SetUserProperties_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "setUserProperties");
    private final static QName _DeleteValidatorDefinition_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "deleteValidatorDefinition");
    private final static QName _GetGeneralViewsResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getGeneralViewsResponse");
    private final static QName _GetServicePoolsInCostumerResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getServicePoolsInCostumerResponse");
    private final static QName _GetConfigurationVariablesPoolsResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getConfigurationVariablesPoolsResponse");
    private final static QName _GetIPAddressResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getIPAddressResponse");
    private final static QName _UpdateCustomer_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "updateCustomer");
    private final static QName _GetUpstreamClassHierarchy_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getUpstreamClassHierarchy");
    private final static QName _UpdateTaskNotificationType_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "updateTaskNotificationType");
    private final static QName _GetProxiesInPoolResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getProxiesInPoolResponse");
    private final static QName _DeleteContract_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "deleteContract");
    private final static QName _DeleteBusinessRuleResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "deleteBusinessRuleResponse");
    private final static QName _GetPossibleChildrenNoRecursive_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getPossibleChildrenNoRecursive");
    private final static QName _UpdateFavoritesFolderResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "updateFavoritesFolderResponse");
    private final static QName _GetSynchronizationGroups_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getSynchronizationGroups");
    private final static QName _RelateObjectsToServiceResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "relateObjectsToServiceResponse");
    private final static QName _DeleteContractPool_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "deleteContractPool");
    private final static QName _CreateQuery_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "createQuery");
    private final static QName _GetListTypeItemRelatedViews_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getListTypeItemRelatedViews");
    private final static QName _GetTasks_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getTasks");
    private final static QName _GetActivityResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getActivityResponse");
    private final static QName _GetProjectResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getProjectResponse");
    private final static QName _GetSubnetsInSubnet_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getSubnetsInSubnet");
    private final static QName _GetPoolsInPoolResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getPoolsInPoolResponse");
    private final static QName _GetBusinessRulesResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getBusinessRulesResponse");
    private final static QName _MovePoolItemToPoolResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "movePoolItemToPoolResponse");
    private final static QName _UpdateContractPoolResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "updateContractPoolResponse");
    private final static QName _GetPoolsInPool_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getPoolsInPool");
    private final static QName _UpdateTaskParametersResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "updateTaskParametersResponse");
    private final static QName _AddPossibleChildrenForClassWithId_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "addPossibleChildrenForClassWithId");
    private final static QName _CopyPoolItemToPoolResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "copyPoolItemToPoolResponse");
    private final static QName _MoveObjectsToPool_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "moveObjectsToPool");
    private final static QName _CreateSDHContainerLinkResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "createSDHContainerLinkResponse");
    private final static QName _GetClassResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getClassResponse");
    private final static QName _GetAllClassesResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getAllClassesResponse");
    private final static QName _AddObjectsToFavoritesFolder_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "addObjectsToFavoritesFolder");
    private final static QName _GetPoolsInObjectResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getPoolsInObjectResponse");
    private final static QName _GetGroupsForUser_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getGroupsForUser");
    private final static QName _CreateClass_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "createClass");
    private final static QName _ReleasePortFromInterface_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "releasePortFromInterface");
    private final static QName _GetClassHierarchyResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getClassHierarchyResponse");
    private final static QName _DeleteCustomerPool_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "deleteCustomerPool");
    private final static QName _ExecuteClassLevelReportResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "executeClassLevelReportResponse");
    private final static QName _DeletePools_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "deletePools");
    private final static QName _DeleteReportResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "deleteReportResponse");
    private final static QName _ExecuteQuery_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "executeQuery");
    private final static QName _GetAllValidatorDefinitions_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getAllValidatorDefinitions");
    private final static QName _GetAllProjects_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getAllProjects");
    private final static QName _GetParentsUntilFirstOfClass_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getParentsUntilFirstOfClass");
    private final static QName _GetObjectChildren_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getObjectChildren");
    private final static QName _UpdateService_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "updateService");
    private final static QName _GetArtifactForActivity_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getArtifactForActivity");
    private final static QName _GetParentResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getParentResponse");
    private final static QName _RelateSubnetToVrf_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "relateSubnetToVrf");
    private final static QName _CreateWarehouse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "createWarehouse");
    private final static QName _GetCustomersInPool_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getCustomersInPool");
    private final static QName _GetContract_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getContract");
    private final static QName _ConnectPhysicalContainersResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "connectPhysicalContainersResponse");
    private final static QName _GetTemplateSpecialElementChildrenResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getTemplateSpecialElementChildrenResponse");
    private final static QName _CreateCustomerPoolResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "createCustomerPoolResponse");
    private final static QName _CreateGeneralViewResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "createGeneralViewResponse");
    private final static QName _FindSDHRoutesUsingContainerLinksResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "findSDHRoutesUsingContainerLinksResponse");
    private final static QName _CommitActivityResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "commitActivityResponse");
    private final static QName _SearchForContactsResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "searchForContactsResponse");
    private final static QName _CreateGeneralView_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "createGeneralView");
    private final static QName _AssociateObjectToProxyResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "associateObjectToProxyResponse");
    private final static QName _GetInventoryLevelReportsResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getInventoryLevelReportsResponse");
    private final static QName _GetContractPoolsResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getContractPoolsResponse");
    private final static QName _ReleaseObjectFromContract_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "releaseObjectFromContract");
    private final static QName _CreateSDHContainerLink_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "createSDHContainerLink");
    private final static QName _GetProjectPoolsResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getProjectPoolsResponse");
    private final static QName _GetTemplateElementResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getTemplateElementResponse");
    private final static QName _CreateConfigurationVariable_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "createConfigurationVariable");
    private final static QName _ServerSideException_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "ServerSideException");
    private final static QName _GetFileResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getFileResponse");
    private final static QName _GetPossibleSpecialChildrenNoRecursiveResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getPossibleSpecialChildrenNoRecursiveResponse");
    private final static QName _GetContractResourcesResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getContractResourcesResponse");
    private final static QName _UpdateProjectPoolResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "updateProjectPoolResponse");
    private final static QName _GetBusinessObjectAuditTrail_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getBusinessObjectAuditTrail");
    private final static QName _GetReport_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getReport");
    private final static QName _GetObjectSpecialChildren_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getObjectSpecialChildren");
    private final static QName _UpdateObject_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "updateObject");
    private final static QName _MoveObjectsToWarehouseResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "moveObjectsToWarehouseResponse");
    private final static QName _RemovePossibleSpecialChildren_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "removePossibleSpecialChildren");
    private final static QName _GetUsersInGroup_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getUsersInGroup");
    private final static QName _GetFavoritesFoldersForObjectResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getFavoritesFoldersForObjectResponse");
    private final static QName _GetLinkConnectedToPortResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getLinkConnectedToPortResponse");
    private final static QName _DeleteProxyResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "deleteProxyResponse");
    private final static QName _UpdateConfigurationVariablesPool_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "updateConfigurationVariablesPool");
    private final static QName _HasAttribute_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "hasAttribute");
    private final static QName _UpdateObjectResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "updateObjectResponse");
    private final static QName _RelateObjectToProject_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "relateObjectToProject");
    private final static QName _GetUpstreamSpecialContainmentHierarchyResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getUpstreamSpecialContainmentHierarchyResponse");
    private final static QName _CreateTask_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "createTask");
    private final static QName _GetCommonParent_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getCommonParent");
    private final static QName _FindSDHRoutesUsingTransportLinks_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "findSDHRoutesUsingTransportLinks");
    private final static QName _DeleteProjectResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "deleteProjectResponse");
    private final static QName _DetachFileFromObject_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "detachFileFromObject");
    private final static QName _DeleteListTypeItemResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "deleteListTypeItemResponse");
    private final static QName _GetPhysicalConnectionsInObjectResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getPhysicalConnectionsInObjectResponse");
    private final static QName _GetObjectRelatedView_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getObjectRelatedView");
    private final static QName _GetChildrenOfClassResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getChildrenOfClassResponse");
    private final static QName _CreateProject_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "createProject");
    private final static QName _DeletePhysicalConnectionResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "deletePhysicalConnectionResponse");
    private final static QName _GetFavoritesFoldersForUserResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getFavoritesFoldersForUserResponse");
    private final static QName _CreatePoolInPool_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "createPoolInPool");
    private final static QName _CreatePoolInPoolResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "createPoolInPoolResponse");
    private final static QName _GetListTypeItemRelatedViewsResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getListTypeItemRelatedViewsResponse");
    private final static QName _CreateSynchronizationDataSourceConfigResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "createSynchronizationDataSourceConfigResponse");
    private final static QName _GetContainersBetweenObjects_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getContainersBetweenObjects");
    private final static QName _CreateBulkSpecialTemplateElement_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "createBulkSpecialTemplateElement");
    private final static QName _DeleteClass_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "deleteClass");
    private final static QName _GetDeviceLayoutsResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getDeviceLayoutsResponse");
    private final static QName _MoveSpecialObjects_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "moveSpecialObjects");
    private final static QName _GetAllContracts_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getAllContracts");
    private final static QName _GetE2EView_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getE2EView");
    private final static QName _CreateListTypeItemRelatedViewResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "createListTypeItemRelatedViewResponse");
    private final static QName _CreateSpecialObjectResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "createSpecialObjectResponse");
    private final static QName _CreateConfigurationVariablesPoolResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "createConfigurationVariablesPoolResponse");
    private final static QName _CreateSDHTransportLinkResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "createSDHTransportLinkResponse");
    private final static QName _UpdateProcessDefinitionResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "updateProcessDefinitionResponse");
    private final static QName _DeleteSynchronizationGroup_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "deleteSynchronizationGroup");
    private final static QName _RelateObjectsToContractResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "relateObjectsToContractResponse");
    private final static QName _GetConfigurationVariablesWithPrefix_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getConfigurationVariablesWithPrefix");
    private final static QName _SubscribeUserToTaskResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "subscribeUserToTaskResponse");
    private final static QName _CreateActivityResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "createActivityResponse");
    private final static QName _AddPossibleSpecialChildrenWithIdResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "addPossibleSpecialChildrenWithIdResponse");
    private final static QName _UpdateTaskNotificationTypeResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "updateTaskNotificationTypeResponse");
    private final static QName _DeleteServiceResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "deleteServiceResponse");
    private final static QName _RelatePortToInterface_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "relatePortToInterface");
    private final static QName _CreateContactResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "createContactResponse");
    private final static QName _GetCustomersInPoolResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getCustomersInPoolResponse");
    private final static QName _GetSubnetUsedIps_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getSubnetUsedIps");
    private final static QName _UpdateGeneralViewResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "updateGeneralViewResponse");
    private final static QName _CreateTemplate_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "createTemplate");
    private final static QName _DeleteValidatorDefinitionResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "deleteValidatorDefinitionResponse");
    private final static QName _DisconnectMPLSLink_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "disconnectMPLSLink");
    private final static QName _GetInstanceableListTypesResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getInstanceableListTypesResponse");
    private final static QName _GetProjectsWithFilter_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getProjectsWithFilter");
    private final static QName _GetPossibleSpecialChildrenNoRecursive_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getPossibleSpecialChildrenNoRecursive");
    private final static QName _GetProject_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getProject");
    private final static QName _GetTemplatesForClassResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getTemplatesForClassResponse");
    private final static QName _GetAllClassesLightResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getAllClassesLightResponse");
    private final static QName _DeleteQueryResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "deleteQueryResponse");
    private final static QName _GetAllServices_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getAllServices");
    private final static QName _GetSyncDataSourceConfigurationsResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getSyncDataSourceConfigurationsResponse");
    private final static QName _GetSpecialChildrenOfClassLightRecursive_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getSpecialChildrenOfClassLightRecursive");
    private final static QName _SetClassPropertiesResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "setClassPropertiesResponse");
    private final static QName _GetFilesForObjectResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getFilesForObjectResponse");
    private final static QName _GetPhysicalConnectionsInObject_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getPhysicalConnectionsInObject");
    private final static QName _GetCurrentJobs_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getCurrentJobs");
    private final static QName _AssociateObjectToProxy_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "associateObjectToProxy");
    private final static QName _GetServicePoolsInCostumer_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getServicePoolsInCostumer");
    private final static QName _MoveObjectsToWarehousePool_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "moveObjectsToWarehousePool");
    private final static QName _UpdateObjectRelatedView_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "updateObjectRelatedView");
    private final static QName _GetParentOfClass_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getParentOfClass");
    private final static QName _CopyTemplateElementsResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "copyTemplateElementsResponse");
    private final static QName _ItOverlapsResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "itOverlapsResponse");
    private final static QName _UpdateTaskScheduleResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "updateTaskScheduleResponse");
    private final static QName _GetService_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getService");
    private final static QName _CreateInventoryLevelReportResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "createInventoryLevelReportResponse");
    private final static QName _RelateSubnetToVrfResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "relateSubnetToVrfResponse");
    private final static QName _GetProjectsWithFilterResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getProjectsWithFilterResponse");
    private final static QName _GetSubnetsResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getSubnetsResponse");
    private final static QName _GetTask_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getTask");
    private final static QName _UpdateSyncDataSourceConfigurationResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "updateSyncDataSourceConfigurationResponse");
    private final static QName _AssociatesPhysicalNodeToWarehouse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "associatesPhysicalNodeToWarehouse");
    private final static QName _GetObjectChildrenForClassWithIdResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getObjectChildrenForClassWithIdResponse");
    private final static QName _GetConfigurationVariablesInPool_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getConfigurationVariablesInPool");
    private final static QName _UpdateProjectPool_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "updateProjectPool");
    private final static QName _CreateProxyPool_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "createProxyPool");
    private final static QName _ReleaseSubnetFromVRFResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "releaseSubnetFromVRFResponse");
    private final static QName _GetObjectsOfClassLightResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getObjectsOfClassLightResponse");
    private final static QName _ReleaseObjectFromServiceResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "releaseObjectFromServiceResponse");
    private final static QName _ConnectPhysicalLinksResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "connectPhysicalLinksResponse");
    private final static QName _CreateClassLevelReportResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "createClassLevelReportResponse");
    private final static QName _CreateTemplateSpecialElement_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "createTemplateSpecialElement");
    private final static QName _GetParents_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getParents");
    private final static QName _GetCustomerPools_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getCustomerPools");
    private final static QName _CreatePoolInWarehouse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "createPoolInWarehouse");
    private final static QName _UpdateSynchronizationGroup_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "updateSynchronizationGroup");
    private final static QName _GetContractResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getContractResponse");
    private final static QName _GetObject_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getObject");
    private final static QName _GetLogicalLinkDetails_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getLogicalLinkDetails");
    private final static QName _GetPoolsInObject_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getPoolsInObject");
    private final static QName _GetFavoritesFolder_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getFavoritesFolder");
    private final static QName _GetListTypeItemRelatedViewResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getListTypeItemRelatedViewResponse");
    private final static QName _CreateService_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "createService");
    private final static QName _DeleteAttributeForClassWithId_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "deleteAttributeForClassWithId");
    private final static QName _DeleteSparePoolResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "deleteSparePoolResponse");
    private final static QName _GetSubnetPoolsResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getSubnetPoolsResponse");
    private final static QName _GetUpstreamClassHierarchyResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getUpstreamClassHierarchyResponse");
    private final static QName _CreateQueryResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "createQueryResponse");
    private final static QName _GetObjectChildrenResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getObjectChildrenResponse");
    private final static QName _GetServicePoolResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getServicePoolResponse");
    private final static QName _DeleteSDHTransportLink_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "deleteSDHTransportLink");
    private final static QName _GetAttributeForClassWithIdResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getAttributeForClassWithIdResponse");
    private final static QName _GetPoolItems_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getPoolItems");
    private final static QName _UpdateOSPView_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "updateOSPView");
    private final static QName _GetTaskResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getTaskResponse");
    private final static QName _UpdateFavoritesFolder_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "updateFavoritesFolder");
    private final static QName _GetCurrentJobsResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getCurrentJobsResponse");
    private final static QName _GetSpecialAttribute_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getSpecialAttribute");
    private final static QName _DeleteSDHContainerLink_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "deleteSDHContainerLink");
    private final static QName _CreateBulkSpecialObjectsResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "createBulkSpecialObjectsResponse");
    private final static QName _UpdateContract_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "updateContract");
    private final static QName _UpdateServicePool_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "updateServicePool");
    private final static QName _DeleteMPLSLink_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "deleteMPLSLink");
    private final static QName _GetSyncDataSourceConfigurationResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getSyncDataSourceConfigurationResponse");
    private final static QName _KillJob_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "killJob");
    private final static QName _SetAttributePropertiesForClassWithIdResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "setAttributePropertiesForClassWithIdResponse");
    private final static QName _CreatePoolInObjectResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "createPoolInObjectResponse");
    private final static QName _GetPossibleSpecialChildrenResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getPossibleSpecialChildrenResponse");
    private final static QName _UpdateTemplateElementResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "updateTemplateElementResponse");
    private final static QName _GetSynchronizationGroup_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getSynchronizationGroup");
    private final static QName _CreateGroupResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "createGroupResponse");
    private final static QName _BulkUploadResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "bulkUploadResponse");
    private final static QName _UpdateSynchronizationGroupResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "updateSynchronizationGroupResponse");
    private final static QName _GetAllServicesResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getAllServicesResponse");
    private final static QName _GetGeneralActivityAuditTrail_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getGeneralActivityAuditTrail");
    private final static QName _GetServicesInPoolResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getServicesInPoolResponse");
    private final static QName _CreateAttributeResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "createAttributeResponse");
    private final static QName _DeleteProjectPoolResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "deleteProjectPoolResponse");
    private final static QName _GetOSPViewResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getOSPViewResponse");
    private final static QName _GetObjectsWithFilter_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getObjectsWithFilter");
    private final static QName _GetDeviceLayoutStructure_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getDeviceLayoutStructure");
    private final static QName _GetProjectsInPool_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getProjectsInPool");
    private final static QName _GetOSPViews_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getOSPViews");
    private final static QName _GetMPLSLinkEndpoints_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getMPLSLinkEndpoints");
    private final static QName _AddPossibleSpecialChildrenResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "addPossibleSpecialChildrenResponse");
    private final static QName _CreateValidatorDefinition_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "createValidatorDefinition");
    private final static QName _CreatePoolItem_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "createPoolItem");
    private final static QName _GetPhysicalTree_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getPhysicalTree");
    private final static QName _ValidateSavedE2EViewResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "validateSavedE2EViewResponse");
    private final static QName _MoveSyncDataSourceConfigurationResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "moveSyncDataSourceConfigurationResponse");
    private final static QName _GetGroupsForUserResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getGroupsForUserResponse");
    private final static QName _ReleasePortFromIP_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "releasePortFromIP");
    private final static QName _GetFile_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getFile");
    private final static QName _GetPossibleChildrenResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getPossibleChildrenResponse");
    private final static QName _RemoveObjectsFromFavoritesFolderResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "removeObjectsFromFavoritesFolderResponse");
    private final static QName _RemoveUserFromGroup_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "removeUserFromGroup");
    private final static QName _GetSpecialChildrenOfClassLightResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getSpecialChildrenOfClassLightResponse");
    private final static QName _ConnectPhysicalContainers_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "connectPhysicalContainers");
    private final static QName _GetConfigurationVariable_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getConfigurationVariable");
    private final static QName _GetSDHTransportLinkStructureResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getSDHTransportLinkStructureResponse");
    private final static QName _IsSubclassOfResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "isSubclassOfResponse");
    private final static QName _GetIPAddress_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getIPAddress");
    private final static QName _GetSDHContainerLinkStructure_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getSDHContainerLinkStructure");
    private final static QName _AssociatesPhysicalNodeToWarehouseResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "associatesPhysicalNodeToWarehouseResponse");
    private final static QName _GetTasksForUserResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getTasksForUserResponse");
    private final static QName _GetLinkConnectedToPort_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getLinkConnectedToPort");
    private final static QName _GetObjectsRelatedToService_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getObjectsRelatedToService");
    private final static QName _ConnectMplsLinkResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "connectMplsLinkResponse");
    private final static QName _GetPossibleChildren_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getPossibleChildren");
    private final static QName _CreateProcessInstance_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "createProcessInstance");
    private final static QName _CreateSessionResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "createSessionResponse");
    private final static QName _SaveQuery_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "saveQuery");
    private final static QName _RelateSubnetToVlanResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "relateSubnetToVlanResponse");
    private final static QName _DeleteAttributeForClassWithIdResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "deleteAttributeForClassWithIdResponse");
    private final static QName _GetMandatoryAttributesInClass_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getMandatoryAttributesInClass");
    private final static QName _CopySyncDataSourceConfiguration_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "copySyncDataSourceConfiguration");
    private final static QName _FindSDHRoutesUsingContainerLinks_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "findSDHRoutesUsingContainerLinks");
    private final static QName _GetWarehousesInPoolResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getWarehousesInPoolResponse");
    private final static QName _CopySpecialObjects_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "copySpecialObjects");
    private final static QName _UpdateCustomerPool_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "updateCustomerPool");
    private final static QName _DownloadBulkLoadLogResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "downloadBulkLoadLogResponse");
    private final static QName _GetObjectsInSparePool_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getObjectsInSparePool");
    private final static QName _RelateObjectToContract_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "relateObjectToContract");
    private final static QName _GetBusinessRules_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getBusinessRules");
    private final static QName _GetProjectActivities_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getProjectActivities");
    private final static QName _GetPhysicalTreeResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getPhysicalTreeResponse");
    private final static QName _GetFavoritesFoldersForObject_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getFavoritesFoldersForObject");
    private final static QName _UpdateOSPViewResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "updateOSPViewResponse");
    private final static QName _DeleteObjectResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "deleteObjectResponse");
    private final static QName _GetProjectActivitiesResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getProjectActivitiesResponse");
    private final static QName _ConnectMirrorMultiplePortResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "connectMirrorMultiplePortResponse");
    private final static QName _GetClassLevelReports_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getClassLevelReports");
    private final static QName _RelateObjectsToService_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "relateObjectsToService");
    private final static QName _CreateBusinessRuleResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "createBusinessRuleResponse");
    private final static QName _CreateTemplateElementResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "createTemplateElementResponse");
    private final static QName _GetContractPoolResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getContractPoolResponse");
    private final static QName _UpdateTaskPropertiesResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "updateTaskPropertiesResponse");
    private final static QName _CreateWarehouseResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "createWarehouseResponse");
    private final static QName _AddPossibleSpecialChildren_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "addPossibleSpecialChildren");
    private final static QName _DeleteGeneralView_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "deleteGeneralView");
    private final static QName _GetTemplateElementChildrenResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getTemplateElementChildrenResponse");
    private final static QName _RemoteProcessDefinition_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "remoteProcessDefinition");
    private final static QName _MovePoolItemToPool_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "movePoolItemToPool");
    private final static QName _CreateTaskResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "createTaskResponse");
    private final static QName _CreateProxy_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "createProxy");
    private final static QName _GetSynchronizationGroupsResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getSynchronizationGroupsResponse");
    private final static QName _DeleteFavoritesFoldersResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "deleteFavoritesFoldersResponse");
    private final static QName _CreateContract_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "createContract");
    private final static QName _GetProjectsRelatedToObjectResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getProjectsRelatedToObjectResponse");
    private final static QName _GetBGPMap_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getBGPMap");
    private final static QName _ReleaseObjectFromProjectResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "releaseObjectFromProjectResponse");
    private final static QName _GetContactsForCustomerResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getContactsForCustomerResponse");
    private final static QName _GetInstanceableListTypes_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getInstanceableListTypes");
    private final static QName _GetQueriesResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getQueriesResponse");
    private final static QName _CreateServicePoolResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "createServicePoolResponse");
    private final static QName _CreateValidatorDefinitionResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "createValidatorDefinitionResponse");
    private final static QName _GetQueries_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getQueries");
    private final static QName _GetContractResources_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getContractResources");
    private final static QName _ReleaseSubnetFromVRF_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "releaseSubnetFromVRF");
    private final static QName _DeleteOSPViewResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "deleteOSPViewResponse");
    private final static QName _GetUsersResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getUsersResponse");
    private final static QName _UpdateCustomerResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "updateCustomerResponse");
    private final static QName _ItOverlaps_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "itOverlaps");
    private final static QName _LaunchAutomatedSynchronizationTaskResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "launchAutomatedSynchronizationTaskResponse");
    private final static QName _MoveObjectsResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "moveObjectsResponse");
    private final static QName _ReleaseObjectFromProject_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "releaseObjectFromProject");
    private final static QName _GetClassLevelReportsResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getClassLevelReportsResponse");
    private final static QName _GetContacts_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getContacts");
    private final static QName _DeleteContractResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "deleteContractResponse");
    private final static QName _MoveSpecialObjectsResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "moveSpecialObjectsResponse");
    private final static QName _UpdateActivity_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "updateActivity");
    private final static QName _UpdateProcessDefinition_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "updateProcessDefinition");
    private final static QName _UpdateReportParameters_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "updateReportParameters");
    private final static QName _GetLogicalLinkDetailsResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getLogicalLinkDetailsResponse");
    private final static QName _HasAttributeResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "hasAttributeResponse");
    private final static QName _CreateObjectResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "createObjectResponse");
    private final static QName _AttachFileToObjectResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "attachFileToObjectResponse");
    private final static QName _DeleteSDHTransportLinkResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "deleteSDHTransportLinkResponse");
    private final static QName _GetWarehouseRootPoolsResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getWarehouseRootPoolsResponse");
    private final static QName _DeleteListTypeItemRelatedViewResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "deleteListTypeItemRelatedViewResponse");
    private final static QName _CreatePhysicalConnectionsResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "createPhysicalConnectionsResponse");
    private final static QName _CloseSessionResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "closeSessionResponse");
    private final static QName _RemoveUserFromGroupResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "removeUserFromGroupResponse");
    private final static QName _GetConfigurationVariableResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getConfigurationVariableResponse");
    private final static QName _UpdateActivityResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "updateActivityResponse");
    private final static QName _DeleteProject_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "deleteProject");
    private final static QName _DeleteSubnetPools_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "deleteSubnetPools");
    private final static QName _DeleteSubnetsResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "deleteSubnetsResponse");
    private final static QName _GetListTypeItemRelatedView_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getListTypeItemRelatedView");
    private final static QName _GetReportResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getReportResponse");
    private final static QName _GetFavoritesFoldersForUser_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getFavoritesFoldersForUser");
    private final static QName _GetListTypeItemResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getListTypeItemResponse");
    private final static QName _CreateBulkObjects_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "createBulkObjects");
    private final static QName _GetChildrenOfClassLightResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getChildrenOfClassLightResponse");
    private final static QName _DeleteUsers_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "deleteUsers");
    private final static QName _DeleteCustomerPoolResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "deleteCustomerPoolResponse");
    private final static QName _GetAffectedServicesResponse_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getAffectedServicesResponse");
    private final static QName _GetGroups_QNAME = new QName("http://ws.northbound.kuwaiba.neotropic.org/", "getGroups");
    private final static QName _UpdateObjectRelatedViewBackground_QNAME = new QName("", "background");
    private final static QName _UpdateObjectRelatedViewStructure_QNAME = new QName("", "structure");
    private final static QName _GetDeviceLayoutStructureResponseReturn_QNAME = new QName("", "return");
    private final static QName _AttachFileToObjectFile_QNAME = new QName("", "file");
    private final static QName _CreateClassIcon_QNAME = new QName("", "icon");
    private final static QName _CreateClassSmallIcon_QNAME = new QName("", "smallIcon");
    private final static QName _UpdateOSPViewContent_QNAME = new QName("", "content");
    private final static QName _CreateQueryQueryStructure_QNAME = new QName("", "queryStructure");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.inventory.communications.wsclient
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link RemoteKpi }
     * 
     */
    public RemoteKpi createRemoteKpi() {
        return new RemoteKpi();
    }

    /**
     * Create an instance of {@link RemoteKpi.Thresholds }
     * 
     */
    public RemoteKpi.Thresholds createRemoteKpiThresholds() {
        return new RemoteKpi.Thresholds();
    }

    /**
     * Create an instance of {@link UnsubscribeUserFromTaskResponse }
     * 
     */
    public UnsubscribeUserFromTaskResponse createUnsubscribeUserFromTaskResponse() {
        return new UnsubscribeUserFromTaskResponse();
    }

    /**
     * Create an instance of {@link DeleteProxyPool }
     * 
     */
    public DeleteProxyPool createDeleteProxyPool() {
        return new DeleteProxyPool();
    }

    /**
     * Create an instance of {@link ReleaseMirrorMultiplePort }
     * 
     */
    public ReleaseMirrorMultiplePort createReleaseMirrorMultiplePort() {
        return new ReleaseMirrorMultiplePort();
    }

    /**
     * Create an instance of {@link UnsubscribeUserFromTask }
     * 
     */
    public UnsubscribeUserFromTask createUnsubscribeUserFromTask() {
        return new UnsubscribeUserFromTask();
    }

    /**
     * Create an instance of {@link GetPoolsInWarehouse }
     * 
     */
    public GetPoolsInWarehouse createGetPoolsInWarehouse() {
        return new GetPoolsInWarehouse();
    }

    /**
     * Create an instance of {@link CreateObjectRelatedViewResponse }
     * 
     */
    public CreateObjectRelatedViewResponse createCreateObjectRelatedViewResponse() {
        return new CreateObjectRelatedViewResponse();
    }

    /**
     * Create an instance of {@link GetAllCustomers }
     * 
     */
    public GetAllCustomers createGetAllCustomers() {
        return new GetAllCustomers();
    }

    /**
     * Create an instance of {@link SaveQueryResponse }
     * 
     */
    public SaveQueryResponse createSaveQueryResponse() {
        return new SaveQueryResponse();
    }

    /**
     * Create an instance of {@link UpdateConfigurationVariable }
     * 
     */
    public UpdateConfigurationVariable createUpdateConfigurationVariable() {
        return new UpdateConfigurationVariable();
    }

    /**
     * Create an instance of {@link CopyObjectsResponse }
     * 
     */
    public CopyObjectsResponse createCopyObjectsResponse() {
        return new CopyObjectsResponse();
    }

    /**
     * Create an instance of {@link DeleteFavoritesFolders }
     * 
     */
    public DeleteFavoritesFolders createDeleteFavoritesFolders() {
        return new DeleteFavoritesFolders();
    }

    /**
     * Create an instance of {@link RemovePrivilegeFromUserResponse }
     * 
     */
    public RemovePrivilegeFromUserResponse createRemovePrivilegeFromUserResponse() {
        return new RemovePrivilegeFromUserResponse();
    }

    /**
     * Create an instance of {@link CopyObjects }
     * 
     */
    public CopyObjects createCopyObjects() {
        return new CopyObjects();
    }

    /**
     * Create an instance of {@link CreateAttributeForClassWithIdResponse }
     * 
     */
    public CreateAttributeForClassWithIdResponse createCreateAttributeForClassWithIdResponse() {
        return new CreateAttributeForClassWithIdResponse();
    }

    /**
     * Create an instance of {@link SetPrivilegeToGroupResponse }
     * 
     */
    public SetPrivilegeToGroupResponse createSetPrivilegeToGroupResponse() {
        return new SetPrivilegeToGroupResponse();
    }

    /**
     * Create an instance of {@link CreateRootPool }
     * 
     */
    public CreateRootPool createCreateRootPool() {
        return new CreateRootPool();
    }

    /**
     * Create an instance of {@link CreateOSPViewResponse }
     * 
     */
    public CreateOSPViewResponse createCreateOSPViewResponse() {
        return new CreateOSPViewResponse();
    }

    /**
     * Create an instance of {@link DeleteObjects }
     * 
     */
    public DeleteObjects createDeleteObjects() {
        return new DeleteObjects();
    }

    /**
     * Create an instance of {@link GetContractsInPoolResponse }
     * 
     */
    public GetContractsInPoolResponse createGetContractsInPoolResponse() {
        return new GetContractsInPoolResponse();
    }

    /**
     * Create an instance of {@link GetSyncDataSourceConfigurations }
     * 
     */
    public GetSyncDataSourceConfigurations createGetSyncDataSourceConfigurations() {
        return new GetSyncDataSourceConfigurations();
    }

    /**
     * Create an instance of {@link RelateObjectToService }
     * 
     */
    public RelateObjectToService createRelateObjectToService() {
        return new RelateObjectToService();
    }

    /**
     * Create an instance of {@link GetSynchronizationProviders }
     * 
     */
    public GetSynchronizationProviders createGetSynchronizationProviders() {
        return new GetSynchronizationProviders();
    }

    /**
     * Create an instance of {@link GetOSPView }
     * 
     */
    public GetOSPView createGetOSPView() {
        return new GetOSPView();
    }

    /**
     * Create an instance of {@link AddIPAddress }
     * 
     */
    public AddIPAddress createAddIPAddress() {
        return new AddIPAddress();
    }

    /**
     * Create an instance of {@link DeleteServicePoolResponse }
     * 
     */
    public DeleteServicePoolResponse createDeleteServicePoolResponse() {
        return new DeleteServicePoolResponse();
    }

    /**
     * Create an instance of {@link CreateSubnetPoolResponse }
     * 
     */
    public CreateSubnetPoolResponse createCreateSubnetPoolResponse() {
        return new CreateSubnetPoolResponse();
    }

    /**
     * Create an instance of {@link GetFirstParentOfClass }
     * 
     */
    public GetFirstParentOfClass createGetFirstParentOfClass() {
        return new GetFirstParentOfClass();
    }

    /**
     * Create an instance of {@link GetProjectPool }
     * 
     */
    public GetProjectPool createGetProjectPool() {
        return new GetProjectPool();
    }

    /**
     * Create an instance of {@link UpdateReportParametersResponse }
     * 
     */
    public UpdateReportParametersResponse createUpdateReportParametersResponse() {
        return new UpdateReportParametersResponse();
    }

    /**
     * Create an instance of {@link ReleasePhysicalNodeFromWarehouse }
     * 
     */
    public ReleasePhysicalNodeFromWarehouse createReleasePhysicalNodeFromWarehouse() {
        return new ReleasePhysicalNodeFromWarehouse();
    }

    /**
     * Create an instance of {@link CreateProcessDefinitionResponse }
     * 
     */
    public CreateProcessDefinitionResponse createCreateProcessDefinitionResponse() {
        return new CreateProcessDefinitionResponse();
    }

    /**
     * Create an instance of {@link DeleteSubnetPoolsResponse }
     * 
     */
    public DeleteSubnetPoolsResponse createDeleteSubnetPoolsResponse() {
        return new DeleteSubnetPoolsResponse();
    }

    /**
     * Create an instance of {@link GetAllProxies }
     * 
     */
    public GetAllProxies createGetAllProxies() {
        return new GetAllProxies();
    }

    /**
     * Create an instance of {@link CreateActivity }
     * 
     */
    public CreateActivity createCreateActivity() {
        return new CreateActivity();
    }

    /**
     * Create an instance of {@link RunValidationsForObject }
     * 
     */
    public RunValidationsForObject createRunValidationsForObject() {
        return new RunValidationsForObject();
    }

    /**
     * Create an instance of {@link RelateObjectToServiceResponse }
     * 
     */
    public RelateObjectToServiceResponse createRelateObjectToServiceResponse() {
        return new RelateObjectToServiceResponse();
    }

    /**
     * Create an instance of {@link DeleteWarehouseResponse }
     * 
     */
    public DeleteWarehouseResponse createDeleteWarehouseResponse() {
        return new DeleteWarehouseResponse();
    }

    /**
     * Create an instance of {@link RelateIPtoPort }
     * 
     */
    public RelateIPtoPort createRelateIPtoPort() {
        return new RelateIPtoPort();
    }

    /**
     * Create an instance of {@link ReleasePhysicalNodeFromWarehouseResponse }
     * 
     */
    public ReleasePhysicalNodeFromWarehouseResponse createReleasePhysicalNodeFromWarehouseResponse() {
        return new ReleasePhysicalNodeFromWarehouseResponse();
    }

    /**
     * Create an instance of {@link GetObjectsRelatedToServiceResponse }
     * 
     */
    public GetObjectsRelatedToServiceResponse createGetObjectsRelatedToServiceResponse() {
        return new GetObjectsRelatedToServiceResponse();
    }

    /**
     * Create an instance of {@link RemovePossibleChildrenForClassWithIdResponse }
     * 
     */
    public RemovePossibleChildrenForClassWithIdResponse createRemovePossibleChildrenForClassWithIdResponse() {
        return new RemovePossibleChildrenForClassWithIdResponse();
    }

    /**
     * Create an instance of {@link GetTemplateElementChildren }
     * 
     */
    public GetTemplateElementChildren createGetTemplateElementChildren() {
        return new GetTemplateElementChildren();
    }

    /**
     * Create an instance of {@link ExecuteInventoryLevelReportResponse }
     * 
     */
    public ExecuteInventoryLevelReportResponse createExecuteInventoryLevelReportResponse() {
        return new ExecuteInventoryLevelReportResponse();
    }

    /**
     * Create an instance of {@link GetSubnetsInSubnetResponse }
     * 
     */
    public GetSubnetsInSubnetResponse createGetSubnetsInSubnetResponse() {
        return new GetSubnetsInSubnetResponse();
    }

    /**
     * Create an instance of {@link GetParentsResponse }
     * 
     */
    public GetParentsResponse createGetParentsResponse() {
        return new GetParentsResponse();
    }

    /**
     * Create an instance of {@link GetProjectResourcesResponse }
     * 
     */
    public GetProjectResourcesResponse createGetProjectResourcesResponse() {
        return new GetProjectResourcesResponse();
    }

    /**
     * Create an instance of {@link GetOSPViewsResponse }
     * 
     */
    public GetOSPViewsResponse createGetOSPViewsResponse() {
        return new GetOSPViewsResponse();
    }

    /**
     * Create an instance of {@link CreateClassLevelReport }
     * 
     */
    public CreateClassLevelReport createCreateClassLevelReport() {
        return new CreateClassLevelReport();
    }

    /**
     * Create an instance of {@link DeleteService }
     * 
     */
    public DeleteService createDeleteService() {
        return new DeleteService();
    }

    /**
     * Create an instance of {@link GetSubscribersForTask }
     * 
     */
    public GetSubscribersForTask createGetSubscribersForTask() {
        return new GetSubscribersForTask();
    }

    /**
     * Create an instance of {@link CommitActivity }
     * 
     */
    public CommitActivity createCommitActivity() {
        return new CommitActivity();
    }

    /**
     * Create an instance of {@link DeleteListTypeItemRelatedView }
     * 
     */
    public DeleteListTypeItemRelatedView createDeleteListTypeItemRelatedView() {
        return new DeleteListTypeItemRelatedView();
    }

    /**
     * Create an instance of {@link AddPossibleSpecialChildrenWithId }
     * 
     */
    public AddPossibleSpecialChildrenWithId createAddPossibleSpecialChildrenWithId() {
        return new AddPossibleSpecialChildrenWithId();
    }

    /**
     * Create an instance of {@link UpdateProjectResponse }
     * 
     */
    public UpdateProjectResponse createUpdateProjectResponse() {
        return new UpdateProjectResponse();
    }

    /**
     * Create an instance of {@link UpdateListTypeItemResponse }
     * 
     */
    public UpdateListTypeItemResponse createUpdateListTypeItemResponse() {
        return new UpdateListTypeItemResponse();
    }

    /**
     * Create an instance of {@link GetPhysicalPathResponse }
     * 
     */
    public GetPhysicalPathResponse createGetPhysicalPathResponse() {
        return new GetPhysicalPathResponse();
    }

    /**
     * Create an instance of {@link CreateServiceResponse }
     * 
     */
    public CreateServiceResponse createCreateServiceResponse() {
        return new CreateServiceResponse();
    }

    /**
     * Create an instance of {@link UpdateContact }
     * 
     */
    public UpdateContact createUpdateContact() {
        return new UpdateContact();
    }

    /**
     * Create an instance of {@link ConnectMirrorMultiplePort }
     * 
     */
    public ConnectMirrorMultiplePort createConnectMirrorMultiplePort() {
        return new ConnectMirrorMultiplePort();
    }

    /**
     * Create an instance of {@link ReconnectPhysicalConnectionResponse }
     * 
     */
    public ReconnectPhysicalConnectionResponse createReconnectPhysicalConnectionResponse() {
        return new ReconnectPhysicalConnectionResponse();
    }

    /**
     * Create an instance of {@link DeleteCostumerResponse }
     * 
     */
    public DeleteCostumerResponse createDeleteCostumerResponse() {
        return new DeleteCostumerResponse();
    }

    /**
     * Create an instance of {@link CreateSparePart }
     * 
     */
    public CreateSparePart createCreateSparePart() {
        return new CreateSparePart();
    }

    /**
     * Create an instance of {@link DeleteSDHTributaryLink }
     * 
     */
    public DeleteSDHTributaryLink createDeleteSDHTributaryLink() {
        return new DeleteSDHTributaryLink();
    }

    /**
     * Create an instance of {@link GetObjectsWithFilterLightResponse }
     * 
     */
    public GetObjectsWithFilterLightResponse createGetObjectsWithFilterLightResponse() {
        return new GetObjectsWithFilterLightResponse();
    }

    /**
     * Create an instance of {@link AssociatePhysicalNodeToWarehouseResponse }
     * 
     */
    public AssociatePhysicalNodeToWarehouseResponse createAssociatePhysicalNodeToWarehouseResponse() {
        return new AssociatePhysicalNodeToWarehouseResponse();
    }

    /**
     * Create an instance of {@link LaunchAdHocAutomatedSynchronizationTask }
     * 
     */
    public LaunchAdHocAutomatedSynchronizationTask createLaunchAdHocAutomatedSynchronizationTask() {
        return new LaunchAdHocAutomatedSynchronizationTask();
    }

    /**
     * Create an instance of {@link DeleteObjectsResponse }
     * 
     */
    public DeleteObjectsResponse createDeleteObjectsResponse() {
        return new DeleteObjectsResponse();
    }

    /**
     * Create an instance of {@link CreateSDHTributaryLink }
     * 
     */
    public CreateSDHTributaryLink createCreateSDHTributaryLink() {
        return new CreateSDHTributaryLink();
    }

    /**
     * Create an instance of {@link GetSynchronizationProvidersResponse }
     * 
     */
    public GetSynchronizationProvidersResponse createGetSynchronizationProvidersResponse() {
        return new GetSynchronizationProvidersResponse();
    }

    /**
     * Create an instance of {@link MoveObjectsToWarehouse }
     * 
     */
    public MoveObjectsToWarehouse createMoveObjectsToWarehouse() {
        return new MoveObjectsToWarehouse();
    }

    /**
     * Create an instance of {@link GetObjectsInFavoritesFolder }
     * 
     */
    public GetObjectsInFavoritesFolder createGetObjectsInFavoritesFolder() {
        return new GetObjectsInFavoritesFolder();
    }

    /**
     * Create an instance of {@link CreateInventoryLevelReport }
     * 
     */
    public CreateInventoryLevelReport createCreateInventoryLevelReport() {
        return new CreateInventoryLevelReport();
    }

    /**
     * Create an instance of {@link GetSpecialChildrenOfClassLightRecursiveResponse }
     * 
     */
    public GetSpecialChildrenOfClassLightRecursiveResponse createGetSpecialChildrenOfClassLightRecursiveResponse() {
        return new GetSpecialChildrenOfClassLightRecursiveResponse();
    }

    /**
     * Create an instance of {@link SetAttributePropertiesResponse }
     * 
     */
    public SetAttributePropertiesResponse createSetAttributePropertiesResponse() {
        return new SetAttributePropertiesResponse();
    }

    /**
     * Create an instance of {@link CopyTemplateElements }
     * 
     */
    public CopyTemplateElements createCopyTemplateElements() {
        return new CopyTemplateElements();
    }

    /**
     * Create an instance of {@link CreateOSPView }
     * 
     */
    public CreateOSPView createCreateOSPView() {
        return new CreateOSPView();
    }

    /**
     * Create an instance of {@link DeleteBusinessRule }
     * 
     */
    public DeleteBusinessRule createDeleteBusinessRule() {
        return new DeleteBusinessRule();
    }

    /**
     * Create an instance of {@link GetArtifactDefinitionForActivity }
     * 
     */
    public GetArtifactDefinitionForActivity createGetArtifactDefinitionForActivity() {
        return new GetArtifactDefinitionForActivity();
    }

    /**
     * Create an instance of {@link GetObjectChildrenForClassWithId }
     * 
     */
    public GetObjectChildrenForClassWithId createGetObjectChildrenForClassWithId() {
        return new GetObjectChildrenForClassWithId();
    }

    /**
     * Create an instance of {@link GetAllCustomersResponse }
     * 
     */
    public GetAllCustomersResponse createGetAllCustomersResponse() {
        return new GetAllCustomersResponse();
    }

    /**
     * Create an instance of {@link DeletePoolsResponse }
     * 
     */
    public DeletePoolsResponse createDeletePoolsResponse() {
        return new DeletePoolsResponse();
    }

    /**
     * Create an instance of {@link CreateUserResponse }
     * 
     */
    public CreateUserResponse createCreateUserResponse() {
        return new CreateUserResponse();
    }

    /**
     * Create an instance of {@link GetSpecialAttributeResponse }
     * 
     */
    public GetSpecialAttributeResponse createGetSpecialAttributeResponse() {
        return new GetSpecialAttributeResponse();
    }

    /**
     * Create an instance of {@link MoveObjects }
     * 
     */
    public MoveObjects createMoveObjects() {
        return new MoveObjects();
    }

    /**
     * Create an instance of {@link CreateListTypeItemRelatedView }
     * 
     */
    public CreateListTypeItemRelatedView createCreateListTypeItemRelatedView() {
        return new CreateListTypeItemRelatedView();
    }

    /**
     * Create an instance of {@link CreateSubnet }
     * 
     */
    public CreateSubnet createCreateSubnet() {
        return new CreateSubnet();
    }

    /**
     * Create an instance of {@link UpdateListTypeItemRelatedViewResponse }
     * 
     */
    public UpdateListTypeItemRelatedViewResponse createUpdateListTypeItemRelatedViewResponse() {
        return new UpdateListTypeItemRelatedViewResponse();
    }

    /**
     * Create an instance of {@link CreateObject }
     * 
     */
    public CreateObject createCreateObject() {
        return new CreateObject();
    }

    /**
     * Create an instance of {@link GetParentsUntilFirstOfClassResponse }
     * 
     */
    public GetParentsUntilFirstOfClassResponse createGetParentsUntilFirstOfClassResponse() {
        return new GetParentsUntilFirstOfClassResponse();
    }

    /**
     * Create an instance of {@link GetPoolResponse }
     * 
     */
    public GetPoolResponse createGetPoolResponse() {
        return new GetPoolResponse();
    }

    /**
     * Create an instance of {@link UpdateValidatorDefinition }
     * 
     */
    public UpdateValidatorDefinition createUpdateValidatorDefinition() {
        return new UpdateValidatorDefinition();
    }

    /**
     * Create an instance of {@link GetTasksResponse }
     * 
     */
    public GetTasksResponse createGetTasksResponse() {
        return new GetTasksResponse();
    }

    /**
     * Create an instance of {@link UpdateContractResponse }
     * 
     */
    public UpdateContractResponse createUpdateContractResponse() {
        return new UpdateContractResponse();
    }

    /**
     * Create an instance of {@link ReconnectPhysicalConnection }
     * 
     */
    public ReconnectPhysicalConnection createReconnectPhysicalConnection() {
        return new ReconnectPhysicalConnection();
    }

    /**
     * Create an instance of {@link ReleaseMirrorPortResponse }
     * 
     */
    public ReleaseMirrorPortResponse createReleaseMirrorPortResponse() {
        return new ReleaseMirrorPortResponse();
    }

    /**
     * Create an instance of {@link CreateProjectResponse }
     * 
     */
    public CreateProjectResponse createCreateProjectResponse() {
        return new CreateProjectResponse();
    }

    /**
     * Create an instance of {@link DeleteSubnets }
     * 
     */
    public DeleteSubnets createDeleteSubnets() {
        return new DeleteSubnets();
    }

    /**
     * Create an instance of {@link ReleaseMirrorPort }
     * 
     */
    public ReleaseMirrorPort createReleaseMirrorPort() {
        return new ReleaseMirrorPort();
    }

    /**
     * Create an instance of {@link DeleteTask }
     * 
     */
    public DeleteTask createDeleteTask() {
        return new DeleteTask();
    }

    /**
     * Create an instance of {@link FindSDHRoutesUsingTransportLinksResponse }
     * 
     */
    public FindSDHRoutesUsingTransportLinksResponse createFindSDHRoutesUsingTransportLinksResponse() {
        return new FindSDHRoutesUsingTransportLinksResponse();
    }

    /**
     * Create an instance of {@link GetGroupsResponse }
     * 
     */
    public GetGroupsResponse createGetGroupsResponse() {
        return new GetGroupsResponse();
    }

    /**
     * Create an instance of {@link SetAttributeProperties }
     * 
     */
    public SetAttributeProperties createSetAttributeProperties() {
        return new SetAttributeProperties();
    }

    /**
     * Create an instance of {@link ReleaseSubnetFromVlan }
     * 
     */
    public ReleaseSubnetFromVlan createReleaseSubnetFromVlan() {
        return new ReleaseSubnetFromVlan();
    }

    /**
     * Create an instance of {@link SetPoolPropertiesResponse }
     * 
     */
    public SetPoolPropertiesResponse createSetPoolPropertiesResponse() {
        return new SetPoolPropertiesResponse();
    }

    /**
     * Create an instance of {@link GetCustomerPool }
     * 
     */
    public GetCustomerPool createGetCustomerPool() {
        return new GetCustomerPool();
    }

    /**
     * Create an instance of {@link GetSubnetPoolResponse }
     * 
     */
    public GetSubnetPoolResponse createGetSubnetPoolResponse() {
        return new GetSubnetPoolResponse();
    }

    /**
     * Create an instance of {@link ConnectMplsLink }
     * 
     */
    public ConnectMplsLink createConnectMplsLink() {
        return new ConnectMplsLink();
    }

    /**
     * Create an instance of {@link ConnectPhysicalLinks }
     * 
     */
    public ConnectPhysicalLinks createConnectPhysicalLinks() {
        return new ConnectPhysicalLinks();
    }

    /**
     * Create an instance of {@link AddPossibleChildren }
     * 
     */
    public AddPossibleChildren createAddPossibleChildren() {
        return new AddPossibleChildren();
    }

    /**
     * Create an instance of {@link GetUsers }
     * 
     */
    public GetUsers createGetUsers() {
        return new GetUsers();
    }

    /**
     * Create an instance of {@link AddObjectsToFavoritesFolderResponse }
     * 
     */
    public AddObjectsToFavoritesFolderResponse createAddObjectsToFavoritesFolderResponse() {
        return new AddObjectsToFavoritesFolderResponse();
    }

    /**
     * Create an instance of {@link GetPhysicalPath }
     * 
     */
    public GetPhysicalPath createGetPhysicalPath() {
        return new GetPhysicalPath();
    }

    /**
     * Create an instance of {@link CreateContact }
     * 
     */
    public CreateContact createCreateContact() {
        return new CreateContact();
    }

    /**
     * Create an instance of {@link ReleaseSyncDataSourceConfigFromSyncGroupResponse }
     * 
     */
    public ReleaseSyncDataSourceConfigFromSyncGroupResponse createReleaseSyncDataSourceConfigFromSyncGroupResponse() {
        return new ReleaseSyncDataSourceConfigFromSyncGroupResponse();
    }

    /**
     * Create an instance of {@link RemovePrivilegeFromGroup }
     * 
     */
    public RemovePrivilegeFromGroup createRemovePrivilegeFromGroup() {
        return new RemovePrivilegeFromGroup();
    }

    /**
     * Create an instance of {@link GetContractsInPool }
     * 
     */
    public GetContractsInPool createGetContractsInPool() {
        return new GetContractsInPool();
    }

    /**
     * Create an instance of {@link DeleteTemplateElement }
     * 
     */
    public DeleteTemplateElement createDeleteTemplateElement() {
        return new DeleteTemplateElement();
    }

    /**
     * Create an instance of {@link CreateProjectPoolResponse }
     * 
     */
    public CreateProjectPoolResponse createCreateProjectPoolResponse() {
        return new CreateProjectPoolResponse();
    }

    /**
     * Create an instance of {@link GetPhysicalConnectionEndpointsResponse }
     * 
     */
    public GetPhysicalConnectionEndpointsResponse createGetPhysicalConnectionEndpointsResponse() {
        return new GetPhysicalConnectionEndpointsResponse();
    }

    /**
     * Create an instance of {@link CreateProjectPool }
     * 
     */
    public CreateProjectPool createCreateProjectPool() {
        return new CreateProjectPool();
    }

    /**
     * Create an instance of {@link GetChildrenOfClassLight }
     * 
     */
    public GetChildrenOfClassLight createGetChildrenOfClassLight() {
        return new GetChildrenOfClassLight();
    }

    /**
     * Create an instance of {@link CreateContractPoolResponse }
     * 
     */
    public CreateContractPoolResponse createCreateContractPoolResponse() {
        return new CreateContractPoolResponse();
    }

    /**
     * Create an instance of {@link CreateProcessDefinition }
     * 
     */
    public CreateProcessDefinition createCreateProcessDefinition() {
        return new CreateProcessDefinition();
    }

    /**
     * Create an instance of {@link RelateObjectToContractResponse }
     * 
     */
    public RelateObjectToContractResponse createRelateObjectToContractResponse() {
        return new RelateObjectToContractResponse();
    }

    /**
     * Create an instance of {@link ConnectMirrorPortResponse }
     * 
     */
    public ConnectMirrorPortResponse createConnectMirrorPortResponse() {
        return new ConnectMirrorPortResponse();
    }

    /**
     * Create an instance of {@link GetObjectResponse }
     * 
     */
    public GetObjectResponse createGetObjectResponse() {
        return new GetObjectResponse();
    }

    /**
     * Create an instance of {@link DisconnectPhysicalConnection }
     * 
     */
    public DisconnectPhysicalConnection createDisconnectPhysicalConnection() {
        return new DisconnectPhysicalConnection();
    }

    /**
     * Create an instance of {@link DeleteCostumer }
     * 
     */
    public DeleteCostumer createDeleteCostumer() {
        return new DeleteCostumer();
    }

    /**
     * Create an instance of {@link GetSubnetPools }
     * 
     */
    public GetSubnetPools createGetSubnetPools() {
        return new GetSubnetPools();
    }

    /**
     * Create an instance of {@link GetCustomerResponse }
     * 
     */
    public GetCustomerResponse createGetCustomerResponse() {
        return new GetCustomerResponse();
    }

    /**
     * Create an instance of {@link DeleteGroupsResponse }
     * 
     */
    public DeleteGroupsResponse createDeleteGroupsResponse() {
        return new DeleteGroupsResponse();
    }

    /**
     * Create an instance of {@link GetConfigurationVariableValueResponse }
     * 
     */
    public GetConfigurationVariableValueResponse createGetConfigurationVariableValueResponse() {
        return new GetConfigurationVariableValueResponse();
    }

    /**
     * Create an instance of {@link GetListTypeItemsResponse }
     * 
     */
    public GetListTypeItemsResponse createGetListTypeItemsResponse() {
        return new GetListTypeItemsResponse();
    }

    /**
     * Create an instance of {@link AssociateObjectsToContract }
     * 
     */
    public AssociateObjectsToContract createAssociateObjectsToContract() {
        return new AssociateObjectsToContract();
    }

    /**
     * Create an instance of {@link DeleteGroups }
     * 
     */
    public DeleteGroups createDeleteGroups() {
        return new DeleteGroups();
    }

    /**
     * Create an instance of {@link ExecuteSyncActionsResponse }
     * 
     */
    public ExecuteSyncActionsResponse createExecuteSyncActionsResponse() {
        return new ExecuteSyncActionsResponse();
    }

    /**
     * Create an instance of {@link RelateObjectsToContract }
     * 
     */
    public RelateObjectsToContract createRelateObjectsToContract() {
        return new RelateObjectsToContract();
    }

    /**
     * Create an instance of {@link UpdateFileProperties }
     * 
     */
    public UpdateFileProperties createUpdateFileProperties() {
        return new UpdateFileProperties();
    }

    /**
     * Create an instance of {@link GetQueryResponse }
     * 
     */
    public GetQueryResponse createGetQueryResponse() {
        return new GetQueryResponse();
    }

    /**
     * Create an instance of {@link GetContactsResponse }
     * 
     */
    public GetContactsResponse createGetContactsResponse() {
        return new GetContactsResponse();
    }

    /**
     * Create an instance of {@link GetSpecialChildrenOfClassLight }
     * 
     */
    public GetSpecialChildrenOfClassLight createGetSpecialChildrenOfClassLight() {
        return new GetSpecialChildrenOfClassLight();
    }

    /**
     * Create an instance of {@link GetSDHContainerLinkStructureResponse }
     * 
     */
    public GetSDHContainerLinkStructureResponse createGetSDHContainerLinkStructureResponse() {
        return new GetSDHContainerLinkStructureResponse();
    }

    /**
     * Create an instance of {@link SetPrivilegeToUser }
     * 
     */
    public SetPrivilegeToUser createSetPrivilegeToUser() {
        return new SetPrivilegeToUser();
    }

    /**
     * Create an instance of {@link GetSubnetResponse }
     * 
     */
    public GetSubnetResponse createGetSubnetResponse() {
        return new GetSubnetResponse();
    }

    /**
     * Create an instance of {@link CreateProxyPoolResponse }
     * 
     */
    public CreateProxyPoolResponse createCreateProxyPoolResponse() {
        return new CreateProxyPoolResponse();
    }

    /**
     * Create an instance of {@link GetChildrenOfClass }
     * 
     */
    public GetChildrenOfClass createGetChildrenOfClass() {
        return new GetChildrenOfClass();
    }

    /**
     * Create an instance of {@link ReleasePortFromIPResponse }
     * 
     */
    public ReleasePortFromIPResponse createReleasePortFromIPResponse() {
        return new ReleasePortFromIPResponse();
    }

    /**
     * Create an instance of {@link ExecuteClassLevelReport }
     * 
     */
    public ExecuteClassLevelReport createExecuteClassLevelReport() {
        return new ExecuteClassLevelReport();
    }

    /**
     * Create an instance of {@link CreateCustomerResponse }
     * 
     */
    public CreateCustomerResponse createCreateCustomerResponse() {
        return new CreateCustomerResponse();
    }

    /**
     * Create an instance of {@link GetProjectPoolResponse }
     * 
     */
    public GetProjectPoolResponse createGetProjectPoolResponse() {
        return new GetProjectPoolResponse();
    }

    /**
     * Create an instance of {@link GetContact }
     * 
     */
    public GetContact createGetContact() {
        return new GetContact();
    }

    /**
     * Create an instance of {@link CreateAttributeForClassWithId }
     * 
     */
    public CreateAttributeForClassWithId createCreateAttributeForClassWithId() {
        return new CreateAttributeForClassWithId();
    }

    /**
     * Create an instance of {@link DeleteSparePool }
     * 
     */
    public DeleteSparePool createDeleteSparePool() {
        return new DeleteSparePool();
    }

    /**
     * Create an instance of {@link UpdateFilePropertiesResponse }
     * 
     */
    public UpdateFilePropertiesResponse createUpdateFilePropertiesResponse() {
        return new UpdateFilePropertiesResponse();
    }

    /**
     * Create an instance of {@link DeleteSynchronizationGroupResponse }
     * 
     */
    public DeleteSynchronizationGroupResponse createDeleteSynchronizationGroupResponse() {
        return new DeleteSynchronizationGroupResponse();
    }

    /**
     * Create an instance of {@link GetBusinessObjectAuditTrailResponse }
     * 
     */
    public GetBusinessObjectAuditTrailResponse createGetBusinessObjectAuditTrailResponse() {
        return new GetBusinessObjectAuditTrailResponse();
    }

    /**
     * Create an instance of {@link GetFavoritesFolderResponse }
     * 
     */
    public GetFavoritesFolderResponse createGetFavoritesFolderResponse() {
        return new GetFavoritesFolderResponse();
    }

    /**
     * Create an instance of {@link MoveObjectsToPoolResponse }
     * 
     */
    public MoveObjectsToPoolResponse createMoveObjectsToPoolResponse() {
        return new MoveObjectsToPoolResponse();
    }

    /**
     * Create an instance of {@link DeleteQuery }
     * 
     */
    public DeleteQuery createDeleteQuery() {
        return new DeleteQuery();
    }

    /**
     * Create an instance of {@link GetSynchronizationGroupResponse }
     * 
     */
    public GetSynchronizationGroupResponse createGetSynchronizationGroupResponse() {
        return new GetSynchronizationGroupResponse();
    }

    /**
     * Create an instance of {@link GetSubnetPool }
     * 
     */
    public GetSubnetPool createGetSubnetPool() {
        return new GetSubnetPool();
    }

    /**
     * Create an instance of {@link CopySpecialObjectsResponse }
     * 
     */
    public CopySpecialObjectsResponse createCopySpecialObjectsResponse() {
        return new CopySpecialObjectsResponse();
    }

    /**
     * Create an instance of {@link GetSyncDataSourceConfiguration }
     * 
     */
    public GetSyncDataSourceConfiguration createGetSyncDataSourceConfiguration() {
        return new GetSyncDataSourceConfiguration();
    }

    /**
     * Create an instance of {@link GetBGPMapResponse }
     * 
     */
    public GetBGPMapResponse createGetBGPMapResponse() {
        return new GetBGPMapResponse();
    }

    /**
     * Create an instance of {@link ExecuteSyncActions }
     * 
     */
    public ExecuteSyncActions createExecuteSyncActions() {
        return new ExecuteSyncActions();
    }

    /**
     * Create an instance of {@link GetTasksForUser }
     * 
     */
    public GetTasksForUser createGetTasksForUser() {
        return new GetTasksForUser();
    }

    /**
     * Create an instance of {@link CreateBulkObjectsResponse }
     * 
     */
    public CreateBulkObjectsResponse createCreateBulkObjectsResponse() {
        return new CreateBulkObjectsResponse();
    }

    /**
     * Create an instance of {@link GetPossibleChildrenNoRecursiveResponse }
     * 
     */
    public GetPossibleChildrenNoRecursiveResponse createGetPossibleChildrenNoRecursiveResponse() {
        return new GetPossibleChildrenNoRecursiveResponse();
    }

    /**
     * Create an instance of {@link CopySyncDataSourceConfigurationResponse }
     * 
     */
    public CopySyncDataSourceConfigurationResponse createCopySyncDataSourceConfigurationResponse() {
        return new CopySyncDataSourceConfigurationResponse();
    }

    /**
     * Create an instance of {@link GetSubnet }
     * 
     */
    public GetSubnet createGetSubnet() {
        return new GetSubnet();
    }

    /**
     * Create an instance of {@link GetAllProjectsResponse }
     * 
     */
    public GetAllProjectsResponse createGetAllProjectsResponse() {
        return new GetAllProjectsResponse();
    }

    /**
     * Create an instance of {@link SetGroupProperties }
     * 
     */
    public SetGroupProperties createSetGroupProperties() {
        return new SetGroupProperties();
    }

    /**
     * Create an instance of {@link GetActivity }
     * 
     */
    public GetActivity createGetActivity() {
        return new GetActivity();
    }

    /**
     * Create an instance of {@link CreateObjectRelatedView }
     * 
     */
    public CreateObjectRelatedView createCreateObjectRelatedView() {
        return new CreateObjectRelatedView();
    }

    /**
     * Create an instance of {@link GetUpstreamSpecialContainmentHierarchy }
     * 
     */
    public GetUpstreamSpecialContainmentHierarchy createGetUpstreamSpecialContainmentHierarchy() {
        return new GetUpstreamSpecialContainmentHierarchy();
    }

    /**
     * Create an instance of {@link DeleteMPLSLinkResponse }
     * 
     */
    public DeleteMPLSLinkResponse createDeleteMPLSLinkResponse() {
        return new DeleteMPLSLinkResponse();
    }

    /**
     * Create an instance of {@link CreateProxyResponse }
     * 
     */
    public CreateProxyResponse createCreateProxyResponse() {
        return new CreateProxyResponse();
    }

    /**
     * Create an instance of {@link GetAttributeResponse }
     * 
     */
    public GetAttributeResponse createGetAttributeResponse() {
        return new GetAttributeResponse();
    }

    /**
     * Create an instance of {@link GetSubClassesLightResponse }
     * 
     */
    public GetSubClassesLightResponse createGetSubClassesLightResponse() {
        return new GetSubClassesLightResponse();
    }

    /**
     * Create an instance of {@link GetCustomerPoolsResponse }
     * 
     */
    public GetCustomerPoolsResponse createGetCustomerPoolsResponse() {
        return new GetCustomerPoolsResponse();
    }

    /**
     * Create an instance of {@link UpdateServiceResponse }
     * 
     */
    public UpdateServiceResponse createUpdateServiceResponse() {
        return new UpdateServiceResponse();
    }

    /**
     * Create an instance of {@link CreateFavoritesFolderForUserResponse }
     * 
     */
    public CreateFavoritesFolderForUserResponse createCreateFavoritesFolderForUserResponse() {
        return new CreateFavoritesFolderForUserResponse();
    }

    /**
     * Create an instance of {@link GetServicesInPool }
     * 
     */
    public GetServicesInPool createGetServicesInPool() {
        return new GetServicesInPool();
    }

    /**
     * Create an instance of {@link RemoveIP }
     * 
     */
    public RemoveIP createRemoveIP() {
        return new RemoveIP();
    }

    /**
     * Create an instance of {@link DeleteSDHTributaryLinkResponse }
     * 
     */
    public DeleteSDHTributaryLinkResponse createDeleteSDHTributaryLinkResponse() {
        return new DeleteSDHTributaryLinkResponse();
    }

    /**
     * Create an instance of {@link DeleteConfigurationVariablesPool }
     * 
     */
    public DeleteConfigurationVariablesPool createDeleteConfigurationVariablesPool() {
        return new DeleteConfigurationVariablesPool();
    }

    /**
     * Create an instance of {@link DeleteContractPoolResponse }
     * 
     */
    public DeleteContractPoolResponse createDeleteContractPoolResponse() {
        return new DeleteContractPoolResponse();
    }

    /**
     * Create an instance of {@link UpdateContractPool }
     * 
     */
    public UpdateContractPool createUpdateContractPool() {
        return new UpdateContractPool();
    }

    /**
     * Create an instance of {@link UpdateListTypeItem }
     * 
     */
    public UpdateListTypeItem createUpdateListTypeItem() {
        return new UpdateListTypeItem();
    }

    /**
     * Create an instance of {@link CreateSession }
     * 
     */
    public CreateSession createCreateSession() {
        return new CreateSession();
    }

    /**
     * Create an instance of {@link RelateObjectToProjectResponse }
     * 
     */
    public RelateObjectToProjectResponse createRelateObjectToProjectResponse() {
        return new RelateObjectToProjectResponse();
    }

    /**
     * Create an instance of {@link GetObjectsOfClassLight }
     * 
     */
    public GetObjectsOfClassLight createGetObjectsOfClassLight() {
        return new GetObjectsOfClassLight();
    }

    /**
     * Create an instance of {@link GetServicePool }
     * 
     */
    public GetServicePool createGetServicePool() {
        return new GetServicePool();
    }

    /**
     * Create an instance of {@link GetClassWithId }
     * 
     */
    public GetClassWithId createGetClassWithId() {
        return new GetClassWithId();
    }

    /**
     * Create an instance of {@link GetPoolsInWarehouseResponse }
     * 
     */
    public GetPoolsInWarehouseResponse createGetPoolsInWarehouseResponse() {
        return new GetPoolsInWarehouseResponse();
    }

    /**
     * Create an instance of {@link SetGroupPropertiesResponse }
     * 
     */
    public SetGroupPropertiesResponse createSetGroupPropertiesResponse() {
        return new SetGroupPropertiesResponse();
    }

    /**
     * Create an instance of {@link RemovePrivilegeFromUser }
     * 
     */
    public RemovePrivilegeFromUser createRemovePrivilegeFromUser() {
        return new RemovePrivilegeFromUser();
    }

    /**
     * Create an instance of {@link DeleteGeneralViewResponse }
     * 
     */
    public DeleteGeneralViewResponse createDeleteGeneralViewResponse() {
        return new DeleteGeneralViewResponse();
    }

    /**
     * Create an instance of {@link GetRootPoolsResponse }
     * 
     */
    public GetRootPoolsResponse createGetRootPoolsResponse() {
        return new GetRootPoolsResponse();
    }

    /**
     * Create an instance of {@link RemovePrivilegeFromGroupResponse }
     * 
     */
    public RemovePrivilegeFromGroupResponse createRemovePrivilegeFromGroupResponse() {
        return new RemovePrivilegeFromGroupResponse();
    }

    /**
     * Create an instance of {@link CreateBulkSpecialTemplateElementResponse }
     * 
     */
    public CreateBulkSpecialTemplateElementResponse createCreateBulkSpecialTemplateElementResponse() {
        return new CreateBulkSpecialTemplateElementResponse();
    }

    /**
     * Create an instance of {@link ReleaseSubnetFromVlanResponse }
     * 
     */
    public ReleaseSubnetFromVlanResponse createReleaseSubnetFromVlanResponse() {
        return new ReleaseSubnetFromVlanResponse();
    }

    /**
     * Create an instance of {@link DisconnectMPLSLinkResponse }
     * 
     */
    public DisconnectMPLSLinkResponse createDisconnectMPLSLinkResponse() {
        return new DisconnectMPLSLinkResponse();
    }

    /**
     * Create an instance of {@link GetTemplateElement }
     * 
     */
    public GetTemplateElement createGetTemplateElement() {
        return new GetTemplateElement();
    }

    /**
     * Create an instance of {@link GetGeneralActivityAuditTrailResponse }
     * 
     */
    public GetGeneralActivityAuditTrailResponse createGetGeneralActivityAuditTrailResponse() {
        return new GetGeneralActivityAuditTrailResponse();
    }

    /**
     * Create an instance of {@link DeleteConfigurationVariableResponse }
     * 
     */
    public DeleteConfigurationVariableResponse createDeleteConfigurationVariableResponse() {
        return new DeleteConfigurationVariableResponse();
    }

    /**
     * Create an instance of {@link DeleteProjectPool }
     * 
     */
    public DeleteProjectPool createDeleteProjectPool() {
        return new DeleteProjectPool();
    }

    /**
     * Create an instance of {@link ReleaseObjectFromProxy }
     * 
     */
    public ReleaseObjectFromProxy createReleaseObjectFromProxy() {
        return new ReleaseObjectFromProxy();
    }

    /**
     * Create an instance of {@link ValidateSavedE2EView }
     * 
     */
    public ValidateSavedE2EView createValidateSavedE2EView() {
        return new ValidateSavedE2EView();
    }

    /**
     * Create an instance of {@link ExecuteTask }
     * 
     */
    public ExecuteTask createExecuteTask() {
        return new ExecuteTask();
    }

    /**
     * Create an instance of {@link SearchForContacts }
     * 
     */
    public SearchForContacts createSearchForContacts() {
        return new SearchForContacts();
    }

    /**
     * Create an instance of {@link DeleteObject }
     * 
     */
    public DeleteObject createDeleteObject() {
        return new DeleteObject();
    }

    /**
     * Create an instance of {@link GetObjectLightResponse }
     * 
     */
    public GetObjectLightResponse createGetObjectLightResponse() {
        return new GetObjectLightResponse();
    }

    /**
     * Create an instance of {@link GetSubClassesLightNoRecursiveResponse }
     * 
     */
    public GetSubClassesLightNoRecursiveResponse createGetSubClassesLightNoRecursiveResponse() {
        return new GetSubClassesLightNoRecursiveResponse();
    }

    /**
     * Create an instance of {@link GetQuery }
     * 
     */
    public GetQuery createGetQuery() {
        return new GetQuery();
    }

    /**
     * Create an instance of {@link GetObjectsInSparePoolResponse }
     * 
     */
    public GetObjectsInSparePoolResponse createGetObjectsInSparePoolResponse() {
        return new GetObjectsInSparePoolResponse();
    }

    /**
     * Create an instance of {@link GetUpstreamContainmentHierarchyResponse }
     * 
     */
    public GetUpstreamContainmentHierarchyResponse createGetUpstreamContainmentHierarchyResponse() {
        return new GetUpstreamContainmentHierarchyResponse();
    }

    /**
     * Create an instance of {@link CreatePhysicalConnection }
     * 
     */
    public CreatePhysicalConnection createCreatePhysicalConnection() {
        return new CreatePhysicalConnection();
    }

    /**
     * Create an instance of {@link GetSubnetUsedIpsResponse }
     * 
     */
    public GetSubnetUsedIpsResponse createGetSubnetUsedIpsResponse() {
        return new GetSubnetUsedIpsResponse();
    }

    /**
     * Create an instance of {@link GetSubClassesLight }
     * 
     */
    public GetSubClassesLight createGetSubClassesLight() {
        return new GetSubClassesLight();
    }

    /**
     * Create an instance of {@link CreateSDHTransportLink }
     * 
     */
    public CreateSDHTransportLink createCreateSDHTransportLink() {
        return new CreateSDHTransportLink();
    }

    /**
     * Create an instance of {@link CreateConfigurationVariablesPool }
     * 
     */
    public CreateConfigurationVariablesPool createCreateConfigurationVariablesPool() {
        return new CreateConfigurationVariablesPool();
    }

    /**
     * Create an instance of {@link GetParentOfClassResponse }
     * 
     */
    public GetParentOfClassResponse createGetParentOfClassResponse() {
        return new GetParentOfClassResponse();
    }

    /**
     * Create an instance of {@link CreateListTypeItemResponse }
     * 
     */
    public CreateListTypeItemResponse createCreateListTypeItemResponse() {
        return new CreateListTypeItemResponse();
    }

    /**
     * Create an instance of {@link UpdateReport }
     * 
     */
    public UpdateReport createUpdateReport() {
        return new UpdateReport();
    }

    /**
     * Create an instance of {@link UpdateSyncDataSourceConfiguration }
     * 
     */
    public UpdateSyncDataSourceConfiguration createUpdateSyncDataSourceConfiguration() {
        return new UpdateSyncDataSourceConfiguration();
    }

    /**
     * Create an instance of {@link RunValidationsForObjectResponse }
     * 
     */
    public RunValidationsForObjectResponse createRunValidationsForObjectResponse() {
        return new RunValidationsForObjectResponse();
    }

    /**
     * Create an instance of {@link CreateSynchronizationDataSourceConfig }
     * 
     */
    public CreateSynchronizationDataSourceConfig createCreateSynchronizationDataSourceConfig() {
        return new CreateSynchronizationDataSourceConfig();
    }

    /**
     * Create an instance of {@link ConnectMirrorPort }
     * 
     */
    public ConnectMirrorPort createConnectMirrorPort() {
        return new ConnectMirrorPort();
    }

    /**
     * Create an instance of {@link CopyTemplateSpecialElements }
     * 
     */
    public CopyTemplateSpecialElements createCopyTemplateSpecialElements() {
        return new CopyTemplateSpecialElements();
    }

    /**
     * Create an instance of {@link UpdateProxyResponse }
     * 
     */
    public UpdateProxyResponse createUpdateProxyResponse() {
        return new UpdateProxyResponse();
    }

    /**
     * Create an instance of {@link DeleteProcessDefinitionResponse }
     * 
     */
    public DeleteProcessDefinitionResponse createDeleteProcessDefinitionResponse() {
        return new DeleteProcessDefinitionResponse();
    }

    /**
     * Create an instance of {@link GetProxyPoolsResponse }
     * 
     */
    public GetProxyPoolsResponse createGetProxyPoolsResponse() {
        return new GetProxyPoolsResponse();
    }

    /**
     * Create an instance of {@link CreateBulkTemplateElement }
     * 
     */
    public CreateBulkTemplateElement createCreateBulkTemplateElement() {
        return new CreateBulkTemplateElement();
    }

    /**
     * Create an instance of {@link GetAllProxiesResponse }
     * 
     */
    public GetAllProxiesResponse createGetAllProxiesResponse() {
        return new GetAllProxiesResponse();
    }

    /**
     * Create an instance of {@link GetAllConfigurationVariables }
     * 
     */
    public GetAllConfigurationVariables createGetAllConfigurationVariables() {
        return new GetAllConfigurationVariables();
    }

    /**
     * Create an instance of {@link GetProjectResources }
     * 
     */
    public GetProjectResources createGetProjectResources() {
        return new GetProjectResources();
    }

    /**
     * Create an instance of {@link ReleaseSyncDataSourceConfigFromSyncGroup }
     * 
     */
    public ReleaseSyncDataSourceConfigFromSyncGroup createReleaseSyncDataSourceConfigFromSyncGroup() {
        return new ReleaseSyncDataSourceConfigFromSyncGroup();
    }

    /**
     * Create an instance of {@link DeleteAttribute }
     * 
     */
    public DeleteAttribute createDeleteAttribute() {
        return new DeleteAttribute();
    }

    /**
     * Create an instance of {@link DeleteClassWithId }
     * 
     */
    public DeleteClassWithId createDeleteClassWithId() {
        return new DeleteClassWithId();
    }

    /**
     * Create an instance of {@link CreateConfigurationVariableResponse }
     * 
     */
    public CreateConfigurationVariableResponse createCreateConfigurationVariableResponse() {
        return new CreateConfigurationVariableResponse();
    }

    /**
     * Create an instance of {@link GetInventoryLevelReports }
     * 
     */
    public GetInventoryLevelReports createGetInventoryLevelReports() {
        return new GetInventoryLevelReports();
    }

    /**
     * Create an instance of {@link CopyTemplateSpecialElementsResponse }
     * 
     */
    public CopyTemplateSpecialElementsResponse createCopyTemplateSpecialElementsResponse() {
        return new CopyTemplateSpecialElementsResponse();
    }

    /**
     * Create an instance of {@link GetAllContractsResponse }
     * 
     */
    public GetAllContractsResponse createGetAllContractsResponse() {
        return new GetAllContractsResponse();
    }

    /**
     * Create an instance of {@link DeleteClassWithIdResponse }
     * 
     */
    public DeleteClassWithIdResponse createDeleteClassWithIdResponse() {
        return new DeleteClassWithIdResponse();
    }

    /**
     * Create an instance of {@link GetParent }
     * 
     */
    public GetParent createGetParent() {
        return new GetParent();
    }

    /**
     * Create an instance of {@link DisconnectPhysicalConnectionResponse }
     * 
     */
    public DisconnectPhysicalConnectionResponse createDisconnectPhysicalConnectionResponse() {
        return new DisconnectPhysicalConnectionResponse();
    }

    /**
     * Create an instance of {@link GetWarehousesInPool }
     * 
     */
    public GetWarehousesInPool createGetWarehousesInPool() {
        return new GetWarehousesInPool();
    }

    /**
     * Create an instance of {@link GetValidatorDefinitionsForClass }
     * 
     */
    public GetValidatorDefinitionsForClass createGetValidatorDefinitionsForClass() {
        return new GetValidatorDefinitionsForClass();
    }

    /**
     * Create an instance of {@link CreateSpecialObject }
     * 
     */
    public CreateSpecialObject createCreateSpecialObject() {
        return new CreateSpecialObject();
    }

    /**
     * Create an instance of {@link UpdateProject }
     * 
     */
    public UpdateProject createUpdateProject() {
        return new UpdateProject();
    }

    /**
     * Create an instance of {@link RelateObjectsToProject }
     * 
     */
    public RelateObjectsToProject createRelateObjectsToProject() {
        return new RelateObjectsToProject();
    }

    /**
     * Create an instance of {@link DeleteOSPView }
     * 
     */
    public DeleteOSPView createDeleteOSPView() {
        return new DeleteOSPView();
    }

    /**
     * Create an instance of {@link GetObjectsInFavoritesFolderResponse }
     * 
     */
    public GetObjectsInFavoritesFolderResponse createGetObjectsInFavoritesFolderResponse() {
        return new GetObjectsInFavoritesFolderResponse();
    }

    /**
     * Create an instance of {@link DetachFileFromObjectResponse }
     * 
     */
    public DetachFileFromObjectResponse createDetachFileFromObjectResponse() {
        return new DetachFileFromObjectResponse();
    }

    /**
     * Create an instance of {@link UpdateConfigurationVariablesPoolResponse }
     * 
     */
    public UpdateConfigurationVariablesPoolResponse createUpdateConfigurationVariablesPoolResponse() {
        return new UpdateConfigurationVariablesPoolResponse();
    }

    /**
     * Create an instance of {@link GetMandatoryAttributesInClassResponse }
     * 
     */
    public GetMandatoryAttributesInClassResponse createGetMandatoryAttributesInClassResponse() {
        return new GetMandatoryAttributesInClassResponse();
    }

    /**
     * Create an instance of {@link GetObjectRelatedViewResponse }
     * 
     */
    public GetObjectRelatedViewResponse createGetObjectRelatedViewResponse() {
        return new GetObjectRelatedViewResponse();
    }

    /**
     * Create an instance of {@link GetFilesForObject }
     * 
     */
    public GetFilesForObject createGetFilesForObject() {
        return new GetFilesForObject();
    }

    /**
     * Create an instance of {@link MoveSyncDataSourceConfiguration }
     * 
     */
    public MoveSyncDataSourceConfiguration createMoveSyncDataSourceConfiguration() {
        return new MoveSyncDataSourceConfiguration();
    }

    /**
     * Create an instance of {@link AddPossibleChildrenForClassWithIdResponse }
     * 
     */
    public AddPossibleChildrenForClassWithIdResponse createAddPossibleChildrenForClassWithIdResponse() {
        return new AddPossibleChildrenForClassWithIdResponse();
    }

    /**
     * Create an instance of {@link CreatePoolInWarehouseResponse }
     * 
     */
    public CreatePoolInWarehouseResponse createCreatePoolInWarehouseResponse() {
        return new CreatePoolInWarehouseResponse();
    }

    /**
     * Create an instance of {@link GetServiceResponse }
     * 
     */
    public GetServiceResponse createGetServiceResponse() {
        return new GetServiceResponse();
    }

    /**
     * Create an instance of {@link GetProcessDefinition }
     * 
     */
    public GetProcessDefinition createGetProcessDefinition() {
        return new GetProcessDefinition();
    }

    /**
     * Create an instance of {@link DeleteReport }
     * 
     */
    public DeleteReport createDeleteReport() {
        return new DeleteReport();
    }

    /**
     * Create an instance of {@link CopyPoolItemToPool }
     * 
     */
    public CopyPoolItemToPool createCopyPoolItemToPool() {
        return new CopyPoolItemToPool();
    }

    /**
     * Create an instance of {@link LaunchAutomatedSynchronizationTask }
     * 
     */
    public LaunchAutomatedSynchronizationTask createLaunchAutomatedSynchronizationTask() {
        return new LaunchAutomatedSynchronizationTask();
    }

    /**
     * Create an instance of {@link CreateContractResponse }
     * 
     */
    public CreateContractResponse createCreateContractResponse() {
        return new CreateContractResponse();
    }

    /**
     * Create an instance of {@link AddPossibleChildrenResponse }
     * 
     */
    public AddPossibleChildrenResponse createAddPossibleChildrenResponse() {
        return new AddPossibleChildrenResponse();
    }

    /**
     * Create an instance of {@link GetUsersInGroupResponse }
     * 
     */
    public GetUsersInGroupResponse createGetUsersInGroupResponse() {
        return new GetUsersInGroupResponse();
    }

    /**
     * Create an instance of {@link UpdateConfigurationVariableResponse }
     * 
     */
    public UpdateConfigurationVariableResponse createUpdateConfigurationVariableResponse() {
        return new UpdateConfigurationVariableResponse();
    }

    /**
     * Create an instance of {@link DeleteClassResponse }
     * 
     */
    public DeleteClassResponse createDeleteClassResponse() {
        return new DeleteClassResponse();
    }

    /**
     * Create an instance of {@link GetTemplateSpecialElementChildren }
     * 
     */
    public GetTemplateSpecialElementChildren createGetTemplateSpecialElementChildren() {
        return new GetTemplateSpecialElementChildren();
    }

    /**
     * Create an instance of {@link AddIPAddressResponse }
     * 
     */
    public AddIPAddressResponse createAddIPAddressResponse() {
        return new AddIPAddressResponse();
    }

    /**
     * Create an instance of {@link GetClassHierarchy }
     * 
     */
    public GetClassHierarchy createGetClassHierarchy() {
        return new GetClassHierarchy();
    }

    /**
     * Create an instance of {@link CreateProcessInstanceResponse }
     * 
     */
    public CreateProcessInstanceResponse createCreateProcessInstanceResponse() {
        return new CreateProcessInstanceResponse();
    }

    /**
     * Create an instance of {@link GetProxiesInPool }
     * 
     */
    public GetProxiesInPool createGetProxiesInPool() {
        return new GetProxiesInPool();
    }

    /**
     * Create an instance of {@link GetProjectPools }
     * 
     */
    public GetProjectPools createGetProjectPools() {
        return new GetProjectPools();
    }

    /**
     * Create an instance of {@link GetSiblings }
     * 
     */
    public GetSiblings createGetSiblings() {
        return new GetSiblings();
    }

    /**
     * Create an instance of {@link CreateFavoritesFolderForUser }
     * 
     */
    public CreateFavoritesFolderForUser createCreateFavoritesFolderForUser() {
        return new CreateFavoritesFolderForUser();
    }

    /**
     * Create an instance of {@link GetCustomer }
     * 
     */
    public GetCustomer createGetCustomer() {
        return new GetCustomer();
    }

    /**
     * Create an instance of {@link CreateMPLSLink }
     * 
     */
    public CreateMPLSLink createCreateMPLSLink() {
        return new CreateMPLSLink();
    }

    /**
     * Create an instance of {@link DeletePhysicalConnection }
     * 
     */
    public DeletePhysicalConnection createDeletePhysicalConnection() {
        return new DeletePhysicalConnection();
    }

    /**
     * Create an instance of {@link CreatePoolInObject }
     * 
     */
    public CreatePoolInObject createCreatePoolInObject() {
        return new CreatePoolInObject();
    }

    /**
     * Create an instance of {@link GetChildrenOfClassLightRecursive }
     * 
     */
    public GetChildrenOfClassLightRecursive createGetChildrenOfClassLightRecursive() {
        return new GetChildrenOfClassLightRecursive();
    }

    /**
     * Create an instance of {@link DeleteContactResponse }
     * 
     */
    public DeleteContactResponse createDeleteContactResponse() {
        return new DeleteContactResponse();
    }

    /**
     * Create an instance of {@link GetUpstreamContainmentHierarchy }
     * 
     */
    public GetUpstreamContainmentHierarchy createGetUpstreamContainmentHierarchy() {
        return new GetUpstreamContainmentHierarchy();
    }

    /**
     * Create an instance of {@link UpdateListTypeItemRelatedView }
     * 
     */
    public UpdateListTypeItemRelatedView createUpdateListTypeItemRelatedView() {
        return new UpdateListTypeItemRelatedView();
    }

    /**
     * Create an instance of {@link GetObjectsWithFilterLight }
     * 
     */
    public GetObjectsWithFilterLight createGetObjectsWithFilterLight() {
        return new GetObjectsWithFilterLight();
    }

    /**
     * Create an instance of {@link CreatePhysicalConnections }
     * 
     */
    public CreatePhysicalConnections createCreatePhysicalConnections() {
        return new CreatePhysicalConnections();
    }

    /**
     * Create an instance of {@link GetCustomerPoolResponse }
     * 
     */
    public GetCustomerPoolResponse createGetCustomerPoolResponse() {
        return new GetCustomerPoolResponse();
    }

    /**
     * Create an instance of {@link RemoveIPResponse }
     * 
     */
    public RemoveIPResponse createRemoveIPResponse() {
        return new RemoveIPResponse();
    }

    /**
     * Create an instance of {@link GetObjectRelatedViewsResponse }
     * 
     */
    public GetObjectRelatedViewsResponse createGetObjectRelatedViewsResponse() {
        return new GetObjectRelatedViewsResponse();
    }

    /**
     * Create an instance of {@link GetConfigurationVariableValue }
     * 
     */
    public GetConfigurationVariableValue createGetConfigurationVariableValue() {
        return new GetConfigurationVariableValue();
    }

    /**
     * Create an instance of {@link GetValidatorDefinitionsForClassResponse }
     * 
     */
    public GetValidatorDefinitionsForClassResponse createGetValidatorDefinitionsForClassResponse() {
        return new GetValidatorDefinitionsForClassResponse();
    }

    /**
     * Create an instance of {@link DeleteWarehouse }
     * 
     */
    public DeleteWarehouse createDeleteWarehouse() {
        return new DeleteWarehouse();
    }

    /**
     * Create an instance of {@link GetAffectedServices }
     * 
     */
    public GetAffectedServices createGetAffectedServices() {
        return new GetAffectedServices();
    }

    /**
     * Create an instance of {@link DeleteConfigurationVariablesPoolResponse }
     * 
     */
    public DeleteConfigurationVariablesPoolResponse createDeleteConfigurationVariablesPoolResponse() {
        return new DeleteConfigurationVariablesPoolResponse();
    }

    /**
     * Create an instance of {@link GetProjectsInPoolResponse }
     * 
     */
    public GetProjectsInPoolResponse createGetProjectsInPoolResponse() {
        return new GetProjectsInPoolResponse();
    }

    /**
     * Create an instance of {@link ReleasePortFromInterfaceResponse }
     * 
     */
    public ReleasePortFromInterfaceResponse createReleasePortFromInterfaceResponse() {
        return new ReleasePortFromInterfaceResponse();
    }

    /**
     * Create an instance of {@link UpdateCustomerPoolResponse }
     * 
     */
    public UpdateCustomerPoolResponse createUpdateCustomerPoolResponse() {
        return new UpdateCustomerPoolResponse();
    }

    /**
     * Create an instance of {@link AttachFileToObject }
     * 
     */
    public AttachFileToObject createAttachFileToObject() {
        return new AttachFileToObject();
    }

    /**
     * Create an instance of {@link DeleteProxyPoolResponse }
     * 
     */
    public DeleteProxyPoolResponse createDeleteProxyPoolResponse() {
        return new DeleteProxyPoolResponse();
    }

    /**
     * Create an instance of {@link DeleteSynchronizationDataSourceConfigResponse }
     * 
     */
    public DeleteSynchronizationDataSourceConfigResponse createDeleteSynchronizationDataSourceConfigResponse() {
        return new DeleteSynchronizationDataSourceConfigResponse();
    }

    /**
     * Create an instance of {@link GetChildrenOfClassLightRecursiveResponse }
     * 
     */
    public GetChildrenOfClassLightRecursiveResponse createGetChildrenOfClassLightRecursiveResponse() {
        return new GetChildrenOfClassLightRecursiveResponse();
    }

    /**
     * Create an instance of {@link AddUserToGroupResponse }
     * 
     */
    public AddUserToGroupResponse createAddUserToGroupResponse() {
        return new AddUserToGroupResponse();
    }

    /**
     * Create an instance of {@link GetSubnets }
     * 
     */
    public GetSubnets createGetSubnets() {
        return new GetSubnets();
    }

    /**
     * Create an instance of {@link GetE2EViewResponse }
     * 
     */
    public GetE2EViewResponse createGetE2EViewResponse() {
        return new GetE2EViewResponse();
    }

    /**
     * Create an instance of {@link CreateTemplateElement }
     * 
     */
    public CreateTemplateElement createCreateTemplateElement() {
        return new CreateTemplateElement();
    }

    /**
     * Create an instance of {@link UpdateTaskParameters }
     * 
     */
    public UpdateTaskParameters createUpdateTaskParameters() {
        return new UpdateTaskParameters();
    }

    /**
     * Create an instance of {@link LaunchSupervisedSynchronizationTask }
     * 
     */
    public LaunchSupervisedSynchronizationTask createLaunchSupervisedSynchronizationTask() {
        return new LaunchSupervisedSynchronizationTask();
    }

    /**
     * Create an instance of {@link GetSpecialAttributes }
     * 
     */
    public GetSpecialAttributes createGetSpecialAttributes() {
        return new GetSpecialAttributes();
    }

    /**
     * Create an instance of {@link CreateTemplateResponse }
     * 
     */
    public CreateTemplateResponse createCreateTemplateResponse() {
        return new CreateTemplateResponse();
    }

    /**
     * Create an instance of {@link DeleteActivityResponse }
     * 
     */
    public DeleteActivityResponse createDeleteActivityResponse() {
        return new DeleteActivityResponse();
    }

    /**
     * Create an instance of {@link GetArtifactDefinitionForActivityResponse }
     * 
     */
    public GetArtifactDefinitionForActivityResponse createGetArtifactDefinitionForActivityResponse() {
        return new GetArtifactDefinitionForActivityResponse();
    }

    /**
     * Create an instance of {@link GetPool }
     * 
     */
    public GetPool createGetPool() {
        return new GetPool();
    }

    /**
     * Create an instance of {@link GetAllClassesLight }
     * 
     */
    public GetAllClassesLight createGetAllClassesLight() {
        return new GetAllClassesLight();
    }

    /**
     * Create an instance of {@link AssociateObjectsToContractResponse }
     * 
     */
    public AssociateObjectsToContractResponse createAssociateObjectsToContractResponse() {
        return new AssociateObjectsToContractResponse();
    }

    /**
     * Create an instance of {@link DeleteServicePool }
     * 
     */
    public DeleteServicePool createDeleteServicePool() {
        return new DeleteServicePool();
    }

    /**
     * Create an instance of {@link GetPossibleSpecialChildren }
     * 
     */
    public GetPossibleSpecialChildren createGetPossibleSpecialChildren() {
        return new GetPossibleSpecialChildren();
    }

    /**
     * Create an instance of {@link LaunchSupervisedSynchronizationTaskResponse }
     * 
     */
    public LaunchSupervisedSynchronizationTaskResponse createLaunchSupervisedSynchronizationTaskResponse() {
        return new LaunchSupervisedSynchronizationTaskResponse();
    }

    /**
     * Create an instance of {@link GetAttributeForClassWithId }
     * 
     */
    public GetAttributeForClassWithId createGetAttributeForClassWithId() {
        return new GetAttributeForClassWithId();
    }

    /**
     * Create an instance of {@link GetPhysicalConnectionEndpoints }
     * 
     */
    public GetPhysicalConnectionEndpoints createGetPhysicalConnectionEndpoints() {
        return new GetPhysicalConnectionEndpoints();
    }

    /**
     * Create an instance of {@link GetSubscribersForTaskResponse }
     * 
     */
    public GetSubscribersForTaskResponse createGetSubscribersForTaskResponse() {
        return new GetSubscribersForTaskResponse();
    }

    /**
     * Create an instance of {@link UpdateTaskProperties }
     * 
     */
    public UpdateTaskProperties createUpdateTaskProperties() {
        return new UpdateTaskProperties();
    }

    /**
     * Create an instance of {@link DeleteActivity }
     * 
     */
    public DeleteActivity createDeleteActivity() {
        return new DeleteActivity();
    }

    /**
     * Create an instance of {@link CreateSparePartResponse }
     * 
     */
    public CreateSparePartResponse createCreateSparePartResponse() {
        return new CreateSparePartResponse();
    }

    /**
     * Create an instance of {@link GetAllClasses }
     * 
     */
    public GetAllClasses createGetAllClasses() {
        return new GetAllClasses();
    }

    /**
     * Create an instance of {@link IsSubclassOf }
     * 
     */
    public IsSubclassOf createIsSubclassOf() {
        return new IsSubclassOf();
    }

    /**
     * Create an instance of {@link GetTemplatesForClass }
     * 
     */
    public GetTemplatesForClass createGetTemplatesForClass() {
        return new GetTemplatesForClass();
    }

    /**
     * Create an instance of {@link SetAttributePropertiesForClassWithId }
     * 
     */
    public SetAttributePropertiesForClassWithId createSetAttributePropertiesForClassWithId() {
        return new SetAttributePropertiesForClassWithId();
    }

    /**
     * Create an instance of {@link DeleteSDHContainerLinkResponse }
     * 
     */
    public DeleteSDHContainerLinkResponse createDeleteSDHContainerLinkResponse() {
        return new DeleteSDHContainerLinkResponse();
    }

    /**
     * Create an instance of {@link GetSiblingsResponse }
     * 
     */
    public GetSiblingsResponse createGetSiblingsResponse() {
        return new GetSiblingsResponse();
    }

    /**
     * Create an instance of {@link GetObjectLight }
     * 
     */
    public GetObjectLight createGetObjectLight() {
        return new GetObjectLight();
    }

    /**
     * Create an instance of {@link GetWarehouseRootPools }
     * 
     */
    public GetWarehouseRootPools createGetWarehouseRootPools() {
        return new GetWarehouseRootPools();
    }

    /**
     * Create an instance of {@link RemovePossibleSpecialChildrenResponse }
     * 
     */
    public RemovePossibleSpecialChildrenResponse createRemovePossibleSpecialChildrenResponse() {
        return new RemovePossibleSpecialChildrenResponse();
    }

    /**
     * Create an instance of {@link GetConfigurationVariablesPools }
     * 
     */
    public GetConfigurationVariablesPools createGetConfigurationVariablesPools() {
        return new GetConfigurationVariablesPools();
    }

    /**
     * Create an instance of {@link UpdateProxyPoolResponse }
     * 
     */
    public UpdateProxyPoolResponse createUpdateProxyPoolResponse() {
        return new UpdateProxyPoolResponse();
    }

    /**
     * Create an instance of {@link UpdateGeneralView }
     * 
     */
    public UpdateGeneralView createUpdateGeneralView() {
        return new UpdateGeneralView();
    }

    /**
     * Create an instance of {@link CreateBulkTemplateElementResponse }
     * 
     */
    public CreateBulkTemplateElementResponse createCreateBulkTemplateElementResponse() {
        return new CreateBulkTemplateElementResponse();
    }

    /**
     * Create an instance of {@link ReleaseObjectFromContractResponse }
     * 
     */
    public ReleaseObjectFromContractResponse createReleaseObjectFromContractResponse() {
        return new ReleaseObjectFromContractResponse();
    }

    /**
     * Create an instance of {@link UpdateObjectRelatedViewResponse }
     * 
     */
    public UpdateObjectRelatedViewResponse createUpdateObjectRelatedViewResponse() {
        return new UpdateObjectRelatedViewResponse();
    }

    /**
     * Create an instance of {@link CreateBusinessRule }
     * 
     */
    public CreateBusinessRule createCreateBusinessRule() {
        return new CreateBusinessRule();
    }

    /**
     * Create an instance of {@link DeleteTaskResponse }
     * 
     */
    public DeleteTaskResponse createDeleteTaskResponse() {
        return new DeleteTaskResponse();
    }

    /**
     * Create an instance of {@link CloseSession }
     * 
     */
    public CloseSession createCloseSession() {
        return new CloseSession();
    }

    /**
     * Create an instance of {@link GetNextActivityForProcessInstance }
     * 
     */
    public GetNextActivityForProcessInstance createGetNextActivityForProcessInstance() {
        return new GetNextActivityForProcessInstance();
    }

    /**
     * Create an instance of {@link SetPrivilegeToUserResponse }
     * 
     */
    public SetPrivilegeToUserResponse createSetPrivilegeToUserResponse() {
        return new SetPrivilegeToUserResponse();
    }

    /**
     * Create an instance of {@link DeleteAttributeResponse }
     * 
     */
    public DeleteAttributeResponse createDeleteAttributeResponse() {
        return new DeleteAttributeResponse();
    }

    /**
     * Create an instance of {@link DownloadBulkLoadLog }
     * 
     */
    public DownloadBulkLoadLog createDownloadBulkLoadLog() {
        return new DownloadBulkLoadLog();
    }

    /**
     * Create an instance of {@link GetSDHTransportLinkStructure }
     * 
     */
    public GetSDHTransportLinkStructure createGetSDHTransportLinkStructure() {
        return new GetSDHTransportLinkStructure();
    }

    /**
     * Create an instance of {@link GetGeneralViewResponse }
     * 
     */
    public GetGeneralViewResponse createGetGeneralViewResponse() {
        return new GetGeneralViewResponse();
    }

    /**
     * Create an instance of {@link GetObjectsWithFilterResponse }
     * 
     */
    public GetObjectsWithFilterResponse createGetObjectsWithFilterResponse() {
        return new GetObjectsWithFilterResponse();
    }

    /**
     * Create an instance of {@link GetContactsForCustomer }
     * 
     */
    public GetContactsForCustomer createGetContactsForCustomer() {
        return new GetContactsForCustomer();
    }

    /**
     * Create an instance of {@link CreateSubnetResponse }
     * 
     */
    public CreateSubnetResponse createCreateSubnetResponse() {
        return new CreateSubnetResponse();
    }

    /**
     * Create an instance of {@link RelateIPtoPortResponse }
     * 
     */
    public RelateIPtoPortResponse createRelateIPtoPortResponse() {
        return new RelateIPtoPortResponse();
    }

    /**
     * Create an instance of {@link DeleteContact }
     * 
     */
    public DeleteContact createDeleteContact() {
        return new DeleteContact();
    }

    /**
     * Create an instance of {@link GetMPLSLinkEndpointsResponse }
     * 
     */
    public GetMPLSLinkEndpointsResponse createGetMPLSLinkEndpointsResponse() {
        return new GetMPLSLinkEndpointsResponse();
    }

    /**
     * Create an instance of {@link RemoteActivityDefinition }
     * 
     */
    public RemoteActivityDefinition createRemoteActivityDefinition() {
        return new RemoteActivityDefinition();
    }

    /**
     * Create an instance of {@link CreateUser }
     * 
     */
    public CreateUser createCreateUser() {
        return new CreateUser();
    }

    /**
     * Create an instance of {@link RelateObjectsToProjectResponse }
     * 
     */
    public RelateObjectsToProjectResponse createRelateObjectsToProjectResponse() {
        return new RelateObjectsToProjectResponse();
    }

    /**
     * Create an instance of {@link SetClassProperties }
     * 
     */
    public SetClassProperties createSetClassProperties() {
        return new SetClassProperties();
    }

    /**
     * Create an instance of {@link CreateSDHTributaryLinkResponse }
     * 
     */
    public CreateSDHTributaryLinkResponse createCreateSDHTributaryLinkResponse() {
        return new CreateSDHTributaryLinkResponse();
    }

    /**
     * Create an instance of {@link GetProxyPools }
     * 
     */
    public GetProxyPools createGetProxyPools() {
        return new GetProxyPools();
    }

    /**
     * Create an instance of {@link GetClass }
     * 
     */
    public GetClass createGetClass() {
        return new GetClass();
    }

    /**
     * Create an instance of {@link DeleteConfigurationVariable }
     * 
     */
    public DeleteConfigurationVariable createDeleteConfigurationVariable() {
        return new DeleteConfigurationVariable();
    }

    /**
     * Create an instance of {@link CreateMPLSLinkResponse }
     * 
     */
    public CreateMPLSLinkResponse createCreateMPLSLinkResponse() {
        return new CreateMPLSLinkResponse();
    }

    /**
     * Create an instance of {@link GetPoolItemsResponse }
     * 
     */
    public GetPoolItemsResponse createGetPoolItemsResponse() {
        return new GetPoolItemsResponse();
    }

    /**
     * Create an instance of {@link GetClassWithIdResponse }
     * 
     */
    public GetClassWithIdResponse createGetClassWithIdResponse() {
        return new GetClassWithIdResponse();
    }

    /**
     * Create an instance of {@link ExecuteInventoryLevelReport }
     * 
     */
    public ExecuteInventoryLevelReport createExecuteInventoryLevelReport() {
        return new ExecuteInventoryLevelReport();
    }

    /**
     * Create an instance of {@link GetAllConfigurationVariablesResponse }
     * 
     */
    public GetAllConfigurationVariablesResponse createGetAllConfigurationVariablesResponse() {
        return new GetAllConfigurationVariablesResponse();
    }

    /**
     * Create an instance of {@link GetListTypeItem }
     * 
     */
    public GetListTypeItem createGetListTypeItem() {
        return new GetListTypeItem();
    }

    /**
     * Create an instance of {@link RemoveObjectsFromFavoritesFolder }
     * 
     */
    public RemoveObjectsFromFavoritesFolder createRemoveObjectsFromFavoritesFolder() {
        return new RemoveObjectsFromFavoritesFolder();
    }

    /**
     * Create an instance of {@link UpdateProxyPool }
     * 
     */
    public UpdateProxyPool createUpdateProxyPool() {
        return new UpdateProxyPool();
    }

    /**
     * Create an instance of {@link CreateTemplateSpecialElementResponse }
     * 
     */
    public CreateTemplateSpecialElementResponse createCreateTemplateSpecialElementResponse() {
        return new CreateTemplateSpecialElementResponse();
    }

    /**
     * Create an instance of {@link GetNextActivityForProcessInstanceResponse }
     * 
     */
    public GetNextActivityForProcessInstanceResponse createGetNextActivityForProcessInstanceResponse() {
        return new GetNextActivityForProcessInstanceResponse();
    }

    /**
     * Create an instance of {@link GetContainersBetweenObjectsResponse }
     * 
     */
    public GetContainersBetweenObjectsResponse createGetContainersBetweenObjectsResponse() {
        return new GetContainersBetweenObjectsResponse();
    }

    /**
     * Create an instance of {@link GetContractPool }
     * 
     */
    public GetContractPool createGetContractPool() {
        return new GetContractPool();
    }

    /**
     * Create an instance of {@link GetContactResponse }
     * 
     */
    public GetContactResponse createGetContactResponse() {
        return new GetContactResponse();
    }

    /**
     * Create an instance of {@link CreateGroup }
     * 
     */
    public CreateGroup createCreateGroup() {
        return new CreateGroup();
    }

    /**
     * Create an instance of {@link GetDeviceLayouts }
     * 
     */
    public GetDeviceLayouts createGetDeviceLayouts() {
        return new GetDeviceLayouts();
    }

    /**
     * Create an instance of {@link CreatePhysicalConnectionResponse }
     * 
     */
    public CreatePhysicalConnectionResponse createCreatePhysicalConnectionResponse() {
        return new CreatePhysicalConnectionResponse();
    }

    /**
     * Create an instance of {@link GetSpecialAttributesResponse }
     * 
     */
    public GetSpecialAttributesResponse createGetSpecialAttributesResponse() {
        return new GetSpecialAttributesResponse();
    }

    /**
     * Create an instance of {@link CreatePoolItemResponse }
     * 
     */
    public CreatePoolItemResponse createCreatePoolItemResponse() {
        return new CreatePoolItemResponse();
    }

    /**
     * Create an instance of {@link UpdateTaskSchedule }
     * 
     */
    public UpdateTaskSchedule createUpdateTaskSchedule() {
        return new UpdateTaskSchedule();
    }

    /**
     * Create an instance of {@link BulkUpload }
     * 
     */
    public BulkUpload createBulkUpload() {
        return new BulkUpload();
    }

    /**
     * Create an instance of {@link DeleteUsersResponse }
     * 
     */
    public DeleteUsersResponse createDeleteUsersResponse() {
        return new DeleteUsersResponse();
    }

    /**
     * Create an instance of {@link GetObjectSpecialChildrenResponse }
     * 
     */
    public GetObjectSpecialChildrenResponse createGetObjectSpecialChildrenResponse() {
        return new GetObjectSpecialChildrenResponse();
    }

    /**
     * Create an instance of {@link GetRootPools }
     * 
     */
    public GetRootPools createGetRootPools() {
        return new GetRootPools();
    }

    /**
     * Create an instance of {@link ExecuteQueryResponse }
     * 
     */
    public ExecuteQueryResponse createExecuteQueryResponse() {
        return new ExecuteQueryResponse();
    }

    /**
     * Create an instance of {@link AssociatePhysicalNodeToWarehouse }
     * 
     */
    public AssociatePhysicalNodeToWarehouse createAssociatePhysicalNodeToWarehouse() {
        return new AssociatePhysicalNodeToWarehouse();
    }

    /**
     * Create an instance of {@link DeleteProxy }
     * 
     */
    public DeleteProxy createDeleteProxy() {
        return new DeleteProxy();
    }

    /**
     * Create an instance of {@link ReleaseObjectFromProxyResponse }
     * 
     */
    public ReleaseObjectFromProxyResponse createReleaseObjectFromProxyResponse() {
        return new ReleaseObjectFromProxyResponse();
    }

    /**
     * Create an instance of {@link CreateSynchronizationGroupResponse }
     * 
     */
    public CreateSynchronizationGroupResponse createCreateSynchronizationGroupResponse() {
        return new CreateSynchronizationGroupResponse();
    }

    /**
     * Create an instance of {@link GetAttribute }
     * 
     */
    public GetAttribute createGetAttribute() {
        return new GetAttribute();
    }

    /**
     * Create an instance of {@link UpdateReportResponse }
     * 
     */
    public UpdateReportResponse createUpdateReportResponse() {
        return new UpdateReportResponse();
    }

    /**
     * Create an instance of {@link UpdateProxy }
     * 
     */
    public UpdateProxy createUpdateProxy() {
        return new UpdateProxy();
    }

    /**
     * Create an instance of {@link GetDeviceLayoutStructureResponse }
     * 
     */
    public GetDeviceLayoutStructureResponse createGetDeviceLayoutStructureResponse() {
        return new GetDeviceLayoutStructureResponse();
    }

    /**
     * Create an instance of {@link ReleaseObjectFromService }
     * 
     */
    public ReleaseObjectFromService createReleaseObjectFromService() {
        return new ReleaseObjectFromService();
    }

    /**
     * Create an instance of {@link UpdateValidatorDefinitionResponse }
     * 
     */
    public UpdateValidatorDefinitionResponse createUpdateValidatorDefinitionResponse() {
        return new UpdateValidatorDefinitionResponse();
    }

    /**
     * Create an instance of {@link GetObjectRelatedViews }
     * 
     */
    public GetObjectRelatedViews createGetObjectRelatedViews() {
        return new GetObjectRelatedViews();
    }

    /**
     * Create an instance of {@link CreateCustomer }
     * 
     */
    public CreateCustomer createCreateCustomer() {
        return new CreateCustomer();
    }

    /**
     * Create an instance of {@link LaunchAdHocAutomatedSynchronizationTaskResponse }
     * 
     */
    public LaunchAdHocAutomatedSynchronizationTaskResponse createLaunchAdHocAutomatedSynchronizationTaskResponse() {
        return new LaunchAdHocAutomatedSynchronizationTaskResponse();
    }

    /**
     * Create an instance of {@link DeleteSynchronizationDataSourceConfig }
     * 
     */
    public DeleteSynchronizationDataSourceConfig createDeleteSynchronizationDataSourceConfig() {
        return new DeleteSynchronizationDataSourceConfig();
    }

    /**
     * Create an instance of {@link GetCommonParentResponse }
     * 
     */
    public GetCommonParentResponse createGetCommonParentResponse() {
        return new GetCommonParentResponse();
    }

    /**
     * Create an instance of {@link GetArtifactForActivityResponse }
     * 
     */
    public GetArtifactForActivityResponse createGetArtifactForActivityResponse() {
        return new GetArtifactForActivityResponse();
    }

    /**
     * Create an instance of {@link GetSubClassesLightNoRecursive }
     * 
     */
    public GetSubClassesLightNoRecursive createGetSubClassesLightNoRecursive() {
        return new GetSubClassesLightNoRecursive();
    }

    /**
     * Create an instance of {@link SetPoolProperties }
     * 
     */
    public SetPoolProperties createSetPoolProperties() {
        return new SetPoolProperties();
    }

    /**
     * Create an instance of {@link CreateServicePool }
     * 
     */
    public CreateServicePool createCreateServicePool() {
        return new CreateServicePool();
    }

    /**
     * Create an instance of {@link AddUserToGroup }
     * 
     */
    public AddUserToGroup createAddUserToGroup() {
        return new AddUserToGroup();
    }

    /**
     * Create an instance of {@link GetProcessDefinitionResponse }
     * 
     */
    public GetProcessDefinitionResponse createGetProcessDefinitionResponse() {
        return new GetProcessDefinitionResponse();
    }

    /**
     * Create an instance of {@link UpdateTaskNotificationType }
     * 
     */
    public UpdateTaskNotificationType createUpdateTaskNotificationType() {
        return new UpdateTaskNotificationType();
    }

    /**
     * Create an instance of {@link GetUpstreamClassHierarchy }
     * 
     */
    public GetUpstreamClassHierarchy createGetUpstreamClassHierarchy() {
        return new GetUpstreamClassHierarchy();
    }

    /**
     * Create an instance of {@link DeleteContract }
     * 
     */
    public DeleteContract createDeleteContract() {
        return new DeleteContract();
    }

    /**
     * Create an instance of {@link GetProxiesInPoolResponse }
     * 
     */
    public GetProxiesInPoolResponse createGetProxiesInPoolResponse() {
        return new GetProxiesInPoolResponse();
    }

    /**
     * Create an instance of {@link UpdateFavoritesFolderResponse }
     * 
     */
    public UpdateFavoritesFolderResponse createUpdateFavoritesFolderResponse() {
        return new UpdateFavoritesFolderResponse();
    }

    /**
     * Create an instance of {@link DeleteBusinessRuleResponse }
     * 
     */
    public DeleteBusinessRuleResponse createDeleteBusinessRuleResponse() {
        return new DeleteBusinessRuleResponse();
    }

    /**
     * Create an instance of {@link GetPossibleChildrenNoRecursive }
     * 
     */
    public GetPossibleChildrenNoRecursive createGetPossibleChildrenNoRecursive() {
        return new GetPossibleChildrenNoRecursive();
    }

    /**
     * Create an instance of {@link RelateObjectsToServiceResponse }
     * 
     */
    public RelateObjectsToServiceResponse createRelateObjectsToServiceResponse() {
        return new RelateObjectsToServiceResponse();
    }

    /**
     * Create an instance of {@link GetSynchronizationGroups }
     * 
     */
    public GetSynchronizationGroups createGetSynchronizationGroups() {
        return new GetSynchronizationGroups();
    }

    /**
     * Create an instance of {@link CreateSubnetPool }
     * 
     */
    public CreateSubnetPool createCreateSubnetPool() {
        return new CreateSubnetPool();
    }

    /**
     * Create an instance of {@link GetConfigurationVariablesWithPrefixResponse }
     * 
     */
    public GetConfigurationVariablesWithPrefixResponse createGetConfigurationVariablesWithPrefixResponse() {
        return new GetConfigurationVariablesWithPrefixResponse();
    }

    /**
     * Create an instance of {@link RemovePossibleChildrenForClassWithId }
     * 
     */
    public RemovePossibleChildrenForClassWithId createRemovePossibleChildrenForClassWithId() {
        return new RemovePossibleChildrenForClassWithId();
    }

    /**
     * Create an instance of {@link SetPrivilegeToGroup }
     * 
     */
    public SetPrivilegeToGroup createSetPrivilegeToGroup() {
        return new SetPrivilegeToGroup();
    }

    /**
     * Create an instance of {@link DeleteListTypeItem }
     * 
     */
    public DeleteListTypeItem createDeleteListTypeItem() {
        return new DeleteListTypeItem();
    }

    /**
     * Create an instance of {@link GetAllValidatorDefinitionsResponse }
     * 
     */
    public GetAllValidatorDefinitionsResponse createGetAllValidatorDefinitionsResponse() {
        return new GetAllValidatorDefinitionsResponse();
    }

    /**
     * Create an instance of {@link CreateRootPoolResponse }
     * 
     */
    public CreateRootPoolResponse createCreateRootPoolResponse() {
        return new CreateRootPoolResponse();
    }

    /**
     * Create an instance of {@link GetGeneralViews }
     * 
     */
    public GetGeneralViews createGetGeneralViews() {
        return new GetGeneralViews();
    }

    /**
     * Create an instance of {@link GetContractPools }
     * 
     */
    public GetContractPools createGetContractPools() {
        return new GetContractPools();
    }

    /**
     * Create an instance of {@link DeleteValidatorDefinition }
     * 
     */
    public DeleteValidatorDefinition createDeleteValidatorDefinition() {
        return new DeleteValidatorDefinition();
    }

    /**
     * Create an instance of {@link GetGeneralViewsResponse }
     * 
     */
    public GetGeneralViewsResponse createGetGeneralViewsResponse() {
        return new GetGeneralViewsResponse();
    }

    /**
     * Create an instance of {@link SetUserProperties }
     * 
     */
    public SetUserProperties createSetUserProperties() {
        return new SetUserProperties();
    }

    /**
     * Create an instance of {@link GetIPAddressResponse }
     * 
     */
    public GetIPAddressResponse createGetIPAddressResponse() {
        return new GetIPAddressResponse();
    }

    /**
     * Create an instance of {@link UpdateCustomer }
     * 
     */
    public UpdateCustomer createUpdateCustomer() {
        return new UpdateCustomer();
    }

    /**
     * Create an instance of {@link GetConfigurationVariablesPoolsResponse }
     * 
     */
    public GetConfigurationVariablesPoolsResponse createGetConfigurationVariablesPoolsResponse() {
        return new GetConfigurationVariablesPoolsResponse();
    }

    /**
     * Create an instance of {@link GetServicePoolsInCostumerResponse }
     * 
     */
    public GetServicePoolsInCostumerResponse createGetServicePoolsInCostumerResponse() {
        return new GetServicePoolsInCostumerResponse();
    }

    /**
     * Create an instance of {@link UpdateServicePoolResponse }
     * 
     */
    public UpdateServicePoolResponse createUpdateServicePoolResponse() {
        return new UpdateServicePoolResponse();
    }

    /**
     * Create an instance of {@link GetListTypeItems }
     * 
     */
    public GetListTypeItems createGetListTypeItems() {
        return new GetListTypeItems();
    }

    /**
     * Create an instance of {@link SetUserPropertiesResponse }
     * 
     */
    public SetUserPropertiesResponse createSetUserPropertiesResponse() {
        return new SetUserPropertiesResponse();
    }

    /**
     * Create an instance of {@link SubscribeUserToTask }
     * 
     */
    public SubscribeUserToTask createSubscribeUserToTask() {
        return new SubscribeUserToTask();
    }

    /**
     * Create an instance of {@link CreateListTypeItem }
     * 
     */
    public CreateListTypeItem createCreateListTypeItem() {
        return new CreateListTypeItem();
    }

    /**
     * Create an instance of {@link CreateContractPool }
     * 
     */
    public CreateContractPool createCreateContractPool() {
        return new CreateContractPool();
    }

    /**
     * Create an instance of {@link UpdateTemplateElement }
     * 
     */
    public UpdateTemplateElement createUpdateTemplateElement() {
        return new UpdateTemplateElement();
    }

    /**
     * Create an instance of {@link GetGeneralView }
     * 
     */
    public GetGeneralView createGetGeneralView() {
        return new GetGeneralView();
    }

    /**
     * Create an instance of {@link GetFirstParentOfClassResponse }
     * 
     */
    public GetFirstParentOfClassResponse createGetFirstParentOfClassResponse() {
        return new GetFirstParentOfClassResponse();
    }

    /**
     * Create an instance of {@link CreateCustomerPool }
     * 
     */
    public CreateCustomerPool createCreateCustomerPool() {
        return new CreateCustomerPool();
    }

    /**
     * Create an instance of {@link GetProjectsRelatedToObject }
     * 
     */
    public GetProjectsRelatedToObject createGetProjectsRelatedToObject() {
        return new GetProjectsRelatedToObject();
    }

    /**
     * Create an instance of {@link MoveObjectsToWarehousePoolResponse }
     * 
     */
    public MoveObjectsToWarehousePoolResponse createMoveObjectsToWarehousePoolResponse() {
        return new MoveObjectsToWarehousePoolResponse();
    }

    /**
     * Create an instance of {@link CreateBulkSpecialObjects }
     * 
     */
    public CreateBulkSpecialObjects createCreateBulkSpecialObjects() {
        return new CreateBulkSpecialObjects();
    }

    /**
     * Create an instance of {@link ExecuteTaskResponse }
     * 
     */
    public ExecuteTaskResponse createExecuteTaskResponse() {
        return new ExecuteTaskResponse();
    }

    /**
     * Create an instance of {@link ReleaseMirrorMultiplePortResponse }
     * 
     */
    public ReleaseMirrorMultiplePortResponse createReleaseMirrorMultiplePortResponse() {
        return new ReleaseMirrorMultiplePortResponse();
    }

    /**
     * Create an instance of {@link UpdateContactResponse }
     * 
     */
    public UpdateContactResponse createUpdateContactResponse() {
        return new UpdateContactResponse();
    }

    /**
     * Create an instance of {@link DeleteTemplateElementResponse }
     * 
     */
    public DeleteTemplateElementResponse createDeleteTemplateElementResponse() {
        return new DeleteTemplateElementResponse();
    }

    /**
     * Create an instance of {@link CreateSynchronizationGroup }
     * 
     */
    public CreateSynchronizationGroup createCreateSynchronizationGroup() {
        return new CreateSynchronizationGroup();
    }

    /**
     * Create an instance of {@link CreateAttribute }
     * 
     */
    public CreateAttribute createCreateAttribute() {
        return new CreateAttribute();
    }

    /**
     * Create an instance of {@link GetConfigurationVariablesInPoolResponse }
     * 
     */
    public GetConfigurationVariablesInPoolResponse createGetConfigurationVariablesInPoolResponse() {
        return new GetConfigurationVariablesInPoolResponse();
    }

    /**
     * Create an instance of {@link RelateSubnetToVlan }
     * 
     */
    public RelateSubnetToVlan createRelateSubnetToVlan() {
        return new RelateSubnetToVlan();
    }

    /**
     * Create an instance of {@link DeleteProcessDefinition }
     * 
     */
    public DeleteProcessDefinition createDeleteProcessDefinition() {
        return new DeleteProcessDefinition();
    }

    /**
     * Create an instance of {@link RelatePortToInterfaceResponse }
     * 
     */
    public RelatePortToInterfaceResponse createRelatePortToInterfaceResponse() {
        return new RelatePortToInterfaceResponse();
    }

    /**
     * Create an instance of {@link KillJobResponse }
     * 
     */
    public KillJobResponse createKillJobResponse() {
        return new KillJobResponse();
    }

    /**
     * Create an instance of {@link CreateClassResponse }
     * 
     */
    public CreateClassResponse createCreateClassResponse() {
        return new CreateClassResponse();
    }

    /**
     * Create an instance of {@link ConnectPhysicalContainersResponse }
     * 
     */
    public ConnectPhysicalContainersResponse createConnectPhysicalContainersResponse() {
        return new ConnectPhysicalContainersResponse();
    }

    /**
     * Create an instance of {@link GetContract }
     * 
     */
    public GetContract createGetContract() {
        return new GetContract();
    }

    /**
     * Create an instance of {@link SearchForContactsResponse }
     * 
     */
    public SearchForContactsResponse createSearchForContactsResponse() {
        return new SearchForContactsResponse();
    }

    /**
     * Create an instance of {@link CommitActivityResponse }
     * 
     */
    public CommitActivityResponse createCommitActivityResponse() {
        return new CommitActivityResponse();
    }

    /**
     * Create an instance of {@link CreateCustomerPoolResponse }
     * 
     */
    public CreateCustomerPoolResponse createCreateCustomerPoolResponse() {
        return new CreateCustomerPoolResponse();
    }

    /**
     * Create an instance of {@link CreateGeneralViewResponse }
     * 
     */
    public CreateGeneralViewResponse createCreateGeneralViewResponse() {
        return new CreateGeneralViewResponse();
    }

    /**
     * Create an instance of {@link FindSDHRoutesUsingContainerLinksResponse }
     * 
     */
    public FindSDHRoutesUsingContainerLinksResponse createFindSDHRoutesUsingContainerLinksResponse() {
        return new FindSDHRoutesUsingContainerLinksResponse();
    }

    /**
     * Create an instance of {@link GetTemplateSpecialElementChildrenResponse }
     * 
     */
    public GetTemplateSpecialElementChildrenResponse createGetTemplateSpecialElementChildrenResponse() {
        return new GetTemplateSpecialElementChildrenResponse();
    }

    /**
     * Create an instance of {@link CreateGeneralView }
     * 
     */
    public CreateGeneralView createCreateGeneralView() {
        return new CreateGeneralView();
    }

    /**
     * Create an instance of {@link AssociateObjectToProxyResponse }
     * 
     */
    public AssociateObjectToProxyResponse createAssociateObjectToProxyResponse() {
        return new AssociateObjectToProxyResponse();
    }

    /**
     * Create an instance of {@link DeletePools }
     * 
     */
    public DeletePools createDeletePools() {
        return new DeletePools();
    }

    /**
     * Create an instance of {@link ExecuteClassLevelReportResponse }
     * 
     */
    public ExecuteClassLevelReportResponse createExecuteClassLevelReportResponse() {
        return new ExecuteClassLevelReportResponse();
    }

    /**
     * Create an instance of {@link DeleteCustomerPool }
     * 
     */
    public DeleteCustomerPool createDeleteCustomerPool() {
        return new DeleteCustomerPool();
    }

    /**
     * Create an instance of {@link GetClassHierarchyResponse }
     * 
     */
    public GetClassHierarchyResponse createGetClassHierarchyResponse() {
        return new GetClassHierarchyResponse();
    }

    /**
     * Create an instance of {@link GetAllProjects }
     * 
     */
    public GetAllProjects createGetAllProjects() {
        return new GetAllProjects();
    }

    /**
     * Create an instance of {@link GetAllValidatorDefinitions }
     * 
     */
    public GetAllValidatorDefinitions createGetAllValidatorDefinitions() {
        return new GetAllValidatorDefinitions();
    }

    /**
     * Create an instance of {@link DeleteReportResponse }
     * 
     */
    public DeleteReportResponse createDeleteReportResponse() {
        return new DeleteReportResponse();
    }

    /**
     * Create an instance of {@link ExecuteQuery }
     * 
     */
    public ExecuteQuery createExecuteQuery() {
        return new ExecuteQuery();
    }

    /**
     * Create an instance of {@link GetArtifactForActivity }
     * 
     */
    public GetArtifactForActivity createGetArtifactForActivity() {
        return new GetArtifactForActivity();
    }

    /**
     * Create an instance of {@link GetObjectChildren }
     * 
     */
    public GetObjectChildren createGetObjectChildren() {
        return new GetObjectChildren();
    }

    /**
     * Create an instance of {@link UpdateService }
     * 
     */
    public UpdateService createUpdateService() {
        return new UpdateService();
    }

    /**
     * Create an instance of {@link GetParentsUntilFirstOfClass }
     * 
     */
    public GetParentsUntilFirstOfClass createGetParentsUntilFirstOfClass() {
        return new GetParentsUntilFirstOfClass();
    }

    /**
     * Create an instance of {@link GetCustomersInPool }
     * 
     */
    public GetCustomersInPool createGetCustomersInPool() {
        return new GetCustomersInPool();
    }

    /**
     * Create an instance of {@link CreateWarehouse }
     * 
     */
    public CreateWarehouse createCreateWarehouse() {
        return new CreateWarehouse();
    }

    /**
     * Create an instance of {@link GetParentResponse }
     * 
     */
    public GetParentResponse createGetParentResponse() {
        return new GetParentResponse();
    }

    /**
     * Create an instance of {@link RelateSubnetToVrf }
     * 
     */
    public RelateSubnetToVrf createRelateSubnetToVrf() {
        return new RelateSubnetToVrf();
    }

    /**
     * Create an instance of {@link AddPossibleChildrenForClassWithId }
     * 
     */
    public AddPossibleChildrenForClassWithId createAddPossibleChildrenForClassWithId() {
        return new AddPossibleChildrenForClassWithId();
    }

    /**
     * Create an instance of {@link CopyPoolItemToPoolResponse }
     * 
     */
    public CopyPoolItemToPoolResponse createCopyPoolItemToPoolResponse() {
        return new CopyPoolItemToPoolResponse();
    }

    /**
     * Create an instance of {@link MoveObjectsToPool }
     * 
     */
    public MoveObjectsToPool createMoveObjectsToPool() {
        return new MoveObjectsToPool();
    }

    /**
     * Create an instance of {@link GetAllClassesResponse }
     * 
     */
    public GetAllClassesResponse createGetAllClassesResponse() {
        return new GetAllClassesResponse();
    }

    /**
     * Create an instance of {@link GetClassResponse }
     * 
     */
    public GetClassResponse createGetClassResponse() {
        return new GetClassResponse();
    }

    /**
     * Create an instance of {@link CreateSDHContainerLinkResponse }
     * 
     */
    public CreateSDHContainerLinkResponse createCreateSDHContainerLinkResponse() {
        return new CreateSDHContainerLinkResponse();
    }

    /**
     * Create an instance of {@link ReleasePortFromInterface }
     * 
     */
    public ReleasePortFromInterface createReleasePortFromInterface() {
        return new ReleasePortFromInterface();
    }

    /**
     * Create an instance of {@link CreateClass }
     * 
     */
    public CreateClass createCreateClass() {
        return new CreateClass();
    }

    /**
     * Create an instance of {@link GetGroupsForUser }
     * 
     */
    public GetGroupsForUser createGetGroupsForUser() {
        return new GetGroupsForUser();
    }

    /**
     * Create an instance of {@link AddObjectsToFavoritesFolder }
     * 
     */
    public AddObjectsToFavoritesFolder createAddObjectsToFavoritesFolder() {
        return new AddObjectsToFavoritesFolder();
    }

    /**
     * Create an instance of {@link GetPoolsInObjectResponse }
     * 
     */
    public GetPoolsInObjectResponse createGetPoolsInObjectResponse() {
        return new GetPoolsInObjectResponse();
    }

    /**
     * Create an instance of {@link CreateQuery }
     * 
     */
    public CreateQuery createCreateQuery() {
        return new CreateQuery();
    }

    /**
     * Create an instance of {@link DeleteContractPool }
     * 
     */
    public DeleteContractPool createDeleteContractPool() {
        return new DeleteContractPool();
    }

    /**
     * Create an instance of {@link GetActivityResponse }
     * 
     */
    public GetActivityResponse createGetActivityResponse() {
        return new GetActivityResponse();
    }

    /**
     * Create an instance of {@link GetProjectResponse }
     * 
     */
    public GetProjectResponse createGetProjectResponse() {
        return new GetProjectResponse();
    }

    /**
     * Create an instance of {@link GetSubnetsInSubnet }
     * 
     */
    public GetSubnetsInSubnet createGetSubnetsInSubnet() {
        return new GetSubnetsInSubnet();
    }

    /**
     * Create an instance of {@link GetListTypeItemRelatedViews }
     * 
     */
    public GetListTypeItemRelatedViews createGetListTypeItemRelatedViews() {
        return new GetListTypeItemRelatedViews();
    }

    /**
     * Create an instance of {@link GetTasks }
     * 
     */
    public GetTasks createGetTasks() {
        return new GetTasks();
    }

    /**
     * Create an instance of {@link GetPoolsInPool }
     * 
     */
    public GetPoolsInPool createGetPoolsInPool() {
        return new GetPoolsInPool();
    }

    /**
     * Create an instance of {@link UpdateTaskParametersResponse }
     * 
     */
    public UpdateTaskParametersResponse createUpdateTaskParametersResponse() {
        return new UpdateTaskParametersResponse();
    }

    /**
     * Create an instance of {@link UpdateContractPoolResponse }
     * 
     */
    public UpdateContractPoolResponse createUpdateContractPoolResponse() {
        return new UpdateContractPoolResponse();
    }

    /**
     * Create an instance of {@link GetBusinessRulesResponse }
     * 
     */
    public GetBusinessRulesResponse createGetBusinessRulesResponse() {
        return new GetBusinessRulesResponse();
    }

    /**
     * Create an instance of {@link MovePoolItemToPoolResponse }
     * 
     */
    public MovePoolItemToPoolResponse createMovePoolItemToPoolResponse() {
        return new MovePoolItemToPoolResponse();
    }

    /**
     * Create an instance of {@link GetPoolsInPoolResponse }
     * 
     */
    public GetPoolsInPoolResponse createGetPoolsInPoolResponse() {
        return new GetPoolsInPoolResponse();
    }

    /**
     * Create an instance of {@link GetObjectRelatedView }
     * 
     */
    public GetObjectRelatedView createGetObjectRelatedView() {
        return new GetObjectRelatedView();
    }

    /**
     * Create an instance of {@link GetPhysicalConnectionsInObjectResponse }
     * 
     */
    public GetPhysicalConnectionsInObjectResponse createGetPhysicalConnectionsInObjectResponse() {
        return new GetPhysicalConnectionsInObjectResponse();
    }

    /**
     * Create an instance of {@link DeleteListTypeItemResponse }
     * 
     */
    public DeleteListTypeItemResponse createDeleteListTypeItemResponse() {
        return new DeleteListTypeItemResponse();
    }

    /**
     * Create an instance of {@link CreateProject }
     * 
     */
    public CreateProject createCreateProject() {
        return new CreateProject();
    }

    /**
     * Create an instance of {@link GetChildrenOfClassResponse }
     * 
     */
    public GetChildrenOfClassResponse createGetChildrenOfClassResponse() {
        return new GetChildrenOfClassResponse();
    }

    /**
     * Create an instance of {@link GetFavoritesFoldersForUserResponse }
     * 
     */
    public GetFavoritesFoldersForUserResponse createGetFavoritesFoldersForUserResponse() {
        return new GetFavoritesFoldersForUserResponse();
    }

    /**
     * Create an instance of {@link DeletePhysicalConnectionResponse }
     * 
     */
    public DeletePhysicalConnectionResponse createDeletePhysicalConnectionResponse() {
        return new DeletePhysicalConnectionResponse();
    }

    /**
     * Create an instance of {@link CreateBulkSpecialTemplateElement }
     * 
     */
    public CreateBulkSpecialTemplateElement createCreateBulkSpecialTemplateElement() {
        return new CreateBulkSpecialTemplateElement();
    }

    /**
     * Create an instance of {@link DeleteClass }
     * 
     */
    public DeleteClass createDeleteClass() {
        return new DeleteClass();
    }

    /**
     * Create an instance of {@link GetContainersBetweenObjects }
     * 
     */
    public GetContainersBetweenObjects createGetContainersBetweenObjects() {
        return new GetContainersBetweenObjects();
    }

    /**
     * Create an instance of {@link CreateSynchronizationDataSourceConfigResponse }
     * 
     */
    public CreateSynchronizationDataSourceConfigResponse createCreateSynchronizationDataSourceConfigResponse() {
        return new CreateSynchronizationDataSourceConfigResponse();
    }

    /**
     * Create an instance of {@link CreatePoolInPoolResponse }
     * 
     */
    public CreatePoolInPoolResponse createCreatePoolInPoolResponse() {
        return new CreatePoolInPoolResponse();
    }

    /**
     * Create an instance of {@link GetListTypeItemRelatedViewsResponse }
     * 
     */
    public GetListTypeItemRelatedViewsResponse createGetListTypeItemRelatedViewsResponse() {
        return new GetListTypeItemRelatedViewsResponse();
    }

    /**
     * Create an instance of {@link CreatePoolInPool }
     * 
     */
    public CreatePoolInPool createCreatePoolInPool() {
        return new CreatePoolInPool();
    }

    /**
     * Create an instance of {@link GetLinkConnectedToPortResponse }
     * 
     */
    public GetLinkConnectedToPortResponse createGetLinkConnectedToPortResponse() {
        return new GetLinkConnectedToPortResponse();
    }

    /**
     * Create an instance of {@link GetFavoritesFoldersForObjectResponse }
     * 
     */
    public GetFavoritesFoldersForObjectResponse createGetFavoritesFoldersForObjectResponse() {
        return new GetFavoritesFoldersForObjectResponse();
    }

    /**
     * Create an instance of {@link HasAttribute }
     * 
     */
    public HasAttribute createHasAttribute() {
        return new HasAttribute();
    }

    /**
     * Create an instance of {@link UpdateObjectResponse }
     * 
     */
    public UpdateObjectResponse createUpdateObjectResponse() {
        return new UpdateObjectResponse();
    }

    /**
     * Create an instance of {@link DeleteProxyResponse }
     * 
     */
    public DeleteProxyResponse createDeleteProxyResponse() {
        return new DeleteProxyResponse();
    }

    /**
     * Create an instance of {@link UpdateConfigurationVariablesPool }
     * 
     */
    public UpdateConfigurationVariablesPool createUpdateConfigurationVariablesPool() {
        return new UpdateConfigurationVariablesPool();
    }

    /**
     * Create an instance of {@link RelateObjectToProject }
     * 
     */
    public RelateObjectToProject createRelateObjectToProject() {
        return new RelateObjectToProject();
    }

    /**
     * Create an instance of {@link DetachFileFromObject }
     * 
     */
    public DetachFileFromObject createDetachFileFromObject() {
        return new DetachFileFromObject();
    }

    /**
     * Create an instance of {@link DeleteProjectResponse }
     * 
     */
    public DeleteProjectResponse createDeleteProjectResponse() {
        return new DeleteProjectResponse();
    }

    /**
     * Create an instance of {@link FindSDHRoutesUsingTransportLinks }
     * 
     */
    public FindSDHRoutesUsingTransportLinks createFindSDHRoutesUsingTransportLinks() {
        return new FindSDHRoutesUsingTransportLinks();
    }

    /**
     * Create an instance of {@link GetCommonParent }
     * 
     */
    public GetCommonParent createGetCommonParent() {
        return new GetCommonParent();
    }

    /**
     * Create an instance of {@link CreateTask }
     * 
     */
    public CreateTask createCreateTask() {
        return new CreateTask();
    }

    /**
     * Create an instance of {@link GetUpstreamSpecialContainmentHierarchyResponse }
     * 
     */
    public GetUpstreamSpecialContainmentHierarchyResponse createGetUpstreamSpecialContainmentHierarchyResponse() {
        return new GetUpstreamSpecialContainmentHierarchyResponse();
    }

    /**
     * Create an instance of {@link GetContractResourcesResponse }
     * 
     */
    public GetContractResourcesResponse createGetContractResourcesResponse() {
        return new GetContractResourcesResponse();
    }

    /**
     * Create an instance of {@link GetPossibleSpecialChildrenNoRecursiveResponse }
     * 
     */
    public GetPossibleSpecialChildrenNoRecursiveResponse createGetPossibleSpecialChildrenNoRecursiveResponse() {
        return new GetPossibleSpecialChildrenNoRecursiveResponse();
    }

    /**
     * Create an instance of {@link GetFileResponse }
     * 
     */
    public GetFileResponse createGetFileResponse() {
        return new GetFileResponse();
    }

    /**
     * Create an instance of {@link GetObjectSpecialChildren }
     * 
     */
    public GetObjectSpecialChildren createGetObjectSpecialChildren() {
        return new GetObjectSpecialChildren();
    }

    /**
     * Create an instance of {@link UpdateObject }
     * 
     */
    public UpdateObject createUpdateObject() {
        return new UpdateObject();
    }

    /**
     * Create an instance of {@link GetReport }
     * 
     */
    public GetReport createGetReport() {
        return new GetReport();
    }

    /**
     * Create an instance of {@link GetBusinessObjectAuditTrail }
     * 
     */
    public GetBusinessObjectAuditTrail createGetBusinessObjectAuditTrail() {
        return new GetBusinessObjectAuditTrail();
    }

    /**
     * Create an instance of {@link UpdateProjectPoolResponse }
     * 
     */
    public UpdateProjectPoolResponse createUpdateProjectPoolResponse() {
        return new UpdateProjectPoolResponse();
    }

    /**
     * Create an instance of {@link RemovePossibleSpecialChildren }
     * 
     */
    public RemovePossibleSpecialChildren createRemovePossibleSpecialChildren() {
        return new RemovePossibleSpecialChildren();
    }

    /**
     * Create an instance of {@link MoveObjectsToWarehouseResponse }
     * 
     */
    public MoveObjectsToWarehouseResponse createMoveObjectsToWarehouseResponse() {
        return new MoveObjectsToWarehouseResponse();
    }

    /**
     * Create an instance of {@link GetUsersInGroup }
     * 
     */
    public GetUsersInGroup createGetUsersInGroup() {
        return new GetUsersInGroup();
    }

    /**
     * Create an instance of {@link GetInventoryLevelReportsResponse }
     * 
     */
    public GetInventoryLevelReportsResponse createGetInventoryLevelReportsResponse() {
        return new GetInventoryLevelReportsResponse();
    }

    /**
     * Create an instance of {@link CreateSDHContainerLink }
     * 
     */
    public CreateSDHContainerLink createCreateSDHContainerLink() {
        return new CreateSDHContainerLink();
    }

    /**
     * Create an instance of {@link ReleaseObjectFromContract }
     * 
     */
    public ReleaseObjectFromContract createReleaseObjectFromContract() {
        return new ReleaseObjectFromContract();
    }

    /**
     * Create an instance of {@link GetContractPoolsResponse }
     * 
     */
    public GetContractPoolsResponse createGetContractPoolsResponse() {
        return new GetContractPoolsResponse();
    }

    /**
     * Create an instance of {@link CreateConfigurationVariable }
     * 
     */
    public CreateConfigurationVariable createCreateConfigurationVariable() {
        return new CreateConfigurationVariable();
    }

    /**
     * Create an instance of {@link GetTemplateElementResponse }
     * 
     */
    public GetTemplateElementResponse createGetTemplateElementResponse() {
        return new GetTemplateElementResponse();
    }

    /**
     * Create an instance of {@link GetProjectPoolsResponse }
     * 
     */
    public GetProjectPoolsResponse createGetProjectPoolsResponse() {
        return new GetProjectPoolsResponse();
    }

    /**
     * Create an instance of {@link ServerSideException }
     * 
     */
    public ServerSideException createServerSideException() {
        return new ServerSideException();
    }

    /**
     * Create an instance of {@link GetAllServices }
     * 
     */
    public GetAllServices createGetAllServices() {
        return new GetAllServices();
    }

    /**
     * Create an instance of {@link DeleteQueryResponse }
     * 
     */
    public DeleteQueryResponse createDeleteQueryResponse() {
        return new DeleteQueryResponse();
    }

    /**
     * Create an instance of {@link GetAllClassesLightResponse }
     * 
     */
    public GetAllClassesLightResponse createGetAllClassesLightResponse() {
        return new GetAllClassesLightResponse();
    }

    /**
     * Create an instance of {@link SetClassPropertiesResponse }
     * 
     */
    public SetClassPropertiesResponse createSetClassPropertiesResponse() {
        return new SetClassPropertiesResponse();
    }

    /**
     * Create an instance of {@link GetSpecialChildrenOfClassLightRecursive }
     * 
     */
    public GetSpecialChildrenOfClassLightRecursive createGetSpecialChildrenOfClassLightRecursive() {
        return new GetSpecialChildrenOfClassLightRecursive();
    }

    /**
     * Create an instance of {@link GetSyncDataSourceConfigurationsResponse }
     * 
     */
    public GetSyncDataSourceConfigurationsResponse createGetSyncDataSourceConfigurationsResponse() {
        return new GetSyncDataSourceConfigurationsResponse();
    }

    /**
     * Create an instance of {@link GetPhysicalConnectionsInObject }
     * 
     */
    public GetPhysicalConnectionsInObject createGetPhysicalConnectionsInObject() {
        return new GetPhysicalConnectionsInObject();
    }

    /**
     * Create an instance of {@link GetFilesForObjectResponse }
     * 
     */
    public GetFilesForObjectResponse createGetFilesForObjectResponse() {
        return new GetFilesForObjectResponse();
    }

    /**
     * Create an instance of {@link GetServicePoolsInCostumer }
     * 
     */
    public GetServicePoolsInCostumer createGetServicePoolsInCostumer() {
        return new GetServicePoolsInCostumer();
    }

    /**
     * Create an instance of {@link AssociateObjectToProxy }
     * 
     */
    public AssociateObjectToProxy createAssociateObjectToProxy() {
        return new AssociateObjectToProxy();
    }

    /**
     * Create an instance of {@link GetCurrentJobs }
     * 
     */
    public GetCurrentJobs createGetCurrentJobs() {
        return new GetCurrentJobs();
    }

    /**
     * Create an instance of {@link GetSubnetUsedIps }
     * 
     */
    public GetSubnetUsedIps createGetSubnetUsedIps() {
        return new GetSubnetUsedIps();
    }

    /**
     * Create an instance of {@link GetCustomersInPoolResponse }
     * 
     */
    public GetCustomersInPoolResponse createGetCustomersInPoolResponse() {
        return new GetCustomersInPoolResponse();
    }

    /**
     * Create an instance of {@link CreateContactResponse }
     * 
     */
    public CreateContactResponse createCreateContactResponse() {
        return new CreateContactResponse();
    }

    /**
     * Create an instance of {@link RelatePortToInterface }
     * 
     */
    public RelatePortToInterface createRelatePortToInterface() {
        return new RelatePortToInterface();
    }

    /**
     * Create an instance of {@link GetInstanceableListTypesResponse }
     * 
     */
    public GetInstanceableListTypesResponse createGetInstanceableListTypesResponse() {
        return new GetInstanceableListTypesResponse();
    }

    /**
     * Create an instance of {@link DisconnectMPLSLink }
     * 
     */
    public DisconnectMPLSLink createDisconnectMPLSLink() {
        return new DisconnectMPLSLink();
    }

    /**
     * Create an instance of {@link DeleteValidatorDefinitionResponse }
     * 
     */
    public DeleteValidatorDefinitionResponse createDeleteValidatorDefinitionResponse() {
        return new DeleteValidatorDefinitionResponse();
    }

    /**
     * Create an instance of {@link CreateTemplate }
     * 
     */
    public CreateTemplate createCreateTemplate() {
        return new CreateTemplate();
    }

    /**
     * Create an instance of {@link UpdateGeneralViewResponse }
     * 
     */
    public UpdateGeneralViewResponse createUpdateGeneralViewResponse() {
        return new UpdateGeneralViewResponse();
    }

    /**
     * Create an instance of {@link GetPossibleSpecialChildrenNoRecursive }
     * 
     */
    public GetPossibleSpecialChildrenNoRecursive createGetPossibleSpecialChildrenNoRecursive() {
        return new GetPossibleSpecialChildrenNoRecursive();
    }

    /**
     * Create an instance of {@link GetProjectsWithFilter }
     * 
     */
    public GetProjectsWithFilter createGetProjectsWithFilter() {
        return new GetProjectsWithFilter();
    }

    /**
     * Create an instance of {@link GetTemplatesForClassResponse }
     * 
     */
    public GetTemplatesForClassResponse createGetTemplatesForClassResponse() {
        return new GetTemplatesForClassResponse();
    }

    /**
     * Create an instance of {@link GetProject }
     * 
     */
    public GetProject createGetProject() {
        return new GetProject();
    }

    /**
     * Create an instance of {@link UpdateProcessDefinitionResponse }
     * 
     */
    public UpdateProcessDefinitionResponse createUpdateProcessDefinitionResponse() {
        return new UpdateProcessDefinitionResponse();
    }

    /**
     * Create an instance of {@link CreateSDHTransportLinkResponse }
     * 
     */
    public CreateSDHTransportLinkResponse createCreateSDHTransportLinkResponse() {
        return new CreateSDHTransportLinkResponse();
    }

    /**
     * Create an instance of {@link CreateActivityResponse }
     * 
     */
    public CreateActivityResponse createCreateActivityResponse() {
        return new CreateActivityResponse();
    }

    /**
     * Create an instance of {@link SubscribeUserToTaskResponse }
     * 
     */
    public SubscribeUserToTaskResponse createSubscribeUserToTaskResponse() {
        return new SubscribeUserToTaskResponse();
    }

    /**
     * Create an instance of {@link GetConfigurationVariablesWithPrefix }
     * 
     */
    public GetConfigurationVariablesWithPrefix createGetConfigurationVariablesWithPrefix() {
        return new GetConfigurationVariablesWithPrefix();
    }

    /**
     * Create an instance of {@link DeleteSynchronizationGroup }
     * 
     */
    public DeleteSynchronizationGroup createDeleteSynchronizationGroup() {
        return new DeleteSynchronizationGroup();
    }

    /**
     * Create an instance of {@link RelateObjectsToContractResponse }
     * 
     */
    public RelateObjectsToContractResponse createRelateObjectsToContractResponse() {
        return new RelateObjectsToContractResponse();
    }

    /**
     * Create an instance of {@link DeleteServiceResponse }
     * 
     */
    public DeleteServiceResponse createDeleteServiceResponse() {
        return new DeleteServiceResponse();
    }

    /**
     * Create an instance of {@link UpdateTaskNotificationTypeResponse }
     * 
     */
    public UpdateTaskNotificationTypeResponse createUpdateTaskNotificationTypeResponse() {
        return new UpdateTaskNotificationTypeResponse();
    }

    /**
     * Create an instance of {@link AddPossibleSpecialChildrenWithIdResponse }
     * 
     */
    public AddPossibleSpecialChildrenWithIdResponse createAddPossibleSpecialChildrenWithIdResponse() {
        return new AddPossibleSpecialChildrenWithIdResponse();
    }

    /**
     * Create an instance of {@link GetDeviceLayoutsResponse }
     * 
     */
    public GetDeviceLayoutsResponse createGetDeviceLayoutsResponse() {
        return new GetDeviceLayoutsResponse();
    }

    /**
     * Create an instance of {@link MoveSpecialObjects }
     * 
     */
    public MoveSpecialObjects createMoveSpecialObjects() {
        return new MoveSpecialObjects();
    }

    /**
     * Create an instance of {@link GetAllContracts }
     * 
     */
    public GetAllContracts createGetAllContracts() {
        return new GetAllContracts();
    }

    /**
     * Create an instance of {@link GetE2EView }
     * 
     */
    public GetE2EView createGetE2EView() {
        return new GetE2EView();
    }

    /**
     * Create an instance of {@link CreateListTypeItemRelatedViewResponse }
     * 
     */
    public CreateListTypeItemRelatedViewResponse createCreateListTypeItemRelatedViewResponse() {
        return new CreateListTypeItemRelatedViewResponse();
    }

    /**
     * Create an instance of {@link CreateSpecialObjectResponse }
     * 
     */
    public CreateSpecialObjectResponse createCreateSpecialObjectResponse() {
        return new CreateSpecialObjectResponse();
    }

    /**
     * Create an instance of {@link CreateConfigurationVariablesPoolResponse }
     * 
     */
    public CreateConfigurationVariablesPoolResponse createCreateConfigurationVariablesPoolResponse() {
        return new CreateConfigurationVariablesPoolResponse();
    }

    /**
     * Create an instance of {@link GetObjectChildrenResponse }
     * 
     */
    public GetObjectChildrenResponse createGetObjectChildrenResponse() {
        return new GetObjectChildrenResponse();
    }

    /**
     * Create an instance of {@link CreateQueryResponse }
     * 
     */
    public CreateQueryResponse createCreateQueryResponse() {
        return new CreateQueryResponse();
    }

    /**
     * Create an instance of {@link GetAttributeForClassWithIdResponse }
     * 
     */
    public GetAttributeForClassWithIdResponse createGetAttributeForClassWithIdResponse() {
        return new GetAttributeForClassWithIdResponse();
    }

    /**
     * Create an instance of {@link DeleteSDHTransportLink }
     * 
     */
    public DeleteSDHTransportLink createDeleteSDHTransportLink() {
        return new DeleteSDHTransportLink();
    }

    /**
     * Create an instance of {@link GetServicePoolResponse }
     * 
     */
    public GetServicePoolResponse createGetServicePoolResponse() {
        return new GetServicePoolResponse();
    }

    /**
     * Create an instance of {@link UpdateOSPView }
     * 
     */
    public UpdateOSPView createUpdateOSPView() {
        return new UpdateOSPView();
    }

    /**
     * Create an instance of {@link GetPoolItems }
     * 
     */
    public GetPoolItems createGetPoolItems() {
        return new GetPoolItems();
    }

    /**
     * Create an instance of {@link GetSpecialAttribute }
     * 
     */
    public GetSpecialAttribute createGetSpecialAttribute() {
        return new GetSpecialAttribute();
    }

    /**
     * Create an instance of {@link GetCurrentJobsResponse }
     * 
     */
    public GetCurrentJobsResponse createGetCurrentJobsResponse() {
        return new GetCurrentJobsResponse();
    }

    /**
     * Create an instance of {@link GetTaskResponse }
     * 
     */
    public GetTaskResponse createGetTaskResponse() {
        return new GetTaskResponse();
    }

    /**
     * Create an instance of {@link UpdateFavoritesFolder }
     * 
     */
    public UpdateFavoritesFolder createUpdateFavoritesFolder() {
        return new UpdateFavoritesFolder();
    }

    /**
     * Create an instance of {@link GetLogicalLinkDetails }
     * 
     */
    public GetLogicalLinkDetails createGetLogicalLinkDetails() {
        return new GetLogicalLinkDetails();
    }

    /**
     * Create an instance of {@link GetPoolsInObject }
     * 
     */
    public GetPoolsInObject createGetPoolsInObject() {
        return new GetPoolsInObject();
    }

    /**
     * Create an instance of {@link GetObject }
     * 
     */
    public GetObject createGetObject() {
        return new GetObject();
    }

    /**
     * Create an instance of {@link GetContractResponse }
     * 
     */
    public GetContractResponse createGetContractResponse() {
        return new GetContractResponse();
    }

    /**
     * Create an instance of {@link CreatePoolInWarehouse }
     * 
     */
    public CreatePoolInWarehouse createCreatePoolInWarehouse() {
        return new CreatePoolInWarehouse();
    }

    /**
     * Create an instance of {@link UpdateSynchronizationGroup }
     * 
     */
    public UpdateSynchronizationGroup createUpdateSynchronizationGroup() {
        return new UpdateSynchronizationGroup();
    }

    /**
     * Create an instance of {@link GetCustomerPools }
     * 
     */
    public GetCustomerPools createGetCustomerPools() {
        return new GetCustomerPools();
    }

    /**
     * Create an instance of {@link GetListTypeItemRelatedViewResponse }
     * 
     */
    public GetListTypeItemRelatedViewResponse createGetListTypeItemRelatedViewResponse() {
        return new GetListTypeItemRelatedViewResponse();
    }

    /**
     * Create an instance of {@link GetFavoritesFolder }
     * 
     */
    public GetFavoritesFolder createGetFavoritesFolder() {
        return new GetFavoritesFolder();
    }

    /**
     * Create an instance of {@link DeleteSparePoolResponse }
     * 
     */
    public DeleteSparePoolResponse createDeleteSparePoolResponse() {
        return new DeleteSparePoolResponse();
    }

    /**
     * Create an instance of {@link CreateService }
     * 
     */
    public CreateService createCreateService() {
        return new CreateService();
    }

    /**
     * Create an instance of {@link DeleteAttributeForClassWithId }
     * 
     */
    public DeleteAttributeForClassWithId createDeleteAttributeForClassWithId() {
        return new DeleteAttributeForClassWithId();
    }

    /**
     * Create an instance of {@link GetUpstreamClassHierarchyResponse }
     * 
     */
    public GetUpstreamClassHierarchyResponse createGetUpstreamClassHierarchyResponse() {
        return new GetUpstreamClassHierarchyResponse();
    }

    /**
     * Create an instance of {@link GetSubnetPoolsResponse }
     * 
     */
    public GetSubnetPoolsResponse createGetSubnetPoolsResponse() {
        return new GetSubnetPoolsResponse();
    }

    /**
     * Create an instance of {@link ReleaseSubnetFromVRFResponse }
     * 
     */
    public ReleaseSubnetFromVRFResponse createReleaseSubnetFromVRFResponse() {
        return new ReleaseSubnetFromVRFResponse();
    }

    /**
     * Create an instance of {@link CreateProxyPool }
     * 
     */
    public CreateProxyPool createCreateProxyPool() {
        return new CreateProxyPool();
    }

    /**
     * Create an instance of {@link GetConfigurationVariablesInPool }
     * 
     */
    public GetConfigurationVariablesInPool createGetConfigurationVariablesInPool() {
        return new GetConfigurationVariablesInPool();
    }

    /**
     * Create an instance of {@link UpdateProjectPool }
     * 
     */
    public UpdateProjectPool createUpdateProjectPool() {
        return new UpdateProjectPool();
    }

    /**
     * Create an instance of {@link GetObjectChildrenForClassWithIdResponse }
     * 
     */
    public GetObjectChildrenForClassWithIdResponse createGetObjectChildrenForClassWithIdResponse() {
        return new GetObjectChildrenForClassWithIdResponse();
    }

    /**
     * Create an instance of {@link ConnectPhysicalLinksResponse }
     * 
     */
    public ConnectPhysicalLinksResponse createConnectPhysicalLinksResponse() {
        return new ConnectPhysicalLinksResponse();
    }

    /**
     * Create an instance of {@link ReleaseObjectFromServiceResponse }
     * 
     */
    public ReleaseObjectFromServiceResponse createReleaseObjectFromServiceResponse() {
        return new ReleaseObjectFromServiceResponse();
    }

    /**
     * Create an instance of {@link GetObjectsOfClassLightResponse }
     * 
     */
    public GetObjectsOfClassLightResponse createGetObjectsOfClassLightResponse() {
        return new GetObjectsOfClassLightResponse();
    }

    /**
     * Create an instance of {@link GetParents }
     * 
     */
    public GetParents createGetParents() {
        return new GetParents();
    }

    /**
     * Create an instance of {@link CreateClassLevelReportResponse }
     * 
     */
    public CreateClassLevelReportResponse createCreateClassLevelReportResponse() {
        return new CreateClassLevelReportResponse();
    }

    /**
     * Create an instance of {@link CreateTemplateSpecialElement }
     * 
     */
    public CreateTemplateSpecialElement createCreateTemplateSpecialElement() {
        return new CreateTemplateSpecialElement();
    }

    /**
     * Create an instance of {@link CopyTemplateElementsResponse }
     * 
     */
    public CopyTemplateElementsResponse createCopyTemplateElementsResponse() {
        return new CopyTemplateElementsResponse();
    }

    /**
     * Create an instance of {@link ItOverlapsResponse }
     * 
     */
    public ItOverlapsResponse createItOverlapsResponse() {
        return new ItOverlapsResponse();
    }

    /**
     * Create an instance of {@link UpdateTaskScheduleResponse }
     * 
     */
    public UpdateTaskScheduleResponse createUpdateTaskScheduleResponse() {
        return new UpdateTaskScheduleResponse();
    }

    /**
     * Create an instance of {@link GetParentOfClass }
     * 
     */
    public GetParentOfClass createGetParentOfClass() {
        return new GetParentOfClass();
    }

    /**
     * Create an instance of {@link UpdateObjectRelatedView }
     * 
     */
    public UpdateObjectRelatedView createUpdateObjectRelatedView() {
        return new UpdateObjectRelatedView();
    }

    /**
     * Create an instance of {@link MoveObjectsToWarehousePool }
     * 
     */
    public MoveObjectsToWarehousePool createMoveObjectsToWarehousePool() {
        return new MoveObjectsToWarehousePool();
    }

    /**
     * Create an instance of {@link RelateSubnetToVrfResponse }
     * 
     */
    public RelateSubnetToVrfResponse createRelateSubnetToVrfResponse() {
        return new RelateSubnetToVrfResponse();
    }

    /**
     * Create an instance of {@link CreateInventoryLevelReportResponse }
     * 
     */
    public CreateInventoryLevelReportResponse createCreateInventoryLevelReportResponse() {
        return new CreateInventoryLevelReportResponse();
    }

    /**
     * Create an instance of {@link GetService }
     * 
     */
    public GetService createGetService() {
        return new GetService();
    }

    /**
     * Create an instance of {@link GetProjectsWithFilterResponse }
     * 
     */
    public GetProjectsWithFilterResponse createGetProjectsWithFilterResponse() {
        return new GetProjectsWithFilterResponse();
    }

    /**
     * Create an instance of {@link AssociatesPhysicalNodeToWarehouse }
     * 
     */
    public AssociatesPhysicalNodeToWarehouse createAssociatesPhysicalNodeToWarehouse() {
        return new AssociatesPhysicalNodeToWarehouse();
    }

    /**
     * Create an instance of {@link UpdateSyncDataSourceConfigurationResponse }
     * 
     */
    public UpdateSyncDataSourceConfigurationResponse createUpdateSyncDataSourceConfigurationResponse() {
        return new UpdateSyncDataSourceConfigurationResponse();
    }

    /**
     * Create an instance of {@link GetTask }
     * 
     */
    public GetTask createGetTask() {
        return new GetTask();
    }

    /**
     * Create an instance of {@link GetSubnetsResponse }
     * 
     */
    public GetSubnetsResponse createGetSubnetsResponse() {
        return new GetSubnetsResponse();
    }

    /**
     * Create an instance of {@link GetGroupsForUserResponse }
     * 
     */
    public GetGroupsForUserResponse createGetGroupsForUserResponse() {
        return new GetGroupsForUserResponse();
    }

    /**
     * Create an instance of {@link MoveSyncDataSourceConfigurationResponse }
     * 
     */
    public MoveSyncDataSourceConfigurationResponse createMoveSyncDataSourceConfigurationResponse() {
        return new MoveSyncDataSourceConfigurationResponse();
    }

    /**
     * Create an instance of {@link ValidateSavedE2EViewResponse }
     * 
     */
    public ValidateSavedE2EViewResponse createValidateSavedE2EViewResponse() {
        return new ValidateSavedE2EViewResponse();
    }

    /**
     * Create an instance of {@link GetPhysicalTree }
     * 
     */
    public GetPhysicalTree createGetPhysicalTree() {
        return new GetPhysicalTree();
    }

    /**
     * Create an instance of {@link RemoveUserFromGroup }
     * 
     */
    public RemoveUserFromGroup createRemoveUserFromGroup() {
        return new RemoveUserFromGroup();
    }

    /**
     * Create an instance of {@link GetPossibleChildrenResponse }
     * 
     */
    public GetPossibleChildrenResponse createGetPossibleChildrenResponse() {
        return new GetPossibleChildrenResponse();
    }

    /**
     * Create an instance of {@link RemoveObjectsFromFavoritesFolderResponse }
     * 
     */
    public RemoveObjectsFromFavoritesFolderResponse createRemoveObjectsFromFavoritesFolderResponse() {
        return new RemoveObjectsFromFavoritesFolderResponse();
    }

    /**
     * Create an instance of {@link GetFile }
     * 
     */
    public GetFile createGetFile() {
        return new GetFile();
    }

    /**
     * Create an instance of {@link ReleasePortFromIP }
     * 
     */
    public ReleasePortFromIP createReleasePortFromIP() {
        return new ReleasePortFromIP();
    }

    /**
     * Create an instance of {@link IsSubclassOfResponse }
     * 
     */
    public IsSubclassOfResponse createIsSubclassOfResponse() {
        return new IsSubclassOfResponse();
    }

    /**
     * Create an instance of {@link GetConfigurationVariable }
     * 
     */
    public GetConfigurationVariable createGetConfigurationVariable() {
        return new GetConfigurationVariable();
    }

    /**
     * Create an instance of {@link GetSDHTransportLinkStructureResponse }
     * 
     */
    public GetSDHTransportLinkStructureResponse createGetSDHTransportLinkStructureResponse() {
        return new GetSDHTransportLinkStructureResponse();
    }

    /**
     * Create an instance of {@link ConnectPhysicalContainers }
     * 
     */
    public ConnectPhysicalContainers createConnectPhysicalContainers() {
        return new ConnectPhysicalContainers();
    }

    /**
     * Create an instance of {@link GetSpecialChildrenOfClassLightResponse }
     * 
     */
    public GetSpecialChildrenOfClassLightResponse createGetSpecialChildrenOfClassLightResponse() {
        return new GetSpecialChildrenOfClassLightResponse();
    }

    /**
     * Create an instance of {@link GetIPAddress }
     * 
     */
    public GetIPAddress createGetIPAddress() {
        return new GetIPAddress();
    }

    /**
     * Create an instance of {@link GetSDHContainerLinkStructure }
     * 
     */
    public GetSDHContainerLinkStructure createGetSDHContainerLinkStructure() {
        return new GetSDHContainerLinkStructure();
    }

    /**
     * Create an instance of {@link GetDeviceLayoutStructure }
     * 
     */
    public GetDeviceLayoutStructure createGetDeviceLayoutStructure() {
        return new GetDeviceLayoutStructure();
    }

    /**
     * Create an instance of {@link GetObjectsWithFilter }
     * 
     */
    public GetObjectsWithFilter createGetObjectsWithFilter() {
        return new GetObjectsWithFilter();
    }

    /**
     * Create an instance of {@link GetOSPViews }
     * 
     */
    public GetOSPViews createGetOSPViews() {
        return new GetOSPViews();
    }

    /**
     * Create an instance of {@link GetProjectsInPool }
     * 
     */
    public GetProjectsInPool createGetProjectsInPool() {
        return new GetProjectsInPool();
    }

    /**
     * Create an instance of {@link AddPossibleSpecialChildrenResponse }
     * 
     */
    public AddPossibleSpecialChildrenResponse createAddPossibleSpecialChildrenResponse() {
        return new AddPossibleSpecialChildrenResponse();
    }

    /**
     * Create an instance of {@link CreateValidatorDefinition }
     * 
     */
    public CreateValidatorDefinition createCreateValidatorDefinition() {
        return new CreateValidatorDefinition();
    }

    /**
     * Create an instance of {@link GetMPLSLinkEndpoints }
     * 
     */
    public GetMPLSLinkEndpoints createGetMPLSLinkEndpoints() {
        return new GetMPLSLinkEndpoints();
    }

    /**
     * Create an instance of {@link CreatePoolItem }
     * 
     */
    public CreatePoolItem createCreatePoolItem() {
        return new CreatePoolItem();
    }

    /**
     * Create an instance of {@link GetGeneralActivityAuditTrail }
     * 
     */
    public GetGeneralActivityAuditTrail createGetGeneralActivityAuditTrail() {
        return new GetGeneralActivityAuditTrail();
    }

    /**
     * Create an instance of {@link GetAllServicesResponse }
     * 
     */
    public GetAllServicesResponse createGetAllServicesResponse() {
        return new GetAllServicesResponse();
    }

    /**
     * Create an instance of {@link UpdateSynchronizationGroupResponse }
     * 
     */
    public UpdateSynchronizationGroupResponse createUpdateSynchronizationGroupResponse() {
        return new UpdateSynchronizationGroupResponse();
    }

    /**
     * Create an instance of {@link GetOSPViewResponse }
     * 
     */
    public GetOSPViewResponse createGetOSPViewResponse() {
        return new GetOSPViewResponse();
    }

    /**
     * Create an instance of {@link DeleteProjectPoolResponse }
     * 
     */
    public DeleteProjectPoolResponse createDeleteProjectPoolResponse() {
        return new DeleteProjectPoolResponse();
    }

    /**
     * Create an instance of {@link CreateAttributeResponse }
     * 
     */
    public CreateAttributeResponse createCreateAttributeResponse() {
        return new CreateAttributeResponse();
    }

    /**
     * Create an instance of {@link GetServicesInPoolResponse }
     * 
     */
    public GetServicesInPoolResponse createGetServicesInPoolResponse() {
        return new GetServicesInPoolResponse();
    }

    /**
     * Create an instance of {@link UpdateContract }
     * 
     */
    public UpdateContract createUpdateContract() {
        return new UpdateContract();
    }

    /**
     * Create an instance of {@link CreateBulkSpecialObjectsResponse }
     * 
     */
    public CreateBulkSpecialObjectsResponse createCreateBulkSpecialObjectsResponse() {
        return new CreateBulkSpecialObjectsResponse();
    }

    /**
     * Create an instance of {@link DeleteSDHContainerLink }
     * 
     */
    public DeleteSDHContainerLink createDeleteSDHContainerLink() {
        return new DeleteSDHContainerLink();
    }

    /**
     * Create an instance of {@link SetAttributePropertiesForClassWithIdResponse }
     * 
     */
    public SetAttributePropertiesForClassWithIdResponse createSetAttributePropertiesForClassWithIdResponse() {
        return new SetAttributePropertiesForClassWithIdResponse();
    }

    /**
     * Create an instance of {@link GetSyncDataSourceConfigurationResponse }
     * 
     */
    public GetSyncDataSourceConfigurationResponse createGetSyncDataSourceConfigurationResponse() {
        return new GetSyncDataSourceConfigurationResponse();
    }

    /**
     * Create an instance of {@link KillJob }
     * 
     */
    public KillJob createKillJob() {
        return new KillJob();
    }

    /**
     * Create an instance of {@link DeleteMPLSLink }
     * 
     */
    public DeleteMPLSLink createDeleteMPLSLink() {
        return new DeleteMPLSLink();
    }

    /**
     * Create an instance of {@link UpdateServicePool }
     * 
     */
    public UpdateServicePool createUpdateServicePool() {
        return new UpdateServicePool();
    }

    /**
     * Create an instance of {@link BulkUploadResponse }
     * 
     */
    public BulkUploadResponse createBulkUploadResponse() {
        return new BulkUploadResponse();
    }

    /**
     * Create an instance of {@link CreateGroupResponse }
     * 
     */
    public CreateGroupResponse createCreateGroupResponse() {
        return new CreateGroupResponse();
    }

    /**
     * Create an instance of {@link GetSynchronizationGroup }
     * 
     */
    public GetSynchronizationGroup createGetSynchronizationGroup() {
        return new GetSynchronizationGroup();
    }

    /**
     * Create an instance of {@link UpdateTemplateElementResponse }
     * 
     */
    public UpdateTemplateElementResponse createUpdateTemplateElementResponse() {
        return new UpdateTemplateElementResponse();
    }

    /**
     * Create an instance of {@link CreatePoolInObjectResponse }
     * 
     */
    public CreatePoolInObjectResponse createCreatePoolInObjectResponse() {
        return new CreatePoolInObjectResponse();
    }

    /**
     * Create an instance of {@link GetPossibleSpecialChildrenResponse }
     * 
     */
    public GetPossibleSpecialChildrenResponse createGetPossibleSpecialChildrenResponse() {
        return new GetPossibleSpecialChildrenResponse();
    }

    /**
     * Create an instance of {@link GetTemplateElementChildrenResponse }
     * 
     */
    public GetTemplateElementChildrenResponse createGetTemplateElementChildrenResponse() {
        return new GetTemplateElementChildrenResponse();
    }

    /**
     * Create an instance of {@link CreateTaskResponse }
     * 
     */
    public CreateTaskResponse createCreateTaskResponse() {
        return new CreateTaskResponse();
    }

    /**
     * Create an instance of {@link MovePoolItemToPool }
     * 
     */
    public MovePoolItemToPool createMovePoolItemToPool() {
        return new MovePoolItemToPool();
    }

    /**
     * Create an instance of {@link RemoteProcessDefinition }
     * 
     */
    public RemoteProcessDefinition createRemoteProcessDefinition() {
        return new RemoteProcessDefinition();
    }

    /**
     * Create an instance of {@link CreateProxy }
     * 
     */
    public CreateProxy createCreateProxy() {
        return new CreateProxy();
    }

    /**
     * Create an instance of {@link GetSynchronizationGroupsResponse }
     * 
     */
    public GetSynchronizationGroupsResponse createGetSynchronizationGroupsResponse() {
        return new GetSynchronizationGroupsResponse();
    }

    /**
     * Create an instance of {@link GetProjectsRelatedToObjectResponse }
     * 
     */
    public GetProjectsRelatedToObjectResponse createGetProjectsRelatedToObjectResponse() {
        return new GetProjectsRelatedToObjectResponse();
    }

    /**
     * Create an instance of {@link CreateContract }
     * 
     */
    public CreateContract createCreateContract() {
        return new CreateContract();
    }

    /**
     * Create an instance of {@link DeleteFavoritesFoldersResponse }
     * 
     */
    public DeleteFavoritesFoldersResponse createDeleteFavoritesFoldersResponse() {
        return new DeleteFavoritesFoldersResponse();
    }

    /**
     * Create an instance of {@link ConnectMirrorMultiplePortResponse }
     * 
     */
    public ConnectMirrorMultiplePortResponse createConnectMirrorMultiplePortResponse() {
        return new ConnectMirrorMultiplePortResponse();
    }

    /**
     * Create an instance of {@link GetClassLevelReports }
     * 
     */
    public GetClassLevelReports createGetClassLevelReports() {
        return new GetClassLevelReports();
    }

    /**
     * Create an instance of {@link DeleteObjectResponse }
     * 
     */
    public DeleteObjectResponse createDeleteObjectResponse() {
        return new DeleteObjectResponse();
    }

    /**
     * Create an instance of {@link GetProjectActivitiesResponse }
     * 
     */
    public GetProjectActivitiesResponse createGetProjectActivitiesResponse() {
        return new GetProjectActivitiesResponse();
    }

    /**
     * Create an instance of {@link CreateBusinessRuleResponse }
     * 
     */
    public CreateBusinessRuleResponse createCreateBusinessRuleResponse() {
        return new CreateBusinessRuleResponse();
    }

    /**
     * Create an instance of {@link RelateObjectsToService }
     * 
     */
    public RelateObjectsToService createRelateObjectsToService() {
        return new RelateObjectsToService();
    }

    /**
     * Create an instance of {@link DeleteGeneralView }
     * 
     */
    public DeleteGeneralView createDeleteGeneralView() {
        return new DeleteGeneralView();
    }

    /**
     * Create an instance of {@link AddPossibleSpecialChildren }
     * 
     */
    public AddPossibleSpecialChildren createAddPossibleSpecialChildren() {
        return new AddPossibleSpecialChildren();
    }

    /**
     * Create an instance of {@link CreateWarehouseResponse }
     * 
     */
    public CreateWarehouseResponse createCreateWarehouseResponse() {
        return new CreateWarehouseResponse();
    }

    /**
     * Create an instance of {@link UpdateTaskPropertiesResponse }
     * 
     */
    public UpdateTaskPropertiesResponse createUpdateTaskPropertiesResponse() {
        return new UpdateTaskPropertiesResponse();
    }

    /**
     * Create an instance of {@link CreateTemplateElementResponse }
     * 
     */
    public CreateTemplateElementResponse createCreateTemplateElementResponse() {
        return new CreateTemplateElementResponse();
    }

    /**
     * Create an instance of {@link GetContractPoolResponse }
     * 
     */
    public GetContractPoolResponse createGetContractPoolResponse() {
        return new GetContractPoolResponse();
    }

    /**
     * Create an instance of {@link DownloadBulkLoadLogResponse }
     * 
     */
    public DownloadBulkLoadLogResponse createDownloadBulkLoadLogResponse() {
        return new DownloadBulkLoadLogResponse();
    }

    /**
     * Create an instance of {@link CopySpecialObjects }
     * 
     */
    public CopySpecialObjects createCopySpecialObjects() {
        return new CopySpecialObjects();
    }

    /**
     * Create an instance of {@link UpdateCustomerPool }
     * 
     */
    public UpdateCustomerPool createUpdateCustomerPool() {
        return new UpdateCustomerPool();
    }

    /**
     * Create an instance of {@link GetWarehousesInPoolResponse }
     * 
     */
    public GetWarehousesInPoolResponse createGetWarehousesInPoolResponse() {
        return new GetWarehousesInPoolResponse();
    }

    /**
     * Create an instance of {@link RelateObjectToContract }
     * 
     */
    public RelateObjectToContract createRelateObjectToContract() {
        return new RelateObjectToContract();
    }

    /**
     * Create an instance of {@link GetObjectsInSparePool }
     * 
     */
    public GetObjectsInSparePool createGetObjectsInSparePool() {
        return new GetObjectsInSparePool();
    }

    /**
     * Create an instance of {@link GetPhysicalTreeResponse }
     * 
     */
    public GetPhysicalTreeResponse createGetPhysicalTreeResponse() {
        return new GetPhysicalTreeResponse();
    }

    /**
     * Create an instance of {@link GetProjectActivities }
     * 
     */
    public GetProjectActivities createGetProjectActivities() {
        return new GetProjectActivities();
    }

    /**
     * Create an instance of {@link GetBusinessRules }
     * 
     */
    public GetBusinessRules createGetBusinessRules() {
        return new GetBusinessRules();
    }

    /**
     * Create an instance of {@link UpdateOSPViewResponse }
     * 
     */
    public UpdateOSPViewResponse createUpdateOSPViewResponse() {
        return new UpdateOSPViewResponse();
    }

    /**
     * Create an instance of {@link GetFavoritesFoldersForObject }
     * 
     */
    public GetFavoritesFoldersForObject createGetFavoritesFoldersForObject() {
        return new GetFavoritesFoldersForObject();
    }

    /**
     * Create an instance of {@link GetObjectsRelatedToService }
     * 
     */
    public GetObjectsRelatedToService createGetObjectsRelatedToService() {
        return new GetObjectsRelatedToService();
    }

    /**
     * Create an instance of {@link GetLinkConnectedToPort }
     * 
     */
    public GetLinkConnectedToPort createGetLinkConnectedToPort() {
        return new GetLinkConnectedToPort();
    }

    /**
     * Create an instance of {@link GetTasksForUserResponse }
     * 
     */
    public GetTasksForUserResponse createGetTasksForUserResponse() {
        return new GetTasksForUserResponse();
    }

    /**
     * Create an instance of {@link AssociatesPhysicalNodeToWarehouseResponse }
     * 
     */
    public AssociatesPhysicalNodeToWarehouseResponse createAssociatesPhysicalNodeToWarehouseResponse() {
        return new AssociatesPhysicalNodeToWarehouseResponse();
    }

    /**
     * Create an instance of {@link CreateProcessInstance }
     * 
     */
    public CreateProcessInstance createCreateProcessInstance() {
        return new CreateProcessInstance();
    }

    /**
     * Create an instance of {@link CreateSessionResponse }
     * 
     */
    public CreateSessionResponse createCreateSessionResponse() {
        return new CreateSessionResponse();
    }

    /**
     * Create an instance of {@link GetPossibleChildren }
     * 
     */
    public GetPossibleChildren createGetPossibleChildren() {
        return new GetPossibleChildren();
    }

    /**
     * Create an instance of {@link ConnectMplsLinkResponse }
     * 
     */
    public ConnectMplsLinkResponse createConnectMplsLinkResponse() {
        return new ConnectMplsLinkResponse();
    }

    /**
     * Create an instance of {@link FindSDHRoutesUsingContainerLinks }
     * 
     */
    public FindSDHRoutesUsingContainerLinks createFindSDHRoutesUsingContainerLinks() {
        return new FindSDHRoutesUsingContainerLinks();
    }

    /**
     * Create an instance of {@link CopySyncDataSourceConfiguration }
     * 
     */
    public CopySyncDataSourceConfiguration createCopySyncDataSourceConfiguration() {
        return new CopySyncDataSourceConfiguration();
    }

    /**
     * Create an instance of {@link DeleteAttributeForClassWithIdResponse }
     * 
     */
    public DeleteAttributeForClassWithIdResponse createDeleteAttributeForClassWithIdResponse() {
        return new DeleteAttributeForClassWithIdResponse();
    }

    /**
     * Create an instance of {@link GetMandatoryAttributesInClass }
     * 
     */
    public GetMandatoryAttributesInClass createGetMandatoryAttributesInClass() {
        return new GetMandatoryAttributesInClass();
    }

    /**
     * Create an instance of {@link RelateSubnetToVlanResponse }
     * 
     */
    public RelateSubnetToVlanResponse createRelateSubnetToVlanResponse() {
        return new RelateSubnetToVlanResponse();
    }

    /**
     * Create an instance of {@link SaveQuery }
     * 
     */
    public SaveQuery createSaveQuery() {
        return new SaveQuery();
    }

    /**
     * Create an instance of {@link CreateBulkObjects }
     * 
     */
    public CreateBulkObjects createCreateBulkObjects() {
        return new CreateBulkObjects();
    }

    /**
     * Create an instance of {@link DeleteUsers }
     * 
     */
    public DeleteUsers createDeleteUsers() {
        return new DeleteUsers();
    }

    /**
     * Create an instance of {@link GetChildrenOfClassLightResponse }
     * 
     */
    public GetChildrenOfClassLightResponse createGetChildrenOfClassLightResponse() {
        return new GetChildrenOfClassLightResponse();
    }

    /**
     * Create an instance of {@link DeleteCustomerPoolResponse }
     * 
     */
    public DeleteCustomerPoolResponse createDeleteCustomerPoolResponse() {
        return new DeleteCustomerPoolResponse();
    }

    /**
     * Create an instance of {@link GetGroups }
     * 
     */
    public GetGroups createGetGroups() {
        return new GetGroups();
    }

    /**
     * Create an instance of {@link GetAffectedServicesResponse }
     * 
     */
    public GetAffectedServicesResponse createGetAffectedServicesResponse() {
        return new GetAffectedServicesResponse();
    }

    /**
     * Create an instance of {@link CloseSessionResponse }
     * 
     */
    public CloseSessionResponse createCloseSessionResponse() {
        return new CloseSessionResponse();
    }

    /**
     * Create an instance of {@link CreatePhysicalConnectionsResponse }
     * 
     */
    public CreatePhysicalConnectionsResponse createCreatePhysicalConnectionsResponse() {
        return new CreatePhysicalConnectionsResponse();
    }

    /**
     * Create an instance of {@link DeleteListTypeItemRelatedViewResponse }
     * 
     */
    public DeleteListTypeItemRelatedViewResponse createDeleteListTypeItemRelatedViewResponse() {
        return new DeleteListTypeItemRelatedViewResponse();
    }

    /**
     * Create an instance of {@link UpdateActivityResponse }
     * 
     */
    public UpdateActivityResponse createUpdateActivityResponse() {
        return new UpdateActivityResponse();
    }

    /**
     * Create an instance of {@link GetConfigurationVariableResponse }
     * 
     */
    public GetConfigurationVariableResponse createGetConfigurationVariableResponse() {
        return new GetConfigurationVariableResponse();
    }

    /**
     * Create an instance of {@link RemoveUserFromGroupResponse }
     * 
     */
    public RemoveUserFromGroupResponse createRemoveUserFromGroupResponse() {
        return new RemoveUserFromGroupResponse();
    }

    /**
     * Create an instance of {@link DeleteSubnetsResponse }
     * 
     */
    public DeleteSubnetsResponse createDeleteSubnetsResponse() {
        return new DeleteSubnetsResponse();
    }

    /**
     * Create an instance of {@link DeleteSubnetPools }
     * 
     */
    public DeleteSubnetPools createDeleteSubnetPools() {
        return new DeleteSubnetPools();
    }

    /**
     * Create an instance of {@link DeleteProject }
     * 
     */
    public DeleteProject createDeleteProject() {
        return new DeleteProject();
    }

    /**
     * Create an instance of {@link GetListTypeItemResponse }
     * 
     */
    public GetListTypeItemResponse createGetListTypeItemResponse() {
        return new GetListTypeItemResponse();
    }

    /**
     * Create an instance of {@link GetFavoritesFoldersForUser }
     * 
     */
    public GetFavoritesFoldersForUser createGetFavoritesFoldersForUser() {
        return new GetFavoritesFoldersForUser();
    }

    /**
     * Create an instance of {@link GetListTypeItemRelatedView }
     * 
     */
    public GetListTypeItemRelatedView createGetListTypeItemRelatedView() {
        return new GetListTypeItemRelatedView();
    }

    /**
     * Create an instance of {@link GetReportResponse }
     * 
     */
    public GetReportResponse createGetReportResponse() {
        return new GetReportResponse();
    }

    /**
     * Create an instance of {@link ReleaseObjectFromProject }
     * 
     */
    public ReleaseObjectFromProject createReleaseObjectFromProject() {
        return new ReleaseObjectFromProject();
    }

    /**
     * Create an instance of {@link LaunchAutomatedSynchronizationTaskResponse }
     * 
     */
    public LaunchAutomatedSynchronizationTaskResponse createLaunchAutomatedSynchronizationTaskResponse() {
        return new LaunchAutomatedSynchronizationTaskResponse();
    }

    /**
     * Create an instance of {@link MoveObjectsResponse }
     * 
     */
    public MoveObjectsResponse createMoveObjectsResponse() {
        return new MoveObjectsResponse();
    }

    /**
     * Create an instance of {@link ItOverlaps }
     * 
     */
    public ItOverlaps createItOverlaps() {
        return new ItOverlaps();
    }

    /**
     * Create an instance of {@link GetClassLevelReportsResponse }
     * 
     */
    public GetClassLevelReportsResponse createGetClassLevelReportsResponse() {
        return new GetClassLevelReportsResponse();
    }

    /**
     * Create an instance of {@link GetContacts }
     * 
     */
    public GetContacts createGetContacts() {
        return new GetContacts();
    }

    /**
     * Create an instance of {@link UpdateProcessDefinition }
     * 
     */
    public UpdateProcessDefinition createUpdateProcessDefinition() {
        return new UpdateProcessDefinition();
    }

    /**
     * Create an instance of {@link UpdateReportParameters }
     * 
     */
    public UpdateReportParameters createUpdateReportParameters() {
        return new UpdateReportParameters();
    }

    /**
     * Create an instance of {@link UpdateActivity }
     * 
     */
    public UpdateActivity createUpdateActivity() {
        return new UpdateActivity();
    }

    /**
     * Create an instance of {@link MoveSpecialObjectsResponse }
     * 
     */
    public MoveSpecialObjectsResponse createMoveSpecialObjectsResponse() {
        return new MoveSpecialObjectsResponse();
    }

    /**
     * Create an instance of {@link DeleteContractResponse }
     * 
     */
    public DeleteContractResponse createDeleteContractResponse() {
        return new DeleteContractResponse();
    }

    /**
     * Create an instance of {@link DeleteSDHTransportLinkResponse }
     * 
     */
    public DeleteSDHTransportLinkResponse createDeleteSDHTransportLinkResponse() {
        return new DeleteSDHTransportLinkResponse();
    }

    /**
     * Create an instance of {@link GetWarehouseRootPoolsResponse }
     * 
     */
    public GetWarehouseRootPoolsResponse createGetWarehouseRootPoolsResponse() {
        return new GetWarehouseRootPoolsResponse();
    }

    /**
     * Create an instance of {@link AttachFileToObjectResponse }
     * 
     */
    public AttachFileToObjectResponse createAttachFileToObjectResponse() {
        return new AttachFileToObjectResponse();
    }

    /**
     * Create an instance of {@link CreateObjectResponse }
     * 
     */
    public CreateObjectResponse createCreateObjectResponse() {
        return new CreateObjectResponse();
    }

    /**
     * Create an instance of {@link HasAttributeResponse }
     * 
     */
    public HasAttributeResponse createHasAttributeResponse() {
        return new HasAttributeResponse();
    }

    /**
     * Create an instance of {@link GetLogicalLinkDetailsResponse }
     * 
     */
    public GetLogicalLinkDetailsResponse createGetLogicalLinkDetailsResponse() {
        return new GetLogicalLinkDetailsResponse();
    }

    /**
     * Create an instance of {@link GetContactsForCustomerResponse }
     * 
     */
    public GetContactsForCustomerResponse createGetContactsForCustomerResponse() {
        return new GetContactsForCustomerResponse();
    }

    /**
     * Create an instance of {@link ReleaseObjectFromProjectResponse }
     * 
     */
    public ReleaseObjectFromProjectResponse createReleaseObjectFromProjectResponse() {
        return new ReleaseObjectFromProjectResponse();
    }

    /**
     * Create an instance of {@link GetBGPMap }
     * 
     */
    public GetBGPMap createGetBGPMap() {
        return new GetBGPMap();
    }

    /**
     * Create an instance of {@link GetQueries }
     * 
     */
    public GetQueries createGetQueries() {
        return new GetQueries();
    }

    /**
     * Create an instance of {@link CreateServicePoolResponse }
     * 
     */
    public CreateServicePoolResponse createCreateServicePoolResponse() {
        return new CreateServicePoolResponse();
    }

    /**
     * Create an instance of {@link CreateValidatorDefinitionResponse }
     * 
     */
    public CreateValidatorDefinitionResponse createCreateValidatorDefinitionResponse() {
        return new CreateValidatorDefinitionResponse();
    }

    /**
     * Create an instance of {@link GetInstanceableListTypes }
     * 
     */
    public GetInstanceableListTypes createGetInstanceableListTypes() {
        return new GetInstanceableListTypes();
    }

    /**
     * Create an instance of {@link GetQueriesResponse }
     * 
     */
    public GetQueriesResponse createGetQueriesResponse() {
        return new GetQueriesResponse();
    }

    /**
     * Create an instance of {@link DeleteOSPViewResponse }
     * 
     */
    public DeleteOSPViewResponse createDeleteOSPViewResponse() {
        return new DeleteOSPViewResponse();
    }

    /**
     * Create an instance of {@link GetContractResources }
     * 
     */
    public GetContractResources createGetContractResources() {
        return new GetContractResources();
    }

    /**
     * Create an instance of {@link ReleaseSubnetFromVRF }
     * 
     */
    public ReleaseSubnetFromVRF createReleaseSubnetFromVRF() {
        return new ReleaseSubnetFromVRF();
    }

    /**
     * Create an instance of {@link UpdateCustomerResponse }
     * 
     */
    public UpdateCustomerResponse createUpdateCustomerResponse() {
        return new UpdateCustomerResponse();
    }

    /**
     * Create an instance of {@link GetUsersResponse }
     * 
     */
    public GetUsersResponse createGetUsersResponse() {
        return new GetUsersResponse();
    }

    /**
     * Create an instance of {@link RemoteServiceLevelCorrelatedInformation }
     * 
     */
    public RemoteServiceLevelCorrelatedInformation createRemoteServiceLevelCorrelatedInformation() {
        return new RemoteServiceLevelCorrelatedInformation();
    }

    /**
     * Create an instance of {@link RemoteValidator }
     * 
     */
    public RemoteValidator createRemoteValidator() {
        return new RemoteValidator();
    }

    /**
     * Create an instance of {@link RemoteViewObject }
     * 
     */
    public RemoteViewObject createRemoteViewObject() {
        return new RemoteViewObject();
    }

    /**
     * Create an instance of {@link GroupInfo }
     * 
     */
    public GroupInfo createGroupInfo() {
        return new GroupInfo();
    }

    /**
     * Create an instance of {@link RemoteValidatorDefinition }
     * 
     */
    public RemoteValidatorDefinition createRemoteValidatorDefinition() {
        return new RemoteValidatorDefinition();
    }

    /**
     * Create an instance of {@link RemoteFavoritesFolder }
     * 
     */
    public RemoteFavoritesFolder createRemoteFavoritesFolder() {
        return new RemoteFavoritesFolder();
    }

    /**
     * Create an instance of {@link RemoteTask }
     * 
     */
    public RemoteTask createRemoteTask() {
        return new RemoteTask();
    }

    /**
     * Create an instance of {@link RemoteSDHContainerLinkDefinition }
     * 
     */
    public RemoteSDHContainerLinkDefinition createRemoteSDHContainerLinkDefinition() {
        return new RemoteSDHContainerLinkDefinition();
    }

    /**
     * Create an instance of {@link RemoteTaskNotificationDescriptor }
     * 
     */
    public RemoteTaskNotificationDescriptor createRemoteTaskNotificationDescriptor() {
        return new RemoteTaskNotificationDescriptor();
    }

    /**
     * Create an instance of {@link RemoteSynchronizationGroup }
     * 
     */
    public RemoteSynchronizationGroup createRemoteSynchronizationGroup() {
        return new RemoteSynchronizationGroup();
    }

    /**
     * Create an instance of {@link RemoteSyncResult }
     * 
     */
    public RemoteSyncResult createRemoteSyncResult() {
        return new RemoteSyncResult();
    }

    /**
     * Create an instance of {@link RemoteSession }
     * 
     */
    public RemoteSession createRemoteSession() {
        return new RemoteSession();
    }

    /**
     * Create an instance of {@link RemoteReportMetadata }
     * 
     */
    public RemoteReportMetadata createRemoteReportMetadata() {
        return new RemoteReportMetadata();
    }

    /**
     * Create an instance of {@link RemoteAttributeMetadata }
     * 
     */
    public RemoteAttributeMetadata createRemoteAttributeMetadata() {
        return new RemoteAttributeMetadata();
    }

    /**
     * Create an instance of {@link RemoteSyncFinding }
     * 
     */
    public RemoteSyncFinding createRemoteSyncFinding() {
        return new RemoteSyncFinding();
    }

    /**
     * Create an instance of {@link RemoteFileObjectLight }
     * 
     */
    public RemoteFileObjectLight createRemoteFileObjectLight() {
        return new RemoteFileObjectLight();
    }

    /**
     * Create an instance of {@link RemoteArtifact }
     * 
     */
    public RemoteArtifact createRemoteArtifact() {
        return new RemoteArtifact();
    }

    /**
     * Create an instance of {@link RemoteArtifactDefinition }
     * 
     */
    public RemoteArtifactDefinition createRemoteArtifactDefinition() {
        return new RemoteArtifactDefinition();
    }

    /**
     * Create an instance of {@link RemoteKpiAction }
     * 
     */
    public RemoteKpiAction createRemoteKpiAction() {
        return new RemoteKpiAction();
    }

    /**
     * Create an instance of {@link RemoteTaskResult }
     * 
     */
    public RemoteTaskResult createRemoteTaskResult() {
        return new RemoteTaskResult();
    }

    /**
     * Create an instance of {@link RemoteReportMetadataLight }
     * 
     */
    public RemoteReportMetadataLight createRemoteReportMetadataLight() {
        return new RemoteReportMetadataLight();
    }

    /**
     * Create an instance of {@link RemoteSDHPosition }
     * 
     */
    public RemoteSDHPosition createRemoteSDHPosition() {
        return new RemoteSDHPosition();
    }

    /**
     * Create an instance of {@link RemoteLogicalConnectionDetails }
     * 
     */
    public RemoteLogicalConnectionDetails createRemoteLogicalConnectionDetails() {
        return new RemoteLogicalConnectionDetails();
    }

    /**
     * Create an instance of {@link RemoteResultRecord }
     * 
     */
    public RemoteResultRecord createRemoteResultRecord() {
        return new RemoteResultRecord();
    }

    /**
     * Create an instance of {@link RemoteMPLSConnectionDetails }
     * 
     */
    public RemoteMPLSConnectionDetails createRemoteMPLSConnectionDetails() {
        return new RemoteMPLSConnectionDetails();
    }

    /**
     * Create an instance of {@link RemoteUserInfoLight }
     * 
     */
    public RemoteUserInfoLight createRemoteUserInfoLight() {
        return new RemoteUserInfoLight();
    }

    /**
     * Create an instance of {@link GroupInfoLight }
     * 
     */
    public GroupInfoLight createGroupInfoLight() {
        return new GroupInfoLight();
    }

    /**
     * Create an instance of {@link RemoteObjectSpecialRelationships }
     * 
     */
    public RemoteObjectSpecialRelationships createRemoteObjectSpecialRelationships() {
        return new RemoteObjectSpecialRelationships();
    }

    /**
     * Create an instance of {@link StringPair }
     * 
     */
    public StringPair createStringPair() {
        return new StringPair();
    }

    /**
     * Create an instance of {@link RemoteObjectRelatedObjects }
     * 
     */
    public RemoteObjectRelatedObjects createRemoteObjectRelatedObjects() {
        return new RemoteObjectRelatedObjects();
    }

    /**
     * Create an instance of {@link RemoteUserInfo }
     * 
     */
    public RemoteUserInfo createRemoteUserInfo() {
        return new RemoteUserInfo();
    }

    /**
     * Create an instance of {@link RemoteFileObject }
     * 
     */
    public RemoteFileObject createRemoteFileObject() {
        return new RemoteFileObject();
    }

    /**
     * Create an instance of {@link RemoteQueryLight }
     * 
     */
    public RemoteQueryLight createRemoteQueryLight() {
        return new RemoteQueryLight();
    }

    /**
     * Create an instance of {@link RemoteConfigurationVariable }
     * 
     */
    public RemoteConfigurationVariable createRemoteConfigurationVariable() {
        return new RemoteConfigurationVariable();
    }

    /**
     * Create an instance of {@link RemoteInventoryProxy }
     * 
     */
    public RemoteInventoryProxy createRemoteInventoryProxy() {
        return new RemoteInventoryProxy();
    }

    /**
     * Create an instance of {@link RemoteAssetLevelCorrelatedInformation }
     * 
     */
    public RemoteAssetLevelCorrelatedInformation createRemoteAssetLevelCorrelatedInformation() {
        return new RemoteAssetLevelCorrelatedInformation();
    }

    /**
     * Create an instance of {@link TransientQuery }
     * 
     */
    public TransientQuery createTransientQuery() {
        return new TransientQuery();
    }

    /**
     * Create an instance of {@link RemoteSyncAction }
     * 
     */
    public RemoteSyncAction createRemoteSyncAction() {
        return new RemoteSyncAction();
    }

    /**
     * Create an instance of {@link RemoteBackgroundJob }
     * 
     */
    public RemoteBackgroundJob createRemoteBackgroundJob() {
        return new RemoteBackgroundJob();
    }

    /**
     * Create an instance of {@link RemoteClassMetadataLight }
     * 
     */
    public RemoteClassMetadataLight createRemoteClassMetadataLight() {
        return new RemoteClassMetadataLight();
    }

    /**
     * Create an instance of {@link RemoteSynchronizationProvider }
     * 
     */
    public RemoteSynchronizationProvider createRemoteSynchronizationProvider() {
        return new RemoteSynchronizationProvider();
    }

    /**
     * Create an instance of {@link RemoteSynchronizationConfiguration }
     * 
     */
    public RemoteSynchronizationConfiguration createRemoteSynchronizationConfiguration() {
        return new RemoteSynchronizationConfiguration();
    }

    /**
     * Create an instance of {@link RemoteActor }
     * 
     */
    public RemoteActor createRemoteActor() {
        return new RemoteActor();
    }

    /**
     * Create an instance of {@link ApplicationLogEntry }
     * 
     */
    public ApplicationLogEntry createApplicationLogEntry() {
        return new ApplicationLogEntry();
    }

    /**
     * Create an instance of {@link RemoteViewObjectLight }
     * 
     */
    public RemoteViewObjectLight createRemoteViewObjectLight() {
        return new RemoteViewObjectLight();
    }

    /**
     * Create an instance of {@link RemoteObjectLight }
     * 
     */
    public RemoteObjectLight createRemoteObjectLight() {
        return new RemoteObjectLight();
    }

    /**
     * Create an instance of {@link RemoteBusinessRuleConstraint }
     * 
     */
    public RemoteBusinessRuleConstraint createRemoteBusinessRuleConstraint() {
        return new RemoteBusinessRuleConstraint();
    }

    /**
     * Create an instance of {@link RemoteObject }
     * 
     */
    public RemoteObject createRemoteObject() {
        return new RemoteObject();
    }

    /**
     * Create an instance of {@link RemoteClassMetadata }
     * 
     */
    public RemoteClassMetadata createRemoteClassMetadata() {
        return new RemoteClassMetadata();
    }

    /**
     * Create an instance of {@link RemoteResultMessage }
     * 
     */
    public RemoteResultMessage createRemoteResultMessage() {
        return new RemoteResultMessage();
    }

    /**
     * Create an instance of {@link RemoteBusinessRule }
     * 
     */
    public RemoteBusinessRule createRemoteBusinessRule() {
        return new RemoteBusinessRule();
    }

    /**
     * Create an instance of {@link RemoteObjectLightList }
     * 
     */
    public RemoteObjectLightList createRemoteObjectLightList() {
        return new RemoteObjectLightList();
    }

    /**
     * Create an instance of {@link RemoteContact }
     * 
     */
    public RemoteContact createRemoteContact() {
        return new RemoteContact();
    }

    /**
     * Create an instance of {@link RemotePool }
     * 
     */
    public RemotePool createRemotePool() {
        return new RemotePool();
    }

    /**
     * Create an instance of {@link PrivilegeInfo }
     * 
     */
    public PrivilegeInfo createPrivilegeInfo() {
        return new PrivilegeInfo();
    }

    /**
     * Create an instance of {@link RemoteQuery }
     * 
     */
    public RemoteQuery createRemoteQuery() {
        return new RemoteQuery();
    }

    /**
     * Create an instance of {@link RemoteTaskScheduleDescriptor }
     * 
     */
    public RemoteTaskScheduleDescriptor createRemoteTaskScheduleDescriptor() {
        return new RemoteTaskScheduleDescriptor();
    }

    /**
     * Create an instance of {@link RemoteKpi.Thresholds.Entry }
     * 
     */
    public RemoteKpi.Thresholds.Entry createRemoteKpiThresholdsEntry() {
        return new RemoteKpi.Thresholds.Entry();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RelateObjectToServiceResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "relateObjectToServiceResponse")
    public JAXBElement<RelateObjectToServiceResponse> createRelateObjectToServiceResponse(RelateObjectToServiceResponse value) {
        return new JAXBElement<RelateObjectToServiceResponse>(_RelateObjectToServiceResponse_QNAME, RelateObjectToServiceResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateActivity }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "createActivity")
    public JAXBElement<CreateActivity> createCreateActivity(CreateActivity value) {
        return new JAXBElement<CreateActivity>(_CreateActivity_QNAME, CreateActivity.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RunValidationsForObject }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "runValidationsForObject")
    public JAXBElement<RunValidationsForObject> createRunValidationsForObject(RunValidationsForObject value) {
        return new JAXBElement<RunValidationsForObject>(_RunValidationsForObject_QNAME, RunValidationsForObject.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllProxies }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getAllProxies")
    public JAXBElement<GetAllProxies> createGetAllProxies(GetAllProxies value) {
        return new JAXBElement<GetAllProxies>(_GetAllProxies_QNAME, GetAllProxies.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RelateIPtoPort }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "relateIPtoPort")
    public JAXBElement<RelateIPtoPort> createRelateIPtoPort(RelateIPtoPort value) {
        return new JAXBElement<RelateIPtoPort>(_RelateIPtoPort_QNAME, RelateIPtoPort.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReleasePhysicalNodeFromWarehouseResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "releasePhysicalNodeFromWarehouseResponse")
    public JAXBElement<ReleasePhysicalNodeFromWarehouseResponse> createReleasePhysicalNodeFromWarehouseResponse(ReleasePhysicalNodeFromWarehouseResponse value) {
        return new JAXBElement<ReleasePhysicalNodeFromWarehouseResponse>(_ReleasePhysicalNodeFromWarehouseResponse_QNAME, ReleasePhysicalNodeFromWarehouseResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteWarehouseResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "deleteWarehouseResponse")
    public JAXBElement<DeleteWarehouseResponse> createDeleteWarehouseResponse(DeleteWarehouseResponse value) {
        return new JAXBElement<DeleteWarehouseResponse>(_DeleteWarehouseResponse_QNAME, DeleteWarehouseResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemovePossibleChildrenForClassWithIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "removePossibleChildrenForClassWithIdResponse")
    public JAXBElement<RemovePossibleChildrenForClassWithIdResponse> createRemovePossibleChildrenForClassWithIdResponse(RemovePossibleChildrenForClassWithIdResponse value) {
        return new JAXBElement<RemovePossibleChildrenForClassWithIdResponse>(_RemovePossibleChildrenForClassWithIdResponse_QNAME, RemovePossibleChildrenForClassWithIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetObjectsRelatedToServiceResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getObjectsRelatedToServiceResponse")
    public JAXBElement<GetObjectsRelatedToServiceResponse> createGetObjectsRelatedToServiceResponse(GetObjectsRelatedToServiceResponse value) {
        return new JAXBElement<GetObjectsRelatedToServiceResponse>(_GetObjectsRelatedToServiceResponse_QNAME, GetObjectsRelatedToServiceResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSubnetsInSubnetResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getSubnetsInSubnetResponse")
    public JAXBElement<GetSubnetsInSubnetResponse> createGetSubnetsInSubnetResponse(GetSubnetsInSubnetResponse value) {
        return new JAXBElement<GetSubnetsInSubnetResponse>(_GetSubnetsInSubnetResponse_QNAME, GetSubnetsInSubnetResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ExecuteInventoryLevelReportResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "executeInventoryLevelReportResponse")
    public JAXBElement<ExecuteInventoryLevelReportResponse> createExecuteInventoryLevelReportResponse(ExecuteInventoryLevelReportResponse value) {
        return new JAXBElement<ExecuteInventoryLevelReportResponse>(_ExecuteInventoryLevelReportResponse_QNAME, ExecuteInventoryLevelReportResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTemplateElementChildren }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getTemplateElementChildren")
    public JAXBElement<GetTemplateElementChildren> createGetTemplateElementChildren(GetTemplateElementChildren value) {
        return new JAXBElement<GetTemplateElementChildren>(_GetTemplateElementChildren_QNAME, GetTemplateElementChildren.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSynchronizationProviders }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getSynchronizationProviders")
    public JAXBElement<GetSynchronizationProviders> createGetSynchronizationProviders(GetSynchronizationProviders value) {
        return new JAXBElement<GetSynchronizationProviders>(_GetSynchronizationProviders_QNAME, GetSynchronizationProviders.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RelateObjectToService }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "relateObjectToService")
    public JAXBElement<RelateObjectToService> createRelateObjectToService(RelateObjectToService value) {
        return new JAXBElement<RelateObjectToService>(_RelateObjectToService_QNAME, RelateObjectToService.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateSubnetPoolResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "createSubnetPoolResponse")
    public JAXBElement<CreateSubnetPoolResponse> createCreateSubnetPoolResponse(CreateSubnetPoolResponse value) {
        return new JAXBElement<CreateSubnetPoolResponse>(_CreateSubnetPoolResponse_QNAME, CreateSubnetPoolResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteServicePoolResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "deleteServicePoolResponse")
    public JAXBElement<DeleteServicePoolResponse> createDeleteServicePoolResponse(DeleteServicePoolResponse value) {
        return new JAXBElement<DeleteServicePoolResponse>(_DeleteServicePoolResponse_QNAME, DeleteServicePoolResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddIPAddress }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "addIPAddress")
    public JAXBElement<AddIPAddress> createAddIPAddress(AddIPAddress value) {
        return new JAXBElement<AddIPAddress>(_AddIPAddress_QNAME, AddIPAddress.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetOSPView }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getOSPView")
    public JAXBElement<GetOSPView> createGetOSPView(GetOSPView value) {
        return new JAXBElement<GetOSPView>(_GetOSPView_QNAME, GetOSPView.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReleasePhysicalNodeFromWarehouse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "releasePhysicalNodeFromWarehouse")
    public JAXBElement<ReleasePhysicalNodeFromWarehouse> createReleasePhysicalNodeFromWarehouse(ReleasePhysicalNodeFromWarehouse value) {
        return new JAXBElement<ReleasePhysicalNodeFromWarehouse>(_ReleasePhysicalNodeFromWarehouse_QNAME, ReleasePhysicalNodeFromWarehouse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateReportParametersResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "updateReportParametersResponse")
    public JAXBElement<UpdateReportParametersResponse> createUpdateReportParametersResponse(UpdateReportParametersResponse value) {
        return new JAXBElement<UpdateReportParametersResponse>(_UpdateReportParametersResponse_QNAME, UpdateReportParametersResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetFirstParentOfClass }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getFirstParentOfClass")
    public JAXBElement<GetFirstParentOfClass> createGetFirstParentOfClass(GetFirstParentOfClass value) {
        return new JAXBElement<GetFirstParentOfClass>(_GetFirstParentOfClass_QNAME, GetFirstParentOfClass.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetProjectPool }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getProjectPool")
    public JAXBElement<GetProjectPool> createGetProjectPool(GetProjectPool value) {
        return new JAXBElement<GetProjectPool>(_GetProjectPool_QNAME, GetProjectPool.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteSubnetPoolsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "deleteSubnetPoolsResponse")
    public JAXBElement<DeleteSubnetPoolsResponse> createDeleteSubnetPoolsResponse(DeleteSubnetPoolsResponse value) {
        return new JAXBElement<DeleteSubnetPoolsResponse>(_DeleteSubnetPoolsResponse_QNAME, DeleteSubnetPoolsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateProcessDefinitionResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "createProcessDefinitionResponse")
    public JAXBElement<CreateProcessDefinitionResponse> createCreateProcessDefinitionResponse(CreateProcessDefinitionResponse value) {
        return new JAXBElement<CreateProcessDefinitionResponse>(_CreateProcessDefinitionResponse_QNAME, CreateProcessDefinitionResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CopyObjectsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "copyObjectsResponse")
    public JAXBElement<CopyObjectsResponse> createCopyObjectsResponse(CopyObjectsResponse value) {
        return new JAXBElement<CopyObjectsResponse>(_CopyObjectsResponse_QNAME, CopyObjectsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteFavoritesFolders }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "deleteFavoritesFolders")
    public JAXBElement<DeleteFavoritesFolders> createDeleteFavoritesFolders(DeleteFavoritesFolders value) {
        return new JAXBElement<DeleteFavoritesFolders>(_DeleteFavoritesFolders_QNAME, DeleteFavoritesFolders.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CopyObjects }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "copyObjects")
    public JAXBElement<CopyObjects> createCopyObjects(CopyObjects value) {
        return new JAXBElement<CopyObjects>(_CopyObjects_QNAME, CopyObjects.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemovePrivilegeFromUserResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "removePrivilegeFromUserResponse")
    public JAXBElement<RemovePrivilegeFromUserResponse> createRemovePrivilegeFromUserResponse(RemovePrivilegeFromUserResponse value) {
        return new JAXBElement<RemovePrivilegeFromUserResponse>(_RemovePrivilegeFromUserResponse_QNAME, RemovePrivilegeFromUserResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetContractsInPoolResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getContractsInPoolResponse")
    public JAXBElement<GetContractsInPoolResponse> createGetContractsInPoolResponse(GetContractsInPoolResponse value) {
        return new JAXBElement<GetContractsInPoolResponse>(_GetContractsInPoolResponse_QNAME, GetContractsInPoolResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteObjects }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "deleteObjects")
    public JAXBElement<DeleteObjects> createDeleteObjects(DeleteObjects value) {
        return new JAXBElement<DeleteObjects>(_DeleteObjects_QNAME, DeleteObjects.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateOSPViewResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "createOSPViewResponse")
    public JAXBElement<CreateOSPViewResponse> createCreateOSPViewResponse(CreateOSPViewResponse value) {
        return new JAXBElement<CreateOSPViewResponse>(_CreateOSPViewResponse_QNAME, CreateOSPViewResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateRootPool }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "createRootPool")
    public JAXBElement<CreateRootPool> createCreateRootPool(CreateRootPool value) {
        return new JAXBElement<CreateRootPool>(_CreateRootPool_QNAME, CreateRootPool.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateAttributeForClassWithIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "createAttributeForClassWithIdResponse")
    public JAXBElement<CreateAttributeForClassWithIdResponse> createCreateAttributeForClassWithIdResponse(CreateAttributeForClassWithIdResponse value) {
        return new JAXBElement<CreateAttributeForClassWithIdResponse>(_CreateAttributeForClassWithIdResponse_QNAME, CreateAttributeForClassWithIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SetPrivilegeToGroupResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "setPrivilegeToGroupResponse")
    public JAXBElement<SetPrivilegeToGroupResponse> createSetPrivilegeToGroupResponse(SetPrivilegeToGroupResponse value) {
        return new JAXBElement<SetPrivilegeToGroupResponse>(_SetPrivilegeToGroupResponse_QNAME, SetPrivilegeToGroupResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSyncDataSourceConfigurations }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getSyncDataSourceConfigurations")
    public JAXBElement<GetSyncDataSourceConfigurations> createGetSyncDataSourceConfigurations(GetSyncDataSourceConfigurations value) {
        return new JAXBElement<GetSyncDataSourceConfigurations>(_GetSyncDataSourceConfigurations_QNAME, GetSyncDataSourceConfigurations.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReleaseMirrorMultiplePort }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "releaseMirrorMultiplePort")
    public JAXBElement<ReleaseMirrorMultiplePort> createReleaseMirrorMultiplePort(ReleaseMirrorMultiplePort value) {
        return new JAXBElement<ReleaseMirrorMultiplePort>(_ReleaseMirrorMultiplePort_QNAME, ReleaseMirrorMultiplePort.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteProxyPool }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "deleteProxyPool")
    public JAXBElement<DeleteProxyPool> createDeleteProxyPool(DeleteProxyPool value) {
        return new JAXBElement<DeleteProxyPool>(_DeleteProxyPool_QNAME, DeleteProxyPool.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UnsubscribeUserFromTaskResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "unsubscribeUserFromTaskResponse")
    public JAXBElement<UnsubscribeUserFromTaskResponse> createUnsubscribeUserFromTaskResponse(UnsubscribeUserFromTaskResponse value) {
        return new JAXBElement<UnsubscribeUserFromTaskResponse>(_UnsubscribeUserFromTaskResponse_QNAME, UnsubscribeUserFromTaskResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetPoolsInWarehouse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getPoolsInWarehouse")
    public JAXBElement<GetPoolsInWarehouse> createGetPoolsInWarehouse(GetPoolsInWarehouse value) {
        return new JAXBElement<GetPoolsInWarehouse>(_GetPoolsInWarehouse_QNAME, GetPoolsInWarehouse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UnsubscribeUserFromTask }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "unsubscribeUserFromTask")
    public JAXBElement<UnsubscribeUserFromTask> createUnsubscribeUserFromTask(UnsubscribeUserFromTask value) {
        return new JAXBElement<UnsubscribeUserFromTask>(_UnsubscribeUserFromTask_QNAME, UnsubscribeUserFromTask.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateObjectRelatedViewResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "createObjectRelatedViewResponse")
    public JAXBElement<CreateObjectRelatedViewResponse> createCreateObjectRelatedViewResponse(CreateObjectRelatedViewResponse value) {
        return new JAXBElement<CreateObjectRelatedViewResponse>(_CreateObjectRelatedViewResponse_QNAME, CreateObjectRelatedViewResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllCustomers }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getAllCustomers")
    public JAXBElement<GetAllCustomers> createGetAllCustomers(GetAllCustomers value) {
        return new JAXBElement<GetAllCustomers>(_GetAllCustomers_QNAME, GetAllCustomers.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateConfigurationVariable }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "updateConfigurationVariable")
    public JAXBElement<UpdateConfigurationVariable> createUpdateConfigurationVariable(UpdateConfigurationVariable value) {
        return new JAXBElement<UpdateConfigurationVariable>(_UpdateConfigurationVariable_QNAME, UpdateConfigurationVariable.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SaveQueryResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "saveQueryResponse")
    public JAXBElement<SaveQueryResponse> createSaveQueryResponse(SaveQueryResponse value) {
        return new JAXBElement<SaveQueryResponse>(_SaveQueryResponse_QNAME, SaveQueryResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateListTypeItemRelatedView }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "createListTypeItemRelatedView")
    public JAXBElement<CreateListTypeItemRelatedView> createCreateListTypeItemRelatedView(CreateListTypeItemRelatedView value) {
        return new JAXBElement<CreateListTypeItemRelatedView>(_CreateListTypeItemRelatedView_QNAME, CreateListTypeItemRelatedView.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MoveObjects }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "moveObjects")
    public JAXBElement<MoveObjects> createMoveObjects(MoveObjects value) {
        return new JAXBElement<MoveObjects>(_MoveObjects_QNAME, MoveObjects.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateUserResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "createUserResponse")
    public JAXBElement<CreateUserResponse> createCreateUserResponse(CreateUserResponse value) {
        return new JAXBElement<CreateUserResponse>(_CreateUserResponse_QNAME, CreateUserResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSpecialAttributeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getSpecialAttributeResponse")
    public JAXBElement<GetSpecialAttributeResponse> createGetSpecialAttributeResponse(GetSpecialAttributeResponse value) {
        return new JAXBElement<GetSpecialAttributeResponse>(_GetSpecialAttributeResponse_QNAME, GetSpecialAttributeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeletePoolsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "deletePoolsResponse")
    public JAXBElement<DeletePoolsResponse> createDeletePoolsResponse(DeletePoolsResponse value) {
        return new JAXBElement<DeletePoolsResponse>(_DeletePoolsResponse_QNAME, DeletePoolsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllCustomersResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getAllCustomersResponse")
    public JAXBElement<GetAllCustomersResponse> createGetAllCustomersResponse(GetAllCustomersResponse value) {
        return new JAXBElement<GetAllCustomersResponse>(_GetAllCustomersResponse_QNAME, GetAllCustomersResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetParentsUntilFirstOfClassResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getParentsUntilFirstOfClassResponse")
    public JAXBElement<GetParentsUntilFirstOfClassResponse> createGetParentsUntilFirstOfClassResponse(GetParentsUntilFirstOfClassResponse value) {
        return new JAXBElement<GetParentsUntilFirstOfClassResponse>(_GetParentsUntilFirstOfClassResponse_QNAME, GetParentsUntilFirstOfClassResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetPoolResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getPoolResponse")
    public JAXBElement<GetPoolResponse> createGetPoolResponse(GetPoolResponse value) {
        return new JAXBElement<GetPoolResponse>(_GetPoolResponse_QNAME, GetPoolResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateObject }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "createObject")
    public JAXBElement<CreateObject> createCreateObject(CreateObject value) {
        return new JAXBElement<CreateObject>(_CreateObject_QNAME, CreateObject.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateListTypeItemRelatedViewResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "updateListTypeItemRelatedViewResponse")
    public JAXBElement<UpdateListTypeItemRelatedViewResponse> createUpdateListTypeItemRelatedViewResponse(UpdateListTypeItemRelatedViewResponse value) {
        return new JAXBElement<UpdateListTypeItemRelatedViewResponse>(_UpdateListTypeItemRelatedViewResponse_QNAME, UpdateListTypeItemRelatedViewResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateSubnet }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "createSubnet")
    public JAXBElement<CreateSubnet> createCreateSubnet(CreateSubnet value) {
        return new JAXBElement<CreateSubnet>(_CreateSubnet_QNAME, CreateSubnet.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReconnectPhysicalConnection }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "reconnectPhysicalConnection")
    public JAXBElement<ReconnectPhysicalConnection> createReconnectPhysicalConnection(ReconnectPhysicalConnection value) {
        return new JAXBElement<ReconnectPhysicalConnection>(_ReconnectPhysicalConnection_QNAME, ReconnectPhysicalConnection.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReleaseMirrorPortResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "releaseMirrorPortResponse")
    public JAXBElement<ReleaseMirrorPortResponse> createReleaseMirrorPortResponse(ReleaseMirrorPortResponse value) {
        return new JAXBElement<ReleaseMirrorPortResponse>(_ReleaseMirrorPortResponse_QNAME, ReleaseMirrorPortResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTasksResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getTasksResponse")
    public JAXBElement<GetTasksResponse> createGetTasksResponse(GetTasksResponse value) {
        return new JAXBElement<GetTasksResponse>(_GetTasksResponse_QNAME, GetTasksResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateContractResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "updateContractResponse")
    public JAXBElement<UpdateContractResponse> createUpdateContractResponse(UpdateContractResponse value) {
        return new JAXBElement<UpdateContractResponse>(_UpdateContractResponse_QNAME, UpdateContractResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateValidatorDefinition }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "updateValidatorDefinition")
    public JAXBElement<UpdateValidatorDefinition> createUpdateValidatorDefinition(UpdateValidatorDefinition value) {
        return new JAXBElement<UpdateValidatorDefinition>(_UpdateValidatorDefinition_QNAME, UpdateValidatorDefinition.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteTask }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "deleteTask")
    public JAXBElement<DeleteTask> createDeleteTask(DeleteTask value) {
        return new JAXBElement<DeleteTask>(_DeleteTask_QNAME, DeleteTask.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteSubnets }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "deleteSubnets")
    public JAXBElement<DeleteSubnets> createDeleteSubnets(DeleteSubnets value) {
        return new JAXBElement<DeleteSubnets>(_DeleteSubnets_QNAME, DeleteSubnets.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReleaseMirrorPort }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "releaseMirrorPort")
    public JAXBElement<ReleaseMirrorPort> createReleaseMirrorPort(ReleaseMirrorPort value) {
        return new JAXBElement<ReleaseMirrorPort>(_ReleaseMirrorPort_QNAME, ReleaseMirrorPort.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateProjectResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "createProjectResponse")
    public JAXBElement<CreateProjectResponse> createCreateProjectResponse(CreateProjectResponse value) {
        return new JAXBElement<CreateProjectResponse>(_CreateProjectResponse_QNAME, CreateProjectResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSynchronizationProvidersResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getSynchronizationProvidersResponse")
    public JAXBElement<GetSynchronizationProvidersResponse> createGetSynchronizationProvidersResponse(GetSynchronizationProvidersResponse value) {
        return new JAXBElement<GetSynchronizationProvidersResponse>(_GetSynchronizationProvidersResponse_QNAME, GetSynchronizationProvidersResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateSDHTributaryLink }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "createSDHTributaryLink")
    public JAXBElement<CreateSDHTributaryLink> createCreateSDHTributaryLink(CreateSDHTributaryLink value) {
        return new JAXBElement<CreateSDHTributaryLink>(_CreateSDHTributaryLink_QNAME, CreateSDHTributaryLink.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteObjectsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "deleteObjectsResponse")
    public JAXBElement<DeleteObjectsResponse> createDeleteObjectsResponse(DeleteObjectsResponse value) {
        return new JAXBElement<DeleteObjectsResponse>(_DeleteObjectsResponse_QNAME, DeleteObjectsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MoveObjectsToWarehouse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "moveObjectsToWarehouse")
    public JAXBElement<MoveObjectsToWarehouse> createMoveObjectsToWarehouse(MoveObjectsToWarehouse value) {
        return new JAXBElement<MoveObjectsToWarehouse>(_MoveObjectsToWarehouse_QNAME, MoveObjectsToWarehouse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSpecialChildrenOfClassLightRecursiveResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getSpecialChildrenOfClassLightRecursiveResponse")
    public JAXBElement<GetSpecialChildrenOfClassLightRecursiveResponse> createGetSpecialChildrenOfClassLightRecursiveResponse(GetSpecialChildrenOfClassLightRecursiveResponse value) {
        return new JAXBElement<GetSpecialChildrenOfClassLightRecursiveResponse>(_GetSpecialChildrenOfClassLightRecursiveResponse_QNAME, GetSpecialChildrenOfClassLightRecursiveResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SetAttributePropertiesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "setAttributePropertiesResponse")
    public JAXBElement<SetAttributePropertiesResponse> createSetAttributePropertiesResponse(SetAttributePropertiesResponse value) {
        return new JAXBElement<SetAttributePropertiesResponse>(_SetAttributePropertiesResponse_QNAME, SetAttributePropertiesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateInventoryLevelReport }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "createInventoryLevelReport")
    public JAXBElement<CreateInventoryLevelReport> createCreateInventoryLevelReport(CreateInventoryLevelReport value) {
        return new JAXBElement<CreateInventoryLevelReport>(_CreateInventoryLevelReport_QNAME, CreateInventoryLevelReport.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetObjectsInFavoritesFolder }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getObjectsInFavoritesFolder")
    public JAXBElement<GetObjectsInFavoritesFolder> createGetObjectsInFavoritesFolder(GetObjectsInFavoritesFolder value) {
        return new JAXBElement<GetObjectsInFavoritesFolder>(_GetObjectsInFavoritesFolder_QNAME, GetObjectsInFavoritesFolder.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetObjectChildrenForClassWithId }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getObjectChildrenForClassWithId")
    public JAXBElement<GetObjectChildrenForClassWithId> createGetObjectChildrenForClassWithId(GetObjectChildrenForClassWithId value) {
        return new JAXBElement<GetObjectChildrenForClassWithId>(_GetObjectChildrenForClassWithId_QNAME, GetObjectChildrenForClassWithId.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetArtifactDefinitionForActivity }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getArtifactDefinitionForActivity")
    public JAXBElement<GetArtifactDefinitionForActivity> createGetArtifactDefinitionForActivity(GetArtifactDefinitionForActivity value) {
        return new JAXBElement<GetArtifactDefinitionForActivity>(_GetArtifactDefinitionForActivity_QNAME, GetArtifactDefinitionForActivity.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteBusinessRule }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "deleteBusinessRule")
    public JAXBElement<DeleteBusinessRule> createDeleteBusinessRule(DeleteBusinessRule value) {
        return new JAXBElement<DeleteBusinessRule>(_DeleteBusinessRule_QNAME, DeleteBusinessRule.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CopyTemplateElements }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "copyTemplateElements")
    public JAXBElement<CopyTemplateElements> createCopyTemplateElements(CopyTemplateElements value) {
        return new JAXBElement<CopyTemplateElements>(_CopyTemplateElements_QNAME, CopyTemplateElements.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateOSPView }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "createOSPView")
    public JAXBElement<CreateOSPView> createCreateOSPView(CreateOSPView value) {
        return new JAXBElement<CreateOSPView>(_CreateOSPView_QNAME, CreateOSPView.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteCostumerResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "deleteCostumerResponse")
    public JAXBElement<DeleteCostumerResponse> createDeleteCostumerResponse(DeleteCostumerResponse value) {
        return new JAXBElement<DeleteCostumerResponse>(_DeleteCostumerResponse_QNAME, DeleteCostumerResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReconnectPhysicalConnectionResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "reconnectPhysicalConnectionResponse")
    public JAXBElement<ReconnectPhysicalConnectionResponse> createReconnectPhysicalConnectionResponse(ReconnectPhysicalConnectionResponse value) {
        return new JAXBElement<ReconnectPhysicalConnectionResponse>(_ReconnectPhysicalConnectionResponse_QNAME, ReconnectPhysicalConnectionResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteSDHTributaryLink }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "deleteSDHTributaryLink")
    public JAXBElement<DeleteSDHTributaryLink> createDeleteSDHTributaryLink(DeleteSDHTributaryLink value) {
        return new JAXBElement<DeleteSDHTributaryLink>(_DeleteSDHTributaryLink_QNAME, DeleteSDHTributaryLink.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateSparePart }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "createSparePart")
    public JAXBElement<CreateSparePart> createCreateSparePart(CreateSparePart value) {
        return new JAXBElement<CreateSparePart>(_CreateSparePart_QNAME, CreateSparePart.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetObjectsWithFilterLightResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getObjectsWithFilterLightResponse")
    public JAXBElement<GetObjectsWithFilterLightResponse> createGetObjectsWithFilterLightResponse(GetObjectsWithFilterLightResponse value) {
        return new JAXBElement<GetObjectsWithFilterLightResponse>(_GetObjectsWithFilterLightResponse_QNAME, GetObjectsWithFilterLightResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LaunchAdHocAutomatedSynchronizationTask }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "launchAdHocAutomatedSynchronizationTask")
    public JAXBElement<LaunchAdHocAutomatedSynchronizationTask> createLaunchAdHocAutomatedSynchronizationTask(LaunchAdHocAutomatedSynchronizationTask value) {
        return new JAXBElement<LaunchAdHocAutomatedSynchronizationTask>(_LaunchAdHocAutomatedSynchronizationTask_QNAME, LaunchAdHocAutomatedSynchronizationTask.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AssociatePhysicalNodeToWarehouseResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "associatePhysicalNodeToWarehouseResponse")
    public JAXBElement<AssociatePhysicalNodeToWarehouseResponse> createAssociatePhysicalNodeToWarehouseResponse(AssociatePhysicalNodeToWarehouseResponse value) {
        return new JAXBElement<AssociatePhysicalNodeToWarehouseResponse>(_AssociatePhysicalNodeToWarehouseResponse_QNAME, AssociatePhysicalNodeToWarehouseResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteListTypeItemRelatedView }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "deleteListTypeItemRelatedView")
    public JAXBElement<DeleteListTypeItemRelatedView> createDeleteListTypeItemRelatedView(DeleteListTypeItemRelatedView value) {
        return new JAXBElement<DeleteListTypeItemRelatedView>(_DeleteListTypeItemRelatedView_QNAME, DeleteListTypeItemRelatedView.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CommitActivity }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "commitActivity")
    public JAXBElement<CommitActivity> createCommitActivity(CommitActivity value) {
        return new JAXBElement<CommitActivity>(_CommitActivity_QNAME, CommitActivity.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteService }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "deleteService")
    public JAXBElement<DeleteService> createDeleteService(DeleteService value) {
        return new JAXBElement<DeleteService>(_DeleteService_QNAME, DeleteService.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSubscribersForTask }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getSubscribersForTask")
    public JAXBElement<GetSubscribersForTask> createGetSubscribersForTask(GetSubscribersForTask value) {
        return new JAXBElement<GetSubscribersForTask>(_GetSubscribersForTask_QNAME, GetSubscribersForTask.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateClassLevelReport }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "createClassLevelReport")
    public JAXBElement<CreateClassLevelReport> createCreateClassLevelReport(CreateClassLevelReport value) {
        return new JAXBElement<CreateClassLevelReport>(_CreateClassLevelReport_QNAME, CreateClassLevelReport.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetOSPViewsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getOSPViewsResponse")
    public JAXBElement<GetOSPViewsResponse> createGetOSPViewsResponse(GetOSPViewsResponse value) {
        return new JAXBElement<GetOSPViewsResponse>(_GetOSPViewsResponse_QNAME, GetOSPViewsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetParentsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getParentsResponse")
    public JAXBElement<GetParentsResponse> createGetParentsResponse(GetParentsResponse value) {
        return new JAXBElement<GetParentsResponse>(_GetParentsResponse_QNAME, GetParentsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetProjectResourcesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getProjectResourcesResponse")
    public JAXBElement<GetProjectResourcesResponse> createGetProjectResourcesResponse(GetProjectResourcesResponse value) {
        return new JAXBElement<GetProjectResourcesResponse>(_GetProjectResourcesResponse_QNAME, GetProjectResourcesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateListTypeItemResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "updateListTypeItemResponse")
    public JAXBElement<UpdateListTypeItemResponse> createUpdateListTypeItemResponse(UpdateListTypeItemResponse value) {
        return new JAXBElement<UpdateListTypeItemResponse>(_UpdateListTypeItemResponse_QNAME, UpdateListTypeItemResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateProjectResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "updateProjectResponse")
    public JAXBElement<UpdateProjectResponse> createUpdateProjectResponse(UpdateProjectResponse value) {
        return new JAXBElement<UpdateProjectResponse>(_UpdateProjectResponse_QNAME, UpdateProjectResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddPossibleSpecialChildrenWithId }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "addPossibleSpecialChildrenWithId")
    public JAXBElement<AddPossibleSpecialChildrenWithId> createAddPossibleSpecialChildrenWithId(AddPossibleSpecialChildrenWithId value) {
        return new JAXBElement<AddPossibleSpecialChildrenWithId>(_AddPossibleSpecialChildrenWithId_QNAME, AddPossibleSpecialChildrenWithId.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetPhysicalPathResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getPhysicalPathResponse")
    public JAXBElement<GetPhysicalPathResponse> createGetPhysicalPathResponse(GetPhysicalPathResponse value) {
        return new JAXBElement<GetPhysicalPathResponse>(_GetPhysicalPathResponse_QNAME, GetPhysicalPathResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ConnectMirrorMultiplePort }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "connectMirrorMultiplePort")
    public JAXBElement<ConnectMirrorMultiplePort> createConnectMirrorMultiplePort(ConnectMirrorMultiplePort value) {
        return new JAXBElement<ConnectMirrorMultiplePort>(_ConnectMirrorMultiplePort_QNAME, ConnectMirrorMultiplePort.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateServiceResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "createServiceResponse")
    public JAXBElement<CreateServiceResponse> createCreateServiceResponse(CreateServiceResponse value) {
        return new JAXBElement<CreateServiceResponse>(_CreateServiceResponse_QNAME, CreateServiceResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateContact }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "updateContact")
    public JAXBElement<UpdateContact> createUpdateContact(UpdateContact value) {
        return new JAXBElement<UpdateContact>(_UpdateContact_QNAME, UpdateContact.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateCustomerResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "createCustomerResponse")
    public JAXBElement<CreateCustomerResponse> createCreateCustomerResponse(CreateCustomerResponse value) {
        return new JAXBElement<CreateCustomerResponse>(_CreateCustomerResponse_QNAME, CreateCustomerResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ExecuteClassLevelReport }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "executeClassLevelReport")
    public JAXBElement<ExecuteClassLevelReport> createExecuteClassLevelReport(ExecuteClassLevelReport value) {
        return new JAXBElement<ExecuteClassLevelReport>(_ExecuteClassLevelReport_QNAME, ExecuteClassLevelReport.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReleasePortFromIPResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "releasePortFromIPResponse")
    public JAXBElement<ReleasePortFromIPResponse> createReleasePortFromIPResponse(ReleasePortFromIPResponse value) {
        return new JAXBElement<ReleasePortFromIPResponse>(_ReleasePortFromIPResponse_QNAME, ReleasePortFromIPResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetChildrenOfClass }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getChildrenOfClass")
    public JAXBElement<GetChildrenOfClass> createGetChildrenOfClass(GetChildrenOfClass value) {
        return new JAXBElement<GetChildrenOfClass>(_GetChildrenOfClass_QNAME, GetChildrenOfClass.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetContact }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getContact")
    public JAXBElement<GetContact> createGetContact(GetContact value) {
        return new JAXBElement<GetContact>(_GetContact_QNAME, GetContact.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetProjectPoolResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getProjectPoolResponse")
    public JAXBElement<GetProjectPoolResponse> createGetProjectPoolResponse(GetProjectPoolResponse value) {
        return new JAXBElement<GetProjectPoolResponse>(_GetProjectPoolResponse_QNAME, GetProjectPoolResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateFilePropertiesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "updateFilePropertiesResponse")
    public JAXBElement<UpdateFilePropertiesResponse> createUpdateFilePropertiesResponse(UpdateFilePropertiesResponse value) {
        return new JAXBElement<UpdateFilePropertiesResponse>(_UpdateFilePropertiesResponse_QNAME, UpdateFilePropertiesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateAttributeForClassWithId }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "createAttributeForClassWithId")
    public JAXBElement<CreateAttributeForClassWithId> createCreateAttributeForClassWithId(CreateAttributeForClassWithId value) {
        return new JAXBElement<CreateAttributeForClassWithId>(_CreateAttributeForClassWithId_QNAME, CreateAttributeForClassWithId.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteSparePool }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "deleteSparePool")
    public JAXBElement<DeleteSparePool> createDeleteSparePool(DeleteSparePool value) {
        return new JAXBElement<DeleteSparePool>(_DeleteSparePool_QNAME, DeleteSparePool.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MoveObjectsToPoolResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "moveObjectsToPoolResponse")
    public JAXBElement<MoveObjectsToPoolResponse> createMoveObjectsToPoolResponse(MoveObjectsToPoolResponse value) {
        return new JAXBElement<MoveObjectsToPoolResponse>(_MoveObjectsToPoolResponse_QNAME, MoveObjectsToPoolResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteSynchronizationGroupResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "deleteSynchronizationGroupResponse")
    public JAXBElement<DeleteSynchronizationGroupResponse> createDeleteSynchronizationGroupResponse(DeleteSynchronizationGroupResponse value) {
        return new JAXBElement<DeleteSynchronizationGroupResponse>(_DeleteSynchronizationGroupResponse_QNAME, DeleteSynchronizationGroupResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetBusinessObjectAuditTrailResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getBusinessObjectAuditTrailResponse")
    public JAXBElement<GetBusinessObjectAuditTrailResponse> createGetBusinessObjectAuditTrailResponse(GetBusinessObjectAuditTrailResponse value) {
        return new JAXBElement<GetBusinessObjectAuditTrailResponse>(_GetBusinessObjectAuditTrailResponse_QNAME, GetBusinessObjectAuditTrailResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetFavoritesFolderResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getFavoritesFolderResponse")
    public JAXBElement<GetFavoritesFolderResponse> createGetFavoritesFolderResponse(GetFavoritesFolderResponse value) {
        return new JAXBElement<GetFavoritesFolderResponse>(_GetFavoritesFolderResponse_QNAME, GetFavoritesFolderResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RelateObjectsToContract }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "relateObjectsToContract")
    public JAXBElement<RelateObjectsToContract> createRelateObjectsToContract(RelateObjectsToContract value) {
        return new JAXBElement<RelateObjectsToContract>(_RelateObjectsToContract_QNAME, RelateObjectsToContract.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteGroups }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "deleteGroups")
    public JAXBElement<DeleteGroups> createDeleteGroups(DeleteGroups value) {
        return new JAXBElement<DeleteGroups>(_DeleteGroups_QNAME, DeleteGroups.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ExecuteSyncActionsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "executeSyncActionsResponse")
    public JAXBElement<ExecuteSyncActionsResponse> createExecuteSyncActionsResponse(ExecuteSyncActionsResponse value) {
        return new JAXBElement<ExecuteSyncActionsResponse>(_ExecuteSyncActionsResponse_QNAME, ExecuteSyncActionsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AssociateObjectsToContract }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "associateObjectsToContract")
    public JAXBElement<AssociateObjectsToContract> createAssociateObjectsToContract(AssociateObjectsToContract value) {
        return new JAXBElement<AssociateObjectsToContract>(_AssociateObjectsToContract_QNAME, AssociateObjectsToContract.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteGroupsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "deleteGroupsResponse")
    public JAXBElement<DeleteGroupsResponse> createDeleteGroupsResponse(DeleteGroupsResponse value) {
        return new JAXBElement<DeleteGroupsResponse>(_DeleteGroupsResponse_QNAME, DeleteGroupsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetConfigurationVariableValueResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getConfigurationVariableValueResponse")
    public JAXBElement<GetConfigurationVariableValueResponse> createGetConfigurationVariableValueResponse(GetConfigurationVariableValueResponse value) {
        return new JAXBElement<GetConfigurationVariableValueResponse>(_GetConfigurationVariableValueResponse_QNAME, GetConfigurationVariableValueResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetListTypeItemsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getListTypeItemsResponse")
    public JAXBElement<GetListTypeItemsResponse> createGetListTypeItemsResponse(GetListTypeItemsResponse value) {
        return new JAXBElement<GetListTypeItemsResponse>(_GetListTypeItemsResponse_QNAME, GetListTypeItemsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCustomerResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getCustomerResponse")
    public JAXBElement<GetCustomerResponse> createGetCustomerResponse(GetCustomerResponse value) {
        return new JAXBElement<GetCustomerResponse>(_GetCustomerResponse_QNAME, GetCustomerResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSDHContainerLinkStructureResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getSDHContainerLinkStructureResponse")
    public JAXBElement<GetSDHContainerLinkStructureResponse> createGetSDHContainerLinkStructureResponse(GetSDHContainerLinkStructureResponse value) {
        return new JAXBElement<GetSDHContainerLinkStructureResponse>(_GetSDHContainerLinkStructureResponse_QNAME, GetSDHContainerLinkStructureResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetContactsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getContactsResponse")
    public JAXBElement<GetContactsResponse> createGetContactsResponse(GetContactsResponse value) {
        return new JAXBElement<GetContactsResponse>(_GetContactsResponse_QNAME, GetContactsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSpecialChildrenOfClassLight }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getSpecialChildrenOfClassLight")
    public JAXBElement<GetSpecialChildrenOfClassLight> createGetSpecialChildrenOfClassLight(GetSpecialChildrenOfClassLight value) {
        return new JAXBElement<GetSpecialChildrenOfClassLight>(_GetSpecialChildrenOfClassLight_QNAME, GetSpecialChildrenOfClassLight.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetQueryResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getQueryResponse")
    public JAXBElement<GetQueryResponse> createGetQueryResponse(GetQueryResponse value) {
        return new JAXBElement<GetQueryResponse>(_GetQueryResponse_QNAME, GetQueryResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateFileProperties }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "updateFileProperties")
    public JAXBElement<UpdateFileProperties> createUpdateFileProperties(UpdateFileProperties value) {
        return new JAXBElement<UpdateFileProperties>(_UpdateFileProperties_QNAME, UpdateFileProperties.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateProxyPoolResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "createProxyPoolResponse")
    public JAXBElement<CreateProxyPoolResponse> createCreateProxyPoolResponse(CreateProxyPoolResponse value) {
        return new JAXBElement<CreateProxyPoolResponse>(_CreateProxyPoolResponse_QNAME, CreateProxyPoolResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSubnetResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getSubnetResponse")
    public JAXBElement<GetSubnetResponse> createGetSubnetResponse(GetSubnetResponse value) {
        return new JAXBElement<GetSubnetResponse>(_GetSubnetResponse_QNAME, GetSubnetResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SetPrivilegeToUser }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "setPrivilegeToUser")
    public JAXBElement<SetPrivilegeToUser> createSetPrivilegeToUser(SetPrivilegeToUser value) {
        return new JAXBElement<SetPrivilegeToUser>(_SetPrivilegeToUser_QNAME, SetPrivilegeToUser.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteTemplateElement }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "deleteTemplateElement")
    public JAXBElement<DeleteTemplateElement> createDeleteTemplateElement(DeleteTemplateElement value) {
        return new JAXBElement<DeleteTemplateElement>(_DeleteTemplateElement_QNAME, DeleteTemplateElement.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetContractsInPool }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getContractsInPool")
    public JAXBElement<GetContractsInPool> createGetContractsInPool(GetContractsInPool value) {
        return new JAXBElement<GetContractsInPool>(_GetContractsInPool_QNAME, GetContractsInPool.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemovePrivilegeFromGroup }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "removePrivilegeFromGroup")
    public JAXBElement<RemovePrivilegeFromGroup> createRemovePrivilegeFromGroup(RemovePrivilegeFromGroup value) {
        return new JAXBElement<RemovePrivilegeFromGroup>(_RemovePrivilegeFromGroup_QNAME, RemovePrivilegeFromGroup.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateProjectPool }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "createProjectPool")
    public JAXBElement<CreateProjectPool> createCreateProjectPool(CreateProjectPool value) {
        return new JAXBElement<CreateProjectPool>(_CreateProjectPool_QNAME, CreateProjectPool.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetPhysicalConnectionEndpointsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getPhysicalConnectionEndpointsResponse")
    public JAXBElement<GetPhysicalConnectionEndpointsResponse> createGetPhysicalConnectionEndpointsResponse(GetPhysicalConnectionEndpointsResponse value) {
        return new JAXBElement<GetPhysicalConnectionEndpointsResponse>(_GetPhysicalConnectionEndpointsResponse_QNAME, GetPhysicalConnectionEndpointsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateProjectPoolResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "createProjectPoolResponse")
    public JAXBElement<CreateProjectPoolResponse> createCreateProjectPoolResponse(CreateProjectPoolResponse value) {
        return new JAXBElement<CreateProjectPoolResponse>(_CreateProjectPoolResponse_QNAME, CreateProjectPoolResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateContractPoolResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "createContractPoolResponse")
    public JAXBElement<CreateContractPoolResponse> createCreateContractPoolResponse(CreateContractPoolResponse value) {
        return new JAXBElement<CreateContractPoolResponse>(_CreateContractPoolResponse_QNAME, CreateContractPoolResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateProcessDefinition }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "createProcessDefinition")
    public JAXBElement<CreateProcessDefinition> createCreateProcessDefinition(CreateProcessDefinition value) {
        return new JAXBElement<CreateProcessDefinition>(_CreateProcessDefinition_QNAME, CreateProcessDefinition.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RelateObjectToContractResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "relateObjectToContractResponse")
    public JAXBElement<RelateObjectToContractResponse> createRelateObjectToContractResponse(RelateObjectToContractResponse value) {
        return new JAXBElement<RelateObjectToContractResponse>(_RelateObjectToContractResponse_QNAME, RelateObjectToContractResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetChildrenOfClassLight }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getChildrenOfClassLight")
    public JAXBElement<GetChildrenOfClassLight> createGetChildrenOfClassLight(GetChildrenOfClassLight value) {
        return new JAXBElement<GetChildrenOfClassLight>(_GetChildrenOfClassLight_QNAME, GetChildrenOfClassLight.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSubnetPools }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getSubnetPools")
    public JAXBElement<GetSubnetPools> createGetSubnetPools(GetSubnetPools value) {
        return new JAXBElement<GetSubnetPools>(_GetSubnetPools_QNAME, GetSubnetPools.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteCostumer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "deleteCostumer")
    public JAXBElement<DeleteCostumer> createDeleteCostumer(DeleteCostumer value) {
        return new JAXBElement<DeleteCostumer>(_DeleteCostumer_QNAME, DeleteCostumer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DisconnectPhysicalConnection }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "disconnectPhysicalConnection")
    public JAXBElement<DisconnectPhysicalConnection> createDisconnectPhysicalConnection(DisconnectPhysicalConnection value) {
        return new JAXBElement<DisconnectPhysicalConnection>(_DisconnectPhysicalConnection_QNAME, DisconnectPhysicalConnection.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ConnectMirrorPortResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "connectMirrorPortResponse")
    public JAXBElement<ConnectMirrorPortResponse> createConnectMirrorPortResponse(ConnectMirrorPortResponse value) {
        return new JAXBElement<ConnectMirrorPortResponse>(_ConnectMirrorPortResponse_QNAME, ConnectMirrorPortResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetObjectResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getObjectResponse")
    public JAXBElement<GetObjectResponse> createGetObjectResponse(GetObjectResponse value) {
        return new JAXBElement<GetObjectResponse>(_GetObjectResponse_QNAME, GetObjectResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReleaseSubnetFromVlan }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "releaseSubnetFromVlan")
    public JAXBElement<ReleaseSubnetFromVlan> createReleaseSubnetFromVlan(ReleaseSubnetFromVlan value) {
        return new JAXBElement<ReleaseSubnetFromVlan>(_ReleaseSubnetFromVlan_QNAME, ReleaseSubnetFromVlan.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetGroupsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getGroupsResponse")
    public JAXBElement<GetGroupsResponse> createGetGroupsResponse(GetGroupsResponse value) {
        return new JAXBElement<GetGroupsResponse>(_GetGroupsResponse_QNAME, GetGroupsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SetAttributeProperties }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "setAttributeProperties")
    public JAXBElement<SetAttributeProperties> createSetAttributeProperties(SetAttributeProperties value) {
        return new JAXBElement<SetAttributeProperties>(_SetAttributeProperties_QNAME, SetAttributeProperties.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindSDHRoutesUsingTransportLinksResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "findSDHRoutesUsingTransportLinksResponse")
    public JAXBElement<FindSDHRoutesUsingTransportLinksResponse> createFindSDHRoutesUsingTransportLinksResponse(FindSDHRoutesUsingTransportLinksResponse value) {
        return new JAXBElement<FindSDHRoutesUsingTransportLinksResponse>(_FindSDHRoutesUsingTransportLinksResponse_QNAME, FindSDHRoutesUsingTransportLinksResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ConnectMplsLink }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "connectMplsLink")
    public JAXBElement<ConnectMplsLink> createConnectMplsLink(ConnectMplsLink value) {
        return new JAXBElement<ConnectMplsLink>(_ConnectMplsLink_QNAME, ConnectMplsLink.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSubnetPoolResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getSubnetPoolResponse")
    public JAXBElement<GetSubnetPoolResponse> createGetSubnetPoolResponse(GetSubnetPoolResponse value) {
        return new JAXBElement<GetSubnetPoolResponse>(_GetSubnetPoolResponse_QNAME, GetSubnetPoolResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCustomerPool }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getCustomerPool")
    public JAXBElement<GetCustomerPool> createGetCustomerPool(GetCustomerPool value) {
        return new JAXBElement<GetCustomerPool>(_GetCustomerPool_QNAME, GetCustomerPool.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SetPoolPropertiesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "setPoolPropertiesResponse")
    public JAXBElement<SetPoolPropertiesResponse> createSetPoolPropertiesResponse(SetPoolPropertiesResponse value) {
        return new JAXBElement<SetPoolPropertiesResponse>(_SetPoolPropertiesResponse_QNAME, SetPoolPropertiesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetUsers }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getUsers")
    public JAXBElement<GetUsers> createGetUsers(GetUsers value) {
        return new JAXBElement<GetUsers>(_GetUsers_QNAME, GetUsers.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddPossibleChildren }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "addPossibleChildren")
    public JAXBElement<AddPossibleChildren> createAddPossibleChildren(AddPossibleChildren value) {
        return new JAXBElement<AddPossibleChildren>(_AddPossibleChildren_QNAME, AddPossibleChildren.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ConnectPhysicalLinks }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "connectPhysicalLinks")
    public JAXBElement<ConnectPhysicalLinks> createConnectPhysicalLinks(ConnectPhysicalLinks value) {
        return new JAXBElement<ConnectPhysicalLinks>(_ConnectPhysicalLinks_QNAME, ConnectPhysicalLinks.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateContact }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "createContact")
    public JAXBElement<CreateContact> createCreateContact(CreateContact value) {
        return new JAXBElement<CreateContact>(_CreateContact_QNAME, CreateContact.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReleaseSyncDataSourceConfigFromSyncGroupResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "releaseSyncDataSourceConfigFromSyncGroupResponse")
    public JAXBElement<ReleaseSyncDataSourceConfigFromSyncGroupResponse> createReleaseSyncDataSourceConfigFromSyncGroupResponse(ReleaseSyncDataSourceConfigFromSyncGroupResponse value) {
        return new JAXBElement<ReleaseSyncDataSourceConfigFromSyncGroupResponse>(_ReleaseSyncDataSourceConfigFromSyncGroupResponse_QNAME, ReleaseSyncDataSourceConfigFromSyncGroupResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddObjectsToFavoritesFolderResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "addObjectsToFavoritesFolderResponse")
    public JAXBElement<AddObjectsToFavoritesFolderResponse> createAddObjectsToFavoritesFolderResponse(AddObjectsToFavoritesFolderResponse value) {
        return new JAXBElement<AddObjectsToFavoritesFolderResponse>(_AddObjectsToFavoritesFolderResponse_QNAME, AddObjectsToFavoritesFolderResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetPhysicalPath }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getPhysicalPath")
    public JAXBElement<GetPhysicalPath> createGetPhysicalPath(GetPhysicalPath value) {
        return new JAXBElement<GetPhysicalPath>(_GetPhysicalPath_QNAME, GetPhysicalPath.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteGeneralViewResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "deleteGeneralViewResponse")
    public JAXBElement<DeleteGeneralViewResponse> createDeleteGeneralViewResponse(DeleteGeneralViewResponse value) {
        return new JAXBElement<DeleteGeneralViewResponse>(_DeleteGeneralViewResponse_QNAME, DeleteGeneralViewResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemovePrivilegeFromUser }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "removePrivilegeFromUser")
    public JAXBElement<RemovePrivilegeFromUser> createRemovePrivilegeFromUser(RemovePrivilegeFromUser value) {
        return new JAXBElement<RemovePrivilegeFromUser>(_RemovePrivilegeFromUser_QNAME, RemovePrivilegeFromUser.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetRootPoolsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getRootPoolsResponse")
    public JAXBElement<GetRootPoolsResponse> createGetRootPoolsResponse(GetRootPoolsResponse value) {
        return new JAXBElement<GetRootPoolsResponse>(_GetRootPoolsResponse_QNAME, GetRootPoolsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReleaseSubnetFromVlanResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "releaseSubnetFromVlanResponse")
    public JAXBElement<ReleaseSubnetFromVlanResponse> createReleaseSubnetFromVlanResponse(ReleaseSubnetFromVlanResponse value) {
        return new JAXBElement<ReleaseSubnetFromVlanResponse>(_ReleaseSubnetFromVlanResponse_QNAME, ReleaseSubnetFromVlanResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateBulkSpecialTemplateElementResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "createBulkSpecialTemplateElementResponse")
    public JAXBElement<CreateBulkSpecialTemplateElementResponse> createCreateBulkSpecialTemplateElementResponse(CreateBulkSpecialTemplateElementResponse value) {
        return new JAXBElement<CreateBulkSpecialTemplateElementResponse>(_CreateBulkSpecialTemplateElementResponse_QNAME, CreateBulkSpecialTemplateElementResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemovePrivilegeFromGroupResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "removePrivilegeFromGroupResponse")
    public JAXBElement<RemovePrivilegeFromGroupResponse> createRemovePrivilegeFromGroupResponse(RemovePrivilegeFromGroupResponse value) {
        return new JAXBElement<RemovePrivilegeFromGroupResponse>(_RemovePrivilegeFromGroupResponse_QNAME, RemovePrivilegeFromGroupResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ValidateSavedE2EView }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "validateSavedE2EView")
    public JAXBElement<ValidateSavedE2EView> createValidateSavedE2EView(ValidateSavedE2EView value) {
        return new JAXBElement<ValidateSavedE2EView>(_ValidateSavedE2EView_QNAME, ValidateSavedE2EView.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReleaseObjectFromProxy }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "releaseObjectFromProxy")
    public JAXBElement<ReleaseObjectFromProxy> createReleaseObjectFromProxy(ReleaseObjectFromProxy value) {
        return new JAXBElement<ReleaseObjectFromProxy>(_ReleaseObjectFromProxy_QNAME, ReleaseObjectFromProxy.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteConfigurationVariableResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "deleteConfigurationVariableResponse")
    public JAXBElement<DeleteConfigurationVariableResponse> createDeleteConfigurationVariableResponse(DeleteConfigurationVariableResponse value) {
        return new JAXBElement<DeleteConfigurationVariableResponse>(_DeleteConfigurationVariableResponse_QNAME, DeleteConfigurationVariableResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteProjectPool }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "deleteProjectPool")
    public JAXBElement<DeleteProjectPool> createDeleteProjectPool(DeleteProjectPool value) {
        return new JAXBElement<DeleteProjectPool>(_DeleteProjectPool_QNAME, DeleteProjectPool.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetGeneralActivityAuditTrailResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getGeneralActivityAuditTrailResponse")
    public JAXBElement<GetGeneralActivityAuditTrailResponse> createGetGeneralActivityAuditTrailResponse(GetGeneralActivityAuditTrailResponse value) {
        return new JAXBElement<GetGeneralActivityAuditTrailResponse>(_GetGeneralActivityAuditTrailResponse_QNAME, GetGeneralActivityAuditTrailResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DisconnectMPLSLinkResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "disconnectMPLSLinkResponse")
    public JAXBElement<DisconnectMPLSLinkResponse> createDisconnectMPLSLinkResponse(DisconnectMPLSLinkResponse value) {
        return new JAXBElement<DisconnectMPLSLinkResponse>(_DisconnectMPLSLinkResponse_QNAME, DisconnectMPLSLinkResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTemplateElement }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getTemplateElement")
    public JAXBElement<GetTemplateElement> createGetTemplateElement(GetTemplateElement value) {
        return new JAXBElement<GetTemplateElement>(_GetTemplateElement_QNAME, GetTemplateElement.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteSDHTributaryLinkResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "deleteSDHTributaryLinkResponse")
    public JAXBElement<DeleteSDHTributaryLinkResponse> createDeleteSDHTributaryLinkResponse(DeleteSDHTributaryLinkResponse value) {
        return new JAXBElement<DeleteSDHTributaryLinkResponse>(_DeleteSDHTributaryLinkResponse_QNAME, DeleteSDHTributaryLinkResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetServicesInPool }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getServicesInPool")
    public JAXBElement<GetServicesInPool> createGetServicesInPool(GetServicesInPool value) {
        return new JAXBElement<GetServicesInPool>(_GetServicesInPool_QNAME, GetServicesInPool.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemoveIP }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "removeIP")
    public JAXBElement<RemoveIP> createRemoveIP(RemoveIP value) {
        return new JAXBElement<RemoveIP>(_RemoveIP_QNAME, RemoveIP.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RelateObjectToProjectResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "relateObjectToProjectResponse")
    public JAXBElement<RelateObjectToProjectResponse> createRelateObjectToProjectResponse(RelateObjectToProjectResponse value) {
        return new JAXBElement<RelateObjectToProjectResponse>(_RelateObjectToProjectResponse_QNAME, RelateObjectToProjectResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateSession }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "createSession")
    public JAXBElement<CreateSession> createCreateSession(CreateSession value) {
        return new JAXBElement<CreateSession>(_CreateSession_QNAME, CreateSession.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteConfigurationVariablesPool }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "deleteConfigurationVariablesPool")
    public JAXBElement<DeleteConfigurationVariablesPool> createDeleteConfigurationVariablesPool(DeleteConfigurationVariablesPool value) {
        return new JAXBElement<DeleteConfigurationVariablesPool>(_DeleteConfigurationVariablesPool_QNAME, DeleteConfigurationVariablesPool.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteContractPoolResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "deleteContractPoolResponse")
    public JAXBElement<DeleteContractPoolResponse> createDeleteContractPoolResponse(DeleteContractPoolResponse value) {
        return new JAXBElement<DeleteContractPoolResponse>(_DeleteContractPoolResponse_QNAME, DeleteContractPoolResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateContractPool }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "updateContractPool")
    public JAXBElement<UpdateContractPool> createUpdateContractPool(UpdateContractPool value) {
        return new JAXBElement<UpdateContractPool>(_UpdateContractPool_QNAME, UpdateContractPool.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateListTypeItem }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "updateListTypeItem")
    public JAXBElement<UpdateListTypeItem> createUpdateListTypeItem(UpdateListTypeItem value) {
        return new JAXBElement<UpdateListTypeItem>(_UpdateListTypeItem_QNAME, UpdateListTypeItem.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetObjectsOfClassLight }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getObjectsOfClassLight")
    public JAXBElement<GetObjectsOfClassLight> createGetObjectsOfClassLight(GetObjectsOfClassLight value) {
        return new JAXBElement<GetObjectsOfClassLight>(_GetObjectsOfClassLight_QNAME, GetObjectsOfClassLight.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SetGroupPropertiesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "setGroupPropertiesResponse")
    public JAXBElement<SetGroupPropertiesResponse> createSetGroupPropertiesResponse(SetGroupPropertiesResponse value) {
        return new JAXBElement<SetGroupPropertiesResponse>(_SetGroupPropertiesResponse_QNAME, SetGroupPropertiesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetPoolsInWarehouseResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getPoolsInWarehouseResponse")
    public JAXBElement<GetPoolsInWarehouseResponse> createGetPoolsInWarehouseResponse(GetPoolsInWarehouseResponse value) {
        return new JAXBElement<GetPoolsInWarehouseResponse>(_GetPoolsInWarehouseResponse_QNAME, GetPoolsInWarehouseResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetClassWithId }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getClassWithId")
    public JAXBElement<GetClassWithId> createGetClassWithId(GetClassWithId value) {
        return new JAXBElement<GetClassWithId>(_GetClassWithId_QNAME, GetClassWithId.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetServicePool }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getServicePool")
    public JAXBElement<GetServicePool> createGetServicePool(GetServicePool value) {
        return new JAXBElement<GetServicePool>(_GetServicePool_QNAME, GetServicePool.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateObjectRelatedView }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "createObjectRelatedView")
    public JAXBElement<CreateObjectRelatedView> createCreateObjectRelatedView(CreateObjectRelatedView value) {
        return new JAXBElement<CreateObjectRelatedView>(_CreateObjectRelatedView_QNAME, CreateObjectRelatedView.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetActivity }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getActivity")
    public JAXBElement<GetActivity> createGetActivity(GetActivity value) {
        return new JAXBElement<GetActivity>(_GetActivity_QNAME, GetActivity.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllProjectsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getAllProjectsResponse")
    public JAXBElement<GetAllProjectsResponse> createGetAllProjectsResponse(GetAllProjectsResponse value) {
        return new JAXBElement<GetAllProjectsResponse>(_GetAllProjectsResponse_QNAME, GetAllProjectsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SetGroupProperties }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "setGroupProperties")
    public JAXBElement<SetGroupProperties> createSetGroupProperties(SetGroupProperties value) {
        return new JAXBElement<SetGroupProperties>(_SetGroupProperties_QNAME, SetGroupProperties.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSubnet }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getSubnet")
    public JAXBElement<GetSubnet> createGetSubnet(GetSubnet value) {
        return new JAXBElement<GetSubnet>(_GetSubnet_QNAME, GetSubnet.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateProxyResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "createProxyResponse")
    public JAXBElement<CreateProxyResponse> createCreateProxyResponse(CreateProxyResponse value) {
        return new JAXBElement<CreateProxyResponse>(_CreateProxyResponse_QNAME, CreateProxyResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteMPLSLinkResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "deleteMPLSLinkResponse")
    public JAXBElement<DeleteMPLSLinkResponse> createDeleteMPLSLinkResponse(DeleteMPLSLinkResponse value) {
        return new JAXBElement<DeleteMPLSLinkResponse>(_DeleteMPLSLinkResponse_QNAME, DeleteMPLSLinkResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetUpstreamSpecialContainmentHierarchy }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getUpstreamSpecialContainmentHierarchy")
    public JAXBElement<GetUpstreamSpecialContainmentHierarchy> createGetUpstreamSpecialContainmentHierarchy(GetUpstreamSpecialContainmentHierarchy value) {
        return new JAXBElement<GetUpstreamSpecialContainmentHierarchy>(_GetUpstreamSpecialContainmentHierarchy_QNAME, GetUpstreamSpecialContainmentHierarchy.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCustomerPoolsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getCustomerPoolsResponse")
    public JAXBElement<GetCustomerPoolsResponse> createGetCustomerPoolsResponse(GetCustomerPoolsResponse value) {
        return new JAXBElement<GetCustomerPoolsResponse>(_GetCustomerPoolsResponse_QNAME, GetCustomerPoolsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSubClassesLightResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getSubClassesLightResponse")
    public JAXBElement<GetSubClassesLightResponse> createGetSubClassesLightResponse(GetSubClassesLightResponse value) {
        return new JAXBElement<GetSubClassesLightResponse>(_GetSubClassesLightResponse_QNAME, GetSubClassesLightResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAttributeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getAttributeResponse")
    public JAXBElement<GetAttributeResponse> createGetAttributeResponse(GetAttributeResponse value) {
        return new JAXBElement<GetAttributeResponse>(_GetAttributeResponse_QNAME, GetAttributeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateFavoritesFolderForUserResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "createFavoritesFolderForUserResponse")
    public JAXBElement<CreateFavoritesFolderForUserResponse> createCreateFavoritesFolderForUserResponse(CreateFavoritesFolderForUserResponse value) {
        return new JAXBElement<CreateFavoritesFolderForUserResponse>(_CreateFavoritesFolderForUserResponse_QNAME, CreateFavoritesFolderForUserResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateServiceResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "updateServiceResponse")
    public JAXBElement<UpdateServiceResponse> createUpdateServiceResponse(UpdateServiceResponse value) {
        return new JAXBElement<UpdateServiceResponse>(_UpdateServiceResponse_QNAME, UpdateServiceResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSubnetPool }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getSubnetPool")
    public JAXBElement<GetSubnetPool> createGetSubnetPool(GetSubnetPool value) {
        return new JAXBElement<GetSubnetPool>(_GetSubnetPool_QNAME, GetSubnetPool.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSynchronizationGroupResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getSynchronizationGroupResponse")
    public JAXBElement<GetSynchronizationGroupResponse> createGetSynchronizationGroupResponse(GetSynchronizationGroupResponse value) {
        return new JAXBElement<GetSynchronizationGroupResponse>(_GetSynchronizationGroupResponse_QNAME, GetSynchronizationGroupResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteQuery }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "deleteQuery")
    public JAXBElement<DeleteQuery> createDeleteQuery(DeleteQuery value) {
        return new JAXBElement<DeleteQuery>(_DeleteQuery_QNAME, DeleteQuery.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSyncDataSourceConfiguration }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getSyncDataSourceConfiguration")
    public JAXBElement<GetSyncDataSourceConfiguration> createGetSyncDataSourceConfiguration(GetSyncDataSourceConfiguration value) {
        return new JAXBElement<GetSyncDataSourceConfiguration>(_GetSyncDataSourceConfiguration_QNAME, GetSyncDataSourceConfiguration.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CopySpecialObjectsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "copySpecialObjectsResponse")
    public JAXBElement<CopySpecialObjectsResponse> createCopySpecialObjectsResponse(CopySpecialObjectsResponse value) {
        return new JAXBElement<CopySpecialObjectsResponse>(_CopySpecialObjectsResponse_QNAME, CopySpecialObjectsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ExecuteSyncActions }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "executeSyncActions")
    public JAXBElement<ExecuteSyncActions> createExecuteSyncActions(ExecuteSyncActions value) {
        return new JAXBElement<ExecuteSyncActions>(_ExecuteSyncActions_QNAME, ExecuteSyncActions.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetBGPMapResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getBGPMapResponse")
    public JAXBElement<GetBGPMapResponse> createGetBGPMapResponse(GetBGPMapResponse value) {
        return new JAXBElement<GetBGPMapResponse>(_GetBGPMapResponse_QNAME, GetBGPMapResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CopySyncDataSourceConfigurationResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "copySyncDataSourceConfigurationResponse")
    public JAXBElement<CopySyncDataSourceConfigurationResponse> createCopySyncDataSourceConfigurationResponse(CopySyncDataSourceConfigurationResponse value) {
        return new JAXBElement<CopySyncDataSourceConfigurationResponse>(_CopySyncDataSourceConfigurationResponse_QNAME, CopySyncDataSourceConfigurationResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateBulkObjectsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "createBulkObjectsResponse")
    public JAXBElement<CreateBulkObjectsResponse> createCreateBulkObjectsResponse(CreateBulkObjectsResponse value) {
        return new JAXBElement<CreateBulkObjectsResponse>(_CreateBulkObjectsResponse_QNAME, CreateBulkObjectsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetPossibleChildrenNoRecursiveResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getPossibleChildrenNoRecursiveResponse")
    public JAXBElement<GetPossibleChildrenNoRecursiveResponse> createGetPossibleChildrenNoRecursiveResponse(GetPossibleChildrenNoRecursiveResponse value) {
        return new JAXBElement<GetPossibleChildrenNoRecursiveResponse>(_GetPossibleChildrenNoRecursiveResponse_QNAME, GetPossibleChildrenNoRecursiveResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTasksForUser }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getTasksForUser")
    public JAXBElement<GetTasksForUser> createGetTasksForUser(GetTasksForUser value) {
        return new JAXBElement<GetTasksForUser>(_GetTasksForUser_QNAME, GetTasksForUser.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteClassWithIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "deleteClassWithIdResponse")
    public JAXBElement<DeleteClassWithIdResponse> createDeleteClassWithIdResponse(DeleteClassWithIdResponse value) {
        return new JAXBElement<DeleteClassWithIdResponse>(_DeleteClassWithIdResponse_QNAME, DeleteClassWithIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetParent }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getParent")
    public JAXBElement<GetParent> createGetParent(GetParent value) {
        return new JAXBElement<GetParent>(_GetParent_QNAME, GetParent.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllContractsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getAllContractsResponse")
    public JAXBElement<GetAllContractsResponse> createGetAllContractsResponse(GetAllContractsResponse value) {
        return new JAXBElement<GetAllContractsResponse>(_GetAllContractsResponse_QNAME, GetAllContractsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CopyTemplateSpecialElementsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "copyTemplateSpecialElementsResponse")
    public JAXBElement<CopyTemplateSpecialElementsResponse> createCopyTemplateSpecialElementsResponse(CopyTemplateSpecialElementsResponse value) {
        return new JAXBElement<CopyTemplateSpecialElementsResponse>(_CopyTemplateSpecialElementsResponse_QNAME, CopyTemplateSpecialElementsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetWarehousesInPool }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getWarehousesInPool")
    public JAXBElement<GetWarehousesInPool> createGetWarehousesInPool(GetWarehousesInPool value) {
        return new JAXBElement<GetWarehousesInPool>(_GetWarehousesInPool_QNAME, GetWarehousesInPool.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DisconnectPhysicalConnectionResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "disconnectPhysicalConnectionResponse")
    public JAXBElement<DisconnectPhysicalConnectionResponse> createDisconnectPhysicalConnectionResponse(DisconnectPhysicalConnectionResponse value) {
        return new JAXBElement<DisconnectPhysicalConnectionResponse>(_DisconnectPhysicalConnectionResponse_QNAME, DisconnectPhysicalConnectionResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteOSPView }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "deleteOSPView")
    public JAXBElement<DeleteOSPView> createDeleteOSPView(DeleteOSPView value) {
        return new JAXBElement<DeleteOSPView>(_DeleteOSPView_QNAME, DeleteOSPView.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RelateObjectsToProject }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "relateObjectsToProject")
    public JAXBElement<RelateObjectsToProject> createRelateObjectsToProject(RelateObjectsToProject value) {
        return new JAXBElement<RelateObjectsToProject>(_RelateObjectsToProject_QNAME, RelateObjectsToProject.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateProject }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "updateProject")
    public JAXBElement<UpdateProject> createUpdateProject(UpdateProject value) {
        return new JAXBElement<UpdateProject>(_UpdateProject_QNAME, UpdateProject.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateSpecialObject }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "createSpecialObject")
    public JAXBElement<CreateSpecialObject> createCreateSpecialObject(CreateSpecialObject value) {
        return new JAXBElement<CreateSpecialObject>(_CreateSpecialObject_QNAME, CreateSpecialObject.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetValidatorDefinitionsForClass }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getValidatorDefinitionsForClass")
    public JAXBElement<GetValidatorDefinitionsForClass> createGetValidatorDefinitionsForClass(GetValidatorDefinitionsForClass value) {
        return new JAXBElement<GetValidatorDefinitionsForClass>(_GetValidatorDefinitionsForClass_QNAME, GetValidatorDefinitionsForClass.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DetachFileFromObjectResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "detachFileFromObjectResponse")
    public JAXBElement<DetachFileFromObjectResponse> createDetachFileFromObjectResponse(DetachFileFromObjectResponse value) {
        return new JAXBElement<DetachFileFromObjectResponse>(_DetachFileFromObjectResponse_QNAME, DetachFileFromObjectResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetObjectsInFavoritesFolderResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getObjectsInFavoritesFolderResponse")
    public JAXBElement<GetObjectsInFavoritesFolderResponse> createGetObjectsInFavoritesFolderResponse(GetObjectsInFavoritesFolderResponse value) {
        return new JAXBElement<GetObjectsInFavoritesFolderResponse>(_GetObjectsInFavoritesFolderResponse_QNAME, GetObjectsInFavoritesFolderResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CopyTemplateSpecialElements }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "copyTemplateSpecialElements")
    public JAXBElement<CopyTemplateSpecialElements> createCopyTemplateSpecialElements(CopyTemplateSpecialElements value) {
        return new JAXBElement<CopyTemplateSpecialElements>(_CopyTemplateSpecialElements_QNAME, CopyTemplateSpecialElements.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetProxyPoolsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getProxyPoolsResponse")
    public JAXBElement<GetProxyPoolsResponse> createGetProxyPoolsResponse(GetProxyPoolsResponse value) {
        return new JAXBElement<GetProxyPoolsResponse>(_GetProxyPoolsResponse_QNAME, GetProxyPoolsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteProcessDefinitionResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "deleteProcessDefinitionResponse")
    public JAXBElement<DeleteProcessDefinitionResponse> createDeleteProcessDefinitionResponse(DeleteProcessDefinitionResponse value) {
        return new JAXBElement<DeleteProcessDefinitionResponse>(_DeleteProcessDefinitionResponse_QNAME, DeleteProcessDefinitionResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateProxyResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "updateProxyResponse")
    public JAXBElement<UpdateProxyResponse> createUpdateProxyResponse(UpdateProxyResponse value) {
        return new JAXBElement<UpdateProxyResponse>(_UpdateProxyResponse_QNAME, UpdateProxyResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteClassWithId }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "deleteClassWithId")
    public JAXBElement<DeleteClassWithId> createDeleteClassWithId(DeleteClassWithId value) {
        return new JAXBElement<DeleteClassWithId>(_DeleteClassWithId_QNAME, DeleteClassWithId.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteAttribute }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "deleteAttribute")
    public JAXBElement<DeleteAttribute> createDeleteAttribute(DeleteAttribute value) {
        return new JAXBElement<DeleteAttribute>(_DeleteAttribute_QNAME, DeleteAttribute.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllConfigurationVariables }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getAllConfigurationVariables")
    public JAXBElement<GetAllConfigurationVariables> createGetAllConfigurationVariables(GetAllConfigurationVariables value) {
        return new JAXBElement<GetAllConfigurationVariables>(_GetAllConfigurationVariables_QNAME, GetAllConfigurationVariables.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetProjectResources }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getProjectResources")
    public JAXBElement<GetProjectResources> createGetProjectResources(GetProjectResources value) {
        return new JAXBElement<GetProjectResources>(_GetProjectResources_QNAME, GetProjectResources.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReleaseSyncDataSourceConfigFromSyncGroup }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "releaseSyncDataSourceConfigFromSyncGroup")
    public JAXBElement<ReleaseSyncDataSourceConfigFromSyncGroup> createReleaseSyncDataSourceConfigFromSyncGroup(ReleaseSyncDataSourceConfigFromSyncGroup value) {
        return new JAXBElement<ReleaseSyncDataSourceConfigFromSyncGroup>(_ReleaseSyncDataSourceConfigFromSyncGroup_QNAME, ReleaseSyncDataSourceConfigFromSyncGroup.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllProxiesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getAllProxiesResponse")
    public JAXBElement<GetAllProxiesResponse> createGetAllProxiesResponse(GetAllProxiesResponse value) {
        return new JAXBElement<GetAllProxiesResponse>(_GetAllProxiesResponse_QNAME, GetAllProxiesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateBulkTemplateElement }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "createBulkTemplateElement")
    public JAXBElement<CreateBulkTemplateElement> createCreateBulkTemplateElement(CreateBulkTemplateElement value) {
        return new JAXBElement<CreateBulkTemplateElement>(_CreateBulkTemplateElement_QNAME, CreateBulkTemplateElement.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetInventoryLevelReports }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getInventoryLevelReports")
    public JAXBElement<GetInventoryLevelReports> createGetInventoryLevelReports(GetInventoryLevelReports value) {
        return new JAXBElement<GetInventoryLevelReports>(_GetInventoryLevelReports_QNAME, GetInventoryLevelReports.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateConfigurationVariableResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "createConfigurationVariableResponse")
    public JAXBElement<CreateConfigurationVariableResponse> createCreateConfigurationVariableResponse(CreateConfigurationVariableResponse value) {
        return new JAXBElement<CreateConfigurationVariableResponse>(_CreateConfigurationVariableResponse_QNAME, CreateConfigurationVariableResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSubClassesLight }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getSubClassesLight")
    public JAXBElement<GetSubClassesLight> createGetSubClassesLight(GetSubClassesLight value) {
        return new JAXBElement<GetSubClassesLight>(_GetSubClassesLight_QNAME, GetSubClassesLight.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSubnetUsedIpsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getSubnetUsedIpsResponse")
    public JAXBElement<GetSubnetUsedIpsResponse> createGetSubnetUsedIpsResponse(GetSubnetUsedIpsResponse value) {
        return new JAXBElement<GetSubnetUsedIpsResponse>(_GetSubnetUsedIpsResponse_QNAME, GetSubnetUsedIpsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreatePhysicalConnection }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "createPhysicalConnection")
    public JAXBElement<CreatePhysicalConnection> createCreatePhysicalConnection(CreatePhysicalConnection value) {
        return new JAXBElement<CreatePhysicalConnection>(_CreatePhysicalConnection_QNAME, CreatePhysicalConnection.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetUpstreamContainmentHierarchyResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getUpstreamContainmentHierarchyResponse")
    public JAXBElement<GetUpstreamContainmentHierarchyResponse> createGetUpstreamContainmentHierarchyResponse(GetUpstreamContainmentHierarchyResponse value) {
        return new JAXBElement<GetUpstreamContainmentHierarchyResponse>(_GetUpstreamContainmentHierarchyResponse_QNAME, GetUpstreamContainmentHierarchyResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateSDHTransportLink }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "createSDHTransportLink")
    public JAXBElement<CreateSDHTransportLink> createCreateSDHTransportLink(CreateSDHTransportLink value) {
        return new JAXBElement<CreateSDHTransportLink>(_CreateSDHTransportLink_QNAME, CreateSDHTransportLink.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateReport }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "updateReport")
    public JAXBElement<UpdateReport> createUpdateReport(UpdateReport value) {
        return new JAXBElement<UpdateReport>(_UpdateReport_QNAME, UpdateReport.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateListTypeItemResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "createListTypeItemResponse")
    public JAXBElement<CreateListTypeItemResponse> createCreateListTypeItemResponse(CreateListTypeItemResponse value) {
        return new JAXBElement<CreateListTypeItemResponse>(_CreateListTypeItemResponse_QNAME, CreateListTypeItemResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateConfigurationVariablesPool }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "createConfigurationVariablesPool")
    public JAXBElement<CreateConfigurationVariablesPool> createCreateConfigurationVariablesPool(CreateConfigurationVariablesPool value) {
        return new JAXBElement<CreateConfigurationVariablesPool>(_CreateConfigurationVariablesPool_QNAME, CreateConfigurationVariablesPool.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetParentOfClassResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getParentOfClassResponse")
    public JAXBElement<GetParentOfClassResponse> createGetParentOfClassResponse(GetParentOfClassResponse value) {
        return new JAXBElement<GetParentOfClassResponse>(_GetParentOfClassResponse_QNAME, GetParentOfClassResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ConnectMirrorPort }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "connectMirrorPort")
    public JAXBElement<ConnectMirrorPort> createConnectMirrorPort(ConnectMirrorPort value) {
        return new JAXBElement<ConnectMirrorPort>(_ConnectMirrorPort_QNAME, ConnectMirrorPort.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateSynchronizationDataSourceConfig }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "createSynchronizationDataSourceConfig")
    public JAXBElement<CreateSynchronizationDataSourceConfig> createCreateSynchronizationDataSourceConfig(CreateSynchronizationDataSourceConfig value) {
        return new JAXBElement<CreateSynchronizationDataSourceConfig>(_CreateSynchronizationDataSourceConfig_QNAME, CreateSynchronizationDataSourceConfig.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RunValidationsForObjectResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "runValidationsForObjectResponse")
    public JAXBElement<RunValidationsForObjectResponse> createRunValidationsForObjectResponse(RunValidationsForObjectResponse value) {
        return new JAXBElement<RunValidationsForObjectResponse>(_RunValidationsForObjectResponse_QNAME, RunValidationsForObjectResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateSyncDataSourceConfiguration }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "updateSyncDataSourceConfiguration")
    public JAXBElement<UpdateSyncDataSourceConfiguration> createUpdateSyncDataSourceConfiguration(UpdateSyncDataSourceConfiguration value) {
        return new JAXBElement<UpdateSyncDataSourceConfiguration>(_UpdateSyncDataSourceConfiguration_QNAME, UpdateSyncDataSourceConfiguration.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ExecuteTask }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "executeTask")
    public JAXBElement<ExecuteTask> createExecuteTask(ExecuteTask value) {
        return new JAXBElement<ExecuteTask>(_ExecuteTask_QNAME, ExecuteTask.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SearchForContacts }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "searchForContacts")
    public JAXBElement<SearchForContacts> createSearchForContacts(SearchForContacts value) {
        return new JAXBElement<SearchForContacts>(_SearchForContacts_QNAME, SearchForContacts.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteObject }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "deleteObject")
    public JAXBElement<DeleteObject> createDeleteObject(DeleteObject value) {
        return new JAXBElement<DeleteObject>(_DeleteObject_QNAME, DeleteObject.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetObjectLightResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getObjectLightResponse")
    public JAXBElement<GetObjectLightResponse> createGetObjectLightResponse(GetObjectLightResponse value) {
        return new JAXBElement<GetObjectLightResponse>(_GetObjectLightResponse_QNAME, GetObjectLightResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetObjectsInSparePoolResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getObjectsInSparePoolResponse")
    public JAXBElement<GetObjectsInSparePoolResponse> createGetObjectsInSparePoolResponse(GetObjectsInSparePoolResponse value) {
        return new JAXBElement<GetObjectsInSparePoolResponse>(_GetObjectsInSparePoolResponse_QNAME, GetObjectsInSparePoolResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetQuery }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getQuery")
    public JAXBElement<GetQuery> createGetQuery(GetQuery value) {
        return new JAXBElement<GetQuery>(_GetQuery_QNAME, GetQuery.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSubClassesLightNoRecursiveResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getSubClassesLightNoRecursiveResponse")
    public JAXBElement<GetSubClassesLightNoRecursiveResponse> createGetSubClassesLightNoRecursiveResponse(GetSubClassesLightNoRecursiveResponse value) {
        return new JAXBElement<GetSubClassesLightNoRecursiveResponse>(_GetSubClassesLightNoRecursiveResponse_QNAME, GetSubClassesLightNoRecursiveResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReleasePortFromInterfaceResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "releasePortFromInterfaceResponse")
    public JAXBElement<ReleasePortFromInterfaceResponse> createReleasePortFromInterfaceResponse(ReleasePortFromInterfaceResponse value) {
        return new JAXBElement<ReleasePortFromInterfaceResponse>(_ReleasePortFromInterfaceResponse_QNAME, ReleasePortFromInterfaceResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateCustomerPoolResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "updateCustomerPoolResponse")
    public JAXBElement<UpdateCustomerPoolResponse> createUpdateCustomerPoolResponse(UpdateCustomerPoolResponse value) {
        return new JAXBElement<UpdateCustomerPoolResponse>(_UpdateCustomerPoolResponse_QNAME, UpdateCustomerPoolResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteProxyPoolResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "deleteProxyPoolResponse")
    public JAXBElement<DeleteProxyPoolResponse> createDeleteProxyPoolResponse(DeleteProxyPoolResponse value) {
        return new JAXBElement<DeleteProxyPoolResponse>(_DeleteProxyPoolResponse_QNAME, DeleteProxyPoolResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AttachFileToObject }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "attachFileToObject")
    public JAXBElement<AttachFileToObject> createAttachFileToObject(AttachFileToObject value) {
        return new JAXBElement<AttachFileToObject>(_AttachFileToObject_QNAME, AttachFileToObject.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddUserToGroupResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "addUserToGroupResponse")
    public JAXBElement<AddUserToGroupResponse> createAddUserToGroupResponse(AddUserToGroupResponse value) {
        return new JAXBElement<AddUserToGroupResponse>(_AddUserToGroupResponse_QNAME, AddUserToGroupResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetChildrenOfClassLightRecursiveResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getChildrenOfClassLightRecursiveResponse")
    public JAXBElement<GetChildrenOfClassLightRecursiveResponse> createGetChildrenOfClassLightRecursiveResponse(GetChildrenOfClassLightRecursiveResponse value) {
        return new JAXBElement<GetChildrenOfClassLightRecursiveResponse>(_GetChildrenOfClassLightRecursiveResponse_QNAME, GetChildrenOfClassLightRecursiveResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteSynchronizationDataSourceConfigResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "deleteSynchronizationDataSourceConfigResponse")
    public JAXBElement<DeleteSynchronizationDataSourceConfigResponse> createDeleteSynchronizationDataSourceConfigResponse(DeleteSynchronizationDataSourceConfigResponse value) {
        return new JAXBElement<DeleteSynchronizationDataSourceConfigResponse>(_DeleteSynchronizationDataSourceConfigResponse_QNAME, DeleteSynchronizationDataSourceConfigResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSubnets }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getSubnets")
    public JAXBElement<GetSubnets> createGetSubnets(GetSubnets value) {
        return new JAXBElement<GetSubnets>(_GetSubnets_QNAME, GetSubnets.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetObjectsWithFilterLight }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getObjectsWithFilterLight")
    public JAXBElement<GetObjectsWithFilterLight> createGetObjectsWithFilterLight(GetObjectsWithFilterLight value) {
        return new JAXBElement<GetObjectsWithFilterLight>(_GetObjectsWithFilterLight_QNAME, GetObjectsWithFilterLight.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteContactResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "deleteContactResponse")
    public JAXBElement<DeleteContactResponse> createDeleteContactResponse(DeleteContactResponse value) {
        return new JAXBElement<DeleteContactResponse>(_DeleteContactResponse_QNAME, DeleteContactResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetUpstreamContainmentHierarchy }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getUpstreamContainmentHierarchy")
    public JAXBElement<GetUpstreamContainmentHierarchy> createGetUpstreamContainmentHierarchy(GetUpstreamContainmentHierarchy value) {
        return new JAXBElement<GetUpstreamContainmentHierarchy>(_GetUpstreamContainmentHierarchy_QNAME, GetUpstreamContainmentHierarchy.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateListTypeItemRelatedView }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "updateListTypeItemRelatedView")
    public JAXBElement<UpdateListTypeItemRelatedView> createUpdateListTypeItemRelatedView(UpdateListTypeItemRelatedView value) {
        return new JAXBElement<UpdateListTypeItemRelatedView>(_UpdateListTypeItemRelatedView_QNAME, UpdateListTypeItemRelatedView.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetChildrenOfClassLightRecursive }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getChildrenOfClassLightRecursive")
    public JAXBElement<GetChildrenOfClassLightRecursive> createGetChildrenOfClassLightRecursive(GetChildrenOfClassLightRecursive value) {
        return new JAXBElement<GetChildrenOfClassLightRecursive>(_GetChildrenOfClassLightRecursive_QNAME, GetChildrenOfClassLightRecursive.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreatePoolInObject }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "createPoolInObject")
    public JAXBElement<CreatePoolInObject> createCreatePoolInObject(CreatePoolInObject value) {
        return new JAXBElement<CreatePoolInObject>(_CreatePoolInObject_QNAME, CreatePoolInObject.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetObjectRelatedViewsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getObjectRelatedViewsResponse")
    public JAXBElement<GetObjectRelatedViewsResponse> createGetObjectRelatedViewsResponse(GetObjectRelatedViewsResponse value) {
        return new JAXBElement<GetObjectRelatedViewsResponse>(_GetObjectRelatedViewsResponse_QNAME, GetObjectRelatedViewsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreatePhysicalConnections }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "createPhysicalConnections")
    public JAXBElement<CreatePhysicalConnections> createCreatePhysicalConnections(CreatePhysicalConnections value) {
        return new JAXBElement<CreatePhysicalConnections>(_CreatePhysicalConnections_QNAME, CreatePhysicalConnections.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCustomerPoolResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getCustomerPoolResponse")
    public JAXBElement<GetCustomerPoolResponse> createGetCustomerPoolResponse(GetCustomerPoolResponse value) {
        return new JAXBElement<GetCustomerPoolResponse>(_GetCustomerPoolResponse_QNAME, GetCustomerPoolResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemoveIPResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "removeIPResponse")
    public JAXBElement<RemoveIPResponse> createRemoveIPResponse(RemoveIPResponse value) {
        return new JAXBElement<RemoveIPResponse>(_RemoveIPResponse_QNAME, RemoveIPResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteWarehouse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "deleteWarehouse")
    public JAXBElement<DeleteWarehouse> createDeleteWarehouse(DeleteWarehouse value) {
        return new JAXBElement<DeleteWarehouse>(_DeleteWarehouse_QNAME, DeleteWarehouse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetConfigurationVariableValue }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getConfigurationVariableValue")
    public JAXBElement<GetConfigurationVariableValue> createGetConfigurationVariableValue(GetConfigurationVariableValue value) {
        return new JAXBElement<GetConfigurationVariableValue>(_GetConfigurationVariableValue_QNAME, GetConfigurationVariableValue.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetValidatorDefinitionsForClassResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getValidatorDefinitionsForClassResponse")
    public JAXBElement<GetValidatorDefinitionsForClassResponse> createGetValidatorDefinitionsForClassResponse(GetValidatorDefinitionsForClassResponse value) {
        return new JAXBElement<GetValidatorDefinitionsForClassResponse>(_GetValidatorDefinitionsForClassResponse_QNAME, GetValidatorDefinitionsForClassResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteConfigurationVariablesPoolResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "deleteConfigurationVariablesPoolResponse")
    public JAXBElement<DeleteConfigurationVariablesPoolResponse> createDeleteConfigurationVariablesPoolResponse(DeleteConfigurationVariablesPoolResponse value) {
        return new JAXBElement<DeleteConfigurationVariablesPoolResponse>(_DeleteConfigurationVariablesPoolResponse_QNAME, DeleteConfigurationVariablesPoolResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetProjectsInPoolResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getProjectsInPoolResponse")
    public JAXBElement<GetProjectsInPoolResponse> createGetProjectsInPoolResponse(GetProjectsInPoolResponse value) {
        return new JAXBElement<GetProjectsInPoolResponse>(_GetProjectsInPoolResponse_QNAME, GetProjectsInPoolResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAffectedServices }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getAffectedServices")
    public JAXBElement<GetAffectedServices> createGetAffectedServices(GetAffectedServices value) {
        return new JAXBElement<GetAffectedServices>(_GetAffectedServices_QNAME, GetAffectedServices.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetClassHierarchy }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getClassHierarchy")
    public JAXBElement<GetClassHierarchy> createGetClassHierarchy(GetClassHierarchy value) {
        return new JAXBElement<GetClassHierarchy>(_GetClassHierarchy_QNAME, GetClassHierarchy.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddIPAddressResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "addIPAddressResponse")
    public JAXBElement<AddIPAddressResponse> createAddIPAddressResponse(AddIPAddressResponse value) {
        return new JAXBElement<AddIPAddressResponse>(_AddIPAddressResponse_QNAME, AddIPAddressResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTemplateSpecialElementChildren }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getTemplateSpecialElementChildren")
    public JAXBElement<GetTemplateSpecialElementChildren> createGetTemplateSpecialElementChildren(GetTemplateSpecialElementChildren value) {
        return new JAXBElement<GetTemplateSpecialElementChildren>(_GetTemplateSpecialElementChildren_QNAME, GetTemplateSpecialElementChildren.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCustomer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getCustomer")
    public JAXBElement<GetCustomer> createGetCustomer(GetCustomer value) {
        return new JAXBElement<GetCustomer>(_GetCustomer_QNAME, GetCustomer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateFavoritesFolderForUser }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "createFavoritesFolderForUser")
    public JAXBElement<CreateFavoritesFolderForUser> createCreateFavoritesFolderForUser(CreateFavoritesFolderForUser value) {
        return new JAXBElement<CreateFavoritesFolderForUser>(_CreateFavoritesFolderForUser_QNAME, CreateFavoritesFolderForUser.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSiblings }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getSiblings")
    public JAXBElement<GetSiblings> createGetSiblings(GetSiblings value) {
        return new JAXBElement<GetSiblings>(_GetSiblings_QNAME, GetSiblings.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetProjectPools }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getProjectPools")
    public JAXBElement<GetProjectPools> createGetProjectPools(GetProjectPools value) {
        return new JAXBElement<GetProjectPools>(_GetProjectPools_QNAME, GetProjectPools.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateProcessInstanceResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "createProcessInstanceResponse")
    public JAXBElement<CreateProcessInstanceResponse> createCreateProcessInstanceResponse(CreateProcessInstanceResponse value) {
        return new JAXBElement<CreateProcessInstanceResponse>(_CreateProcessInstanceResponse_QNAME, CreateProcessInstanceResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetProxiesInPool }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getProxiesInPool")
    public JAXBElement<GetProxiesInPool> createGetProxiesInPool(GetProxiesInPool value) {
        return new JAXBElement<GetProxiesInPool>(_GetProxiesInPool_QNAME, GetProxiesInPool.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateMPLSLink }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "createMPLSLink")
    public JAXBElement<CreateMPLSLink> createCreateMPLSLink(CreateMPLSLink value) {
        return new JAXBElement<CreateMPLSLink>(_CreateMPLSLink_QNAME, CreateMPLSLink.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeletePhysicalConnection }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "deletePhysicalConnection")
    public JAXBElement<DeletePhysicalConnection> createDeletePhysicalConnection(DeletePhysicalConnection value) {
        return new JAXBElement<DeletePhysicalConnection>(_DeletePhysicalConnection_QNAME, DeletePhysicalConnection.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddPossibleChildrenForClassWithIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "addPossibleChildrenForClassWithIdResponse")
    public JAXBElement<AddPossibleChildrenForClassWithIdResponse> createAddPossibleChildrenForClassWithIdResponse(AddPossibleChildrenForClassWithIdResponse value) {
        return new JAXBElement<AddPossibleChildrenForClassWithIdResponse>(_AddPossibleChildrenForClassWithIdResponse_QNAME, AddPossibleChildrenForClassWithIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetFilesForObject }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getFilesForObject")
    public JAXBElement<GetFilesForObject> createGetFilesForObject(GetFilesForObject value) {
        return new JAXBElement<GetFilesForObject>(_GetFilesForObject_QNAME, GetFilesForObject.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MoveSyncDataSourceConfiguration }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "moveSyncDataSourceConfiguration")
    public JAXBElement<MoveSyncDataSourceConfiguration> createMoveSyncDataSourceConfiguration(MoveSyncDataSourceConfiguration value) {
        return new JAXBElement<MoveSyncDataSourceConfiguration>(_MoveSyncDataSourceConfiguration_QNAME, MoveSyncDataSourceConfiguration.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetObjectRelatedViewResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getObjectRelatedViewResponse")
    public JAXBElement<GetObjectRelatedViewResponse> createGetObjectRelatedViewResponse(GetObjectRelatedViewResponse value) {
        return new JAXBElement<GetObjectRelatedViewResponse>(_GetObjectRelatedViewResponse_QNAME, GetObjectRelatedViewResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMandatoryAttributesInClassResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getMandatoryAttributesInClassResponse")
    public JAXBElement<GetMandatoryAttributesInClassResponse> createGetMandatoryAttributesInClassResponse(GetMandatoryAttributesInClassResponse value) {
        return new JAXBElement<GetMandatoryAttributesInClassResponse>(_GetMandatoryAttributesInClassResponse_QNAME, GetMandatoryAttributesInClassResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateConfigurationVariablesPoolResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "updateConfigurationVariablesPoolResponse")
    public JAXBElement<UpdateConfigurationVariablesPoolResponse> createUpdateConfigurationVariablesPoolResponse(UpdateConfigurationVariablesPoolResponse value) {
        return new JAXBElement<UpdateConfigurationVariablesPoolResponse>(_UpdateConfigurationVariablesPoolResponse_QNAME, UpdateConfigurationVariablesPoolResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteReport }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "deleteReport")
    public JAXBElement<DeleteReport> createDeleteReport(DeleteReport value) {
        return new JAXBElement<DeleteReport>(_DeleteReport_QNAME, DeleteReport.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetProcessDefinition }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getProcessDefinition")
    public JAXBElement<GetProcessDefinition> createGetProcessDefinition(GetProcessDefinition value) {
        return new JAXBElement<GetProcessDefinition>(_GetProcessDefinition_QNAME, GetProcessDefinition.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreatePoolInWarehouseResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "createPoolInWarehouseResponse")
    public JAXBElement<CreatePoolInWarehouseResponse> createCreatePoolInWarehouseResponse(CreatePoolInWarehouseResponse value) {
        return new JAXBElement<CreatePoolInWarehouseResponse>(_CreatePoolInWarehouseResponse_QNAME, CreatePoolInWarehouseResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetServiceResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getServiceResponse")
    public JAXBElement<GetServiceResponse> createGetServiceResponse(GetServiceResponse value) {
        return new JAXBElement<GetServiceResponse>(_GetServiceResponse_QNAME, GetServiceResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteClassResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "deleteClassResponse")
    public JAXBElement<DeleteClassResponse> createDeleteClassResponse(DeleteClassResponse value) {
        return new JAXBElement<DeleteClassResponse>(_DeleteClassResponse_QNAME, DeleteClassResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateConfigurationVariableResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "updateConfigurationVariableResponse")
    public JAXBElement<UpdateConfigurationVariableResponse> createUpdateConfigurationVariableResponse(UpdateConfigurationVariableResponse value) {
        return new JAXBElement<UpdateConfigurationVariableResponse>(_UpdateConfigurationVariableResponse_QNAME, UpdateConfigurationVariableResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddPossibleChildrenResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "addPossibleChildrenResponse")
    public JAXBElement<AddPossibleChildrenResponse> createAddPossibleChildrenResponse(AddPossibleChildrenResponse value) {
        return new JAXBElement<AddPossibleChildrenResponse>(_AddPossibleChildrenResponse_QNAME, AddPossibleChildrenResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetUsersInGroupResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getUsersInGroupResponse")
    public JAXBElement<GetUsersInGroupResponse> createGetUsersInGroupResponse(GetUsersInGroupResponse value) {
        return new JAXBElement<GetUsersInGroupResponse>(_GetUsersInGroupResponse_QNAME, GetUsersInGroupResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateContractResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "createContractResponse")
    public JAXBElement<CreateContractResponse> createCreateContractResponse(CreateContractResponse value) {
        return new JAXBElement<CreateContractResponse>(_CreateContractResponse_QNAME, CreateContractResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LaunchAutomatedSynchronizationTask }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "launchAutomatedSynchronizationTask")
    public JAXBElement<LaunchAutomatedSynchronizationTask> createLaunchAutomatedSynchronizationTask(LaunchAutomatedSynchronizationTask value) {
        return new JAXBElement<LaunchAutomatedSynchronizationTask>(_LaunchAutomatedSynchronizationTask_QNAME, LaunchAutomatedSynchronizationTask.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CopyPoolItemToPool }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "copyPoolItemToPool")
    public JAXBElement<CopyPoolItemToPool> createCopyPoolItemToPool(CopyPoolItemToPool value) {
        return new JAXBElement<CopyPoolItemToPool>(_CopyPoolItemToPool_QNAME, CopyPoolItemToPool.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSDHTransportLinkStructure }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getSDHTransportLinkStructure")
    public JAXBElement<GetSDHTransportLinkStructure> createGetSDHTransportLinkStructure(GetSDHTransportLinkStructure value) {
        return new JAXBElement<GetSDHTransportLinkStructure>(_GetSDHTransportLinkStructure_QNAME, GetSDHTransportLinkStructure.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DownloadBulkLoadLog }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "downloadBulkLoadLog")
    public JAXBElement<DownloadBulkLoadLog> createDownloadBulkLoadLog(DownloadBulkLoadLog value) {
        return new JAXBElement<DownloadBulkLoadLog>(_DownloadBulkLoadLog_QNAME, DownloadBulkLoadLog.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetContactsForCustomer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getContactsForCustomer")
    public JAXBElement<GetContactsForCustomer> createGetContactsForCustomer(GetContactsForCustomer value) {
        return new JAXBElement<GetContactsForCustomer>(_GetContactsForCustomer_QNAME, GetContactsForCustomer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetObjectsWithFilterResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getObjectsWithFilterResponse")
    public JAXBElement<GetObjectsWithFilterResponse> createGetObjectsWithFilterResponse(GetObjectsWithFilterResponse value) {
        return new JAXBElement<GetObjectsWithFilterResponse>(_GetObjectsWithFilterResponse_QNAME, GetObjectsWithFilterResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetGeneralViewResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getGeneralViewResponse")
    public JAXBElement<GetGeneralViewResponse> createGetGeneralViewResponse(GetGeneralViewResponse value) {
        return new JAXBElement<GetGeneralViewResponse>(_GetGeneralViewResponse_QNAME, GetGeneralViewResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteContact }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "deleteContact")
    public JAXBElement<DeleteContact> createDeleteContact(DeleteContact value) {
        return new JAXBElement<DeleteContact>(_DeleteContact_QNAME, DeleteContact.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RelateIPtoPortResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "relateIPtoPortResponse")
    public JAXBElement<RelateIPtoPortResponse> createRelateIPtoPortResponse(RelateIPtoPortResponse value) {
        return new JAXBElement<RelateIPtoPortResponse>(_RelateIPtoPortResponse_QNAME, RelateIPtoPortResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateSubnetResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "createSubnetResponse")
    public JAXBElement<CreateSubnetResponse> createCreateSubnetResponse(CreateSubnetResponse value) {
        return new JAXBElement<CreateSubnetResponse>(_CreateSubnetResponse_QNAME, CreateSubnetResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateSDHTributaryLinkResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "createSDHTributaryLinkResponse")
    public JAXBElement<CreateSDHTributaryLinkResponse> createCreateSDHTributaryLinkResponse(CreateSDHTributaryLinkResponse value) {
        return new JAXBElement<CreateSDHTributaryLinkResponse>(_CreateSDHTributaryLinkResponse_QNAME, CreateSDHTributaryLinkResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RelateObjectsToProjectResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "relateObjectsToProjectResponse")
    public JAXBElement<RelateObjectsToProjectResponse> createRelateObjectsToProjectResponse(RelateObjectsToProjectResponse value) {
        return new JAXBElement<RelateObjectsToProjectResponse>(_RelateObjectsToProjectResponse_QNAME, RelateObjectsToProjectResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SetClassProperties }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "setClassProperties")
    public JAXBElement<SetClassProperties> createSetClassProperties(SetClassProperties value) {
        return new JAXBElement<SetClassProperties>(_SetClassProperties_QNAME, SetClassProperties.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateUser }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "createUser")
    public JAXBElement<CreateUser> createCreateUser(CreateUser value) {
        return new JAXBElement<CreateUser>(_CreateUser_QNAME, CreateUser.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemoteActivityDefinition }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "remoteActivityDefinition")
    public JAXBElement<RemoteActivityDefinition> createRemoteActivityDefinition(RemoteActivityDefinition value) {
        return new JAXBElement<RemoteActivityDefinition>(_RemoteActivityDefinition_QNAME, RemoteActivityDefinition.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMPLSLinkEndpointsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getMPLSLinkEndpointsResponse")
    public JAXBElement<GetMPLSLinkEndpointsResponse> createGetMPLSLinkEndpointsResponse(GetMPLSLinkEndpointsResponse value) {
        return new JAXBElement<GetMPLSLinkEndpointsResponse>(_GetMPLSLinkEndpointsResponse_QNAME, GetMPLSLinkEndpointsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetConfigurationVariablesPools }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getConfigurationVariablesPools")
    public JAXBElement<GetConfigurationVariablesPools> createGetConfigurationVariablesPools(GetConfigurationVariablesPools value) {
        return new JAXBElement<GetConfigurationVariablesPools>(_GetConfigurationVariablesPools_QNAME, GetConfigurationVariablesPools.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetWarehouseRootPools }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getWarehouseRootPools")
    public JAXBElement<GetWarehouseRootPools> createGetWarehouseRootPools(GetWarehouseRootPools value) {
        return new JAXBElement<GetWarehouseRootPools>(_GetWarehouseRootPools_QNAME, GetWarehouseRootPools.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemovePossibleSpecialChildrenResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "removePossibleSpecialChildrenResponse")
    public JAXBElement<RemovePossibleSpecialChildrenResponse> createRemovePossibleSpecialChildrenResponse(RemovePossibleSpecialChildrenResponse value) {
        return new JAXBElement<RemovePossibleSpecialChildrenResponse>(_RemovePossibleSpecialChildrenResponse_QNAME, RemovePossibleSpecialChildrenResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetObjectLight }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getObjectLight")
    public JAXBElement<GetObjectLight> createGetObjectLight(GetObjectLight value) {
        return new JAXBElement<GetObjectLight>(_GetObjectLight_QNAME, GetObjectLight.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteSDHContainerLinkResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "deleteSDHContainerLinkResponse")
    public JAXBElement<DeleteSDHContainerLinkResponse> createDeleteSDHContainerLinkResponse(DeleteSDHContainerLinkResponse value) {
        return new JAXBElement<DeleteSDHContainerLinkResponse>(_DeleteSDHContainerLinkResponse_QNAME, DeleteSDHContainerLinkResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSiblingsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getSiblingsResponse")
    public JAXBElement<GetSiblingsResponse> createGetSiblingsResponse(GetSiblingsResponse value) {
        return new JAXBElement<GetSiblingsResponse>(_GetSiblingsResponse_QNAME, GetSiblingsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateProxyPoolResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "updateProxyPoolResponse")
    public JAXBElement<UpdateProxyPoolResponse> createUpdateProxyPoolResponse(UpdateProxyPoolResponse value) {
        return new JAXBElement<UpdateProxyPoolResponse>(_UpdateProxyPoolResponse_QNAME, UpdateProxyPoolResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReleaseObjectFromContractResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "releaseObjectFromContractResponse")
    public JAXBElement<ReleaseObjectFromContractResponse> createReleaseObjectFromContractResponse(ReleaseObjectFromContractResponse value) {
        return new JAXBElement<ReleaseObjectFromContractResponse>(_ReleaseObjectFromContractResponse_QNAME, ReleaseObjectFromContractResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateObjectRelatedViewResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "updateObjectRelatedViewResponse")
    public JAXBElement<UpdateObjectRelatedViewResponse> createUpdateObjectRelatedViewResponse(UpdateObjectRelatedViewResponse value) {
        return new JAXBElement<UpdateObjectRelatedViewResponse>(_UpdateObjectRelatedViewResponse_QNAME, UpdateObjectRelatedViewResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateBulkTemplateElementResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "createBulkTemplateElementResponse")
    public JAXBElement<CreateBulkTemplateElementResponse> createCreateBulkTemplateElementResponse(CreateBulkTemplateElementResponse value) {
        return new JAXBElement<CreateBulkTemplateElementResponse>(_CreateBulkTemplateElementResponse_QNAME, CreateBulkTemplateElementResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateGeneralView }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "updateGeneralView")
    public JAXBElement<UpdateGeneralView> createUpdateGeneralView(UpdateGeneralView value) {
        return new JAXBElement<UpdateGeneralView>(_UpdateGeneralView_QNAME, UpdateGeneralView.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteAttributeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "deleteAttributeResponse")
    public JAXBElement<DeleteAttributeResponse> createDeleteAttributeResponse(DeleteAttributeResponse value) {
        return new JAXBElement<DeleteAttributeResponse>(_DeleteAttributeResponse_QNAME, DeleteAttributeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetNextActivityForProcessInstance }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getNextActivityForProcessInstance")
    public JAXBElement<GetNextActivityForProcessInstance> createGetNextActivityForProcessInstance(GetNextActivityForProcessInstance value) {
        return new JAXBElement<GetNextActivityForProcessInstance>(_GetNextActivityForProcessInstance_QNAME, GetNextActivityForProcessInstance.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SetPrivilegeToUserResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "setPrivilegeToUserResponse")
    public JAXBElement<SetPrivilegeToUserResponse> createSetPrivilegeToUserResponse(SetPrivilegeToUserResponse value) {
        return new JAXBElement<SetPrivilegeToUserResponse>(_SetPrivilegeToUserResponse_QNAME, SetPrivilegeToUserResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CloseSession }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "closeSession")
    public JAXBElement<CloseSession> createCloseSession(CloseSession value) {
        return new JAXBElement<CloseSession>(_CloseSession_QNAME, CloseSession.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateBusinessRule }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "createBusinessRule")
    public JAXBElement<CreateBusinessRule> createCreateBusinessRule(CreateBusinessRule value) {
        return new JAXBElement<CreateBusinessRule>(_CreateBusinessRule_QNAME, CreateBusinessRule.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteTaskResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "deleteTaskResponse")
    public JAXBElement<DeleteTaskResponse> createDeleteTaskResponse(DeleteTaskResponse value) {
        return new JAXBElement<DeleteTaskResponse>(_DeleteTaskResponse_QNAME, DeleteTaskResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteServicePool }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "deleteServicePool")
    public JAXBElement<DeleteServicePool> createDeleteServicePool(DeleteServicePool value) {
        return new JAXBElement<DeleteServicePool>(_DeleteServicePool_QNAME, DeleteServicePool.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetPossibleSpecialChildren }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getPossibleSpecialChildren")
    public JAXBElement<GetPossibleSpecialChildren> createGetPossibleSpecialChildren(GetPossibleSpecialChildren value) {
        return new JAXBElement<GetPossibleSpecialChildren>(_GetPossibleSpecialChildren_QNAME, GetPossibleSpecialChildren.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LaunchSupervisedSynchronizationTaskResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "launchSupervisedSynchronizationTaskResponse")
    public JAXBElement<LaunchSupervisedSynchronizationTaskResponse> createLaunchSupervisedSynchronizationTaskResponse(LaunchSupervisedSynchronizationTaskResponse value) {
        return new JAXBElement<LaunchSupervisedSynchronizationTaskResponse>(_LaunchSupervisedSynchronizationTaskResponse_QNAME, LaunchSupervisedSynchronizationTaskResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllClasses }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getAllClasses")
    public JAXBElement<GetAllClasses> createGetAllClasses(GetAllClasses value) {
        return new JAXBElement<GetAllClasses>(_GetAllClasses_QNAME, GetAllClasses.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateSparePartResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "createSparePartResponse")
    public JAXBElement<CreateSparePartResponse> createCreateSparePartResponse(CreateSparePartResponse value) {
        return new JAXBElement<CreateSparePartResponse>(_CreateSparePartResponse_QNAME, CreateSparePartResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteActivity }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "deleteActivity")
    public JAXBElement<DeleteActivity> createDeleteActivity(DeleteActivity value) {
        return new JAXBElement<DeleteActivity>(_DeleteActivity_QNAME, DeleteActivity.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSubscribersForTaskResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getSubscribersForTaskResponse")
    public JAXBElement<GetSubscribersForTaskResponse> createGetSubscribersForTaskResponse(GetSubscribersForTaskResponse value) {
        return new JAXBElement<GetSubscribersForTaskResponse>(_GetSubscribersForTaskResponse_QNAME, GetSubscribersForTaskResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateTaskProperties }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "updateTaskProperties")
    public JAXBElement<UpdateTaskProperties> createUpdateTaskProperties(UpdateTaskProperties value) {
        return new JAXBElement<UpdateTaskProperties>(_UpdateTaskProperties_QNAME, UpdateTaskProperties.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAttributeForClassWithId }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getAttributeForClassWithId")
    public JAXBElement<GetAttributeForClassWithId> createGetAttributeForClassWithId(GetAttributeForClassWithId value) {
        return new JAXBElement<GetAttributeForClassWithId>(_GetAttributeForClassWithId_QNAME, GetAttributeForClassWithId.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetPhysicalConnectionEndpoints }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getPhysicalConnectionEndpoints")
    public JAXBElement<GetPhysicalConnectionEndpoints> createGetPhysicalConnectionEndpoints(GetPhysicalConnectionEndpoints value) {
        return new JAXBElement<GetPhysicalConnectionEndpoints>(_GetPhysicalConnectionEndpoints_QNAME, GetPhysicalConnectionEndpoints.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SetAttributePropertiesForClassWithId }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "setAttributePropertiesForClassWithId")
    public JAXBElement<SetAttributePropertiesForClassWithId> createSetAttributePropertiesForClassWithId(SetAttributePropertiesForClassWithId value) {
        return new JAXBElement<SetAttributePropertiesForClassWithId>(_SetAttributePropertiesForClassWithId_QNAME, SetAttributePropertiesForClassWithId.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTemplatesForClass }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getTemplatesForClass")
    public JAXBElement<GetTemplatesForClass> createGetTemplatesForClass(GetTemplatesForClass value) {
        return new JAXBElement<GetTemplatesForClass>(_GetTemplatesForClass_QNAME, GetTemplatesForClass.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IsSubclassOf }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "isSubclassOf")
    public JAXBElement<IsSubclassOf> createIsSubclassOf(IsSubclassOf value) {
        return new JAXBElement<IsSubclassOf>(_IsSubclassOf_QNAME, IsSubclassOf.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LaunchSupervisedSynchronizationTask }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "launchSupervisedSynchronizationTask")
    public JAXBElement<LaunchSupervisedSynchronizationTask> createLaunchSupervisedSynchronizationTask(LaunchSupervisedSynchronizationTask value) {
        return new JAXBElement<LaunchSupervisedSynchronizationTask>(_LaunchSupervisedSynchronizationTask_QNAME, LaunchSupervisedSynchronizationTask.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateTaskParameters }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "updateTaskParameters")
    public JAXBElement<UpdateTaskParameters> createUpdateTaskParameters(UpdateTaskParameters value) {
        return new JAXBElement<UpdateTaskParameters>(_UpdateTaskParameters_QNAME, UpdateTaskParameters.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateTemplateElement }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "createTemplateElement")
    public JAXBElement<CreateTemplateElement> createCreateTemplateElement(CreateTemplateElement value) {
        return new JAXBElement<CreateTemplateElement>(_CreateTemplateElement_QNAME, CreateTemplateElement.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetE2EViewResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getE2EViewResponse")
    public JAXBElement<GetE2EViewResponse> createGetE2EViewResponse(GetE2EViewResponse value) {
        return new JAXBElement<GetE2EViewResponse>(_GetE2EViewResponse_QNAME, GetE2EViewResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetArtifactDefinitionForActivityResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getArtifactDefinitionForActivityResponse")
    public JAXBElement<GetArtifactDefinitionForActivityResponse> createGetArtifactDefinitionForActivityResponse(GetArtifactDefinitionForActivityResponse value) {
        return new JAXBElement<GetArtifactDefinitionForActivityResponse>(_GetArtifactDefinitionForActivityResponse_QNAME, GetArtifactDefinitionForActivityResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteActivityResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "deleteActivityResponse")
    public JAXBElement<DeleteActivityResponse> createDeleteActivityResponse(DeleteActivityResponse value) {
        return new JAXBElement<DeleteActivityResponse>(_DeleteActivityResponse_QNAME, DeleteActivityResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateTemplateResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "createTemplateResponse")
    public JAXBElement<CreateTemplateResponse> createCreateTemplateResponse(CreateTemplateResponse value) {
        return new JAXBElement<CreateTemplateResponse>(_CreateTemplateResponse_QNAME, CreateTemplateResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSpecialAttributes }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getSpecialAttributes")
    public JAXBElement<GetSpecialAttributes> createGetSpecialAttributes(GetSpecialAttributes value) {
        return new JAXBElement<GetSpecialAttributes>(_GetSpecialAttributes_QNAME, GetSpecialAttributes.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetPool }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getPool")
    public JAXBElement<GetPool> createGetPool(GetPool value) {
        return new JAXBElement<GetPool>(_GetPool_QNAME, GetPool.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AssociateObjectsToContractResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "associateObjectsToContractResponse")
    public JAXBElement<AssociateObjectsToContractResponse> createAssociateObjectsToContractResponse(AssociateObjectsToContractResponse value) {
        return new JAXBElement<AssociateObjectsToContractResponse>(_AssociateObjectsToContractResponse_QNAME, AssociateObjectsToContractResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllClassesLight }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getAllClassesLight")
    public JAXBElement<GetAllClassesLight> createGetAllClassesLight(GetAllClassesLight value) {
        return new JAXBElement<GetAllClassesLight>(_GetAllClassesLight_QNAME, GetAllClassesLight.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LaunchAdHocAutomatedSynchronizationTaskResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "launchAdHocAutomatedSynchronizationTaskResponse")
    public JAXBElement<LaunchAdHocAutomatedSynchronizationTaskResponse> createLaunchAdHocAutomatedSynchronizationTaskResponse(LaunchAdHocAutomatedSynchronizationTaskResponse value) {
        return new JAXBElement<LaunchAdHocAutomatedSynchronizationTaskResponse>(_LaunchAdHocAutomatedSynchronizationTaskResponse_QNAME, LaunchAdHocAutomatedSynchronizationTaskResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateCustomer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "createCustomer")
    public JAXBElement<CreateCustomer> createCreateCustomer(CreateCustomer value) {
        return new JAXBElement<CreateCustomer>(_CreateCustomer_QNAME, CreateCustomer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetObjectRelatedViews }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getObjectRelatedViews")
    public JAXBElement<GetObjectRelatedViews> createGetObjectRelatedViews(GetObjectRelatedViews value) {
        return new JAXBElement<GetObjectRelatedViews>(_GetObjectRelatedViews_QNAME, GetObjectRelatedViews.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCommonParentResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getCommonParentResponse")
    public JAXBElement<GetCommonParentResponse> createGetCommonParentResponse(GetCommonParentResponse value) {
        return new JAXBElement<GetCommonParentResponse>(_GetCommonParentResponse_QNAME, GetCommonParentResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteSynchronizationDataSourceConfig }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "deleteSynchronizationDataSourceConfig")
    public JAXBElement<DeleteSynchronizationDataSourceConfig> createDeleteSynchronizationDataSourceConfig(DeleteSynchronizationDataSourceConfig value) {
        return new JAXBElement<DeleteSynchronizationDataSourceConfig>(_DeleteSynchronizationDataSourceConfig_QNAME, DeleteSynchronizationDataSourceConfig.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SetPoolProperties }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "setPoolProperties")
    public JAXBElement<SetPoolProperties> createSetPoolProperties(SetPoolProperties value) {
        return new JAXBElement<SetPoolProperties>(_SetPoolProperties_QNAME, SetPoolProperties.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetArtifactForActivityResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getArtifactForActivityResponse")
    public JAXBElement<GetArtifactForActivityResponse> createGetArtifactForActivityResponse(GetArtifactForActivityResponse value) {
        return new JAXBElement<GetArtifactForActivityResponse>(_GetArtifactForActivityResponse_QNAME, GetArtifactForActivityResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSubClassesLightNoRecursive }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getSubClassesLightNoRecursive")
    public JAXBElement<GetSubClassesLightNoRecursive> createGetSubClassesLightNoRecursive(GetSubClassesLightNoRecursive value) {
        return new JAXBElement<GetSubClassesLightNoRecursive>(_GetSubClassesLightNoRecursive_QNAME, GetSubClassesLightNoRecursive.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetProcessDefinitionResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getProcessDefinitionResponse")
    public JAXBElement<GetProcessDefinitionResponse> createGetProcessDefinitionResponse(GetProcessDefinitionResponse value) {
        return new JAXBElement<GetProcessDefinitionResponse>(_GetProcessDefinitionResponse_QNAME, GetProcessDefinitionResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddUserToGroup }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "addUserToGroup")
    public JAXBElement<AddUserToGroup> createAddUserToGroup(AddUserToGroup value) {
        return new JAXBElement<AddUserToGroup>(_AddUserToGroup_QNAME, AddUserToGroup.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateServicePool }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "createServicePool")
    public JAXBElement<CreateServicePool> createCreateServicePool(CreateServicePool value) {
        return new JAXBElement<CreateServicePool>(_CreateServicePool_QNAME, CreateServicePool.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AssociatePhysicalNodeToWarehouse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "associatePhysicalNodeToWarehouse")
    public JAXBElement<AssociatePhysicalNodeToWarehouse> createAssociatePhysicalNodeToWarehouse(AssociatePhysicalNodeToWarehouse value) {
        return new JAXBElement<AssociatePhysicalNodeToWarehouse>(_AssociatePhysicalNodeToWarehouse_QNAME, AssociatePhysicalNodeToWarehouse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateSynchronizationGroupResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "createSynchronizationGroupResponse")
    public JAXBElement<CreateSynchronizationGroupResponse> createCreateSynchronizationGroupResponse(CreateSynchronizationGroupResponse value) {
        return new JAXBElement<CreateSynchronizationGroupResponse>(_CreateSynchronizationGroupResponse_QNAME, CreateSynchronizationGroupResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReleaseObjectFromProxyResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "releaseObjectFromProxyResponse")
    public JAXBElement<ReleaseObjectFromProxyResponse> createReleaseObjectFromProxyResponse(ReleaseObjectFromProxyResponse value) {
        return new JAXBElement<ReleaseObjectFromProxyResponse>(_ReleaseObjectFromProxyResponse_QNAME, ReleaseObjectFromProxyResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteProxy }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "deleteProxy")
    public JAXBElement<DeleteProxy> createDeleteProxy(DeleteProxy value) {
        return new JAXBElement<DeleteProxy>(_DeleteProxy_QNAME, DeleteProxy.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateReportResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "updateReportResponse")
    public JAXBElement<UpdateReportResponse> createUpdateReportResponse(UpdateReportResponse value) {
        return new JAXBElement<UpdateReportResponse>(_UpdateReportResponse_QNAME, UpdateReportResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAttribute }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getAttribute")
    public JAXBElement<GetAttribute> createGetAttribute(GetAttribute value) {
        return new JAXBElement<GetAttribute>(_GetAttribute_QNAME, GetAttribute.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateValidatorDefinitionResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "updateValidatorDefinitionResponse")
    public JAXBElement<UpdateValidatorDefinitionResponse> createUpdateValidatorDefinitionResponse(UpdateValidatorDefinitionResponse value) {
        return new JAXBElement<UpdateValidatorDefinitionResponse>(_UpdateValidatorDefinitionResponse_QNAME, UpdateValidatorDefinitionResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDeviceLayoutStructureResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getDeviceLayoutStructureResponse")
    public JAXBElement<GetDeviceLayoutStructureResponse> createGetDeviceLayoutStructureResponse(GetDeviceLayoutStructureResponse value) {
        return new JAXBElement<GetDeviceLayoutStructureResponse>(_GetDeviceLayoutStructureResponse_QNAME, GetDeviceLayoutStructureResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReleaseObjectFromService }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "releaseObjectFromService")
    public JAXBElement<ReleaseObjectFromService> createReleaseObjectFromService(ReleaseObjectFromService value) {
        return new JAXBElement<ReleaseObjectFromService>(_ReleaseObjectFromService_QNAME, ReleaseObjectFromService.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateProxy }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "updateProxy")
    public JAXBElement<UpdateProxy> createUpdateProxy(UpdateProxy value) {
        return new JAXBElement<UpdateProxy>(_UpdateProxy_QNAME, UpdateProxy.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDeviceLayouts }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getDeviceLayouts")
    public JAXBElement<GetDeviceLayouts> createGetDeviceLayouts(GetDeviceLayouts value) {
        return new JAXBElement<GetDeviceLayouts>(_GetDeviceLayouts_QNAME, GetDeviceLayouts.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateGroup }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "createGroup")
    public JAXBElement<CreateGroup> createCreateGroup(CreateGroup value) {
        return new JAXBElement<CreateGroup>(_CreateGroup_QNAME, CreateGroup.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreatePhysicalConnectionResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "createPhysicalConnectionResponse")
    public JAXBElement<CreatePhysicalConnectionResponse> createCreatePhysicalConnectionResponse(CreatePhysicalConnectionResponse value) {
        return new JAXBElement<CreatePhysicalConnectionResponse>(_CreatePhysicalConnectionResponse_QNAME, CreatePhysicalConnectionResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BulkUpload }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "bulkUpload")
    public JAXBElement<BulkUpload> createBulkUpload(BulkUpload value) {
        return new JAXBElement<BulkUpload>(_BulkUpload_QNAME, BulkUpload.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteUsersResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "deleteUsersResponse")
    public JAXBElement<DeleteUsersResponse> createDeleteUsersResponse(DeleteUsersResponse value) {
        return new JAXBElement<DeleteUsersResponse>(_DeleteUsersResponse_QNAME, DeleteUsersResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetObjectSpecialChildrenResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getObjectSpecialChildrenResponse")
    public JAXBElement<GetObjectSpecialChildrenResponse> createGetObjectSpecialChildrenResponse(GetObjectSpecialChildrenResponse value) {
        return new JAXBElement<GetObjectSpecialChildrenResponse>(_GetObjectSpecialChildrenResponse_QNAME, GetObjectSpecialChildrenResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetRootPools }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getRootPools")
    public JAXBElement<GetRootPools> createGetRootPools(GetRootPools value) {
        return new JAXBElement<GetRootPools>(_GetRootPools_QNAME, GetRootPools.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateTaskSchedule }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "updateTaskSchedule")
    public JAXBElement<UpdateTaskSchedule> createUpdateTaskSchedule(UpdateTaskSchedule value) {
        return new JAXBElement<UpdateTaskSchedule>(_UpdateTaskSchedule_QNAME, UpdateTaskSchedule.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreatePoolItemResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "createPoolItemResponse")
    public JAXBElement<CreatePoolItemResponse> createCreatePoolItemResponse(CreatePoolItemResponse value) {
        return new JAXBElement<CreatePoolItemResponse>(_CreatePoolItemResponse_QNAME, CreatePoolItemResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSpecialAttributesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getSpecialAttributesResponse")
    public JAXBElement<GetSpecialAttributesResponse> createGetSpecialAttributesResponse(GetSpecialAttributesResponse value) {
        return new JAXBElement<GetSpecialAttributesResponse>(_GetSpecialAttributesResponse_QNAME, GetSpecialAttributesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ExecuteQueryResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "executeQueryResponse")
    public JAXBElement<ExecuteQueryResponse> createExecuteQueryResponse(ExecuteQueryResponse value) {
        return new JAXBElement<ExecuteQueryResponse>(_ExecuteQueryResponse_QNAME, ExecuteQueryResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateMPLSLinkResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "createMPLSLinkResponse")
    public JAXBElement<CreateMPLSLinkResponse> createCreateMPLSLinkResponse(CreateMPLSLinkResponse value) {
        return new JAXBElement<CreateMPLSLinkResponse>(_CreateMPLSLinkResponse_QNAME, CreateMPLSLinkResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteConfigurationVariable }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "deleteConfigurationVariable")
    public JAXBElement<DeleteConfigurationVariable> createDeleteConfigurationVariable(DeleteConfigurationVariable value) {
        return new JAXBElement<DeleteConfigurationVariable>(_DeleteConfigurationVariable_QNAME, DeleteConfigurationVariable.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetClass }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getClass")
    public JAXBElement<GetClass> createGetClass(GetClass value) {
        return new JAXBElement<GetClass>(_GetClass_QNAME, GetClass.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetProxyPools }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getProxyPools")
    public JAXBElement<GetProxyPools> createGetProxyPools(GetProxyPools value) {
        return new JAXBElement<GetProxyPools>(_GetProxyPools_QNAME, GetProxyPools.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetPoolItemsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getPoolItemsResponse")
    public JAXBElement<GetPoolItemsResponse> createGetPoolItemsResponse(GetPoolItemsResponse value) {
        return new JAXBElement<GetPoolItemsResponse>(_GetPoolItemsResponse_QNAME, GetPoolItemsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetListTypeItem }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getListTypeItem")
    public JAXBElement<GetListTypeItem> createGetListTypeItem(GetListTypeItem value) {
        return new JAXBElement<GetListTypeItem>(_GetListTypeItem_QNAME, GetListTypeItem.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ExecuteInventoryLevelReport }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "executeInventoryLevelReport")
    public JAXBElement<ExecuteInventoryLevelReport> createExecuteInventoryLevelReport(ExecuteInventoryLevelReport value) {
        return new JAXBElement<ExecuteInventoryLevelReport>(_ExecuteInventoryLevelReport_QNAME, ExecuteInventoryLevelReport.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllConfigurationVariablesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getAllConfigurationVariablesResponse")
    public JAXBElement<GetAllConfigurationVariablesResponse> createGetAllConfigurationVariablesResponse(GetAllConfigurationVariablesResponse value) {
        return new JAXBElement<GetAllConfigurationVariablesResponse>(_GetAllConfigurationVariablesResponse_QNAME, GetAllConfigurationVariablesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetClassWithIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getClassWithIdResponse")
    public JAXBElement<GetClassWithIdResponse> createGetClassWithIdResponse(GetClassWithIdResponse value) {
        return new JAXBElement<GetClassWithIdResponse>(_GetClassWithIdResponse_QNAME, GetClassWithIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetContactResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getContactResponse")
    public JAXBElement<GetContactResponse> createGetContactResponse(GetContactResponse value) {
        return new JAXBElement<GetContactResponse>(_GetContactResponse_QNAME, GetContactResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetContractPool }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getContractPool")
    public JAXBElement<GetContractPool> createGetContractPool(GetContractPool value) {
        return new JAXBElement<GetContractPool>(_GetContractPool_QNAME, GetContractPool.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetContainersBetweenObjectsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getContainersBetweenObjectsResponse")
    public JAXBElement<GetContainersBetweenObjectsResponse> createGetContainersBetweenObjectsResponse(GetContainersBetweenObjectsResponse value) {
        return new JAXBElement<GetContainersBetweenObjectsResponse>(_GetContainersBetweenObjectsResponse_QNAME, GetContainersBetweenObjectsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetNextActivityForProcessInstanceResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getNextActivityForProcessInstanceResponse")
    public JAXBElement<GetNextActivityForProcessInstanceResponse> createGetNextActivityForProcessInstanceResponse(GetNextActivityForProcessInstanceResponse value) {
        return new JAXBElement<GetNextActivityForProcessInstanceResponse>(_GetNextActivityForProcessInstanceResponse_QNAME, GetNextActivityForProcessInstanceResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateTemplateSpecialElementResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "createTemplateSpecialElementResponse")
    public JAXBElement<CreateTemplateSpecialElementResponse> createCreateTemplateSpecialElementResponse(CreateTemplateSpecialElementResponse value) {
        return new JAXBElement<CreateTemplateSpecialElementResponse>(_CreateTemplateSpecialElementResponse_QNAME, CreateTemplateSpecialElementResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemoveObjectsFromFavoritesFolder }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "removeObjectsFromFavoritesFolder")
    public JAXBElement<RemoveObjectsFromFavoritesFolder> createRemoveObjectsFromFavoritesFolder(RemoveObjectsFromFavoritesFolder value) {
        return new JAXBElement<RemoveObjectsFromFavoritesFolder>(_RemoveObjectsFromFavoritesFolder_QNAME, RemoveObjectsFromFavoritesFolder.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateProxyPool }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "updateProxyPool")
    public JAXBElement<UpdateProxyPool> createUpdateProxyPool(UpdateProxyPool value) {
        return new JAXBElement<UpdateProxyPool>(_UpdateProxyPool_QNAME, UpdateProxyPool.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateBulkSpecialObjects }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "createBulkSpecialObjects")
    public JAXBElement<CreateBulkSpecialObjects> createCreateBulkSpecialObjects(CreateBulkSpecialObjects value) {
        return new JAXBElement<CreateBulkSpecialObjects>(_CreateBulkSpecialObjects_QNAME, CreateBulkSpecialObjects.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ExecuteTaskResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "executeTaskResponse")
    public JAXBElement<ExecuteTaskResponse> createExecuteTaskResponse(ExecuteTaskResponse value) {
        return new JAXBElement<ExecuteTaskResponse>(_ExecuteTaskResponse_QNAME, ExecuteTaskResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReleaseMirrorMultiplePortResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "releaseMirrorMultiplePortResponse")
    public JAXBElement<ReleaseMirrorMultiplePortResponse> createReleaseMirrorMultiplePortResponse(ReleaseMirrorMultiplePortResponse value) {
        return new JAXBElement<ReleaseMirrorMultiplePortResponse>(_ReleaseMirrorMultiplePortResponse_QNAME, ReleaseMirrorMultiplePortResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateContactResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "updateContactResponse")
    public JAXBElement<UpdateContactResponse> createUpdateContactResponse(UpdateContactResponse value) {
        return new JAXBElement<UpdateContactResponse>(_UpdateContactResponse_QNAME, UpdateContactResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetConfigurationVariablesInPoolResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getConfigurationVariablesInPoolResponse")
    public JAXBElement<GetConfigurationVariablesInPoolResponse> createGetConfigurationVariablesInPoolResponse(GetConfigurationVariablesInPoolResponse value) {
        return new JAXBElement<GetConfigurationVariablesInPoolResponse>(_GetConfigurationVariablesInPoolResponse_QNAME, GetConfigurationVariablesInPoolResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RelateSubnetToVlan }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "relateSubnetToVlan")
    public JAXBElement<RelateSubnetToVlan> createRelateSubnetToVlan(RelateSubnetToVlan value) {
        return new JAXBElement<RelateSubnetToVlan>(_RelateSubnetToVlan_QNAME, RelateSubnetToVlan.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateAttribute }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "createAttribute")
    public JAXBElement<CreateAttribute> createCreateAttribute(CreateAttribute value) {
        return new JAXBElement<CreateAttribute>(_CreateAttribute_QNAME, CreateAttribute.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateSynchronizationGroup }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "createSynchronizationGroup")
    public JAXBElement<CreateSynchronizationGroup> createCreateSynchronizationGroup(CreateSynchronizationGroup value) {
        return new JAXBElement<CreateSynchronizationGroup>(_CreateSynchronizationGroup_QNAME, CreateSynchronizationGroup.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteTemplateElementResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "deleteTemplateElementResponse")
    public JAXBElement<DeleteTemplateElementResponse> createDeleteTemplateElementResponse(DeleteTemplateElementResponse value) {
        return new JAXBElement<DeleteTemplateElementResponse>(_DeleteTemplateElementResponse_QNAME, DeleteTemplateElementResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateClassResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "createClassResponse")
    public JAXBElement<CreateClassResponse> createCreateClassResponse(CreateClassResponse value) {
        return new JAXBElement<CreateClassResponse>(_CreateClassResponse_QNAME, CreateClassResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link KillJobResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "killJobResponse")
    public JAXBElement<KillJobResponse> createKillJobResponse(KillJobResponse value) {
        return new JAXBElement<KillJobResponse>(_KillJobResponse_QNAME, KillJobResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RelatePortToInterfaceResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "relatePortToInterfaceResponse")
    public JAXBElement<RelatePortToInterfaceResponse> createRelatePortToInterfaceResponse(RelatePortToInterfaceResponse value) {
        return new JAXBElement<RelatePortToInterfaceResponse>(_RelatePortToInterfaceResponse_QNAME, RelatePortToInterfaceResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteProcessDefinition }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "deleteProcessDefinition")
    public JAXBElement<DeleteProcessDefinition> createDeleteProcessDefinition(DeleteProcessDefinition value) {
        return new JAXBElement<DeleteProcessDefinition>(_DeleteProcessDefinition_QNAME, DeleteProcessDefinition.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateListTypeItem }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "createListTypeItem")
    public JAXBElement<CreateListTypeItem> createCreateListTypeItem(CreateListTypeItem value) {
        return new JAXBElement<CreateListTypeItem>(_CreateListTypeItem_QNAME, CreateListTypeItem.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SetUserPropertiesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "setUserPropertiesResponse")
    public JAXBElement<SetUserPropertiesResponse> createSetUserPropertiesResponse(SetUserPropertiesResponse value) {
        return new JAXBElement<SetUserPropertiesResponse>(_SetUserPropertiesResponse_QNAME, SetUserPropertiesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SubscribeUserToTask }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "subscribeUserToTask")
    public JAXBElement<SubscribeUserToTask> createSubscribeUserToTask(SubscribeUserToTask value) {
        return new JAXBElement<SubscribeUserToTask>(_SubscribeUserToTask_QNAME, SubscribeUserToTask.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetListTypeItems }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getListTypeItems")
    public JAXBElement<GetListTypeItems> createGetListTypeItems(GetListTypeItems value) {
        return new JAXBElement<GetListTypeItems>(_GetListTypeItems_QNAME, GetListTypeItems.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateServicePoolResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "updateServicePoolResponse")
    public JAXBElement<UpdateServicePoolResponse> createUpdateServicePoolResponse(UpdateServicePoolResponse value) {
        return new JAXBElement<UpdateServicePoolResponse>(_UpdateServicePoolResponse_QNAME, UpdateServicePoolResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetGeneralView }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getGeneralView")
    public JAXBElement<GetGeneralView> createGetGeneralView(GetGeneralView value) {
        return new JAXBElement<GetGeneralView>(_GetGeneralView_QNAME, GetGeneralView.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateTemplateElement }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "updateTemplateElement")
    public JAXBElement<UpdateTemplateElement> createUpdateTemplateElement(UpdateTemplateElement value) {
        return new JAXBElement<UpdateTemplateElement>(_UpdateTemplateElement_QNAME, UpdateTemplateElement.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateContractPool }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "createContractPool")
    public JAXBElement<CreateContractPool> createCreateContractPool(CreateContractPool value) {
        return new JAXBElement<CreateContractPool>(_CreateContractPool_QNAME, CreateContractPool.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateCustomerPool }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "createCustomerPool")
    public JAXBElement<CreateCustomerPool> createCreateCustomerPool(CreateCustomerPool value) {
        return new JAXBElement<CreateCustomerPool>(_CreateCustomerPool_QNAME, CreateCustomerPool.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetFirstParentOfClassResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getFirstParentOfClassResponse")
    public JAXBElement<GetFirstParentOfClassResponse> createGetFirstParentOfClassResponse(GetFirstParentOfClassResponse value) {
        return new JAXBElement<GetFirstParentOfClassResponse>(_GetFirstParentOfClassResponse_QNAME, GetFirstParentOfClassResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MoveObjectsToWarehousePoolResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "moveObjectsToWarehousePoolResponse")
    public JAXBElement<MoveObjectsToWarehousePoolResponse> createMoveObjectsToWarehousePoolResponse(MoveObjectsToWarehousePoolResponse value) {
        return new JAXBElement<MoveObjectsToWarehousePoolResponse>(_MoveObjectsToWarehousePoolResponse_QNAME, MoveObjectsToWarehousePoolResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetProjectsRelatedToObject }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getProjectsRelatedToObject")
    public JAXBElement<GetProjectsRelatedToObject> createGetProjectsRelatedToObject(GetProjectsRelatedToObject value) {
        return new JAXBElement<GetProjectsRelatedToObject>(_GetProjectsRelatedToObject_QNAME, GetProjectsRelatedToObject.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetConfigurationVariablesWithPrefixResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getConfigurationVariablesWithPrefixResponse")
    public JAXBElement<GetConfigurationVariablesWithPrefixResponse> createGetConfigurationVariablesWithPrefixResponse(GetConfigurationVariablesWithPrefixResponse value) {
        return new JAXBElement<GetConfigurationVariablesWithPrefixResponse>(_GetConfigurationVariablesWithPrefixResponse_QNAME, GetConfigurationVariablesWithPrefixResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateSubnetPool }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "createSubnetPool")
    public JAXBElement<CreateSubnetPool> createCreateSubnetPool(CreateSubnetPool value) {
        return new JAXBElement<CreateSubnetPool>(_CreateSubnetPool_QNAME, CreateSubnetPool.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetContractPools }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getContractPools")
    public JAXBElement<GetContractPools> createGetContractPools(GetContractPools value) {
        return new JAXBElement<GetContractPools>(_GetContractPools_QNAME, GetContractPools.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateRootPoolResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "createRootPoolResponse")
    public JAXBElement<CreateRootPoolResponse> createCreateRootPoolResponse(CreateRootPoolResponse value) {
        return new JAXBElement<CreateRootPoolResponse>(_CreateRootPoolResponse_QNAME, CreateRootPoolResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetGeneralViews }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getGeneralViews")
    public JAXBElement<GetGeneralViews> createGetGeneralViews(GetGeneralViews value) {
        return new JAXBElement<GetGeneralViews>(_GetGeneralViews_QNAME, GetGeneralViews.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteListTypeItem }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "deleteListTypeItem")
    public JAXBElement<DeleteListTypeItem> createDeleteListTypeItem(DeleteListTypeItem value) {
        return new JAXBElement<DeleteListTypeItem>(_DeleteListTypeItem_QNAME, DeleteListTypeItem.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllValidatorDefinitionsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getAllValidatorDefinitionsResponse")
    public JAXBElement<GetAllValidatorDefinitionsResponse> createGetAllValidatorDefinitionsResponse(GetAllValidatorDefinitionsResponse value) {
        return new JAXBElement<GetAllValidatorDefinitionsResponse>(_GetAllValidatorDefinitionsResponse_QNAME, GetAllValidatorDefinitionsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SetPrivilegeToGroup }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "setPrivilegeToGroup")
    public JAXBElement<SetPrivilegeToGroup> createSetPrivilegeToGroup(SetPrivilegeToGroup value) {
        return new JAXBElement<SetPrivilegeToGroup>(_SetPrivilegeToGroup_QNAME, SetPrivilegeToGroup.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemovePossibleChildrenForClassWithId }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "removePossibleChildrenForClassWithId")
    public JAXBElement<RemovePossibleChildrenForClassWithId> createRemovePossibleChildrenForClassWithId(RemovePossibleChildrenForClassWithId value) {
        return new JAXBElement<RemovePossibleChildrenForClassWithId>(_RemovePossibleChildrenForClassWithId_QNAME, RemovePossibleChildrenForClassWithId.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SetUserProperties }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "setUserProperties")
    public JAXBElement<SetUserProperties> createSetUserProperties(SetUserProperties value) {
        return new JAXBElement<SetUserProperties>(_SetUserProperties_QNAME, SetUserProperties.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteValidatorDefinition }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "deleteValidatorDefinition")
    public JAXBElement<DeleteValidatorDefinition> createDeleteValidatorDefinition(DeleteValidatorDefinition value) {
        return new JAXBElement<DeleteValidatorDefinition>(_DeleteValidatorDefinition_QNAME, DeleteValidatorDefinition.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetGeneralViewsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getGeneralViewsResponse")
    public JAXBElement<GetGeneralViewsResponse> createGetGeneralViewsResponse(GetGeneralViewsResponse value) {
        return new JAXBElement<GetGeneralViewsResponse>(_GetGeneralViewsResponse_QNAME, GetGeneralViewsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetServicePoolsInCostumerResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getServicePoolsInCostumerResponse")
    public JAXBElement<GetServicePoolsInCostumerResponse> createGetServicePoolsInCostumerResponse(GetServicePoolsInCostumerResponse value) {
        return new JAXBElement<GetServicePoolsInCostumerResponse>(_GetServicePoolsInCostumerResponse_QNAME, GetServicePoolsInCostumerResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetConfigurationVariablesPoolsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getConfigurationVariablesPoolsResponse")
    public JAXBElement<GetConfigurationVariablesPoolsResponse> createGetConfigurationVariablesPoolsResponse(GetConfigurationVariablesPoolsResponse value) {
        return new JAXBElement<GetConfigurationVariablesPoolsResponse>(_GetConfigurationVariablesPoolsResponse_QNAME, GetConfigurationVariablesPoolsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetIPAddressResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getIPAddressResponse")
    public JAXBElement<GetIPAddressResponse> createGetIPAddressResponse(GetIPAddressResponse value) {
        return new JAXBElement<GetIPAddressResponse>(_GetIPAddressResponse_QNAME, GetIPAddressResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateCustomer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "updateCustomer")
    public JAXBElement<UpdateCustomer> createUpdateCustomer(UpdateCustomer value) {
        return new JAXBElement<UpdateCustomer>(_UpdateCustomer_QNAME, UpdateCustomer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetUpstreamClassHierarchy }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getUpstreamClassHierarchy")
    public JAXBElement<GetUpstreamClassHierarchy> createGetUpstreamClassHierarchy(GetUpstreamClassHierarchy value) {
        return new JAXBElement<GetUpstreamClassHierarchy>(_GetUpstreamClassHierarchy_QNAME, GetUpstreamClassHierarchy.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateTaskNotificationType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "updateTaskNotificationType")
    public JAXBElement<UpdateTaskNotificationType> createUpdateTaskNotificationType(UpdateTaskNotificationType value) {
        return new JAXBElement<UpdateTaskNotificationType>(_UpdateTaskNotificationType_QNAME, UpdateTaskNotificationType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetProxiesInPoolResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getProxiesInPoolResponse")
    public JAXBElement<GetProxiesInPoolResponse> createGetProxiesInPoolResponse(GetProxiesInPoolResponse value) {
        return new JAXBElement<GetProxiesInPoolResponse>(_GetProxiesInPoolResponse_QNAME, GetProxiesInPoolResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteContract }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "deleteContract")
    public JAXBElement<DeleteContract> createDeleteContract(DeleteContract value) {
        return new JAXBElement<DeleteContract>(_DeleteContract_QNAME, DeleteContract.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteBusinessRuleResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "deleteBusinessRuleResponse")
    public JAXBElement<DeleteBusinessRuleResponse> createDeleteBusinessRuleResponse(DeleteBusinessRuleResponse value) {
        return new JAXBElement<DeleteBusinessRuleResponse>(_DeleteBusinessRuleResponse_QNAME, DeleteBusinessRuleResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetPossibleChildrenNoRecursive }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getPossibleChildrenNoRecursive")
    public JAXBElement<GetPossibleChildrenNoRecursive> createGetPossibleChildrenNoRecursive(GetPossibleChildrenNoRecursive value) {
        return new JAXBElement<GetPossibleChildrenNoRecursive>(_GetPossibleChildrenNoRecursive_QNAME, GetPossibleChildrenNoRecursive.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateFavoritesFolderResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "updateFavoritesFolderResponse")
    public JAXBElement<UpdateFavoritesFolderResponse> createUpdateFavoritesFolderResponse(UpdateFavoritesFolderResponse value) {
        return new JAXBElement<UpdateFavoritesFolderResponse>(_UpdateFavoritesFolderResponse_QNAME, UpdateFavoritesFolderResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSynchronizationGroups }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getSynchronizationGroups")
    public JAXBElement<GetSynchronizationGroups> createGetSynchronizationGroups(GetSynchronizationGroups value) {
        return new JAXBElement<GetSynchronizationGroups>(_GetSynchronizationGroups_QNAME, GetSynchronizationGroups.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RelateObjectsToServiceResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "relateObjectsToServiceResponse")
    public JAXBElement<RelateObjectsToServiceResponse> createRelateObjectsToServiceResponse(RelateObjectsToServiceResponse value) {
        return new JAXBElement<RelateObjectsToServiceResponse>(_RelateObjectsToServiceResponse_QNAME, RelateObjectsToServiceResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteContractPool }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "deleteContractPool")
    public JAXBElement<DeleteContractPool> createDeleteContractPool(DeleteContractPool value) {
        return new JAXBElement<DeleteContractPool>(_DeleteContractPool_QNAME, DeleteContractPool.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateQuery }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "createQuery")
    public JAXBElement<CreateQuery> createCreateQuery(CreateQuery value) {
        return new JAXBElement<CreateQuery>(_CreateQuery_QNAME, CreateQuery.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetListTypeItemRelatedViews }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getListTypeItemRelatedViews")
    public JAXBElement<GetListTypeItemRelatedViews> createGetListTypeItemRelatedViews(GetListTypeItemRelatedViews value) {
        return new JAXBElement<GetListTypeItemRelatedViews>(_GetListTypeItemRelatedViews_QNAME, GetListTypeItemRelatedViews.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTasks }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getTasks")
    public JAXBElement<GetTasks> createGetTasks(GetTasks value) {
        return new JAXBElement<GetTasks>(_GetTasks_QNAME, GetTasks.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetActivityResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getActivityResponse")
    public JAXBElement<GetActivityResponse> createGetActivityResponse(GetActivityResponse value) {
        return new JAXBElement<GetActivityResponse>(_GetActivityResponse_QNAME, GetActivityResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetProjectResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getProjectResponse")
    public JAXBElement<GetProjectResponse> createGetProjectResponse(GetProjectResponse value) {
        return new JAXBElement<GetProjectResponse>(_GetProjectResponse_QNAME, GetProjectResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSubnetsInSubnet }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getSubnetsInSubnet")
    public JAXBElement<GetSubnetsInSubnet> createGetSubnetsInSubnet(GetSubnetsInSubnet value) {
        return new JAXBElement<GetSubnetsInSubnet>(_GetSubnetsInSubnet_QNAME, GetSubnetsInSubnet.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetPoolsInPoolResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getPoolsInPoolResponse")
    public JAXBElement<GetPoolsInPoolResponse> createGetPoolsInPoolResponse(GetPoolsInPoolResponse value) {
        return new JAXBElement<GetPoolsInPoolResponse>(_GetPoolsInPoolResponse_QNAME, GetPoolsInPoolResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetBusinessRulesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getBusinessRulesResponse")
    public JAXBElement<GetBusinessRulesResponse> createGetBusinessRulesResponse(GetBusinessRulesResponse value) {
        return new JAXBElement<GetBusinessRulesResponse>(_GetBusinessRulesResponse_QNAME, GetBusinessRulesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MovePoolItemToPoolResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "movePoolItemToPoolResponse")
    public JAXBElement<MovePoolItemToPoolResponse> createMovePoolItemToPoolResponse(MovePoolItemToPoolResponse value) {
        return new JAXBElement<MovePoolItemToPoolResponse>(_MovePoolItemToPoolResponse_QNAME, MovePoolItemToPoolResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateContractPoolResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "updateContractPoolResponse")
    public JAXBElement<UpdateContractPoolResponse> createUpdateContractPoolResponse(UpdateContractPoolResponse value) {
        return new JAXBElement<UpdateContractPoolResponse>(_UpdateContractPoolResponse_QNAME, UpdateContractPoolResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetPoolsInPool }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getPoolsInPool")
    public JAXBElement<GetPoolsInPool> createGetPoolsInPool(GetPoolsInPool value) {
        return new JAXBElement<GetPoolsInPool>(_GetPoolsInPool_QNAME, GetPoolsInPool.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateTaskParametersResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "updateTaskParametersResponse")
    public JAXBElement<UpdateTaskParametersResponse> createUpdateTaskParametersResponse(UpdateTaskParametersResponse value) {
        return new JAXBElement<UpdateTaskParametersResponse>(_UpdateTaskParametersResponse_QNAME, UpdateTaskParametersResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddPossibleChildrenForClassWithId }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "addPossibleChildrenForClassWithId")
    public JAXBElement<AddPossibleChildrenForClassWithId> createAddPossibleChildrenForClassWithId(AddPossibleChildrenForClassWithId value) {
        return new JAXBElement<AddPossibleChildrenForClassWithId>(_AddPossibleChildrenForClassWithId_QNAME, AddPossibleChildrenForClassWithId.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CopyPoolItemToPoolResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "copyPoolItemToPoolResponse")
    public JAXBElement<CopyPoolItemToPoolResponse> createCopyPoolItemToPoolResponse(CopyPoolItemToPoolResponse value) {
        return new JAXBElement<CopyPoolItemToPoolResponse>(_CopyPoolItemToPoolResponse_QNAME, CopyPoolItemToPoolResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MoveObjectsToPool }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "moveObjectsToPool")
    public JAXBElement<MoveObjectsToPool> createMoveObjectsToPool(MoveObjectsToPool value) {
        return new JAXBElement<MoveObjectsToPool>(_MoveObjectsToPool_QNAME, MoveObjectsToPool.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateSDHContainerLinkResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "createSDHContainerLinkResponse")
    public JAXBElement<CreateSDHContainerLinkResponse> createCreateSDHContainerLinkResponse(CreateSDHContainerLinkResponse value) {
        return new JAXBElement<CreateSDHContainerLinkResponse>(_CreateSDHContainerLinkResponse_QNAME, CreateSDHContainerLinkResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetClassResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getClassResponse")
    public JAXBElement<GetClassResponse> createGetClassResponse(GetClassResponse value) {
        return new JAXBElement<GetClassResponse>(_GetClassResponse_QNAME, GetClassResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllClassesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getAllClassesResponse")
    public JAXBElement<GetAllClassesResponse> createGetAllClassesResponse(GetAllClassesResponse value) {
        return new JAXBElement<GetAllClassesResponse>(_GetAllClassesResponse_QNAME, GetAllClassesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddObjectsToFavoritesFolder }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "addObjectsToFavoritesFolder")
    public JAXBElement<AddObjectsToFavoritesFolder> createAddObjectsToFavoritesFolder(AddObjectsToFavoritesFolder value) {
        return new JAXBElement<AddObjectsToFavoritesFolder>(_AddObjectsToFavoritesFolder_QNAME, AddObjectsToFavoritesFolder.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetPoolsInObjectResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getPoolsInObjectResponse")
    public JAXBElement<GetPoolsInObjectResponse> createGetPoolsInObjectResponse(GetPoolsInObjectResponse value) {
        return new JAXBElement<GetPoolsInObjectResponse>(_GetPoolsInObjectResponse_QNAME, GetPoolsInObjectResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetGroupsForUser }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getGroupsForUser")
    public JAXBElement<GetGroupsForUser> createGetGroupsForUser(GetGroupsForUser value) {
        return new JAXBElement<GetGroupsForUser>(_GetGroupsForUser_QNAME, GetGroupsForUser.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateClass }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "createClass")
    public JAXBElement<CreateClass> createCreateClass(CreateClass value) {
        return new JAXBElement<CreateClass>(_CreateClass_QNAME, CreateClass.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReleasePortFromInterface }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "releasePortFromInterface")
    public JAXBElement<ReleasePortFromInterface> createReleasePortFromInterface(ReleasePortFromInterface value) {
        return new JAXBElement<ReleasePortFromInterface>(_ReleasePortFromInterface_QNAME, ReleasePortFromInterface.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetClassHierarchyResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getClassHierarchyResponse")
    public JAXBElement<GetClassHierarchyResponse> createGetClassHierarchyResponse(GetClassHierarchyResponse value) {
        return new JAXBElement<GetClassHierarchyResponse>(_GetClassHierarchyResponse_QNAME, GetClassHierarchyResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteCustomerPool }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "deleteCustomerPool")
    public JAXBElement<DeleteCustomerPool> createDeleteCustomerPool(DeleteCustomerPool value) {
        return new JAXBElement<DeleteCustomerPool>(_DeleteCustomerPool_QNAME, DeleteCustomerPool.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ExecuteClassLevelReportResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "executeClassLevelReportResponse")
    public JAXBElement<ExecuteClassLevelReportResponse> createExecuteClassLevelReportResponse(ExecuteClassLevelReportResponse value) {
        return new JAXBElement<ExecuteClassLevelReportResponse>(_ExecuteClassLevelReportResponse_QNAME, ExecuteClassLevelReportResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeletePools }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "deletePools")
    public JAXBElement<DeletePools> createDeletePools(DeletePools value) {
        return new JAXBElement<DeletePools>(_DeletePools_QNAME, DeletePools.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteReportResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "deleteReportResponse")
    public JAXBElement<DeleteReportResponse> createDeleteReportResponse(DeleteReportResponse value) {
        return new JAXBElement<DeleteReportResponse>(_DeleteReportResponse_QNAME, DeleteReportResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ExecuteQuery }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "executeQuery")
    public JAXBElement<ExecuteQuery> createExecuteQuery(ExecuteQuery value) {
        return new JAXBElement<ExecuteQuery>(_ExecuteQuery_QNAME, ExecuteQuery.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllValidatorDefinitions }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getAllValidatorDefinitions")
    public JAXBElement<GetAllValidatorDefinitions> createGetAllValidatorDefinitions(GetAllValidatorDefinitions value) {
        return new JAXBElement<GetAllValidatorDefinitions>(_GetAllValidatorDefinitions_QNAME, GetAllValidatorDefinitions.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllProjects }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getAllProjects")
    public JAXBElement<GetAllProjects> createGetAllProjects(GetAllProjects value) {
        return new JAXBElement<GetAllProjects>(_GetAllProjects_QNAME, GetAllProjects.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetParentsUntilFirstOfClass }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getParentsUntilFirstOfClass")
    public JAXBElement<GetParentsUntilFirstOfClass> createGetParentsUntilFirstOfClass(GetParentsUntilFirstOfClass value) {
        return new JAXBElement<GetParentsUntilFirstOfClass>(_GetParentsUntilFirstOfClass_QNAME, GetParentsUntilFirstOfClass.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetObjectChildren }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getObjectChildren")
    public JAXBElement<GetObjectChildren> createGetObjectChildren(GetObjectChildren value) {
        return new JAXBElement<GetObjectChildren>(_GetObjectChildren_QNAME, GetObjectChildren.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateService }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "updateService")
    public JAXBElement<UpdateService> createUpdateService(UpdateService value) {
        return new JAXBElement<UpdateService>(_UpdateService_QNAME, UpdateService.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetArtifactForActivity }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getArtifactForActivity")
    public JAXBElement<GetArtifactForActivity> createGetArtifactForActivity(GetArtifactForActivity value) {
        return new JAXBElement<GetArtifactForActivity>(_GetArtifactForActivity_QNAME, GetArtifactForActivity.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetParentResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getParentResponse")
    public JAXBElement<GetParentResponse> createGetParentResponse(GetParentResponse value) {
        return new JAXBElement<GetParentResponse>(_GetParentResponse_QNAME, GetParentResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RelateSubnetToVrf }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "relateSubnetToVrf")
    public JAXBElement<RelateSubnetToVrf> createRelateSubnetToVrf(RelateSubnetToVrf value) {
        return new JAXBElement<RelateSubnetToVrf>(_RelateSubnetToVrf_QNAME, RelateSubnetToVrf.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateWarehouse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "createWarehouse")
    public JAXBElement<CreateWarehouse> createCreateWarehouse(CreateWarehouse value) {
        return new JAXBElement<CreateWarehouse>(_CreateWarehouse_QNAME, CreateWarehouse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCustomersInPool }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getCustomersInPool")
    public JAXBElement<GetCustomersInPool> createGetCustomersInPool(GetCustomersInPool value) {
        return new JAXBElement<GetCustomersInPool>(_GetCustomersInPool_QNAME, GetCustomersInPool.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetContract }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getContract")
    public JAXBElement<GetContract> createGetContract(GetContract value) {
        return new JAXBElement<GetContract>(_GetContract_QNAME, GetContract.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ConnectPhysicalContainersResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "connectPhysicalContainersResponse")
    public JAXBElement<ConnectPhysicalContainersResponse> createConnectPhysicalContainersResponse(ConnectPhysicalContainersResponse value) {
        return new JAXBElement<ConnectPhysicalContainersResponse>(_ConnectPhysicalContainersResponse_QNAME, ConnectPhysicalContainersResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTemplateSpecialElementChildrenResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getTemplateSpecialElementChildrenResponse")
    public JAXBElement<GetTemplateSpecialElementChildrenResponse> createGetTemplateSpecialElementChildrenResponse(GetTemplateSpecialElementChildrenResponse value) {
        return new JAXBElement<GetTemplateSpecialElementChildrenResponse>(_GetTemplateSpecialElementChildrenResponse_QNAME, GetTemplateSpecialElementChildrenResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateCustomerPoolResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "createCustomerPoolResponse")
    public JAXBElement<CreateCustomerPoolResponse> createCreateCustomerPoolResponse(CreateCustomerPoolResponse value) {
        return new JAXBElement<CreateCustomerPoolResponse>(_CreateCustomerPoolResponse_QNAME, CreateCustomerPoolResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateGeneralViewResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "createGeneralViewResponse")
    public JAXBElement<CreateGeneralViewResponse> createCreateGeneralViewResponse(CreateGeneralViewResponse value) {
        return new JAXBElement<CreateGeneralViewResponse>(_CreateGeneralViewResponse_QNAME, CreateGeneralViewResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindSDHRoutesUsingContainerLinksResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "findSDHRoutesUsingContainerLinksResponse")
    public JAXBElement<FindSDHRoutesUsingContainerLinksResponse> createFindSDHRoutesUsingContainerLinksResponse(FindSDHRoutesUsingContainerLinksResponse value) {
        return new JAXBElement<FindSDHRoutesUsingContainerLinksResponse>(_FindSDHRoutesUsingContainerLinksResponse_QNAME, FindSDHRoutesUsingContainerLinksResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CommitActivityResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "commitActivityResponse")
    public JAXBElement<CommitActivityResponse> createCommitActivityResponse(CommitActivityResponse value) {
        return new JAXBElement<CommitActivityResponse>(_CommitActivityResponse_QNAME, CommitActivityResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SearchForContactsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "searchForContactsResponse")
    public JAXBElement<SearchForContactsResponse> createSearchForContactsResponse(SearchForContactsResponse value) {
        return new JAXBElement<SearchForContactsResponse>(_SearchForContactsResponse_QNAME, SearchForContactsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateGeneralView }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "createGeneralView")
    public JAXBElement<CreateGeneralView> createCreateGeneralView(CreateGeneralView value) {
        return new JAXBElement<CreateGeneralView>(_CreateGeneralView_QNAME, CreateGeneralView.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AssociateObjectToProxyResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "associateObjectToProxyResponse")
    public JAXBElement<AssociateObjectToProxyResponse> createAssociateObjectToProxyResponse(AssociateObjectToProxyResponse value) {
        return new JAXBElement<AssociateObjectToProxyResponse>(_AssociateObjectToProxyResponse_QNAME, AssociateObjectToProxyResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetInventoryLevelReportsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getInventoryLevelReportsResponse")
    public JAXBElement<GetInventoryLevelReportsResponse> createGetInventoryLevelReportsResponse(GetInventoryLevelReportsResponse value) {
        return new JAXBElement<GetInventoryLevelReportsResponse>(_GetInventoryLevelReportsResponse_QNAME, GetInventoryLevelReportsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetContractPoolsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getContractPoolsResponse")
    public JAXBElement<GetContractPoolsResponse> createGetContractPoolsResponse(GetContractPoolsResponse value) {
        return new JAXBElement<GetContractPoolsResponse>(_GetContractPoolsResponse_QNAME, GetContractPoolsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReleaseObjectFromContract }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "releaseObjectFromContract")
    public JAXBElement<ReleaseObjectFromContract> createReleaseObjectFromContract(ReleaseObjectFromContract value) {
        return new JAXBElement<ReleaseObjectFromContract>(_ReleaseObjectFromContract_QNAME, ReleaseObjectFromContract.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateSDHContainerLink }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "createSDHContainerLink")
    public JAXBElement<CreateSDHContainerLink> createCreateSDHContainerLink(CreateSDHContainerLink value) {
        return new JAXBElement<CreateSDHContainerLink>(_CreateSDHContainerLink_QNAME, CreateSDHContainerLink.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetProjectPoolsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getProjectPoolsResponse")
    public JAXBElement<GetProjectPoolsResponse> createGetProjectPoolsResponse(GetProjectPoolsResponse value) {
        return new JAXBElement<GetProjectPoolsResponse>(_GetProjectPoolsResponse_QNAME, GetProjectPoolsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTemplateElementResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getTemplateElementResponse")
    public JAXBElement<GetTemplateElementResponse> createGetTemplateElementResponse(GetTemplateElementResponse value) {
        return new JAXBElement<GetTemplateElementResponse>(_GetTemplateElementResponse_QNAME, GetTemplateElementResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateConfigurationVariable }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "createConfigurationVariable")
    public JAXBElement<CreateConfigurationVariable> createCreateConfigurationVariable(CreateConfigurationVariable value) {
        return new JAXBElement<CreateConfigurationVariable>(_CreateConfigurationVariable_QNAME, CreateConfigurationVariable.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ServerSideException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "ServerSideException")
    public JAXBElement<ServerSideException> createServerSideException(ServerSideException value) {
        return new JAXBElement<ServerSideException>(_ServerSideException_QNAME, ServerSideException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetFileResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getFileResponse")
    public JAXBElement<GetFileResponse> createGetFileResponse(GetFileResponse value) {
        return new JAXBElement<GetFileResponse>(_GetFileResponse_QNAME, GetFileResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetPossibleSpecialChildrenNoRecursiveResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getPossibleSpecialChildrenNoRecursiveResponse")
    public JAXBElement<GetPossibleSpecialChildrenNoRecursiveResponse> createGetPossibleSpecialChildrenNoRecursiveResponse(GetPossibleSpecialChildrenNoRecursiveResponse value) {
        return new JAXBElement<GetPossibleSpecialChildrenNoRecursiveResponse>(_GetPossibleSpecialChildrenNoRecursiveResponse_QNAME, GetPossibleSpecialChildrenNoRecursiveResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetContractResourcesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getContractResourcesResponse")
    public JAXBElement<GetContractResourcesResponse> createGetContractResourcesResponse(GetContractResourcesResponse value) {
        return new JAXBElement<GetContractResourcesResponse>(_GetContractResourcesResponse_QNAME, GetContractResourcesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateProjectPoolResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "updateProjectPoolResponse")
    public JAXBElement<UpdateProjectPoolResponse> createUpdateProjectPoolResponse(UpdateProjectPoolResponse value) {
        return new JAXBElement<UpdateProjectPoolResponse>(_UpdateProjectPoolResponse_QNAME, UpdateProjectPoolResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetBusinessObjectAuditTrail }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getBusinessObjectAuditTrail")
    public JAXBElement<GetBusinessObjectAuditTrail> createGetBusinessObjectAuditTrail(GetBusinessObjectAuditTrail value) {
        return new JAXBElement<GetBusinessObjectAuditTrail>(_GetBusinessObjectAuditTrail_QNAME, GetBusinessObjectAuditTrail.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetReport }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getReport")
    public JAXBElement<GetReport> createGetReport(GetReport value) {
        return new JAXBElement<GetReport>(_GetReport_QNAME, GetReport.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetObjectSpecialChildren }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getObjectSpecialChildren")
    public JAXBElement<GetObjectSpecialChildren> createGetObjectSpecialChildren(GetObjectSpecialChildren value) {
        return new JAXBElement<GetObjectSpecialChildren>(_GetObjectSpecialChildren_QNAME, GetObjectSpecialChildren.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateObject }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "updateObject")
    public JAXBElement<UpdateObject> createUpdateObject(UpdateObject value) {
        return new JAXBElement<UpdateObject>(_UpdateObject_QNAME, UpdateObject.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MoveObjectsToWarehouseResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "moveObjectsToWarehouseResponse")
    public JAXBElement<MoveObjectsToWarehouseResponse> createMoveObjectsToWarehouseResponse(MoveObjectsToWarehouseResponse value) {
        return new JAXBElement<MoveObjectsToWarehouseResponse>(_MoveObjectsToWarehouseResponse_QNAME, MoveObjectsToWarehouseResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemovePossibleSpecialChildren }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "removePossibleSpecialChildren")
    public JAXBElement<RemovePossibleSpecialChildren> createRemovePossibleSpecialChildren(RemovePossibleSpecialChildren value) {
        return new JAXBElement<RemovePossibleSpecialChildren>(_RemovePossibleSpecialChildren_QNAME, RemovePossibleSpecialChildren.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetUsersInGroup }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getUsersInGroup")
    public JAXBElement<GetUsersInGroup> createGetUsersInGroup(GetUsersInGroup value) {
        return new JAXBElement<GetUsersInGroup>(_GetUsersInGroup_QNAME, GetUsersInGroup.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetFavoritesFoldersForObjectResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getFavoritesFoldersForObjectResponse")
    public JAXBElement<GetFavoritesFoldersForObjectResponse> createGetFavoritesFoldersForObjectResponse(GetFavoritesFoldersForObjectResponse value) {
        return new JAXBElement<GetFavoritesFoldersForObjectResponse>(_GetFavoritesFoldersForObjectResponse_QNAME, GetFavoritesFoldersForObjectResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetLinkConnectedToPortResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getLinkConnectedToPortResponse")
    public JAXBElement<GetLinkConnectedToPortResponse> createGetLinkConnectedToPortResponse(GetLinkConnectedToPortResponse value) {
        return new JAXBElement<GetLinkConnectedToPortResponse>(_GetLinkConnectedToPortResponse_QNAME, GetLinkConnectedToPortResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteProxyResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "deleteProxyResponse")
    public JAXBElement<DeleteProxyResponse> createDeleteProxyResponse(DeleteProxyResponse value) {
        return new JAXBElement<DeleteProxyResponse>(_DeleteProxyResponse_QNAME, DeleteProxyResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateConfigurationVariablesPool }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "updateConfigurationVariablesPool")
    public JAXBElement<UpdateConfigurationVariablesPool> createUpdateConfigurationVariablesPool(UpdateConfigurationVariablesPool value) {
        return new JAXBElement<UpdateConfigurationVariablesPool>(_UpdateConfigurationVariablesPool_QNAME, UpdateConfigurationVariablesPool.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link HasAttribute }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "hasAttribute")
    public JAXBElement<HasAttribute> createHasAttribute(HasAttribute value) {
        return new JAXBElement<HasAttribute>(_HasAttribute_QNAME, HasAttribute.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateObjectResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "updateObjectResponse")
    public JAXBElement<UpdateObjectResponse> createUpdateObjectResponse(UpdateObjectResponse value) {
        return new JAXBElement<UpdateObjectResponse>(_UpdateObjectResponse_QNAME, UpdateObjectResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RelateObjectToProject }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "relateObjectToProject")
    public JAXBElement<RelateObjectToProject> createRelateObjectToProject(RelateObjectToProject value) {
        return new JAXBElement<RelateObjectToProject>(_RelateObjectToProject_QNAME, RelateObjectToProject.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetUpstreamSpecialContainmentHierarchyResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getUpstreamSpecialContainmentHierarchyResponse")
    public JAXBElement<GetUpstreamSpecialContainmentHierarchyResponse> createGetUpstreamSpecialContainmentHierarchyResponse(GetUpstreamSpecialContainmentHierarchyResponse value) {
        return new JAXBElement<GetUpstreamSpecialContainmentHierarchyResponse>(_GetUpstreamSpecialContainmentHierarchyResponse_QNAME, GetUpstreamSpecialContainmentHierarchyResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateTask }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "createTask")
    public JAXBElement<CreateTask> createCreateTask(CreateTask value) {
        return new JAXBElement<CreateTask>(_CreateTask_QNAME, CreateTask.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCommonParent }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getCommonParent")
    public JAXBElement<GetCommonParent> createGetCommonParent(GetCommonParent value) {
        return new JAXBElement<GetCommonParent>(_GetCommonParent_QNAME, GetCommonParent.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindSDHRoutesUsingTransportLinks }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "findSDHRoutesUsingTransportLinks")
    public JAXBElement<FindSDHRoutesUsingTransportLinks> createFindSDHRoutesUsingTransportLinks(FindSDHRoutesUsingTransportLinks value) {
        return new JAXBElement<FindSDHRoutesUsingTransportLinks>(_FindSDHRoutesUsingTransportLinks_QNAME, FindSDHRoutesUsingTransportLinks.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteProjectResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "deleteProjectResponse")
    public JAXBElement<DeleteProjectResponse> createDeleteProjectResponse(DeleteProjectResponse value) {
        return new JAXBElement<DeleteProjectResponse>(_DeleteProjectResponse_QNAME, DeleteProjectResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DetachFileFromObject }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "detachFileFromObject")
    public JAXBElement<DetachFileFromObject> createDetachFileFromObject(DetachFileFromObject value) {
        return new JAXBElement<DetachFileFromObject>(_DetachFileFromObject_QNAME, DetachFileFromObject.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteListTypeItemResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "deleteListTypeItemResponse")
    public JAXBElement<DeleteListTypeItemResponse> createDeleteListTypeItemResponse(DeleteListTypeItemResponse value) {
        return new JAXBElement<DeleteListTypeItemResponse>(_DeleteListTypeItemResponse_QNAME, DeleteListTypeItemResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetPhysicalConnectionsInObjectResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getPhysicalConnectionsInObjectResponse")
    public JAXBElement<GetPhysicalConnectionsInObjectResponse> createGetPhysicalConnectionsInObjectResponse(GetPhysicalConnectionsInObjectResponse value) {
        return new JAXBElement<GetPhysicalConnectionsInObjectResponse>(_GetPhysicalConnectionsInObjectResponse_QNAME, GetPhysicalConnectionsInObjectResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetObjectRelatedView }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getObjectRelatedView")
    public JAXBElement<GetObjectRelatedView> createGetObjectRelatedView(GetObjectRelatedView value) {
        return new JAXBElement<GetObjectRelatedView>(_GetObjectRelatedView_QNAME, GetObjectRelatedView.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetChildrenOfClassResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getChildrenOfClassResponse")
    public JAXBElement<GetChildrenOfClassResponse> createGetChildrenOfClassResponse(GetChildrenOfClassResponse value) {
        return new JAXBElement<GetChildrenOfClassResponse>(_GetChildrenOfClassResponse_QNAME, GetChildrenOfClassResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateProject }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "createProject")
    public JAXBElement<CreateProject> createCreateProject(CreateProject value) {
        return new JAXBElement<CreateProject>(_CreateProject_QNAME, CreateProject.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeletePhysicalConnectionResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "deletePhysicalConnectionResponse")
    public JAXBElement<DeletePhysicalConnectionResponse> createDeletePhysicalConnectionResponse(DeletePhysicalConnectionResponse value) {
        return new JAXBElement<DeletePhysicalConnectionResponse>(_DeletePhysicalConnectionResponse_QNAME, DeletePhysicalConnectionResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetFavoritesFoldersForUserResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getFavoritesFoldersForUserResponse")
    public JAXBElement<GetFavoritesFoldersForUserResponse> createGetFavoritesFoldersForUserResponse(GetFavoritesFoldersForUserResponse value) {
        return new JAXBElement<GetFavoritesFoldersForUserResponse>(_GetFavoritesFoldersForUserResponse_QNAME, GetFavoritesFoldersForUserResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreatePoolInPool }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "createPoolInPool")
    public JAXBElement<CreatePoolInPool> createCreatePoolInPool(CreatePoolInPool value) {
        return new JAXBElement<CreatePoolInPool>(_CreatePoolInPool_QNAME, CreatePoolInPool.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreatePoolInPoolResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "createPoolInPoolResponse")
    public JAXBElement<CreatePoolInPoolResponse> createCreatePoolInPoolResponse(CreatePoolInPoolResponse value) {
        return new JAXBElement<CreatePoolInPoolResponse>(_CreatePoolInPoolResponse_QNAME, CreatePoolInPoolResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetListTypeItemRelatedViewsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getListTypeItemRelatedViewsResponse")
    public JAXBElement<GetListTypeItemRelatedViewsResponse> createGetListTypeItemRelatedViewsResponse(GetListTypeItemRelatedViewsResponse value) {
        return new JAXBElement<GetListTypeItemRelatedViewsResponse>(_GetListTypeItemRelatedViewsResponse_QNAME, GetListTypeItemRelatedViewsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateSynchronizationDataSourceConfigResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "createSynchronizationDataSourceConfigResponse")
    public JAXBElement<CreateSynchronizationDataSourceConfigResponse> createCreateSynchronizationDataSourceConfigResponse(CreateSynchronizationDataSourceConfigResponse value) {
        return new JAXBElement<CreateSynchronizationDataSourceConfigResponse>(_CreateSynchronizationDataSourceConfigResponse_QNAME, CreateSynchronizationDataSourceConfigResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetContainersBetweenObjects }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getContainersBetweenObjects")
    public JAXBElement<GetContainersBetweenObjects> createGetContainersBetweenObjects(GetContainersBetweenObjects value) {
        return new JAXBElement<GetContainersBetweenObjects>(_GetContainersBetweenObjects_QNAME, GetContainersBetweenObjects.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateBulkSpecialTemplateElement }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "createBulkSpecialTemplateElement")
    public JAXBElement<CreateBulkSpecialTemplateElement> createCreateBulkSpecialTemplateElement(CreateBulkSpecialTemplateElement value) {
        return new JAXBElement<CreateBulkSpecialTemplateElement>(_CreateBulkSpecialTemplateElement_QNAME, CreateBulkSpecialTemplateElement.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteClass }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "deleteClass")
    public JAXBElement<DeleteClass> createDeleteClass(DeleteClass value) {
        return new JAXBElement<DeleteClass>(_DeleteClass_QNAME, DeleteClass.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDeviceLayoutsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getDeviceLayoutsResponse")
    public JAXBElement<GetDeviceLayoutsResponse> createGetDeviceLayoutsResponse(GetDeviceLayoutsResponse value) {
        return new JAXBElement<GetDeviceLayoutsResponse>(_GetDeviceLayoutsResponse_QNAME, GetDeviceLayoutsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MoveSpecialObjects }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "moveSpecialObjects")
    public JAXBElement<MoveSpecialObjects> createMoveSpecialObjects(MoveSpecialObjects value) {
        return new JAXBElement<MoveSpecialObjects>(_MoveSpecialObjects_QNAME, MoveSpecialObjects.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllContracts }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getAllContracts")
    public JAXBElement<GetAllContracts> createGetAllContracts(GetAllContracts value) {
        return new JAXBElement<GetAllContracts>(_GetAllContracts_QNAME, GetAllContracts.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetE2EView }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getE2EView")
    public JAXBElement<GetE2EView> createGetE2EView(GetE2EView value) {
        return new JAXBElement<GetE2EView>(_GetE2EView_QNAME, GetE2EView.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateListTypeItemRelatedViewResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "createListTypeItemRelatedViewResponse")
    public JAXBElement<CreateListTypeItemRelatedViewResponse> createCreateListTypeItemRelatedViewResponse(CreateListTypeItemRelatedViewResponse value) {
        return new JAXBElement<CreateListTypeItemRelatedViewResponse>(_CreateListTypeItemRelatedViewResponse_QNAME, CreateListTypeItemRelatedViewResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateSpecialObjectResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "createSpecialObjectResponse")
    public JAXBElement<CreateSpecialObjectResponse> createCreateSpecialObjectResponse(CreateSpecialObjectResponse value) {
        return new JAXBElement<CreateSpecialObjectResponse>(_CreateSpecialObjectResponse_QNAME, CreateSpecialObjectResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateConfigurationVariablesPoolResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "createConfigurationVariablesPoolResponse")
    public JAXBElement<CreateConfigurationVariablesPoolResponse> createCreateConfigurationVariablesPoolResponse(CreateConfigurationVariablesPoolResponse value) {
        return new JAXBElement<CreateConfigurationVariablesPoolResponse>(_CreateConfigurationVariablesPoolResponse_QNAME, CreateConfigurationVariablesPoolResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateSDHTransportLinkResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "createSDHTransportLinkResponse")
    public JAXBElement<CreateSDHTransportLinkResponse> createCreateSDHTransportLinkResponse(CreateSDHTransportLinkResponse value) {
        return new JAXBElement<CreateSDHTransportLinkResponse>(_CreateSDHTransportLinkResponse_QNAME, CreateSDHTransportLinkResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateProcessDefinitionResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "updateProcessDefinitionResponse")
    public JAXBElement<UpdateProcessDefinitionResponse> createUpdateProcessDefinitionResponse(UpdateProcessDefinitionResponse value) {
        return new JAXBElement<UpdateProcessDefinitionResponse>(_UpdateProcessDefinitionResponse_QNAME, UpdateProcessDefinitionResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteSynchronizationGroup }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "deleteSynchronizationGroup")
    public JAXBElement<DeleteSynchronizationGroup> createDeleteSynchronizationGroup(DeleteSynchronizationGroup value) {
        return new JAXBElement<DeleteSynchronizationGroup>(_DeleteSynchronizationGroup_QNAME, DeleteSynchronizationGroup.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RelateObjectsToContractResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "relateObjectsToContractResponse")
    public JAXBElement<RelateObjectsToContractResponse> createRelateObjectsToContractResponse(RelateObjectsToContractResponse value) {
        return new JAXBElement<RelateObjectsToContractResponse>(_RelateObjectsToContractResponse_QNAME, RelateObjectsToContractResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetConfigurationVariablesWithPrefix }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getConfigurationVariablesWithPrefix")
    public JAXBElement<GetConfigurationVariablesWithPrefix> createGetConfigurationVariablesWithPrefix(GetConfigurationVariablesWithPrefix value) {
        return new JAXBElement<GetConfigurationVariablesWithPrefix>(_GetConfigurationVariablesWithPrefix_QNAME, GetConfigurationVariablesWithPrefix.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SubscribeUserToTaskResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "subscribeUserToTaskResponse")
    public JAXBElement<SubscribeUserToTaskResponse> createSubscribeUserToTaskResponse(SubscribeUserToTaskResponse value) {
        return new JAXBElement<SubscribeUserToTaskResponse>(_SubscribeUserToTaskResponse_QNAME, SubscribeUserToTaskResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateActivityResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "createActivityResponse")
    public JAXBElement<CreateActivityResponse> createCreateActivityResponse(CreateActivityResponse value) {
        return new JAXBElement<CreateActivityResponse>(_CreateActivityResponse_QNAME, CreateActivityResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddPossibleSpecialChildrenWithIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "addPossibleSpecialChildrenWithIdResponse")
    public JAXBElement<AddPossibleSpecialChildrenWithIdResponse> createAddPossibleSpecialChildrenWithIdResponse(AddPossibleSpecialChildrenWithIdResponse value) {
        return new JAXBElement<AddPossibleSpecialChildrenWithIdResponse>(_AddPossibleSpecialChildrenWithIdResponse_QNAME, AddPossibleSpecialChildrenWithIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateTaskNotificationTypeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "updateTaskNotificationTypeResponse")
    public JAXBElement<UpdateTaskNotificationTypeResponse> createUpdateTaskNotificationTypeResponse(UpdateTaskNotificationTypeResponse value) {
        return new JAXBElement<UpdateTaskNotificationTypeResponse>(_UpdateTaskNotificationTypeResponse_QNAME, UpdateTaskNotificationTypeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteServiceResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "deleteServiceResponse")
    public JAXBElement<DeleteServiceResponse> createDeleteServiceResponse(DeleteServiceResponse value) {
        return new JAXBElement<DeleteServiceResponse>(_DeleteServiceResponse_QNAME, DeleteServiceResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RelatePortToInterface }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "relatePortToInterface")
    public JAXBElement<RelatePortToInterface> createRelatePortToInterface(RelatePortToInterface value) {
        return new JAXBElement<RelatePortToInterface>(_RelatePortToInterface_QNAME, RelatePortToInterface.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateContactResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "createContactResponse")
    public JAXBElement<CreateContactResponse> createCreateContactResponse(CreateContactResponse value) {
        return new JAXBElement<CreateContactResponse>(_CreateContactResponse_QNAME, CreateContactResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCustomersInPoolResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getCustomersInPoolResponse")
    public JAXBElement<GetCustomersInPoolResponse> createGetCustomersInPoolResponse(GetCustomersInPoolResponse value) {
        return new JAXBElement<GetCustomersInPoolResponse>(_GetCustomersInPoolResponse_QNAME, GetCustomersInPoolResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSubnetUsedIps }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getSubnetUsedIps")
    public JAXBElement<GetSubnetUsedIps> createGetSubnetUsedIps(GetSubnetUsedIps value) {
        return new JAXBElement<GetSubnetUsedIps>(_GetSubnetUsedIps_QNAME, GetSubnetUsedIps.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateGeneralViewResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "updateGeneralViewResponse")
    public JAXBElement<UpdateGeneralViewResponse> createUpdateGeneralViewResponse(UpdateGeneralViewResponse value) {
        return new JAXBElement<UpdateGeneralViewResponse>(_UpdateGeneralViewResponse_QNAME, UpdateGeneralViewResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateTemplate }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "createTemplate")
    public JAXBElement<CreateTemplate> createCreateTemplate(CreateTemplate value) {
        return new JAXBElement<CreateTemplate>(_CreateTemplate_QNAME, CreateTemplate.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteValidatorDefinitionResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "deleteValidatorDefinitionResponse")
    public JAXBElement<DeleteValidatorDefinitionResponse> createDeleteValidatorDefinitionResponse(DeleteValidatorDefinitionResponse value) {
        return new JAXBElement<DeleteValidatorDefinitionResponse>(_DeleteValidatorDefinitionResponse_QNAME, DeleteValidatorDefinitionResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DisconnectMPLSLink }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "disconnectMPLSLink")
    public JAXBElement<DisconnectMPLSLink> createDisconnectMPLSLink(DisconnectMPLSLink value) {
        return new JAXBElement<DisconnectMPLSLink>(_DisconnectMPLSLink_QNAME, DisconnectMPLSLink.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetInstanceableListTypesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getInstanceableListTypesResponse")
    public JAXBElement<GetInstanceableListTypesResponse> createGetInstanceableListTypesResponse(GetInstanceableListTypesResponse value) {
        return new JAXBElement<GetInstanceableListTypesResponse>(_GetInstanceableListTypesResponse_QNAME, GetInstanceableListTypesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetProjectsWithFilter }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getProjectsWithFilter")
    public JAXBElement<GetProjectsWithFilter> createGetProjectsWithFilter(GetProjectsWithFilter value) {
        return new JAXBElement<GetProjectsWithFilter>(_GetProjectsWithFilter_QNAME, GetProjectsWithFilter.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetPossibleSpecialChildrenNoRecursive }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getPossibleSpecialChildrenNoRecursive")
    public JAXBElement<GetPossibleSpecialChildrenNoRecursive> createGetPossibleSpecialChildrenNoRecursive(GetPossibleSpecialChildrenNoRecursive value) {
        return new JAXBElement<GetPossibleSpecialChildrenNoRecursive>(_GetPossibleSpecialChildrenNoRecursive_QNAME, GetPossibleSpecialChildrenNoRecursive.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetProject }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getProject")
    public JAXBElement<GetProject> createGetProject(GetProject value) {
        return new JAXBElement<GetProject>(_GetProject_QNAME, GetProject.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTemplatesForClassResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getTemplatesForClassResponse")
    public JAXBElement<GetTemplatesForClassResponse> createGetTemplatesForClassResponse(GetTemplatesForClassResponse value) {
        return new JAXBElement<GetTemplatesForClassResponse>(_GetTemplatesForClassResponse_QNAME, GetTemplatesForClassResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllClassesLightResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getAllClassesLightResponse")
    public JAXBElement<GetAllClassesLightResponse> createGetAllClassesLightResponse(GetAllClassesLightResponse value) {
        return new JAXBElement<GetAllClassesLightResponse>(_GetAllClassesLightResponse_QNAME, GetAllClassesLightResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteQueryResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "deleteQueryResponse")
    public JAXBElement<DeleteQueryResponse> createDeleteQueryResponse(DeleteQueryResponse value) {
        return new JAXBElement<DeleteQueryResponse>(_DeleteQueryResponse_QNAME, DeleteQueryResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllServices }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getAllServices")
    public JAXBElement<GetAllServices> createGetAllServices(GetAllServices value) {
        return new JAXBElement<GetAllServices>(_GetAllServices_QNAME, GetAllServices.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSyncDataSourceConfigurationsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getSyncDataSourceConfigurationsResponse")
    public JAXBElement<GetSyncDataSourceConfigurationsResponse> createGetSyncDataSourceConfigurationsResponse(GetSyncDataSourceConfigurationsResponse value) {
        return new JAXBElement<GetSyncDataSourceConfigurationsResponse>(_GetSyncDataSourceConfigurationsResponse_QNAME, GetSyncDataSourceConfigurationsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSpecialChildrenOfClassLightRecursive }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getSpecialChildrenOfClassLightRecursive")
    public JAXBElement<GetSpecialChildrenOfClassLightRecursive> createGetSpecialChildrenOfClassLightRecursive(GetSpecialChildrenOfClassLightRecursive value) {
        return new JAXBElement<GetSpecialChildrenOfClassLightRecursive>(_GetSpecialChildrenOfClassLightRecursive_QNAME, GetSpecialChildrenOfClassLightRecursive.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SetClassPropertiesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "setClassPropertiesResponse")
    public JAXBElement<SetClassPropertiesResponse> createSetClassPropertiesResponse(SetClassPropertiesResponse value) {
        return new JAXBElement<SetClassPropertiesResponse>(_SetClassPropertiesResponse_QNAME, SetClassPropertiesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetFilesForObjectResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getFilesForObjectResponse")
    public JAXBElement<GetFilesForObjectResponse> createGetFilesForObjectResponse(GetFilesForObjectResponse value) {
        return new JAXBElement<GetFilesForObjectResponse>(_GetFilesForObjectResponse_QNAME, GetFilesForObjectResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetPhysicalConnectionsInObject }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getPhysicalConnectionsInObject")
    public JAXBElement<GetPhysicalConnectionsInObject> createGetPhysicalConnectionsInObject(GetPhysicalConnectionsInObject value) {
        return new JAXBElement<GetPhysicalConnectionsInObject>(_GetPhysicalConnectionsInObject_QNAME, GetPhysicalConnectionsInObject.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCurrentJobs }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getCurrentJobs")
    public JAXBElement<GetCurrentJobs> createGetCurrentJobs(GetCurrentJobs value) {
        return new JAXBElement<GetCurrentJobs>(_GetCurrentJobs_QNAME, GetCurrentJobs.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AssociateObjectToProxy }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "associateObjectToProxy")
    public JAXBElement<AssociateObjectToProxy> createAssociateObjectToProxy(AssociateObjectToProxy value) {
        return new JAXBElement<AssociateObjectToProxy>(_AssociateObjectToProxy_QNAME, AssociateObjectToProxy.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetServicePoolsInCostumer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getServicePoolsInCostumer")
    public JAXBElement<GetServicePoolsInCostumer> createGetServicePoolsInCostumer(GetServicePoolsInCostumer value) {
        return new JAXBElement<GetServicePoolsInCostumer>(_GetServicePoolsInCostumer_QNAME, GetServicePoolsInCostumer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MoveObjectsToWarehousePool }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "moveObjectsToWarehousePool")
    public JAXBElement<MoveObjectsToWarehousePool> createMoveObjectsToWarehousePool(MoveObjectsToWarehousePool value) {
        return new JAXBElement<MoveObjectsToWarehousePool>(_MoveObjectsToWarehousePool_QNAME, MoveObjectsToWarehousePool.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateObjectRelatedView }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "updateObjectRelatedView")
    public JAXBElement<UpdateObjectRelatedView> createUpdateObjectRelatedView(UpdateObjectRelatedView value) {
        return new JAXBElement<UpdateObjectRelatedView>(_UpdateObjectRelatedView_QNAME, UpdateObjectRelatedView.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetParentOfClass }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getParentOfClass")
    public JAXBElement<GetParentOfClass> createGetParentOfClass(GetParentOfClass value) {
        return new JAXBElement<GetParentOfClass>(_GetParentOfClass_QNAME, GetParentOfClass.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CopyTemplateElementsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "copyTemplateElementsResponse")
    public JAXBElement<CopyTemplateElementsResponse> createCopyTemplateElementsResponse(CopyTemplateElementsResponse value) {
        return new JAXBElement<CopyTemplateElementsResponse>(_CopyTemplateElementsResponse_QNAME, CopyTemplateElementsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ItOverlapsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "itOverlapsResponse")
    public JAXBElement<ItOverlapsResponse> createItOverlapsResponse(ItOverlapsResponse value) {
        return new JAXBElement<ItOverlapsResponse>(_ItOverlapsResponse_QNAME, ItOverlapsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateTaskScheduleResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "updateTaskScheduleResponse")
    public JAXBElement<UpdateTaskScheduleResponse> createUpdateTaskScheduleResponse(UpdateTaskScheduleResponse value) {
        return new JAXBElement<UpdateTaskScheduleResponse>(_UpdateTaskScheduleResponse_QNAME, UpdateTaskScheduleResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetService }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getService")
    public JAXBElement<GetService> createGetService(GetService value) {
        return new JAXBElement<GetService>(_GetService_QNAME, GetService.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateInventoryLevelReportResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "createInventoryLevelReportResponse")
    public JAXBElement<CreateInventoryLevelReportResponse> createCreateInventoryLevelReportResponse(CreateInventoryLevelReportResponse value) {
        return new JAXBElement<CreateInventoryLevelReportResponse>(_CreateInventoryLevelReportResponse_QNAME, CreateInventoryLevelReportResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RelateSubnetToVrfResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "relateSubnetToVrfResponse")
    public JAXBElement<RelateSubnetToVrfResponse> createRelateSubnetToVrfResponse(RelateSubnetToVrfResponse value) {
        return new JAXBElement<RelateSubnetToVrfResponse>(_RelateSubnetToVrfResponse_QNAME, RelateSubnetToVrfResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetProjectsWithFilterResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getProjectsWithFilterResponse")
    public JAXBElement<GetProjectsWithFilterResponse> createGetProjectsWithFilterResponse(GetProjectsWithFilterResponse value) {
        return new JAXBElement<GetProjectsWithFilterResponse>(_GetProjectsWithFilterResponse_QNAME, GetProjectsWithFilterResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSubnetsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getSubnetsResponse")
    public JAXBElement<GetSubnetsResponse> createGetSubnetsResponse(GetSubnetsResponse value) {
        return new JAXBElement<GetSubnetsResponse>(_GetSubnetsResponse_QNAME, GetSubnetsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTask }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getTask")
    public JAXBElement<GetTask> createGetTask(GetTask value) {
        return new JAXBElement<GetTask>(_GetTask_QNAME, GetTask.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateSyncDataSourceConfigurationResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "updateSyncDataSourceConfigurationResponse")
    public JAXBElement<UpdateSyncDataSourceConfigurationResponse> createUpdateSyncDataSourceConfigurationResponse(UpdateSyncDataSourceConfigurationResponse value) {
        return new JAXBElement<UpdateSyncDataSourceConfigurationResponse>(_UpdateSyncDataSourceConfigurationResponse_QNAME, UpdateSyncDataSourceConfigurationResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AssociatesPhysicalNodeToWarehouse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "associatesPhysicalNodeToWarehouse")
    public JAXBElement<AssociatesPhysicalNodeToWarehouse> createAssociatesPhysicalNodeToWarehouse(AssociatesPhysicalNodeToWarehouse value) {
        return new JAXBElement<AssociatesPhysicalNodeToWarehouse>(_AssociatesPhysicalNodeToWarehouse_QNAME, AssociatesPhysicalNodeToWarehouse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetObjectChildrenForClassWithIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getObjectChildrenForClassWithIdResponse")
    public JAXBElement<GetObjectChildrenForClassWithIdResponse> createGetObjectChildrenForClassWithIdResponse(GetObjectChildrenForClassWithIdResponse value) {
        return new JAXBElement<GetObjectChildrenForClassWithIdResponse>(_GetObjectChildrenForClassWithIdResponse_QNAME, GetObjectChildrenForClassWithIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetConfigurationVariablesInPool }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getConfigurationVariablesInPool")
    public JAXBElement<GetConfigurationVariablesInPool> createGetConfigurationVariablesInPool(GetConfigurationVariablesInPool value) {
        return new JAXBElement<GetConfigurationVariablesInPool>(_GetConfigurationVariablesInPool_QNAME, GetConfigurationVariablesInPool.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateProjectPool }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "updateProjectPool")
    public JAXBElement<UpdateProjectPool> createUpdateProjectPool(UpdateProjectPool value) {
        return new JAXBElement<UpdateProjectPool>(_UpdateProjectPool_QNAME, UpdateProjectPool.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateProxyPool }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "createProxyPool")
    public JAXBElement<CreateProxyPool> createCreateProxyPool(CreateProxyPool value) {
        return new JAXBElement<CreateProxyPool>(_CreateProxyPool_QNAME, CreateProxyPool.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReleaseSubnetFromVRFResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "releaseSubnetFromVRFResponse")
    public JAXBElement<ReleaseSubnetFromVRFResponse> createReleaseSubnetFromVRFResponse(ReleaseSubnetFromVRFResponse value) {
        return new JAXBElement<ReleaseSubnetFromVRFResponse>(_ReleaseSubnetFromVRFResponse_QNAME, ReleaseSubnetFromVRFResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetObjectsOfClassLightResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getObjectsOfClassLightResponse")
    public JAXBElement<GetObjectsOfClassLightResponse> createGetObjectsOfClassLightResponse(GetObjectsOfClassLightResponse value) {
        return new JAXBElement<GetObjectsOfClassLightResponse>(_GetObjectsOfClassLightResponse_QNAME, GetObjectsOfClassLightResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReleaseObjectFromServiceResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "releaseObjectFromServiceResponse")
    public JAXBElement<ReleaseObjectFromServiceResponse> createReleaseObjectFromServiceResponse(ReleaseObjectFromServiceResponse value) {
        return new JAXBElement<ReleaseObjectFromServiceResponse>(_ReleaseObjectFromServiceResponse_QNAME, ReleaseObjectFromServiceResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ConnectPhysicalLinksResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "connectPhysicalLinksResponse")
    public JAXBElement<ConnectPhysicalLinksResponse> createConnectPhysicalLinksResponse(ConnectPhysicalLinksResponse value) {
        return new JAXBElement<ConnectPhysicalLinksResponse>(_ConnectPhysicalLinksResponse_QNAME, ConnectPhysicalLinksResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateClassLevelReportResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "createClassLevelReportResponse")
    public JAXBElement<CreateClassLevelReportResponse> createCreateClassLevelReportResponse(CreateClassLevelReportResponse value) {
        return new JAXBElement<CreateClassLevelReportResponse>(_CreateClassLevelReportResponse_QNAME, CreateClassLevelReportResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateTemplateSpecialElement }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "createTemplateSpecialElement")
    public JAXBElement<CreateTemplateSpecialElement> createCreateTemplateSpecialElement(CreateTemplateSpecialElement value) {
        return new JAXBElement<CreateTemplateSpecialElement>(_CreateTemplateSpecialElement_QNAME, CreateTemplateSpecialElement.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetParents }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getParents")
    public JAXBElement<GetParents> createGetParents(GetParents value) {
        return new JAXBElement<GetParents>(_GetParents_QNAME, GetParents.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCustomerPools }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getCustomerPools")
    public JAXBElement<GetCustomerPools> createGetCustomerPools(GetCustomerPools value) {
        return new JAXBElement<GetCustomerPools>(_GetCustomerPools_QNAME, GetCustomerPools.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreatePoolInWarehouse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "createPoolInWarehouse")
    public JAXBElement<CreatePoolInWarehouse> createCreatePoolInWarehouse(CreatePoolInWarehouse value) {
        return new JAXBElement<CreatePoolInWarehouse>(_CreatePoolInWarehouse_QNAME, CreatePoolInWarehouse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateSynchronizationGroup }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "updateSynchronizationGroup")
    public JAXBElement<UpdateSynchronizationGroup> createUpdateSynchronizationGroup(UpdateSynchronizationGroup value) {
        return new JAXBElement<UpdateSynchronizationGroup>(_UpdateSynchronizationGroup_QNAME, UpdateSynchronizationGroup.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetContractResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getContractResponse")
    public JAXBElement<GetContractResponse> createGetContractResponse(GetContractResponse value) {
        return new JAXBElement<GetContractResponse>(_GetContractResponse_QNAME, GetContractResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetObject }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getObject")
    public JAXBElement<GetObject> createGetObject(GetObject value) {
        return new JAXBElement<GetObject>(_GetObject_QNAME, GetObject.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetLogicalLinkDetails }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getLogicalLinkDetails")
    public JAXBElement<GetLogicalLinkDetails> createGetLogicalLinkDetails(GetLogicalLinkDetails value) {
        return new JAXBElement<GetLogicalLinkDetails>(_GetLogicalLinkDetails_QNAME, GetLogicalLinkDetails.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetPoolsInObject }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getPoolsInObject")
    public JAXBElement<GetPoolsInObject> createGetPoolsInObject(GetPoolsInObject value) {
        return new JAXBElement<GetPoolsInObject>(_GetPoolsInObject_QNAME, GetPoolsInObject.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetFavoritesFolder }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getFavoritesFolder")
    public JAXBElement<GetFavoritesFolder> createGetFavoritesFolder(GetFavoritesFolder value) {
        return new JAXBElement<GetFavoritesFolder>(_GetFavoritesFolder_QNAME, GetFavoritesFolder.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetListTypeItemRelatedViewResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getListTypeItemRelatedViewResponse")
    public JAXBElement<GetListTypeItemRelatedViewResponse> createGetListTypeItemRelatedViewResponse(GetListTypeItemRelatedViewResponse value) {
        return new JAXBElement<GetListTypeItemRelatedViewResponse>(_GetListTypeItemRelatedViewResponse_QNAME, GetListTypeItemRelatedViewResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateService }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "createService")
    public JAXBElement<CreateService> createCreateService(CreateService value) {
        return new JAXBElement<CreateService>(_CreateService_QNAME, CreateService.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteAttributeForClassWithId }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "deleteAttributeForClassWithId")
    public JAXBElement<DeleteAttributeForClassWithId> createDeleteAttributeForClassWithId(DeleteAttributeForClassWithId value) {
        return new JAXBElement<DeleteAttributeForClassWithId>(_DeleteAttributeForClassWithId_QNAME, DeleteAttributeForClassWithId.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteSparePoolResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "deleteSparePoolResponse")
    public JAXBElement<DeleteSparePoolResponse> createDeleteSparePoolResponse(DeleteSparePoolResponse value) {
        return new JAXBElement<DeleteSparePoolResponse>(_DeleteSparePoolResponse_QNAME, DeleteSparePoolResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSubnetPoolsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getSubnetPoolsResponse")
    public JAXBElement<GetSubnetPoolsResponse> createGetSubnetPoolsResponse(GetSubnetPoolsResponse value) {
        return new JAXBElement<GetSubnetPoolsResponse>(_GetSubnetPoolsResponse_QNAME, GetSubnetPoolsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetUpstreamClassHierarchyResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getUpstreamClassHierarchyResponse")
    public JAXBElement<GetUpstreamClassHierarchyResponse> createGetUpstreamClassHierarchyResponse(GetUpstreamClassHierarchyResponse value) {
        return new JAXBElement<GetUpstreamClassHierarchyResponse>(_GetUpstreamClassHierarchyResponse_QNAME, GetUpstreamClassHierarchyResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateQueryResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "createQueryResponse")
    public JAXBElement<CreateQueryResponse> createCreateQueryResponse(CreateQueryResponse value) {
        return new JAXBElement<CreateQueryResponse>(_CreateQueryResponse_QNAME, CreateQueryResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetObjectChildrenResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getObjectChildrenResponse")
    public JAXBElement<GetObjectChildrenResponse> createGetObjectChildrenResponse(GetObjectChildrenResponse value) {
        return new JAXBElement<GetObjectChildrenResponse>(_GetObjectChildrenResponse_QNAME, GetObjectChildrenResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetServicePoolResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getServicePoolResponse")
    public JAXBElement<GetServicePoolResponse> createGetServicePoolResponse(GetServicePoolResponse value) {
        return new JAXBElement<GetServicePoolResponse>(_GetServicePoolResponse_QNAME, GetServicePoolResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteSDHTransportLink }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "deleteSDHTransportLink")
    public JAXBElement<DeleteSDHTransportLink> createDeleteSDHTransportLink(DeleteSDHTransportLink value) {
        return new JAXBElement<DeleteSDHTransportLink>(_DeleteSDHTransportLink_QNAME, DeleteSDHTransportLink.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAttributeForClassWithIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getAttributeForClassWithIdResponse")
    public JAXBElement<GetAttributeForClassWithIdResponse> createGetAttributeForClassWithIdResponse(GetAttributeForClassWithIdResponse value) {
        return new JAXBElement<GetAttributeForClassWithIdResponse>(_GetAttributeForClassWithIdResponse_QNAME, GetAttributeForClassWithIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetPoolItems }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getPoolItems")
    public JAXBElement<GetPoolItems> createGetPoolItems(GetPoolItems value) {
        return new JAXBElement<GetPoolItems>(_GetPoolItems_QNAME, GetPoolItems.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateOSPView }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "updateOSPView")
    public JAXBElement<UpdateOSPView> createUpdateOSPView(UpdateOSPView value) {
        return new JAXBElement<UpdateOSPView>(_UpdateOSPView_QNAME, UpdateOSPView.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTaskResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getTaskResponse")
    public JAXBElement<GetTaskResponse> createGetTaskResponse(GetTaskResponse value) {
        return new JAXBElement<GetTaskResponse>(_GetTaskResponse_QNAME, GetTaskResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateFavoritesFolder }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "updateFavoritesFolder")
    public JAXBElement<UpdateFavoritesFolder> createUpdateFavoritesFolder(UpdateFavoritesFolder value) {
        return new JAXBElement<UpdateFavoritesFolder>(_UpdateFavoritesFolder_QNAME, UpdateFavoritesFolder.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCurrentJobsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getCurrentJobsResponse")
    public JAXBElement<GetCurrentJobsResponse> createGetCurrentJobsResponse(GetCurrentJobsResponse value) {
        return new JAXBElement<GetCurrentJobsResponse>(_GetCurrentJobsResponse_QNAME, GetCurrentJobsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSpecialAttribute }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getSpecialAttribute")
    public JAXBElement<GetSpecialAttribute> createGetSpecialAttribute(GetSpecialAttribute value) {
        return new JAXBElement<GetSpecialAttribute>(_GetSpecialAttribute_QNAME, GetSpecialAttribute.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteSDHContainerLink }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "deleteSDHContainerLink")
    public JAXBElement<DeleteSDHContainerLink> createDeleteSDHContainerLink(DeleteSDHContainerLink value) {
        return new JAXBElement<DeleteSDHContainerLink>(_DeleteSDHContainerLink_QNAME, DeleteSDHContainerLink.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateBulkSpecialObjectsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "createBulkSpecialObjectsResponse")
    public JAXBElement<CreateBulkSpecialObjectsResponse> createCreateBulkSpecialObjectsResponse(CreateBulkSpecialObjectsResponse value) {
        return new JAXBElement<CreateBulkSpecialObjectsResponse>(_CreateBulkSpecialObjectsResponse_QNAME, CreateBulkSpecialObjectsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateContract }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "updateContract")
    public JAXBElement<UpdateContract> createUpdateContract(UpdateContract value) {
        return new JAXBElement<UpdateContract>(_UpdateContract_QNAME, UpdateContract.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateServicePool }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "updateServicePool")
    public JAXBElement<UpdateServicePool> createUpdateServicePool(UpdateServicePool value) {
        return new JAXBElement<UpdateServicePool>(_UpdateServicePool_QNAME, UpdateServicePool.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteMPLSLink }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "deleteMPLSLink")
    public JAXBElement<DeleteMPLSLink> createDeleteMPLSLink(DeleteMPLSLink value) {
        return new JAXBElement<DeleteMPLSLink>(_DeleteMPLSLink_QNAME, DeleteMPLSLink.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSyncDataSourceConfigurationResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getSyncDataSourceConfigurationResponse")
    public JAXBElement<GetSyncDataSourceConfigurationResponse> createGetSyncDataSourceConfigurationResponse(GetSyncDataSourceConfigurationResponse value) {
        return new JAXBElement<GetSyncDataSourceConfigurationResponse>(_GetSyncDataSourceConfigurationResponse_QNAME, GetSyncDataSourceConfigurationResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link KillJob }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "killJob")
    public JAXBElement<KillJob> createKillJob(KillJob value) {
        return new JAXBElement<KillJob>(_KillJob_QNAME, KillJob.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SetAttributePropertiesForClassWithIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "setAttributePropertiesForClassWithIdResponse")
    public JAXBElement<SetAttributePropertiesForClassWithIdResponse> createSetAttributePropertiesForClassWithIdResponse(SetAttributePropertiesForClassWithIdResponse value) {
        return new JAXBElement<SetAttributePropertiesForClassWithIdResponse>(_SetAttributePropertiesForClassWithIdResponse_QNAME, SetAttributePropertiesForClassWithIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreatePoolInObjectResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "createPoolInObjectResponse")
    public JAXBElement<CreatePoolInObjectResponse> createCreatePoolInObjectResponse(CreatePoolInObjectResponse value) {
        return new JAXBElement<CreatePoolInObjectResponse>(_CreatePoolInObjectResponse_QNAME, CreatePoolInObjectResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetPossibleSpecialChildrenResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getPossibleSpecialChildrenResponse")
    public JAXBElement<GetPossibleSpecialChildrenResponse> createGetPossibleSpecialChildrenResponse(GetPossibleSpecialChildrenResponse value) {
        return new JAXBElement<GetPossibleSpecialChildrenResponse>(_GetPossibleSpecialChildrenResponse_QNAME, GetPossibleSpecialChildrenResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateTemplateElementResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "updateTemplateElementResponse")
    public JAXBElement<UpdateTemplateElementResponse> createUpdateTemplateElementResponse(UpdateTemplateElementResponse value) {
        return new JAXBElement<UpdateTemplateElementResponse>(_UpdateTemplateElementResponse_QNAME, UpdateTemplateElementResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSynchronizationGroup }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getSynchronizationGroup")
    public JAXBElement<GetSynchronizationGroup> createGetSynchronizationGroup(GetSynchronizationGroup value) {
        return new JAXBElement<GetSynchronizationGroup>(_GetSynchronizationGroup_QNAME, GetSynchronizationGroup.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateGroupResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "createGroupResponse")
    public JAXBElement<CreateGroupResponse> createCreateGroupResponse(CreateGroupResponse value) {
        return new JAXBElement<CreateGroupResponse>(_CreateGroupResponse_QNAME, CreateGroupResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BulkUploadResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "bulkUploadResponse")
    public JAXBElement<BulkUploadResponse> createBulkUploadResponse(BulkUploadResponse value) {
        return new JAXBElement<BulkUploadResponse>(_BulkUploadResponse_QNAME, BulkUploadResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateSynchronizationGroupResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "updateSynchronizationGroupResponse")
    public JAXBElement<UpdateSynchronizationGroupResponse> createUpdateSynchronizationGroupResponse(UpdateSynchronizationGroupResponse value) {
        return new JAXBElement<UpdateSynchronizationGroupResponse>(_UpdateSynchronizationGroupResponse_QNAME, UpdateSynchronizationGroupResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllServicesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getAllServicesResponse")
    public JAXBElement<GetAllServicesResponse> createGetAllServicesResponse(GetAllServicesResponse value) {
        return new JAXBElement<GetAllServicesResponse>(_GetAllServicesResponse_QNAME, GetAllServicesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetGeneralActivityAuditTrail }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getGeneralActivityAuditTrail")
    public JAXBElement<GetGeneralActivityAuditTrail> createGetGeneralActivityAuditTrail(GetGeneralActivityAuditTrail value) {
        return new JAXBElement<GetGeneralActivityAuditTrail>(_GetGeneralActivityAuditTrail_QNAME, GetGeneralActivityAuditTrail.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetServicesInPoolResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getServicesInPoolResponse")
    public JAXBElement<GetServicesInPoolResponse> createGetServicesInPoolResponse(GetServicesInPoolResponse value) {
        return new JAXBElement<GetServicesInPoolResponse>(_GetServicesInPoolResponse_QNAME, GetServicesInPoolResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateAttributeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "createAttributeResponse")
    public JAXBElement<CreateAttributeResponse> createCreateAttributeResponse(CreateAttributeResponse value) {
        return new JAXBElement<CreateAttributeResponse>(_CreateAttributeResponse_QNAME, CreateAttributeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteProjectPoolResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "deleteProjectPoolResponse")
    public JAXBElement<DeleteProjectPoolResponse> createDeleteProjectPoolResponse(DeleteProjectPoolResponse value) {
        return new JAXBElement<DeleteProjectPoolResponse>(_DeleteProjectPoolResponse_QNAME, DeleteProjectPoolResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetOSPViewResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getOSPViewResponse")
    public JAXBElement<GetOSPViewResponse> createGetOSPViewResponse(GetOSPViewResponse value) {
        return new JAXBElement<GetOSPViewResponse>(_GetOSPViewResponse_QNAME, GetOSPViewResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetObjectsWithFilter }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getObjectsWithFilter")
    public JAXBElement<GetObjectsWithFilter> createGetObjectsWithFilter(GetObjectsWithFilter value) {
        return new JAXBElement<GetObjectsWithFilter>(_GetObjectsWithFilter_QNAME, GetObjectsWithFilter.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDeviceLayoutStructure }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getDeviceLayoutStructure")
    public JAXBElement<GetDeviceLayoutStructure> createGetDeviceLayoutStructure(GetDeviceLayoutStructure value) {
        return new JAXBElement<GetDeviceLayoutStructure>(_GetDeviceLayoutStructure_QNAME, GetDeviceLayoutStructure.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetProjectsInPool }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getProjectsInPool")
    public JAXBElement<GetProjectsInPool> createGetProjectsInPool(GetProjectsInPool value) {
        return new JAXBElement<GetProjectsInPool>(_GetProjectsInPool_QNAME, GetProjectsInPool.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetOSPViews }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getOSPViews")
    public JAXBElement<GetOSPViews> createGetOSPViews(GetOSPViews value) {
        return new JAXBElement<GetOSPViews>(_GetOSPViews_QNAME, GetOSPViews.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMPLSLinkEndpoints }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getMPLSLinkEndpoints")
    public JAXBElement<GetMPLSLinkEndpoints> createGetMPLSLinkEndpoints(GetMPLSLinkEndpoints value) {
        return new JAXBElement<GetMPLSLinkEndpoints>(_GetMPLSLinkEndpoints_QNAME, GetMPLSLinkEndpoints.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddPossibleSpecialChildrenResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "addPossibleSpecialChildrenResponse")
    public JAXBElement<AddPossibleSpecialChildrenResponse> createAddPossibleSpecialChildrenResponse(AddPossibleSpecialChildrenResponse value) {
        return new JAXBElement<AddPossibleSpecialChildrenResponse>(_AddPossibleSpecialChildrenResponse_QNAME, AddPossibleSpecialChildrenResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateValidatorDefinition }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "createValidatorDefinition")
    public JAXBElement<CreateValidatorDefinition> createCreateValidatorDefinition(CreateValidatorDefinition value) {
        return new JAXBElement<CreateValidatorDefinition>(_CreateValidatorDefinition_QNAME, CreateValidatorDefinition.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreatePoolItem }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "createPoolItem")
    public JAXBElement<CreatePoolItem> createCreatePoolItem(CreatePoolItem value) {
        return new JAXBElement<CreatePoolItem>(_CreatePoolItem_QNAME, CreatePoolItem.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetPhysicalTree }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getPhysicalTree")
    public JAXBElement<GetPhysicalTree> createGetPhysicalTree(GetPhysicalTree value) {
        return new JAXBElement<GetPhysicalTree>(_GetPhysicalTree_QNAME, GetPhysicalTree.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ValidateSavedE2EViewResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "validateSavedE2EViewResponse")
    public JAXBElement<ValidateSavedE2EViewResponse> createValidateSavedE2EViewResponse(ValidateSavedE2EViewResponse value) {
        return new JAXBElement<ValidateSavedE2EViewResponse>(_ValidateSavedE2EViewResponse_QNAME, ValidateSavedE2EViewResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MoveSyncDataSourceConfigurationResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "moveSyncDataSourceConfigurationResponse")
    public JAXBElement<MoveSyncDataSourceConfigurationResponse> createMoveSyncDataSourceConfigurationResponse(MoveSyncDataSourceConfigurationResponse value) {
        return new JAXBElement<MoveSyncDataSourceConfigurationResponse>(_MoveSyncDataSourceConfigurationResponse_QNAME, MoveSyncDataSourceConfigurationResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetGroupsForUserResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getGroupsForUserResponse")
    public JAXBElement<GetGroupsForUserResponse> createGetGroupsForUserResponse(GetGroupsForUserResponse value) {
        return new JAXBElement<GetGroupsForUserResponse>(_GetGroupsForUserResponse_QNAME, GetGroupsForUserResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReleasePortFromIP }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "releasePortFromIP")
    public JAXBElement<ReleasePortFromIP> createReleasePortFromIP(ReleasePortFromIP value) {
        return new JAXBElement<ReleasePortFromIP>(_ReleasePortFromIP_QNAME, ReleasePortFromIP.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetFile }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getFile")
    public JAXBElement<GetFile> createGetFile(GetFile value) {
        return new JAXBElement<GetFile>(_GetFile_QNAME, GetFile.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetPossibleChildrenResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getPossibleChildrenResponse")
    public JAXBElement<GetPossibleChildrenResponse> createGetPossibleChildrenResponse(GetPossibleChildrenResponse value) {
        return new JAXBElement<GetPossibleChildrenResponse>(_GetPossibleChildrenResponse_QNAME, GetPossibleChildrenResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemoveObjectsFromFavoritesFolderResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "removeObjectsFromFavoritesFolderResponse")
    public JAXBElement<RemoveObjectsFromFavoritesFolderResponse> createRemoveObjectsFromFavoritesFolderResponse(RemoveObjectsFromFavoritesFolderResponse value) {
        return new JAXBElement<RemoveObjectsFromFavoritesFolderResponse>(_RemoveObjectsFromFavoritesFolderResponse_QNAME, RemoveObjectsFromFavoritesFolderResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemoveUserFromGroup }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "removeUserFromGroup")
    public JAXBElement<RemoveUserFromGroup> createRemoveUserFromGroup(RemoveUserFromGroup value) {
        return new JAXBElement<RemoveUserFromGroup>(_RemoveUserFromGroup_QNAME, RemoveUserFromGroup.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSpecialChildrenOfClassLightResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getSpecialChildrenOfClassLightResponse")
    public JAXBElement<GetSpecialChildrenOfClassLightResponse> createGetSpecialChildrenOfClassLightResponse(GetSpecialChildrenOfClassLightResponse value) {
        return new JAXBElement<GetSpecialChildrenOfClassLightResponse>(_GetSpecialChildrenOfClassLightResponse_QNAME, GetSpecialChildrenOfClassLightResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ConnectPhysicalContainers }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "connectPhysicalContainers")
    public JAXBElement<ConnectPhysicalContainers> createConnectPhysicalContainers(ConnectPhysicalContainers value) {
        return new JAXBElement<ConnectPhysicalContainers>(_ConnectPhysicalContainers_QNAME, ConnectPhysicalContainers.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetConfigurationVariable }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getConfigurationVariable")
    public JAXBElement<GetConfigurationVariable> createGetConfigurationVariable(GetConfigurationVariable value) {
        return new JAXBElement<GetConfigurationVariable>(_GetConfigurationVariable_QNAME, GetConfigurationVariable.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSDHTransportLinkStructureResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getSDHTransportLinkStructureResponse")
    public JAXBElement<GetSDHTransportLinkStructureResponse> createGetSDHTransportLinkStructureResponse(GetSDHTransportLinkStructureResponse value) {
        return new JAXBElement<GetSDHTransportLinkStructureResponse>(_GetSDHTransportLinkStructureResponse_QNAME, GetSDHTransportLinkStructureResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IsSubclassOfResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "isSubclassOfResponse")
    public JAXBElement<IsSubclassOfResponse> createIsSubclassOfResponse(IsSubclassOfResponse value) {
        return new JAXBElement<IsSubclassOfResponse>(_IsSubclassOfResponse_QNAME, IsSubclassOfResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetIPAddress }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getIPAddress")
    public JAXBElement<GetIPAddress> createGetIPAddress(GetIPAddress value) {
        return new JAXBElement<GetIPAddress>(_GetIPAddress_QNAME, GetIPAddress.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSDHContainerLinkStructure }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getSDHContainerLinkStructure")
    public JAXBElement<GetSDHContainerLinkStructure> createGetSDHContainerLinkStructure(GetSDHContainerLinkStructure value) {
        return new JAXBElement<GetSDHContainerLinkStructure>(_GetSDHContainerLinkStructure_QNAME, GetSDHContainerLinkStructure.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AssociatesPhysicalNodeToWarehouseResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "associatesPhysicalNodeToWarehouseResponse")
    public JAXBElement<AssociatesPhysicalNodeToWarehouseResponse> createAssociatesPhysicalNodeToWarehouseResponse(AssociatesPhysicalNodeToWarehouseResponse value) {
        return new JAXBElement<AssociatesPhysicalNodeToWarehouseResponse>(_AssociatesPhysicalNodeToWarehouseResponse_QNAME, AssociatesPhysicalNodeToWarehouseResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTasksForUserResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getTasksForUserResponse")
    public JAXBElement<GetTasksForUserResponse> createGetTasksForUserResponse(GetTasksForUserResponse value) {
        return new JAXBElement<GetTasksForUserResponse>(_GetTasksForUserResponse_QNAME, GetTasksForUserResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetLinkConnectedToPort }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getLinkConnectedToPort")
    public JAXBElement<GetLinkConnectedToPort> createGetLinkConnectedToPort(GetLinkConnectedToPort value) {
        return new JAXBElement<GetLinkConnectedToPort>(_GetLinkConnectedToPort_QNAME, GetLinkConnectedToPort.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetObjectsRelatedToService }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getObjectsRelatedToService")
    public JAXBElement<GetObjectsRelatedToService> createGetObjectsRelatedToService(GetObjectsRelatedToService value) {
        return new JAXBElement<GetObjectsRelatedToService>(_GetObjectsRelatedToService_QNAME, GetObjectsRelatedToService.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ConnectMplsLinkResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "connectMplsLinkResponse")
    public JAXBElement<ConnectMplsLinkResponse> createConnectMplsLinkResponse(ConnectMplsLinkResponse value) {
        return new JAXBElement<ConnectMplsLinkResponse>(_ConnectMplsLinkResponse_QNAME, ConnectMplsLinkResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetPossibleChildren }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getPossibleChildren")
    public JAXBElement<GetPossibleChildren> createGetPossibleChildren(GetPossibleChildren value) {
        return new JAXBElement<GetPossibleChildren>(_GetPossibleChildren_QNAME, GetPossibleChildren.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateProcessInstance }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "createProcessInstance")
    public JAXBElement<CreateProcessInstance> createCreateProcessInstance(CreateProcessInstance value) {
        return new JAXBElement<CreateProcessInstance>(_CreateProcessInstance_QNAME, CreateProcessInstance.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateSessionResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "createSessionResponse")
    public JAXBElement<CreateSessionResponse> createCreateSessionResponse(CreateSessionResponse value) {
        return new JAXBElement<CreateSessionResponse>(_CreateSessionResponse_QNAME, CreateSessionResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SaveQuery }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "saveQuery")
    public JAXBElement<SaveQuery> createSaveQuery(SaveQuery value) {
        return new JAXBElement<SaveQuery>(_SaveQuery_QNAME, SaveQuery.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RelateSubnetToVlanResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "relateSubnetToVlanResponse")
    public JAXBElement<RelateSubnetToVlanResponse> createRelateSubnetToVlanResponse(RelateSubnetToVlanResponse value) {
        return new JAXBElement<RelateSubnetToVlanResponse>(_RelateSubnetToVlanResponse_QNAME, RelateSubnetToVlanResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteAttributeForClassWithIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "deleteAttributeForClassWithIdResponse")
    public JAXBElement<DeleteAttributeForClassWithIdResponse> createDeleteAttributeForClassWithIdResponse(DeleteAttributeForClassWithIdResponse value) {
        return new JAXBElement<DeleteAttributeForClassWithIdResponse>(_DeleteAttributeForClassWithIdResponse_QNAME, DeleteAttributeForClassWithIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMandatoryAttributesInClass }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getMandatoryAttributesInClass")
    public JAXBElement<GetMandatoryAttributesInClass> createGetMandatoryAttributesInClass(GetMandatoryAttributesInClass value) {
        return new JAXBElement<GetMandatoryAttributesInClass>(_GetMandatoryAttributesInClass_QNAME, GetMandatoryAttributesInClass.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CopySyncDataSourceConfiguration }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "copySyncDataSourceConfiguration")
    public JAXBElement<CopySyncDataSourceConfiguration> createCopySyncDataSourceConfiguration(CopySyncDataSourceConfiguration value) {
        return new JAXBElement<CopySyncDataSourceConfiguration>(_CopySyncDataSourceConfiguration_QNAME, CopySyncDataSourceConfiguration.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindSDHRoutesUsingContainerLinks }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "findSDHRoutesUsingContainerLinks")
    public JAXBElement<FindSDHRoutesUsingContainerLinks> createFindSDHRoutesUsingContainerLinks(FindSDHRoutesUsingContainerLinks value) {
        return new JAXBElement<FindSDHRoutesUsingContainerLinks>(_FindSDHRoutesUsingContainerLinks_QNAME, FindSDHRoutesUsingContainerLinks.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetWarehousesInPoolResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getWarehousesInPoolResponse")
    public JAXBElement<GetWarehousesInPoolResponse> createGetWarehousesInPoolResponse(GetWarehousesInPoolResponse value) {
        return new JAXBElement<GetWarehousesInPoolResponse>(_GetWarehousesInPoolResponse_QNAME, GetWarehousesInPoolResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CopySpecialObjects }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "copySpecialObjects")
    public JAXBElement<CopySpecialObjects> createCopySpecialObjects(CopySpecialObjects value) {
        return new JAXBElement<CopySpecialObjects>(_CopySpecialObjects_QNAME, CopySpecialObjects.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateCustomerPool }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "updateCustomerPool")
    public JAXBElement<UpdateCustomerPool> createUpdateCustomerPool(UpdateCustomerPool value) {
        return new JAXBElement<UpdateCustomerPool>(_UpdateCustomerPool_QNAME, UpdateCustomerPool.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DownloadBulkLoadLogResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "downloadBulkLoadLogResponse")
    public JAXBElement<DownloadBulkLoadLogResponse> createDownloadBulkLoadLogResponse(DownloadBulkLoadLogResponse value) {
        return new JAXBElement<DownloadBulkLoadLogResponse>(_DownloadBulkLoadLogResponse_QNAME, DownloadBulkLoadLogResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetObjectsInSparePool }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getObjectsInSparePool")
    public JAXBElement<GetObjectsInSparePool> createGetObjectsInSparePool(GetObjectsInSparePool value) {
        return new JAXBElement<GetObjectsInSparePool>(_GetObjectsInSparePool_QNAME, GetObjectsInSparePool.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RelateObjectToContract }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "relateObjectToContract")
    public JAXBElement<RelateObjectToContract> createRelateObjectToContract(RelateObjectToContract value) {
        return new JAXBElement<RelateObjectToContract>(_RelateObjectToContract_QNAME, RelateObjectToContract.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetBusinessRules }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getBusinessRules")
    public JAXBElement<GetBusinessRules> createGetBusinessRules(GetBusinessRules value) {
        return new JAXBElement<GetBusinessRules>(_GetBusinessRules_QNAME, GetBusinessRules.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetProjectActivities }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getProjectActivities")
    public JAXBElement<GetProjectActivities> createGetProjectActivities(GetProjectActivities value) {
        return new JAXBElement<GetProjectActivities>(_GetProjectActivities_QNAME, GetProjectActivities.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetPhysicalTreeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getPhysicalTreeResponse")
    public JAXBElement<GetPhysicalTreeResponse> createGetPhysicalTreeResponse(GetPhysicalTreeResponse value) {
        return new JAXBElement<GetPhysicalTreeResponse>(_GetPhysicalTreeResponse_QNAME, GetPhysicalTreeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetFavoritesFoldersForObject }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getFavoritesFoldersForObject")
    public JAXBElement<GetFavoritesFoldersForObject> createGetFavoritesFoldersForObject(GetFavoritesFoldersForObject value) {
        return new JAXBElement<GetFavoritesFoldersForObject>(_GetFavoritesFoldersForObject_QNAME, GetFavoritesFoldersForObject.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateOSPViewResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "updateOSPViewResponse")
    public JAXBElement<UpdateOSPViewResponse> createUpdateOSPViewResponse(UpdateOSPViewResponse value) {
        return new JAXBElement<UpdateOSPViewResponse>(_UpdateOSPViewResponse_QNAME, UpdateOSPViewResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteObjectResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "deleteObjectResponse")
    public JAXBElement<DeleteObjectResponse> createDeleteObjectResponse(DeleteObjectResponse value) {
        return new JAXBElement<DeleteObjectResponse>(_DeleteObjectResponse_QNAME, DeleteObjectResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetProjectActivitiesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getProjectActivitiesResponse")
    public JAXBElement<GetProjectActivitiesResponse> createGetProjectActivitiesResponse(GetProjectActivitiesResponse value) {
        return new JAXBElement<GetProjectActivitiesResponse>(_GetProjectActivitiesResponse_QNAME, GetProjectActivitiesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ConnectMirrorMultiplePortResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "connectMirrorMultiplePortResponse")
    public JAXBElement<ConnectMirrorMultiplePortResponse> createConnectMirrorMultiplePortResponse(ConnectMirrorMultiplePortResponse value) {
        return new JAXBElement<ConnectMirrorMultiplePortResponse>(_ConnectMirrorMultiplePortResponse_QNAME, ConnectMirrorMultiplePortResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetClassLevelReports }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getClassLevelReports")
    public JAXBElement<GetClassLevelReports> createGetClassLevelReports(GetClassLevelReports value) {
        return new JAXBElement<GetClassLevelReports>(_GetClassLevelReports_QNAME, GetClassLevelReports.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RelateObjectsToService }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "relateObjectsToService")
    public JAXBElement<RelateObjectsToService> createRelateObjectsToService(RelateObjectsToService value) {
        return new JAXBElement<RelateObjectsToService>(_RelateObjectsToService_QNAME, RelateObjectsToService.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateBusinessRuleResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "createBusinessRuleResponse")
    public JAXBElement<CreateBusinessRuleResponse> createCreateBusinessRuleResponse(CreateBusinessRuleResponse value) {
        return new JAXBElement<CreateBusinessRuleResponse>(_CreateBusinessRuleResponse_QNAME, CreateBusinessRuleResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateTemplateElementResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "createTemplateElementResponse")
    public JAXBElement<CreateTemplateElementResponse> createCreateTemplateElementResponse(CreateTemplateElementResponse value) {
        return new JAXBElement<CreateTemplateElementResponse>(_CreateTemplateElementResponse_QNAME, CreateTemplateElementResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetContractPoolResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getContractPoolResponse")
    public JAXBElement<GetContractPoolResponse> createGetContractPoolResponse(GetContractPoolResponse value) {
        return new JAXBElement<GetContractPoolResponse>(_GetContractPoolResponse_QNAME, GetContractPoolResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateTaskPropertiesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "updateTaskPropertiesResponse")
    public JAXBElement<UpdateTaskPropertiesResponse> createUpdateTaskPropertiesResponse(UpdateTaskPropertiesResponse value) {
        return new JAXBElement<UpdateTaskPropertiesResponse>(_UpdateTaskPropertiesResponse_QNAME, UpdateTaskPropertiesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateWarehouseResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "createWarehouseResponse")
    public JAXBElement<CreateWarehouseResponse> createCreateWarehouseResponse(CreateWarehouseResponse value) {
        return new JAXBElement<CreateWarehouseResponse>(_CreateWarehouseResponse_QNAME, CreateWarehouseResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddPossibleSpecialChildren }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "addPossibleSpecialChildren")
    public JAXBElement<AddPossibleSpecialChildren> createAddPossibleSpecialChildren(AddPossibleSpecialChildren value) {
        return new JAXBElement<AddPossibleSpecialChildren>(_AddPossibleSpecialChildren_QNAME, AddPossibleSpecialChildren.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteGeneralView }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "deleteGeneralView")
    public JAXBElement<DeleteGeneralView> createDeleteGeneralView(DeleteGeneralView value) {
        return new JAXBElement<DeleteGeneralView>(_DeleteGeneralView_QNAME, DeleteGeneralView.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTemplateElementChildrenResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getTemplateElementChildrenResponse")
    public JAXBElement<GetTemplateElementChildrenResponse> createGetTemplateElementChildrenResponse(GetTemplateElementChildrenResponse value) {
        return new JAXBElement<GetTemplateElementChildrenResponse>(_GetTemplateElementChildrenResponse_QNAME, GetTemplateElementChildrenResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemoteProcessDefinition }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "remoteProcessDefinition")
    public JAXBElement<RemoteProcessDefinition> createRemoteProcessDefinition(RemoteProcessDefinition value) {
        return new JAXBElement<RemoteProcessDefinition>(_RemoteProcessDefinition_QNAME, RemoteProcessDefinition.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MovePoolItemToPool }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "movePoolItemToPool")
    public JAXBElement<MovePoolItemToPool> createMovePoolItemToPool(MovePoolItemToPool value) {
        return new JAXBElement<MovePoolItemToPool>(_MovePoolItemToPool_QNAME, MovePoolItemToPool.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateTaskResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "createTaskResponse")
    public JAXBElement<CreateTaskResponse> createCreateTaskResponse(CreateTaskResponse value) {
        return new JAXBElement<CreateTaskResponse>(_CreateTaskResponse_QNAME, CreateTaskResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateProxy }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "createProxy")
    public JAXBElement<CreateProxy> createCreateProxy(CreateProxy value) {
        return new JAXBElement<CreateProxy>(_CreateProxy_QNAME, CreateProxy.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSynchronizationGroupsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getSynchronizationGroupsResponse")
    public JAXBElement<GetSynchronizationGroupsResponse> createGetSynchronizationGroupsResponse(GetSynchronizationGroupsResponse value) {
        return new JAXBElement<GetSynchronizationGroupsResponse>(_GetSynchronizationGroupsResponse_QNAME, GetSynchronizationGroupsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteFavoritesFoldersResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "deleteFavoritesFoldersResponse")
    public JAXBElement<DeleteFavoritesFoldersResponse> createDeleteFavoritesFoldersResponse(DeleteFavoritesFoldersResponse value) {
        return new JAXBElement<DeleteFavoritesFoldersResponse>(_DeleteFavoritesFoldersResponse_QNAME, DeleteFavoritesFoldersResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateContract }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "createContract")
    public JAXBElement<CreateContract> createCreateContract(CreateContract value) {
        return new JAXBElement<CreateContract>(_CreateContract_QNAME, CreateContract.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetProjectsRelatedToObjectResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getProjectsRelatedToObjectResponse")
    public JAXBElement<GetProjectsRelatedToObjectResponse> createGetProjectsRelatedToObjectResponse(GetProjectsRelatedToObjectResponse value) {
        return new JAXBElement<GetProjectsRelatedToObjectResponse>(_GetProjectsRelatedToObjectResponse_QNAME, GetProjectsRelatedToObjectResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetBGPMap }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getBGPMap")
    public JAXBElement<GetBGPMap> createGetBGPMap(GetBGPMap value) {
        return new JAXBElement<GetBGPMap>(_GetBGPMap_QNAME, GetBGPMap.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReleaseObjectFromProjectResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "releaseObjectFromProjectResponse")
    public JAXBElement<ReleaseObjectFromProjectResponse> createReleaseObjectFromProjectResponse(ReleaseObjectFromProjectResponse value) {
        return new JAXBElement<ReleaseObjectFromProjectResponse>(_ReleaseObjectFromProjectResponse_QNAME, ReleaseObjectFromProjectResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetContactsForCustomerResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getContactsForCustomerResponse")
    public JAXBElement<GetContactsForCustomerResponse> createGetContactsForCustomerResponse(GetContactsForCustomerResponse value) {
        return new JAXBElement<GetContactsForCustomerResponse>(_GetContactsForCustomerResponse_QNAME, GetContactsForCustomerResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetInstanceableListTypes }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getInstanceableListTypes")
    public JAXBElement<GetInstanceableListTypes> createGetInstanceableListTypes(GetInstanceableListTypes value) {
        return new JAXBElement<GetInstanceableListTypes>(_GetInstanceableListTypes_QNAME, GetInstanceableListTypes.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetQueriesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getQueriesResponse")
    public JAXBElement<GetQueriesResponse> createGetQueriesResponse(GetQueriesResponse value) {
        return new JAXBElement<GetQueriesResponse>(_GetQueriesResponse_QNAME, GetQueriesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateServicePoolResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "createServicePoolResponse")
    public JAXBElement<CreateServicePoolResponse> createCreateServicePoolResponse(CreateServicePoolResponse value) {
        return new JAXBElement<CreateServicePoolResponse>(_CreateServicePoolResponse_QNAME, CreateServicePoolResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateValidatorDefinitionResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "createValidatorDefinitionResponse")
    public JAXBElement<CreateValidatorDefinitionResponse> createCreateValidatorDefinitionResponse(CreateValidatorDefinitionResponse value) {
        return new JAXBElement<CreateValidatorDefinitionResponse>(_CreateValidatorDefinitionResponse_QNAME, CreateValidatorDefinitionResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetQueries }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getQueries")
    public JAXBElement<GetQueries> createGetQueries(GetQueries value) {
        return new JAXBElement<GetQueries>(_GetQueries_QNAME, GetQueries.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetContractResources }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getContractResources")
    public JAXBElement<GetContractResources> createGetContractResources(GetContractResources value) {
        return new JAXBElement<GetContractResources>(_GetContractResources_QNAME, GetContractResources.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReleaseSubnetFromVRF }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "releaseSubnetFromVRF")
    public JAXBElement<ReleaseSubnetFromVRF> createReleaseSubnetFromVRF(ReleaseSubnetFromVRF value) {
        return new JAXBElement<ReleaseSubnetFromVRF>(_ReleaseSubnetFromVRF_QNAME, ReleaseSubnetFromVRF.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteOSPViewResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "deleteOSPViewResponse")
    public JAXBElement<DeleteOSPViewResponse> createDeleteOSPViewResponse(DeleteOSPViewResponse value) {
        return new JAXBElement<DeleteOSPViewResponse>(_DeleteOSPViewResponse_QNAME, DeleteOSPViewResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetUsersResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getUsersResponse")
    public JAXBElement<GetUsersResponse> createGetUsersResponse(GetUsersResponse value) {
        return new JAXBElement<GetUsersResponse>(_GetUsersResponse_QNAME, GetUsersResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateCustomerResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "updateCustomerResponse")
    public JAXBElement<UpdateCustomerResponse> createUpdateCustomerResponse(UpdateCustomerResponse value) {
        return new JAXBElement<UpdateCustomerResponse>(_UpdateCustomerResponse_QNAME, UpdateCustomerResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ItOverlaps }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "itOverlaps")
    public JAXBElement<ItOverlaps> createItOverlaps(ItOverlaps value) {
        return new JAXBElement<ItOverlaps>(_ItOverlaps_QNAME, ItOverlaps.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LaunchAutomatedSynchronizationTaskResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "launchAutomatedSynchronizationTaskResponse")
    public JAXBElement<LaunchAutomatedSynchronizationTaskResponse> createLaunchAutomatedSynchronizationTaskResponse(LaunchAutomatedSynchronizationTaskResponse value) {
        return new JAXBElement<LaunchAutomatedSynchronizationTaskResponse>(_LaunchAutomatedSynchronizationTaskResponse_QNAME, LaunchAutomatedSynchronizationTaskResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MoveObjectsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "moveObjectsResponse")
    public JAXBElement<MoveObjectsResponse> createMoveObjectsResponse(MoveObjectsResponse value) {
        return new JAXBElement<MoveObjectsResponse>(_MoveObjectsResponse_QNAME, MoveObjectsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReleaseObjectFromProject }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "releaseObjectFromProject")
    public JAXBElement<ReleaseObjectFromProject> createReleaseObjectFromProject(ReleaseObjectFromProject value) {
        return new JAXBElement<ReleaseObjectFromProject>(_ReleaseObjectFromProject_QNAME, ReleaseObjectFromProject.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetClassLevelReportsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getClassLevelReportsResponse")
    public JAXBElement<GetClassLevelReportsResponse> createGetClassLevelReportsResponse(GetClassLevelReportsResponse value) {
        return new JAXBElement<GetClassLevelReportsResponse>(_GetClassLevelReportsResponse_QNAME, GetClassLevelReportsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetContacts }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getContacts")
    public JAXBElement<GetContacts> createGetContacts(GetContacts value) {
        return new JAXBElement<GetContacts>(_GetContacts_QNAME, GetContacts.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteContractResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "deleteContractResponse")
    public JAXBElement<DeleteContractResponse> createDeleteContractResponse(DeleteContractResponse value) {
        return new JAXBElement<DeleteContractResponse>(_DeleteContractResponse_QNAME, DeleteContractResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MoveSpecialObjectsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "moveSpecialObjectsResponse")
    public JAXBElement<MoveSpecialObjectsResponse> createMoveSpecialObjectsResponse(MoveSpecialObjectsResponse value) {
        return new JAXBElement<MoveSpecialObjectsResponse>(_MoveSpecialObjectsResponse_QNAME, MoveSpecialObjectsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateActivity }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "updateActivity")
    public JAXBElement<UpdateActivity> createUpdateActivity(UpdateActivity value) {
        return new JAXBElement<UpdateActivity>(_UpdateActivity_QNAME, UpdateActivity.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateProcessDefinition }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "updateProcessDefinition")
    public JAXBElement<UpdateProcessDefinition> createUpdateProcessDefinition(UpdateProcessDefinition value) {
        return new JAXBElement<UpdateProcessDefinition>(_UpdateProcessDefinition_QNAME, UpdateProcessDefinition.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateReportParameters }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "updateReportParameters")
    public JAXBElement<UpdateReportParameters> createUpdateReportParameters(UpdateReportParameters value) {
        return new JAXBElement<UpdateReportParameters>(_UpdateReportParameters_QNAME, UpdateReportParameters.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetLogicalLinkDetailsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getLogicalLinkDetailsResponse")
    public JAXBElement<GetLogicalLinkDetailsResponse> createGetLogicalLinkDetailsResponse(GetLogicalLinkDetailsResponse value) {
        return new JAXBElement<GetLogicalLinkDetailsResponse>(_GetLogicalLinkDetailsResponse_QNAME, GetLogicalLinkDetailsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link HasAttributeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "hasAttributeResponse")
    public JAXBElement<HasAttributeResponse> createHasAttributeResponse(HasAttributeResponse value) {
        return new JAXBElement<HasAttributeResponse>(_HasAttributeResponse_QNAME, HasAttributeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateObjectResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "createObjectResponse")
    public JAXBElement<CreateObjectResponse> createCreateObjectResponse(CreateObjectResponse value) {
        return new JAXBElement<CreateObjectResponse>(_CreateObjectResponse_QNAME, CreateObjectResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AttachFileToObjectResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "attachFileToObjectResponse")
    public JAXBElement<AttachFileToObjectResponse> createAttachFileToObjectResponse(AttachFileToObjectResponse value) {
        return new JAXBElement<AttachFileToObjectResponse>(_AttachFileToObjectResponse_QNAME, AttachFileToObjectResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteSDHTransportLinkResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "deleteSDHTransportLinkResponse")
    public JAXBElement<DeleteSDHTransportLinkResponse> createDeleteSDHTransportLinkResponse(DeleteSDHTransportLinkResponse value) {
        return new JAXBElement<DeleteSDHTransportLinkResponse>(_DeleteSDHTransportLinkResponse_QNAME, DeleteSDHTransportLinkResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetWarehouseRootPoolsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getWarehouseRootPoolsResponse")
    public JAXBElement<GetWarehouseRootPoolsResponse> createGetWarehouseRootPoolsResponse(GetWarehouseRootPoolsResponse value) {
        return new JAXBElement<GetWarehouseRootPoolsResponse>(_GetWarehouseRootPoolsResponse_QNAME, GetWarehouseRootPoolsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteListTypeItemRelatedViewResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "deleteListTypeItemRelatedViewResponse")
    public JAXBElement<DeleteListTypeItemRelatedViewResponse> createDeleteListTypeItemRelatedViewResponse(DeleteListTypeItemRelatedViewResponse value) {
        return new JAXBElement<DeleteListTypeItemRelatedViewResponse>(_DeleteListTypeItemRelatedViewResponse_QNAME, DeleteListTypeItemRelatedViewResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreatePhysicalConnectionsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "createPhysicalConnectionsResponse")
    public JAXBElement<CreatePhysicalConnectionsResponse> createCreatePhysicalConnectionsResponse(CreatePhysicalConnectionsResponse value) {
        return new JAXBElement<CreatePhysicalConnectionsResponse>(_CreatePhysicalConnectionsResponse_QNAME, CreatePhysicalConnectionsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CloseSessionResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "closeSessionResponse")
    public JAXBElement<CloseSessionResponse> createCloseSessionResponse(CloseSessionResponse value) {
        return new JAXBElement<CloseSessionResponse>(_CloseSessionResponse_QNAME, CloseSessionResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemoveUserFromGroupResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "removeUserFromGroupResponse")
    public JAXBElement<RemoveUserFromGroupResponse> createRemoveUserFromGroupResponse(RemoveUserFromGroupResponse value) {
        return new JAXBElement<RemoveUserFromGroupResponse>(_RemoveUserFromGroupResponse_QNAME, RemoveUserFromGroupResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetConfigurationVariableResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getConfigurationVariableResponse")
    public JAXBElement<GetConfigurationVariableResponse> createGetConfigurationVariableResponse(GetConfigurationVariableResponse value) {
        return new JAXBElement<GetConfigurationVariableResponse>(_GetConfigurationVariableResponse_QNAME, GetConfigurationVariableResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateActivityResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "updateActivityResponse")
    public JAXBElement<UpdateActivityResponse> createUpdateActivityResponse(UpdateActivityResponse value) {
        return new JAXBElement<UpdateActivityResponse>(_UpdateActivityResponse_QNAME, UpdateActivityResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteProject }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "deleteProject")
    public JAXBElement<DeleteProject> createDeleteProject(DeleteProject value) {
        return new JAXBElement<DeleteProject>(_DeleteProject_QNAME, DeleteProject.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteSubnetPools }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "deleteSubnetPools")
    public JAXBElement<DeleteSubnetPools> createDeleteSubnetPools(DeleteSubnetPools value) {
        return new JAXBElement<DeleteSubnetPools>(_DeleteSubnetPools_QNAME, DeleteSubnetPools.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteSubnetsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "deleteSubnetsResponse")
    public JAXBElement<DeleteSubnetsResponse> createDeleteSubnetsResponse(DeleteSubnetsResponse value) {
        return new JAXBElement<DeleteSubnetsResponse>(_DeleteSubnetsResponse_QNAME, DeleteSubnetsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetListTypeItemRelatedView }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getListTypeItemRelatedView")
    public JAXBElement<GetListTypeItemRelatedView> createGetListTypeItemRelatedView(GetListTypeItemRelatedView value) {
        return new JAXBElement<GetListTypeItemRelatedView>(_GetListTypeItemRelatedView_QNAME, GetListTypeItemRelatedView.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetReportResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getReportResponse")
    public JAXBElement<GetReportResponse> createGetReportResponse(GetReportResponse value) {
        return new JAXBElement<GetReportResponse>(_GetReportResponse_QNAME, GetReportResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetFavoritesFoldersForUser }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getFavoritesFoldersForUser")
    public JAXBElement<GetFavoritesFoldersForUser> createGetFavoritesFoldersForUser(GetFavoritesFoldersForUser value) {
        return new JAXBElement<GetFavoritesFoldersForUser>(_GetFavoritesFoldersForUser_QNAME, GetFavoritesFoldersForUser.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetListTypeItemResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getListTypeItemResponse")
    public JAXBElement<GetListTypeItemResponse> createGetListTypeItemResponse(GetListTypeItemResponse value) {
        return new JAXBElement<GetListTypeItemResponse>(_GetListTypeItemResponse_QNAME, GetListTypeItemResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateBulkObjects }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "createBulkObjects")
    public JAXBElement<CreateBulkObjects> createCreateBulkObjects(CreateBulkObjects value) {
        return new JAXBElement<CreateBulkObjects>(_CreateBulkObjects_QNAME, CreateBulkObjects.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetChildrenOfClassLightResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getChildrenOfClassLightResponse")
    public JAXBElement<GetChildrenOfClassLightResponse> createGetChildrenOfClassLightResponse(GetChildrenOfClassLightResponse value) {
        return new JAXBElement<GetChildrenOfClassLightResponse>(_GetChildrenOfClassLightResponse_QNAME, GetChildrenOfClassLightResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteUsers }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "deleteUsers")
    public JAXBElement<DeleteUsers> createDeleteUsers(DeleteUsers value) {
        return new JAXBElement<DeleteUsers>(_DeleteUsers_QNAME, DeleteUsers.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteCustomerPoolResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "deleteCustomerPoolResponse")
    public JAXBElement<DeleteCustomerPoolResponse> createDeleteCustomerPoolResponse(DeleteCustomerPoolResponse value) {
        return new JAXBElement<DeleteCustomerPoolResponse>(_DeleteCustomerPoolResponse_QNAME, DeleteCustomerPoolResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAffectedServicesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getAffectedServicesResponse")
    public JAXBElement<GetAffectedServicesResponse> createGetAffectedServicesResponse(GetAffectedServicesResponse value) {
        return new JAXBElement<GetAffectedServicesResponse>(_GetAffectedServicesResponse_QNAME, GetAffectedServicesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetGroups }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.northbound.kuwaiba.neotropic.org/", name = "getGroups")
    public JAXBElement<GetGroups> createGetGroups(GetGroups value) {
        return new JAXBElement<GetGroups>(_GetGroups_QNAME, GetGroups.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "background", scope = UpdateObjectRelatedView.class)
    public JAXBElement<byte[]> createUpdateObjectRelatedViewBackground(byte[] value) {
        return new JAXBElement<byte[]>(_UpdateObjectRelatedViewBackground_QNAME, byte[].class, UpdateObjectRelatedView.class, ((byte[]) value));
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "structure", scope = UpdateObjectRelatedView.class)
    public JAXBElement<byte[]> createUpdateObjectRelatedViewStructure(byte[] value) {
        return new JAXBElement<byte[]>(_UpdateObjectRelatedViewStructure_QNAME, byte[].class, UpdateObjectRelatedView.class, ((byte[]) value));
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "return", scope = GetDeviceLayoutStructureResponse.class)
    public JAXBElement<byte[]> createGetDeviceLayoutStructureResponseReturn(byte[] value) {
        return new JAXBElement<byte[]>(_GetDeviceLayoutStructureResponseReturn_QNAME, byte[].class, GetDeviceLayoutStructureResponse.class, ((byte[]) value));
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "file", scope = AttachFileToObject.class)
    public JAXBElement<byte[]> createAttachFileToObjectFile(byte[] value) {
        return new JAXBElement<byte[]>(_AttachFileToObjectFile_QNAME, byte[].class, AttachFileToObject.class, ((byte[]) value));
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "return", scope = ExecuteInventoryLevelReportResponse.class)
    public JAXBElement<byte[]> createExecuteInventoryLevelReportResponseReturn(byte[] value) {
        return new JAXBElement<byte[]>(_GetDeviceLayoutStructureResponseReturn_QNAME, byte[].class, ExecuteInventoryLevelReportResponse.class, ((byte[]) value));
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "icon", scope = CreateClass.class)
    public JAXBElement<byte[]> createCreateClassIcon(byte[] value) {
        return new JAXBElement<byte[]>(_CreateClassIcon_QNAME, byte[].class, CreateClass.class, ((byte[]) value));
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "smallIcon", scope = CreateClass.class)
    public JAXBElement<byte[]> createCreateClassSmallIcon(byte[] value) {
        return new JAXBElement<byte[]>(_CreateClassSmallIcon_QNAME, byte[].class, CreateClass.class, ((byte[]) value));
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "file", scope = BulkUpload.class)
    public JAXBElement<byte[]> createBulkUploadFile(byte[] value) {
        return new JAXBElement<byte[]>(_AttachFileToObjectFile_QNAME, byte[].class, BulkUpload.class, ((byte[]) value));
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "content", scope = UpdateOSPView.class)
    public JAXBElement<byte[]> createUpdateOSPViewContent(byte[] value) {
        return new JAXBElement<byte[]>(_UpdateOSPViewContent_QNAME, byte[].class, UpdateOSPView.class, ((byte[]) value));
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "structure", scope = CreateProcessDefinition.class)
    public JAXBElement<byte[]> createCreateProcessDefinitionStructure(byte[] value) {
        return new JAXBElement<byte[]>(_UpdateObjectRelatedViewStructure_QNAME, byte[].class, CreateProcessDefinition.class, ((byte[]) value));
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "background", scope = CreateListTypeItemRelatedView.class)
    public JAXBElement<byte[]> createCreateListTypeItemRelatedViewBackground(byte[] value) {
        return new JAXBElement<byte[]>(_UpdateObjectRelatedViewBackground_QNAME, byte[].class, CreateListTypeItemRelatedView.class, ((byte[]) value));
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "structure", scope = CreateListTypeItemRelatedView.class)
    public JAXBElement<byte[]> createCreateListTypeItemRelatedViewStructure(byte[] value) {
        return new JAXBElement<byte[]>(_UpdateObjectRelatedViewStructure_QNAME, byte[].class, CreateListTypeItemRelatedView.class, ((byte[]) value));
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "queryStructure", scope = CreateQuery.class)
    public JAXBElement<byte[]> createCreateQueryQueryStructure(byte[] value) {
        return new JAXBElement<byte[]>(_CreateQueryQueryStructure_QNAME, byte[].class, CreateQuery.class, ((byte[]) value));
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "return", scope = ExecuteClassLevelReportResponse.class)
    public JAXBElement<byte[]> createExecuteClassLevelReportResponseReturn(byte[] value) {
        return new JAXBElement<byte[]>(_GetDeviceLayoutStructureResponseReturn_QNAME, byte[].class, ExecuteClassLevelReportResponse.class, ((byte[]) value));
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "return", scope = DownloadBulkLoadLogResponse.class)
    public JAXBElement<byte[]> createDownloadBulkLoadLogResponseReturn(byte[] value) {
        return new JAXBElement<byte[]>(_GetDeviceLayoutStructureResponseReturn_QNAME, byte[].class, DownloadBulkLoadLogResponse.class, ((byte[]) value));
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "queryStructure", scope = SaveQuery.class)
    public JAXBElement<byte[]> createSaveQueryQueryStructure(byte[] value) {
        return new JAXBElement<byte[]>(_CreateQueryQueryStructure_QNAME, byte[].class, SaveQuery.class, ((byte[]) value));
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "structure", scope = UpdateProcessDefinition.class)
    public JAXBElement<byte[]> createUpdateProcessDefinitionStructure(byte[] value) {
        return new JAXBElement<byte[]>(_UpdateObjectRelatedViewStructure_QNAME, byte[].class, UpdateProcessDefinition.class, ((byte[]) value));
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "icon", scope = SetClassProperties.class)
    public JAXBElement<byte[]> createSetClassPropertiesIcon(byte[] value) {
        return new JAXBElement<byte[]>(_CreateClassIcon_QNAME, byte[].class, SetClassProperties.class, ((byte[]) value));
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "smallIcon", scope = SetClassProperties.class)
    public JAXBElement<byte[]> createSetClassPropertiesSmallIcon(byte[] value) {
        return new JAXBElement<byte[]>(_CreateClassSmallIcon_QNAME, byte[].class, SetClassProperties.class, ((byte[]) value));
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "background", scope = UpdateGeneralView.class)
    public JAXBElement<byte[]> createUpdateGeneralViewBackground(byte[] value) {
        return new JAXBElement<byte[]>(_UpdateObjectRelatedViewBackground_QNAME, byte[].class, UpdateGeneralView.class, ((byte[]) value));
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "structure", scope = UpdateGeneralView.class)
    public JAXBElement<byte[]> createUpdateGeneralViewStructure(byte[] value) {
        return new JAXBElement<byte[]>(_UpdateObjectRelatedViewStructure_QNAME, byte[].class, UpdateGeneralView.class, ((byte[]) value));
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "background", scope = CreateObjectRelatedView.class)
    public JAXBElement<byte[]> createCreateObjectRelatedViewBackground(byte[] value) {
        return new JAXBElement<byte[]>(_UpdateObjectRelatedViewBackground_QNAME, byte[].class, CreateObjectRelatedView.class, ((byte[]) value));
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "structure", scope = CreateObjectRelatedView.class)
    public JAXBElement<byte[]> createCreateObjectRelatedViewStructure(byte[] value) {
        return new JAXBElement<byte[]>(_UpdateObjectRelatedViewStructure_QNAME, byte[].class, CreateObjectRelatedView.class, ((byte[]) value));
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "content", scope = CreateOSPView.class)
    public JAXBElement<byte[]> createCreateOSPViewContent(byte[] value) {
        return new JAXBElement<byte[]>(_UpdateOSPViewContent_QNAME, byte[].class, CreateOSPView.class, ((byte[]) value));
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "return", scope = GetClassHierarchyResponse.class)
    public JAXBElement<byte[]> createGetClassHierarchyResponseReturn(byte[] value) {
        return new JAXBElement<byte[]>(_GetDeviceLayoutStructureResponseReturn_QNAME, byte[].class, GetClassHierarchyResponse.class, ((byte[]) value));
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "background", scope = CreateGeneralView.class)
    public JAXBElement<byte[]> createCreateGeneralViewBackground(byte[] value) {
        return new JAXBElement<byte[]>(_UpdateObjectRelatedViewBackground_QNAME, byte[].class, CreateGeneralView.class, ((byte[]) value));
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "structure", scope = CreateGeneralView.class)
    public JAXBElement<byte[]> createCreateGeneralViewStructure(byte[] value) {
        return new JAXBElement<byte[]>(_UpdateObjectRelatedViewStructure_QNAME, byte[].class, CreateGeneralView.class, ((byte[]) value));
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "background", scope = UpdateListTypeItemRelatedView.class)
    public JAXBElement<byte[]> createUpdateListTypeItemRelatedViewBackground(byte[] value) {
        return new JAXBElement<byte[]>(_UpdateObjectRelatedViewBackground_QNAME, byte[].class, UpdateListTypeItemRelatedView.class, ((byte[]) value));
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "structure", scope = UpdateListTypeItemRelatedView.class)
    public JAXBElement<byte[]> createUpdateListTypeItemRelatedViewStructure(byte[] value) {
        return new JAXBElement<byte[]>(_UpdateObjectRelatedViewStructure_QNAME, byte[].class, UpdateListTypeItemRelatedView.class, ((byte[]) value));
    }

}
