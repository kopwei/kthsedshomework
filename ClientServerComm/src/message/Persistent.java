/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package message;

import java.io.IOException;

/**
 *
 * @author Kop
 */
public interface Persistent {
    /**
     * Called to persist an object
     */
    byte[] persist() throws IOException;
    
    /**
     * Called to resurrect a persistent object
     */    
    void resurrect(byte[] data) throws IOException;
}
