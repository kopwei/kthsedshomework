/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package hangmanclient;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.TextField;

/**
 *
 * @author Kop
 */
public class HangmanConnectForm extends Form implements CommandListener{
    private HangmanClientCmd mainCmd;
    private TextField serverIP;
    private TextField serverPort;
    
    private Command exitCommand;
    private Command connectCommand;
     
    public HangmanConnectForm(HangmanClientCmd cmd, String title) {
        super(title);
        mainCmd = cmd;
        
        connectCommand = new Command("Connect", Command.SCREEN, 1);
        exitCommand = new Command("Exit", Command.EXIT, 0);
        
        serverIP = new TextField("Server IP", "localhost", 50, TextField.ANY);
        serverPort = new TextField("Server Port", "4444", 50, TextField.ANY);
    }

    public void initialize() {
        addCommand(connectCommand);
        addCommand(exitCommand);
        append(serverIP);
        append(serverPort);
        setCommandListener(this);
    }
    public void commandAction(Command c, Displayable s) {
        try {
            if (c.getLabel().equals("Connect")) {
                mainCmd.connect(serverIP.getString(), serverPort.getString());            
            }
        } catch (Exception e) {
            
        }
    }
}
