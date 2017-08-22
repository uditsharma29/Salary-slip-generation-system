
import com.darwinsys.spdf.PDF;
import java.io.PrintWriter;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author aticus
 */
   

import java.io.*;
import java.util.*;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import javax.servlet.ServletContext;


public class SendMailAttachment implements Runnable
{
    Session session;
    String[] to;
    ServletContext context;
    static Exception e=null;
    static int status;
    String path;
    String month;
    String paySession;
    DatabaseManagement database;
    
    
    
    public SendMailAttachment(final ServletContext context,String[] to,String month,String session)
    
    {
     this.paySession=session;
     this.month=month;
     this.to=to;   
     Properties props = new Properties();
     database=new DatabaseManagement(context);
     
    
        
     props.put("mail.smtp.host", "smtp.gmail.com");
     //below mentioned mail.smtp.port is optional
     props.put("mail.smtp.port", "587");		
     props.put("mail.smtp.auth", "true");
     props.put("mail.smtp.starttls.enable", "true");
     
     
     this.context=context;
        

    this.session = Session.getInstance(props,new javax.mail.Authenticator()
    {
  	  protected PasswordAuthentication getPasswordAuthentication() 
  	  {
  	 	 return new PasswordAuthentication(context.getInitParameter("sendingEmailAdd"),context.getInitParameter("sendingEmailPass"));
  	  }
   });
    }
    
   
    
   

    public void prepareMessage()
    {
        for(int i=0;i<to.length;i++)
        {
         try
           {
           e=null;
           MimeMessage message = new MimeMessage(session);
           message.setFrom(new InternetAddress(this.context.getInitParameter("sendingEmailAdd")));
           message.addRecipient(Message.RecipientType.TO,new InternetAddress(to[i]));
           message.setSubject("PaySlip");
       
         
 
             
           
         // Create the message body part
         BodyPart messageBodyPart = new MimeBodyPart();
 
         // Fill the message
         messageBodyPart.setText("This Mail contains Payslip Pdf Attachment For "+month+"("+database.tablename(paySession)+")");
          
         // Create a multipart message for attachment
         Multipart multipart = new MimeMultipart();
 
         // Set text message part
         multipart.addBodyPart(messageBodyPart);
 
         // Second part is attachment
         path=context.getRealPath("/WEB-INF/classes/resources/PDF/");
         messageBodyPart = new MimeBodyPart();
         String filename = path+"/"+to[i]+".pdf";
         DataSource source = new FileDataSource(filename);
         messageBodyPart.setDataHandler(new DataHandler(source));
         messageBodyPart.setFileName("Payslip"+month+"("+database.tablename(paySession)+")"+".pdf");
         multipart.addBodyPart(messageBodyPart);
 
         // Send the complete message parts
         message.setContent(multipart);
 
       
       
       Transport.send(message);
       status+=1;
 
    }
    catch(Exception e)
    {
        this.e=e;
       
    }
       
    }
       
    }
    @Override
    public void run() 
    {
        prepareMessage();
    }
}
   
