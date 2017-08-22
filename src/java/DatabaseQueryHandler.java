import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;


public class DatabaseQueryHandler  {
    
    static void deleteEntries(Connection conn,String tableName, String month) throws Exception{
        
        Statement st = conn.createStatement();
        String query = "DELETE FROM "+tableName+" where month='"+month+"'";
        st.execute(query);
        
    }
    

    static void createTable(Connection conn, String name) throws SQLException{
            Statement st = conn.createStatement();
            String query =  "create table `" +name+"` (sno INT(5),name varchar(30),basic double DEFAULT '0.0',agp double DEFAULT '0.0',actual_basic double DEFAULT '0.0',da double DEFAULT '0.0',hra double DEFAULT '0.0',ma double DEFAULT '0.0',total double DEFAULT '0.0',pf_ded double DEFAULT '0.0',pf_inst double DEFAULT '0.0',pf_loan_instal double DEFAULT '0.0',ded_hrent double DEFAULT '0.0',p_tax double DEFAULT '0.0',i_tax double DEFAULT '0.0', net_pay double DEFAULT '0.0', emp_id INT(10),month varchar(6), PRIMARY KEY(name,month));";
            st.execute(query);
            
    }
    
    static void dropTable(Connection conn, String name)throws Exception{
        
        Statement st = conn.createStatement();
        String query = "Drop Table "+name;
        st.execute(query);
        
        
    }
    
    static void InsertData(Connection conn,String tableName,String fileName, String emp_id, String month) throws FileNotFoundException, SQLException, IOException{
        BufferedReader fl = new BufferedReader(new FileReader(fileName));
        Statement st = conn.createStatement();
        String line = "";
        String[] arr;
        String query = "";
        
        while((line = fl.readLine()) != null){
             arr = line.split(",");
             query = "Insert into "+tableName+" Values('"+arr[0]+"','"+arr[1]+"','"+arr[2]+"','"+arr[3]+"')";
             st.execute(query);
        }
        
    }

}