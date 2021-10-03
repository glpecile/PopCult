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
    <title><spring:message code="profile.requests.title"/> &#8226; PopCult</title>

</head>
<body class="bg-gray-50">
<div class="min-h-screen flex flex-col">
    <jsp:include page="/WEB-INF/jsp/components/navbar.jsp"/>
    <br>
    <div class="col-8 offset-2 flex-grow">
        <h1 class="text-center display-5 fw-bolder py-4">
            <spring:message code="mods.requests.title"/>
        </h1>
        <%--    tabs     --%>
        <jsp:include page="/WEB-INF/jsp/components/modsTabs.jsp">
            <jsp:param name="path" value="modRequests"/>
        </jsp:include>
        <%--     mods requests   --%>
        <c:if test="${requestersContainer.totalCount == 0}">
            <h3 class="text-center text-gray-400 pt-3">
                <spring:message code="mods.request.empty"/>
            </h3>
        </c:if>
        <c:forEach var="request" items="${requestersContainer.elements}">
            <jsp:include page="/WEB-INF/jsp/components/modsRequest.jsp">
                <jsp:param name="userId" value="${request.userId}"/>
                <jsp:param name="name" value="${request.name}"/>
                <jsp:param name="username" value="${request.username}"/>
                <jsp:param name="imageId" value="${request.imageId}"/>
            </jsp:include>
        </c:forEach>
    </div>
    <jsp:include page="/WEB-INF/jsp/components/pageNavigation.jsp">
        <jsp:param name="mediaPages" value="${requestersContainer.totalPages}"/>
        <jsp:param name="currentPage" value="${requestersContainer.currentPage + 1}"/>
        <jsp:param name="url" value="/admin/mods/requests"/>
    </jsp:include>
    <jsp:include page="/WEB-INF/jsp/components/footer.jsp"/>
</div>
</body>
</html>
