<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="j" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <jsp:include page="/resources/externalResources.jsp"/>
    <!-- favicon -->
    <link rel="shortcut icon" href="<c:url value='/resources/images/favicon.ico'/>" type="image/x-icon">
    <title>Profile &#8226; PopCult</title>

</head>
<body>
<jsp:include page="/WEB-INF/jsp/components/navbar.jsp">
    <jsp:param name="user" value="${user.userName}"/>
</jsp:include>
<br>
<div class="col-8 offset-2">
    <div class="row py-2">
        <div class="col-12 col-lg-4">
            <img src="https://media.discordapp.net/attachments/872172548102705162/887167057236484156/cross-png-icon-2.png?width=413&height=413"
                 class="rounded-circle img-fluid img-thumbnail card-img-top "
                 alt="Profile Image">
        </div>
        <div class="col-12 col-lg-8">
            <h1 class="display-5 fw-bolder"><c:out value="${user.name}"/></h1>
<%--            <jsp:include page="/WEB-INF/jsp/components/edit.jsp"/>--%>
            <p class="lead text-justify"><c:out value="@${user.userName}"/></p>
        </div>
    </div>
    <div class="row">
        <h2 class="font-bold text-2xl py-2">My lists</h2>
        <c:forEach var="cover" items="${lists}">
            <div class="col-12 col-sm-12 col-md-6 col-lg-4 col-xl-3 py-2">
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
    </div>
    <br>
    <jsp:include page="/WEB-INF/jsp/components/pageNavigation.jsp">
        <jsp:param name="mediaPages" value="${listsPages}"/>
        <jsp:param name="currentPage" value="${currentPage}"/>
        <jsp:param name="urlBase" value="/lists"/>
    </jsp:include>
    <jsp:include page="/WEB-INF/jsp/components/footer.jsp"/>
</body>
</html>