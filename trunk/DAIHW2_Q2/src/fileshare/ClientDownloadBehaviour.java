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
    private boolean blocked  = false;
    private boolean askedForUpdate = false;
    private boolean peerUpdated = false;
    private int counter = 0;
    private ArrayList<AID> myPeerList = new ArrayList<AID>(); 
    
    public ClientDownloadBehaviour(Agent client) {
        super(client);
        this.clientAgent = (FileSharingClient) client;
    }
    
    @Override
    @SuppressWarnings("empty-statement")
    public void action() {
        if(blocked) return;
        // Step 0) if all the blocks are fullfilled, then stop the behaviour
        if (clientAgent.getFileManager().isFull()) {
            System.out.println("Blocks full, download stops ");
            finished = true;
            System.out.println("downloading blocks finished");
            return;
        }
        // Step 2) check the lost blocks and prepare for the message
        ArrayList<Integer> lostBlocks = clientAgent.getFileManager().getLostBlockNumbers();
        BTMessageContent messageContent = new BTMessageContent();
        messageContent.setBlockNumbers(lostBlocks);
        // Get the peer set and randomly select one of the peer
       
        if (counter == 0) {
            if (!askedForUpdate) {
                ArrayList<AID> trackerAIDs = clientAgent.getTrackers();
                ACLMessage sMsg = new ACLMessage(ACLMessage.REQUEST);
                for (AID aid : trackerAIDs) {
                    sMsg.addReceiver(aid);
                }
                myAgent.send(sMsg);
                askedForUpdate = true;
            }
            if (!peerUpdated) {
                return;
            }
            
            HashSet<AID> peerSet = clientAgent.getClientBehaviour().getPeerSet();
            peerUpdated = false;
            if (peerSet.size() == 0) { 
                return;
            }
            peerSet.remove(myAgent.getAID());
            myPeerList = new ArrayList(peerSet);
            Collections.shuffle(myPeerList);
        }
        try {
            // Prepare the message and send it
            ACLMessage proposeMessage = new ACLMessage(ACLMessage.PROPOSE);
            proposeMessage.setContentObject(messageContent);
            proposeMessage.addReceiver(myPeerList.get(counter));
            clientAgent.send(proposeMessage);
            counter++;
        } catch (IOException ex) {
            Logger.getLogger(ClientDownloadBehaviour.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (counter == myPeerList.size()) {
            counter = 0;
            askedForUpdate = false;
        }
//        while (blocked) {
//            ;
//        }
        blocked = true;
    }
    
    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }
    
    public void setPeerUpdated(boolean peerUpdated) {
        this.peerUpdated = peerUpdated;
    }

    @Override
    public boolean done() {
        return finished;
    }
}
