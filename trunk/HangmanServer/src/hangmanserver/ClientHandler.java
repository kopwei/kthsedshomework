/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package hangmanserver;


import java.io.IOException;
import java.io.InputStream;
import java.io.OptionalDataException;
import java.io.OutputStream;
import java.net.Socket;

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
    private char[] currentWord = {'H', 'L', 'L'};
    private boolean running = true;
    
    public ClientHandler(Socket socket, HangmanServerViewCmd cmd) {
        clientSocket = socket;
        this.mainCmd = cmd;
    }
    
    @Override 
    public void run() {
        // Prepare for listening  to the input message object
        HangmanMessage messageObject = new HangmanMessage();
        InputStream reader = null;
        while (running) {
            try {
                reader = clientSocket.getInputStream();
            } catch (IOException ie) {
            }
            try {
                final int MAX_LENGTH = 128;
                byte[] buf = new byte[MAX_LENGTH];
                reader.read(buf);
                messageObject.resurrect(buf);
                // Read the object out;
            } catch (OptionalDataException e2) {
                System.out.println(e2.toString());
                return;
            } catch (IOException e3) {
                System.out.println(e3.toString());
                return;
            }
            if (null != messageObject.getContent()) {
                // Transform the message
                processMessage(messageObject);
            }
        }
        try {
            clientSocket.close();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    private void processMessage(HangmanMessage message) {
        // Check the messages validity
        if (null == message) {
            return;
        }
        String type = message.getHangmanMessageType();
        // Process the message according to message type
        if (type.equals(HangmanMessageType.StartNewRound)) {
            startNewRound();
        }
        if (type.equals(HangmanMessageType.CheckInput)) {
            checkInput(message);
        }
        if (type.equals(HangmanMessageType.GameOver)) {
            gameOver();
        }
        if (type.equals(HangmanMessageType.Terminate)) {
            terminateClient();
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
        for (int i = 0; i < length; i++) {
            currentWord[i] = '_';

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
        replyMessage.setContent(String.valueOf(currentWord));
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
        replyMessage.setHangmanMessageType(HangmanMessageType.Unknown);
        replyMessage.setContent(String.valueOf(currentWord));
        replyClient(replyMessage);
    }
    
    private void replyClient(HangmanMessage message) {
        // Check the socket validity
        if (null == clientSocket) {
            return;
        }
        OutputStream out = null;
        try {
            out = clientSocket.getOutputStream();
            out.write(message.persist());
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
