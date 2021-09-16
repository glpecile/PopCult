<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div>
    <form action="<c:url value="/${param.URL}" />" method="POST">
        <c:choose>
            <c:when test="${param.favorite}">
                <button class="btn bg-transparent shadow-none" type="submit" id="deleteFav" name="deleteFav">
                    <i class="fas fa-heart text-secondary fa-2x"></i>
                </button>
            </c:when>
            <c:otherwise>
                <button class="btn bg-transparent shadow-none" type="submit" id="addFav" name="addFav">
                    <i class="far fa-heart text-secondary fa-2x"></i>
                </button>
            </c:otherwise>
        </c:choose>
    </form>
</div>