<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="j" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <title><spring:message code="profile.passwordChange.title"/> &#8226; PopCult</title>
</head>
<body class="bg-gray-50">
<div class="min-h-screen flex flex-col">
    <jsp:include page="/WEB-INF/jsp/components/navbar.jsp"/>
    <div class="col-8 offset-2 flex-grow">
        <br>
        <c:url value="/changePassword" var="postPath"/>
        <form:form modelAttribute="changePassword" action="${postPath}" method="post"
                   class="g-3 p-4 my-8 bg-white shadow-lg rounded-lg">
            <div class="flex flex-col justify-center items-center">
                <h2 class="text-3xl">
                    <spring:message code="profile.passwordChange.header" arguments="${user.username}"/>
                </h2>
                <br>
                <div class="py-1 text-semibold w-full">
                    <form:label path="currentPassword" cssClass="form-label">
                        <spring:message code="profile.passwordChange.current"/>
                    </form:label>
                    <form:input path="currentPassword" type="password" cssClass="form-control"/>
                    <form:errors path="currentPassword" cssClass="formError text-sm text-red-500" element="p"/>
                </div>
                <div class="py-1 text-semibold w-full">
                    <form:label path="newPassword" cssClass="form-label">
                        <spring:message code="profile.passwordChange.new"/>
                    </form:label>
                    <form:input path="newPassword" type="password" cssClass="form-control"/>
                    <form:errors path="newPassword" cssClass="formError text-sm text-red-500" element="p"/>
                    <form:errors cssClass="formError text-sm text-red-500" element="p"/>
                </div>
                <div class="py-1 text-semibold w-full">
                    <form:label path="repeatPassword" cssClass="form-label">
                        <spring:message code="profile.passwordChange.repeat"/>
                    </form:label>
                    <form:input path="repeatPassword" type="password" cssClass="form-control"/>
                    <form:errors path="repeatPassword" cssClass="formError text-sm text-red-500" element="p"/>
                </div>
            </div>
            <br>
            <div class="flex justify-between">
                    <%--discard changes--%>
                <a href=
                        <c:url value="/settings"/>>
                    <button class="btn btn-light my-2 bg-gray-300 group hover:bg-yellow-400 text-gray-700 font-semibold hover:text-white my-2" type="button">
                        <i class="fas fa-undo group-hover:text-white pr-2"></i>
                        <spring:message code="profile.passwordChange.discard"/>
                    </button>
                </a>
                    <%--save changes--%>
                <button class="btn btn-secondary my-2 bg-gray-300 group hover:bg-green-400 text-gray-700 font-semibold hover:text-white my-2"
                        id="changePass" name="changePass" type="submit">
                    <i class="fas fa-save group-hover:text-white pr-2"></i>
                    <spring:message code="profile.passwordChange.save"/>
                </button>
            </div>
        </form:form>
    </div>
    <jsp:include page="/WEB-INF/jsp/components/footer.jsp"/>
</div>
</body>
</html>
