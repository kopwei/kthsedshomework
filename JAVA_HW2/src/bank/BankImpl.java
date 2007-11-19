/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bank;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Hashtable;
import java.util.UUID;

/**
 *
 * @author Kop
 */
public class BankImpl extends UnicastRemoteObject implements Bank{
    private String bankName = null;
    private Hashtable <UUID, BankAccount> accounts = new Hashtable<UUID, BankAccount>();
    
    public BankImpl() throws RemoteException {
        
    }
    
    /**
     * Constructor for initialize bank name
     * @param bankName the name of the bank
     * @throws java.rmi.RemoteException
     */
    public BankImpl(String bankName) throws RemoteException {
        
    }

    /**
     * Create a new bank account
     * @param name account name
     * @return the created account
     * @throws java.rmi.RemoteException
     */
    public BankAccount createAccount(String name) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Get the account object according to name
     * @param name account name
     * @return the account
     * @throws java.rmi.RemoteException
     */
    public BankAccount getAccount(String name) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Delete the account according to name 
     * @param name the account name
     * @return the delete action succeeds or not
     * @throws java.rmi.RemoteException
     */
    public boolean deleteAccount(String name) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
