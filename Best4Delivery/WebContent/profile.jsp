<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

  <head>

    <%@ include file="./mod/headerdefaults.jsp" %>

    <title>User Profile</title>

  </head>

  <body>

    <%@ include file="./mod/menuapp.jsp" %>

    <h1>Edit your profile data</h1>
  
    <%@ include file="./mod/userdata.jsp" %>
    
    <script type="text/javascript">

      $(document).ready(function() {
    	  
        $("#menu_profile").addClass("active");
        $("form#form_user :input[name='action']").val("saveProfile");
        $("#form_user").attr("action","<%= response.encodeURL(request.getContextPath()) + "/Controller" %>");
        $("#button_submit").prop("value","Save Profile");
        
      });

    </script>

  </body>

</html>