/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fileshare;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Kop
 */
public class ClientUploadBehaviour extends OneShotBehaviour{
    private FileSharingClient clientAgent = null;
    private ACLMessage proposeMsg = null;
    
    public ClientUploadBehaviour(Agent client, ACLMessage msg) throws Exception {
        super(client);
        clientAgent = (FileSharingClient)client;
        if (null == clientAgent) {
            throw(new Exception("agent type dosen't match"));
        }
        proposeMsg = msg;
    }

    @Override
    public void action() {
        
        //if (msg.)
        try {
            // Get the object out of the content
            BTMessageContent content = (BTMessageContent)(proposeMsg.getContentObject());
            if (null == content) {
                return;
            }
            // Check the available blocks which it can provide.
            ArrayList<Integer> lostBlocks = content.getLostBlockNumber();
            ArrayList<Integer> availableBlocks = new ArrayList<Integer>();
            FileManager manager = clientAgent.getFileManager(); 
            for (Integer integer : lostBlocks) {
                if (manager.isBlockAvailable(integer.intValue())) {
                    availableBlocks.add(integer);
                }
            }
            // If there are blocks available then randomly select a block and reply it
            if (availableBlocks.size() > 0) {
                // Randomly select a block and packs it to a message
                Random rand = new Random(System.currentTimeMillis());
                int index = rand.nextInt(availableBlocks.size());
                String block = manager.getBlockAt(index);
                // Prepare a BTMessageContent
                BTMessageContent replyContent = new BTMessageContent();
                replyContent.setBlockContent(block);
                ACLMessage replyMessage = new ACLMessage(ACLMessage.ACCEPT_PROPOSAL);
                replyMessage.setContentObject(replyContent);
                replyMessage.addReceiver(proposeMsg.getSender());
                clientAgent.send(replyMessage);
                System.out.println("I replie the block " + index + " to agent " + proposeMsg.getSender().getName());
            }
            // If there is no block available,then reject the propose
            else {
                ACLMessage replyMsg = new ACLMessage(ACLMessage.REJECT_PROPOSAL);
                replyMsg.addReceiver(proposeMsg.getSender());
                clientAgent.send(replyMsg);
                System.out.println("I don't have any block to agent " + proposeMsg.getSender().getName());
            }
        }
        catch (IOException ex) {
            Logger.getLogger(ClientUploadBehaviour.class.getName()).log(Level.SEVERE, null, ex);
        }        catch (UnreadableException ue) {
            System.err.println(ue.getMessage());
        }
        
    } 

}
