/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package hello;

import java.io.OutputStream;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;
import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;

/**
 * @author Kop
 */
public class Midlet extends MIDlet implements CommandListener{
    private Form connectForm;
    private Form clientForm;
    
    private TextField serverIP;
    
    private Command exitCommand;
    private Command connectCommand;
    private Command sendCommand;
    private Command exitCommand2;
    
    private Display display;
    private StreamConnection sc;
    private OutputStream os;
    
    public Midlet() {
        super();
        display = Display.getDisplay(this);
        connectForm = new Form("Connect");
        serverIP = new TextField("Server IP", "localhost", 50, 0);
        connectCommand = new Command("Connect", Command.SCREEN, 1);
        exitCommand = new Command("Exit", Command.EXIT, 0);
        sendCommand = new Command("Send", Command.SCREEN, 1);
        exitCommand2 = new Command("Exit", Command.EXIT, 0);
    }
    
    public void startApp() {
        connectForm.addCommand(connectCommand);
        connectForm.addCommand(exitCommand);
        connectForm.append(serverIP);
        
        connectForm.setCommandListener(this);
        display.setCurrent(connectForm);
    }

    public void pauseApp() {
    }

    public void destroyApp(boolean unconditional) {
    }

    public void commandAction(Command c, Displayable s) {
        try {
            if (c == exitCommand) {
                destroyApp(false);
                notifyDestroyed();
            } else if (c == connectCommand) {
                sc = (StreamConnection) Connector.open("socket://" + serverIP.getString() + ":4444");
                
            }
        } catch (Exception e) {
            
        }
    }
}
