/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package message.hangmanMessage;

import message.SimpleMessage;

/**
 *
 * @author Kop
 */
public class HangmanMessage extends SimpleMessage{
    private HangmanMessageType messageType = HangmanMessageType.Unknown;
    
    public void setHangmanMessageType(HangmanMessageType type) { this.messageType = type; }
    public HangmanMessageType getHangmanMessageType() { return messageType; }

}
