package M;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import common.GlobalData;

public class CompanyInfoManager
{

	public static CompanyInfoDB getCompanyInfo()
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
			String query = "SELECT * FROM company_info";

			// create the java statement
			Statement st = conn.createStatement();

			// execute the query, and get a java resultset
			ResultSet rs = st.executeQuery(query);

			// iterate through the java resultset
			while (rs.next())
			{
				CompanyInfoDB cc = new CompanyInfoDB();
				cc.id = rs.getInt("id");
				cc.company_name = rs.getString("company_name");
				cc.address_no = rs.getString("address_no");
				cc.alley =  rs.getString("alley");
				cc.sub_district = rs.getString("sub_district");
				cc.district = rs.getString("district");
				cc.province = rs.getString("province");
				cc.postal_code = rs.getInt("postal_code");
				cc.phone = rs.getString("phone");
				cc.email = rs.getString("email");
				st.close();
				return cc;
				
			}
		
		} catch (Exception e)
		{
			System.err.println("Got an exception! :"+e);
			System.err.println(e.getMessage());
		}

		return null;
	}
	
	public static void editCompanyInfo(CompanyInfoDB x)
	{
		try
		{
			// create our mysql database connection
			String myDriver = "com.mysql.jdbc.Driver";
			String myUrl = "jdbc:mysql://" + GlobalData.DATABASE_LOCATION + ":" + GlobalData.DATABASE_PORT + "/"+ GlobalData.DATABASE_DATABASE_NAME;
			Class.forName(myDriver);
			Connection conn = DriverManager.getConnection(myUrl, GlobalData.DATABASE_USERNAME, "");
	
			String sql = "UPDATE company_info SET company_name=?,address_no=?,alley=?,sub_district=?,district=?,province=?,postal_code=?,phone=?,email=?";
		
			// create the java statement
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1,x.company_name);
			st.setString(2,x.address_no);
			st.setString(3,x.alley);
			st.setString(4,x.sub_district);
			st.setString(5,x.district);
			st.setString(6,x.province);
			st.setInt(7,x.postal_code);
			st.setString(8,x.phone);
			st.setString(9,x.email);
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
	
	
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub

	}

}
