<%--
  Created by IntelliJ IDEA.
  User: Владислав
  Date: 24.12.13
  Time: 22:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% request.setAttribute("page2", "active");%>
<%@include file="header.jsp" %>


<%
    if (request.getParameter("userId") != null) {
        session.setAttribute("userId", request.getParameter("userId"));
        session.setAttribute("ref", request.getHeader("referer"));
    }
    int userId = -1;
    if (session.getAttribute("userId") != null) {
        userId = Integer.parseInt((String) session.getAttribute("userId"));
    }

    User new_user = service.getUser(userId);
    if (new_user == null) new_user = new User();


    if (request.getMethod().equals("POST") && request.getParameter("loginForm") != null
            && request.getParameter("loginForm").equals("ok")) {

        new_user.setLogin(request.getParameter("login"));
        new_user.setPassword(request.getParameter("password"));
        new_user.setName(request.getParameter("name"));
        new_user.setSureName(request.getParameter("sureName"));
        new_user.setMail(request.getParameter("mail"));
        new_user.setTelephone(request.getParameter("telephone"));
        new_user.setAddress(request.getParameter("address"));
        if (request.getParameter("isNetworkMember") != null) {
            new_user.setNetworkMember(true);
        }
        if (request.getParameter("isVoteCastomer") != null) {
            new_user.setVoteCastomer(true);
        }
        session.setAttribute("userId", null);
        service.saveUser(new_user, manager.getId());
        String refer = (String) session.getAttribute("ref");
        if (refer != null) {
            response.sendRedirect(refer);
        }
    }
%>

<html>
<head>
    <title></title>
</head>
<body>


<table border="0" cellspacing="0" cellpadding="0" align="center">

    <form name="add user" method="post" action="addUser.jsp">
        <tr>
            <td style="text-align: left; min-width: 100px">
                <fmt:message key="User.login"/>
            </td>
            <td td style="text-align: right;">
                <input type="text" name="login" value="<%= new_user.getLogin()%>">
            </td>
        </tr>
        <tr>
            <td style="text-align: left; min-width: 100px">
                <fmt:message key="User.password"/>
            </td>
            <td td style="text-align: right;">
                <input type="password" name="password" value="<%= new_user.getPassword()%>">
            </td>
        </tr>
        <tr>
            <td style="text-align: left; min-width: 100px">
                <fmt:message key="User.name"/>
            </td>
            <td td style="text-align: right;">
                <input type="text" name="name" value="<%= new_user.getName()%>">
            </td>
        </tr>
        <tr>
            <td style="text-align: left; min-width: 100px">
                <fmt:message key="User.sureName"/>
            </td>
            <td td style="text-align: right;">
                <input type="text" name="sureName" value="<%= new_user.getSureName()%>">
            </td>
        </tr>
        <tr>
            <td style="text-align: left; min-width: 100px">
                <fmt:message key="User.mail"/>
            </td>
            <td td style="text-align: right;">
                <input type="text" name="mail" value="<%= new_user.getMail()%>">
            </td>
        </tr>
        <tr>
            <td style="text-align: left; min-width: 100px">
                <fmt:message key="User.telephone"/>
            </td>
            <td td style="text-align: right;">
                <input type="text" name="telephone" value="<%= new_user.getTelephone()%>">
            </td>
        </tr>
        <tr>
            <td style="text-align: left; min-width: 100px">
                <fmt:message key="User.address"/>
            </td>
            <td td style="text-align: right;">
                <input type="text" name="address" value="<%= new_user.getAddress()%>">
            </td>
        </tr>
        <tr>
            <td style="text-align: left; min-width: 100px">
                <fmt:message key="User.isVoteCastomer"/>
            </td>
            <td td style="text-align: right;">
                <input type="checkbox" name="isVoteCastomer" <% if (new_user.isVoteCastomer()) { %> checked <% } %>>
            </td>
        </tr>
        <tr>
            <td style="text-align: left; min-width: 100px">
                <fmt:message key="User.isNetworkMember"/>
            </td>
            <td td style="text-align: right;">
                <input type="checkbox" name="isNetworkMember" <% if (new_user.isNetworkMember()) { %> checked <% } %>>
            </td>
        </tr>
        <tr>
            <td style="text-align: left; min-width: 100px">

            </td>
            <td td style="text-align: right;">
                <input type="submit" name="loginForm" value="ok">
            </td>
        </tr>


    </form>
</table>

</body>
</html>
<%@include file="../footer.jsp" %>
