/*
 * Register.java
 *
 * Created on Dec 4, 2007, 11:01:53 PM
 */
 
package gnomeshop;

import com.sun.rave.web.ui.appbase.AbstractPageBean;
import com.sun.webui.jsf.component.Body;
import com.sun.webui.jsf.component.Form;
import com.sun.webui.jsf.component.Head;
import com.sun.webui.jsf.component.Html;
import com.sun.webui.jsf.component.Label;
import com.sun.webui.jsf.component.Link;
import com.sun.webui.jsf.component.Page;
import com.sun.webui.jsf.component.PasswordField;
import com.sun.webui.jsf.component.TextField;
import gnomeshop.items.MemberBean;
import javax.faces.FacesException;
import javax.faces.component.html.HtmlCommandButton;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

/**
 * <p>Page bean that corresponds to a similarly named JSP page.  This
 * class contains component definitions (and initialization code) for
 * all components that you have defined on this page, as well as
 * lifecycle methods and event handlers where you may add behavior
 * to respond to incoming events.</p>
 *
 * @author Kop
 */
public class Register extends AbstractPageBean {
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
        private Label userNameLabel = new Label();

        public Label getUserNameLabel() {
                return userNameLabel;
        }

        public void setUserNameLabel(Label l) {
                this.userNameLabel = l;
        }
        private TextField userNameField = new TextField();

        public TextField getUserNameField() {
                return userNameField;
        }

        public void setUserNameField(TextField tf) {
                this.userNameField = tf;
        }
        private Label passwordLabel = new Label();

        public Label getPasswordLabel() {
                return passwordLabel;
        }

        public void setPasswordLabel(Label l) {
                this.passwordLabel = l;
        }
        private PasswordField passwordField = new PasswordField();

        public PasswordField getPasswordField() {
                return passwordField;
        }

        public void setPasswordField(PasswordField pf) {
                this.passwordField = pf;
        }
        private Label firstNameLabel = new Label();

        public Label getFirstNameLabel() {
                return firstNameLabel;
        }

        public void setFirstNameLabel(Label l) {
                this.firstNameLabel = l;
        }
        private TextField firstNameField = new TextField();

        public TextField getFirstNameField() {
                return firstNameField;
        }

        public void setFirstNameField(TextField tf) {
                this.firstNameField = tf;
        }
        private Label lastNameLabel = new Label();

        public Label getLastNameLabel() {
                return lastNameLabel;
        }

        public void setLastNameLabel(Label l) {
                this.lastNameLabel = l;
        }
        private TextField lastNameField = new TextField();

        public TextField getLastNameField() {
                return lastNameField;
        }

        public void setLastNameField(TextField tf) {
                this.lastNameField = tf;
        }
        private Label emailLabel = new Label();

        public Label getEmailLabel() {
                return emailLabel;
        }

        public void setEmailLabel(Label l) {
                this.emailLabel = l;
        }
        private TextField emailField = new TextField();

        public TextField getEmailField() {
                return emailField;
        }

        public void setEmailField(TextField tf) {
                this.emailField = tf;
        }
        private Label phoneLabel = new Label();

        public Label getPhoneLabel() {
                return phoneLabel;
        }

        public void setPhoneLabel(Label l) {
                this.phoneLabel = l;
        }
        private TextField phoneField = new TextField();

        public TextField getPhoneField() {
                return phoneField;
        }

        public void setPhoneField(TextField tf) {
                this.phoneField = tf;
        }
        private HtmlCommandButton submitButton = new HtmlCommandButton();

        public HtmlCommandButton getSubmitButton() {
                return submitButton;
        }

        public void setSubmitButton(HtmlCommandButton hcb) {
                this.submitButton = hcb;
        }

    // </editor-fold>

    /**
     * <p>Construct a new Page bean instance.</p>
     */
    public Register() {
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
        
        // <editor-fold defaultstate="collapsed" desc="Managed Component Initialization">
        // Initialize automatically managed components
        // *Note* - this logic should NOT be modified
        try {
            _init();
        } catch (Exception e) {
            log("Register Initialization Failure", e);
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

        public String submitButton_action() {
                // TODO: Process the button click action. Return value is a navigation
                // Get DatabaseUtil instance
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
		DatabaseUtil dbUtil = (DatabaseUtil) servletContext.getAttribute("DATABASE_UTIL");
		if (null != dbUtil) {
                    String userName = userNameField.getText().toString();
                    String passWord = passwordField.getPassword().toString();
                    String firstName = firstNameField.getText().toString();
                    String lastName = lastNameField.getText().toString();
                    String email = emailField.getText().toString();
                    String telephone = phoneField.getText().toString();
                    boolean bRes = dbUtil.insertMember(new MemberBean(userName, passWord, firstName, lastName, telephone, email));
                    if (bRes) {
                        return ("Register Success");
                    } 
                }
                return ("Register Failed");
        }
    
}

