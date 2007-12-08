/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gnomeshop;

import gnomeshop.items.MemberBean;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

/**
 *
 * @author Kop
 */
public class LoginManager {
    private String userName = null;
    private String password = null;
    private MemberBean currentMember = null;
    
    public LoginManager() {
        userName = "";
        password ="";
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * This method is used to login
     * @return
     */
    public String loginUser() {
        // TODO:
        // Get DatabaseUtil instance
        FacesContext facesContext = FacesContext.getCurrentInstance();
	ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
	DatabaseUtil dbUtil = (DatabaseUtil) servletContext.getAttribute("DATABASE_UTIL");
	if (null != dbUtil) {
            currentMember = dbUtil.getMemberByUserNameAndPwd(userName, password);
        } 
        if (null != currentMember) {
            return "LoginSucceed";
        } else {
            return "LoginFailed";
        }
    }
    
    public String checkAdmin() {
        String adminReq = "Admin required";
        if (null == currentMember) {
            return adminReq;
        }
        if (currentMember.getMemberLevel() != MemberBean.ADMINISTRATOR) {
            return adminReq;
        }
        return null;
    }
    
    /**
     * This method is used to check if the user is logged in
     * @return Whether the user logs in
     */
    public boolean getLoggedIn() {
        return (null != currentMember);
    }
    
    /**
     * This method is used to check if the login user is the administrator or not
     * @return Whether the admin logs in
     */
    public boolean isAdmin() {
        if (null != currentMember) {
            return (MemberBean.ADMINISTRATOR == currentMember.getMemberLevel());
        } else {
            return false;
        }
    }
    
    public String  logoutUser() {
        // TODO:
        this.userName = null;
        this.password = null;
        this.currentMember = null;
        return null;
    }
}
