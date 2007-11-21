/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bank;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author Kop
 */
public class BankAccountImpl extends UnicastRemoteObject implements BankAccount{
    String accountID = null;
    float balance = 0f;
    public BankAccountImpl(String accountId, float balance) throws RemoteException {
        this.accountID = accountId;
        this.balance = balance;
    }
    public BankAccountImpl() throws RemoteException {
        
    }
    
        

    /**
     * This method is used to increase the account balance
     * @param numberOfDeposit, the number of deposit, e.g. the original balance is 20, if the
     * deposit number is 30, then the final balance will be 50
     */
    public synchronized void deposit(float numberOfDeposit) {
        balance += numberOfDeposit;
    }
    
    /**
     * This method is used to decrease the account balance
     * @param numberOfWithdraw, the number of decreasing, e.g. the original balance is 30, if the
     * decrease number is 20, then the final balance will be 10. The decreasing number can not be
     * greater than the existing balance number.
     * @return return true indicate that the decreasing action succeeds, otherwise the decreasing
     * fails
     */
    public synchronized boolean withdraw(float numberOfWithdraw) {
        if (balance < numberOfWithdraw) {
            return false;
        }
        else {
            balance -= numberOfWithdraw;
            return true;
        }
    }
    
    /**
     * This method is used to get the current balance of the bank account
     * @return the balance value
     */
    public synchronized float getBalance() {
        return balance;
    }
    
   /**
    * This method is used to get the account id of the bank account
    * @return the account id
    */
    public String getAccountID() {
        return accountID;
    }

}
