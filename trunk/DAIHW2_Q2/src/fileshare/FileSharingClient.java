/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fileshare;

import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.SearchConstraints;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import java.util.ArrayList;
import java.util.HashSet;

/**
 *
 * @author Kop
 */
public class FileSharingClient extends Agent{
    
    private ArrayList<AID> trackers = new ArrayList<AID>();
    private FileManager fileManager = null;
    private HashSet<AID> clientPeers = null;
    
    @Override
    protected void setup() {
        try {
            // Prepare the search dfd template used for searching tracker
            DFAgentDescription templateDFD = new DFAgentDescription();
            ServiceDescription templateSD = new ServiceDescription();
            templateSD.setType("bt-tracker");
            
            SearchConstraints sc = new SearchConstraints();
            sc.setMaxResults(10L);
            
            // Search for trackers and add the tracker into tracker list
            DFAgentDescription[] searchResults = DFService.search(this, templateDFD, sc);
            
            for (DFAgentDescription dFAgentDescription : searchResults) {
                trackers.add(dFAgentDescription.getName());
            }       
        }
        catch (FIPAException fe) {
            System.out.println(fe.getMessage());
            System.exit(1);
        }
        TorrentFileParser parser = new TorrentFileParser("bt.xml");
        FileManager manager = new FileManager(parser.getFileName(), parser.getBlockNumber());
        
        
    }
    
    public FileManager getFileManager() {
        return fileManager;
    }
    
    public HashSet<AID> getPeersList() {
        return clientPeers;
    }
    
    public void setPeersList(HashSet peersList) {
        clientPeers = peersList;
    }
}
