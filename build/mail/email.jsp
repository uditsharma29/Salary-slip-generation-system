<%-- 
    Document   : email
    Created on : 27 Apr, 2016, 4:25:01 PM
    Author     : Udit Sharma
--%>
<%@ page import="java.sql.*" %>
<% Class.forName("com.mysql.jdbc.Driver"); %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
            String month = request.getParameter("month");
            String  sessionlocal = request.getParameter("session");
            if(month==null)
            {
                month = (String)request.getAttribute("month");
                sessionlocal = (String)request.getAttribute("session");
            }
%>
<!DOCTYPE html>
<html>
<head>
    <title id='tit'>
    Send Email</title>
    
    <style>
        #mailing 
 {
    width: 300px;
    height: 300px;
    overflow: scroll;
      border-style: solid;
    border-width: 1px;
     
        }
        #outer {
	       align-self: center;
            margin-top: 70px;
            width: 300px;
            margin: auto;
            padding: 70px;
            
}
        #heading {
	   height: 200px;
            background-color: beige;
            position: relative;
}   
        img{
            height: 150px;
            width: 150px;
        }
        #check
        {
            align-items: center;
        }
        .msg
        {
            text-align: center;
        }
        h1
        {
            vertical-align: 50px;
            display: inline;
            text-align: center;
            padding-left: 20px;
            font-size: 50px;
        }
        #link{
            float:right;
        }
        
    </style>
    </head>
    <body onload="disablebutton()">
        <%  response.setHeader("Cache-Control","no store,no-cache,must-revalidate");
            response.setHeader("Cache-Control","post-check=0,pre-check=0");
            response.setHeader("Pragma","no-cache");
            
            String sessionlocalFrom=sessionlocal.substring(5,9);
            String sessionlocalTo = sessionlocal.substring(9,13);
             %>
        <form action="mail" method="get">
        <div id="heading">
            <img src="Davv-logo.png">
            <h1>Emailing System</h1>
            <a id='link' href="index.jsp">HOME</a>
           
        </div>
             <p class="msg" id="fail">Sending Failed!!!</p>
            <div class="msg"><span id="span" class="msg">Sending  </span> <progress class="msg" id='progressbar' value="0" max='100'></progress><span id="percentage"></span></div>
            <p class="msg"> Select employees for whom the E-mail has to be sent for <%= month %> (<%= sessionlocalFrom %>-<%= sessionlocalTo %>)</p>
        <div id="outer">
            Select All <input type="checkbox"  name="select" id="check" onclick="checkAll()"><br>
        <div id="mailing">
       
            
            
             <% 
                 
                String name, email="";
                try{
            Class.forName("com.mysql.jdbc.Driver"); 
            String DB_URL = "jdbc:mysql://localhost:3306/payslip";
            String USER = pageContext.getServletContext().getInitParameter("databaseUserName");
            String PASS = pageContext.getServletContext().getInitParameter("databasePassword");
           Connection conn = DriverManager.getConnection(DB_URL,USER,PASS);
      if (conn==null){
          System.out.println("connection not created");
      }
       

            Statement statement = conn.createStatement();

            
           
              ResultSet resultset = 
                statement.executeQuery(" select facultyinfo.name, facultyinfo.email from "+sessionlocal+" inner join facultyinfo where "+sessionlocal+".name = facultyinfo.name and "+sessionlocal+".month ='" + month+"' order by facultyinfo.dep"); 
                 
            while(resultset.next()) {
            email= resultset.getString("facultyinfo.email");
            name = resultset.getString("facultyinfo.name");
            
             %>
             <input type="checkbox"  onclick="disablebutton()" name="mail" value= <%=email%>> <%=name %><br>
			<%}}
                        catch(Exception e)
                        {
                            out.print(e.getMessage());
}%>
<input type="hidden" name="month" value="<%= month%>">
<input type="hidden" name="session" value="<%= sessionlocal%>">

                
        </div>
            <br>
           
            <input type="button" id="mysubmit" name="submit" onclick="ajaxExample()" value="Send E-Mail">
        </div>
        
                

            </form>
       
            
                
          
            
    </body>
     <script>
            var mailsent;
            var xhttp;
            var span=document.getElementById("span");
            span.style.visibility="hidden";
            var myvar;
            var limit;
            var count;
            var timeout;
            var progress= progress=document.getElementById("progressbar");
            progress.style.visibility="hidden";
            var fail=document.getElementById("fail");
            fail.style.visibility="hidden";
           
            var interval;
           function ajaxExample()
           {
                limit=0;
                fail.style.visibility="hidden";
                count=0;
               console.log("ajaxExample");
               xhttp=new XMLHttpRequest();
               document.getElementById('mysubmit').disabled='true';
               mailsent=document.getElementsByName("mail");
               var string="month=<%=month%>&session=<%=sessionlocal%>&";
               for(var i=0;i<mailsent.length;i++)
               {
                   if(mailsent[i].checked==true)
                   {    
                    string+="mail="+mailsent[i].value;
                    if(i<mailsent.length-1)
                        string+="&";
                    count=count+1;
                     }
               }
              
               xhttp.onreadystatechange=function()
               {
                    
                    if(xhttp.readyState==4 && xhttp.status==200)
                    {
                         var msg = xhttp.responseText; 
                         console.log("knowStatus");
                         console.log(msg);
                             var obj=JSON.parse(msg);
                             if(obj.alert===true)
                            {    
                                 alert(obj.alertMsg);
                                 document.getElementById('mysubmit').removeAttribute('disabled');
                                 
                                
                            }
                            else
                            {
                                 interval=window.setInterval(knowStatus,1000);
                                 document.getElementById('mysubmit').disabled=true;
                            }

                    }
               }
               xhttp.open("post","mail",true);
               xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
               xhttp.send(string);
           }
           function finish()
           {
               if(confirm("Mails   Sent\n\nDo you Want to be Redirected to Index Page?"))
               {
                                        window.location.assign("index.jsp");
                                        span.style.visibility="hidden";
                                    progress.style.visibility="hidden";
                                    document.getElementById("percentage").style.visibility="hidden";
                                    document.getElementById('mysubmit').removeAttribute('disabled');
                                    }
           }
           
           function knowStatus()
           {
               
               xhttp=new XMLHttpRequest();
                    xhttp.onreadystatechange=function()
                    {
                        if(xhttp.readyState==4 && xhttp.status==200)
                        {    var msg=xhttp.responseText; 
                            console.log(msg);
                             var obj=JSON.parse(msg);
                             if(obj.alert===true)
                        {    
                             window.clearInterval(interval);   
                             alert(obj.alertMsg);
                                span.style.visibility="hidden";
                                    progress.style.visibility="hidden";
                             document.getElementById('mysubmit').removeAttribute('disabled');
                              fail.style.visibility="visible";
                             
                        }
                        if(obj.alert===false){
                                
                                progress.style.visibility="visible";
                                span.style.visibility="visible";
                                myvar=obj.status;
                                span.value="sending"+myvar;
                                progress.value=Math.floor((myvar/count) * 100);
                                document.getElementById("percentage").innerHTML= " &nbsp;&nbsp; "+Math.floor((myvar/count) * 100)+"  % &nbsp;completed";
                                if(count==myvar)
                                {
                                    window.clearInterval(interval);  
                                    console.log("start");
                                    timeout=window.setTimeout(finish,3000);
                                    
                                }
                                
                            }
                               
                                

                           
                        }
                    }

                    xhttp.open("get","mailSent",true);
                    xhttp.send();
                    
                   
                }
            
        </script>
    <script>
        function checkAll()
        {
     var checkboxes = document.getElementsByTagName('input'), val = null; 
   
     for (var i = 0; i < checkboxes.length; i++)
     {
         if (checkboxes[i].type == 'checkbox')
         {
             if (val === null) val = checkboxes[i].checked;
             checkboxes[i].checked = val;
         }
     }
          
  disablebutton();
 }
 
  function disablebutton()
        {
     var checkboxes = document.getElementsByName('mail'); 
     document.getElementById('mysubmit').disabled='true';
     var count=0;
     for (var i = 0; i < checkboxes.length; i++)
     {
             if (checkboxes[i].checked===true)
             {
                          document.getElementById('mysubmit').removeAttribute('disabled');
                          count=count+1;
                         
             }
            if (checkboxes[i].checked==false)
             {
                          document.getElementById('check').checked=false;
                         
             }
         }
         if(count==checkboxes.length)
                document.getElementById('check').checked=true;
 }
 
    function checker()
        {
             document.getElementById('tit').innerHTML="Sending...";
     
        }
    </script>

</html>