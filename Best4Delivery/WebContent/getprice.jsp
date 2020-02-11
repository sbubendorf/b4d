<%@page import="util.BaseParam"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

  <head>

    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ include file="mod/headerdefaults.jsp" %>

    <title>B4D Start - Get your price</title>
    
    <style type="text/css">
      .form-horizontal .control-label{
         text-align:left; 
      }
    </style>
    
  </head>
  
  <body style="text-align: center;">
  
    <script type="text/javascript">
    
      $(document).ready(function() {
        
        $("#city_depa, #city_dest").autocomplete({
          width         : 500,
          max           : 8,
          delay         : 100,
          minLength     : 3,
          autoFocus     : true,
          cacheLength   : 1,
          highlight     : false,
          source        : function(request, response) {
            var elemCity = this.element;
            var elemCountry = this.element.closest(".panel-body").find("select");
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
            	var country = $(this).closest(".panel-body").find("select");
              $(this).val(ui.item.name);
              if ( $(country).val() == "" ) {
                $(country).val(ui.item.country);
              }
              $("#" + $(this).attr("id") + "_id").val(ui.item.id);
            } else {
              $(this).val("");
            }
          },
        });
      
        $('#form_get_price').bootstrapValidator({
        	feedbackIcons: {
        	  valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
          },
          fields: {
        	  country_depa: {
        		  validators: {
        			  notEmpty: {
        				  message: "The country of departure is mandatory!"
        			  }
        		  }
        	  },
            city_depa: {
              validators: {
                notEmpty: {
                  message: "The city of departure is mandatory!"
                }
              }
            },
            country_dest: {
              validators: {
                notEmpty: {
                  message: "The country of destination is mandatory!"
                }
              }
            },
            city_dest: {
              validators: {
                notEmpty: {
                  message: "The city of destination is mandatory!"
                }
              }
            },
        	  weight: {
        		  validators: {
        			  notEmpty: {
        				  message: 'The weight of your freight is required!'
                },
                regexp: {
                	message: 'The weight must be given as a numeric value!',
                  regexp: /^[1-9][0-9]*$/
                },
                callback: {
                	message: 'Your freight is too heavy (max <%= BaseParam.MAX_WEIGHT %> kg)!',
                  callback: function(value, validator, $field) {
                    return parseInt(value) == 'NaN' || value <= <%= BaseParam.MAX_WEIGHT %>;
                  }
                }
              }
            },
            width: {
              validators: {
                notEmpty: {
                  message: 'The width of your freight is required!'
                },
                regexp: {
                  message: 'The width must be given as a numeric amount of centimeters!',
                  regexp: /^[1-9][0-9]*$/
                },
                callback: {
                    message: 'Your freight is too wide! (max. <%= BaseParam.DIM_MAX_WIDTH %> cm)',
                    callback: function(value, validator, $field) {
                      return value <= <%= BaseParam.DIM_MAX_WIDTH %>;
                    }
                  }
              }
            },
            length: {
              validators: {
                notEmpty: {
                  message: 'The length of your freight is required!'
                },
                regexp: {
                  message: 'The length must be given as a numeric amount of centimeters!',
                  regexp: /^[1-9][0-9]*$/
                },
                callback: {
                  message: 'Your freight is too long! (max. <%= BaseParam.DIM_MAX_LENGTH %> cm)',
                  callback: function(value, validator, $field) {
                    return value <= <%= BaseParam.DIM_MAX_LENGTH %>;
                  }
                }
              }
            },
            height: {
              validators: {
                notEmpty: {
                  message: 'The height of your freight is required!'
                },
                regexp: {
                  message: 'The height must be given as a numeric amount of centimeters!',
                  regexp: /^[1-9][0-9]*$/
                },
                callback: {
                  message: 'Your freight is too high! (max. <%= BaseParam.DIM_MAX_HEIGHT%> cm)',
                  callback: function(value, validator, $field) {
                    return value <= <%= BaseParam.DIM_MAX_HEIGHT %>;
                  }
                }
              }
            },
            count: {
              validators: {
                notEmpty: {
                  message: "The number of pieces of your freight is mandatory!"
                }
              }
            },
            description: {
              validators: {
                notEmpty: {
                  message: "The description of your freight is mandatory!"
                }
              }
            },
            danger_goods: {
              validators: {
                notEmpty: {
                  message: 'Please specify the risk of your goods!'
                },
                callback: {
                  message: 'The transport of dangerous goods cannot be booked with Best4Delivery - Sorry!',
                  callback: function(value, validator, $field) {
                    return value == 0;
                  }
                }
              }
            },
            disclaimer_accept: {
              validators: {
                notEmpty: {
                  message: 'Please accept the disclaimer!'
                },
                callback: {
                  message: 'You must accept the disclaimer to continue!',
                  callback: function(value, validator, $field) {
                    return value == 1;
                  }
                }
              }
            }
          }
        });      
        
/*
        // Clear all form fields
         $('#form_get_price').find("input,textarea,select")
           .val('')
           .end()
           .find("input[type=checkbox], input[type=radio]")
             .prop("checked", "")
             .end();        
*/

        $("#menu_get_price").addClass("active");
        $("#country_depa").focus();
        //newFreight();
        $("[name='count']").each(function(){
        	checkStackable(this);
        });

        $("#pickupdate").datepicker("option", "maxDate", "+2W");
        $("#pickupdate").datepicker("option", "numberOfMonths", "3");
        
        console.log("maxDate : " + $("#pickupdate").datepicker("option", "maxDate"));

      });
      
      function newFreight() {
    	  var $div = $("div[id^='freight_']:last");
    	  console.log("Cloning DIV with id " + $div.prop("id"));
    	  var num = parseInt( $div.prop("id").match(/\d+/g), 10 ) + 1;
    	  var $clon = $div.clone().prop('id', 'freight_' + num );
    	  console.log("New Freight Div created wiht ID " + $clon.prop("id"));
    	  $clon.insertAfter($div);
    	  $clon.show();
      }
      
      function checkStackable(input) {
    	  var $div = $(input).closest("div[id^='freight_']");
    	  var counter = $div.find("#count").val();
    	  var height  = $div.find("#height").val();
        if ( counter == 1 || height > (<%= BaseParam.DIM_MAX_HEIGHT %>/2) ) {
          $div.find("#stackable_div").hide();
        } else {
          $div.find("#stackable_div").show();
        }
      }
        
    </script>

    <%@ include file="mod/menuapp.jsp" %>
    
    <div class="row">
      <div class="col-md-12" >
        <h2>Specify your Delivery</h2>
      </div>
    </div>
    
    <jsp:useBean id="countryList" class="beans.CountryList" scope="application"></jsp:useBean>

    <form id="form_get_price" method="post" class="form-horizontal" action="<%= response.encodeURL(request.getContextPath()) %>/Controller" >
    
      <input type="hidden" name="action" value="getPrice">

      <div class="row">
        <div class="col-md-1" ></div>
        <div class="col-md-4" >
          <div class="panel panel-default">
            <div class="panel-heading">Place of departure</div>
            <div id="body_departure" class="panel-body">
              <div class="form-group">
                <label for="country_depa" class="col-sm-2 control-label">Country</label>
                <div class="col-sm-10">
                  <select name="country_depa" class="form-control" id="country_depa">
                    <option></option>
                    <c:forEach var="item" items="${countryList.getSupportedCountriesB4D()}">
                      <option value="${item.iso2}">${item.name}</option>
                    </c:forEach>
                  </select>
                </div>
              </div>
              <div class="form-group">
                <label for="city_depa" class="col-sm-2 control-label">City</label>
                <div class="col-sm-10">
                  <input type="text" name="city_depa" id="city_depa" class="form-control" placeholder="Place of Departure">
                </div>
              </div>
              <div class="form-group">
                <label for="city_depa_id" class="col-sm-2 control-label">City ID</label>
                <div class="col-sm-10">
                  <input type="text" name="city_depa_id" id="city_depa_id" class="form-control" placeholder="ID of departure city">
                </div>
              </div>
            </div>
          </div>          
        </div>
        <div class="col-md-2">
          <img alt="Delivery" src="images/pfeil_schild.gif" style="width: 80%; height: 80%"/>
        </div>
        <div class="col-md-4" >
          <div class="panel panel-default">
            <div class="panel-heading">Place of destination</div>
            <div class="panel-body">
              <div class="form-group">
                <label for="country_dest" class="col-sm-2 control-label">Country</label>
                <div class="col-sm-10">
                  <select name="country_dest" class="form-control" id="country_dest">
                    <option></option>
                    <c:forEach var="item" items="${countryList.getSupportedCountriesB4D()}">
                      <option value="${item.iso2}">${item.name}</option>
                    </c:forEach>
                  </select>
                </div>
              </div>
              <div class="form-group">
                <label for="city_dest" class="col-sm-2 control-label">City</label>
                <div class="col-sm-10">
                  <input type="text" name="city_dest" id="city_dest" class="form-control" placeholder="Place of Destination">
                </div>
              </div>
              <div class="form-group">
                <label for="city_dest_id" class="col-sm-2 control-label">City ID</label>
                <div class="col-sm-10">
                  <input type="text" name="city_dest_id" id="city_dest_id" class="form-control" placeholder="ID of destination city">
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="col-md-1"></div>
      </div>
    
      <div class="row" id="freight_0">
        <div class="col-md-1"></div>
        <div class="col-md-10">
          <div class="panel panel-default">
            <div class="panel-heading">Freight Specification</div>
            <div class="panel-body">
              <div class="form-group">
                <label class="col-md-2 control-label">
                  Dimension:
                </label>
                <div class="col-md-3">
                  Length<input type="text" name="length" id="length" class="form-control" placeholder="cm">
                </div>
                <div class="col-md-3">
                  Width<input type="text" name="width" id="width" class="form-control" placeholder="cm">
                </div>
                <div class="col-md-3">
                  Height<input type="text" name="height" id="height" class="form-control" placeholder="cm" onchange="checkStackable(this);">
                </div>
              </div>
              <div class="form-group">
                <label for="weight" class="col-md-2 control-label">Weight:</label>
                <div class="col-md-2">
                  <input type="text" name="weight" id="weight" class="form-control" placeholder="kg">
                </div>
                <label for="weight" class="col-md-1 control-label">kg</label>
                <label for="count" class="col-md-1 control-label">Count:</label>
                <div class="col-md-2">
                  <select name="count" class="form-control" id="count" onchange="checkStackable(this);">
                    <%
                    for ( int i = 1 ; i <= 50 ; i++ ) {
                    %>
                      <option value="<%=i%>"><%=i%></option>
                    <%
                    }
                    %>
                  </select>
                </div>
                <div id="stackable_div">
                  <label for="stackable" class="col-md-1 control-label">Stackable:</label>
                  <div class="col-md-2">
                    <select name="stackable" class="form-control" id="stackable">
                      <option value=0>No</option>
                      <option value=1>Yes</option>
                    </select>
                  </div>
                </div>
              </div>
              <div class="form-group">
                <label for="description" class="col-md-2 control-label">Description:</label>
                <div class="col-md-9">
                  <input type="text" name="description" id="description" class="form-control" placeholder="Description of the package / goods)">
                </div>
              </div>
              <div class="form-group">
                <label for="danger_goods" class="col-md-2 control-label">Dangerous Goods:</label>
                <div class="col-md-9">
                  <select name="danger_goods" class="form-control" id="danger_goods">
                    <option value=0>No</option>
                    <option value=1>Yes</option>
                  </select>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="col-md-1"></div>
        &nbsp;
        </div>
      </div>
      
      <div class="row" id="schedule">
        <div class="col-md-1"></div>
        <div class="col-md-10">
          <div class="panel panel-default">
            <div class="panel-heading">Choose a desired pick-up date</div>
            <div class="panel-body">
              <div class="form-group">
                <label for="pickupdate" class="col-md-2 control-label">Select your desired pick-up date:</label>
                <div class="col-md-9">
                  <input type="text" id="pickupdate" class="form-control" placeholder="Pickup Date">
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="col-md-1"></div>
        &nbsp;
        </div>
      </div>
      
      <div class="row" id="incoterms">
        <div class="col-md-1"></div>
        <div class="col-md-10">
          <div class="panel panel-default">
            <div class="panel-heading">Select your Incoterms</div>
            <div class="panel-body">
              <img alt="This page is under construction" src="images/under_construction_05.gif">
            </div>
          </div>
        </div>
        <div class="col-md-1"></div>
        &nbsp;
        </div>
      </div>
      
      <div class="row" id="disclaimer">
        <div class="col-md-1"></div>
        <div class="col-md-10">
          <div class="panel panel-default">
            <div class="panel-heading">Disclaimer</div>
            <div class="panel-body">
              <p>
                Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.              
              </p>
              <p><b>Ein-/Ausschluss von Leistungen:</b></p>
              <p>
                <ul>
                  <li>Normales Stückgut, normale Laufzeiten, Express ausgeschlossen</li>
                  <li>Versicherung nicht inbegriffen</li>
                  <li>etc.</li>
                </ul>
              </p>
              <div class="form-group">
                <label for="disclaimer_accept" class="col-md-1 control-label">Accept:</label>
                <div class="col-md-2">
                  <select name="disclaimer_accept" class="form-control" id="disclaimer_accept">
                    <option></option>
                    <option value=0>No</option>
                    <option value=1>Yes</option>
                  </select>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="col-md-1"></div>
        &nbsp;
        </div>
      </div>
      
      <div class="form-group">
        <div class="col-sm-offset-2 col-sm-8">
          <button id="button_submit" type="submit" class="btn btn-default">Get your price</button>
        </div>
      </div>

      

    </form>
    
  </body>
  

</html>