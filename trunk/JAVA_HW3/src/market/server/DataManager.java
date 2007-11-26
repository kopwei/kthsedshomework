/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package market.server;

import bank.BankAccount;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;
import java.util.Vector;


/**
 *
 * @author Kop
 */
public class DataManager {
    private String userName = null;
    private char[] passWord = null;
    private Connection con = null;
    
    public DataManager(String userName, char[] passWord) {
        this.userName = userName;
        this.passWord = passWord;
    }
    
    
    public void publishConnection() {
        try {
            InetAddress add = Inet4Address.getLocalHost();
            String ip = add.getHostAddress();
            String connectionString = "jdbc:mysql://" + ip.toString() + ":3306/mysql";         
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            con = DriverManager.getConnection(connectionString, userName, new String(passWord));     
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }   
    }
    
    public void storeClientAccount(ClientAccount account) {
        try {
            PreparedStatement stmt = con.prepareStatement("INSERT INTO marketdata.clientaccounts " +
                    "(clientid, username, password, bankaccount) VALUES (?, ?, ?, ?)");
            stmt.setString(1, account.getClientID().toString());
            stmt.setString(2, account.getUserName());
            stmt.setString(3, account.getPassword().toString());
            stmt.setString(4, account.getBankAccountName());
            int count = stmt.executeUpdate();
            // TODO: need implementation here
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
    
    public void storeWish(ItemForSell wishItem) {
        // TODO: need implementation here
    }
    
    public void storeItem(ItemForSell item) {
        // TODO: need implementation here
    }
    
    public void storeBankAccount(BankAccount account) {
        // TODO: need implementation here
    }
    
    public void updateItem(ItemForSell item) {
        // TODO: need implementation here
    }
//    public void setItemState(ItemStateType type) {
//        // TODO: need implementation here
//    }
//    
    public ClientAccount getClientAccountByID(UUID clientId) {
        // TODO: need implementation here
        return null;
    }
    
    public ClientAccount getClientAccountByNameAndPassword(String name, char[] password) {
        // TODO: need implementation here
        return null;
    }
    
    public Vector<String> getAllClientName() {
        // TODO: need implementation here
        return null;
    }
    
    public ItemForSell getItemByID(UUID itemId) {
        // TODO: need implementation here
        return null;
    }
    
    public BankAccount getBankAccountByName(String accountName) {
        // TODO: need implementation here
        return null;
    }
    
    public void removeBankAccount(String accountName) {
        // TODO: need implementation here
    }
    
    public Vector<ClientAccount> getAllClientAccounts() {
        // TODO: need implementation here
        return null;
    }
    
    public Vector<ItemForSell> getAllSellingItems() {
        // TODO: need implementation here
        return null;
    }
    
    public Vector<ItemForSell> getAllWishItems() {
        // TODO: need implementation here
        return null;
    }
    
    public Vector<ItemForSell> getMatchedSellingItems(ItemForSell wishItem) {
        // TODO: need implementation here
        return null;
    }
    
    public Vector<ItemForSell> getMatchedWishItems(ItemForSell publishedItem) {
        // TODO: need implementation here
        return null;
    }
    
    public float getBankBalance(String accountName) {
        // TODO: need implementation here
        return 0.0f;
    }
    
    public void deposit(String accountName, float number) {
        // TODO: need implementation here      
    }
    
    public boolean withDraw(String accountName, float number) {
        // TODO: need implementation here
        return true;
    }
    
    public Vector<ItemForSell> getSellingItems(UUID sellerID) {
        // TODO: need implementation here
        return null;
    }

    public Vector<ItemForSell> getBoughtItems(UUID buyerID) {
        // TODO: need implementation here
        return null;
    }

    public Vector<ItemForSell> getWishItems(UUID clientID) {
        // TODO: need implementation here
        return null;
    }
}
