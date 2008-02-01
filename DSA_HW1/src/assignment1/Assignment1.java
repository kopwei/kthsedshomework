package assignment1;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import tbn.TBN;
import tbn.api.ParsingFailedException;
import tbn.api.SystemBuildFailedException;
import tbn.api.TBNSystem;

public class Assignment1 {

    /**
     * @param args
     */
    public static void main(String[] args) {

        if (args.length != 2) {
            System.err.println("usage: Assignment1 <topology.xml> <nodeId>");
            return;
        }

        try {
            String topologyFileName = args[0];
            String nodeId = args[1];
            Properties properties = new Properties();
            properties.setProperty("topology.file", topologyFileName);
            properties.setProperty("node.id", nodeId);
            properties.store(new FileOutputStream("node.properties"), null);

            TBNSystem sys = TBN.getSystem();
            sys.buildSystem(Assignment1.class.getResource("assignment1.xml").getPath());
            sys.startReconfigurationServer();
        } catch (ParsingFailedException e) {
            e.printStackTrace();
        } catch (SystemBuildFailedException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
