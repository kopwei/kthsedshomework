/*
 * GnomeDetail.java
 *
 * Created on 2007-12-6, 20:11:45
 */
 
package gnomeshop;

import com.sun.rave.web.ui.appbase.AbstractPageBean;
import com.sun.webui.jsf.component.Body;
import com.sun.webui.jsf.component.Button;
import com.sun.webui.jsf.component.Form;
import com.sun.webui.jsf.component.Head;
import com.sun.webui.jsf.component.Html;
import com.sun.webui.jsf.component.Link;
import com.sun.webui.jsf.component.Page;
import javax.faces.FacesException;
import gnomeshop.ApplicationBean;
import gnomeshop.SessionBean;
import gnomeshop.RequestBean;
import gnomeshop.items.ProductBean;
import gnomeshop.items.ShoppingCartBean;
import gnomeshop.items.ShoppingItemBean;
import javax.faces.component.html.HtmlInputText;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

/**
 * <p>Page bean that corresponds to a similarly named JSP page.  This
 * class contains component definitions (and initialization code) for
 * all components that you have defined on this page, as well as
 * lifecycle methods and event handlers where you may add behavior
 * to respond to incoming events.</p>
 *
 * @author Ricky
 */
public class GnomeDetail extends AbstractPageBean {
    private ProductBean productBean = null;
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
    private Button addIntoCartButton = new Button();

    public Button getAddIntoCartButton() {
        return addIntoCartButton;
    }

    public void setAddIntoCartButton(Button b) {
        this.addIntoCartButton = b;
    }

    private HtmlInputText numberTextField = new HtmlInputText();

    public HtmlInputText getNumberTextField() {
        return numberTextField;
    }

    public void setNumberTextField(HtmlInputText hit) {
        this.numberTextField = hit;
    }

    // </editor-fold>

    /**
     * <p>Construct a new Page bean instance.</p>
     */
    public GnomeDetail() {
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
        FacesContext fc = FacesContext.getCurrentInstance();
        String productID = fc.getExternalContext().getRequestParameterMap().get("productid");
        ServletContext servletContext = (ServletContext) fc.getExternalContext().getContext();
        DatabaseUtil databaseUtil = (DatabaseUtil) servletContext.getAttribute("DATABASE_UTIL");
        if (null != databaseUtil) {            
            if (null != productID) {
                setProductBean(databaseUtil.getProductDetails(productID));
                SessionBean sessionBean = (SessionBean) getBean("SessionBean");
                sessionBean.setCurrentProductBean(productBean);
            }
        }
        
        // <editor-fold defaultstate="collapsed" desc="Managed Component Initialization">
        // Initialize automatically managed components
        // *Note* - this logic should NOT be modified
        try {
            _init();
        } catch (Exception e) {
            log("GnomeDetail Initialization Failure", e);
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
    protected SessionBean getSessionBean() {
        return (SessionBean) getBean("SessionBean");
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
     * This method will be invoked while user clicks the addIntoCart button, and will add the current gnome into
     * shopping cart
     * @return The action String
     */
    public String addIntoCartButton_action() {
        // TODO: Process the action. Return value is a navigation
        SessionBean sessionBean = (SessionBean) getBean("SessionBean");
        productBean = sessionBean.getCurrentProductBean();

        // Check if the member is logged in or not
        LoginManager manager = (LoginManager)getBean("LoginManager");
        if (null != manager) {
            if (!manager.getLoggedIn()) {
                return "Login";
            }
        }
        // case name where null will return to the same page.
        boolean result = false;
        if (null != getProductBean()) {
            int number = Integer.parseInt(numberTextField.getValue().toString());
            if (productBean.getQuantity() >= number && number > 0) {
                ShoppingItemBean shoppingItemBean = new ShoppingItemBean(productBean.getId(), productBean.getName(), productBean.getPrice(), number);
                ShoppingCartBean shoppingCartBean = (ShoppingCartBean) getBean("ShoppingCartBean");
                result = shoppingCartBean.addShoppingItem(shoppingItemBean);
            }
            else {
                result = false;
            }
        }
        String addIntoCartActionResult = null;
        if (result) {
            addIntoCartActionResult = "AddingSuccess";
        }
        else {
            addIntoCartActionResult = "AddingFailure";
        }
        return addIntoCartActionResult;
    }

    /**
     * This method is used to get the product of the current page
     * @return The product object
     */
    public ProductBean getProductBean() {
        return productBean;
    }

    /**
     * This method is used to get the product of the current page
     * @param productBean The product object
     */
    public void setProductBean(ProductBean productBean) {
        this.productBean = productBean;
    }

}

