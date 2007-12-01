/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package hangmanclient;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.microedition.io.*;
import message.hangmanMessage.HangmanMessage;
import message.hangmanMessage.HangmanMessageType;
/**
 *
 * @author Kop
 */
public class HangmanClientComminicator {
    private HangmanClientCmd mainCmd;
    private StreamConnection clientSocket = null;
    
    public HangmanClientComminicator(HangmanClientCmd cmd) {
        mainCmd = cmd;
    }
    public String getNewWord() {
        HangmanMessage replyMessage = null;
        // Create a connect new round message and request the server
        try {
            HangmanMessage requestMessage = new HangmanMessage();
            requestMessage.setHangmanMessageType(HangmanMessageType.StartNewRound);
            replyMessage = requestServer(requestMessage);
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        if (null != replyMessage) {
            return replyMessage.getContent();
        } else {
            return null;
        }
    }
    
    public HangmanMessage checkInput(String inputString) {
        // Create a connect new round message and request the server
        HangmanMessage requestMessage = new HangmanMessage();
        requestMessage.setHangmanMessageType(HangmanMessageType.CheckInput);
        requestMessage.setContent(inputString);
        HangmanMessage replyMessage = requestServer(requestMessage);
        // 
        return replyMessage;
    }
    
    /**
     * This method is used to notify server that the game is over
     * @return the message contains the correct word
     */
    public HangmanMessage gameOver() {
        HangmanMessage requestMessage = new HangmanMessage();
        requestMessage.setHangmanMessageType(HangmanMessageType.GameOver);
        HangmanMessage replyMessage = requestServer(requestMessage);
        // 
        return replyMessage;
    }
    
    public void terminate() {
        HangmanMessage requestMessage = new HangmanMessage();
        requestMessage.setHangmanMessageType(HangmanMessageType.Terminate);
        requestServer(requestMessage);
    }
    
    private HangmanMessage requestServer(HangmanMessage requestMessage) {
        
        HangmanMessage replyMessage = new HangmanMessage();
        // Create a client socket which communicate with the server
        try {
            if (null == clientSocket) {
                clientSocket  = (StreamConnection) Connector.open("socket://" + mainCmd.getServerIP() + ":4444");
            }
        }
        catch (IOException ie) {
            System.err.println(ie.getMessage());
        }
        // Check the validity
        if (null == clientSocket) {
            return null;
        }
        // Send the message object to server and wait for reply
        try {
            OutputStream os = clientSocket.openOutputStream();
            os.write(requestMessage.persist());
            os.flush();
            //objOut.close();
        }
        catch (IOException ie) {
            System.err.println(ie.getMessage());
        }
        try {
            // Get the reply message from the server
            InputStream is = clientSocket.openInputStream();
            final int MAX_LENGTH = 128;
            byte[] buf = new byte[MAX_LENGTH];
            is.read(buf);
            replyMessage.resurrect(buf);
            //objInput.close();
        }
        catch (IOException ie) {
            System.err.println(ie.getMessage());
            return null;
        }
        return replyMessage;
    }

}
