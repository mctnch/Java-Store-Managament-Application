package M;

public class UserDB
{
	public int id;
	public String username;
	public String password;
	public String usertype;
	
	public UserDB()
	{
		
	}
	
	public UserDB(int id)
	{

		this.id = id;
	}
	
	public UserDB(int id , String username , String usertype)
	{
		this.id = id;
		this.username = username;
		this.usertype = usertype ;
	}
	
	public UserDB(int id , String username , String password , String usertype)
	{
		this.id = id;
		this.username = username;
		this.password = password;
		this.usertype = usertype ;
	}

}
