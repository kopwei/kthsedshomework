/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fileshare;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import java.util.ArrayList;
import java.util.HashSet;


/**
 *
 * @author Kop
 */
public class ClientFileSharingBehaviour extends SimpleBehaviour{
    private boolean finished = false;
    //private PeerListQueryBehaviour peerListQueryBehaviour = null;
    private ClientBlockUploader uploader = null;
    private HashSet<AID> peerSet = new HashSet<AID>();
    FileSharingClient clientAgent = null;
    private boolean registered = false;
    
    
    public ClientFileSharingBehaviour(Agent a) {
        super(a);
        clientAgent = (FileSharingClient)a;
    }
    
    @Override
    public void action() {
        // Step 1) If the blocks are not empty, then Register to tracker
        if (!clientAgent.getFileManager().isEmpty() && !registered) {
            ACLMessage registerMessage  = new ACLMessage(ACLMessage.INFORM);
            //registerMessage.setContent(STATE_READY);
            ArrayList<AID> trackers = clientAgent.getTrackers();
            for (AID aid : trackers) {
                registerMessage.addReceiver(aid);
            }
            clientAgent.send(registerMessage);
            registered = true;
        }
        // Step 2) Start to receiving files
        receivingMessages();
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
     */
    private void receivingMessages() {
        try {
            ACLMessage msg = myAgent.blockingReceive(3000);
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
        // Set the upload message and start the upload behaviour
        uploader = new ClientBlockUploader(myAgent, msg);
         System.out.println("I received a propose message from " + msg.getSender().getName());
         uploader.action();
    }
    
    /**
     * This method will process the accept propose message
     * @param msg
     */
    private void handleAcceptMessage(ACLMessage msg) {
        try {
            System.out.println("I received an accept message from " + msg.getSender().getName());
            // Step 1) Fill the block first
            BTMessageContent content = (BTMessageContent) msg.getContentObject();
            if (null == content) {
                System.out.println("accept message' content is null.");
                return;
            }
            int index = content.getBlockIndex();
            if (null == clientAgent.getFileManager().getBlockAt(index)) {
                clientAgent.getFileManager().insertBlock(index, content.getBlockContent());
                System.out.println("I have received the block no " + index);
                clientAgent.increaseProfit();
            }
            // Release the download behaviour
            clientAgent.getDownloadBehaviour().notifyProposeReplied();
        } catch (UnreadableException ex) {
            System.err.println(ex.getMessage());
        }
    }
    
    /**
     * This method will process the propose message
     * @param msg
     */
    private void handleRejectMessage(ACLMessage msg) {
        // Release the download behaviour
        System.out.println("I received a reject message from " + msg.getSender().getName());
        clientAgent.getDownloadBehaviour().notifyProposeReplied();
    }
    
    /**
     * 
     * @param msg
     */
    private void handleInformMessage(ACLMessage msg) {
        // Update the peer set
        try {
            BTMessageContent content = (BTMessageContent) msg.getContentObject();
            System.out.println("I received a inform message, from " + msg.getSender().getName() + " content is " + content);
            if (null == content) return;
            this.peerSet = content.getAIDCollection();
            clientAgent.getDownloadBehaviour().setPeerUpdatedState(true);
            for (AID aid : peerSet) {
               System.out.print(aid.getName());
            }
            System.out.println();           
        }   
        catch (UnreadableException ex) {
            System.err.println(ex.getMessage());
        }
    }
}
