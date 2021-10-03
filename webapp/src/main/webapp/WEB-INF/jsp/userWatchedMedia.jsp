<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="j" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <title><spring:message code="profile.tabs.watchedMedia"/> &#8226; PopCult</title>
</head>
<body class="bg-gray-50">
<div class="min-h-screen flex flex-col">
    <jsp:include page="/WEB-INF/jsp/components/navbar.jsp"/>
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
            <jsp:param name="path" value="WatchedMedia"/>
        </jsp:include>
        <%-- current tab --%>
        <div class="row">
            <c:if test="${watchedMediaIdsContainer.totalCount == 0}">
                <h3 class="text-center text-gray-400">
                    <spring:message code="profile.watchedMedia.noMedia"/>
                </h3>
            </c:if>
            <c:forEach var="media" items="${watchedMediaIdsContainer.elements}">
                <div class="col-12 col-sm-12 col-md-6 col-lg-4 col-xl-3 py-2">
                    <jsp:include page="/WEB-INF/jsp/components/card.jsp">
                        <jsp:param name="image" value="${media.image}"/>
                        <jsp:param name="title" value="${media.title}"/>
                        <jsp:param name="releaseDate" value="${media.releaseYear}"/>
                        <jsp:param name="mediaId" value="${media.mediaId}"/>
                    </jsp:include>
                        <%--                <jsp:param name="lastWatched" value="${media.lastWatched}"/>--%>
                        <%--                <jsp:param name="listOwner" value="${username}"/>--%>
                    <div class="pt-4">
                        <jsp:include page="/WEB-INF/jsp/components/editWatchDate.jsp">
                            <jsp:param name="lastWatched" value="${media.lastWatched}"/>
                            <jsp:param name="listOwner" value="${username}"/>
                            <jsp:param name="mediaTitle" value="${media.title}"/>
                            <jsp:param name="id" value="${media.mediaId}"/>
                            <jsp:param name="listOwnerId" value="${user.userId}"/>
                        </jsp:include>
                    </div>
                </div>
            </c:forEach>
        </div>
        <br>
        <jsp:include page="/WEB-INF/jsp/components/pageNavigation.jsp">
            <jsp:param name="mediaPages" value="${watchedMediaIdsContainer.totalPages}"/>
            <jsp:param name="currentPage" value="${watchedMediaIdsContainer.currentPage + 1}"/>
            <jsp:param name="url" value="${urlBase}"/>
        </jsp:include>
    </div>
    <jsp:include page="/WEB-INF/jsp/components/footer.jsp"/>
</div>
</body>
</html>