/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package agents;

import jade.content.ContentElement;
import jade.content.ContentManager;
import jade.content.lang.Codec.CodecException;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.OntologyException;
import jade.content.onto.UngroundedException;
import jade.content.onto.basic.Action;
import jade.content.onto.basic.Result;
import jade.core.AID;
import jade.core.Agent;
import jade.core.Location;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.domain.JADEAgentManagement.QueryPlatformLocationsAction;
import jade.domain.mobility.CloneAction;
import jade.domain.mobility.MobileAgentDescription;
import jade.domain.mobility.MobilityOntology;
import jade.domain.mobility.MoveAction;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.ControllerException;
import jade.wrapper.StaleProxyException;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ricky
 */
public class ControllerAgent extends Agent{
    private AgentContainer home;
    private AgentContainer[] container = null;
    private Map locations = new HashMap();
    private Vector<AID> agents = new Vector<AID>();
    private Vector<String> containerName = new Vector<String>();
    // Get a JADE Runtime instance
    Runtime runtime = Runtime.instance();

    @Override
    protected void setup() {
        try {
            // Register languager and ontology
            ContentManager manager = getContentManager();
            manager.registerLanguage(new SLCodec());
            manager.registerOntology(MobilityOntology.getInstance());

            // Create the container objects
            home = runtime.createAgentContainer(new ProfileImpl());
            container = new AgentContainer[2];
            container[0] = runtime.createAgentContainer(new ProfileImpl());
            container[1] = runtime.createAgentContainer(new ProfileImpl());
            doWait(2000);

            //Get available locations with AMS
            sendRequest(new Action(getAMS(), new QueryPlatformLocationsAction()));

            // Receiver response from AMS
            MessageTemplate mt = MessageTemplate.and(MessageTemplate.MatchSender(getAMS()), MessageTemplate.MatchPerformative(ACLMessage.INFORM));
            ACLMessage resp = blockingReceive(mt);
            ContentElement ce = getContentManager().extractContent(resp);
            Result result = (Result) ce;
            for (Iterator it = result.getItems().iterator(); it.hasNext();) {
                Location loc = (Location) it.next();
                locations.put(loc.getName(), loc);
            }
            Location homeLoc = (Location) locations.get(home.getContainerName());
            
            /**
             * Create new agent ---- contractor manager
             */ 
            AgentController ac1 = null;
            String name1 = "Manager";
            Object[] args1 = new Object[2];
            args1[0] = getAID();
            //args1[1] = 
            ac1 = home.createNewAgent(name1, ContractManager.class.getName(), args1);
            ac1.start();
            AID aid1 = new AID(name1, AID.ISLOCALNAME);
            agents.add(0, aid1);
            doWait(2000);
            
            /**
             * Create new agent ---- contractor1
             */ 
            AgentController ac2 = null;
            String name2 = "Contractor1";
            Object[] args2 = new Object[2];
            args2[0] = getAID();
            //args2[1] = "";
            ac2 = home.createNewAgent(name2, Contractor.class.getName(), args2);
            ac2.start();
            AID aid2 = new AID(name2, AID.ISLOCALNAME);
            agents.add(1, aid2);
            doWait(2000);
                        
            /**
             * Create new agent ---- contractor2
             */ 
            AgentController ac3 = null;
            String name3 = "Contractor2";
            Object[] args3 = new Object[2];
            args3[0] = getAID();
            //args3[1] = 
            ac3 = home.createNewAgent(name3, Contractor.class.getName(), args3);
            ac3.start();
            AID aid3 = new AID(name3, AID.ISLOCALNAME);
            agents.add(2, aid3);
            doWait(2000);
            
            // Clone manager to two containers
            String destName1 = container[0].getContainerName();
            String destName2 = container[1].getContainerName();
            Location dest1 = (Location) locations.get(destName1);
            Location dest2 = (Location) locations.get(destName2);
            MobileAgentDescription mad1 = new MobileAgentDescription();
            MobileAgentDescription mad2 = new MobileAgentDescription();
            mad1.setName(aid1);
            mad1.setDestination(dest1);
            mad2.setName(aid1);
            mad2.setDestination(dest2);
            String newName1 = "Clone1-" + name1;
            String newName2 = "Clone2-" + name1;
            CloneAction ca1 = new CloneAction();
            CloneAction ca2 = new CloneAction();
            ca1.setNewName(newName1);
            ca1.setMobileAgentDescription(mad1);
            ca2.setNewName(newName2);
            ca2.setMobileAgentDescription(mad2);
            sendRequest(new Action(aid1, ca1));
            sendRequest(new Action(aid1, ca2));
            AID aid4 = new AID(newName1, AID.ISLOCALNAME);
            AID aid5 = new AID(newName2, AID.ISLOCALNAME);
            agents.add(3, aid4);
            agents.add(4, aid5);
            doWait(2000);
            
            // clone contractor1 to container1, contractor2 to container2
            MobileAgentDescription mad3 = new MobileAgentDescription();
            MobileAgentDescription mad4 = new MobileAgentDescription();
            mad3.setName(aid2);
            mad3.setDestination(dest1);
            mad4.setName(aid3);
            mad4.setDestination(dest2);
            String newName3 = "Clone-" + name2;
            String newName4 = "Clone-" + name3;
            CloneAction ca3 = new CloneAction();
            CloneAction ca4 = new CloneAction();
            ca3.setNewName(newName3);
            ca3.setMobileAgentDescription(mad3);
            ca4.setNewName(newName4);
            ca4.setMobileAgentDescription(mad4);
            sendRequest(new Action(aid2, ca3));
            sendRequest(new Action(aid3, ca4));
            AID aid6 = new AID(newName3, AID.ISLOCALNAME);
            AID aid7 = new AID(newName4, AID.ISLOCALNAME);
            agents.add(5, aid6);
            agents.add(6, aid7);
            doWait(2000);
 // agents[] = {"Manager", "Contractor1", "Contractor2", "Clone1-Manager", "Clone2-Manager", "Clone-Contractor1", "Clone-Contractor2"}
            
            // move contractor1 to container1
            MobileAgentDescription madForCon1 = new MobileAgentDescription();
            madForCon1.setName(aid2);
            madForCon1.setDestination(dest1);
            MoveAction maForCon1 = new MoveAction();
            maForCon1.setMobileAgentDescription(madForCon1);
            sendRequest(new Action(aid2, maForCon1));
            doWait(2000);
            // move contractor2 to container2
            MobileAgentDescription madForCon2 = new MobileAgentDescription();
            madForCon2.setName(aid3);
            madForCon2.setDestination(dest2);
            MoveAction maForCon2 = new MoveAction();
            maForCon2.setMobileAgentDescription(madForCon2);
            sendRequest(new Action(aid3, maForCon2));
            doWait(2000);
            
            // tell contractors and clone-contractors to begin setting lowest line
            ACLMessage aclm = new ACLMessage(ACLMessage.UNKNOWN);
            aclm.addReceiver(aid2);
            aclm.addReceiver(aid3);
            aclm.addReceiver(aid6);
            aclm.addReceiver(aid7);
            send(aclm);
            
            // send aids of contractor1 and clone-contractor1 to clone1-manager
            HashSet<AID> aidC1 = new HashSet<AID>();
            aidC1.add(aid2);
            aidC1.add(aid6);
            AIDMessageContent aidMC = new AIDMessageContent();
            aidMC.setAIDCollection(aidC1);
            ACLMessage msgToCloneManager1 = new ACLMessage(ACLMessage.INFORM_REF);
            msgToCloneManager1.setContentObject(aidMC);
            msgToCloneManager1.addReceiver(aid4);
            send(msgToCloneManager1);
            // send aids of contractor2 and clone-contractor2 to clone2-manager
            aidC1.clear();
            aidC1.add(aid3);
            aidC1.add(aid7);
            aidMC.setAIDCollection(aidC1);
            ACLMessage msgToCloneManager2 = new ACLMessage(ACLMessage.INFORM_REF);
            msgToCloneManager2.setContentObject(aidMC);
            msgToCloneManager2.addReceiver(aid5);
            send(msgToCloneManager2);

            // send request to move clone-manager to home container
            int i = 0;
            while (i < 2) {
                ACLMessage msg = blockingReceive(MessageTemplate.MatchPerformative(ACLMessage.INFORM));
                MobileAgentDescription mad = new MobileAgentDescription();
                mad.setName(msg.getSender());
                mad.setDestination(homeLoc);
                MoveAction ma = new MoveAction();
                ma.setMobileAgentDescription(mad);
                sendRequest(new Action(msg.getSender(), ma));
                i++;
                // pass aid of manager to clones
                AIDMessageContent aidmc = new AIDMessageContent();
                aidmc.setTheAID(aid1);
                ACLMessage _msg = new ACLMessage(ACLMessage.INFORM_IF);
                _msg.setContentObject(aidmc);
                _msg.addReceiver(msg.getSender());
                send(_msg);
            }
        } catch (ControllerException ex) {
            Logger.getLogger(ControllerAgent.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ControllerAgent.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CodecException ex) {
            Logger.getLogger(ControllerAgent.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UngroundedException ex) {
            Logger.getLogger(ControllerAgent.class.getName()).log(Level.SEVERE, null, ex);
        } catch (OntologyException ex) {
            Logger.getLogger(ControllerAgent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void sendRequest(Action action) {
            ACLMessage request = new ACLMessage(ACLMessage.REQUEST);
            request.setLanguage(new SLCodec().getName());
            request.setOntology(MobilityOntology.getInstance().getName());
        try {
            getContentManager().fillContent(request, action);
            request.addReceiver(action.getActor());
            send(request);
        } catch (CodecException ex) {
            Logger.getLogger(ControllerAgent.class.getName()).log(Level.SEVERE, null, ex);
        } catch (OntologyException ex) {
            Logger.getLogger(ControllerAgent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
}
