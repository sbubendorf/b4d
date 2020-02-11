
    <link href="css/navbar.css" rel="stylesheet">

    <%@ page import="beans.User" %>
    
    <!-- Static navbar -->
    <nav class="navbar navbar-default">
      <div class="container-fluid">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="#">Best 4 Delivery</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
          <ul class="nav navbar-nav">
            <% if ( ((User)session.getAttribute("user")).isMover() || ((User)session.getAttribute("user")).isAdmin())  {%>
            <li id="menu_set_price"><a href="setprice.jsp">Prices</a></li>
            <li id="menu_set_countries"><a href="Controller?action=getCountries">Countries</a></li>
            <li id="menu_set_zones"><a href="setzones.jsp">Zones</a></li>
            <li id="menu_settings"><a href="settings.jsp">Mover Settings</a></li>
            <% } %>
            <% if ( !((User)session.getAttribute("user")).isMover() || ((User)session.getAttribute("user")).isAdmin())  {%>
            <li id="menu_get_price"><a href="getprice.jsp">Get a price</a></li>
            <% } %>
            <li id="menu_profile"><a href="profile.jsp">User Profile</a></li>
            <li id="menu_admin"><a href="admin.jsp">Administration</a></li>
          </ul>
          <ul class="nav navbar-nav navbar-right">
            <div id="navbar" class="navbar-collapse collapse">
              <form id="form_logoff" name="form_logoff" class="navbar-form navbar-right" method="post" action="<%= response.encodeURL(request.getContextPath()) + "/Controller?action=doLogoff" %>">
                <input type="hidden" name="action" value="doLogoff">
                <%=((User)session.getAttribute("user")).getUserName() %>                
                <button type="submit" class="btn btn-success">Sign off</button>
              </form>
            </div><!--/.navbar-collapse -->
          </ul>
        </div><!--/.nav-collapse -->
      </div><!--/.container-fluid -->
    </nav>
    
    <% 
      String message = (String) request.getAttribute("message");
      if ( message != null && message.length() > 0 ) {
    %>    
    <div id="message_box" class="alert alert-<%= request.getAttribute("messageType") %> ">
      <strong><%= request.getAttribute("message") %></strong>
    </div>
    <script type="text/javascript">
      <%
      if ( "success".equals(request.getAttribute("messageType")) ) {
      %>
        $("#message_box").delay(5000).slideUp(200, function() {
            $(this).alert('close');
        });
      <%
      }
      %>
    </script>
 
    <%
      }
    %>