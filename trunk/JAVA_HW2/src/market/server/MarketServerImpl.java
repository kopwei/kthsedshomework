/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package market.server;

import bank.BankAccount;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.UUID;

/**
 *
 * @author Kop
 */
public class MarketServerImpl extends UnicastRemoteObject implements MarketServer{
    private Hashtable<String, ClientAccount> clientAccountTable = new Hashtable<String, ClientAccount>();
    private Hashtable<UUID, ItemForSell> itemTable = new Hashtable<UUID, ItemForSell>();
    private String marketName = "No_Name";
    
    /**
     * Default constructor
     * @throws java.rmi.RemoteException
     */
    public MarketServerImpl() throws RemoteException{
        super();
    }
    
    /**
     * Constructor for initializing members
     * @param marketName
     * @throws java.rmi.RemoteException
     */
    public MarketServerImpl(String marketName) throws RemoteException {
        super();
        this.marketName = marketName;
    }

    /**
     * 
     * @param itemName
     * @param price
     * @param type
     * @param sellerAccount
     * @throws java.rmi.RemoteException
     */
    public void publishItemForSell(String itemName, float price, ItemType type, ClientAccount sellerAccount) throws RemoteException {
        UUID clientID = sellerAccount.getClientID();
        ClientAccount account = clientAccountTable.get(clientID);
        if (null == account) {
            return;
        }
        ItemForSellImpl item = new ItemForSellImpl(itemName, price, type, clientID);
        account.addItemForSell(item);
    }
    
    public void publishWishItem(String itemName, float price) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * 
     * @param accountName
     * @return
     * @throws java.rmi.RemoteException
     */
    public ClientAccount getClientAccount(String accountName,char[] password) throws RemoteException {
        Collection<ClientAccount> accounts = clientAccountTable.values();
        for (ClientAccount clientAccount : accounts) {
            if (clientAccount.getUserName().equals(accountName) && 
                    clientAccount.isValidPassword(password)) {
                return clientAccount;
            }
        }
        return null;
    }

    /**
     * 
     * @param itemID
     * @param buyerAccount
     * @return
     * @throws java.rmi.RemoteException
     */
    public boolean buyItem(UUID itemID, ClientAccount buyerAccount) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Get the list of items with a certain type
     * @param type, which indicate a unique type, if the type is unknown, then all the items will be
     * returned
     * @return the list of items
     * @throws java.rmi.RemoteException
     */
    public ArrayList<ItemForSell> getItemsByType(ItemType type) throws RemoteException {
        ArrayList<ItemForSell> list = new ArrayList<ItemForSell>();
        Collection<ItemForSell> col =  itemTable.values();
        // Iterate the item collection
        for (ItemForSell item : col) {
            if (item.getType() == type || ItemType.Unknown == type) {
                list.add(item);
            }
        }
        return list;
    }

    /**
     * 
     * @param name
     * @param password
     * @param bankAccount
     * @return
     * @throws java.rmi.RemoteException
     */
    public ClientAccount registerAccount(String name, char[] password, BankAccount bankAccount) throws RemoteException {
        ClientAccountImpl account = new ClientAccountImpl(name, password, bankAccount);
        return account;
    }

    /**
     * 
     * @param clientID
     * @return
     * @throws java.rmi.RemoteException
     */
    public String getClientNameByID(UUID clientID) throws RemoteException {
        ClientAccount account = clientAccountTable.get(clientID);
        if (null == account) return null;
        else {
            return account.getUserName();
        }
    }

}
