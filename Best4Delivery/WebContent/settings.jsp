<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

  <head>

    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ include file="mod/headerdefaults.jsp" %>

    <title>B4D Mover Settings</title>
    
  </head>
  
  <body style="text-align: center;">
  
    <%@ include file="mod/menuapp.jsp" %>
    
    <jsp:useBean id="mover" class="beans.Mover" scope="session"></jsp:useBean>

    <div class="container">  
      <form id="form_settings" method="post" class="form-horizontal" action="<%= response.encodeURL(request.getContextPath()) %>/Controller" >
        <input name="action" value="saveSettings" style="display: none">
        <div class="panel panel-default">
          <div class="panel-heading">General Settings</div>
          <div class="panel-body">
            <div class="form-group">
              <label for="dim_weight" class="col-md-4 control-label">Dimensional / Cubical Weight</label>
              <div class="col-md-2">
                <input type="text" name="dim_weight" class="form-control" value="<jsp:getProperty property="dimWeight" name="mover"/>">
              </div>
            </div>
          </div>
        </div>
        <div class="panel panel-default">
          <div class="panel-heading">Size Ranges</div>
          <div class="panel-body">
           
          </div>
        </div>
        <div class="col-md-12">
          <button type="submit" class="btn btn-primary">Save Settings</button>
        </div>
      </form>
    </div>
    
    <script type="text/javascript">

      $(document).ready(function() {
    	  
        $("#menu_settings").addClass("active");
        
        $('#form_settings').bootstrapValidator({
          feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
          },
          fields: {
        	  dim_weight: {
              validators: {
                notEmpty: {
                  message: "The country of departure is mandatory!"
                },
                regexp: {
                  message: 'The weight must be given as a numeric value!',
                  regexp: /^[0-9]*.[0-9]*$/
                },
                callback: {
                  message: 'Your factor for cubic weight calculation is not valid!',
                  callback: function(value, validator, $field) {
                    return value > 0 || value <= 1000;
                  }
                }
              }
            },
          }
        });      
      });

    </script>

  </body>
  

</html>