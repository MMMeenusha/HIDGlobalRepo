package Utils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class sql extends TestData {
	
	public sql() throws IOException {
		super();
		// TODO Auto-generated constructor stub
	}

	Connection con;
	Statement stmt;
	ResultSet rs;
	List<List<Object>> result = new ArrayList<List<Object>>();
	
	public List<List<Object>> connect_DB(String query) throws Exception
	{	
		Class.forName("oracle.jdbc.driver.OracleDriver");
		con=DriverManager.getConnection(getProp("dbURL"),getProp("dbUname"),getProp("dbPword"));  
		stmt=con.createStatement();  
		rs=stmt.executeQuery(query);  
		while(rs.next()) 
		{
			int columncount = rs.getMetaData().getColumnCount();
			List row = new ArrayList();			
			for(int i=1;i<=columncount;i++)
			{
				row.add(rs.getObject(i));
			}
			result.add(row);
		}
			
			//System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3));  
		con.close();
		return result;  
	}

}
