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
import market.client.ClientInterface;

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
    public void publishWishItem(String itemName, float price, ClientAccount buyerAccount) throws RemoteException;
    
    /**
     * 
     * @param accountName
     * @return
     * @throws java.rmi.RemoteException
     */
    public ClientAccount getClientAccount(String accountName,char[] password) throws RemoteException;
    
    /**
     * 
     * @return
     * @throws java.rmi.RemoteException
     */
    public Vector<String> getAllClientName() throws RemoteException;
    
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
    public Vector<ItemForSell> getSellsItemsByType(ItemType type) throws RemoteException;
    
  
    /**
     * 
     * @param id
     * @return
     * @throws java.rmi.RemoteException
     */
    public ItemForSell getItemByID(UUID id) throws RemoteException;
    
    /**
     * 
     * @param accountName
     * @param password
     * @param ipAddress
     * @return
     * @throws java.rmi.RemoteException
     */
    public boolean login(String accountName, char[] password, String ipAddress) throws RemoteException;
    
    public void addClientNotifyObject(ClientInterface clientObj, UUID clientID) throws RemoteException;
}
