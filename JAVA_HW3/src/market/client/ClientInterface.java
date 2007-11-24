/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package market.client;

import java.rmi.*;
/**
 *
 * @author Ricky
 */
public interface ClientInterface extends Remote{
    /**
     * notify client that somebody has bought his item
     * @param item
     * @throws java.rmi.RemoteException
     */
    public void notifyItemSoldout(String itemName, float itemPrice) throws RemoteException;
    /**
     * notify client that some item that matches his "wish" price is available
     * @param item
     * @throws java.rmi.RemoteException
     */
    public void notifyItemAvailable(String itemName, float itemPrice) throws RemoteException;
    /**
     * notify client that server is shut down
     * @throws java.rmi.RemoteException
     */
    public void notifyServerDown() throws RemoteException;
}
