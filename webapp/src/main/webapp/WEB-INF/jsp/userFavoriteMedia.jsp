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
    <title>Favorite Media &#8226; PopCult</title>

</head>
<body class="bg-gray-50">
<jsp:include page="/WEB-INF/jsp/components/navbar.jsp"/>
<sec:authorize access="isAuthenticated()">
    <c:set var="currentUsername">
        <sec:authentication property="principal.username"/>
    </c:set>
</sec:authorize>
<br>
<div class="col-8 offset-2 py-2">
    <%--    profile   --%>
    <jsp:include page="/WEB-INF/jsp/components/profile.jsp">
        <jsp:param name="name" value="${user.name}"/>
        <jsp:param name="username" value="${username}"/>
        <jsp:param name="imageId" value="${user.imageId}"/>
    </jsp:include>
    <%--    tabs     --%>
    <jsp:include page="/WEB-INF/jsp/components/userTabs.jsp">
        <jsp:param name="username" value="${user.username}"/>
        <jsp:param name="path" value="favMedia"/>
    </jsp:include>
    <%-- current tab --%>
    <c:choose>
        <c:when test="${favoriteMediaContainer.totalCount > 0}">
            <div class="row">
                <c:forEach var="media" items="${favoriteMediaContainer.elements}">
                    <div class="col-12 col-sm-12 col-md-6 col-lg-4 col-xl-3 py-2">
                        <jsp:include page="/WEB-INF/jsp/components/card.jsp">
                            <jsp:param name="image" value="${media.image}"/>
                            <jsp:param name="title" value="${media.title}"/>
                            <jsp:param name="releaseDate" value="${media.releaseYear}"/>
                            <jsp:param name="mediaId" value="${media.mediaId}"/>
                        </jsp:include>
                    </div>
                </c:forEach>
            </div>

            <br>
            <jsp:include page="/WEB-INF/jsp/components/pageNavigation.jsp">
                <jsp:param name="mediaPages" value="${favoriteMediaContainer.totalPages}"/>
                <jsp:param name="currentPage" value="${favoriteMediaContainer.currentPage + 1}"/>
                <jsp:param name="url" value="${urlBase}"/>
            </jsp:include>
        </c:when>
        <c:otherwise>
            <div>
                <h3 class="text-center">It seems there is no favorite media to show! :c</h3>
            </div>
            <c:if test="${currentUsername == user.username}">
                <div class="row">
                    <h4 class="text-center py-2">Why dont you check this recommendations out?</h4>
                    <c:forEach var="media" items="${suggestedMediaContainer.elements}">
                        <div class="col-12 col-sm-12 col-md-6 col-lg-4 col-xl-3 py-2">
                            <jsp:include page="/WEB-INF/jsp/components/card.jsp">
                                <jsp:param name="image" value="${media.image}"/>
                                <jsp:param name="title" value="${media.title}"/>
                                <jsp:param name="releaseDate" value="${media.releaseYear}"/>
                                <jsp:param name="mediaId" value="${media.mediaId}"/>
                            </jsp:include>
                        </div>
                    </c:forEach>
                </div>
                <br>
                <jsp:include page="/WEB-INF/jsp/components/pageNavigation.jsp">
                    <jsp:param name="mediaPages" value="${suggestedMediaContainer.totalPages}"/>
                    <jsp:param name="currentPage" value="${suggestedMediaContainer.currentPage + 1}"/>
                    <jsp:param name="url" value="${urlBase}"/>
                </jsp:include>
            </c:if>
        </c:otherwise>
    </c:choose>
</div>
<jsp:include page="/WEB-INF/jsp/components/footer.jsp"/>
</body>
</html>