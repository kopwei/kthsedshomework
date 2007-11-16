/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fileshare;

import jade.core.behaviours.SimpleBehaviour;
//import jade.domain.introspection.ACLMessage;
import jade.lang.acl.ACLMessage;


/**
 *
 * @author Kop
 */
public class ClientFileSharingBehaviour extends SimpleBehaviour{
    private boolean finished = false;
    
    @Override
    public void action() {
        
        receivingMessages();
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean done() {
        //throw new UnsupportedOperationException("Not supported yet.");
        return finished;
    }
    
    private void receivingMessages() {
        try {
            ACLMessage msg = myAgent.blockingReceive();
            if (null != msg) {
                processMessage(msg);
            }       
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
    
    private void processMessage(ACLMessage msg) {
        // Check the validity
        if (null == msg) {
            return;
        }
        // Distribute the processing according to the message type
        switch (msg.getPerformative()) {
        case ACLMessage.PROPOSE:
            
            break;
        case ACLMessage.ACCEPT_PROPOSAL:
            break;
        case ACLMessage.REJECT_PROPOSAL:
            break;
        case ACLMessage.INFORM:
            break;
        default:
            break;
        }
    }

    
    
}
