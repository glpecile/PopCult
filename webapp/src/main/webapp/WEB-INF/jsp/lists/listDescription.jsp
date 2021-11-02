<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!doctype html>
<html lang="en">
<head>
    <jsp:include page="/resources/externalResources.jsp"/>
    <!-- favicon -->
    <link rel="shortcut icon" href="<c:url value='/resources/images/favicon.ico'/>" type="image/x-icon">
    <title><c:out value="${list.listName}"/> &#8226; PopCult</title>
</head>
<c:url value="/lists/${list.mediaListId}" var="forkPath"/>
<c:url value="/lists/edit/${list.mediaListId}/manageMedia" var="editListMediaPath"/>
<c:url value="/lists/${listId}" var="commentPath"/>
<c:url value="/lists/${listId}/sendRequest" var="requestPath"/>
<c:url value="/lists/${listId}/comments" var="commentsDetailPath"/>
<body class="bg-gray-50">
<div class="flex flex-col min-h-screen">
    <jsp:include page="/WEB-INF/jsp/components/navbar.jsp"/>
    <div class="col-8 offset-2 flex-grow">
        <div class="flex flex-wrap pt-4">
            <div class="col-md-auto">
                <h2 class="display-5 fw-bolder"><c:out value="${list.listName}"/></h2>
                <div class="flex justify-right">
                    <h4 class="py-2 pb-2.5">
                        <spring:message code="lists.by"/> <a class="text-purple-500 hover:text-purple-900"
                                                             href="<c:url value="/user/${user.username}"/>"><b><c:out
                            value="${user.username}"/></b></a>
                    </h4>
                    <%-- Forked From --%>
                    <c:if test="${list.forkedFrom != null}">
                        <h4 class="py-2 pb-2.5">
                            <spring:message code="lists.forkedFrom"/> <a
                                class="text-purple-500 hover:text-purple-900"
                                href="<c:url value="/lists/${list.forkedFrom.mediaListId}"/>"><b><c:out
                                value="${list.forkedFrom.listName}"/></b></a>
                        </h4>
                    </c:if>
                </div>
                <%-- Amount of Forks --%>
                <c:if test="${forks.totalCount != 0}">
                    <div class="flex">
                        <h4 class="py-2 pb-2.5">
                            <spring:message code="lists.forkedAmount"/>
                        </h4>
                        <a class="text-purple-500 hover:text-purple-900 cursor-pointer font-bold text-center px-1 pt-1.5"
                           data-bs-toggle="modal"
                           data-bs-target="#forksModal">
                            <c:out value="${forks.totalCount}"/>
                        </a>
                        <h4 class="py-2 pb-2.5">
                            <spring:message code="lists.forkedTimes" arguments="${forks.totalCount}"/>
                        </h4>
                    </div>
                </c:if>
            </div>
            <div class="flex flex-grow justify-between">
                <div class="pt-2.5">
                    <jsp:include page="/WEB-INF/jsp/components/favorite.jsp">
                        <jsp:param name="URL" value="/lists/${list.mediaListId}"/>
                        <jsp:param name="favorite" value="${isFavoriteList}"/>
                    </jsp:include>
                </div>
                <c:url var="reportPath" value="/report/lists/${listId}"/>
                <a class="py-3.5 mt-0.5" href="${reportPath}">
                    <button type="button">
                        <i class="fas fa-exclamation-circle text-right text-gray-500 justify-end hover:text-yellow-400 cursor-pointer fa-2x"
                           title="<spring:message code="report"/>"></i>
                    </button>
                </a>
            </div>
        </div>
        <p class="lead text-justify pb-2"><c:out value="${list.description}"/></p>
        <c:if test="${collaborators.totalCount != 0}">
            <h4 class="font-bold py-2 pb-2.5"><spring:message code="lists.collaborators"/></h4>
            <div class="flex flex-wrap justify-start items-center space-x-1.5 space-y-1.5">
                <c:forEach var="request" items="${collaborators.elements}">
                    <jsp:include page="/WEB-INF/jsp/components/chip.jsp">
                        <jsp:param name="text" value="${request.collaborator.username}"/>
                        <jsp:param name="tooltip" value="${request.collaborator.username}"/>
                        <jsp:param name="url" value="/user/${request.collaborator.username}"/>
                    </jsp:include>
                </c:forEach>
            </div>
        </c:if>

        <!-- Media icons -->
        <div class="flex flex-wrap justify-start">
            <jsp:include page="/WEB-INF/jsp/components/share.jsp"/>
            <c:choose>
                <c:when test="${canEdit}">
                    <div class="flex justify-center py-2">
                        <a href="${editListMediaPath}">
                            <button type="button"
                                    class="btn btn-link text-purple-500 group hover:text-purple-900 btn-rounded">
                                <i class="far fa-edit pr-2 text-purple-500 group-hover:text-purple-900"></i>
                                <spring:message code="lists.edit"/>
                            </button>
                        </a>
                    </div>
                </c:when>
                <c:when test="${list.collaborative && !canEdit}">
                    <div class="flex justify-center py-2">
                        <form action="${requestPath}" method="POST">
                            <input type="hidden" name="userId" value="${currentUser.userId}">
                            <button type="submit"
                                    class="btn btn-link text-purple-500 group hover:text-purple-900 btn-rounded">
                                <i class="fas fa-users pr-2 text-purple-500 group-hover:text-purple-900"></i>
                                <spring:message code="lists.collaborate"/>
                            </button>
                        </form>
                    </div>
                </c:when>
            </c:choose>
            <c:if test="${list.user.userId != currentUser.userId}">
                <div class="flex justify-end py-2">
                    <form:form cssClass="m-0" action="${forkPath}" method="POST">
                        <button type="submit" id="fork" name="fork"
                                class="btn btn-link text-purple-500 group hover:text-purple-900 btn-rounded">
                            <i class="far fa-copy pr-2 text-purple-500 group-hover:text-purple-900"></i>
                            <spring:message code="lists.fork"/>
                        </button>
                    </form:form>
                </div>
            </c:if>
        </div>
        <!-- Films and Series in the list -->
        <div class="row pb-4">
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
        <!-- Comments Section -->
        <div class="flex flex-col bg-white shadow-md rounded-lg pb-3">
            <div class="flex justify-between p-2.5 pb-0">
                <h2 class="font-bold text-2xl">
                    <spring:message code="comments.section"/>
                </h2>
                <a href="${commentsDetailPath}">
                    <div class="flex rounded-full p-3.5 my-1 h-6 w-auto justify-center items-center text-white bg-purple-500 hover:bg-purple-900">
                        <spring:message code="home.viewAll"/> (<c:out value="${listCommentsContainer.totalCount}"/>)
                    </div>
                </a>
            </div>
            <spring:message code="comments.placeholder" var="commentPlaceholder"/>
            <form:form modelAttribute="commentForm" action="${commentPath}" method="POST">
                <label class="p-2 text-semibold w-full flex flex-col">
                    <form:textarea path="body" rows="3"
                                   class="form-control resize-y text-base rounded-lg shadow-sm pl-3 pr-8"
                                   name="body" placeholder="${commentPlaceholder}" type="text"/>
                    <form:errors path="body" cssClass="formError text-red-500" element="p"/>
                    <input type="hidden" value="comment" name="comment" id="comment">
                    <button class="btn btn-secondary rounded-lg mt-2 bg-purple-500 hover:bg-purple-900 flex items-center w-24"
                            type="submit">
                        <spring:message code="comments.submit"/>
                    </button>
                </label>
            </form:form>
            <c:choose>
                <c:when test="${listCommentsContainer.totalCount != 0}">
                    <c:forEach var="comment" items="${listCommentsContainer.elements}">
                        <jsp:include page="/WEB-INF/jsp/components/comment.jsp">
                            <jsp:param name="username" value="${comment.user.username}"/>
                            <jsp:param name="comment" value="${comment.commentBody}"/>
                            <jsp:param name="commenterId" value="${comment.user.userId}"/>
                            <jsp:param name="currentUserId" value="${currentUser.userId}"/>
                            <jsp:param name="commentId" value="${comment.commentId}"/>
                            <jsp:param name="type" value="lists"/>
                            <jsp:param name="id" value="${listId}"/>
                            <jsp:param name="deletePath" value="/lists/${listId}/deleteComment/${comment.commentId}"/>
                            <jsp:param name="currentURL" value=""/>
                        </jsp:include>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <p class="text-center text-gray-400 m-1.5">
                        <spring:message code="comments.empty"/>
                    </p>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
    <jsp:include page="/WEB-INF/jsp/components/footer.jsp"/>
    <%-- Forks Modal--%>
    <div class="modal fade" id="forksModal" tabindex="-1" aria-labelledby="forksModalLabel"
         aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title font-bold text-2xl" id="forksModalLabel">
                        <spring:message code="lists.forks"/>
                    </h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <div class="overflow-y-auto h-50">
                        <c:forEach var="fork" items="${forks.elements}">
                            <a href="<c:url value="/lists/${fork.mediaListId}"/> ">
                                <div
                                        class="w-full h-20 bg-white overflow-hidden rounded-lg shadow-md flex justify-between transition duration-300 ease-in-out group hover:bg-gray-50 transform hover:-translate-y-1 hover:scale-107">
                                    <div class="flex">
                                        <h4 class="pl-3 py-4 text-xl font-semibold tracking-tight text-purple-500 group-hover:text-purple-900">
                                            <spring:message code="lists.forkedBy" arguments="${fork.listName}, ${fork.user.username}"/>
                                        </h4>
                                    </div>
                                </div>
                            </a>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
