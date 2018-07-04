import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class Drive{
		public Connection getconnection() {
			Connection ct=null;		
			try {
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
				String url="jdbc:sqlserver://localhost:1433;databaseName=XK";
	            String user="sa";
	            String password="20141314*";
	            ct=DriverManager.getConnection(url,user,password);	   
			}catch(Exception e) {
				
			}finally{
				
			}
			return ct;
		}
	  	
}