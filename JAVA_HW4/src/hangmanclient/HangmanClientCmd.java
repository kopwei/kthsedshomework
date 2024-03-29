/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hangmanclient;

import java.io.InputStream;
import java.io.OutputStream;
import javax.microedition.io.StreamConnection;
import javax.microedition.lcdui.Display;
import javax.microedition.midlet.MIDletStateChangeException;

/**
 *
 * @author Kop
 */
public class HangmanClientCmd {

        private HangmanClient mainApp = null;
        private Display display;
        private HangmanClientForm clientForm = null;
        private HangmanConnectForm connectForm = null;
        private CommunicationHandler handler = null;
        private String serverIP = null;
        private String serverPort = null;
        private int dangerLevel = 0;
        private StreamConnection connection = null;
        private OutputStream outStream = null;
        private InputStream inputStream = null;
        //private boolean terminated = false;

        HangmanClientCmd(HangmanClient app) {
                mainApp = app;
                display = Display.getDisplay(mainApp);
                clientForm = new HangmanClientForm(this, "Welcome");
                connectForm = new HangmanConnectForm(this, "Connect");
        }

        public HangmanClient getApplication() {
                return mainApp;
        }

        public void start() {
                connectForm.initialize();
                clientForm.initialize();
                display.setCurrent(connectForm);
        }

        public void terminate(boolean unconditional) {
                try {
                        //terminated = true;
                        handler = new CommunicationHandler(this);
                        handler.setCommandString(CommunicationHandler.TERMINATE);
                        handler.start();
                        mainApp.destroyApp(unconditional);
                        mainApp.notifyDestroyed();
                } catch (MIDletStateChangeException ex) {
                        System.err.println(ex.getMessage());
                }
        }

        private void victory() {
                // Tell the server thr round is over
                //terminated = true;
                clientForm.victory();
        }

        private void lose() {
                // Tell the server thr round is over and display the whole word
                //terminated = true;
                handler = new CommunicationHandler(this);
                handler.setCommandString(CommunicationHandler.GAMEOVER);
                handler.start();
                clientForm.lose();
        }

        public String getServerIP() {
                return this.serverIP;
        }

        public StreamConnection getConnection() {
                return this.connection;
        }

        public OutputStream getOutputStream() {
                return outStream;
        }

        public InputStream getInputStream() {
                return inputStream;
        }

        public void setConnectionSocket(StreamConnection con) {
                this.connection = con;
        }

        public void setOutputStream(OutputStream stream) {
                this.outStream = stream;
        }

        public void setInputStream(InputStream stream) {
                this.inputStream = stream;
        }

        public String getServerPort() {
                return this.serverPort;
        }

        private void changeToClientForm() {
                // TODO: Need implementation here
                display.setCurrent(clientForm);
        }

        public void setString(String word) {
                // TODO: Need implementation here
                clientForm.setText(word);
                char[] charArray = word.toCharArray();
                boolean isVictory = true;
                for (int i = 0; i < charArray.length; i++) {
                        char c = charArray[i];
                        if (c == '_') {
                                isVictory = false;
                                break;
                        }
                }
                if (isVictory) {
                        victory();
                }
                
        }

        public boolean send(String str) {
                if (null != str) {
                        handler = new CommunicationHandler(this);
                        handler.setCommandString(CommunicationHandler.CHECKINPUT);
                        handler.setInputString(str);
                        handler.start();
                        return true;

                } else {
                        return false;
                }
        }

        public void increaseDanger() {
                dangerLevel++;
                clientForm.setDanger(dangerLevel);
                if (dangerLevel == 7) {
                        lose();
                }
        }

        public boolean restart() {
                if (null != serverIP && null != serverPort) {
                        this.dangerLevel = 0;
                        clientForm.setDanger(dangerLevel);
                        handler = new CommunicationHandler(this);
                        handler.setCommandString(CommunicationHandler.STARTROUND);
                        handler.start();
                        return true;
                } else {
                        return false;
                }
        }

        public boolean connect(String ip, String portnumber) {
                // Check the validity of server IP and port
                if (null != ip && null != portnumber) {
                        this.serverIP = ip;
                        this.serverPort = portnumber;
                        changeToClientForm();
                        this.dangerLevel = 0;
                        clientForm.setDanger(dangerLevel);
                        handler = new CommunicationHandler(this);
                        handler.setCommandString(CommunicationHandler.STARTROUND);
                        handler.start();
                        return true;
                } // If the IP and port are not properly set, then warn user
                else {
                        return false;
                }
        }
}
