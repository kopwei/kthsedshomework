/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fileshare;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.SimpleBehaviour;
//import jade.domain.introspection.ACLMessage;
import jade.lang.acl.ACLMessage;
import java.util.HashSet;


/**
 *
 * @author Kop
 */
public class ClientFileSharingBehaviour extends SimpleBehaviour{
    private boolean finished = false;
    private PeerListQueryBehaviour peerListQueryBehaviour = null;
    private ClientDownloadBehaviour downloadBehaviour = null;
    private ClientUploadBehaviour uploadBehaviour = null;
    private HashSet<AID> peerSet = new HashSet<AID>();
    
    
    public ClientFileSharingBehaviour(Agent a) {
        super(a);
        FileSharingClient clientAgent = (FileSharingClient)a;
        if (null != clientAgent) {
            peerListQueryBehaviour = new PeerListQueryBehaviour(clientAgent);
            downloadBehaviour = new ClientDownloadBehaviour(clientAgent);
            uploadBehaviour = new ClientUploadBehaviour(clientAgent);
        }
    }
    
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
    
    /**
     * 
     * @return
     */
    public HashSet<AID> getPeerSet() {
        return peerSet;
    }
    
    /**
     * 
     * @param peerSet
     */
    public void setPeerSet(HashSet<AID> peerSet) {
        this.peerSet = peerSet;
    }
    
    /**
     * 
     */
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
    
    /**
     * 
     * @param msg
     */
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
