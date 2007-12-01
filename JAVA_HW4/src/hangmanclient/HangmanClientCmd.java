/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package hangmanclient;

import javax.microedition.lcdui.Display;
import javax.microedition.midlet.MIDletStateChangeException;

/**
 *
 * @author Kop
 */
public class HangmanClientCmd {
    private HangmanClient mainApp = null;
    private Display display;
    private HangmanClientForm clientForm = null;
    private HangmanConnectForm connectForm = null;
    private CommunicationHandler handler = null;
    
    

    HangmanClientCmd(HangmanClient app) {
        mainApp = app;
        display = Display.getDisplay(mainApp);
        clientForm = new HangmanClientForm(this, "Welcome");
        connectForm = new HangmanConnectForm(this, "Connect");
    }
    
    public HangmanClient getApplication() {
        return mainApp;
    }
    
    public void start() {
        connectForm.initialize();
        clientForm.initialize();        
        display.setCurrent(connectForm);
//        connectForm.setCommandListener(this);
//        clientForm.setCommandListener(this);
    }
    
    public void terminate(boolean unconditional) {
        try {
            mainApp.destroyApp(unconditional);
            mainApp.notifyDestroyed();
        } catch (MIDletStateChangeException ex) {
            System.err.println(ex.getMessage());
        }
    }
    
    public String getServerIP() {
        //TODO: need implementation here
        return connectForm.getServerIP();
    }
    
    private void changeToClientForm() {
        // TODO: Need implementation here
        display.setCurrent(clientForm);
    }

    public void setString(String word) {
        // TODO: Need implementation here
        clientForm.setText(word);
    }
    
    public boolean connect() {
        // Check the validity of server IP and port
        if (null != connectForm.getServerIP()) {
            changeToClientForm();
            handler = new CommunicationHandler(this);
            handler.setCommandString(CommunicationHandler.STARTROUND);
            handler.start();
            return true;
        } // If the IP and port are not properly set, then warn user
        else {
            return false;
        }
    }
}
