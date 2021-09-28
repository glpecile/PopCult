<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <jsp:include page="/resources/externalResources.jsp"/>
    <!-- favicon -->
    <link rel="shortcut icon" href="<c:url value='/resources/images/favicon.ico'/>" type="image/x-icon">
    <!-- Local CSS -->
    <link rel="stylesheet" href="<c:url value="/resources/css/overflow.css"/>"/>
    <!-- Local Scripts -->
    <script type="text/javascript" src="<c:url value="/resources/js/components/slider.js"/>"></script>
    <title><spring:message code="lists.title"/> &#8226; PopCult</title>
</head>
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
        </div>
    </div>
    <br>
    <jsp:include page="/WEB-INF/jsp/components/pageNavigation.jsp">
        <jsp:param name="mediaPages" value="${allListContainer.totalPages}"/>
        <jsp:param name="currentPage" value="${allListContainer.currentPage + 1}"/>
        <jsp:param name="url" value="/lists"/>
    </jsp:include>
</div>
<jsp:include page="/WEB-INF/jsp/components/footer.jsp"/>
</body>
</html>
