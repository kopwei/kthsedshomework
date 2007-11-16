/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fileshare;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.SimpleBehaviour;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
/**
 *
 * @author Ricky
 */
public class ClientDownloadBehaviour extends SimpleBehaviour{
    //private HashSet<AID> peerSet = new HashSet<AID>();
    private FileSharingClient clientAgent = new FileSharingClient();
    
    public ClientDownloadBehaviour(Agent client) {
        this.clientAgent = (FileSharingClient) client;
    }
    
    @Override
    public void action() {
        HashSet<AID> peerSet  = clientAgent.getClientBehaviour().getPeerSet();
        int length = peerSet.size();
        Random rnd = new Random(System.currentTimeMillis());
        // choose a random position/peer in peerSet to start to ask for block-downloading
        int startPosition = rnd.nextInt(length);
        ArrayList<AID> pl = new ArrayList(peerSet);
        for (int i = startPosition; i < pl.size(); i++) {
            AID peer = pl.get(i);
            String fileName = clientAgent.getFileManager().getFileName();
            ArrayList lostBlocks = clientAgent.getFileManager().getLostBlockNumbers();
            
        }
    }

    @Override
    public boolean done() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
