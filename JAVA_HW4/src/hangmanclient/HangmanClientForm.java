/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hangmanclient;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;

/**
 * @author Kop
 */
public class HangmanClientForm extends Form implements CommandListener {
        private Ticker titleTicker;
        private TextField enterField;
        private StringItem wordFieldLabel;
        private StringItem wordField;
        private StringItem guessedFieldLabel;
        private StringItem guessedField;
        private Image[] images = new Image[8];
        private ImageItem imageItem;
        private Command sendCommand;
        private Command restartCommand;
        private Command exitCommand;
        private Hashtable guessedWords = new Hashtable();
        private HangmanClientCmd mainCmd = null;

        public HangmanClientForm(HangmanClientCmd cmd, String title) {
                super(title);
                initImages();
                titleTicker = new Ticker("Welcome to Hangman World");
                enterField = new TextField("Input a letter or a word", "", 50, TextField.ANY);
                wordFieldLabel = new StringItem("", "", StringItem.PLAIN);
                wordFieldLabel.setLabel("Word to Guess");
                wordField = new StringItem("Current Word: ", "", StringItem.PLAIN);
                guessedFieldLabel = new StringItem("", "", StringItem.PLAIN);
                guessedFieldLabel.setLabel("GuessedLabel");
                guessedField = new StringItem("The words you guessed:", "", StringItem.PLAIN);
                imageItem = new ImageItem(null, images[0], ImageItem.LAYOUT_NEWLINE_BEFORE |
                        ImageItem.LAYOUT_CENTER, null);
                sendCommand = new Command("Send", Command.BACK, 0);
                restartCommand = new Command("Restart", Command.ITEM, 1);
                exitCommand = new Command("Exit", Command.EXIT, 2);
                mainCmd = cmd;
        }

        public void initialize() {
                addCommand(sendCommand);
                addCommand(restartCommand);
                addCommand(exitCommand);
                setTicker(titleTicker);
                append(enterField);
                //append(wordFieldLabel);
                append(wordField);
                //append(guessedFieldLabel);
                append(guessedField);
                append(imageItem);
                setCommandListener(this);
        }

        public void victory() {
                removeCommand(sendCommand);
        }

        public void lose() {
                removeCommand(sendCommand);
        }

        public void commandAction(Command c, Displayable s) {
                if (c == exitCommand) {
                        mainCmd.terminate(true);
                } else if (c == sendCommand) {
                        String enteredText = enterField.getString();
                        enterField.setString("");
                       boolean valid = isValidString(enteredText);
                        if (valid) {
                                guessedWords.put(enteredText, "");
                                mainCmd.send(enteredText);
                                
                                Enumeration e = guessedWords.keys();
                                StringBuffer buffer = new StringBuffer();
                                while (e.hasMoreElements()) {
                                        String str = e.nextElement().toString();
                                        buffer.append(str);
                                        buffer.append(", ");
                                }                      
                                guessedField.setText(buffer.toString());
                        }
                } else if (c == restartCommand) {
                        addCommand(sendCommand);
                        enterField.setString("");
                        guessedField.setText("");
                        guessedWords.clear();
                        mainCmd.restart();
                }
        }

        private boolean initImages() {
                // TODO: need implementation here
                try {
                        for (int i = 0; i < images.length; i++) {
                                String imageUrl = "/hangmanclient/resources/images/HangPic" + Integer.toString(i) + ".png";
                                images[i] = Image.createImage(imageUrl);
                        }
                } catch (IOException e) {
                        System.err.println(e.getMessage());
                        return false;
                }
                return true;
        }

        public void setText(String word) {
                wordField.setText(word);
        }

        public void setDanger(int dangerLevel) {
                // TODO: Need implementation here
                if (dangerLevel > 7) {
                        dangerLevel = 7;
                }
                if (dangerLevel < 0) {
                        dangerLevel = 0;
                }
                imageItem.setImage(images[dangerLevel]);
        }
        
        /**
         * 
         * @param enteredText
         * @return
         */
        private boolean isValidString(String enteredText) {
                // Step 1) Check the input length
                 if (enteredText.length() < 1)
                         return false;
                 // Step 2) Check if there is some forbidden character
                 char[] charArray = enteredText.toCharArray();
                 for (int i = 0; i < charArray.length; i++) {
                        char c = charArray[i];
                        if (!Character.isLowerCase(c)) {
                                return false;
                        }
                 }
                Enumeration e = guessedWords.keys();
                boolean valid = true;
                // Step 3) check if the word is already guessed
                while (e.hasMoreElements()) {
                        String str = e.nextElement().toString();
                        if (str.equals(enteredText)) {
                                valid = false;
                                break;
                        }
                }
                return valid;
        }
}
