<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <jsp:include page="/resources/externalResources.jsp"/>
    <!-- favicon -->
    <link rel="shortcut icon" href="<c:url value='/resources/images/favicon.ico'/>" type="image/x-icon">
    <title><c:out value="${list.name}"/> &#8226; PopCult</title>
    <c:url value="/lists/${list.mediaListId}" var="forkPath"/>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/components/navbar.jsp"/>
<div class="col-8 offset-2">
    <h2 class="display-5 fw-bolder"><c:out value="${list.name}"/></h2>
    <p class="lead text-justify"><c:out value="${list.description}"/></p>

    <div class="row">
        <div class="col">
            <jsp:include page="/WEB-INF/jsp/components/share.jsp"/>
        </div>
        <c:choose>
            <c:when test="${list.userId == currentUser.userId}">
                <div class="col flex justify-center py-2">
                    <a href="${pageContext.request.contextPath}/editList/${list.mediaListId}">
                        <button type="button" class="btn btn-secondary btn-rounded">
                            Edit list
                        </button>
                    </a>
                </div>
            </c:when>
            <c:otherwise>
                <div class="col flex justify-center py-2">
                    <form:form action="${forkPath}" method="POST">
                        <button type="submit" class="btn btn-secondary btn-rounded">Fork this list</button>
                        <input id="currentUserId" type="hidden" name="currentUserId" value="${currentUser.userId}">
                    </form:form>
                </div>
            </c:otherwise>
        </c:choose>

    </div>
    <div class="row">
        <c:forEach var="media" items="${media}">
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
</div>
<jsp:include page="/WEB-INF/jsp/components/footer.jsp"/>
</body>
</html>
