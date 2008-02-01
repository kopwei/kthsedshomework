/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment1.events;

/**
 *
 * @author Kop
 */
public class FloodInitEvent implements tbn.api.Event {
    private String floodInitEventMsg;

    public FloodInitEvent(String message) {
        this.floodInitEventMsg = message;
    }
    
    public String getFloodInitEventMessage() {
        return floodInitEventMsg;
    }
}
