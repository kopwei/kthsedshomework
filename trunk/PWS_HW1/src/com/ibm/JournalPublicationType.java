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
 * It describes a journal publication information. It extends from the PublicationType by adding the journal name and journal volume information.
 * 
 * <p>Java class for JournalPublicationType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="JournalPublicationType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.ibm.com}PublicationType">
 *       &lt;sequence>
 *         &lt;element name="JournalName" type="{http://www.ibm.com}NameType"/>
 *         &lt;element name="JournalVolume" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="32"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "JournalPublicationType", propOrder = {
    "journalName",
    "journalVolume"
})
public class JournalPublicationType
    extends PublicationType
{

    @XmlElement(name = "JournalName", required = true)
    protected String journalName;
    @XmlElement(name = "JournalVolume")
    protected String journalVolume;

    /**
     * Gets the value of the journalName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJournalName() {
        return journalName;
    }

    /**
     * Sets the value of the journalName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJournalName(String value) {
        this.journalName = value;
    }

    /**
     * Gets the value of the journalVolume property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJournalVolume() {
        return journalVolume;
    }

    /**
     * Sets the value of the journalVolume property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJournalVolume(String value) {
        this.journalVolume = value;
    }

}