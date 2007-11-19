/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package market.server;

import bank.BankAccount;
import java.util.UUID;

/**
 *
 * @author Kop
 */
public class ClientAccount {
    private String userName = null;
    private char[] passWord = null;
    private BankAccount bankAccount = null;
    private UUID clientAccountID = null;
    /**
     * Constructor which used to initialize all members
     * @param userName the user name of the client account
     * @param passWord
     */
    public ClientAccount(String userName, char[] passWord) {
        this.userName = userName;
        this.passWord = passWord;
        clientAccountID = UUID.randomUUID();
        // TODO: need the bank account info
    }
    
    /**
     * 
     * @return
     */
    public String getUserName() {
        return userName;
    }
    
    /**
     * 
     * @return
     */
    public BankAccount getBankAccount() {
        return bankAccount;
    }
    
    /**
     * 
     * @return
     */
    public UUID getClientID() {
        return clientAccountID;
    }
    
}
