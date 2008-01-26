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
            PhoneNumberType phoneNumber = createPhoneNumber(objFactory, "+46", "704646375", null);
            ArrayList<PhoneNumberType> phoneList = new ArrayList<PhoneNumberType>();
            phoneList.add(phoneNumber);
            
            ArrayList<String> emailAddrList = new ArrayList<String>();
            emailAddrList.add("Ricky.yanyi326@gmail.com");
            
            PostalType postInfo = createPostalInfo(objFactory, "Kista Alléväg 48 B:10", "16455");
            
            ContactMethodType contactMethod = createContactMethod(objFactory, phoneList, emailAddrList, postInfo);
            
            ContactInfoType contactInfo = createContactInfo(objFactory, name, contactMethod);
            
            resume.setContactInfo(contactInfo);
            
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
    
    /////////////////////////////////////////////////////////////////////////////////
    // Above is Wei 
    ///////////////////////////////////////////////////////////////////////////////////
    // Below is Yan
    ///////////////////////////////////////////////////////////////////////////////////
    
    

}
