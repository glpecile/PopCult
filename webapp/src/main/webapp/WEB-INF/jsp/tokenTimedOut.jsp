<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <jsp:include page="/resources/externalResources.jsp"/>
    <!-- favicon -->
    <link rel="shortcut icon" href="<c:url value='/resources/images/favicon.ico'/>" type="image/x-icon">
    <title>Log-in &#8226; PopCult</title></head>
<body class="bg-gradient-to-r from-yellow-500 to-purple-900">
<%-- Variables --%>
<c:url value="/login" var="loginUrl"/>
<div class="flex flex-col min-h-screen">
    <div class="flex-grow space-y-2">
        <%-- Logo and card title --%>
        <a class="flex justify-center items-center pt-16" href="<c:url value="/"/>">
            <img class="w-32" src="<c:url value='/resources/images/PopCultLogo.png'/>" alt="popcult_logo" onclick="this.src='<c:url
                    value='/resources/images/PopCultLogoClosed.png'/>'">
        </a>
        <div class="container w-full max-w-xs mx-auto mt-8 px-4 font-sans rounded-lg shadow-lg bg-white p-2 my-8">
            <div class="flex flex-col justify-center items-center">
                <p class="text"><spring:message code="email.timedOutToken"/></p>
                <br>
                <a class="text-purple-900 text-sm hover:text-yellow-500 uppercase" href="<c:url value="/resendToken?token=${token}"/>"><spring:message
                        code="email.resendEmail"/></a>
            </div>
        </div>
    </div>
    <!-- Footer -->
    <div class="text-white">
        <jsp:include page="/WEB-INF/jsp/components/footer.jsp"/>
    </div>
</div>
</body>
</html>


