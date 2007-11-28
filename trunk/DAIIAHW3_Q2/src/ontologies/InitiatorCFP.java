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
public class InitiatorCFP implements Concept{
    private Item item = null;
    private float currentPrice = 0.0f;
    
    public void setItem(Item item) {
        this.item = item;
    }
    
    public void setCurrentPrice(float price) {
        this.currentPrice = price;
    }
    
    public Item getItem() {
        return item;
    }
    
    public float getCurrentPrice() {
        return currentPrice;
    }

}
