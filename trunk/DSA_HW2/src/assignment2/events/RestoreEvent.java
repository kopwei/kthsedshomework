/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package assignment2.events;

import tbn.comm.mina.NodeReference;

/**
 *
 * @author Kop
 */
public class RestoreEvent extends FailureEvent{
    public RestoreEvent(String eventMessage, NodeReference ref) {
        super(eventMessage, ref);
    }
    
}
