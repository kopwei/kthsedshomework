/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package hangmanclient;

/**
 *
 * @author Kop
 */
public class HangmanClientCmd {
    
    private HangmanClientView mainView = null;
    private HangmanClientComminicator communicator = null;
    
    public HangmanClientCmd(HangmanClientView view) {
        this.mainView = view;
        communicator = new HangmanClientComminicator(this);
    }
    
    public String getServerIP() {
        //TODO: need implementation here
        return "localhost";
    }

}
