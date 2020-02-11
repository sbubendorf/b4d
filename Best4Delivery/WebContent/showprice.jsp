<%@page import="price.PriceRequest"%>
<%@page import="java.util.List"%>
<%@page import="price.PriceOffer"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

  <head>

    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ include file="mod/headerdefaults.jsp" %>

    <title>B4D Price Offers</title>
    
  </head>
  
  <body style="text-align: center;">
  
    <script type="text/javascript">
    
      $(document).ready(function() {
        
      });
      
    </script>

    <%@ include file="mod/menuapp.jsp" %>
    <% PriceRequest pr = (PriceRequest)request.getAttribute("priceRequest"); %>
    
    <div class="row">
      <div class="col-md-12" >
        <h2>Price offers for your request</h2>
        From: <b><%= pr.getCityDepaName() %></b><br/>
        To: <b><%= pr.getCityDestName() %></b><br/>
        Weight: <b><%= pr.getWeight() %></b> kb<br/>
        Width: <b><%= pr.getWidth() %></b> cm<br/>
        Length: <b><%= pr.getLength() %></b> cm<br/>
        Height: <b><%= pr.getHeight() %></b> cm<br/>
        &nbsp;
      </div>
    </div>
    <%
    int counter = 0;
    for ( PriceOffer offer : (List<PriceOffer>)request.getAttribute("priceOffers") ) {
    	counter += 1;
    %>
      <div class="row">
        <div class="col-md-3">
          &nbsp;
        </div>
        <div class="col-md-1">
          <%= counter %>
        </div>
        <div class="col-md-4">
          <div class="panel panel-default">
            <div class="panel-heading">Offer from <b><%= offer.getMover().getName() %></b></div>
            <div class="panel-body">
              <div class="row">
                <div class="col-md-3">
                  &nbsp;
                </div>              
                <div class="col-md-6">
                  CHF <%= offer.getPrice() %>.00
                </div>
                <div class="col-md-3">
                  <button type="button" class="btn btn-success">Accept</button>
                </div>              
              </div>  
            </div>
          </div>
        </div>
        <div class="col-md-4">
        &nbsp;
        </div>
      </div>
    <% } %>
    
  </body>
  

</html>