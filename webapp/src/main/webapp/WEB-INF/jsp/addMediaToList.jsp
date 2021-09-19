<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="/resources/externalResources.jsp"/>
    <!-- favicon -->
    <link rel="shortcut icon" href="<c:url value='/resources/images/favicon.ico'/>" type="image/x-icon">
    <title>Add media to a new list &#8226; PopCult</title>
    <c:url value="/createList/addMedia" var="deletePath"/>

</head>
<body>
<div class="flex flex-col h-screen bg-gray-50">
    <jsp:include page="/WEB-INF/jsp/components/navbar.jsp"/>
    <div class="flex-grow col-8 offset-2">
        <div class="row g-3 p-2 my-8 bg-white shadow-lg">
            <h2 class="font-bold text-2xl">Step 2: Manage list content</h2>
            <c:if test="${listsContainer.totalCount != 0}">
                <h4 class="py-2">Currently in this list</h4>
            </c:if>
            <div class="row">
                <c:forEach var="media" items="${listsContainer.elements}">
                    <div class="col-12 col-sm-12 col-md-6 col-lg-4 col-xl-3 py-2">
                        <jsp:include page="/WEB-INF/jsp/components/card.jsp">
                            <jsp:param name="image" value="${media.image}"/>
                            <jsp:param name="title" value="${media.title}"/>
                            <jsp:param name="releaseDate" value="${media.releaseYear}"/>
                            <jsp:param name="mediaId" value="${media.mediaId}"/>
                            <jsp:param name="editListId" value="${mediaListId}"/>
                            <jsp:param name="deletePath" value="/createList/addMedia"/>
                        </jsp:include>
                    </div>
                </c:forEach>
            </div>

            <br>
            <jsp:include page="/WEB-INF/jsp/components/pageNavigation.jsp">
                <jsp:param name="mediaPages" value="${listsContainer.totalPages}"/>
                <jsp:param name="currentPage" value="${listsContainer.currentPage + 1}"/>
                <jsp:param name="url" value="/createList/addMedia"/>
            </jsp:include>

            <div class="flex justify-between px-4">
                <form:form action="${deletePath}" method="DELETE">
                    <input type="hidden" id="mediaListId" name="mediaListId" value="${mediaListId}">
                    <button type="submit" value="delete" name="delete"
                            class="btn btn-danger bg-gray-300 hover:bg-red-400 text-gray-700 font-semibold hover:text-white">
                        Discard changes
                    </button>
                </form:form>
                <a href=<c:url value="/lists/${mediaListId}"/>>
                    <button type="button"
                            class="btn btn-warning btn btn-danger bg-gray-300 hover:bg-green-400 text-gray-700 font-semibold hover:text-white">
                        Save and finish
                    </button>
                </a>
            </div>
        </div>
    </div>
</body>
</html>
