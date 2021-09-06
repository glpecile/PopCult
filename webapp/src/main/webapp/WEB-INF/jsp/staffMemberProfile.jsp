<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<html>
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
    <title><c:out value="${member.name}"/> &#8226; PopCult</title>

</head>
<body>
<jsp:include page="/WEB-INF/jsp/components/navbar.jsp"/>
<br>
<div class="col-8 offset-2">
    <div class="row">
        <div class="col-12 col-lg-4">
            <img src="${member.image}" class="img-fluid img-thumbnail card-img-top rounded-lg" alt="Media Image">
        </div>
        <div class="col-12 col-lg-8">
            <h1 class="display-5 fw-bolder"><c:out value="${member.name}"/></h1>
            <p class="lead text-justify"><c:out value="${member.description}"/></p>
        </div>
    </div>
    <br>
    <div class="flex text-center">
        <div class="dropdown pr-4">
            <button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton1" data-bs-toggle="dropdown"
                    aria-expanded="false">
                Role
            </button>
            <ul class="dropdown-menu shadow-lg" aria-labelledby="dropdownMenuButton1">
                <li><a class="dropdown-item" href="<c:url value="/staff/${staffMemberId}/actor"/>">Actor</a></li>
                <li><a class="dropdown-item" href="<c:url value='/staff/${staffMemberId}/director'/>">Director</a></li>
            </ul>
        </div>
        <c:choose>
            <c:when test="${roleType == 'actor'}">
                <h4 class="font-bold text-2xl">Films & Series starring ${member.name}</h4>
            </c:when>
            <c:when test="${roleType == 'director'}">
                <h4 class="font-bold text-2xl">Films & Series directed by ${member.name}</h4>
            </c:when>
            <c:otherwise>
                <h4 class="font-bold text-2xl">Known for</h4>
            </c:otherwise>
        </c:choose>
    </div>
    <br>

    <div class="row">
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