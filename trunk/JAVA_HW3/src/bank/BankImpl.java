/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bank;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
//import java.util.Hashtable;
import market.server.DataManager;

/**
 *
 * @author Kop
 */
public class BankImpl extends UnicastRemoteObject implements Bank{
    //private Hashtable clientAccountsTable = new Hashtable();
    private DataManager dataManager = null;
    /**
     * Constructor for initialize bank name
     * @param bankName the name of the bank
     * @throws java.rmi.RemoteException
     */
   public BankImpl() throws RemoteException {
        super();
    }
   
   public BankImpl(DataManager mgr) throws RemoteException {
       super();
       this.dataManager = mgr;
   }
    /**
     * Create a new bank account
     * @param name account name
     * @return the created account
     * @throws java.rmi.RemoteException
     */
   public synchronized boolean createAccount(String name) throws RemoteException {
        BankAccount account = dataManager.getBankAccountByName(name);
        if (account != null) {
            System.out.println("Account [" + name + "] exists!");
            //throw new Rejected("Rejected: BankAccount for: " + name + " already exists: " + account);
        }
        else {
            account = new BankAccount(name);
            dataManager.storeBankAccount(account);
            System.out.println("Account is created for " + name);
        }
        return true;
    }

    /**
     * Get the account object according to name
     * @param name account name
     * @return the account
     * @throws java.rmi.RemoteException
     */
    public BankAccount getAccount(String name) throws RemoteException {
        BankAccount account = dataManager.getBankAccountByName(name);
        if (account == null) {
            System.out.println("Account [" + name + "] does not exist!");
            //throw new Rejected("Rejected: BankAccount for: " + name + " does not exist!");
            return account;
        }
        else {
            return account;
        }
    }

    public synchronized void deleteAccount(String name) throws RemoteException {
        dataManager.removeBankAccount(name);
    }

    public synchronized float getBalance(String accountName) throws RemoteException {
        return dataManager.getBankBalance(accountName);      
    }

    public synchronized void deposit(String accountName, float depositNumber) throws RemoteException {
        dataManager.deposit(accountName, depositNumber);
    }

    public synchronized boolean withdraw(String accountName, float withDrawNumber) throws RemoteException {
        return dataManager.withDraw(accountName, withDrawNumber);
    }
}
