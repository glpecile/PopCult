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
    <title><spring:message code="lists.manageCollab"/> &#8226; PopCult</title>
</head>
<c:url value="/lists/${list.mediaListId}/cancelCollab" var="deleteCollabPath"/>
<c:url value="/lists/edit/${list.mediaListId}/manageMedia" var="editListMediaPath"/>
<c:url value="/lists/${list.mediaListId}/collaborators/search" var="searchUsersUrl"/>
<c:url value="/lists/${list.mediaListId}/collaborators/add" var="addCollabPath"/>
<body class="bg-gray-50">
<div class="flex flex-col h-screen bg-gray-50">
    <jsp:include page="/WEB-INF/jsp/components/navbar.jsp"/>
    <div class="flex-grow col-8 offset-2">
        <h2 class="display-5 fw-bolder mb-2 pl-3 py-4"><spring:message code="lists.allCollaborators"/><a
                href="<c:url value="/lists/${list.mediaListId}"/>" class="text-purple-500 hover:text-purple-900"> <c:out
                value="${list.listName}"/></a></h2>
        <div class="flex justify-end mt-2">
            <button class="btn btn-link bg-gray-300 hover:bg-purple-400 text-gray-700 font-semibold hover:text-white"
                    data-bs-toggle="modal" data-bs-dismiss="modal"
                    data-bs-target="#addCollaboratorsModal">
                <i class="fas fa-plus text-gray-500 group-hover:text-white pr-2"></i>
                <spring:message code="lists.collab.add"/>
            </button>
        </div>
        <div class="flex flex-col space-y-2.5">
            <c:if test="${collaboratorsContainer.totalCount == 0}">
                <div class="flex-col flex-wrap p-4 space-x-4">
                    <img class="w-36 object-center mx-auto" src="<c:url value="/resources/images/PopCultLogoExclamation.png"/>"
                         alt="no_results_image">
                    <h3 class="text-center py-2 mt-0.5 text-gray-400">
                        <spring:message code="lists.collab.empty"/>
                    </h3>
                </div>
            </c:if>
            <c:forEach var="collaborators" items="${collaboratorsContainer.elements}">
                <jsp:include page="/WEB-INF/jsp/components/collaborator.jsp">
                    <jsp:param name="username" value="${collaborators.collaborator.username}"/>
                    <jsp:param name="collabId" value="${collaborators.collabId}"/>
                    <jsp:param name="returnURL" value="/lists/${listId}/collaborators"/>
                    <jsp:param name="deleteCollabPath" value="${deleteCollabPath}"/>
                </jsp:include>
            </c:forEach>
        </div>
        <div class="flex justify-end mt-2">
            <a href="${editListMediaPath}">
                <button type="button"
                        class="btn btn-link bg-gray-300 group hover:bg-purple-400 text-gray-700 font-semibold hover:text-white">
                    <i class="fas fa-check group-hover:text-white pr-2"></i>
                    <spring:message code="lists.backEdit"/>
                </button>
            </a>
        </div>
    </div>
    <c:url value="" var="baseURL"/>
    <jsp:include page="/WEB-INF/jsp/components/pageNavigation.jsp">
        <jsp:param name="mediaPages" value="${collaboratorsContainer.totalPages}"/>
        <jsp:param name="currentPage" value="${collaboratorsContainer.currentPage + 1}"/>
        <jsp:param name="baseURL" value="${baseURL}"/>
    </jsp:include>
    <jsp:include page="/WEB-INF/jsp/components/footer.jsp"/>

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
                        <!-- Search Results of every User -->
                        <div class="row">
                            <div class="overflow-y-auto h-50">
                                <div class="flex flex-col space-y-2.5">
                                    <form:checkboxes path="user" items="${userSearchResults}"/>
                                    <form:errors path="user" cssClass="error text-red-400"/>
                                </div>
                            </div>
                        </div>
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
    <c:if test="${userSearchTerm!=null}">
    $(document).ready(function () {
        $('#addCollaboratorsModal').modal('show');
    });
    </c:if>
</script>
</html>