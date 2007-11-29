/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package agents;

import jade.content.ContentElement;
import jade.content.ContentManager;
import jade.content.lang.Codec;
import jade.content.lang.Codec.CodecException;
import jade.content.lang.leap.LEAPCodec;
import jade.content.onto.Ontology;
import jade.content.onto.OntologyException;
import jade.content.onto.UngroundedException;
import jade.core.Agent;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import java.util.logging.Level;
import java.util.logging.Logger;
import ontologies.AuctionOntology;
import ontologies.InitiatorCFP;
import ontologies.ParticipantPropose;

/**
 *
 * @author Kop
 */
public class ParticipantDutchAuctionBehaviour extends SimpleBehaviour{
    private boolean finished = false;
    
    private ContentManager manager = null;
    private Codec codec = new LEAPCodec();
    private Ontology ontology = AuctionOntology.getInstance();
    private float percent = 0;
    private float maxAcceptedPrice = 0;
    private boolean firstCFP = true;

    ParticipantDutchAuctionBehaviour(Participant a) {
        super(a);
        manager = a.getContentManager();
        percent = a.getPercent();
    }

    @Override
    public void action() {
        try {
            ACLMessage msg = myAgent.blockingReceive();
            if (msg.getPerformative() == ACLMessage.CFP) {
                ContentElement ce = manager.extractContent(msg);
                if (ce instanceof InitiatorCFP) {
                    InitiatorCFP cfp = (InitiatorCFP) ce;
                    float currentPrice = cfp.getCurrentPrice();
                    if (firstCFP) {
                        maxAcceptedPrice = currentPrice * (1 - percent);
                        firstCFP = false;
                        System.out.println("Participant " + myAgent.getLocalName() + ": Got first CFP, Price: " + currentPrice + ", my MaxAcceptedPrice: " + maxAcceptedPrice);
                    }

                    if (currentPrice <= maxAcceptedPrice) {
                        ACLMessage reply = msg.createReply();
                        reply.setPerformative(ACLMessage.PROPOSE);
                        reply.addReceiver(msg.getSender());
                        reply.setLanguage(codec.getName());
                        reply.setOntology(ontology.getName());
                        ParticipantPropose pp = new ParticipantPropose();
                        pp.setAcceptance(true);
                        manager.fillContent(reply, pp);
                        myAgent.send(reply);
                        System.out.println("current price: " + currentPrice + ", and I can accept it");
                    } else {
                        System.out.println("current price: " + currentPrice + ", I can not accept it");
                    }
                }
            }
            if (msg.getPerformative() == ACLMessage.ACCEPT_PROPOSAL) {
                finished = true;
                System.out.println("Participant " + myAgent.getLocalName() + ": win the auction");
            }
            if (msg.getPerformative() == ACLMessage.CANCEL) {
                finished = true;
                System.out.println("Participant " + myAgent.getLocalName() + ": auction is over");
            }
        } catch (CodecException ex) {
            Logger.getLogger(ParticipantDutchAuctionBehaviour.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UngroundedException ex) {
            Logger.getLogger(ParticipantDutchAuctionBehaviour.class.getName()).log(Level.SEVERE, null, ex);
        } catch (OntologyException ex) {
            Logger.getLogger(ParticipantDutchAuctionBehaviour.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public boolean done() {
        return finished;
    }

}
