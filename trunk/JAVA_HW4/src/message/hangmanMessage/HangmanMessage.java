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
public class HangmanMessage extends SimpleMessage implements Persistent {

        private String messageType = HangmanMessageType.Unknown;

        public HangmanMessage() {
                super();
                messageType = HangmanMessageType.Unknown;
        }

        public void setHangmanMessageType(String type) {
                this.messageType = type;
        }

        public String getHangmanMessageType() {
                return messageType;
        }

        public byte[] persist() throws IOException {
                ByteArrayOutputStream bout = new ByteArrayOutputStream();
                DataOutputStream dout = new DataOutputStream(bout);

                dout.writeUTF(messageType);
                //System.out.println("The message type wrote is " + messageType.toString());
                dout.writeUTF(this.getContent());
                //System.out.println("The content wrote is " + this.getContent());
                dout.flush();
                return bout.toByteArray();
        }

        public void resurrect(byte[] data) throws IOException {
                ByteArrayInputStream bin = new ByteArrayInputStream(data);
                DataInputStream din = new DataInputStream(bin);

                String type = din.readUTF();
                //System.out.println("The type is " + type);
                String content = din.readUTF();
                setContent(content);
                //System.out.println("The content is " + content);
                messageType = type;
        }
}
