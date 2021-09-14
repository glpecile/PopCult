<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <jsp:include page="/resources/externalResources.jsp"/>
    <!-- favicon -->
    <link rel="shortcut icon" href="<c:url value='/resources/images/favicon.ico'/>" type="image/x-icon">
    <title>Edit List <c:out value="${list.name}"/> &#8226; PopCult</title>
    <c:url value="/editList/${list.mediaListId}" var="deletePath"/>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/components/navbar.jsp"/>
<div class="col-8 offset-2">
    <h2 class="display-5 fw-bolder"><c:out value="${list.name}"/></h2>
    <p class="lead text-justify"><c:out value="${list.description}"/></p>
    <div class="row justify-content-md-end">
        <div class="col">
            <form:form action="${deletePath}" method="DELETE">
                <button type="submit" class="btn btn-outline-dark btn-rounded">
                    Delete
                </button>
                <input id="delete" type="hidden" name="delete" value=<c:out value="true"/>>
            </form:form>
        </div>
        <div class="col">
            <a href="${pageContext.request.contextPath}/lists/${list.mediaListId}">
                <button type="button" class="btn btn-danger btn-rounded">
                    Discard
                </button>
            </a>
        </div>
        <div class="col">
            <a href="${pageContext.request.contextPath}/lists/${list.mediaListId}">
                <button class="btn btn-success btn-rounded">
                    Save
                </button>
            </a>
        </div>
    </div>
    <div class="row">
        <c:forEach var="media" items="${media}">
            <div class="col-12 col-sm-12 col-md-6 col-lg-4 col-xl-3 py-2">
                <jsp:include page="/WEB-INF/jsp/components/card.jsp">
                    <jsp:param name="image" value="${media.image}"/>
                    <jsp:param name="title" value="${media.title}"/>
                    <jsp:param name="releaseDate" value="${media.releaseYear}"/>
                    <jsp:param name="mediaId" value="${media.mediaId}"/>
                    <jsp:param name="editListId" value="${list.mediaListId}"/>
                </jsp:include>
            </div>
        </c:forEach>
    </div>
</div>
<jsp:include page="/WEB-INF/jsp/components/footer.jsp"/>
</body>
</html>
