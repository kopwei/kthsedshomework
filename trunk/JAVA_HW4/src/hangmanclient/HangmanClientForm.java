/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package hangmanclient;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Vector;
import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;

/**
 * @author Kop
 */
public class HangmanClientForm extends Form implements CommandListener{   
    private TextField enterField;
    private StringItem wordField;
    private StringItem guessedField;
    
    private Image[] images = new Image[8];
    private ImageItem imageItem;
    
    
    private Command sendCommand;
    private Command exitCommand;
    
    private Hashtable guessedWords = new Hashtable();
    
    private HangmanClientCmd mainCmd = null;
    
    
    public HangmanClientForm(HangmanClientCmd cmd, String title) {
        super(title);
        initImages();
        enterField = new TextField("Input a letter or a word", "", 50, TextField.ANY);
        wordField = new StringItem("Current Word:  ", "\n", StringItem.PLAIN);
        wordField.setLabel("Word To Guess");
        guessedField = new StringItem("The words you guessed: ", "\n",  StringItem.PLAIN);
        guessedField.setLabel("Guessd Words");
        imageItem = new ImageItem(null, images[0], ImageItem.LAYOUT_NEWLINE_BEFORE | 
                ImageItem.LAYOUT_CENTER, null);
        sendCommand = new Command("Send", Command.SCREEN, 1);
        exitCommand = new Command("Exit", Command.EXIT, 0);            
        mainCmd = cmd;
    }
   
    public void initialize() {    
        addCommand(sendCommand);
        addCommand(exitCommand);     
        append(enterField);
        append(wordField);
        append(guessedField);
        append(imageItem);
        setCommandListener(this);       
    }

    public void commandAction(Command c, Displayable s) {
        if (c == exitCommand) {
            mainCmd.terminate(false);
        } else if (c == sendCommand) {
            mainCmd.send(enterField.getString());
            enterField.setString("");
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
  
}
