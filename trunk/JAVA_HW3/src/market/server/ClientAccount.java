/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package market.server;

import bank.BankAccount;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Vector;
import java.util.UUID;



/**
 *
 * @author Kop
 */
public interface ClientAccount extends Remote{
    /**
     * Get the user name of the account
     * @return
     */
    public String getUserName() throws RemoteException;
    
    /**
     * Get the bank account of the client
     * @return
     */
    public BankAccount getBankAccount() throws RemoteException;
    
    /**
     * Get the UUID of the client
     * @return
     */
    public UUID getClientID() throws RemoteException;
    
    /**
     * 
     * @param password
     * @return
     * @throws java.rmi.RemoteException
     */
    public boolean isValidPassword(char[] password) throws RemoteException;
    
    /**
     * 
     * @return
     * @throws java.rmi.RemoteException
     */
    public Vector<ItemForSell> getSellersItem() throws RemoteException;
    
    /**
     * 
     * @throws java.rmi.RemoteException
     */
    public void addItemForSell(ItemForSell item) throws RemoteException;
    
    /**
     * 
     * @param item
     * @throws java.rmi.RemoteException
     */
    public void removeItemForSell(ItemForSell item) throws RemoteException;
    
    /**
     * 
     * @param item
     * @throws java.rmi.RemoteException
     */
    public void addBoughtItem(ItemForSell item) throws RemoteException;
    
    /**
     * 
     * @return
     * @throws java.rmi.RemoteException
     */
    public Vector<ItemForSell> getBoughtItems() throws RemoteException;
    
    /**
     * 
     * @return
     * @throws java.rmi.RemoteException
     */
    public Vector<ItemForSell> getSoldItems() throws RemoteException;
    
    /**
     * 
     * @param item
     * @throws java.rmi.RemoteException
     */
    public void addSoldItem(ItemForSell item) throws RemoteException;
    
    /**
     * 
     * @param itemName
     * @param acceptencePrice
     * @throws java.rmi.RemoteException
     */
    public void addWantedItem(ItemForSell item) throws RemoteException;
    
    /**
     * 
     * @return
     * @throws java.rmi.RemoteException
     */
    public Vector<ItemForSell> getWantedItems() throws RemoteException;
}
