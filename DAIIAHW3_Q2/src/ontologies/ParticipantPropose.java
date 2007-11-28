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
public class ParticipantPropose implements Predicate{

    private float cryPrice = 0.0f;

    public void setProposePrice(float price) {
        this.cryPrice = price;
    }
  
    public float getProposePrice() {
        return cryPrice;
    }
}
