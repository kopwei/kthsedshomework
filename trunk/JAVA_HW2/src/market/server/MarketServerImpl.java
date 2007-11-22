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
import market.client.ClientInterface;

/**
 *
 * @author Kop
 */
public class MarketServerImpl extends UnicastRemoteObject implements MarketServer{
    private Hashtable<UUID, ClientAccount> clientAccountTable = new Hashtable<UUID, ClientAccount>();
    private Hashtable<UUID, ClientInterface> notifiableClientTable = new Hashtable<UUID, ClientInterface>();
    private Hashtable<UUID, ItemForSell> itemForSellTable = new Hashtable<UUID, ItemForSell>();
    private Hashtable<String, ItemForSell> itemWantedTable = new Hashtable<String, ItemForSell>();
    private String marketName = "No_Name";
    private MarketServerCmd mainCmd;
    
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
        this.marketName = marketName;
        this.mainCmd = cmd;
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
        ItemForSell item = new ItemForSellImpl(itemName, price, type, clientID);
        account.addItemForSell(item);
        itemForSellTable.put(item.getItemID(), item);
        Collection<ItemForSell> wantedItems = itemWantedTable.values();
        for (ItemForSell wanted : wantedItems) {
            if (wanted.match(item) && wanted.getPrice() >= item.getPrice()) {
                // TODO: We have notify
                ClientInterface client = notifiableClientTable.get(wanted.getSellerClientID());
                try {
                client.notifyItemAvailable(itemName, price);
                }
                catch(Exception e) {
                    System.err.println(e.getMessage());
                }
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
    public void publishWishItem(String itemName, float price, ClientAccount buyerAccount) throws RemoteException {
        UUID clientID = buyerAccount.getClientID();
        ClientAccount account = clientAccountTable.get(clientID);
        if (null == account) {
            return;
        }
        ItemForSell item = new ItemForSellImpl(itemName, price, clientID);
        account.addWantedItem(item);
        itemWantedTable.put(itemName, item);                
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
     * @return
     * @throws java.rmi.RemoteException
     */
    public Vector<String> getAllClientName() throws RemoteException {
        Collection<ClientAccount> clientAccounts = clientAccountTable.values();
        Vector<String> clientNames = new Vector<String>(clientAccounts.size());
        for (ClientAccount account : clientAccounts) {
            clientNames.add(account.getUserName());
        }
        return clientNames;
    }
    
    /**
     * 
     * @param itemID
     * @param buyerAccount
     * @return
     * @throws java.rmi.RemoteException
     */
    public boolean buyItem(ItemForSell item, ClientAccount buyerAccount) throws RemoteException {
        float itemPrice = item.getPrice();
        BankAccount buyerBankAccount = buyerAccount.getBankAccount();
        // Check if the buyer is able to buy the item
        if (buyerBankAccount.getBalance() < itemPrice) {
            return false;
        }
        else {
            // If the buyer can afford the item then deals
            ClientAccount seller = clientAccountTable.get(item.getSellerClientID());
            BankAccount sellerBankAccount = seller.getBankAccount();
            itemForSellTable.remove(item.getItemID());
            buyerAccount.addBoughtItem(item);
            seller.addSoldItem(item);
            sellerBankAccount.deposit(itemPrice);
            buyerBankAccount.withdraw(itemPrice);
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
    public Vector<ItemForSell> getSellsItemsByType(ItemType type) throws RemoteException {
        Vector<ItemForSell> retVector = new Vector<ItemForSell>();
        Collection<ItemForSell> col =  itemForSellTable.values();
        // Iterate the item collection
        for (ItemForSell item : col) {
            if (item.getType() == type || ItemType.Unknown == type) {
                retVector.add(item);
            }
        }
        return retVector;
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
        // Check if the account is already registered
        Collection<ClientAccount> accounts = clientAccountTable.values();
        for (ClientAccount clientAccount : accounts) {
            if (clientAccount.getUserName().equals(name)) {
                throw new RemoteException("You account is already registered");
            }
        }
        // If it is not registered then register it
        ClientAccountImpl account = new ClientAccountImpl(name, password, bankAccount);
        clientAccountTable.put(account.getClientID(), account);
        mainCmd.getMainView().refreshData();
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

    /**
     * 
     * @param accountName
     * @param password
     * @param ipAddress
     * @return
     * @throws java.rmi.RemoteException
     */
    public boolean login(String accountName, char[] password) throws RemoteException {
        ClientAccount account = getClientAccount(accountName, password);
        if (null != account) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * 
     * @param id
     * @return
     * @throws java.rmi.RemoteException
     */
    public ItemForSell getItemByID(UUID id) throws RemoteException {
        return itemForSellTable.get(id);
    }

    /**
     * 
     * @param clientObj
     * @param client
     * @throws java.rmi.RemoteException
     */
    public void addClientNotifyObject(ClientInterface clientObj, ClientAccount client) throws RemoteException {
        notifiableClientTable.put(client.getClientID(), clientObj);
    }

    /**
     * 
     * @param clientTD
     * @throws java.rmi.RemoteException
     */
    public void logout(UUID clientTD) throws RemoteException {
        notifiableClientTable.remove(clientTD);
        
    }
}
