<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <title>Discover new Multimedia Content &#8226; PopCult</title>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/components/navbar.jsp"/>
<div class="row">
    <c:forEach var="media" items="${discoveryLists}">
        <div class="col-12 col-sm-12 col-md-6 col-lg-4 col-xl-3">
            <jsp:include page="/WEB-INF/jsp/components/multilayeredImage.jsp">
                <jsp:param name="image" value="${media.image}"/>
            </jsp:include>
        </div>
    </c:forEach>
</div>
</body>
</html>
