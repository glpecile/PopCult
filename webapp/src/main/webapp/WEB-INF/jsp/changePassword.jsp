<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="j" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <jsp:include page="/resources/externalResources.jsp"/>
    <!-- favicon -->
    <link rel="shortcut icon" href="<c:url value='/resources/images/favicon.ico'/>" type="image/x-icon">
    <title>Update your Settings &#8226; PopCult</title>
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
                <h2 class="text-3xl">Edit <c:out value="${user.username}"/>'s password</h2>
                <br>

                <div class="py-1 text-semibold w-full">
                    <form:label path="currentPassword" cssClass="form-label">Current Password: </form:label>
                    <form:input path="currentPassword" type="password" cssClass="form-control"/>
                    <form:errors path="currentPassword" cssClass="formError text-sm text-red-500" element="p"/>
                </div>

                <div class="py-1 text-semibold w-full">
                    <form:label path="newPassword" cssClass="form-label">New Password: </form:label>
                    <form:input path="newPassword" type="password" cssClass="form-control"/>
                    <form:errors path="newPassword" cssClass="formError text-sm text-red-500" element="p"/>
                    <form:errors cssClass="formError text-sm text-red-500" element="p"/>
                </div>

                <div class="py-1 text-semibold w-full">
                    <form:label path="repeatPassword" cssClass="form-label">Repeat Password: </form:label>
                    <form:input path="repeatPassword" type="password" cssClass="form-control"/>
                    <form:errors path="repeatPassword" cssClass="formError text-sm text-red-500" element="p"/>
                </div>
            </div>
            <br>
            <div class="flex justify-between">
                    <%--discard changes--%>
                <a href=
                        <c:url value="/settings"/>>
                    <button class="btn btn-light my-2" type="button">Discard</button>
                </a>
                    <%--save changes--%>
                <button class="btn btn-secondary my-2" id="changePass" name="changePass" type="submit">Save</button>
            </div>
        </form:form>
    </div>
    <jsp:include page="/WEB-INF/jsp/components/footer.jsp"/>
</div>
</body>
</html>
