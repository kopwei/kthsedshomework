/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package market.server;

import bank.Bank;
import bank.BankAccount;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
    private Bank bankObj = null;
    
    public DataManager(String userName, char[] passWord, Bank bankObj) {
        this.userName = userName;
        this.passWord = passWord;
        this.bankObj = bankObj;
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
            // Prepate the statement with SQL update command
            PreparedStatement stmt = con.prepareStatement("INSERT INTO marketdata.clientaccounts " +
                    "(clientid, username, password, bankaccount) VALUES (?, ?, ?, ?)");
            stmt.setString(1, account.getClientID().toString());
            stmt.setString(2, account.getUserName());
            stmt.setString(3, new String(account.getPassword()));
            stmt.setString(4, account.getBankAccountName());
            // Execute and update the data
            int count = stmt.executeUpdate();
            stmt.close();
            // TODO: need implementation here
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
    
    public void storeWish(ItemForSell wishItem) {
        try {
            // Cast the item into item implementation object
            ItemForSellImpl wishItemImpl = (ItemForSellImpl)wishItem;
            if (null == wishItemImpl) {
                return;
            }
            // Prepate the statement with SQL update command
            PreparedStatement stmt = con.prepareStatement("INSERT INTO marketdata.wishes (name," +
                    "price, clientid) VALES(?, ?, ?)");
            stmt.setString(1, wishItem.getName());
            stmt.setFloat(2, wishItem.getPrice());
            stmt.setString(3, wishItemImpl.getWisherClientID().toString());
            // Execute and update the data
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        } catch(RemoteException ex) {
            System.err.println(ex.getMessage());
        }
    }
    
    public void storeItem(ItemForSell item) {
        try {
            // Cast the item into item implementation object
            ItemForSellImpl itemImpl = (ItemForSellImpl)item;
            if (null == itemImpl) {
                return;
            }
            // Prepate the statement with SQL update command
            PreparedStatement stmt = con.prepareStatement("INSERT INTO marketdata.items (itemid, name," +
                    "price, state, sellerid, buyerid, itemtype) VALUES(?, ?, ?, ?, ?, ?, ?)");
            stmt.setString(1, item.getItemID().toString());
            stmt.setString(2, item.getName());
            stmt.setString(3, Float.toString(itemImpl.getPrice()));
            // Prepare for the state
            stmt.setString(4, item.getState().toString());
            stmt.setString(5, itemImpl.getSellerClientID().toString());
            stmt.setString(6, null);
            stmt.setString(7, itemImpl.getType().toString());
            
            // Execute and update the data
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        } catch(RemoteException ex) {
            System.err.println(ex.getMessage());
        }
    }
    
    public void storeBankAccount(BankAccount account) {
        try {
            // Prepate the statement with SQL update command
            PreparedStatement stmt = con.prepareStatement("INSERT INTO marketdata.bankaccounts (" +
                    "name, balance) VALUEs(?, ?)");
            // Execute and update the data
            stmt.setString(1, account.getAccountName());
            float accountBalance = bankObj.getBalance(account.getAccountName());
            stmt.setFloat(2, accountBalance);
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        } catch(RemoteException ex) {
            System.err.println(ex.getMessage());
        }
    }
    
    public void updateItem(ItemForSell item) {
        try {
            // Cast the item into item implementation object
            ItemForSellImpl itemImpl = (ItemForSellImpl)item;
            if (null == itemImpl) {
                return;
            }
            // Prepate the statement with SQL update command
            PreparedStatement stmt = con.prepareStatement("UPDATE marketdata.items SET " +
                    "state = ?, buyerid = ? WHERE itemid = ?");         
            // Prepare for the state
            stmt.setString(1, item.getState().toString());
            if (null != itemImpl.getBuyerClientID()) {
                stmt.setString(2, itemImpl.getBuyerClientID().toString());
            }
            stmt.setString(3, item.getItemID().toString());
            
            // Execute and update the data
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        } catch(RemoteException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public ClientAccount getClientAccountByID(UUID clientId) {
        try {
            // Prepate the statement with SQL update command
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM marketdata.clientaccounts" +
                    " WHWERE clientid = ?");
            stmt.setString(1, clientId.toString());
            ResultSet rs = stmt.executeQuery();
            // Create the client account object and return it
            if (rs.next()) {
                String clientName = rs.getString("username");
                char[] password = rs.getString("password").toCharArray();
                String bankAccountName = rs.getString("bankaccount");
                stmt.close();
                return new ClientAccount(clientName, password, bankAccountName, clientId);
            }
            stmt.close();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return null;
    }
    
    public ClientAccount getClientAccountByNameAndPassword(String name, char[] password) {
        try {
            // Prepate the statement with SQL update command
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM marketdata.clientaccounts" +
                    " WHWERE (username = ? AND password = ?)");
            stmt.setString(1, name);
            stmt.setString(2, new String(password));
            ResultSet rs = stmt.executeQuery();
            stmt.close();
            // Create the client account object and return it
            if (rs.next()) {
                UUID clientID = UUID.fromString(rs.getString("clientid"));
                String clientName = rs.getString("username");
                char[] passwordArray = rs.getString("password").toCharArray();
                String bankAccountName = rs.getString("bankaccount");               
                return new ClientAccount(clientName, passwordArray, bankAccountName, clientID);
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return null;
    }
    
    public Vector<String> getAllClientName() {
        try {
            // Create the query statement and query the database
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM marketdata.clientaccounts");
            stmt.close();
            // Return the client name collection with vector
            Vector<String> nameVector = new Vector<String>();
            while (rs.next()) {
                nameVector.add(rs.getString("username"));
            }
            return nameVector;
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return null;
    }
    
    public ItemForSell getItemByID(UUID itemId) {
        try {
            // Prepate the statement with SQL update command
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM marketdata.items" + " WHWERE itemid = ?");
            stmt.setString(1, itemId.toString());
            ResultSet rs = stmt.executeQuery();
            stmt.close();
            if (rs.next()) {
                UUID sellerId = UUID.fromString(rs.getString("sellerid"));
                UUID buyerId = UUID.fromString(rs.getString("buyerid"));
                String itemName = rs.getString("name");
                ItemType type = ItemType.valueOf(rs.getString("itemtype"));
                float price = rs.getFloat("price");
                ItemStateType state = ItemStateType.valueOf(rs.getString("state"));              
                 // Create the item object and return it
                return new ItemForSellImpl(itemName, price, type, sellerId, buyerId, itemId, state);
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        } catch(RemoteException ex) {
            System.err.println(ex.getMessage());
        }
        return null;
    }
    
    public BankAccount getBankAccountByName(String accountName) {
        try {
             // Prepate the statement with SQL update command
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM marketdata.bankaccounts " + 
                    "WHERE name = ?");
            stmt.setString(1, accountName);
            ResultSet rs = stmt.executeQuery();
            stmt.close();
            if (rs.next()) {
                float balance = rs.getFloat("balance");              
                // Create the bank account object and return it
                return new BankAccount(accountName, balance);
            }            
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return null;
    }
    
    public void removeBankAccount(String accountName) {
        try {
            // Prepate the statement with SQL update command
            PreparedStatement stmt = con.prepareStatement("DELETE FROM marketdata.bankaccounts WHERE " + 
                    "name = ?");
            stmt.setString(1, accountName);
            // Execute the delete action
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
    
    public boolean isClientAccountExist(String accountName) {
        // TODO: need implementation here
        return true;
    }
    
    public Vector<ItemForSell> getSellingItemsByType(ItemType type) {
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
