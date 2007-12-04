/**
 * 
 */
package gnomeshop.items;

import gnomeshop.DatabaseUtil;
import java.util.ArrayList;
import java.util.Iterator;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

/**
 * @author Kop
 *
 */
public class MenuBean {
	private String menu;
	private long lastUpdate;
	private String browsePage = "browse.jsp";
	
	private int updateInterval = 1; // in minutes

	/**
	 * This method is used to set the browse page name
	 * @param browsePage the browsePage to set
	 */
	public void setBrowsePage(String browsePage) {
		this.browsePage = browsePage;
	}

	/**
	 * This method is used to get the browse page name
	 * @return the browsePage
	 */
	public String getBrowsePage() {
		return browsePage;
	}

	/**
	 * This method is used to set the update interval in minutes
	 * @param updateInterval the updateInterval to set
	 */
	public void setUpdateInterval(int updateInterval) {
		this.updateInterval = updateInterval;
	}

	/**
	 * This method is used to get the update interval in minutes
	 * @return the updateInterval
	 */
	public int getUpdateInterval() {
		return updateInterval;
	}

	/**
	 * This method is used to get the current menu
	 * @return the menu
	 */
	public String getMenu() {
		// If the menu is expired then update it
		long now = System.currentTimeMillis();
		if (now > lastUpdate + updateInterval * 60 * 1000) {
			updateMenu();
		}
		return menu;
	}
	
	/**
	 * This method is used to update the current menu
	 */
	private void updateMenu() {
		// Get DatabaseUtil instance
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
		DatabaseUtil dbUtil = (DatabaseUtil) servletContext.getAttribute("DATABASE_UTIL");
		// Prepare a StringBuffer used to output
		StringBuffer buffer = new StringBuffer(512);
		buffer.append("<table>\n");
		// Iterate the categories collection and append them into the output buffer
		ArrayList<Category> categories = dbUtil.getCategories();
		Iterator<Category> iterator = categories.iterator();
		while (iterator.hasNext()) {
			Category category = iterator.next();
			buffer.append("<tr><td>");
			buffer.append("<a href = \"" + browsePage + "?categoryId=" + category.getId() + "\">" + 
					category.getDescription() + "</a>");
			buffer.append("</td></tr>\n");
		}
		buffer.append("</table>\n");
		// Update the menu and reset the update time
		menu = buffer.toString();
		lastUpdate = System.currentTimeMillis();
	}
	

}
