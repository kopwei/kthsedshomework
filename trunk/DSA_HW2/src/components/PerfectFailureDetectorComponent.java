/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package components;

import assignments.util.TopologyDescriptor;
import org.apache.log4j.Logger;
import tbn.api.Component;

/**
 *
 * @author Ricky
 */
public class PerfectFailureDetectorComponent {
    private Component component;
    private static Logger log = Logger.getLogger(PerfectFailureDetectorComponent.class);
    private TopologyDescriptor topologyDescriptor;
    
    public void handleHeartbeatMessage() {
        
    }
    
    public void handleInitEvent() {
        
    }

    public void handleCheckTimeoutEvent() {
        
    }
    
    public void handleHeartbeatTimeoutEvent() {
        
    }
}
