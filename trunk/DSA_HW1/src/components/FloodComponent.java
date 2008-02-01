/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package components;

import assignment1.events.FloodDoneEvent;
import assignment1.events.FloodInitEvent;
import assignment1.events.FloodMessage;
import assignment1.events.InitEvent;
import assignments.util.TopologyDescriptor;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import org.apache.log4j.Logger;
import tbn.api.Component;
import tbn.comm.mina.MessageHandler;
import tbn.comm.mina.NodeReference;

/**
 *
 * @author Ricky
 */
public class FloodComponent {

    private static Logger log = Logger.getLogger(FloodComponent.class);
    private Component component;
    private TopologyDescriptor topologyDescriptor;
    private MessageHandler messageHandler;
    private Hashtable<FloodMessage, HashSet<NodeReference>> floodMessageTable;

    public FloodComponent(Component component) {
        this.component = component;
        messageHandler = new MessageHandler(this.component);
        floodMessageTable = new Hashtable<FloodMessage, HashSet<NodeReference>>();
    }

    public void handleInitEvent(InitEvent event) {
        //log.debug("INIT Delay Component");
        this.topologyDescriptor = event.getTopologyDescriptor();
    }

    public void handleFloodInitEvent(FloodInitEvent event) {
        String floodInitEventMsg = event.getFloodInitEventMessage();
        
        System.out.println("FloodComponent: I got a FloodInitEvent message: " + floodInitEventMsg);
        
        FloodMessage floodMessage = new FloodMessage("Flood is coming! Run!!!");
        // send a FloodMessage to all its neighbors
        for (NodeReference nodeRef : topologyDescriptor.getAllOtherNodes()) {
            floodMessage.setDestination(nodeRef);
            floodMessage.setSource(topologyDescriptor.getMyNodeRef());
            component.raiseEvent(floodMessage);
        }
    }

    public void handleFloodMessage(FloodMessage event) {
        // If the message is a totally new message, we have to store it and its source
        if (!floodMessageTable.containsKey(event)) {
            HashSet<NodeReference> sourceSet = new HashSet<NodeReference>();
            sourceSet.add(event.getSource());
            floodMessageTable.put(event, sourceSet);
            // We have to send the event to all other neighbors
            for (NodeReference neighbor : topologyDescriptor.getAllOtherNodes()) {
                event.setDestination(neighbor);
                event.setSource(topologyDescriptor.getMyNodeRef());
                component.raiseEvent(event);
            }
        } // If the message is not new message, we have to store its source
        else {
            HashSet<NodeReference> nodeSet = floodMessageTable.get(event);
            nodeSet.add(event.getSource());
            // If the source number reaches the number of neighbors, we thought it is done
            if (nodeSet.size() == topologyDescriptor.getAllOtherNodes().size()) {
                // Clear the flood table
                Enumeration<HashSet<NodeReference>> enumSets = floodMessageTable.elements();
                while (enumSets.hasMoreElements()) {
                    HashSet<tbn.comm.mina.NodeReference> hashSet = (HashSet<tbn.comm.mina.NodeReference>) enumSets.nextElement();
                    hashSet.clear();
                }
                floodMessageTable.clear();
                // raise the done event
                FloodDoneEvent doneEvent = new FloodDoneEvent();
                component.raiseEvent(doneEvent);
            }
        }
    }
}
