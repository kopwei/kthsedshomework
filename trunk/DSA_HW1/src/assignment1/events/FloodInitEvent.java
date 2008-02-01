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
    private String eventMessage;

    public FloodInitEvent(String eventMessage) {
        this.eventMessage = eventMessage;
    }
    
    public String getEventMessage() {
        return eventMessage;
    }
}
