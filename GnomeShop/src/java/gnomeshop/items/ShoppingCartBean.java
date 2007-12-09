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
	public boolean addShoppingItem(ShoppingItemBean item) {
		boolean result = shoppingItems.add(item);
                return result;
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
	
        /**
         * This method is used to remove the corresponding item in the shopping cart
         * @param itemId The unique product id represent the shopping item
         */
        public void removeShoppingItem(String itemId) {
            int index = -1;
            for (int i = 0; i < shoppingItems.size(); i++) {
                ShoppingItemBean item = shoppingItems.get(i);
                if (item.getProductId().equals(itemId)) {
                    index = i;
                    break;
                }
            }
            if (index >= 0) {
                shoppingItems.remove(index);
            }
        }
        
        /**
         * This method is used to clear all the things in the shopping item
         */
        public void clear() {
            shoppingItems.clear();
        }
}
