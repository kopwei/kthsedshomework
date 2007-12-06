/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gnomeshop.items;

import java.util.UUID;

/**
 *
 * @author Kop
 */
public class MemberBean {
    public static final int NORMALMEMBER = 1;
    public static final int ADMINISTRATOR = 2;
    
    private UUID memberId;
    private String userName;
    private String passWord;
    private String firstName;
    private String lastName;
    private String telephone;
    private String email;
    private int memberLevel;
    
   /**
     * The constructor used for initialize the information
    * @param userName Member's user name
    * @param passWord Member's password
    * @param firstName Member's first name
    * @param lastName Member's last name
    * @param telephone Member's telephone number
    * @param emailAddress Member's email address
    */           
    public MemberBean(String userName, String passWord, String firstName, String lastName, 
            String telephone, String emailAddress) {
        this.memberId = UUID.randomUUID();
        this.userName = userName;
        this.passWord = passWord;
        this.firstName = firstName;
        this.lastName = lastName;
        this.telephone = telephone;
        this.email = emailAddress;
        this.memberLevel = NORMALMEMBER;
    }
    
    /**
     * This constructor is used to re-constrct the member object from database
     * @param memberId Member's ID
     * @param userName Member's user name
     * @param passWord Member's password
     * @param firstName Member's first name
     * @param lastName Member's last name
     * @param telephone Member's telephone number
     * @param emailAddress Member's email address
     */
    public MemberBean(String memberId, String userName, String passWord, String firstName, String lastName, 
            String telephone, String emailAddress) {
        this.memberId = UUID.randomUUID();
        this.userName = userName;
        this.passWord = passWord;
        this.firstName = firstName;
        this.lastName = lastName;
        this.telephone = telephone;
        this.email = emailAddress;
        this.memberLevel = NORMALMEMBER;
    }

     public String getMemberId() {
        return memberId.toString();
    }

    public void setMemberId(String memberId) {
        this.memberId = UUID.fromString(memberId);
    }
    /**
     * This method is used to get the user name of the member
     * @return Member's first name
     */
    public String getUserName() {
        return userName;
    }

    /**
     * This method is used to set the user name of the member
     * @param userName Member's user name
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * This method is used to get the password of the member
     * @return Member's password
     */
    public String getPassWord() {
        return passWord;
    }

    /**
     * This method is used to set the password of the member
     * @param passWord Member's password
     */
    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    /**
     * This method is used to get the first name of the member
     * @return Member's first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * This method is used to set the first name of the member
     * @param firstName Member's first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * This method is used to get the last name of the member
     * @return Member's last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * This method is used to set the last name of the member
     * @param lastName Member's last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * This method is used to get the telephone number of the member
     * @return Member's telephone number
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     * This method is used to set the telephone number of the member
     * @param telephone Member's telephone number
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /**
     * This method is used to get the email address of the member
     * @return Members email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * This method is used to set the email address of the member
     * @param email Members email address
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * This method is used to get the authotity of the member
     * @return Member's authority level
     */
    public int getMemberLevel() {
        return memberLevel;
    }

    /**
     * This method is used to set the authotity of the member
     * @param memberLevel Member's authority level
     */
    public void setMemberLevel(int memberLevel) {
        this.memberLevel = memberLevel;
    }

    public String loginUser() {
        // TODO: need implementation here
        return null;
    }

 
}
