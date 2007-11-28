/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package agents;

import jade.content.ContentManager;
import jade.content.lang.Codec;
import jade.content.lang.leap.LEAPCodec;
import jade.content.onto.Ontology;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import java.util.logging.Level;
import java.util.logging.Logger;
import ontologies.AuctionOntology;

/**
 *
 * @author Kop
 */
public class Participant extends Agent{
    private String auctionProtocol = null;
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
        
        if (auctionProtocol.equals("EnglishAuction")) {
            addBehaviour(new ParticipantEnglishAuctionBehaviour());
        }
        if (auctionProtocol.equals("DutchAuction")) {
            addBehaviour(new ParticipantDutchAuctionBehaviour());
        }
    }
    
}
