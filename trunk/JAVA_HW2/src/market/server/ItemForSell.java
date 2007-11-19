/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package market.server;

import java.rmi.RemoteException;
import java.util.UUID;

/**
 *
 * @author Kop
 */
public interface ItemForSell {

    /**
     * 
     * @return
     * @throws java.rmi.RemoteException
     */
    UUID getClientID() throws RemoteException;

    /**
     * 
     * @return
     * @throws java.rmi.RemoteException
     */
    UUID getItemID() throws RemoteException;

    /**
     * 
     * @return
     * @throws java.rmi.RemoteException
     */
    String getName() throws RemoteException;

    /**
     * 
     * @return
     * @throws java.rmi.RemoteException
     */
    float getPrice() throws RemoteException;

    /**
     * 
     * @return
     * @throws java.rmi.RemoteException
     */
    ItemType getType() throws RemoteException;
}
