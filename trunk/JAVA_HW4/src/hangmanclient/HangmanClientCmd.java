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
    
    private String serverIP = null;
    private String serverPort = null;
    
    private int dangerLevel = 0;
    
    

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
        return this.serverIP;
    }
    
    public String getServerPort() {
        return this.serverPort;
    }
    private void changeToClientForm() {
        // TODO: Need implementation here
        display.setCurrent(clientForm);
    }

    public void setString(String word) {
        // TODO: Need implementation here
        clientForm.setText(word);
    }
    
    public boolean send(String str) {
        if (null != str) {
            handler = new CommunicationHandler(this);
            handler.setCommandString(CommunicationHandler.CHECKINPUT);
            handler.setInputString(str);
            handler.start();
            return true;
            
        } else {
            return false;
        }
    }
    
    public void increaseDanger() {
        dangerLevel++;
        clientForm.setDanger(dangerLevel);
    }
    
    public boolean connect(String ip, String portnumber) {
        // Check the validity of server IP and port
        if (null != ip && null != portnumber) {
            this.serverIP = ip;
            this.serverPort = portnumber;
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
