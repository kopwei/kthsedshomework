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
                clientSocket = cmd.getConnection();
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
                try {
                        HangmanMessage requestMessage = new HangmanMessage();
                        requestMessage.setHangmanMessageType(HangmanMessageType.Terminate);
                        requestServer(requestMessage);
                        clientSocket.close();
                } catch (IOException ex) {
                        ex.printStackTrace();
                }
        }

        private HangmanMessage requestServer(HangmanMessage requestMessage) {

                HangmanMessage replyMessage = new HangmanMessage();
                // Check the validity
                if (null == clientSocket) {
                        try {
                                String socketURL = "socket://" + mainCmd.getServerIP() + ":" + mainCmd.getServerPort();
                                clientSocket = (StreamConnection) Connector.open(socketURL);
                                mainCmd.setConnectionSocket(clientSocket);
                        } catch (IOException ex) {
                                ex.printStackTrace();
                        }
                }
                // Send the message object to server and wait for reply
                try {
                        OutputStream os = mainCmd.getOutputStream();
                        if (null == os) {
                                os = mainCmd.getConnection().openOutputStream();
                                mainCmd.setOutputStream(os);
                        }
                        os.write(requestMessage.persist());
                        os.flush();
                //objOut.close();
                //os.close();
                } catch (IOException ie) {
                        System.err.println(ie.getMessage());
                }
                try {
                        // Get the reply message from the server
                        InputStream is = mainCmd.getInputStream();
                        if (null == is) {
                                is = clientSocket.openInputStream();
                                mainCmd.setInputStream(is);
                        }
                        final int MAX_LENGTH = 128;
                        byte[] buf = new byte[MAX_LENGTH];
                        is.read(buf);
                        replyMessage.resurrect(buf);
                //is.close();
                //objInput.close();
                } catch (IOException ie) {
                        System.err.println(ie.getMessage());
                        return null;
                }
                return replyMessage;
        }
}
