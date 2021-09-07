<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!doctype html>
<html lang="en">
<head>
  <jsp:include page="/resources/externalResources.jsp"/>
  <!-- favicon -->
  <link rel="shortcut icon" href="<c:url value='/resources/images/favicon.ico'/>" type="image/x-icon">
  <title>PopCult</title>
</head>

<body>
<jsp:include page="/WEB-INF/jsp/components/navbar.jsp"/>
<c:choose>
  <c:when test="${mediaList.size() == 0}">
      <br>
      <h3 style="text-align:center"> Sorry, we don't have movies to show you right now.</h3>
  </c:when>
  <c:otherwise>
    <div class="col-8 offset-2">
      <br>

      <h4>Recently Added Series</h4>
      <div class="container-fluid">
        <div class="row flex-row flex-nowrap overflow-auto">
          <c:forEach var="latestFilm" items="${latestFilms}">
            <div class="col-12 col-sm-12 col-md-6 col-lg-4 col-xl-3">
              <jsp:include page="/WEB-INF/jsp/components/card.jsp">
                <jsp:param name="image" value="${latestFilm.image}"/>
                <jsp:param name="title" value="${latestFilm.title}"/>
                <jsp:param name="releaseDate" value="${latestFilm.releaseYear}"/>
                <jsp:param name="mediaId" value="${latestFilm.mediaId}"/>
              </jsp:include>
            </div>
          </c:forEach>
        </div>
      </div>

      <hr>

      <div class="row">
        <h4>Explore some Series</h4>
        <c:forEach var="media" items="${mediaList}">
          <div class="col-12 col-sm-12 col-md-6 col-lg-4 col-xl-3">
            <jsp:include page="/WEB-INF/jsp/components/card.jsp">
              <jsp:param name="image" value="${media.image}"/>
              <jsp:param name="title" value="${media.title}"/>
              <jsp:param name="releaseDate" value="${media.releaseYear}"/>
              <jsp:param name="mediaId" value="${media.mediaId}"/>
            </jsp:include>
          </div>
        </c:forEach>
      </div>

      <br>

      <jsp:include page="/WEB-INF/jsp/components/pageNavigation.jsp">
        <jsp:param name="mediaPages" value="${mediaPages}"/>
        <jsp:param name="currentPage" value="${currentPage}"/>
        <jsp:param name="urlBase" value="/films/"/>
      </jsp:include>
    </div>
  </c:otherwise>
</c:choose>
<jsp:include page="/WEB-INF/jsp/components/footer.jsp"/>
</body>
</html>
