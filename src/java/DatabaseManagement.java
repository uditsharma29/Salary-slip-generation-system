
import java.sql.Connection;
import javax.servlet.ServletContext;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author aticus
 */
public class DatabaseManagement
{
    GetConnection getCon;
    Connection con=null;
    ServletContext cxt;
    String query;
    String filePath;
    public DatabaseManagement(ServletContext cxt)
    {
        this.cxt=cxt;
        getCon=new GetConnection(cxt);
       
    }
     
    public void insert(String tablename,String month,PrintWriter out) throws Exception
    {
        
         con=getCon.returnConnection();
          int k = 0;
          filePath=cxt.getRealPath("/WEB-INF/classes/resources/CsvFolder/");
        //  String csvFile = "C:\\Users\\aticus\\Documents\\NetBeansProjects\\PaySlip\\src\\java\\Book1.csv";
            String csvFile = filePath+cxt.getInitParameter("filename");

          BufferedReader br = null;
          String line = "";
          String cvsSplitBy = ",";
             
            
             br = new BufferedReader(new FileReader(csvFile));
             int a=501;
             int i=0;
             PreparedStatement ps=con.prepareStatement("insert into "+tablename+" values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            while ((line = br.readLine()) != null) 
            {
                /*if(i==12){
                    i++;
                    continue;
                }*/
	        // use comma as separator
                 String[] country = line.split(cvsSplitBy);
                 for(i=0;i<country.length;i++)
                 {      
                       if(i<12)
                       ps.setString(i+1,country[i]);
                       
                       else if(i>12)
                       ps.setString(i, country[i-1]);
                           
                 }
                 ps.setInt(i+1,a++);
                 ps.setString(i+2,month);
                  k=ps.executeUpdate();
            
            }
            ps=con.prepareStatement("update "+tablename+" set "+tablename+".emp_id=(select facultyinfo.emp_id from facultyinfo where "+tablename+".name=facultyinfo.name) where month='"+month+"'");
            ps.executeUpdate();
            con.close();
            
       
    }
    
     public String tablename(String session)
   {
       String sessionFrom =session.substring(5,9);
       String sessionTo =session.substring(9,13);
       return sessionFrom+"-"+sessionTo;

   }
    
   public void sendEmail(String session,String month) throws Exception
   {
        
        con=getCon.returnConnection();
        PaySlip paySlip=new PaySlip();
        PreparedStatement ps=con.prepareStatement("select parameter from sal_parameter where attribute='da' ");
        ResultSet rs= ps.executeQuery();
        rs.first();
                paySlip.setDaPercentage(rs.getDouble("parameter"));
        
        ps=con.prepareStatement("select parameter from sal_parameter where attribute='hra'");
        rs=ps.executeQuery();
        rs.first();
             paySlip.setHraPercentage(rs.getDouble("parameter"));
       
        
        
        ps=con.prepareStatement("select parameter from sal_parameter where attribute='pf_ded'");
        rs= ps.executeQuery();
        if(rs.first())
               paySlip.setPfDeductionPercentage(rs.getDouble("parameter"));
        
        
                
                
                
                
                
        int i=0;
        
        String query="select "+session+".*,facultyinfo.dep, facultyinfo.email, facultyinfo.designation,scale.payscale_from,scale.payscale_to from "+session+
                " INNER JOIN facultyinfo,scale where "+session+".emp_id = facultyinfo.emp_id and facultyinfo.designation=scale.designation and "
                +session+".month ='"+month+"'";
        
        
         ps=con.prepareStatement(query);
         rs= ps.executeQuery();
        
          
        rs.first();
        while(!rs.isAfterLast())
        {
            paySlip=new PaySlip();
            paySlip.setName(rs.getString("name"));
            paySlip.setBasic(rs.getDouble("basic"));
            paySlip.setAgp(rs.getDouble("agp"));
            paySlip.setActualBasic(rs.getDouble("actual_basic"));
            paySlip.setMa(rs.getDouble("ma"));
            paySlip.setTotal(rs.getDouble("total"));
            paySlip.setInstPF(rs.getDouble("pf_inst"));
            paySlip.setPfLoan(rs.getDouble("pf_loan_instal"));
            paySlip.setHouseRentDeduction(rs.getDouble("ded_hrent"));
            paySlip.setpTax(rs.getDouble("p_tax"));
            paySlip.setiTax(rs.getDouble("i_tax"));
            paySlip.setNetPay(rs.getDouble("net_pay"));
            paySlip.setEmployeeId(rs.getString("emp_id"));
            paySlip.setMonth(rs.getString("month"));
            paySlip.setSession(session);
            paySlip.setDa(rs.getDouble("da"));
            paySlip.setHra(rs.getDouble("hra"));
            paySlip.setPfDeduction(rs.getDouble("pf_ded"));
            paySlip.setDesignation(rs.getString("designation"));
            paySlip.setSession(tablename(session));
            paySlip.setDepartment(rs.getString("dep"));
            paySlip.setPayscaleFrom(rs.getString("payscale_from"));
            paySlip.setPayscaleTo(rs.getString("payscale_to"));
            paySlip.setEmail(rs.getString("email"));
            paySlip.setLWP(0.0);
            paySlip.setWelfarefund(0.0);
            paySlip.setTotalDeduction();
            
                    
                    PdfGen.pdfMaker(paySlip,cxt);
            
            rs.next();
            
        }
            
        con.close();
        
       
   }
   
    
}
