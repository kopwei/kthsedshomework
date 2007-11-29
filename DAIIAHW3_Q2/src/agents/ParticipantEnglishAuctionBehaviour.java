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
import jade.content.lang.sl.SLCodec;
import jade.content.onto.Ontology;
import jade.content.onto.OntologyException;
import jade.content.onto.UngroundedException;
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
public class ParticipantEnglishAuctionBehaviour extends SimpleBehaviour{
    private boolean finished = false;
    
    private ContentManager manager = null;
    private Codec codec = new SLCodec();
    private Ontology ontology = AuctionOntology.getInstance();
    private float percent = 0;
    private float maxAcceptedPrice = 0;
    private boolean firstCFP = true;

    ParticipantEnglishAuctionBehaviour(Participant p) {
        super(p);
        manager = p.getContentManager();
        manager.registerLanguage(codec);
	manager.registerOntology(ontology);
        percent = p.getPercent();        
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
                        maxAcceptedPrice = currentPrice * (1 + percent);
                        firstCFP = false;
                        System.out.println("Participant " + myAgent.getLocalName() + ": Got first CFP, Price: " + currentPrice + ", my MaxAcceptedPrice: " + maxAcceptedPrice);
                    }

                    ACLMessage reply = new ACLMessage(ACLMessage.PROPOSE);
                    reply.addReceiver(msg.getSender());
                    reply.setLanguage(codec.getName());
                    reply.setOntology(ontology.getName());
                    ParticipantPropose pp = new ParticipantPropose();
                    if (currentPrice <= maxAcceptedPrice) {
                        pp.setAcceptance(true);
                        System.out.println("current price: " + currentPrice + ", and I can accept it");
                    } else {
                        pp.setAcceptance(false);
                        finished = true;
                        System.out.println("current price: " + currentPrice + ", I can not accept it");
                    }
                    manager.fillContent(reply, pp);
                    myAgent.send(reply);
                }
            }
            if (msg.getPerformative() == ACLMessage.ACCEPT_PROPOSAL) {
                finished = true;
                System.out.println("Participant " + myAgent.getLocalName() + ": win the auction");
            }
        } catch (CodecException ex) {
            Logger.getLogger(ParticipantEnglishAuctionBehaviour.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UngroundedException ex) {
            Logger.getLogger(ParticipantEnglishAuctionBehaviour.class.getName()).log(Level.SEVERE, null, ex);
        } catch (OntologyException ex) {
            Logger.getLogger(ParticipantEnglishAuctionBehaviour.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public boolean done() {
        return finished;
    }
}
