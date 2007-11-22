/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package market.client;

import java.rmi.*;
import bank.*;
import java.rmi.server.UnicastRemoteObject;
/**
 *
 * @author Ricky
 */
public class ClientImpl extends UnicastRemoteObject implements ClientInterface{
    private MarketClientView clientView = null;
    
    public ClientImpl(MarketClientView cv) throws RemoteException{
            this.clientView = cv;
    }
    
    public void notifyItemSoldout(String itemName, float itemPrice) throws RemoteException {
        clientView.addMessage("Your item " + itemName + " has been sold out in $" + itemPrice);
    }

    public void notifyItemAvailable(String itemName, float itemPrice) throws RemoteException {
        clientView.addMessage("The item " + itemName + " with your interested price $" + itemPrice + " is available.");
    }
}
