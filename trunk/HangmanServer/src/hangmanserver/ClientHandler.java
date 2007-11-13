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

import java.util.logging.Level;
import java.util.logging.Logger;
import message.hangmanMessage.*;

/**
 *
 * @author Kop
 */
public class ClientHandler extends Thread {
    private Socket clientSocket = null;
    
    private HangmanServerViewCmd mainCmd = null;
    // The correct word from the dictionary
    private char[] word;
    // The current word which the client will see
    private char[] currentWord;
    private boolean running = true;
    
    public ClientHandler(Socket socket, HangmanServerViewCmd cmd) {
        clientSocket = socket;
        this.mainCmd = cmd;
    }
    
    @Override 
    public void run() {
        // Prepare for listening  to the input message object
        Object messageObject = null;
        ObjectInputStream reader = null;
        while (running) {
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
        try {
            clientSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
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
            case GameOver:
                gameOver();
                break;
            case Terminate:
                terminateClient();
                break;
        default:
            break;
        }
    }
    
    public void stopRunning() {
        this.running = false;
    }
    
    private void startNewRound() {
        String newWord = WordReader.getWord();
        word = newWord.toCharArray();
        int length = word.length;
        currentWord = new char[length];
        // Use underline to fill the char array
        for (char c : currentWord) {
            c = '_';
        }
        mainCmd.getMainView().addNewMessage("The new word for client " + 
                clientSocket.getInetAddress().toString() +
                " is " + newWord + "\n");
        replyClient();
    }
    
    private void checkInput(HangmanMessage message) {
        // Get the message content and cast it to char array
        String content = message.getContent();
        if (content.length() == 0 || word == null) {
            return;
        }
        mainCmd.getMainView().addNewMessage("Client " + 
                clientSocket.getLocalAddress().toString() + 
                ": inputs " + content + "\n");
        
        boolean bFound = false;
        char[] inputCharArray = content.toCharArray();
        
        if (inputCharArray.length == 1) {
        // content is just one character
            for (int i = 0; i < word.length; i++) {
                if (inputCharArray[0] == word[i]) {
        // Change the current word's space into corresponding character         
                    currentWord[i] = inputCharArray[0];
                    bFound = true;
                }
            }
        }
        else {
        // content is a word
        // it is demanded that length of the word that client inputs equals the length of the correct word
            if (inputCharArray.length == word.length) {
        // check if the word that client inputs is just the correct word, the two words are identical when the value is 0         
                if (content.compareTo(new String(word)) == 0) {
                    currentWord = word;
                    bFound = true;
                }
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
        replyClient(replyMessage);
    }
    
    private void gameOver() {
        mainCmd.getMainView().addNewMessage("Client " + 
                clientSocket.getLocalAddress().toString() + 
                ": game over.\n");
        
        HangmanMessage correctWordMessage = new HangmanMessage();
        correctWordMessage.setHangmanMessageType(HangmanMessageType.GameOver);
        correctWordMessage.setContent(new String(word));  // word.toString() ----> wrong
        replyClient(correctWordMessage);
        this.run();
    }
    
    private void terminateClient() {
        mainCmd.getMainView().addNewMessage("Client " + 
                clientSocket.getLocalAddress().toString() + 
                ": terminate\n");
        
        this.stopRunning();
    }
    
    public void terminateServer() {
        HangmanMessage serverStopMessage = new HangmanMessage();
        serverStopMessage.setHangmanMessageType(HangmanMessageType.Terminate);
        serverStopMessage.setContent("Server terminates.");
        replyClient(serverStopMessage);
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
