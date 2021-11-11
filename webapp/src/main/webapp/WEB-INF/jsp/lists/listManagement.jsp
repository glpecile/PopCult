<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
    <title><spring:message code="lists.edit.title"/> &#8226; PopCult</title>
</head>
<!-- Variables -->
<c:url value="/lists/edit/${mediaListId}/delete" var="deleteListPath"/>
<c:url value="/lists/edit/${mediaListId}/deleteMedia" var="deleteMediaPath"/>
<c:url value="/lists/edit/${mediaListId}/search" var="searchUrl"/>
<c:url value="/lists/${mediaListId}" var="listPath"/>
<c:url value="/list/edit/${mediaListId}" var="editListDetails"/>
<c:url value="/lists/edit/${list.mediaListId}/update" var="editListPath"/>
<c:url value="/lists/edit/${list.mediaListId}/addMedia" var="addMediaPath"/>
<c:url value="/lists/${list.mediaListId}/cancelCollab" var="deleteCollabPath"/>
<c:url value="/lists/${listId}/collaborators" var="collaboratorsPath"/>
<c:url value="/lists/edit/${list.mediaListId}/searchUsers" var="searchUsersUrl"/>
<c:url value="/lists/edit/${list.mediaListId}/addCollaborators" var="addCollabPath"/>
<body class="bg-gray-50">
<div class="flex flex-col h-screen bg-gray-50">
    <jsp:include page="/WEB-INF/jsp/components/navbar.jsp"/>
    <c:set var="isOwner" value="${currentUser == list.user}"/>
    <div class="flex-grow col-8 offset-2">
        <div class="row g-3 p-2 my-8 bg-white shadow-lg rounded-lg">
            <div class="flex flex-wrap justify-between m-0">
                <h2 class="display-5 fw-bolder"><c:out value="${list.listName}"/></h2>
                <c:if test="${isOwner}">
                    <button class="btn btn-link my-3.5 px-2.5 group bg-gray-300 hover:bg-purple-400 text-gray-700 font-semibold hover:text-white"
                            data-bs-toggle="modal" data-bs-target="#editListDetailsModal">
                        <i class="fas fa-pencil-alt text-gray-500 group-hover:text-white pr-2"></i><spring:message
                            code="lists.editDetails"/>
                    </button>
                </c:if>
            </div>
            <%--List current content--%>
            <div class="flex justify-between">
                <h4 class="py-0.5">
                    <spring:message code="lists.currently"/>
                </h4>
                <div class="flex justify-end space-x-2">
                    <c:if test="${list.collaborative && isOwner}">
                        <button class="btn btn-link my-1.5 px-2.5 group bg-gray-300 hover:bg-purple-400 text-gray-700 font-semibold hover:text-white"
                                data-bs-toggle="modal" data-bs-target="#manageCollaboratorsModal">
                            <i class="fas fa-users text-gray-500 group-hover:text-white pr-2"></i>
                            <spring:message code="lists.manageCollab"/>
                        </button>
                    </c:if>
                    <button class="btn btn-link my-1.5 px-2.5 group bg-gray-300 hover:bg-green-400 text-gray-700 font-semibold hover:text-white"
                            data-bs-toggle="modal" data-bs-target="#addMediaModal">
                        <i class="fas fa-plus text-gray-500 group-hover:text-white pr-2"></i>
                        <spring:message code="lists.addMedia"/>
                    </button>
                </div>
            </div>
            <c:if test="${mediaContainer.totalCount == 0}">
                <div class="flex-col flex-wrap p-4 space-x-4">
                    <img class="w-36 object-center mx-auto" src="<c:url value="/resources/images/PopCultLogoExclamation.png"/>"
                         alt="no_results_image">
                    <h3 class="text-center py-2 mt-0.5 text-gray-400">
                        <spring:message code="lists.empty"/>
                    </h3>
                    <h3 class="text-center py-0.5 text-gray-400">
                        <spring:message code="lists.howTo"/>
                    </h3>
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
                <c:if test="${isOwner}">
                    <jsp:include page="/WEB-INF/jsp/components/confirmationDeleteListModal.jsp">
                        <jsp:param name="mediaListId" value="${mediaListId}"/>
                        <jsp:param name="deleteListPath" value="/lists/edit/${mediaListId}/delete"/>
                    </jsp:include>
                </c:if>
                <a href=${listPath}>
                    <button type="button"
                            class="btn btn-warning btn btn-success bg-gray-300 group hover:bg-green-400 text-gray-700 font-semibold hover:text-white">
                        <i class="fas fa-check group-hover:text-white pr-2"></i>
                        <spring:message code="general.done"/>
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
                    <h5 class="modal-title font-bold text-2xl" id="editListDetailsModalLabel">
                        <spring:message code="lists.editDesc"/>
                    </h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <form:form modelAttribute="editListDetails" action="${editListPath}" method="POST">
                    <div class="modal-body">
                        <div class="flex flex-col gap-2.5">
                            <div class="col-md-6">
                                <form:label path="listTitle" for="listName"
                                            class="form-label">
                                    <spring:message code="lists.name"/>
                                </form:label>
                                <form:input path="listTitle" type="text"
                                            class="form-control focus:outline-none focus:ring focus:border-purple-300"
                                            id="listName" value="${list.listName}"/>
                                <form:errors path="listTitle" cssClass="formError text-red-500" element="p"/>
                            </div>
                            <div class="col-md-12">
                                <form:label path="description" for="listDesc"
                                            class="form-label">
                                    <spring:message code="lists.desc"/>
                                </form:label>
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
                                        <spring:message code="lists.makePublic"/>
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
                                        <spring:message code="lists.makeCollab"/>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer flex space-x-2">
                        <button type="submit" value="save" name="save"
                                class="btn btn-success bg-gray-300 hover:bg-green-500 text-gray-700 font-semibold hover:text-white">
                            <i class="fas fa-save group-hover:text-white pr-2"></i><spring:message code="general.save"/>
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
                    <h5 class="modal-title font-bold text-2xl" id="addMediaModalLabel">
                        <spring:message code="lists.searchAndAdd"/>
                    </h5>
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
                        <c:choose>
                            <c:when test="${searchResultSize != 0}">
                                <!-- Search Results of every Media -->
                                <div class="row">
                                    <div class="overflow-y-auto h-50">
                                        <div class="flex flex-col space-y-2.5">
                                            <form:checkboxes path="media" items="${searchResults}"/>
                                            <form:errors path="media" cssClass="error text-red-400"/>
                                        </div>
                                    </div>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <p> TODO appropriate message </p>
                            </c:otherwise>
                        </c:choose>
                        <br>
                    </c:if>
                </div>
                <div class="modal-footer flex space-x-2">
                    <button type="submit" value="add" name="add"
                            class="btn btn-success bg-gray-300 hover:bg-green-500 text-gray-700 font-semibold hover:text-white">
                        <i class="fas fa-plus group-hover:text-white pr-2"></i><spring:message code="lists.addMedia"/>
                    </button>
                </div>
                </form:form>
            </div>
        </div>
    </div>
    <%-- Manage Collaborators Modal--%>
    <div class="modal fade" id="manageCollaboratorsModal" tabindex="-1" aria-labelledby="manageCollaboratorsModalLabel"
         aria-hidden="true">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <div class="flex justify-between">
                        <h5 class="modal-title font-bold text-2xl" id="manageCollaboratorsModalLabel">
                            <spring:message code="lists.collab.manage"/>
                        </h5>
                        <c:if test="${collaboratorsContainer.totalCount != 0}">
                            <a href="${collaboratorsPath}">
                                <button class="btn btn-link text-purple-500 hover:text-purple-900 btn-rounded ">
                                    <spring:message code="home.viewAll"/>
                                </button>
                            </a>
                            <button class="btn btn-link text-purple-500 hover:text-purple-900 btn-rounded"
                                    data-bs-toggle="modal" data-bs-dismiss="modal"
                                    data-bs-target="#addCollaboratorsModal">
                                <i class="fas fa-plus text-gray-500 group-hover:text-white pr-2"></i>
                                <spring:message code="lists.collab.add"/>
                            </button>
                        </c:if>
                    </div>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <div class="overflow-y-auto h-50">
                        <div class="flex flex-col space-y-2.5">
                            <c:if test="${collaboratorsContainer.totalCount == 0}">
                                <div class="flex-col flex-wrap p-4 space-x-4">
                                    <img class="w-36 object-center mx-auto" src="<c:url value="/resources/images/PopCultLogoExclamation.png"/>"
                                         alt="no_results_image">
                                    <h3 class="text-center py-2 mt-0.5 text-gray-400">
                                        <spring:message code="lists.collab.empty"/>
                                    </h3>
                                </div>
                                <button class="btn btn-link flex justify-center group bg-gray-300 hover:bg-purple-400 text-gray-700 font-semibold hover:text-white"
                                        data-bs-toggle="modal" data-bs-dismiss="modal"
                                        data-bs-target="#addCollaboratorsModal">
                                    <i class="fas fa-plus text-gray-500 group-hover:text-white pr-2"></i>
                                    <spring:message code="lists.collab.add"/>
                                </button>
                            </c:if>
                            <c:forEach var="request" items="${collaboratorsContainer.elements}">
                                <jsp:include page="/WEB-INF/jsp/components/collaborator.jsp">
                                    <jsp:param name="username" value="${request.collaborator.username}"/>
                                    <jsp:param name="collabId" value="${request.collabId}"/>
                                    <jsp:param name="returnURL" value="/lists/edit/${listId}/manageMedia"/>
                                    <jsp:param name="deleteCollabPath" value="${deleteCollabPath}"/>
                                </jsp:include>
                            </c:forEach>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <%-- Add Collaborators Modal --%>
    <div class="modal fade" id="addCollaboratorsModal" tabindex="-1" aria-labelledby="addCollaboratorsModalLabel"
         aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <div class="flex justify-between">
                        <h5 class="modal-title font-bold text-2xl" id="addCollaboratorsModalLabel">
                            <spring:message code="lists.collab.add"/>
                        </h5>
                    </div>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <div class="overflow-y-auto h-50">
                        <div class="flex flex-col space-y-2.5">
                            <!-- Search input -->
                            <form class="space-y-2" action="${searchUsersUrl}" method="get"
                                  enctype="application/x-www-form-urlencoded">
                                <div class="flex flex-col relative">
                                    <label class="py-2 text-semibold w-full flex">
                                        <input class="form-control text-base rounded-full h-8 shadow-sm pl-3 pr-8"
                                               type="text"
                                               name="term"
                                               placeholder="<spring:message code="search.placeholder"/>"/>
                                        <button class="btn btn-link bg-transparent rounded-full h-8 w-8 p-2 absolute inset-y-3 right-2 flex items-center"
                                                name="search" type="submit">
                                            <i class="fas fa-search text-gray-500 text-center rounded-full mb-2"></i>
                                        </button>
                                    </label>
                                </div>
                            </form>
                        </div>
                    </div>
                    <form:form cssClass="m-0 p-0" modelAttribute="usernameForm" action="${addCollabPath}" method="POST">
                        <c:if test="${userSearchTerm != null}">
                            <h2 class="font-bold pb-1.5">
                                <spring:message code="search.by"/> <c:out value="${userSearchTerm}"/>
                            </h2>
                        <c:choose>
                            <c:when test="${userSearchResultSize != 0}">
                                <!-- Search Results of every User -->
                                <div class="row">
                                    <div class="overflow-y-auto h-50">
                                        <div class="flex flex-col space-y-2.5">
                                            <form:checkboxes path="user" items="${userSearchResults}"/>
                                            <form:errors path="user" cssClass="error text-red-400"/>
                                        </div>
                                    </div>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <p> TODO appropriate message </p>
                            </c:otherwise>
                        </c:choose>
                        <br>
                    </c:if>
                </div>
                <div class="modal-footer flex space-x-2">
                    <button type="submit" value="add" name="add"
                            class="btn btn-success bg-gray-300 hover:bg-green-500 text-gray-700 font-semibold hover:text-white">
                        <i class="fas fa-plus group-hover:text-white pr-2"></i><spring:message code="lists.collab.add"/>
                    </button>
                </div>
                </form:form>
            </div>
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
    <c:if test="${userSearchTerm!=null}">
    $(document).ready(function () {
        $('#addCollaboratorsModal').modal('show');
    });
    </c:if>
</script>
</html>
