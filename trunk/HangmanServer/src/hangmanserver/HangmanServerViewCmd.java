/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package hangmanserver;

import javax.swing.JOptionPane;



/**
 *
 * @author Kop
 */
public class HangmanServerViewCmd {
    private HangmanServerView serverView = null;
    private ServerMainThread mainThread = null;
    
    public HangmanServerViewCmd(HangmanServerView view) {
        serverView = view;
    }
    
    public HangmanServerView getMainView() {
        return serverView;
    }
    public void stop() {
        if (null != mainThread) {
            mainThread.stopAllThreads();
            mainThread.stopRunning();
        }
    }
    public void start() {
        String portNumberString = serverView.getPortNumberString();
        boolean isPortNumberValid = true;
        try {
            int portNumber = Integer.parseInt(portNumberString);
            if (portNumber > 65535 || portNumber <= 1024) isPortNumberValid = false;
        }
        catch (NumberFormatException e) {
            isPortNumberValid = false;
        }
       if (!isPortNumberValid) {
           JOptionPane.showMessageDialog(serverView, "Please enter a valid port number");
           serverView.setStatusLabelToStop("Stop");
           return;
       }
        if (null == mainThread) {
            mainThread = new ServerMainThread(this);
            mainThread.start();
            serverView.setStatusLabelToStop("Running");
        }
    }
}
