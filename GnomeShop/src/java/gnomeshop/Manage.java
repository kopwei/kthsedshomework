/*
 * Manage.java
 *
 * Created on Dec 6, 2007, 8:31:27 PM
 */
package gnomeshop;

import com.sun.rave.web.ui.appbase.AbstractPageBean;
import com.sun.webui.jsf.component.Body;
import com.sun.webui.jsf.component.Form;
import com.sun.webui.jsf.component.Head;
import com.sun.webui.jsf.component.Html;
import com.sun.webui.jsf.component.Hyperlink;
import com.sun.webui.jsf.component.Link;
import com.sun.webui.jsf.component.Page;
import com.sun.webui.jsf.component.StaticText;
import com.sun.webui.jsf.component.Table;
import com.sun.webui.jsf.component.TableColumn;
import com.sun.webui.jsf.component.TableRowGroup;
import com.sun.webui.jsf.component.TextField;
import gnomeshop.items.ProductBean;
import java.io.IOException;
import java.util.ArrayList;
import javax.faces.FacesException;
import javax.faces.component.html.HtmlOutputLink;
import javax.faces.component.html.HtmlOutputText;
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
public class Manage extends AbstractPageBean {
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
    
    private StaticText staticText1 = new StaticText();

    public StaticText getStaticText1() {
        return staticText1;
    }

    public void setStaticText1(StaticText st) {
        this.staticText1 = st;
    }
    private TextField quantityField = new TextField();

    public TextField getQuantityField() {
        return quantityField;
    }

    public void setQuantityField(TextField tf) {
        this.quantityField = tf;
    }
    private Table table1 = new Table();

    public Table getTable1() {
        return table1;
    }

    public void setTable1(Table t) {
        this.table1 = t;
    }
    private TableRowGroup tableRowGroup1 = new TableRowGroup();

    public TableRowGroup getTableRowGroup1() {
        return tableRowGroup1;
    }

    public void setTableRowGroup1(TableRowGroup trg) {
        this.tableRowGroup1 = trg;
    }
    private TableColumn tableColumn6 = new TableColumn();

    public TableColumn getTableColumn6() {
        return tableColumn6;
    }

    public void setTableColumn6(TableColumn tc) {
        this.tableColumn6 = tc;
    }
    private StaticText staticText7 = new StaticText();

    public StaticText getStaticText7() {
        return staticText7;
    }

    public void setStaticText7(StaticText st) {
        this.staticText7 = st;
    }
    private TableColumn tableColumn8 = new TableColumn();

    public TableColumn getTableColumn8() {
        return tableColumn8;
    }

    public void setTableColumn8(TableColumn tc) {
        this.tableColumn8 = tc;
    }
    private StaticText staticText9 = new StaticText();

    public StaticText getStaticText9() {
        return staticText9;
    }

    public void setStaticText9(StaticText st) {
        this.staticText9 = st;
    }
    private TableColumn tableColumn9 = new TableColumn();

    public TableColumn getTableColumn9() {
        return tableColumn9;
    }

    public void setTableColumn9(TableColumn tc) {
        this.tableColumn9 = tc;
    }
    private StaticText staticText10 = new StaticText();

    public StaticText getStaticText10() {
        return staticText10;
    }

    public void setStaticText10(StaticText st) {
        this.staticText10 = st;
    }
    private TableColumn removeColumn = new TableColumn();

    public TableColumn getRemoveColumn() {
        return removeColumn;
    }

    public void setRemoveColumn(TableColumn tc) {
        this.removeColumn = tc;
    }
    private StaticText staticText2 = new StaticText();

    public StaticText getStaticText2() {
        return staticText2;
    }

    public void setStaticText2(StaticText st) {
        this.staticText2 = st;
    }

    private Hyperlink removeHyperlink = new Hyperlink();

    public Hyperlink getRemoveHyperlink() {
        return removeHyperlink;
    }

    public void setRemoveHyperlink(Hyperlink h) {
        this.removeHyperlink = h;
    }
    
    private HtmlOutputLink addProductHyperlink = new HtmlOutputLink();

    public HtmlOutputLink getAddProductHyperlink() {
        return addProductHyperlink;
    }

    public void setAddProductHyperlink(HtmlOutputLink hol) {
        this.addProductHyperlink = hol;
    }
    private HtmlOutputText addProductHyperlinkText = new HtmlOutputText();

    public HtmlOutputText getAddProductHyperlinkText() {
        return addProductHyperlinkText;
    }

    public void setAddProductHyperlinkText(HtmlOutputText hot) {
        this.addProductHyperlinkText = hot;
    }
    
     // </editor-fold>
    
    private ArrayList<ProductBean> products = new ArrayList<ProductBean>();
    
    /**
     * <p>Construct a new Page bean instance.</p>
     */
    public Manage() {
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
        // Process the argument
        processParameter();

        // Set the data array
        FacesContext fc = FacesContext.getCurrentInstance();
        ServletContext servletContext = (ServletContext) fc.getExternalContext().getContext();
        DatabaseUtil databaseUtil = (DatabaseUtil) servletContext.getAttribute("DATABASE_UTIL");
        if (null != databaseUtil) {
            setProducts(databaseUtil.getAllProducts());
        }

        // <editor-fold defaultstate="collapsed" desc="Managed Component Initialization">
        // Initialize automatically managed components
        // *Note* - this logic should NOT be modified
        try {
            _init();
        } catch (Exception e) {
            log("ManageGnomePage Initialization Failure", e);
            throw e instanceof FacesException ? (FacesException) e : new FacesException(e);
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
     * This method is used to handle the related arguments in URL
     */
    private void processParameter() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
        String productId = facesContext.getExternalContext().getRequestParameterMap().get("productid");
        String removeStr = facesContext.getExternalContext().getRequestParameterMap().get("remove");
        if (null != removeStr) {
            boolean remove = Boolean.valueOf(removeStr);
            DatabaseUtil dbUtil = (DatabaseUtil) servletContext.getAttribute("DATABASE_UTIL");
            if (null != dbUtil && remove) {
                if (null != productId) {
                    dbUtil.removeProduct(productId);
                }
            }
        }
    }

    /**
     * This method is used to get all the products info
     * @return The list of products
     */
    public ArrayList<ProductBean> getProducts() {
        return products;
    }

    /**
     * This method is used to set all the products info
     * @param products The list of products
     */
    public void setProducts(ArrayList<ProductBean> products) {
        this.products = products;
    }
}

