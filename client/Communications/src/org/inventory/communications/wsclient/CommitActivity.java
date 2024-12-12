
package org.inventory.communications.wsclient;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for commitActivity complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="commitActivity">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="processInstanceId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="activityDefinitionId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="artifact" type="{http://ws.northbound.kuwaiba.neotropic.org/}remoteArtifact" minOccurs="0"/>
 *         &lt;element name="sessionId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "commitActivity", propOrder = {
    "processInstanceId",
    "activityDefinitionId",
    "artifact",
    "sessionId"
})
public class CommitActivity {

    protected String processInstanceId;
    protected String activityDefinitionId;
    protected RemoteArtifact artifact;
    protected String sessionId;

    /**
     * Gets the value of the processInstanceId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProcessInstanceId() {
        return processInstanceId;
    }

    /**
     * Sets the value of the processInstanceId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProcessInstanceId(String value) {
        this.processInstanceId = value;
    }

    /**
     * Gets the value of the activityDefinitionId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getActivityDefinitionId() {
        return activityDefinitionId;
    }

    /**
     * Sets the value of the activityDefinitionId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setActivityDefinitionId(String value) {
        this.activityDefinitionId = value;
    }

    /**
     * Gets the value of the artifact property.
     * 
     * @return
     *     possible object is
     *     {@link RemoteArtifact }
     *     
     */
    public RemoteArtifact getArtifact() {
        return artifact;
    }

    /**
     * Sets the value of the artifact property.
     * 
     * @param value
     *     allowed object is
     *     {@link RemoteArtifact }
     *     
     */
    public void setArtifact(RemoteArtifact value) {
        this.artifact = value;
    }

    /**
     * Gets the value of the sessionId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSessionId() {
        return sessionId;
    }

    /**
     * Sets the value of the sessionId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSessionId(String value) {
        this.sessionId = value;
    }

}
