<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <title><c:out value="${media.title}"/> &#8226; PopCult</title>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/components/navbar.jsp"/>
<br>
<div class="col-8 offset-2">
    <div class="row">
        <div class="col-12 col-lg-4">
            <img class="img-fluid img-thumbnail card-img-top" src="${media.image}" alt="Media Image"/>
        </div>

        <div class="col-12 col-lg-8">
            <h1 class="display-5 fw-bolder"><c:out value="${media.title}"/></h1>
            <div class="fs-5 mb-5">
                <span><c:out value="${media.releaseYear}"/></span>
                <span class="mx-3 mt-3">&#8226;</span>
                <span>Country</span>
            </div>
            <p class="lead "><c:out value="${media.description}"/></p>

            <hr>

            <c:if test="${genreList.size() > 0}">
                <h5>Genre</h5>
                <c:forEach var="genre" items="${genreList}">
                    <jsp:include page="/WEB-INF/jsp/components/chip.jsp">
                        <jsp:param name="text" value="${genre}"/>
                        <jsp:param name="tooltip" value=""/>
                        <jsp:param name="url" value="/genre/${genre}/"/>
                    </jsp:include>
                </c:forEach>
            </c:if>

            <c:if test="${studioList.size() > 0}">
                <h5><br>Production Companies</h5>
                <c:forEach var="studio" items="${studioList}">
                    <jsp:include page="/WEB-INF/jsp/components/chip.jsp">
                        <jsp:param name="text" value="${studio.name}"/>
                        <jsp:param name="tooltip" value=""/>
                        <jsp:param name="url" value="/studio/${studio.studioId}/"/>
                    </jsp:include>
                </c:forEach>
            </c:if>

            <c:if test="${directorList.size() > 0}">
                <h5><br>Director</h5>
                <c:forEach var="director" items="${directorList}">
                    <jsp:include page="/WEB-INF/jsp/components/chip.jsp">
                        <jsp:param name="text" value="${director.staffMember.name}"/>
                        <jsp:param name="tooltip" value=""/>
                        <jsp:param name="url" value="/staff/${director.staffMember.staffMemberId}"/>
                    </jsp:include>
                </c:forEach>
            </c:if>

            <c:if test="${actorList.size() > 0}">
                <h5><br>Cast</h5>
                <c:forEach var="actor" items="${actorList}">
                    <jsp:include page="/WEB-INF/jsp/components/chip.jsp">
                        <jsp:param name="text" value="${actor.staffMember.name}"/>
                        <jsp:param name="tooltip" value="${actor.characterName}"/>
                        <jsp:param name="url" value="/staff/${actor.staffMember.staffMemberId}"/>
                    </jsp:include>
                </c:forEach>
                <hr>
            </c:if>
        </div>
    </div>
</div>

</body>
</html>