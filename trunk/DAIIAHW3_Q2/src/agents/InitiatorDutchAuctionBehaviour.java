/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package agents;

import jade.content.ContentManager;
import jade.content.lang.Codec;
import jade.content.lang.Codec.CodecException;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.Ontology;
import jade.content.onto.OntologyException;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import java.util.ArrayList;
import ontologies.AuctionInitiation;
import ontologies.AuctionOntology;
import ontologies.InitiatorCFP;
import ontologies.Item;
import ontologies.MobilePhone;
import ontologies.ParticipantPropose;

/**
 *
 * @author Kop
 */
public class InitiatorDutchAuctionBehaviour extends SimpleBehaviour{
    
    private final static long TIME_OUT = 3000;
    
    private boolean finished = false;
    private ArrayList<AID> participants = null;
    // We handle contents
    private ContentManager manager = null;
    // This agent speaks the SL language
    private Codec codec = new SLCodec();
    
    private AID winner = null;
    
    private Ontology ontology = AuctionOntology.getInstance();
    
    public InitiatorDutchAuctionBehaviour(Agent agent, ArrayList<AID> participants) {
        super(agent);
        this.participants = participants;
        this.manager = myAgent.getContentManager();
        manager.registerLanguage(codec);
	manager.registerOntology(ontology);
    }

    @Override
    public void action() {
        try {
            // Prepare the basic architecture of the initializing message
            ACLMessage initMsg = new ACLMessage(ACLMessage.INFORM);
            for (AID agentID : participants) {
                initMsg.addReceiver(agentID);
            }
            initMsg.setLanguage(codec.getName());
            initMsg.setOntology(ontology.getName());

            // Prepare the message content
            Item mobilePhone = new MobilePhone();
            mobilePhone.setItemProposedPrice(30f);
            mobilePhone.setName("Nokia N82");

            AuctionInitiation init = new AuctionInitiation();
            init.setItem(mobilePhone);

            manager.fillContent(initMsg, init);
            myAgent.send(initMsg);
            float bestPrice = iterateForBestPrice(mobilePhone, 100.0f);
            finished = true;
            if (bestPrice > mobilePhone.getItemProposedPrice()) {
                System.out.println("The best price is " + bestPrice);
                System.out.println("The winner is " + winner.getLocalName());
            }
            else {
                System.out.println("The best price is " + bestPrice);
                System.out.println("But it doesn't meet the lowest price, the auction ends");
            }
        } catch (Codec.CodecException ex) {
            System.err.println(ex.getMessage());
        } catch (OntologyException ex) {
            System.err.println(ex.getMessage());
        }
        
    }

    @Override
    public boolean done() {
        return finished;
    }

    private float iterateForBestPrice(Item item, float currentPrice) {
        if (currentPrice < 1.0f) {
            return 0.0f;
        }
        try {
            ACLMessage cfpMessage = new ACLMessage(ACLMessage.CFP);
            cfpMessage.setLanguage(codec.getName());
            cfpMessage.setOntology(ontology.getName());
            for (AID agentID : participants) {
                cfpMessage.addReceiver(agentID);
            }
            InitiatorCFP cfp = new InitiatorCFP();
            cfp.setCurrentPrice(currentPrice);

            manager.fillContent(cfpMessage, cfp);
            System.out.println("I create a cfp with price " + currentPrice);
            myAgent.send(cfpMessage);
            ACLMessage proposedMsg = myAgent.blockingReceive(TIME_OUT) ;
            
            if (null == proposedMsg) {
                float newPrice = currentPrice - 1;
                return iterateForBestPrice(item, newPrice);
            }
            else {
                ParticipantPropose propose = (ParticipantPropose)manager.extractContent(proposedMsg);
                if (propose.getAcceptance()) {
                    winner = proposedMsg.getSender();
                    // Announce the auction is over
                    ACLMessage overMsg = new ACLMessage(ACLMessage.CANCEL);
                    for (AID aid : participants) {
                        if (!aid.equals(winner)) {
                            overMsg.addReceiver(aid);
                        }
                    }
                    myAgent.send(overMsg);
                    ACLMessage acceptMsg = new ACLMessage(ACLMessage.ACCEPT_PROPOSAL);
                    acceptMsg.addReceiver(winner);
                    myAgent.send(acceptMsg);
                    return currentPrice;
                }
                else {
                    float newPrice = currentPrice - 1;
                    return iterateForBestPrice(item, newPrice);
                }
            }
                
        } catch (CodecException ex) {
            System.err.println(ex.getMessage());
            return 0.0f;
        } catch (OntologyException ex) {
            System.err.println(ex.getMessage());
            return 0.0f;
        }
    }
}
