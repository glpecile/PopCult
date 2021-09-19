<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<form class="m-0" action="<c:url value="/${param.URL}" />" method="POST">
    <c:choose>
        <c:when test="${param.favorite}">
            <button class="btn bg-transparent shadow-none hover:text-purple-900" type="submit" id="deleteFav" name="deleteFav" title="Remove from Favorites">
                <i class="fas fa-heart text-purple-500 hover:text-purple-900 fa-2x"></i>
            </button>
        </c:when>
        <c:otherwise>
            <button class="btn bg-transparent shadow-none hover:text-purple-900" type="submit" id="addFav" name="addFav" title="Add to Favorites">
                <i class="far fa-heart text-purple-500 hover:text-purple-900 fa-2x"></i>
            </button>
        </c:otherwise>
    </c:choose>
</form>
