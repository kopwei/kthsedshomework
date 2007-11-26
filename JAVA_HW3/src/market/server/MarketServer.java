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
    //public ClientAccount registerAccount(String name, char[] password, BankAccount bankAccount) throws RemoteException;
    
    /**
     * 
     * @param itemName
     * @param price
     * @param type
     * @throws java.rmi.RemoteException
     */
    public void publishItemForSell(String itemName, float price, ItemType type, UUID sellerAccountID) throws RemoteException;
    
    /**
     * 
     * @param itemName
     * @param price
     * @throws java.rmi.RemoteException
     */
    public void publishWishItem(String itemName, float price, UUID buyerAccountID) throws RemoteException;
    
//    /**
//     * 
//     * @param accountName
//     * @return
//     * @throws java.rmi.RemoteException
//     */
//    public ClientAccount getClientAccount(String accountName,char[] password) throws RemoteException;
    
    /**
     * 
     * @param sellerID
     * @return
     * @throws java.rmi.RemoteException
     */
    public Vector<ItemForSell> getSellingItems(UUID sellerID) throws RemoteException;
    
    /**
     * 
     * @param buyerID
     * @return
     * @throws java.rmi.RemoteException
     */
    public Vector<ItemForSell> getBoughtItems(UUID buyerID) throws RemoteException;
    
    /**
     * 
     * @param clientID
     * @return
     * @throws java.rmi.RemoteException
     */
    public Vector<ItemForSell> getWishItems(UUID clientID) throws RemoteException;;
    
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
    public boolean buyItem(ItemForSell item, UUID buyerAccountID) throws RemoteException;
    
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
     * @param bankAccountName
     * @return
     * @throws java.rmi.RemoteException
     */
    public UUID login(String accountName, char[] password, String bankAccountName) throws RemoteException;
    
    /**
     * 
     * @param clientTD
     * @return
     * @throws java.rmi.RemoteException
     */
    public void logout(UUID clientID) throws RemoteException;
    
    /**
     * 
     * @param clientObj
     * @param client
     * @throws java.rmi.RemoteException
     */
    public void addClientNotifyObject(ClientInterface clientObj, UUID clientID) throws RemoteException;
}
