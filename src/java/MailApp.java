import java.io.*;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.*;
import javax.servlet.http.*;
//import org.apache.jasper.tagplugins.jstl.ForEach;

public class MailApp extends HttpServlet {
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        boolean flag=true;
        try
        {
        response.setContentType("text/html;charset=UTF-8");
        
        String send = "", subject, message, user, pass;
        String month = request.getParameter("month");
        String session = request.getParameter("session");
        String []to = request.getParameterValues("mail");
        SendMailAttachment.status=0;
      
        DatabaseManagement dm = new DatabaseManagement(getServletContext());
        
            dm.sendEmail(session, month);
        
        Runnable r = new SendMailAttachment(getServletContext(),to,month,session);
        Thread t=new Thread(r);
        t.start();
        
        if(flag)
        out.write("{\"alert\":false}");
        
             
     
        }
        catch (FileNotFoundException ex) {
            flag=false;
             out.write("{\"alert\":true,\"alertMsg\":\""+ex.getMessage()+"\"}");
            //out.write("{\"alert\":true,\"alertMsg\":\""+"Pdf file not Created check pdf folder path in web.xml OR file is been used by some other Application."+"\"}");
        }
        catch (Exception ex) 
        {
            String msg=ex.getMessage();
            msg=msg.substring(0,27)+" "+msg.substring(29);
            out.write("{\"alert\":true,\"alertMsg\":\""+msg+"\"}");
        }
    }   
}