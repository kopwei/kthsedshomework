/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package market.server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import javax.swing.JFrame;

/**
 *
 * @author Kop
 */
public class MarketServerCmd {
    private MarketServer server;
    private MarketServerView view;
    
    public MarketServerCmd() {
        // Create the server with a defined name
        try {
            // Assign a security manager, in the event that dynamic
            // classes are loaded
//            if (System.getSecurityManager() == null) {
//                System.setSecurityManager (new RMISecurityManager());
//            }

            // Bind the server
            server = new MarketServerImpl(this, "Taobao Market");
            Registry registry = LocateRegistry.getRegistry ("localhost", 1099);
            registry.rebind("TaobaoServer", server);
            //Naming.rebind("taobao", server);
//        } catch (AlreadyBoundException ex) {
//            Logger.getLogger(MarketServerCmd.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (MalformedURLException ex) {
//            Logger.getLogger(MarketServerCmd.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            //Logger.getLogger(MarketServerCmd.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println(ex.getMessage());
        } catch(Exception ex) {
            //Logger.getLogger(MarketServerCmd.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println(ex.getMessage());
        }
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
    
    /**
     * Get the server object
     * @return
     */
    public MarketServer getServer() { 
        return server;
    }
    
    /**
     * Get the main view object
     * @return
     */
    public MarketServerView getMainView() {
        return view;
    }
   
    public static void main(String[] args) {
        new MarketServerCmd();
    }
    
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private void createAndShowGUI() {
        //Create and set up the window.
        view = new MarketServerView(this);
        view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Display the window.
        view.setVisible(true);
    }

}