/**
 * 
 */
package gnomeshop.items;

/**
 * @author Kop
 *
 */

/**
 * This class represent a shopping item entity
 */
public class ShoppingItemBean {
	private String productId;
	private String productName;
	private float price;
	private int quantity;
	
	/**
	 * The constructor used for initialize the information of the shopping item
	 * @param productId The product id of the shopping item
	 * @param productName The product name of the shopping item
	 * @param price The price of the shopping item
	 * @param quantity The total number of the shopping item
	 */
	public ShoppingItemBean(String productId, String productName, float price, int quantity) {
		this.productId = productId;
		this.productName = productName;
		this.price = price;
		this.quantity = quantity;
	}
	
	/**
	 * This method is used to get the id of the shopping item
	 * @return The shopping item id
	 */
	public String getProductId() {
		return productId;
	}
	
	/**
	 * This method is used to set the id of the shopping item
	 * @param productId The shopping item id
	 */
	public void setProductId(String productId) {
		this.productId = productId;
	}
	
	/**
	 * This method is used to get the name of the shopping item
	 * @return The shopping item name
	 */
	public String getProductName() {
		return productName;
	}
	
	/**
	 * This method is used to set the name of the shopping item
	 * @param productName The shopping item name
	 */
	public void setProductname(String productName) {
		this.productName = productName;
	}
	
	/**
	 * This method is used to get the price of the shopping item
	 * @return The shopping item price
	 */
	public float getPrice() {
		return price;
	}
	
	/**
	 * This method is used to set the price of the shopping item
	 * @param price The shopping item price
	 */
	public void setPrice(float price) {
		this.price = price;
	}
	
	/**
	 * This method is used to get the quantity of the shopping item
	 * @return The shopping item quantity
	 */
	public int getQuantity() {
		return quantity;
	}
	
	/**
	 * This method is used to set the quantity of the shopping item
	 * @param quantity The shopping item quantity
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

}
