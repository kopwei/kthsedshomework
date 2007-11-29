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
    private int utility = 0;
    
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
    
    public void setUtility(int utility) {
        this.utility = utility;
    }
    
    public int getUtility() {
        return utility;
    }
}
