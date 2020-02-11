<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
  
    <%@ include file="mod/headerdefaults.jsp" %>

    <title>Signin B4D</title>

    <link href="css/signin.css" rel="stylesheet">

  </head>

  <body style="text-align: center;">

    <div style="background-image: url(./images/web_home_banner.jpg); height: 300px; background-size:100% auto;">

      <div class="container">
        <div class="row">
          <div class="col-md-12">
            <form class="form-signin" method="post" action="<%= response.encodeURL(request.getContextPath()) + "/Controller" %>">
      
              <input type="hidden" name="action" value="doLogin">
      
              <h2 class="form-signin-heading">Please sign in</h2>
      
              <label for="inputEmail" class="sr-only">Email address</label>
              <input type="email" id="inputEmail" name="email" value="<%=request.getAttribute("email")%>" class="form-control" placeholder="Email address" required autofocus>
      
              <label for="inputPassword" class="sr-only">Password</label>
              <input type="password" id="inputPassword" name="password" value="<%=request.getAttribute("password")%>" class="form-control" placeholder="Password" required>
      
              <div class="checkbox">
                <label>
                  <input type="checkbox" value="remember-me"> Remember me
                </label>
              </div>
      
              <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
              <button class="btn btn-lg btn-link btn-block" type="button" onclick="location.href='index.jsp';">Cancel</button>
      
            </form>

          </div>
        </div>
      </div>      
    </div>

    <div class="container">
    
      <% 
        String message = (String) request.getAttribute("message");
        String messageType = (String) request.getAttribute("message_type");
        if ( message != null && message.length() > 0 ) {
          if ( messageType == null || messageType.length() == 0 ) {
            messageType = "danger"; 
          }
      %>
      <div class="alert alert-<%= messageType %>">
        <strong>Oh snap!</strong> <%= message %>
      </div>
      <% } %>

    </div> <!-- /container -->

    <script type="text/javascript">

      $(document).ready(function() {
    	  
        
      });
      
    </script>

    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <script src="../../assets/js/ie10-viewport-bug-workaround.js"></script>
  </body>
</html>