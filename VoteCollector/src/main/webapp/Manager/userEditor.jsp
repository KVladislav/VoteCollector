<%--
  Created by IntelliJ IDEA.
  User: Владислав
  Date: 24.12.13
  Time: 22:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="java.util.List" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% request.setAttribute("page3", "active");%>
<%@include file="header.jsp" %>
<%
    List<User> users;

    if (request.getMethod().equals("POST") && request.getParameter("Action").equals("Delete")) {
        service.deleteUser(Integer.parseInt(request.getParameter("userId")), manager.getId());
    }
    users = service.getUsersByManager(manager.getId());
    request.setAttribute("users", users);
%>


<html>
<head>
    <title></title>
</head>
<body>
<table border="1" cellspacing="1" cellpadding="1" align="center">
    <tr>
        <td>Id</td>
        <td><fmt:message key="User.login"/></td>
        <td><fmt:message key="User.name"/></td>
        <td><fmt:message key="User.sureName"/></td>
        <td><fmt:message key="User.mail"/></td>
        <td><fmt:message key="User.telephone"/></td>
        <td><fmt:message key="User.isNetworkMember"/></td>
        <td><fmt:message key="User.isVoteCastomer"/></td>
        <td>edit</td>
        <td>delete</td>
    </tr>
    <c:forEach var="state" items="${users}">
        <tr>
            <td>
                <c:out value="${state.id}"/>
            </td>
            <td>
                <c:out value="${state.login}"/>
            </td>
            <td>
                <c:out value="${state.name}"/>
            </td>
            <td>
                <c:out value="${state.sureName}"/>
            </td>
            <td>
                <c:out value="${state.mail}"/>
            </td>
            <td>
                <c:out value="${state.telephone}"/>
            </td>
            <td>
                <c:out value="${state.networkMember}"/>
            </td>
            <td>
                <c:out value="${state.voteCastomer}"/>
            </td>
            <td>
                <form name="editUser" method="post" action="addUser.jsp">
                    <input type="hidden" name="userId" value="${state.id}">
                    <input type="submit" name="Action" value="Edit">
                </form>
            </td>
            <td>
                <form name="deleteUser" method="post" action="">
                    <input type="hidden" name="userId" value="${state.id}">
                    <input type="submit" name="Action" value="Delete">
                </form>
            </td>

        </tr>

    </c:forEach>
</table>


</body>
</html>
<%@include file="../footer.jsp" %>
