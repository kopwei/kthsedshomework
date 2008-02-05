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
import java.util.HashSet;
import java.util.Hashtable;
import org.apache.log4j.Logger;
import tbn.api.Component;
import tbn.comm.mina.NodeReference;

/**
 *
 * @author Ricky
 */
public class FloodComponent {

    private static Logger log = Logger.getLogger(FloodComponent.class);
    private Component component;
    private TopologyDescriptor topologyDescriptor;
    //private MessageHandler messageHandler;
    private Hashtable<FloodMessage, HashSet<NodeReference>> floodMessageTable;

    public FloodComponent(Component component) {
        this.component = component;
        //messageHandler = new MessageHandler(this.component);
        floodMessageTable = new Hashtable<FloodMessage, HashSet<NodeReference>>();
    }

    public void handleInitEvent(InitEvent event) {
        //log.debug("INIT Delay Component");
        this.topologyDescriptor = event.getTopologyDescriptor();
    }

    public void handleFloodInitEvent(FloodInitEvent event) {
        String floodInitEventMsg = event.getFloodInitEventMessage();
        
        System.out.println(topologyDescriptor.getMyNodeRef() + ": FloodComponent got a FloodInitEvent message: " + floodInitEventMsg);
        FloodMessage floodMessage = null;
        // send a FloodMessage to all its neighbors
        for (NodeReference nodeRef : topologyDescriptor.getAllOtherNodes()) {
            floodMessage = new FloodMessage(topologyDescriptor.getMyNodeRef() + ": Flood is coming! Run!!!");
            floodMessage.setDestination(nodeRef);
            floodMessage.setSource(topologyDescriptor.getMyNodeRef());
            component.raiseEvent(floodMessage);
            log.info("I raised a flood info event to " + floodMessage.getDestination());
        }
        // store this FloodMessage event into FloodMessageTable
        HashSet<NodeReference> _sourceSet = new HashSet<NodeReference>();
        _sourceSet.add(topologyDescriptor.getMyNodeRef());
        floodMessageTable.put(floodMessage, _sourceSet);
    }

    public void handleFloodMessage(FloodMessage event) {
         // If the message is a totally new message, we have to store it and its source
        if (!floodMessageTable.containsKey(event)) {
            log.info("I received a flood message from " + event.getSource());
            HashSet<NodeReference> sourceSet = new HashSet<NodeReference>();
            sourceSet.add(event.getSource());
            floodMessageTable.put(event, sourceSet);
            // We have to send the event to all other neighbors
            for (NodeReference neighbor : topologyDescriptor.getAllOtherNodes()) {
                FloodMessage newEvent = event.clone();
                newEvent.setDestination(neighbor);
                component.raiseEvent(newEvent);
                 log.info("I raised the flood message to the neighbor " + neighbor);
            }
        } // If the message is not new message, we have to store its source
        else {
            HashSet<NodeReference> nodeSet = floodMessageTable.get(event);
            nodeSet.add(event.getSource());
            // If the source number reaches the number of neighbors, we thought it is done
            if (nodeSet.size() == topologyDescriptor.getAllOtherNodes().size() + 1) {
                // Clear the corresponding hashset
            	nodeSet.clear();
                floodMessageTable.remove(event);
                FloodDoneEvent doneEvent = new FloodDoneEvent(event.getMessage());
                component.raiseEvent(doneEvent);
                log.info("I raised the done info");
            }
        }
    }
}
