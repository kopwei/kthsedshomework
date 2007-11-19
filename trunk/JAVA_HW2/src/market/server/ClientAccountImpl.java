/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package market.server;

import bank.BankAccount;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.UUID;

/**
 *
 * @author Kop
 */
public class ClientAccountImpl extends UnicastRemoteObject implements ClientAccount{
    private String userName = null;
    private char[] passWord = null;
    private BankAccount bankAccount = null;
    private UUID clientAccountID = null;
    
    /**
     * Constructor which used to initialize all members
     * @param userName the user name of the client account
     * @param passWord
     */
    public ClientAccountImpl(String userName, char[] passWord) throws RemoteException{
        this.userName = userName;
        this.passWord = passWord;
        clientAccountID = UUID.randomUUID();
        // TODO: need the bank account info
    }
    
    /**
     * 
     * @return
     */
    public String getUserName() throws RemoteException {
        return userName;
    }
    
    /**
     * 
     * @return
     */
    public BankAccount getBankAccount() throws RemoteException {
        return bankAccount;
    }
    
    /**
     * 
     * @return
     */
    public UUID getClientID() throws RemoteException {
        return clientAccountID;
    }
}
