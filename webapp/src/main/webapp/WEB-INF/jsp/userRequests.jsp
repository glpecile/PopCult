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
                <jsp:param name="username" value="${request.collaboratorUsername}"/>
                <jsp:param name="listname" value="${request.listname}"/>
                <jsp:param name="listId" value="${request.listId}"/>
                <jsp:param name="collabId" value="${request.collabId}"/>
                <jsp:param name="currentUsername" value="${username}"/>
            </jsp:include>
        </c:forEach>
    </div>
    <jsp:include page="/WEB-INF/jsp/components/footer.jsp"/>
</div>
</body>
</html>