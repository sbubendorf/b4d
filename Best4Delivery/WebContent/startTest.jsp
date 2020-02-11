<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@page import="javax.sql.DataSource"%>
<%@page import="javax.naming.Context"%>
<%@page import="javax.naming.InitialContext"%>
<%@page import="java.sql.DriverManager"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <link rel="css/stylesheet" href="styles.css"/>
    <!-- Bootstrap -->
    <link href="css/bootstrap.min.css" rel="stylesheet">    
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page
    via file:// -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
      <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
    <![endif]-->
    <title>Fastlane for Test Login</title>
  </head>

  <body style="text-align: center;">
  
    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="https://code.jquery.com/jquery.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="js/bootstrap.min.js"></script>
    
    <script>
      $(function() {
    	  $("[name='loginform']").submit();
      });
    </script>
    
    <div class="container">  
  
      <h1>Welcome to the Test version of Best4Delivery</h1>
      
      <div class="row">
      
        <div class="col-md-12">

          <h2>Select a test user:</h2>
          <table>
            <tr>
              <th width="60">ID</th>
              <th width="120">Name</th>
              <th width="120">First Name</th>
              <th width="120">E-Mail</th>
            </tr>

            <%
            try {
                InitialContext initContext = new InitialContext();
                Context env = (Context) initContext.lookup("java:comp/env");
                DataSource ds = (DataSource) env.lookup("jdbc/b4d");
                Connection con = ds.getConnection();

                String select = "select * from user where last_name is not null";
                PreparedStatement ps = con.prepareStatement(select);
                ResultSet rs = ps.executeQuery();

                int count = 1;
                while (rs.next()) {
                    count++;
            %>

            <tr>
              <td style="text-align: left;"><%= rs.getInt("id") %></td>
              <td style="text-align: left;"><%= rs.getString("last_name") %></td>
              <td style="text-align: left;"><%= rs.getString("first_name") %></td>
              <td style="text-align: left;"><%= rs.getString("email") %></td>
            </tr>

            <%
                }

                rs.close();
                ps.close();
                con.close();

            } catch (Exception e) {
                e.printStackTrace();
            }

            %>
            
          </table>
          
          <form name="loginform" method="post" action="<%= response.encodeURL(request.getContextPath()) + "/Controller" %>">
            <input type="hidden" name="action" value="doLogin">
            <div class="form-group">
              <input name="email" type="email" placeholder="Email" class="form-control" value="anna@bolika.com" required>
            </div>
            <div class="form-group">
              <input name="password" type="password" placeholder="Password" class="form-control" value="anna4bolika" required>
            </div>
            <button type="submit" class="btn btn-success">Sign in</button>
          </form>
          
        </div>
      
      </div>
    
    </div>
    
  </body>
  

</html>