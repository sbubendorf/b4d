<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<sql:query var="rs" dataSource="jdbc/b4d">
  select * from user
</sql:query>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>

  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Test Datasource to B4D</title>
    <link rel="stylesheet" href="styles.css"/>
  </head>
  
  <body>

  <h2>Content of table USER</h2>

  <table>
    <thead>
      <tr>
        <th>ID</th>
        <th>Login</th>
        <th>Name</th>
        <th>E-Mail</th>
      </tr>
    </thead>
    <tbody>
      <c:forEach var="row" items="${rs.rows}">
        <tr>
          <td>${row.id}</td>
          <td>${row.login_name}</td>
          <td>${row.name}</td>
          <td>${row.email}</td>
        </tr>
      </c:forEach>
    </tbody>
  </table>

  </body>
</html>