/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package client;

import java.awt.HeadlessException;
import javax.swing.JOptionPane;
import message.hangmanMessage.HangmanMessage;
import message.hangmanMessage.HangmanMessageType;

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
    public boolean startButtonActionPerformed() {
        // TODO add your handling code here:
        // Check the validity of server IP and port
        if (null != serverIP && serverPort > 1024) {
            String newWord = communicator.getNewWord();
            if (null != newWord) {
                mainView.setText(newWord);
                mainView.setDanger(0);
                return true;
            } else {
                return false;
            }
        } // If the IP and port are not properly set, then warn user
        else {
            try {
                JOptionPane.showMessageDialog(mainView.getFrame(), new String("Please set the server's IP and port properly"));
            } catch (HeadlessException he) {
                System.err.println(he.getMessage());
            }
            return false;
        }
    }

    /**
     * 
     * @param evt
     */
    public void endButtonActionPerformed() {
        // Tell server the client will terminate
        communicator.terminate();
        // Exit the application
        mainView.getApplication().exit();
    }
    
    /**
     * 
     * @param evt
     */
    public void submit(String submitInfo) {
        HangmanMessage msg = communicator.checkInput(submitInfo);
        mainView.setText(msg.getContent());
        // If all the letters are fullfilled then call victory
        if (isVictory(msg.getContent())) {
            mainView.victory();
        }
        if (msg.getHangmanMessageType().equals(HangmanMessageType.WrongInput)) {
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
    
    public void victory() {
        // Tell the server thr round is over
        communicator.gameOver();
    }
    
    public void lose() {
        // Tell the server thr round is over and display the whole word
        HangmanMessage msg = communicator.gameOver();
        if (null != msg) {
            mainView.setText(msg.getContent());
        }
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
        // If all the characters are really 
        boolean isVictory = true;
        char[] charArray = word.toCharArray();
        for (char c : charArray) {
            if (!Character.isLetter(c)) {
                isVictory = false;
            }
        }
        return isVictory;
    }
    
    private void increaseDanger()
    {
        // Increase the danger level by 1
        dangerLevel += 1;
        if (dangerLevel == 7) {
            mainView.lose();
        }
        mainView.setDanger(dangerLevel);
    }   
}
    
