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
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Kop
 */
public class DataManager {
    private String userName = null;
    private char[] passWord = null;
    private Connection con = null;
    private Bank bankObj = null;
    
    /**
     * 
     * @param userName
     * @param passWord
     * @param bankObj
     */
    public DataManager(String userName, char[] passWord, Bank bankObj) {
        this.userName = userName;
        this.passWord = passWord;
        this.bankObj = bankObj;
    }
    
    /**
     * 
     */
    public boolean publishConnection() {
        try {
            InetAddress add = Inet4Address.getLocalHost();
            String ip = add.getHostAddress();
            String connectionString = "jdbc:mysql://" + ip.toString() + ":3306/mysql";         
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            con = DriverManager.getConnection(connectionString, userName, new String(passWord));  
            return true;
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            return false;
        }   
    }
    
    /**
     * 
     * @param account
     */
    public void storeClientAccount(ClientAccount account) {
        try {
            // Prepate the statement with SQL update command
            PreparedStatement stmt = con.prepareStatement("INSERT INTO marketdata.clientaccounts " +
                    "(clientid, username, password, bankaccount) VALUES (?, ?, ?, ?)");
            stmt.setString(1, account.getClientID().toString());
            stmt.setString(2, account.getUserName());
            char[] password = account.getPassword();
            String passwordStr = new String(password);
            MessageDigest md = MessageDigest.getInstance("SHA");
            String digestedString = new String(md.digest(passwordStr.getBytes()));
            
            stmt.setString(3, digestedString);
            stmt.setString(4, account.getBankAccountName());
            // Execute and update the data
            int count = stmt.executeUpdate();
            stmt.close();
            // TODO: need implementation here
        } catch (NoSuchAlgorithmException ex) {
            System.err.println(ex.getMessage());
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
    
    /**
     * 
     * @param wishItem
     */
    public void storeWish(ItemForSell wishItem) {
        try {
            // Cast the item into item implementation object
            //ItemForSellImpl wishItemImpl = (ItemForSellImpl)wishItem;
            if (null == wishItem) {
                return;
            }
            // Prepate the statement with SQL update command
            PreparedStatement stmt = con.prepareStatement("INSERT INTO marketdata.wishes (name," +
                    "price, clientid) VALUES(?, ?, ?)");
            stmt.setString(1, wishItem.getName());
            stmt.setFloat(2, wishItem.getPrice());
            stmt.setString(3, wishItem.getWisherClientID().toString());
            // Execute and update the data
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        } catch(RemoteException ex) {
            System.err.println(ex.getMessage());
        }
    }
    
    /**
     * 
     * @param item
     */
    public void storeItem(ItemForSell item) {
        try {
            // Cast the item into item implementation object
            //ItemForSellImpl itemImpl = (ItemForSellImpl)item;
            if (null == item) {
                return;
            }
            // Prepate the statement with SQL update command
            PreparedStatement stmt = con.prepareStatement("INSERT INTO marketdata.items (itemid, name," +
                    "price, state, sellerid, buyerid, itemtype) VALUES(?, ?, ?, ?, ?, ?, ?)");
            stmt.setString(1, item.getItemID().toString());
            stmt.setString(2, item.getName());
            stmt.setString(3, Float.toString(item.getPrice()));
            // Prepare for the state
            stmt.setString(4, item.getState().toString());
            stmt.setString(5, item.getSellerClientID().toString());
            stmt.setString(6, null);
            stmt.setString(7, item.getType().toString());
            
            // Execute and update the data
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        } catch(RemoteException ex) {
            System.err.println(ex.getMessage());
        }
    }
    
    /**
     * 
     * @param account
     */
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
    
    /**
     * 
     * @param item
     */
    public void updateItem(ItemForSell item) {
        try {
            // Cast the item into item implementation object
            //ItemForSellImpl itemImpl = (ItemForSellImpl)item;
            if (null == item) {
                return;
            }
            // Prepate the statement with SQL update command
            PreparedStatement stmt = con.prepareStatement("UPDATE marketdata.items SET " +
                    "state = ?, buyerid = ? WHERE itemid = ?");         
            // Prepare for the state
            stmt.setString(1, item.getState().toString());
            if (null != item.getBuyerClientID()) {
                stmt.setString(2, item.getBuyerClientID().toString());
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

    /**
     * 
     * @param clientId
     * @return
     */
    public ClientAccount getClientAccountByID(UUID clientId) {
        try {
            // Prepate the statement with SQL update command
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM marketdata.clientaccounts" +
                    " WHERE clientid = ?");
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
    
    /**
     * 
     * @param name
     * @param password
     * @return
     */
    public ClientAccount getClientAccountByNameAndPassword(String name, char[] password) {
        try {
            // Prepate the statement with SQL update command
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM marketdata.clientaccounts" +
                    " WHERE (username = ? AND password = ?)");
            stmt.setString(1, name);
            String passwordStr = new String(password);
            MessageDigest md = MessageDigest.getInstance("SHA");
            String digestedString = new String(md.digest(passwordStr.getBytes()));
            stmt.setString(2, digestedString);
            ResultSet rs = stmt.executeQuery();
            
            // Create the client account object and return it
            if (rs.next()) {
                UUID clientID = UUID.fromString(rs.getString("clientid"));
                String clientName = rs.getString("username");
                char[] passwordArray = rs.getString("password").toCharArray();
                String bankAccountName = rs.getString("bankaccount");               
                stmt.close();
                return new ClientAccount(clientName, passwordArray, bankAccountName, clientID);
            }
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(DataManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return null;
    }
    
    /**
     * 
     * @return
     */
    public Vector<String> getAllClientName() {
        Vector<String> nameVector = new Vector<String>();
        try {
            // Create the query statement and query the database
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM marketdata.clientaccounts");
            
            // Return the client name collection with vector            
            while (rs.next()) {
                nameVector.add(rs.getString("username"));
            }
            stmt.close();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return nameVector;
    }
    
    /**
     * 
     * @param itemId
     * @return
     */
    public ItemForSell getItemByID(UUID itemId) {
        try {
            // Prepate the statement with SQL update command
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM marketdata.items" + " WHERE itemid = ?");
            stmt.setString(1, itemId.toString());
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                UUID sellerId = UUID.fromString(rs.getString("sellerid"));
                UUID buyerId = UUID.fromString(rs.getString("buyerid"));
                String itemName = rs.getString("name");
                ItemType type = ItemType.valueOf(rs.getString("itemtype"));
                float price = rs.getFloat("price");
                ItemStateType state = ItemStateType.valueOf(rs.getString("state"));              
                 // Create the item object and return it
                stmt.close();
                return new ItemForSellImpl(itemName, price, type, sellerId, buyerId, itemId, state);
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        } catch(RemoteException ex) {
            System.err.println(ex.getMessage());
        }
        return null;
    }
    
    /**
     * 
     * @param accountName
     * @return
     */
    public BankAccount getBankAccountByName(String accountName) {
        try {
             // Prepate the statement with SQL update command
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM marketdata.bankaccounts " + 
                    "WHERE name = ?");
            stmt.setString(1, accountName);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                float balance = rs.getFloat("balance");              
                // Create the bank account object and return it
                stmt.close();
                return new BankAccount(accountName, balance);
            }            
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return null;
    }
    
    /**
     * 
     * @param accountName
     */
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
    
    /**
     * 
     * @param accountName
     * @return
     */
    public boolean isClientAccountExist(String accountName) {
        try {
            // Prepate the statement with SQL update command
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM marketdata.clientaccounts " +
                    "WHERE username = ?");
            stmt.setString(1, accountName);
            ResultSet rs = stmt.executeQuery();
            
            return rs.next();     
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return true;
    }
    
    /**
     * 
     * @param type
     * @return
     */
    public Vector<ItemForSell> getSellingItemsByType(ItemType type) {
        Vector<ItemForSell> items = new Vector<ItemForSell>();
        try {
            PreparedStatement stmt;
            // Prepate the statement with SQL update command
            if (type != ItemType.Unknown) {
                stmt = con.prepareStatement("SELECT * FROM marketdata.items WHERE (" +
                        "itemtype = ? AND state = ?)");
                stmt.setString(1, type.toString());
                stmt.setString(2, ItemStateType.OnSell.toString());
            } else {
                stmt = con.prepareStatement("SELECT * FROM marketdata.items WHERE (" +
                        "state = ?)");
                stmt.setString(1, ItemStateType.OnSell.toString());
            }
            ResultSet rs = stmt.executeQuery();
            //stmt.close();         
            while (rs.next()) {
                UUID itemId = UUID.fromString(rs.getString("itemid"));
                UUID sellerId = UUID.fromString(rs.getString("sellerid"));
                String itemName = rs.getString("name");
                float price = rs.getFloat("price");
                ItemStateType state = ItemStateType.valueOf(rs.getString("state"));              
                 // Create the item object and return it
                items.addElement(new ItemForSellImpl(itemName, price, type, sellerId, null, itemId, state));
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        } catch(RemoteException ex) {
            System.err.println(ex.getMessage());
        }
        return items;
    }
    
    /**
     * 
     * @param wishItem
     * @return
     */
    public Vector<ItemForSell> getMatchedSellingItems(ItemForSell wishItem) {
        Vector<ItemForSell> items = new Vector<ItemForSell>();
        try {
            // Prepate the statement with SQL update command
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM marketdata.items WHERE (" +
                    "name = ? AND price <= ? AND state = ?)");   
            stmt.setString(1, wishItem.getName());
            stmt.setFloat(2, wishItem.getPrice());
            stmt.setString(3, ItemStateType.OnSell.toString());
            ResultSet rs = stmt.executeQuery();
           // stmt.close();
            while (rs.next()) {
                UUID itemId = UUID.fromString(rs.getString("itemid"));
                UUID sellerId = UUID.fromString(rs.getString("sellerid"));
                UUID buyerId = UUID.fromString(rs.getString("buyerid"));
                ItemType type = ItemType.valueOf(rs.getString("itemtype"));
                String itemName = rs.getString("name");
                float price = rs.getFloat("price");
                ItemStateType state = ItemStateType.valueOf(rs.getString("state"));              
                 // Create the item object and return it
                items.addElement(new ItemForSellImpl(itemName, price, type, sellerId, buyerId, itemId, state));
            }
            
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        } catch(RemoteException ex) {
            System.err.println(ex.getMessage());
        }
        return items;
    }
    
    /**
     * 
     * @param publishedItem
     * @return
     */
    public Vector<ItemForSell> getMatchedWishItems(ItemForSell publishedItem) {
        Vector<ItemForSell> items = new Vector<ItemForSell>();
        try {
            // Prepate the statement with SQL update command
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM marketdata.wishes WHERE (" +
                    "name = ? AND price > ?)");
            stmt.setString(1, publishedItem.getName());
            stmt.setFloat(2, publishedItem.getPrice());
            ResultSet rs = stmt.executeQuery();
            //stmt.close();
            while (rs.next()) {
                UUID wisherID = UUID.fromString(rs.getString("clientid"));
                float price = rs.getFloat("price");
                 // Create the item object and return it
                items.addElement(new ItemForSellImpl(publishedItem.getName(), price, wisherID));
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        } catch(RemoteException ex) {
            System.err.println(ex.getMessage());
        }
        
        return items;
    }
    
    /**
     * 
     * @param accountName
     * @return
     */
    public float getBankBalance(String accountName) {
        try {
            // Prepate the statement with SQL update command
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM marketdata.bankaccounts " +
                    "WHERE name = ?");
            stmt.setString(1, accountName);
            ResultSet rs = stmt.executeQuery();
            //stmt.close();
            // Get the balance and return it
            if (rs.next()) {
                float balance = rs.getFloat("balance");
                return balance;
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return 0.0f;
    }
    
    /**
     * 
     * @param accountName
     * @param number
     */
    public void deposit(String accountName, float number) {
        if (number < 0) {
            return;
        }
        try {
            // Prepate the statement with SQL update command
            PreparedStatement stmt = con.prepareStatement("UPDATE marketdata.bankaccounts " +
                    "SET balance = balance + ? WHERE name = ?");
            stmt.setFloat(1, number);
            stmt.setString(2, accountName);
            // Execute the update
            stmt.executeUpdate();
            //stmt.close();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
    
    /**
     * 
     * @param accountName
     * @param number
     * @return
     */
    public boolean withDraw(String accountName, float number) {
        // Check if the balance is ready for withdraw
        float balance = getBankBalance(accountName);
        if (balance < number) {
            return false;
        }
        else {
            try {
                // Prepate the statement with SQL update command
                PreparedStatement stmt = con.prepareStatement("UPDATE marketdata.bankaccounts " +
                        "SET banlance = banlance - ? WHERE name = ?");
                stmt.setFloat(1, number);
                stmt.setString(2, accountName);
                // Execute the update
                stmt.executeUpdate();
                //stmt.close();
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
            }
        }
        return true;
    }
    
    /**
     * 
     * @param sellerID
     * @return
     */
    public Vector<ItemForSell> getSellingItems(UUID sellerID) {
        return getClientItemByState(sellerID, ItemStateType.OnSell, true);
    }

    /**
     * 
     * @param buyerID
     * @return
     */
    public Vector<ItemForSell> getBoughtItems(UUID buyerID) {
        return getClientItemByState(buyerID, ItemStateType.Sold, false);
    }
    
    /**
     * 
     * @param sellerID
     * @return
     */
    public Vector<ItemForSell> getSoldItems(UUID sellerID) {
        return getClientItemByState(sellerID, ItemStateType.Sold, true);
    }
    

    private Vector<ItemForSell> getClientItemByState(UUID clientID, ItemStateType state, boolean isSelling) {
        Vector<ItemForSell> items = new Vector<ItemForSell>();
        PreparedStatement stmt;
        try {
            // Prepate the statement with SQL update command
            if (isSelling) {
                stmt = con.prepareStatement("SELECT * FROM marketdata.items WHERE (" + 
                        "sellerid = ? AND state = ?)");
            }
            else {
                stmt = con.prepareStatement("SELECT * FROM marketdata.items WHERE (" + 
                        "buyerid = ? AND state = ?)");
            }
            stmt.setString(1, clientID.toString());
            stmt.setString(2, state.toString());
            ResultSet rs = stmt.executeQuery();
            //stmt.close();
            while (rs.next()) {
                UUID itemId = UUID.fromString(rs.getString("itemid"));
                UUID sellerID = UUID.fromString(rs.getString("sellerid"));
                ItemType type = ItemType.valueOf(rs.getString("itemtype"));
                String itemName = rs.getString("name");
                float price = rs.getFloat("price");
                // Create the item object and return it
                if (state.equals(ItemStateType.OnSell)) {
                    items.addElement(new ItemForSellImpl(itemName, price, type, sellerID, null, itemId, state));
                }
                else {
                    UUID buyerID = UUID.fromString(rs.getString("buyerid"));
                    items.addElement(new ItemForSellImpl(itemName, price, type, clientID, buyerID, itemId, state));
                }
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        } catch (RemoteException ex) {
            System.err.println(ex.getMessage());
        }
        return items;
    }
    
    /**
     * 
     * @param clientID
     * @return
     */
    public Vector<ItemForSell> getWishItems(UUID clientID) {
        Vector<ItemForSell> items = new Vector<ItemForSell>();
        try {
            // Prepate the statement with SQL update command
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM marketdata.wishes WHERE (" +
                    "clientid = ?)");   
            stmt.setString(1, clientID.toString());
            ResultSet rs = stmt.executeQuery();
            //stmt.close();
            while (rs.next()) {
                String itemName = rs.getString("name");
                float price = rs.getFloat("price");            
                 // Create the item object and return it
                items.addElement(new ItemForSellImpl(itemName, price, clientID));
            }
            
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        } catch(RemoteException ex) {
            System.err.println(ex.getMessage());
        }
        return items;
    }
}
