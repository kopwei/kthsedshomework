/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fileshare;

import jade.core.AID;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import java.io.IOException;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Ricky
 */
public class TrackerBehaviour extends SimpleBehaviour{
    private boolean finished = false;
    private HashSet<AID> peersList = new HashSet<AID>();

    @Override
    public void action() {
        ACLMessage msg = myAgent.blockingReceive();
        System.out.println("I received a message ");
        ACLMessage reply = msg.createReply();
        int msgPerf = msg.getPerformative();
        // check the performative of the msg
        /**
         * the int value of performative "INFORM" is 7, client sends a msg with 
         * perf "INFORM" when he wanna share file, tracker adds him to the list 
         * of peers
         */ 
        if (msgPerf == 7) {
            peersList.add(msg.getSender());
        }
        /**
         * the int value of performative "REQUEST" is 16, client sends a msg 
         * with perf "REQUEST" when he initializes and has no block, tracker 
         * returns the list of peers
         */ 
        if (msgPerf == 16) {
            try {
                reply.setPerformative(ACLMessage.INFORM);
//            StringBuffer str = new StringBuffer();
//            for (Iterator<AID> it = peersList.iterator(); it.hasNext();) {
//                if (it.next() != null) {
//                    AID aid = it.next();
//                    String aidStr = aid.toString();
//                    str.append(aidStr + ",");
//                }
//                else {
//                    str.append(it.toString());
//                }
//            }
                BTMessageContent btmc = new BTMessageContent();
                btmc.setAIDCollection(peersList);
                reply.setContentObject(btmc.getAIDCollection());
            } catch (IOException ex) {
                Logger.getLogger(TrackerBehaviour.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        myAgent.send(reply);
    }

    @Override
    public boolean done() {
        return finished;
    }
}
