<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
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
    <title><spring:message code="films.title"/> &#8226; PopCult</title>
</head>
<body class="bg-gray-50">
<jsp:include page="/WEB-INF/jsp/components/navbar.jsp"/>
<c:choose>
    <c:when test="${fn:length(mediaListContainer.elements) == 0}">
        <br>
        <h3 class="text-center">
            <spring:message code="films.noMedia"/>
        </h3>
    </c:when>
    <c:otherwise>
        <div class="col-8 offset-2">
            <br>
            <h4 class="font-bold text-2xl pt-2">
                <spring:message code="films.popular"/>
            </h4>
            <div class="flex flex-col" data-controller="slider">
                <div class="flex py-4 px-2 overflow-x-scroll no-scrollbar" data-slider-target="scrollContainer">
                    <c:set var="i" value="1"/>
                    <c:forEach var="latestFilm" items="${mostLikedFilms}">
                        <div class="px-2 col-12 col-sm-12 col-md-6 col-lg-4 col-xl-3" data-slider-target="image" id="${i}">
                            <c:set var="i" value="${i + 1}"/>
                            <jsp:include page="/WEB-INF/jsp/components/card.jsp">
                                <jsp:param name="image" value="${latestFilm.image}"/>
                                <jsp:param name="title" value="${latestFilm.title}"/>
                                <jsp:param name="releaseDate" value="${latestFilm.releaseYear}"/>
                                <jsp:param name="mediaId" value="${latestFilm.mediaId}"/>
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
                <h4 class="font-bold text-2xl pt-2">
                    <spring:message code="films.explore"/>
                </h4>
                <c:forEach var="media" items="${mediaListContainer.elements}">
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
                <jsp:param name="mediaPages" value="${mediaListContainer.totalPages}"/>
                <jsp:param name="currentPage" value="${mediaListContainer.currentPage + 1}"/>
                <jsp:param name="url" value="${urlBase}"/>
            </jsp:include>
        </div>
    </c:otherwise>
</c:choose>
<jsp:include page="/WEB-INF/jsp/components/footer.jsp"/>
</body>
</html>
