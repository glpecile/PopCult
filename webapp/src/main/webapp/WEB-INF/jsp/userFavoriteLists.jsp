<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="j" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <jsp:include page="/resources/externalResources.jsp"/>
    <!-- favicon -->
    <link rel="shortcut icon" href="<c:url value='/resources/images/favicon.ico'/>" type="image/x-icon">
    <title>Favorite Lists &#8226; PopCult</title>

</head>
<body>
<jsp:include page="/WEB-INF/jsp/components/navbar.jsp">
    <jsp:param name="user" value="${user.username}"/>
</jsp:include>
<br>
<div class="col-8 offset-2 py-2">
    <%--    tabs     --%>
    <jsp:include page="/WEB-INF/jsp/components/userTabs.jsp">
        <jsp:param name="username" value="${user.username}"/>
        <jsp:param name="path" value="favLists"/>
    </jsp:include>
    <%-- current tab --%>
    <div class="row">
        <c:if test="${listsAmount == 0}">
            <h3 class="text-center">You don't seem to have any favorite lists to show! :c</h3>
        </c:if>
        <c:forEach var="cover" items="${favoriteLists}">
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
    <jsp:param name="mediaPages" value="${listsPages}"/>
    <jsp:param name="currentPage" value="${currentPage}"/>
    <jsp:param name="urlBase" value="/lists"/>
</jsp:include>
<jsp:include page="/WEB-INF/jsp/components/footer.jsp"/>
</body>
</html>