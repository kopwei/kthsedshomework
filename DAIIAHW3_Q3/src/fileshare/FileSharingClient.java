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
import java.util.Collection;
import java.util.Hashtable;

/**
 *
 * @author Kop
 */
public class FileSharingClient extends Agent{
    
    private ArrayList<AID> trackers = new ArrayList<AID>();
    private FileManager fileManager = null;
    private ClientFileSharingBehaviour clientBehaviour = null;
    private ClientDownloadBehaviour downloadBehaviour = null;
    
    private int profit = 0;
    private Hashtable<AID, Integer> benefitTable = new Hashtable<AID, Integer>();
    
    //private HashSet<AID> clientPeers = null;
    
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
        // Read torrent information and start the main behaviour
        TorrentFileParser parser = new TorrentFileParser("bt.xml");
        fileManager = new FileManager(parser.getFileName(), parser.getBlockNumber());
        // Act as seed if the argument is server
        Object[] args = getArguments();
        if (null != args) {
            if (args.length > 0) {
                if (args[0].toString().equals("seed")) {
                    actAsSeed();
                }
            }
        }
        
//        ParallelBehaviour par = new ParallelBehaviour(ParallelBehaviour.WHEN_ALL);
//        addBehaviour(par);
        // Start the main behaviour
        clientBehaviour = new ClientFileSharingBehaviour(this);
        //par.addSubBehaviour(clientBehaviour);
        addBehaviour(clientBehaviour);
        // Start the mdownload behaviour
        downloadBehaviour = new ClientDownloadBehaviour(this);
        //par.addSubBehaviour(downloadBehaviour);
        addBehaviour(downloadBehaviour);
    }
    
    /**
     * Get the client's file manager which handle the file management
     * @return file manager object
     */
    public FileManager getFileManager() {
        return fileManager;
    }
    
    /**
     * Get the client's main behaviour
     * @return client's main behaviour
     */
    public ClientFileSharingBehaviour getClientBehaviour() {
        return clientBehaviour;
    }
    
    /**
     * Get the download behaviour
     * @return clien't download behaviour
     */
    public ClientDownloadBehaviour getDownloadBehaviour() {
        return downloadBehaviour;
    }
    
    /**
     * Get all the available trackers
     * @return the trackers list
     */
    public ArrayList<AID> getTrackers() {
        return trackers;
    }
    
    private void actAsSeed() {
        //System.out.println()
        fileManager.readFullFile();
    }
         
    public void increaseBenefit(AID agent) {
        // Increase the benefit
        Integer benefit = benefitTable.get(agent);
        if (null != benefit) {
            benefit = new Integer(benefit.intValue() + 1);
        }
    }
    
    public int getUtility() {
        Collection<Integer> benefits = benefitTable.values();
        int sumBenefit = 0;
        for (Integer integer : benefits) {
            sumBenefit += integer.intValue();
        }
        return profit - sumBenefit;
    }
    
    public void increaseProfit() {
        profit += 1;
    }
    
    public int getAverageBenefit() {
        // TODO: need implementation here
        return 0;
    }
        
}
