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
        
    public BankAccount(String name){
        this.accountName = name;
    }
    
    public String getAccountName() {
        return accountName;
    }

//    /**
//     * This method is used to increase the account balance
//     * @param numberOfDeposit, the number of deposit, e.g. the original balance is 20, if the
//     * deposit number is 30, then the final balance will be 50
//     */
//    public synchronized void deposit(float numberOfDeposit){
//         if (numberOfDeposit < 0) {
//             return;
//            // throw new Rejected("Rejected: BankAccount " + name + ": Illegal value: " + value + " for deposit.");
//        }
//        balance += numberOfDeposit;
//        System.out.println("Transaction: Account " + _name + ": deposit: $" + numberOfDeposit + ", balance: $" + balance);
//    }
//    
//    /**
//     * This method is used to decrease the account balance
//     * @param numberOfWithdraw, the number of decreasing, e.g. the original balance is 30, if the
//     * decrease number is 20, then the final balance will be 10. The decreasing number can not be
//     * greater than the existing balance number.
//     * @return return true indicate that the decreasing action succeeds, otherwise the decreasing
//     * fails
//     */
//    public synchronized boolean withdraw(float numberOfWithdraw){
//        if (numberOfWithdraw < 0) {
//            return false;
//            // throw new Rejected("Rejected: BankAccount " + name + ": Illegal vaule: " + value + " for withdraw.");
//        }
//        if ((balance - numberOfWithdraw) < 0) {
//            return false;
//            // throw new Rejected("Rejected: BankAccount " + name + ": Negative balance: $" + (_balance - value) + "on withdraw: $" + value);
//        }
//        balance -= numberOfWithdraw;
//        System.out.println("Transaction: Account " + _name + ": withdraw: $" + numberOfWithdraw + ", balance: $" + balance);
//        return true;
//    }
//    
//    /**
//     * This method is used to get the current balance of the bank account
//     * @return the balance value
//     */
//    public synchronized float getBalance(){
//        return balance;
//    }
    
}
