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
 * It describes the information of an award.
 * 
 * <p>Java class for AwardType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AwardType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="AwardName" type="{http://www.ibm.com}NameType"/>
 *         &lt;element name="Level" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="16"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="AwardGrantor" type="{http://www.ibm.com}NameType" minOccurs="0"/>
 *         &lt;element name="AwardedDate" type="{http://www.ibm.com}FlexibleDates"/>
 *         &lt;element name="ReasonToBeAwarded" type="{http://www.ibm.com}TextDescriptionType" minOccurs="0"/>
 *         &lt;element name="PersonalContributionDescription" type="{http://www.ibm.com}TextDescriptionType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AwardType", propOrder = {
    "awardName",
    "level",
    "awardGrantor",
    "awardedDate",
    "reasonToBeAwarded",
    "personalContributionDescription"
})
public class AwardType {

    @XmlElement(name = "AwardName", required = true)
    protected String awardName;
    @XmlElement(name = "Level")
    protected String level;
    @XmlElement(name = "AwardGrantor")
    protected String awardGrantor;
    @XmlElement(name = "AwardedDate", required = true)
    protected FlexibleDates awardedDate;
    @XmlElement(name = "ReasonToBeAwarded")
    protected String reasonToBeAwarded;
    @XmlElement(name = "PersonalContributionDescription")
    protected String personalContributionDescription;

    /**
     * Gets the value of the awardName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAwardName() {
        return awardName;
    }

    /**
     * Sets the value of the awardName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAwardName(String value) {
        this.awardName = value;
    }

    /**
     * Gets the value of the level property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLevel() {
        return level;
    }

    /**
     * Sets the value of the level property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLevel(String value) {
        this.level = value;
    }

    /**
     * Gets the value of the awardGrantor property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAwardGrantor() {
        return awardGrantor;
    }

    /**
     * Sets the value of the awardGrantor property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAwardGrantor(String value) {
        this.awardGrantor = value;
    }

    /**
     * Gets the value of the awardedDate property.
     * 
     * @return
     *     possible object is
     *     {@link FlexibleDates }
     *     
     */
    public FlexibleDates getAwardedDate() {
        return awardedDate;
    }

    /**
     * Sets the value of the awardedDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link FlexibleDates }
     *     
     */
    public void setAwardedDate(FlexibleDates value) {
        this.awardedDate = value;
    }

    /**
     * Gets the value of the reasonToBeAwarded property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReasonToBeAwarded() {
        return reasonToBeAwarded;
    }

    /**
     * Sets the value of the reasonToBeAwarded property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReasonToBeAwarded(String value) {
        this.reasonToBeAwarded = value;
    }

    /**
     * Gets the value of the personalContributionDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPersonalContributionDescription() {
        return personalContributionDescription;
    }

    /**
     * Sets the value of the personalContributionDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPersonalContributionDescription(String value) {
        this.personalContributionDescription = value;
    }

}
