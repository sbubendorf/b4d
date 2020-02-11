<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <link rel="stylesheet" href="styles.css"/>
    <!-- Bootstrap -->
    <link href="css/bootstrap.min.css" rel="stylesheet">    
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page
    via file:// -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
      <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
    <![endif]-->
    <title>About</title>
    
  </head>

  <body style="text-align: center;">
  
    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="https://code.jquery.com/jquery.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="js/bootstrap.min.js"></script>
    
    <script>
    	$(function() {
    		$("#menu_about").addClass("active");
    	});
    </script>
    
    <%@ include file="./mod/menumain.jsp" %>

    <div class="container">  
  
      <h1>All about Best4Delivery</h1>
      
      <div class="row">
      
        <div class="col-md-8" >
          <p>
            Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor 
            invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et 
            accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata 
            sanctus est Lorem ipsum dolor sit amet.     
          </p>
        </div>

        <div class="col-md-4">
          <img alt="Best4Delivery" src="images/b4d.gif" height="300px">
        </div>        
      
      </div>
    
    </div>
    
    <p>
      Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor 
      invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et 
      accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata 
      sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing 
      elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, 
      sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita 
      kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.    
    </p>
    
  </body>
  

</html>