<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="/resources/externalResources.jsp"/>
    <!-- favicon -->
    <link rel="shortcut icon" href="<c:url value='/resources/images/favicon.ico'/>" type="image/x-icon">
    <title>Manage list content &#8226; PopCult</title>
</head>
<!-- Variables -->
<c:url value="/lists/edit/${mediaListId}/delete" var="deleteListPath"/>
<c:url value="/lists/edit/${mediaListId}/deleteMedia" var="deleteMediaPath"/>
<c:url value="/lists/edit/${mediaListId}/search" var="searchUrl"/>
<c:url value="/lists/${mediaListId}" var="listPath"/>
<c:url value="/list/edit/${mediaListId}" var="editListDetails"/>
<c:url value="/lists/edit/${list.mediaListId}/update" var="editListPath"/>
<c:url value="/lists/edit/${list.mediaListId}/addMedia" var="addMediaPath"/>
<body class="bg-gray-50">
<div class="flex flex-col h-screen bg-gray-50">
    <jsp:include page="/WEB-INF/jsp/components/navbar.jsp"/>
    <div class="flex-grow col-8 offset-2">
        <div class="row g-3 p-2 my-8 bg-white shadow-lg rounded-lg">
            <div class="flex justify-between m-0">
                <h2 class="display-5 fw-bolder"><c:out value="${list.listName}"/></h2>
                <button class="btn btn-link my-3.5 px-2.5 group bg-gray-300 hover:bg-purple-400 text-gray-700 font-semibold hover:text-white"
                        data-bs-toggle="modal" data-bs-target="#editListDetailsModal">
                    <i class="fas fa-pencil-alt text-gray-500 group-hover:text-white pr-2"></i> Edit Details
                </button>
            </div>
            <%--List current content--%>
            <div class="flex justify-between">
                <h4 class="py-0.5">Currently in this list</h4>
                <button class="btn btn-link my-1.5 px-2.5 group bg-gray-300 hover:bg-green-400 text-gray-700 font-semibold hover:text-white"
                        data-bs-toggle="modal" data-bs-target="#addMediaModal">
                    <i class="fas fa-plus text-gray-500 group-hover:text-white pr-2"></i> Add Media
                </button>
            </div>
            <c:if test="${mediaContainer.totalCount == 0}">
                <div class="flex flex-col">
                    <h4 class="text-center py-0.5">It seems this list is empty!</h4>
                    <h4 class="text-center py-0.5">You can search for media to add with the + Add Media button!</h4>
                </div>
            </c:if>
            <div class="flex flex-col space-y-2.5">
                <c:forEach var="media" items="${mediaContainer.elements}">
                    <jsp:include page="/WEB-INF/jsp/components/compactCard.jsp">
                        <jsp:param name="image" value="${media.image}"/>
                        <jsp:param name="title" value="${media.title}"/>
                        <jsp:param name="releaseDate" value="${media.releaseYear}"/>
                        <jsp:param name="mediaId" value="${media.mediaId}"/>
                        <jsp:param name="deleteFromListId" value="${mediaListId}"/>
                        <jsp:param name="deletePath" value="/lists/edit/${mediaListId}/deleteMedia"/>
                    </jsp:include>
                </c:forEach>
            </div>

            <br>
            <jsp:include page="/WEB-INF/jsp/components/pageNavigation.jsp">
                <jsp:param name="mediaPages" value="${mediaContainer.totalPages}"/>
                <jsp:param name="currentPage" value="${mediaContainer.currentPage + 1}"/>
                <jsp:param name="url" value="/lists/edit/${mediaListId}/manageMedia"/>
            </jsp:include>
            <div class="flex justify-between mb-2">
                <jsp:include page="/WEB-INF/jsp/components/confirmDelete.jsp">
                    <jsp:param name="mediaListId" value="${mediaListId}"/>
                    <jsp:param name="deleteListPath" value="/lists/edit/${mediaListId}/delete"/>
                    <jsp:param name="title" value="Delete this list"/>
                    <jsp:param name="message" value="Are you sure you want to delete this list?"/>
                </jsp:include>
                <a href=${listPath}>
                    <button type="button"
                            class="btn btn-warning btn btn-success bg-gray-300 group hover:bg-green-400 text-gray-700 font-semibold hover:text-white">
                        <i class="fas fa-save group-hover:text-white pr-2"></i>Done
                    </button>
                </a>
            </div>
        </div>
    </div>
    <jsp:include page="/WEB-INF/jsp/components/footer.jsp"/>
    <%-- Edit List Details Modal--%>
    <div class="modal fade" id="editListDetailsModal" tabindex="-1" aria-labelledby="editListDetailsModalLabel"
         aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title font-bold text-2xl" id="editListDetailsModalLabel">Edit list details</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <form:form modelAttribute="editListDetails" action="${editListPath}" method="POST">
                    <div class="modal-body">
                        <div class="flex flex-col gap-2.5">
                            <div class="col-md-6">
                                <form:label path="listTitle" for="listName"
                                            class="form-label">Name of the list</form:label>
                                <form:input path="listTitle" type="text"
                                            class="form-control focus:outline-none focus:ring focus:border-purple-300"
                                            id="listName" value="${list.listName}"/>
                                <form:errors path="listTitle" cssClass="formError text-red-500" element="p"/>
                            </div>
                            <div class="col-md-12">
                                <form:label path="description" for="listDesc"
                                            class="form-label">Description</form:label>
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
                                                <form:checkbox path="visible" class="form-check-label"
                                                               for="invalidCheck2"
                                                               checked="true"/>
                                            </c:when>
                                            <c:otherwise>
                                                <form:checkbox path="visible" class="form-check-label"
                                                               for="invalidCheck2"/>
                                            </c:otherwise>
                                        </c:choose>
                                        Make list public for everyone.
                                    </div>
                                </div>
                                <div class="col-md-6 py-2">
                                    <div class="form-check">
                                        <c:choose>
                                            <c:when test="${list.collaborative}">
                                                <form:checkbox path="collaborative" class="form-check-label"
                                                               for="invalidCheck3"
                                                               checked="true"/>
                                            </c:when>
                                            <c:otherwise>
                                                <form:checkbox path="collaborative" class="form-check-label"
                                                               for="invalidCheck2"/>
                                            </c:otherwise>
                                        </c:choose>
                                        Enable others to suggest new movies to add.
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer flex space-x-2 pb-0">
                        <button type="submit" value="save" name="save"
                                class="btn btn-success bg-gray-300 hover:bg-green-500 text-gray-700 font-semibold hover:text-white">
                            <i class="fas fa-save group-hover:text-white pr-2"></i>Save changes
                        </button>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
    <%-- Add Media Modal--%>
    <div class="modal fade" id="addMediaModal" tabindex="-1" aria-labelledby="addMediaModalLabel"
         aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title font-bold text-2xl" id="addMediaModalLabel">Search and add new media</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <%--           TODO REPLACE WITH COMPONENT WITH VARIABLE URL--%>
                    <!-- Search input -->
                    <form class="space-y-2" action="${searchUrl}" method="get"
                          enctype="application/x-www-form-urlencoded">
                        <div class="flex flex-col relative">
                            <label class="py-2 text-semibold w-full flex">
                                <input type="hidden" name="mediaListId" value="${mediaListId}">
                                <input class="form-control text-base rounded-full h-8 shadow-sm pl-3 pr-8"
                                       type="text"
                                       name="term"
                                       placeholder="<spring:message code="search.placeholder"/>"/>
                                <button class="btn btn-link bg-transparent rounded-full h-8 w-8 p-2 absolute inset-y-3 right-2 flex items-center"
                                        name="search" id="search" type="submit">
                                    <i class="fas fa-search text-gray-500 text-center rounded-full mb-2"></i>
                                </button>
                            </label>
                        </div>
                    </form>
                    <form:form cssClass="m-0 p-0" modelAttribute="mediaForm" action="${addMediaPath}" method="POST">
                    <c:if test="${searchTerm != null}">
                        <h2 class="font-bold pb-1.5">
                            <spring:message code="search.by"/> <c:out value="${searchTerm}"/>
                        </h2>
                        <!-- Search Results of every Media -->
                        <div class="row">
                            <div class="overflow-y-auto h-32">
                                <div class="flex flex-col space-y-2.5">
                                    <form:checkboxes path="media" items="${searchResults}"/>
                                    <form:errors path="media" cssClass="error text-red-400"/>
                                </div>
                            </div>
                        </div>
                        <br>
                    </c:if>
                </div>
                <div class="modal-footer flex space-x-2">
                    <button type="submit" value="add" name="add"
                            class="btn btn-success bg-gray-300 hover:bg-green-500 text-gray-700 font-semibold hover:text-white">
                        <i class="fas fa-plus group-hover:text-white pr-2"></i>Add Media
                    </button>
                </div>
                </form:form>
            </div>
        </div>
    </div>
</body>
<script>
    <c:if test="${editDetailsErrors}">
    $(document).ready(function () {
        $('#editListDetailsModal').modal('show');
    });
    </c:if>
    <c:if test="${searchTerm!=null}">
    $(document).ready(function () {
        $('#addMediaModal').modal('show');
    });
    </c:if>
</script>
</html>
