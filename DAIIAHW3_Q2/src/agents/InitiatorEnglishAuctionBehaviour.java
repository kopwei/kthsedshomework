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
public class InitiatorEnglishAuctionBehaviour extends SimpleBehaviour{
    private boolean finished = false;
    private ArrayList<AID> participants = null;
    // We handle contents
    private ContentManager manager = null;
    // This agent speaks the SL language
    private Codec codec = new SLCodec();
    
    private AID winner = null;
    
    private Ontology ontology = AuctionOntology.getInstance();
    
    

    public InitiatorEnglishAuctionBehaviour(Agent agent, ArrayList<AID> participants) {
        super(agent);
        this.participants = participants;
        this.manager = myAgent.getContentManager();
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
            mobilePhone.setProposedPrice(30f);
            mobilePhone.setName("Nokia N82");

            AuctionInitiation init = new AuctionInitiation();
            init.setItem(mobilePhone);

            manager.fillContent(initMsg, init);
            myAgent.send(initMsg);
            float bestPrice = iterateForBestPrice(participants, mobilePhone, 10.0f);
            finished = true;
            if (bestPrice > mobilePhone.getProposedPrice()) {
                System.out.println("The best price is " + bestPrice);
                System.out.println("The winner is " + winner.getLocalName());
            }
            else {
                System.out.println("The best price is " + bestPrice);
                System.out.println("But it doesn't meet the lowest price, the auction ends");
            }
        } catch (CodecException ex) {
            System.err.println(ex.getMessage());
        } catch (OntologyException ex) {
            System.err.println(ex.getMessage());
        }
    
    }

    @Override
    public boolean done() {
        return finished;
    }

    private float iterateForBestPrice(ArrayList<AID> agentIDs, Item item, float currentPrice) {
        try {
            ACLMessage cfpMessage = new ACLMessage(ACLMessage.CFP);
            for (AID agentID : agentIDs) {
                cfpMessage.addReceiver(agentID);
            }
            InitiatorCFP cfp = new InitiatorCFP();
            cfp.setCurrentPrice(currentPrice);
            
            manager.fillContent(cfpMessage, cfp);
            // 
            System.out.println("I create a cfp with price " + currentPrice);
            myAgent.send(cfpMessage);
            
            // Ask all the contrators for their price and store their new proposed price
            int participantNumber = 0;
            //HashMap<AID, Integer> ctors = new HashMap<AID, Integer>();
            ArrayList<AID> acceptedAgents = new ArrayList<AID>();
            while (participantNumber < agentIDs.size()) {
                ACLMessage replyMsg = myAgent.blockingReceive();
                if (null != replyMsg) {
                    participantNumber++;
                    if (replyMsg.getPerformative() == ACLMessage.PROPOSE) {
                        ParticipantPropose propose = (ParticipantPropose) (manager.extractContent(replyMsg));
                        if (propose.getAcceptance()) {
                            acceptedAgents.add(replyMsg.getSender());
                        }
                    }
                }
            }
            if (acceptedAgents.size() == 0) {
                return currentPrice;
            }
            if (acceptedAgents.size() == 1) {
                winner = acceptedAgents.get(0);
                if( currentPrice > item.getProposedPrice()) {
                    return currentPrice;               
                }
                else {
                    currentPrice += 1;
                    return iterateForBestPrice(agentIDs, item, currentPrice);
                }
            }
            else {
                currentPrice += 1;
                return iterateForBestPrice(agentIDs, item, currentPrice);
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
