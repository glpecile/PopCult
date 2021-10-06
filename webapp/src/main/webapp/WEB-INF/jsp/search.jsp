<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <meta property="og:image" content="<c:url value="/resources/images/PopCultCompleteLogo.png"/>">
    <jsp:include page="/resources/externalResources.jsp"/>
    <!-- favicon -->
    <link rel="shortcut icon" href="<c:url value='/resources/images/favicon.ico'/>" type="image/x-icon">
    <!-- Local CSS -->
    <link rel="stylesheet" href="<c:url value="/resources/css/overflow.css"/>"/>
    <!-- Local Scripts -->
    <script type="text/javascript" src="<c:url value="/resources/js/components/slider.js"/>"></script>
    <title><spring:message code="search.title"/> &#8226; PopCult</title>
</head>
<c:url value="/search" var="searchUrl"/>
<body class="bg-gray-50">
<div class="min-h-screen flex flex-col">
    <jsp:include page="/WEB-INF/jsp/components/navbar.jsp"/>
    <div class="col-8 offset-2 flex-grow">
        <c:choose>
            <c:when test="${ 0 < ( searchFilmsContainer.totalCount + listCoversContainer.totalCount)}">
                <h1 class="font-bold text-3xl pt-4">
                    <spring:message code="search.results"
                                    arguments="${searchFilmsContainer.totalCount + listCoversContainer.totalCount}, ${param.term}"/>
                </h1>
                <br>
                <form:form cssClass="m-0 p-0" modelAttribute="searchForm" action="${searchUrl}" method="GET">
                    <form:hidden path="term" value="${param.term}"/>
                    <div class="flex flex-row text-center justify-between pb-2.5">
                        <div class="flex space-x-3">
                            <!-- Sort by -->
                            <div class="flex flex-col">
                                <p>
                                    <spring:message code="search.sortBy"/>
                                </p>
                                <form:select cssClass="form-select block" path="sortType" items="${sortTypes}"/>
                            </div>
                            <!-- Media Type -->
                            <div class="dropdown flex flex-col">
                                <p>
                                    <spring:message code="search.mediaTypes"/>
                                </p>
                                <button class="form-select block dropdown-toggle" type="button" id="dropdownType"
                                        data-bs-toggle="dropdown"
                                        aria-expanded="false">
                                    <c:choose>
                                        <c:when test="${fn:length(param.mediaTypes) == 0}">
                                            <spring:message code="search.all"/>
                                        </c:when>
                                        <c:when test="${fn:length(param.mediaTypes) == 1}">
                                            <c:out value="${param.mediaTypes}"/>
                                        </c:when>
                                        <c:otherwise>
                                            <c:forEach items="${paramValues.mediaTypes}" var="type">
                                                <c:out value="${type} "/>
                                            </c:forEach>
                                        </c:otherwise>
                                    </c:choose>
                                </button>
                                <ul class="dropdown-menu rounded-lg shadow-md" aria-labelledby="dropdownType">
                                    <div class="overflow-y-auto h-auto">
                                        <div class="flex flex-col p-2.5 space-y-2">
                                            <form:checkboxes path="mediaTypes" items="${mediaTypes}"/>
                                        </div>
                                    </div>
                                </ul>
                            </div>
                            <!-- Decades -->
                            <div class="flex flex-col">
                                <p><spring:message code="search.decades"/></p>
                                <form:select cssClass="form-select block" path="decade" items="${decades}"/>
                            </div>
                            <!-- Categories -->
                            <div class="dropdown pr-4 flex flex-col">
                                <p>
                                    <spring:message code="search.categories"/>
                                </p>
                                <button class="form-select block dropdown-toggle" id="dropdownMenuButton"
                                        data-bs-toggle="dropdown"
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
                                <ul class="dropdown-menu rounded-lg shadow-md" aria-labelledby="dropdownMenuButton">
                                    <div class="overflow-y-auto h-64">
                                        <div class="flex flex-col p-2.5 space-y-2">
                                            <form:checkboxes path="genres" items="${genreTypes}"/>
                                        </div>
                                    </div>
                                </ul>
                            </div>
                        </div>
                        <!-- Apply and clear buttons -->
                        <div class="flex space-x-3 justify-center py-3">
                            <button class="btn btn-success bg-gray-300 group hover:bg-green-400 text-gray-700 font-semibold hover:text-white"
                                    type="submit">
                                <i class="fas fa-filter group-hover:text-white pr-2"></i>
                                <c:out value="APPLY FILTERS"/>
                            </button>
                            <button class="btn btn-warning bg-gray-300 group hover:bg-red-400 text-gray-700 font-semibold hover:text-white"
                                    type="submit" name="clear" id="clear">
                                <i class="far fa-times-circle group-hover:text-white pr-2"></i>
                                <c:out value="CLEAR FILTERS"/>
                            </button>
                        </div>
                    </div>
                </form:form>
                <!-- Film and series results -->
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
                <!-- Lists results -->
                <c:if test="${listCoversContainer.totalCount > 0}">
                    <!-- Search Results of every List -->
                    <div class="row">
                        <h2 class="font-bold text-2xl py-3">
                            <spring:message code="search.list.results"/>
                        </h2>
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
                <h1 class="font-bold text-2xl py-2">
                    <spring:message code="search.sorry" arguments="${param.term}"/>
                        <%--  Sorry, we couldn't find any terms for <c:out value="${param.term}"/>--%>
                </h1>
                <br>
            </c:otherwise>
        </c:choose>
        <c:url value="" var="urlBase2">
            <c:forEach var="p" items="${param}">
                <c:choose>
                    <c:when test="${p.key eq 'genres'}">
                        <c:forEach var="genre" items="${paramValues.genres}">
                            <c:param name="genres" value="${genre}"/>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <c:param name="${p.key}" value="${p.value}"/>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </c:url>
        <jsp:include page="/WEB-INF/jsp/components/pageNavigation.jsp">
            <jsp:param name="mediaPages" value="${searchFilmsContainer.totalPages}"/>
            <jsp:param name="currentPage" value="${searchFilmsContainer.currentPage + 1}"/>
            <jsp:param name="url" value="${urlBase2}"/>
        </jsp:include>
    </div>
    <jsp:include page="/WEB-INF/jsp/components/footer.jsp">
        <jsp:param name="url" value="${urlBase2}"/>
    </jsp:include>
</div>
</body>
</html>