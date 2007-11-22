/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package market.server;

import bank.BankAccount;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;
import java.util.Collection;
import java.util.Hashtable;
import java.util.UUID;

/**
 *
 * @author Kop
 */
public class ClientAccountImpl extends UnicastRemoteObject implements ClientAccount{
    private String userName = null;
    private char[] passWord = null;
    private BankAccount bankAccount = null;
    private UUID clientAccountID = null;
    private Hashtable<UUID, ItemForSell> itemsForSell = new Hashtable<UUID, ItemForSell>();
    private Hashtable<UUID, ItemForSell> boughtItems = new Hashtable<UUID, ItemForSell>();
    private Hashtable<UUID, ItemForSell> soldItems = new Hashtable<UUID, ItemForSell>();
    private Hashtable<String, ItemForSell> wantedItems = new Hashtable<String, ItemForSell>();
    
    
    /**
     * Constructor which used to initialize all members
     * @param userName the user name of the client account
     * @param passWord
     */
    public ClientAccountImpl(String userName, char[] passWord, BankAccount bankAccount) throws RemoteException{
        super();
        this.userName = userName;
        this.passWord = passWord;
        clientAccountID = UUID.randomUUID();
        this.bankAccount = bankAccount;
    }
    
    /**
     * 
     * @return
     */
    public String getUserName() throws RemoteException {
        return userName;
    }
    
    /**
     * 
     * @return
     */
    public BankAccount getBankAccount() throws RemoteException {
        return bankAccount;
    }
    
    /**
     * 
     * @return
     */
    public UUID getClientID() throws RemoteException {
        return clientAccountID;
    }

    /**
     * 
     * @param password
     * @return
     * @throws java.rmi.RemoteException
     */
    public boolean isValidPassword(char[] password) throws RemoteException {
        if (password.equals(this.passWord)) {
            return true;
        }
        else {
            return false;
        }
    }
    

    /**
     * 
     * @return
     * @throws java.rmi.RemoteException
     */
    public Vector<ItemForSell> getSellersItem() throws RemoteException {
        Vector<ItemForSell> returnItems = new Vector<ItemForSell>();
        Collection<ItemForSell> col = itemsForSell.values();
        for (ItemForSell itemForSell : col) {
            returnItems.add(itemForSell);
        }
        return returnItems;
    }

    /**
     * 
     * @param item
     * @throws java.rmi.RemoteException
     */
    public void addItemForSell(ItemForSell item) throws RemoteException {
        UUID uuid = item.getItemID();
        itemsForSell.put(uuid, item);
    }

    /**
     * 
     * @return
     * @throws java.rmi.RemoteException
     */
    public Vector<ItemForSell> getBoughtItems() throws RemoteException {
        Vector<ItemForSell> returnItems = new Vector<ItemForSell>();
        Collection<ItemForSell> col = boughtItems.values();
        for (ItemForSell boughtItem : col) {
            returnItems.add(boughtItem);
        }
        return returnItems;
    }

    /**
     * 
     * @param item
     * @throws java.rmi.RemoteException
     */
    public void addBoughtItem(ItemForSell item) throws RemoteException {
        UUID uuid = item.getItemID();
        boughtItems.put(uuid, item);
    }
    
    /**
     * 
     * @return
     * @throws java.rmi.RemoteException
     */
    public Vector<ItemForSell> getSoldItems() throws RemoteException {
        Vector<ItemForSell> returnItems = new Vector<ItemForSell>();
        Collection<ItemForSell> col = soldItems.values();
        for (ItemForSell soldItem : col) {
            returnItems.add(soldItem);
        }
        return returnItems;
    }

    /**
     * 
     * @param item
     * @throws java.rmi.RemoteException
     */
    public void addSoldItem(ItemForSell item) throws RemoteException {
        UUID uuid = item.getItemID();
        soldItems.put(uuid, item);
    }

    /**
     * 
     * @return
     * @throws java.rmi.RemoteException
     */
    public Vector<ItemForSell> getWantedItems() throws RemoteException {
        Vector<ItemForSell> returnItems = new Vector<ItemForSell>();
        Collection<ItemForSell> col = wantedItems.values();
        for (ItemForSell wantedItem : col) {
            returnItems.add(wantedItem);
        }
        return returnItems;
    }

    /**
     * 
     * @param item
     * @throws java.rmi.RemoteException
     */
    public void addWantedItem(ItemForSell item) throws RemoteException {
        //UUID uuid = item.getItemID();
        wantedItems.put(item.getName(), item);
    }
}
