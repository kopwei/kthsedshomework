/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gnomeshop;

/**
 *
 * @author Kop
 */
import gnomeshop.items.*;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * This class contains several utility functions for query and update the database 
 */
public class DatabaseUtil {

    String dbUrl = "jdbc:mysql://localhost:3306/webshop";
    String jdbcDriver = "com.mysql.jdbc.Driver";
    Connection connection = null;

    public DatabaseUtil() {
        try {

            
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }

    /**
     * This method is used to set the string of database url
     * @param dbUrl the input database url
     */
    public void setDbUrl(String dbUrl) {
        this.dbUrl = dbUrl;
    }

    /**
     * This method is used to set the String of database driver
     * @param jdbcDriver the input database driver string
     */
    public void setJdbcDriver(String jdbcDriver) {
        this.jdbcDriver = jdbcDriver;
    }

    /**
     * This method is used to get all the categories stored in the database
     * @return The array of the category objects
     */
    public ArrayList<Category> getCategories() {
        // Prepared the return array and the query string 
        ArrayList<Category> categories = new ArrayList<Category>();
        String sql = "SELECT Id, Description FROM Categories";
        try {
            // Create the connection and execute the query command
            Class.forName(jdbcDriver).newInstance();
            connection = DriverManager.getConnection(dbUrl, "root", "123456");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            // Get the query result and fill the return array list
            while (resultSet.next()) {
                String id = resultSet.getString(1);
                String description = resultSet.getString(2);
                Category category = new Category(id, description);
                categories.add(category);
            }
            // Close the database connection
            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception ex) {
            // TODO: handle exception
            System.err.println(ex.getMessage());
        }
        return categories;
    }

    /**
     * This method is used to get the detailed information of the product according to the given product id
     * @param productId The product id which uniquely identifies a product
     * @return The product's detail
     */
    public ProductBean getProductDetails(String productId) {
        // Prepared the return object and the query string
        String sql = "SELECT ProductId, Name, Description, Price, Quantity FROM Products" + " WHERE ProductId = " + productId;
        ProductBean result = null;
        try {
            Class.forName(jdbcDriver).newInstance();
            connection = DriverManager.getConnection(dbUrl, "root", "123456");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            // Get the query result and fill the return object
            if (resultSet.next()) {
                String id = resultSet.getString(1);
                String name = resultSet.getString(2);
                String description = resultSet.getString(3);
                float price = resultSet.getFloat(4);
                int quantity = resultSet.getInt(5);
                result = new ProductBean(id, name, description, price, quantity);
            }
            // Close the database connection
            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            // TODO: handle exception
            System.err.println(e.getMessage());
        }
        return result;
    }

    /**
     * This method is used to search for a list of matched products according to the product search query String
     * @param searchKey The input search query String
     * @return The matched list of products
     */
    public ArrayList<ProductBean> searchProducts(String searchKey) {
        // Prepared the return array and the query string 
        ArrayList<ProductBean> products = new ArrayList<ProductBean>();
        String sql = "SELECT ProductId, Name, Price FROM Products" + " WHERE Name LIKE '%" + searchKey + "%'" +
                " OR Description LIKE '%" + searchKey + "%'";
        try {
            // Create the connection and execute the query command
            Class.forName(jdbcDriver).newInstance();
            connection = DriverManager.getConnection(dbUrl, "root", "123456");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            // Get the query result and fill the return array list
            while (resultSet.next()) {
                String id = resultSet.getString(1);
                String name = resultSet.getString(2);
                float price = resultSet.getFloat(3);
                ProductBean product = new ProductBean(id, name, price);
                products.add(product);
            }
            // Close the database connection
            resultSet.close();
            statement.close();
            connection.close();

        } catch (Exception e) {
            // TODO: handle exception
            System.err.println(e.getMessage());
        }
        return products;
    }

    /**
     * This method is used to get all the products which belongs to a single category
     * @param categoryId The id of the desired category
     * @return The matched list of products
     */
    public ArrayList<ProductBean> getProductByCategory(String categoryId) {
        // Prepared the return array and the query string 
        ArrayList<ProductBean> products = new ArrayList<ProductBean>();
        String sql = "SELECT ProductId, Name, Price FROM Products" + " WHERE CategoryId = " + categoryId;
        try {
            // Create the connection and execute the query command
            Class.forName(jdbcDriver).newInstance();
            connection = DriverManager.getConnection(dbUrl, "root", "123456");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            // Get the query result and fill the return array list
            while (resultSet.next()) {
                String id = resultSet.getString(1);
                String name = resultSet.getString(2);
                float price = resultSet.getFloat(3);
                ProductBean product = new ProductBean(id, name, price);
                products.add(product);
            }
            // Close the database connection
            resultSet.close();
            statement.close();
            connection.close();

        } catch (Exception e) {
            // TODO: handle exception
            System.err.println(e.getMessage());
        }
        return products;
    }
    
    public ArrayList<MemberBean> getAllMembers() {
        // Prepared the return array and the query string 
        ArrayList<MemberBean> members = new ArrayList<MemberBean>();
        
        return members;
    }

    /**
     * This method is used to store a product into the database
     * @param product The product to be stored
     */
    public synchronized void insertProduct(ProductBean product) {
        // Verify the input parameter
        if (null == product || 0 >= product.getQuantity()) {
            return;
        }
        // Prepare the SQL arguments and sql statement
        String productId = product.getId();
        String productName = product.getName();
        float price = product.getPrice();
        String description = product.getDescription();
        int quantiry = product.getQuantity();

        String sql = "INSERT INTO Products (ProductId, Name," +
                "Description, Price, Quantity) VALUES(" + productId + ", " + productName + ", " + price + ", " +
                description + ", " + quantiry + ")";
        try {
            // Create the connection and execute the update command
            Class.forName(jdbcDriver).newInstance();
            connection = DriverManager.getConnection(dbUrl, "root", "123456");
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
            statement.close();
            connection.close();

        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }

    /**
     * This method is used to get the member object by user name and password
     * @param userName The user name 
     * @param passWord The password
     * @return The found user bean object, return null if there is not any match
     */
    public MemberBean getMemberByUserNameAndPwd(String userName, String passWord) {
        // Verify the input parameter
        if (userName.length() < 1 || passWord.length() < 1) {
            return null;
        }
        try {
            // Hash the password
            MessageDigest md = MessageDigest.getInstance("SHA");
            String digestedPwd = new String(md.digest(passWord.getBytes()));
            // Prepare the query command string
            String sql = "SELECT member_id,  member_level, first_name, last_name, email, phone FROM members" +
                    " WHERE (username = " + userName + " AND password = " + digestedPwd + ")";
            // Create the connection and execute the query
            Class.forName(jdbcDriver).newInstance();
            connection = DriverManager.getConnection(dbUrl, "root", "123456");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            // Get the query result 
            if (resultSet.next()) {
                String memberId = resultSet.getString(1);
                int memberLevel = resultSet.getInt(2);
                String firstName = resultSet.getString(3);
                String lastName = resultSet.getString(4);
                String email = resultSet.getString(5);
                String telephone = resultSet.getString(6);
                resultSet.close();
                statement.close();
                connection.close();
                return new MemberBean(memberId, userName, passWord, memberLevel, firstName, lastName, telephone, email);
            } else {
                return null;
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            return null;
        }
    }

    /**
     * This method is used to store a member into the database
     * @param member The member to be stored
     */
    public synchronized void insertMember(MemberBean member) {
        // Verify the input parameter
        if (null == member) {
            return;
        }

        try {
            // Hash the password
            String passWord = member.getPassWord();
            MessageDigest md = MessageDigest.getInstance("SHA");
            String digestedPwd = new String(md.digest(passWord.getBytes()));
            // Prepare the SQL arguments and sql statement
            String sql = "INSERT INTO members (member_id, username, password, member_level, first_name, " +
                    "last_name, email, phone, isblocked)  VALUES(?, ?, ?, ?, ?, ?, ?, ?, ? ) ";
            // Create the connection and execute the update command
            Class.forName(jdbcDriver).newInstance();
            connection = DriverManager.getConnection(dbUrl, "root", "123456");
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, member.getMemberId());
            statement.setString(2, member.getUserName());
            statement.setString(3, digestedPwd);
            statement.setInt(4, member.getMemberLevel());
            statement.setString(5, member.getFirstName());
            statement.setString(6, member.getLastName());
            statement.setString(7, member.getEmail());
            statement.setString(8, member.getTelephone());
            statement.setBoolean(9, false);

            statement.executeUpdate();
            statement.close();
            connection.close();

        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }

    /**
     * This method is used to get the detailed information of the order according to the given member id
     * @param memberId The member's Id
     * @return The collection of orders
     */
    public ArrayList<OrderBean> getOrderByMemberId(String memberId) {
        // Prepared the return array and the query string 
        ArrayList<OrderBean> orders = new ArrayList<OrderBean>();
        String sql = "SELECT OrderId,  DeliveryAddress, CCName, CCNumber, CCExpireDate  FROM Products" +
                " WHERE member_id = " + memberId;
        try {
            // Create the connection and execute the query command
            Class.forName(jdbcDriver).newInstance();
            connection = DriverManager.getConnection(dbUrl, "root", "123456");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            // Get the query result and fill the return array list
            while (resultSet.next()) {
                String orderId = resultSet.getString(1);
                String deliveryAddress = resultSet.getString(2);
                String ccName = resultSet.getString(3);
                String ccNumber = resultSet.getString(4);
                String ccExpireDate = resultSet.getString(5);
                OrderBean order = new OrderBean(orderId, memberId, ccName, deliveryAddress, ccName,
                        ccNumber, ccExpireDate);
                orders.add(order);
            }
            // Close the database connection
            resultSet.close();
            statement.close();
            connection.close();

        } catch (Exception e) {
            // TODO: handle exception
            System.err.println(e.getMessage());
        }
        return orders;
    }

    /**
     * This method is used to store a order into the database
     * @param order The current submit order
     * @param shoppingCart The shopping cart with the order
     */
    public synchronized void insertOrder(OrderBean order, ShoppingCartBean shoppingCart) {
        @SuppressWarnings("unused")
        // Prepare the SQL arguments
        String orderId = order.getOrderId();
        String memberId = order.getMemberId();
        String contactName = order.getContactName();
        String deliveryAddress = order.getDeliveryAddress();
        String creditCardName = order.getCreditCardName();
        String creditCardNumber = order.getCreditCardNumber();
        String creditCardExpiryDate = order.getCreditCardExpiryDate();
        String sql = "INSERT INTO Orders" + " (OrderId, MemberId, ContactName, DeliveryAddress, CCName, CCNumber, CCExpiryDate)" +
                " VALUES" + " (" + orderId + ", '" + memberId + "',  '" + contactName + "', '" + deliveryAddress + "', '" + creditCardName +
                "', '" + creditCardNumber + "', '" + creditCardExpiryDate + "')";
        try {
            // Create the connection and execute the update command
            Class.forName(jdbcDriver).newInstance();
            connection = DriverManager.getConnection(dbUrl, "root", "123456");
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);

            // Iterate the shopping items and insert them into details
            Iterator<ShoppingItemBean> shoppingItems = shoppingCart.getShoppingItems().iterator();
            while (shoppingItems.hasNext()) {
                ShoppingItemBean item = shoppingItems.next();
                String productId = item.getProductId();
                int quantity = item.getQuantity();
                float price = item.getPrice();
                sql = "INSERT INTO OrderDetails" + " (OrderId, ProductId, Quantity, Price)" + " VALUES" + " (" +
                        orderId + ", " + productId + ", " + quantity + ", " + price + ")";
                statement.execute(sql);
            }
            statement.close();
            connection.close();

        } catch ( Exception e) {
            // TODO: handle exception
            System.err.println(e.getMessage());
        }
    }
}
