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
    private Hyperlink loginHyperlink = new Hyperlink();

    public Hyperlink getLoginHyperlink() {
        return loginHyperlink;
    }

    public void setLoginHyperlink(Hyperlink h) {
        this.loginHyperlink = h;
    }
    private Hyperlink homeHyperlink = new Hyperlink();

    public Hyperlink getHomeHyperlink() {
        return homeHyperlink;
    }

    public void setHomeHyperlink(Hyperlink h) {
        this.homeHyperlink = h;
    }
    private Hyperlink adminHyperlink = new Hyperlink();

    public Hyperlink getAdminHyperlink() {
        return adminHyperlink;
    }

    public void setAdminHyperlink(Hyperlink h) {
        this.adminHyperlink = h;
    }
    private Hyperlink registerHyperlink = new Hyperlink();

    public Hyperlink getRegisterHyperlink() {
        return registerHyperlink;
    }

    public void setRegisterHyperlink(Hyperlink h) {
        this.registerHyperlink = h;
    }
    private Hyperlink shoppingCartHyperlink = new Hyperlink();

    public Hyperlink getShoppingCartHyperlink() {
        return shoppingCartHyperlink;
    }

    public void setShoppingCartHyperlink(Hyperlink h) {
        this.shoppingCartHyperlink = h;
    }
    private Hyperlink manageHyperlink = new Hyperlink();

    public Hyperlink getManageHyperlink() {
        return manageHyperlink;
    }

    public void setManageHyperlink(Hyperlink h) {
        this.manageHyperlink = h;
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
