/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bank;



/**
 *
 * @author Kop
 */
public class BankAccount{
    private String accountName = null;
    private float balance = 0;
        
    public BankAccount(String name, float balance){
        this.accountName = name;
        this.balance = balance;
    }
    
    public String getAccountName() {
        return accountName;
    }
    
    public float getBalance() {
        return balance;
    }    
}
