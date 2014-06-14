<%--
  Created by IntelliJ IDEA.
  User: Владислав
  Date: 23.12.13
  Time: 15:39
  To change this template use File | Settings | File Templates.
--%>
<%@include file="/checkLoginState.jsp" %>
<% if (manager == null) {
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
                <li class=${page1}><a href='managerWorkSpace.jsp'><span>Home</span></a></li>
                <li class=${page2}><a href='addUser.jsp'><span>Add User</span></a></li>
                <li class=${page3}><a href='userEditor.jsp'><span>Users</span></a></li>
                <li class=${page4}><a href='subjectEditor.jsp'><span>Subjects</span></a></li>
                <li class=${page5}><a href='profileEditor.jsp'><span>Edit Profile</span></a></li>
                <li class='nolink'><a><span>En</span></a></li>
                <li class='nolink'><a><span>Ru</span></a></li>
                <li class='right'><a href='/'><span>Logout</span></a></li>
                <li class='nolink'><a><span>You are: [${LogedUser}]</span></a></li>


            </ul>
        </div>
    </tr>
    </tbody>
</table>


</body>
</html>
