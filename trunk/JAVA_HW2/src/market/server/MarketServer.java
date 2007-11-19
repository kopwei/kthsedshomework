/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package market.server;

import java.util.Hashtable;
import java.util.UUID;

/**
 *
 * @author Kop
 */
public class MarketServer {
    private Hashtable<String, ClientAccount> clientAccountTable = new Hashtable<String, ClientAccount>();
    private Hashtable<UUID, ItemForSell> itemTable = new Hashtable<UUID, ItemForSell>();
    
    
    public void registerAccount() {
        
    }
    
    public void publishItem(String itemName, int price, String type) {
        
    }
    
    public boolean buyItem(UUID itemID, ClientAccount buyerAccount) {
        // TODO: need implementation here
        return true;
    }
    
    
    
}
