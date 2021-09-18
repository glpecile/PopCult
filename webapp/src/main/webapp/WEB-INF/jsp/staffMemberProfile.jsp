<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<html>
<head>
    <jsp:include page="/resources/externalResources.jsp"/>
    <!-- favicon -->
    <link rel="shortcut icon" href="<c:url value='/resources/images/favicon.ico'/>" type="image/x-icon">
    <title><c:out value="${member.name}"/> &#8226; PopCult</title>

</head>
<body>
<jsp:include page="/WEB-INF/jsp/components/navbar.jsp"/>
<br>
<div class="col-8 offset-2">
    <div class="row">
        <div class="col-12 col-lg-4">
            <img src="${member.image}" class="img-fluid img-thumbnail card-img-top rounded-lg" alt="Media Image">
            <jsp:include page="/WEB-INF/jsp/components/share.jsp"/>
        </div>
        <div class="col-12 col-lg-8">
            <h1 class="display-5 fw-bolder"><c:out value="${member.name}"/></h1>
            <br>
            <p class="lead text-justify"><c:out value="${member.description}"/></p>
        </div>
    </div>
    <br>
    <div class="flex text-center">
        <div class="dropdown pr-4">
            <button class="btn btn-secondary btn-rounded dropdown-toggle" type="button" id="dropdownMenuButton1" data-bs-toggle="dropdown"
                    aria-expanded="false">
                <c:choose>
                    <c:when test="${roleType == 'actor'}">
                        Actor
                    </c:when>
                    <c:when test="${roleType == 'director'}">
                        Director
                    </c:when>
                    <c:otherwise>
                        All
                    </c:otherwise>
                </c:choose>
            </button>
            <ul class="dropdown-menu shadow-lg" aria-labelledby="dropdownMenuButton1">
                <li><a class="dropdown-item" href="<c:url value="/staff/${staffMemberId}/actor"/>">Actor</a></li>
                <li><a class="dropdown-item" href="<c:url value='/staff/${staffMemberId}/director'/>">Director</a></li>
            </ul>
        </div>
        <c:choose>
            <c:when test="${roleType == 'actor'}">
                <h4 class="font-bold text-2xl">Films & Series starring ${member.name}</h4>
            </c:when>
            <c:when test="${roleType == 'director'}">
                <h4 class="font-bold text-2xl">Films & Series directed by ${member.name}</h4>
            </c:when>
            <c:otherwise>
                <h4 class="font-bold text-2xl">Known for</h4>
            </c:otherwise>
        </c:choose>
    </div>
    <br>

    <div class="row">
        <c:forEach var="media" items="${media}">
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
        <jsp:param name="mediaPages" value="${mediaIdsContainer.totalPages}"/>
        <jsp:param name="currentPage" value="${mediaIdsContainer.currentPage + 1}"/>
        <jsp:param name="url" value="${urlBase}"/>
    </jsp:include>
</div>
<jsp:include page="/WEB-INF/jsp/components/footer.jsp"/>
</body>
</html>