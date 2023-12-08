import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
	
	static boolean connect() throws SQLException
	{
	try
	{
		
	Class.forName("com.mysql.cj.jdbc.Driver");
	Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/netbank","root","root");
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	
	return true;
}
}
