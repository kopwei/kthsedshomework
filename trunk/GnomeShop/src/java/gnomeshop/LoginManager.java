/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gnomeshop;

import gnomeshop.items.MemberBean;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

/**
 * This class is a session bean which is used for manage the login/logout status
 * @author Kop
 */
public class LoginManager {
    private String userName = null;
    private String password = null;
    private MemberBean currentMember = null;
    
    /**
     * Default constructor
     */
    public LoginManager() {
        userName = "";
        password ="";
    }

    /**
     * This method is used to get the user name of the current login user
     * @return The user name string
     */
    public String getUserName() {
        return userName;
    }

    /**
     * This method is used to set the user name of the current login user
     * @param userName The user name string
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * This method is used to get the password of the current login user
     * @return The password String
     */
    public String getPassword() {
        return password;
    }

    /**
     * This method is used to set the password of the current login user
     * @param password The password String
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * This method is used to login
     * @return The action string
     */
    public String loginUser() {
        // TODO:
        // Get DatabaseUtil instance
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
        DatabaseUtil dbUtil = (DatabaseUtil) servletContext.getAttribute("DATABASE_UTIL");
        if (null != dbUtil) {
            setCurrentMember(dbUtil.getMemberByUserNameAndPwd(userName, password));
        }
        // Get the member and check if it is blocked
        if (null != getCurrentMember()) {
            if (!currentMember.getBlocked()) {
                return "LoginSucceed";
            }
        }
        return "LoginFailed";
    }

    /**
     * This method is used to check if the current logged member is an administrator or not
     * @return Whether the user is an administrator
     */
    public String checkAdmin() {
        String adminReq = "Admin required";
        if (null == getCurrentMember()) {
            return adminReq;
        }
        if (getCurrentMember().getMemberLevel() != MemberBean.ADMINISTRATOR) {
            return adminReq;
        }
        return null;
    }
    
    /**
     * This method is used to check if the user is logged in
     * @return Whether the user logs in
     */
    public boolean getLoggedIn() {
        return (null != getCurrentMember());
    }
    
    /**
     * This method is used to check if the login user is the administrator or not
     * @return Whether the admin logs in
     */
    public boolean isAdmin() {
        if (null != getCurrentMember()) {
            return (MemberBean.ADMINISTRATOR == getCurrentMember().getMemberLevel());
        } else {
            return false;
        }
    }
    
    /**
     * This method is used for log out the current user
     * @return The action string
     */
    public String  logoutUser() {
        // TODO:
        this.userName = null;
        this.password = null;
        this.setCurrentMember(null);
        return null;
    }
    
    /**
     * This method is used to get the current login member
     * @return the current login member
     */
    public MemberBean getCurrentMember() {
        return currentMember;
    }

    /**
     * This method is used to set the current login member
     * @param currentMember the current login member
     */
    public void setCurrentMember(MemberBean currentMember) {
        this.currentMember = currentMember;
    }
}
