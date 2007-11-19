/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package market.server;

import bank.BankAccount;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.UUID;



/**
 *
 * @author Kop
 */
public interface ClientAccount extends Remote{
    /**
     * Get the user name of the account
     * @return
     */
    public String getUserName() throws RemoteException;
    
    /**
     * Get the bank account of the client
     * @return
     */
    public BankAccount getBankAccount() throws RemoteException;
    
    /**
     * Get the UUID of the client
     * @return
     */
    public UUID getClientID() throws RemoteException;
}
