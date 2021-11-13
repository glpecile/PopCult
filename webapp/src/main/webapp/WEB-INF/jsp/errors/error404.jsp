<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <meta property="og:image" content="<c:url value="/resources/images/PopCultCompleteLogo.png"/>">
    <jsp:include page="/resources/externalResources.jsp"/>
    <!-- favicon -->
    <link rel="shortcut icon" href="<c:url value='/resources/images/favicon.ico'/>" type="image/x-icon">
    <!-- Local CSS -->
    <link rel="stylesheet" href="<c:url value="/resources/css/overflow.css"/>"/>
    <!-- Local Scripts -->
    <script type="text/javascript" src="<c:url value="/resources/js/components/slider.js"/>"></script>
    <title><spring:message code="error.404.title"/> &#8226; PopCult</title>
</head>

<body class="bg-gray-50">
<div class="flex flex-col h-screen">
    <jsp:include page="/WEB-INF/jsp/components/navbar.jsp"/>
    <div class="flex-grow whitespace-pre-line">
        <div class="flex flex-wrap p-3.5 mx-auto my-auto">
            <img class="w-80 pt-12" src="<c:url value="/resources/images/PopCultLogoX.png"/>" alt="error_image">
            <div class="flex flex-col pl-8">
                <h1 class="text-6xl font-black text-justify">
                    <spring:message code="error.404"/>
                </h1>
                <p class="text-2xl font-semibold text-justify">
                    <spring:message code="error.404.message"/>
                </p>
                <a class="text-2xl font-bold text-purple-500 hover:text-purple-900" href="<c:url value="/"/>">
                    <spring:message code="nav.drop.home"/>
                </a>
            </div>
        </div>
    </div>
    <jsp:include page="/WEB-INF/jsp/components/footer.jsp"/>
</div>
</body>
</html>