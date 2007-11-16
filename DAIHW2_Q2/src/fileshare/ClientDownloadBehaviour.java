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
    
    public ClientDownloadBehaviour(Agent client) {
        this.clientAgent = (FileSharingClient) client;
    }
    
    @Override
    public void action() {
        // Step 0) if all the blocks are fullfilled, then stop the behaviour
        if (clientAgent.getFileManager().isFull()) {
            finished = true;
            return;
        }
        // Step 2) check the lost blocks and prepare for the message
        ArrayList<Integer> lostBlocks = clientAgent.getFileManager().getLostBlockNumbers();
        BTMessageContent messageContent = new BTMessageContent();
        messageContent.setBlockNumbers(lostBlocks);
        // Get the peer set and randomly select one of the peer
        HashSet<AID> peerSet = clientAgent.getClientBehaviour().getPeerSet();
        if (peerSet.size() == 0) {
            return;
        }
        ArrayList<AID> peerList = new ArrayList(peerSet);
        Collections.shuffle(peerList);
        try {
            // Prepare the message and send it
            ACLMessage proposeMessage = new ACLMessage(ACLMessage.PROPOSE);
            proposeMessage.setContentObject(messageContent);
            proposeMessage.addReceiver(peerList.get(0));
            clientAgent.send(proposeMessage);
        } catch (IOException ex) {
            Logger.getLogger(ClientDownloadBehaviour.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public boolean done() {
        return finished;
    }
}
