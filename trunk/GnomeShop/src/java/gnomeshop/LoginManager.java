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
    private String passWord = null;
    private MemberBean currentMember = null;
    
    public LoginManager() {
        
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String loginUser() {
        // TODO:
        // Get DatabaseUtil instance
        FacesContext facesContext = FacesContext.getCurrentInstance();
	ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
	DatabaseUtil dbUtil = (DatabaseUtil) servletContext.getAttribute("DATABASE_UTIL");
	if (null != dbUtil) {
            currentMember = dbUtil.getMemberByUserNameAndPwd(userName, passWord);
        }
        return null;
    }
    
    public boolean getLoggedIn() {
        return (null == currentMember);
    }
    
    public String  logoutUser() {
        // TODO:
        this.userName = null;
        this.passWord = null;
        this.currentMember = null;
        return null;
    }
}
