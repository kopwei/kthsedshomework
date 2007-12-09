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
     * 
     * @return
     */
    public String  logoutUser() {
        // TODO:
        this.userName = null;
        this.password = null;
        this.setCurrentMember(null);
        return null;
    }
    
    /**
     * 
     * @return
     */
    public MemberBean getCurrentMember() {
        return currentMember;
    }

    /**
     * 
     * @param currentMember
     */
    public void setCurrentMember(MemberBean currentMember) {
        this.currentMember = currentMember;
    }
}
