<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

  <head>

    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ include file="mod/headerdefaults.jsp" %>

    <title>B4D Administration</title>
    
  </head>
  
  <body style="text-align: center;">
  
    <%@ include file="mod/menuapp.jsp" %>
    
    <jsp:useBean id="countryList" class="beans.CountryList" scope="application"></jsp:useBean>

    <div class="container">
    
      <h1>This page is for administration purpose!</h1>  
  
      <img alt="This page is under construction" src="images/under_construction_05.gif">
      
    </div>
    
    <script type="text/javascript">

      $(document).ready(function() {
    	  
        $("#menu_admin").addClass("active");
        
      });

    </script>

  </body>
  

</html>