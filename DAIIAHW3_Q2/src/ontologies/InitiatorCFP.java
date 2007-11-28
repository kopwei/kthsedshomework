/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ontologies;

import jade.content.Predicate;

/**
 *
 * @author Kop
 */
public class InitiatorCFP implements Predicate{
    private Item item = null;
    private float currentPrice = 0.0f;

    public void setCurrentPrice(float price) {
        this.currentPrice = price;
    } 
    
    public float getCurrentPrice() {
        return currentPrice;
    }

}
