/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.servlet.ServletContext;

/**
 *
 * @author aticus
 */
public class GetConnection 
{
    String localhost;
    String port;
    String databaseName;
    String username;
    String password;
    Connection con;
   
    public GetConnection(ServletContext cxt)
    {
        localhost=cxt.getInitParameter("localhost");
        port=cxt.getInitParameter("port");
        databaseName=cxt.getInitParameter("databaseName");
        username=cxt.getInitParameter("databaseUserName");
        password=cxt.getInitParameter("databasePassword");
        
    }
    public Connection returnConnection() throws ClassNotFoundException, SQLException
    {
        
       
           Class.forName("com.mysql.jdbc.Driver");
                  con = DriverManager.getConnection("jdbc:mysql://"+localhost+":"+port+"/"+databaseName,username,password);
        
        return con;
    }
    
}
