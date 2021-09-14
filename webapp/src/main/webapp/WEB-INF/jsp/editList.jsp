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
    <c:url value="/editList/${list.mediaListId}" var="postPath"/>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/components/navbar.jsp"/>
<div class="col-8 offset-2 py-2">
    <form:form modelAttribute="createListForm" action="${postPath}" method="post">
        <div class="row">
            <div class="col-md-6">
                <form:label path="listTitle" for="listName" class="form-label">Name of the list</form:label>
                <form:input path="listTitle" type="text"
                            class="form-control focus:outline-none focus:ring focus:border-purple-300"
                            id="listName" value="${list.name}"/>
                <form:errors path="listTitle" cssClass="formError text-red-500" element="p"/>
            </div>
            <div class="col-md-6">
                <form:label path="description" for="listDesc" class="form-label">Description</form:label>
                <form:input path="description" type="text" class="form-control resize" id="listDesc"
                            value="${list.description}"/>
                <form:errors path="description" cssClass="formError text-red-500" element="p"/>
            </div>
            <div class="col-md-4">
                <div class="form-check">
                    <form:checkbox path="visible" class="form-check-label" for="invalidCheck2" value="true"/>
                    Make list public for everyone.
                </div>
            </div>
            <div class="col-md-6">
                <div class="form-check">
                    <form:checkbox path="collaborative" class="form-check-label" for="invalidCheck3"/>
                    Enable others to suggest new movies to add.
                </div>
            </div>
        </div>
        <%--        <h2 class="display-5 fw-bolder"><c:out value="${list.name}"/></h2>--%>
        <%--        <p class="lead text-justify"><c:out value="${list.description}"/></p>--%>
        <div class="row justify-content-end">
            <div class="col">
                <form:form action="${deletePath}" method="DELETE">
                    <button type="submit" value="delete" name="delete" class="btn btn-outline-dark btn-rounded">Delete
                    </button>
                </form:form>
            </div>
            <div class="col">
                <a href="${pageContext.request.contextPath}/lists/${list.mediaListId}">
                    <button type="button" class="btn btn-danger btn-rounded">Discard</button>
                </a>
            </div>
            <div class="col">
                <button type="submit" value="save" name="save" class="btn btn-success btn-rounded">Save</button>
            </div>
        </div>
    </form:form>

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
