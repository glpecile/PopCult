<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<form class="m-0" action="<c:url value="/${param.URL}" />" method="POST">
    <c:choose>
        <c:when test="${param.watchlisted}">
            <button class="btn bg-transparent shadow-none" type="submit" id="deleteWatchlist" name="deleteWatchlist"
                    title="Remove from Watchlist">
                <i class="fas fa-calendar-minus text-purple-500 hover:text-purple-900 fa-2x"></i>
            </button>
        </c:when>
        <c:otherwise>
            <button class="btn bg-transparent shadow-none" type="submit" id="addWatchlist" name="addWatchlist" title="Add to Watchlist">
                <i class="far fa-calendar-plus text-purple-500 hover:text-purple-900 fa-2x"></i>
            </button>
        </c:otherwise>
    </c:choose>
</form>
