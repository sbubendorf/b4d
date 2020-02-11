    <link href="css/navbar.css" rel="stylesheet">

    <!-- Static navbar -->
    <nav class="navbar navbar-default">
      <div class="container-fluid">
        <div id="navbar" class="navbar-collapse collapse">
          <ul class="nav navbar-nav">
            <li id="menu_home"><a href="index.jsp">Home</a></li>
            <li id="menu_about" ><a href="about.jsp">About</a></li>
            <li id="menu_contact" ><a href="contact.jsp">Contact</a></li>
            <li id="menu_register" class="dropdown">
              <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Register<span class="caret"></span></a>
              <ul class="dropdown-menu">
                <li id="menu_register_customer"><a href="<%=request.getContextPath()%>/Controller?action=register">as Customer</a></li>
                <li id="menu_register_deliverer"><a href="<%=request.getContextPath()%>/Controller?action=register">as Deliverer</a></li>
              </ul>
            </li>
          </ul>
          <ul class="nav navbar-nav navbar-right" id="menu_login">
            <div id="navbar" class="navbar-collapse collapse">
              <form class="navbar-form navbar-right" method="post" action="<%= response.encodeURL(request.getContextPath()) + "/Controller" %>">
                <input type="hidden" name="action" value="doLogin">
                <div class="form-group">
                  <!-- <input name="email" type="email" placeholder="Email" class="form-control" required>  -->
                  <input name="email" type="text" placeholder="Email" class="form-control" required>
                </div>
                <div class="form-group">
                  <input name="password" type="password" placeholder="Password" class="form-control" required>
                </div>
                <button type="submit" class="btn btn-success">Sign in</button>
              </form>
            </div><!--/.navbar-collapse -->
          </ul>
        </div><!--/.nav-collapse -->
      </div><!--/.container-fluid -->
    </nav>

