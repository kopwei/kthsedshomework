/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package assignment2.events;

import tbn.comm.mina.NodeReference;


/**
 *
 * @author Ricky
 */
public class CrashEvent extends FailureEvent{
    public CrashEvent(String eventMessage, NodeReference ref) {
        super(eventMessage, ref);
    }
}
