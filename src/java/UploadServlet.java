import java.io.*;
import java.sql.Connection;
import java.util.*;
import javax.servlet.RequestDispatcher;
 
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.output.*;

@MultipartConfig
public class UploadServlet extends HttpServlet {
   
   
   private boolean isMultipart;
   String insertCase="";
   private String filePath;
   private String relativePath;
   private int maxFileSize = 50 * 1024;
   private int maxMemSize = 50 * 1024;
   private File file ;

   public void init( ){
      // Get the file location where it would be stored.
      relativePath =   getServletContext().getInitParameter("file-upload"); 
      filePath=getServletContext().getRealPath(relativePath);
   }
   public void doPost(HttpServletRequest request, 
               HttpServletResponse response)
              throws ServletException, java.io.IOException {
      // Check that we have a file upload request
      isMultipart = ServletFileUpload.isMultipartContent(request);
      response.setContentType("text/html");
      java.io.PrintWriter out = response.getWriter( );
      if( !isMultipart ){
         out.println("<html>");
         out.println("<head>");
         out.println("<title>Servlet upload</title>");  
         out.println("</head>");
         out.println("<body>");
         out.println("<p>No file uploaded</p>"); 
         out.println("</body>");
         out.println("</html>");
         return;
      }
      DiskFileItemFactory factory = new DiskFileItemFactory();
      // maximum size that will be stored in memory
      factory.setSizeThreshold(maxMemSize);
      // Location to save data that is larger than maxMemSize.
      factory.setRepository(new File("c:\\temp"));

      // Create a new file upload handler
      ServletFileUpload upload = new ServletFileUpload(factory);
      // maximum file size to be uploaded.
      upload.setSizeMax( maxFileSize );
      String month="not fetching",session="not fetching";

      try{ 
      // Parse the request to get file items.
      List fileItems = upload.parseRequest(request);
	
      // Process the uploaded file items
      Iterator i = fileItems.iterator();

      out.println("<html>");
      out.println("<head>");
      out.println("<title>Servlet upload</title>");  
      out.println("</head>");
      out.println("<body>");
      while ( i.hasNext () ) 
      {
         FileItem fi = (FileItem)i.next();
         
         if ( !fi.isFormField () )	
         {
            // Get the uploaded file parameters
            String fieldName = fi.getFieldName();
            String fileName = getServletContext().getInitParameter("filename") ;
            String contentType = fi.getContentType();
            boolean isInMemory = fi.isInMemory();
            long sizeInBytes = fi.getSize();
            // Write the file
            if( fileName.lastIndexOf("\\") >= 0 ){
               file = new File( filePath + 
               fileName.substring( fileName.lastIndexOf("\\"))) ;
            }else{
               file = new File( filePath + 
               fileName.substring(fileName.lastIndexOf("\\")+1)) ;
            }
            fi.write( file ) ;
            out.println("Uploaded Filename: " + fi.getName() + "<br>");
         }
         else
         {
                 /*  out.print(fi.getFieldName());
         out.print(fi.getString());
         out.print("<br>");*/
                if (fi.getFieldName().equals("month"))
                    month = fi.getString();
                if (fi.getFieldName().equals("session"))
                    session = fi.getString();
                if (fi.getFieldName().equals("insertCase"))
                    insertCase = fi.getString();
                
                
              
         }
      }
      out.println("</body>");
      out.println("</html>");
      
      if("overWrite".equals(insertCase))
      {
          Connection conn= new GetConnection(getServletContext()).returnConnection();
          DatabaseQueryHandler.deleteEntries(conn, session, month);
          new DatabaseManagement(getServletContext()).insert(session,month,out);
      }    
      else if("insert".equals(insertCase))
      {
          new DatabaseManagement(getServletContext()).insert(session, month, out);
      }
      response.setHeader("Cache-Control","no store,no-cache,must-revalidate");
      response.setHeader("Cache-Control","post-check=0,pre-check=0");
      response.setHeader("Pragma","no-cache");
      request.setAttribute("month",month);
      request.setAttribute("session",session);
      request.getRequestDispatcher("email.jsp").forward(request, response);
     
   }catch(NullPointerException ex) {
            request.setAttribute("alert","true");
            request.setAttribute("alertMsg","CsvFile not Found ,Please set Proper Path in web.xml");
            RequestDispatcher d2 = request.getRequestDispatcher("index.jsp");
            d2.forward(request, response);out.print(ex.getMessage());
   }
   catch(Exception ex) {
            request.setAttribute("alert","true");
            request.setAttribute("alertMsg",ex.getMessage());
            RequestDispatcher d2 = request.getRequestDispatcher("index.jsp");
            d2.forward(request, response);out.print(ex.getMessage());
   }
   }
   public void doGet(HttpServletRequest request, 
                       HttpServletResponse response)
        throws ServletException, java.io.IOException {
        
        throw new ServletException("GET method used with " +
                getClass( ).getName( )+": POST method required.");
   } 
}