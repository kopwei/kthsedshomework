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
    private boolean blocked  = true;
    private int counter = 0;
    private ArrayList<AID> myPeerList = new ArrayList<AID>(); 
    
    public ClientDownloadBehaviour(Agent client) {
        super(client);
        this.clientAgent = (FileSharingClient) client;
    }
    
    @Override
    @SuppressWarnings("empty-statement")
    public void action() {
        // Step 0) if all the blocks are fullfilled, then stop the behaviour
        if (clientAgent.getFileManager().isFull()) {
            System.out.println("Blocks full, download stops ");
            finished = true;
            return;
        }
        // Step 2) check the lost blocks and prepare for the message
        ArrayList<Integer> lostBlocks = clientAgent.getFileManager().getLostBlockNumbers();
        BTMessageContent messageContent = new BTMessageContent();
        messageContent.setBlockNumbers(lostBlocks);
        // Get the peer set and randomly select one of the peer
       
        if (counter == 0) {
            HashSet<AID> peerSet = clientAgent.getClientBehaviour().getPeerSet();
            if (peerSet.size() == 0) { 
                return;
            }
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
        if (counter == myPeerList.size()) counter = 0;
        while (blocked) {
            ;
        }
        blocked = true;
    }
    
    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    @Override
    public boolean done() {
        return finished;
    }
}
