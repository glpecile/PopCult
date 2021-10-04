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
        <h1 class="font-bold text-2xl py-2">
            <spring:message code="search.results"
                            arguments="${searchFilmsContainer.totalCount + listCoversContainer.totalCount}, ${param.term}"/>
                <%--                        There are <c:out value="${searchFilmsContainer.totalCount + listCoversContainer.totalCount}"/>--%>
                <%--                        result(s) for <c:out value="${param.term}"/>--%>
        </h1>
        <br>
        <div class="col-12">
            <form:form cssClass="m-0 p-0" modelAttribute="searchForm" action="${searchUrl}" method="GET">
                <form:hidden path="term" value="${param.term}"/>
                <div class="flex text-center justify-between">
                    <div class="flex flex-col">
                        <span>
                            <spring:message code="search.sortBy"/>
                        </span>
                        <form:select cssClass="form-select block w-full" path="sortType" items="${sortTypes}"/>
                    </div>
                    <div class="dropdown pr-4 flex flex-col">
                        <span>
                            <spring:message code="search.mediaTypes"/>
                        </span>
                        <button class="btn btn-secondary btn-rounded dropdown-toggle" type="button" id="dropdownType"
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
                        <ul class="dropdown-menu shadow-lg" aria-labelledby="dropdownType">
                            <div class="overflow-y-auto h-32">
                                <div class="flex flex-col space-y-2.5">
                                    <form:checkboxes path="mediaTypes" items="${mediaTypes}"/>
                                </div>
                            </div>
                        </ul>
                    </div>
                    <div class="flex flex-col">
                        <span><spring:message code="search.decades"/></span>
                        <form:select cssClass="form-select block w-full" path="decade" items="${decades}"/>
                    </div>
                    <div class="dropdown pr-4 flex flex-col">
                        <span>
                            <spring:message code="search.categories"/>
                        </span>
                        <button class="btn btn-secondary btn-rounded dropdown-toggle" type="button" id="dropdownGenre"
                                data-bs-toggle="dropdown"
                                aria-expanded="false">
                            <c:choose>
                                <c:when test="${fn:length(param.genres) == 0}">
                                    <spring:message code="search.all"/>
                                </c:when>
                                <c:when test="${fn:length(param.genres) > 1}">
                                    <spring:message code="search.multiple"/>
                                </c:when>
                                <c:otherwise>
                                    <c:out value="${param.genres}"/>
                                </c:otherwise>
                            </c:choose>
                        </button>
                        <ul class="dropdown-menu shadow-lg" aria-labelledby="dropdownGenre">
                            <div class="overflow-y-auto h-32">
                                <div class="flex flex-col space-y-2.5">
                                    <form:checkboxes path="genres" items="${genreTypes}"/>
                                </div>
                            </div>
                        </ul>
                    </div>
                    <button class="btn btn-secondary btn-rounded " type="submit">
                        <spring:message code="search.applyFilters"/>
                    </button>
                    <button class="btn btn-secondary btn-rounded " type="submit" name="clear" id="clear">
                        <spring:message code="search.clearFilters"/>
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
                        <h2 class="font-bold text-2xl py-2 text-purple-500 hover:text-purple-900">
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
    </div>
    <jsp:include page="/WEB-INF/jsp/components/footer.jsp">
        <jsp:param name="url" value="${urlBase2}"/>
    </jsp:include>
</div>
</body>
</html>