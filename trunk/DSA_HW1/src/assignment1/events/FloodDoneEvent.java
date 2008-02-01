/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment1.events;

/**
 *
 * @author Kop
 */
public class FloodDoneEvent implements tbn.api.Event {

    private String message;

    public FloodDoneEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        // TODO: need implementation here
        return "Flood Done ( " + message + " )";
    }
}
