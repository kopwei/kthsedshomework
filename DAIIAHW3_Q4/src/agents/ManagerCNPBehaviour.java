/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package agents;

import jade.core.AID;
import jade.core.behaviours.SimpleBehaviour;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.lang.acl.ACLMessage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 *
 * @author Kop
 */
public class ManagerCNPBehaviour extends SimpleBehaviour{
    private ArrayList<DFAgentDescription> descriptions;
    private boolean isFinished = false;
    private int bestPrice = 0;
    
    
    public ManagerCNPBehaviour(ArrayList<DFAgentDescription> descs) {
        descriptions = descs;
    }

    @Override
    public void action() {
        // Step 1) Prepare for the message to ask price
        ACLMessage requestPriceMsg = new ACLMessage(ACLMessage.REQUEST);
        for (DFAgentDescription dFAgentDescription : descriptions) {
            requestPriceMsg.addReceiver(dFAgentDescription.getName());
        }
        
        requestPriceMsg.setContent("Ask for price");
        // Step 2) Send the message and wait for price
        myAgent.send(requestPriceMsg);
        HashMap<AID, Integer> contractors = new HashMap<AID, Integer>();
        while (contractors.size() < 3) {
            ACLMessage replyMsg = myAgent.blockingReceive();
            if (null != replyMsg) {
                if (replyMsg.getPerformative() == ACLMessage.INFORM) {
                    contractors.put(replyMsg.getSender(), Integer.parseInt(replyMsg.getContent()));
                }
            }
        }
        // Get the current best contractor and ask for more benefit
        AID bestContractor = getBestContractor(contractors);
        bestPrice = contractors.get(bestContractor);
        if (contractors.size() > 1) {
            bestContractor = iterateForBestContractor(contractors, bestPrice);
        }
        System.out.println("I have got the best price from " + bestContractor.getName());
        System.out.println("The best price is  " + bestPrice);
        isFinished = true;
    }
    
    /**
     * This method is used to find out the contractor with the best price among the contractors
     * @param contractors input contractors with their price
     * @return the best contractor AID amoong the contractors
     */
    private AID getBestContractor(HashMap<AID, Integer> contractors) {
        // Prepare to iterate the map and find the best contractor out
        Iterator<AID> itor = contractors.keySet().iterator();
        AID bestAID = null;
        int currentBestPrice = Integer.MAX_VALUE;
        // Iterate the hashmap, if the price is lower, then we set the best price as the lower price
        // the best AID as the current AID
        while (itor.hasNext()) {
            AID currentAID = itor.next();
            int price = contractors.get(currentAID).intValue();
            if (price < currentBestPrice) {
                currentBestPrice = price;
                bestAID = currentAID;
            }
        }
        return bestAID;
    }
    
    private AID iterateForBestContractor(HashMap<AID, Integer> contractors, int currentBestPrice) {
        // Prepare the message which contains the best price
        ACLMessage bestPriceMsg = new ACLMessage(ACLMessage.INFORM);
        Iterator<AID> itor = contractors.keySet().iterator();
        while (itor.hasNext()) {
            bestPriceMsg.addReceiver(itor.next());

        }
        bestPriceMsg.setContent(Integer.toString(currentBestPrice));
        myAgent.send(bestPriceMsg);
        System.out.println("I send the lowest price as " + currentBestPrice);
        
        // Ask all the contrators for their price and store their new proposed price
        int contractorNumber = 0;
        HashMap<AID, Integer> ctors = new HashMap<AID, Integer>();
        while (contractorNumber < contractors.size()) {
            ACLMessage replyMsg = myAgent.blockingReceive();
            if (null != replyMsg) {
                contractorNumber++;
                if (replyMsg.getPerformative() == ACLMessage.INFORM) {
                    ctors.put(replyMsg.getSender(), Integer.parseInt(replyMsg.getContent()));
                }
            }
        }
        // Get the best price out and check if there is only one best contractor
        AID bestContractor = getBestContractor(ctors);
        bestPrice = ctors.get(bestContractor).intValue();
        if (ctors.size() == 1) {
            return bestContractor;
        }
        else {
            // If there are more contractors, next iteration 
            return iterateForBestContractor(ctors, bestPrice);
        }
    }

    @Override
    public boolean done() {
        return isFinished;
    }
}
