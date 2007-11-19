/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package market.server;

import java.util.UUID;

/**
 *
 * @author Kop
 */
public class ItemForSell {
    private String name = null;
    private int price = 0;    
    private ItemType type = ItemType.Unknown;
    private UUID itemId = null;
    
    /**
     * Constructor for initiating the Item
     * @param name
     * @param price
     */
    public ItemForSell(String name, int price, ItemType type) {
        this.name = name;
        this.price = price;
        this.type = type;
        this.itemId = UUID.randomUUID();
    }
    
    /**
     * 
     * @return
     */
    public int getPrice() {
        return price;
    }
    
    /**
     * 
     * @return
     */
    public String getName() {
        return name;
    }
    
    /**
     * 
     * @return
     */
    public ItemType getType() {
        return type;
    }
    
    /**
     * 
     * @return
     */
    public UUID getID() {
        return itemId;
    }
    
}
