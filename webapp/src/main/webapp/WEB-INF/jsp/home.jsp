<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>

<html>
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
          crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
    <title>PopCult</title>
</head>

<body>
<jsp:include page="/WEB-INF/jsp/components/navbar.jsp" />
<div class="col-8 offset-2">
    <div class="row">
        <c:forEach var="media" items="${mediaList}">
            <div class="col-12 col-sm-12 col-md-6 col-lg-4 col-xl-3">
                <div class="card">
                    <div class="card-body">
                        <img class="card-img-top" src="${media.image}" alt="Card image cap">

                        <h4 class="card-title"><c:out value="${media.title}"/></h4>
                        <h5><c:out value="${media.releaseDate}"/></h5>
                            <%--                        <p class="card-text">Nullam id dolor id nibh ultricies vehicula ut id elit. Cras justo odio, dapibus ac facilisis in, egestas eget quam. Donec id elit non mi porta gravida at eget metus.</p>--%>
                            <%--                        <button class="btn btn-primary" type="button">Button</button>--%>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>
</body>
</html>
