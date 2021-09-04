<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<html>
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <title><c:out value="${member.name}"/> &#8226; PopCult</title>
</head>
<body>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
<div class="row justify-content-center">
    <div class="col-8 clearfix">
        <img src="${member.image}" class="img-fluid img-thumbnail float-left mr-2" width="20%" alt="Media Image">
        <div class="list-group list-group-horizontal">
            <h1><c:out value="${member.name}"/></h1>
        </div>
        <p>${member.description}</p>
        <p>**********</p>
        
        <div class="accordion accordion-flush">
            <div class="accordion-item">
                <h4 class="accordion-header">
                    <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#accordion-as-actor" aria-expanded="false"
                            aria-controls="accordion-as-actor">
                        <h4>As actor:</h4>
                    </button>
                </h4>
                <div id="accordion-as-actor" class="accordion-collapse collapse show" aria-labelledby="panelsStayOpen-headingTwo">
                    <div class="accordion-body">
                        <c:forEach var="media" items="${mediaActing}">
                            <p><c:out value="${media.title}"/></p>
                        </c:forEach>
                    </div>
                </div>
            </div>
            <div class="accordion-item">
                <h4 class="accordion-header">
                    <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#accordion-as-director" aria-expanded="false"
                            aria-controls="accordion-as-director">
                        <h4>As director:</h4>
                    </button>
                </h4>
                <div id="accordion-as-director" class="accordion-collapse collapse show" aria-labelledby="panelsStayOpen-headingOne">
                    <div class="accordion-body">
                        <c:forEach var="media" items="${mediaDirecting}">
                            <p><c:out value="${media.title}"/></p>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
