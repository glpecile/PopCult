<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div>
  <form action="<c:url value="/${param.URL}" />" method="POST">
    <c:choose>
      <c:when test="${param.watchlisted}">
        <button class="btn bg-transparent shadow-none" type="submit" id="deleteWatchlist" name="deleteWatchlist" title="Remove from Watchlist">
          <i class="far fa-calendar-minus text-secondary fa-2x"></i>
        </button>
      </c:when>
      <c:otherwise>
        <button class="btn bg-transparent shadow-none" type="submit" id="addWatchlist" name="addWatchlist" title="Add to Watchlist">
          <i class="far fa-calendar-plus text-secondary fa-2x"></i>
        </button>
      </c:otherwise>
    </c:choose>
  </form>
</div>