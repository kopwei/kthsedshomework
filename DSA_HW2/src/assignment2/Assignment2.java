/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package assignment2;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import tbn.TBN;
import tbn.api.ParsingFailedException;
import tbn.api.SystemBuildFailedException;
import tbn.api.TBNSystem;

/**
 *
 * @author Ricky
 */
public class Assignment2 {

    public static void main(String args[]) {
            if (args.length != 3) {
                System.err.println("usage: Assignment2 <topology.xml> <nodeId> pfd|epfd");
                return;
            }
            
        try {
            String topologyFileName = args[0];
            String nodeId = args[1];
            Properties properties = new Properties();
            properties.setProperty("topology.file", topologyFileName);
            properties.setProperty("node.id", nodeId);
            properties.store(new FileOutputStream("node.properties"), null);
            
            String source = null;
            if (args[2].equals("pfd") || args[2].equals("epfd")) {
                if (args[2].equals("pfd")) source = "assignment2-pfd.xml";
                if (args[2].equals("epfd")) source = "assignment2-epfd.xml";
            }
            else{
                System.err.println("usage: Assignment2 <topology.xml> <nodeId> pfd|epfd");
                return;
            }
            TBNSystem sys = TBN.getSystem();
            sys.buildSystem(Assignment2.class.getResource(source).getPath());

        } catch (ParsingFailedException ex) {
            Logger.getLogger(Assignment2.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SystemBuildFailedException ex) {
            Logger.getLogger(Assignment2.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Assignment2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
