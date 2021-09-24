<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
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
<c:url value="" var="nextUrl">
    <c:forEach var="p" items="${param}">
        <c:param name="${p.key}" value="${p.value}"/>
    </c:forEach>
</c:url>
<div class="col-8 offset-2">
    <div class="row">
        <h1 class="font-bold text-2xl py-2">There are <c:out value="${ searchSeriesContainer.totalCount}"/> result(s) for <c:out value="${term}"/></h1>
    </div>
    <br>
    <div class="flex text-center">
        <div class="dropdown pr-4">
            <button class="btn btn-secondary btn-rounded dropdown-toggle" type="button" id="dropdownMenuButton1" data-bs-toggle="dropdown"
                    aria-expanded="false">
                <c:out value="${param.sort}"/>
            </button>
            <ul class="dropdown-menu shadow-lg" aria-labelledby="dropdownMenuButton1">
                <c:forEach var="type" items="${sortTypes}">
                    <li><a class="dropdown-item" href="<spring:url value=""><spring:param name="sort" value="${type}" /><spring:param name="genre" value="${param.genre}" /><spring:param name="term" value="${param.term}"/></spring:url>"><c:out value="${type}"/></a></li>
                </c:forEach>
            </ul>
        </div>
        <div class="dropdown pr-4">
            <button class="btn btn-secondary btn-rounded dropdown-toggle" type="button" id="dropdownMenuButton2" data-bs-toggle="dropdown"
                    aria-expanded="false">
                <c:out value="${param.genre}"/>
            </button>
            <ul class="dropdown-menu shadow-lg" aria-labelledby="dropdownMenuButton2">
                <c:forEach var="type" items="${genreTypes}">
                    <li><a class="dropdown-item" href="<spring:url value=""><spring:param name="sort" value="${param.sort}" /><spring:param name="genre" value="${type}" /><spring:param name="term" value="${param.term}"/></spring:url>"><c:out value="${type}"/></a></li>
                </c:forEach>
            </ul>
        </div>
    </div>

    <!-- Search Results of every Film -->
    <div class="row">
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
    <jsp:include page="/WEB-INF/jsp/components/pageNavigation.jsp">
        <jsp:param name="mediaPages" value="${searchSeriesContainer.totalPages}"/>
        <jsp:param name="currentPage" value="${searchSeriesContainer.currentPage + 1}"/>
        <jsp:param name="url" value="${nextUrl}"/>
    </jsp:include>
</div>
<jsp:include page="/WEB-INF/jsp/components/footer.jsp"/>
</body>
</html>
