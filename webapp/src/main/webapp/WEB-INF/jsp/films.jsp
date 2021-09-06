<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!doctype html>
<html lang="en">
<head>
  <!-- BS5 -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
  <!-- Font Awesome -->
  <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css" rel="stylesheet"/>
  <!-- Google Fonts -->
  <link href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700&display=swap" rel="stylesheet"/>
  <!-- MDB -->
  <link href="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/3.6.0/mdb.min.css" rel="stylesheet"/>
  <!-- Tailwind -->
  <link href="https://unpkg.com/tailwindcss@^2/dist/tailwind.min.css" rel="stylesheet">
  <!-- BS5 -->
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
          integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
  <!-- MDB -->
  <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/3.6.0/mdb.min.js"></script>
  <link rel="shortcut icon" href="<c:url value='/resources/images/favicon.ico'/>">
  <title>PopCult</title>
</head>

<body>
<jsp:include page="/WEB-INF/jsp/components/navbar.jsp"/>

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
<jsp:include page="/WEB-INF/jsp/components/footer.jsp"/>
</body>
</html>
