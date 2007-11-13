/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package hangmanclient;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import message.hangmanMessage.HangmanMessage;
import message.hangmanMessage.HangmanMessageType;
/**
 *
 * @author Kop
 */
public class HangmanClientComminicator {
    private HangmanClientCmd mainCmd;
    private Socket clientSocket = null;
    
    public HangmanClientComminicator(HangmanClientCmd cmd) {
        mainCmd = cmd;
    }
    public String getNewWord() {
        // Create a start new round message and request the server
        HangmanMessage requestMessage = new HangmanMessage();
        requestMessage.setHangmanMessageType(HangmanMessageType.StartNewRound);
        HangmanMessage replyMessage = requestServer(requestMessage);
        if (null != replyMessage) {
            return replyMessage.getContent();
        }
        else {
            return null;
        }
    }
    
    public HangmanMessage checkInput(String inputString) {
        // Create a start new round message and request the server
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
        
        HangmanMessage replyMessage = null;
        ObjectOutputStream objOut = null;
        ObjectInputStream objInput = null;
        // Create a client socket which communicate with the server
        try {
            if (null == clientSocket) {
                clientSocket  = new Socket(mainCmd.getServerIP(), mainCmd.getServerPort());
            }
        }
        catch (UnknownHostException ue) {
            System.err.println(ue.getMessage());
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
            objOut = new ObjectOutputStream(clientSocket.getOutputStream());
            objOut.writeObject(requestMessage);
            objOut.flush();
            //objOut.close();
        }
        catch (IOException ie) {
            System.err.println(ie.getMessage());
        }
        try {
            // Get the reply message from the server
            objInput = new ObjectInputStream(clientSocket.getInputStream());
            replyMessage = (HangmanMessage)(objInput.readObject());
            //objInput.close();
        }
        catch (ClassNotFoundException ce) {
            return null;
        }
        catch (IOException ie) {
            System.err.println(ie.getMessage());
            return null;
        }
//        try {
//            if (null != objOut) {
//                objOut.close();
//            }
//            if (null != objInput) {
//                objInput.close();
//            }
//            // Close the socket and return
//            if (null != clientSocket) {
//                clientSocket.close();
//            }
//        }
//        catch (IOException ie) {
//            return null;
//        }
        return replyMessage;
    }

}
