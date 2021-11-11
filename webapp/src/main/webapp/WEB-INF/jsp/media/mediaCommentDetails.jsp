<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="j" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <meta property="og:image" content="<c:url value="/resources/images/PopCultCompleteLogo.png"/>">
    <jsp:include page="/resources/externalResources.jsp"/>
    <!-- favicon -->
    <link rel="shortcut icon" href="<c:url value='/resources/images/favicon.ico'/>" type="image/x-icon">
    <title><spring:message code="comments.title" arguments="${media.title}"/> &#8226; PopCult</title>

</head>
<body class="bg-gray-50">
<div class="min-h-screen flex flex-col">
    <jsp:include page="/WEB-INF/jsp/components/navbar.jsp"/>
    <br>
    <div class="col-8 offset-2 flex-grow">
        <!-- Comments Section -->
        <div class="flex flex-wrap justify-between p-2.5 pb-0">
            <h1 class="text-3xl fw-bolder py-2 pt-2">
                <spring:message code="comments.main.title"/><a class="text-purple-500 hover:text-purple-900"
                                                               href="<c:url value="/media/${mediaId}"/>"> <c:out
                    value="${media.title}"/></a>
            </h1>
            <div class="flex rounded-full p-2.5 mt-4 h-8 w-8 justify-center items-center text-base text-white bg-purple-500">
                <c:out value="${mediaCommentsContainer.totalCount}"/>
            </div>
        </div>
        <c:choose>
            <c:when test="${mediaCommentsContainer.totalCount != 0}">
                <c:forEach var="comment" items="${mediaCommentsContainer.elements}">
                    <jsp:include page="/WEB-INF/jsp/components/comment.jsp">
                        <jsp:param name="username" value="${comment.user.username}"/>
                        <jsp:param name="comment" value="${comment.commentBody}"/>
                        <jsp:param name="commenterId" value="${comment.user.userId}"/>
                        <jsp:param name="currentUserId" value="${currentUser.userId}"/>
                        <jsp:param name="commentId" value="${comment.commentId}"/>
                        <jsp:param name="type" value="lists"/>
                        <jsp:param name="id" value="${mediaId}"/>
                        <jsp:param name="deletePath" value="/media/${mediaId}/deleteComment/${comment.commentId}"/>
                        <jsp:param name="currentURL" value="/comments"/>
                    </jsp:include>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <div class="flex-col flex-wrap p-4 space-x-4">
                    <img class="w-36 object-center mx-auto" src="<c:url value="/resources/images/PopCultLogoExclamation.png"/>"
                         alt="no_results_image">
                    <p class="text-center text-gray-400 m-1.5 py-2 mt-0.5">
                        <spring:message code="comments.empty"/>
                    </p>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
    <br>
    <c:url var="urlBase" value=""/>
    <jsp:include page="/WEB-INF/jsp/components/pageNavigation.jsp">
        <jsp:param name="mediaPages" value="${mediaCommentsContainer.totalPages}"/>
        <jsp:param name="currentPage" value="${mediaCommentsContainer.currentPage + 1}"/>
        <jsp:param name="url" value="${urlBase}"/>
    </jsp:include>
    <jsp:include page="/WEB-INF/jsp/components/footer.jsp"/>
</div>
</body>
</html>