/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package agents;

import jade.content.Concept;
import jade.content.ContentElement;
import jade.content.ContentManager;
import jade.content.lang.Codec.CodecException;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.OntologyException;
import jade.content.onto.UngroundedException;
import jade.content.onto.basic.Action;
import jade.core.AID;
import jade.core.Agent;
import jade.core.Location;
import jade.core.behaviours.SimpleBehaviour;
import jade.domain.mobility.CloneAction;
import jade.domain.mobility.MobilityOntology;
import jade.domain.mobility.MoveAction;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ricky
 */
public class ManagerCNPBehaviour extends SimpleBehaviour{
    private boolean isFinished = false;
    private int bestPrice = 0;
    private AID bestContractor;
    
    private ContractManager managerAgent;
    private ContentManager contentManager;
    private HashSet<AID> contractorCollection = new HashSet<AID>();
    private Location destination;
    private AID controller;
    HashMap<AID, Integer> offerOfContractor = new HashMap<AID, Integer>();
    
    public ManagerCNPBehaviour(Agent a) {
        super(a);
        managerAgent = (ContractManager)a;
        this.controller = managerAgent.getController();
    }

    @Override
    public void action() {
        // Register languager and ontology
        contentManager = managerAgent.getContentManager();
        contentManager.registerLanguage(new SLCodec());
        contentManager.registerOntology(MobilityOntology.getInstance());
        ACLMessage msg = managerAgent.blockingReceive();
        System.out.println(managerAgent.getLocalName() + ": got a message from " + msg.getSender().getLocalName());
        if (msg.getSender().equals(controller)) {
            try {
                switch (msg.getPerformative()) {
                    case ACLMessage.REQUEST:
                        ContentElement content = contentManager.extractContent(msg);
                        Concept concept = ((Action) content).getAction();
                        if (concept instanceof CloneAction) {
                            CloneAction ca = (CloneAction) concept;
                            String newName = ca.getNewName();
                            Location location = ca.getMobileAgentDescription().getDestination();
                            destination = location;
                            System.out.println(managerAgent.getLocalName() + ": got a clone action to " + destination.getName());
                            managerAgent.doClone(location, newName);
                        }
                        if (concept instanceof MoveAction) {
                            MoveAction ma = (MoveAction) concept;
                            Location location = ma.getMobileAgentDescription().getDestination();
                            System.out.println(managerAgent.getLocalName() + ": got a move action to " + destination.getName());
                            managerAgent.doMove(destination = location);
                        }
                        break;
                    case ACLMessage.INFORM_REF:
                        contractorCollection = ((AIDMessageContent) msg.getContentObject()).getAIDCollection();
                        // Step 1) Prepare for the message to ask price
                        ACLMessage requestPriceMsg = new ACLMessage(ACLMessage.REQUEST);
                        for (AID aid : contractorCollection) {
                            requestPriceMsg.addReceiver(aid);
                        }
                        requestPriceMsg.setContent("Ask for price");
                        managerAgent.send(requestPriceMsg);
                        System.out.println(managerAgent.getLocalName() + ": send request for price, to " + requestPriceMsg.getAllReceiver().toString());
                        break;
                    case ACLMessage.INFORM_IF:
                        AID contractManagerAID = ((AIDMessageContent) msg.getContentObject()).getTheAID();
                        AIDMessageContent aidmc = new AIDMessageContent();
                        aidmc.setTheAID(bestContractor);
                        aidmc.setBestPrice(bestPrice);
                        ACLMessage _msg = new ACLMessage(ACLMessage.CONFIRM);
                        _msg.setContentObject(aidmc);
                        _msg.addReceiver(contractManagerAID);
                        managerAgent.send(_msg);
                        break;
                    default:
                        break;
                    }
            } catch (IOException ex) {
                Logger.getLogger(ManagerCNPBehaviour.class.getName()).log(Level.SEVERE, null, ex);
            } catch (UnreadableException ex) {
                Logger.getLogger(ManagerCNPBehaviour.class.getName()).log(Level.SEVERE, null, ex);
            } catch (CodecException ex) {
                Logger.getLogger(ManagerCNPBehaviour.class.getName()).log(Level.SEVERE, null, ex);
            } catch (UngroundedException ex) {
                Logger.getLogger(ManagerCNPBehaviour.class.getName()).log(Level.SEVERE, null, ex);
            } catch (OntologyException ex) {
                Logger.getLogger(ManagerCNPBehaviour.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            if (msg.getPerformative() == ACLMessage.INFORM) {
                offerOfContractor.put(msg.getSender(), Integer.parseInt(msg.getContent()));
                if (offerOfContractor.size() < 2) {
                    return;
                }
                // Get the current best contractor and ask for more benefit
                bestContractor = getBestContractor(offerOfContractor);
                bestPrice = offerOfContractor.get(bestContractor);
                if (offerOfContractor.size() > 1) {
                    bestContractor = iterateForBestContractor(offerOfContractor, bestPrice);
                }
                System.out.println(managerAgent.getLocalName() + ": I have got the best price from " + bestContractor.getLocalName());
                System.out.println("The best price is  " + bestPrice);
                ACLMessage moveMsg = new ACLMessage(ACLMessage.INFORM);
                moveMsg.addReceiver(controller);
                System.out.println(managerAgent.getLocalName() + ": tell " + controller.getLocalName() + " it is over ");
                managerAgent.send(moveMsg);
            }
            if (msg.getPerformative() == ACLMessage.CONFIRM) {
                try {
                    AID bc = ((AIDMessageContent) msg.getContentObject()).getTheAID();
                    int price = ((AIDMessageContent) msg.getContentObject()).getBestPrice();
                    offerOfContractor.put(bc, price);
                    if (offerOfContractor.size() < 2) return;
                    bestContractor = getBestContractor(offerOfContractor);
                    bestPrice = offerOfContractor.get(bestContractor);
                    
                    System.out.println(managerAgent.getLocalName() + ": The final result: ");
                    System.out.println("best contractor: " + bestContractor.getLocalName() + ", best price: " + bestPrice);
                    isFinished = true;
                } catch (UnreadableException ex) {
                    Logger.getLogger(ManagerCNPBehaviour.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    /**
     * This method is used to find out the contractor with the best price among the offerOfContractor
     * @param offerOfContractor input offerOfContractor with their price
     * @return the best contractor AID amoong the offerOfContractor
     */
    private AID getBestContractor(HashMap<AID, Integer> offerOfContractor) {
        // Prepare to iterate the map and find the best contractor out
        Iterator<AID> itor = offerOfContractor.keySet().iterator();
        AID bestAID = null;
        int currentBestPrice = Integer.MAX_VALUE;
        // Iterate the hashmap, if the price is lower, then we set the best price as the lower price
        // the best AID as the current AID
        while (itor.hasNext()) {
            AID currentAID = itor.next();
            int price = offerOfContractor.get(currentAID).intValue();
            if (price < currentBestPrice) {
                currentBestPrice = price;
                bestAID = currentAID;
            }
        }
        return bestAID;
    }
    
    private AID iterateForBestContractor(HashMap<AID, Integer> offerOfContractor, int currentBestPrice) {
        // Prepare the message which contains the best price
        ACLMessage bestPriceMsg = new ACLMessage(ACLMessage.INFORM);
        Iterator<AID> itor = offerOfContractor.keySet().iterator();
        while (itor.hasNext()) {
            bestPriceMsg.addReceiver(itor.next());

        }
        bestPriceMsg.setContent(Integer.toString(currentBestPrice));
        myAgent.send(bestPriceMsg);
        System.out.println(managerAgent.getLocalName() + ": I send the lowest price as " + currentBestPrice);
        
        // Ask all the contrators for their price and store their new proposed price
        int contractorNumber = 0;
        HashMap<AID, Integer> ctors = new HashMap<AID, Integer>();
        while (contractorNumber < offerOfContractor.size()) {
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
        bestPrice = ctors.get(bestContractor);
        if (ctors.size() == 1) {
            return bestContractor;
        }
        else {
            // If there are more offerOfContractor, next iteration 
            return iterateForBestContractor(ctors, bestPrice);
        }
    }

    @Override
    public boolean done() {
        return isFinished;
    }
}
