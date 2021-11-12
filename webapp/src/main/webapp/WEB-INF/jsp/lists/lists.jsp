<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
    <title><spring:message code="lists.title"/> &#8226; PopCult</title>
</head>
<c:url var="url" value=""/>
<c:url value="/lists/new" var="createListPath"/>
<body class="bg-gray-50">
<jsp:include page="/WEB-INF/jsp/components/navbar.jsp"/>
<br>
<div class="col-8 offset-2">
    <div class="row">
        <div class="flex justify-between">
            <%-- Popular Lists --%>
            <h2 class="font-bold text-2xl py-2">
                <spring:message code="lists.popular"/>
            </h2>
            <%-- Create List Button --%>
            <a href=${createListPath}>
                <button class="btn btn-link my-2.5 text-purple-500 hover:text-purple-900 btn-rounded">
                    <spring:message code="lists.create"/>
                </button>
            </a>
        </div>
        <div class="flex flex-col" data-controller="slider">
            <div class="flex py-4 px-2 overflow-x-scroll no-scrollbar" data-slider-target="scrollContainer">
                <c:set var="i" value="1"/>
                <c:forEach var="cover" items="${mostLikedLists}">
                    <div class="px-2 col-12 col-sm-12 col-md-6 col-lg-4 col-xl-3" data-slider-target="image" id="${i}">
                        <c:set var="i" value="${i + 1}"/>
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
            <div class="flex mx-auto my-2">
                <ul class="flex justify-center">
                    <li class="h-3 w-3 rounded-full mx-2 cursor-pointer bg-gray-300" data-slider-target="indicator" data-image-id="1"
                        data-action="click->slider#scrollTo"></li>
                    <li class="h-3 w-3 rounded-full mx-2 cursor-pointer bg-gray-300" data-slider-target="indicator" data-image-id="2"
                        data-action="click->slider#scrollTo"></li>
                    <li class="h-3 w-3 rounded-full mx-2 cursor-pointer bg-gray-300" data-slider-target="indicator" data-image-id="3"
                        data-action="click->slider#scrollTo"></li>
                    <li class="h-3 w-3 rounded-full mx-2 cursor-pointer bg-gray-300" data-slider-target="indicator" data-image-id="4"
                        data-action="click->slider#scrollTo"></li>
                    <li class="h-3 w-3 rounded-full mx-2 cursor-pointer bg-gray-300" data-slider-target="indicator" data-image-id="5"
                        data-action="click->slider#scrollTo"></li>
                    <li class="h-3 w-3 rounded-full mx-2 cursor-pointer bg-gray-300" data-slider-target="indicator" data-image-id="6"
                        data-action="click->slider#scrollTo"></li>
                </ul>
            </div>
        </div>
        <div class="row">
            <h2 class="font-bold text-2xl py-2">
                <spring:message code="lists.explore"/>
            </h2>
            <c:set var="sortTypes" value="${sortTypes}" scope="request"/>
            <c:set var="genreTypes" value="${genreTypes}" scope="request"/>
            <c:set var="decadesType" value="${decadesType}" scope="request"/>
            <jsp:include page="/WEB-INF/jsp/components/filters.jsp">
                <jsp:param name="url" value="${url}"/>
            </jsp:include>


            <c:choose>
                <c:when test="${fn:length(allListContainer.elements) == 0}">
                    <br>
                    <h3 class="text-center">
                        <spring:message code="films.noMedia"/>
                    </h3>
                </c:when>
                <c:otherwise>
                    <c:forEach var="cover" items="${allLists}">
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
                    <br>
                    <c:url value="" var="paginationUrl">
                        <c:forEach var="p" items="${param}">
                            <c:choose>
                                <c:when test="${p.key eq 'genres'}">
                                    <c:forEach var="genre" items="${paramValues.genres}">
                                        <c:param name="genres" value="${genre}"/>
                                    </c:forEach>
                                </c:when>
                                <c:when test="${p.key eq 'page'}"/>
                                <c:otherwise>
                                    <c:param name="${p.key}" value="${p.value}"/>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </c:url>
                    <jsp:include page="/WEB-INF/jsp/components/pageNavigation.jsp">
                        <jsp:param name="mediaPages" value="${allListContainer.totalPages}"/>
                        <jsp:param name="currentPage" value="${allListContainer.currentPage + 1}"/>
                        <jsp:param name="url" value="${paginationUrl}"/>
                    </jsp:include>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>
<jsp:include page="/WEB-INF/jsp/components/footer.jsp"/>
</body>
</html>
