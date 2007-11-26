/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package market.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.UUID;

/**
 *
 * @author Kop
 */
public class ItemForSellImpl extends UnicastRemoteObject implements ItemForSell {
    private String name = null;
    private float price = 0;    
    private ItemType type = ItemType.Unknown;
    private ItemStateType state = ItemStateType.OnSell;
    private UUID itemId = null;
    private UUID sellerID = null;
    private UUID buyerID = null;
    private UUID wisherID = null;
    
    /**
     * Constructor for initiating the Item
     * @param name
     * @param price
     */
    public ItemForSellImpl(String name, float price, ItemType type,UUID sellerID) throws RemoteException {
        super();
        this.name = name;
        this.price = price;
        this.type = type;
        this.itemId = UUID.randomUUID();
        this.sellerID = sellerID;
    }
    /**
     * 
     * @param name
     * @param price
     * @throws java.rmi.RemoteException
     */
    public ItemForSellImpl(String name, float price, UUID wisherID) throws RemoteException {
        super();
        this.name = name;
        this.price = price;
        this.wisherID = wisherID;
    }
        
    /**
     * 
     * @return
     */
    public float getPrice() throws RemoteException {
        return price;
    }
    
    /**
     * 
     * @return
     */
    public String getName() throws RemoteException {
        return name;
    }
    
    /**
     * 
     * @return
     */
    public ItemType getType() {
        return type;
    }
    
    /**
     * 
     * @return
     */
    public UUID getItemID() {
        return itemId;
    }
    
    /**
     * 
     * @return
     */
    public UUID getSellerClientID() {
        return sellerID;
    }
    
    /**
     * 
     * @return
     */
    public UUID getWisherClientID() {
        return wisherID;
    }
    
    /**
     * 
     * @return
     */
    public UUID getBuyerClientID() {
        return buyerID;
    }
    
    /**
     * 
     * @param buyerID
     */
    public void setBuyerID(UUID buyerID) {
        this.buyerID = buyerID;
    }

    /**
     * This method is used to check if the item matches another
     * @param anotherItem
     * @return if the two item matches
     * @throws java.rmi.RemoteException
     */
    public boolean match(ItemForSell anotherItem) throws RemoteException {
        if (this.itemId != null & anotherItem.getItemID() != null) {
            return (this.itemId.equals(anotherItem.getItemID()));
        }
        else {
            return this.name.equals(anotherItem.getName());
        }
    }
    
    /**
     * 
     * @return
     */
    @Override
    public String toString() {
        return name;
    }

    /**
     * 
     * @return
     * @throws java.rmi.RemoteException
     */
    public String show() throws RemoteException {
        StringBuffer buffer = new StringBuffer();
        buffer.append(name + "\n");
        buffer.append("Price: " + price + "\n");
        //buffer.append("Seller : ");
        return buffer.toString();
    }

    /**
     * 
     * @return
     * @throws java.rmi.RemoteException
     */
    public ItemStateType getState() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    /**
     * 
     */
    public void setSold() {
        this.state = ItemStateType.Sold;
    }
}
