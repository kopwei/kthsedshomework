/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package hangmanserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

/**
 *
 * @author Kop
 */
public class ServerMainThread extends Thread{
    private HangmanServerViewCmd mainCmd = null;
    private boolean listening = true;
    
    private Vector<ClientHandler> handlerVector = new Vector<ClientHandler>();
    
    public ServerMainThread(HangmanServerViewCmd cmd) {
        mainCmd = cmd;
    }
    
    @Override
    public void run() {
        ServerSocket serverSocket = null;
        int portNumber = Integer.parseInt(mainCmd.getMainView().getPortNumberString());
        
        try {
            serverSocket  = new ServerSocket(portNumber);
        }
        catch (IOException ie) {
            System.err.println(ie.getMessage());
            System.exit(1);
        }
        System.out.println(portNumber);
        
        while (listening) {
            try {
                Socket clientSocket = serverSocket.accept();
                ClientHandler handler = new ClientHandler(clientSocket, mainCmd);
                handlerVector.add(handler);
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
    
    public void stopRunning() {
        listening = false;
    }
    
    public void stopAllThreads() {
        for (ClientHandler clientHandler : handlerVector) {
            clientHandler.terminateServer();
        }
    }

}
