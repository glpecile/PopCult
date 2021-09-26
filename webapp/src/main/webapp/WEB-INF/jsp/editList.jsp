<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <jsp:include page="/resources/externalResources.jsp"/>
    <!-- favicon -->
    <link rel="shortcut icon" href="<c:url value='/resources/images/favicon.ico'/>" type="image/x-icon">
    <title>Edit List <c:out value="${list.listName}"/> &#8226; PopCult</title>
</head>
<c:url value="/lists/${list.mediaListId}/delete" var="deletePath"/>
<c:url value="/lists/${list.mediaListId}/update" var="editListPath"/>
<c:url value="lists/${list.mediaListId}" var="listPath"/>
<body class="bg-gray-50">
<div class="h-full flex flex-col">
    <jsp:include page="/WEB-INF/jsp/components/navbar.jsp"/>
    <div class="col-8 offset-2 py-4 flex justify-center items-start flex-grow">
        <form:form cssClass="rounded-lg shadow-lg p-4 bg-white" modelAttribute="createListForm" action="${editListPath}"
                   method="post">
            <div class="flex flex-col gap-2.5">
                <div class="col-md-6">
                    <h2 class="font-bold text-2xl">Step 1: Edit your list information</h2>
                    <form:label path="listTitle" for="listName" class="form-label">Name of the list</form:label>
                    <form:input path="listTitle" type="text"
                                class="form-control focus:outline-none focus:ring focus:border-purple-300"
                                id="listName" value="${list.listName}"/>
                    <form:errors path="listTitle" cssClass="formError text-red-500" element="p"/>
                </div>
                <div class="col-md-12">
                    <form:label path="description" for="listDesc" class="form-label">Description</form:label>
                    <form:input path="description" type="text"
                                class="form-control h-24 resize-y overflow-clip overflow-auto" id="listDesc"
                                value="${list.description}"/>
                    <form:errors path="description" cssClass="formError text-red-500" element="p"/>
                </div>
                <div class="flex justify-between">
                    <div class="col-md-6 py-2">
                        <div class="form-check">
                            <c:choose>
                                <c:when test="${list.visible}">
                                    <form:checkbox path="visible" class="form-check-label" for="invalidCheck2"
                                                   checked="true"/>
                                </c:when>
                                <c:otherwise>
                                    <form:checkbox path="visible" class="form-check-label" for="invalidCheck2"/>
                                </c:otherwise>
                            </c:choose>
                            Make list public for everyone.
                        </div>
                    </div>
                    <div class="col-md-6 py-2">
                        <div class="form-check">
                            <c:choose>
                                <c:when test="${list.collaborative}">
                                    <form:checkbox path="collaborative" class="form-check-label" for="invalidCheck3"
                                                   checked="true"/>
                                </c:when>
                                <c:otherwise>
                                    <form:checkbox path="collaborative" class="form-check-label" for="invalidCheck2"/>
                                </c:otherwise>
                            </c:choose>

                            Enable others to suggest new movies to add.
                        </div>
                    </div>
                </div>
            </div>
            <div class="flex justify-between space-x-2.5">
                <form:form action="${deletePath}" method="DELETE">
                    <button type="submit" value="delete" name="delete"
                            class="btn btn-danger bg-gray-300 hover:bg-red-400 text-gray-700 font-semibold hover:text-white">
                        Delete this list
                    </button>
                </form:form>
                <a href="${listPath}">
                    <button type="button"
                            class="btn btn-warning btn btn-danger bg-gray-300 hover:bg-gray-400 text-gray-700 font-semibold hover:text-white">
                        Discard changes
                    </button>
                </a>
                <button type="submit" value="save" name="save"
                        class="btn btn-success btn btn-danger bg-gray-300 hover:bg-green-500 text-gray-700 font-semibold hover:text-white">
                    Save
                    and edit your list media
                </button>
            </div>
        </form:form>
        <%--    <div class="row">--%>
        <%--        <c:forEach var="media" items="${media}">--%>
        <%--            <div class="col-12 col-sm-12 col-md-6 col-lg-4 col-xl-3 py-4">--%>
        <%--                <jsp:include page="/WEB-INF/jsp/components/card.jsp">--%>
        <%--                    <jsp:param name="image" value="${media.image}"/>--%>
        <%--                    <jsp:param name="title" value="${media.title}"/>--%>
        <%--                    <jsp:param name="releaseDate" value="${media.releaseYear}"/>--%>
        <%--                    <jsp:param name="mediaId" value="${media.mediaId}"/>--%>
        <%--                    <jsp:param name="deleteFromListId" value="${list.mediaListId}"/>--%>
        <%--                    <jsp:param name="deletePath" value="/editList/${list.mediaListId}"/>--%>
        <%--                </jsp:include>--%>
        <%--            </div>--%>
        <%--        </c:forEach>--%>
        <%--    </div>--%>
    </div>
    <jsp:include page="/WEB-INF/jsp/components/footer.jsp"/>
</div>
</body>
</html>
