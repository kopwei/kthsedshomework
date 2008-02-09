/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package components;

import assignment2.events.CheckTimeoutEvent;
import assignment2.events.CrashEvent;
import assignment2.events.HeartbeatMessage;
import assignment2.events.HeartbeatTimeoutEvent;
import assignment2.events.InitEvent;
import assignments.util.TopologyDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Properties;
import java.util.logging.Level;
import org.apache.log4j.Logger;
import tbn.TBN;
import tbn.api.Component;
import tbn.api.HandlerNotSubscribedException;
import tbn.api.NoSuchMethodException;
import tbn.api.TBNSystem;
import tbn.comm.mina.NodeReference;
import tbn.timer.TimerHandler;

/**
 *
 * @author Ricky
 */
public class PFDComponent {
    private Component component;
    private static Logger log = Logger.getLogger(PFDComponent.class);
    private TopologyDescriptor topologyDescriptor;
    private long gamma;
    private long delta;
    private TimerHandler timerHandler;
    
    private HashSet<NodeReference> aliveSet;
    private HashSet<NodeReference> detectedSet;
    
    public PFDComponent(Component component) {
        this.component = component;
        aliveSet = new HashSet<NodeReference>();
        detectedSet = new HashSet<NodeReference>();
        timerHandler = new TimerHandler(component);
    }
    
    public void init(Object params[]) {
        try {
            Properties properties = new Properties();
            properties.load((InputStream) params[0]);
            gamma = Long.parseLong(properties.getProperty("gamma", "2000"));
            delta = Long.parseLong(properties.getProperty("delta", "4000"));
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(PFDComponent.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
            
    public void handleHeartbeatMessage(HeartbeatMessage messageEvent) {
        log.info("I received a heart beat message ");
        // Add the source to destination
        aliveSet.add(messageEvent.getSource());
    }
    
    public void handleInitEvent(InitEvent event) {
        try {
            log.info("Intializing PFD component");
            this.topologyDescriptor = event.getTopologyDescriptor();
            for (NodeReference ref : topologyDescriptor.getAllOtherNodes()) {
                aliveSet.add(ref);
            }
            detectedSet.clear();
            // TODO: We should start timer here
            //TimerEvent event = new TimerEvent
            TBNSystem sys = TBN.getSystem();
            timerHandler.startTimer(new HeartbeatTimeoutEvent(), "handleHeartbeatTimeoutEvent", gamma);
            timerHandler.startTimer(new CheckTimeoutEvent(), "handleCheckTimeoutEvent", gamma + delta);
        } catch (HandlerNotSubscribedException ex) {
            java.util.logging.Logger.getLogger(PFDComponent.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchMethodException ex) {
            java.util.logging.Logger.getLogger(PFDComponent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void handleCheckTimeoutEvent(CheckTimeoutEvent event) {
        try {
            log.info("I received a check time out event ");
            for (NodeReference ref : topologyDescriptor.getAllOtherNodes()) {
                if (!aliveSet.contains(ref) && !detectedSet.contains(ref)) {
                    detectedSet.add(ref);
                    component.raiseEvent(new CrashEvent("node " + ref.toString() + "crashed"));
                }
            }
            aliveSet.clear();
            // TODO: We should start check timer here
            timerHandler.startTimer(new CheckTimeoutEvent(), "handleCheckTimeoutEvent", gamma + delta);
        } catch (HandlerNotSubscribedException ex) {
            java.util.logging.Logger.getLogger(PFDComponent.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchMethodException ex) {
            java.util.logging.Logger.getLogger(PFDComponent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void handleHeartbeatTimeoutEvent(HeartbeatTimeoutEvent event) {
        try {
            log.info("I received a heart beat time out event ");
            for (NodeReference ref : topologyDescriptor.getAllOtherNodes()) {
                HeartbeatMessage message = new HeartbeatMessage();
                message.setDestination(ref);
                message.setSource(topologyDescriptor.getMyNodeRef());
                component.raiseEvent(message);
            }
            // TODO: We should start a timer to send heart beat
            timerHandler.startTimer(new HeartbeatTimeoutEvent(), "handleHeartbeatTimeoutEvent", gamma);
        } catch (HandlerNotSubscribedException ex) {
            java.util.logging.Logger.getLogger(PFDComponent.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchMethodException ex) {
            java.util.logging.Logger.getLogger(PFDComponent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
