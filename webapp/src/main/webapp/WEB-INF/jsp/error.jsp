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
    <title> &#8226; PopCult</title>
</head>

<body class="bg-gray-50">
<div class="flex flex-col h-screen">
    <jsp:include page="/WEB-INF/jsp/components/navbar.jsp"/>
    <div class="flex-grow py-24 whitespace-pre-line">
        <h1 class="display-5 font-black whitespace-pre-wrap text-center text-justify"><c:out value="${title}"/></h1>
        <p class="display-5 font-semibold text-center text-justify"><c:out value="${description}"/></p>
    </div>
    <jsp:include page="/WEB-INF/jsp/components/footer.jsp"/>
</div>
</body>
</html>
