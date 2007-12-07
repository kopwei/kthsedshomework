/*
 * MemberDetail.java
 *
 * Created on Dec 6, 2007, 10:27:13 PM
 */
 
package gnomeshop;

import com.sun.rave.web.ui.appbase.AbstractPageBean;
import com.sun.webui.jsf.component.Body;
import com.sun.webui.jsf.component.Checkbox;
import com.sun.webui.jsf.component.Form;
import com.sun.webui.jsf.component.Head;
import com.sun.webui.jsf.component.Html;
import com.sun.webui.jsf.component.Hyperlink;
import com.sun.webui.jsf.component.Label;
import com.sun.webui.jsf.component.Link;
import com.sun.webui.jsf.component.Page;
import com.sun.webui.jsf.component.StaticText;
import gnomeshop.items.MemberBean;
import javax.faces.FacesException;
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
public class MemberDetail extends AbstractPageBean {
    private MemberBean member = null;
    private StaticText userNameText = new StaticText();
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

    // </editor-fold>
    private Label label1 = new Label();

    public Label getLabel1() {
        return label1;
    }

    public void setLabel1(Label l) {
        this.label1 = l;
    }
    private Label label2 = new Label();

    public Label getLabel2() {
        return label2;
    }

    public void setLabel2(Label l) {
        this.label2 = l;
    }
    private Label label3 = new Label();

    public Label getLabel3() {
        return label3;
    }

    public void setLabel3(Label l) {
        this.label3 = l;
    }
    private StaticText lastNameText = new StaticText();

    public StaticText getLastNameText() {
        return lastNameText;
    }

    public void setLastNameText(StaticText st) {
        this.lastNameText = st;
    }
    private Label label4 = new Label();

    public Label getLabel4() {
        return label4;
    }

    public void setLabel4(Label l) {
        this.label4 = l;
    }
    private StaticText emailText = new StaticText();

    public StaticText getEmailText() {
        return emailText;
    }

    public void setEmailText(StaticText st) {
        this.emailText = st;
    }
    private Label label5 = new Label();

    public Label getLabel5() {
        return label5;
    }

    public void setLabel5(Label l) {
        this.label5 = l;
    }
    private StaticText telephoneText = new StaticText();

    public StaticText getTelephoneText() {
        return telephoneText;
    }

    public void setTelephoneText(StaticText st) {
        this.telephoneText = st;
    }
    private Label label6 = new Label();

    public Label getLabel6() {
        return label6;
    }

    public void setLabel6(Label l) {
        this.label6 = l;
    }
    private Checkbox blockCheckbox = new Checkbox();

    public Checkbox getBlockCheckbox() {
        return blockCheckbox;
    }

    public void setBlockCheckbox(Checkbox c) {
        this.blockCheckbox = c;
    }

     public StaticText getUserNameText() {
        return userNameText;
    }

    public void setUserNameText(StaticText st) {
        this.userNameText = st;
    }
    private StaticText firstNameText = new StaticText();

    public StaticText getFirstNameText() {
        return firstNameText;
    }

    public void setFirstNameText(StaticText st) {
        this.firstNameText = st;
    }
    private Hyperlink blockHyperlink = new Hyperlink();

    public Hyperlink getBlockHyperlink() {
        return blockHyperlink;
    }

    public void setBlockHyperlink(Hyperlink h) {
        this.blockHyperlink = h;
    }
    private Hyperlink unblockHyperlink = new Hyperlink();

    public Hyperlink getUnblockHyperlink() {
        return unblockHyperlink;
    }

    public void setUnblockHyperlink(Hyperlink h) {
        this.unblockHyperlink = h;
    }

    /**
     * <p>Construct a new Page bean instance.</p>
     */
    public MemberDetail() {
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
         // Get DatabaseUtil instance
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
        String memberId = facesContext.getExternalContext().getRequestParameterMap().get("memberid");
        DatabaseUtil dbUtil = (DatabaseUtil) servletContext.getAttribute("DATABASE_UTIL");
        if (null != dbUtil) {            
            if (null != memberId) {
                member = dbUtil.getMemberById(memberId);
                String blockString = facesContext.getExternalContext().getRequestParameterMap().get("block");
                if (null != blockString) {
                    setMemberBlock(Boolean.valueOf(blockString));
                }
            }
        }
        
        // <editor-fold defaultstate="collapsed" desc="Managed Component Initialization">
        // Initialize automatically managed components
        // *Note* - this logic should NOT be modified
        try {
            _init();
        } catch (Exception e) {
            log("MemberDetail Initialization Failure", e);
            throw e instanceof FacesException ? (FacesException) e: new FacesException(e);
        }
        
        // </editor-fold>
        // Perform application initialization that must complete
        // *after* managed components are initialized
        // TODO - add your own initialization code here
         if (null != dbUtil) {
            member = dbUtil.getMemberById(memberId);
         }
        updateBlockState();
    }
    
    private void updateBlockState() {
        if (null == member) {
            return;
        }
        blockHyperlink.setVisible(false);
        unblockHyperlink.setVisible(false);
        blockCheckbox.setSelected(member.getBlocked());
        if (member.getBlocked()) {
            unblockHyperlink.setVisible(true);
        } else {
            blockHyperlink.setVisible(true);
        }
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
     * This method is used to get the current member information
     * @return The current member object
     */
    public MemberBean getMember() {
        return member;
    }

    private void setMemberBlock(boolean block) {
        if (null == member) {
            return;
        }
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
        DatabaseUtil dbUtil = (DatabaseUtil) servletContext.getAttribute("DATABASE_UTIL");
        if (null != dbUtil) {
            dbUtil.blockMember(member.getMemberId(), block);
        }
    }
}

