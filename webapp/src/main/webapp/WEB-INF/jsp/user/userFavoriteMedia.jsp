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
    <title><spring:message code="profile.tabs.favMedia"/> &#8226; PopCult</title>
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
            <jsp:param name="path" value="favMedia"/>
        </jsp:include>
        <%-- current tab --%>
        <c:choose>
            <c:when test="${favoriteMediaContainer.totalCount > 0}">
                <div class="row pb-2">
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
                <jsp:include page="/WEB-INF/jsp/components/pageNavigation.jsp">
                    <jsp:param name="mediaPages" value="${favoriteMediaContainer.totalPages}"/>
                    <jsp:param name="currentPage" value="${favoriteMediaContainer.currentPage + 1}"/>
                    <jsp:param name="url" value="/user/${user.username}/favoriteMedia"/>
                </jsp:include>
            </c:when>
            <c:otherwise>
                <div class="flex-col flex-wrap p-4 space-x-4">
                    <img class="w-36 object-center mx-auto" src="<c:url value="/resources/images/PopCultLogoExclamation.png"/>"
                         alt="no_results_image">
                    <h3 class="text-center py-2 mt-0.5 text-gray-400">
                        <spring:message code="profile.favMedia.noMedia"/>
                    </h3>
                </div>
                <c:if test="${currentUsername == user.username}">
                    <div class="row">
                        <h4 class="text-center py-2 text-gray-400">
                            <spring:message code="profile.favMedia.callToAction"/>
                        </h4>
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
                </c:if>
            </c:otherwise>
        </c:choose>
    </div>
    <jsp:include page="/WEB-INF/jsp/components/footer.jsp"/>
</div>
</body>
</html>