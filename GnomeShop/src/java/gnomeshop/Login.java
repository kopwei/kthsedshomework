/*
 * Login.java
 *
 * Created on Dec 4, 2007, 11:15:18 PM
 */
 
package gnomeshop;

import com.sun.rave.web.ui.appbase.AbstractPageBean;
import com.sun.webui.jsf.component.Body;
import com.sun.webui.jsf.component.Button;
import com.sun.webui.jsf.component.Form;
import com.sun.webui.jsf.component.Head;
import com.sun.webui.jsf.component.Html;
import com.sun.webui.jsf.component.Label;
import com.sun.webui.jsf.component.Link;
import com.sun.webui.jsf.component.Page;
import com.sun.webui.jsf.component.PasswordField;
import com.sun.webui.jsf.component.TextField;
import javax.faces.FacesException;
import javax.faces.component.html.HtmlCommandButton;

/**
 * <p>Page bean that corresponds to a similarly named JSP page.  This
 * class contains component definitions (and initialization code) for
 * all components that you have defined on this page, as well as
 * lifecycle methods and event handlers where you may add behavior
 * to respond to incoming events.</p>
 *
 * @author Kop
 */
public class Login extends AbstractPageBean {
    // <editor-fold defaultstate="collapsed" desc="Managed Component Definition">

    /**
     * <p>Automatically managed component initialization.  <strong>WARNING:</strong>
     * This method is automatically generated, so any user-specified code inserted
     * here is subject to being replaced.</p>
     */
    private void _init() throws Exception {
    }

    private Page page1 = new Page();
    
    public Page getPage1() {
        return page1;
    }
    
    public void setPage1(Page p) {
        this.page1 = p;
    }
    
    private Html html1 = new Html();
    
    public Html getHtml1() {
        return html1;
    }
    
    public void setHtml1(Html h) {
        this.html1 = h;
    }
    
    private Head head1 = new Head();
    
    public Head getHead1() {
        return head1;
    }
    
    public void setHead1(Head h) {
        this.head1 = h;
    }
    
    private Link link1 = new Link();
    
    public Link getLink1() {
        return link1;
    }
    
    public void setLink1(Link l) {
        this.link1 = l;
    }
    
    private Body body1 = new Body();
    
    public Body getBody1() {
        return body1;
    }
    
    public void setBody1(Body b) {
        this.body1 = b;
    }
    
    private Form form1 = new Form();
    
    public Form getForm1() {
        return form1;
    }
    
    public void setForm1(Form f) {
        this.form1 = f;
    }
        private TextField userNameField = new TextField();

        public TextField getUserNameField() {
                return userNameField;
        }

        public void setUserNameField(TextField tf) {
                this.userNameField = tf;
        }
        private PasswordField passwordField = new PasswordField();

        public PasswordField getPasswordField() {
                return passwordField;
        }

        public void setPasswordField(PasswordField pf) {
                this.passwordField = pf;
        }
        private HtmlCommandButton loginButton = new HtmlCommandButton();

        public HtmlCommandButton getLoginButton() {
                return loginButton;
        }

        public void setLoginButton(HtmlCommandButton hcb) {
                this.loginButton = hcb;
        }
    private TextField userNameField1 = new TextField();

    public TextField getUserNameField1() {
        return userNameField1;
    }

    public void setUserNameField1(TextField tf) {
        this.userNameField1 = tf;
    }
    private PasswordField passwordField2 = new PasswordField();

    public PasswordField getPasswordField2() {
        return passwordField2;
    }

    public void setPasswordField2(PasswordField pf) {
        this.passwordField2 = pf;
    }

    // </editor-fold>

    private LoginManager loginMgr;
    private Button button1 = new Button();

    public Button getButton1() {
        return button1;
    }

    public void setButton1(Button b) {
        this.button1 = b;
    }
    private HtmlCommandButton logoutButton = new HtmlCommandButton();

    public HtmlCommandButton getLogoutButton() {
        return logoutButton;
    }

    public void setLogoutButton(HtmlCommandButton hcb) {
        this.logoutButton = hcb;
    }
    private Label userNameLabel = new Label();

    public Label getUserNameLabel() {
        return userNameLabel;
    }

    public void setUserNameLabel(Label l) {
        this.userNameLabel = l;
    }
    /**
     * <p>Construct a new Page bean instance.</p>
     */
    public Login() {
        
    }

    /**
     * <p>Callback method that is called whenever a page is navigated to,
     * either directly via a URL, or indirectly via page navigation.
     * Customize this method to acquire resources that will be needed
     * for event handlers and lifecycle methods, whether or not this
     * page is performing post back processing.</p>
     * 
     * <p>Note that, if the current request is a postback, the property
     * values of the components do <strong>not</strong> represent any
     * values submitted with this request.  Instead, they represent the
     * property values that were saved for this view when it was rendered.</p>
     */
    @Override
    public void init() {
        // Perform initializations inherited from our superclass
        super.init();
        // Perform application initialization that must complete
        // *before* managed components are initialized
        // TODO - add your own initialiation code here
        loginMgr = (LoginManager)getBean("LoginManager");
        if (loginMgr != null) {
            if(loginMgr.getLoggedIn()) {
                this.userNameLabel.setText(loginMgr.getUserName());
            }
        }
        // <editor-fold defaultstate="collapsed" desc="Managed Component Initialization">
        // Initialize automatically managed components
        // *Note* - this logic should NOT be modified
        try {
            _init();
        } catch (Exception e) {
            log("Login Initialization Failure", e);
            throw e instanceof FacesException ? (FacesException) e: new FacesException(e);
        }
        
        // </editor-fold>
        // Perform application initialization that must complete
        // *after* managed components are initialized
        // TODO - add your own initialization code here
        
    }

    /**
     * <p>Callback method that is called after the component tree has been
     * restored, but before any event processing takes place.  This method
     * will <strong>only</strong> be called on a postback request that
     * is processing a form submit.  Customize this method to allocate
     * resources that will be required in your event handlers.</p>
     */
    @Override
    public void preprocess() {
    }

    /**
     * <p>Callback method that is called just before rendering takes place.
     * This method will <strong>only</strong> be called for the page that
     * will actually be rendered (and not, for example, on a page that
     * handled a postback and then navigated to a different page).  Customize
     * this method to allocate resources that will be required for rendering
     * this page.</p>
     */
    @Override
    public void prerender() {
    }

    /**
     * <p>Callback method that is called after rendering is completed for
     * this request, if <code>init()</code> was called (regardless of whether
     * or not this was the page that was actually rendered).  Customize this
     * method to release resources acquired in the <code>init()</code>,
     * <code>preprocess()</code>, or <code>prerender()</code> methods (or
     * acquired during execution of an event handler).</p>
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
        
        public LoginManager getLoginMgr() {
            return loginMgr;
        }

        public String loginButton_action() {
                // TODO: Process the action. Return value is a navigation
                // case name where null will return to the same page.
            
            loginMgr.setUserName(getUserNameField().getText().toString());
            loginMgr.setPassword(getPasswordField().getPassword().toString());
            return loginMgr.loginUser();            
        }
        
        public String logoutButton_action() {
                // TODO: Process the action. Return value is a navigation
                // case name where null will return to the same page.
            return loginMgr.logoutUser();            
        }
}

