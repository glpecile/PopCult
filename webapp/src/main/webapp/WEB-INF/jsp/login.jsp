<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<body>
<h2><spring:message code="login.greeting"/></h2>
<c:url value="/login" var="loginUrl"/>
<form action="${loginUrl}" method="post" enctype="application/x-www-form-urlencoded">
    <div>
        <spring:message code="login.email"/>
        <input type="text" name="email"/>
    </div>
    <div>
        <spring:message code="login.password"/>
        <input type="password" name="password"/>
    </div>
    <div>
        <label>
            <input name="rememberme" type="checkbox"/>
            <spring:message code="login.remember_me"/>
        </label>
    </div>
    <div>
        <input type="submit" value="<spring:message code="login.login"/>"/>
    </div>
</form>
</body>
</html>

