<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!doctype html>
<html lang="en">
<head>
    <jsp:include page="/resources/externalResources.jsp"/>
    <!-- favicon -->
    <link rel="shortcut icon" href="<c:url value='/resources/images/favicon.ico'/>" type="image/x-icon">
    <!-- Local CSS -->
    <link rel="stylesheet" href="<c:url value="/resources/css/overflow.css"/>"/>
    <!-- Local Scripts -->
    <script type="text/javascript" src="<c:url value="/resources/js/components/slider.js"/>"></script>
    <title><spring:message code="admin.title"/> &#8226; PopCult</title>
</head>

<body class="bg-gray-50">
<div class="min-h-screen flex flex-col">
    <jsp:include page="/WEB-INF/jsp/components/navbar.jsp"/>
    <div class="col-8 offset-2 flex-grow">
        <!-- Admin Panel message -->
        <h1 class="text-center display-5 fw-bolder py-4">
            <spring:message code="admin.title"/>
        </h1>
        <!-- Link to reports -->
        <h2 class="text-xl py-2 pb-2.5"><a class="hover:text-gray-800" href="<c:url value="/admin/reports"/>">
            <b class="text-purple-500 hover:text-purple-900"><spring:message code="report.title.plural"/></b>
        </a></h2>
        <!-- Link to mods -->
        <h2 class="text-xl py-2 pb-2.5"><a class="hover:text-gray-800" href="<c:url value="/admin/mods"/>">
            <b class="text-purple-500 hover:text-purple-900"><spring:message code="mods.title"/></b>
        </a></h2>

    </div>
    <jsp:include page="/WEB-INF/jsp/components/footer.jsp"/>
</div>
</body>
</html>

