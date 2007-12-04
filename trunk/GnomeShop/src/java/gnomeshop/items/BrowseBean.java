/**
 * 
 */
package gnomeshop.items;

import gnomeshop.DatabaseUtil;
import java.util.Collection;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

/**
 * @author Kop
 *
 */
public class BrowseBean {
	private String categoryId;
	
	/**
	 * default constructor used to initialize the members to its default state
	 */
	public BrowseBean() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		if (null != facesContext) {
			setCategoryId(facesContext.getExternalContext().getRequestParameterMap().get("categoryId"));
		}
	}

	/**
	 * This method is used to get the category id of the browse object
	 * @param categoryId the categoryId to set
	 */
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	/**
	 * This method is used to get the category id of the browse object
	 * @return the categoryId
	 */
	public String getCategoryId() {
		return categoryId;
	}

	/**
	 * This method is used to get the products according to the category id
	 * @return The collection of matched products
	 */
	public Collection<ProductBean> getResult() {
		// Get DatabaseUtil instance
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
		DatabaseUtil dbUtil = (DatabaseUtil) servletContext.getAttribute("DATABASE_UTIL");
		if (null != dbUtil) {
			return dbUtil.getProductByCategory(categoryId);
		}
		else {
			return null;
		}
	}
}
