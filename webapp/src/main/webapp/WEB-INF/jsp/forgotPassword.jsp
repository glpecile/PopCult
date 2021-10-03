<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <meta property="og:image" content="<c:url value="/resources/images/PopCultCompleteLogo.png"/>">
    <jsp:include page="/resources/externalResources.jsp"/>
    <!-- favicon -->
    <link rel="shortcut icon" href="<c:url value='/resources/images/favicon.ico'/>" type="image/x-icon">
    <title><spring:message code="login.title"/> &#8226; PopCult</title></head>
<body class="bg-gradient-to-r from-yellow-500 to-purple-900">
<%-- Variables --%>
<c:url value="/forgotPassword" var="forgotPasswordUrl"/>
<div class="min-h-screen flex flex-col">
    <div class="flex-grow space-y-2">
        <%-- Logo and card title --%>
        <a class="flex justify-center items-center pt-16" href="<c:url value="/"/>">
            <img class="w-32" src="<c:url value='/resources/images/PopCultLogo.png'/>" alt="popcult_logo" onclick="this.src='<c:url
                    value='/resources/images/PopCultLogoClosed.png'/>'">
        </a>
        <h2 class="font-bold text-4xl text-center text-white py-2">
            <spring:message code="forgotPassword"/>
        </h2>

        <%-- Forgot password form --%>
        <div class="container w-full max-w-xs mx-auto mt-8 px-4 font-sans rounded-lg shadow-lg bg-white p-2 my-8">
            <form:form class="m-0 p-0" modelAttribute="emailForm" action="${forgotPasswordUrl}" method="post">
                <div class="flex flex-col justify-center items-center">
                    <div class="py-1 px-2.5 text-semibold w-full">
                        <form:label path="email" cssClass="form-label">
                            <spring:message code="register.email"/>
                        </form:label>
                        <form:input type="text" cssClass="form-control" path="email"/>
                        <form:errors path="email" cssClass="formError text-sm text-red-500" element="p"/>
                    </div>
                </div>
                <div class="py-1 px-2.5 text-semibold flex justify-between">
                    <a href="<c:url value="/login"/>">
                        <button class="btn btn-dark px-2.5 my-2" type="button">
                            <spring:message code="forgotPassword.cancel"/>
                        </button>
                    </a>
                    <button class="btn btn-secondary px-2.5 my-2" type="submit">
                        <spring:message code="forgotPassword.button"/>
                    </button>
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

