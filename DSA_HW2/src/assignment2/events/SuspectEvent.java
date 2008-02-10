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
public class SuspectEvent extends FailureEvent{
    public SuspectEvent(String eventMessage, NodeReference ref) {
        super(eventMessage, ref);
    }
}
