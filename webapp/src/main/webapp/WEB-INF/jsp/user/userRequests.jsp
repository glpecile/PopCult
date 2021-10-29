<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="j" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
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
    <title><spring:message code="profile.requests.title"/> &#8226; PopCult</title>

</head>
<body class="bg-gray-50">
<div class="min-h-screen flex flex-col">
    <jsp:include page="/WEB-INF/jsp/components/navbar.jsp"/>
    <br>
    <div class="col-8 offset-2 flex-grow">
        <h2 class="display-5 fw-bolder text-center">
            <spring:message code="profile.panel.title" arguments="${username}"/>
        </h2>
        <jsp:include page="/WEB-INF/jsp/components/notificationTabs.jsp">
            <jsp:param name="username" value="${username}"/>
            <jsp:param name="path" value="requests"/>
        </jsp:include>
        <h1 class="font-bold text-2xl pt-2">
            <spring:message code="profile.requests.title"/>
        </h1>
        <c:if test="${requestContainer.totalCount == 0}">
            <h3 class="text-center text-gray-400 pt-3">
                <spring:message code="profile.requests.none"/>
            </h3>
        </c:if>
        <c:forEach var="request" items="${requestContainer.elements}">
            <jsp:include page="/WEB-INF/jsp/components/request.jsp">
                <jsp:param name="username" value="${request.collaborator.username}"/>
                <jsp:param name="listname" value="${request.mediaList.listName}"/>
                <jsp:param name="listId" value="${request.mediaList.mediaListId}"/>
                <jsp:param name="collabId" value="${request.collabId}"/>
                <jsp:param name="currentUsername" value="${username}"/>
            </jsp:include>
        </c:forEach>
    </div>
    <c:url var="baseURL" value=""/>
    <jsp:include page="/WEB-INF/jsp/components/pageNavigation.jsp">
        <jsp:param name="mediaPages" value="${requestContainer.totalPages}"/>
        <jsp:param name="currentPage" value="${requestContainer.currentPage + 1}"/>
        <jsp:param name="url" value="${baseURL}"/>
    </jsp:include>
    <jsp:include page="/WEB-INF/jsp/components/footer.jsp"/>
</div>
</body>
</html>