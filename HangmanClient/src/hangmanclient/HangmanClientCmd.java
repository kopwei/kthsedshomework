/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package hangmanclient;

import java.awt.event.KeyEvent;
//import message.hangmanMessage.HangmanMessage;

/**
 *
 * @author Kop
 */
public class HangmanClientCmd {
    
    private HangmanClientView mainView;
    private HangmanClientComminicator communicator;
    private int dangerLevel = 0;
    
    /**
     * 
     * @param view
     */
    public HangmanClientCmd(HangmanClientView view) {
        mainView = view;
        communicator = new HangmanClientComminicator();
    }
    
    /**
     * This method
     * @param evt
     */
    public void startButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        String newWord = communicator.getNewWord();
        mainView.setText(newWord);
    }
    
    public void keyPressed(KeyEvent evt) {
        char c = evt.getKeyChar();
        Boolean isCorrect = Boolean.FALSE;
        String msg = communicator.checkInput(c, isCorrect);
        mainView.setText(msg);
        if (Boolean.FALSE == isCorrect) {
            increaseDanger();
        }
    }
    /**
     * 
     * @param evt
     */
    public void endButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        dangerLevel -= 1;
        if (dangerLevel < 0) dangerLevel = 0;
        mainView.setText("HelloHello");   
        mainView.setPicture(dangerLevel);
    }
    
    private boolean increaseDanger()
    {
        dangerLevel += 1;
        if (dangerLevel > 7) dangerLevel = 7;
        mainView.setPicture(dangerLevel);
        return (dangerLevel == 7);
    }
}
