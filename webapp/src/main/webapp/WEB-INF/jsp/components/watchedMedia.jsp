<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<form class="m-0" action="<c:url value="/${param.URL}" />" method="POST">
    <c:choose>
        <c:when test="${param.isWatched}">
            <button class="btn bg-transparent shadow-none" type="submit" id="deleteWatched" name="deleteWatched" title="Set as not watched">
                <i class="fas fa-eye text-purple-500 hover:text-purple-900 fa-2x"></i>
            </button>
        </c:when>
        <c:otherwise>
            <button class="btn bg-transparent shadow-none" type="submit" id="addWatched" name="addWatched" title="Set as watched">
                <i class="far fa-eye-slash text-purple-500 hover:text-purple-900 fa-2x"></i>
            </button>
        </c:otherwise>
    </c:choose>
</form>