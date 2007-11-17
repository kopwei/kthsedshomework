/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fileshare;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Ricky
 */
public class ClientDownloadBehaviour extends SimpleBehaviour{
    private FileSharingClient clientAgent = null;
    private boolean finished = false;
    private boolean isWaitingForProposeReply  = false;
    private boolean isWaitingForPeerListUpdate = false;
    private boolean isPeerListUpdated = false;
    private int peerPointer = 0;
    private ArrayList<AID> myPeerList = new ArrayList<AID>(); 
    
    public ClientDownloadBehaviour(Agent client) {
        super(client);
        this.clientAgent = (FileSharingClient) client;
    }
    
    @Override
    @SuppressWarnings("empty-statement")
    public void action() {
        if(isWaitingForProposeReply) return;
        // Step 0) if all the blocks are fullfilled, then stop the behaviour
        if (clientAgent.getFileManager().isFull()) {
            System.out.println("Blocks full, download stops ");
            finished = true;
            System.out.println("downloading blocks finished");
            return;
        }

        // Get the peer set and randomly select one of the peer
       
        if (peerPointer == 0) {
            if (!isWaitingForPeerListUpdate) {
                ArrayList<AID> trackerAIDs = clientAgent.getTrackers();
                ACLMessage sMsg = new ACLMessage(ACLMessage.REQUEST);
                for (AID aid : trackerAIDs) {
                    sMsg.addReceiver(aid);
                }
                myAgent.send(sMsg);
                isWaitingForPeerListUpdate = true;
            }
            if (!isPeerListUpdated) {
                return;
            }
            
            HashSet<AID> peerSet = clientAgent.getClientBehaviour().getPeerSet();
            //isPeerListUpdated = false;
            if (peerSet.size() == 0) { 
                return;
            }
            peerSet.remove(myAgent.getAID());
            myPeerList = new ArrayList(peerSet);
            Collections.shuffle(myPeerList);
        }
        try {
            // Step 2) check the lost blocks and prepare for the message
            ArrayList<Integer> lostBlocks = clientAgent.getFileManager().getLostBlockNumbers();
            BTMessageContent messageContent = new BTMessageContent();
            messageContent.setBlockNumbers(lostBlocks);
            // Prepare the message and send it
            ACLMessage proposeMessage = new ACLMessage(ACLMessage.PROPOSE);
            proposeMessage.setContentObject(messageContent);
            proposeMessage.addReceiver(myPeerList.get(peerPointer));
            clientAgent.send(proposeMessage);
            peerPointer++;
            isWaitingForProposeReply = true;
        } catch (IOException ex) {
            Logger.getLogger(ClientDownloadBehaviour.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (peerPointer == myPeerList.size()) {
            peerPointer = 0;
            isWaitingForPeerListUpdate = false;
            isPeerListUpdated = false;
        }     
    }
    
    public void notifyProposeReplied() {
        this.isWaitingForProposeReply = false;
    }
    
    public void setPeerUpdatedState(boolean peerUpdated) {
        this.isPeerListUpdated = peerUpdated;
    }

    @Override
    public boolean done() {
        return finished;
    }
}
