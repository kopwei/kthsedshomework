/*
 * AddProduct.java
 *
 * Created on Dec 10, 2007, 7:19:13 PM
 */
 
package gnomeshop;

import com.sun.rave.web.ui.appbase.AbstractPageBean;
import com.sun.webui.jsf.component.Body;
import com.sun.webui.jsf.component.Form;
import com.sun.webui.jsf.component.Head;
import com.sun.webui.jsf.component.Html;
import com.sun.webui.jsf.component.Link;
import com.sun.webui.jsf.component.Page;
import com.sun.webui.jsf.component.TextArea;
import com.sun.webui.jsf.component.TextField;
import gnomeshop.items.ProductBean;
import java.io.IOException;
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
public class AddProduct extends AbstractPageBean {
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
    private TextField nameField = new TextField();

    public TextField getNameField() {
        return nameField;
    }

    public void setNameField(TextField tf) {
        this.nameField = tf;
    }
    private TextField priceField = new TextField();

    public TextField getPriceField() {
        return priceField;
    }

    public void setPriceField(TextField tf) {
        this.priceField = tf;
    }
    private TextArea descriptionArea = new TextArea();

    public TextArea getDescriptionArea() {
        return descriptionArea;
    }

    public void setDescriptionArea(TextArea ta) {
        this.descriptionArea = ta;
    }
    private TextField quantityField = new TextField();

    public TextField getQuantityField() {
        return quantityField;
    }

    public void setQuantityField(TextField tf) {
        this.quantityField = tf;
    }
    private HtmlCommandButton addButton = new HtmlCommandButton();

    public HtmlCommandButton getAddButton() {
        return addButton;
    }

    public void setAddButton(HtmlCommandButton hcb) {
        this.addButton = hcb;
    }

    // </editor-fold>

    /**
     * <p>Construct a new Page bean instance.</p>
     */
    public AddProduct() {
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
         try {
            LoginManager loginMgr = (LoginManager) getBean("LoginManager");
            if (null != loginMgr) {
                if (!loginMgr.isAdmin()) {
                    FacesContext facesContext = FacesContext.getCurrentInstance();
                    facesContext.getExternalContext().redirect("Login.jsp");
                }
            }
        } catch (IOException ex) {
            //Logger.getLogger(AdminPage.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println(ex.getMessage());
        }
        // <editor-fold defaultstate="collapsed" desc="Managed Component Initialization">
        // Initialize automatically managed components
        // *Note* - this logic should NOT be modified
        try {
            _init();
        } catch (Exception e) {
            log("AddProduct Initialization Failure", e);
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

    /**
     * <p>Return a reference to the scoped data bean.</p>
     *
     * @return reference to the scoped data bean
     */
    protected ApplicationBean getApplicationBean() {
        return (ApplicationBean) getBean("ApplicationBean");
    }

    /**
     * This method will be invoked if user clicks the add button and it will add the corresponding item into
     * database
     * @return The action String
     */
    public String addButton_action() {
        // TODO: Process the button click action. Return value is a navigation
        // case name where null will return to the same page.
        if (nameField.getText() == null || priceField.getText() == null || quantityField.getText() == null) {
            return null;
        }
        try {
            String name = nameField.getText().toString();
            float price = Float.parseFloat(priceField.getText().toString());
            String desc = descriptionArea.getText().toString();
            int quantity = Integer.parseInt(quantityField.getText().toString());
            ProductBean product = new ProductBean(name, price, desc, quantity);
            FacesContext facesContext = FacesContext.getCurrentInstance();
            ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
            DatabaseUtil dbUtil = (DatabaseUtil) servletContext.getAttribute("DATABASE_UTIL");
            if (null != dbUtil) {
                dbUtil.insertProduct(product);
            }
            return "AddProductSucceed";
        } catch (Exception e) {
            return "AddingFailed";
        }
    }    
}

