<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<html>
<head>
    <jsp:include page="/resources/externalResources.jsp"/>
    <!-- favicon -->
    <link rel="shortcut icon" href="<c:url value='/resources/images/favicon.ico'/>" type="image/x-icon">
    <title><c:out value="${user.userName}"/> &#8226; PopCult</title>

</head>
<body>
<jsp:include page="/WEB-INF/jsp/components/navbar.jsp"/>
<br>
<div class="col-8 offset-2">
    <div class="row">
        <div class="col-12 col-lg-4">
            <img src="${user.profilePhotoURL}" class="img-fluid img-thumbnail card-img-top rounded-lg" alt="Profile Image">
        </div>
        <div class="col-12 col-lg-8">
            <h1 class="display-5 fw-bolder"><c:out value="${user.name}"/></h1>
            <jsp:include page="/WEB-INF/jsp/components/edit.jsp"/>
            <p class="lead text-justify"><c:out value="@${user.userName}"/></p>
        </div>
    </div>

</div>
<jsp:include page="/WEB-INF/jsp/components/footer.jsp"/>
</body>
</html>