<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

  <head>
    <%@ include file="mod/headerdefaults.jsp" %>
    
    <style type="text/css">
      .btn-trans > a {
          background:none;
          border:none;
          box-shadow:none;
      }    
      
      .btn-trans .btn {
        background-image: none;
      }
    </style>
    
    <title>Welcome to Best4Delivery</title>
    
  </head>

  <body>
  
    <div style="background-image: url(./images/web_home_banner.jpg); height: 300px; background-size:100% auto;">
      <div class="btn-group btn-trans">
        <button type="button" class="btn btn-default">Home</button>
        <button type="button" class="btn btn-default">Spediteur</button>
        <button type="button" class="btn btn-default">Anfrage</button>
        <div class="btn-group">
          <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">Registrieren<span class="caret"></span></button>
          <ul class="dropdown-menu" role="menu">
            <li><a href="#">Als Kunde</a></li>
            <li><a href="#">Als Spediteur</a></li>
          </ul>
        </div>
      </div>    

      <br><br>
      <img alt="B4D" src="images/b4d-logo.png">
      
      <br><br>
      <div class="container">
        <div class="row">
          <div class="col-md-1">
            <button type="button" class="btn btn-warning btn-lg">Was ist B4D?</button>
          </div>
          <div class="col-md-10">
          </div>
        </div>
      </div>      
    </div>
  
    <form method="post" action="<%= response.encodeURL(request.getContextPath()) + "/Controller" %>" class="form-horizontal" role="form">
    
      <input type="hidden" name="action" value="doLogin">
      
      <div class="form-group">
        <label for="email" class="col-sm-2 control-label">E-Mail</label>
        <div class="col-sm-8">
          <input type="text" name="email" id="email" class="form-control" value="<%=request.getAttribute("email")%>" placeholder="Enter E-Mail address">
        </div>
      </div>

      <div class="form-group">
        <label for="password" class="col-sm-2 control-label">Password</label>
        <div class="col-sm-8">
          <input type="text" name="password" id="password" class="form-control" value="<%=request.getAttribute("email")%>" placeholder="Enter your password">
        </div>
      </div>
      
      <div class="form-group">
        <div class="col-sm-offset-2 col-sm-8">
          <div class="checkbox">
            <label>
              <input type="checkbox"> Remember me
            </label>
          </div>
        </div>
      </div>
      
      <div class="form-group">
        <div class="col-sm-offset-2 col-sm-8">
          <button type="submit" class="btn btn-default">Sign in</button>
        </div>
      </div>

      <p class="login-error">
        <%= request.getAttribute("message") %>
      </p>
    
    </form>    

  </body>

</html>