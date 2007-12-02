/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package agents;

import jade.content.ContentManager;
import jade.content.lang.sl.SLCodec;
import jade.core.AID;
import jade.core.Agent;
import jade.domain.mobility.MobilityOntology;

/**
 *
 * @author Ricky
 */
public class ContractManager extends Agent{
    private AID controller;
//    private Location destination;
//    private HashSet<AID> contractorCollection = new HashSet<AID>();
    
    @Override
    protected void setup() {
        Object[] args = getArguments();
        controller = (AID) args[0];
        //destination = here();
        
        System.out.println(getLocalName() + " is created");
        
        addBehaviour(new ManagerCNPBehaviour(this));
    }
    
    // Put agent clean-up operations here
    @Override
    protected void takeDown() {
        // Printout a dismissal message
        System.out.println("Manager agent " + getAID().getName() + " terminating.");
    }
    
//    private void setDestination(Location location) {
//        destination = location;
//    }
    
    public AID getController() {
        return controller;
    }
}
