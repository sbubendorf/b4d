<%@page import="javax.sql.DataSource"%>
<%@page import="javax.naming.Context"%>
<%@page import="javax.naming.InitialContext"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.sql.*"%>
<%@page import="java.util.*"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
try {
    InitialContext initContext = new InitialContext();
    Context env = (Context) initContext.lookup("java:comp/env");
    DataSource ds = (DataSource) env.lookup("jdbc/b4d");
    Connection con = ds.getConnection();

    String term = (String) request.getParameter("name");

    String select = "select * from city where country_cd = ? and lower(city_name) like ? limit 5";
    PreparedStatement ps = con.prepareStatement(select);
    ps.setString(1, "CH");
    ps.setString(2, "%" + term.toLowerCase() + "%");
    ResultSet rs = ps.executeQuery();

    int count = 1;
    while (rs.next()) {
        out.print(rs.getString("city_name") + "\n");
        if (count >= 5)// 5=How many results have to show while we are typing(auto suggestions)
            break;
        count++;
    }

    rs.close();
    ps.close();
    con.close();

} catch (Exception e) {
    e.printStackTrace();
}

%>