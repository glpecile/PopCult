<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <jsp:include page="/resources/externalResources.jsp"/>
    <!-- favicon -->
    <link rel="shortcut icon" href="<c:url value='/resources/images/favicon.ico'/>" type="image/x-icon">
    <title><c:out value="${media.title}"/> &#8226; PopCult</title>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/components/navbar.jsp"/>
<br>
<div class="col-8 offset-2">
    <div class="row">
        <div class="col-12 col-lg-4">
            <img class="img-fluid img-thumbnail card-img-top rounded-lg" src="${media.image}" alt="Media Image"/>
            <jsp:include page="/WEB-INF/jsp/components/share.jsp"/>
            <%-- dropdown lists list--%>
            <div class="dropdown flex justify-center py-2">
                <button class="btn btn-secondary dropdown-toggle btn-rounded" type="button" id="addMediaToList"
                        data-bs-toggle="dropdown" aria-expanded="false">
                    Add to a list
                </button>
                <ul class="dropdown-menu" aria-labelledby="Add Media to List">
                    <c:forEach var="list" items="${userLists}">
                        <form action="<c:url value="/media/${mediaId}" />" method="POST">
                            <button class="dropdown-item" type="submit"><c:out value="${list.name}"/></button>
                            <input type="hidden" id="mediaListId" name="mediaListId"
                                   value="<c:out value = "${list.mediaListId}"/>">
                        </form>
                    </c:forEach>
                </ul>
            </div>
        </div>
        <div class="col-12 col-lg-8">
            <div class="row justify-content-start">
                <div class="col-md-auto">
                    <h1 class="display-5 fw-bolder"><c:out value="${media.title}"/></h1>
                </div>
                <div class="col-md-auto pt-2">
                    <jsp:include page="/WEB-INF/jsp/components/favorite.jsp">
                        <jsp:param name="URL" value="media/${mediaId}"/>
                        <jsp:param name="favorite" value="${isFavoriteMedia}"/>
                    </jsp:include>
                </div>
                <div class="col col-lg-2 pt-2">
                    <jsp:include page="/WEB-INF/jsp/components/watchedMedia.jsp">
                        <jsp:param name="URL" value="media/${mediaId}"/>
                        <jsp:param name="isWatched" value="${isWatchedMedia}"/>
                    </jsp:include>
                </div>
            </div>
            <div class="text-xl py-2">
                <span><c:out value="${media.releaseYear}"/></span>
                <span class="mx-3 mt-3">&#8226;</span>
                <span><c:out value="${media.country}"/></span>
            </div>

            <p class="lead text-justify"><c:out value="${media.description}"/></p>

            <br>

            <c:if test="${genresAmount > 0}">
                <h5 class="font-bold text-2xl py-2">Genre</h5>
                <div class="flex flex-wrap justify-start items-center space-x-1.5 space-y-1.5">
                    <c:forEach var="genre" items="${genreList}">
                        <jsp:include page="/WEB-INF/jsp/components/chip.jsp">
                            <jsp:param name="text" value="${genre}"/>
                            <jsp:param name="tooltip" value=""/>
                            <jsp:param name="url" value="/genre/${genre}/"/>
                        </jsp:include>
                    </c:forEach>
                </div>
            </c:if>

            <c:if test="${studiosAmount > 0}">
                <h5 class="font-bold text-2xl py-2"><br>Production Companies</h5>
                <div class="flex flex-wrap justify-start items-center space-x-1.5 space-y-1.5">
                    <c:forEach var="studio" items="${studioList}">
                        <jsp:include page="/WEB-INF/jsp/components/chip.jsp">
                            <jsp:param name="text" value="${studio.name}"/>
                            <jsp:param name="tooltip" value=""/>
                            <jsp:param name="url" value="/studio/${studio.studioId}/"/>
                        </jsp:include>
                    </c:forEach>
                </div>
            </c:if>

            <c:if test="${directorsAmount > 0}">
                <h5 class="font-bold text-2xl py-2"><br>Director</h5>
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

            <c:if test="${actorsAmount > 0}">
                <h5 class="font-bold text-2xl py-2"><br>Cast</h5>
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
        <c:if test="${relatedListsAmount > 0}">
            <h2 class="font-bold text-2xl py-2">Popular Lists</h2>
            <c:forEach var="cover" items="${relatedLists}">
                <div class="col-12 col-sm-12 col-md-6 col-lg-4 col-xl-3 py-2">
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
            <br>
            <jsp:include page="/WEB-INF/jsp/components/pageNavigation.jsp">
                <jsp:param name="mediaPages" value="${popularListPages}"/>
                <jsp:param name="currentPage" value="${currentPage}"/>
                <jsp:param name="url" value="/lists?"/>
            </jsp:include>
        </c:if>
    </div>
</div>
<jsp:include page="/WEB-INF/jsp/components/footer.jsp"/>
</body>
</html>