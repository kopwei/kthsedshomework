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
        //component.raiseEvent(event);
    }
    
    public void handleFloodMessage(FloodMessage event) {
        
    }
}
