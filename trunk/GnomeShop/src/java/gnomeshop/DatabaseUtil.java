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
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;

/**
 * This class contains several utility functions for query and update the database 
 */
public class DatabaseUtil {

    String dbUrl = "jdbc:mysql://localhost:3306/webshop";
    String jdbcDriver = "com.mysql.jdbc.Driver";
    Connection connection = null;
    private final String dbPassword = "123456";
    private final String dbUserName = "root";


    /**
     * Default constructor
     */
    public DatabaseUtil() {
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
            connection = DriverManager.getConnection(dbUrl,dbUserName,dbPassword);
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
        String sql = "SELECT ProductId, Name, Description, Price, Quantity FROM Products" + " WHERE ProductId = '" + productId + "'";
        ProductBean result = null;
        try {
            Class.forName(jdbcDriver).newInstance();
            connection = DriverManager.getConnection(dbUrl,dbUserName,dbPassword);
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
     * This method is used to update the quantity of product when customer wants to buy the product
     * @param productId The product id which uniquely identifies a product
     * @param Quantity The amount of product customer wants to buy, which is also the amount is to be decreased
     * @return result of updating action
     */
    public synchronized boolean decreaseProductQuantity(String productId, int Quantity) {
         // Prepared the return object and the query string
        String querySql = "SELECT Quantity FROM Products WHERE ProductId = '" + productId + "'";
        try {
            Class.forName(jdbcDriver).newInstance();
            connection = DriverManager.getConnection(dbUrl,dbUserName,dbPassword);
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(querySql);
            if (result.next()) {
                String sql = null;
                int storedQuantity = Integer.parseInt(result.getString(1));
                if (storedQuantity > Quantity) {
                    sql = "UPDATE Products SET Quantity = Quantity - " + Quantity + " WHERE ProductId = '" + productId + "'";

                } else if (storedQuantity == Quantity) {
                    sql = "DELETE FROM Products WHERE ProductId = '" + productId + "'";
                } else {
                    throw new Exception("Can not decrease to minus");
                }
                statement.executeUpdate(sql);
            }
            // Close the database connection
            statement.close();
            connection.close();
        } catch (Exception e) {
            // TODO: handle exception
            System.err.println(e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * This method is used to search for a list of matched products according to the product search query String
     * @param searchKey The input search query String
     * @return The matched list of products
     */
    public ArrayList<ProductBean> searchProducts(String searchKey) {
        // Prepared the return array and the query string 
        ArrayList<ProductBean> products = new ArrayList<ProductBean>();
        String sql = "SELECT ProductId, Name, Price, Description, Quantity FROM Products" + " WHERE Name LIKE '%" + searchKey + "%'" +
                " OR Description LIKE '%" + searchKey + "%'";
        try {
            // Create the connection and execute the query command
            Class.forName(jdbcDriver).newInstance();
            connection = DriverManager.getConnection(dbUrl,dbUserName,dbPassword);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            // Get the query result and fill the return array list
            while (resultSet.next()) {
                String id = resultSet.getString(1);
                String name = resultSet.getString(2);
                float price = resultSet.getFloat(3);
                String desc = resultSet.getString(4);
                int quantity = resultSet.getInt(5);
                ProductBean product = new ProductBean(id, name, desc, price, quantity);
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
     * This method is used to get all the products
     * @return The  list of products
     */
    public ArrayList<ProductBean> getAllProducts() {
        // Prepared the return array and the query string 
        ArrayList<ProductBean> products = new ArrayList<ProductBean>();
        String sql = "SELECT ProductId, Name, Price, Description, Quantity FROM Products";
        try {
            // Create the connection and execute the query command
            Class.forName(jdbcDriver).newInstance();
            connection = DriverManager.getConnection(dbUrl,dbUserName,dbPassword);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            // Get the query result and fill the return array list
            while (resultSet.next()) {
                String id = resultSet.getString(1);
                String name = resultSet.getString(2);
                float price = resultSet.getFloat(3);
                String desc = resultSet.getString(4);
                int quantity = resultSet.getInt(5);
                ProductBean product = new ProductBean(id, name, desc, price, quantity);
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
        String sql = "SELECT ProductId, Name, Price, Description, Quantity FROM Products" + " WHERE CategoryId = " + categoryId;
        try {
            // Create the connection and execute the query command
            Class.forName(jdbcDriver).newInstance();
            connection = DriverManager.getConnection(dbUrl,dbUserName,dbPassword);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            // Get the query result and fill the return array list
            while (resultSet.next()) {
                String id = resultSet.getString(1);
                String name = resultSet.getString(2);
                float price = resultSet.getFloat(3);
                String desc = resultSet.getString(4);
                int quantity = resultSet.getInt(5);
                ProductBean product = new ProductBean(id, name, desc, price, quantity);
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
     * This method is used to change the block state of the member
     * @param memberId The id of the target member
     * @param setBlock Whether to block or unblock
     */
    public void blockMember(String memberId, boolean setBlock) {
        // Verify the input parameter
        if (memberId.length() < 1) {
            return;
        }
        //Prepare the update string
        String sql = "UPDATE members SET isblocked = " + setBlock + " WHERE member_id = '" + memberId + "'";
        try {
            // Create the connection and execute the query command
            Class.forName(jdbcDriver).newInstance();
            connection = DriverManager.getConnection(dbUrl,dbUserName,dbPassword);
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
            statement.close();
            connection.close();
        } catch (Exception e) {
            // TODO: handle exception
            System.err.println(e.getMessage());
        }
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

        String sql = "INSERT INTO Products (ProductId, Name, " +
                "Price, Description, Quantity) VALUES('" + productId + "', '" + productName + "', " + price + ", '" +
                description + "', " + quantiry + ")";
        try {
            // Create the connection and execute the update command
            Class.forName(jdbcDriver).newInstance();
            connection = DriverManager.getConnection(dbUrl,dbUserName,dbPassword);
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
            statement.close();
            connection.close();

        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }

    /**
     * This method is used to remove a kind of product from database
     * @param productId The ID of product to be removed
     */
    public synchronized void removeProduct(String productId) {
        // Verify the input parameter
        if (null == productId) {
            return;
        }
        // Prepare the SQL arguments and sql statement
        String sql = "DELETE FROM Products WHERE ProductId = '" + productId + "'";
        try {
            // Create the connection and execute the update command
            Class.forName(jdbcDriver).newInstance();
            connection = DriverManager.getConnection(dbUrl,dbUserName,dbPassword);
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
            String sql = "SELECT member_id,  member_level, first_name, last_name, email, phone, isblocked FROM members" +
                    " WHERE (username = '" + userName + "' AND password = '" + digestedPwd + "')";
            // Create the connection and execute the query
            Class.forName(jdbcDriver).newInstance();
            connection = DriverManager.getConnection(dbUrl,dbUserName,dbPassword);
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
                boolean blocked = resultSet.getBoolean(7);
                resultSet.close();
                statement.close();
                connection.close();
                return new MemberBean(memberId, userName, passWord, memberLevel, firstName, lastName, telephone, email, blocked);
            } else {
                return null;
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            return null;
        }
    }

    /**
     * This method is used to get the member object by user's id
     * @param memberId User's member id
     * @return The member object
     */
    public MemberBean getMemberById(String memberId) {
        // Verify the input parameter
        if (memberId.length() < 1) {
            return null;
        }
        try {
            // Prepare the query command string
            String sql = "SELECT username, password,  member_level,  first_name, last_name, email, phone, isblocked FROM members" +
                    " WHERE (member_id = '" + memberId + "')";
            // Create the connection and execute the query
            Class.forName(jdbcDriver).newInstance();
            connection = DriverManager.getConnection(dbUrl,dbUserName,dbPassword);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            // Get the query result 
            if (resultSet.next()) {
                String userName = resultSet.getString(1);
                String password = resultSet.getString(2);
                int memberLevel = resultSet.getInt(3);
                String firstName = resultSet.getString(4);
                String lastName = resultSet.getString(5);
                String email = resultSet.getString(6);
                String telephone = resultSet.getString(7);
                boolean blocked = resultSet.getBoolean(8);
                resultSet.close();
                statement.close();
                connection.close();
                return new MemberBean(memberId, userName, password, memberLevel, firstName, lastName,
                        telephone, email, blocked);
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
     * @return Whether it suceed or not
     */
    public synchronized boolean insertMember(MemberBean member) {
        // Verify the input parameter
        if (null == member) {
            return false;
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
            connection = DriverManager.getConnection(dbUrl,dbUserName,dbPassword);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, member.getMemberId());
            statement.setString(2, member.getUserName());
            statement.setString(3, digestedPwd);
            statement.setInt(4, member.getMemberLevel());
            statement.setString(5, member.getFirstName());
            statement.setString(6, member.getLastName());
            statement.setString(7, member.getEmail());
            statement.setString(8, member.getTelephone());
            statement.setBoolean(9, member.getBlocked());

            statement.executeUpdate();
            statement.close();
            connection.close();
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            return false;
        }
        return true;
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
            connection = DriverManager.getConnection(dbUrl,dbUserName,dbPassword);
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
     * @return Whether it suceed or not
     */
    public synchronized boolean insertOrder(OrderBean order, ShoppingCartBean shoppingCart) {
        @SuppressWarnings("unused")
        // Prepare the SQL arguments
        String orderId = order.getOrderId();
        String memberId = order.getMemberId();
        String contactName = order.getContactName();
        String deliveryAddress = order.getDeliveryAddress();
        String creditCardName = order.getCreditCardName();
        String creditCardNumber = order.getCreditCardNumber();
        String creditCardExpiryDate = order.getCreditCardExpiryDate();
        String sql = "INSERT INTO Orders" + " (OrderId, member_id, ContactName, DeliveryAddress, CCName, CCNumber, CCExpiryDate)" +
                " VALUES" + " ('" + orderId + "', '" + memberId + "',  '" + contactName + "', '" + deliveryAddress + "', '" + creditCardName +
                "', '" + creditCardNumber + "', '" + creditCardExpiryDate + "')";
        try {
            // Create the connection and execute the update command
            Class.forName(jdbcDriver).newInstance();
            connection = DriverManager.getConnection(dbUrl,dbUserName,dbPassword);
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);

            // Iterate the shopping items and insert them into details
            Iterator<ShoppingItemBean> shoppingItems = shoppingCart.getShoppingItems().iterator();
            while (shoppingItems.hasNext()) {
                ShoppingItemBean item = shoppingItems.next();
                String detailId = UUID.randomUUID().toString();
                String productId = item.getProductId();
                String productName = item.getProductName();
                int quantity = item.getQuantity();
                float price = item.getPrice();
                sql = "INSERT INTO OrderDetails" + " (Id, OrderId, ProductId, ProductName, Quantity, Price)" + " VALUES" + " ('" + detailId + "','" +
                        orderId + "',' " + productId + "', ' " + productName + "'," + quantity + ", " + price + ")";
                statement.execute(sql);
            }
            statement.close();
            connection.close();

        } catch ( Exception e) {
            // TODO: handle exception
            System.err.println(e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * This method is used to get the order detail according to a order id
     * @param orderId The id which represent a order
     * @return The list of shopping items within a order
     */
    public ArrayList<ShoppingItemBean> getOrderDetails(String orderId) {
        // Prepared the return array and the query string 
        ArrayList<ShoppingItemBean> items = new ArrayList<ShoppingItemBean>();
        String sql = "SELECT ProductId, ProductName, Quantity, Price FROM OrderDetails WHERE OrderId = '" + orderId + "'";
        try {
            // Create the connection and execute the query command
            Class.forName(jdbcDriver).newInstance();
            connection = DriverManager.getConnection(dbUrl,dbUserName,dbPassword);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            // Get the query result and fill the return array list
            while (resultSet.next()) {
                String productId = resultSet.getString(1);
                String productName = resultSet.getString(2);
                int quantity = resultSet.getInt(3);
                float price = resultSet.getFloat(4);
                ShoppingItemBean item = new ShoppingItemBean(productId, productName, price, quantity);
                items.add(item);
            }
            // Close the database connection
            resultSet.close();
            statement.close();
            connection.close();

        } catch (Exception e) {
            // TODO: handle exception
            System.err.println(e.getMessage());
        }
        return items;
    }

    /**
     * This method is used to get all the orders
     * @return The  list of orders
     */
    public ArrayList<OrderBean> getAllOrders() {
        // Prepared the return array and the query string 
        ArrayList<OrderBean> orders = new ArrayList<OrderBean>();
        String sql = "SELECT OrderId, member_id,   ContactName, DeliveryAddress, CCName, CCNumber, CCExpiryDate  FROM Orders";
        try {
            // Create the connection and execute the query command
            Class.forName(jdbcDriver).newInstance();
            connection = DriverManager.getConnection(dbUrl,dbUserName,dbPassword);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            // Get the query result and fill the return array list
            while (resultSet.next()) {
                String orderId = resultSet.getString(1);
                String memberId = resultSet.getString(2);
                String contactName = resultSet.getString(3);
                String deliveryAddress = resultSet.getString(4);
                String ccName = resultSet.getString(5);
                String ccNumber = resultSet.getString(6);
                String ccExpireDate = resultSet.getString(7);
                OrderBean order = new OrderBean(orderId, memberId, contactName, deliveryAddress, ccName, ccNumber, ccExpireDate);
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
     * This method is used to get all the members
     * @return The  list of members
     */
    public ArrayList<MemberBean> getAllMembers() {
        // Prepared the return array and the query string 
        ArrayList<MemberBean> members = new ArrayList<MemberBean>();
        String sql = "SELECT member_id, username, password,  member_level,  first_name, last_name, email, phone, isblocked FROM members";
        try {
            // Create the connection and execute the query command
            Class.forName(jdbcDriver).newInstance();
            connection = DriverManager.getConnection(dbUrl,dbUserName,dbPassword);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            // Get the query result and fill the return array list
            while (resultSet.next()) {
                String memberId = resultSet.getString(1);
                String userName = resultSet.getString(2);
                String password = resultSet.getString(3);
                int memberLevel = resultSet.getInt(4);
                String firstName = resultSet.getString(5);
                String lastName = resultSet.getString(6);
                String email = resultSet.getString(7);
                String telephone = resultSet.getString(8);
                boolean blocked = resultSet.getBoolean(9);
                MemberBean member = new MemberBean(memberId, userName, password, memberLevel, firstName, lastName,
                        telephone, email, blocked);
                members.add(member);
            }
            // Close the database connection
            resultSet.close();
            statement.close();
            connection.close();

        } catch (Exception e) {
            // TODO: handle exception
            System.err.println(e.getMessage());
        }
        return members;
    }
}
