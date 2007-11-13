/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package hangmanserver;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

/**
 *
 * @author Kop
 */
public class WordReader {
    /**
     * 
     * @return
     */
    public static synchronized String getWord() {
        StringBuffer buffer = new StringBuffer();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("words.dic"));
            Random rand = new Random(System.currentTimeMillis());
            
            while(true) {
                int randInt = rand.nextInt(25140) + 1;
                for (int i = 0; i < randInt; i++) {
                    int length = buffer.length();
                    buffer.delete(0, length);
                    buffer.append(reader.readLine());
                }
                if (isWordValid(buffer)) {
                    break;
                }
            } 
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
        return buffer.toString();
    }
    
    /**
     * 
     * @param word
     * @return
     */
    private static boolean isWordValid(StringBuffer word) {
        boolean isValid = true;
        int length = word.length();
        // the length of word can not larger than 10
        if (length > 10) return false;
        // Check if the word contains invalid characters.
        for (int i = 0; i < length; i++) {
            char c = word.charAt(i);
            int charValue = (int)c;
            if (charValue > 122 || charValue < 97) {
                isValid = false;
                break;
            }
        }
        return isValid;
    }
}
