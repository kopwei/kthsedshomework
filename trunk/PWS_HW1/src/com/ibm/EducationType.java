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
 * It describes a piece of education experience, including the school name, the degree name and major information. 
 * 
 * <p>Java class for EducationType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EducationType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="SchoolName" type="{http://www.ibm.com}NameType"/>
 *         &lt;element name="Degree" type="{http://www.ibm.com}DegreeType"/>
 *         &lt;element name="Major" type="{http://www.ibm.com}MajorType"/>
 *         &lt;element name="DissertationName" type="{http://www.ibm.com}NameType" minOccurs="0"/>
 *         &lt;element name="QualificationReference" type="{http://www.ibm.com}QualificationReferenceType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EducationType", propOrder = {
    "schoolName",
    "degree",
    "major",
    "dissertationName",
    "qualificationReference"
})
public class EducationType {

    @XmlElement(name = "SchoolName", required = true)
    protected String schoolName;
    @XmlElement(name = "Degree", required = true)
    protected DegreeType degree;
    @XmlElement(name = "Major", required = true)
    protected String major;
    @XmlElement(name = "DissertationName")
    protected String dissertationName;
    @XmlElement(name = "QualificationReference")
    protected List<QualificationReferenceType> qualificationReference;

    /**
     * Gets the value of the schoolName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSchoolName() {
        return schoolName;
    }

    /**
     * Sets the value of the schoolName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSchoolName(String value) {
        this.schoolName = value;
    }

    /**
     * Gets the value of the degree property.
     * 
     * @return
     *     possible object is
     *     {@link DegreeType }
     *     
     */
    public DegreeType getDegree() {
        return degree;
    }

    /**
     * Sets the value of the degree property.
     * 
     * @param value
     *     allowed object is
     *     {@link DegreeType }
     *     
     */
    public void setDegree(DegreeType value) {
        this.degree = value;
    }

    /**
     * Gets the value of the major property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMajor() {
        return major;
    }

    /**
     * Sets the value of the major property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMajor(String value) {
        this.major = value;
    }

    /**
     * Gets the value of the dissertationName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDissertationName() {
        return dissertationName;
    }

    /**
     * Sets the value of the dissertationName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDissertationName(String value) {
        this.dissertationName = value;
    }

    /**
     * Gets the value of the qualificationReference property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the qualificationReference property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getQualificationReference().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link QualificationReferenceType }
     * 
     * 
     */
    public List<QualificationReferenceType> getQualificationReference() {
        if (qualificationReference == null) {
            qualificationReference = new ArrayList<QualificationReferenceType>();
        }
        return this.qualificationReference;
    }

}