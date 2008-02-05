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
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    @Override
    public boolean equals(Object message) {
    	FloodMessage fMessage = (FloodMessage)message;
    	if (null == fMessage)
    		return false;
    	return (fMessage.getMessage().equals(this.message));
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 19 * hash + (this.message != null ? this.message.hashCode() : 0);
        return hash;
    }

    @Override
    public FloodMessage clone() {
        FloodMessage msg = (FloodMessage) super.clone();
        msg.setMessage(message);
        return msg;
    }
}
