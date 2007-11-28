/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package agents;

import jade.core.behaviours.SimpleBehaviour;

/**
 *
 * @author Kop
 */
public class InitiatorDutchAuctionBehaviour extends SimpleBehaviour{
    
    private boolean finished = false;

    @Override
    public void action() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean done() {
        return finished;
    }
}
