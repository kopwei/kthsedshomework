/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package market.client;

import java.rmi.*;
import bank.*;
/**
 *
 * @author Ricky
 */
public class ClientImpl implements ClientInterface{
    private MarketClientView clientView = null;
    
    public ClientImpl(MarketClientView cv) {
            this.clientView = cv;
    }
    
    public void notifyItemSoldout(String itemName, float itemPrice) throws RemoteException {
        clientView.addMessage("Your item " + itemName + " has been sold out in $" + itemPrice);
    }

    public void notifyItemAvailable(String itemName, float itemPrice) throws RemoteException {
        clientView.addMessage("The item " + itemName + " with your interested price $" + itemPrice + " is available.");
    }
}
