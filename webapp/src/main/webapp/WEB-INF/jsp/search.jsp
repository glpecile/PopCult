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

<div class="col-8 offset-2">
    <br>
    <!-- Search Results of every Media -->
    <div class="row">
        <h2 class="font-bold text-2xl py-2">Search Results</h2>
        <c:forEach var="media" items="${searchResultsContainer.elements}">
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
        <jsp:param name="mediaPages" value="${searchResultsContainer.totalPages}"/>
        <jsp:param name="currentPage" value="${searchResultsContainer.currentPage + 1}"/>
        <jsp:param name="url" value="${urlBase}"/>
    </jsp:include>
</div>
<jsp:include page="/WEB-INF/jsp/components/footer.jsp"/>
</body>
</html>