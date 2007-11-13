/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package hangmanserver;

import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

/**
 *
 * @author Kop
 */
public class HangmanServerApp {
    
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() throws UnknownHostException {
        //Create and set up the window.
        HangmanServerView view = new HangmanServerView();
        view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Display the window.
        view.setVisible(true);
    }
    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    createAndShowGUI();
                } catch (UnknownHostException ex) {
                    Logger.getLogger(HangmanServerApp.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
}
