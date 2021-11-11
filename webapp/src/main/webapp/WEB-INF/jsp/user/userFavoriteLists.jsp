<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="j" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <title><spring:message code="profile.tabs.favLists"/> &#8226; PopCult</title>

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
                    <div class="flex-col flex-wrap p-4 space-x-4">
                        <img class="w-36 object-center mx-auto" src="<c:url value="/resources/images/PopCultLogoExclamation.png"/>"
                             alt="no_results_image">
                        <h3 class="text-center py-2 mt-0.5 text-gray-400">
                            <spring:message code="profile.favLists.noLists"/>
                        </h3>
                    </div>
                </c:if>
                <div class="row">
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
                </div>
                <br>
                <jsp:include page="/WEB-INF/jsp/components/pageNavigation.jsp">
                    <jsp:param name="mediaPages" value="${userFavListsContainer.totalPages}"/>
                    <jsp:param name="currentPage" value="${userFavListsContainer.currentPage + 1}"/>
                    <jsp:param name="url" value="/user/${user.username}/favoriteLists"/>
                </jsp:include>
            </c:when>
            <c:otherwise>
                <c:if test="${userPublicLists.totalCount == 0}">
                    <div class="flex-col flex-wrap p-4 space-x-4">
                        <img class="w-36 object-center mx-auto" src="<c:url value="/resources/images/PopCultLogoExclamation.png"/>"
                             alt="no_results_image">
                        <h3 class="text-center py-2 mt-0.5 text-gray-400">
                            <spring:message code="profile.favLists.otherNoLists"/>
                        </h3>
                    </div>
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
                    <jsp:param name="url" value="/user/${user.username}/favoriteLists"/>
                </jsp:include>
            </c:otherwise>
        </c:choose>
    </div>
    <jsp:include page="/WEB-INF/jsp/components/footer.jsp"/>
</div>
</body>
</html>