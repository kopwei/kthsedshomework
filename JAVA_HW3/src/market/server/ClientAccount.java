/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package market.server;

import java.util.UUID;

/**
 *
 * @author Kop
 */
public class ClientAccount{
    private String userName = null;
    private char[] passWord = null;
    private String bankAccountName = null;
    private UUID clientAccountID = null;
//    private Hashtable<UUID, ItemForSell> itemsForSell = new Hashtable<UUID, ItemForSell>();
//    private Hashtable<UUID, ItemForSell> boughtItems = new Hashtable<UUID, ItemForSell>();
//    private Hashtable<UUID, ItemForSell> soldItems = new Hashtable<UUID, ItemForSell>();
//    private Hashtable<String, ItemForSell> wantedItems = new Hashtable<String, ItemForSell>();
    
    
    /**
     * Constructor which used to initialize all members
     * @param userName the user name of the client account
     * @param passWord
     */
    public ClientAccount(String userName, char[] passWord, String bankAccountName) {
        this.userName = userName;
        this.passWord = passWord;
        clientAccountID = UUID.randomUUID();
        this.bankAccountName = bankAccountName;
    }
    
    /**
     * 
     * @return
     */
    public String getUserName() {
        return userName;
    }
    
    /**
     * 
     * @return
     */
    public char[] getPassword() {
        return passWord;
    }
    
    /**
     * 
     * @return
     */
    public String getBankAccountName() {
        return bankAccountName;
    }
    
    /**
     * 
     * @return
     */
    public UUID getClientID() {
        return clientAccountID;
    }

   /**
    * 
    * @param password
    * @return
    */
    public boolean isValidPassword(char[] password) {
        if (password.equals(this.passWord)) {
            return true;
        }
        else {
            return false;
        }
    }
    
    @Override
    public String toString() {
        return userName;
    }
    

//    /**
//     * 
//     * @return
//     * @throws java.rmi.RemoteException
//     */
//    public Vector<ItemForSell> getSellersItem() throws RemoteException {
//        Vector<ItemForSell> returnItems = new Vector<ItemForSell>();
//        Collection<ItemForSell> col = itemsForSell.values();
//        for (ItemForSell itemForSell : col) {
//            returnItems.add(itemForSell);
//        }
//        return returnItems;
//    }
//
//    /**
//     * 
//     * @param item
//     * @throws java.rmi.RemoteException
//     */
//    public void addItemForSell(ItemForSell item) throws RemoteException {
//        UUID uuid = item.getItemID();
//        itemsForSell.put(uuid, item);
//    }
//    
//    /**
//     * 
//     * @param item
//     * @throws java.rmi.RemoteException
//     */
//    public void removeItemForSell(ItemForSell item) throws RemoteException {
//        UUID uuid = item.getItemID();
//        itemsForSell.remove(uuid);
//    }
//    
//
//    /**
//     * 
//     * @return
//     * @throws java.rmi.RemoteException
//     */
//    public Vector<ItemForSell> getBoughtItems() throws RemoteException {
//        Vector<ItemForSell> returnItems = new Vector<ItemForSell>();
//        Collection<ItemForSell> col = boughtItems.values();
//        for (ItemForSell boughtItem : col) {
//            returnItems.add(boughtItem);
//        }
//        return returnItems;
//    }
//
//    /**
//     * 
//     * @param item
//     * @throws java.rmi.RemoteException
//     */
//    public void addBoughtItem(ItemForSell item) throws RemoteException {
//        UUID uuid = item.getItemID();
//        boughtItems.put(uuid, item);
//    }
//    
//    /**
//     * 
//     * @return
//     * @throws java.rmi.RemoteException
//     */
//    public Vector<ItemForSell> getSoldItems() throws RemoteException {
//        Vector<ItemForSell> returnItems = new Vector<ItemForSell>();
//        Collection<ItemForSell> col = soldItems.values();
//        for (ItemForSell soldItem : col) {
//            returnItems.add(soldItem);
//        }
//        return returnItems;
//    }
//
//    /**
//     * 
//     * @param item
//     * @throws java.rmi.RemoteException
//     */
//    public void addSoldItem(ItemForSell item) throws RemoteException {
//        UUID uuid = item.getItemID();
//        soldItems.put(uuid, item);
//    }
//
//    /**
//     * 
//     * @return
//     * @throws java.rmi.RemoteException
//     */
//    public Vector<ItemForSell> getWantedItems() throws RemoteException {
//        Vector<ItemForSell> returnItems = new Vector<ItemForSell>();
//        Collection<ItemForSell> col = wantedItems.values();
//        for (ItemForSell wantedItem : col) {
//            returnItems.add(wantedItem);
//        }
//        return returnItems;
//    }
    
    

//    /**
//     * 
//     * @param item
//     * @throws java.rmi.RemoteException
//     */
//    public void addWantedItem(ItemForSell item) throws RemoteException {
//        //UUID uuid = item.getItemID();
//        wantedItems.put(item.getName(), item);
//    }


}
