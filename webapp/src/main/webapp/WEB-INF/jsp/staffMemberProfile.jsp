<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<html>
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
    <title><c:out value="${member.name}"/> &#8226; PopCult</title>

</head>
<body>
<jsp:include page="/WEB-INF/jsp/components/navbar.jsp"/>
<br>
<div class="col-8 offset-2">
    <div class="row justify-content-center">
        <div class="col-4 clearfix">
            <img src="${member.image}" class="img-fluid img-thumbnail float-left mr-2" alt="Media Image">
        </div>
        <div class="col-8">
            <div class="list-group list-group-horizontal">
                <h1><c:out value="${member.name}"/></h1>
            </div>
            <p>${member.description}</p>
        </div>
    </div>
    <br>
    <hr>
    <br>
    <div class="dropdown">
        <button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">
            Role
        </button>
        <ul class="dropdown-menu" aria-labelledby="dropdownMenuButton1">
            <li><a class="dropdown-item" href="<c:url value="/staff/${staffMemberId}/actor"/>">Actor</a></li>
            <li><a class="dropdown-item" href="<c:url value='/staff/${staffMemberId}/director'/>">Director</a></li>
        </ul>
    </div>
    <br>
    <div class="row">
        <c:choose>
            <c:when test="${roleType == 'actor'}">
                <h4>Films & Series starring ${member.name}</h4>
            </c:when>
            <c:when test="${roleType == 'director'}">
                <h4>Films & Series directed by ${member.name}</h4>
            </c:when>
            <c:otherwise>
                <h4>Known for</h4>
            </c:otherwise>
        </c:choose>
        <c:forEach var="media" items="${media}">
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
    <jsp:include page="/WEB-INF/jsp/components/pageNavigation.jsp">
        <jsp:param name="mediaPages" value="${mediaPages}"/>
        <jsp:param name="currentPage" value="${currentPage}"/>
        <jsp:param name="urlBase" value="${urlBase}"/>
    </jsp:include>
</div>
<jsp:include page="/WEB-INF/jsp/components/footer.jsp"/>
</body>
</html>