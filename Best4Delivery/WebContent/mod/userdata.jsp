    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

    <jsp:useBean id="user" class="beans.User" scope="session"></jsp:useBean>
    <jsp:useBean id="countryList" class="beans.CountryList" scope="application"></jsp:useBean>
    <jsp:setProperty property="*" name="user"/>
    
    <form id="form_user" method="post" action="/B4D/register.jsp" class="form-horizontal">

      <input type="hidden" name="action" value="unknown">
      
      <div class="form-group">
        <label for="email" class="col-sm-2 control-label">E-Mail</label>
        <div class="col-sm-8">
          <input  name="email" id="email" 
                  type="text" class="form-control" 
                  value="<jsp:getProperty property="email" name="user"/>"
                  placeholder="Enter E-Mail address"
                  autofocus="autofocus" >
          <span class="help-block">This address will be your login ID</span>
        </div>
      </div>
      
      <!-- Modal -->
      <div class="modal fade" id="emailAlert" role="dialog">
        <div class="modal-dialog">
        
          <!-- Modal content-->
          <div class="modal-content">
            <div class="modal-header">
              <button type="button" class="close" data-dismiss="modal">&times;</button>
              <h4 class="modal-title">Checking eMail address...</h4>
            </div>
            <div class="modal-body">
              <p id="emailMessage">Some text in the modal.</p>
            </div>
            <div class="modal-footer">
              <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
            </div>
          </div>
          
        </div>
      </div>

      <div class="form-group">
        <label for="firm" class="col-sm-2 control-label">Firm</label>
        <div class="col-sm-8">
          <input type="text" name="firm" id="firm" class="form-control" value='<jsp:getProperty property="firm" name="user"/>' placeholder="Name of the firm">
        </div>
      </div>

      <div class="form-group">
        <label for="last_name" class="col-sm-2 control-label">Last name</label>
        <div class="col-sm-8">
          <input type="text" name="last_name" id="last_name" class="form-control" value='<jsp:getProperty property="lastName" name="user"/>' placeholder="Lastname">
        </div>
      </div>

      <div class="form-group">
        <label for="first_name" class="col-sm-2 control-label">First name</label>
        <div class="col-sm-8">
          <input type="text" name="first_name" id="first_name" class="form-control" value='<jsp:getProperty property="firstName" name="user"/>' placeholder="Firstname">
        </div>
      </div>

      <div class="form-group">
        <label for="street" class="col-sm-2 control-label">Street</label>
        <div class="col-sm-6">
          <input type="text" name="street" id="street" class="form-control" value='<jsp:getProperty property="street" name="user"/>' placeholder="Street">
        </div>
        <label for="street_no" class="col-sm-1 control-label">No</label>
        <div class="col-sm-1">
          <input type="text" name="street_no" id="street_no" class="form-control" value='<jsp:getProperty property="streetNo" name="user"/>' placeholder="House Number">
        </div>
      </div>

      <div class="form-group">
        <label for="countrysel" class="col-sm-2 control-label">Country</label>
        <div class="col-sm-8">
          <select name="country_id" class="form-control" id="countrysel">
            <option></option>
            <c:forEach var="item" items="${countryList.getAllCountries()}">
              <option value="${item.getId()}" ${item.getId() == user.getCountryId() ? 'selected' : '' }>${item.getName()}</option>
            </c:forEach>
          </select>
        </div>
      </div>

      <div class="form-group">
        <label for="zip" class="col-sm-2 control-label">Zip</label>
        <div class="col-sm-2">
          <input type="text" name="zip" id="zip" class="form-control" value='<jsp:getProperty property="zip" name="user"/>' placeholder="Zip code">
        </div>
        <label for="city" class="col-sm-1 control-label">City</label>
        <div class="col-sm-5">
          <input type="text" name="city" id="city" class="form-control" value='<jsp:getProperty property="city" name="user"/>' placeholder="City">
        </div>
      </div>

      <div class="form-group">
        <label for="password" class="col-sm-2 control-label">Password</label>
        <div class="col-sm-8">
          <input type="password" name="password" id="password" class="form-control" value='<jsp:getProperty property="password" name="user"/>' placeholder="Enter your password">
        </div>
      </div>
      
      <div class="form-group">
        <label for="password2" class="col-sm-2 control-label">Repeat Password</label>
        <div class="col-sm-8">
          <input type="password" name="password2" id="password2" class="form-control" value='<jsp:getProperty property="password" name="user"/>' placeholder="Enter your password again (just to be sure ...)">
        </div>
      </div>
      
      <div class="form-group">
        <label for="remarks" class="col-sm-2 control-label">Remarks</label>
        <div class="col-sm-8">
          <textarea name="remarks" id="remarks" class="form-control" rows="5" placeholder="Tell us something about you ..."><jsp:getProperty property="remarks" name="user"/></textarea>
        </div>
      </div>
      
      <div class="form-group">
        <label for="genderoption" class="col-sm-2 control-label">Gender</label>
        <div class="col-sm-1" id="genderoption">
          <div class="radio">
            <label>
              <%
              String checked = "";
              if (user.getGender() == 1) {
                checked="checked"; 
              }
              %>
              <input type="radio" name="gender" id="gendermale" value="1" <%=checked %>>Male
            </label>
          </div> 
        </div> 
        <div class="col-sm-7" id="genderoption">
          <div class="radio">
            <label>
              <%
              checked = "";
              if (user.getGender() == 2) {
                checked="checked"; 
              }
              %>
              <input type="radio" name="gender" id="genderfemale" value="2" <%=checked %>>Female
            </label>
          </div>
        </div> 
      </div>      
      
      <div class="form-group">
        <label for="date_birth" class="col-sm-2 control-label">Date of birth</label>
        <div class='col-sm-8'>
          <div class='input-group date' id='birthdatepicker'>
            <input name="date_birth" id="date_birth" type='text' class="form-control" value="<%= user.getBirthDateText()%>"/>
            <span class="input-group-addon">
              <span class="glyphicon glyphicon-calendar"></span>
            </span>
          </div>
        </div>
      </div>      
    
      <div class="form-group">
        <div class="col-sm-offset-2 col-sm-8">
          <button id="button_submit" type="submit" class="btn btn-default">Register</button>
        </div>
      </div>

    </form>
    
    <script type="text/javascript">
    
      function checkEmail() {
        var email  = $("#email").val();
        var params = {
          "action"  : "checkEmail",
          "email"   : email
        }
            
        $.get(
          "Register", 
          params,
          function(message) {
            if (message != null && message.length > 0) {
              $("#emailMessage").text(message);
              $("#emailAlert").modal("show");
            }
          }
        );
      }

      $(document).ready(function() {
        
        $('#birthdatepicker').datepicker({
			viewMode: 'years',
            format: "dd.mm.yyyy"
        });
        $('#birthdatepicker input').off('focus')
        .click(function () {
            $(this).parent().data("DateTimePicker").show();
        })        
        
        $('#emailAlert').on('hidden.bs.modal', function (e) {
          // Set focus on eMail entry field after error message is closed.
          $("#email").focus();
        });
        
        /*
        $("#email").change(function() {
          checkEmail();
        });
        */
        
      });
    </script>
