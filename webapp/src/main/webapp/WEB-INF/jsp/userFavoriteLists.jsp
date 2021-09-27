<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="j" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <jsp:include page="/resources/externalResources.jsp"/>
    <!-- favicon -->
    <link rel="shortcut icon" href="<c:url value='/resources/images/favicon.ico'/>" type="image/x-icon">
    <title>Favorite Lists &#8226; PopCult</title>

</head>
<body class="bg-gray-50">
<div class="min-h-screen flex flex-col">
    <jsp:include page="/WEB-INF/jsp/components/navbar.jsp"/>
    <sec:authorize access="isAuthenticated()">
        <c:set var="currentUsername">
            <sec:authentication property="principal.username"/>
        </c:set>
    </sec:authorize>
    <br>
    <div class="col-8 offset-2 flex-grow">
        <%--    profile   --%>
        <jsp:include page="/WEB-INF/jsp/components/profile.jsp">
            <jsp:param name="name" value="${user.name}"/>
            <jsp:param name="username" value="${username}"/>
            <jsp:param name="imageId" value="${user.imageId}"/>
        </jsp:include>
        <%--    tabs     --%>
        <jsp:include page="/WEB-INF/jsp/components/userTabs.jsp">
            <jsp:param name="username" value="${user.username}"/>
            <jsp:param name="path" value="favLists"/>
        </jsp:include>
        <%-- current tab --%>
        <c:choose>
            <c:when test="${currentUsername == user.username}">
                <c:if test="${userFavListsContainer.totalCount == 0}">
                    <h3 class="text-center text-gray-400">It seems there are no favorite lists to show! :c</h3>
                </c:if>
                <c:forEach var="cover" items="${favoriteLists}">
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
                <br>
                <jsp:include page="/WEB-INF/jsp/components/pageNavigation.jsp">
                    <jsp:param name="mediaPages" value="${userFavListsContainer.totalPages}"/>
                    <jsp:param name="currentPage" value="${userFavListsContainer.currentPage + 1}"/>
                    <jsp:param name="url" value="${urlBase}"/>
                </jsp:include>
            </c:when>
            <c:otherwise>
                <c:if test="${userPublicLists.totalCount == 0}">
                    <h3 class="text-center text-grey-400">It seems this user has no lists to show! :c</h3>
                </c:if>
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
                <br>
                <jsp:include page="/WEB-INF/jsp/components/pageNavigation.jsp">
                    <jsp:param name="mediaPages" value="${userPublicLists.totalPages}"/>
                    <jsp:param name="currentPage" value="${userPublicLists.currentPage + 1}"/>
                    <jsp:param name="url" value="${urlBase}"/>
                </jsp:include>
            </c:otherwise>
        </c:choose>
    </div>
    <jsp:include page="/WEB-INF/jsp/components/footer.jsp"/>
</div>
</body>
</html>