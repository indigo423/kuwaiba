
package org.inventory.communications.wsclient;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for createProject complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="createProject">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="projectPoolId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="projectClass" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="attributeNames" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="attributeValues" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
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
@XmlType(name = "createProject", propOrder = {
    "projectPoolId",
    "projectClass",
    "attributeNames",
    "attributeValues",
    "sessionId"
})
public class CreateProject {

    protected String projectPoolId;
    protected String projectClass;
    @XmlElement(nillable = true)
    protected List<String> attributeNames;
    @XmlElement(nillable = true)
    protected List<String> attributeValues;
    protected String sessionId;

    /**
     * Gets the value of the projectPoolId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProjectPoolId() {
        return projectPoolId;
    }

    /**
     * Sets the value of the projectPoolId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProjectPoolId(String value) {
        this.projectPoolId = value;
    }

    /**
     * Gets the value of the projectClass property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProjectClass() {
        return projectClass;
    }

    /**
     * Sets the value of the projectClass property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProjectClass(String value) {
        this.projectClass = value;
    }

    /**
     * Gets the value of the attributeNames property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the attributeNames property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAttributeNames().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getAttributeNames() {
        if (attributeNames == null) {
            attributeNames = new ArrayList<String>();
        }
        return this.attributeNames;
    }

    /**
     * Gets the value of the attributeValues property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the attributeValues property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAttributeValues().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getAttributeValues() {
        if (attributeValues == null) {
            attributeValues = new ArrayList<String>();
        }
        return this.attributeValues;
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
