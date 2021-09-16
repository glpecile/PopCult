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
    <title>Film and Series Discovery &#8226; PopCult</title>
</head>

<body class="bg-gray-50">
<jsp:include page="/WEB-INF/jsp/components/navbar.jsp"/>

<div class="col-8 offset-2 py-2">
    <!-- Slider for films -->
    <h2 class="font-bold text-2xl pt-2">Recently Added Films</h2>
    <div class="flex flex-col" data-controller="slider">
        <div class="flex py-4 px-2 overflow-x-scroll no-scrollbar" data-slider-target="scrollContainer">
            <c:set var="i" value="1"/>
            <c:forEach var="film" items="${filmsList}">
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

    <!-- Slider for series -->
    <h2 class="font-bold text-2xl pt-2">Recently Added Series</h2>
    <div class="flex flex-col" data-controller="slider">
        <div class="flex py-4 px-2 overflow-x-scroll no-scrollbar" data-slider-target="scrollContainer">
            <c:set var="j" value="7"/>
            <c:forEach var="serie" items="${seriesList}">
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

    <!-- Every Film and Series -->
    <div class="row">
        <h2 class="font-bold text-2xl py-2">Explore some Films and Series</h2>
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
        <jsp:param name="url" value="/?"/>
    </jsp:include>
</div>
<jsp:include page="/WEB-INF/jsp/components/footer.jsp"/>
</body>
</html>
