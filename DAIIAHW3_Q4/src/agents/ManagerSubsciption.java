/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package agents;

import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.proto.SubscriptionInitiator;
import java.util.ArrayList;

/**
 *
 * @author Kop
 */
public class ManagerSubsciption extends SubscriptionInitiator{
    
    private ArrayList<DFAgentDescription> arrRegisteredContractorDescs = new ArrayList<DFAgentDescription>();

    public ManagerSubsciption(Agent a, ACLMessage msg) {
        super(a, msg);
    }
        
    @Override
    protected void handleInform(ACLMessage inform) {
        System.out.println("Agent " + myAgent.getLocalName() +" get notification received from DF");
        try {
            DFAgentDescription[] dfResults = DFService.decodeNotification(inform.getContent());
            if (dfResults.length > 0) {
                arrRegisteredContractorDescs.add(dfResults[0]);
            }
            if (arrRegisteredContractorDescs.size() == 3) {
                myAgent.addBehaviour(new ManagerCNPBehaviour(arrRegisteredContractorDescs));
            }
        }
        catch (FIPAException fe) {
            System.err.println(fe.getMessage());
        }
    }
}
