/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package market.client;

/**
 *
 * @author Ricky
 */
public class Item {
    private String itemName = null;
    private float itemPrice = 0;
    
    public Item(String name, float price) {
        this.itemName = name;
        this.itemPrice = price;
    }
    
    public String getItemName() {
        return itemName;
    }
    
    public float getItemPrice() {
        return itemPrice;
    }
}
