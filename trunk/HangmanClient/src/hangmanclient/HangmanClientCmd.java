/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package hangmanclient;

import java.awt.HeadlessException;
import javax.swing.JOptionPane;

/**
 *
 * @author Kop
 */
public class HangmanClientCmd {
    
    private HangmanClientView mainView;
    private HangmanClientComminicator communicator;
    private int dangerLevel = 0;
    private String serverIP = null;
    private int serverPort = 0;
    
    /**
     * 
     * @param view
     */
    public HangmanClientCmd(HangmanClientView view) {
        mainView = view;
        communicator = new HangmanClientComminicator(this);
    }
    
    /**
     * This method
     * @param evt
     */
    public void startButtonActionPerformed() {
        // TODO add your handling code here:
        // Check the validity of server IP and port
        if (null != serverIP && serverPort > 1024) {
            String newWord = communicator.getNewWord();
            mainView.setText(newWord);
            mainView.setDanger(0);
        }
        // If the IP and port are not properly set, then warn user
        else {
            try {
                JOptionPane.showMessageDialog(mainView.getFrame(), new String("Please set the server's IP and port properly"));
            }
            catch (HeadlessException he) {
                System.err.println(he.getMessage());
            }
        }
    }
        
    /**
     * 
     * @param evt
     */
    public void endButtonActionPerformed() {
        // TODO add your handling code here:
        mainView.getApplication().exit();
    }
    
    /**
     * 
     * @param evt
     */
    public void submit(String submitInfo) {
        //char c = evt.getKeyChar();
        Boolean isCorrect = Boolean.FALSE;
        String msg = communicator.checkInput(submitInfo, isCorrect);
        mainView.setText(msg);
        // If all the letters are fullfilled then call victory
        if (isVictory(msg)) {
            mainView.victory();
        }
        if (Boolean.FALSE == isCorrect) {
            increaseDanger();
        }
    }
    
    public void setUpMenuItemActionPerformed() {
        // Start the property setting dialog
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ClientPropertyInputDlg dialog = new ClientPropertyInputDlg(HangmanClientCmd.this, true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override public void windowClosing(java.awt.event.WindowEvent e) {
                        //System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    
    
    public void setServerIP(String serverIP) {
        // Set the server IP address
        this.serverIP = serverIP;
    }
    
    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }
    
    public String getServerIP() {
        return serverIP;
    }
    
    public int getServerPort(){
        return serverPort;
    }
    
    public HangmanClientView getMainView() {
        return mainView;
    }
    
    private boolean isVictory(String word) {
        boolean isVictory = true;
        char[] charArray = word.toCharArray();
        for (char c : charArray) {
            if (c > 122 || c < 97) {
                isVictory = false;
            }
        }
        return isVictory;
    }
    
    private void increaseDanger()
    {
        dangerLevel += 1;
        if (dangerLevel == 7) {
            mainView.lose();
        }
        mainView.setDanger(dangerLevel);
    }   
}
    
