package M;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.sql.*;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import common.GlobalData;
public class ProductManager
{
	public static ArrayList<ProductDB> getAllProduct()
	{
		ArrayList<ProductDB> list = new ArrayList<ProductDB>();

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
			String query = "SELECT * FROM products";

			// create the java statement
			Statement st = conn.createStatement();

			// execute the query, and get a java resultset
			ResultSet rs = st.executeQuery(query);

			// iterate through the java resultset
			while (rs.next())
			{
				int product_id = rs.getInt("product_id");
				String product_name = rs.getString("product_name");
				double price_per_unit = rs.getDouble("price_per_unit");
				String product_description = rs.getString("product_description");
				byte[] image_byte = rs.getBytes("product_image");
				
				
			    ByteArrayInputStream bais = new ByteArrayInputStream(image_byte);
			    BufferedImage product_image = ImageIO.read(bais); 
				
				ProductDB cc = new ProductDB(product_id, product_name, price_per_unit, product_description,product_image);
				list.add(cc);
				// print the results
				//System.out.format("%d, %s, %s, %s \n", id, firstName, lastName, phone);
			}
			st.close();
		} catch (Exception e)
		{
			System.err.println("Got an exception! :"+e);
			System.err.println(e.getMessage());
		}

		return list;
	}

	
	public static void saveNewProduct(ProductDB x)
	{
		try
		{
			// create our mysql database connection
			String myDriver = "com.mysql.jdbc.Driver";
			String myUrl = "jdbc:mysql://" + GlobalData.DATABASE_LOCATION + ":" + GlobalData.DATABASE_PORT + "/"+ GlobalData.DATABASE_DATABASE_NAME;
			Class.forName(myDriver);
			Connection conn = DriverManager.getConnection(myUrl, GlobalData.DATABASE_USERNAME, "");
			
			byte[] buffer;
			if(x.product_image != null)
			{
				ByteArrayOutputStream outStream = new ByteArrayOutputStream();
				ImageIO.write(x.product_image,"png",outStream);
				buffer = outStream.toByteArray();
				outStream.close();
			}
			else
			{
				buffer = new byte[0];
			}
	
			String sql = "INSERT INTO Products (product_name, price_per_unit, product_description,product_image) VALUES (?,?,?,?)";
		
			// create the java statement
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1,x.product_name);
			st.setDouble(2,x.price_per_unit);
			st.setString(3,x.product_description);
			st.setBytes(4,buffer);
			st.execute();
			st.close();
			
		} 
		catch (Exception e)
		{
			System.err.println("Got an exception! ");
			System.err.println(e.getMessage());
		}
		
	}
	
	
	public static void editProduct(ProductDB x)
	{
		try
		{
			// create our mysql database connection
			String myDriver = "com.mysql.jdbc.Driver";
			String myUrl = "jdbc:mysql://" + GlobalData.DATABASE_LOCATION + ":" + GlobalData.DATABASE_PORT + "/"+ GlobalData.DATABASE_DATABASE_NAME;
			Class.forName(myDriver);
			Connection conn = DriverManager.getConnection(myUrl, GlobalData.DATABASE_USERNAME, "");
			
			byte[] buffer;
			if(x.product_image != null)
			{
				ByteArrayOutputStream outStream = new ByteArrayOutputStream();
				ImageIO.write(x.product_image,"png",outStream);
				buffer = outStream.toByteArray();
				outStream.close();
			}
			else
			{
				buffer = new byte[0];
			}
	
			String sql = "UPDATE Products SET product_name = ?, price_per_unit = ?, product_description = ?,product_image = ? where product_id = ?";
		
			// create the java statement
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1,x.product_name);
			st.setDouble(2,x.price_per_unit);
			st.setString(3,x.product_description);
			st.setBytes(4,buffer);
			st.setInt(5, x.product_id);
			st.execute();
			st.close();
			
		} 
		catch (Exception e)
		{
			System.err.println("Got an exception! ");
			System.err.println(e.getMessage());
		}
		
	}
	
	public static void deleteProduct(ProductDB x)
	{
		try
		{
			// create our mysql database connection
			String myDriver = "com.mysql.jdbc.Driver";
			String myUrl = "jdbc:mysql://" + GlobalData.DATABASE_LOCATION + ":" + GlobalData.DATABASE_PORT + "/"+ GlobalData.DATABASE_DATABASE_NAME;
			Class.forName(myDriver);
			Connection conn = DriverManager.getConnection(myUrl, GlobalData.DATABASE_USERNAME, "");
	
			String sql = "DELETE from Products where product_id = ?";
		
			// create the java statement
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, x.product_id);
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
	
	public static ArrayList<ProductDB> searchProduct(String s)
	{
		ArrayList<ProductDB> list = new ArrayList<ProductDB>();

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
			String query = "SELECT * FROM products where LOWER(product_name) LIKE ? OR LOWER(product_description) LIKE ?";

			// create the java statement
			PreparedStatement st = conn.prepareStatement(query);
			st.setString(1,"%"+s.toLowerCase()+"%");
			st.setString(2,"%"+s.toLowerCase()+"%");
			// execute the query, and get a java resultset
			ResultSet rs = st.executeQuery();

			// iterate through the java resultset
			while (rs.next())
			{
				int product_id = rs.getInt("product_id");
				String product_name = rs.getString("product_name");
				double price_per_unit = rs.getDouble("price_per_unit");
				String product_description = rs.getString("product_description");
				byte[] image_byte = rs.getBytes("product_image");
				
				
			    ByteArrayInputStream bais = new ByteArrayInputStream(image_byte);
			    BufferedImage product_image = ImageIO.read(bais); 
				
				ProductDB cc = new ProductDB(product_id, product_name, price_per_unit, product_description,product_image);
				list.add(cc);
				// print the results
				//System.out.format("%d, %s, %s, %s \n", id, firstName, lastName, phone);
			}
			st.close();
		} catch (Exception e)
		{
			System.err.println("Got an exception! :"+e);
			System.err.println(e.getMessage());
		}

		return list;
	}
	
	public static void main(String[] args) /// for test method getAllProduct()
	{
		/*
		//Test getAllProduct()
		ArrayList<ProductDB> ll = getAllProduct();
		System.out.println(ll.size());
		*/
		
		//Test saveNewProduct(Product x)
		ProductDB x = new ProductDB();
		//x.id = 26;
		//x.name = "test";
		//x.surname = "test";
		//x.phone = "0804452255";
		//saveNewProduct(x);
		//editProduct(x);
		//deleteProduct(x);
	}
}

