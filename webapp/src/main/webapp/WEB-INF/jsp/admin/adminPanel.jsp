<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sring" uri="http://www.springframework.org/tags" %>

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
        <div class="grid lg:grid-cols-2 grid-cols-1 gap-5">
            <!-- Link to reports -->
            <div
                    class="group flex flex-col bg-white shadow-lg rounded-lg p-3 transition duration-500 ease-in-out hover:bg-gray-50 transform hover:-translate-y-1 hover:scale-107">
                <i class="fas fa-exclamation-circle text-center text-purple-500 group-hover:text-purple-900 fa-2x mt-3"></i>
                <h2 class="text-2xl text-center py-2 pb-2.5">
                    <a class="stretched-link text-purple-500 group-hover:text-purple-900" href="<c:url value="/admin/reports"/>">
                        <b><spring:message code="report.title.plural"/></b>
                    </a>
                </h2>
                <p class="p-2.5 m-1.5 text-center">
                    <spring:message code="admin.reports.desc"/>
                </p>
            </div>
            <!-- Link to mods -->
            <sec:authorize access="hasRole('ADMIN')">
                <div
                        class="group flex flex-col bg-white shadow-lg rounded-lg p-3 transition duration-500 ease-in-out hover:bg-gray-50 transform hover:-translate-y-1 hover:scale-107">
                    <i class="fas fa-users text-center text-purple-500 group-hover:text-purple-900 fa-2x mt-3"></i>
                    <h2 class="text-2xl text-center py-2 pb-2.5">
                        <a class="stretched-link text-purple-500 group-hover:text-purple-900" href="<c:url value="/admin/mods"/>">
                            <b><spring:message code="mods.title"/></b>
                        </a>
                    </h2>
                    <p class="p-2.5 m-1.5 text-center">
                        <spring:message code="admin.mods.desc"/>
                    </p>
                </div>
            </sec:authorize>
        </div>
    </div>
    <jsp:include page="/WEB-INF/jsp/components/footer.jsp"/>
</div>
</body>
</html>

