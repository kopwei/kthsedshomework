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

    public String loginUser() {
        // TODO:
        // Get DatabaseUtil instance
        FacesContext facesContext = FacesContext.getCurrentInstance();
	ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
	DatabaseUtil dbUtil = (DatabaseUtil) servletContext.getAttribute("DATABASE_UTIL");
	if (null != dbUtil) {
            currentMember = dbUtil.getMemberByUserNameAndPwd(userName, password);
        }
        return null;
    }
    
    public boolean getLoggedIn() {
        return (null == currentMember);
    }
    
    public String  logoutUser() {
        // TODO:
        this.userName = null;
        this.password = null;
        this.currentMember = null;
        return null;
    }
}
