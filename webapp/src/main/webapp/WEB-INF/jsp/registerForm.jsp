<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <jsp:include page="/resources/externalResources.jsp"/>
    <!-- favicon -->
    <link rel="shortcut icon" href="<c:url value='/resources/images/favicon.ico'/>" type="image/x-icon">
    <title>Register &#8226; PopCult</title>
</head>

<body>
<c:url value="/register" var="postPath"/>
<div class="flex flex-col h-full bg-gradient-to-r from-yellow-500 to-purple-900">
    <div class="flex-grow">
        <%-- Logo and card title --%>
        <a class="flex justify-center items-center pt-4" href="<c:url value="/"/>">
            <img class="w-32" src="<c:url value='/resources/images/PopCultLogo.png'/>" onclick="this.src='<c:url
                    value='/resources/images/PopCultLogoClosed.png'/>'" alt="popcult_logo">
        </a>
        <h2 class="font-bold text-4xl text-center text-white py-2"><spring:message code="register.default"/></h2>
    </div>
    <%-- Sign Up form --%>
    <div class="container w-full max-w-xs mx-auto px-4 font-sans rounded-lg shadow-lg bg-white">
        <form:form class="space-y-2" modelAttribute="registerForm" action="${postPath}" method="post">
            <div class="flex flex-col justify-center items-center">
                <div class="py-1 text-semibold w-full">
                    <form:label path="email" cssClass="form-label">Email: </form:label>
                    <form:input type="text" cssClass="form-control" path="email"/>
                    <form:errors path="email" cssClass="formError text-red-500" element="p"/>
                </div>
                <div class="py-1 text-semibold w-full">
                    <form:label path="password" cssClass="form-label">Password: </form:label>
                    <form:input type="password" cssClass="form-control" path="password"/>
                    <form:errors path="password" cssClass="formError text-red-500" element="p"/>
                </div>
                <div class="py-1 text-semibold w-full">
                    <form:label path="repeatPassword" cssClass="form-label">Repeat Password: </form:label>
                    <form:input type="password" cssClass="form-control" path="repeatPassword"/>
                    <form:errors path="password" cssClass="formError text-red-500" element="p"/>
                </div>
                <div class="py-1 text-semibold w-full">
                    <form:label path="username" cssClass="form-label">Username: </form:label>
                    <form:input type="text" cssClass="form-control" path="username"/>
                    <form:errors path="username" cssClass="formError text-red-500" element="p"/>
                </div>
                <div class="py-1 text-semibold w-full">
                    <form:label path="name" cssClass="form-label">Name: </form:label>
                    <form:input type="text" cssClass="form-control" path="name"/>
                    <form:errors path="name" cssClass="formError text-red-500" element="p"/>
                </div>

                <button class="btn btn-secondary my-2 w-full" type="submit"><spring:message code="register.default"/></button>
            </div>
        </form:form>
    </div>
    <div class="text-white">
        <jsp:include page="/WEB-INF/jsp/components/footer.jsp"/>
    </div>
</div>
</body>
</html>
