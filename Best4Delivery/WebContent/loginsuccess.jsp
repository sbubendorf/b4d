<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link rel="stylesheet" href="styles.css"/>
	<title>Login Success</title>
  </head>

  <body>
    <h1>User successfully logged in</h1>
	<table border="1" cellspacing="0" cellpadding="5" >
      <tr>
        <td width="100">E-Mail</td>
        <td width="100"><%= request.getAttribute("email") == null ? "-" : request.getAttribute("email") %></td>
      </tr>
    </table>
    <p>
      <input type="button" name="logout" value="Log Out" onclick="location.href='/<%=request.getContextPath()%>/Controller?action=logout';"/>
    </p>
  </body>

</html>