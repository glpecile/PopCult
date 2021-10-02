<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
<c:url value="/search" var="searchUrl"/>
<body class="bg-gray-50">
<div class="min-h-screen flex flex-col">
    <jsp:include page="/WEB-INF/jsp/components/navbar.jsp"/>
    <div class="col-8 offset-2 flex-grow">
        <c:choose>
            <c:when test="${ 0 < ( searchFilmsContainer.totalCount + listCoversContainer.totalCount)}">
                <div class="row">
                    <h1 class="font-bold text-2xl py-2">There are <c:out
                            value="${searchFilmsContainer.totalCount + listCoversContainer.totalCount}"/>
                        result(s) for <c:out value="${param.term}"/></h1>
                </div>
                <br>
            <div class="col-8 offset-2">
                <div class="row">
                    <h1 class="font-bold text-2xl py-2">There are <c:out value="${ searchFilmsContainer.totalCount}"/> result(s) for <c:out value="${param.term}"/></h1>
                </div>
                <br>
                <form:form cssClass="m-0 p-0" modelAttribute="searchForm" action="${searchUrl}" method="GET">
                    <form:hidden path="term" value="${param.term}"/>
                    <div class="flex text-center">
                        <div class="dropdown pr-4">
                            <button class="btn btn-secondary btn-rounded dropdown-toggle" type="button" id="dropdownMenuButton1" data-bs-toggle="dropdown"
                                    aria-expanded="false">
                                <c:choose>
                                    <c:when test="${param.sortType == null}">
                                        <c:out value="ALL"/>
                                    </c:when>
                                    <c:otherwise>
                                        <c:out value="${param.sortType}"/>
                                    </c:otherwise>
                                </c:choose>
                            </button>
                            <ul class="dropdown-menu shadow-lg" aria-labelledby="dropdownMenuButton1">
                                <form:select path="sortType" items="${sortTypes}"/>
                            </ul>
                        </div>
                        <div class="dropdown pr-4">
                            <button class="btn btn-secondary btn-rounded dropdown-toggle" type="button" id="dropdownMenuButton2" data-bs-toggle="dropdown"
                                    aria-expanded="false">
                                <c:choose>
                                    <c:when test="${fn:length(param.genres) == 0}">
                                        <c:out value="ALL"/>
                                    </c:when>
                                    <c:when test="${fn:length(param.genres) > 1}">
                                        <c:out value="MULTIPLE"/>
                                    </c:when>
                                    <c:otherwise>
                                        <c:out value="${param.genres}"/>
                                    </c:otherwise>
                                </c:choose>
                            </button>
                            <ul class="dropdown-menu shadow-lg" aria-labelledby="dropdownMenuButton2">
<%--                                <c:forEach var="type" items="${genreTypes}">--%>
<%--                                    <li><a class="dropdown-item" href="<spring:url value=""><spring:param name="sort" value="${param.sort}" /><spring:param name="genre" value="${type}" /><spring:param name="term" value="${param.term}"/></spring:url>"><c:out value="${type}"/></a></li>--%>
<%--                                </c:forEach>--%>
                                    <form:checkboxes path="genres" items="${genreTypes}"/>
                            </ul>
                        </div>
                        <button class="btn btn-secondary btn-rounded " type="submit" name="search">
                            <c:out value="SEARCH"/>
                        </button>
                    </div>
                </form:form>

                <c:if test="${searchFilmsContainer.totalCount > 0}">
                    <div class="row">
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
                        <br>
                    </div>
                </c:if>
                <c:if test="${listCoversContainer.totalCount > 0}">
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
                </c:if>
            </c:when>
            <c:otherwise>
                <div class="row">
                    <h1 class="font-bold text-2xl py-2">Sorry, we couldn't find any terms for <c:out value="${param.term}"/></h1>
                </div>
                <br>
            </c:otherwise>
        </c:choose>
                <c:url value="" var="urlBase2">
                    <c:forEach var="p" items="${param}">
                        <c:param name="${p.key}" value="${p.value}"/>
                    </c:forEach>
                </c:url>
                <jsp:include page="/WEB-INF/jsp/components/pageNavigation.jsp">
                    <jsp:param name="mediaPages" value="${searchFilmsContainer.totalPages}"/>
                    <jsp:param name="currentPage" value="${searchFilmsContainer.currentPage + 1}"/>
                    <jsp:param name="url" value="${urlBase2}"/>
                </jsp:include>
    </div>
    <jsp:include page="/WEB-INF/jsp/components/footer.jsp"/>
</div>
</body>
</html>