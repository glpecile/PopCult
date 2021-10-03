<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <meta property="og:image" content="<c:url value="/resources/images/PopCultCompleteLogo.png"/>">
    <jsp:include page="/resources/externalResources.jsp"/>
    <!-- favicon -->
    <link rel="shortcut icon" href="<c:url value='/resources/images/favicon.ico'/>" type="image/x-icon">
    <title><spring:message code="register.title"/> &#8226; PopCult</title>
</head>

<body class="bg-gradient-to-r from-yellow-500 to-purple-900">
<c:url value="/register" var="postPath"/>
<div class="min-h-screen flex flex-col justify-center">
    <%-- Logo and card title --%>
    <a class="flex justify-center items-center pt-4" href="<c:url value="/"/>">
        <img class="w-32 scale-105" src="<c:url value='/resources/images/PopCultLogo.png'/>" onclick="this.src='<c:url
                value='/resources/images/PopCultLogoClosed.png'/>'" alt="popcult_logo">
    </a>
    <h2 class="font-bold text-4xl text-center text-white py-2.5">
        <spring:message code="register.greeting"/>
    </h2>
    <div class="flex-grow">
        <%-- Sign Up form --%>
        <div class="container w-full max-w-sm mx-auto p-2.5 font-sans rounded-lg shadow-lg bg-white">
            <form:form class="m-0 p-0" modelAttribute="registerForm" action="${postPath}" method="post">
                <div class="flex flex-col justify-center items-center">
                    <div class="py-1 px-2.5 text-semibold w-full">
                        <form:label path="email" cssClass="form-label">
                            <spring:message code="register.email"/>
                        </form:label>
                        <form:input type="text" cssClass="form-control" path="email"/>
                        <form:errors path="email" cssClass="formError text-sm text-red-500" element="p"/>
                    </div>
                    <div class="py-1 px-2.5 text-semibold w-full">
                        <form:label path="password" cssClass="form-label">
                            <spring:message code="register.password"/>
                        </form:label>
                        <form:input type="password" cssClass="form-control" path="password"/>
                        <form:errors path="password" cssClass="formError text-sm text-red-500" element="p"/>
                        <form:errors cssClass="formError text-sm text-red-500" element="p"/>
                    </div>
                    <div class="py-1 px-2.5 text-semibold w-full">
                        <form:label path="repeatPassword" cssClass="form-label">
                            <spring:message code="register.repeatPassword"/>
                        </form:label>
                        <form:input type="password" cssClass="form-control" path="repeatPassword"/>
                        <form:errors path="repeatPassword" cssClass="formError text-sm text-red-500" element="p"/>
                    </div>
                    <div class="py-1 px-2.5 text-semibold w-full">
                        <form:label path="username" cssClass="form-label">
                            <spring:message code="register.username"/>
                        </form:label>
                        <form:input type="text" cssClass="form-control" path="username"/>
                        <form:errors path="username" cssClass="formError text-sm text-red-500" element="p"/>
                    </div>
                    <div class="py-1 px-2.5 text-semibold w-full">
                        <form:label path="name" cssClass="form-label">
                            <spring:message code="register.name"/>
                        </form:label>
                        <form:input type="text" cssClass="form-control" path="name"/>
                        <form:errors path="name" cssClass="formError text-sm text-red-500" element="p"/>
                    </div>
                    <div class="py-1 px-2.5 text-semibold w-full">
                        <button class="btn btn-secondary px-2.5 mt-2 w-full" type="submit">
                            <spring:message code="register.default"/>
                        </button>
                    </div>
                </div>
            </form:form>
        </div>
    </div>
    <div class="text-white">
        <jsp:include page="/WEB-INF/jsp/components/footer.jsp"/>
    </div>
</div>
</body>
</html>
