/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bank;

/**
 *
 * @author Kop
 */
public class BankAccount {
    String accountID = null;
    int balance = 0;
    
    /**
     * Constructor to initialize members
     * @param accountId
     * @param balance
     */
    public BankAccount(String accountId, int balance) {
        this.accountID = accountId;
        this.balance = balance;
    }
    
    /**
     * This method is used to increase the account balance
     * @param numberOfDeposit, the number of deposit, e.g. the original balance is 20, if the
     * deposit number is 30, then the final balance will be 50
     */
    public void deposit(int numberOfDeposit) {
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
    public boolean withdraw(int numberOfWithdraw) {
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
    public int getBalance() {
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
