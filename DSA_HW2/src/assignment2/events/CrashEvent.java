/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package assignment2.events;

/**
 *
 * @author Ricky
 */
public class CrashEvent implements tbn.api.Event{
    private String crashEventMessage;

    public CrashEvent(String crashEventMessage) {
        this.crashEventMessage = crashEventMessage;
    }
    
    public String getCrashEventMessage() {
        return crashEventMessage;
    }
}
