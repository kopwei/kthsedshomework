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
    private UUID itemId = null;
    private UUID clientID = null;
    
    /**
     * Constructor for initiating the Item
     * @param name
     * @param price
     */
    public ItemForSellImpl(String name, float price, ItemType type, UUID clientID) throws RemoteException {
        super();
        this.name = name;
        this.price = price;
        this.type = type;
        this.itemId = UUID.randomUUID();
        this.clientID = clientID;
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
    
    public UUID getClientID() {
        return clientID;
    }
    
}
