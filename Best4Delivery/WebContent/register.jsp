<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Register new user</title>

    <link rel="stylesheet" href="css/styles.css">
    <link rel="stylesheet" href="css/bootstrap.min.css">    
    <link rel="stylesheet" href="css/bootstrap-datepicker.css">

    <script src="js/jquery-2.2.3.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/bootstrap-datepicker.min.js"></script>

  </head>

  <body>

    <%@ include file="./mod/menumain.jsp" %>

    <h1>Register to Best 4 Delivery</h1>
    
    <%@ page import="beans.User" %>

    <%@ include file="./mod/userdata.jsp" %>

    <script type="text/javascript">

      $(document).ready(function() {
    	  
        $("#menu_register").addClass("active");
        $("form#form_user :input[name='action']").val("doRegister");
        $("#form_user").attr("action","<%= response.encodeURL(request.getContextPath()) + "/Controller" %>");
        $("#button_submit").prop("value","Register");
        
      });

    </script>

  </body>

</html>