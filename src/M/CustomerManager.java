package M;

import java.sql.*;
import java.util.ArrayList;

import common.GlobalData;

public class CustomerManager
{

	public static ArrayList<CustomerDB> getAllCustomer()
	{
		ArrayList<CustomerDB> list = new ArrayList<CustomerDB>();

		try
		{
			// create our mysql database connection
			String myDriver = "com.mysql.jdbc.Driver";
			String myUrl = "jdbc:mysql://" + GlobalData.DATABASE_LOCATION + ":" + GlobalData.DATABASE_PORT + "/"
					+ GlobalData.DATABASE_DATABASE_NAME;
			Class.forName(myDriver);
			Connection conn = DriverManager.getConnection(myUrl, GlobalData.DATABASE_USERNAME, "");

			// our SQL SELECT query.
			// if you only need a few columns, specify them by name instead of using "*"
			String query = "SELECT * FROM customer2";

			// create the java statement
			Statement st = conn.createStatement();

			// execute the query, and get a java resultset
			ResultSet rs = st.executeQuery(query);

			// iterate through the java resultset
			while (rs.next())
			{
				int id = rs.getInt("id");
				String firstName = rs.getString("name");
				String lastName = rs.getString("surname");
				String phone = rs.getString("phone");

				CustomerDB cc = new CustomerDB(id, firstName, lastName, phone);
				list.add(cc);
				// print the results
				System.out.format("%d, %s, %s, %s \n", id, firstName, lastName, phone);
			}
			st.close();
		} catch (Exception e)
		{
			System.err.println("Got an exception! :"+e);
			System.err.println(e.getMessage());
		}

		return list;
	}

	
	public static void saveNewCustomer(CustomerDB x)
	{
		try
		{
			// create our mysql database connection
			String myDriver = "com.mysql.jdbc.Driver";
			String myUrl = "jdbc:mysql://" + GlobalData.DATABASE_LOCATION + ":" + GlobalData.DATABASE_PORT + "/"+ GlobalData.DATABASE_DATABASE_NAME;
			Class.forName(myDriver);
			Connection conn = DriverManager.getConnection(myUrl, GlobalData.DATABASE_USERNAME, "");
	
			String sql = "INSERT INTO customer2 (name,surname,phone) VALUES (?,?,?)";
		
			// create the java statement
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1,x.name);
			st.setString(2,x.surname);
			st.setString(3,x.phone);
			st.execute();
			System.out.println("Record Inserted Successfully");
			st.close();
			
		} 
		catch (Exception e)
		{
			System.err.println("Got an exception! ");
			System.err.println(e.getMessage());
		}
		
	}
	
	
	public static ArrayList<CustomerDB> CheckingOldDataBeforEdit(int id)
	{
		ArrayList<CustomerDB> list = new ArrayList<CustomerDB>();

		try
		{
			// create our mysql database connection
			String myDriver = "com.mysql.jdbc.Driver";
			String myUrl = "jdbc:mysql://" + GlobalData.DATABASE_LOCATION + ":" + GlobalData.DATABASE_PORT + "/"
					+ GlobalData.DATABASE_DATABASE_NAME;
			Class.forName(myDriver);
			Connection conn = DriverManager.getConnection(myUrl, GlobalData.DATABASE_USERNAME, "");

			// our SQL SELECT query.
			// if you only need a few columns, specify them by name instead of using "*"
			String query = "SELECT * FROM customer2 where id =?";

			// create the java statement
			PreparedStatement st = conn.prepareStatement(query);
			st.setInt(1,id);
			// execute the query, and get a java resultset
			ResultSet rs = st.executeQuery();

			// iterate through the java resultset
			while (rs.next())
			{
				String firstName = rs.getString("name");
				String lastName = rs.getString("surname");
				String phone = rs.getString("phone");

				CustomerDB cc = new CustomerDB(id, firstName, lastName, phone);
				list.add(cc);
				// print the results
				System.out.format("%d, %s, %s, %s \n", id, firstName, lastName, phone);
			}
			st.close();
		} catch (Exception e)
		{
			System.err.println("Got an exception! :"+e);
			System.err.println(e.getMessage());
		}

		return list;
	}
	
	public static void editCustomer(CustomerDB x)
	{
		try
		{
			// create our mysql database connection
			String myDriver = "com.mysql.jdbc.Driver";
			String myUrl = "jdbc:mysql://" + GlobalData.DATABASE_LOCATION + ":" + GlobalData.DATABASE_PORT + "/"+ GlobalData.DATABASE_DATABASE_NAME;
			Class.forName(myDriver);
			Connection conn = DriverManager.getConnection(myUrl, GlobalData.DATABASE_USERNAME, "");
	
			String sql = "UPDATE customer2 SET name = ?,surname =? ,phone = ? where id =?";
		
			// create the java statement
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1,x.name);
			st.setString(2,x.surname);
			st.setString(3,x.phone);
			st.setInt(4,x.id);
			st.execute();
			System.out.println("Record Updated Successfully");
			st.close();
			
		} 
		catch (Exception e)
		{
			System.err.println("Got an exception! ");
			System.err.println(e.getMessage());
		}
		
	}
	
	public static void deleteCustomer(CustomerDB x)
	{
		try
		{
			// create our mysql database connection
			String myDriver = "com.mysql.jdbc.Driver";
			String myUrl = "jdbc:mysql://" + GlobalData.DATABASE_LOCATION + ":" + GlobalData.DATABASE_PORT + "/"+ GlobalData.DATABASE_DATABASE_NAME;
			Class.forName(myDriver);
			Connection conn = DriverManager.getConnection(myUrl, GlobalData.DATABASE_USERNAME, "");
	
			String sql = "DELETE from customer2 where id = ?";
		
			// create the java statement
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1,x.id);
			st.execute();
			System.out.println("Record Deleted Successfully");
			st.close();
			
		} 
		catch (Exception e)
		{
			System.err.println("Got an exception! ");
			System.err.println(e.getMessage());
		}
		
	}
	
	public static ArrayList<CustomerDB> searchCustomer(String s)
	{
		ArrayList<CustomerDB> list = new ArrayList<CustomerDB>();

		try
		{
			// create our mysql database connection
			String myDriver = "com.mysql.jdbc.Driver";
			String myUrl = "jdbc:mysql://" + GlobalData.DATABASE_LOCATION + ":" + GlobalData.DATABASE_PORT + "/"
					+ GlobalData.DATABASE_DATABASE_NAME;
			Class.forName(myDriver);
			Connection conn = DriverManager.getConnection(myUrl, GlobalData.DATABASE_USERNAME, "");

			// our SQL SELECT query.
			// if you only need a few columns, specify them by name instead of using "*"
			String query = "SELECT * FROM customer2 where LOWER(name) LIKE ? OR LOWER(surname) LIKE ?";

			// create the java statement
			PreparedStatement st = conn.prepareStatement(query);
			st.setString(1,"%"+s.toLowerCase()+"%");
			st.setString(2,"%"+s.toLowerCase()+"%");
			// execute the query, and get a java resultset
			ResultSet rs = st.executeQuery();

			// iterate through the java resultset
			while (rs.next())
			{
				int id = rs.getInt("id");
				String firstName = rs.getString("name");
				String lastName = rs.getString("surname");
				String phone = rs.getString("phone");

				CustomerDB cc = new CustomerDB(id, firstName, lastName, phone);
				list.add(cc);
				// print the results
				System.out.format("%d, %s, %s, %s \n", id, firstName, lastName, phone);
			}
			st.close();
		} catch (Exception e)
		{
			System.err.println("Got an exception! :"+e);
			System.err.println(e.getMessage());
		}

		return list;
	}
	
	
	public static void main(String[] args) /// for test method getAllCustomer()
	{
		/*
		//Test getAllCustomer()
		ArrayList<CustomerDB> ll = getAllCustomer();
		System.out.println(ll.size());
		*/
		
		//Test saveNewCustomer(Customer x)
		CustomerDB x = new CustomerDB();
		x.id = 26;
		x.name = "test";
		x.surname = "test";
		x.phone = "0804452255";
		//saveNewCustomer(x);
		//editCustomer(x);
		//deleteCustomer(x);
	}
}
