/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package market.client;

import java.net.MalformedURLException;
import java.rmi.*;
import bank.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Ricky
 */
public class ClientImpl implements ClientInterface{
    private MarketClientView clientView = null;
    private String clientName = null;
    
    public ClientImpl(MarketClientView cv) {
            this.clientView = cv;
    }
    
    public void notifyItemSoldout(Item item) throws RemoteException {
        Item soldOutItem = item;
        System.out.println("Your item " + soldOutItem.getItemName() + " has been sold out in $" 
                + soldOutItem.getItemPrice());
    }

    public void notifyItemAvailable(Item item) throws RemoteException {
        Item interestedItem = item;
        System.out.println("The item " + interestedItem.getItemName() + " with your interested price $" 
                + interestedItem.getItemPrice() + " is available.");
    }
}
