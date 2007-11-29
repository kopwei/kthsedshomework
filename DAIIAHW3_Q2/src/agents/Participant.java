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
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import java.util.logging.Level;
import java.util.logging.Logger;
import ontologies.AuctionInitiation;
import ontologies.AuctionOntology;
import ontologies.Item;

/**
 *
 * @author Kop
 */
public class Participant extends Agent{
    private String auctionProtocol = null;
    private float percent = 0;
    private ContentManager manager = (ContentManager)getContentManager();
    private Codec codec = new LEAPCodec();
    private Ontology ontology = AuctionOntology.getInstance();

    @Override
    protected void setup() {
        Object[] args = getArguments();
        if (args == null) {
            doDelete();
        }
        auctionProtocol = args[0].toString();
        percent = Float.parseFloat(args[1].toString());
        
        try {
            // DF registration
            DFAgentDescription dfad = new DFAgentDescription();
            dfad.setName(getAID());
            ServiceDescription sd = new ServiceDescription();
            sd.setName("participant");
            sd.setType("auction-participant");
            dfad.addServices(sd);
            DFService.register(this, dfad);
        } catch (FIPAException ex) {
            Logger.getLogger(Participant.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        manager.registerLanguage(codec);
        manager.registerOntology(ontology);
        
        ACLMessage startAuction = blockingReceive(MessageTemplate.MatchPerformative(ACLMessage.INFORM));
        if (startAuction != null) {
            try {
                ContentElement ce = manager.extractContent(startAuction);
                if (ce instanceof AuctionInitiation) {
                    AuctionInitiation ai = (AuctionInitiation) ce;
                    Item item = ai.getItem();
                    System.out.println("Initiator " + startAuction.getSender().getName() + " will start an auction for item: " + item.getName());
                }
            } catch (CodecException ex) {
                Logger.getLogger(Participant.class.getName()).log(Level.SEVERE, null, ex);
            } catch (UngroundedException ex) {
                Logger.getLogger(Participant.class.getName()).log(Level.SEVERE, null, ex);
            } catch (OntologyException ex) {
                Logger.getLogger(Participant.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        if (auctionProtocol.equals("EnglishAuction")) {
            addBehaviour(new ParticipantEnglishAuctionBehaviour(this));
        }
        if (auctionProtocol.equals("DutchAuction")) {
            addBehaviour(new ParticipantDutchAuctionBehaviour(this));
        }
    }

    @Override
    protected void takeDown() {
        System.out.println("Participant " + getLocalName() + ": quit from auction.");
    }
    
    private ContentManager getManager() {
        return manager;
    }
    
    public float getPercent() {
        return percent;
    }
    
}
