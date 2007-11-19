/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package market.server;

import bank.BankAccount;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.UUID;

/**
 *
 * @author Kop
 */
public interface MarketServer extends Remote{
    
    /**
     * 
     * @return The registered account
     * @throws java.rmi.RemoteException
     */
    public ClientAccount registerAccount(String name, char[] password, BankAccount bankAccount) throws RemoteException;
    
    /**
     * 
     * @param itemName
     * @param price
     * @param type
     * @throws java.rmi.RemoteException
     */
    public void publishItemForSell(String itemName, float price, ItemType type, ClientAccount sellerAccount) throws RemoteException;
    
    /**
     * 
     * @param itemName
     * @param price
     * @throws java.rmi.RemoteException
     */
    public void publishWishItem(String itemName, float price) throws RemoteException;
    
    /**
     * 
     * @param accountName
     * @return
     * @throws java.rmi.RemoteException
     */
    public ClientAccount getClientAccount(String accountName,char[] password) throws RemoteException;
    
    /**
     * 
     * @param clientID
     * @return
     * @throws java.rmi.RemoteException
     */
    public String getClientNameByID(UUID clientID) throws RemoteException;
    
    /**
     * 
     * @param itemID
     * @param buyerAccount
     * @return
     * @throws java.rmi.RemoteException
     */
    public boolean buyItem(UUID itemID, ClientAccount buyerAccount) throws RemoteException;
    
    /**
     * 
     * @param type
     * @return
     * @throws java.rmi.RemoteException
     */
    public ArrayList<ItemForSell> getItemsByType(ItemType type) throws RemoteException;
}
