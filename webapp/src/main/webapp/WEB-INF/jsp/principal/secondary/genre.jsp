<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
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
    <title>
        <spring:message code="${genre.genre}"/> &#8226; PopCult
    </title>
</head>
<body class="bg-gray-50">
<div class="min-h-screen flex flex-col">
    <jsp:include page="/WEB-INF/jsp/components/navbar.jsp"/>
    <div class="col-8 flex-col offset-2 flex flex-grow">
        <div class="row m-0">
            <h1 class="display-5 fw-bolder py-8">
                <spring:message code="${genre.genre}"/>
            </h1>
            <c:if test="${fn:length(genreLists) > 0}">
                <h4 class="font-bold text-2xl">
                    <spring:message code="genre.lists"/>
                </h4>
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
            <h4 class="font-bold text-2xl">
                <spring:message code="genre.thereAre" arguments="${mediaPageContainer.totalCount}"/>
            </h4>
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
                <jsp:param name="url" value="/genre/${genre.genre}"/>
            </jsp:include>
        </div>
    </div>
    <jsp:include page="/WEB-INF/jsp/components/footer.jsp"/>
</div>
</body>
</html>