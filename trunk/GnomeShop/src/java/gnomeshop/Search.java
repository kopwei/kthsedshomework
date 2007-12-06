/*
 * Search.java
 *
 * Created on Dec 4, 2007, 11:17:37 PM
 */
 
package gnomeshop;

import com.sun.rave.web.ui.appbase.AbstractFragmentBean;
import com.sun.webui.jsf.component.Button;
import com.sun.webui.jsf.component.DropDown;
import com.sun.webui.jsf.component.Label;
import com.sun.webui.jsf.component.TextField;
import com.sun.webui.jsf.model.SingleSelectOptionsList;
import javax.faces.FacesException;
import javax.faces.component.html.HtmlCommandButton;
import javax.faces.component.html.HtmlOutputFormat;
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
public class Search extends AbstractFragmentBean {
    // <editor-fold defaultstate="collapsed" desc="Managed Component Definition">

    /**
     * <p>Automatically managed component initialization. <strong>WARNING:</strong>
     * This method is automatically generated, so any user-specified code inserted
     * here is subject to being replaced.</p>
     */
    private void _init() throws Exception {
    }
        private DropDown categoryDropDown = new DropDown();

        public DropDown getCategoryDropDown() {
                return categoryDropDown;
        }

        public void setCategoryDropDown(DropDown dd) {
                this.categoryDropDown = dd;
        }
        private SingleSelectOptionsList categoryDropDownDefaultOptions = new SingleSelectOptionsList();

        public SingleSelectOptionsList getCategoryDropDownDefaultOptions() {
                return categoryDropDownDefaultOptions;
        }

        public void setCategoryDropDownDefaultOptions(SingleSelectOptionsList ssol) {
                this.categoryDropDownDefaultOptions = ssol;
        }
        private Label categoryLabel = new Label();

        public Label getCategoryLabel() {
                return categoryLabel;
        }

        public void setCategoryLabel(Label l) {
                this.categoryLabel = l;
        }
        private Label keywordLabel = new Label();

        public Label getKeywordLabel() {
                return keywordLabel;
        }

        public void setKeywordLabel(Label l) {
                this.keywordLabel = l;
        }
        private TextField keywordField = new TextField();

        public TextField getKeywordField() {
                return keywordField;
        }

        public void setKeywordField(TextField tf) {
                this.keywordField = tf;
        }
        private HtmlCommandButton searchButton = new HtmlCommandButton();

        public HtmlCommandButton getSearchButton() {
                return searchButton;
        }

        public void setSearchButton(HtmlCommandButton hcb) {
                this.searchButton = hcb;
        }
    // </editor-fold>

    public Search() {
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

    public String searchButton_action() {
        // TODO: Process the button click action. Return value is a navigation
        // case name where null will return to the same page.
        return null;
    }

}
