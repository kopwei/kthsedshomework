/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bank;

import java.rmi.Remote;

/**
 *
 * @author Kop
 */
public interface BankAccount extends Remote{ 
    
    /**
     * This method is used to increase the account balance
     * @param numberOfDeposit, the number of deposit, e.g. the original balance is 20, if the
     * deposit number is 30, then the final balance will be 50
     */
    public void deposit(float numberOfDeposit);
    
    /**
     * This method is used to decrease the account balance
     * @param numberOfWithdraw, the number of decreasing, e.g. the original balance is 30, if the
     * decrease number is 20, then the final balance will be 10. The decreasing number can not be
     * greater than the existing balance number.
     * @return return true indicate that the decreasing action succeeds, otherwise the decreasing
     * fails
     */
    public boolean withdraw(float numberOfWithdraw);
    
    /**
     * This method is used to get the current balance of the bank account
     * @return the balance value
     */
    public float getBalance();
    
   /**
    * This method is used to get the account id of the bank account
    * @return the account id
    */
    public String getAccountID();
}
