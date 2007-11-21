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
    
    public void notifyItemSoldout(String itemName, float itemPrice) throws RemoteException {
        System.out.println("Your item " + itemName + " has been sold out in $" 
                + itemPrice);
    }

    public void notifyItemAvailable(String itemName, float itemPrice) throws RemoteException {
        System.out.println("The item " + itemName + " with your interested price $" 
                + itemPrice + " is available.");
    }
}
