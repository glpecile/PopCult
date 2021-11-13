<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="j" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <jsp:include page="/resources/externalResources.jsp"/>
    <!-- favicon -->
    <link rel="shortcut icon" href="<c:url value='/resources/images/favicon.ico'/>" type="image/x-icon">
    <title><spring:message code="bans.title"/> &#8226; PopCult</title>
</head>
<body class="bg-gray-50">
<div class="min-h-screen flex flex-col">
    <jsp:include page="/WEB-INF/jsp/components/navbar.jsp"/>
    <div class="col-8 offset-2 flex-grow">
        <!-- Banned Users Panel message -->
        <h1 class="text-center display-5 fw-bolder py-4">
            <spring:message code="bans.title"/>
        </h1>
        <%--  Banned Users  --%>
        <c:if test="${bannedUsersContainer.totalCount == 0}">
            <div class="flex-col flex-wrap p-4 space-x-4">
                <img class="w-36 object-center mx-auto" src="<c:url value="/resources/images/PopCultLogoExclamation.png"/>"
                     alt="no_results_image">
                <h3 class="text-xl text-gray-400 py-2 mt-3 text-center">
                    <spring:message code="bans.empty"/>
                </h3>
            </div>
        </c:if>
        <c:forEach var="bannedUser" items="${bannedUsersContainer.elements}">
            <jsp:include page="/WEB-INF/jsp/components/bannedInfo.jsp">
                <jsp:param name="userId" value="${bannedUser.userId}"/>
                <jsp:param name="name" value="${bannedUser.name}"/>
                <jsp:param name="username" value="${bannedUser.username}"/>
                <jsp:param name="strikes" value="${bannedUser.strikes}"/>
                <jsp:param name="unbanDate" value="${bannedUser.unbanDate}"/>
                <jsp:param name="imageId" value="${bannedUser.imageId}"/>
            </jsp:include>
        </c:forEach>
    </div>
    <jsp:include page="/WEB-INF/jsp/components/pageNavigation.jsp">
        <jsp:param name="mediaPages" value="${bannedUsersContainer.totalPages}"/>
        <jsp:param name="currentPage" value="${bannedUsersContainer.currentPage + 1}"/>
        <jsp:param name="url" value="/admin/bans"/>
    </jsp:include>
    <jsp:include page="/WEB-INF/jsp/components/footer.jsp"/>
</div>
</body>
</html>