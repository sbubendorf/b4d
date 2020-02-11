<%@ page language="java" contentType="text/html; charset=ISO-8859-1"  pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">

  <head>

    <%@ include file="mod/headerdefaults.jsp" %>

    <script type="text/javascript">
      $( function() {
        var availableTags = [
          "ActionScript",
          "AppleScript",
          "Asp",
          "BASIC",
          "C",
          "C++",
          "Clojure",
          "COBOL",
          "ColdFusion",
          "Erlang",
          "Fortran",
          "Groovy",
          "Haskell",
          "Java",
          "JavaScript",
          "Lisp",
          "Perl",
          "PHP",
          "Python",
          "Ruby",
          "Scala",
          "Scheme"
        ];
    
        $("#tags").autocomplete({
        	source: availableTags
        });
    
        $("#city_depa").autocomplete({
        	source: function(request, response) {
        	  $.ajax({
        		  url: "<%= response.encodeURL(request.getContextPath()) %>/CitySearch",
        		  type: "post",
        		  dataType: "json",
        		  data: request,
        		  success: function( data, textStatus, jqXHR) {
                var items = data;
                response(items);
        			},
              error: function(jqXHR, textStatus, errorThrown){
            	  console.info( textStatus);
              }
        		});
          },
          minLength: 2
        });
      });
    </script>
  </head>
  
  <body>
  
    <h1>Test jQuery Autocomplete functionality</h1>
    
    <div class="container">  
      <div class="row">
        <form action="form_request">
          <div class="col-md-3">
            <b>Transportanfrage stellen:</b>
          </div>
          <div class="form-group">
            <label for="city_depa" class="col-sm-1 control-label">Ort von</label>
            <div class="col-sm-3">
              <input type="text" name="city_depa" id="city_depa" class="form-control" placeholder="Place of Departure">
            </div>
          </div>
          <div class="form-group">
            <label for="city_dest" class="col-sm-1 control-label">Ort nach</label>
            <div class="col-sm-3">
              <input type="text" name="city_dest" id="city_dest" class="form-control" placeholder="Place of Destination">
            </div>
          </div>
          <div class="col-md-1">
            <button id="button_submit" type="submit" class="btn btn-default">Weiter</button>
          </div>
        </form>
      </div>
    </div>
 
    <div class="ui-widget">
      <label for="tags">City: </label>
      <input id="city">
    </div>
 
    <div class="ui-widget">
      <label for="tags">Tags: </label>
      <input id="tags">
    </div>
 
  </body>
  
</html>