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
public class AuctionInitiation implements Predicate{
    private Item item = null;   
    public void setItem(Item item) {
        this.item = item;
    }
    
    public Item getItem() {
        return item;
    }
}
