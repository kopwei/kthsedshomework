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
    public void action() {
        if(isWaitingForProposeReply) {
            //System.out.println("I am waiting for proposed reply, so I returned");
            return;
        }
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
                //System.out.println("I am waiting for peerlist update so I returned");
                return;
            }
            
            HashSet<AID> peerSet = clientAgent.getClientBehaviour().getPeerSet();
            
            peerSet.remove(myAgent.getAID());
            //isPeerListUpdated = false;
            if (peerSet.size() == 0) { 
                //System.out.println("The peerlist is empty, thats why I returned");
                isWaitingForPeerListUpdate = false;
                isPeerListUpdated = false;
                return;
            }
            myPeerList = new ArrayList(peerSet);
            Collections.shuffle(myPeerList);
        }
        try {
            // Step 2) check the lost blocks and prepare for the message
            ArrayList<Integer> lostBlocks = clientAgent.getFileManager().getLostBlockNumbers();
            BTMessageContent messageContent = new BTMessageContent();
            messageContent.setBlockNumbers(lostBlocks);
            messageContent.setUtility(clientAgent.getUtility());
            // Prepare the message and send it
            ACLMessage proposeMessage = new ACLMessage(ACLMessage.PROPOSE);
            proposeMessage.setContentObject(messageContent);
            proposeMessage.addReceiver(myPeerList.get(peerPointer));
            clientAgent.send(proposeMessage);
            System.out.println("I send the propose message to " + myPeerList.get(peerPointer).getName() + " and waiting for his reply");
            peerPointer++;
            isWaitingForProposeReply = true;
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
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
