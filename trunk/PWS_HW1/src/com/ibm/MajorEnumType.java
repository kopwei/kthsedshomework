//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.0 in JDK 1.6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2008.01.25 at 09:08:09 PM CET 
//


package com.ibm;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;


/**
 * <p>Java class for MajorEnumType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="MajorEnumType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Computer Science"/>
 *     &lt;enumeration value="Electronic Engineering"/>
 *     &lt;enumeration value="Math"/>
 *     &lt;enumeration value="Information"/>
 *     &lt;enumeration value="Physics"/>
 *     &lt;enumeration value="Automation"/>
 *     &lt;enumeration value="Psychology"/>
 *     &lt;enumeration value="Behavioral Science"/>
 *     &lt;enumeration value="Recognition"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlEnum
public enum MajorEnumType {

    @XmlEnumValue("Computer Science")
    COMPUTER_SCIENCE("Computer Science"),
    @XmlEnumValue("Electronic Engineering")
    ELECTRONIC_ENGINEERING("Electronic Engineering"),
    @XmlEnumValue("Math")
    MATH("Math"),
    @XmlEnumValue("Information")
    INFORMATION("Information"),
    @XmlEnumValue("Physics")
    PHYSICS("Physics"),
    @XmlEnumValue("Automation")
    AUTOMATION("Automation"),
    @XmlEnumValue("Psychology")
    PSYCHOLOGY("Psychology"),
    @XmlEnumValue("Behavioral Science")
    BEHAVIORAL_SCIENCE("Behavioral Science"),
    @XmlEnumValue("Recognition")
    RECOGNITION("Recognition");
    private final String value;

    MajorEnumType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static MajorEnumType fromValue(String v) {
        for (MajorEnumType c: MajorEnumType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v.toString());
    }

}
