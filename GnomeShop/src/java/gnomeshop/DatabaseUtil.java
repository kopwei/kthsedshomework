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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * This class contains several utility functions for query and update the database 
 */
public class DatabaseUtil {
	String dbUrl = "jdbc:odbc:OnlineShop";
	String jdbcDriver = "sun.jdbc.odbc.JdbcOdbcDriver";
	
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
		ArrayList<Category>  categories = new ArrayList<Category>();
		String sql = "SELECT Id, Description FROM Categories";
		try {
			// Create the connection and execute the query command
			Class.forName(jdbcDriver);
			Connection connection = DriverManager.getConnection(dbUrl);
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
		String sql= "SELECT ProductId, Name, Description, Price FROM Products" + " WHERE ProductId = " + productId;
		ProductBean result = null;
		try {
			Class.forName(jdbcDriver);
			Connection connection = DriverManager.getConnection(dbUrl);
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			// Get the query result and fill the return object
			if (resultSet.next()) {
				String id = resultSet.getString(1);
				String name = resultSet.getString(2);
				String description = resultSet.getString(3);
				float price = resultSet.getFloat(4);
				result = new ProductBean(id, name, description, price);
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
			Class.forName(jdbcDriver);
			Connection connection = DriverManager.getConnection(dbUrl);
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
			Class.forName(jdbcDriver);
			Connection connection = DriverManager.getConnection(dbUrl);
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
	 * This method is used to store a order into the database
	 * @param order The current submit order
	 * @param shoppingCart The shopping cart with the order
	 */
	public synchronized void insertOrder(OrderBean order, ShoppingCartBean shoppingCart) {
		// Create the order id and prepare the order information
		long orderId = System.currentTimeMillis();
		@SuppressWarnings("unused")
		ArrayList<ProductBean> products = new ArrayList<ProductBean>();
		String contactName = order.getContactName();
		String deliveryAddress = order.getDeliveryAddress();
		String creditCardName = order.getCreditCardName();
		String creditCardNumber = order.getCreditCardNumber();
		String creditCardExpiryDate = order.getCreditCardExpiryDate();
		String sql = "INSERT INTO Orders" + " (OrderId, ContactName, DeliveryAddress, CCName, CCNumber, CCExpiryDate)" +
			" VALUES" + " (" + orderId + ", '" + contactName + "', '" + deliveryAddress + "', '" + creditCardName + 
			"', '" + creditCardNumber + "', '" + creditCardExpiryDate + "')";
		try {
			// Create the connection and execute the update command
			Class.forName(jdbcDriver);
			Connection connection = DriverManager.getConnection(dbUrl);
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
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println(e.getMessage());
		}
		
	}
}
