//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.0 in JDK 1.6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2008.01.25 at 09:08:09 PM CET 
//


package com.ibm;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Speciality means your strong suits, such as music, sports, etc
 * 
 * <p>Java class for SpecialityType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SpecialityType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="SpecialityName" type="{http://www.ibm.com}NameType"/>
 *         &lt;element name="SpecialityDescription" type="{http://www.ibm.com}TextDescriptionType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SpecialityType", propOrder = {
    "specialityName",
    "specialityDescription"
})
public class SpecialityType {

    @XmlElement(name = "SpecialityName", required = true)
    protected String specialityName;
    @XmlElement(name = "SpecialityDescription", required = true)
    protected String specialityDescription;

    /**
     * Gets the value of the specialityName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSpecialityName() {
        return specialityName;
    }

    /**
     * Sets the value of the specialityName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSpecialityName(String value) {
        this.specialityName = value;
    }

    /**
     * Gets the value of the specialityDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSpecialityDescription() {
        return specialityDescription;
    }

    /**
     * Sets the value of the specialityDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSpecialityDescription(String value) {
        this.specialityDescription = value;
    }

}
