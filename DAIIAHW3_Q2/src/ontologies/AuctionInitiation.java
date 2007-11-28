/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ontologies;

import jade.content.Concept;

/**
 *
 * @author Kop
 */
public class AuctionInitiation implements Concept{
    private Item item = null;
    private float initPrice = 0.0f;
    
    public void setItem(Item item) {
        this.item = item;
    }
    
    public void setInitPrice(float price) {
        this.initPrice = price;
    }
    
    public Item getItem() {
        return item;
    }
    
    public float getInitPrice() {
        return initPrice;
    }
}
