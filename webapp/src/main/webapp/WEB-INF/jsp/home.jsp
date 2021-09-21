<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
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
    <title>Film and Series Discovery &#8226; PopCult</title>
</head>

<body class="bg-gray-50">
<jsp:include page="/WEB-INF/jsp/components/navbar.jsp"/>

<div class="col-8 offset-2 py-2">
    <!-- Welcome back message -->
    <div class="flex flex-col justify-center items-center py-4 mx-auto">
        <sec:authorize access="!isAuthenticated()">
            <h1 class="text-center text-3xl">
                A site for film and series lovers...
            </h1>
            <a class="btn btn-secondary rounded-full shadow-lg my-4 w-1/4"
               href="<c:url value="/register"/>">
                Get started - sign up!
            </a>
        </sec:authorize>
        <sec:authorize access="isAuthenticated()">
            <c:set var="username">
                <sec:authentication property="principal.username"/>
            </c:set>
            <h1 class="text-center text-3xl">
                Welcome back...
            </h1>
            <a class="text-center text-5xl font-bold" aria-current="page"
               href="<c:url value="/user/${username}"/>">
                    ${username}
            </a>
        </sec:authorize>
    </div>
    <!-- Recent lists -->
    <div class="flex justify-between">
        <h2 class="font-bold text-2xl pt-2">Recently added lists</h2>
        <a href="<c:url value="/lists"/>">
            <button class="btn btn-link text-purple-500 hover:text-purple-900 btn-rounded">View All</button>
        </a>
    </div>
    <div class="flex flex-col" data-controller="slider">
        <div class="flex py-4 px-2 overflow-x-scroll no-scrollbar" data-slider-target="scrollContainer">
            <c:set var="k" value="1"/>
            <c:forEach var="cover" items="${recentlyAddedLists}">
                <div class="px-2 col-12 col-sm-12 col-md-6 col-lg-4 col-xl-3" data-slider-target="image" id="${k}">
                    <c:set var="k" value="${k + 1}"/>
                        <%--            <div class="col-12 col-sm-12 col-md-6 col-lg-4 col-xl-3 py-3">--%>
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
    <!-- Slider for films -->
    <div class="flex justify-between">
        <h2 class="font-bold text-2xl pt-2">Recently Added Films</h2>
        <a href="<c:url value="/media/films"/>">
            <button class="btn btn-link text-purple-500 hover:text-purple-900 btn-rounded ">View All</button>
        </a>
    </div>
    <div class="flex flex-col" data-controller="slider">
        <div class="flex py-4 px-2 overflow-x-scroll no-scrollbar" data-slider-target="scrollContainer">
            <c:set var="i" value="7"/>
            <c:forEach var="film" items="${latestFilmsList}">
                <div class="px-2 col-12 col-sm-12 col-md-6 col-lg-4 col-xl-3" data-slider-target="image" id="${i}">
                    <c:set var="i" value="${i + 1}"/>
                    <jsp:include page="/WEB-INF/jsp/components/card.jsp">
                        <jsp:param name="image" value="${film.image}"/>
                        <jsp:param name="title" value="${film.title}"/>
                        <jsp:param name="releaseDate" value="${film.releaseYear}"/>
                        <jsp:param name="mediaId" value="${film.mediaId}"/>
                    </jsp:include>
                </div>
            </c:forEach>
        </div>
        <div class="flex mx-auto my-2">
            <ul class="flex justify-center">
                <li class="h-3 w-3 rounded-full mx-2 cursor-pointer bg-gray-300" data-slider-target="indicator" data-image-id="7"
                    data-action="click->slider#scrollTo"></li>
                <li class="h-3 w-3 rounded-full mx-2 cursor-pointer bg-gray-300" data-slider-target="indicator" data-image-id="8"
                    data-action="click->slider#scrollTo"></li>
                <li class="h-3 w-3 rounded-full mx-2 cursor-pointer bg-gray-300" data-slider-target="indicator" data-image-id="9"
                    data-action="click->slider#scrollTo"></li>
                <li class="h-3 w-3 rounded-full mx-2 cursor-pointer bg-gray-300" data-slider-target="indicator" data-image-id="10"
                    data-action="click->slider#scrollTo"></li>
                <li class="h-3 w-3 rounded-full mx-2 cursor-pointer bg-gray-300" data-slider-target="indicator" data-image-id="11"
                    data-action="click->slider#scrollTo"></li>
                <li class="h-3 w-3 rounded-full mx-2 cursor-pointer bg-gray-300" data-slider-target="indicator" data-image-id="12"
                    data-action="click->slider#scrollTo"></li>
            </ul>
        </div>
    </div>
    <!-- Slider for series -->
    <div class="flex justify-between">
        <h2 class="font-bold text-2xl pt-2">Recently Added Series</h2>
        <a href="<c:url value="/media/series"/>">
            <button class="btn btn-link text-purple-500 hover:text-purple-900 btn-rounded ">View All</button>
        </a>
    </div>
    <div class="flex flex-col" data-controller="slider">
        <div class="flex py-4 px-2 overflow-x-scroll no-scrollbar" data-slider-target="scrollContainer">
            <c:set var="j" value="13"/>
            <c:forEach var="serie" items="${latestSeriesList}">
                <%--                <div class="w-96 h-64 px-4 flex-shrink-0" data-slider-target="image">--%>
                <div class="px-2 col-12 col-sm-12 col-md-6 col-lg-4 col-xl-3" data-slider-target="image" id="${j}">
                    <c:set var="j" value="${j + 1}"/>
                    <jsp:include page="/WEB-INF/jsp/components/card.jsp">
                        <jsp:param name="image" value="${serie.image}"/>
                        <jsp:param name="title" value="${serie.title}"/>
                        <jsp:param name="releaseDate" value="${serie.releaseYear}"/>
                        <jsp:param name="mediaId" value="${serie.mediaId}"/>
                    </jsp:include>
                </div>
            </c:forEach>
        </div>
        <div class="flex mx-auto my-2">
            <ul class="flex justify-center">
                <li class="h-3 w-3 rounded-full mx-2 cursor-pointer bg-gray-300" data-slider-target="indicator" data-image-id="13"
                    data-action="click->slider#scrollTo"></li>
                <li class="h-3 w-3 rounded-full mx-2 cursor-pointer bg-gray-300" data-slider-target="indicator" data-image-id="14"
                    data-action="click->slider#scrollTo"></li>
                <li class="h-3 w-3 rounded-full mx-2 cursor-pointer bg-gray-300" data-slider-target="indicator" data-image-id="15"
                    data-action="click->slider#scrollTo"></li>
                <li class="h-3 w-3 rounded-full mx-2 cursor-pointer bg-gray-300" data-slider-target="indicator" data-image-id="16"
                    data-action="click->slider#scrollTo"></li>
                <li class="h-3 w-3 rounded-full mx-2 cursor-pointer bg-gray-300" data-slider-target="indicator" data-image-id="17"
                    data-action="click->slider#scrollTo"></li>
                <li class="h-3 w-3 rounded-full mx-2 cursor-pointer bg-gray-300" data-slider-target="indicator" data-image-id="18"
                    data-action="click->slider#scrollTo"></li>
            </ul>
        </div>
    </div>
</div>
<jsp:include page="/WEB-INF/jsp/components/footer.jsp"/>
</body>
</html>
