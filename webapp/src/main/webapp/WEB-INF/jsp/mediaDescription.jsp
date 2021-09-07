<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <!-- favicon -->
    <link rel="shortcut icon" href="<c:url value='/resources/images/favicon.ico'/>" type="image/x-icon">
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
    <title><c:out value="${media.title}"/> &#8226; PopCult</title>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/components/navbar.jsp"/>
<br>
<div class="col-8 offset-2">
    <div class="row">
        <div class="col-12 col-lg-4">
            <img class="img-fluid img-thumbnail card-img-top rounded-lg" src="${media.image}" alt="Media Image"/>
        </div>

        <div class="col-12 col-lg-8">
            <h1 class="display-5 fw-bolder"><c:out value="${media.title}"/></h1>
            <div class="text-xl py-2">
                <span><c:out value="${media.releaseYear}"/></span>
                <span class="mx-3 mt-3">&#8226;</span>
                <span><c:out value="${media.country}"/></span>
            </div>

            <p class="lead text-justify"><c:out value="${media.description}"/></p>

            <br>

            <c:if test="${genreList.size() > 0}">
                <h5 class="font-bold text-2xl py-2">Genre</h5>
                <div class="flex flex-wrap justify-start items-center space-x-1.5 space-y-1.5">
                    <c:forEach var="genre" items="${genreList}">
                        <jsp:include page="/WEB-INF/jsp/components/chip.jsp">
                            <jsp:param name="text" value="${genre}"/>
                            <jsp:param name="tooltip" value=""/>
                            <jsp:param name="url" value="/genre/${genre}/"/>
                        </jsp:include>
                    </c:forEach>
                </div>
            </c:if>

            <c:if test="${studioList.size() > 0}">
                <h5 class="font-bold text-2xl py-2"><br>Production Companies</h5>
                <div class="flex flex-wrap justify-start items-center space-x-1.5 space-y-1.5">
                    <c:forEach var="studio" items="${studioList}">
                        <jsp:include page="/WEB-INF/jsp/components/chip.jsp">
                            <jsp:param name="text" value="${studio.name}"/>
                            <jsp:param name="tooltip" value=""/>
                            <jsp:param name="url" value="/studio/${studio.studioId}/"/>
                        </jsp:include>
                    </c:forEach>
                </div>
            </c:if>

            <c:if test="${directorList.size() > 0}">
                <h5 class="font-bold text-2xl py-2"><br>Director</h5>
                <div class="flex flex-wrap justify-start items-center space-x-1.5 space-y-1.5">
                    <c:forEach var="director" items="${directorList}">
                        <jsp:include page="/WEB-INF/jsp/components/chip.jsp">
                            <jsp:param name="text" value="${director.staffMember.name}"/>
                            <jsp:param name="tooltip" value=""/>
                            <jsp:param name="url" value="/staff/${director.staffMember.staffMemberId}"/>
                        </jsp:include>
                    </c:forEach>
                </div>
            </c:if>

            <c:if test="${actorList.size() > 0}">
                <h5 class="font-bold text-2xl py-2"><br>Cast</h5>
                <div class="flex flex-wrap justify-start items-center space-x-1.5 space-y-1.5">
                    <c:forEach var="actor" items="${actorList}">
                        <jsp:include page="/WEB-INF/jsp/components/chip.jsp">
                            <jsp:param name="text" value="${actor.staffMember.name}"/>
                            <jsp:param name="tooltip" value="${actor.characterName}"/>
                            <jsp:param name="url" value="/staff/${actor.staffMember.staffMemberId}"/>
                        </jsp:include>
                    </c:forEach>
                </div>
                <br>
            </c:if>
        </div>
    </div>
    <div class="row">
        <h2 class="font-bold text-2xl py-2">Popular Lists</h2>
        <c:forEach var="cover" items="${relatedLists}">
            <div class="col-12 col-sm-12 col-md-6 col-lg-4 col-xl-3">
                <jsp:include page="/WEB-INF/jsp/components/gridCard.jsp">
                    <jsp:param name="title" value="${cover.name}"/>
                    <jsp:param name="listId" value="${cover.listId}"/>
                    <jsp:param name="image1" value="${cover.image1}"/>
                    <jsp:param name="image2" value="${cover.image2}"/>
                    <jsp:param name="image3" value="${cover.image3}"/>
                    <jsp:param name="image4" value="${cover.image4}"/>
                </jsp:include>
            </div>
        </c:forEach>
        <br>
        <jsp:include page="/WEB-INF/jsp/components/pageNavigation.jsp">
            <jsp:param name="mediaPages" value="${popularListPages}"/>
            <jsp:param name="currentPage" value="${currentPage}"/>
            <jsp:param name="urlBase" value="/lists"/>
        </jsp:include>
    </div>
</div>
<jsp:include page="/WEB-INF/jsp/components/footer.jsp"/>
</body>
</html>