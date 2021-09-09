<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" %>
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
    <title>Page not found &#8226; PopCult</title>
</head>

<body class="bg-gray-50">
<div class="flex flex-col h-screen">
<jsp:include page="/WEB-INF/jsp/components/navbar.jsp"/>
<div class="flex-grow py-24 whitespace-pre-line">
    <h1 class="display-5 font-black whitespace-pre text-center text-justify">Error 404: </h1>
    <p class="display-5 font-semibold text-center text-justify">Page not found :c</p>
</div>
<jsp:include page="/WEB-INF/jsp/components/footer.jsp"/>
</div>
</body>
</html>
