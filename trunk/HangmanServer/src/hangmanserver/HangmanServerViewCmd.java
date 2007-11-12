/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package hangmanserver;



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
    
    public void Start() {
        if (null == mainThread) {
            mainThread = new ServerMainThread(this);
            mainThread.start();
        }
    }
}
