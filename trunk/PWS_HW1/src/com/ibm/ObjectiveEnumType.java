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
 * <p>Java class for ObjectiveEnumType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ObjectiveEnumType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="CRL-Business Transformation Solution"/>
 *     &lt;enumeration value="CRL-Supply Chain and Logistics Management Research"/>
 *     &lt;enumeration value="CRL-Management Science and Operations Research"/>
 *     &lt;enumeration value="CRL-User Paradigm Research"/>
 *     &lt;enumeration value="CRL-Distributed Computing"/>
 *     &lt;enumeration value="CRL-Data Management"/>
 *     &lt;enumeration value="CRL-Internet Media"/>
 *     &lt;enumeration value="CRL-Embedded System Design"/>
 *     &lt;enumeration value="CRL-Knowledge Representation and Reasoning"/>
 *     &lt;enumeration value="CRL-Mobile Messaging"/>
 *     &lt;enumeration value="CRL-Wireless Applications and Solutions"/>
 *     &lt;enumeration value="CRL-Home/Office Automation"/>
 *     &lt;enumeration value="CRL-Speech Technology"/>
 *     &lt;enumeration value="CDL-Pervasive Computing"/>
 *     &lt;enumeration value="CDL-Web Platform and Applications"/>
 *     &lt;enumeration value="CDL-Tivoli Development"/>
 *     &lt;enumeration value="CDL-Linux Development, Cluster And Grid Computing"/>
 *     &lt;enumeration value="CDL-Speech Development"/>
 *     &lt;enumeration value="CDL-Middleware and Systems Development"/>
 *     &lt;enumeration value="CDL-eBusiness"/>
 *     &lt;enumeration value="CDL-Banking System Development"/>
 *     &lt;enumeration value="CDL-Lotus Software Testing"/>
 *     &lt;enumeration value="CDL-eCommerce testing and Linux development"/>
 *     &lt;enumeration value="CDL-e-Commerce Development"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlEnum
public enum ObjectiveEnumType {

    @XmlEnumValue("CRL-Business Transformation Solution")
    CRL_BUSINESS_TRANSFORMATION_SOLUTION("CRL-Business Transformation Solution"),
    @XmlEnumValue("CRL-Supply Chain and Logistics Management Research")
    CRL_SUPPLY_CHAIN_AND_LOGISTICS_MANAGEMENT_RESEARCH("CRL-Supply Chain and Logistics Management Research"),
    @XmlEnumValue("CRL-Management Science and Operations Research")
    CRL_MANAGEMENT_SCIENCE_AND_OPERATIONS_RESEARCH("CRL-Management Science and Operations Research"),
    @XmlEnumValue("CRL-User Paradigm Research")
    CRL_USER_PARADIGM_RESEARCH("CRL-User Paradigm Research"),
    @XmlEnumValue("CRL-Distributed Computing")
    CRL_DISTRIBUTED_COMPUTING("CRL-Distributed Computing"),
    @XmlEnumValue("CRL-Data Management")
    CRL_DATA_MANAGEMENT("CRL-Data Management"),
    @XmlEnumValue("CRL-Internet Media")
    CRL_INTERNET_MEDIA("CRL-Internet Media"),
    @XmlEnumValue("CRL-Embedded System Design")
    CRL_EMBEDDED_SYSTEM_DESIGN("CRL-Embedded System Design"),
    @XmlEnumValue("CRL-Knowledge Representation and Reasoning")
    CRL_KNOWLEDGE_REPRESENTATION_AND_REASONING("CRL-Knowledge Representation and Reasoning"),
    @XmlEnumValue("CRL-Mobile Messaging")
    CRL_MOBILE_MESSAGING("CRL-Mobile Messaging"),
    @XmlEnumValue("CRL-Wireless Applications and Solutions")
    CRL_WIRELESS_APPLICATIONS_AND_SOLUTIONS("CRL-Wireless Applications and Solutions"),
    @XmlEnumValue("CRL-Home/Office Automation")
    CRL_HOME_OFFICE_AUTOMATION("CRL-Home/Office Automation"),
    @XmlEnumValue("CRL-Speech Technology")
    CRL_SPEECH_TECHNOLOGY("CRL-Speech Technology"),
    @XmlEnumValue("CDL-Pervasive Computing")
    CDL_PERVASIVE_COMPUTING("CDL-Pervasive Computing"),
    @XmlEnumValue("CDL-Web Platform and Applications")
    CDL_WEB_PLATFORM_AND_APPLICATIONS("CDL-Web Platform and Applications"),
    @XmlEnumValue("CDL-Tivoli Development")
    CDL_TIVOLI_DEVELOPMENT("CDL-Tivoli Development"),
    @XmlEnumValue("CDL-Linux Development, Cluster And Grid Computing")
    CDL_LINUX_DEVELOPMENT_CLUSTER_AND_GRID_COMPUTING("CDL-Linux Development, Cluster And Grid Computing"),
    @XmlEnumValue("CDL-Speech Development")
    CDL_SPEECH_DEVELOPMENT("CDL-Speech Development"),
    @XmlEnumValue("CDL-Middleware and Systems Development")
    CDL_MIDDLEWARE_AND_SYSTEMS_DEVELOPMENT("CDL-Middleware and Systems Development"),
    @XmlEnumValue("CDL-eBusiness")
    CDL_E_BUSINESS("CDL-eBusiness"),
    @XmlEnumValue("CDL-Banking System Development")
    CDL_BANKING_SYSTEM_DEVELOPMENT("CDL-Banking System Development"),
    @XmlEnumValue("CDL-Lotus Software Testing")
    CDL_LOTUS_SOFTWARE_TESTING("CDL-Lotus Software Testing"),
    @XmlEnumValue("CDL-eCommerce testing and Linux development")
    CDL_E_COMMERCE_TESTING_AND_LINUX_DEVELOPMENT("CDL-eCommerce testing and Linux development"),
    @XmlEnumValue("CDL-e-Commerce Development")
    CDL_E_COMMERCE_DEVELOPMENT("CDL-e-Commerce Development");
    private final String value;

    ObjectiveEnumType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ObjectiveEnumType fromValue(String v) {
        for (ObjectiveEnumType c: ObjectiveEnumType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v.toString());
    }

}
