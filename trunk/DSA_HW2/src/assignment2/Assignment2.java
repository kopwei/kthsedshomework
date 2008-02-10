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

/**
 * put topology.xml and pfd.properties under the directory "..\dist\" or "..\classes\", and 
 * use command "FOR /L %G IN (0, 1, 2) DO start java assignment2.Assignment2 topology.xml %G pfd"
 * on cmd (under current directory) to start 3 processes, but 3 processes all are about node 0, 
 * supposing that 3 processes read the same node.properties file with same data as the interval of 
 * 3 processes starting is very short.
 * Another way: under the directory same as told above, using 3 console windows to start 3 different
 * processes for 3 nodes respectively. It can work!
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
