<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="j" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <jsp:include page="/resources/externalResources.jsp"/>
    <!-- favicon -->
    <link rel="shortcut icon" href="<c:url value='/resources/images/favicon.ico'/>" type="image/x-icon">
    <title>Profile &#8226; PopCult</title>

</head>
<body>
<jsp:include page="/WEB-INF/jsp/components/navbar.jsp"/>
<br>
<div class="col-8 offset-2">
    <%--    profile   --%>
    <jsp:include page="/WEB-INF/jsp/components/profile.jsp">
        <jsp:param name="name" value="${user.name}"/>
        <jsp:param name="username" value="${username}"/>
        <jsp:param name="profilePicture" value="https://cdn.discordapp.com/attachments/758850104517460008/885980983696973884/E-8U707WUAQVsK4.png"/>
    </jsp:include>
    <%--    tabs     --%>
    <jsp:include page="/WEB-INF/jsp/components/userTabs.jsp">
        <jsp:param name="username" value="${user.username}"/>
        <jsp:param name="path" value="myLists"/>
    </jsp:include>
    <%-- current tab --%>
    <div class="row">
        <%--        <h2 class="font-bold text-2xl py-2">My lists</h2>--%>
        <c:if test="${fn:length(lists) == 0}">
            <h3 class="text-center">You don't seem to have any favorite lists to show! :c</h3>
        </c:if>
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
        <jsp:param name="mediaPages" value="${userListsContainer.totalPages}"/>
        <jsp:param name="currentPage" value="${userListsContainer.currentPage + 1}"/>
        <jsp:param name="url" value="${urlBase}"/>
    </jsp:include>
    <jsp:include page="/WEB-INF/jsp/components/footer.jsp"/>
</body>
</html>