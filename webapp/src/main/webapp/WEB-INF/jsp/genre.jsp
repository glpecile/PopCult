<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <jsp:include page="/resources/externalResources.jsp"/>
    <!-- favicon -->
    <link rel="shortcut icon" href="<c:url value='/resources/images/favicon.ico'/>" type="image/x-icon">
    <title><c:out value="${genreName}"/> &#8226; PopCult</title>
</head>
<body class="bg-gray-50">
<jsp:include page="/WEB-INF/jsp/components/navbar.jsp"/>
<c:url value="" var="nextUrl">
    <c:forEach var="p" items="${param}">
        <c:param name="${p.key}" value="${p.value}"/>
    </c:forEach>
</c:url>
<div class="col-8 flex-col offset-2 flex h-screen">
    <div class="flex-grow">
        <div class="row m-0">
            <h1 class="display-5 fw-bolder py-8"><c:out value="${genreName}"/> Genre</h1>
            <c:if test="${fn:length(genreLists) > 0}">
                <h4 class="font-bold text-2xl">Lists that contain this genre</h4>
                <c:forEach var="cover" items="${genreLists}">
                    <div class="col-12 col-sm-12 col-md-6 col-lg-4 col-xl-3 py-8">
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
            </c:if>
        </div>
        <div class="row m-0">
            <h4 class="font-bold text-2xl">There are <c:out value="${mediaPageContainer.totalCount}"/> <c:out value="${genreName}"/> Films
                & Series</h4>
            <c:forEach var="media" items="${mediaPageContainer.elements}">
                <div class="col-12 col-sm-12 col-md-6 col-lg-4 col-xl-3 pt-4">
                    <jsp:include page="/WEB-INF/jsp/components/card.jsp">
                        <jsp:param name="image" value="${media.image}"/>
                        <jsp:param name="title" value="${media.title}"/>
                        <jsp:param name="releaseDate" value="${media.releaseYear}"/>
                        <jsp:param name="mediaId" value="${media.mediaId}"/>
                    </jsp:include>
                </div>
            </c:forEach>
        </div>
        <div class="pt-8">
            <jsp:include page="/WEB-INF/jsp/components/pageNavigation.jsp">
                <jsp:param name="mediaPages" value="${mediaPageContainer.totalPages}"/>
                <jsp:param name="currentPage" value="${mediaPageContainer.currentPage + 1}"/>
                <jsp:param name="url" value="${nextUrl}"/>
            </jsp:include>
        </div>
    </div>
    <jsp:include page="/WEB-INF/jsp/components/footer.jsp"/>
</div>
</body>
</html>