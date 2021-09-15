<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="j" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <jsp:include page="/resources/externalResources.jsp"/>
    <!-- favicon -->
    <link rel="shortcut icon" href="<c:url value='/resources/images/favicon.ico'/>" type="image/x-icon">
    <title>To Watch Media &#8226; PopCult</title>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/components/navbar.jsp">
    <jsp:param name="user" value="${user.username}"/>
</jsp:include>
<br>
<div class="col-8 offset-2">
    <%--    tabs     --%>
    <jsp:include page="/WEB-INF/jsp/components/userTabs.jsp">
        <jsp:param name="username" value="${user.username}"/>
        <jsp:param name="path" value="toWatchMedia"/>
    </jsp:include>
    <%-- current tab --%>
    <div class="row">
        <h2 class="font-bold text-2xl py-2">This page is not ready yet!</h2>
    </div>
</div>
<jsp:include page="/WEB-INF/jsp/components/footer.jsp"/>
</body>
</html>