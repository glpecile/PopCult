<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <%--    <link rel="stylesheet" href="<c:url value="/css/style.css"/>" />--%>
</head>
<body>
<h2>Register</h2>
<c:url value="/register" var="postPath"/>
<form:form modelAttribute="registerForm" action="${postPath}" method="post">
    <div>
        <form:label path="email">Email: </form:label>
        <form:input type="text" path="email"/>
        <form:errors path="email" element="p"/>
    </div>
    <div>
        <form:label path="password">Password: </form:label>
        <form:input type="password" path="password"/>
        <form:errors path="password" element="p"/>
    </div>
    <div>
        <form:label path="repeatPassword">Repeat Password: </form:label>
        <form:input type="password" path="repeatPassword"/>
        <form:errors path="password" element="p"/>
    </div>
    <div>
        <form:label path="username">Username: </form:label>
        <form:input type="text" path="username"/>
        <form:errors path="username" element="p"/>
    </div>
    <div>
        <form:label path="name">Name: </form:label>
        <form:input type="text" path="name"/>
        <form:errors path="name" element="p"/>
    </div>
    <div>
        <input type="submit" value="Register!"/>
    </div>
</form:form>
</body>
</html>
