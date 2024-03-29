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
    private String _name = null;
    private float balance = 0;
        
    public BankAccountImpl(String name) throws RemoteException {
        super();
        this._name = name;
    }

    /**
     * This method is used to increase the account balance
     * @param numberOfDeposit, the number of deposit, e.g. the original balance is 20, if the
     * deposit number is 30, then the final balance will be 50
     */
    public synchronized void deposit(float numberOfDeposit) throws RemoteException{
         if (numberOfDeposit < 0) {
             return;
            // throw new Rejected("Rejected: BankAccount " + name + ": Illegal value: " + value + " for deposit.");
        }
        balance += numberOfDeposit;
        System.out.println("Transaction: Account " + _name + ": deposit: $" + numberOfDeposit + ", balance: $" + balance);
    }
    
    /**
     * This method is used to decrease the account balance
     * @param numberOfWithdraw, the number of decreasing, e.g. the original balance is 30, if the
     * decrease number is 20, then the final balance will be 10. The decreasing number can not be
     * greater than the existing balance number.
     * @return return true indicate that the decreasing action succeeds, otherwise the decreasing
     * fails
     */
    public synchronized boolean withdraw(float numberOfWithdraw) throws RemoteException{
        if (numberOfWithdraw < 0) {
            return false;
            // throw new Rejected("Rejected: BankAccount " + name + ": Illegal vaule: " + value + " for withdraw.");
        }
        if ((balance - numberOfWithdraw) < 0) {
            return false;
            // throw new Rejected("Rejected: BankAccount " + name + ": Negative balance: $" + (_balance - value) + "on withdraw: $" + value);
        }
        balance -= numberOfWithdraw;
        System.out.println("Transaction: Account " + _name + ": withdraw: $" + numberOfWithdraw + ", balance: $" + balance);
        return true;
    }
    
    /**
     * This method is used to get the current balance of the bank account
     * @return the balance value
     */
    public synchronized float getBalance() throws RemoteException{
        return balance;
    }
    
//   /**
//    * This method is used to get the account id of the bank account
//    * @return the account id
//    */
//    public String get_name() {
//        return _name;
//    }

}
