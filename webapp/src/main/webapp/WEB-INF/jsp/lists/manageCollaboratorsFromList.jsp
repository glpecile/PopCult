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
<body class="bg-gray-50">
<div class="flex flex-col h-screen bg-gray-50">
    <jsp:include page="/WEB-INF/jsp/components/navbar.jsp"/>
    <div class="flex-grow col-8 offset-2">
        <h2 class="display-5 fw-bolder mb-2 pl-3 py-4"><spring:message code="lists.allCollaborators"/><a href="<c:url value="/lists/${list.mediaListId}"/>" class="text-purple-500 hover:text-purple-900" > <c:out value="${list.listName}"/></a></h2>
        <div class="flex flex-col space-y-2.5">
            <c:if test="${collaboratorsContainer.totalCount == 0}">
                <h3 class="text-center text-gray-400">
                    <spring:message code="lists.collab.empty"/>
                </h3>
            </c:if>
            <c:forEach var="collaborators" items="${collaboratorsContainer.elements}">
                <jsp:include page="/WEB-INF/jsp/components/collaborator.jsp">
                    <jsp:param name="username" value="${collaborators.collaboratorUsername}"/>
                    <jsp:param name="collabId" value="${collaborators.collabId}"/>
                    <jsp:param name="returnURL" value="/lists/${listId}/collaborators"/>
                    <jsp:param name="deleteCollabPath" value="${deleteCollabPath}"/>
                </jsp:include>
            </c:forEach>
        </div>
        <div class="flex justify-end mt-2">
            <a href="${editListMediaPath}">
                <button type="button"
                        class="btn btn-warning btn btn-success bg-gray-300 group hover:bg-green-400 text-gray-700 font-semibold hover:text-white">
                    <i class="fas fa-check group-hover:text-white pr-2"></i>
                    <spring:message code="lists.backEdit"/>
                </button>
            </a>
        </div>
    </div>
    <br>
    <c:url value="" var="baseURL"/>
    <jsp:include page="/WEB-INF/jsp/components/pageNavigation.jsp">
        <jsp:param name="mediaPages" value="${collaboratorsContainer.totalPages}"/>
        <jsp:param name="currentPage" value="${collaboratorsContainer.currentPage + 1}"/>
        <jsp:param name="baseURL" value="${baseURL}"/>
    </jsp:include>
    <jsp:include page="/WEB-INF/jsp/components/footer.jsp"/>
</div>
</body>
</html>