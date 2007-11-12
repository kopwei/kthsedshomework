/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package hangmanserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Kop
 */
public class ServerMainThread extends Thread{
    private HangmanServerViewCmd mainCmd = null;
    
    public ServerMainThread(HangmanServerViewCmd cmd) {
        mainCmd = cmd;
    }
    
    @Override public void run() {
        ServerSocket serverSocket = null;
        boolean listening = true;
        
        try {
           serverSocket  = new ServerSocket(4444);
        }
        catch (IOException ie) {
            System.err.println(ie.getMessage());
            System.exit(1);
        }
        
        while (listening) {
            try {
                Socket clientSocket = serverSocket.accept();
                Thread handler = new ClientHandler(clientSocket);
                mainCmd.getMainView().addClient(clientSocket.getInetAddress().toString());
                handler.start();
            }
            catch (IOException ie)
            {
                System.err.println(ie.getMessage());
                System.exit(1);
            }
        }
        try {
            serverSocket.close();
        }
        catch (IOException ie){
            System.err.println(ie.getMessage());
            System.exit(1);
        }
    }

}
