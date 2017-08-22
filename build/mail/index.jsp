<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!doctype html>
 <% 
         String alertVar=(String)request.getAttribute("alert");
         String alertMsg=(String)request.getAttribute("alertMsg");
         if(request.getParameter("alert")!=null)
         {
               alertVar=(String)request.getParameter("alert");
               alertMsg=(String)request.getParameter("alertMsg");
         }
         response.setHeader("Cache-Control","no store,no-cache,must-revalidate");
            response.setHeader("Cache-Control","post-check=0,pre-check=0");
         response.setHeader("Pragma","no-cache");
    %>
<html>
    <head>
        <title>Online Salary Receipt Generation</title>
              <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/bootstrap.min.css">
        <link rel="stylesheet" href="css/bootstrap-theme.css">
        <style>
            select{
                margin-bottom: 20px;
            }
        </style>
    
        
    </head>
    
    <body > 
        
        <header class="container">
            <div class="jumbotron page-header">
            <h1><small>Online Salary Receipt Generation</small></h1>
            </div>
        </header>
        
        <main class="col-lg-4 col-lg-offset-4" style="text-align:center">
            <form  method="post" action="UploadServlet" id="uploadform" enctype="multipart/form-data">
                <select class="form-control input-sm" name="month" id="month">
                </select>
                
                <select class="form-control input-sm" name="session" id="session">
                    
                </select>
                
                <div class="row">
                <div class="col-xs-2">
                    <input class="form-control input-sm" id="isFile" name="isFile"  type="checkbox" onclick="enableUpload()" style="margin-bottom:15px;">
                </div>
                <div class="col-xs-6 text-left">
                    <p style="margin-top:8px">Accessing Previous Entry, Not Attaching New File</p>
                </div>
                </div>
                <input type="hidden" id="insertCase" name="insertCase" >
                <input onchange="submitEnable()" type="file" id="file" class="btn btn-success" style="margin-bottom:2px" name="file"><br>
                 
            </form>
                            <input type="button" id="submit" onclick="ajaxUse()" value="submit" class="btn btn-block btn-primary" disabled>

            <form id="hiddenform" method="post" >
                <input type="hidden" id="hiddenmonth" name="month" >
                <input type="hidden" id="hiddensession" name="session">      
            </form>
        </main>
            <script>
                
                function func()
            {
                   init();

         if("<%=alertVar%>"==="true")
           {
           
        alert("<%=alertMsg%>");
                }
        
        if("<%=alertVar%>"==="delete")
           {
           if(confirm("<%=alertMsg%>"))
           {
                    document.getElementById("myform").submit();
           }
           
                }

                }
                
               
            </script>
    </body>
    <script src="js/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script>
        

            

    // do something with the images
        
        window.onload= func;
        
         window.onbeforeunload=function(e)
                {
                    location.assign("index.jsp");
                }
        k = 0;
        function enableUpload(){
           var a =  document.getElementById("file");
           var submit = document.getElementById("submit");
           if(k==0){
               a.setAttribute("disabled","true");
               submit.removeAttribute("disabled");
               k=1;
           }
           else{
               
               a.removeAttribute("disabled");
               if(a.value==''){
               submit.setAttribute("disabled","true");
                }
               k=0;
               console.log("Here");
           }
          
        }
        
        function submitEnable(){
            console.log("cartoon");
            var file = document.getElementById("file");
            var submit = document.getElementById("submit");
            if(file.value!=""){
                submit.removeAttribute("disabled");
            }
        }
        
        function init(){
           
        
       
            
            k = 0;
            d = new Date();
            console.log(d.getMonth());
            var data = "";
            for(i=1996;i<d.getFullYear()+10;i++){
                if(i==d.getFullYear()){
                    console.log();
                data = data + "<option value='table"+i+""+(i+1)+"' selected >"+i+"-"+(i+1)+"</option>";    
                }
                else{
                data = data + "<option value='table"+i+""+(i+1)+"'>"+i+"-"+(i+1)+"</option>";
                }
                }
            document.getElementById("session").innerHTML=data;
            monthGen();
            
          
        }
        
        function monthGen(){
            var a = ["Jan","Feb","Mar","April","May","June","July","Aug","Sep","Oct","Nov","Dec"];
            var data = "";
            console.log(d.getMonth());
            for(i=0;i<12;i++){
                if(i==d.getMonth()){
                data = data + "<option value='"+a[i]+"' selected>"+a[i]+"</option>";
                }
                else{
                    data = data + "<option value='"+a[i]+"'>"+a[i]+"</option>";
                }
                }
            document.getElementById("month").innerHTML = data;
             
        }
   
   
        function ajaxUse()
        {
            var noFile=document.getElementById('isFile');
            var month=document.getElementById('month');
            var session=document.getElementById('session');
            var xhttp=new XMLHttpRequest();
            var hiddenmonth=document.getElementById("hiddenmonth");
            var hiddensession=document.getElementById("hiddensession");
            var hiddenform=document.getElementById("hiddenform");
            var uploadform=document.getElementById("uploadform");
            var insertCase=document.getElementById("insertCase");
            noFile.value=noFile.checked;
            xhttp.onreadystatechange=function()
            {
                if(xhttp.readyState==4 && xhttp.status==200)
                {
                        var msg=xhttp.responseText;  
                        console.log(msg);
                             var obj=JSON.parse(msg);
                             if(obj.case==="alert")
                        {    
                             alert(obj.alertMsg);

                        }
                        else if(obj.case==="email")
                            {
                                hiddenmonth.value=month.value;
                                hiddensession.value=session.value;
                                hiddenform.action="email.jsp";
                                hiddenform.submit();
                                
                            }
                            else if(obj.case==="overWrite")
                            {
                                if(confirm(obj.alertMsg))
                                {
                                   insertCase.value="overWrite";
                                    document.getElementById("uploadform").submit();
                                }
                                
                            }else if(obj.case==="insert")
                                  {
                                    insertCase.value="overWrite";
                                    uploadform.submit();
                                  }  
                }
            }
            xhttp.open("POST","RequestHandler",true);
            var parameters="noFile="+noFile.value+"&month="+month.value+"&session="+session.value;
            xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
            console.log(parameters);
            xhttp.send(parameters);
        }
    </script>
   
    
    
     
</html>