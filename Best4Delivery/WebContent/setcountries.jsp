<%@page import="beans.CountryList"%>
<%@page import="beans.Country"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

  <head>

    <%@ include file="mod/headerdefaults.jsp" %>

    <title>Country Selection</title>
    
  </head>
  
  <body style="text-align: center;">
  
    <%@ include file="mod/menuapp.jsp" %>
    
    <jsp:useBean id="countryList" class="beans.CountryList" scope="application"></jsp:useBean>
    
    <div class="container">  
  
      <div class="container">
        <div class="panel panel-default">
          <div class="panel-heading">Select the countries you serve</div>
          <div class="panel-body">
            <form name="formCountrySupport" method="post" action="<%= response.encodeURL(request.getContextPath()) + "/Controller" %>">
              <input type="hidden" name="action" value="saveCountries">
              <table>
                <c:forEach var="item" items="${countryList.getSupportedCountriesB4D()}">
                  <tr>
                    <td>${item.getName()}</td>
                    <td><input type="checkbox" name="support_${item.getIso2()}" ${item.getSupportedMoverCheck()}></td>
                  </tr>
                </c:forEach>
              </table>
              <div class="form-group">
                <div class="col-sm-offset-2 col-sm-8">
                  <button id="button_submit" type="submit" class="btn btn-default">Save</button>
                </div>
              </div>
            </form>
          </div>
        </div>
      </div>
      
      <img alt="This page is under construction" src="images/under_construction_05.gif">
      
    </div>
    
    <script type="text/javascript">

      $(document).ready(function() {
    	  
        $("#menu_set_countries").addClass("active");
        $("[name^='support']").bootstrapSwitch();
        
        
      });

    </script>

  </body>
  

</html>