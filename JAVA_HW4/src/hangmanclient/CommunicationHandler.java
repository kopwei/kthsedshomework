/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hangmanclient;

import message.hangmanMessage.HangmanMessage;
import message.hangmanMessage.HangmanMessageType;

/**
 *
 * @author Kop
 */
public class CommunicationHandler extends Thread {

        public static final String STARTROUND = "StartNewRound";
        public static final String CHECKINPUT = "CheckInput";
        public static final String TERMINATE = "Terminate";
        public static final String GAMEOVER = "GameOver";
        private HangmanClientComminicator communicator = null;
        private HangmanClientCmd mainCmd = null;
        // private boolean finished = false;
        private String commandStr = null;
        private String inputStr = null;

        public CommunicationHandler(HangmanClientCmd cmd) {
                mainCmd = cmd;
                communicator = new HangmanClientComminicator(cmd);
        }

        public void setCommandString(String command) {
                commandStr = command;
        }

        public void setInputString(String input) {
                inputStr = input;
        }

        public void run() {
                if (null == commandStr) {
                        return;
                }
                if (commandStr.equals(STARTROUND)) {
                        String text = communicator.getNewWord();
                        if (null != text) {
                                mainCmd.setString(text);
                        }
                } else if (commandStr.equals(CHECKINPUT) && null != inputStr) {
                        HangmanMessage msg = communicator.checkInput(inputStr);
                        if (null != msg) {
                                String text = msg.getContent();
                                mainCmd.setString(text);
                                if (msg.getHangmanMessageType().equals(HangmanMessageType.WrongInput)) {
                                        mainCmd.increaseDanger();
                                }
                        }
                } else if (commandStr.equals(TERMINATE)) {
                        communicator.terminate();
                } else if (commandStr.equals(GAMEOVER)) {
                        HangmanMessage msg = communicator.gameOver();
                        if (null != msg) {
                                String text = msg.getContent();
                                mainCmd.setString(text);
                        }
                }
        }
}
