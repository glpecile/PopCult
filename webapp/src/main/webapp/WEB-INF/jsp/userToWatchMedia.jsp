<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="j" uri="http://java.sun.com/jsp/jstl/core" %>
<%--TODO CAN BE DELETED--%>
<html>
<head>
    <jsp:include page="/resources/externalResources.jsp"/>
    <!-- favicon -->
    <link rel="shortcut icon" href="<c:url value='/resources/images/favicon.ico'/>" type="image/x-icon">
    <title>To Watch Media &#8226; PopCult</title>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/components/navbar.jsp"/>
<br>
<div class="col-8 offset-2">
    <%--    profile   --%>

    <%--    tabs     --%>
    <jsp:include page="/WEB-INF/jsp/components/userTabs.jsp">
        <jsp:param name="username" value="${user.username}"/>
        <jsp:param name="path" value="toWatchMedia"/>
    </jsp:include>
    <%-- current tab --%>
        <c:choose>
            <c:when test="${mediaCount > 0}">
                <div class="row">
                    <c:forEach var="media" items="${mediaList}">
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
                    <jsp:param name="mediaPages" value="${mediaPages}"/>
                    <jsp:param name="currentPage" value="${currentPage}"/>
                    <jsp:param name="urlBase" value="/"/>
                </jsp:include>
            </c:when>
            <c:otherwise>
                <div class="row">
                    <h3 class="text-center">You don't seem to have any media in your watchlist!</h3>
                    <h4 class="text-center py-2">Why dont you check this recommendations out?</h4>
                    <c:forEach var="media" items="${suggestedMedia}">
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
                    <jsp:param name="mediaPages" value="${suggestedMediaPages}"/>
                    <jsp:param name="currentPage" value="${currentPage}"/>
                    <jsp:param name="url" value="/${user.username}/favoriteMedia?"/>
                </jsp:include>
            </c:otherwise>
        </c:choose>
</div>
<jsp:include page="/WEB-INF/jsp/components/footer.jsp"/>
</body>
</html>