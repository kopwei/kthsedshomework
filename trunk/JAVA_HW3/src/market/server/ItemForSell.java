/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package market.server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.UUID;

/**
 *
 * @author Kop
 */
public interface ItemForSell extends Remote{

    /**
     * 
     * @return
     * @throws java.rmi.RemoteException
     */
    UUID getSellerClientID() throws RemoteException;

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
    
    /**
     * 
     * @return
     * @throws java.rmi.RemoteException
     */
    ItemStateType getState() throws RemoteException;
    
    /**
     * This method is used to check if the item matches another
     * @param anotherItem
     * @return
     * @throws java.rmi.RemoteException
     */
    boolean match(ItemForSell anotherItem) throws RemoteException;
    
    /**
     * This method is used to display the item
     * @return
     * @throws java.rmi.RemoteException
     */
    String show() throws RemoteException;
}
