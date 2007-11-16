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
import jade.lang.acl.UnreadableException;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Kop
 */
public class ClientFileSharingBehaviour extends SimpleBehaviour{
    private boolean finished = false;
    private PeerListQueryBehaviour peerListQueryBehaviour = null;
    private ClientUploadBehaviour uploadBehaviour = null;
    private HashSet<AID> peerSet = new HashSet<AID>();
    FileSharingClient clientAgent = null;
    private boolean registered = false;
    
    
    public ClientFileSharingBehaviour(Agent a) {
        super(a);
        clientAgent = (FileSharingClient)a;
        if (null != clientAgent) {
            peerListQueryBehaviour = new PeerListQueryBehaviour(clientAgent);
            uploadBehaviour = new ClientUploadBehaviour(clientAgent);
        }
    }
    
    @Override
    public void action() {
        // Step 1) Query for peer list
        myAgent.addBehaviour(peerListQueryBehaviour);
        
        // Step 2) If the blocks are not empty, then Register to tracker
        if (!clientAgent.getFileManager().isEmpty() && !registered) {
            ACLMessage registerMessage  = new ACLMessage(ACLMessage.INFORM);
            //registerMessage.setContent(STATE_READY);
            clientAgent.send(registerMessage);
            registered = true;
        }
        // Step 3) Start to receiving files
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
     * This method is used to update the peer set
     * @param peerSet Input peer hash set to update the peers 
     */
    public void setPeerSet(HashSet<AID> peerSet) {
        this.peerSet = peerSet;
    }
        
    //private int checkLostBlocks
    
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
            handleProposeMessage(msg);
            break;
        case ACLMessage.ACCEPT_PROPOSAL:
            handleAcceptMessage(msg);
            break;
        case ACLMessage.REJECT_PROPOSAL:
            handleRejectMessage(msg);
            break;
        case ACLMessage.INFORM:
            handleInformMessage(msg);
            break;
        default: 
            break;
        }
    }

    /**
     * This method will process the propose message
     * @param msg
     */
    private void handleProposeMessage(ACLMessage msg) {
        
    }
    
    /**
     * This method will process the accept propose message
     * @param msg
     */
    private void handleAcceptMessage(ACLMessage msg) {
        try {
            // Step 1) Fill the block first
            BTMessageContent content = (BTMessageContent) msg.getContentObject();
            if (null == content) return;
            int index = content.getBlockIndex();
            if (null == clientAgent.getFileManager().getBlockAt(index)) {
                clientAgent.getFileManager().insertBlock(index, content.getBlockContent());
            }
            // Release the download behaviour
            clientAgent.getDownloadBehaviour().setBlocked(false);
        } catch (UnreadableException ex) {
            Logger.getLogger(ClientFileSharingBehaviour.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * This method will process the propose message
     * @param msg
     */
    private void handleRejectMessage(ACLMessage msg) {
        // Release the download behaviour
        clientAgent.getDownloadBehaviour().setBlocked(false);
    }
    
    private void handleInformMessage(ACLMessage msg) {
        
    }
}