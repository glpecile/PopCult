<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <meta property="og:image" content="<c:url value="/resources/images/PopCultCompleteLogo.png"/>">
    <jsp:include page="/resources/externalResources.jsp"/>
    <!-- favicon -->
    <link rel="shortcut icon" href="<c:url value='/resources/images/favicon.ico'/>" type="image/x-icon">
    <title><spring:message code="login.title"/> &#8226; PopCult</title></head>
<body class="bg-gradient-to-r from-yellow-500 to-purple-900">
<%-- Variables --%>
<c:url value="/login" var="loginUrl"/>
<div class="min-h-screen flex flex-col">
    <div class="flex-grow space-y-2">
        <%-- Logo and card title --%>
        <a class="flex justify-center items-center pt-16" href="<c:url value="/"/>">
            <img class="w-32" src="<c:url value='/resources/images/PopCultLogo.png'/>" alt="popcult_logo" onclick="this.src='<c:url
                    value='/resources/images/PopCultLogoClosed.png'/>'">
        </a>
        <h2 class="font-bold text-4xl text-center text-white py-2"><spring:message code="login.greeting"/></h2>

        <%-- Sign In form --%>
        <div class="container w-full max-w-xs mx-auto mt-8 px-4 font-sans rounded-lg shadow-lg bg-white p-2 my-8">
            <form class="space-y-4" action="${loginUrl}" method="post" enctype="application/x-www-form-urlencoded">
                <c:if test="${successfulConfirmation}">
                    <label class="py-2 text-semibold text-success w-full">
                        <spring:message code="login.successfulConfirmation"/>
                    </label>
                </c:if>

                <div class="flex flex-col justify-center items-center">
                    <label class="py-2 text-semibold w-full">
                        <spring:message code="login.username"/>
                        <input class="form-control shadow-sm" type="text" name="username"/>
                    </label>
                    <label class="py-2 text-semibold w-full">
                        <spring:message code="login.password"/>
                        <input class="form-control shadow-sm" type="password" name="password"/>
                    </label>
                    <label class="py-2 text-semibold w-full">
                        <input class="shadow-sm" type="checkbox" name="rememberme">
                        <spring:message code="login.remember_me"/>
                    </label>
                    <div class="py-1 text-semibold text-danger w-full">
                        ${error}
                    </div>
                    <button class="btn btn-secondary my-2 w-full" type="submit"><spring:message code="login.login"/></button>
                    <a class="text-purple-900 text-sm hover:text-yellow-500 uppercase" href="<c:url value="/forgotPassword"/>"><spring:message
                            code="forgotPassword"/></a>
                </div>
            </form>
        </div>
        <div class="flex justify-center">
            <a class="text-white text-center text-sm hover:text-purple-900 uppercase py-2" href="<c:url value="/register"/>">
                <spring:message code="register.cta"/>
            </a>
        </div>
    </div>
    <div class="text-white">
        <jsp:include page="/WEB-INF/jsp/components/footer.jsp"/>
    </div>
</div>
</body>
</html>

