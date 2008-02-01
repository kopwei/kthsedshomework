/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package assignment1.events;

import tbn.comm.mina.events.MessageEvent;

/**
 *
 * @author Ricky
 */
public class FloodMessage extends MessageEvent{
    private String message;
    
    public FloodMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
