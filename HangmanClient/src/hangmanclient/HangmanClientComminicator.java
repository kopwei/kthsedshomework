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
    
    public String checkInput(char inputChar, Boolean isCorrect) {
        // Create a start new round message and request the server
        HangmanMessage requestMessage = new HangmanMessage();
        requestMessage.setHangmanMessageType(HangmanMessageType.CheckInput);
        requestMessage.setContent(Character.toString(inputChar));
        HangmanMessage replyMessage = requestServer(requestMessage);
        if (null != replyMessage) {
            if (HangmanMessageType.CorrectInput == replyMessage.getHangmanMessageType()) {
                isCorrect = Boolean.TRUE;
            }
            else {
                isCorrect = Boolean.FALSE;
            }
            return replyMessage.getContent();
        }
        else {
            return null;
        }
    }
    
    private HangmanMessage requestServer(HangmanMessage requestMessage) {
        Socket clientSocket = null;
        HangmanMessage replyMessage = null;
        ObjectOutputStream objOut = null;
        ObjectInputStream objInput = null;
        // Create a client socket which communicate with the server
        try {
            clientSocket  = new Socket("localhost", 4444);
        }
        catch (UnknownHostException ue) {
            
        }
        catch (IOException ie) {
            System.err.println(ie.getMessage());
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
