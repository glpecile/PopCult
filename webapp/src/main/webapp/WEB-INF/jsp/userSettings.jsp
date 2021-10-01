<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="j" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <jsp:include page="/resources/externalResources.jsp"/>
    <!-- favicon -->
    <link rel="shortcut icon" href="<c:url value='/resources/images/favicon.ico'/>" type="image/x-icon">
    <title>
        <spring:message code="profile.settings.title"/> &#8226; PopCult</title>
    <c:url value="/settings" var="postPath"/>
</head>
<body class="bg-gray-50">
<div class="min-h-screen flex flex-col">
    <jsp:include page="/WEB-INF/jsp/components/navbar.jsp"/>
    <div class="col-8 offset-2 flex-grow">
        <br>
        <form:form cssClass="g-3 p-4 my-8 bg-white shadow-lg rounded-lg" modelAttribute="userSettings" action="${postPath}" method="post">
            <div class="flex flex-col justify-center items-center">
                <h2 class="text-3xl">
                    <c:out value="${user.username}"/><spring:message code="profile.settings.header"/>
                </h2>
                <br>
                    <%-- Name Form --%>
                <div class="py-1 text-semibold w-full">
                    <form:label path="name" cssClass="form-label">
                        <spring:message code="profile.settings.name"/>
                    </form:label>
                    <form:input type="text" cssClass="form-control" path="name" value="${user.name}"/>
                    <form:errors path="name" cssClass="formError text-red-500" element="p"/>
                </div>
                    <%-- Username Form --%>
                <div class="py-1 text-semibold w-full">
                    <form:label path="username" cssClass="form-label">
                        <spring:message code="profile.settings.username"/>
                    </form:label>
                    <form:input type="text" cssClass="form-control" path="username" value="${user.username}"/>
                    <form:errors path="username" cssClass="formError text-red-500" element="p"/>
                </div>
                    <%-- Email Form --%>
                <div class="py-1 text-semibold w-full">
                    <form:label path="email" cssClass="form-label">
                        <spring:message code="profile.settings.email"/>
                    </form:label>
                    <form:input type="text" cssClass="form-control" path="email" value="${user.email}"/>
                    <form:errors path="email" cssClass="formError text-red-500" element="p"/>
                </div>
            </div>
            <br>
            <div class="flex justify-between">
                    <%-- Change password --%>
                <a href=<c:url value="/changePassword"/>>
                    <button type="button" class="btn btn-dark my-2">
                        <spring:message code="profile.settings.passwordChange"/>
                    </button>
                </a>
                <div class="flex space-x-3">
                        <%-- Discard changes --%>
                    <a href=<c:url value="/user/${user.username}"/>>
                        <button type="button" class="btn btn-light my-2">
                            <spring:message code="general.revert"/>
                        </button>
                    </a>
                        <%-- Save changes --%>
                    <input type="hidden" name="userId" id="userId" value="<c:out value="${user.userId}"/>">
                    <button class="btn btn-secondary my-2" id="editUser" name="editUser" type="submit">
                        <spring:message code="general.save"/>
                    </button>
                </div>
            </div>
        </form:form>
    </div>
    <jsp:include page="/WEB-INF/jsp/components/footer.jsp"/>
</div>
</body>
</html>
