/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package assignment2.components;

import assignment2.events.CrashEvent;
import assignment2.events.RestoreEvent;
import assignment2.events.SuspectEvent;
import org.apache.log4j.Logger;
import tbn.api.Component;

/**
 *
 * @author Ricky
 */
public class ApplicationComponent {

    private Component component;
    private static Logger log = Logger.getLogger(ApplicationComponent.class);


    public ApplicationComponent(Component component) {
        this.component = component;
    }

    public void handleCrashEvent(CrashEvent crashEvent) {
        log.info("Crashing message: " + crashEvent.getCrashEventMessage());
    }
    
    public void handleSuspectEvent(SuspectEvent suspectEvent) {
        log.info("Node " + suspectEvent.getNodeReference() + " is suspected of crashing");
    }
    
    public void handleRestoreEvent(RestoreEvent restoreEvent) {
        log.info("Node " + restoreEvent.getNodeReference() + " is restored");
    }
    

}
