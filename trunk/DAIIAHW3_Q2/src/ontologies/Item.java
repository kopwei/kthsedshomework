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
public class Item implements Concept{
    private String name = null;
    //private float initialPrice = 0.0f;
    private float proposedPrice = 0.0f;
    
    public void setName(String name) {
        this.name = name;
    }
    
//    public void setInitPrice(float price) {
//        this.initialPrice = price;
//    }
//    
    public void setItemProposedPrice(float price) {
        this.proposedPrice = price;
    }
    
    public String getName() {
        return name;
    }
    
//    public float getInitPrice() {
//        return initialPrice;
//    }
//    
    public float getItemProposedPrice() {
        return proposedPrice;
    }

}
