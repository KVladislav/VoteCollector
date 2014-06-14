<%--
  Created by IntelliJ IDEA.
  User: Владислав
  Date: 23.12.13
  Time: 15:39
  To change this template use File | Settings | File Templates.
--%>
<%@include file="/checkLoginState.jsp" %>
<% if (user == null) {
    response.sendRedirect("/");
    return;
}
%>

<html>
<head>
    <link rel="stylesheet" type="text/css" href="/style.css">
    <title></title>
</head>
<body>

<table width="100%" border="0" cellspacing="0" cellpadding="0" align="">
    <tbody>
    <tr>
        <img src="/VoteCollector.png">
    </tr>

    <tr style="text-align: right;">
        <div id='cssmenu'>
            <ul>
                <li class=${page1}><a href='#'><span>New User</span></a></li>
                <li class=${page1}><a href='#'><span>Users</span></a></li>
                <li class=${page1}><a href='#'><span>Subjects</span></a></li>
                <li class=${page1}><a href='#'><span>Edit Profile</span></a></li>
                <li class=${page1}><a href='/'><span>Logout</span></a></li>
                <li class=${page1}><a><span>You are: [${LogedUser}]</span></a></li>


            </ul>
        </div>
    </tr>
    </tbody>
</table>


</body>
</html>
