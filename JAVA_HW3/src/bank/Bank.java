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
    public boolean createAccount(String name) throws RemoteException;
        
    /**
     * Delete the account according to name 
     * @param name the account name
     * @return the delete action succeeds or not
     * @throws java.rmi.RemoteException
     */
    public void deleteAccount(String name) throws RemoteException;
    
    /**
     * Get the balance of the account according to the account name
     * @param accountName
     * @return
     * @throws java.rmi.RemoteException
     */
    public float getBalance(String accountName) throws RemoteException;
    
    /**
     * This method is used to increase the account balance
     * @param accountName
     * @param depositNumber
     * @throws java.rmi.RemoteException
     */
    public void deposit(String accountName, float depositNumber) throws RemoteException;
    
    /**
     * This method is used to decrease the account balance
     * @param accountName
     * @param withDrawNumber
     * @return indicates if the action suceeds
     * @throws java.rmi.RemoteException
     */
    public boolean withdraw(String accountName, float withDrawNumber) throws RemoteException;
}
