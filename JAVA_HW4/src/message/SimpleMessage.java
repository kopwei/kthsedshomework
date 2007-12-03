/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package message;

/**
 *
 * @author Kop
 */
public class SimpleMessage {

        private String messageType = MessageType.UnknownMessage;
        private String content = "Hello";

        public SimpleMessage() {
                messageType = MessageType.UnknownMessage;
                content = "Hello";
        }

        /**
         * 
         * @return
         */
        public String getContent() {
                return content;
        }

        /**
         * 
         * @param content
         */
        public void setContent(String content) {
                this.content = content;
        }

        /**
         * 
         * @return
         */
        public String getMessageType() {
                return messageType;
        }

        /**
         * 
         * @param type
         */
        public void setMessageType(String type) {
                this.messageType = type;
        }
}
