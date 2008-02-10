/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package assignment2.events;

import tbn.api.Event;
import tbn.comm.mina.NodeReference;

/**
 *
 * @author Kop
 */
public class FailureEvent implements Event{
    private String failureMessage;
    private NodeReference ref;

    public FailureEvent(String eventMessage, NodeReference ref) {
        this.failureMessage = eventMessage;
        this.ref = ref;
    }
    
    public String getCrashEventMessage() {
        return failureMessage;
    }
    
    public NodeReference getNodeReference() {
        return ref;
    }
}
