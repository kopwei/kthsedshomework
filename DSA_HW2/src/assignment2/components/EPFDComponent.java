/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package assignment2.components;

import assignment2.events.HeartbeatMessage;
import assignment2.events.InitEvent;
import assignment2.events.RestoreEvent;
import assignment2.events.SuspectEvent;
import assignment2.events.TimeoutEvent;
import assignments.util.TopologyDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Properties;
import java.util.logging.Level;
import org.apache.log4j.Logger;
import tbn.api.Component;
import tbn.api.HandlerNotSubscribedException;
import tbn.api.NoSuchMethodException;
import tbn.comm.mina.NodeReference;
import tbn.timer.TimerHandler;

/**
 *
 * @author Kop
 */
public class EPFDComponent {
    private Component component;
    private static Logger log = Logger.getLogger(EPFDComponent.class);
    private TopologyDescriptor topologyDescriptor;
    private long period;
    private long delta;
    private TimerHandler timerHandler;
    
    private HashSet<NodeReference> aliveSet;
    private HashSet<NodeReference> suspectedSet;
    
    public EPFDComponent(Component component) {
        this.component = component;
        aliveSet = new HashSet<NodeReference>();
        suspectedSet = new HashSet<NodeReference>();
    }
    
    public void init(Object params[]) {
        try {
            suspectedSet.clear();
            aliveSet.clear();
            Properties properties = new Properties();
            properties.load((InputStream) params[0]);
            period = Long.parseLong(properties.getProperty("period", "6000"));
            delta = Long.parseLong(properties.getProperty("delta", "4000"));
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }
    
    public void handleHeartBeatMessage(HeartbeatMessage message) {
        log.info("I received a heart beat message from " + message.getSource());
        // Add the source to destination
        aliveSet.add(message.getSource());
    }
    
    public void handleInitMessage(InitEvent event) {        
        log.info("Intializing PFD component");
        this.topologyDescriptor = event.getTopologyDescriptor();
        aliveSet.clear();
        for (NodeReference ref : topologyDescriptor.getAllOtherNodes()) {
            aliveSet.add(ref);
        }
        suspectedSet.clear();
        // TODO: have to set period
        try {
            timerHandler.startTimer(new TimeoutEvent(), "handleTimeoutEvent", period);
        } catch (NoSuchMethodException ex) {
            System.err.println(ex.getMessage());
        } catch (HandlerNotSubscribedException ex) {
            System.err.println(ex.getMessage());
        }
    }
    
    public void handelTimoutEvent(TimeoutEvent event) {
        boolean hasCommon = false;
        for (NodeReference aliveRef : aliveSet) {
            if (suspectedSet.contains(aliveRef)) {
                hasCommon = true;
                break;
            }
        }
        if (hasCommon)
            period += delta;
        
        for (NodeReference ref : topologyDescriptor.getAllOtherNodes()) {
            if (!aliveSet.contains(ref) && !suspectedSet.contains(ref)) {
                suspectedSet.add(ref);
                SuspectEvent susEvent = new SuspectEvent("Node " + ref + " is suspected to crash", ref);
                component.raiseEvent(susEvent);
            } else if (aliveSet.contains(ref) && suspectedSet.contains(ref)) {
                suspectedSet.remove(ref);
                RestoreEvent resEvent = new RestoreEvent("Node " + ref + " is restored from suspision", ref);
                component.raiseEvent(resEvent);
            }
            HeartbeatMessage heartBeat = new HeartbeatMessage();
            heartBeat.setSource(topologyDescriptor.getMyNodeRef());
            heartBeat.setDestination(ref);
            component.raiseEvent(heartBeat);
        }
        aliveSet.clear();
        try {
            timerHandler.startTimer(new TimeoutEvent(), "handleTimeoutEvent", period);
        } catch (NoSuchMethodException ex) {
            System.err.println(ex.getMessage());
        } catch (HandlerNotSubscribedException ex) {
            System.err.println(ex.getMessage());
        }
    }
}
