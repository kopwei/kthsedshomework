/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fileshare;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import java.util.ArrayList;
/**
 *
 * @author Ricky
 */
public class PeerListQueryBehaviour extends OneShotBehaviour{
    private FileSharingClient clientAgent = null;
    /**
     * 
     * @param trackerAid the AID of tracker agent
     * @param client
     */
    public PeerListQueryBehaviour(Agent clientAgent) { 
        //trackerAIDs = trackerAid;
        super(clientAgent);
        this.clientAgent = (FileSharingClient)clientAgent;
    }

    @Override
    public void action() {
        ArrayList<AID> trackerAIDs = clientAgent.getTrackers();
        ACLMessage sMsg = new ACLMessage(ACLMessage.REQUEST);
        for (AID aid : trackerAIDs) {
            sMsg.addReceiver(aid);
        }
        myAgent.send(sMsg);            
    }
}
