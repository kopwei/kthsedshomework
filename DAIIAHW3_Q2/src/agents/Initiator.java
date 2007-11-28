/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package agents;

import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.SearchConstraints;
import jade.domain.FIPAAgentManagement.ServiceDescription;

/**
 *
 * @author Kop
 */
public class Initiator extends Agent{
    @Override
    protected void setup() {
        // Step 1) prepare for the DFDescription template
        DFAgentDescription templateDFD = new DFAgentDescription();
        ServiceDescription templateSD = new ServiceDescription();
        templateSD.setType("auction-participant");
        //templateSD.addProperties(new Property("", ""));
        templateDFD.addServices(templateSD);
        
        // Prepare the search constraints
        SearchConstraints sc = new SearchConstraints();
        // We want to receive at most 10 results
        sc.setMaxResults(10L);
        addBehaviour(new InitiatorSubscription(this, DFService.createSubscriptionMessage(this, 
                getDefaultDF(), templateDFD, sc)));
    }
    
    // Put agent clean-up operations here
    @Override
    protected void takeDown() {
        // Printout a dismissal message
        System.out.println("Manager agent " + getAID().getName() + " terminating.");
    }

}
