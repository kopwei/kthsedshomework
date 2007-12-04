/**
 * 
 */
package gnomeshop.items;

/**
 * @author Kop
 *
 */
public class ProductBean {
	private String id;
	private String name;
	private String description;
	private float price;
	
	/**
	 * Default constructor.
	 */
	public ProductBean() {
		
	}
	
	/**
	 * Constructor used to initialize id, name and price of the product
	 * @param id Id of the product
	 * @param name Name of the product
	 * @param price Price of the product
	 */
	public ProductBean(String id, String name, float price) {
		this.id = id;
		this.name = name;
		this.price = price;
	}
	
	/**
	 * Constructor used to initialize id, name, description and price of the product
	 * @param id Id of the product
	 * @param name Name of the product
	 * @param description Description of the product
	 * @param price Price of the product
	 */
	public ProductBean(String id, String name, String description, float price) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
	}

	/**
	 * This method is used to get the id of the product
	 * @return The product id
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * This method is used to set the product id 
	 * @param id The id of the product
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * This method is used to get the name of the product
	 * @return The product name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * This method is used to set the product name 
	 * @param name The name of the product
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * This method is used to get the description of the product
	 * @return The product description
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * This method is used to set the product description 
	 * @param id The description of the product
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * This method is used to get the price of the product
	 * @return The product price
	 */
	public float getPrice() {
		return price;
	}
	
	/**
	 * This method is used to set the product price 
	 * @param price The id of the product
	 */
	public void setPrice(float price) {
		this.price = price;
	}
}
