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
import java.util.logging.Level;
import java.util.logging.Logger;
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
            iterateForBestPrice(participants, mobilePhone, 10.0f);
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

    private void iterateForBestPrice(ArrayList<AID> agentIDs, Item item, float currentPrice) {
        try {
            ACLMessage cfpMessage = new ACLMessage(ACLMessage.CFP);
            for (AID agentID : agentIDs) {
                cfpMessage.addReceiver(agentID);
            }
            InitiatorCFP cfp = new InitiatorCFP();
            cfp.setCurrentPrice(currentPrice);

            manager.fillContent(cfpMessage, cfp);
            myAgent.send(cfpMessage);
            
            // Ask all the contrators for their price and store their new proposed price
            int contractorNumber = 0;
            //HashMap<AID, Integer> ctors = new HashMap<AID, Integer>();
            while (contractorNumber < agentIDs.size()) {
                ACLMessage replyMsg = myAgent.blockingReceive();
                if (null != replyMsg) {
                    contractorNumber++;
                    if (replyMsg.getPerformative() == ACLMessage.PROPOSE) {
                        ParticipantPropose propose = (ParticipantPropose) (manager.extractContent(replyMsg));
                    }
                }
            }
        } catch (CodecException ex) {
            System.err.println(ex.getMessage());
        } catch (OntologyException ex) {
            System.err.println(ex.getMessage());
        }
        
    }

}
