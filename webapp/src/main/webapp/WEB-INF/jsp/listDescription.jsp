<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <jsp:include page="/resources/externalResources.jsp"/>
    <!-- favicon -->
    <link rel="shortcut icon" href="<c:url value='/resources/images/favicon.ico'/>" type="image/x-icon">
    <title><c:out value="${list.listName}"/> &#8226; PopCult</title>
</head>
<c:url value="/lists/${list.mediaListId}" var="forkPath"/>
<c:url value="/lists/edit/${list.mediaListId}/manageMedia" var="editListMediaPath"/>
<c:url value="/lists/${listId}/comment" var="commentPath"/>
<c:url value="/lists/${listId}/sendRequest" var="requestPath"/>

<body class="bg-gray-50">
<div class="flex flex-col min-h-screen">
    <jsp:include page="/WEB-INF/jsp/components/navbar.jsp"/>
    <div class="col-8 offset-2 flex-grow">
        <div class="flex justify-content-start content-center pt-4">
            <div class="col-md-auto">
                <h2 class="display-5 fw-bolder"><c:out value="${list.listName}"/></h2>
                <h4 class="py-2 pb-2.5"><a class="hover:text-gray-800" href="<c:url value="/user/${user.username}"/>">
                    by: <b class="text-purple-500 hover:text-purple-900"><c:out value="${user.username}"/></b>
                </a></h4>
            </div>
            <div class="pt-2.5">
                <jsp:include page="/WEB-INF/jsp/components/favorite.jsp">
                    <jsp:param name="URL" value="/lists/${list.mediaListId}"/>
                    <jsp:param name="favorite" value="${isFavoriteList}"/>
                </jsp:include>
            </div>
        </div>
        <p class="lead text-justify pb-2"><c:out value="${list.description}"/></p>
        <!-- Media icons -->
        <div class="flex flex-wrap justify-start">
            <jsp:include page="/WEB-INF/jsp/components/share.jsp"/>
            <c:choose>
                <c:when test="${list.userId == currentUser.userId}">
                    <div class="flex justify-center py-2">

                        <a href="${editListMediaPath}">
                            <button type="button"
                                    class="btn btn-link text-purple-500 hover:text-purple-900 btn-rounded">
                                <i class="far fa-edit pr-2 text-purple-500 hover:text-purple-900"></i>
                                Edit list
                            </button>
                        </a>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="flex justify-center py-2">
                        <form:form cssClass="m-0" action="${forkPath}" method="POST">
                            <button type="submit" id="fork" name="fork"
                                    class="btn btn-link text-purple-500 hover:text-purple-900 btn-rounded">
                                <i class="far fa-copy pr-2 text-purple-500 hover:text-purple-900"></i>
                                Fork this list
                            </button>
                        </form:form>
                    </div>
                    <c:if test="${list.collaborative}">
                        <div class="flex justify-end py-2">
                            <form action="${requestPath}" method="POST">
                                <input type="hidden" name="userId" value="${user.userId}">
                                <button type="submit"
                                        class="btn btn-link text-purple-500 hover:text-purple-900 btn-rounded">
                                    <i class="fas fa-users pr-2 text-purple-500 hover:text-purple-900"></i>
                                    Ask to collaborate
                                </button>
                            </form>
                        </div>
                    </c:if>
                </c:otherwise>
            </c:choose>
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
                <div class="flex rounded-full p-2.5 my-1 h-6 w-6 justify-center items-center text-white bg-purple-500">
                    ${listCommentsContainer.totalCount}
                </div>
            </div>
            <spring:message code="comments.placeholder" var="commentPlaceholder"/>
            <form:form modelAttribute="commentForm" action="${commentPath}" method="POST">
                <label class="p-2 text-semibold w-full flex flex-col">
                    <form:textarea path="body" rows="3"
                                   class="form-control resize-y text-base rounded-lg shadow-sm pl-3 pr-8"
                                   name="body" placeholder="${commentPlaceholder}" type="text"/>
                    <form:errors path="body" cssClass="formError text-red-500" element="p"/>
                    <input type="hidden" value="<c:out value="${currentUser.userId}"/>" name="userId" id="userId">
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
                            <jsp:param name="username" value="${comment.username}"/>
                            <jsp:param name="comment" value="${comment.commentBody}"/>
                            <jsp:param name="commenterId" value="${comment.userId}"/>
                            <jsp:param name="currentUserId" value="${currentUser.userId}"/>
                            <jsp:param name="commentId" value="${comment.commentId}"/>
                            <jsp:param name="deletePath" value="/lists/${listId}/deleteComment"/>
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
</div>
</body>
</html>
