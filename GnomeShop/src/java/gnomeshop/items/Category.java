/**
 * 
 */
package gnomeshop.items;

/**
 * @author Kop
 *
 */

/**
 * This category class is used to define a category entity
 */
public class Category {
	private String id;
	private String description;
	
	/**
	 * The constructor used for initialize the id and description
	 * @param id The id of the category
	 * @param description The brief description of the category
	 */
	public Category(String id, String description) {
		this.id = id;
		this.description = description;
	}
	
	/**
	 * Get the id of the current category
	 * @return The category ID
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * Set the id of the current category
	 * @param id The category ID
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * Get the description of the current category
	 * @return Description of the category
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * Set the description of the current category
	 * @param description Description of the category
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
}
