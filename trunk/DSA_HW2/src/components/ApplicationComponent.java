/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package components;

import assignment2.events.CrashEvent;
import assignment2.events.InitEvent;
import assignments.util.TopologyDescriptor;
import assignments.util.TopologyParser;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
            nodeID = Integer.parseInt(properties.getProperty("node.id", "0"));
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
    
    public void start() {
        InitEvent startEvent = new InitEvent(topologyDescriptor);
        component.raiseEvent(startEvent);
        log.info("Raise InitEvent, and start Perfect Failure Detector Algorithm");
        if (nodeID == 0) {
            new ApplicationComponent.ApplicationThread().start();
        }
    }
    
    class ApplicationThread extends Thread {

        @Override
        public void run() {
            System.out.println("Application THREAD RUNNING");

//            while (true) {
//                BufferedReader userIn = new BufferedReader(
//                        new InputStreamReader(System.in));
//                String messageString = null;
//                try {
//                    messageString = userIn.readLine();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//                if (messageString == null) {
//                    continue;
//                }
//                if (messageString.equals("quit")) {
//                    System.exit(0);
//                }
//            }
        }
    }

    public int getNodeID() {
        return nodeID;
    }
}
