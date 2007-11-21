/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bank;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Kop
 */
public interface Bank extends Remote{
    /**
     * Create a new bank account
     * @param name account name
     * @return the created account
     * @throws java.rmi.RemoteException
     */
    public BankAccount createAccount(String name) throws RemoteException;
    
    /**
     * Get the account object according to name
     * @param name account name
     * @return the account
     * @throws java.rmi.RemoteException
     */
    public BankAccount getAccount(String name) throws RemoteException;
    
    /**
     * Delete the account according to name 
     * @param name the account name
     * @return the delete action succeeds or not
     * @throws java.rmi.RemoteException
     */
    public boolean deleteAccount(BankAccount acc) throws RemoteException;
}
