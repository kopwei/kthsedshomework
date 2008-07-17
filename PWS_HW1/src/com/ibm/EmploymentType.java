//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.0 in JDK 1.6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2008.01.25 at 09:08:09 PM CET 
//


package com.ibm;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * It describes a candidate's employment experience in one unit. Except the employer information, there are positions a candidate have held in the unit.
 * 
 * <p>Java class for EmploymentType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EmploymentType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="EmployerInfo" type="{http://www.ibm.com}EmployerInfoType"/>
 *         &lt;element name="Position" type="{http://www.ibm.com}PositionType" maxOccurs="unbounded"/>
 *         &lt;element name="ReferencePerson" type="{http://www.ibm.com}ContactInfoType" minOccurs="0"/>
 *         &lt;element name="ReasonToLeave" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.ibm.com}TextDescriptionType">
 *               &lt;maxLength value="256"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EmploymentType", propOrder = {
    "employerInfo",
    "position",
    "referencePerson",
    "reasonToLeave"
})
public class EmploymentType {

    @XmlElement(name = "EmployerInfo", required = true)
    protected EmployerInfoType employerInfo;
    @XmlElement(name = "Position", required = true)
    protected List<PositionType> position;
    @XmlElement(name = "ReferencePerson")
    protected ContactInfoType referencePerson;
    @XmlElement(name = "ReasonToLeave")
    protected String reasonToLeave;

    /**
     * Gets the value of the employerInfo property.
     * 
     * @return
     *     possible object is
     *     {@link EmployerInfoType }
     *     
     */
    public EmployerInfoType getEmployerInfo() {
        return employerInfo;
    }

    /**
     * Sets the value of the employerInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link EmployerInfoType }
     *     
     */
    public void setEmployerInfo(EmployerInfoType value) {
        this.employerInfo = value;
    }

    /**
     * Gets the value of the position property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the position property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPosition().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PositionType }
     * 
     * 
     */
    public List<PositionType> getPosition() {
        if (position == null) {
            position = new ArrayList<PositionType>();
        }
        return this.position;
    }

    /**
     * Gets the value of the referencePerson property.
     * 
     * @return
     *     possible object is
     *     {@link ContactInfoType }
     *     
     */
    public ContactInfoType getReferencePerson() {
        return referencePerson;
    }

    /**
     * Sets the value of the referencePerson property.
     * 
     * @param value
     *     allowed object is
     *     {@link ContactInfoType }
     *     
     */
    public void setReferencePerson(ContactInfoType value) {
        this.referencePerson = value;
    }

    /**
     * Gets the value of the reasonToLeave property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReasonToLeave() {
        return reasonToLeave;
    }

    /**
     * Sets the value of the reasonToLeave property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReasonToLeave(String value) {
        this.reasonToLeave = value;
    }

}