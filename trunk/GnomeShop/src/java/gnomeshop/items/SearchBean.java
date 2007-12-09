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
/**
 * This class represent a search action entity
 * @author Kop
 */
public class SearchBean {
	private String searchKey;

	/**
	 * This method is used to set the search key of the current object
	 * @param searchKey the searchKey to set
	 */
	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}

	/**
	 *  This method is used to get the search key of the current object
	 * @return the searchKey
	 */
	public String getSearchKey() {
		return searchKey;
	}
	
	/**
	 * This method is used to get the products according to the search key 
	 * @return The collection of matched products
	 */
	public Collection<ProductBean> getSearchResult() {
		// Get DatabaseUtil instance
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
		DatabaseUtil dbUtil = (DatabaseUtil) servletContext.getAttribute("DATABASE_UTIL");
		if (null != dbUtil) {
			return dbUtil.searchProducts(searchKey);
		}
		else {
			return null;
		}
	}

}
