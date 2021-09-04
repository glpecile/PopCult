<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!doctype html>
<html lang="en">
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
    <link rel="shortcut icon" href="<c:url value='/resources/images/favicon.ico'/>">
    <title>PopCult</title>
</head>

<body>
<jsp:include page="/WEB-INF/jsp/components/navbar.jsp"/>

<div class="col-8 offset-2">

    <h4>Recently Added Films</h4>
    <div class="container-fluid">
        <div class="row flex-row flex-nowrap overflow-auto">
            <c:forEach var="film" items="${filmsList}">
                <div class="col-12 col-sm-12 col-md-6 col-lg-4 col-xl-3">
                    <jsp:include page="/WEB-INF/jsp/components/card.jsp">
                        <jsp:param name="image" value="${film.image}"/>
                        <jsp:param name="title" value="${film.title}"/>
                        <jsp:param name="releaseDate" value="${film.releaseYear}"/>
                        <jsp:param name="mediaId" value="${film.mediaId}"/>
                    </jsp:include>
                </div>
            </c:forEach>
        </div>
    </div>

    <br>

    <h4>Recently Added Series</h4>
    <div class="container-fluid">
        <div class="row flex-row flex-nowrap overflow-auto">
            <c:forEach var="serie" items="${seriesList}">
                <div class="col-12 col-sm-12 col-md-6 col-lg-4 col-xl-3">
                    <jsp:include page="/WEB-INF/jsp/components/card.jsp">
                        <jsp:param name="image" value="${serie.image}"/>
                        <jsp:param name="title" value="${serie.title}"/>
                        <jsp:param name="releaseDate" value="${serie.releaseYear}"/>
                        <jsp:param name="mediaId" value="${serie.mediaId}"/>
                    </jsp:include>
                </div>
            </c:forEach>
        </div>
    </div>

    <br>

    <div class="row">
        <h4>Explore some Films and Series</h4>
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
</div>
<jsp:include page="/WEB-INF/jsp/components/footer.jsp"/>
<%--<script>--%>
<%--    $(function () {--%>
<%--        $('[data-toggle="tooltip"]').tooltip();--%>
<%--    });--%>
<%--</script>--%>
</body>
</html>
