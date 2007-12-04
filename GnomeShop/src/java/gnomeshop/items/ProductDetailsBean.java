/**
 * 
 */
package gnomeshop.items;

import gnomeshop.DatabaseUtil;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

/**
 * @author Kop
 *
 */

/**
 * This class represents a product detail entity, which includes detailed information of a product
 */
public class ProductDetailsBean extends ProductBean {
	@SuppressWarnings("unused")
	private String imageUrl;
	private String imageDir;
	
	/**
	 * Default constructor used to initialize all the members to the default state
	 */
	public ProductDetailsBean() {
		// Get the faces context
		FacesContext facesContext = FacesContext.getCurrentInstance();
		if (null != facesContext) {
			String productId = facesContext.getExternalContext().getRequestParameterMap().get("productId");
			// Get DatabaseUtil instance
			ServletContext servletContext = (ServletContext)facesContext.getExternalContext().getContext();
			DatabaseUtil dbUtil = (DatabaseUtil)servletContext.getAttribute("DATABASE_UTIL");
			ProductBean product = dbUtil.getProductDetails(productId);
			// Initialize the object information
			setId(productId);
			setName(product.getName());
			setDescription(product.getDescription());
			setPrice(product.getPrice());
		}
	}
	
	/**
	 * This method is used to get the image directory path of the web site
	 * @return The image directory of the web site
	 */
	public String getImageDir() {
		return imageDir;
	}
	
	/**
	 * This method is used to set the image directory of the current object
	 * @param imageDir The image directory path of the web site
	 */
	public void setImageDir(String imageDir) {
		this.imageDir = imageDir;
	}
	
	/**
	 * This method will give out the image URL string   
	 * @return The image URL
	 */
	public String getImageUrl() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		@SuppressWarnings("unused")
		String contextPath = facesContext.getExternalContext().getRequestContextPath();
		return getImageDir() + getId() + ".gif";
	}
	
	/**
	 * This method is used to set the image URL of the current object
	 * @param imageUrl
	 */
	public void setImageUrl(String imageUrl) {
		this.imageDir = imageUrl;
	}

}
