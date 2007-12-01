/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package hangmanclient;

import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

/**
 *
 * @author Kop
 */
public class HangmanClient extends MIDlet{
    
    private HangmanClientCmd mainCmd = null;

    public HangmanClient() {
        super();
        mainCmd = new HangmanClientCmd(this);
    }
    protected void startApp() throws MIDletStateChangeException {
        mainCmd.start();
    }

    protected void pauseApp() {
        
    }

    protected void destroyApp(boolean arg0) throws MIDletStateChangeException {
        mainCmd.terminate(arg0);
    }
}
