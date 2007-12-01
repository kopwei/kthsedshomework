/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package message;

import java.io.Serializable;

/**
 *
 * @author Kop
 */


public class SimpleMessage implements Serializable{
    private MessageType messageType = MessageType.UnknownMessage;
    private String content;
    
    /**
     * 
     * @return
     */
    public String getContent() { return content; }
    /**
     * 
     * @param content
     */
    public void setContent(String content) { this.content = content; }
    
    /**
     * 
     * @return
     */
    public MessageType getMessageType() { return messageType; }
    /**
     * 
     * @param type
     */
    public void setMessageType(MessageType type) { this.messageType = type; }
}
