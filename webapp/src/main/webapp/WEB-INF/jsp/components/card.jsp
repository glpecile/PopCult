<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="card shadow rounded-lg transition duration-500 ease-in-out hover:bg-gray-50 transform hover:-translate-y-1 hover:scale-110 shadow-inner">
    <div class="card-body">
        <%--        <span data-toggle="tooltip" data-placement="top" title="<c:out value="${param.title}"/>(<c:out value="${param.releaseDate}"/>)">--%>
        <%--        </span>--%>
        <img class="card-img-top rounded-lg" src="<c:out value="${param.image}"/>" alt="media_image">
        <%--    <h4 class="card-title"><c:out value="${param.title}"/></h4>--%>
        <%--    <h5><c:out value="${param.releaseDate}"/></h5>--%>
        <c:choose>
            <c:when test="${param.editListId != null}">
            <form action="<c:url value="/editList/${param.editListId}" />" method="POST">
                delete this media
                <input class="opacity-0" type="submit" name="mediaId"
                       value="${param.mediaId}">
            </form>
            </c:when>
            <c:otherwise>
                <a href="<c:url value="/media/${param.mediaId}"/>" class="stretched-link" title="<c:out
        value="${param.title}"/>(<c:out value="${param.releaseDate}"/>)"></a>
            </c:otherwise>
        </c:choose>

    </div>
</div>