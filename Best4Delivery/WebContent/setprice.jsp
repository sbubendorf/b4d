<%@page import="beans.Zone"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

  <head>

    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ include file="mod/headerdefaults.jsp" %>

    <link rel="stylesheet" type="text/css" media="screen" href="css/ui.jqgrid.css" />
    <link rel="stylesheet" type="text/css" media="screen" href="css/ui.jqgrid-bootstrap.css" />
    <script src="js/grid.locale-en.js" type="text/javascript"></script>
    <script src="js/jquery.jqGrid.min.js" type="text/javascript"></script>

    <title>B4D Start - Get your price</title>
    
  </head>
  
  <body style="text-align: center;">
  
    <%@ include file="mod/menuapp.jsp" %>
    
    <jsp:useBean id="countryList" class="beans.CountryList" scope="application"></jsp:useBean>
    <jsp:useBean id="mover"       class="beans.Mover"       scope="session"></jsp:useBean>
    
    <div class="container">

      <ul class="nav nav-tabs">
        <li class="active"><a data-toggle="tab" href="#tab_sheet">Price Sheet</a></li>
        <li><a data-toggle="tab" href="#tab_table">Price Table</a></li>
      </ul>
      
      <div class="tab-content">

        <div id="tab_sheet" class="tab-pane fade in active">
          <div class="panel panel-default">
            <div class="panel-heading">Price Sheet Handling</div>
            <div class="panel-body">
              <div class="panel panel-default">
                <div class="panel-heading">Download</div>
                <div class="panel-body">
                  <form name="downloadExcel" action="ExcelGenerator" method="post">
                    <div class="form-group">
                      <label for="country_depa" class="col-sm-1 control-label">von</label>
                      <div class="col-sm-3">
                        <select name="country_depa" class="form-control" id="country_depa">
                          <option></option>
                          <c:forEach var="item" items="${countryList.getSupportedCountriesMover()}">
                            <option value="${item.iso2}">${item.name}</option>
                          </c:forEach>
                        </select>
                      </div>
                    </div>
                    <div class="form-group">
                      <label for="country_dest" class="col-sm-1 control-label">nach</label>
                      <div class="col-sm-3">
                        <select name="country_dest" class="form-control" id="country_dest">
                          <option></option>
                          <c:forEach var="item" items="${countryList.getSupportedCountriesMover()}">
                            <option value="${item.iso2}">${item.name}</option>
                          </c:forEach>
                        </select>
                      </div>
                    </div>
                    <button type="submit" class="btn btn-primary">Generate Price Sheet</button>
                  </form>
                </div>
              </div>
              <div class="panel panel-default">
                <div class="panel-heading">Upload</div>
                <div class="panel-body">
                  <form name="uploadExcel" enctype="multipart/form-data" action="ExcelLoader" method="post">
                    <label class="btn btn-primary" for="filename">
                      <input id="filename" name="filename" type="file" style="display:none;" onchange="$('#fileinfo').html($(this).val());">
                      Browse
                    </label>
                    <span class='label label-info' id="fileinfo"></span>
                    <button type="submit" class="btn btn-primary">Upload Price Sheet</button>
                  </form>
                </div>
              </div>
            </div>
          </div>
        </div>

        <div id="tab_table" class="tab-pane fade">
          <div class="panel panel-default">
            <div class="panel-heading">Price Table</div>
            <div class="panel-body">
              <form id="price_countries" method="post" class="form-horizontal" action="<%= response.encodeURL(request.getContextPath()) %>/Controller" >
                <input name="action" value="getPrices" style="display: none">
                <div class="form-group">
                  <label class="control-label col-md-2" for="country_from">Country From</label>
                  <div class="col-md-10">
                    <select name="country_from" class="form-control" id="country_from" onchange="getPrices();">
                      <option></option>
                      <c:forEach var="item" items="${countryList.getSupportedCountriesMover()}">
                        <option value="${item.iso2}" ${item.iso2 == zoneList.getCountryIso() ? 'selected' : '' }>${item.name}</option>
                      </c:forEach>
                    </select>
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-sm-2 control-label" for="country_to">Country To</label>
                  <div class="col-md-10">
                    <select name="country_to" class="form-control" id="country_to" onchange="getPrices();">
                      <option></option>
                      <c:forEach var="item" items="${countryList.getSupportedCountriesMover()}">
                        <option value="${item.iso2}" ${item.iso2 == zoneList.getCountryIso() ? 'selected' : '' }>${item.name}</option>
                      </c:forEach>
                    </select>
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-sm-2 control-label" for="size_range">Size Range</label>
                  <div class="col-md-10">
                    <select name="size_range" class="form-control" id="size_range" onchange="getPrices();">
                      <option></option>
                      <c:forEach var="item" items="${mover.getSizeRanges()}">
                        <option value="${item.getRangeId()}">${item.getRangeLabel()}</option>
                      </c:forEach>
                    </select>
                  </div>
                </div>
                <div class="form-group">
                  <div class="col-md-offset-2 col-md-10">
                    <button type="submit" class="btn btn-primary">Get Prices</button>
                  </div>
                </div>
              </form>
            </div>
            <div id="grid_prices" style="display:none; padding-bottom:30px; margin-left:20px">
              <table id="prices"></table>
              <div id="prices_pager"></div>
            </div>
          </div>
        </div>

      </div>

    </div>
    
    <script type="text/javascript">
    
      $(document).ready(function() {
    	  
        $("#menu_set_price").addClass("active");

        $("#uploadExcel").submit(function() {
        	$("#message_box").empty();
        });

        $("#downloadExcel").submit(function() {
          $("#message_box").empty();
        });
        
      });
      
      function getPrices() {
    	  
        if ( $("#country_from").val() == "" || $("#country_to").val() == "" || $("#size_range").val() == "" ) {
    		  return;
    	  }
    	  
    	  // Get the list of destination zones to define the colModel of the price grid -------------------------
        $.ajax({
          url         : "<%= response.encodeURL(request.getContextPath()) %>/PriceLoader",
//           dataType    : "json",
//           contentType : "application/json",
          type        : 'POST',
          async       : false,
          data        : {
                          action        : "getZones",
                          country       : $("#country_to").val()
                        },
          success     : function( data ) {
        	                jQuery.each(data, function(index, itemData) {
        	                	alert(index + " : " + itemData.name);
        	                });
                        },
          error       : function(jqXHR, textStatus, errorThrown) {
                          alert( textStatus);
                        }
    	  
        });
    	  
    	  // Build jqGrid with column for each destination zone ---------------------------------

        $("#gridPrices").jqGrid({
            url         : 'data.json',
            datatype    : "json",
             colModel   : [
                            { label: 'Category Name', name: 'CategoryName', width: 75 },
                            { label: 'Product Name', name: 'ProductName', width: 90 },
                            { label: 'Country', name: 'Country', width: 100 },
                            { label: 'Price', name: 'Price', width: 80, sorttype: 'integer' },
                            // sorttype is used only if the data is loaded locally or loadonce is set to true
                            { label: 'Quantity', name: 'Quantity', width: 80, sorttype: 'number' }                   
                          ],
            viewrecords : true, // show the current page, data rang and total records on the toolbar
            width       : 780,
            height      : 200,
            rowNum      : 30,
            loadonce    : true, // this is just for the demo
            pager       : "#jqGridPager"
          });      
      
      
      }
      

    </script>

  </body>
  

</html>