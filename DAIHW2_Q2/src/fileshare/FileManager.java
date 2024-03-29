/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fileshare;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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
        fileBlocks = new ArrayList<String>();
        for (int i = 0; i < blockNumber; i++) {
            fileBlocks.add(null);
        }
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
        if (null != fileBlocks) {
            fileBlocks.set(index, blockContent);
        }
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
        return (fileBlocks.get(index) != null);
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
        
        int size = fileBlocks.size();
        ArrayList<Integer> lostBlocks = new ArrayList<Integer>();
        // Iterate the file blocks and store the lost block numbers into the array list
        for (int i = 0; i < size; i++) {
            if (fileBlocks.get(i) == null) {
                lostBlocks.add(new Integer(i));
            }
        }
        ArrayList<Integer> returnArray = new ArrayList<Integer>(lostBlocks.size());
        for (Integer integer : lostBlocks) {
            if(null != integer) {
                returnArray.add(integer);
            }
        }

        return returnArray;
    }
    
    public void readFullFile() {
        try {
            FileReader fstream = new FileReader(fileName);
            BufferedReader reader = new BufferedReader(fstream);
            String block = null;
            for (int i = 0; i < fileBlocks.size(); i++) {
                block = reader.readLine();
                if (null != block) {
                    insertBlock(i, block);
                }
            } 
        }
        catch(IOException ie) {
            System.err.println(ie.getMessage());
            System.exit(0);
        }
    }
    
    public void writeFullFile() {
        
    }
}
