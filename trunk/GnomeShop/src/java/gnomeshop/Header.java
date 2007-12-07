/*
 * Header.java
 *
 * Created on Dec 5, 2007, 1:52:07 PM
 */
 
package gnomeshop;

import com.sun.rave.web.ui.appbase.AbstractFragmentBean;
import com.sun.webui.jsf.component.Hyperlink;
import javax.faces.FacesException;
import javax.faces.component.html.HtmlGraphicImage;
import javax.faces.component.html.HtmlOutputLink;
import javax.faces.component.html.HtmlOutputText;

/**
 * <p>Fragment bean that corresponds to a similarly named JSP page
 * fragment.  This class contains component definitions (and initialization
 * code) for all components that you have defined on this fragment, as well as
 * lifecycle methods and event handlers where you may add behavior
 * to respond to incoming events.</p>
 *
 * @author Kop
 */
public class Header extends AbstractFragmentBean {
    // <editor-fold defaultstate="collapsed" desc="Managed Component Definition">

    /**
     * <p>Automatically managed component initialization. <strong>WARNING:</strong>
     * This method is automatically generated, so any user-specified code inserted
     * here is subject to being replaced.</p>
     */
    private void _init() throws Exception {
    }
    private HtmlGraphicImage image1 = new HtmlGraphicImage();

    public HtmlGraphicImage getImage1() {
        return image1;
    }

    public void setImage1(HtmlGraphicImage hgi) {
        this.image1 = hgi;
    }
    private HtmlOutputLink homeHyperlink = new HtmlOutputLink();

    public HtmlOutputLink getHomeHyperlink() {
        return homeHyperlink;
    }

    public void setHomeHyperlink(HtmlOutputLink hol) {
        this.homeHyperlink = hol;
    }
    private HtmlOutputText hyperlink1Text = new HtmlOutputText();

    public HtmlOutputText getHyperlink1Text() {
        return hyperlink1Text;
    }

    public void setHyperlink1Text(HtmlOutputText hot) {
        this.hyperlink1Text = hot;
    }
    private HtmlOutputLink adminHyperlink = new HtmlOutputLink();

    public HtmlOutputLink getAdminHyperlink() {
        return adminHyperlink;
    }

    public void setAdminHyperlink(HtmlOutputLink hol) {
        this.adminHyperlink = hol;
    }
    private HtmlOutputText hyperLink1Text = new HtmlOutputText();

    public HtmlOutputText getHyperLink1Text() {
        return hyperLink1Text;
    }

    public void setHyperLink1Text(HtmlOutputText hot) {
        this.hyperLink1Text = hot;
    }
    private HtmlOutputLink registerHyperlink = new HtmlOutputLink();

    public HtmlOutputLink getRegisterHyperlink() {
        return registerHyperlink;
    }

    public void setRegisterHyperlink(HtmlOutputLink hol) {
        this.registerHyperlink = hol;
    }
    private HtmlOutputText hyperlink1Text1 = new HtmlOutputText();

    public HtmlOutputText getHyperlink1Text1() {
        return hyperlink1Text1;
    }

    public void setHyperlink1Text1(HtmlOutputText hot) {
        this.hyperlink1Text1 = hot;
    }
    private HtmlOutputLink loginHyperlink = new HtmlOutputLink();

    public HtmlOutputLink getLoginHyperlink() {
        return loginHyperlink;
    }

    public void setLoginHyperlink(HtmlOutputLink hol) {
        this.loginHyperlink = hol;
    }
    private HtmlOutputText hyperlink1Text2 = new HtmlOutputText();

    public HtmlOutputText getHyperlink1Text2() {
        return hyperlink1Text2;
    }

    public void setHyperlink1Text2(HtmlOutputText hot) {
        this.hyperlink1Text2 = hot;
    }
    private HtmlOutputLink shoppingCartHyperlink = new HtmlOutputLink();

    public HtmlOutputLink getShoppingCartHyperlink() {
        return shoppingCartHyperlink;
    }

    public void setShoppingCartHyperlink(HtmlOutputLink hol) {
        this.shoppingCartHyperlink = hol;
    }
    private HtmlOutputText hyperlink1Text3 = new HtmlOutputText();

    public HtmlOutputText getHyperlink1Text3() {
        return hyperlink1Text3;
    }

    public void setHyperlink1Text3(HtmlOutputText hot) {
        this.hyperlink1Text3 = hot;
    }
    private HtmlOutputLink manageHyperlink = new HtmlOutputLink();

    public HtmlOutputLink getManageHyperlink() {
        return manageHyperlink;
    }

    public void setManageHyperlink(HtmlOutputLink hol) {
        this.manageHyperlink = hol;
    }
    private HtmlOutputText hyperlink1Text4 = new HtmlOutputText();

    public HtmlOutputText getHyperlink1Text4() {
        return hyperlink1Text4;
    }

    public void setHyperlink1Text4(HtmlOutputText hot) {
        this.hyperlink1Text4 = hot;
    }
    // </editor-fold>

    public Header() {
    }

    /**
     * <p>Callback method that is called whenever a page containing
     * this page fragment is navigated to, either directly via a URL,
     * or indirectly via page navigation.  Override this method to acquire
     * resources that will be needed for event handlers and lifecycle methods.</p>
     * 
     * <p>The default implementation does nothing.</p>
     */
    @Override
    public void init() {
        // Perform initializations inherited from our superclass
        super.init();
        // Perform application initialization that must complete
        // *before* managed components are initialized
        // TODO - add your own initialiation code here
        
        
        // <editor-fold defaultstate="collapsed" desc="Visual-Web-managed Component Initialization">
        // Initialize automatically managed components
        // *Note* - this logic should NOT be modified
        try {
            _init();
        } catch (Exception e) {
            log("Page1 Initialization Failure", e);
            throw e instanceof FacesException ? (FacesException) e: new FacesException(e);
        }
        
        // </editor-fold>
        // Perform application initialization that must complete
        // *after* managed components are initialized
        // TODO - add your own initialization code here
    }

    /**
     * <p>Callback method that is called after rendering is completed for
     * this request, if <code>init()</code> was called.  Override this
     * method to release resources acquired in the <code>init()</code>
     * resources that will be needed for event handlers and lifecycle methods.</p>
     * 
     * <p>The default implementation does nothing.</p>
     */
    @Override
    public void destroy() {
    }

        /**
         * <p>Return a reference to the scoped data bean.</p>
         *
         * @return reference to the scoped data bean
         */
        protected ApplicationBean getApplicationBean() {
                return (ApplicationBean) getBean("ApplicationBean");
        }

        /**
         * <p>Return a reference to the scoped data bean.</p>
         *
         * @return reference to the scoped data bean
         */
        protected RequestBean getRequestBean() {
                return (RequestBean) getBean("RequestBean");
        }

        /**
         * <p>Return a reference to the scoped data bean.</p>
         *
         * @return reference to the scoped data bean
         */
        protected SessionBean getSessionBean() {
                return (SessionBean) getBean("SessionBean");
        }

    public String indexHyperlink_action() {
        // TODO: Process the action. Return value is a navigation
        // case name where null will return to the same page.
        return null;
    }

    public String loginHyperlink_action() {
        // TODO: Process the action. Return value is a navigation
        // case name where null will return to the same page.
        return "Login";
    }

    public String manageHyperlink_action() {
        // TODO: Process the action. Return value is a navigation
        // case name where null will return to the same page.
        return "Manage";
    }

    public String registerHyperlink_action() {
        // TODO: Process the action. Return value is a navigation
        // case name where null will return to the same page.
        return "Register";
    }

    public String adminHyperlink_action() {
        // TODO: Process the action. Return value is a navigation
        // case name where null will return to the same page.
        return "Admin";
    }

    public String shoppingCartHyperlink_action() {
        // TODO: Process the action. Return value is a navigation
        // case name where null will return to the same page.
        return "ShoppingCart";
    }
    
    public String homeHyperlink_action() {
        // TODO: Process the action. Return value is a navigation
        // case name where null will return to the same page.
        return "Home";
    }
}
