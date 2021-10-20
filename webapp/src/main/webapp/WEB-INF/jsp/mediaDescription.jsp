<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <meta property="og:image" content="<c:url value="/resources/images/PopCultCompleteLogo.png"/>">
    <jsp:include page="/resources/externalResources.jsp"/>
    <!-- favicon -->
    <link rel="shortcut icon" href="<c:url value='/resources/images/favicon.ico'/>" type="image/x-icon">
    <title><c:out value="${media.title}"/> &#8226; PopCult</title>
</head>
<c:url value="/lists/new" var="createListPath"/>
<c:url value="/media/${mediaId}" var="mediaPath"/>
<c:url value="/media/${mediaId}" var="commentPath"/>
<c:url value="/media/${mediaId}/comments" var="commentsDetailPath"/>
<c:url value="/media/${mediaId}/lists" var="viewAllListsPath"/>
<body class="bg-gray-50">
<div class="min-h-screen flex flex-col">
    <jsp:include page="/WEB-INF/jsp/components/navbar.jsp"/>
    <br>
    <div class="col-8 offset-2 flex-grow">
        <div class="row">
            <div class="col-12 col-lg-4">
                <img class="img-fluid img-thumbnail card-img-top rounded-lg shadow-md" src="${media.image}"
                     alt="Media Image"/>
                <!-- Button Grid. Possible TODO: Make component. -->
                <div class="grid auto-rows-min shadow-md rounded-lg divide-y divide-fuchsia-300 my-3 bg-white">
                    <%-- Icon row --%>
                    <div class="flex justify-around pt-2">
                        <jsp:include page="/WEB-INF/jsp/components/favorite.jsp">
                            <jsp:param name="URL" value="media/${mediaId}"/>
                            <jsp:param name="favorite" value="${isFavoriteMedia}"/>
                        </jsp:include>
                        <jsp:include page="/WEB-INF/jsp/components/watchedMedia.jsp">
                            <jsp:param name="URL" value="media/${mediaId}"/>
                            <jsp:param name="isWatched" value="${isWatchedMedia}"/>
                        </jsp:include>
                        <jsp:include page="/WEB-INF/jsp/components/watchlist.jsp">
                            <jsp:param name="URL" value="media/${mediaId}"/>
                            <jsp:param name="watchlisted" value="${isToWatchMedia}"/>
                        </jsp:include>
                    </div>
                    <%-- Share --%>
                    <jsp:include page="/WEB-INF/jsp/components/share.jsp"/>
                    <%-- Dropdown lists list --%>
                    <sec:authorize access="isAuthenticated()">
                        <div class="dropdown flex justify-center py-2 shadow-md">
                            <button class="btn btn-link text-purple-500 hover:text-purple-900 dropdown-toggle btn-rounded"
                                    type="button"
                                    id="addMediaToList"
                                    data-bs-toggle="dropdown" aria-expanded="false">
                                <spring:message code="media.addToList"/>
                            </button>
                            <ul class="dropdown-menu rounded-lg" aria-labelledby="Add Media to List">
                                <c:forEach var="list" items="${userLists}">
                                    <form action="${mediaPath}" method="POST">
                                        <button class="dropdown-item p-1.5" type="submit"><c:out
                                                value="${list.listName}"/></button>
                                        <input type="hidden" id="mediaListId" name="mediaListId"
                                               value="<c:out value = "${list.mediaListId}"/>">
                                    </form>
                                </c:forEach>
                                <a class="dropdown-item p-1.5" href=${createListPath}>
                                    <spring:message code="lists.create"/>
                                </a>
                            </ul>
                        </div>
                    </sec:authorize>
                </div>
                <!-- End component -->
            </div>

            <div class="col-12 col-lg-8 mb-1.5">
                <div class="row justify-content-start">
                    <div class="col-md-auto">
                        <h1 class="display-5 fw-bolder"><c:out value="${media.title}"/></h1>
                    </div>
                </div>
                <div class="text-xl py-2">
                    <span><c:out value="${media.releaseYear}"/></span>
                    <span class="mx-3 mt-3">&#8226;</span>
                    <span><c:out value="${media.country.countryName}"/></span>
                </div>
                <p class="lead text-justify"><c:out value="${media.description}"/></p>
                <br>
                <c:if test="${fn:length(media.genres) > 0}">
                    <h5 class="font-bold text-2xl py-2">
                        <spring:message code="media.genre"/>
                    </h5>
                    <div class="flex flex-wrap justify-start items-center space-x-1.5 space-y-1.5">
                        <c:forEach var="genre" items="${media.genres}">
                            <jsp:include page="/WEB-INF/jsp/components/chip.jsp">
                                <jsp:param name="text" value="${genre.genre}"/>
                                <jsp:param name="tooltip" value=""/>
                                <jsp:param name="url" value="/genre/${genre.genre}/"/>
                            </jsp:include>
                        </c:forEach>
                    </div>
                </c:if>

                <c:if test="${fn:length(media.studios) > 0}">
                    <h5 class="font-bold text-2xl py-2"><br>
                        <spring:message code="media.studio"/>
                    </h5>
                    <div class="flex flex-wrap justify-start items-center space-x-1.5 space-y-1.5">
                        <c:forEach var="studio" items="${media.studios}">
                            <jsp:include page="/WEB-INF/jsp/components/chip.jsp">
                                <jsp:param name="text" value="${studio.name}"/>
                                <jsp:param name="tooltip" value=""/>
                                <jsp:param name="url" value="/studio/${studio.studioId}/"/>
                            </jsp:include>
                        </c:forEach>
                    </div>
                </c:if>

                <c:if test="${fn:length(directorList) > 0}">
                    <h5 class="font-bold text-2xl py-2"><br>
                        <spring:message code="media.director"/>
                    </h5>
                    <div class="flex flex-wrap justify-start items-center space-x-1.5 space-y-1.5">
                        <c:forEach var="director" items="${directorList}">
                            <jsp:include page="/WEB-INF/jsp/components/chip.jsp">
                                <jsp:param name="text" value="${director.staffMember.name}"/>
                                <jsp:param name="tooltip" value=""/>
                                <jsp:param name="url" value="/staff/${director.staffMember.staffMemberId}"/>
                            </jsp:include>
                        </c:forEach>
                    </div>
                </c:if>

                <c:if test="${fn:length(actorList) > 0}">
                    <h5 class="font-bold text-2xl py-2"><br>
                        <spring:message code="media.cast"/>
                    </h5>
                    <div class="flex flex-wrap justify-start items-center space-x-1.5 space-y-1.5">
                        <c:forEach var="actor" items="${actorList}">
                            <jsp:include page="/WEB-INF/jsp/components/chip.jsp">
                                <jsp:param name="text" value="${actor.staffMember.name}"/>
                                <jsp:param name="tooltip" value="${actor.characterName}"/>
                                <jsp:param name="url" value="/staff/${actor.staffMember.staffMemberId}"/>
                            </jsp:include>
                        </c:forEach>
                    </div>
                    <br>
                </c:if>
            </div>
        </div>
        <div class="row">
            <c:if test="${fn:length(relatedLists) > 0}">
                <div class="flex justify-between">
                    <h2 class="font-bold text-2xl py-2">
                        <spring:message code="lists.contain.media" arguments="${media.title}"/>
                    </h2>
                    <a href="${viewAllListsPath}">
                        <div class="flex rounded-full p-3.5 my-1 h-6 w-auto justify-center items-center text-white bg-purple-500 hover:bg-purple-900">
                            <spring:message code="home.viewAll"/>
                        </div>
                    </a>
                </div>
                <c:forEach var="cover" items="${relatedLists}">
                    <div class="col-12 col-sm-12 col-md-6 col-lg-4 col-xl-3 py-3">
                        <jsp:include page="/WEB-INF/jsp/components/gridCard.jsp">
                            <jsp:param name="title" value="${cover.name}"/>
                            <jsp:param name="listId" value="${cover.listId}"/>
                            <jsp:param name="image1" value="${cover.image1}"/>
                            <jsp:param name="image2" value="${cover.image2}"/>
                            <jsp:param name="image3" value="${cover.image3}"/>
                            <jsp:param name="image4" value="${cover.image4}"/>
                        </jsp:include>
                    </div>
                </c:forEach>
            </c:if>
        </div>
        <!-- Comments Section -->
        <div class="flex flex-col bg-white shadow-md rounded-lg pb-3 my-2.5">
            <div class="flex justify-between p-2.5 pb-0">
                <h2 class="font-bold text-2xl">
                    <spring:message code="comments.section"/>
                </h2>
                <a href="${commentsDetailPath}">
                    <div class="flex rounded-full p-3.5 my-1 h-6 w-auto justify-center items-center text-white bg-purple-500 hover:bg-purple-900">
                        <spring:message code="home.viewAll"/> (<c:out value="${mediaCommentsContainer.totalCount}"/>)
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
                <c:when test="${mediaCommentsContainer.totalCount != 0}">
                    <c:forEach var="comment" items="${mediaCommentsContainer.elements}">
                        <jsp:include page="/WEB-INF/jsp/components/comment.jsp">
                            <jsp:param name="username" value="${comment.username}"/>
                            <jsp:param name="comment" value="${comment.commentBody}"/>
                            <jsp:param name="commenterId" value="${comment.userId}"/>
                            <jsp:param name="currentUserId" value="${currentUser.userId}"/>
                            <jsp:param name="commentId" value="${comment.commentId}"/>
                            <jsp:param name="type" value="media"/>
                            <jsp:param name="id" value="${mediaId}"/>
                            <jsp:param name="deletePath" value="/media/${mediaId}/deleteComment/${comment.commentId}"/>
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
</div>
</body>
</html>