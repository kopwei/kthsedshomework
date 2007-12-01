/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package message.hangmanMessage;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import message.Persistent;
import message.SimpleMessage;

/**
 *
 * @author Kop
 */
public class HangmanMessage extends SimpleMessage implements Persistent{
    private HangmanMessageType messageType = HangmanMessageType.Unknown;
    
    public void setHangmanMessageType(HangmanMessageType type) { this.messageType = type; }
    public HangmanMessageType getHangmanMessageType() { return messageType; }

    public byte[] persist() throws IOException {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        DataOutputStream dout = new DataOutputStream(bout);
        
        dout.writeUTF(messageType.toString());
        dout.writeUTF(this.getContent());
        dout.flush();
        return bout.toByteArray();
    }

    public void resurrect(byte[] data) throws IOException {
        ByteArrayInputStream bin = new ByteArrayInputStream(data);
        DataInputStream din = new DataInputStream(bin);
        setContent(din.readUTF());
        messageType = HangmanMessageType.valueOf(din.readUTF());
    }
    
    

}
