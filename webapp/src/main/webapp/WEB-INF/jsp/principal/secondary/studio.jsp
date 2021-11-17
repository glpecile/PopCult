<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
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
    <title><c:out value="${studio.name}"/> &#8226; PopCult</title>
</head>
<body class="bg-gray-50">
<div class="min-h-screen flex flex-col">
    <jsp:include page="/WEB-INF/jsp/components/navbar.jsp"/>
    <div class="col-8 offset-2 flex-grow">
        <div class="row">
            <div class="col-12 col-lg-2">
                <img class="img-fluid rounded-lg shadow-md" src="${studio.image}"
                     onerror="this.src='https://media.discordapp.net/attachments/851847371851956334/884465752307015740/local-file-not-found.png';"
                     alt="StudioPicture"/>
            </div>
            <div class="col-12 col-lg-10">
                <h1 class="display-5 fw-bolder break-words max-w-full"><c:out value="${studio.name}"/></h1>
            </div>
        </div>
        <h4 class="lead py-4">
            <spring:message code="studio.productions" arguments="${mediaPageContainer.totalCount}"/>
        </h4>
        <div class="row">
            <c:forEach var="media" items="${mediaPageContainer.elements}">
                <div class="col-12 col-sm-12 col-md-6 col-lg-4 col-xl-3">
                    <jsp:include page="/WEB-INF/jsp/components/card.jsp">
                        <jsp:param name="image" value="${media.image}"/>
                        <jsp:param name="title" value="${media.title}"/>
                        <jsp:param name="releaseDate" value="${media.releaseYear}"/>
                        <jsp:param name="mediaId" value="${media.mediaId}"/>
                    </jsp:include>
                </div>
            </c:forEach>
        </div>
        <jsp:include page="/WEB-INF/jsp/components/pageNavigation.jsp">
            <jsp:param name="mediaPages" value="${mediaPageContainer.totalPages}"/>
            <jsp:param name="currentPage" value="${mediaPageContainer.currentPage + 1}"/>
            <jsp:param name="url" value="/studio/${studio.studioId}"/>
        </jsp:include>
    </div>
    <jsp:include page="/WEB-INF/jsp/components/footer.jsp"/>
</div>
</body>
</html>
