/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package marshal;

import com.ibm.*;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;

/**
 *
 * @author Kop
 */
public class ResumeBuilder {
    public static void main(String[] args) {
        try {
            // Create a JAXBContext
            JAXBContext jc = JAXBContext.newInstance("com.ibm");
            Marshaller m = jc.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            // Create an ObjectFactory instance;
            ObjectFactory objFactory = new ObjectFactory();
            // Create an empty resume
            Resume resume = objFactory.createResume();
            // set the required resume attribute
            PersonNameType name = createPersonName(objFactory, "Yi", null, "Yan");
            // Set the phone list
            PhoneNumberType phoneNumber = createPhoneNumber(objFactory, "+46", "704646375", null);
            ArrayList<PhoneNumberType> phoneList = new ArrayList<PhoneNumberType>();
            phoneList.add(phoneNumber);
            
            // Set the email list
            ArrayList<String> emailAddrList = new ArrayList<String>();
            emailAddrList.add("Ricky.yanyi326@gmail.com");
            
            // Prepare the postal info
            PostalType postInfo = createPostalInfo(objFactory, "Kista Alléväg 48 B:10", "16455");
            
            ContactMethodType contactMethod = createContactMethod(objFactory, phoneList, emailAddrList, postInfo);
            
            ContactInfoType contactInfo = createContactInfo(objFactory, name, contactMethod);
            
            resume.setContactInfo(contactInfo);
            
            // Prepare the objectives
            ObjectiveEnumType obj = ObjectiveEnumType.CDL_BANKING_SYSTEM_DEVELOPMENT;
            
            Resume.Objectives objs = new Resume.Objectives();
            objs.getObjective().add(obj.value());            
            resume.setObjectives(objs);
            
            // Prepate the education history
            Resume.EducationHistory eduHistory = new Resume.EducationHistory();
            
            XMLGregorianCalendar calendar = DatatypeFactory.newInstance().newXMLGregorianCalendar();
            calendar.setYear(2007);
            FlexibleDates degreeDate = createFlexibleDate(objFactory, calendar, DatatypeConstants.GYEAR);
            DegreeType degree = createDegree(objFactory, "BSc", degreeDate);
            EducationType edu = createEductionInfo(objFactory, "Wuhan University", degree, "Computer Science", null, null);
            eduHistory.getEducation().add(edu);
            
            resume.setEducationHistory(eduHistory);
            
            
            // Write out the resume to standard output
            m.marshal(resume, System.out);            
            
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        
    }
    
    /**
     * 
     * @param objFactory
     * @param personName
     * @param contactMethod
     * @return
     */
    private static ContactInfoType createContactInfo(ObjectFactory objFactory,
            PersonNameType personName, ContactMethodType contactMethod) throws JAXBException {
        ContactInfoType contactInfo = objFactory.createContactInfoType();
        contactInfo.setPersonName(personName);
        contactInfo.setContactMethod(contactMethod);
        return contactInfo;        
    }
    
    /**
     * 
     * @param objFactory
     * @param firstName
     * @param middleName
     * @param familiName
     * @return
     */
    private static PersonNameType createPersonName(ObjectFactory objFactory, String firstName,
            String middleName, String familiName) throws JAXBException {
        PersonNameType personName = objFactory.createPersonNameType();

        personName.setFamilyName(familiName);
        if (null != middleName)
            personName.setMiddleName(familiName);
        personName.setGivenName(firstName);
        return personName;
    }
    
    /**
     * 
     * @param objFactory
     * @param phoneList
     * @param emailList
     * @param postInfo
     * @return
     */
    private static ContactMethodType createContactMethod(ObjectFactory objFactory, 
            List<PhoneNumberType> phoneList, List<String> emailList, PostalType postInfo) throws JAXBException {
        ContactMethodType contactMethod = objFactory.createContactMethodType();
        for (String email : emailList) {
            contactMethod.getInternetEmailAddress().add(email);
        }
        
        for (PhoneNumberType phone : phoneList) {
            contactMethod.getPhone().add(phone);
        }
        
        contactMethod.setPostal(postInfo);
        return contactMethod;
    }
    
    /**
     * 
     * @param objFactory
     * @param areaCode
     * @param subscriberNumber
     * @param extNumber
     * @return
     */
    private static PhoneNumberType createPhoneNumber(ObjectFactory objFactory, String areaCode,
            String subscriberNumber, String extNumber) throws JAXBException {
        PhoneNumberType phone = objFactory.createPhoneNumberType();
        phone.setSubscriberNumber(subscriberNumber);
        if (null != areaCode)
            phone.setAreaCityCode(areaCode);
        if (null != extNumber)
            phone.setExtension(extNumber);
        return phone;
    }
    
    /**
     * 
     * @param objFactory
     * @param postalAddr
     * @param postalCode
     * @return
     */
    private static PostalType createPostalInfo(ObjectFactory objFactory, String postalAddr, 
            String postalCode) throws JAXBException {
        PostalType postalInfo = objFactory.createPostalType();
        postalInfo.setPostalAddress(postalAddr);
        postalInfo.setPostalCode(postalCode);
        return postalInfo;
    } 
    
    /**
     * 
     * @param objFactory
     * @param schoolName
     * @param degree
     * @param major
     * @param desertationName
     * @param referenceList
     * @return
     * @throws javax.xml.bind.JAXBException
     */
    private static EducationType createEductionInfo(ObjectFactory objFactory, String schoolName,
            DegreeType degree, String major, String desertationName, 
            List<QualificationReferenceType> referenceList) throws JAXBException {
        EducationType education = objFactory.createEducationType();
        education.setSchoolName(schoolName);
        education.setDegree(degree);
        education.setMajor(major);
        education.setDissertationName(desertationName);
        if (null != referenceList) {
            for (QualificationReferenceType qualificationReferenceType : referenceList) {
                education.getQualificationReference().add(qualificationReferenceType);
            }
        }
        return education;
        
    }
    
    /**
     * 
     * @param objFactory
     * @param degreeName
     * @param degreeDate
     * @return
     * @throws javax.xml.bind.JAXBException
     */
    private static DegreeType createDegree(ObjectFactory objFactory, String degreeName, 
            FlexibleDates degreeDate) throws JAXBException {
        DegreeType degree = objFactory.createDegreeType();
        degree.setDegreeName(degreeName);
        degree.setDegreeDate(degreeDate);
        return degree;
    }
    
    /**
     * 
     * @param objFactory
     * @param yearMonth
     * @return
     * @throws javax.xml.bind.JAXBException
     */
    private static FlexibleDates createFlexibleDate(ObjectFactory objFactory, 
            XMLGregorianCalendar calendar, QName type) throws JAXBException {
        FlexibleDates date = objFactory.createFlexibleDates();
        if (type.equals(DatatypeConstants.GYEARMONTH))
            date.setYearMonth(calendar);
        if (type.equals(DatatypeConstants.GMONTHDAY))
            date.setYearMonthDay(calendar);
        if (type.equals(DatatypeConstants.GYEAR))
            date.setYear(calendar);
        return date;
    }
    
    
    /////////////////////////////////////////////////////////////////////////////////
    // Above is Wei 
    ///////////////////////////////////////////////////////////////////////////////////
    // Below is Yan
    ///////////////////////////////////////////////////////////////////////////////////
    
    

}
