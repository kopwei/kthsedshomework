/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package market.server;

import bank.Bank;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import market.client.ClientInterface;

/**
 *
 * @author Kop
 */
public class MarketServerImpl extends UnicastRemoteObject implements MarketServer{
    //private Hashtable<UUID, ClientAccount> clientAccountTable = new Hashtable<UUID, ClientAccount>();
    private Hashtable<UUID, ClientInterface> notifiableClientTable = new Hashtable<UUID, ClientInterface>();
    //private Hashtable<UUID, ItemForSell> itemForSellTable = new Hashtable<UUID, ItemForSell>();
    //private Hashtable<String, ItemForSell> itemWantedTable = new Hashtable<String, ItemForSell>();
    private String marketName = "No_Name";
    private MarketServerCmd mainCmd;
    private DataManager dataManager = null;
    private Bank bank = null;
    
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
    public MarketServerImpl(MarketServerCmd cmd, String marketName) throws RemoteException {
        super();
        try {
            this.marketName = marketName;
            this.mainCmd = cmd;
            this.dataManager = cmd.getDataManager();
            bank = (Bank) Naming.lookup("rmi://localhost/SEB");
        } catch (NotBoundException ex) {
            Logger.getLogger(MarketServerImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(MarketServerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * 
     * @param itemName
     * @param price
     * @param type
     * @param sellerAccount
     * @throws java.rmi.RemoteException
     */
    public void publishItemForSell(String itemName, float price, ItemType type, UUID sellerID) throws RemoteException {
        if (null == dataManager) {
            return;
        }
        ClientAccount account = dataManager.getClientAccountByID(sellerID);
        if (null == account) {
            return;
        }
        ItemForSell item = new ItemForSellImpl(itemName, price, type, sellerID);
        dataManager.storeItem(item);
        
        
        Collection<ItemForSell> wantedItems = dataManager.getMatchedWishItems(item);
        for (ItemForSell wanted : wantedItems) {         
            // We have notify the notifiable clients
            ClientInterface client = notifiableClientTable.get(wanted.getWisherClientID());
            try {
                client.notifyItemAvailable(itemName, price);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
            
        }
        mainCmd.getMainView().refreshData();
    }
    
    /**
     * 
     * @param itemName
     * @param price
     * @param buyerAccount
     * @throws java.rmi.RemoteException
     */
    public void publishWishItem(String itemName, float price, UUID buyerAccountID) throws RemoteException {
        if (null == dataManager) {
            return;
        }
        ClientAccount account = dataManager.getClientAccountByID(buyerAccountID);
        if (null == account) {
            return;
        }
        ItemForSell wish = new ItemForSellImpl(itemName, price, buyerAccountID);
        //account.addWantedItem(item);
        dataManager.storeWish(wish);                
    }

    public void setDataManager(DataManager dataManager) {
        this.dataManager = dataManager;
    }
    
    /**
     * 
     * @return
     * @throws java.rmi.RemoteException
     */
    
    
    /**
     * 
     * @param itemID
     * @param buyerAccount
     * @return
     * @throws java.rmi.RemoteException
     */
    public boolean buyItem(ItemForSell item, UUID buyerAccountID) throws RemoteException {
        //ItemForSellImpl itemForSell = (ItemForSellImpl)item;
        float itemPrice = item.getPrice();
        ClientAccount buyerAccount = dataManager.getClientAccountByID(buyerAccountID);
        String buyerBankAccountName = buyerAccount.getBankAccountName();
        // BankAccount buyerBankAccount = dataManager.getBankAccountByName(buyerAccount.getBankAccountName());
        // Check if the buyer is able to buy the item
        if (bank.getBalance(buyerBankAccountName) < itemPrice) {
            return false;
        }
        else {
            // If the buyer can afford the item then deals
            ClientAccount seller = dataManager.getClientAccountByID(item.getSellerClientID());
            String sellerBankAccountName = seller.getBankAccountName();
            item.setSold();
            item.setBuyerID(buyerAccountID);
            dataManager.updateItem(item);
            
            bank.deposit(sellerBankAccountName, itemPrice);
            bank.withdraw(buyerBankAccountName, itemPrice);
            ClientInterface notifiableClient = notifiableClientTable.get(seller.getClientID());
            try {
                notifiableClient.notifyItemSoldout(item.getName(), itemPrice);
            } catch(Exception e) {
                System.err.println(e.getMessage());
            }
            mainCmd.getMainView().refreshData();
            return true;
        }
    }

    /**
     * Get the retVector of items with a certain type
     * @param type, which indicate a unique type, if the type is unknown, then all the items will be
     * returned
     * @return the retVector of items
     * @throws java.rmi.RemoteException
     */
    public Vector<ItemForSell> getSellingItemsByType(ItemType type) throws RemoteException {
        if (null == dataManager) {
            return null;
        } else {
            return dataManager.getSellingItemsByType(type);
        }
    }

    /**
     * 
     * @param name
     * @param password
     * @param bankAccount
     * @return
     * @throws java.rmi.RemoteException
     */
    private UUID registerAccount(String name, char[] password, String bankAccountName) throws RemoteException {
        // Check if the account is already registered
        if (dataManager.isClientAccountExist(name)) {
            throw new RemoteException("You account is already registered");
        }
        // If it is not registered then register it
        ClientAccount account = new ClientAccount(name, password, bankAccountName);
        dataManager.storeClientAccount(account);
        mainCmd.getMainView().refreshData();
        return account.getClientID();
    }

    /**
     * 
     * @param clientID
     * @return
     * @throws java.rmi.RemoteException
     */
    public String getClientNameByID(UUID clientID) throws RemoteException {
        ClientAccount account = dataManager.getClientAccountByID(clientID);
        if (null == account) return null;
        else {
            return account.getUserName();
        }
    }

    /**
     * 
     * @param accountName
     * @param password
     * @param ipAddress
     * @return
     * @throws java.rmi.RemoteException
     */
    public UUID login(String accountName, char[] password, String bankAccountName) throws RemoteException {
        ClientAccount account = dataManager.getClientAccountByNameAndPassword(accountName, password);
        if (null != account) {
            return account.getClientID();
        }
        else {
            return registerAccount(accountName, password, bankAccountName);
        }
    }

    /**
     * 
     * @param id
     * @return
     * @throws java.rmi.RemoteException
     */
    public ItemForSell getItemByID(UUID id) throws RemoteException {
        return dataManager.getItemByID(id);
    }

    /**
     * 
     * @param clientObj
     * @param client
     * @throws java.rmi.RemoteException
     */
    public void addClientNotifyObject(ClientInterface clientObj, UUID clientID) throws RemoteException {
        // Check if it is already added
        Enumeration<UUID> ids = notifiableClientTable.keys();
        while (ids.hasMoreElements()) {
            UUID uuid = ids.nextElement();
            if (uuid.equals(clientID)) {
                throw new RemoteException("You have already logged in and you can not login twice");
            }
        }
        // Else admit login
        notifiableClientTable.put(clientID, clientObj);
        mainCmd.getMainView().refreshData();
    }
    
    public Vector<ClientAccount> getAllNotifiableClients() {
        Enumeration<UUID> notifiableClientIDs = notifiableClientTable.keys();
        Vector<ClientAccount> returnVec = new Vector<ClientAccount>();
        while (notifiableClientIDs.hasMoreElements()) {
            UUID uuid = notifiableClientIDs.nextElement();
            ClientAccount client = dataManager.getClientAccountByID(uuid);
            if (null != client) {
                returnVec.add(client);
            }
        }
        return returnVec;
    }
    
    /**
     * 
     * @return
     */
    public Collection<ClientInterface> getAllClientObj() {
        return notifiableClientTable.values();
    }

    /**
     * 
     * @param clientTD
     * @throws java.rmi.RemoteException
     */
    public void logout(UUID clientID) throws RemoteException {
        notifiableClientTable.remove(clientID);
        mainCmd.getMainView().refreshData();
    }

    /**
     * 
     * @param sellerID
     * @return
     * @throws java.rmi.RemoteException
     */
    public Vector<ItemForSell> getSellingItems(UUID sellerID) throws RemoteException {
        return dataManager.getSellingItems(sellerID);
    }

    /**
     * 
     * @param buyerID
     * @return
     * @throws java.rmi.RemoteException
     */
    public Vector<ItemForSell> getBoughtItems(UUID buyerID) throws RemoteException {
        return dataManager.getBoughtItems(buyerID);
    }

    /**
     * 
     * @param clientID
     * @return
     * @throws java.rmi.RemoteException
     */
    public Vector<ItemForSell> getWishItems(UUID clientID) throws RemoteException {
        return dataManager.getWishItems(clientID);
    }

    /**
     * 
     * @param sellerID
     * @return
     * @throws java.rmi.RemoteException
     */
    public Vector<ItemForSell> getSoldItems(UUID sellerID) throws RemoteException {
        return dataManager.getSoldItems(sellerID);
    }
}
