/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package components;

import assignment1.events.FloodInitEvent;
import assignment1.events.FloodMessage;
import assignment1.events.InitEvent;
import assignments.util.TopologyDescriptor;
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

    public FloodComponent(Component component) {
        this.component = component;
        messageHandler = new MessageHandler(this.component);
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
        
    }
}
