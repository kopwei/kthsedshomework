/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fileshare;

import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

/**
 *
 * @author Kop
 */
public class ClientBlockUploader{
    private FileSharingClient clientAgent = null;
    private ACLMessage proposeMsg = null;
    
    public ClientBlockUploader(Agent client, ACLMessage proposeMsg) {
        clientAgent = (FileSharingClient)client;
        this.proposeMsg = proposeMsg;
    }
    
    public void action() {
        
        //if (msg.)
        try {
            // Get the object out of the content
            BTMessageContent content = (BTMessageContent)(proposeMsg.getContentObject());
            if (null == content) {
                System.out.println("Strange, The proposed message contains nothing");
                return;
            }
            // Check the available blocks which it can provide.
            ArrayList<Integer> lostBlocks = content.getLostBlockNumber();
            Vector<Integer> availableBlocks = new Vector<Integer>();
            FileManager manager = clientAgent.getFileManager(); 
            for (Integer integer : lostBlocks) {
                if (manager.isBlockAvailable(integer.intValue())) {
                    availableBlocks.addElement(integer);
                }
            }
            // Here we have to add something to think before upload the block
            int requesterUtility = content.getUtility();
            int myUtility = clientAgent.getUtility();
            boolean shouldTransmit = requesterUtility < myUtility || requesterUtility <= 0;
            // If there are blocks available then randomly select a block and reply it
            if (availableBlocks.size() > 0 && shouldTransmit) {
                ArrayList<Integer> availableBlockList = new ArrayList<Integer>(availableBlocks.size());
                for (Integer integer : availableBlocks) {
                    if (null != integer) {
                        availableBlockList.add(integer);
                    }
                }

                // Randomly select a block and packs it to a message
                Random rand = new Random(System.currentTimeMillis());
                int randInt = rand.nextInt(availableBlockList.size());
                int index = availableBlockList.get(randInt).intValue();
                String block = manager.getBlockAt(index);
                // Prepare a BTMessageContent
                BTMessageContent replyContent = new BTMessageContent();
                replyContent.setBlockContent(block);
                replyContent.setBlockIndex(index);
                ACLMessage replyMessage = new ACLMessage(ACLMessage.ACCEPT_PROPOSAL);
                replyMessage.setContentObject(replyContent);
                replyMessage.addReceiver(proposeMsg.getSender());
                clientAgent.send(replyMessage);
                System.out.println("I replie the block " + index + " to agent " + proposeMsg.getSender().getName());
                // Increase the benefit to others
                clientAgent.increaseBenefit(proposeMsg.getSender());
            } // If there is no block available,then reject the propose
            else {
                ACLMessage replyMsg = new ACLMessage(ACLMessage.REJECT_PROPOSAL);
                replyMsg.addReceiver(proposeMsg.getSender());
                clientAgent.send(replyMsg);
                //System.out.println("I don't have any block to agent " + proposeMsg.getSender().getName());
            }
        }
        catch (IOException ex) {
            System.err.println(ex.getMessage());
        }        catch (UnreadableException ue) {
            System.err.println(ue.getMessage());
        }
        
    } 

}
