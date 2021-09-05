<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <title><c:out value="${genreName}"/> &#8226; PopCult</title>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/components/navbar.jsp"/>
<div class="col-8 offset-2">
<br>

<div class="row">
    <h4>There are <c:out value="${mediaCount}"/> <c:out value="${genreName}"/> medias</h4>
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
    <jsp:param name="urlBase" value="/genre/"/>
</jsp:include>
</div>
<jsp:include page="/WEB-INF/jsp/components/footer.jsp"/>
</body>
</body>
</html>
