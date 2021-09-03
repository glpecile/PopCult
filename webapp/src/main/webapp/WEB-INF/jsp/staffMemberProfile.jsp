<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<html>
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
          crossorigin="anonymous">
    <title><c:out value="${member.name}"/> &#8226; PopCult</title>
</head>
<body>
<div class="row justify-content-center">
    <div class="col-8 clearfix">
        <img src="${member.image}" class="img-fluid img-thumbnail float-left mr-2" width="20%" alt="Media Image">
        <div class="list-group list-group-horizontal">
            <h1><c:out value="${member.name}"/></h1>
        </div>
        <p>${member.description}</p>
        <c:forEach var="media" items="${mediaActing}">
            <p><c:out value="${media.title}"/></p>
        </c:forEach>
        <c:forEach var="media" items="${mediaDirecting}">
            <p><c:out value="${media.title}"/></p>
        </c:forEach>
    </div>
</div>
</body>
</html>
