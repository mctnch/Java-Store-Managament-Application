package M;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Base64;

import common.GlobalData;
import common.PasswordUtils;

public class UserManager
{

	public static ArrayList<UserDB> getAllUser()
	{
		ArrayList<UserDB> list = new ArrayList<UserDB>();

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
			String query = "SELECT * FROM users";

			// create the java statement
			Statement st = conn.createStatement();

			// execute the query, and get a java resultset
			ResultSet rs = st.executeQuery(query);

			// iterate through the java resultset
			while (rs.next())
			{
				int id = rs.getInt("id");
				String username = rs.getString("username");
				String usertype = rs.getString("usertype");

				UserDB cc = new UserDB(id, username, usertype);
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

	
	public static void saveNewUser(UserDB x)
	{
		
		byte[] salt =  PasswordUtils.generateSalt();
		String store_salt = Base64.getEncoder().encodeToString(salt);

        // Hash the password with the salt
        String hashedPassword = PasswordUtils.hashPassword(x.password, salt);
		
		try
		{
			// create our mysql database connection
			String myDriver = "com.mysql.jdbc.Driver";
			String myUrl = "jdbc:mysql://" + GlobalData.DATABASE_LOCATION + ":" + GlobalData.DATABASE_PORT + "/"+ GlobalData.DATABASE_DATABASE_NAME;
			Class.forName(myDriver);
			Connection conn = DriverManager.getConnection(myUrl, GlobalData.DATABASE_USERNAME, "");
	
			String sql = "INSERT INTO users (username,hashed_password,salt,usertype) VALUES (?,?,?,?)";
			
			// create the java statement
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1,x.username);
			st.setString(2,hashedPassword);
			st.setString(3, store_salt);
			st.setString(4,x.usertype);
		
			// create the java statement
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
	
	public static void deleteUser(UserDB x)
	{
		try
		{
			// create our mysql database connection
			String myDriver = "com.mysql.jdbc.Driver";
			String myUrl = "jdbc:mysql://" + GlobalData.DATABASE_LOCATION + ":" + GlobalData.DATABASE_PORT + "/"+ GlobalData.DATABASE_DATABASE_NAME;
			Class.forName(myDriver);
			Connection conn = DriverManager.getConnection(myUrl, GlobalData.DATABASE_USERNAME, "");
	
			String sql = "DELETE from users where id = ?";
		
			// create the java statement
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, x.id);
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
	
	
	public static boolean checkLogin(String username, String password)
	{
		
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
			String query = "SELECT * FROM users where LOWER(username) = LOWER(?)";

			// create the java statement
			PreparedStatement st = conn.prepareStatement(query);
			st.setString(1,username);

			// execute the query, and get a java resultset
			ResultSet rs = st.executeQuery();

			// iterate through the java resultset
			while (rs.next())
			{
				String storeHash = rs.getString(3);
				String storeSalt = rs.getString(4);
				byte[] get_salt = Base64.getDecoder().decode(storeSalt);
				
				if (PasswordUtils.verifyPassword(password, storeHash, get_salt)) {
					GlobalData.CurrentUser_ID = rs.getInt(1);
					GlobalData.CurrentUser_username = rs.getString(2);
					GlobalData.CurrentUser_usertype = rs.getString(4);
					st.close();
					return true;
		        } else {
		        	return false;
		        }
			
			}
			st.close();
			
		} catch (Exception e)
		{
			System.err.println("Got an exception! :"+e);
			System.err.println(e.getMessage());
		}
		return false;
	}
	
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub

	}

}
