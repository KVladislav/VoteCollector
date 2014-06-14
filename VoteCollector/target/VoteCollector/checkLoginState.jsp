<%@ page import="org.hillel.it.votecollector.model.entity.Manager" %>
<%@ page import="org.hillel.it.votecollector.model.entity.User" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Владислав
  Date: 24.12.13
  Time: 22:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="service" class="org.hillel.it.votecollector.service.VoteCollectorServiceImpl"
             scope="application"></jsp:useBean>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%


    Manager manager = (Manager) session.getAttribute("manager");
    User user = (User) session.getAttribute("user");

    if (user == null && manager == null) {
        session.setAttribute("ref", request.getRequestURI());
        String redirect = request.getContextPath();
        response.sendRedirect(redirect);
        return;
    }
    if (user != null) {
        request.setAttribute("LogedUser", user.getLogin());
    }
    if (manager != null) {
        request.setAttribute("LogedUser", manager.getLogin());
    }
%>


<html>
<head>
    <title></title>
</head>
<body>

</body>
</html>
