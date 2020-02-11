<%@page import="beans.Zone"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

  <head>

    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ include file="mod/headerdefaults.jsp" %>

    <title>B4D Zone Administration</title>
    
  </head>
  
  <body style="text-align: center;">
  
    <%@ include file="mod/menuapp.jsp" %>
    
    <jsp:useBean id="countryList" class="beans.CountryList" scope="application"></jsp:useBean>
    <jsp:useBean id="zoneList"    class="beans.ZoneList"    scope="session"></jsp:useBean>

    <div class="container">  
  
      <div class="container">
        <div class="panel panel-default">
          <div class="panel-heading"><b>Select a country</b></div>
          <div class="panel-body">
            <form id="zone_country" method="post" class="form-horizontal" action="<%= response.encodeURL(request.getContextPath()) %>/Controller" >
              <input name="action" value="getZones" style="display: none">
              <div class="container">
                <div class="row">
                  <div class="form-group">
                    <label for="country" class="col-sm-2 control-label">Country</label>
                    <div class="col-sm-6">
                      <select name="country" class="form-control" id="country">
                        <option></option>
                        <c:forEach var="item" items="${countryList.getSupportedCountriesMover()}">
                          <option value="${item.iso2}" ${item.iso2 == zoneList.getCountryIso() ? 'selected' : '' }>${item.name}</option>
                        </c:forEach>
                      </select>
                    </div>
                    <div class="col-sm-2">
                      <button type="submit" class="btn btn-primary">Get Zones</button>
                    </div>
                  </div>
                </div>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
    
    <div class="container">
      <h2>Zones defined for <%= zoneList.getCountryIso() %></h2>
      <div class="row">
        <div class="col-md-8">
          <table id="tab_zones" class="table table-hover">
            <thead>
              <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Key</th>
                <th>Remark</th>
              </tr>
            </thead>
            <tbody>
              <%
              for ( Zone zone : zoneList.getZoneList() ) {
              %>
              <tr>
                <td><%= zone.getId() %></td>
                <td><%= zone.getName() %></td>
                <td><%= zone.getShortName() %></td>
                <td><%= zone.getDescription() %></td>
              </tr>
              <%
              }
              %>
            </tbody>
          </table>
        </div>
        <div class="col-md-4">
          <button type="button" id="btn_add_zone" class="btn btn-primary btn-block">Add a new Zone</button><br/>
          <div class="panel panel-default" id="panel_zone_details" style="display:none;">
            <div class="panel-heading"><b>Zone Details</b></div>
            <div class="panel-body">
              <form id="form_zone_details" method="post" class="form-horizontal" action="<%= response.encodeURL(request.getContextPath()) %>/Controller" >
                <input name="action" value="saveZoneDetails" style="display: none">
                <input name="zone_id" id="zone_id" style="display: none">
                <div class="form-group">
                  <label for="zone_id_show" class="col-md-4 control-label">ID</label>
                  <div class="col-md-8">
                    <input type="text" name="zone_id_show" id="zone_id_show" class="form-control" disabled>
                  </div>
                </div>
                <div class="form-group">
                  <label for="zone_name" class="col-md-4 control-label">Name</label>
                  <div class="col-md-8">
                    <input type="text" name="zone_name" id="zone_name" class="form-control" placeholder="Name of the Zone">
                  </div>
                </div>
                <div class="form-group">
                  <label for="zone_short_name" class="col-md-4 control-label">Short Name</label>
                  <div class="col-md-8">
                    <input type="text" name="zone_short_name" id="zone_short_name" class="form-control" placeholder="Short name of the Zone">
                  </div>
                </div>
                <div class="form-group">
                  <label for="zone_remark" class="col-md-4 control-label">Remarks</label>
                  <div class="col-md-8">
                    <textarea rows="5" name="zone_remark" id="zone_remark" class="form-control"></textarea>
                  </div>
                </div>
                <button type="submit" class="btn btn-primary">Save Zone</button>
              </form>
            </div>
          </div>
        </div>
      </div>
    
    </div>
    
    ${zoneList.getCountryIso()}
    
    <script type="text/javascript">

      $(document).ready(function() {
    	  
        $("#menu_set_zones").addClass("active");
        
        $("#btn_add_zone").click(function(e) {
        	$("#panel_zone_details").show();
        });
        
        $("#tab_zones > tbody > tr").click(function() {
        	$(this).addClass('bg-info').siblings().removeClass('bg-info');
          $("#panel_zone_details").show();
          $("#zone_id").val($(this).find("td:eq(0)").html());
          $("#zone_id_show").val($(this).find("td:eq(0)").html());
          $("#zone_name").val($(this).find("td:eq(1)").html());
          $("#zone_short_name").val($(this).find("td:eq(2)").html());
          $("#zone_remark").val($(this).find("td:eq(3)").html());
        });
        
        $("#country").focus();      
        
      });

    </script>

  </body>
  

</html>