<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <jsp:include page="/resources/externalResources.jsp"/>
    <!-- favicon -->
    <link rel="shortcut icon" href="<c:url value='/resources/images/favicon.ico'/>" type="image/x-icon">
    <title><spring:message code="login.title"/> &#8226; PopCult</title></head>
<body class="bg-gradient-to-r from-yellow-500 to-purple-900">
<%-- Variables --%>
<c:url value="/resetPassword" var="resetPasswordUrl">
    <c:param name="token" value="${param.token}"/>
</c:url>
<div class="min-h-screen flex flex-col">
    <div class="flex-grow space-y-2">
        <%-- Logo and card title --%>
        <a class="flex justify-center items-center pt-16" href="<c:url value="/"/>">
            <img class="w-32" src="<c:url value='/resources/images/PopCultLogo.png'/>" alt="popcult_logo" onclick="this.src='<c:url
                    value='/resources/images/PopCultLogoClosed.png'/>'">
        </a>
        <h2 class="font-bold text-4xl text-center text-white py-2"><spring:message code="resetPassword"/></h2>

        <%-- Forgot password form --%>
        <div class="container w-full max-w-xs mx-auto mt-8 px-4 font-sans rounded-lg shadow-lg bg-white p-2 my-8">
            <form:form class="m-0 p-0" modelAttribute="resetPasswordForm" action="${resetPasswordUrl}" method="post">
                <div class="flex flex-col justify-center items-center">
                    <div class="py-1 px-2.5 text-semibold w-full">
                        <form:label path="newPassword" cssClass="form-label">
                            <spring:message code="register.password"/>
                        </form:label>
                        <form:input path="newPassword" type="password" cssClass="form-control" />
                        <form:errors path="newPassword" cssClass="formError text-sm text-red-500" element="p"/>
                        <form:errors cssClass="formError text-sm text-red-500" element="p"/>
                    </div>
                    <div class="py-1 px-2.5 text-semibold w-full">
                        <form:label path="repeatPassword" cssClass="form-label">
                            <spring:message code="register.repeatPassword"/>
                        </form:label>
                        <form:input path="repeatPassword" type="password" cssClass="form-control"/>
                        <form:errors path="repeatPassword" cssClass="formError text-sm text-red-500" element="p"/>
                    </div>
                    <div class="py-1 px-2.5 text-semibold w-full">
                        <button class="btn btn-secondary px-2.5 mt-2 w-full" type="submit">
                            <spring:message code="resetPassword.button"/>
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


