/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package marshal;

import com.ibm.*;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
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
            
            // Prepare the education history
            Resume.EducationHistory eduHistory = new Resume.EducationHistory();
            
            XMLGregorianCalendar calendar = DatatypeFactory.newInstance().newXMLGregorianCalendar();
            calendar.setYear(2007);
            FlexibleDates degreeDate = createFlexibleDate(objFactory, calendar, DatatypeConstants.GYEAR);
            DegreeType degree = createDegree(objFactory, "BSc", degreeDate);
            EducationType edu = createEductionInfo(objFactory, "Wuhan University", degree, "Computer Science", null, null);
            eduHistory.getEducation().add(edu);
            
            resume.setEducationHistory(eduHistory);
            
            // Set the complex type ---- Languages
            Resume.Languages languages = new Resume.Languages();
            
            LanguageNameEnumType languageNameType = LanguageNameEnumType.ENGLISH;
            SkillLevelType skillLevelType = SkillLevelType.ABOVE_AVERAGE;
            LanguageType language = createLanguage(objFactory, languageNameType, skillLevelType);
            
            languages.getLanguage().add(language);
            
            resume.setLanguages(languages);
            
            // Set the complex type ---- Experience
            XMLGregorianCalendar calendar2 = DatatypeFactory.newInstance().newXMLGregorianCalendar();
            calendar2.setYear(2004);
            FlexibleDates activityDate = createFlexibleDate(objFactory, calendar2, DatatypeConstants.GYEAR);
            DurationType duration = createDuration(objFactory, activityDate, null);
            
            ArrayList<String> role = new ArrayList<String>();
            role.add("organizer");
            SocialActivityType socialActivity = createSocialActivity(objFactory, "Youth Volunteer Association relief efforts for victims of disaster area", 
                    duration, role);
            
            ExperienceType experience = new ExperienceType();
            experience.getSocialActivity().add(socialActivity);
            
            resume.setExperience(experience);
            
            // Set the complex type ---- Qualifications
            XMLGregorianCalendar calendar3 = DatatypeFactory.newInstance().newXMLGregorianCalendar();
            calendar3.setYear(2007);
            calendar3.setMonth(06);
            FlexibleDates certifiedDate = createFlexibleDate(objFactory, calendar3, DatatypeConstants.GYEARMONTH);
            CertificationType certification = createCertification(objFactory, "Certification of Bachelor Degree", "National Notarization Department in Hubei", certifiedDate, null, null);
            QualificationType qualification = new QualificationType();
            qualification.getCertification().add(certification);
            
            resume.setQualifications(qualification);
            
            // Write out the resume to standard output
            m.marshal(resume, System.out);
            m.marshal(resume, new FileOutputStream("Resume.xml"));
            
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
    
    /**
     * 
     * @param objectFactory
     * @param languageNameType
     * @param skillLevelType
     * @return
     * @throws javax.xml.bind.JAXBException
     */
    private static LanguageType createLanguage(ObjectFactory objectFactory, 
            LanguageNameEnumType languageNameType, SkillLevelType skillLevelType) throws JAXBException {
        LanguageType language = objectFactory.createLanguageType();
        LanguageNameEnumType languageName = languageNameType;
        language.setLanguageName(languageName.value());
        language.setLevel(skillLevelType);
        
        return language;
    }
    
    /**
     * 
     * @param objectFactory
     * @param project
     * @param employment
     * @param professionalTraining
     * @param socialActivity
     * @return
     * @throws javax.xml.bind.JAXBException
     */
    private static ExperienceType createExperience(ObjectFactory objectFactory, 
            List<ProjectType> project, List<EmploymentType> employment, List<ProfessionalTrainingType> professionalTraining, 
            List<SocialActivityType> socialActivity) throws JAXBException {
        ExperienceType experience = objectFactory.createExperienceType();
        for (ProjectType _project : project) {
            experience.getProject().add(_project);
        }
        
        for (EmploymentType _employment : employment) {
            experience.getEmployment().add(_employment);
        }

        for (ProfessionalTrainingType _professionalTraining : professionalTraining) {
            experience.getProfessionalTraining().add(_professionalTraining);
        }

        for (SocialActivityType _socialActivity : socialActivity) {
            experience.getSocialActivity().add(_socialActivity);
        }

        return experience;
    }
    
    /**
     * 
     * @param objectFactory
     * @param trainingName
     * @param trainingProvider
     * @param trainingSite
     * @param duration
     * @param demonstratedSkills
     * @return
     * @throws javax.xml.bind.JAXBException
     */
    private static ProfessionalTrainingType createProfessionalTraining(ObjectFactory objectFactory, 
            String trainingName, String trainingProvider, String trainingSite, DurationType duration, 
            DemonstratedSkillsType demonstratedSkills) throws JAXBException {
        ProfessionalTrainingType professionalTraining = objectFactory.createProfessionalTrainingType();
        
        professionalTraining.setTrainingName(trainingName);
        professionalTraining.setTrainingProvider(trainingProvider);
        professionalTraining.setTrainingSite(trainingSite);
        professionalTraining.setDuration(duration);
        professionalTraining.setDemonstratedSkills(demonstratedSkills);
        
        return professionalTraining;
    }
    
    /**
     * 
     * @param objectFactory
     * @param startDate
     * @param endDate
     * @return
     * @throws javax.xml.bind.JAXBException
     */
    private static DurationType createDuration(ObjectFactory objectFactory, FlexibleDates startDate, 
            FlexibleDates endDate) throws JAXBException {
        DurationType duration = objectFactory.createDurationType();
        duration.setStartDate(startDate);
        duration.setEndDate(endDate);
        
        return duration;
    }
    
    /**
     * 
     * @param objectFactory
     * @param technicalSkill
     * @param nonTechnicalSkill
     * @return
     * @throws javax.xml.bind.JAXBException
     */
    private static DemonstratedSkillsType createDemonstratedSkills(ObjectFactory objectFactory, 
            List<TechSkillType> technicalSkill, List<NonTechSkillType> nonTechnicalSkill) throws JAXBException {
        DemonstratedSkillsType demonstratedSkills = objectFactory.createDemonstratedSkillsType();
        
        for (TechSkillType techSkillType : technicalSkill) {
            demonstratedSkills.getTechnicalSkill().add(techSkillType);
        }

        for (NonTechSkillType nonTechSkillType : nonTechnicalSkill) {
            demonstratedSkills.getNonTechnicalSkill().add(nonTechSkillType);
        }

        return demonstratedSkills;
    }

    /**
     * 
     * @param objectFactory
     * @param skillName
     * @param skillLevel
     * @return
     * @throws javax.xml.bind.JAXBException
     */
    private static TechSkillType createTechSkill(ObjectFactory objectFactory, String skillName, 
            SkillLevelType skillLevel) throws JAXBException {
        TechSkillType techSkill = objectFactory.createTechSkillType();
        
        techSkill.setSkillName(skillName);
        techSkill.setSkillLevel(skillLevel);
        
        return techSkill;
    }
    
    /**
     * 
     * @param objectFactory
     * @param skillName
     * @param skillLevel
     * @return
     * @throws javax.xml.bind.JAXBException
     */
    private static NonTechSkillType createNonTechSkill(ObjectFactory objectFactory, String skillName, 
            SkillLevelType skillLevel) throws JAXBException {
        NonTechSkillType nonTechSkill = objectFactory.createNonTechSkillType();
        
        nonTechSkill.setSkillName(skillName);
        nonTechSkill.setSkillLevel(skillLevel);
        
        return nonTechSkill;
    }
    
    /**
     * 
     * @param objectFactory
     * @param activityDescription
     * @param duration
     * @param role
     * @return
     * @throws javax.xml.bind.JAXBException
     */
    private static SocialActivityType createSocialActivity(ObjectFactory objectFactory, String activityDescription, 
            DurationType duration, List<String> role) throws JAXBException {
        SocialActivityType socialActivity = objectFactory.createSocialActivityType();
        
        socialActivity.setActivityDescription(activityDescription);
        socialActivity.setDuration(duration);
        for (String _role : role) {
            socialActivity.getRole().add(_role);
        }

        return socialActivity;
    }
    
    /**
     * 
     * @param objectFactory
     * @param certificationTitle
     * @param certificationGrantor
     * @param certifiedDate
     * @param score
     * @param demonstratedSkills
     * @return
     * @throws javax.xml.bind.JAXBException
     */
    private static CertificationType createCertification(ObjectFactory objectFactory, String certificationTitle, 
            String certificationGrantor, FlexibleDates certifiedDate, String score, DemonstratedSkillsType demonstratedSkills) 
            throws JAXBException {
        CertificationType certification = objectFactory.createCertificationType();
        
        certification.setCertificationTitle(certificationTitle);
        certification.setCertificationGrantor(certificationGrantor);
        certification.setCertifiedDate(certifiedDate);
        certification.setScore(score);
        certification.setDemonstratedSkills(demonstratedSkills);
        
        return certification;
    }
}
