<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
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
    <title><c:out value="${member.name}"/> &#8226; PopCult</title>
</head>
<body class="bg-gray-50">
<div class="min-h-screen flex flex-col">
    <jsp:include page="/WEB-INF/jsp/components/navbar.jsp"/>
    <br>
    <div class="col-8 offset-2 flex-grow">
        <div class="row">
            <div class="col-12 col-lg-4">
                <img src="${member.image}" class="img-fluid img-thumbnail card-img-top rounded-lg shadow-md" alt="Media Image">
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
                            <spring:message code="staff.actor"/>
                        </c:when>
                        <c:when test="${roleType == 'director'}">
                            <spring:message code="staff.director"/>
                        </c:when>
                        <c:otherwise>
                            <spring:message code="staff.all"/>
                        </c:otherwise>
                    </c:choose>
                </button>
                <ul class="dropdown-menu shadow-lg" aria-labelledby="dropdownMenuButton1">
                    <li><a class="dropdown-item" href="<c:url value="/staff/${staffMemberId}"/>">
                        <spring:message code="staff.all"/>
                    </a></li>
                    <li><a class="dropdown-item" href="<c:url value="/staff/${staffMemberId}/actor"/>">
                        <spring:message code="staff.actor"/>
                    </a></li>
                    <li><a class="dropdown-item" href="<c:url value='/staff/${staffMemberId}/director'/>">
                        <spring:message code="staff.director"/>
                    </a></li>
                </ul>
            </div>
            <c:choose>
                <c:when test="${roleType == 'actor'}">
                    <h4 class="font-bold text-2xl">
                        <spring:message code="staff.starring" arguments="${member.name}"/>
                    </h4>
                </c:when>
                <c:when test="${roleType == 'director'}">
                    <h4 class="font-bold text-2xl">
                        <spring:message code="staff.directing" arguments="${member.name}"/>
                    </h4>
                </c:when>
                <c:otherwise>
                    <h4 class="font-bold text-2xl">
                        <spring:message code="staff.known"/>
                    </h4>
                </c:otherwise>
            </c:choose>
        </div>
        <br>
        <div class="row">
            <c:forEach var="media" items="${mediaContainer.elements}">
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

        <br>

        <c:url var="url" value=""/>
        <jsp:include page="/WEB-INF/jsp/components/pageNavigation.jsp">
            <jsp:param name="mediaPages" value="${mediaContainer.totalPages}"/>
            <jsp:param name="currentPage" value="${mediaContainer.currentPage + 1}"/>
            <jsp:param name="url" value="${url}"/>
        </jsp:include>
    </div>
    <jsp:include page="/WEB-INF/jsp/components/footer.jsp"/>
</div>
</body>
</html>