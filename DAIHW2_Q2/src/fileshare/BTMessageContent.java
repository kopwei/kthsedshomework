/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fileshare;

import jade.core.AID;
import jade.util.leap.Serializable;
import java.util.ArrayList;
import java.util.HashSet;



/**
 *
 * @author Kop
 */
public class BTMessageContent implements Serializable{
    private HashSet<AID> aidCollection = null;
    private ArrayList<Integer> blockNumbers = null;
    private String fileName = null;
    private String blockContent = null;
    private int blockIndex = -1;
    
    /**
     * 
     * @param aidCol
     */
    public void setAIDCollection(HashSet<AID> aidCol) {
        aidCollection = aidCol;
    }
    
    /**
     * 
     * @param numbers
     */
    public void setBlockNumbers(ArrayList<Integer> numbers) {
        blockNumbers = numbers;
    }
    
    /**
     * 
     * @param fileName
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    
    /**
     * 
     * @param obj
     */
    public void setBlockContent(String obj) {
        blockContent = obj;
    }
    
    public void setBlockIndex(int index) {
        blockIndex = index;
    }
    /**
     * 
     * @return
     */
    public HashSet<AID> getAIDCollection() {
        return aidCollection;
    }
    
    /**
     * 
     * @return
     */
    public ArrayList<Integer> getLostBlockNumber() {
        return blockNumbers;
    }
    
    /**
     * 
     * @return
     */
    public String getFileName() {
        return fileName;
    }
   
    /**
     * 
     * @return
     */
    public String getBlockContent() {
        return blockContent;
    }
    
    public int getBlockIndex() {
        return blockIndex;
    }
    /**
     * This method is used to get the set of AID from the message content
     * @param msg input message
     * @return the HashSet of the AID
     */
//    public static HashSet<AID> getAIDFromMessage(ACLMessage msg) {
//        // Step 1) Check the message's validity
//        if (null == msg) {
//            return null;
//        }
//        HashSet<AID> peers = new HashSet<AID>();
//        String content = msg.getContent();
//        String[] aidString = content.split(",");
//        for (String string : aidString) {
//            peers.add(new AID(string, true));
//        }
//        return peers;
//    }
//    
//    public static String getFileNameFromMessage(ACLMessage msg) {
//        
//    }
//    
//    public static ArrayList<Integer> getLostBlocksNumberFromMessage(ACLMessage msg) {
//        // Step 1) Check the message's validity
//        if (null == msg) {
//            return null;
//        }
//        ArrayList<Integer> lostBlockNumbers = new ArrayList<Integer>();
//        String[] numbers = msg.getContent().split(",");
//        msg.setC
//        return lostBlockNumbers;
//    }
}
