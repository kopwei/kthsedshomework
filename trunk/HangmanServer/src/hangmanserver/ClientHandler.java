/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package hangmanserver;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.net.Socket;

import message.hangmanMessage.*;

/**
 *
 * @author Kop
 */
public class ClientHandler extends Thread{
    private Socket clientSocket = null;
    // The correct word from the dictionary
    private char[] word;
    // The current word which the client will see
    private char[] currentWord;
    
    public ClientHandler(Socket socket) {
        clientSocket = socket;
    }
    
    @Override public void run() {
        // Prepare for listening  to the input message object
        Object messageObject = null;
        ObjectInputStream reader = null;
        while (true) {
            try {
                reader = new ObjectInputStream(clientSocket.getInputStream());
            } catch (IOException ie) {

            }
            try {
                // Read the object out;
                messageObject = reader.readObject();
                //reader.close();
            } catch (ClassNotFoundException e1) {
                System.out.println(e1.toString());
                return;
            } catch (OptionalDataException e2) {
                System.out.println(e2.toString());
                return;
            } catch (IOException e3) {
                System.out.println(e3.toString());
                return;
            }
            if (null != messageObject) {
                // Transform the message 
                HangmanMessage message = (HangmanMessage) messageObject;
                processMessage(message);
            }
        }
    }
    
    private void processMessage(HangmanMessage message) {
        // Check the messages validity
        if (null == message) {
            return;
        }
        // Process the message according to message type
        switch (message.getHangmanMessageType()) {
            case StartNewRound:
                startNewRound();
                break;
            case CheckInput:
                checkInput(message);
                break;
        default:
            break;
        }
    }
    
    private void startNewRound() {
        word = WordReader.getWord().toCharArray();
        int length = word.length;
        currentWord = new char[length];
        // Use space to fill the char array
        for (char c : currentWord) {
            c = 27;
        }
        replyClient();
    }
    
    private void checkInput(HangmanMessage message) {
        // Get the message content and cast it to char array
        String content = message.getContent();
        if (content.length() == 0 || word == null) {
            return;
        }
        char[] inputCharArray = content.toCharArray();
        // Since there will only be one character inside, we just get it
        char inputChar = inputCharArray[0];
        boolean bFound = false;
        // Change the current word's space into corresponding character
        for (int i = 0; i < word.length; i++) {
            char c = word[i];
            if (c == inputChar) {
                currentWord[i] = c;
                bFound = true;
            }
        }
        HangmanMessage replyMessage = new HangmanMessage();
        replyMessage.setContent(new String(currentWord));
        // If the input char is in the word then reply with the word which the character visible
        if (bFound) {
            replyMessage.setHangmanMessageType(HangmanMessageType.CorrectInput);
        }
        // If the input char is not in the word then reply the invalid
        else {
            replyMessage.setHangmanMessageType(HangmanMessageType.WrongInput);
        }
    }
    
    private void replyClient() {
        // Prepare the reply message and reply it to client
        HangmanMessage replyMessage = new HangmanMessage();
        replyMessage.setHangmanMessageType(HangmanMessageType.StartNewRound);
        replyMessage.setContent(new String(currentWord));
        replyClient(replyMessage);
    }
    
    private void replyClient(HangmanMessage message) {
        // Check the socket validity
        if (null == clientSocket) {
            return;
        }
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(clientSocket.getOutputStream());
            out.writeObject(message);
            out.flush();
        }
        catch (IOException ie) {
            System.err.println(ie.getMessage());
        }
//        try {
//            out.close();
//        }
//        catch (IOException ie) {
//            System.err.println(ie.getMessage());
//        }
    }
}
