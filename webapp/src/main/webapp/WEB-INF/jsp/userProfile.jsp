<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="j" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <jsp:include page="/resources/externalResources.jsp"/>
    <!-- favicon -->
    <link rel="shortcut icon" href="<c:url value='/resources/images/favicon.ico'/>" type="image/x-icon">
    <title>Profile &#8226; PopCult</title>

</head>
<body>
<jsp:include page="/WEB-INF/jsp/components/navbar.jsp"/>
<br>
<div class="col-8 offset-2">
    <%--    profile   --%>
    <jsp:include page="/WEB-INF/jsp/components/profile.jsp">
        <jsp:param name="name" value="${user.name}"/>
        <jsp:param name="username" value="${username}"/>
        <jsp:param name="imageId" value="${user.imageId}"/>
    </jsp:include>
    <%--    tabs     --%>
    <jsp:include page="/WEB-INF/jsp/components/userTabs.jsp">
        <jsp:param name="username" value="${user.username}"/>
        <jsp:param name="path" value="myLists"/>
    </jsp:include>
    <%-- current tab --%>
    <div class="row">
        <%--        <h2 class="font-bold text-2xl py-2">My lists</h2>--%>
        <c:if test="${fn:length(lists) == 0}">
            <h3 class="text-center">It seems there are no favorite lists to show! :c</h3>
        </c:if>
        <sec:authorize access="isAuthenticated()">
            <c:set var="currentUsername">
                <sec:authentication property="principal.username"/>
            </c:set>
        </sec:authorize>
        <c:choose>
        <c:when test="${currentUsername == user.username}">
        <c:forEach var="cover" items="${lists}">
            <div class="col-12 col-sm-12 col-md-6 col-lg-4 col-xl-3 py-2">
                <jsp:include page="/WEB-INF/jsp/components/gridCard.jsp">
                    <jsp:param name="title" value="${cover.name}"/>
                    <jsp:param name="listId" value="${cover.listId}"/>
                    <jsp:param name="image1" value="${cover.image1}"/>
                    <jsp:param name="image2" value="${cover.image2}"/>
                    <jsp:param name="image3" value="${cover.image3}"/>
                    <jsp:param name="image4" value="${cover.image4}"/>
                </jsp:include>
            </div>
        </c:forEach>
    </div>
    <br>
    <jsp:include page="/WEB-INF/jsp/components/pageNavigation.jsp">
        <jsp:param name="mediaPages" value="${userListsContainer.totalPages}"/>
        <jsp:param name="currentPage" value="${userListsContainer.currentPage + 1}"/>
        <jsp:param name="url" value="${urlBase}"/>
    </jsp:include>
    </c:when>
    <c:otherwise>
    <c:forEach var="cover" items="${userPublicListCover}">
        <div class="col-12 col-sm-12 col-md-6 col-lg-4 col-xl-3 py-2">
            <jsp:include page="/WEB-INF/jsp/components/gridCard.jsp">
                <jsp:param name="title" value="${cover.name}"/>
                <jsp:param name="listId" value="${cover.listId}"/>
                <jsp:param name="image1" value="${cover.image1}"/>
                <jsp:param name="image2" value="${cover.image2}"/>
                <jsp:param name="image3" value="${cover.image3}"/>
                <jsp:param name="image4" value="${cover.image4}"/>
            </jsp:include>
        </div>
    </c:forEach>
</div>
<br>
<jsp:include page="/WEB-INF/jsp/components/pageNavigation.jsp">
    <jsp:param name="mediaPages" value="${userPublicLists.totalPages}"/>
    <jsp:param name="currentPage" value="${userPublicLists.currentPage + 1}"/>
    <jsp:param name="url" value="${urlBase}"/>
</jsp:include>
</c:otherwise>
</c:choose>

<jsp:include page="/WEB-INF/jsp/components/footer.jsp"/>
</body>
</html>