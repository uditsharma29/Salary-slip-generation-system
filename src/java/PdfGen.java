

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Date;
 
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
 
public class PdfGen {
 private static String path;
  
    public static void pdfMaker(PaySlip paySlip,ServletContext cxt) throws Exception
    {
            
                  // path=cxt.getInitParameter("PDF-storage")+paySlip.getEmail()+".pdf";
                  path=cxt.getRealPath("/WEB-INF/classes/resources/PDF/")+"/"+paySlip.getEmail()+".pdf";
                  OutputStream file = new FileOutputStream(new File(path));
                  Document document = new Document(PageSize.A4,10,10,40,10);
                  
                  Font f3= new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
	          PdfWriter.getInstance(document, file);
                  
 
				
 
			//Inserting Table in PDF
			     PdfPTable table=new PdfPTable(4);
                             table.setWidths(new float[]{500.0f,500.0f,500.0f,500.0f});
                             
     
	                     
                             
                             
                             
                              PdfPCell cell=new PdfPCell (new Paragraph("Employee ID: ",f3));
				      cell.setHorizontalAlignment (Element.ALIGN_CENTER);
				      cell.setPaddingTop(5.0f);
                                       cell.setBorderWidthRight(0.0f);
                                       cell.setBorderWidthBottom(0.0f);
                                      cell.setPaddingBottom(5.0f);
                                      table.addCell(cell);
                                      
                                      
                                      
                                      
                                      cell=new PdfPCell (new Paragraph("Name",f3));
				      cell.setHorizontalAlignment (Element.ALIGN_CENTER);
				      cell.setPaddingTop(5.0f);
                                      cell.setColspan(2);
                                       cell.setBorderWidthRight(0.0f);
                                       cell.setBorderWidthLeft(0.0f);
                                       cell.setBorderWidthBottom(0.0f);
                                      cell.setPaddingBottom(5.0f);
                                      table.addCell(cell);
                                      
                                      
                                      
                                      cell=new PdfPCell (new Paragraph("Department",f3));
				      cell.setHorizontalAlignment (Element.ALIGN_CENTER);
				      cell.setPaddingTop(5.0f);            
                                       cell.setBorderWidthLeft(0.0f);
                                       cell.setBorderWidthBottom(0.0f);
                                      cell.setPaddingBottom(5.0f);
                                      table.addCell(cell);
                                      
                                      
                                      
                                      
                                      cell=new PdfPCell (new Paragraph(paySlip.getEmployeeId()+""));
				      cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                                       cell.setBorderWidthRight(0.0f);
                                        cell.setBorderWidthBottom(0.0f);
                                       cell.setBorderWidthTop(0.0f);
                                        cell.setPaddingBottom(5.0f);
                                      table.addCell(cell);
                                      
                                      
                                      
                                      
                                      cell=new PdfPCell (new Paragraph(paySlip.getName()+""));
				      cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                                      cell.setBorderWidth(0.0f);
                                      cell.setColspan(2);
                                      cell.setPaddingBottom(5.0f);
                                      table.addCell(cell);
                                      
                                      
                                      
                                      cell=new PdfPCell (new Paragraph(paySlip.getDepartment()+""));
				      cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                                       cell.setBorderWidthLeft(0.0f);
                                       cell.setBorderWidthTop(0.0f);
                                       cell.setBorderWidthBottom(0.0f);
                                       cell.setPaddingBottom(5.0f);
                                      table.addCell(cell);
                             
                             
                             
                             
                             
                                     
                                    
                                      cell=new PdfPCell (new Paragraph("Designation",f3));
				      cell.setHorizontalAlignment (Element.ALIGN_CENTER);
        			      cell.setPaddingTop(5.0f);                                   
                                      cell.setPaddingBottom(5.0f);
                                      table.addCell(cell);
                                      
                                      
                                      
                                      cell=new PdfPCell (new Paragraph("Scale",f3));
				      cell.setHorizontalAlignment (Element.ALIGN_CENTER);
				      cell.setPaddingTop(10.0f);
                                      cell.setPaddingBottom(4.0f);
                                      table.addCell(cell);
                                      
                                      
                                      
                                      
                                      cell=new PdfPCell (new Paragraph("No. Of Leaves",f3));
				      cell.setHorizontalAlignment (Element.ALIGN_CENTER);
				      cell.setPaddingTop(10.0f);
                                      cell.setPaddingBottom(4.0f);
                                      table.addCell(cell);
                                      
                                      
                                      
                                      cell=new PdfPCell (new Paragraph("Month",f3));
				      cell.setHorizontalAlignment (Element.ALIGN_CENTER);
				      cell.setPaddingTop(5.0f);                                      cell.setPaddingBottom(5.0f);
                                      table.addCell(cell);
                                      
                                      cell=new PdfPCell (new Paragraph(paySlip.getDesignation()));
				      cell.setHorizontalAlignment (Element.ALIGN_CENTER);
				      cell.setPaddingTop(5.0f);                                      cell.setPaddingBottom(5.0f);
                                      table.addCell(cell);
                                      
                                      
                                      
                                      cell=new PdfPCell (new Paragraph(paySlip.getPayscaleFrom()+"-"+paySlip.getPayscaleTo()));
				      cell.setHorizontalAlignment (Element.ALIGN_CENTER);
				      cell.setPaddingTop(5.0f);                                      cell.setPaddingBottom(5.0f);
                                      table.addCell(cell);
                                      
                                      
                                      
                                      
                                      cell=new PdfPCell (new Paragraph("0"));
				      cell.setHorizontalAlignment (Element.ALIGN_CENTER);
				      cell.setPaddingTop(5.0f);                                      cell.setPaddingBottom(5.0f);
                                      table.addCell(cell);
                                      
                                      
                                      
                                      cell=new PdfPCell (new Paragraph(paySlip.getMonth()+" ("+paySlip.getSession()+")"));
				      cell.setHorizontalAlignment (Element.ALIGN_CENTER);
                                      cell.setPaddingTop(5.0f);                                      cell.setPaddingBottom(5.0f);
                                      table.addCell(cell);
                                      
 
	
                                      
                                 
                               
                                      
                                    	
                                     
                                      cell=new PdfPCell (new Paragraph("Earning",f3));
                                      cell.setColspan (2);
				      cell.setHorizontalAlignment (Element.ALIGN_CENTER);
				      cell.setPaddingTop(5.0f);                                      cell.setPaddingBottom(5.0f);
                                      table.addCell(cell);	
                                      
                                      cell=new PdfPCell (new Paragraph("Deduction",f3));
                                      cell.setColspan (2);
				      cell.setHorizontalAlignment (Element.ALIGN_CENTER);
				      cell.setPaddingTop(5.0f);                                      cell.setPaddingBottom(5.0f);
                                      table.addCell(cell);
                                      
                                      
                                      
                                      Phrase ph =new Phrase("Basic Salary",f3);
                                       table.addCell(ph);
				      table.addCell(paySlip.getBasic()+"");
                                      ph =new Phrase("LWP",f3);
                                       table.addCell(ph);
                                      table.addCell("0.00");
                                      
                                      ph =new Phrase("Grade Pay",f3);
                                       table.addCell(ph);
				      table.addCell(""+paySlip.getAgp());
                                      ph =new Phrase("PF"+"  ("+paySlip.getPfDeductionPercentage()+" %)",f3);
                                       table.addCell(ph);
                                      table.addCell(paySlip.getPfDeduction()+"");
                                      
                                      
                                      ph =new Phrase("DA"+"  ("+paySlip.getDaPercentage()+" %)",f3);
                                       table.addCell(ph);
				      table.addCell(paySlip.getDa()+"");
                                      ph =new Phrase("PF Loan",f3);
                                       table.addCell(ph);
                                      table.addCell(""+paySlip.getPfLoan());
                                      
                                       ph =new Phrase("HRA"+"  ("+paySlip.getHraPercentage()+" %)",f3);
                                       table.addCell(ph);
				      table.addCell(paySlip.getHra()+"");
				      ph =new Phrase("HRent",f3);
                                       table.addCell(ph);
                                      table.addCell(""+paySlip.getHouseRentDeduction());
                                      
                                      ph =new Phrase("MA",f3);
                                       table.addCell(ph);
				      table.addCell(paySlip.getMa()+"");
				      ph =new Phrase("Prof.Tax",f3);
                                       table.addCell(ph);
                                      table.addCell(""+paySlip.getpTax());
                                      
                                      
                                      table.addCell("");
                                      table.addCell("");
                                       ph =new Phrase("Income Tax",f3);
                                       table.addCell(ph);
                                      table.addCell(""+paySlip.getiTax());
                                      
                                      
                                     
                                       table.addCell("");
				      table.addCell("");
                                      ph =new Phrase("Welfare Fund",f3);
                                       table.addCell(ph);
				    
                                      table.addCell("0.00"); 
                                      
                                       ph =new Phrase("Total Gross",f3);
                                       table.addCell(ph);
                                      
				      table.addCell(""+paySlip.getTotal());
                                      
                                       ph =new Phrase("Total Deduction",f3);
                                       table.addCell(ph);
				     
                                      table.addCell(""+paySlip.getTotalDeduction());
                                      
                                      
                                      cell=new PdfPCell (new Paragraph("Total Gross - Total Deduction = Net Salary",f3));
                                      cell.setColspan (2);
				      cell.setPaddingTop(5.0f);                                      cell.setPaddingBottom(5.0f);
                                      table.addCell(cell);
                                      
                                      
                                      cell=new PdfPCell (new Paragraph(""+paySlip.getNetPay()));
                                      cell.setColspan (2);
				      cell.setHorizontalAlignment (Element.ALIGN_CENTER);
				      cell.setPaddingTop(5.0f); 
                                      cell.setPaddingBottom(5.0f);
                                      table.addCell(cell);
                                      
                                      cell=new PdfPCell (new Paragraph("Amount in words(after rounding) ",f3));
                                      cell.setColspan (2);
				      cell.setPaddingTop(5.0f);   
                                      cell.setPaddingBottom(5.0f);
                                      table.addCell(cell);
                                      
				     cell=new PdfPCell (new Paragraph(" "+paySlip.getAmountInWords().toUpperCase()));
                                      cell.setColspan (2);
				      cell.setPaddingTop(5.0f);    
                                      cell.setPaddingBottom(5.0f);
                                      table.addCell(cell);
                                      
                                      
 
			 //Text formating in PDF
	   /*             Chunk chunk=new Chunk("Welecome To Java4s Programming Blog...");
					chunk.setUnderline(+1f,-2f);//1st co-ordinate is for line width,2nd is space between
					Chunk chunk1=new Chunk("Php4s.com");
					chunk1.setUnderline(+4f,-8f);
					chunk1.setBackground(new BaseColor (17, 46, 193));  */    
 
			 //Now Insert Every Thing Into PDF Document
                                      
                          
		         document.open();//PDF document opened........			       
                       
                         /*  Chunk chunk=new Chunk("DEVI AHILYA VISHWAVIDYALAYA, INDORE");*/
			  Font f= new Font(Font.FontFamily.TIMES_ROMAN, 17, Font.BOLD);
                            Font f1= new Font(Font.FontFamily.TIMES_ROMAN, 13, Font.BOLD);
                          Paragraph p=new Paragraph("DEVI AHILYA VISHWAVIDYALAYA,INDORE",f);
                         Paragraph p1=new Paragraph("INSTITUTE OF ENGINEERING & TECHNOLOGY",f1);
			 p1.setAlignment(Element.ALIGN_CENTER);
                        // p.add(chunk);
                         
                       /*  Chunk chunk1=new Chunk("Institute of Engineering & Technology");
			 p=new Paragraph();
                        
			 p.setAlignment(Element.ALIGN_CENTER);
                         p.add(chunk1);*/
                         
                         final String resourcesPath="resources/images/Davv-logo.png";
                          Image image1 = Image.getInstance(PdfGen.class.getResource(resourcesPath));
                         // document.add(image1);
                         // image1.scaleAbsolute(50f, 50f);
                          image1.scalePercent(20.0f);
                          
                          
                          
                          cell = new PdfPCell(image1, true);
                          cell.setBorderWidth(0.0f);
                          
                          
                          
                          
                          
                           PdfPCell cell1 = new PdfPCell();
                           cell1.setPaddingTop(5.0f);
                           cell1.addElement(p);
                           cell1.addElement(p1);
                           cell1.setBorderWidth(0.0f);
                         
                           PdfPTable table1 = new PdfPTable(2);
                           
                           table1.setWidths(new float[]{130.0f,850.0f});
                           table1.addCell(cell);
                            table1.addCell(cell1);
                            document.add(table1);
                            
                            Chunk chunk=new Chunk(" ");
                            
                         p=new Paragraph();
			 p.setAlignment(Element.ALIGN_CENTER);
                         p.add(chunk);
			 document.add(p);
                          
                       
                         
                         
                         document.add(Chunk.NEWLINE);  
                         document.add(Chunk.NEWLINE);  
                         document.add(Chunk.NEWLINE);  
                         
                    
                         
                              
                         
                         chunk=new Chunk("PAYSLIP",f1)   ;
                         chunk.setUnderline(+1f,-2f);
			 p=new Paragraph();
			 p.setAlignment(Element.ALIGN_CENTER);
                         p.add(chunk);
                        
			 document.add(p);
                         
                           document.add(Chunk.NEWLINE);  
                         
                    
                       
 
					document.add(table);
 /*
					document.add(chunk);
					document.add(chunk1);
 
					document.add(Chunk.NEWLINE);   //Something like in HTML :-)							    
 
       				document.newPage();            //Opened new page
					document.add(list);            //In the new page we are going to add list*/
                       
                         document.add(Chunk.NEWLINE);  
                         document.add(Chunk.NEWLINE);  
                         chunk=new Chunk("Note!! This is computer generated Salary Receipt does not require signature.",f1);
			 p=new Paragraph();
			 p.setAlignment(Element.ALIGN_CENTER);
                         p.add(chunk);
			 document.add(p);
		         document.close();
 
			             file.close();
 
                    

            
 
        
      
    }
            
}