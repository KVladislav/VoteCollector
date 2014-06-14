<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="org.hillel.it.votecollector.model.entity.User" %>
<%@ page import="org.hillel.it.votecollector.model.entity.Manager" %>
<jsp:useBean id="service" class="org.hillel.it.votecollector.service.VoteCollectorServiceImpl"
             scope="application"></jsp:useBean>


<%--
  Created by IntelliJ IDEA.
  User: Владислав
  Date: 20.12.13
  Time: 0:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%

    //    String lng = (String) session.getAttribute("lang");

//    if (session != null) {
//        session.invalidate();
//    }
//    session = request.getSession(true);
//    session.setAttribute("ref", refer);
//    session.setAttribute("lang", lng);
    session.setAttribute("user", null);
    session.setAttribute("manager", null);


    if (request.getMethod().equals("POST") && request.getParameter("language") != null) {
        String s = request.getParameter("language");
        session.setAttribute("lang", s);
    }
%>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messageBundle" scope="session"/>

<%


    if (request.getMethod().equals("POST") && request.getParameter("loginForm") != null && request.getParameter("loginForm").equals("ok")) {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String refer = (String) session.getAttribute("ref");
        User user = service.getUser(login, password);
        if (user != null) {
            session.setAttribute("user", user);

            if (refer != null) {
                session.setAttribute("ref", null);
                response.sendRedirect(refer);
            } else
                response.sendRedirect("User/userWorkSpace.jsp");
            return;
        }
        Manager manager = service.getManager(login, password);
        if (manager != null) {
            session.setAttribute("manager", manager);
            if (refer != null) {
                session.setAttribute("ref", null);
                response.sendRedirect(refer);
            } else
                response.sendRedirect("Manager/managerWorkSpace.jsp");
            return;
        }
%>
<fmt:message key="login.errorMessage" var="failedLogin"/>
<%
    }
%>


<html>
<head>
    <title>VoteCollector Login page</title>
</head>
<body>


<table width="30%" height="100%" border="0" cellspacing="0" cellpadding="0" align="center">
    <tr>
        <td>
            <div style="vertical-align:middle; text-align:center">
                <img src="res/VoteCollector.png">
            </div>
            <br>

            <form name="Login page" method="post" action="">
                <table width="30%" border="0" cellspacing="0" cellpadding="0" align="center">
                    <tbody>
                    <tr>
                        <td style="text-align: left; min-width: 100px"><strong><fmt:message key="login.login"/></strong>
                        </td>
                        <td style="text-align: right;"><input type="text" name="login" value="root"></td>
                    </tr>
                    <tr>
                        <td style="text-align: left; min-width: 100px"><strong><fmt:message
                                key="login.password"/></strong></td>
                        <td style="text-align: right;"><input type="password" name="password" value="root"></td>
                    </tr>
                    <form name="langSelect" method="post" action="">
                        <tr>
                            <td>English<input type="radio" name="language" value="en" onclick="this.form.submit();">
                            </td>
                            <td>Russian<input type="radio" name="language" value="ru" onclick="this.form.submit();">
                            </td>
                        </tr>
                    </form>
                    <tr>
                        <td></td>
                        <td style="text-align: right;"><input type="submit" name="loginForm" value="ok"></td>
                    </tr>
                    <tr>
                        <td><h5 style="color:red">${failedLogin}</h5></td>

                    </tr>
                    </tbody>
                </table>
            </form>
        </td>
    </tr>
</table>
</body>
</html>

