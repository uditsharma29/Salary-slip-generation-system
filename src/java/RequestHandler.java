import java.io.IOException;
import java.sql.*;
import java.io.*;
import java.sql.DriverManager;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;

//Imported lib for the purpose of the file upload
import org.apache.commons.fileupload.servlet.ServletFileUpload;


/**
 *
 * @author lifeisshubh
 */
@MultipartConfig
public class RequestHandler extends HttpServlet {
    

    
    
//    Variable for database connectivity
//    Data is copied from the Deployment Descriptor
   
    Connection connection;
    
    
    @Override
    public void init(){
      
     
      
      
    }
    @Override
    public void doPost(HttpServletRequest request, 
               HttpServletResponse response)
              throws ServletException, IOException {
      // Check that we have a file upload request
      PrintWriter out = response.getWriter();
      try{
          GetConnection getCon=new GetConnection(getServletContext());
      connection=getCon.returnConnection();
      
      response.setContentType("text/html");
      
      String noFile = "";
      DatabaseManagement dm= new DatabaseManagement(getServletContext());
      noFile = (String)request.getParameter("noFile");
      String tableName = (String)request.getParameter("session");
      String month = (String)request.getParameter("month");
     
      response.setHeader("Cache-Control","no store,no-cache,must-revalidate");
      response.setHeader("Cache-Control","post-check=0,pre-check=0");
      response.setHeader("Pragma","no-cache");
      
//      Query if no file is attached to the system
      if( "true".equals(noFile) )
      {
          boolean dispatching = false;
          try{
         boolean isExist = false;
          DatabaseMetaData dbm = connection.getMetaData();
          // check if "tableName" table is there
          ResultSet tables = dbm.getTables(null, null,tableName, null);
         if (tables.next()) 
         {
            //Table exists, Now Checking for the existance of the month
        
            Statement st = connection.createStatement();
             String query = "SELECT * FROM `"+tableName + "` WHERE "+" month=\'"+month+"\'";
            ResultSet rs = st.executeQuery(query);
         
              if(rs.next())
                {
             
                // Entry for the month exists
                      dispatching = true;
                }
       }
        }catch(Exception e){
            out.write("{\"case\":\"alert\",\"alertMsg\":\""+e.getMessage()+"\"}");
        }
        if(dispatching)
        {
            out.write("{\"case\":\"email\"}");
        }
        else{
           
            out.write("{\"case\":\"alert\",\"alertMsg\":\""+"Entry For "+month+"  "+new DatabaseManagement(getServletContext()).tablename(tableName)+" does not exist.    Please Try Again"+"\"}");
        }
      }
      else{
          
//       If file is send by the user
         try{
                boolean isExist = false;
                DatabaseMetaData dbm = connection.getMetaData();
                // check if "tableName" table is there
                ResultSet tables = dbm.getTables(null, null,tableName, null);
                
//                Table exist
                if (tables.next()){
                    //Table exists, Now Checking for the existance of the month

                    Statement st = connection.createStatement();
                    String query = "SELECT * FROM `"+tableName + "` WHERE "+" month=\'"+month+"\'";
                    ResultSet rs = st.executeQuery(query);

                if(rs.next()){
                      out.write("{\"case\":\"overWrite\",\"alertMsg\":\""+"Entries for "+month+"  "+new DatabaseManagement(getServletContext()).tablename(tableName)+" Already Exist Due You Want To overWrite Previous Entries"+"\"}");
                    // Entry for the month exists
                  //  request.getRequestDispatcher("Sure").forward(request, response);
                }
                else{
//                    entry for month does not exist
                    
//                    send the requests to update servlet
//                    no overlapping of data
//                    since new month in already exist table
                    
                     out.write("{\"case\":\"insert\"}");
                    
                }
                    }
                else{
                    DatabaseQueryHandler.createTable(connection, tableName);
                    dm.insert(tableName,month,out);
                    out.write("{\"case\":\"email\"}");
                }
            }catch(Exception e){
            out.write("{\"case\":\"alert\",\"alertMsg\":\""+e.getMessage()+"\"}");
            }
            


          }
      }catch(Exception e)
      {
            out.write("{\"case\":\"alert\",\"alertMsg\":\""+"DataBase Connection Not Estabilised"+"\"}");
      }
      
      
      
   } 


}