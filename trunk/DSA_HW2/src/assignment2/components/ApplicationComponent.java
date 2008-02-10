/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package assignment2.components;

import assignment2.events.CrashEvent;
import assignment2.events.InitEvent;
import assignment2.events.RestoreEvent;
import assignment2.events.SuspectEvent;
import assignments.util.TopologyDescriptor;
import assignments.util.TopologyParser;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.apache.log4j.Logger;
import tbn.api.Component;
import tbn.comm.mina.NodeReference;

/**
 *
 * @author Ricky
 */
public class ApplicationComponent {

    private Component component;
    private static Logger log = Logger.getLogger(ApplicationComponent.class);
    private String topologyFile;
    private TopologyDescriptor topologyDescriptor;
    private TopologyParser topologyParser;
    private int nodeID = 0;

    public ApplicationComponent(Component component) {
        this.component = component;
    }
    
    public void init(Object[] params) {
        try {
            Properties properties = new Properties();
            properties.load((InputStream) params[0]);
            topologyFile = properties.getProperty("topology.file",
                    "topology.xml");
            String strPro = properties.getProperty("node.id", "0");
            nodeID = Integer.parseInt(strPro);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Parsing Topology Descriptor File
        topologyParser = new TopologyParser(nodeID, topologyFile);

        this.topologyDescriptor = topologyParser.parseTopologyFile();
        NodeReference.setThisNodeReference(topologyDescriptor.getMyNodeRef());
    }
    
    public void handleCrashEvent(CrashEvent crashEvent) {
        log.info("Crashing message: " + crashEvent.getCrashEventMessage());
    }
    
    public void handelSuspectEvent(SuspectEvent suspectEvent) {
        //log.info("Crashing message: " + suspectEvent.getCrashEventMessage());
    }
    
    public void handleRestoreEvent(RestoreEvent restoreEvent) {
        
    }
    
    public void start() {
        InitEvent startEvent = new InitEvent(topologyDescriptor);
        component.raiseEvent(startEvent);
        log.info("Raising InitEvent");
        //if (nodeID == 0) {
            new ApplicationComponent.ApplicationThread().start();
        //}
    }
    
    class ApplicationThread extends Thread {

        @Override
        public void run() {
            System.out.println("Application THREAD RUNNING");
        }
    }

    public int getNodeID() {
        return nodeID;
    }
}