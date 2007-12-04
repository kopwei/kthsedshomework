/**
 * 
 */
package gnomeshop.items;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * @author Kop
 *
 */

/**
 * This class represent a shopping cart entity
 */
public class ShoppingCartBean {
	private ArrayList<ShoppingItemBean> shoppingItems = new ArrayList<ShoppingItemBean>();
	
	/**
	 * Default constructor used to initialize the information into default state
	 */
	public ShoppingCartBean() {
		
	}
	
	/**
	 * This method is used to get all the shopping items of the current cart
	 * @return The collection of shopping items
	 */
	public Collection<ShoppingItemBean> getShoppingItems() {
		return shoppingItems;
	}
	
	/**
	 * This method is used to set all the shopping items of the current cart
	 * @param shoppingItems The collection of shopping items
	 */
	public void setShoppingItems(Collection<ShoppingItemBean> shoppingItems) {
		this.shoppingItems = new ArrayList<ShoppingItemBean>(shoppingItems);
	}
	
	/**
	 * This method is used to add a new shopping item into the current cart
	 * @param item The shopping item
	 */
	public void addShoppingItem(ShoppingItemBean item) {
		shoppingItems.add(item);
	}
	
	/**
	 * This method is used to clear the shopping cart
	 */
	public void removeShoppingItems() {
		shoppingItems.clear();
	}
	
	/**
	 * This method is used to get the total price of the shopping items in the cart
	 * @return The total price
	 */
	public float getTotal() {
		float total = 0;
		Iterator<ShoppingItemBean> iterator = shoppingItems.iterator();
		while (iterator.hasNext()) {
			ShoppingItemBean item = iterator.next();
			total += item.getPrice() * item.getQuantity();
		}
		return total;
	}
	
}
