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
    <title>
        <spring:message code="profile.settings.title"/> &#8226; PopCult</title>
    <c:url value="/settings" var="postPath"/>
</head>
<body class="bg-gray-50">
<div class="min-h-screen flex flex-col">
    <jsp:include page="/WEB-INF/jsp/components/navbar.jsp"/>
    <div class="col-8 offset-2 flex-grow">
        <form:form cssClass="g-3 p-4 my-8 bg-white shadow-lg rounded-lg" modelAttribute="userSettings" action="${postPath}" method="post">
            <div class="flex flex-col justify-center items-center">
                <h2 class="text-3xl mb-3">
                    <spring:message code="profile.settings.header" arguments="${user.username}"/>
                </h2>
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
                    <form:input type="text" cssClass="form-control" path="username" value="${user.username}" disabled="true"/>
                    <form:errors path="username" cssClass="formError text-red-500" element="p"/>
                </div>
                    <%-- Email Form --%>
                <div class="py-1 text-semibold w-full">
                    <form:label path="email" cssClass="form-label">
                        <spring:message code="profile.settings.email"/>
                    </form:label>
                    <form:input type="text" cssClass="form-control" path="email" value="${user.email}" disabled="true"/>
                    <form:errors path="email" cssClass="formError text-red-500" element="p"/>
                </div>
            </div>
            <br>
            <div class="flex justify-between">
                    <%-- Change password --%>
                <a href=<c:url value="/changePassword"/>>
                    <button type="button"
                            class="btn btn-dark my-2 bg-gray-300 group hover:bg-purple-400 text-gray-700 font-semibold hover:text-white">
                        <i class="fas fa-unlock-alt group-hover:text-white pr-2"></i>
                        <spring:message code="profile.settings.passwordChange"/>
                    </button>
                </a>
                <button type="button" data-bs-toggle="modal" data-bs-target="#deleteUserModal"
                        class="btn btn-dark my-2 bg-gray-300 group hover:bg-red-400 text-gray-700 font-semibold hover:text-white">
                    <i class="fas fa-unlock-alt group-hover:text-white pr-2"></i>
                    <spring:message code="profile.settings.deleteUser"/>
                </button>
                    <%--                </a>--%>
                <div class="flex space-x-3">
                        <%-- Discard changes --%>
                    <a href=<c:url value="/user/${user.username}"/>>
                        <button type="button"
                                class="btn btn-warning bg-gray-300 group hover:bg-yellow-400 text-gray-700 font-semibold hover:text-white my-2">
                            <i class="fas fa-undo group-hover:text-white pr-2"></i>
                            <spring:message code="general.revert"/>
                        </button>
                    </a>
                        <%-- Save changes --%>
                    <button class="btn btn-success bg-gray-300 hover:bg-green-400 text-gray-700 font-semibold hover:text-white my-2"
                            id="editUser" name="editUser" type="submit">
                        <i class="fas fa-save group-hover:text-white pr-2"></i>
                        <spring:message code="general.save"/>
                    </button>
                </div>
            </div>
        </form:form>
    </div>
    <%-- Delete User Modal --%>
    <div class="modal fade" id="deleteUserModal" tabindex="-1" aria-labelledby="deleteUserModalLabel"
         aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title font-bold text-2xl" id="deleteUserModalLabel">
                        <spring:message code="profile.settings.deleteUser.header"/>
                    </h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <spring:message code="profile.settings.deleteUser.modal"/>
                </div>
                <div class="modal-footer">
                    <c:url value="/deleteUser" var="deletePath"/>
                    <form:form action="${deletePath}" method="DELETE">
                        <button type="submit"
                                class="btn btn-danger bg-gray-300 group hover:bg-red-400 text-gray-700 font-semibold hover:text-white">
                            <i class="fas fa-user-alt-slash group-hover:text-white pr-2" aria-hidden="true"></i>
                            <spring:message code="profile.settings.deleteUser"/>
                        </button>
                    </form:form>
                </div>
            </div>
        </div>
    </div>
    <jsp:include page="/WEB-INF/jsp/components/footer.jsp"/>
</div>
</body>
</html>
