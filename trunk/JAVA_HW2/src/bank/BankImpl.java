/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bank;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Hashtable;

/**
 *
 * @author Kop
 */
public class BankImpl extends UnicastRemoteObject implements Bank{
private Hashtable clientAccountsTable = new Hashtable();

    /**
     * Constructor for initialize bank name
     * @param bankName the name of the bank
     * @throws java.rmi.RemoteException
     */
   public BankImpl() throws RemoteException {
        super();
    }
    /**
     * Create a new bank account
     * @param name account name
     * @return the created account
     * @throws java.rmi.RemoteException
     */
   public synchronized BankAccount createAccount(String name) throws RemoteException {
        BankAccountImpl account = (BankAccountImpl) clientAccountsTable.get(name);
        if (account != null) {
            System.out.println("Account [" + name + "] exists!");
            //throw new Rejected("Rejected: BankAccount for: " + name + " already exists: " + account);
        }
        account = new BankAccountImpl(name);
        clientAccountsTable.put(name, account);
        System.out.println("Account is created for " + name);
        return (BankAccount)account;
    }

    /**
     * Get the account object according to name
     * @param name account name
     * @return the account
     * @throws java.rmi.RemoteException
     */
    public BankAccount getAccount(String name) throws RemoteException {
        BankAccountImpl account = (BankAccountImpl) clientAccountsTable.get(name);
        if (account == null) {
            System.out.println("Account [" + name + "] does not exist!");
            //throw new Rejected("Rejected: BankAccount for: " + name + " does not exist!");
            return (BankAccount)account;
        }
        else {
            return (BankAccount)account;
        }
    }

    public boolean deleteAccount(BankAccount acc) throws RemoteException {
        if ((acc != null) && (clientAccountsTable.contains(acc))) {
            java.util.Enumeration en = clientAccountsTable.keys();
            while(en.hasMoreElements()) {
                String name = (String)en.nextElement();
                BankAccount ba = (BankAccount) clientAccountsTable.get(name);
                if (ba.equals(acc)) {
                    clientAccountsTable.remove(name);
                    System.out.println("Account for: " + name + " is deleted");
                    return true;
                }
            }
        }
        return false;
    }
}
