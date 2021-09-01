<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>

<html>
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
          crossorigin="anonymous">
    <title>PopCult</title>
</head>

<body>
<div>
    <div class="row">
        <c:forEach var="media" items="${mediaList}">
            <div class="col-4">
                <div class="card">
                    <div class="card-body">
                        <img class="card-img-top" src="${media.image}" alt="Card image cap">

                        <h4 class="card-title"><c:out value="${media.title}"/></h4>
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
