/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package assignment1.events;

import tbn.api.Event;

/**
 *
 * @author Ricky
 */
public class FloodMessage implements Event{
    private String message;
    
    public FloodMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
