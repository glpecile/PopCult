<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <jsp:include page="/resources/externalResources.jsp"/>
    <!-- favicon -->
    <link rel="shortcut icon" href="<c:url value='/resources/images/favicon.ico'/>" type="image/x-icon">
    <title>Discover new Multimedia Content &#8226; PopCult</title>
</head>
<body class="bg-gray-50">
<jsp:include page="/WEB-INF/jsp/components/navbar.jsp"/>
<br>
<div class="col-8 offset-2">
    <div class="row">
        <div class="col-sm-8">
            <h2 class="font-bold text-2xl py-2">Recently added lists</h2>
        </div>
        <div class="col-sm-4 pt-2 flex justify-end">
            <a href="${pageContext.request.contextPath}/createList">
                <button class="btn btn-link text-purple-500 hover:text-purple-900 btn-rounded">+ Create new list</button>
            </a>
        </div>
        <c:forEach var="cover" items="${recentlyAdded}">
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
    <div class="row">
        <h2 class="font-bold text-2xl py-2">Discover our favorite lists!</h2>
        <c:forEach var="cover" items="${discovery}">
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
    <div class="row">
        <h2 class="font-bold text-2xl py-2">All lists</h2>
        <c:forEach var="cover" items="${allLists}">
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
</div>
<br>
<jsp:include page="/WEB-INF/jsp/components/pageNavigation.jsp">
    <jsp:param name="mediaPages" value="${allListContainer.totalPages}"/>
    <jsp:param name="currentPage" value="${allListContainer.currentPage + 1}"/>
    <jsp:param name="url" value="/lists"/>
</jsp:include>
<jsp:include page="/WEB-INF/jsp/components/footer.jsp"/>
</body>
</html>
