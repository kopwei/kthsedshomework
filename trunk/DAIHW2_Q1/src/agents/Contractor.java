/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package agents;

import jade.core.Agent;
import jade.core.behaviours.*;
import jade.core.behaviours.ReceiverBehaviour.NotYetReady;
import jade.core.behaviours.ReceiverBehaviour.TimedOut;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ricky
 */
public class Contractor extends Agent{
    private final int lowestLine = 0;
    private int initialPrice = 0;
    
    @Override
    protected void setup() {
        // DF service registration
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setType("jade-contractor");
        dfd.addServices(sd);
        
        try {
            DFService.register(this, dfd);
        }
        catch (FIPAException fe) {fe.printStackTrace();}
        
        try {
            System.out.println(getLocalName() + ": Please set the lowest line of offer.");
            BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
            initialPrice = Integer.parseInt(buffer.readLine());

            ACLMessage firstRequest = blockingReceive(MessageTemplate.MatchPerformative(ACLMessage.REQUEST));
            ACLMessage firstOffer = firstRequest.createReply();
            Random rnd = new Random(System.currentTimeMillis());
            initialPrice = lowestLine + rnd.nextInt(30) + 1;
            firstOffer.setPerformative(ACLMessage.INFORM);
            firstOffer.setContent(Integer.toString(initialPrice));
            send(firstOffer);

            addBehaviour(new CyclicBehaviour(this) {

                private ReceiverBehaviour be = new ReceiverBehaviour(myAgent, -1, MessageTemplate.MatchPerformative(ACLMessage.INFORM));

                @Override
                public void action() {
                    addBehaviour(be);
                    ACLMessage msg = null;

                    if (be.done()) {
                        try {
                            msg = be.getMessage();
                        } catch (TimedOut ex) {
                            Logger.getLogger(Contractor.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (NotYetReady ex) {
                            Logger.getLogger(Contractor.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    if (msg != null) {
                        String lowestPrice = msg.getContent();
                        int lp = Integer.parseInt(lowestPrice);
                        int nextOffer = lp - 1;
                        // can I make a better offer?
                        ACLMessage reply = msg.createReply();

                        if (lowestLine <= nextOffer) {
                            // create the reply
                            reply.setPerformative(ACLMessage.INFORM);
                            reply.setContent(Integer.toString(nextOffer));
                        } else {
                            reply.setPerformative(ACLMessage.REFUSE);
                            return;
                        }
                        send(reply);
                    }
                }
            });
        } catch (IOException ex) {
            Logger.getLogger(Contractor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }   
}
