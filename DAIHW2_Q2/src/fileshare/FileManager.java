/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fileshare;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Kop
 */
public class FileManager {
    private String fileName = null;
    private ArrayList<String> fileBlocks = null;
    
    public FileManager(String fileName, int blockNumber){
        this.fileName = fileName;
        fileBlocks = new ArrayList<String>(blockNumber);
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
     * @param index
     * @param blockContent
     */
    public void insertBlock(int index, String blockContent) {
        fileBlocks.set(index, blockContent);
    }
    
    /**
     * 
     * @param index
     * @return
     */
    public String getBlockAt(int index) {
        return fileBlocks.get(index);
    }
    
    /**
     * Check if one of the block is available
     * @param index
     * @return
     */
    public boolean isBlockAvailable(int index) {
        return (fileBlocks.get(index) == null);
    }
    
    /**
     * 
     * @return bool value indicates if all the blocks are empty
     */
    public boolean isEmpty() {
        boolean isEmpty = true;
        // Iterate the file blocks and check if any of the block is not null
        for (Iterator<String> it = fileBlocks.iterator(); it.hasNext();) {
            if (null != it.next()) {
                isEmpty = false;
                break;
            }
        }
        return isEmpty;
    }
    
    /**
     * 
     * @return
     */
    public boolean isFull() {
        boolean isFull = true;
        // Iterate the file blocks and check if any of the block is not null
        for (Iterator<String> it = fileBlocks.iterator(); it.hasNext();) {
            if (null == it.next()) {
                isFull = false;
                break;
            }
        }
        return isFull;
    }
    
    /**
     * 
     * @return
     */
    public ArrayList<Integer> getLostBlockNumbers() {
        ArrayList<Integer> lostBlocks = new ArrayList<Integer>();
        // Iterate the file blocks and store the lost block numbers into the array list
        for (int i = 0; i < fileBlocks.size(); i++) {
            if (fileBlocks.get(i) == null) {
                lostBlocks.add(new Integer(i));
            }
        }
        return lostBlocks;
    }   
}
