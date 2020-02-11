<%@page import="beans.User"%>
<%@page import="javax.naming.Context"%>
<%@page import="javax.naming.InitialContext"%>
<%@page import="java.sql.Connection"%>
<%@page import="javax.sql.DataSource"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="beans.CountryList"%>
<%@page import="beans.Country"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

  <head>
    <%@ include file="mod/headerdefaults.jsp" %>
    
    <style type="text/css">
      .btn-trans > a {
          background:none;
          border:none;
          box-shadow:none;
      }    
      
      .btn-trans .btn {
        background-image: none;
      }
    </style>
    
    <title>Welcome to Best4Delivery</title>
    
  </head>

  <body style="text-align: center;">
  
    <script>
      $(function() {
        $("#menu_home").addClass("active");
        $("#city_depa").focus();
        $("#city_depa, #city_dest").autocomplete({
          width         : 500,
          max           : 8,
          delay         : 100,
          minLength     : 3,
          autoFocus     : false,
          cacheLength   : 1,
          highlight     : false,
          source        : function(request, response) {
        	  var elemCity = this.element;
        	  var elemCountry = this.element.prev("select");
        	  $.ajax({
              url       : "<%= response.encodeURL(request.getContextPath()) %>/CitySearch",
              type      : "POST",
              dataType  : "json",
              data      : { 
            	              country  : elemCountry.val(),     // $("#country_depa").val(),
            	              term     : request.term
            	            },
              success   : function( data ) {
                        		if ( elemCountry.val() == "" ) {
                              response($.map(data, function(item) {
                                return { 
                                  label     : item.zip + " " + item.name + " (" + item.country_iso + ")",
                                  name      : item.name,
                                  zip       : item.zip,
                                  id        : item.id,
                                  country   : item.country_iso
                                };
                              }));
                            } else {
                              response($.map(data, function(item) {
                              	return { 
                              	  label     : item.zip + " " + item.name,
                              		name      : item.name,
                              		zip       : item.zip,
                              		id        : item.id,
                              		country   : item.country_iso
                              	};
                              }));
                            }
                          },
              error     : function(jqXHR, textStatus, errorThrown) {
                            alert( textStatus);
                          }
            });
          },
          select        : function (event, ui) {
        	  if ( ui.item ) {
        		  $(this).val(ui.item.name);
              if ( $(this).prev("select").val() == "" ) {
            	  $(this).prev("select").val(ui.item.country);
        		  }
        	  } else {
              $(this).val("");
        	  }
          },
        });

      });
      
    </script>

    <%
    
        User user = (User)session.getAttribute("user");
        if ( user == null ) {
          user = new User(0); 
        }
    
        InitialContext initContext = new InitialContext();
        Context env = (Context) initContext.lookup("java:comp/env");
        DataSource ds = (DataSource) env.lookup("jdbc/b4d");
        Connection con = ds.getConnection();

        String select = "select name, iso_2 from country where supported_b4d = 1";
        PreparedStatement ps = con.prepareStatement(select);
        ResultSet rs = ps.executeQuery();

        CountryList countryList = new CountryList();
        while (rs.next()) {
          Country c = new Country();
          c.setIso2(rs.getString("iso_2"));
          c.setName(rs.getString("name"));
          countryList.addCountry(c);
        }
        rs.close();
        ps.close();
        con.close();
        
    %>
    
    <nav class="navbar navbar-default navbar-fixed-top">
      <div class="container-fluid">
        <div class="navbar-header">
          <a class="navbar-brand" href="#">Best 4 Delivery</a>
        </div>
        <ul class="nav navbar-nav">
          <li class="active"><a href="#">Home</a></li>
          <li><a href="#">Spediteur</a></li>
          <li><a href="#">Anfrage</a></li>
        </ul>
        <button type="button" class="btn btn-warning navbar-btn">Was ist B4D?</button>
        <ul class="nav navbar-nav navbar-right">
          <li><a href="#"><span class="glyphicon glyphicon-user"></span> Registrieren</a></li>
          <li><a href="<%= request.getContextPath() %>/Controller?action=login"><span class="glyphicon glyphicon-log-in"></span> Login</a></li>
        </ul>
      </div>
    </nav>

    <div style="background-image: url(./images/web_home_banner.jpg); background-size:100% auto; background-repeat: no-repeat; height: 400px;">
      <div class="btn-group btn-trans">
        <br/><br/><br/>
        <button type="button" class="btn btn-default">Home</button>
        <button type="button" class="btn btn-default">Spediteur</button>
        <button type="button" class="btn btn-default">Anfrage</button>
        <div class="btn-group">
          <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">Registrieren<span class="caret"></span></button>
          <ul class="dropdown-menu" role="menu">
            <li><a href="#">Als Kunde</a></li>
            <li><a href="#">Als Spediteur</a></li>
          </ul>
        </div>
      </div>    

      <br><br>
      <img alt="B4D" src="images/b4d-logo.png">
      
      <br><br>
      
      <div class="container">
        <div class="row">
        <div class="col-md-1">
          <button type="button" class="btn btn-warning btn-lg">Was ist B4D?</button>
        </div>
        <div class="col-md-10">
        </div>
        <div class="col-md-1">
          <a href="<%= request.getContextPath() %>/Controller?action=login">
            <button type="button" class="btn btn-warning btn-lg">Login</button>
          </a>
        </div>
        </div>
      </div>      
    </div>
    
    <div class="jumbotron dark4">
      <div class="container">  
        <div class="row">
          <form method="post" action="getprice.jsp">
            <div class="col-md-3">
              <b>Transportanfrage stellen:</b>
            </div>
            <div class="col-md-3">
              <div class="form-group">
                <label for="country_depa" class="control-label">Ort von</label>
                <div>
                  <select name="country_depa" class="form-control" id="country_depa">
                    <option value="">Please choose a country</option>
                    <%
                    for ( Country c : countryList.getAllCountries() ) {
                    %>
                    <option value="<%= c.getIso2()%>"><%= c.getName() %></option>
                    <%
                    }
                    %>
                  </select>
                  <input type="text" name="city_depa" id="city_depa" class="form-control" placeholder="Place of Departure">
                </div>
              </div>
            </div>
            <div class="col-md-3">
              <div class="form-group">
                <label for="country_dest" class="control-label">Ort nach</label>
                <div>
                  <select name="country_dest" class="form-control" id="country_dest">
                    <option value="">Please choose a country</option>
                    <%
                    for ( Country c : countryList.getAllCountries() ) {
                    %>
                    <option value="<%= c.getIso2()%>"><%= c.getName() %></option>
                    <%
                    }
                    %>
                  </select>
                  <input type="text" name="city_dest" id="city_dest" class="form-control" placeholder="Place of Destination">
                </div>
              </div>
            </div>
            <div class="col-md-3">
              <button id="button_submit" type="submit" class="btn btn-default">Weiter</button>
            </div>
          </form>
        </div>
      </div>
    </div>
    
    <div class="yellow3">
      <div class="container text-left">
      <div class="row">
        <div class="col-md-6">
          <b>Was sind Ihre Vorteile als Spediteur?</b><br>
          <ul>
            <li>Zusätzlicher kostengünstiger Absatz- und Vertriebskanal</li>
            <li>Keine Abnahmeverpflichtung oder feste Bindung an b4d</li>
            <li>Einsparung von eigenen IT-Investitionskosten</li>
            <li>Neutralität: keine Beteiligung von oder Verträge mit Logistikunternehmen</li>
            <li>Added Value Services (z.B. Reporting)</li>
          </ul>
        </div>
        <div class="col-md-6">
          <b>Was sind Ihre Vorteile als Kunde?</b><br>
          <ul>
            <li>Der Service ist kostenlos!</li>
            <li>Keine mühsamen Anfragen mehr an verschiedene Spediteure - weniger Aufwand und große Zeitersparnis!</li>
            <li>Transparenz - bessere Vergleichbarkeit zwischen den Angeboten!</li>
            <li>Zugriff auf sämtliche Spediteure, die an b4d teilnehmen!</li>
          </ul>
        </div>
      </div>
      </div>
    </div>

    <div class="jumbotron dark4">
      <h2>Info / News</h2>
    </div>
    
      <div class="row yellow3">
        <div class="col-md-12">
          <b>Infos & News zu b4d</b><br><br>
          Abcd efghi jklmnop qrs tuvw xyz. Abcd efghi jklmnop qrs tuvw xyz. Abcd efghi jklmnop qrs tuvw xyz.<br> 
          Abcd efghi jklmnop qrs tuvw xyz. Abcd efghi jklmnop qrs tuvw xyz. Abcd efghi jklmnop qrs tuvw xyz.<br> 
          Abcd efghi jklmnop qrs tuvw xyz. Abcd efghi jklmnop qrs tuvw xyz. Abcd efghi jklmnop qrs tuvw xyz. jklmnop qrs tuvw xyz.<br>
          Abcd efghi jklmnop qrs tuvw xyz. Abcd efghi jklmnop qrs tuvw xyz. Abcd efghi jklmnop qrs tuvw xyz. <br>
          <br>
          <br>
          <br>
        </div>
      </div>

    <div class="jumbotron dark4">
      <h2>Testimonials</h2>
    </div>
    
      <div class="row text-center yellow3">
        <div class="col-md-4">
          <button type="button" class="btn btn-warning btn-lg">
            "b4d hat uns eine <br>große Zeitersparnis eingebracht"
          </button>
          <br><br>
        </div>
        <div class="col-md-4">
          <button type="button" class="btn btn-warning btn-lg">
            "b4d ist für uns ein <br>zusätzlicher kostengünstiger Absatzkanal"
          </button>
        </div>
        <div class="col-md-4">
          <button type="button" class="btn btn-warning btn-lg">
            "b4d ..."
          </button>
        </div>
      </div>

    <div class="jumbotron dark4">
      <div class="container text-center">
        <div class="row">
          <div class="col-md-3">
            <br><br><br><br><br>
            <img alt="B4D" src="images/b4d-logo.png">
          </div>
          <div class="col-md-3">
            <h2>Über uns</h2><hr>
            Über uns<hr>
            Impressum<hr>
            AGB<hr>
          </div>
          <div class="col-md-3">
            <h2>Mehr über B4D</h2><hr>
            Für Spediteure<hr>
            Für Transportkunden<hr>
          </div>
          <div class="col-md-3">
            <h2>Kontakt B4D</h2><hr>
            Adresse AAA<br>
            Adresse BBB<br>
            ZIP City<br>
            +41 61 888 11 11
            <hr>
            info@best4delivery.com
            <hr>
          </div>
        </div>
      </div>
    </div>
  </body>
  

</html>