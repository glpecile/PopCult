<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!doctype html>
<html lang="en">
<head>
    <jsp:include page="/resources/externalResources.jsp"/>
    <!-- favicon -->
    <link rel="shortcut icon" href="<c:url value='/resources/images/favicon.ico'/>" type="image/x-icon">
    <!-- Local CSS -->
    <link rel="stylesheet" href="<c:url value="/resources/css/overflow.css"/>"/>
    <!-- Local Scripts -->
    <script type="text/javascript" src="<c:url value="/resources/js/components/slider.js"/>"></script>
    <title>Search Results &#8226; PopCult</title>
</head>

<body class="bg-gray-50">
<jsp:include page="/WEB-INF/jsp/components/navbar.jsp"/>

<div class="col-8 offset-2">
    <div class="row">
        <h1 class="font-bold text-2xl py-2">There are <c:out value="${searchSeriesContainer.totalCount + searchFilmsContainer.totalCount + listCoversContainer.totalCount}"/> results for <c:out value="${term}"/></h1>
    </div>
    <br>
    <!-- Search Results of every Film -->
    <div class="row">
        <c:url value="/search/films" var="filmsUrl">
            <c:param name="sort" value="${param.sort}"/>
            <c:param name="term" value="${param.term}"/>
            <c:param name="genre" value="${param.genre}"/>
        </c:url>
        <a href="${filmsUrl}">
            <h2 class="font-bold text-2xl py-2">Search Films Results</h2>
        </a>
        <c:forEach var="media" items="${searchFilmsContainer.elements}">
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
    <!-- Search Results of every Series -->
    <div class="row">
        <c:url value="/search/series" var="seriesUrl">
            <c:param name="sort" value="${param.sort}"/>
            <c:param name="term" value="${param.term}"/>
            <c:param name="genre" value="${param.genre}"/>
        </c:url>
        <a href="${seriesUrl}">
            <h2 class="font-bold text-2xl py-2">Search Series Results</h2>
        </a>
        <c:forEach var="media" items="${searchSeriesContainer.elements}">
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
    <!-- Search Results of every List -->
    <div class="row">
        <c:url value="/search/lists" var="listsUrl">
            <c:param name="sort" value="${param.sort}"/>
            <c:param name="term" value="${param.term}"/>
            <c:param name="genre" value="${param.genre}"/>
        </c:url>
        <a href="${listsUrl}">
            <h2 class="font-bold text-2xl py-2">Search Lists Results</h2>
        </a>
        <c:forEach var="cover" items="${listCoversContainer.elements}">
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
</div>
<jsp:include page="/WEB-INF/jsp/components/footer.jsp"/>
</body>
</html>