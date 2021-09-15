<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="j" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <jsp:include page="/resources/externalResources.jsp"/>
    <!-- favicon -->
    <link rel="shortcut icon" href="<c:url value='/resources/images/favicon.ico'/>" type="image/x-icon">
    <title>Favorite Media &#8226; PopCult</title>

</head>
<body>
<jsp:include page="/WEB-INF/jsp/components/navbar.jsp">
    <jsp:param name="user" value="${user.username}"/>
</jsp:include>
<br>
<div class="col-8 offset-2 py-2">
    <%--    tabs     --%>
    <jsp:include page="/WEB-INF/jsp/components/userTabs.jsp">
        <jsp:param name="username" value="${user.username}"/>
        <jsp:param name="path" value="favMedia"/>
    </jsp:include>
    <%-- current tab --%>
    <c:choose>
        <c:when test="${favoriteAmount > 0}">
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
                <h2 class="font-bold text-2xl py-2">Favorite Media</h2>
                <h3 class="text-center">You don't seem to have any favorite media to show!</h3>
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
                <jsp:param name="urlBase" value="/"/>
            </jsp:include>
        </c:otherwise>
    </c:choose>
</div>
<jsp:include page="/WEB-INF/jsp/components/footer.jsp"/>
</body>
</html>