<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%
    request.setCharacterEncoding("utf-8");
%>
<html>
<head>
    <meta charset="utf-8">
    <title>用户登录</title>
</head>
<body>
<%--@elvariable id="users" type="com.validator.Users"--%>
<sf:form modelAttribute="users" method="post">
    用户名:<sf:input path="username"/><sf:errors path="username"/>
    <br>
    密码:<sf:input path="password"/><sf:errors path="password"/>
    <br>
    昵称:<sf:input path="nickname"/><sf:errors path="nickname"/>
    <br>
    <input type="submit" value="提交">
</sf:form>
</body>
</html>
