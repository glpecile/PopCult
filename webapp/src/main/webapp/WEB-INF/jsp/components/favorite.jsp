<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<form class="m-0" action="<c:url value="/${param.URL}" />" method="POST">
    <c:choose>
        <c:when test="${param.favorite}">
            <button class="btn bg-transparent shadow-none hover:text-purple-900" type="submit" id="deleteFav" name="deleteFav"
                    title="<spring:message code="fav.remove"/>">
                <i class="fas fa-heart text-purple-500 hover:text-purple-900 fa-2x"></i>
            </button>
        </c:when>
        <c:otherwise>
            <button class="btn bg-transparent shadow-none hover:text-purple-900" type="submit" id="addFav" name="addFav"
                    title="<spring:message code="fav.add"/>">
                <i class="far fa-heart text-purple-500 hover:text-purple-900 fa-2x"></i>
            </button>
        </c:otherwise>
    </c:choose>
</form>
