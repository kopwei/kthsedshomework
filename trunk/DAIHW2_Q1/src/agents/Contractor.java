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
    private int lowestLine = 0;
    private int initialPrice = 0;
    
    @Override
    protected void setup() {
        // set the lowest line
        try {
            System.out.println(getLocalName() + ": Please set the lowest line of offer.");
            BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
            lowestLine = Integer.parseInt(buffer.readLine());
        } catch (IOException ex) {
            Logger.getLogger(Contractor.class.getName()).log(Level.SEVERE, null, ex);
        }
        // DF service registration
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setType("jade-contractor");
        sd.setName("HW2-Q1-contractor");
        dfd.addServices(sd);
        
        try {
            DFService.register(this, dfd);
        }
        catch (FIPAException fe) {fe.printStackTrace();}
       
        ACLMessage firstRequest = blockingReceive(MessageTemplate.MatchPerformative(ACLMessage.REQUEST));
        ACLMessage firstOffer = firstRequest.createReply();
        Random rnd = new Random(System.currentTimeMillis());
        initialPrice = lowestLine + rnd.nextInt(15) + 1;
        firstOffer.setPerformative(ACLMessage.INFORM);
        firstOffer.setContent(Integer.toString(initialPrice));
        System.out.println(getLocalName() + ": My first offer is " + firstOffer);
        send(firstOffer);

        addBehaviour(new CyclicBehaviour(this) {
            //private ReceiverBehaviour be = new ReceiverBehaviour(myAgent, -1, MessageTemplate.MatchPerformative(ACLMessage.INFORM));
            @Override
            public void action() {
                ACLMessage msg = myAgent.blockingReceive(MessageTemplate.MatchPerformative(ACLMessage.INFORM));

                if (msg != null) {
                    String lowestPrice = msg.getContent();
                    System.out.println(myAgent.getLocalName() + ": get manager's msg, content is " + lowestPrice);
                    
                    int lp = Integer.parseInt(lowestPrice);
                    int nextOffer = lp - 1;
                    // can I make a better offer?
                    ACLMessage reply = msg.createReply();

                    if (lowestLine <= nextOffer) {
                        // create the reply
                        System.out.println(myAgent.getLocalName() + ": I can accept, my new offer is " + nextOffer);
                        reply.setPerformative(ACLMessage.INFORM);
                        reply.setContent(Integer.toString(nextOffer));
                    } else {
                        System.out.println(myAgent.getLocalName() + ": I can not accept.");
                        reply.setPerformative(ACLMessage.REFUSE);
                    }
                    send(reply);
                }
            }
        });
    }
}
