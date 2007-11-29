/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package market.server;

import bank.Bank;
import bank.BankImpl;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Collection;
import javax.swing.JFrame;
import market.client.ClientInterface;

/**
 *
 * @author Kop
 */
public class MarketServerCmd {
    private MarketServer server;
    private Bank bank;
    private MarketServerView view;
    private DataManager dataMgr = null;
    
    public MarketServerCmd() {
        // Create the server with a defined name
        try {
            // Assign a security manager, in the event that dynamic
            // classes are loaded
//            if (System.getSecurityManager() == null) {
//                System.setSecurityManager (new RMISecurityManager());
//            }
            // Bind the server
            InetAddress add = Inet4Address.getLocalHost();
            String ip = add.getHostAddress();
            Registry registry = LocateRegistry.getRegistry (ip, 1099);
            bank = new BankImpl();
            registry.rebind("SEB", bank);           
            server = new MarketServerImpl(this, "Taobao Market");
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
    
    public DataManager getDataManager() {
        return dataMgr;
    }
    
    public boolean initDataManager(String userName, char[] passWord) {
        dataMgr = new DataManager(userName, passWord, bank);
        boolean connectionSuccess = dataMgr.publishConnection();
        if (connectionSuccess) {
            ((BankImpl) bank).setDataManager(dataMgr);
            ((MarketServerImpl) server).setDataManager(dataMgr);
            view.refreshData();
            return true;
        } else {
            return false;
        }
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
    
    /**
     * 
     */
    public void Close() {
        // Notify all logged in clients that the server is down
        Collection<ClientInterface> clientObjs = ((MarketServerImpl)server).getAllClientObj();
        for (ClientInterface clientInterface : clientObjs) {
            try {
                clientInterface.notifyServerDown();
            }
            catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
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
