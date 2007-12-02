/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package agents;

import jade.content.Concept;
import jade.content.ContentElement;
import jade.content.lang.Codec.CodecException;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.OntologyException;
import jade.content.onto.UngroundedException;
import jade.content.onto.basic.Action;
import jade.core.AID;
import jade.core.Agent;
import jade.core.Location;
import jade.core.behaviours.*;
import jade.domain.mobility.CloneAction;
import jade.domain.mobility.MobilityOntology;
import jade.domain.mobility.MoveAction;
import jade.lang.acl.ACLMessage;
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
public class Contractor extends Agent {

    private int lowestLine = 0;
    private int initialPrice = 0;
    private Location destination;
    private AID controller;

    @Override
    protected void setup() {
        try {
            Object[] args = getArguments();
            if (null == args) {
                doDelete();
            }
            controller = (AID) args[0];
            destination = here();
            // Register languager and ontology
            getContentManager().registerLanguage(new SLCodec());
            getContentManager().registerOntology(MobilityOntology.getInstance());
        } catch (Exception ex) {
            Logger.getLogger(Contractor.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println(getLocalName() + " is created.");

        addBehaviour(new CyclicBehaviour(this) {

                    //private ReceiverBehaviour be = new ReceiverBehaviour(myAgent, -1, MessageTemplate.MatchPerformative(ACLMessage.INFORM));

                    @Override
                public void action() {
                        try {
                            ACLMessage msg = myAgent.blockingReceive();
                            System.out.println(getLocalName() + ": got a message from " + msg.getSender().getLocalName());
                            switch (msg.getPerformative()) {
                                case ACLMessage.REQUEST:
                                    if (msg.getSender().equals(controller)) {
                                        try {
                                            ContentElement content = getContentManager().extractContent(msg);
                                            Concept concept = ((Action) content).getAction();
                                            if (concept instanceof CloneAction) {
                                                CloneAction ca = (CloneAction) concept;
                                                String newName = ca.getNewName();
                                                Location location = ca.getMobileAgentDescription().getDestination();
                                                destination = location;
                                                System.out.println(getLocalName() + ": got a clone action to " + destination.getName());
                                                doClone(location, newName);
                                            }
                                            if (concept instanceof MoveAction) {
                                                MoveAction ma = (MoveAction) concept;
                                                Location location = ma.getMobileAgentDescription().getDestination();
                                                System.out.println(getLocalName() + ": got a move action to " + destination.getName());
                                                doMove(destination = location);
                                            }
                                        } catch (CodecException ex) {
                                            Logger.getLogger(Contractor.class.getName()).log(Level.SEVERE, null, ex);
                                        } catch (UngroundedException ex) {
                                            Logger.getLogger(Contractor.class.getName()).log(Level.SEVERE, null, ex);
                                        } catch (OntologyException ex) {
                                            Logger.getLogger(Contractor.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                    } else {
                                        ACLMessage firstOffer = msg.createReply();
                                        firstOffer.setPerformative(ACLMessage.INFORM);
                                        firstOffer.setContent(Integer.toString(initialPrice));
                                        System.out.println(getLocalName() + ": My first offer is " + firstOffer);
                                        send(firstOffer);
                                    }
                                    break;
                                case ACLMessage.INFORM:
                                    if (msg != null) {
                                        String lowestPrice = msg.getContent();
                                        System.out.println(myAgent.getLocalName() + ": get manager's msg, content is " + lowestPrice);

                                        int lp = Integer.parseInt(lowestPrice);
                                        int nextOffer = lp - 1;

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
                                    break;
                                case ACLMessage.UNKNOWN:
                                    // set the lowest line
                                    System.out.println(getLocalName() + ": Please set the lowest offer:");
                                    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                                    lowestLine = Integer.parseInt(br.readLine());

                                    Random rnd = new Random(System.currentTimeMillis());
                                    initialPrice = lowestLine + rnd.nextInt(10) + 1;
                                    break;
                                default:
                                    break;
                            }
                        } catch (IOException ex) {
                            Logger.getLogger(Contractor.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });
    }
}
