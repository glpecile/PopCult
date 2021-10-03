<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
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
    <!-- Local CSS -->
    <link rel="stylesheet" href="<c:url value="/resources/css/overflow.css"/>"/>
    <!-- Local Scripts -->
    <script type="text/javascript" src="<c:url value="/resources/js/components/slider.js"/>"></script>
    <title><spring:message code="home.title"/> &#8226; PopCult</title>
</head>

<body class="bg-gray-50">
<div class="min-h-screen flex flex-col">
    <jsp:include page="/WEB-INF/jsp/components/navbar.jsp"/>
    <div class="col-8 offset-2 py-2">
        <!-- Welcome back message -->
        <div class="flex flex-col justify-center items-center py-4 mx-auto">
            <c:set var="authenticated" value="false"/>
            <sec:authorize access="!isAuthenticated()">
                <h1 class="text-center text-3xl">
                    <spring:message code="home.slogan"/>
                </h1>
                <a class="btn btn-secondary bg-purple-500 hover:bg-purple-900 rounded-full shadow-md hover:shadow-lg my-4 w-1/4"
                   href="<c:url value="/register"/>">
                    <spring:message code="home.callToAction"/>
                </a>
            </sec:authorize>
            <sec:authorize access="isAuthenticated()">
                <c:set var="username">
                    <sec:authentication property="principal.username"/>
                    <c:set var="authenticated" value="true"/>
                </c:set>
                <h1 class="text-center text-3xl">
                    <spring:message code="home.loggedGreeting"/>
                </h1>
                <a class="text-center text-5xl font-bold text-purple-500 hover:text-purple-900" aria-current="page"
                   href="<c:url value="/user/${username}"/>">
                        ${username}
                </a>
            </sec:authorize>
        </div>
        <!-- Recent lists -->
        <div class="flex justify-between">
            <h2 class="font-bold text-2xl pt-2">
                <c:choose>
                    <c:when test="${discoveryListsContainer.totalCount == 0 || !authenticated}">
                        <spring:message code="home.title.lists"/>
                    </c:when>
                    <c:otherwise>
                        <spring:message code="home.title.lists.discovery"/>
                    </c:otherwise>
                </c:choose>
            </h2>
            <a href="<c:url value="/lists"/>">
                <button class="btn btn-link text-purple-500 hover:text-purple-900 btn-rounded">
                    <spring:message code="home.viewAll"/>
                </button>
            </a>
        </div>
        <div class="flex flex-col" data-controller="slider">
            <div class="flex py-4 px-2 overflow-x-scroll no-scrollbar" data-slider-target="scrollContainer">
                <c:set var="k" value="1"/>
                <c:choose>
                    <c:when test="${discoveryListsContainer.totalCount == 0 || !authenticated}">
                        <c:forEach var="cover" items="${recentlyAddedLists}">
                            <div class="px-2 col-12 col-sm-12 col-md-6 col-lg-4 col-xl-3" data-slider-target="image"
                                 id="${k}">
                                <c:set var="k" value="${k + 1}"/>
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
                    </c:when>
                    <c:otherwise>
                        <c:forEach var="cover" items="${discoveryListsCovers}">
                            <div class="px-2 col-12 col-sm-12 col-md-6 col-lg-4 col-xl-3" data-slider-target="image"
                                 id="${k}">
                                <c:set var="k" value="${k + 1}"/>
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
                    </c:otherwise>
                </c:choose>
            </div>
            <div class="flex mx-auto my-2">
                <ul class="flex justify-center">
                    <li class="h-3 w-3 rounded-full mx-2 cursor-pointer bg-gray-300 hover:bg-purple-900"
                        data-slider-target="indicator"
                        data-image-id="1"
                        data-action="click->slider#scrollTo"></li>
                    <li class="h-3 w-3 rounded-full mx-2 cursor-pointer bg-gray-300 hover:bg-purple-900"
                        data-slider-target="indicator"
                        data-image-id="2"
                        data-action="click->slider#scrollTo"></li>
                    <li class="h-3 w-3 rounded-full mx-2 cursor-pointer bg-gray-300 hover:bg-purple-900"
                        data-slider-target="indicator"
                        data-image-id="3"
                        data-action="click->slider#scrollTo"></li>
                    <li class="h-3 w-3 rounded-full mx-2 cursor-pointer bg-gray-300 hover:bg-purple-900"
                        data-slider-target="indicator"
                        data-image-id="4"
                        data-action="click->slider#scrollTo"></li>
                    <li class="h-3 w-3 rounded-full mx-2 cursor-pointer bg-gray-300 hover:bg-purple-900"
                        data-slider-target="indicator"
                        data-image-id="5"
                        data-action="click->slider#scrollTo"></li>
                    <li class="h-3 w-3 rounded-full mx-2 cursor-pointer bg-gray-300 hover:bg-purple-900"
                        data-slider-target="indicator"
                        data-image-id="6"
                        data-action="click->slider#scrollTo"></li>
                </ul>
            </div>
        </div>
        <!-- Slider for films -->
        <div class="flex justify-between">
            <h2 class="font-bold text-2xl pt-2">
                <c:choose>
                    <c:when test="${discoveryFilmContainer.totalCount == 0 || !authenticated}">
                        <spring:message code="home.title.films"/>
                    </c:when>
                    <c:otherwise>
                        <spring:message code="home.title.films.discovery"/>
                    </c:otherwise>
                </c:choose>
            </h2>
            <a href="<c:url value="/media/films"/>">
                <button class="btn btn-link text-purple-500 hover:text-purple-900 btn-rounded ">
                    <spring:message code="home.viewAll"/>
                </button>
            </a>
        </div>
        <div class="flex flex-col" data-controller="slider">
            <div class="flex py-4 px-2 overflow-x-scroll no-scrollbar" data-slider-target="scrollContainer">
                <c:set var="i" value="7"/>
                <c:choose>
                    <c:when test="${discoveryFilmContainer.totalCount == 0 || !authenticated}">
                        <c:forEach var="film" items="${latestFilmsList}">
                            <div class="px-2 col-12 col-sm-12 col-md-6 col-lg-4 col-xl-3" data-slider-target="image"
                                 id="${i}">
                                <c:set var="i" value="${i + 1}"/>
                                <jsp:include page="/WEB-INF/jsp/components/card.jsp">
                                    <jsp:param name="image" value="${film.image}"/>
                                    <jsp:param name="title" value="${film.title}"/>
                                    <jsp:param name="releaseDate" value="${film.releaseYear}"/>
                                    <jsp:param name="mediaId" value="${film.mediaId}"/>
                                </jsp:include>
                            </div>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <c:forEach var="film" items="${discoveryFilmContainer.elements}">
                            <div class="px-2 col-12 col-sm-12 col-md-6 col-lg-4 col-xl-3" data-slider-target="image"
                                 id="${i}">
                                <c:set var="i" value="${i + 1}"/>
                                <jsp:include page="/WEB-INF/jsp/components/card.jsp">
                                    <jsp:param name="image" value="${film.image}"/>
                                    <jsp:param name="title" value="${film.title}"/>
                                    <jsp:param name="releaseDate" value="${film.releaseYear}"/>
                                    <jsp:param name="mediaId" value="${film.mediaId}"/>
                                </jsp:include>
                            </div>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>

            </div>
            <div class="flex mx-auto my-2">
                <ul class="flex justify-center">
                    <li class="h-3 w-3 rounded-full mx-2 cursor-pointer bg-gray-300 hover:bg-purple-900"
                        data-slider-target="indicator"
                        data-image-id="7"
                        data-action="click->slider#scrollTo"></li>
                    <li class="h-3 w-3 rounded-full mx-2 cursor-pointer bg-gray-300 hover:bg-purple-900"
                        data-slider-target="indicator"
                        data-image-id="8"
                        data-action="click->slider#scrollTo"></li>
                    <li class="h-3 w-3 rounded-full mx-2 cursor-pointer bg-gray-300 hover:bg-purple-900"
                        data-slider-target="indicator"
                        data-image-id="9"
                        data-action="click->slider#scrollTo"></li>
                    <li class="h-3 w-3 rounded-full mx-2 cursor-pointer bg-gray-300 hover:bg-purple-900"
                        data-slider-target="indicator"
                        data-image-id="10"
                        data-action="click->slider#scrollTo"></li>
                    <li class="h-3 w-3 rounded-full mx-2 cursor-pointer bg-gray-300 hover:bg-purple-900"
                        data-slider-target="indicator"
                        data-image-id="11"
                        data-action="click->slider#scrollTo"></li>
                    <li class="h-3 w-3 rounded-full mx-2 cursor-pointer bg-gray-300 hover:bg-purple-900"
                        data-slider-target="indicator"
                        data-image-id="12"
                        data-action="click->slider#scrollTo"></li>
                </ul>
            </div>
        </div>
        <!-- Slider for series -->
        <div class="flex justify-between">
            <h2 class="font-bold text-2xl pt-2">
                <c:choose>
                    <c:when test="${discoverySeriesContainer.totalCount == 0 || !authenticated}">
                        <spring:message code="home.title.series"/>
                    </c:when>
                    <c:otherwise>
                        <spring:message code="home.title.series.discovery"/>
                    </c:otherwise>
                </c:choose>
            </h2>
            <a href="<c:url value="/media/series"/>">
                <button class="btn btn-link text-purple-500 hover:text-purple-900 btn-rounded ">
                    <spring:message code="home.viewAll"/>
                </button>
            </a>
        </div>
        <div class="flex flex-col" data-controller="slider">
            <div class="flex py-4 px-2 overflow-x-scroll no-scrollbar" data-slider-target="scrollContainer">
                <c:set var="j" value="13"/>
                <c:choose>
                    <c:when test="${discoverySeriesContainer.totalCount == 0 || !authenticated}">
                        <c:forEach var="serie" items="${latestSeriesList}">
                            <div class="px-2 col-12 col-sm-12 col-md-6 col-lg-4 col-xl-3" data-slider-target="image"
                                 id="${j}">
                                <c:set var="j" value="${j + 1}"/>
                                <jsp:include page="/WEB-INF/jsp/components/card.jsp">
                                    <jsp:param name="image" value="${serie.image}"/>
                                    <jsp:param name="title" value="${serie.title}"/>
                                    <jsp:param name="releaseDate" value="${serie.releaseYear}"/>
                                    <jsp:param name="mediaId" value="${serie.mediaId}"/>
                                </jsp:include>
                            </div>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <c:forEach var="serie" items="${discoverySeriesContainer.elements}">
                            <div class="px-2 col-12 col-sm-12 col-md-6 col-lg-4 col-xl-3" data-slider-target="image"
                                 id="${j}">
                                <c:set var="j" value="${j + 1}"/>
                                <jsp:include page="/WEB-INF/jsp/components/card.jsp">
                                    <jsp:param name="image" value="${serie.image}"/>
                                    <jsp:param name="title" value="${serie.title}"/>
                                    <jsp:param name="releaseDate" value="${serie.releaseYear}"/>
                                    <jsp:param name="mediaId" value="${serie.mediaId}"/>
                                </jsp:include>
                            </div>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>

            </div>
            <div class="flex mx-auto my-2">
                <ul class="flex justify-center">
                    <li class="h-3 w-3 rounded-full mx-2 cursor-pointer bg-gray-300 hover:bg-purple-900"
                        data-slider-target="indicator"
                        data-image-id="13"
                        data-action="click->slider#scrollTo"></li>
                    <li class="h-3 w-3 rounded-full mx-2 cursor-pointer bg-gray-300 hover:bg-purple-900"
                        data-slider-target="indicator"
                        data-image-id="14"
                        data-action="click->slider#scrollTo"></li>
                    <li class="h-3 w-3 rounded-full mx-2 cursor-pointer bg-gray-300 hover:bg-purple-900"
                        data-slider-target="indicator"
                        data-image-id="15"
                        data-action="click->slider#scrollTo"></li>
                    <li class="h-3 w-3 rounded-full mx-2 cursor-pointer bg-gray-300 hover:bg-purple-900"
                        data-slider-target="indicator"
                        data-image-id="16"
                        data-action="click->slider#scrollTo"></li>
                    <li class="h-3 w-3 rounded-full mx-2 cursor-pointer bg-gray-300 hover:bg-purple-900"
                        data-slider-target="indicator"
                        data-image-id="17"
                        data-action="click->slider#scrollTo"></li>
                    <li class="h-3 w-3 rounded-full mx-2 cursor-pointer bg-gray-300 hover:bg-purple-900"
                        data-slider-target="indicator"
                        data-image-id="18"
                        data-action="click->slider#scrollTo"></li>
                </ul>
            </div>
        </div>
    </div>
    <jsp:include page="/WEB-INF/jsp/components/footer.jsp"/>
</div>
</body>
</html>
