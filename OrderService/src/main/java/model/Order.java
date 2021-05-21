package model;

import java.sql.*;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Order {
	//A common method to connect to the DB
		private Connection connect()
		{
			Connection con = null;
			try
			{
				Class.forName("com.mysql.jdbc.Driver");
				//Provide the correct details: DBServer/DBName, username, password
				con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/orderdb", "root", "");
			}
		catch (Exception e)
				{e.printStackTrace();}
				return con;
			}
		
		public String insertOrder(String buyerID, String buyerName, String orderType, String orderDate, String orderDescription, String totalAmount)
		{
			String output = "";
			try
			{
				Connection con = connect();
				if (con == null)
				{
					return "Error while connecting to the database for inserting."; 
				}
				
				// create a prepared statement
				String query = " INSERT INTO orderdb (`orderID`, `buyerID`, `buyerName`, `orderType`, `orderDate`, `orderDescription`, `totalAmount`)"
				+ " values (?, ?, ?, ?, ?, ?, ?)";
				PreparedStatement preparedStmt = con.prepareStatement(query);
				
				// binding values
				preparedStmt.setInt(1, 0);
				preparedStmt.setString(2, buyerID);
				preparedStmt.setString(3, buyerName);
				preparedStmt.setString(4, orderType);
				preparedStmt.setString(5, orderDate);
				preparedStmt.setString(6, orderDescription);
				preparedStmt.setDouble(7, Double.parseDouble(totalAmount));
				
				
				// execute the statement
				preparedStmt.execute();
				con.close();
				output = "Inserted successfully";
			}
			catch (Exception e)
			{
				output = "Error while inserting the order.";
				System.err.println(e.getMessage());
			}
			return output;
		}
		
		
		public String readOrder()
		{
			String output = "";
			try
			{
				Connection con = connect();
				if (con == null)
				{
					return "Error while connecting to the database for reading."; 
				}
				
				// Prepare the html table to be displayed
				output = "<table border='1'><tr><th>Order ID</th>" +
				"<th>Buyer ID</th>" +
				"<th>Buyer Name</th>" +
				"<th>Order Type</th>"+
				"<th>Order Date</th>"+
				"<th>Order Description</th>"+
				"<th>Total Amount</th>"+
				"<th>Update</th><th>Remove</th></tr>";
				
				
				String query = "SELECT * FROM orderdb";
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(query);

				// iterate through the rows in the result set
				while (rs.next())
				{
					String orderID = Integer.toString(rs.getInt("orderID"));
					String buyerID = rs.getString("buyerID");
					String buyerName = rs.getString("buyerName");
					String orderType = rs.getString("orderType");
					String orderDate = rs.getString("orderDate");
					String orderDescription = rs.getString("orderDescription");
					String totalAmount =  Double.toString(rs.getDouble("totalAmount"));
					
					// Add into the html table
					output += "<tr><td>" + orderID + "</td>";
					output += "<td>" + buyerID + "</td>";
					output += "<td>" + buyerName + "</td>";
					output += "<td>" + orderType + "</td>";
					output += "<td>" + orderDate + "</td>";
					output += "<td>" + orderDescription + "</td>";
					output += "<td>" + totalAmount + "</td>";
					
					output += "<td><input name='btnUpdate' type='button' value='Update' class='btn btn-secondary'></td>" + "<td><form method='post' action='order.jsp'>"
							 + "<input name='btnRemove' type='submit' value='Remove' class='btn btn-danger'>"
							 + "<input name='orderID' type='hidden' value='" + orderID + "'>" + "</form></td></tr>"; 
					
				}
				
			con.close();
			// Complete the html table
			output += "</table>";
			}
			catch (Exception e)
			{
				output = "Error while reading the order.";
				System.err.println(e.getMessage());
			}
			return output;
		}
		public String updateOrder(String orderID,String buyerID, String buyerName, String orderType, String orderDate, String orderDescription, String totalAmount)
		{
			String output = "";
			try
			{
				Connection con = connect();
				if (con == null)
				{
					return "Error while connecting to the database for updating."; 
				}
				
				// create a prepared statement
				String query = "UPDATE orderdb SET buyerID=?,buyerName=?,orderType=?,orderDate=?,orderDescription=?,totalAmount=? WHERE orderID=? ";
				PreparedStatement preparedStmt = con.prepareStatement(query);
				
				// binding valuesorderDetails
				preparedStmt.setString(1, buyerID);
				preparedStmt.setString(2, buyerName);
				preparedStmt.setString(3, orderType);
				preparedStmt.setString(4, orderDate);
				preparedStmt.setString(5, orderDescription);
				preparedStmt.setDouble(6, Double.parseDouble(totalAmount));
				preparedStmt.setInt(7, Integer.parseInt(orderID));
				
				// execute the statement
				preparedStmt.execute();
				con.close();
				output = "Updated successfully";
			}
			catch (Exception e)
			{
				output = "Error while updating the order.";
				System.err.println(e.getMessage());
			}
			
			return output;
		}
		
		
		public String deleteOrder(String orderID)
		{
			String output = "";
			try
			{
				Connection con = connect();
				if (con == null)
				{
					return "Error while connecting to the database for deleting."; 
				}
				
				// create a prepared statement
				String query = "delete from orderdb where orderID=?";
				PreparedStatement preparedStmt = con.prepareStatement(query);
			
				// binding values
				preparedStmt.setInt(1, Integer.parseInt(orderID));
			
				// execute the statement
				preparedStmt.execute();
				con.close();
				output = "Deleted successfully";
			}
			catch (Exception e)
			{
				output = "Error while deleting the order.";
				System.err.println(e.getMessage());
			}
			return output;
		}

}