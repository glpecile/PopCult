<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<form class="m-0" action="<c:url value="/${param.URL}" />" method="POST">
    <c:choose>
        <c:when test="${param.isWatched}">
            <button class="btn bg-transparent shadow-none" type="submit" id="deleteWatched" name="deleteWatched"
                    title="<spring:message code="watchedMedia.mark"/>">
                <i class="fas fa-eye text-purple-500 hover:text-purple-900 fa-2x"></i>
            </button>
        </c:when>
        <c:otherwise>
            <button class="btn bg-transparent shadow-none" type="submit" id="addWatched" name="addWatched"
                    title="<spring:message code="watchedMedia.unmark"/>">
                <i class="far fa-eye-slash text-purple-500 hover:text-purple-900 fa-2x"></i>
            </button>
        </c:otherwise>
    </c:choose>
</form>